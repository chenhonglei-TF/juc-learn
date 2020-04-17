package com.chenhl.juc.threads.concollections;

import java.util.HashMap;

public class HashMapTest {

    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put(null, null);
        map.put(null, null);
        System.out.println(map);

    }
}
