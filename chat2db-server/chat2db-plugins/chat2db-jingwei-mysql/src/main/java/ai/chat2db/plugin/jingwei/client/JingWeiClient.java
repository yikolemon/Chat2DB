package ai.chat2db.plugin.jingwei.client;

import ai.chat2db.plugin.jingwei.client.req.JingWeiTokenReq;
import ai.chat2db.plugin.jingwei.client.req.SqlExecuteRequest;
import ai.chat2db.plugin.jingwei.client.resp.*;
import com.dtflys.forest.annotation.*;
import com.dtflys.forest.callback.OnLoadCookie;
import com.dtflys.forest.http.ForestResponse;

import java.util.List;

/**
 * @author duanfuqiang
 * @date 2025/9/24 16:34
 */
@Address(host = "jingwei.ppdaicorp.com", port = "80")
public interface JingWeiClient {

    @Post("/api/user/login")
    @LogEnabled(value = true)
    @Redirection(value = false)
    ForestResponse<String> getToken(@JSONBody JingWeiTokenReq req);

    @Get("/api/audit/ddl/rds/datasource")
    @LogEnabled(value = true)
    @Redirection(value = false)
    ForestResponse<BaseResp<List<Cluster>>> getCataLogs(@Query("query_type") String queryType, @Query("env") String env, @Header("Cookie") String tokenCookie);

    /**
     * 查询数据库下的表列表
     * @param clusterId    集群ID
     * @param databaseName 数据库名称
     * @param tokenCookie  认证Cookie
     * @return 表名列表响应
     */
    @Get("/api/audit/ddl/rds/table_name_list")
    @Redirection(value = false)
    ForestResponse<TableListResponse> getTableList(@Query("cluster_id") String clusterId, @Query("database_name") String databaseName, @Header("Cookie") String tokenCookie);

    /**
     * 获取表的字段和索引信息
     * @param clusterId    集群ID
     * @param databaseName 数据库名称
     * @param tableName    表名
     * @param tokenCookie  认证Cookie
     * @return 表字段和索引信息响应
     */
    @Get("/api/audit/ddl/rds/table/_columns_and_index")
    @Redirection(value = false)
    ForestResponse<TableInfoResponse> getTableColumnsAndIndex(
            @Query("cluster_id") String clusterId,
            @Query("rds_db") String databaseName,
            @Query("rds_table") String tableName,
            @Header("Cookie") String tokenCookie);
    
    /**
     * 执行SQL语句
     * @param request SQL执行请求
     * @param tokenCookie 认证Cookie
     * @return SQL执行响应
     */
    @Post("/api/rds/rds-core-pro-user/${databaseName}/async/_exe")
    @Redirection(value = false)
    ForestResponse<SqlExecuteResponse> executeSql(@Var("databaseName") String databaseName, @JSONBody SqlExecuteRequest request, @Header("Cookie") String tokenCookie);
    
    /**
     * 获取SQL查询结果
     * @param sqlExeRecordId SQL执行记录ID
     * @param tokenCookie 认证Cookie
     * @return SQL查询结果响应
     */
    @Get("/api/rds/sql/exe/record/${sqlExeRecordId}/_result")
    @Redirection(value = false)
    ForestResponse<SqlQueryResultResponse> getSqlQueryResult(@Var("sqlExeRecordId") Long sqlExeRecordId, @Header("Cookie") String tokenCookie);
}
