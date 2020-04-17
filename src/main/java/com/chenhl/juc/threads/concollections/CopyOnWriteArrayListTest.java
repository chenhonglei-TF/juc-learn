package com.chenhl.juc.threads.concollections;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/*
    CopyOnWriteArrayList 写入并复制

    添加操作多时，效率低，因为每次添加时都会进行复制，开销比较大。并发迭代操作多时可以选择
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(td).start();
        }
    }

}

class ThreadDemo implements Runnable {

    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }
    @Override
    public void run() {

        Iterator<String> iterator = list.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            list.add("AA");
        }
    }
}
