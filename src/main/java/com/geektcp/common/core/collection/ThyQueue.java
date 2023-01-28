package com.geektcp.common.core.collection;

/**
 * @author tanghaiyang on 2019/9/28.
 */
public interface ThyQueue<Item> extends Iterable<Item> {

    int size();

    boolean isEmpty();

    ThyQueue<Item> add(Item item);

    Item remove() throws Exception;
}
