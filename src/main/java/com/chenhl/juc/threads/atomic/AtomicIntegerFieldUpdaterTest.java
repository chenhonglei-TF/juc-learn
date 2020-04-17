package com.chenhl.juc.threads.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/*
    AtomicIntegerFieldUpdater要点总结：
    1.更新器更新的必须是int类型的变量，不能是其包装类型。
    2.更新器所更新的变量必须是volatile类型的变量。
    3.变量不能是static的，必须是实例变量。因为Unsafe.objectFieldOffset()方法不支持静态变量（CAS操作本质上就通过对象实例的偏移量来直接进行赋值）。
    4.更新器只能修改它可见范围内的变量，因为更新器是通过反射来得到这个变量，如果变量不可见就会报错。

    如果要更新的变量是包装类型，那么就可以使用AtomicReferenceFieldUpdater来进行更新。

 */
public class AtomicIntegerFieldUpdaterTest {

    public static void main(String[] args) {
//        Person person = new Person();
//
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(person.age++);
//            }).start();
//        }

        Person person = new Person();
        AtomicIntegerFieldUpdater<Person> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(atomicIntegerFieldUpdater.getAndIncrement(person));
            }).start();
        }
    }
}

class Person {

//    int age = 1;
    volatile int age = 1;
}
