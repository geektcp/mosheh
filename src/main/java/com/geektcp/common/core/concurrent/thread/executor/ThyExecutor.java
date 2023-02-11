package com.geektcp.common.core.concurrent.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author geektcp on 2023/2/6 22:44.
 */
public class ThyExecutor {

    private static ExecutorService executorService;

    private ThyExecutor(){

    }


    public static void sss(){

        executorService = Executors.newFixedThreadPool(3);
        executorService = Executors.newSingleThreadExecutor();

        executorService = Executors.newFixedThreadPool(10);
        executorService = Executors.newScheduledThreadPool(10);
        executorService = Executors.newCachedThreadPool();


        executorService = Executors.newWorkStealingPool();


    }
}
