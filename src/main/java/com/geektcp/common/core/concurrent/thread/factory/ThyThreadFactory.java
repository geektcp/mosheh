package com.geektcp.common.core.concurrent.thread.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author geektcp on 2023/2/6 23:04.
 */
public class ThyThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger count = new AtomicInteger(1);
    private final String prefix;

    public ThyThreadFactory(String namePrefix) {
        this.prefix = namePrefix;
        SecurityManager s = System.getSecurityManager();
        this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, prefix + "_" + count.getAndIncrement(), 0);
        t.setDaemon(true);
        return t;
    }

    public ThreadGroup getThreadGroup() {
        return this.group;
    }

}
