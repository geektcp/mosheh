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
