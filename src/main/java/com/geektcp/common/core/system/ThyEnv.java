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
package com.geektcp.common.core.system;

import java.util.Map;

/**
 * @author geektcp on 2023/2/5 1:28.
 */
class ThyEnv {

    private ThyEnv() {
    }

    private static Map<String, String> env = System.getenv();

    /**
     * only get evv , can not set env
     * because env init when java process start
     * */
    public static String getEnv(String name) {
        return env.getOrDefault(name, "");
    }

}
