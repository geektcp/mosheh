package com.geektcp.common.core.util;

/**
 * @author tanghaiyang on 2018/3/15.
 */
public class ClassUtils {

    public static Class<?> loadClass(String className) {
        if (className == null) {
            return null;
        }
        Class<?> cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            //log.debug("Exception while loading class: " + className, ex);
        }
        return cls;
    }

    public static String jarForClass(String className) {
        Class<?> cls = loadClass(className);
        return jarForClass(cls);
    }

    public static String jarForClass(Class<?> klass) {
        return klass.getProtectionDomain().getCodeSource().getLocation().getPath();
    }

    private ClassUtils() {
        // Disable explicit object creation
    }
}
