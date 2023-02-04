package com.geektcp.common.core.sort;

/**
 * @author geektcp on 2019/9/23.
 */
public class Selection<T extends Comparable<T>> extends Sort<T> {

    @Override
    public void sort(T[] numberArray) {
        int N = numberArray.length;
        for (int i = 0; i < N - 1; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (less(numberArray[j], numberArray[min])) {
                    min = j;
                }
            }
            swap(numberArray, i, min);
        }
    }
}
