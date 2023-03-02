package com.geektcp.common.core.cache.tiny.cache;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author geektcp on 2019/9/10.
 */
public final class WeakCache<K, V> {

    private final int size;

    private final Map<K, V> eden;

    private final Map<K, V> longTerm;

    public WeakCache(int size) {
        this.size = size;
        this.eden = new ConcurrentHashMap<>(size);
        this.longTerm = new WeakHashMap<>(size);
    }

    public V get(K k) {
        V v = this.eden.get(k);
        if (v == null) {
            v = this.longTerm.get(k);
            if (v != null)
                this.eden.put(k, v);
        }
        return v;
    }

    public void put(K k, V v) {
        if (this.eden.size() >= size) {
            this.longTerm.putAll(this.eden);
            this.eden.clear();
        }
        this.eden.put(k, v);
    }

}
