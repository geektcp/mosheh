package com.geektcp.common.core.system;

import java.util.Objects;

/**
 * @author geektcp on 2018/3/15.
 */
public class ThyClass {

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
