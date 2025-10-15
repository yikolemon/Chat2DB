package ai.chat2db.plugin.jingwei.driver.catalog;

import ai.chat2db.plugin.jingwei.client.JingWeiClient;
import ai.chat2db.plugin.jingwei.client.resp.BaseResp;
import ai.chat2db.plugin.jingwei.client.resp.Cluster;
import ai.chat2db.plugin.jingwei.client.resp.Database;
import ai.chat2db.plugin.jingwei.driver.Constants;
import ai.chat2db.plugin.jingwei.driver.TokenManager;
import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestCookie;
import com.dtflys.forest.http.ForestResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理数据库 catalog 信息（线程安全版本，不包含 currentCatalog）
 */
public class CatalogsManager {

    private static final Logger logger = LoggerFactory.getLogger(CatalogsManager.class);

    // 全局 catalogMap（key = databaseName）
    private static final Map<String, CatalogInfo> catalogMap = new ConcurrentHashMap<>();

    // 按 idc 分组的 catalogMap（key = idc/environment, value = Map<databaseName, CatalogInfo>）
    private static final Map<String, Map<String, CatalogInfo>> idcCatalogMap = new ConcurrentHashMap<>();

    // 新增：按 clusterId 划分 catalog
    // key = clusterId, value = Map<databaseName, CatalogInfo>
    private static final Map<String, Map<String, CatalogInfo>> clusterCatalogMap = new ConcurrentHashMap<>();

    private static final Map<String, ClusterInfo> clusterMap = new ConcurrentHashMap<>();
    
    // 控制初始化只执行一次
    private static volatile boolean initialized = false;

    private CatalogsManager() {
        // 私有化构造函数，防止实例化tr
    }

    /**
     * 初始化 catalog 信息（线程安全，避免重复 init）
     */
    public static void initCatalogs() throws SQLException {
        if (!initialized) { // 第一重检查
            synchronized (CatalogsManager.class) {
                if (!initialized) { // 第二重检查
                    try {
                        String token = TokenManager.getToken();
                        String env = Constants.JING_WEI_CATA_LOG_QUERY_ENV;
                        String queryType = Constants.JING_WEI_CATA_LOG_QUERY_TYPE;
                        ForestResponse<BaseResp<List<Cluster>>> response = Forest.client(JingWeiClient.class)
                                .getCataLogs(queryType, env, token);
                        if (response != null && response.isSuccess() && response.status_2xx()) {
                            BaseResp<List<Cluster>> clustersResp = response.getResult();
                            if (!Boolean.TRUE.equals(clustersResp.getSuccess())) {
                                logger.error("fetch cataLogs error");
                                throw new SQLException("获取数据库结构失败");
                            }
                            List<Cluster> clusterList = clustersResp.getData();
                            processClusters(clusterList);
                            initialized = true;
                        } else if (response != null && response.status_3xx()){
                            TokenManager.clearToken();
                            throw new RuntimeException("精卫TOKEN过期");                        
                        } else {
                            throw new RuntimeException("精卫访问失败");
                        }
                    } catch (Exception e) {
                        logger.error("Error loading catalogs", e);

                    }
                }
            }
        }
    }

    /**
     * 强制刷新 catalog 信息（线程安全）
     */
    public static void refreshCatalogs() throws SQLException {
        synchronized (CatalogsManager.class) {
            catalogMap.clear();
            idcCatalogMap.clear();
            initialized = false;
        }
        initCatalogs();
    }

    /**
     * 处理集群信息，提取所有数据库作为 catalog
     */
    private static void processClusters(List<Cluster> clusters) {
        if (clusters == null || clusters.isEmpty()) return;

        for (Cluster cluster : clusters) {
            String clusterId = cluster.getClusterId();
            String environment = cluster.getEnvironment(); // 这里作为 idc
            String businessName = cluster.getBusinessName();
            String domain = cluster.getDomain();
            String idc = cluster.getIdc();
            Integer port = cluster.getPort();

            List<Database> databases = cluster.getDatabases();
            
            if (databases != null) {
                for (Database db : databases) {
                    String databaseName = db.getDatabaseName();
                    CatalogInfo catalogInfo = new CatalogInfo();
                    catalogInfo.setClusterId(clusterId);
                    catalogInfo.setDataBaseId(db.getId());
                    catalogInfo.setDataSourcePort(port);
                    catalogInfo.setDataSourceType(0);
                    catalogInfo.setEnvironment(environment);
                    catalogInfo.setBusinessName(businessName);
                    catalogInfo.setDomain(domain);
                    catalogInfo.setDatabaseName(databaseName);

                    // 全局存一份
                    catalogMap.put(databaseName, catalogInfo);
                    ClusterInfo clusterInfo = trans2ClusterInfo(cluster);
                    // idc 分组存一份
                    idcCatalogMap.computeIfAbsent(idc, k -> new ConcurrentHashMap<>()).put(databaseName, catalogInfo);

                    //clster 分组存一份
                    clusterMap.put(clusterId, clusterInfo);
                    clusterCatalogMap.computeIfAbsent(clusterId, k -> new ConcurrentHashMap<>()).put(databaseName, catalogInfo);
                }
            }
        }
    }
    
    private static ClusterInfo trans2ClusterInfo(Cluster cluster) {
        ClusterInfo clusterInfo = new ClusterInfo();
        BeanUtils.copyProperties(cluster, clusterInfo);
        return clusterInfo;
    }

    /**
     * 获取所有可用的 catalog 列表
     */
    public static List<String> getAllCatalogs() {
        return new ArrayList<>(catalogMap.keySet());
    }

    /**
     * 获取某个 idc 下的所有 catalog 列表
     */
    public static List<String> getCataLogs(String idc) {
        Map<String, CatalogInfo> map = idcCatalogMap.get(idc);
        if (map == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(map.keySet());
    }
    
    /**
     * 根据 idc 获取该 idc 下所有 cluster 的 catalog 映射
     * key = ClusterInfo
     * value = Map<databaseName, CatalogInfo>
     */
    public static Map<ClusterInfo, Map<String, CatalogInfo>> getCataLogsGroupByCluster(String idc) {
        if (MapUtils.isEmpty(clusterMap)) {
            return Collections.emptyMap();
        }

        // 过滤出指定 idc 的 cluster 列表
        List<ClusterInfo> clusters = clusterMap.values().stream()
                .filter(cluster -> idc.equals(cluster.getIdc()))
                .toList();

        if (CollectionUtils.isEmpty(clusters)) {
            return Collections.emptyMap();
        }

        // 从 clusterCatalogMap 中筛选出属于这些 cluster 的项
        Map<ClusterInfo, Map<String, CatalogInfo>> result = new ConcurrentHashMap<>();
        for (ClusterInfo cluster : clusters) {
            Map<String, CatalogInfo> catalogs = clusterCatalogMap.get(cluster.getClusterId());
            if (MapUtils.isNotEmpty(catalogs)) {
                result.put(cluster, new ConcurrentHashMap<>(catalogs));
            }
        }

        return result;
    }



    /**
     * 获取指定 catalog 的详细信息
     */
    public static CatalogInfo getCatalogInfo(String catalog) {
        return catalogMap.get(catalog);
    }

    /**
     * 清空 catalog 信息
     */
    public static void clear() {
        synchronized (CatalogsManager.class) {
            catalogMap.clear();
            idcCatalogMap.clear();
            initialized = false;
        }
    }
    
    public static ClusterInfo getClusterInfo(String clusterId) {
        return clusterMap.get(clusterId);
    }
}
