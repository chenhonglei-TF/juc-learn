package com.chenhl.juc.threads.forkjoinpool;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinTest {

    public static void main(String[] args) {
        // 使用ForkJoin框架计算5百亿之和
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();

        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, 50000000000L);

        Long sum = pool.invoke(task);

        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗费时间为：" + Duration.between(start, end).toMillis());//23218
    }


    //使用java8计算5百亿之和
    @Test
    public void testJ8Sum() {
        Instant start = Instant.now();
        Long sum = LongStream.rangeClosed(0L, 50000000000L).parallel().reduce(0L, Long::sum);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("使用java8耗时：" + Duration.between(start, end).toMillis());//13757
    }

    //使用for循环计算5百亿之和
    @Test
    public void testFor() {
        Instant start = Instant.now();

        long sum = 0L;
        for (long i = 0; i < 50000000000L; i++) {
            sum += i;
        }

        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("使用for循环耗时：" + Duration.between(start, end).toMillis());//17719
    }
}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private static final long serialVersionUID = -8764545855153264340L;

    private long start;
    private long end;

    private static final long THRESHOLD = 10000L;

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        if (length <= THRESHOLD) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }

            return sum;
        } else {
            long middle = (end + start) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();

            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }
}