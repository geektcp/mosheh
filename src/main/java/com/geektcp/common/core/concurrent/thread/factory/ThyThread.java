package com.geektcp.common.core.concurrent.thread.factory;


/**
 * @author geektcp on 2023/2/6 23:04.
 */
public class ThyThread extends Thread {

    private boolean isRunning;


    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        this.isRunning = true;
        innerRun();
        this.isRunning = false;
    }

    private void innerRun() {
        // do something

    }

}
