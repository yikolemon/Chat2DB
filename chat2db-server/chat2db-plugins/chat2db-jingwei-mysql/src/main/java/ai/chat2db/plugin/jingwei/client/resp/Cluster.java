package ai.chat2db.plugin.jingwei.client.resp;

import com.alibaba.fastjson2.annotation.JSONField;
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
    private String clusterId;
    
    /**
     * 环境
     */
    @JSONField(name = "environment")
    private String environment;
    
    /**
     * 业务名称
     */
    @JSONField(name = "business_name")
    private String businessName;
    
    /**
     * 业务信息
     */
    @JSONField(name = "business_info")
    private String businessInfo;
    
    /**
     * 域名
     */
    @JSONField(name = "domain")
    private String domain;
    
    /**
     * 端口
     */
    @JSONField(name = "port")
    private Integer port;
    
    /**
     * 标签
     */
    @JSONField(name = "tags")
    private String tags;
    
    /**
     * 描述
     */
    @JSONField(name = "description")
    private String description;
    
    /**
     * IDC
     */
    @JSONField(name = "idc")
    private String idc;
    
    /**
     * 数据库列表
     */
    @JSONField(name = "databases")
    private List<Database> databases;
}
