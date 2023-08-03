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
