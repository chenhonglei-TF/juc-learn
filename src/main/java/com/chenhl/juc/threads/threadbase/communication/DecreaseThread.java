package com.chenhl.juc.threads.threadbase.communication;

public class DecreaseThread extends Thread {

    private Sample sample;

    public DecreaseThread(String name, Sample sample) {
        super(name);
        this.sample = sample;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.sample.decrease();
        }
    }
}
