package ai.chat2db.plugin.jingwei;

import ai.chat2db.spi.DBManage;
import ai.chat2db.spi.model.AsyncContext;
import ai.chat2db.spi.model.Function;
import ai.chat2db.spi.model.Procedure;
import ai.chat2db.spi.sql.ConnectInfo;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author duanfuqiang
 * @date 2025/9/30 15:18
 */
public class JingWeiDBManage implements DBManage {
    @Override
    public Connection getConnection(ConnectInfo connectInfo) {
        return null;
    }

    @Override
    public void connectDatabase(Connection connection, String database) {

    }

    @Override
    public void modifyDatabase(Connection connection, String databaseName, String newDatabaseName) {

    }

    @Override
    public void createDatabase(Connection connection, String databaseName) {

    }

    @Override
    public void dropDatabase(Connection connection, String databaseName) {

    }

    @Override
    public void createSchema(Connection connection, String databaseName, String schemaName) {

    }

    @Override
    public void dropSchema(Connection connection, String databaseName, String schemaName) {

    }

    @Override
    public void modifySchema(Connection connection, String databaseName, String schemaName, String newSchemaName) {

    }

    @Override
    public void dropTable(Connection connection, String databaseName, String schemaName, String tableName) {

    }

    @Override
    public void dropSequence(Connection connection, String databaseName, String schemaName, String sequenceName) {

    }

    @Override
    public void dropFunction(Connection connection, String databaseName, String schemaName, String functionName) {

    }

    @Override
    public void dropTrigger(Connection connection, String databaseName, String schemaName, String triggerName) {

    }

    @Override
    public void dropProcedure(Connection connection, String databaseName, String schemaName, String triggerName) {

    }

    @Override
    public void updateProcedure(Connection connection, String databaseName, String schemaName, Procedure procedure) throws SQLException {

    }

    @Override
    public void exportDatabase(Connection connection, String databaseName, String schemaName, AsyncContext asyncContext) throws SQLException {

    }

    @Override
    public void exportTable(Connection connection, String databaseName, String schemaName, String tableName, AsyncContext asyncContext) throws SQLException {

    }

    @Override
    public void truncateTable(Connection connection, String databaseName, String schemaName, String tableName) throws SQLException {

    }

    @Override
    public void copyTable(Connection connection, String databaseName, String schemaName, String tableName, String newTableName, boolean copyData) throws SQLException {

    }

    @Override
    public void deleteProcedure(Connection connection, String databaseName, String schemaName, Procedure procedure) {

    }

    @Override
    public void deleteFunction(Connection connection, String databaseName, String schemaName, Function function) {

    }
}
