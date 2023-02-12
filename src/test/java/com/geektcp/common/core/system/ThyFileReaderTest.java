package com.geektcp.common.core.system;

import org.junit.Test;

import java.io.File;

public class ThyFileReaderTest {

    @Test
    public void test2(){
        Sys.p("111: {}", Sys.getResourceClassPath());
        Sys.p("222: {}", Sys.getResourceRootPath());
        Sys.p("444: {}", Sys.getResourcePath("dist.json"));
        Sys.p("555: {}", Sys.getResourcePath("/aaa/dist.json"));
        Sys.p("666: {}", Sys.getResourcePath("ThyFileSystem.class"));
    }

}
