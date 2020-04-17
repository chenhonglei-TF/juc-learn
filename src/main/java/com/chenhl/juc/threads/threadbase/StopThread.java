package com.chenhl.juc.threads.threadbase;

public class StopThread implements Runnable {

    int count = 0;
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && count < 1000) {
            System.out.println("count=" + count++);
            // sleep can response for interrupt
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        Thread thread = new Thread(new StopThread());
        thread.start();
        Thread.sleep(5);
        thread.interrupt();
    }
}
