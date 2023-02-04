package com.geektcp.common.core.concurrent.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author geektcp on 2023/2/2 0:16.
 */
public interface Lock {

    void lock();

    void unlock();

    boolean tryLock();

    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    void lockInterrupt() throws InterruptedException;

}
