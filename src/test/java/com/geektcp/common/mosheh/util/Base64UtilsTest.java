package com.geektcp.common.mosheh.util;

import com.geektcp.common.mosheh.system.Sys;

/**
 * @author geektcp on 2023/9/13 21:47.
 */
public class Base64UtilsTest {
    public static void main(String[] args) {
        String base64Data = Base64Utils.getBase64FromUrl("https://baidu.com/favicon.ico");
        Sys.p(base64Data.length());
        Sys.p(base64Data);
    }
}
