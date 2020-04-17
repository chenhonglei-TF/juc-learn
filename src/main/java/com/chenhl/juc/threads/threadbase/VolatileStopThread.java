package com.chenhl.juc.threads.threadbase;

public class VolatileStopThread implements Runnable{

    private volatile boolean canceled = false;

    @Override
    public void run() {
        int num =0;

        try {
            while (!canceled && num <= 1000000) {
                if (num % 10 == 0) {
                    System.out.println("num=" + num);
                }
                num++;
                Thread.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        VolatileStopThread r = new VolatileStopThread();
        Thread thread = new Thread(r);
        thread.start();
        Thread.sleep(1000);
        r.canceled = true;
        System.out.println(r.canceled);
    }
}
