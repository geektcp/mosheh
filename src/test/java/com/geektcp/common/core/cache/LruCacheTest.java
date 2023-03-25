package com.geektcp.common.core.cache;

import com.geektcp.common.core.cache.common.LruCache;
import com.geektcp.common.core.system.Sys;

public class LruCacheTest {

    public static void main(String[] args) {
        LruCache<String, Object> lruCache = new LruCache<>();

        lruCache.put("aaa", 11);
        lruCache.put("bbb", 22);
        lruCache.put("ccc", 33);
        lruCache.put("ddd", 44);

        Sys.p(lruCache.get("ddd"));

    }

}
