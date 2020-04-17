package com.chenhl.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * 文件通道用法：
 * 通过NIO读取文件涉及3个步骤：
 * 1. 从FileInputStream获取到FileChannel对象
 * 2. 创建Buffer。
 * 3. 将数据从Channel读取到Buffer中。
 *
 */
public class NioTest4 {

    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {

            // 特别注意这行代码如果要是被注释掉了，不管有没有到达流的末尾，都不可能再读到字节了，所以返回的是0
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
