package ai.chat2db.plugin.jingwei.driver.catalog;

import ai.chat2db.plugin.jingwei.driver.result.Columns;
import ai.chat2db.plugin.jingwei.driver.result.FastjsonRow;
import ai.chat2db.plugin.jingwei.driver.result.ResultSetImpl;
import ai.chat2db.plugin.jingwei.driver.result.Rows;
import com.alibaba.fastjson2.JSONObject;

import java.sql.*;
import java.util.List;

/**
 * @author duanfuqiang
 * @date 2025/9/28 20:22
 */
public class JingWeiMetaData implements DatabaseMetaData {
    
    private String idc;
    
    public JingWeiMetaData(String idc) {
        this.idc = idc;
    }
    @Override
    public boolean allProceduresAreCallable() throws SQLException {
        return false;
    }

    @Override
    public boolean allTablesAreSelectable() throws SQLException {
        return false;
    }

    @Override
    public String getURL() throws SQLException {
        return "";
    }

    @Override
    public String getUserName() throws SQLException {
        return "";
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public boolean nullsAreSortedHigh() throws SQLException {
        return false;
    }

    @Override
    public boolean nullsAreSortedLow() throws SQLException {
        return false;
    }

    @Override
    public boolean nullsAreSortedAtStart() throws SQLException {
        return false;
    }

    @Override
    public boolean nullsAreSortedAtEnd() throws SQLException {
        return false;
    }

    @Override
    public String getDatabaseProductName() throws SQLException {
        return "MYSQL";
    }

    @Override
    public String getDatabaseProductVersion() throws SQLException {
        return "UNKNOWN";
    }

    @Override
    public String getDriverName() throws SQLException {
        return "JING_WEI_PROXY";
    }

    @Override
    public String getDriverVersion() throws SQLException {
        return "0.1.0";
    }

    @Override
    public int getDriverMajorVersion() {
        return 0;
    }

    @Override
    public int getDriverMinorVersion() {
        return 0;
    }

    @Override
    public boolean usesLocalFiles() throws SQLException {
        return false;
    }

    @Override
    public boolean usesLocalFilePerTable() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return false;
    }

    @Override
    public String getIdentifierQuoteString() throws SQLException {
        return "";
    }

    @Override
    public String getSQLKeywords() throws SQLException {
        return "";
    }

    @Override
    public String getNumericFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getStringFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getSystemFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getTimeDateFunctions() throws SQLException {
        return "";
    }

    @Override
    public String getSearchStringEscape() throws SQLException {
        return "";
    }

    @Override
    public String getExtraNameCharacters() throws SQLException {
        return "";
    }

    @Override
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsColumnAliasing() throws SQLException {
        return false;
    }

    @Override
    public boolean nullPlusNonNullIsNull() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsConvert() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsConvert(int fromType, int toType) throws SQLException {
        return false;
    }

    @Override
    public boolean supportsTableCorrelationNames() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOrderByUnrelated() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsGroupBy() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsGroupByUnrelated() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsLikeEscapeClause() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMultipleResultSets() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMultipleTransactions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsNonNullableColumns() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCoreSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsANSI92FullSQL() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOuterJoins() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsFullOuterJoins() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsLimitedOuterJoins() throws SQLException {
        return false;
    }

    @Override
    public String getSchemaTerm() throws SQLException {
        return "";
    }

    @Override
    public String getProcedureTerm() throws SQLException {
        return "";
    }

    @Override
    public String getCatalogTerm() throws SQLException {
        return "";
    }

    @Override
    public boolean isCatalogAtStart() throws SQLException {
        return false;
    }

    @Override
    public String getCatalogSeparator() throws SQLException {
        return "";
    }

