package com.chenhl.juc.threads.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo1 {

    public static void main(String[] args) throws Exception{
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println(no +"号运动员完成了比赛。");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        countDownLatch.countDown();
                    }
                }
            };
            executorService.submit(runnable);
        }

        System.out.println("等待5个运动员都跑完……");
        countDownLatch.await();
        System.out.println("所有人都跑完了，比赛结束。");
        executorService.shutdown();
    }
}
