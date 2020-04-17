package com.chenhl.juc.threads.threadbase;

/**
 * @description: volatile关键字只能保证变量的可见性和有序性，并不能保证变量的原子性
 *
 * happens-before原则（先行发生原则）：
 * 1.程序次序规则：一个线程内，按照代码顺序，书写在前面的操作先行发生于书写在后面的操作
 * 2.锁定规则：一个unlock操作先行发生于后面对同一个锁的lock操作
 * 3.volatile规则：对于一个volatile变量的写操作先行发生于后面对这个变量的读操作
 * 4.传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则操作A先行发生于操作C
 * 5.线程启动规则：Thread对象的start()方法先行发生于此线程的每一个动作
 * 6.线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生
 * 7.线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行
 * 8.对象终结规则：一个对象的初始化完成先行发生于它的finalize()方法的开始
 *
 * 备注：
 * 第一条：
 * 对于程序次序规则来说，我的理解就是一段程序代码的执行在单个线程中看起来是有序的。注意，虽然这条规则中提到“书写在前面的操作先行发生于书写在后面的操作”，
 * 这个应该是程序看起来执行的顺序是按照代码顺序执行的，因为虚拟机可能会对程序代码进行指令重排序。虽然进行重排序，但是最终执行的结果是与程序顺序执行的结果一致的，
 * 它只会对不存在数据依赖性的指令进行重排序。因此，在单个线程中，程序执行看起来是有序执行的，这一点要注意理解。事实上，这个规则是用来保证程序在单线程中执行结果的正确性，
 * 但无法保证程序在多线程中执行的正确性。
 *
 * 第二条：
 * 无论在单线程中还是多线程中，同一个锁如果出于被锁定的状态，那么必须先对锁进行了释放操作，后面才能继续进行lock操作。
 *
 * 第三条：
 * 直观地解释就是，如果一个线程先去写一个变量，然后一个线程去进行读取，那么写入操作肯定会先行发生于读操作。
 *
 * 第四条：
 * 实际上就是体现happens-before原则具备传递性。
 *
 * @author: TF019387 chenhonglei
 * @date: 2018/2/27 13:50
 */
public class VolatileTest {

    public volatile static int count = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int j = 0; j < 100; j++) {
                    count++;
                }
            }
            ).start();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("count= " + count);
    }
}


/*
因此，什么时候适合用volatile呢？
1.运行结果并不依赖变量的当前值，或者能够确保只有单一的线程修改变量的值。或者说，对变量的写操作不依赖于当前值
2.变量不需要与其他的状态变量共同参与不变约束。或者说，该变量没有包含在其他变量的不变式中。


什么是指令重排？
指令重排是指JVM在编译Java代码的时候，或者CPU在执行JVM字节码的时候，对现有的指令顺序进行重新排序。
处理器在进行重排序时是会考虑指令之间的数据依赖性，如果一个指令Instruction 2必须用到Instruction 1的结果，那么处理器会保证Instruction 1会在Instruction 2之前执行。
指令重排的目的是为了在不改变程序执行结果的前提下，优化程序的运行效率。需要注意的是，这里所说的不改变执行结果，指的是不改变单线程下的程序执行结果。
然而，指令重排是一把双刃剑，虽然优化了程序的执行效率，但是在某些情况下，会影响到多线程的执行结果。
比如：
boolean contextReady = false;
在线程A中执行:
context = loadContext();
contextReady = true;

在线程B中执行:
while( ! contextReady ){
   sleep(200);
}
doAfterContextReady (context);

以上程序看似没有问题。线程B循环等待上下文context的加载，一旦context加载完成，contextReady == true的时候，才执行doAfterContextReady 方法。
但是，如果线程A执行的代码发生了指令重排，初始化和contextReady的赋值交换了顺序：

boolean contextReady = false;
在线程A中执行:
contextReady = true;
context = loadContext();

在线程B中执行:
while( ! contextReady ){
   sleep(200);
}
doAfterContextReady (context);

这个时候，很可能context对象还没有加载完成，变量contextReady 已经为true，线程B直接跳出了循环等待，开始执行doAfterContextReady 方法，结果自然会出现错误。
需要注意的是，这里java代码的重排只是为了简单示意，真正的指令重排是在字节码指令的层面。


指令重排序的解决：内存屏障
内存屏障（Memory Barrier）是一种CPU指令，维基百科给出了如下定义：
A memory barrier, also known as a membar, memory fence or fence instruction, is a type of barrier instruction that causes a CPU or compiler to enforce an ordering constraint on memory operations issued before and after the barrier instruction. This typically means that operations issued prior to the barrier are guaranteed to be performed before operations issued after the barrier.
翻译结果如下：
内存屏障也称为内存栅栏或栅栏指令，是一种屏障指令，它使CPU或编译器对屏障指令之前和之后发出的内存操作执行一个排序约束。 这通常意味着在屏障之前发布的操作被保证在屏障之后发布的操作之前执行。

内存屏障共分为四种类型：

①LoadLoad屏障：
抽象场景：Load1; LoadLoad; Load2
Load1 和 Load2 代表两条读取指令。在Load2要读取的数据被访问前，保证Load1要读取的数据被读取完毕。

②StoreStore屏障：
抽象场景：Store1; StoreStore; Store2
Store1 和 Store2代表两条写入指令。在Store2写入执行前，保证Store1的写入操作对其它处理器可见

③LoadStore屏障：
抽象场景：Load1; LoadStore; Store2
在Store2被写入前，保证Load1要读取的数据被读取完毕。

④StoreLoad屏障：
抽象场景：Store1; StoreLoad; Load2
在Load2读取操作执行前，保证Store1的写入对所有处理器可见。StoreLoad屏障的开销是四种屏障中最大的。


内存屏障在Java代码中的使用
首先 volatile做的事情是，在一个变量被volatile修饰后，JVM会为我们做两件事：
1.在每个volatile写操作前插入StoreStore屏障，在写操作后插入StoreLoad屏障。
2.在每个volatile读操作前插入LoadLoad屏障，在读操作后插入LoadStore屏障。

比如刚才的例子：
boolean contextReady = false;
在线程A中执行:
context = loadContext();
contextReady = true;

我们给contextReady 增加volatile修饰符，会带来什么效果呢？
volatile boolean contextReady = false;
在线程A中执行:
context = loadContext();
-->插入StoreStore屏障
contextReady = true;
-->插入StoreLoad屏障

由于加入了StoreStore屏障，屏障上方的普通写入语句 context = loadContext()  和屏障下方的volatile写入语句 contextReady = true 无法交换顺序，从而成功阻止了指令重排序。

happens-before是JSR-133规范之一，内存屏障是CPU指令。可以简单的认为前者是最终目的，后者是实现手段。

总结：
volatile特性之一：
保证共享变量在线程之间的可见性。可见性的保证是基于CPU的内存屏障指令，被JSR-133抽象为happens-before原则。

volatile特性之二：
阻止编译时和运行时的指令重排。编译时JVM编译器遵循内存屏障的约束，运行时依靠CPU屏障指令来阻止重排。

补充：volatile除了保证可见性和阻止指令重排，还解决了long类型和double类型数据的8字节赋值问题。这个特性相对简单，本文就不详细描述了。

volatile保证可见性的通俗理解：
Volatile修饰的成员变量在每次被线程访问时，都强迫从共享内存中重读该成员变量的值。而且，当成员变量发生变化时，强迫线程将变化值回写到共享内存。
这样在任何时刻，两个不同的线程总是看到某个成员变量的值是相同的，更简单一点理解就是volatile修饰的变量值发生变化时对于另外的线程是可见的。


 */