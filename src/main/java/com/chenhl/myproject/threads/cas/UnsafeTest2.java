package com.chenhl.myproject.threads.cas;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;

public class UnsafeTest2 {

    public static void main(String[] args) throws Exception{
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        Class<A> aClass = A.class;
        A a = (A) unsafe.allocateInstance(aClass);
        System.out.println(a);
    }

}

class A implements Serializable {
    private final int num;

    public A(int num) {
        System.out.println("Hello A");
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
