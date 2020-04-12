package com.chenhl.myproject.threads.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: chenhonglei
 * @Date: 2018/10/9 10:32
 * @Description:
 * 比如一个团队赛跑游戏，最后要计算团队赛跑的成绩，主线程计算最后成绩，要等到所有
 * 团队成员跑完，方可计算总成绩。
 *
 * 题目2：统计4个盘子的大小，统计完后交给第五个人汇总
 *
 * 总结：CountDownLatch强调的是一个线程（或多个）需要等待另外的n个线程干完某件事情之后才能继续执行。
 * main线程是裁判，4个Runner是跑步的。运动员先准备，裁判喊跑，运动员才开始跑（这是第一次同步，对应begin）。
 * 4个人谁跑到终点了，countdown一下，直到4个人全部到达，裁判喊停（这是第二次同步，对应end），然后算时间。
 */
public class CountDownLatchTest3 {

    public static void main(String[] args){

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(4);
        for (int i = 0; i < 4; ++i) // create and start threads
            new Thread(new Runner(startSignal, doneSignal), "线程"+i).start();

        // 这边插入一些代码，确保上面的每个线程先启动起来，才执行下面的代码。
        judgePrepare();//由于startSignal是1，上边的4个线程还不能执行


        System.out.println("judge say : run !");
        // 因为这里 N == 1，所以，只要调用一次，那么所有的 await 方法都可以通过
        startSignal.countDown();      // startSignal=0，线程可以开始执行了
        long startTime = System.currentTimeMillis();


        System.out.println("judge say : come on !");
        // 等待所有任务结束
        try {
            doneSignal.await();           // 判断上边4个是否执行完了，如果是，则允许下边的代码执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            System.out.println("judge say : all arrived !");
            System.out.println("spend time: " + (endTime - startTime));
        }
    }

    private static void judgePrepare() {
        try {
            Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }


}

class Runner implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    Runner(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " ready!");
            startSignal.await();  //startSignal的计数到0了吗？到0了，才能执行
            doWork();
            Thread.sleep((long) (Math.random() * 10000));
        } catch (InterruptedException ex) {
        }finally {
            System.out.println(Thread.currentThread().getName() + " arrived !");
            doneSignal.countDown();
        }
    }

    void doWork() {
        System.out.println(Thread.currentThread().getName() + ": 开始跑...");
    }
}

