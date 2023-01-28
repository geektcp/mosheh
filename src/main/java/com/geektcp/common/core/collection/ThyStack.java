package com.geektcp.common.core.collection;

/**
 * @author tanghaiyang on 2019/9/28.
 */
public interface ThyStack<Item> extends Iterable<Item> {

    ThyStack<Item> push(Item item);

    Item pop() throws Exception;

    boolean isEmpty();

    int size();

}
