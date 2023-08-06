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

package com.geektcp.common.mosheh.util;

import com.geektcp.common.mosheh.collection.Lists;

import java.util.List;
import java.util.Objects;

/**
 * @author geektcp on 2023/8/3 10:02.
 */
public class CollectionUtils {

    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";


    public static boolean isEmpty(List list) {
        return Objects.isNull(list) || list.isEmpty();
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }


    /**
     * default use ASC sort
     * @param list this the disorder list
     * @param <T> the type of list
     * @return  return the ordered list
     */
    public static <T extends Sortable> List<T> sort(List<T> list) {
        return sort(list, SORT_ASC);
    }

    /**
     * bubble sort algorithm
     * user can set the sortType SORT_ASC or SORT_DESC
     * @param list this the disorder list
     * @param <T> the type of list element
     */
    public static <T extends Sortable> List<T> sort(List<T> list, String sortType) {
        List<T> ret = Lists.newArrayList();
        List<T> listCopy = deepCopy(list);
        int size = listCopy.size();
        for (int i = 0; i < size; i++) {
            T bubble = getBubble(listCopy, sortType);
            listCopy.remove(bubble);
            ret.add(bubble);
        }
        return ret;
    }


    ////////////////////////////////////////////////////
    /**
     * get the Bubble
     * @param list the deep copied list
     * @param sortType sort type
     * @param <T> the type of list element
     * @return return the Bubble
     */
    private static <T extends Sortable> T getBubble(List<T> list, String sortType) {
        T bubble = list.get(0);
        for (int i = 0; i < list.size() - 1; i++) {
            bubble = compareSort(bubble, list.get(i + 1), sortType);
        }
        return bubble;
    }


    /**
     * compare and switch
     * @param src source obj
     * @param dst dest obj
     * @param sortType sort type
     * @param <T> the type of list element
     * @return the result of every comparison
     */
    private static <T extends Sortable> T compareSort(T src, T dst, String sortType) {
        if(Objects.isNull(dst)){
            return src;
        }
        if(Objects.isNull(src)){
            return dst;
        }
        int compareResult = compareSort(src.getSort(), dst.getSort(), sortType);
        if (compareResult > 0) {
            return src;
        }
        return dst;
    }

    @SuppressWarnings("unchecked")
    private static int compareSort(Comparable src, Comparable dst, String sortType) {
        if (SORT_ASC.equals(sortType)) {
            return dst.compareTo(src);
        }
        if (SORT_DESC.equals(sortType)) {
            return src.compareTo(dst);
        }
        return -1;  // default asc
    }

    @SuppressWarnings("unchecked")
    private static <T extends Sortable> List<T> deepCopy(List<T> list){
        Class<List<T>> cls = (Class<List<T>>) list.getClass();
        return ObjectUtils.deepCopyList(list, cls);
    }
}
