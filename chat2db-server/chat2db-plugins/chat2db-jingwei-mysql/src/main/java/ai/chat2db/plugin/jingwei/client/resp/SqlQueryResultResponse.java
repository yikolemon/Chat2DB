package ai.chat2db.plugin.jingwei.client.resp;

import lombok.Data;

import java.util.List;

/**
 * SQL查询结果响应
 */
@Data
public class SqlQueryResultResponse {
    
    /**
     * 数据内容
     */
    private List<SqlExeRecord> data;
    
    /**
     * 状态码，1表示成功
     */
    private Integer status;
    
    /**
     * 消息
     */
    private String message;
    
    /**
     * 是否成功
     */
    private Boolean success;
    

}