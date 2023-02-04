package com.geektcp.common.core.sort;

/**
 * @author geektcp on 2019/9/23.
 */
public class HeapSort<T extends Comparable<T>> extends Sort<T> {

    @Override
    public void sort(T[] numberArray) {
        int N = numberArray.length - 1;
        for (int k = N / 2; k >= 1; k--)
            sink(numberArray, k, N);

        while (N > 1) {
            swap(numberArray, 1, N--);
            sink(numberArray, 1, N);
        }
    }

    private void sink(T[] numberArray, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(numberArray, j, j + 1))
                j++;
            if (!less(numberArray, k, j))
                break;
            swap(numberArray, k, j);
            k = j;
        }
    }

    private boolean less(T[] numberArray, int i, int j) {
        return numberArray[i].compareTo(numberArray[j]) < 0;
    }
}
