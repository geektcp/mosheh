package com.geektcp.common.core.system;

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
