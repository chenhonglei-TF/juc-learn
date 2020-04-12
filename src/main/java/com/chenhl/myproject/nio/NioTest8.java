package com.chenhl.myproject.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 直接缓冲，DirectBuffer
 * DirectBuffer持有一个指向对外内存的引用，在进行io时，如果是HeapByteBuffer，
 * 操作系统需要先将jvm堆上的数据拷贝到堆外上的某一块区域，然后再进行io操作，
 * 如果是DirectByteBuffer的话，直接就进行操作了，无需一次拷贝，这种也叫做零拷贝。
 * HeapByteBuffer: 堆上的buffer
 * DirectByteBuffer: 也是堆上的buffer，不过持有一个指向堆外内存的引用
 */
public class NioTest8 {

    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("input2.txt");
        FileOutputStream outputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        //直接缓冲，DirectBuffer
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while (true) {

            buffer.clear();

            int read = inputChannel.read(buffer);

            System.out.println(read);

            if (-1 == read) {
                break;
            }

            buffer.flip();

            outputChannel.write(buffer);

        }

        inputChannel.close();
        outputChannel.close();
    }
}

