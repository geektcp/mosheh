package com.geektcp.common.core.cache.tiny.storage;

import com.geektcp.common.core.cache.Store;
import com.geektcp.common.core.tree.RedBlackTree;

/**
 * @author geektcp on 2023/3/12 19:55.
 */
public class CacheTree<K extends Comparable<K>, V> extends RedBlackTree<K, V> implements Store<K, V> {

    public CacheTree() {
        // to do

    }

    public CacheTree(K k, V v) {
        // to do

    }


    @Override
    public boolean clear() {
        return false;
    }

    @Override
    public boolean put(K k, V v) {
        super.add(k, v);
        return true;
    }

    @Override
    public boolean delete(K nodeId) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public V getRoot() {
        return null;
    }

    @Override
    public V fetch(K k) {
        return super.get(k);
    }

    @Override
    public V getHeight(K nodeId) {
        return null;
    }

    @Override
    public V getHeight() {
        return null;
    }
}
