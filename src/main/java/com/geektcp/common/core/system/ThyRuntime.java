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

import java.io.IOException;

/**
 * @author geektcp on 2023/2/4 23:47.
 */
class ThyRuntime {

    private ThyRuntime() {
    }

    /**
     * return used max memory in Java virtual machine
     */
    public static long maxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    /**
     * return total memory in Java virtual machine
     */
    public static long totalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * return free memory in Java virtual machine
     */
    public static long freeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * @author geektcp
     * @return number of CPUs of the machine
     */
    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static Process exec(String cmd) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            Sys.p(e.getMessage());
        }
        return process;
    }

    public static void gc() {
        Runtime.getRuntime().gc();
    }


}
