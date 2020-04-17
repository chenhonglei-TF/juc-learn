package com.chenhl.juc.threads.countdownlatch.impl;

import com.chenhl.juc.threads.countdownlatch.AbstractDataRunnable;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: chenhonglei
 * @Date: 2018/12/5 14:57
 * @Description:
 * 地图模型
 */
public class MapData extends AbstractDataRunnable {
    public MapData(String name, CountDownLatch count) {
        super(name, count);
    }

    @Override
    public void handle() throws InterruptedException {
        //模拟加载时间，3秒
        Thread.sleep(3000);
    }
}
