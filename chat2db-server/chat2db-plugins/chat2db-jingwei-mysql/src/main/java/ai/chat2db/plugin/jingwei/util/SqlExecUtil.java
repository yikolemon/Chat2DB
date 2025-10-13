package ai.chat2db.plugin.jingwei.util;

import ai.chat2db.plugin.jingwei.client.JingWeiClient;
import ai.chat2db.plugin.jingwei.client.req.SqlExecuteRequest;
import ai.chat2db.plugin.jingwei.client.resp.SqlExeRecord;
import ai.chat2db.plugin.jingwei.client.resp.SqlExecDetailResp;
import ai.chat2db.plugin.jingwei.client.resp.SqlExecuteResponse;
import ai.chat2db.plugin.jingwei.client.resp.SqlQueryResultResponse;
import ai.chat2db.plugin.jingwei.driver.Constants;
import ai.chat2db.plugin.jingwei.driver.catalog.CatalogInfo;
import ai.chat2db.plugin.jingwei.driver.catalog.CatalogsManager;
import ai.chat2db.plugin.jingwei.driver.connection.ConnectionInfo;
import ai.chat2db.plugin.jingwei.driver.TokenManager;
import ai.chat2db.plugin.jingwei.driver.result.Columns;
import ai.chat2db.plugin.jingwei.driver.result.FastjsonRow;
import ai.chat2db.plugin.jingwei.driver.result.ResultSetImpl;
import ai.chat2db.plugin.jingwei.driver.result.Rows;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestResponse;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * @author duanfuqiang
 * @date 2025/9/29 20:42
 */
public class SqlExecUtil {

    public static ResultSetImpl sqlExec(ConnectionInfo conInfo, String sql) throws SQLException {
        JingWeiClient client = Forest.client(JingWeiClient.class);
        String token = TokenManager.getToken();
        String db = conInfo.getDb();

        CatalogInfo catalogInfo = CatalogsManager.getCatalogInfo(db);
        SqlExecuteRequest sqlExecuteRequest = new SqlExecuteRequest();
        sqlExecuteRequest.setSql(sql);
        sqlExecuteRequest.setEnv(Integer.valueOf(Constants.JING_WEI_CATA_LOG_QUERY_ENV));
        sqlExecuteRequest.setBusinessId(null);
        sqlExecuteRequest.setBusinessName(catalogInfo.getBusinessName());
        sqlExecuteRequest.setDatasourceType(catalogInfo.getDataSourceType());
        sqlExecuteRequest.setDatasourcePort(catalogInfo.getDataSourcePort());
        sqlExecuteRequest.setDatabaseId(catalogInfo.getDataBaseId());

        // 1️⃣ 发送执行请求
        ForestResponse<SqlExecuteResponse> sqlExecResp = client.executeSql(sqlExecuteRequest, token);
        if (sqlExecResp == null || !sqlExecResp.isSuccess()) {
            throw new SQLException("执行sql失败: " + sql + ", connectInfo: " + JSONUtil.toJson(conInfo));
        }
        SqlExecuteResponse result = sqlExecResp.getResult();
        if (result == null || result.getData() == null) {
            throw new SQLException("执行sql失败: " + sql + ", connectInfo: " + JSONUtil.toJson(conInfo));
        }
        Long execId = result.getData();

        // 2️⃣ 轮询查询执行结果
        SqlQueryResultResponse queryResult = null;
        int maxRetry = 10;
        int retry = 0;
        while (retry < maxRetry) {
            ForestResponse<SqlQueryResultResponse> sqlQueryResult = client.getSqlQueryResult(execId, token);
            if (sqlQueryResult != null && sqlQueryResult.isSuccess()) {
                queryResult = sqlQueryResult.getResult();
                if (queryResult != null && queryResult.getData() != null) {
                    break; // 拿到数据了，退出
                }
            }
            retry++;
            try {
                Thread.sleep(1000L); // 等 1 秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new SQLException("线程被中断，执行sql失败: " + sql, e);
            }
        }

        if (queryResult == null || queryResult.getData() == null) {
            throw new SQLException("获取sql结果超时: " + sql + ", connectInfo: " + JSONUtil.toJson(conInfo));
        }
        List<SqlExeRecord> sqlExeRecordList = queryResult.getData();
        if (CollectionUtil.isEmpty(sqlExeRecordList)) {
            throw new SQLException("执行sql失败，执行无返回结果");
        }
        SqlExeRecord sqlExeRecord = sqlExeRecordList.get(0);
        String execResult = sqlExeRecord.getResult();
        if (StringUtils.isEmpty(execResult)) {
            throw new SQLException("执行sql失败，执行返回结果为空");
        }
        SqlExecDetailResp execDetailResp = JSONUtil.toObject(execResult, SqlExecDetailResp.class);
        // 3️⃣ 转换成 ResultSetImpl
        Rows rows = toRows(execDetailResp); // 你需要自己实现一个转换方法
        return new ResultSetImpl(rows);
    }

    private static Rows toRows(SqlExecDetailResp execDetailResp) {
        // 构建 Columns
        Columns columns = new Columns();
        List<String> fieldList = execDetailResp.getFields();
        if (CollUtil.isEmpty(fieldList)) {
            return new Rows(columns);
        }
        for (int i = 0; i < fieldList.size(); i++) {
            columns.addColumn(i + 1, fieldList.get(i));
        }
        Rows rows = new Rows(columns);
        // 遍历 data 填充行
        List<List<String>> data = execDetailResp.getData();
        if (data != null) {
            for (List<String> rowData : data) {
                JSONObject rowObj = new JSONObject();
                for (int i = 0; i < fieldList.size() && i < rowData.size(); i++) {
                    rowObj.put(fieldList.get(i), rowData.get(i));
                }
                rows.addRow(new FastjsonRow(rowObj, columns));
            }
        }

        return rows;
    }


    private static Rows toRows(String jsonData) {

        Object parsed = JSONUtil.toJsonNode(jsonData);
        JSONArray array;
        if (parsed instanceof JSONArray) {
            array = (JSONArray) parsed;
        } else if (parsed instanceof JSONObject) {
            array = new JSONArray();
            array.add(parsed);
        } else {
            // 如果解析失败或不是对象/数组
            array = new JSONArray();
        }
        Columns columns = new Columns();
        if (!array.isEmpty()) {
            JSONObject first = array.getJSONObject(0);
            Set<String> keys = first.keySet();
            int i = 1;
            for (String key : keys) {
                columns.addColumn(i++, key);
            }
        }

        Rows rows = new Rows(columns);
        for (int i = 0; i < array.size(); i++) {
            JSONObject node = array.getJSONObject(i);
            rows.addRow(new FastjsonRow(node, columns));
        }
        return rows;
    }

}
