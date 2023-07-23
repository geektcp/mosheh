package com.geektcp.common.mosheh.cache.tiny.storage;

import com.geektcp.common.mosheh.cache.Store;
import com.geektcp.common.mosheh.tree.RedBlackTree;

/**
 * @author geektcp on 2023/3/12 19:55.
 */
public class CacheTree<K extends AbstractKey<K>, V> extends RedBlackTree<K, V> implements Store<K, V> {

    public static boolean IS_VALID = true;

    public CacheTree() {
        // to do
        IS_VALID = true;
        this.init();

    }

    public CacheTree(K k, V v) {
        // to do

    }


    @Override
    public boolean clear() {
        return super.clear();
    }

    @Override
    public boolean put(K k, V v) {
        if(!IS_VALID){
            return false;
        }
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

    public void print(){
        super.print();
    }

    public boolean invalid(){
        IS_VALID = false;
        return super.clear();
    }

    @Override
    public void start(){
        IS_VALID = true;
    }

    @Override
    public void stop(){
        IS_VALID = false;
        super.clear();
    }
}
