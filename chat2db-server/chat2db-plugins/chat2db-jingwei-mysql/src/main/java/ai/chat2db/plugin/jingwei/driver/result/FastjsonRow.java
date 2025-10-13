package ai.chat2db.plugin.jingwei.driver.result;

import com.alibaba.fastjson2.JSONObject;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * FastjsonRow 替代 JacksonNodeRow，使用 JSONObject
 */
public class FastjsonRow implements Row {
    private final JSONObject jsonObject;

    /**
     * 列名数据
     */
    private final Columns columns;

    public FastjsonRow(JSONObject jsonObject, Columns columns) {
        Objects.requireNonNull(jsonObject, "jsonObject required");
        Objects.requireNonNull(columns, "columns required");
        this.jsonObject = jsonObject;
        this.columns = columns;
    }

    @Override
    public Columns getColumns() {
        return columns;
    }

    @Override
    public String getStringByIndex(Integer columnIndex) {
        return getStringByName(columns.getName(columnIndex));
    }

    @Override
    public String getStringByName(String columnName) {
        Object value = jsonObject.get(columnName);
        return value != null ? value.toString() : null;
    }

    @Override
    public Integer getIntByIndex(Integer columnIndex) {
        return getIntByName(columns.getName(columnIndex));
    }

    @Override
    public Integer getIntByName(String columnName) {
        Object value = jsonObject.get(columnName);
        return value != null ? ((Number) value).intValue() : null;
    }

    @Override
    public Long getLongByIndex(Integer columnIndex) {
        return getLongByName(columns.getName(columnIndex));
    }

    @Override
    public Long getLongByName(String columnName) {
        Object value = jsonObject.get(columnName);
        return value != null ? ((Number) value).longValue() : null;
    }

    @Override
    public Float getFloatByIndex(Integer columnIndex) {
        return getFloatByName(columns.getName(columnIndex));
    }

    @Override
    public Float getFloatByName(String columnName) {
        Object value = jsonObject.get(columnName);
        return value != null ? ((Number) value).floatValue() : null;
    }

    @Override
    public Double getDoubleByIndex(Integer columnIndex) {
        return getDoubleByName(columns.getName(columnIndex));
    }

    @Override
    public Double getDoubleByName(String columnName) {
        Object value = jsonObject.get(columnName);
        return value != null ? ((Number) value).doubleValue() : null;
    }

    @Override
    public Boolean getBooleanByIndex(Integer columnIndex) {
        return getBooleanByName(columns.getName(columnIndex));
    }

    @Override
    public Boolean getBooleanByName(String columnName) {
        Object value = jsonObject.get(columnName);
        return value != null ? (Boolean) value : null;
    }

    @Override
    public BigDecimal getBigDecimalByIndex(Integer columnIndex) {
        return getBigDecimalByName(columns.getName(columnIndex));
    }

    @Override
    public BigDecimal getBigDecimalByName(String columnName) {
        Object value = jsonObject.get(columnName);
        if (value == null) throw null;
        if (value instanceof BigDecimal) return (BigDecimal) value;
        if (value instanceof Number) return BigDecimal.valueOf(((Number) value).doubleValue());
        try {
            return new BigDecimal(value.toString());
        } catch (NumberFormatException e) {
            throw null;
        }
    }
}
