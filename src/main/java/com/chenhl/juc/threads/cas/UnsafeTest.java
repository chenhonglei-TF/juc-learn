package com.chenhl.juc.threads.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {

    private static Unsafe unsafe;

    static {
        try {
            // 通过反射来获取该类的实例
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            System.out.println(unsafe);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Get Unsafe instance occur error "  + e);
        }
    }

    public static void main(String[] args) throws Exception {
        final Class<Target> clazz = Target.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        System.out.println("fieldName:fieldOffset");
        for (Field f : declaredFields) {
//            获取属性偏移量，可以通过这个偏移量给属性设置
            System.out.println(f.getName() + ": " + unsafe.objectFieldOffset(f));
        }
        Target target = new Target();
        Field intParam = clazz.getDeclaredField("intParam");
        int a = (Integer) intParam.get(target);
        System.out.println(intParam.getName() + "原始值: " + a);//3
        System.out.println(unsafe.compareAndSwapInt(target, 12, 3, 10));//true
        int b = (Integer) intParam.get(target);
        System.out.println(intParam.getName() + "修改后的值: " + b);//10

        System.out.println(unsafe.compareAndSwapInt(target, 12, 3, 10));//false

        System.out.println(unsafe.compareAndSwapObject(target, 24, null, "5"));//true

    }

}
class Target {
    int intParam = 3;//12
    long longParam;//16
    String strParam1;//24
    String strParam2;//28
}

/*
Unsafe：
1. Java最初被设计为一种安全的受控环境。尽管如此，HotSpot还是包含了一个后门sun.misc.Unsafe，提供了一些可以直接操控内存和线程的底层操作。
Unsafe被JDK广泛应用于java.nio和并发包等实现中，这个不安全的类提供了一个观察HotSpot JVM内部结构并且可以对其进行修改，但是不建议在生产环境中使用。

2. 可以通过native方法直接操作堆外内存，可以随意查看及修改JVM中运行时的数据结构，例如查看和修改对象的成员，Unsafe的操作粒度不是类，而是数据和地址。

3. 在java中可以通过反射来获取该类的实例

4. java不能直接访问操作系统底层，而是通过本地方法来访问。Unsafe类提供了硬件级别的原子操作，主要提供了以下功能：
    4.1 通过Unsafe类可以对内存进行操作；
    // 分配内存
    public native long allocateMemory(long var1);
    // 重新分配内存
    public native long reallocateMemory(long var1, long var3);
    // 释放内存
    public native void freeMemory(long var1);

    public native int addressSize();
    public native int pageSize();

    4.2 可以定位对象某字段的内存位置，也可以修改对象的字段值，即使它是私有的；
    public native int arrayBaseOffset(Class<?> var1);

    public native int arrayIndexScale(Class<?> var1);

    public native Object allocateInstance(Class<?> var1) throws InstantiationException;

    public final native boolean compareAndSwapObject(Object var1, long var2, Object var4, Object var5);

    public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);

    public final native boolean compareAndSwapLong(Object var1, long var2, long var4, long var6);

    4.3 线程的挂起和恢复 通过park方法挂起当前调用线程，通过unpark恢复一个线程（参数）,线程操作相关还有一个LockSupport类的封装。
    将一个线程进行挂起是通过park方法实现的，调用 park后，线程将一直阻塞直到超时或者中断等条件出现。unpark可以终止一个挂起的线程，使其恢复正常。
    整个并发框架中对线程的挂起操作被封装在 LockSupport类中，LockSupport类中有各种版本pack方法，但最终都调用了Unsafe.park()方法。

    4.4 CAS操作(乐观锁)
    Java并发包(java.util.concurrent)中大量使用了CAS操作,涉及到并发的地方都调用了sun.misc.Unsafe类方法进行CAS操作。
    在Unsafe中是通过compareAndSwapXXX方法实现的。

 */