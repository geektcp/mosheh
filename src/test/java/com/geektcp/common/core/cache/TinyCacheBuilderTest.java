package com.geektcp.common.core.cache;

import com.geektcp.common.core.cache.builder.*;
import com.geektcp.common.core.cache.loading.InvalidateLoadingCache;
import com.geektcp.common.core.cache.builder.listener.TinyListener;
import com.geektcp.common.core.cache.builder.listener.TinyRemovalCause;
import com.geektcp.common.core.cache.builder.listener.TinyRemovalNotification;
import com.geektcp.common.core.cache.builder.loader.implement.TinyCacheLoader;
import com.geektcp.common.core.system.Sys;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author geektcp on 2023/2/26 16:27.
 */
public class TinyCacheBuilderTest {

    private static TinyListener<String, Integer> localListener = new TinyListener<String, Integer>() {
        @Override
        public void onRemoval(TinyRemovalNotification<String, Integer> notification) {
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
        InvalidateLoadingCache<String, Integer> tinyLoadingCache = TinyCacheBuilder.newBuilder()
                .refreshAfterWrite(7, TimeUnit.SECONDS)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .removalListener(localListener)
                .build(new TinyCacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });

        tinyLoadingCache.put("a", 1);
        Sys.p(tinyLoadingCache.get("a"));


        Assert.assertTrue(true);
    }
}
