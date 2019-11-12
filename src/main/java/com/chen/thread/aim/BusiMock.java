package com.chen.thread.aim;

public class BusiMock {

    public static void business(int sleepTime){
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
