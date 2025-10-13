package ai.chat2db.plugin.jingwei.client.resp;

import lombok.Data;

/**
 * SQL执行响应
 */
@Data
public class SqlExecuteResponse {
    
    /**
     * 数据，可能是影响的行数
     */
    private Long data;
    
    /**
     * 状态码，1表示成功
     */
    private Integer status;
    
    /**
     * 消息
     */
    private String message;
    
    /**
     * Druid解析错误数据
     */
    private Object druidParseErrorData;
    
    /**
     * 是否成功
     */
    private Boolean success;
}