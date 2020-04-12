package com.chenhl.myproject.threads.concollections;

import java.util.Hashtable;

/**
 *  key, value 都不能为空
 *
 * 1. 锁整个表，也就是当并行的时候，hashtable会将并行转换为串行，因此效率低。
 * 2. 遇到符合操作时，也是线程不安全的。？？
 *
 */
public class HashtableTest {

    public static void main(String[] args) {
        Hashtable table = new Hashtable();
        table.put(null, "hello");
    }
}
