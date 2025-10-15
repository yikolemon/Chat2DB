package ai.chat2db.plugin.jingwei.driver.table;

import ai.chat2db.plugin.jingwei.client.JingWeiClient;
import ai.chat2db.plugin.jingwei.client.resp.TableInfoResponse;
import ai.chat2db.plugin.jingwei.client.resp.TableListResponse;
import ai.chat2db.plugin.jingwei.driver.TokenManager;
import ai.chat2db.spi.model.Table;
import ai.chat2db.spi.model.TableColumn;
import ai.chat2db.spi.model.TableIndex;
import ai.chat2db.spi.model.TableIndexColumn;
import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 管理表信息（线程安全版本）
 */
public class TablesManager {



    private static final ConcurrentHashMap<String, ReentrantLock> LOCK_MAP = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(TablesManager.class);

    // 全局表信息映射（key = databaseName:tableName）
    private static final Map<String, Table> tableMap = new ConcurrentHashMap<>();

    // 按数据库分组的表信息映射（key = databaseName, value = Map<tableName, TableInfo>）
    private static final Map<String, Map<String, Table>> schemaTableMap = new ConcurrentHashMap<>();

    // 按集群ID分组的表信息映射（key = clusterId, value = Map<databaseName:tableName, TableInfo>）
    private static final Map<String, Map<String, Table>> clusterTableMap = new ConcurrentHashMap<>();

    // 控制初始化只执行一次
    private static final Map<String, Boolean> initializedMap = new ConcurrentHashMap<>();
    
    private static final Map<String, Boolean> tableInfoInitializedMap = new ConcurrentHashMap<>();

    private TablesManager() {
        // 私有化构造函数，防止实例化
    }
    
