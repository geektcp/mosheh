package com.geektcp.common.core.cache;

import com.geektcp.common.core.cache.builder.*;
import com.geektcp.common.core.cache.builder.loader.TinyLoader;
import com.geektcp.common.core.cache.loading.InvalidateLoadingCache;
import com.geektcp.common.core.cache.builder.listener.TinyListener;
import com.geektcp.common.core.cache.builder.listener.TinyRemovalCause;
import com.geektcp.common.core.cache.builder.listener.TinyRemovalNotification;
import com.geektcp.common.core.concurrent.thread.executor.TinyExecutorBuilder;
import com.geektcp.common.core.concurrent.thread.executor.service.TinyExecutor;
import com.geektcp.common.core.generator.IdGenerator;
import com.geektcp.common.core.system.Sys;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author geektcp on 2023/2/26 16:27.
 */
public class TinyCacheBuilderTest {

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
        InvalidateLoadingCache<String, Long> tinyLoadingCache = TinyCacheBuilder.newBuilder()
                .refreshAfterWrite(7, TimeUnit.SECONDS)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .removalListener(localListener)
                .build(new TinyLoader<String, Long>() {
                    @Override
                    public Long load(String key) {
                        return myLoad(key);
                    }
                });

        tinyLoadingCache.put("a", 1L);
        Sys.p(tinyLoadingCache.get("a"));


        Assert.assertTrue(true);
    }


    private Long myLoad(String key){
       return IdGenerator.getId();
    }


    @Test
    public void test2(){
        final AtomicInteger loadCount = new AtomicInteger();
        final AtomicInteger reloadCount = new AtomicInteger();
        final AtomicInteger loadAllCount = new AtomicInteger();


        TinyLoader<Object, Object> baseLoader =
                new TinyLoader<Object, Object>() {
                    @Override
                    public Object load(Object key) {
                        loadCount.incrementAndGet();
                        return new Object();
                    }

                    @Override
                    public ListenableFuture<Object> reload(Object key, Object oldValue) {
                        reloadCount.incrementAndGet();
                        return Futures.immediateFuture(new Object());
                    }

                    @Override
                    public Map<Object, Object> loadAll(Iterable<?> keys) {
                        loadAllCount.incrementAndGet();
                        return ImmutableMap.of();
                    }
                };

        Object unused1 = baseLoader.load(new Object());
        TinyExecutor executor = TinyExecutorBuilder.newSingleThreadExecutor();
        TinyLoader<Object, Object> asyncReloading = TinyLoader.asyncReloading(baseLoader, executor);

        Object unused3 = asyncReloading.load(new Object());
        Future<?> possiblyIgnoredError1 = asyncReloading.reload(new Object(), new Object());
        Map<Object, Object> unused4 = asyncReloading.loadAll(ImmutableList.of(new Object()));

        Assert.assertEquals(2, loadCount.get());
        Assert.assertEquals(1, reloadCount.get());
        Assert.assertEquals(2, loadAllCount.get());


    }
}
