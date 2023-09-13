package com.geektcp.common.mosheh.util;

import com.geektcp.common.mosheh.system.Sys;

/**
 * @author geektcp on 2023/9/14 0:17.
 */
public class DownUtilsTest {
    public static void main(String[] args) {
        boolean ret = DownUtils.downFileFromUrl("https://baidu.com/favicon.ico", "aaa.png");
        Sys.p(ret);
    }

}
