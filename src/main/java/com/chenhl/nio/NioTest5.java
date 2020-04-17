package com.chenhl.nio;

import java.nio.ByteBuffer;
/*

ByteBuffer类型化的get和put
 */
public class NioTest5 {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        byteBuffer.putInt(15);
        System.out.println(byteBuffer.position());
        byteBuffer.putLong(1000000L);
        System.out.println(byteBuffer.position());
        byteBuffer.putDouble(3.1415);
        System.out.println(byteBuffer.position());
        byteBuffer.putChar('你');
        System.out.println(byteBuffer.position());
        byteBuffer.putShort((short) 2);
        System.out.println(byteBuffer.position());
        byteBuffer.putChar('我');
        System.out.println(byteBuffer.position());

        byteBuffer.flip();
        System.out.println("===============");
        System.out.println(byteBuffer.position());

        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.position());
    }
}
