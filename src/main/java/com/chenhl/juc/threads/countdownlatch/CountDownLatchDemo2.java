package com.chenhl.juc.threads.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo2 {

    public static void main(String[] args) throws Exception{
        System.out.println("运动员有5秒的准备时间");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(no +"号运动员准备完毕，等待裁判员的发令枪。");
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        countDownLatch.countDown();
                    }
                }
            };
            executorService.submit(runnable);
        }

        Thread.sleep(5000);
        countDownLatch.await();
        System.out.println("5秒钟的时间已过，发令枪响，比赛开始！");
        countDownLatch.countDown();
        executorService.shutdown();
    }
}
