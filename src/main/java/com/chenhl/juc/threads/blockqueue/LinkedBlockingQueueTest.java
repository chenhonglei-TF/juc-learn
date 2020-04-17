package com.chenhl.juc.threads.blockqueue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueTest {

    public static void main(String[] args) {
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(2);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1 run");
                for (int i=0;i<20;i++) {
                    try {
                        System.out.println("putting..");
                        linkedBlockingQueue.put("A");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) ;

        t1.start();
    }



}
