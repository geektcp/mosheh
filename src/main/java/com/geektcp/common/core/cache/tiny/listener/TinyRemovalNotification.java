package com.geektcp.common.core.cache.tiny.listener;

import com.geektcp.common.core.util.Preconditions;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.AbstractMap;

/**
 * @author geektcp on 2023/2/26 17:16.
 */
public class TinyRemovalNotification<K, V> extends AbstractMap.SimpleImmutableEntry<K, V> {

    private final TinyRemovalCause cause;
    private static final long serialVersionUID = 0L;

    public static <K, V> TinyRemovalNotification<K, V> create(@Nullable K key, @Nullable V value, TinyRemovalCause cause) {
        return new TinyRemovalNotification(key, value, cause);
    }

    private TinyRemovalNotification(@Nullable K key, @Nullable V value, TinyRemovalCause cause) {
        super(key, value);
        this.cause = (TinyRemovalCause) Preconditions.checkNotNull(cause);
    }

    public TinyRemovalCause getCause() {
        return this.cause;
    }

    public boolean wasEvicted() {
        return this.cause.wasEvicted();
    }

}
