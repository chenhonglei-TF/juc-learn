package com.chenhl.juc.threads.communication;

/**
 * Created by user on 2017/6/24.
 */
public class DecrThread extends Thread {

    private Sample sample;

    public DecrThread(Sample sample) {
        this.sample = sample;
    }

    @Override
    public void run() {
        for (int i =0; i<50; i++) {
            try {
                Thread.sleep((long)Math.random() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sample.decr();
        }
    }
}
