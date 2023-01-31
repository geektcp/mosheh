package com.geektcp.common.core.sort;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author tanghaiyang on 2019/9/23.
 */
public class QuickSort<T extends Comparable<T>> extends Sort<T> {

    @Override
    public void sort(T[] numberArray) {
        shuffle(numberArray);
        sort(numberArray, 0, numberArray.length - 1);
    }

    private void sort(T[] numberArray, int l, int h) {
        if (h <= l)
            return;
        int j = partition(numberArray, l, h);
        sort(numberArray, l, j - 1);
        sort(numberArray, j + 1, h);
    }

    private void shuffle(T[] numberArray) {
        List<Comparable> list = Arrays.asList(numberArray);
        Collections.shuffle(list);
        list.toArray(numberArray);
    }

    private int partition(T[] numberArray, int l, int h) {
        int i = l, j = h + 1;
        T v = numberArray[l];
        while (true) {
            while (less(numberArray[++i], v) && i != h) ;
            while (less(v, numberArray[--j]) && j != l) ;
            if (i >= j)
                break;
            swap(numberArray, i, j);
        }
        swap(numberArray, l, j);
        return j;
    }

    public T select(T[] numberArray, int k) {
        int l = 0, h = numberArray.length - 1;
        while (h > l) {
            int j = partition(numberArray, l, h);

            if (j == k) {
                return numberArray[k];

            } else if (j > k) {
                h = j - 1;

            } else {
                l = j + 1;
            }
        }
        return numberArray[k];
    }
}