    @Override
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsPositionedDelete() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsPositionedUpdate() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSelectForUpdate() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsStoredProcedures() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInExists() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInIns() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsUnion() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsUnionAll() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return false;
    }

    @Override
    public int getMaxBinaryLiteralLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxCharLiteralLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInGroupBy() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInIndex() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInOrderBy() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInSelect() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxColumnsInTable() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxConnections() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxCursorNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxIndexLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxSchemaNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxProcedureNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxCatalogNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxRowSize() throws SQLException {
        return 0;
    }

    @Override
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return false;
    }

    @Override
    public int getMaxStatementLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxStatements() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxTableNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxTablesInSelect() throws SQLException {
        return 0;
    }

    @Override
    public int getMaxUserNameLength() throws SQLException {
        return 0;
    }

    @Override
    public int getDefaultTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public boolean supportsTransactions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
        return false;
    }

    @Override
    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        return false;
    }

    @Override
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return false;
    }

    @Override
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getSchemas() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getCatalogs() throws SQLException {
        // 确保目录信息已初始化
        CatalogsManager.initCatalogs();
        
        // 获取所有目录
        List<String> catalogs = CatalogsManager.getCataLogs(idc);
        
        // 创建列信息
        Columns columns = new Columns();
        columns.addColumn(1, "TABLE_CAT");
        
        // 创建行数据
        Rows rows = new Rows(columns);
        for (String catalog : catalogs) {
            // 为每个目录创建一行数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("TABLE_CAT", catalog);
            rows.addRow(new FastjsonRow(jsonObject, columns));
        }
        // 返回结果集
        return new ResultSetImpl(rows);
    }

    @Override
    public ResultSet getTableTypes() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getTypeInfo() throws SQLException {
        // 创建列信息
        Columns columns = new Columns();
        columns.addColumn(1, "TYPE_NAME");
        columns.addColumn(2, "DATA_TYPE");
        columns.addColumn(3, "PRECISION");
        columns.addColumn(4, "LITERAL_PREFIX");
        columns.addColumn(5, "LITERAL_SUFFIX");
        columns.addColumn(6, "CREATE_PARAMS");
        columns.addColumn(7, "NULLABLE");
        columns.addColumn(8, "CASE_SENSITIVE");
        columns.addColumn(9, "SEARCHABLE");
        columns.addColumn(10, "UNSIGNED_ATTRIBUTE");
        columns.addColumn(11, "FIXED_PREC_SCALE");
        columns.addColumn(12, "AUTO_INCREMENT");
        columns.addColumn(13, "LOCAL_TYPE_NAME");
        columns.addColumn(14, "MINIMUM_SCALE");
        columns.addColumn(15, "MAXIMUM_SCALE");
        columns.addColumn(16, "SQL_DATA_TYPE");
        columns.addColumn(17, "SQL_DATETIME_SUB");
        columns.addColumn(18, "NUM_PREC_RADIX");

        // 创建行数据
        Rows rows = new Rows(columns);

        // 添加 MySQL 类型信息
        addTypeInfo(rows, "VARCHAR", Types.VARCHAR, 65535, "'", "'", "length", DatabaseMetaData.typeNullable, true, DatabaseMetaData.typeSearchable, false, false, false, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "TINYINT", Types.TINYINT, 3, null, null, null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, true, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "TEXT", Types.LONGVARCHAR, 65535, "'", "'", null, DatabaseMetaData.typeNullable, true, DatabaseMetaData.typeSearchable, false, false, false, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "SMALLINT", Types.SMALLINT, 5, null, null, null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, true, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "MEDIUMINT", Types.INTEGER, 7, null, null, null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, true, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "INT", Types.INTEGER, 10, null, null, null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, true, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "INTEGER", Types.INTEGER, 10, null, null, null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, true, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "BIGINT", Types.BIGINT, 19, null, null, null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, true, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "FLOAT", Types.REAL, 10, null, null, null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, true, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "DOUBLE", Types.DOUBLE, 17, null, null, null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, true, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "DECIMAL", Types.DECIMAL, 65, null, null, "precision,scale", DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, true, null, 0, 30, 0, 0, 10);
        addTypeInfo(rows, "DATE", Types.DATE, 10, "'", "'", null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, false, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "DATETIME", Types.TIMESTAMP, 19, "'", "'", null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, false, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "TIMESTAMP", Types.TIMESTAMP, 19, "'", "'", null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, false, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "TIME", Types.TIME, 8, "'", "'", null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, false, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "CHAR", Types.CHAR, 255, "'", "'", "length", DatabaseMetaData.typeNullable, true, DatabaseMetaData.typeSearchable, false, false, false, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "BLOB", Types.LONGVARBINARY, 65535, "'", "'", null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, false, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "ENUM", Types.VARCHAR, 65535, "'", "'", null, DatabaseMetaData.typeNullable, true, DatabaseMetaData.typeSearchable, false, false, false, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "SET", Types.VARCHAR, 64, "'", "'", null, DatabaseMetaData.typeNullable, true, DatabaseMetaData.typeSearchable, false, false, false, null, 0, 0, 0, 0, 10);
        addTypeInfo(rows, "BIT", Types.BIT, 1, null, null, null, DatabaseMetaData.typeNullable, false, DatabaseMetaData.typeSearchable, false, false, false, null, 0, 0, 0, 0, 10);
        
        // 返回结果集
        return new ResultSetImpl(rows);
    }
    
    private void addTypeInfo(Rows rows, String typeName, int dataType, int precision, 
                            String literalPrefix, String literalSuffix, String createParams,
                            int nullable, boolean caseSensitive, int searchable,
                            boolean unsignedAttribute, boolean fixedPrecScale, boolean autoIncrement,
                            String localTypeName, int minimumScale, int maximumScale,
                            int sqlDataType, int sqlDatetimeSub, int numPrecRadix) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TYPE_NAME", typeName);
        jsonObject.put("DATA_TYPE", dataType);
        jsonObject.put("PRECISION", precision);
        jsonObject.put("LITERAL_PREFIX", literalPrefix);
        jsonObject.put("LITERAL_SUFFIX", literalSuffix);
        jsonObject.put("CREATE_PARAMS", createParams);
        jsonObject.put("NULLABLE", nullable);
        jsonObject.put("CASE_SENSITIVE", caseSensitive);
        jsonObject.put("SEARCHABLE", searchable);
        jsonObject.put("UNSIGNED_ATTRIBUTE", unsignedAttribute);
        jsonObject.put("FIXED_PREC_SCALE", fixedPrecScale);
        jsonObject.put("AUTO_INCREMENT", autoIncrement);
        jsonObject.put("LOCAL_TYPE_NAME", localTypeName);
        jsonObject.put("MINIMUM_SCALE", minimumScale);
        jsonObject.put("MAXIMUM_SCALE", maximumScale);
        jsonObject.put("SQL_DATA_TYPE", sqlDataType);
        jsonObject.put("SQL_DATETIME_SUB", sqlDatetimeSub);
        jsonObject.put("NUM_PREC_RADIX", numPrecRadix);
        
        rows.addRow(new FastjsonRow(jsonObject, rows.getColumns()));
    }

    @Override
    public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsResultSetType(int type) throws SQLException {
        return false;
    }

    @Override
    public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
        return false;
    }

    @Override
    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        return false;
    }

    @Override
    public boolean ownDeletesAreVisible(int type) throws SQLException {
        return false;
    }

    @Override
    public boolean ownInsertsAreVisible(int type) throws SQLException {
        return false;
    }

    @Override
    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        return false;
    }

    @Override
    public boolean othersDeletesAreVisible(int type) throws SQLException {
        return false;
    }

    @Override
    public boolean othersInsertsAreVisible(int type) throws SQLException {
        return false;
    }

    @Override
    public boolean updatesAreDetected(int type) throws SQLException {
        return false;
    }

    @Override
    public boolean deletesAreDetected(int type) throws SQLException {
        return false;
    }

    @Override
    public boolean insertsAreDetected(int type) throws SQLException {
        return false;
    }

    @Override
    public boolean supportsBatchUpdates() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Connection getConnection() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsSavepoints() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsNamedParameters() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsMultipleOpenResults() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsGetGeneratedKeys() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsResultSetHoldability(int holdability) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        return 0;
    }

    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        return 0;
    }

    @Override
    public int getJDBCMajorVersion() throws SQLException {
        return 0;
    }

    @Override
    public int getJDBCMinorVersion() throws SQLException {
        return 0;
    }

    @Override
    public int getSQLStateType() throws SQLException {
        return 0;
    }

    @Override
    public boolean locatorsUpdateCopy() throws SQLException {
        return false;
    }

    @Override
    public boolean supportsStatementPooling() throws SQLException {
        return false;
    }

    @Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return false;
    }

    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return false;
    }

    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        return false;
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
