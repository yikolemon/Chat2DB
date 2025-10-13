package ai.chat2db.plugin.jingwei.client.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * SQL 执行详情响应
 * author: duanfuqiang
 * date: 2025/9/30 10:09
 */
@Data
public class SqlExecDetailResp {

    /**
     * 是否成功
     */
    @JSONField(name = "success")
    private boolean success;

    /**
     * data 字段（二维数组，每行数据对应 fields）
     */
    @JSONField(name = "data")
    private List<List<String>> data;

    /**
     * 延迟
     */
    @JSONField(name = "delay")
    private Integer delay;

    /**
     * 执行耗时
     */
    @JSONField(name = "executetime")
    private Integer executetime;

    /**
     * 字段类型映射
     * key = 字段名, value = 类型字符串
     */
    @JSONField(name = "field_type")
    private Map<String, String> fieldType;

    /**
     * 字段列表（和 data 的每列顺序对应）
     */
    @JSONField(name = "fields")
    private List<String> fields;

    /**
     * 消息
     */
    @JSONField(name = "message")
    private String message;

    /**
     * 执行的 SQL
     */
    @JSONField(name = "sql")
    private String sql;
}
