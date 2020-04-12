package com.chenhl.myproject.threads.futuretask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureTaskTest2 {

    public static void main(String[] args) {

        List<FutureTask<Integer>> taskList = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i=0; i<10; i++) {
            FutureTask<Integer> task = new FutureTask<Integer>(new ComputeTask(i, ""+i));
            taskList.add(task);

            executorService.submit(task);
        }

        System.out.println("所有计算任务提交完毕， 主线程接着干其他事情");

        Integer totalResult = 0;

        for (int i=0; i<taskList.size(); i++) {
            try {
                FutureTask<Integer> ft = taskList.get(i);
                System.out.println("第"+i+"次的结果："+ft.get());
                totalResult += ft.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        System.out.println("多任务计算后的结果：" + totalResult);
    }
}
