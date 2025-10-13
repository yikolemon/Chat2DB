package ai.chat2db.plugin.jingwei.driver.catalog;

import lombok.Data;

/**
 * Catalog详细信息
 */
@Data
public class CatalogInfo {
    private String clusterId;
    private Integer dataBaseId;
    private Integer dataSourcePort;
    private Integer dataSourceType;
    private String environment;
    private String businessName;
    private String domain;
    private String databaseName;
}