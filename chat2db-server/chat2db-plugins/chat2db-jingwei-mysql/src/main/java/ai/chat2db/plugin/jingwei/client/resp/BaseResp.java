package ai.chat2db.plugin.jingwei.client.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author duanfuqiang
 * @date 2025/9/24 17:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResp<T> {

    @JSONField(name = "data")
    @JsonProperty("data")
    private T data;

    @JSONField(name = "status")
    @JsonProperty("status")
    private Integer status;

    @JSONField(name = "message")
    @JsonProperty("message")
    private String message;

    @JSONField(name = "success")
    @JsonProperty("success")
    private Boolean success;
}