    public static void initTableInfo(String clusterId, String databaseName, String tableName) {
        String key = getTableKey(clusterId, databaseName, tableName);

        // 已初始化则直接返回
        if (Boolean.TRUE.equals(tableInfoInitializedMap.get(key))) {
            return;
        }

        // 获取该表对应的锁对象（若不存在则新建）
        ReentrantLock lock = LOCK_MAP.computeIfAbsent(key, k -> new ReentrantLock());
        lock.lock();
        try{
            // 双重检查，防止重复初始化
            if (Boolean.TRUE.equals(tableInfoInitializedMap.get(key))) {
                return;
            }

            // 执行初始化逻辑
            Table tableInfo = fetchTableInfo(tableName, clusterId, databaseName);

            // 存储结果
            tableMap.put(key, tableInfo);
            schemaTableMap
                    .computeIfAbsent(databaseName, db -> new ConcurrentHashMap<>())
                    .put(tableName, tableInfo);

            tableInfoInitializedMap.put(key, true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
            // 可选：释放锁对象，避免LOCK_MAP无限增长
            LOCK_MAP.remove(key);
        }
    }


    /**
     * 加载指定数据库的表信息
     */
    private static void loadTablesForDatabase(String clusterId, String databaseName) throws SQLException {
        String key = clusterId + "#" + databaseName;
        ReentrantLock lock = LOCK_MAP.computeIfAbsent(key, k -> new ReentrantLock());

        lock.lock();
        try {
            String token = TokenManager.getToken();

            // 第一步：获取表名列表
            JingWeiClient client = Forest.client(JingWeiClient.class);
            ForestResponse<TableListResponse> tableListResponse = client.getTableList(clusterId, databaseName, token);

            if (tableListResponse == null || !tableListResponse.isSuccess()) {
                logger.error("fetch table list error for database: {}", databaseName);
                throw new SQLException("加载精卫表名列表失败");
            }
            if (tableListResponse.getResult() == null || !Boolean.TRUE.equals(tableListResponse.getResult().getSuccess())) {
                logger.error("fetch table list error for database: {}", databaseName);
                throw new SQLException("加载精卫表名列表失败");
            }

            List<String> tableNameList = tableListResponse.getResult().getData();
            if (CollectionUtils.isEmpty(tableNameList)) {
                return;
            }

            // 先用表名创建基本信息
            processTables(tableNameList, clusterId, databaseName);

        } catch (Exception e) {
            logger.error("Error loading tables for database: {}", databaseName, e);
            throw new SQLException("获取表信息失败", e);
        } finally {
            lock.unlock();
            // 可选：清理锁对象，避免无限增长
            LOCK_MAP.remove(key, lock);
        }
    }

    /**
     * 处理表名列表（仅包含基本信息）
     */
    private static void processTables(List<String> tables, String clusterId, String databaseName){
        if (tables == null || tables.isEmpty()) return;
        
        // 获取或创建数据库表映射
        Map<String, Table> dbTableMap = schemaTableMap.computeIfAbsent(databaseName, k -> new ConcurrentHashMap<>());
        
        // 获取或创建集群表映射
        Map<String, Table> clusterTables = clusterTableMap.computeIfAbsent(clusterId, k -> new ConcurrentHashMap<>());
        
        for (String tableName : tables) {
            Table table = new Table();
            table.setDetailInfo(false);
            table.setName(tableName);
            table.setDatabaseName(databaseName);
            table.setDbType("JINGWEI");
            // 写入缓存（线程安全）
            dbTableMap.put(tableName, table);
            clusterTables.put(tableName, table);
            tableMap.put(tableName, table);
        }
    }
    
    
    
    private static Table fetchTableInfo(String tableName, String clusterId, String databaseName) throws SQLException {
        String token = TokenManager.getToken();
        JingWeiClient client = Forest.client(JingWeiClient.class);
        ForestResponse<TableInfoResponse> tableInfoResp = client.getTableColumnsAndIndex(clusterId, databaseName, tableName, token);
        if (tableInfoResp == null || !tableInfoResp.isSuccess()) {
            throw new SQLException("获取TABLE失败");
        }
        if (tableInfoResp.getResult() == null || !Boolean.TRUE.equals(tableInfoResp.getResult().getSuccess())) {
            throw new SQLException("获取TABLE失败");
        }
        TableInfoResponse.TableInfoData data = tableInfoResp.getResult().getData();
        List<TableInfoResponse.IndexInfo> indexInfoList = data.getIndex();
        List<TableInfoResponse.ColumnInfo> columnInfoList = data.getColumns();

        // 创建并填充 Table 对象
        Table table = new Table();
        table.setName(tableName);
        table.setSchemaName(databaseName);
        table.setDatabaseName(databaseName);
        table.setDbType("MySQL"); // 假设是MySQL类型，根据实际情况调整

        // 处理列信息
        if (columnInfoList != null && !columnInfoList.isEmpty()) {
            List<TableColumn> tableColumns = new ArrayList<>();
            for (TableInfoResponse.ColumnInfo columnInfo : columnInfoList) {
                TableColumn tableColumn = new TableColumn();
                tableColumn.setTableName(tableName);
                tableColumn.setName(columnInfo.getColumnName());
                tableColumn.setColumnType(columnInfo.getTypeName());
                tableColumn.setDataType(null);
                tableColumn.setComment(columnInfo.getRemarks());
                tableColumn.setNullable(columnInfo.getIsNullable() ? 1 : 0);
                tableColumn.setPrimaryKey(columnInfo.getIsPrimaryKey());
                tableColumn.setAutoIncrement(columnInfo.getIsAutoincrement());
                tableColumn.setOrdinalPosition(columnInfo.getOrdinalPosition());
                tableColumns.add(tableColumn);
            }
            table.setColumnList(tableColumns);
        }

        // 处理索引信息
        if (indexInfoList != null && !indexInfoList.isEmpty()) {
            List<TableIndex> tableIndices = new ArrayList<>();
            for (TableInfoResponse.IndexInfo indexInfo : indexInfoList) {
                TableIndex tableIndex = new TableIndex();
                tableIndex.setName(indexInfo.getIndexName());
                tableIndex.setType(indexInfo.getIndexType());

                // 处理索引列
                if (indexInfo.getIndexColumns() != null && !indexInfo.getIndexColumns().isEmpty()) {
                    List<TableIndexColumn> indexColumns = new ArrayList<>();
                    for (String columnName : indexInfo.getIndexColumns()) {
                        TableIndexColumn indexColumn = new TableIndexColumn();
                        indexColumn.setIndexName(columnName);
                        // 由于 IndexInfo 中没有提供列的顺序和排序方向，这里设置默认值
                        indexColumn.setOrdinalPosition((short) 1); // 默认位置
                        indexColumn.setAscOrDesc("A"); // 默认升序
                        indexColumns.add(indexColumn);
                    }
                    tableIndex.setColumnList(indexColumns);
                }

                tableIndices.add(tableIndex);
            }
            table.setIndexList(tableIndices);
        }
        // 如果有分区信息，设置分区
        if (data.getPartition() != null) {
            table.setPartition(data.getPartition().toString());
        }
        table.setDetailInfo(true);
        return table;
    }

    /**
     * 获取所有表的列表
     */
    public static List<String> getAllTables() {
        return new ArrayList<>(tableMap.keySet());
    }

    /**
     * 获取指定数据库下的所有表列表
     */
    public static List<String> getTables(String databaseName) {
        Map<String, Table> map = schemaTableMap.get(databaseName);
        if (map == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(map.keySet());
    }

    /**
     * 获取指定数据库下的所有表信息
     */
    public static List<Table> getTableInfos(String clusterId, String databaseName) throws SQLException {
        Map<String, Table> map = schemaTableMap.get(databaseName);
        if (map == null) {
            //初始化该dataBaseName
            loadTablesForDatabase(clusterId, databaseName);
            map = schemaTableMap.get(databaseName);
        }
        if (map == null) {
            return new ArrayList<>();
        } else  {
            return new ArrayList<>(map.values());
        }
    }

    /**
     * 获取指定数据库和表名的表信息
     */
    public static Table getTableInfo(String clusterId, String databaseName, String tableName) {
        String key = getTableKey(clusterId, databaseName, tableName);
        return tableMap.get(key);
    }

    /**
     * 获取指定集群下的所有表信息
     */
    public static List<Table> getTableInfosByCluster(String clusterId) {
        Map<String, Table> map = clusterTableMap.get(clusterId);
        if (map == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 清空表信息
     */
    public static void clear(String idc) {
        String intern = idc.intern();
        synchronized (intern) {
            tableMap.clear();
            schemaTableMap.clear();
            clusterTableMap.clear();
            // 标记未初始化
            initializedMap.remove(idc);
        }
    }

    /**
     * 生成表的唯一键
     */
    private static String getTableKey(String clusterId, String databaseName, String tableName) {
        return clusterId+ "#" + databaseName + "#" + tableName;
    }
}