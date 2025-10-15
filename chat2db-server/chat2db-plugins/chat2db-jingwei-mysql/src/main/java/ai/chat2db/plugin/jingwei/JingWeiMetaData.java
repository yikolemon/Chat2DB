package ai.chat2db.plugin.jingwei;

import ai.chat2db.plugin.jingwei.driver.JingWeiSQLExecutor;
import ai.chat2db.plugin.jingwei.driver.catalog.CatalogInfo;
import ai.chat2db.plugin.jingwei.driver.catalog.CatalogsManager;
import ai.chat2db.plugin.jingwei.driver.catalog.ClusterInfo;
import ai.chat2db.plugin.jingwei.driver.connection.ConnectionInfo;
import ai.chat2db.plugin.jingwei.driver.connection.JingWeiConnection;
import ai.chat2db.plugin.jingwei.driver.table.TablesManager;
import ai.chat2db.server.tools.base.wrapper.result.PageResult;
import ai.chat2db.spi.CommandExecutor;
import ai.chat2db.spi.MetaData;
import ai.chat2db.spi.SqlBuilder;
import ai.chat2db.spi.ValueProcessor;
import ai.chat2db.spi.jdbc.DefaultMetaService;
import ai.chat2db.spi.model.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author duanfuqiang
 * @date 2025/9/30 15:18
 */
@AllArgsConstructor
public class JingWeiMetaData implements MetaData {
    
    @Override
    public List<Database> databases(Connection connection) {
        if (!(connection instanceof JingWeiConnection jingWeiConnection)) {
            throw new RuntimeException("精卫 connection 错误");
        }
        ConnectionInfo conInfo = jingWeiConnection.getConInfo();
        Map<ClusterInfo, Map<String, CatalogInfo>> cataLogs =
                CatalogsManager.getCataLogsGroupByCluster(conInfo.getIdc());
        if (MapUtils.isEmpty(cataLogs)) {
            return Collections.emptyList();
        }
        List<Database> result = new ArrayList<>();
        for (Map.Entry<ClusterInfo, Map<String, CatalogInfo>> entry : cataLogs.entrySet()) {
            ClusterInfo cluster = entry.getKey();
            Map<String, CatalogInfo> catalogs = entry.getValue();

            Database database = new Database();
            // cluster 信息作为 Database 的上层标识
            database.setName(cluster.getClusterId());
            database.setComment(cluster.getDomain());
            database.setCharset("utf8mb4");
            database.setCollation("utf8mb4_general_ci");
            database.setOwner("null");

            // schemas = 当前 cluster 下所有的数据库名
            List<Schema> schemaList = new ArrayList<>();
            for (Map.Entry<String, CatalogInfo> catalogEntry : catalogs.entrySet()) {
                String dbName = catalogEntry.getKey();
                CatalogInfo catalogInfo = catalogEntry.getValue();
                Schema schema = new Schema();
                schema.setName(dbName);
                schema.setComment(catalogInfo.getDomain());
                schemaList.add(schema);
            }
            database.setSchemas(schemaList);
            result.add(database);
        }
        return result;
    }


    @Override
    public List<Schema> schemas(Connection connection, String databaseName) {
        List<Database> databases = databases(connection);

        if (CollectionUtils.isEmpty(databases)) {
            return Collections.emptyList();
        }
        // 如果传了 databaseName，只返回对应的 schemas
        if (StringUtils.isNotEmpty(databaseName)) {
            Database database = databases.stream()
                    .filter(d -> databaseName.equals(d.getName()))
                    .findFirst()
                    .orElse(null);

            if (database == null || CollectionUtils.isEmpty(database.getSchemas())) {
                return Collections.emptyList();
            }
            return database.getSchemas();
        }

        // 否则合并所有 cluster 下的 schemas
        List<Schema> allSchemas = new ArrayList<>();
        for (Database db : databases) {
            if (CollectionUtils.isNotEmpty(db.getSchemas())) {
                allSchemas.addAll(db.getSchemas());
            }
        }
        return allSchemas;
    }


    @Override
    public String tableDDL(Connection connection, String databaseName, String schemaName, String tableName) {
        return "";
    }

