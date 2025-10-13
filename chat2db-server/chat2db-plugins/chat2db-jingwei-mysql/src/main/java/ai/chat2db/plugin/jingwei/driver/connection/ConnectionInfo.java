package ai.chat2db.plugin.jingwei.driver.connection;


import ai.chat2db.plugin.jingwei.driver.Type;
import ai.chat2db.plugin.jingwei.driver.catalog.CatalogsManager;
import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 维护连接信息
 */
@Data
public abstract class ConnectionInfo {
    private Type type;
    private String originalConn;
    private Map<String, String> properties;
    private String url;
    private String db;
    private String idc;
    private String user;
    private String password;
    private String dynamicPwd;


    public ConnectionInfo(String url, Properties properties) {
        this.originalConn = url;
        this.properties = new ConcurrentHashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            this.properties.put(key, properties.getProperty(key));
        }
    }

    public static ConnectionInfo getConnectionUrlInstance(String url, Properties info) throws SQLException {
        Type figuredType = Type.figure(url);
        if (figuredType == null) {
            throw new SQLException("url格式不支持");
        }
        if (figuredType == Type.JINGWEI_CONNECTION) {
            return new JingWeiConnectionInfo(url, info);
        }
        throw new SQLFeatureNotSupportedException();
    }

    public static boolean acceptsUrl(String url) {
        return Type.accept(url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionInfo that = (ConnectionInfo) o;
        return Objects.equals(originalConn, that.originalConn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalConn);
    }

    public void initDb() {
        String idc = this.getIdc();
        List<String> cataLogs = CatalogsManager.getCataLogs(idc);
        if (CollectionUtil.isNotEmpty(cataLogs)) {
            this.db = cataLogs.get(0);
        }
    }
}
