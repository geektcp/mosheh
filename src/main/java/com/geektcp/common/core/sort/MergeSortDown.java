package com.geektcp.common.core.sort;

/**
 * @author tanghaiyang on 2019/9/23.
 */
public class MergeSortDown<T extends Comparable<T>> extends MergeSort<T> {

    @Override
    public void sort(T[] numberArray) {
        aux = (T[]) new Comparable[numberArray.length];
        sort(numberArray, 0, numberArray.length - 1);
    }

    private void sort(T[] numberArray, int l, int h) {
        if (h <= l) {
            return;
        }
        int mid = l + (h - l) / 2;
        sort(numberArray, l, mid);
        sort(numberArray, mid + 1, h);
        merge(numberArray, l, mid, h);
    }
}
