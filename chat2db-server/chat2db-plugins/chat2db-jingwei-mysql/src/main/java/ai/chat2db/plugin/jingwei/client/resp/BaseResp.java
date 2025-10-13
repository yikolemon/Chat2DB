package ai.chat2db.plugin.jingwei.client.resp;

import com.alibaba.fastjson2.annotation.JSONField;
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
    private T data;

    @JSONField(name = "status")
    private Integer status;

    @JSONField(name = "message")
    private String message;

    @JSONField(name = "success")
    private Boolean success;
}
