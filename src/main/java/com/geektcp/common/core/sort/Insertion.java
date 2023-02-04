package com.geektcp.common.core.sort;

/**
 * @author geektcp on 2019/9/23.
 */
public class Insertion<T extends Comparable<T>> extends Sort<T> {

    @Override
    public void sort(T[] numberArray) {
        int N = numberArray.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(numberArray[j], numberArray[j - 1]); j--) {
                swap(numberArray, j, j - 1);
            }
        }
    }
}
