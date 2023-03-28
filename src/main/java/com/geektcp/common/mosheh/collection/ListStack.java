package com.geektcp.common.mosheh.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author geektcp on 2019/9/28.
 */
public class ListStack<Item> implements ThyStack<Item> {

    private Node top = null;
    private int N = 0;

    private class Node {
        Item item;
        Node next;
    }

    @Override
    public ThyStack<Item> push(Item item) {
        Node newTop = new Node();
        newTop.item = item;
        newTop.next = top;
        top = newTop;
        N++;

        return this;
    }

    @Override
    public Item pop() throws Exception {
        if (isEmpty()) {
            throw new Exception("stack is empty");
        }
        Item item = top.item;
        top = top.next;
        N--;

        return item;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node cur = top;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item item = cur.item;
                cur = cur.next;
                return item;
            }
        };

    }
}
