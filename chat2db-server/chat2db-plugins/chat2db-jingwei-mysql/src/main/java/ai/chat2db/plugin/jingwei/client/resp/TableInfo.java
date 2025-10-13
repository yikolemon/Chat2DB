package ai.chat2db.plugin.jingwei.client.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 表信息响应实体
 */
@Data
public class TableInfo {
    
    /**
     * 表名
     */
    @JSONField(name = "table_name")
    private String tableName;
    
    /**
     * 表注释
     */
    @JSONField(name = "table_comment")
    private String tableComment;
    
    /**
     * 创建时间
     */
    @JSONField(name = "create_time")
    private String createTime;
    
    /**
     * 表类型
     */
    @JSONField(name = "table_type")
    private String tableType;
}