package com.chenhl.myproject.threads.countdownlatch.impl;

import com.chenhl.myproject.threads.countdownlatch.AbstractDataRunnable;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: chenhonglei
 * @Date: 2018/12/5 14:58
 * @Description:
 * 人物模型
 */
public class PersonageData extends AbstractDataRunnable {
    public PersonageData(String name, CountDownLatch count) {
        super(name, count);
    }

    @Override
    public void handle() throws InterruptedException {
        //模拟加载时间，1秒
        Thread.sleep(1000);
    }
}
