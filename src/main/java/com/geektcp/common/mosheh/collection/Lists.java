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

package com.geektcp.common.mosheh.collection;

import com.geektcp.common.mosheh.system.Sys;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class Lists {

    public static <T> T[] toArray(List<T> src, Class<T> cls) {
        if (Objects.isNull(src)) {
            Sys.p("List src is null");
            return (T[]) Array.newInstance(cls, 0);
        }
        int size = src.size();
        T[] dst = (T[]) Array.newInstance(cls, size);

        for (int i = 0; i < size; i++) {
            dst[i] = src.get(i);
        }
        return dst;
    }

    public static <T> T[] toArray(List<T> src) {
        Class<?> cls = Object.class;
        if (Objects.isNull(src) || src.isEmpty()) {
            Sys.p("List src is null or empty");
            return (T[]) Array.newInstance(cls, 0);
        }
        int size = src.size();

        {
            // get class
            boolean isAllNull = false;
            for (T checkElement : src) {
                if (Objects.nonNull(checkElement)) {
                    isAllNull = true;
                    cls = checkElement.getClass();
                    break;
                }
            }
            if (isAllNull) {
                return (T[]) Array.newInstance(cls, 0);
            }
        }

        T[] dst = (T[]) Array.newInstance(cls, size);

        for (int i = 0; i < size; i++) {
            dst[i] = src.get(i);
        }
        return dst;
    }


    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList();
    }



}
