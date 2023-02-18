package com.geektcp.common.core.concurrent.thread.executor;

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
public class TinyExecutorServiceTest {


    @Test
    public void test() {
        TinyExecutorService tinyExecutorService = TinyExecutorService.getInstance();
        Future<String> future = tinyExecutorService.submit(() -> (
                Sys.getResourceClassPath(TinyExecutorServiceTest.class)));
        try {
            String result = future.get(5, TimeUnit.SECONDS);
            Sys.p(result);
        } catch (Exception e) {
            Sys.p(e.getMessage());
        }
        tinyExecutorService.cleanup();
        Assert.assertTrue(true);
    }

    @Test
    public void test2() {
        TinyExecutorService tinyExecutorService = new TinyExecutorService(15);
        List<Future<Long>> result = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Future<Long> future = tinyExecutorService.submit(() -> (
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
        TinyExecutorService tinyExecutorService = TinyExecutorService.getInstance();
        tinyExecutorService.submit(() -> (
                Sys.getResourceClassPath(TinyExecutorServiceTest.class)
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
