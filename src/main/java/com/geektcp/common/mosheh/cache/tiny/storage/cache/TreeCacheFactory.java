package com.geektcp.common.mosheh.cache.tiny.storage.cache;

import com.geektcp.common.mosheh.cache.Store;
import com.geektcp.common.mosheh.cache.tiny.storage.AbstractCacheFactory;
import com.geektcp.common.mosheh.tree.RedBlackTree;

/**
 * @author geektcp on 2023/8/13 0:08.
 */
public class TreeCacheFactory<K, V> extends AbstractCacheFactory {

    private Store store;


    public static TreeCacheFactory build(){
        return new TreeCacheFactory<>();
    }

    @Override
    public Store<K, V> buildCache() {
        store = new CacheTree();
        return store;
    }

    @Override
    public void set() {

    }

}
