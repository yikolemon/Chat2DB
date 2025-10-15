package ai.chat2db.plugin.jingwei;

import ai.chat2db.plugin.jingwei.driver.JingWeiMysqlDriver;
import ai.chat2db.plugin.jingwei.driver.connection.ConnectionInfo;
import ai.chat2db.plugin.jingwei.driver.connection.JingWeiConnection;
import ai.chat2db.plugin.jingwei.driver.connection.JingWeiConnectionInfo;
import ai.chat2db.spi.DBManage;
import ai.chat2db.spi.model.AsyncContext;
import ai.chat2db.spi.model.Function;
import ai.chat2db.spi.model.Procedure;
import ai.chat2db.spi.sql.ConnectInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;

/**
 * @author duanfuqiang
 * @date 2025/9/30 15:18
 */
@Slf4j
public class JingWeiDBManage implements DBManage {
    @Override
    public Connection getConnection(ConnectInfo connectInfo) {
        try{
            Properties properties = new Properties();
            properties.put("user", connectInfo.getUser());
            properties.put("password", connectInfo.getPassword());
            return new JingWeiMysqlDriver().connect(connectInfo.getUrl(), properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connectDatabase(Connection connection, String database) {
        
    }

    @Override
    public void modifyDatabase(Connection connection, String databaseName, String newDatabaseName) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void createDatabase(Connection connection, String databaseName) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void dropDatabase(Connection connection, String databaseName) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void createSchema(Connection connection, String databaseName, String schemaName) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void dropSchema(Connection connection, String databaseName, String schemaName) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void modifySchema(Connection connection, String databaseName, String schemaName, String newSchemaName) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void dropTable(Connection connection, String databaseName, String schemaName, String tableName) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void dropSequence(Connection connection, String databaseName, String schemaName, String sequenceName) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void dropFunction(Connection connection, String databaseName, String schemaName, String functionName) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void dropTrigger(Connection connection, String databaseName, String schemaName, String triggerName) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void dropProcedure(Connection connection, String databaseName, String schemaName, String triggerName) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void updateProcedure(Connection connection, String databaseName, String schemaName, Procedure procedure) throws SQLException {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void exportDatabase(Connection connection, String databaseName, String schemaName, AsyncContext asyncContext) throws SQLException {
        throw new RuntimeException("导出动作未支持");
    }

    @Override
    public void exportTable(Connection connection, String databaseName, String schemaName, String tableName, AsyncContext asyncContext) throws SQLException {
        throw new RuntimeException("导出动作未支持");
    }

    @Override
    public void truncateTable(Connection connection, String databaseName, String schemaName, String tableName) throws SQLException {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void copyTable(Connection connection, String databaseName, String schemaName, String tableName, String newTableName, boolean copyData) throws SQLException {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void deleteProcedure(Connection connection, String databaseName, String schemaName, Procedure procedure) {
        throw new RuntimeException("精卫只支持查询动作");
    }

    @Override
    public void deleteFunction(Connection connection, String databaseName, String schemaName, Function function) {
        throw new RuntimeException("精卫只支持查询动作");
    }
}
