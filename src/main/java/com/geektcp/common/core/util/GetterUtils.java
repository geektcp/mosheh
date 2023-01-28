package com.geektcp.common.core.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author tanghaiyang on 2017/12/6.
 */
public class GetterUtils {

    public static <K, V> String get(String key, Map<K, V> map) {
        return get(key, "", map);
    }

    public static <K, V> String get(String key, String defaultValue, Map<K, V> map) {
        if (map == null || !map.containsKey(key)) {
            return defaultValue;
        }
        return Objects.toString(map.get(key), "");
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> getListMap(Object obj) {
        return (List<Map<String, Object>>) obj;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMap(Object obj) {
        return (Map<String, Object>) obj;
    }
}
