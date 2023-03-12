package com.geektcp.common.core.cache;

import com.geektcp.common.core.cache.tiny.CacheBuilder;
import com.geektcp.common.core.cache.tiny.loader.TinyLoader;
import com.geektcp.common.core.cache.tiny.listener.TinyListener;
import com.geektcp.common.core.cache.tiny.listener.TinyRemovalCause;
import com.geektcp.common.core.cache.tiny.listener.TinyRemovalNotification;
import com.geektcp.common.core.cache.tiny.loading.LoadingCache;
import com.geektcp.common.core.generator.IdGenerator;
import com.geektcp.common.core.system.Sys;
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
        Long v = loadingCache.get("a");
        Sys.p(v);

        Assert.assertTrue(true);
    }


    private Long myLoad(String key){
       return IdGenerator.getId();
    }

}
