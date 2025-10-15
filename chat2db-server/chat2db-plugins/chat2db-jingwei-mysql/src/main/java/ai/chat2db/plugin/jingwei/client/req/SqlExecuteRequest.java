package ai.chat2db.plugin.jingwei.client.req;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SQL执行请求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SqlExecuteRequest {
    
    /**
     * SQL语句
     */
    private String sql;
    
    /**
     * 环境，2表示生产环境
     */
    private Integer env;
    
    /**
     * 业务ID
     */
    @JSONField(name = "business_id")
    @JsonProperty("business_id")
    private String businessId;
    
    /**
     * 业务名称
     */
    @JSONField(name = "business_name")
    @JsonProperty("business_name")
    private String businessName;
    
    /**
     * 数据源类型，0表示MySQL
     */
    @JSONField(name = "datasource_type")
    @JsonProperty("datasource_type")
    private Integer datasourceType;
    
    /**
     * 数据源URL
     */
    @JSONField(name = "datasource_url")
    @JsonProperty("datasource_url")
    private String datasourceUrl;
    
    /**
     * 数据源端口
     */
    @JSONField(name = "datasource_port")
    @JsonProperty("datasource_port")
    private Integer datasourcePort;
    
    /**
     * 数据库ID
     */
    @JSONField(name = "database_id")
    @JsonProperty("database_id")
    private Integer databaseId;
}