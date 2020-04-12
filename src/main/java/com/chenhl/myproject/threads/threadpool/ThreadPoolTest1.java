package com.chenhl.myproject.threads.threadpool;

import java.util.concurrent.ExecutorService;

/*
    线程池的思想：

1.传统的线程与线程池

线程的创建与线程要执行的任务绑定到了一起，
ThreadFactory:
线程池其实就是将线程的创建和线程要执行的任务分开了。

Executor:

jdk1.5的Future

future.get()方法是一个鸡肋的方法，不知道什么时机去调用。
 */
public class ThreadPoolTest1 {

    public static void main(String[] args) {
        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor();
        executor.init();

        ExecutorService pool = executor.getCustomThreadPoolExecutor();

        for (int i = 1; i < 100; i++) {
            System.out.println("提交第" + i + "个任务");
            pool.execute(new MyRunnable());
        }


//        executor.destroy();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("running========" + Thread.currentThread().getName() + "==start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("running========" + Thread.currentThread().getName() + "==end");
    }
}
