package ai.chat2db.plugin.jingwei.client.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * SQL执行记录
 */
@Data
public class SqlExeRecord {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 插入时间
     */
    private String inserttime;

    /**
     * 更新时间
     */
    private String updatetime;

    /**
     * 是否激活
     */
    private Boolean isactive;

    /**
     * SQL执行记录ID
     */
    @JSONField(name = "sql_exe_record_id")
    @JsonProperty("sql_exe_record_id")
    private Long sqlExeRecordId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 语法错误类型
     */
    @JSONField(name = "syntax_error_type")
    @JsonProperty("syntax_error_type")
    private String syntaxErrorType;

    /**
     * 创建者名称
     */
    @JSONField(name = "creator_name")
    @JsonProperty("creator_name")
    private String creatorName;

    /**
     * 创建者账号
     */
    @JSONField(name = "creator_account")
    @JsonProperty("creator_account")
    private String creatorAccount;

    /**
     * SQL操作类型
     */
    @JSONField(name = "sql_option_type")
    @JsonProperty("sql_option_type")
    private Integer sqlOptionType;

    /**
     * 集群ID
     */
    @JSONField(name = "cluster_id")
    @JsonProperty("cluster_id")
    private String clusterId;

    /**
     * 数据源类型
     */
    @JSONField(name = "datasource_type")
    @JsonProperty("datasource_type")
    private Integer datasourceType;

    /**
     * 数据库名
     */
    private String db;

    /**
     * RDS数据库ID
     */
    @JSONField(name = "rds_db_id")
    @JsonProperty("rds_db_id")
    private String rdsDbId;

    /**
     * 业务ID
     */
    @JSONField(name = "business_id")
    @JsonProperty("business_id")
    private String businessId;

    /**
     * 业务名称
     */
    @JSONField(name = "business_name")
    @JsonProperty("business_name")
    private String businessName;

    /**
     * 表名列表
     */
    @JSONField(name = "table_name_list")
    @JsonProperty("table_name_list")
    private String tableNameList;

    /**
     * SQL文本
     */
    @JSONField(name = "sql_text")
    @JsonProperty("sql_text")
    private String sqlText;

    /**
     * 原始SQL文本
     */
    @JSONField(name = "origin_sql_text")
    @JsonProperty("origin_sql_text")
    private String originSqlText;

    /**
     * 结果（JSON字符串）
     */
    private String result;

    /**
     * 数据计数
     */
    @JSONField(name = "data_count")
    @JsonProperty("data_count")
    private Integer dataCount;

    /**
     * 语法错误SQL
     */
    @JSONField(name = "syntax_error_sql")
    @JsonProperty("syntax_error_sql")
    private String syntaxErrorSql;

    /**
     * 环境
     */
    private Integer env;

    /**
     * IP地址
     */
    private String ip;

    /**
     * MAC地址
     */
    private String mac;

    /**
     * 主机名
     */
    @JSONField(name = "host_name")
    @JsonProperty("host_name")
    private String hostName;

    /**
     * 数据源URL
     */
    @JSONField(name = "datasource_url")
    @JsonProperty("datasource_url")
    private String datasourceUrl;

    /**
     * 数据源端口
     */
    @JSONField(name = "datasource_port")
    @JsonProperty("datasource_port")
    private Integer datasourcePort;

    /**
     * 分析规则代码
     */
    @JSONField(name = "analysis_rule_code")
    @JsonProperty("analysis_rule_code")
    private String analysisRuleCode;
}