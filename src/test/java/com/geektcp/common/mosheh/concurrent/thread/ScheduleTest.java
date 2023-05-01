package com.geektcp.common.mosheh.concurrent.thread;

import com.geektcp.common.mosheh.system.Sys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author Mr.Tang on 2023/4/23 10:47.
 */
public class ScheduleTest {

    public static void main(String[] args) {
        test2();
    }

    private static void test1() {
        Sys.p("start");
        Queue<String> queue = new ConcurrentLinkedDeque<>(
                Arrays.asList("number 1", "number 2", "number 3", "number 4")
        );

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        for (int i = 0; i < queue.size(); i++) {
            ScheduledFuture<String> future = pool.schedule(new Callable<String>() {
                @Override
                public String call()  {
                    Sys.p(Thread.currentThread().getName() + " " + System.currentTimeMillis() + " current job:" + queue.poll());
                    Sys.sleep(2000);
                    return "callSchedule";
                }
            }, 1, TimeUnit.SECONDS);
        }
    }

    private static void test2() {
        Sys.p("start");
        Queue<String> queue = new ConcurrentLinkedDeque<>(
                Arrays.asList("number 1", "number 2", "number 3", "number 4")
        );

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

        pool.scheduleAtFixedRate(() -> {
            int num = 0;
            Sys.p(Thread.currentThread().getName() + " " + System.currentTimeMillis() + " current job:" + queue.poll());
            Sys.sleep(2000);
            num++;
            Sys.p("num: {}", num);
            if (num > 10) {
                pool.shutdown();
            }
        }, 1, 2, TimeUnit.SECONDS);

    }
}
