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
