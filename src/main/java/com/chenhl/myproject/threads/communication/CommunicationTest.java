package com.chenhl.myproject.threads.communication;

public class CommunicationTest {

    public static void main(String[] args) {
        Sample sample = new Sample();
        Thread t1 = new IncrThread(sample);
        Thread t3 = new IncrThread(sample);
        Thread t2 = new DecrThread(sample);
        Thread t4 = new DecrThread(sample);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

}
