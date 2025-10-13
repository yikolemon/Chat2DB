package ai.chat2db.plugin.jingwei.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class JSONUtil {
    private static final Logger log = LoggerFactory.getLogger(JSONUtil.class);

    /**
     * 对象转 JSON 字符串
     */
    public static String toJson(Object o) {
        try {
            return JSON.toJSONString(o);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    public static <T> T toObjectByTypeRef(String json, TypeReference<T> typeRef) {
        try {
            return JSON.parseObject(json, typeRef);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw null;
    }


    /**
     * JSON 字符串转对象
     */
    public static <T> T toObject(String json, Class<T> tClass) {
        try {
            return JSON.parseObject(json, tClass);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw null;
    }
    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            return JSON.parseArray(json, clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }


    /**
     * JSON 字符串转对象（带 TypeReference，支持泛型）
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        try {
            return JSON.parseObject(json, typeReference);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw null;
    }

    /**
     * JSON 字符串转 JSON 对象节点（类似 Jackson 的 JsonNode）
     */
    public static Object toJsonNode(String json) {
        try {
            return JSON.parse(json);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        throw null;
    }

}
