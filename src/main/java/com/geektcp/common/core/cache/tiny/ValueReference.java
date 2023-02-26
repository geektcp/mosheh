package com.geektcp.common.core.cache.tiny;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.ref.ReferenceQueue;
import java.util.concurrent.ExecutionException;

/**
 * @author geektcp on 2023/2/26 18:00.
 */
public interface ValueReference<K, V> {
    @Nullable
    V get();

    V waitForValue() throws ExecutionException;

    int getWeight();

    @Nullable
    TinyReferenceEntry<K, V> getEntry();

    ValueReference<K, V> copyFor(ReferenceQueue<V> var1, @Nullable V var2, TinyReferenceEntry<K, V> var3);

    void notifyNewValue(@Nullable V var1);

    boolean isLoading();

    boolean isActive();
}
