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
