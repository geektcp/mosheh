package com.geektcp.common.mosheh.util;

import com.geektcp.common.mosheh.exception.BaseException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author geektcp on 2023/8/6 15:53.
 */
public class ObjectUtils {

    public static <T extends Object> T deepCopyClass(Object object, Class<T> clazz) {
        Object ret = deepCopy(object);

        return clazz.cast(ret);
    }

    public static <T extends Object> List<T> deepCopyList(Object object, Class<List<T>> clazz) {
        Object ret = deepCopy(object);

        return clazz.cast(ret);
    }


    /*
     * para list will be modified when excute recursive inserting, so need deepcopy
     *
     * */
    public static Object deepCopy(Object object) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
            return objInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }
}
