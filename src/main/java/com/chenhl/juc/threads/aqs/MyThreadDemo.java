package com.chenhl.juc.threads.aqs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: chenhonglei
 * @Date: 2018/10/6 19:19
 * @Description:
 */
public class MyThreadDemo {

    private ReentrantLock lock = new ReentrantLock(true);

    public void test() {
        lock.lock();
        try {
            System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁");
            System.out.println("线程" + Thread.currentThread().getName() + "执行业务代码");
        } finally {
            System.out.println("线程" + Thread.currentThread().getName() + "开始释放锁");
            lock.unlock();
            System.out.println("线程" + Thread.currentThread().getName() + "完成释放锁");
        }
    }

}
