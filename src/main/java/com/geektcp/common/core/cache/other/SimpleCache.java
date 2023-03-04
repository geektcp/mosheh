/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.geektcp.common.core.cache.other;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author geektcp on 2019/9/26.
 */
public class SimpleCache {

    private static Map<String, Object> cacheMap = new HashMap<>();
    private static Map<String, Long> expireTimeMap = new HashMap<>();

    private SimpleCache() {
    }

    public static Object get(String key) {
        if (!cacheMap.containsKey(key)) {
            return null;
        }
        if (expireTimeMap.containsKey(key) && expireTimeMap.get(key) < System.currentTimeMillis()) {
            return null;
        }
        return cacheMap.get(key);
    }

    public static void put(String key, Object value) {
        cacheMap.put(key, value);
    }

    public static void put(final String key, Object value, int millSeconds) {
        final long expireTime = System.currentTimeMillis() + millSeconds;
        cacheMap.put(key, value);
        expireTimeMap.put(key, expireTime);
        if (cacheMap.size() > 2) {
            new Thread(()-> {
                    Iterator<Map.Entry<String, Object>> iterator = cacheMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Object> entry = iterator.next();
                        if (expireTimeMap.containsKey(entry.getKey())) {
                            long expireTimeTmp = expireTimeMap.get(key);
                            if (System.currentTimeMillis() > expireTimeTmp) {
                                iterator.remove();
                                expireTimeMap.remove(entry.getKey());
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
