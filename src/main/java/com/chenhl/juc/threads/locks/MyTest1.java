package com.chenhl.juc.threads.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: chenhonglei
 * @Date: 2019/11/2 12:27
 * @Description:
 */
public class MyTest1 {

    private Lock lock = new ReentrantLock();


    public void myMethod1() {
        try {
            lock.lock();
            System.out.println("myMethod1 invoked");

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
//            lock.unlock();
        }
    }

    public void myMethod2() {
        try {
            lock.lock();
            System.out.println("myMethod2 invoked");

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MyTest1 myTest1 = new MyTest1();

        Thread t1 = new MyThread1(myTest1);
        Thread t2 = new MyThread2(myTest1);

        t1.start();
        t2.start();
    }
}

class MyThread1 extends Thread{

    private MyTest1 myTest1;

    public MyThread1(MyTest1 myTest1) {
        this.myTest1 = myTest1;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            myTest1.myMethod1();
        }


    }
}


class MyThread2 extends Thread{

    private MyTest1 myTest1;

    public MyThread2(MyTest1 myTest1) {
        this.myTest1 = myTest1;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            myTest1.myMethod2();
        }


    }
}