    @Override
    public List<Table> tables(Connection connection, String databaseName, String schemaName, String tableName) {
        if (connection instanceof JingWeiConnection) {
            try {
                if (StringUtils.isEmpty(databaseName) && StringUtils.isNotEmpty(schemaName)) {
                    CatalogInfo catalogInfo = CatalogsManager.getCatalogInfo(schemaName);
                    if (catalogInfo == null) {
                        throw new RuntimeException("获取库信息失败");
                    }
                    databaseName = catalogInfo.getClusterId();
                }
                // 确保TablesManager已初始化
                return TablesManager.getTableInfos(databaseName, schemaName);
            } catch (Exception e) {
                throw new RuntimeException("获取表信息失败", e);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> tableNames(Connection connection, String databaseName, String schemaName, String tableName) {
        return Collections.emptyList();
    }

    @Override
    public PageResult<Table> tables(Connection connection, String databaseName, String schemaName, String tableNamePattern, int pageNo, int pageSize) {
//        if (!(connection instanceof JingWeiConnection jingWeiConnection)) {
//            throw new RuntimeException("精卫链接错误");
//        }
//        try {
//            // 确保TablesManager已初始化
//            ConnectionInfo conInfo = jingWeiConnection.getConInfo();
//            String idc = conInfo.getIdc();
//            TablesManager.initTables(idc);
//            
//            // 获取所有表
//            List<Table> allTables = tables(connection, databaseName, schemaName, tableNamePattern);
//            
//            if (CollectionUtils.isEmpty(allTables)) {
//                return PageResult.empty(pageNo, pageSize);
//            }
//            
//            // 计算分页
//            int total = allTables.size();
//            int start = (pageNo - 1) * pageSize;
//            int end = Math.min(start + pageSize, total);
//            
//            if (start >= total) {
//                return PageResult.empty(pageNo, pageSize);
//            }
//            
//            List<Table> pagedTables = allTables.subList(start, end);
//            return PageResult.of(pagedTables, (long) total, pageNo, pageSize);
//        } catch (Exception e) {
//            throw new RuntimeException("获取分页表信息失败", e);
//        }
        throw new RuntimeException("未支持");
    }

    @Override
    public Table view(Connection connection, String databaseName, String schemaName, String viewName) {
        return null;
    }

    @Override
    public List<String> viewNames(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public List<Table> views(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public List<Function> functions(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public List<Trigger> triggers(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public List<Procedure> procedures(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public List<TableColumn> columns(Connection connection, String databaseName, String schemaName, String tableName) {
        Table tableInfo = TablesManager.getTableInfo(databaseName, schemaName, tableName);
        if (tableInfo == null || !Boolean.TRUE.equals(tableInfo.getDetailInfo())) {
            //加载表索引及列信息
            if (StringUtils.isEmpty(databaseName)) {
                CatalogInfo catalogInfo = CatalogsManager.getCatalogInfo(schemaName);
                if (catalogInfo == null || StringUtils.isEmpty(catalogInfo.getClusterId())) {
                    throw new RuntimeException("获取cluster信息失败");
                }
                databaseName = catalogInfo.getClusterId();
            }
            TablesManager.initTableInfo(databaseName, schemaName, tableName);
            tableInfo = TablesManager.getTableInfo(databaseName, schemaName, tableName);
        }
        if (tableInfo == null) {
            throw new RuntimeException("获取表列信息错误");
        }
        return tableInfo.getColumnList();
    }

    @Override
    public List<TableColumn> columns(Connection connection, String databaseName, String schemaName, String tableName, String columnName) {
        return List.of();
    }

    @Override
    public List<TableIndex> indexes(Connection connection, String databaseName, String schemaName, String tableName) {
        return List.of();
    }

    @Override
    public Function function(Connection connection, String databaseName, String schemaName, String functionName) {
        return null;
    }

    @Override
    public Trigger trigger(Connection connection, String databaseName, String schemaName, String triggerName) {
        return null;
    }

    @Override
    public Procedure procedure(Connection connection, String databaseName, String schemaName, String procedureName) {
        return null;
    }

    @Override
    public List<Type> types(Connection connection) {
        return List.of();
    }

    @Override
    public SqlBuilder getSqlBuilder() {
        return null;
    }

    @Override
    public TableMeta getTableMeta(String databaseName, String schemaName, String tableName) {
        return null;
    }

    @Override
    public String getMetaDataName(String... names) {
        return "";
    }

    @Override
    public ValueProcessor getValueProcessor() {
        return null;
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return JingWeiSQLExecutor.getInstance();
    }

    @Override
    public List<String> getSystemDatabases() {
        return List.of();
    }

    @Override
    public List<String> getSystemSchemas() {
        return List.of();
    }

    @Override
    public String sequenceDDL(Connection connection, String databaseName, String schemaName, String tableName) {
        return "";
    }

    @Override
    public List<SimpleSequence> sequences(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public Sequence sequences(Connection connection, String databaseName, String schemaName, String sequenceName) {
        return null;
    }

    @Override
    public List<String> usernames(Connection connection) {
        return List.of();
    }
}
