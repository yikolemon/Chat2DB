package ai.chat2db.plugin.jingwei.driver.connection;

import ai.chat2db.plugin.jingwei.driver.JdbcRpcException;
import ai.chat2db.plugin.jingwei.driver.Type;
import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JingWeiConnectionInfo extends ConnectionInfo {

    public JingWeiConnectionInfo(String url, Properties properties) {
        super(url, properties);
        this.setType(Type.JINGWEI_CONNECTION);

        // 用户名
        String user = properties.getProperty("user");
        if (StringUtils.isEmpty(user)) {
            throw new JdbcRpcException("user名称缺失");
        }
        setUser(user);

        // 密码
        String password = properties.getProperty("password");
        if (StringUtils.isEmpty(password)) {
            throw new JdbcRpcException("password缺失");
        }
        setPassword(password);

        // 解析 URL
        ParsedUrl parsedUrl = parseUrl(url);

        Map<String, String> params = parsedUrl.params;
        String token = null;
        if (CollectionUtil.isNotEmpty(params)) {
            token = params.get("token");
        }

        if (StringUtils.isEmpty(parsedUrl.dynamicPwd) && StringUtils.isEmpty(token)) {
            throw new JdbcRpcException("动态密码及token缺失");
        }

        setDynamicPwd(parsedUrl.dynamicPwd);
        setIdc(parsedUrl.idc);
        setUrl(parsedUrl.baseUrl); // 不包含前缀 "jdbc:"

        // 合并参数：先 properties，再 URL params（覆盖）
        mergeProperties(properties, parsedUrl.params);
    }

    /**
     * 合并 properties 与 URL 参数到 info
     */
    private void mergeProperties(Properties properties, Map<String, String> urlParams) {
        // 先放入 properties
        if (properties != null) {
            for (String name : properties.stringPropertyNames()) {
                this.getProperties().put(name, properties.getProperty(name));
            }
        }
        // URL 参数覆盖
        if (urlParams != null && !urlParams.isEmpty()) {
            this.getProperties().putAll(urlParams);
        }
    }

    /**
     * 解析 JDBC URL
     * 格式: jdbc:jingwei:<idc>?dynamicPwd=xxxx&key=value
     */
    private ParsedUrl parseUrl(String url) {
        if (StringUtils.isEmpty(url) || !url.startsWith("jdbc:jingwei:")) {
            throw new JdbcRpcException("URL格式错误，应为 jdbc:jingwei:<idc>?...");
        }

        // 去掉前缀 jdbc:
        String body = url.substring("jdbc:".length()); // jingwei:mex?dynamicPwd=xxx

        String[] parts = body.split(":", 2);
        if (parts.length != 2 || !"jingwei".equals(parts[0])) {
            throw new JdbcRpcException("URL格式错误，应为 jdbc:jingwei:<idc>?...");
        }

        String idcPart = parts[1]; // mex?dynamicPwd=xxx
        String idc = extractIdc(idcPart);
        Map<String, String> params = extractParams(idcPart);

        ParsedUrl parsed = new ParsedUrl();
        parsed.idc = idc;
        parsed.dynamicPwd = params.get("dynamicPwd");
        parsed.params = params;
        parsed.baseUrl = body; // jingwei:mex?dynamicPwd=xxx
        return parsed;
    }

    /** 提取 idc，去掉 ? 之后的部分 */
    private String extractIdc(String raw) {
        int idx = raw.indexOf('?');
        return (idx >= 0) ? raw.substring(0, idx) : raw;
    }

    /** 解析 query 参数 */
    private Map<String, String> extractParams(String raw) {
        Map<String, String> map = new HashMap<>();
        int idx = raw.indexOf('?');
        if (idx < 0) return map;

        String query = raw.substring(idx + 1);
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                map.put(kv[0], kv[1]);
            }
        }
        return map;
    }

    /** 内部类存放解析结果 */
    private static class ParsedUrl {
        String idc;
        String dynamicPwd;
        String baseUrl;
        Map<String, String> params;
    }
}
