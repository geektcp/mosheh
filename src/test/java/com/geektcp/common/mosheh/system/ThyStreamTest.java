package com.geektcp.common.mosheh.system;

import org.junit.Assert;
import org.junit.Test;

public class ThyStreamTest {

    @Test
    public void test(){
        String str = "abc: {}|{}/{}";
        int[] indices = new int[3];
        Sys.p(str, "yju", 555);

        Assert.assertTrue(true);
    }

    @Test
    public void test2(){
        Sys.p("ssss: {}|{}", "ttt", 1111,"ssf","gggg");

        Assert.assertTrue(true);
    }

}
