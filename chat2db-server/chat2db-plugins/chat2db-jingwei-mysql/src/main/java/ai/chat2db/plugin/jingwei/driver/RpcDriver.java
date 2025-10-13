package ai.chat2db.plugin.jingwei.driver;

import ai.chat2db.plugin.jingwei.driver.catalog.CatalogsManager;
import ai.chat2db.plugin.jingwei.driver.connection.ConnectionInfo;
import ai.chat2db.plugin.jingwei.driver.connection.JingWeiConnection;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * driver基类。 模板方法模式，实现了需要的
 */
public abstract class RpcDriver implements Driver {

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        /**
         * 0. 校验是否支持该url
         */
        if (!ConnectionInfo.acceptsUrl(url)) {
            throw new SQLException("url格式不支持");
        }
        //获取连接
        ConnectionInfo conInfo = ConnectionInfo.getConnectionUrlInstance(url, info);
        JingWeiConnection jingWeiConnection = new JingWeiConnection(conInfo);
        // 初始化cataLogs
        CatalogsManager.initCatalogs();
        conInfo.initDb();
        return jingWeiConnection;
    }


    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return Constants.MAJOR_VERSION;
    }

    @Override
    public int getMinorVersion() {
        return Constants.MINOR_VERSION;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
}
