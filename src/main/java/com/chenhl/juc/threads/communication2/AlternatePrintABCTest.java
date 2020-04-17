package com.chenhl.juc.threads.communication2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交替打印ABC
 * <p>
 * 开启3个线程，这三个线程的ID分别为A、B、C,每个线程将自己的ID在屏幕上打印10遍，要求输出的结果必须按顺序显示
 * 如ABCABCABC...... 一次递归
 */
public class AlternatePrintABCTest {

    public static void main(String[] args) {
        AlternatePrint alternatePrint = new AlternatePrint();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                alternatePrint.loopPrintA(i);
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                alternatePrint.loopPrintB(i);
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                alternatePrint.loopPrintC(i);
            }
        }, "C").start();
    }
}

class AlternatePrint {
    private int num = 1;

    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();


    public void loopPrintA(int totalLoop) {
        lock.lock();

        try {
            //1. 判断
            if (num != 1) {
                conditionA.await();
            }

            //2. 打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            num = 2;
            conditionB.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopPrintB(int totalLoop) {
        lock.lock();

        try {
            //1. 判断
            if (num != 2) {
                conditionB.await();
            }

            //2. 打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            num = 3;
            conditionC.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void loopPrintC(int totalLoop) {
        lock.lock();

        try {
            //1. 判断
            if (num != 3) {
                conditionC.await();
            }

            //2. 打印
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + totalLoop);
            }

            //3. 唤醒
            num = 1;
            conditionA.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
