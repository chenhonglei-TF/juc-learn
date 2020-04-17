package com.chenhl.juc.threads.completable.future;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 使用线程池
public class CompletableFutureDemo {

    ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws InterruptedException {
        long t1 = System.currentTimeMillis();
        System.out.println("开始获取价格，t1=" + t1);
        System.out.println("获取价格结果:"+new CompletableFutureDemo().getPrices() +"，耗时=" + (System.currentTimeMillis() - t1));

    }

    private Set<Integer> getPrices() throws InterruptedException{

        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());
        threadPool.submit(new Task(123, prices));
        threadPool.submit(new Task(456, prices));
        threadPool.submit(new Task(789, prices));
        Thread.sleep(3000);
        return prices;
    }

    private class Task implements Runnable{
        Integer productId;
        Set<Integer> prices;

        public Task(Integer productId, Set<Integer> prices) {
            this.productId = productId;
            this.prices = prices;
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
        }
    }



}
