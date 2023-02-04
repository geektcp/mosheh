package com.geektcp.common.core.sort;


/**
 * @author geektcp on 2019/9/23.
 */
public class Bubble<T extends Comparable<T>> extends Sort<T> {

    @Override
    public void sort(T[] numberArray) {
        int N = numberArray.length;
        boolean isSorted = false;
        for (int i = N - 1; i > 0 && !isSorted; i--) {
            isSorted = true;
            for (int j = 0; j < i; j++) {
                if (less(numberArray[j + 1], numberArray[j])) {
                    isSorted = false;
                    swap(numberArray, j, j + 1);
                }
            }
        }
    }
}
