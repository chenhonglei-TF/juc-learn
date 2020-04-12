package com.chenhl.myproject.threads.countdownlatch.impl;

import com.chenhl.myproject.threads.countdownlatch.AbstractDataRunnable;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: chenhonglei
 * @Date: 2018/12/5 14:57
 * @Description:
 */
public class GoodsData  extends AbstractDataRunnable {

    public GoodsData(String name, CountDownLatch count) {
        super(name, count);
    }

    @Override
    public void handle() throws InterruptedException {
        //模拟加载时间，2.5秒
        Thread.sleep(2500);
    }
}