package com.chenhl.juc.threads.threadbase;

public class VolatileTest2 {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        Thread t = new Thread(td);
        t.start();

        while (true) {
//            synchronized (td) {
//                if (td.isFlag()) {
//                    System.out.println("===========");
//                    break;
//                }
//            }

            if (td.isFlag()) {
                System.out.println("===========");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable {

    private volatile boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;

        System.out.println("flag= " + flag);
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
