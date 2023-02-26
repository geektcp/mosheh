package com.geektcp.common.core.cache;


/**
 * @author geektcp on 2019/11/30 0:59.
 */
public interface ThyCache {

    void clean();

    void refresh(String key);

    void put(String key, Object value);

    Object get(String key);

    void delete(String key);

}
