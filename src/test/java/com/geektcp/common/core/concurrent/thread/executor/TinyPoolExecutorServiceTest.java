package com.geektcp.common.core.concurrent.thread.executor;

import com.geektcp.common.core.concurrent.thread.service.TinyExecutorService;
import com.geektcp.common.core.generator.IdGenerator;
import com.geektcp.common.core.system.Sys;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author geektcp on 2023/2/18 22:05.
 */
public class TinyPoolExecutorServiceTest {


    @Test
    public void test() {
        ThyExecutor thyExecutor = ThyExecutor.getInstance();
        Future<String> future = thyExecutor.submit(() -> (
                Sys.getResourceClassPath(TinyPoolExecutorServiceTest.class)));
        try {
            String result = future.get(5, TimeUnit.SECONDS);
            Sys.p(result);
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }
        thyExecutor.cleanup();
        Assert.assertTrue(true);
    }

    @Test
    public void test2() {
        ThyExecutor thyExecutor = new ThyExecutor(15);
        List<Future<Long>> result = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Future<Long> future = thyExecutor.submit(() -> (
                    getId()
            ));
            result.add(future);
        }

        result.forEach(future -> {
            try {
                Long ret = future.get(5, TimeUnit.SECONDS);
                Sys.p(ret);
            } catch (Exception e) {
                Sys.p(e.getMessage());
            }
        });

//        tinyExecutorService.cleanup();

        Sys.sleep(5);
        Assert.assertTrue(true);
    }

    @Test
    public void test3() {
        ThyExecutor thyExecutor = ThyExecutor.getInstance();
        thyExecutor.submit(() -> (
                Sys.getResourceClassPath(TinyPoolExecutorServiceTest.class)
        ));

//        tinyExecutorService.cleanup();
        Assert.assertTrue(true);
    }

    private Long getId() {
        long id = IdGenerator.getId();
        Sys.sleep(1000);
        Sys.p("id: {}, thread id: {}", id, Thread.currentThread().getId());
        return id;
    }

}
