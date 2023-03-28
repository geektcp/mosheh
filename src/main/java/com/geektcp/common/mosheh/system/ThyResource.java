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

import java.net.URL;
import java.util.Objects;

/**
 * @author geektcp on 2023/2/13 19:58.
 */
class ThyResource {

    private ThyResource() {
    }

    public static String getResourceRootPath() {
        return getResourceClassPath("/", ThyFileSystem.class);
    }

    public static String getResourceClassPath() {
        return getResourceClassPath("", ThyFileSystem.class);
    }

    public static String getResourceClassPath(String name) {
        return getResourceClassPath(name, ThyFileSystem.class);
    }

    public static String getResourceClassPath(Class<?> cls) {
        return getResourceClassPath("", cls);
    }

    public static String getResourceClassPath(String name, Class<?> cls) {
        if (Objects.isNull(name) || Objects.isNull(cls)) {
            return "";
        }
        URL url = cls.getResource(name);
        if (Objects.isNull(url)) {
            return "";
        }
        return url.getPath();
    }

    public static String getResourcePath() {
        return getResourcePath("");
    }

    public static String getResourcePath(String name) {
        String rootPath = getResourceRootPath();
        if (Objects.isNull(name)) {
            return rootPath;
        }
        if (name.startsWith("/")) {
            name = name.substring(1);
        }
        return rootPath + name;
    }
}
