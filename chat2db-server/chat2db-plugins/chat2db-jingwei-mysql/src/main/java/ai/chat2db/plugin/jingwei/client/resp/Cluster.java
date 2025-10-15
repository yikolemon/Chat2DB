package ai.chat2db.plugin.jingwei.client.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 数据库集群信息
 */
@Data
public class Cluster {
    
    /**
     * 集群ID
     */
    @JSONField(name = "cluster_id")
    @JsonProperty("cluster_id")
    private String clusterId;
    
    /**
     * 环境
     */
    @JSONField(name = "environment")
    @JsonProperty("environment")
    private String environment;
    
    /**
     * 业务名称
     */
    @JSONField(name = "business_name")
    @JsonProperty("business_name")
    private String businessName;
    
    /**
     * 业务信息
     */
    @JSONField(name = "business_info")
    @JsonProperty("business_info")
    private String businessInfo;
    
    /**
     * 域名
     */
    @JSONField(name = "domain")
    @JsonProperty("domain")
    private String domain;
    
    /**
     * 端口
     */
    @JSONField(name = "port")
    @JsonProperty("port")
    private Integer port;
    
    /**
     * 标签
     */
    @JSONField(name = "tags")
    @JsonProperty("tags")
    private String tags;
    
    /**
     * 描述
     */
    @JSONField(name = "description")
    @JsonProperty("description")
    private String description;
    
    /**
     * IDC
     */
    @JSONField(name = "idc")
    @JsonProperty("idc")
    private String idc;
    
    /**
     * 数据库列表
     */
    @JSONField(name = "databases")
    @JsonProperty("databases")
    private List<Database> databases;
}
