package ai.chat2db.plugin.jingwei.client.resp;

import com.alibaba.fastjson2.annotation.JSONField;
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
    private Long sqlExeRecordId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 语法错误类型
     */
    @JSONField(name = "syntax_error_type")
    private String syntaxErrorType;

    /**
     * 创建者名称
     */
    @JSONField(name = "creator_name")
    private String creatorName;

    /**
     * 创建者账号
     */
    @JSONField(name = "creator_account")
    private String creatorAccount;

    /**
     * SQL操作类型
     */
    @JSONField(name = "sql_option_type")
    private Integer sqlOptionType;

    /**
     * 集群ID
     */
    @JSONField(name = "cluster_id")
    private String clusterId;

    /**
     * 数据源类型
     */
    @JSONField(name = "datasource_type")
    private Integer datasourceType;

    /**
     * 数据库名
     */
    private String db;

    /**
     * RDS数据库ID
     */
    @JSONField(name = "rds_db_id")
    private String rdsDbId;

    /**
     * 业务ID
     */
    @JSONField(name = "business_id")
    private String businessId;

    /**
     * 业务名称
     */
    @JSONField(name = "business_name")
    private String businessName;

    /**
     * 表名列表
     */
    @JSONField(name = "table_name_list")
    private String tableNameList;

    /**
     * SQL文本
     */
    @JSONField(name = "sql_text")
    private String sqlText;

    /**
     * 原始SQL文本
     */
    @JSONField(name = "origin_sql_text")
    private String originSqlText;

    /**
     * 结果（JSON字符串）
     */
    private String result;

    /**
     * 数据计数
     */
    @JSONField(name = "data_count")
    private Integer dataCount;

    /**
     * 语法错误SQL
     */
    @JSONField(name = "syntax_error_sql")
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
    private String hostName;

    /**
     * 数据源URL
     */
    @JSONField(name = "datasource_url")
    private String datasourceUrl;

    /**
     * 数据源端口
     */
    @JSONField(name = "datasource_port")
    private Integer datasourcePort;

    /**
     * 分析规则代码
     */
    @JSONField(name = "analysis_rule_code")
    private String analysisRuleCode;
}