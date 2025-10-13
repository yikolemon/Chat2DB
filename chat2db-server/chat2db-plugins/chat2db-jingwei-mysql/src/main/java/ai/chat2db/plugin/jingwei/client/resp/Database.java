package ai.chat2db.plugin.jingwei.client.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.Map;

/**
 * 数据库信息
 */
@Data
public class Database {
    
    /**
     * 数据库ID
     */
    @JSONField(name = "id")
    private Integer id;
    
    /**
     * 数据库类型
     */
    @JSONField(name = "db_type")
    private String dbType;
    
    /**
     * 集群ID
     */
    @JSONField(name = "cluster_id")
    private String clusterId;
    
    /**
     * 数据库名称
     */
    @JSONField(name = "database_name")
    private String databaseName;
    
    /**
     * 描述
     */
    @JSONField(name = "description")
    private String description;
    
    /**
     * 负责人信息
     */
    @JSONField(name = "owner")
    private Map<String, Object> owner;
    
    /**
     * 重要性级别
     */
    @JSONField(name = "importance")
    private Integer importance;
    
    /**
     * 标志
     */
    @JSONField(name = "flags")
    private String flags;
}