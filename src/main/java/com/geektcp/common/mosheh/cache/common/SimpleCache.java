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
package com.geektcp.common.mosheh.cache.common;

import com.geektcp.common.mosheh.cache.Cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author geektcp on 2019/9/26.
 */
public class SimpleCache<K, V> implements Cache<K, V> {

    private Map<K, V> cacheMap = new HashMap<>();
    private Map<K, Long> expireTimeMap = new HashMap<>();

    public SimpleCache() {
        // do noting

    }

    public  V get(K key) {
        if (!cacheMap.containsKey(key)) {
            return null;
        }
        if (expireTimeMap.containsKey(key) && expireTimeMap.get(key) < System.currentTimeMillis()) {
            return null;
        }
        return cacheMap.get(key);
    }

    public boolean put(K key, V value) {
        cacheMap.put(key, value);
        return true;
    }

    public  void put(final K key, V value, int millSeconds) {
        final long expireTime = System.currentTimeMillis() + millSeconds;
        cacheMap.put(key, value);
        expireTimeMap.put(key, expireTime);
        if (cacheMap.size() > 2) {
            new Thread(()-> {
                    Iterator<Map.Entry<K, V>> iterator = cacheMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<K, V> entry = iterator.next();
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

    public boolean isExist(String key) {
        return cacheMap.containsKey(key);
    }


    @Override
    public boolean clear() {
        cacheMap.clear();
        return false;
    }

    @Override
    public boolean refresh(K key) {
        return false;
    }


    @Override
    public boolean delete(K key) {
        cacheMap.remove(key);
        return true;
    }

    @Override
    public void print(){

    }

    @Override
    public void travel(){

    }

    @Override
    public void start(){
    }

    @Override
    public void stop(){
    }
}
