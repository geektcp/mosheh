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
package com.geektcp.common.mosheh.system;

import java.util.Objects;

/**
 * @author geektcp on 2018/3/15.
 */
class ThyClass {

    private ThyClass() {
    }

    public static Class<?> getClass(String className) {
        if (className == null) {
            return null;
        }
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            Sys.p("exception: + " + className + e.getMessage());
        }
        return cls;
    }

    public static String getClassPath(String className) {
        Class<?> cls = getClass(className);
        return getClassPath(cls);
    }

    public static String getClassPath(Class<?> cls) {
        if(Objects.isNull(cls)) {
            return null;
        }
        return cls.getResource("").getPath();
    }


}
