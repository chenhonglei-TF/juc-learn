package com.chenhl.myproject.threads.concollections;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 锁分段机制
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {

        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

        map.put(1, "hello");

        String s = map.get(1);

        System.out.println(s);

        System.out.println(map.size());
    }
}
