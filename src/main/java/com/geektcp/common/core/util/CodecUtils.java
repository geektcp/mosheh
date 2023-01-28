package com.geektcp.common.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author tanghaiyang on 2017/12/12.
 */
public class CodecUtils {

    private CodecUtils() {
        throw new UnsupportedOperationException();
    }

    public static String md5(String source) {
        String result = "";
        if (source == null || ("".equals(source))) {
            return result;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(source.getBytes("utf-8"));
            StringBuffer sign = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 0) {
                    bt += 256;
                }
                if (bt < 16) {
                    sign.append(0);
                }
                sign.append(Integer.toHexString(bt));
            }
            result = sign.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
