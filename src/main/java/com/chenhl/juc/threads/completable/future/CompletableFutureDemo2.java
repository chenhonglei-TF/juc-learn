package com.chenhl.juc.threads.completable.future;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;

// 使用CountDownLatch
public class CompletableFutureDemo2 {

    ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws InterruptedException {
        long t1 = System.currentTimeMillis();
        System.out.println("开始获取价格，t1=" + t1);
        System.out.println("获取价格结果:"+new CompletableFutureDemo2().getPrices() +"，耗时=" + (System.currentTimeMillis() - t1));

    }

    private Set<Integer> getPrices() throws InterruptedException{
        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());
        CountDownLatch countDownLatch = new CountDownLatch(3);
        threadPool.submit(new Task(123, prices, countDownLatch));
        threadPool.submit(new Task(456, prices, countDownLatch));
        threadPool.submit(new Task(789, prices, countDownLatch));
        countDownLatch.await(3, SECONDS);
//        Thread.sleep(3000);
        return prices;
    }

    private class Task implements Runnable{
        Integer productId;
        Set<Integer> prices;
        CountDownLatch countDownLatch;

        public Task(Integer productId, Set<Integer> prices,CountDownLatch countDownLatch) {
            this.productId = productId;
            this.prices = prices;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            int price = 0;
            try {
                Thread.sleep((long) (Math.random() * 4000));
                price = (int) (Math.random() * 4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            prices.add(price);
            countDownLatch.countDown();
        }
    }



}
