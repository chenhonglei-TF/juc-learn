package com.chenhl.juc.threads.threadlocal;

/**
 * ThreadLocal的作用:提供线程内的局部变量，这种变量在线程的生命周期内起作用，
 * 减少同一个线程内多个函数或者组件之间一些公共变量的传递的复杂度
 */
public class ThreadLocalTest1 {

    private static final ThreadLocal<Integer> tl = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    public static void main(String[] args) {

        for (int i=0; i<2; i++) {
            new Thread(new Sample(i)).start();
        }
    }

    static class Sample implements Runnable {
        private int index;
        public Sample(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            System.out.println("线程" + index + "的初始化值：" + tl.get());
            for (int i=0; i<3; i++) {
                tl.set(tl.get() + i);
            }
            System.out.println("线程" + index + "的累加值：" + tl.get());
        }
    }
}
