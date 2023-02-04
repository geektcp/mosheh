package com.geektcp.common.core.system;

import com.geektcp.common.core.util.JsonUtils;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author geektcp on 2023/2/4 23:37.
 */
public class SysTest {

    @Test
    public void testPrint() {
        Sys.println("aaa");
        Sys.println("123");

        Assert.assertTrue(true);
    }

    @Test
    public void testRuntime() {
        Sys.println(Sys.availableProcessors());
        Sys.println(Sys.maxMemory());
        Sys.println(Sys.totalMemory());

        Assert.assertTrue(true);
    }

    @Test
    public void testProperties() {
        Properties prop = Sys.getProperties();
        Sys.println(JsonUtils.toString(prop));

        Assert.assertTrue(true);
    }

    @Test
    public void testVar() {
        Sys.println(Sys.getUserHome());
        Sys.println(Sys.getResourcePath());
        Sys.println(Sys.getFileSeparator());
        Sys.println(Sys.getJdkVersion());
        Sys.println(Sys.getUserHome());
        Sys.println(Sys.getOsName());
        Sys.println(Sys.getUserLanguage());
        Sys.println(Sys.getUserName());

        Assert.assertTrue(true);
    }

    @Test
    public void testEnv() {
        // windows
        Sys.println(Sys.getEnv("JAVA_HOME"));
        Sys.println(Sys.getEnv("Path"));

        // linux
        Sys.println(Sys.getEnv("PATH"));
        Sys.println(Sys.getEnv("HOME"));
        Assert.assertTrue(true);
    }

}
