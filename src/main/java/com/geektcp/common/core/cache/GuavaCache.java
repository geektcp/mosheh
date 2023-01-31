package com.geektcp.common.core.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.geektcp.common.core.util.PrintUtils.print;


/**
 * @author haiyang.tang on 11.27 027 18:27:09.
 */
public class GuavaCache<T> implements Cache {

    private GuavaCache() {
    }

    private static LoadingCache<String, Object> CACHE = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterAccess(30, TimeUnit.SECONDS)
            .build(new CacheLoader<String, Object>() {
                @Override
                public Object load(String s) {
                    return new Object();
                }
            });

    public static void put(String key, AtomicLong o) {
        CACHE.put(key, o);
    }

    public Object get(String key) {
        Object value = CACHE.getIfPresent(key);
        if (Objects.isNull(value)) {
            return new Object();
        }
        return value;
    }

    public Object get(String key, Class cls) {
        Object value = CACHE.getIfPresent(key);
        if (Objects.isNull(value)) {
            return new Object();
        }
        return value;
    }

    public static List<String> listCache() {
        List<String> list = Lists.newArrayList();
        Set<String> keys = CACHE.asMap().keySet();
        try {
            for (String key : keys) {
                if (Objects.isNull(CACHE.getIfPresent(key))) {
                    continue;
                }
                list.add(key + " " + CACHE.getIfPresent(key));
            }
        } catch (Exception e) {
            print(e.getMessage());
        }
        return list;
    }

    @Override
    public void clean() {

    }

    @Override
    public void refresh(String key) {

    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public void delete(String key) {

    }
}
