package com.chenhl.juc.threads.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo3 {

    public static void main(String[] args) throws Exception{
        System.out.println("运动员有5秒的准备时间");
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = () -> {
                try {
                    System.out.println(no +"号运动员准备完毕，等待裁判员的发令枪。");
                    startSignal.await();

                    Thread.sleep((long) (Math.random() * 1000));
                    System.out.println(no +"号运动员完成了比赛。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    doneSignal.countDown();
                }
            };
            executorService.submit(runnable);
        }

        Thread.sleep(5000);
        System.out.println("5秒钟的时间已过，发令枪响，比赛开始！");
        startSignal.countDown();

        System.out.println("等待5个运动员都跑完……");
        doneSignal.await();


        System.out.println("所有人都跑完了，比赛结束。");
        executorService.shutdown();
    }
}
