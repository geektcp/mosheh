package com.geektcp.common.core.concurrent.thread.executor;

import org.junit.Assert;
import org.junit.Test;

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
