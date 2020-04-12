package com.chenhl.myproject.threads.completable.future;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

// 使用CompletableFuture
public class CompletableFutureDemo3 {

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        System.out.println("开始获取价格，t1=" + t1);
        System.out.println("获取价格结果:"+new CompletableFutureDemo3().getPrices() +"，耗时=" + (System.currentTimeMillis() - t1));

    }

    private Set<Integer> getPrices(){
        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(new Task(123, prices));
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(new Task(456, prices));
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(new Task(789, prices));
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);
        try {
            allTasks.get(3, SECONDS);
        } catch (Exception e) {
//            e.printStackTrace();
        }
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
