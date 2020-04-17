package com.chenhl.juc.threads.futuretask;

import java.util.concurrent.Callable;

public class ComputeTask implements Callable<Integer> {

    private Integer result = 0;

    private String taskName = "";

    public ComputeTask(Integer result, String taskName) {
        this.result = result;
        this.taskName = taskName;
        System.out.println("生成子线程计算任务：" + taskName);
    }

    public String getTaskName() {
        return taskName;
    }

    @Override
    public Integer call() throws Exception {
        for (int i=0; i<100; i++) {
            result += i;
        }
        Thread.sleep(6000);
        System.out.println("子线程计算任务：" + taskName + "执行完成");
        if (taskName.equals("9")){
            throw  new RuntimeException("paoyichangle");
        }
        return result;
    }
}
