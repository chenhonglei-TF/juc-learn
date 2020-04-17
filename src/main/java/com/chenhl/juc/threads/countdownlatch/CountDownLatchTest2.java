package com.chenhl.juc.threads.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: chenhonglei
 * @Date: 2018/10/8 15:43
 * @Description:
 *
 * 假设我们有 N ( N > 0 ) 个任务，那么我们会用 N 来初始化一个 CountDownLatch，然后将这个 latch 的引用传递到各个线程中，在每个线程完成了任务后，
 * 调用 latch.countDown() 代表完成了一个任务，调用 latch.await() 的方法的线程会阻塞，直到所有的任务完成。
 */
public class CountDownLatchTest2 {

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(2);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignore) {
                }
                // 休息 5 秒后(模拟线程工作了 5 秒)，调用 countDown()
                latch.countDown();
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignore) {
                }
                // 休息 10 秒后(模拟线程工作了 10 秒)，调用 countDown()
                latch.countDown();
            }
        }, "t2");

        t1.start();
        t2.start();

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 阻塞，等待 state 减为 0
                    latch.await();
                    System.out.println("线程 t3 从 await 中返回了");
                } catch (InterruptedException e) {
                    System.out.println("线程 t3 await 被中断");
                    Thread.currentThread().interrupt();
                }
            }
        }, "t3");
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 阻塞，等待 state 减为 0
                    latch.await();
                    System.out.println("线程 t4 从 await 中返回了");
                } catch (InterruptedException e) {
                    System.out.println("线程 t4 await 被中断");
                    Thread.currentThread().interrupt();
                }
            }
        }, "t4");

        t3.start();
        t4.start();
    }
}
