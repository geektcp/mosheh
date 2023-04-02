package com.geektcp.common.mosheh.collection;

/**
 * @author geektcp on 2019/9/28.
 */
public interface ThyQueue<Item> extends Iterable<Item> {

    int size();

    boolean isEmpty();

    ThyQueue<Item> add(Item item);

    Item remove() throws Exception;
}
