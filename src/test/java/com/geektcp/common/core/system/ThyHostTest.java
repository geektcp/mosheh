package com.geektcp.common.core.system;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author geektcp on 2023/2/13 20:36.
 */
public class ThyHostTest {

    @Test
    public void getIp(){
        Sys.p(ThyHost.getPublicIp());
        Sys.p(ThyHost.getPrivateIp());
        Sys.p(ThyHost.getHostName());
        Sys.p(ThyHost.getLoopbackIp());
        Assert.assertTrue(true);
    }


    @Test
    public void getIp2(){
        Sys.p(Sys.getPublicIp());
        Sys.p(Sys.getPrivateIp());
        Sys.p(Sys.getHostName());
        Sys.p(Sys.getLoopbackIp());
        Assert.assertTrue(true);
    }
}
