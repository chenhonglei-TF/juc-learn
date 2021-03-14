package com.chenhl.juc.threads.futuretask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTaskTest3 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        int num = 20;
        List<Future<String>> results = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            results.add(executorService.submit(new Child()));
        }
        System.out.println("==========");
        for (Future<String> result : results) {
            try {
                String s = result.get();
                System.out.println(s);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        System.out.println("全部执行完了");
        executorService.shutdown();
    }


    private static class Child implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(new Random().nextInt(1000));
            return Thread.currentThread().getName();
        }
    }
}
