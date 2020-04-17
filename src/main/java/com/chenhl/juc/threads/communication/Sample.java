package com.chenhl.juc.threads.communication;

/**
 * Created by user on 2017/6/24.
 */
public class Sample {

    private int number;

    public synchronized void incr() {

        while (number != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        number++;

        System.out.println(number);

        notifyAll();
    }

    public synchronized void decr() {

        while (0 == number) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        number--;

        System.out.println(number);

        notifyAll();
    }
}
