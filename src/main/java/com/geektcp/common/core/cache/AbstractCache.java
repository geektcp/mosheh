package com.geektcp.common.core.cache;

import com.geektcp.common.core.cache.Cache;

/**
 * @author geektcp on 2023/3/5 15:13.
 */
public class AbstractCache<K,V> implements Cache<K,V> {


    @Override
    public boolean clear() {
        return false;
    }

    public boolean refresh(K key){
        return false;
    }

    public V get(K key){

        return null;
    }

    public boolean delete(K key){

        return false;
    }

    @Override
    public boolean put(K k, V v) {
        return false;
    }

}
