package com.chenhl.juc.threads.countdownlatch.impl;

import com.chenhl.juc.threads.countdownlatch.AbstractDataRunnable;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: chenhonglei
 * @Date: 2018/12/5 14:56
 * @Description:
 *
 * 背景
 */
public class BackGroundData  extends AbstractDataRunnable {

    public BackGroundData(String name, CountDownLatch count) {
        super(name, count);
    }

    @Override
    public void handle() throws InterruptedException {
        //模拟加载时间，2秒
        Thread.sleep(2000);
    }
}