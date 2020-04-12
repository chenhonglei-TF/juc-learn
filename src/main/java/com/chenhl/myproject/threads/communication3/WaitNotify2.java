package com.chenhl.myproject.threads.communication3;

/**
 * 下面的这个例子也很清楚，如果线程调用 wait 方法，当此线程被中断的时候，wait 方法会返回，然后重新获取监视器锁，然后抛出 InterruptedException 异常。
 */
public class WaitNotify2 {

    public static void main(String[] args) {
        Object object = new Object();

        Thread t1 = new Thread(() -> {

            synchronized (object) {
                System.out.println("线程1 获取到监视器锁");
                try {
                    object.wait();
                    System.out.println("线程1 恢复啦。我为什么这么久才恢复，因为notify方法虽然早就发生了，可是我还要获取锁才能继续执行。");
                } catch (InterruptedException e) {
                    System.out.println("线程1 wait方法抛出了InterruptedException异常，即使是异常，我也是要获取到监视器锁了才会抛出");
                }
            }
        }, "线程1");
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (object) {
                System.out.println("线程2 拿到了监视器锁。为什么呢，因为线程1 在 wait 方法的时候会自动释放锁");
                System.out.println("线程2 设置线程1 中断");
                t1.interrupt();
                System.out.println("线程2 执行完了 中断，先休息3秒再说。");
                try {
                    Thread.sleep(3000);
                    System.out.println("线程2 休息完啦。注意了，调sleep方法和wait方法不一样，不会释放监视器锁");
                } catch (InterruptedException e) {

                }
                System.out.println("线程2 休息够了，结束操作");
            }
        }, "线程2");
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
