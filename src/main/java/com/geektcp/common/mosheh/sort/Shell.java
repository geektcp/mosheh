package com.geektcp.common.mosheh.sort;

/**
 * @author geektcp on 2019/9/23.
 */
public class Shell<T extends Comparable<T>> extends Sort<T> {

    @Override
    public void sort(T[] numberArray) {

        int length = numberArray.length;
        int h = 1;

        while (h < length / 3) {
            h = 3 * h + 1; // 1, 4, 13, 40, ...
        }

        while (h >= 1) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && less(numberArray[j], numberArray[j - h]); j -= h) {
                    swap(numberArray, j, j - h);
                }
            }
            h = h / 3;
        }
    }
}
