package ai.chat2db.plugin.jingwei;

import ai.chat2db.server.tools.base.wrapper.result.PageResult;
import ai.chat2db.spi.CommandExecutor;
import ai.chat2db.spi.MetaData;
import ai.chat2db.spi.SqlBuilder;
import ai.chat2db.spi.ValueProcessor;
import ai.chat2db.spi.model.*;

import java.sql.Connection;
import java.util.List;

/**
 * @author duanfuqiang
 * @date 2025/9/30 15:18
 */
public class JingWeiMetaData implements MetaData {
    @Override
    public List<Database> databases(Connection connection) {
        return List.of();
    }

    @Override
    public List<Schema> schemas(Connection connection, String databaseName) {
        return List.of();
    }

    @Override
    public String tableDDL(Connection connection, String databaseName, String schemaName, String tableName) {
        return "";
    }

    @Override
    public List<Table> tables(Connection connection, String databaseName, String schemaName, String tableName) {
        return List.of();
    }

    @Override
    public List<String> tableNames(Connection connection, String databaseName, String schemaName, String tableName) {
        return List.of();
    }

    @Override
    public PageResult<Table> tables(Connection connection, String databaseName, String schemaName, String tableNamePattern, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public Table view(Connection connection, String databaseName, String schemaName, String viewName) {
        return null;
    }

    @Override
    public List<String> viewNames(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public List<Table> views(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public List<Function> functions(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public List<Trigger> triggers(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public List<Procedure> procedures(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public List<TableColumn> columns(Connection connection, String databaseName, String schemaName, String tableName) {
        return List.of();
    }

    @Override
    public List<TableColumn> columns(Connection connection, String databaseName, String schemaName, String tableName, String columnName) {
        return List.of();
    }

    @Override
    public List<TableIndex> indexes(Connection connection, String databaseName, String schemaName, String tableName) {
        return List.of();
    }

    @Override
    public Function function(Connection connection, String databaseName, String schemaName, String functionName) {
        return null;
    }

    @Override
    public Trigger trigger(Connection connection, String databaseName, String schemaName, String triggerName) {
        return null;
    }

    @Override
    public Procedure procedure(Connection connection, String databaseName, String schemaName, String procedureName) {
        return null;
    }

    @Override
    public List<Type> types(Connection connection) {
        return List.of();
    }

    @Override
    public SqlBuilder getSqlBuilder() {
        return null;
    }

    @Override
    public TableMeta getTableMeta(String databaseName, String schemaName, String tableName) {
        return null;
    }

    @Override
    public String getMetaDataName(String... names) {
        return "";
    }

    @Override
    public ValueProcessor getValueProcessor() {
        return null;
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return null;
    }

    @Override
    public List<String> getSystemDatabases() {
        return List.of();
    }

    @Override
    public List<String> getSystemSchemas() {
        return List.of();
    }

    @Override
    public String sequenceDDL(Connection connection, String databaseName, String schemaName, String tableName) {
        return "";
    }

    @Override
    public List<SimpleSequence> sequences(Connection connection, String databaseName, String schemaName) {
        return List.of();
    }

    @Override
    public Sequence sequences(Connection connection, String databaseName, String schemaName, String sequenceName) {
        return null;
    }

    @Override
    public List<String> usernames(Connection connection) {
        return List.of();
    }
}
