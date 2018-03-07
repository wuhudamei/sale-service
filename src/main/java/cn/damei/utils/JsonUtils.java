package cn.damei.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    public static final String STRING_EMPTY = "";

    public static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    private JsonUtils() {
    }

    /**
     * 对象转成Json
     *
     * @param obj 对象
     * @return
     */
    public static String toJson(Object obj) {
        String json;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return STRING_EMPTY;
        }
        return json;
    }

    /**
     * Json串->对象
     *
     * @param json  json串
     * @param clazz class类型
     * @param <T>   泛型
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将Json字符串转成Map
     *
     * @param json       json
     * @param keyClass   key的类型
     * @param valueClass value的类型
     * @param <K>        泛型Key
     * @param <V>        泛型Value
     * @return
     */
    public static <K, V> Map<K, V> fromJsonAsMap(String json, Class<K> keyClass, Class<V> valueClass) {
        JavaType type = mapper.getTypeFactory().
                constructMapType(Map.class, keyClass, valueClass);
        try {
            Map<K, V> map = mapper.readValue(json, type);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * Json串转成List,带泛型
     *
     * @param json  json串
     * @param clazz 泛型类
     * @param <T>   泛型
     * @return
     */
    public static <T> List<T> fromJsonAsList(String json, Class<T> clazz) {
        JavaType type = mapper.getTypeFactory().
                constructCollectionType(List.class, clazz);
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String s = "{loginName:\"huyatao@damei.com\",password:\"123456\",sign:\"oUCBrv3AerlSgvBH39dLJW-idPpc\",ticket:\"d290b82f37798c7c6050ddd91b6b6815\"}";
        Map<String, String> map = fromJsonAsMap(s, String.class, String.class);
        System.out.println(map);
    }
}