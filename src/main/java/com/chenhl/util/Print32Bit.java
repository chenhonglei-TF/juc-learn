package com.chenhl.util;

public class Print32Bit {

    public static void printBit(int i){
        for (int j = 31; j >=0; j--) {
            System.out.print((i & (1<<j))==0?"0":"1");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        printBit(2);
    }
}
