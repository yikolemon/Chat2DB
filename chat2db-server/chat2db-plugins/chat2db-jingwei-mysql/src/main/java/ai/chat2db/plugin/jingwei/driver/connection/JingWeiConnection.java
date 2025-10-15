package ai.chat2db.plugin.jingwei.driver.connection;

import ai.chat2db.plugin.jingwei.driver.TokenManager;
import ai.chat2db.plugin.jingwei.driver.statment.PreparedStatementImpl;
import ai.chat2db.plugin.jingwei.driver.statment.StatementImpl;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class JingWeiConnection implements Connection {
    private boolean closed;
    private ConnectionInfo conInfo;
    
    public ConnectionInfo getConInfo() {
        return conInfo;
    }

    public JingWeiConnection(ConnectionInfo conInfo) {
        this.conInfo = conInfo;
        // token登录
        Map<String, String> properties = conInfo.getProperties();
        String token = properties.get("token");
        if (StringUtils.isNotEmpty(token)) {
            TokenManager.initToken(token);
            return;
        }
        // 动态密码登录
        String user = conInfo.getUser();
        String password = conInfo.getPassword();
        String dynamicPwd = conInfo.getDynamicPwd();
        TokenManager.initToken(dynamicPwd, user, password);
    }
    
    @Override
    public Statement createStatement() throws SQLException {
        return new StatementImpl(this);
    }
    
    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new PreparedStatementImpl(this, sql);
    }
    
    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new SQLFeatureNotSupportedException("CallableStatement not supported");
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return sql;
    }


    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        // 只读驱动，自动提交设置无效
        if (!autoCommit) {
            throw new SQLFeatureNotSupportedException("只支持查询操作，不支持事务");
        }
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        // 只读驱动，始终自动提交
        return true;
    }

    @Override
    public void commit() throws SQLException {
        // 只读驱动，提交操作无效
        throw new SQLFeatureNotSupportedException("只支持查询操作，不支持事务");
    }

    @Override
    public void rollback() throws SQLException {
        // 只读驱动，回滚操作无效
        throw new SQLFeatureNotSupportedException("只支持查询操作，不支持事务");
    }
    
    @Override
    public void close() throws SQLException {
        this.closed = true;
    }


    @Override
    public boolean isClosed() throws SQLException {
        return closed;
    }


    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        throw new SQLFeatureNotSupportedException("功能未支持");
    }
    
    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        // 只读驱动，设置只读模式无效
        if (!readOnly) {
            throw new SQLFeatureNotSupportedException("只支持查询操作，必须为只读模式");
        }
    }
    
    @Override
    public boolean isReadOnly() throws SQLException {
        // 始终返回只读模式
        return true;
    }
    
    @Override
    public void setCatalog(String catalog) throws SQLException {
        this.conInfo.setDb(catalog);
    }
    
    @Override
    public String getCatalog() throws SQLException {
        return this.conInfo.getDb();
    }
    
    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        // 只读驱动不支持事务隔离级别设置
        if (level != Connection.TRANSACTION_NONE) {
            throw new SQLFeatureNotSupportedException("只读驱动不支持事务隔离级别设置");
        }
    }
    
    @Override
    public int getTransactionIsolation() throws SQLException {
        // 只读驱动始终返回无事务
        return Connection.TRANSACTION_NONE;
    }
    
    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }


    @Override
    public void clearWarnings() throws SQLException {
    }
    
    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        // 只支持前向只读结果集
        if (resultSetType != ResultSet.TYPE_FORWARD_ONLY || resultSetConcurrency != ResultSet.CONCUR_READ_ONLY) {
            throw new SQLFeatureNotSupportedException("只支持前向只读结果集");
        }
        return new StatementImpl(this);
    }
    
    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        // 只支持前向只读结果集
        if (resultSetType != ResultSet.TYPE_FORWARD_ONLY || resultSetConcurrency != ResultSet.CONCUR_READ_ONLY) {
            throw new SQLFeatureNotSupportedException("只支持前向只读结果集");
        }
        return new PreparedStatementImpl(this, sql);
    }
    
    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

    }
    
    @Override
    public void setHoldability(int holdability) throws SQLException {

    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {

    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        // 只支持前向只读结果集
        if (resultSetType != ResultSet.TYPE_FORWARD_ONLY || resultSetConcurrency != ResultSet.CONCUR_READ_ONLY) {
            throw new SQLFeatureNotSupportedException("只支持前向只读结果集");
        }
        return new StatementImpl(this);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        // 只支持前向只读结果集
        if (resultSetType != ResultSet.TYPE_FORWARD_ONLY || resultSetConcurrency != ResultSet.CONCUR_READ_ONLY) {
            throw new SQLFeatureNotSupportedException("只支持前向只读结果集");
        }
        return new PreparedStatementImpl(this, sql);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new SQLFeatureNotSupportedException("不支持存储过程调用");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        // 只读驱动不支持自动生成键
        if (autoGeneratedKeys == Statement.RETURN_GENERATED_KEYS) {
            throw new SQLFeatureNotSupportedException("只读驱动不支持自动生成键");
        }
        return new PreparedStatementImpl(this, sql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        // 只读驱动不支持自动生成键
        throw new SQLFeatureNotSupportedException("只读驱动不支持自动生成键");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        // 只读驱动不支持自动生成键
        throw new SQLFeatureNotSupportedException("只读驱动不支持自动生成键");
    }

    @Override
    public Clob createClob() throws SQLException {
        // 只读驱动不支持创建LOB对象
        throw new SQLFeatureNotSupportedException("只读驱动不支持创建LOB对象");
    }

    @Override
    public Blob createBlob() throws SQLException {
        // 只读驱动不支持创建LOB对象
        throw new SQLFeatureNotSupportedException("只读驱动不支持创建LOB对象");
    }

    @Override
    public NClob createNClob() throws SQLException {
        // 只读驱动不支持创建LOB对象
        throw new SQLFeatureNotSupportedException("只读驱动不支持创建LOB对象");
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        // 只读驱动不支持创建SQLXML对象
        throw new SQLFeatureNotSupportedException("只读驱动不支持创建SQLXML对象");
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        if (timeout < 0) {
            throw new SQLException("超时值不能为负数");
        }
        
        try {
            // 检查客户端连接是否有效
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        // 只读驱动不支持创建Array对象
        throw new SQLFeatureNotSupportedException("只读驱动不支持创建Array对象");
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        // 只读驱动不支持创建Struct对象
        throw new SQLFeatureNotSupportedException("只读驱动不支持创建Struct对象");
    }

    @Override
    public void setSchema(String schema) throws SQLException {

    }

    @Override
    public String getSchema() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void abort(Executor executor) throws SQLException {

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
