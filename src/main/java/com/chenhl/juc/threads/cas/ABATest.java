package com.chenhl.juc.threads.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABATest {

    private static AtomicInteger atomicInteger = new AtomicInteger(100);
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 0);

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            atomicInteger.compareAndSet(100, 101);
            atomicInteger.compareAndSet(101, 100);
        });

        Thread thread1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean bool = atomicInteger.compareAndSet(100, 101);
            System.out.println(bool);//true
        });

        thread.start();
        thread1.start();
        thread.join();
        thread1.join();

        Thread t3 = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicStampedReference.compareAndSet(100, 101,
                        atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
                atomicStampedReference.compareAndSet(101, 100,
                        atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            }
        });

        Thread t4 = new Thread(new Runnable() {
            public void run() {
                int stamp = atomicStampedReference.getStamp();
                System.out.println("before sleep: stamp=" + stamp);

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("after sleep: stamp=" + stamp);
                System.out.println("当前 stamp: " + atomicStampedReference.getStamp());
                boolean bool = atomicStampedReference.compareAndSet(100, 101,
                        stamp, stamp + 1);

                System.out.println(bool);//false
            }
        });

        t3.start();
        t4.start();
    }

}
/*
CAS操作(乐观锁):
    CAS 操作包含三个操作数 —— 内存位置（V）、预期原值（A）和新值(B)。
    如果内存位置的值与预期原值相匹配，那么处理器会自动将该位置值更新为新值。否则，处理器不做任何操作。
    无论哪种情况，它都会在 CAS 指令之前返回该位置的值。CAS 有效地说明了“我认为位置 V 应该包含值 A；如果包含该值，则将 B 放到这个位置；否则，不要更改该位置，只告诉我这个位置现在的值即可。”


CAS实际上是利用处理器提供的CMPXCHG指令实现的，而处理器执行CMPXCHG指令是一个原子性操作。
 */