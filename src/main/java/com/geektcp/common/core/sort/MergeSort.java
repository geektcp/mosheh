package com.geektcp.common.core.sort;


/**
 * @author geektcp on 2019/9/23.
 */
public abstract class MergeSort<T extends Comparable<T>> extends Sort<T> {

    protected T[] aux;


    protected void merge(T[] numberArray, int l, int m, int h) {

        int i = l, j = m + 1;

        for (int k = l; k <= h; k++) {
            aux[k] = numberArray[k];
        }

        for (int k = l; k <= h; k++) {
            if (i > m) {
                numberArray[k] = aux[j++];

            } else if (j > h) {
                numberArray[k] = aux[i++];

            } else if (aux[i].compareTo(aux[j]) <= 0) {
                numberArray[k] = aux[i++];

            } else {
                numberArray[k] = aux[j++];
            }
        }
    }
}
