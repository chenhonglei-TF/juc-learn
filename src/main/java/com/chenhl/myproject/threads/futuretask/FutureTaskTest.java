package com.chenhl.myproject.threads.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    public static void main(String[] args) {

        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int i=0;
                for (; i< 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " 的循环变量i的值：" + i);
                }
                return i;
            }
        });

        for (int i=0; i<100; i++) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量i的值：" + i);

            if (i==50) {
                new Thread(task, "有返回值的线程").start();
            }
        }

        try {
            System.out.println("子线程的返回值: " + task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
