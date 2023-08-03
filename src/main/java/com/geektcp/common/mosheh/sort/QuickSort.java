/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geektcp.common.mosheh.sort;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author geektcp on 2019/9/23.
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
