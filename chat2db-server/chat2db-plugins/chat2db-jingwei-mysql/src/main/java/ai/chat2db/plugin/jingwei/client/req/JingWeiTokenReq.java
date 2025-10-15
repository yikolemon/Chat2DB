package ai.chat2db.plugin.jingwei.client.req;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JingWeiTokenReq {

    @JSONField(name = "login_name")
    @JsonProperty("login_name")
    private String loginName;

    @JSONField(name = "login_pwd")
    @JsonProperty("login_pwd")
    private String loginPwd;

    @JSONField(name = "dynamic_pwd")
    @JsonProperty("dynamic_pwd")
    private String dynamicPwd;

    @JSONField(name = "is_oversea")
    @JsonProperty("is_oversea")
    private Boolean isOversea = false;
    
}
