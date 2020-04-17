package com.chenhl.nio;


import java.nio.ByteBuffer;

/**
 * 只读Buffer,我们随时可以将一个读写buffer改为只读buffer,但反之不行。
 * 在方法调用的时候，我们可以把一个buffer转换成一个只读buffer
 */
public class NioTest7 {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(10);

        System.out.println(buffer.getClass());

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        System.out.println(readOnlyBuffer.getClass());

        readOnlyBuffer.position(0);
        // 只读buffer不能修改
//        readOnlyBuffer.put((byte) 2);
    }
}