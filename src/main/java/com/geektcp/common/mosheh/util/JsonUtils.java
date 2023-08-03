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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author geektcp on 2018/4/12.
 */
public class JsonUtils {

    private JsonUtils() {
    }

    public static <T> Map<String, Object> toMap(T bean) {
        String text = JSON.toJSONString(bean);
        return JSON.parseObject(text, new TypeReference<Map<String, Object>>() {
        });
    }

    public static <T> List<Map<String, Object>> toListMap(List<T> beanList) {
        String text = JSON.toJSONString(beanList);
        return JSON.parseObject(text, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    public static Map<String, Object> jsonToMap(String json) {
        return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
    }

    public static List<Map<String, Object>> jsonToListMap(String json) {
        return JSON.parseObject(json, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    public static List<Map<String, Object>> toListMap(Object obj) {
        String json = JSON.toJSONString(obj);
        return JSON.parseObject(json, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    public static <S, D> D copy(S srcBean, Class<D> dstClass) {
        return JSON.parseObject(JSON.toJSONString(srcBean), dstClass);
    }

    public static <T> String toString(T object) {
        return toString(object, true);
    }

    public static <T> String toString(T object, boolean isPretty) {
        return JSON.toJSONString(object, isPretty);
    }

}
