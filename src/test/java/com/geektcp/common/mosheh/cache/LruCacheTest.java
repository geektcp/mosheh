package com.geektcp.common.mosheh.cache;


import com.geektcp.common.mosheh.cache.common.LruCache;
import com.geektcp.common.mosheh.system.Sys;
import org.junit.Assert;
import org.junit.Test;

public class LruCacheTest {

    @Test
    public void test() {
        LruCache<String, Object> lruCache = new LruCache<>();

        lruCache.put("aaa", 11);
        lruCache.put("bbb", 22);
        lruCache.put("ccc", 33);
        lruCache.put("ddd", 44);

        Sys.p(lruCache.get("ddd"));

        Assert.assertTrue(true);
    }

}
