package com.chenhl.myproject.threads.aqs;

/**
 * @Auther: chenhonglei
 * @Date: 2018/10/6 19:21
 * @Description:
 */
public class MyThreadTest {

    public static void main(String[] args) {

        final MyThreadDemo demo = new MyThreadDemo();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    demo.test();
                }
            }
        });
        t1.setName("A");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    demo.test();
                }
            }
        });
        t2.setName("B");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    demo.test();
                }
            }
        });
        t3.setName("C");

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    demo.test();
                }
            }
        });
        t4.setName("D");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
