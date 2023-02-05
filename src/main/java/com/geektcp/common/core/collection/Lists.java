package com.geektcp.common.core.collection;

import com.geektcp.common.core.system.Sys;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public class Lists<T> {

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
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


}
