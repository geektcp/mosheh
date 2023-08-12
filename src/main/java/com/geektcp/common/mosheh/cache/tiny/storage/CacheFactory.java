package com.geektcp.common.mosheh.cache.tiny.storage;

import com.geektcp.common.mosheh.cache.Store;

/**
 * @author geektcp on 2023/8/13 0:04.
 */
public interface CacheFactory {

    Store buildCache();

    void set();

}
