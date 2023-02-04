package com.geektcp.common.core.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author geektcp on 2019/9/26.
 */
public class SimpleCache {
    private static Map<String, Object> cacheMap = new HashMap<>();
    private static Map<String, Long> expireTimeMap = new HashMap<>();

    public static Object get(String key) {
        if (!cacheMap.containsKey(key)) {
            return null;
        }
        if (expireTimeMap.containsKey(key)) {
            if (expireTimeMap.get(key) < System.currentTimeMillis()) {
                return null;
            }
        }
        return cacheMap.get(key);
    }

    public static <T> T getT(String key) {
        Object obj = get(key);
        return obj == null ? null : (T) obj;
    }

    public static void set(String key, Object value) {
        cacheMap.put(key, value);
    }

    public static void set(final String key, Object value, int millSeconds) {
        final long expireTime = System.currentTimeMillis() + millSeconds;
        cacheMap.put(key, value);
        expireTimeMap.put(key, expireTime);
        if (cacheMap.size() > 2) {
            new Thread(new Runnable() {
                public void run() {
                    Iterator<Map.Entry<String, Object>> iterator = cacheMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = iterator.next();
                        if (expireTimeMap.containsKey(entry.getKey())) {
                            long expireTime = expireTimeMap.get(key);
                            if (System.currentTimeMillis() > expireTime) {
                                iterator.remove();
                                expireTimeMap.remove(entry.getKey());
                            }
                        }
                    }
                }
            }).start();
        }
    }

    public static boolean isExist(String key) {
        return cacheMap.containsKey(key);
    }

}
