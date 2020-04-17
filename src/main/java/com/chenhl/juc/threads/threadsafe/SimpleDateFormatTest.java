package com.chenhl.juc.threads.threadsafe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @Auther: chenhonglei
 * @Date: 2018/12/5 12:23
 * @Description:
 *
 * 由于我们在声明SimpleDateFormat的时候，使用的是static定义的。那么这个SimpleDateFormat就是一个共享变量，随之，SimpleDateFormat中的calendar也就可以被多个线程访问到。
 *
 * 假设线程1刚刚执行完calendar.setTime把时间设置成2018-11-11，还没等执行完，线程2又执行了calendar.setTime把时间改成了2018-12-12。
 * 这时候线程1继续往下执行，拿到的calendar.getTime得到的时间就是线程2改过之后的。
 * 解决方法：
 * 1、使用局部变量
 * 2、枷锁 （示例代码中使用）
 * 3、使用ThreadLocal
 * 4、使用DateTimeFormatter
 */
public class SimpleDateFormatTest {

    /**
     * 定义一个全局的SimpleDateFormat
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //解决方法三：
//    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
//        @Override
//        protected SimpleDateFormat initialValue() {
//            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        }
//    };

    /**
     * 使用ThreadFactoryBuilder定义一个线程池
     */
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("demo-pool").build();

    private static ExecutorService pool = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 定义一个CountDownLatch，保证所有子线程执行完之后主线程再执行
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(100);

    public static void main(String[] args) throws Exception{
        //定义一个线程安全的HashSet
        Set<String> dates = Collections.synchronizedSet(new HashSet<String>());
        for (int i = 0; i < 100; i++) {
            //获取当前时间
            Calendar calendar = Calendar.getInstance();
            int finalI = i;
            pool.execute(() -> {
                //时间增加
                calendar.add(Calendar.DATE, finalI);
                //通过simpleDateFormat把时间转换成字符串
                synchronized (simpleDateFormat) {
                    String dateString = simpleDateFormat.format(calendar.getTime());
                    System.out.println(Thread.currentThread().getName() + " : " + dateString);
                    dates.add(dateString);
                }

                //解决方法三之用法
//                String dateString = simpleDateFormatThreadLocal.get().format(calendar.getTime());

                //把字符串放入Set中
                //countDown
                countDownLatch.countDown();
            });
        }
        //阻塞，直到countDown数量为0
        countDownLatch.await();
        //输出去重后的时间个数
        System.out.println(dates.size());
        pool.shutdown();
    }

}
