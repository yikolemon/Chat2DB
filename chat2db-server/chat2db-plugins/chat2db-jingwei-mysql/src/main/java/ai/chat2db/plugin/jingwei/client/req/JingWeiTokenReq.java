package ai.chat2db.plugin.jingwei.client.req;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class JingWeiTokenReq {

    @JSONField(name = "login_name")
    private String loginName;

    @JSONField(name = "login_pwd")
    private String loginPwd;

    @JSONField(name = "dynamic_pwd")
    private String dynamicPwd;

    @JSONField(name = "is_oversea")
    private Boolean isOversea = false;
    
}
