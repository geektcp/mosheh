package com.geektcp.common.mosheh.sort;

/**
 * @author geektcp on 2019/9/23.
 */
public abstract class Sort<T extends Comparable<T>> {

    public abstract void sort(T[] numberArray);

    protected boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    protected void swap(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
