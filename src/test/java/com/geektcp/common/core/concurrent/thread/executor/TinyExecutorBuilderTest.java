package com.geektcp.common.core.concurrent.thread.executor;

import com.geektcp.common.core.system.Sys;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

/**
 * @author geektcp on 2023/2/18 23:22.
 */
public class TinyExecutorBuilderTest {

    @Test
    public void test3() {
        TinyExecutorBuilder.newTinyThreadPool(TinyExecutorBuilder.newSingleThreadExecutor());
        Assert.assertTrue(true);
    }

}
