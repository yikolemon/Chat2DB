package ai.chat2db.plugin.jingwei.client.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 表信息响应（包含字段和索引）
 */
@Data
public class TableInfoResponse {
    
    /**
     * 数据内容
     */
    private TableInfoData data;
    
    /**
     * 状态码
     */
    private Integer status;
    
    /**
     * 是否成功
     */
    private Boolean success;
    
    /**
     * 表信息数据
     */
    @Data
    public static class TableInfoData {
        /**
         * 字段列表
         */
        private List<ColumnInfo> columns;
        
        /**
         * 索引列表
         */
        private List<IndexInfo> index;
        
        /**
         * 分区信息
         */
        private Object partition;
    }
    
    /**
     * 字段信息
     */
    @Data
    public static class ColumnInfo {
        /**
         * 字段名
         */
        @JSONField(name = "column_name")
        @JsonProperty("column_name")
        private String columnName;
        
        /**
         * 字段位置
         */
        @JSONField(name = "ordinal_position")
        @JsonProperty("ordinal_position")
        private Integer ordinalPosition;
        
        /**
         * 字段类型
         */
        @JSONField(name = "type_name")
        @JsonProperty("type_name")
        private String typeName;
        
        /**
         * 是否可为空
         */
        @JSONField(name = "is_nullable")
        @JsonProperty("is_nullable")
        private Boolean isNullable;
        
        /**
         * 是否自增
         */
        @JSONField(name = "is_autoincrement")
        @JsonProperty("is_autoincrement")
        private Boolean isAutoincrement;
        
        /**
         * 是否主键
         */
        @JSONField(name = "is_primary_key")
        @JsonProperty("is_primary_key")
        private Boolean isPrimaryKey;
        
        /**
         * 字段注释
         */
        private String remarks;
    }
    
    /**
     * 索引信息
     */
    @Data
    public static class IndexInfo {
        /**
         * 索引名
         */
        @JSONField(name = "index_name")
        @JsonProperty("index_name")
        private String indexName;
        
        /**
         * 索引类型
         */
        @JSONField(name = "index_type")
        @JsonProperty("index_type")
        private String indexType;
        
        /**
         * 存储类型
         */
        @JSONField(name = "storage_type")
        @JsonProperty("storage_type")
        private String storageType;
        
        /**
         * 索引字段
         */
        @JSONField(name = "index_columns")
        @JsonProperty("index_columns")
        private List<String> indexColumns;
    }
}