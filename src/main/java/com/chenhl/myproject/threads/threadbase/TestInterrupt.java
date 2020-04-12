package com.chenhl.myproject.threads.threadbase;

/**
 * 运行很长时间，但是非阻塞的方法，interrupt方法并不一定能中断线程
 */
public class TestInterrupt {

    public static void main(String[] args) {
        Thread t = new MyThread();
        t.start();
        t.interrupt();
        System.out.println("已调用线程的interrupt方法");
    }


    static class MyThread extends Thread {
        public void run() {
            int num = longTimeRunningNonInterruptMethod(2, 0);
            System.out.println("长时间任务运行结束,num=" + num);
            System.out.println("线程的中断状态:" + Thread.interrupted());
        }
        private static int longTimeRunningNonInterruptMethod(int count, int initNum) {
            for(int i=0; i<count; i++) {
                for(int j=0; j<Integer.MAX_VALUE; j++) {
                    initNum ++;
                }
            }
            return initNum;
        }
    }
}
