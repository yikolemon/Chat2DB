package ai.chat2db.plugin.jingwei.client.resp;

import lombok.Data;

import java.util.List;

/**
 * 表列表响应
 */
@Data
public class TableListResponse {
    
    /**
     * 数据内容，表名列表
     */
    private List<String> data;
    
    /**
     * 状态码
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