package com.geektcp.common.core.cache;

/**
 * @author tanghaiyang  on 2021/5/6 16:59.
 */
public interface Cache {

    void clean();

    void refresh(String key);

    void put(String key, Object value);

    Object get(String key);

    void delete(String key);

}
