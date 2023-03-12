package com.geektcp.common.core.cache;
/*
import com.geektcp.common.core.system.Sys;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.*;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author geektcp on 2019/11/30 0:59.
 */


/*
public class GuavaCacheTest {

    private static RemovalListener<String, Integer> localListener = new RemovalListener<String, Integer>() {
        @Override
        public void onRemoval(RemovalNotification<String, Integer> notification) {
            String describe = String.format("thread id=%s, key=%s, value=%s, reason=%s in localListener",
                    Thread.currentThread().getId(),
                    notification.getKey(),
                    notification.getValue(),
                    notification.getCause());
            Sys.p(describe);

            if (notification.getCause().equals(RemovalCause.EXPIRED) && notification.getValue() != null) {
                Sys.printf("remove %s expired cache\n", notification.getKey());
            }
        }
    };

    public static LoadingCache<String, Integer> LocalCache = CacheBuilder.newBuilder()
            .refreshAfterWrite(7, TimeUnit.SECONDS)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .removalListener(localListener)
            .build(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String key) throws Exception {
                    return 0;
                }
            });




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
            Sys.p(e.getMessage());
        }
        return list;
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
*/
