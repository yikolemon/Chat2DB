package ai.chat2db.plugin.jingwei.driver;


import ai.chat2db.plugin.jingwei.client.JingWeiClient;
import ai.chat2db.plugin.jingwei.client.req.JingWeiTokenReq;
import ai.chat2db.plugin.jingwei.client.resp.BaseResp;
import ai.chat2db.plugin.jingwei.util.JSONUtil;
import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.utils.StringUtils;

import java.util.concurrent.atomic.AtomicReference;

public class TokenManager {

    private TokenManager(){}

    /** 缓存当前 token */
    private static final AtomicReference<String> TOKEN = new AtomicReference<>();

    /** 锁对象，保证多线程只初始化一次 */
    private static final Object LOCK = new Object();

    public static String initToken(String token) {
        if (TOKEN.get() == null) {
            synchronized (LOCK) {
                if (TOKEN.get() == null) { // 双重检查
                    TOKEN.set(token);
                }
            }
        }
        return "JSESSIONID=" + TOKEN.get();
    }

    /**
     * 初始化 token（首次调用或 token 为空时获取新 token）
     */
    public static String initToken(String dynamicPwd, String loginName, String loginPwd) {
        if (TOKEN.get() == null) {
            synchronized (LOCK) {
                if (TOKEN.get() == null) { // 双重检查
                    String token = fetchNewToken(dynamicPwd, loginName, loginPwd);
                    TOKEN.set(token);
                }
            }
        }
        return "JSESSIONID=" + TOKEN.get();
    }

    /**
     * 获取当前 token（如果没有初始化，直接返回 null）
     */
    public static String getToken() {
        String token = TOKEN.get();
        if (token == null) {
            throw new JdbcRpcException("Token 未初始化，请先调用 initToken");
        }
        return "JSESSIONID=" + token;
    }

    /**
     * 强制刷新 token
     */
    public static String refreshToken(String dynamicPwd, String loginName, String loginPwd) {
        synchronized (LOCK) {
            String token = fetchNewToken(dynamicPwd, loginName, loginPwd);
            TOKEN.set(token);
            return "JSESSIONID=" + token;
        }
    }

    /**
     * 清理 token（token 失效时调用）
     */
    public static void clearToken() {
        TOKEN.set(null);
    }

    /**
     * 向认证服务获取新 token
     */
    private static String fetchNewToken(String dynamicPwd, String loginName, String loginPwd) {
        JingWeiClient client = Forest.client(JingWeiClient.class);
        JingWeiTokenReq req = new JingWeiTokenReq();
        req.setDynamicPwd(dynamicPwd);
        req.setLoginName(loginName);
        req.setLoginPwd(loginPwd);
        ForestResponse<String> tokenResp = client.getToken(req);

        if (!tokenResp.isSuccess() || StringUtils.isEmpty(tokenResp.getResult())) {
            throw new JdbcRpcException("连接精卫失败，请检查用户名，密码，动态令牌");
        }

        try {
            BaseResp baseResp = JSONUtil.toObject(tokenResp.getResult(), BaseResp.class);
            if (!Boolean.TRUE.equals(baseResp.getSuccess())) {
                String message = baseResp.getMessage();
                throw new JdbcRpcException(message);
            }
        } catch (Exception e) {
            throw new JdbcRpcException(e.getMessage(), e);
        }

        return tokenResp.getCookie("JSESSIONID").getValue();
    }
}
