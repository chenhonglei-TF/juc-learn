package com.chenhl.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * Selector
 *
 * epoll原理
 * select，poll，epoll都是IO多路复用的机制。I/O多路复用就是通过一种机制，一个进程可以监视多个描述符，一旦某个描述符就绪（一般是读就绪或者写就绪），
 * 能够通知程序进行相应的读写操作。
 * 但select，poll，epoll本质上都是同步I/O，因为他们都需要在读写事件就绪后自己负责进行读写，也就是说这个读写过程是阻塞的，
 * 而异步I/O则无需自己负责进行读写，异步I/O的实现会负责把数据从内核拷贝到用户空间。
 *
 * epoll是Linux下的一种IO多路复用技术，可以非常高效的处理数以百万计的socket句柄。
 *
 * 在 select/poll中，进程只有在调用一定的方法后，内核才对所有监视的文件描述符进行扫描，而epoll事先通过epoll_ctl()来注册一个文件描述符，
 * 一旦基于某个文件描述符就绪时，内核会采用类似callback的回调机制，迅速激活这个文件描述符，当进程调用epoll_wait() 时便得到通知。
 * (此处去掉了遍历文件描述符，而是通过监听回调的的机制。这正是epoll的魅力所在。)
 * 如果没有大量的idle -connection或者dead-connection，epoll的效率并不会比select/poll高很多，但是当遇到大量的idle- connection，
 * 就会发现epoll的效率大大高于select/poll。
 *
 *
 * 注意：linux下Selector底层是通过epoll来实现的，当创建好epoll句柄后，它就会占用一个fd值，在linux下如果查看/proc/进程id/fd/，是能够看到这个fd的，
 * 所以在使用完epoll后，必须调用close()关闭，否则可能导致fd被耗尽。
 *
 *
 * 先看看使用c封装的3个epoll系统调用:
 *
 * 1. int epoll_create(int size)
 * epoll_create建立一个epoll对象。参数size是内核保证能够正确处理的最大句柄数，多于这个最大数时内核可不保证效果。
 * 2. int epoll_ctl(int epfd, int op, int fd, struct epoll_event *event)
 * epoll_ctl可以操作epoll_create创建的epoll，如将socket句柄加入到epoll中让其监控，或把epoll正在监控的某个socket句柄移出epoll。
 * 3. int epoll_wait(int epfd, struct epoll_event *events,int maxevents, int timeout)
 * epoll_wait在调用时，在给定的timeout时间内，所监控的句柄中有事件发生时，就返回用户态的进程。
 *
 *
 *
 * 1. epoll初始化时，会向内核注册一个文件系统，用于存储被监控的句柄文件，调用epoll_create时，会在这个文件系统中创建一个file节点。
 * 同时epoll会开辟自己的内核高速缓存区，以红黑树的结构保存句柄，以支持快速的查找、插入、删除。还会再建立一个list链表，用于存储准备就绪的事件。
 * 2. 当执行epoll_ctl时，除了把socket句柄放到epoll文件系统里file对象对应的红黑树上之外，还会给内核中断处理程序注册一个回调函数，
 * 告诉内核，如果这个句柄的中断到了，就把它放到准备就绪list链表里。所以，当一个socket上有数据到了，内核在把网卡上的数据copy到内核中后，
 * 就把socket插入到就绪链表里。
 * 3. 当epoll_wait调用时，仅仅观察就绪链表里有没有数据，如果有数据就返回，否则就sleep，超时时立刻返回
 *
 * 最后顺便说下在Linux系统中JDK NIO使用的是 LT ，而Netty epoll使用的是 ET。
 *
 *
 *
 */
public class NioTest12 {

    public static void main(String[] args) throws Exception{

        int ports[] = new int[5];

        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        Selector selector = Selector.open();

        System.out.println(SelectorProvider.provider().getClass());
        System.out.println(sun.nio.ch.DefaultSelectorProvider.create().getClass());

        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("listen port: " + ports[i] );
        }

        while (true) {
            int numbers = selector.select();
            System.out.println("numbers: " + numbers);

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys: " + selectionKeys);

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);

                    iterator.remove();

                    System.out.println("from client connection: " + socketChannel);
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int byteRead = 0 ;

                    while (true) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        byteBuffer.clear();
                        int read = socketChannel.read(byteBuffer);
                        if (read <= 0) {
                            break;
                        }

                        byteBuffer.flip();

                        socketChannel.write(byteBuffer);
                        byteRead += read;
                    }

                    System.out.println("had read: " + byteRead + ", from: " + socketChannel);

                    iterator.remove();
                }
            }
        }
    }
}
