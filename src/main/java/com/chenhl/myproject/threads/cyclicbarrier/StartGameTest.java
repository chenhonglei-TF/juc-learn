package com.chenhl.myproject.threads.cyclicbarrier;

import java.io.IOException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Auther: chenhonglei
 * @Date: 2018/12/5 14:50
 * @Description:
 */
public class StartGameTest {

    public static void main(String[] args) throws IOException {
//        CyclicBarrier barrier = new CyclicBarrier(5);
        CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            try {
                System.out.println("阶段完成，等待2秒...");
                Thread.sleep(2000);
                System.out.println("进入下个阶段...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        Thread player1 = new Thread(new StartGame("1",barrier));
        Thread player2 = new Thread(new StartGame("2",barrier));
        Thread player3 = new Thread(new StartGame("3",barrier));
        Thread player4 = new Thread(new StartGame("4",barrier));
        Thread player5 = new Thread(new StartGame("5",barrier));

        player1.start();
        player2.start();
        player3.start();
        player4.start();
        player5.start();

        System.in.read();
    }
}
