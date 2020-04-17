package com.chenhl.juc.threads.communication3;

/**
 * 下面的例子展示了，wait 方法返回后，需要重新获取监视器锁，才可以继续往下执行。
 */
public class WaitNotify {

    public static void main(String[] args) {
        Object object = new Object();

        Thread t1 = new Thread(() -> {

            synchronized (object) {
                System.out.println("线程1 获取到监视器锁");
                try {
                    object.wait();
                    System.out.println("线程1 恢复啦。我为什么这么久才恢复，因为notify方法虽然早就发生了，可是我还要获取锁才能继续执行。");
                } catch (InterruptedException e) {
                    System.out.println("线程1 wait方法抛出了InterruptedException异常");
                }
            }
        }, "线程1");

        Thread t2 = new Thread(() -> {
            synchronized (object) {
                System.out.println("线程2 拿到了监视器锁。为什么呢，因为线程1 在 wait 方法的时候会自动释放锁");
                System.out.println("线程2 执行 notify 操作");
                object.notify();
                System.out.println("线程2 执行完了 notify，先休息3秒再说。");
                try {
                    Thread.sleep(3000);
                    System.out.println("线程2 休息完啦。注意了，调sleep方法和wait方法不一样，不会释放监视器锁");
                } catch (InterruptedException e) {

                }
                System.out.println("线程2 休息够了，结束操作");
            }
        }, "线程2");

//        Thread t2 = new Thread(() -> {
//            try {
//                System.out.println("线程2没有获取到对象的监视器锁，就开始调用对象的wait方法，此时会抛出java.lang.IllegalMonitorStateException异常");
//                object.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "线程2");

        t1.start();
        t2.start();

        // 外线程
        System.out.println("3"); // 断点3

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("4"); // 断点4
    }


}
