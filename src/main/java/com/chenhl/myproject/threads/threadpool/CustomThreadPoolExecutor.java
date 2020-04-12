package com.chenhl.myproject.threads.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadPoolExecutor {

    private ThreadPoolExecutor executor = null;

    public void init() {
        executor = new ThreadPoolExecutor(
                10, //核心线程池大小
                30, //最大线程池大小
                30, // 线程池中超过corePoolSize数目的空闲线程最大存活时间
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(10),// 10容量的阻塞队列
                new CustomThreadFactory(), // 定制的线程工厂
                new CustomRejectedExecutionHandler()//当提交任务数超过maxmumPoolSize+workQueue之和时,
                                                    //  即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
                                                   //任务会交给RejectedExecutionHandler来处理
        );
    }

    public void destroy() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public ExecutorService getCustomThreadPoolExecutor() {
        return this.executor;
    }

    private class CustomThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = CustomThreadPoolExecutor.class.getSimpleName() + count.addAndGet(1);
            System.out.println(threadName);
            t.setName(threadName);
            return t;
        }
    }

    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // 记录异常
            // 报警处理
            System.out.println("error ---------------");
        }
    }
}
