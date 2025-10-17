package ai.chat2db.plugin.jingwei.util;

import ai.chat2db.plugin.jingwei.client.JingWeiClient;
import ai.chat2db.plugin.jingwei.client.req.SqlExecuteRequest;
import ai.chat2db.plugin.jingwei.client.resp.SqlExeRecord;
import ai.chat2db.plugin.jingwei.client.resp.SqlExecDetailResp;
import ai.chat2db.plugin.jingwei.client.resp.SqlExecuteResponse;
import ai.chat2db.plugin.jingwei.client.resp.SqlQueryResultResponse;
import ai.chat2db.plugin.jingwei.driver.Constants;
import ai.chat2db.plugin.jingwei.driver.catalog.CatalogInfo;
import ai.chat2db.plugin.jingwei.driver.TokenManager;
import ai.chat2db.spi.enums.DataTypeEnum;
import ai.chat2db.spi.enums.SqlTypeEnum;
import ai.chat2db.spi.model.ExecuteResult;
import ai.chat2db.spi.model.Header;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestResponse;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author duanfuqiang
 * @date 2025/9/29 20:42
 */
public class SqlExecUtil {

    public static ExecuteResult sqlExec(CatalogInfo catalogInfo, String sql) throws SQLException {
        JingWeiClient client = Forest.client(JingWeiClient.class);
        String token = TokenManager.getToken();

        SqlExecuteRequest sqlExecuteRequest = new SqlExecuteRequest();
        sqlExecuteRequest.setSql(sql);
        sqlExecuteRequest.setEnv(Integer.valueOf(Constants.JING_WEI_CATA_LOG_QUERY_ENV));
        sqlExecuteRequest.setBusinessId(null);
        sqlExecuteRequest.setBusinessName(catalogInfo.getBusinessName());
        sqlExecuteRequest.setDatasourceType(catalogInfo.getDataSourceType());
        sqlExecuteRequest.setDatasourcePort(catalogInfo.getDataSourcePort());
        sqlExecuteRequest.setDatabaseId(catalogInfo.getDataBaseId());

        // 1️⃣ 发送执行请求
        ForestResponse<SqlExecuteResponse> sqlExecResp = client.executeSql(catalogInfo.getClusterId(), catalogInfo.getDatabaseName(), sqlExecuteRequest, token);
        if (sqlExecResp == null || !sqlExecResp.isSuccess()) {
            throw new SQLException("执行sql失败: " + sql + ", connectInfo: " + JSON.toJSONString(catalogInfo));
        }
        SqlExecuteResponse result = sqlExecResp.getResult();
        if (result == null || result.getData() == null) {
            throw new SQLException("执行sql失败: " + sql + ", connectInfo: " + JSON.toJSONString(catalogInfo));
        }
        Long execId = result.getData();

        // 2️⃣ 轮询查询执行结果
        SqlQueryResultResponse queryResult = null;
        int maxRetry = 15;
        int retry = 0;
        while (retry < maxRetry) {
            ForestResponse<SqlQueryResultResponse> sqlQueryResult = client.getSqlQueryResult(execId, token);
            if (sqlQueryResult != null && sqlQueryResult.isSuccess()) {
                queryResult = sqlQueryResult.getResult();
                if (queryResult != null && queryResult.getData() != null) {
                    break;
                }
            }
            retry++;
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new SQLException("线程被中断，执行sql失败: " + sql, e);
            }
        }

        if (queryResult == null || queryResult.getData() == null) {
            throw new SQLException("获取sql结果超时: " + sql + ", connectInfo: " + JSON.toJSONString(catalogInfo));
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

        SqlExecDetailResp execDetailResp = JSON.parseObject(execResult, SqlExecDetailResp.class);

        // 3️⃣ 转换成 ExecuteResult
        ExecuteResult executeResult = new ExecuteResult();
        executeResult.setSuccess(execDetailResp.isSuccess());
        executeResult.setSql(execDetailResp.getSql());
        executeResult.setOriginalSql(sql);
        executeResult.setMessage(execDetailResp.getMessage());

        if (execDetailResp.getExecutetime() != null) {
            executeResult.setDuration(Long.valueOf(execDetailResp.getExecutetime()));
        }

        // ⚙️ 构造 header 列表（第一列是“行号”）
        List<Header> headerList = new ArrayList<>();
        Header rowNumberHeader = new Header();
        rowNumberHeader.setName("行号");
        rowNumberHeader.setDataType(DataTypeEnum.CHAT2DB_ROW_NUMBER.name());
        headerList.add(rowNumberHeader);

        if (CollUtil.isNotEmpty(execDetailResp.getFields())) {
            List<String> fields = execDetailResp.getFields();
            Map<String, String> fieldType = execDetailResp.getFieldType();
            for (String field : fields) {
                Header header = new Header();
                header.setName(field);
                header.setDataType(DataTypeEnum.STRING.name());
                // 可以根据 fieldType 决定更精确类型
                if (fieldType != null && fieldType.containsKey(field)) {
                    String type = fieldType.get(field);
                    if (type != null && type.toLowerCase().contains("int")) {
                        header.setDataType(DataTypeEnum.NUMERIC.name());
                    }
                }
                headerList.add(header);
            }
        }
        executeResult.setHeaderList(headerList);

        // ⚙️ 构造 dataList，每行前加行号（从 1 开始）
        List<List<String>> rawDataList = execDetailResp.getData();
        List<List<String>> wrappedDataList = new ArrayList<>();
        if (CollUtil.isNotEmpty(rawDataList)) {
            int rowNum = 1;
            for (List<String> row : rawDataList) {
                List<String> newRow = new ArrayList<>();
                newRow.add(String.valueOf(rowNum++)); // 第一列：行号
                newRow.addAll(row); // 追加原始列
                wrappedDataList.add(newRow);
            }
        }
        executeResult.setDataList(wrappedDataList);

        // ⚙️ 其他元信息
        executeResult.setCanEdit(false);
        executeResult.setSqlType(SqlTypeEnum.SELECT.name());
        executeResult.setPageSize(100);
        executeResult.setPageNo(1);
        executeResult.setFuzzyTotal("1");
        executeResult.setHasNextPage(false);
        executeResult.setDescription("执行成功");

        return executeResult;
    }
}
