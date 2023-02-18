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

import java.util.Properties;

/**
 * @author geektcp on 2023/2/5 0:08.
 */
class ThySystemProperties {

    private ThySystemProperties() {
    }

    private static Properties prop = System.getProperties();

    public static Properties getProperties() {
        return prop;
    }

    public static String getUserHome() {
        return prop.getProperty("user.home");
    }

    public static String getUserDir() {
        return prop.getProperty("user.dir");
    }

    public static String getUserName() {
        return prop.getProperty("user.name");
    }

    public static String getTmpdir() {
        return prop.getProperty("java.io.tmpdir");
    }


    public static String getUserLanguage() {
        return prop.getProperty("user.language");
    }

    public static String getUserTimezone() {
        return prop.getProperty("user.timezone");
    }

    public static String getOsName() {
        return prop.getProperty("os.name");
    }

    public static boolean isWindows() {
        return getOsName().contains("Windows");
    }

    public static boolean isLinux() {
        return getOsName().contains("Linux");
    }

    public static String getOsVersion() {
        return prop.getProperty("os.version");
    }

    public static String getJdkVersion() {
        return prop.getProperty("java.version");
    }

    public static String getFileEncoding() {
        return prop.getProperty("file.encoding");
    }

    public static String getOsArch() {
        return prop.getProperty("os.arch");
    }

    public static String getJdkArch() {
        return prop.getProperty("sun.arch.data.model");
    }

    public static int getOsBit() {
        String osArch = getOsArch();
        if (osArch.contains("64")) {
            return 64;
        }
        return 32;
    }

    public static int getJdkBit() {
        String jdkArch = getJdkArch();
        if (jdkArch.contains("64")) {
            return 64;
        }
        return 32;
    }

    public static String getJavaClassPath() {
        return prop.getProperty("java.class.path");
    }

    public static String getFileSeparator() {
        return prop.getProperty("file.separator");
    }


}
