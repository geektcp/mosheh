package com.geektcp.common.core.collection;

import java.util.Iterator;

/**
 * @author geektcp on 2019/9/28.
 */
public class ArrayStack<Item> implements ThyStack<Item> {

    private Item[] a = (Item[]) new Object[1];

    private int N = 0;

    @Override
    public ThyStack<Item> push(Item item) {
        check();
        a[N++] = item;
        return this;
    }

    @Override
    public Item pop() {
        if (isEmpty()) {
            return null;
        }

        Item item = a[--N];
        check();
        a[N] = null;

        return item;
    }


    private void check() {
        if (N >= a.length) {
            resize(2 * a.length);

        } else if (N > 0 && N <= a.length / 4) {
            resize(a.length / 2);
        }
    }

    private void resize(int size) {
        Item[] tmp = (Item[]) new Object[size];
        for (int i = 0; i < N; i++) {
            tmp[i] = a[i];
        }
        a = tmp;
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
            private int i = N;
            @Override
            public boolean hasNext() {
                return i > 0;
            }
            @Override
            public Item next() {
                return a[--i];
            }
        };

    }
}
