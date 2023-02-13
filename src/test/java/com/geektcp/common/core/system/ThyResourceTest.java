package com.geektcp.common.core.system;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author geektcp on 2023/2/13 20:00.
 */
public class ThyResourceTest {

    @Test
    public void getPath(){
        Sys.p(ThyResource.getResourceClassPath());
        Sys.p(ThyResource.getResourcePath());

        Sys.p(Sys.getResourceClassPath(ThyResourceTest.class));
        Sys.p(Sys.getResourcePath());

        Assert.assertTrue(true);
    }
}
