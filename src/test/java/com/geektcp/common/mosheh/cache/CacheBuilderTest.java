package com.geektcp.common.mosheh.cache;

import com.geektcp.common.mosheh.cache.tiny.CacheBuilder;
import com.geektcp.common.mosheh.cache.tiny.loader.TinyLoader;
import com.geektcp.common.mosheh.cache.tiny.listener.TinyListener;
import com.geektcp.common.mosheh.cache.tiny.listener.TinyRemovalCause;
import com.geektcp.common.mosheh.cache.tiny.listener.TinyRemovalNotification;
import com.geektcp.common.mosheh.cache.tiny.loading.LoadingCache;
import com.geektcp.common.mosheh.generator.IdGenerator;
import com.geektcp.common.mosheh.system.Sys;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author geektcp on 2023/2/26 16:27.
 */
public class CacheBuilderTest {

    private static TinyListener<String, Long> localListener = new TinyListener<String, Long>() {
        @Override
        public void onRemoval(TinyRemovalNotification<String, Long> notification) {
            String describe = String.format("thread id=%s, key=%s, value=%s, reason=%s in localListener",
                    Thread.currentThread().getId(),
                    notification.getKey(),
                    notification.getValue(),
                    notification.getCause());
            Sys.p(describe);

            if (notification.getCause().equals(TinyRemovalCause.EXPIRED) && notification.getValue() != null) {
                Sys.p("remove {} expired cache\n", notification.getKey());
            }
        }
    };

    @Test
    public void test() {
        LoadingCache<String, Long> loadingCache = CacheBuilder.newBuilder()
                .refreshAfterWrite(7, TimeUnit.SECONDS)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .removalListener(localListener)
                .build(new TinyLoader<String, Long>() {
                    @Override
                    public Long load(String key) {
                        return myLoad(key);
                    }
                });

        loadingCache.put("a", 1L);
//        loadingCache.print();
        Sys.p("----------------");
        loadingCache.put("b", 23L);
//        loadingCache.print();
        Sys.p("----------------");
        loadingCache.put("c", 35L);
//        loadingCache.print();
        Sys.p("----------------");
        loadingCache.put("d", 42L);
        loadingCache.print();
        loadingCache.travel();
        Sys.p("----------------");
        Sys.p("----------------");
        Sys.p("a: {}", loadingCache.get("a"));
        Sys.p("b: {}", loadingCache.get("b"));
        Sys.p("f: {}", loadingCache.get("f"));
        Sys.p("c: {}", loadingCache.get("c"));
        Sys.p("d: {}", loadingCache.get("d"));
        Assert.assertTrue(true);
    }


    private Long myLoad(String key) {
        return IdGenerator.getId();
    }

}
