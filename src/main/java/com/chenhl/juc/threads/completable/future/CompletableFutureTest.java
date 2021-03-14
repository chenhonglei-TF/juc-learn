package com.chenhl.juc.threads.completable.future;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class CompletableFutureTest {

    public static void main(String[] args) {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("complete ...");
//        test1();
        test2();
    }

    /*
        //runAsync 方法接收的是 Runnable 的实例，意味着它没有返回值
        CompletableFuture.runAsync(Runnable runnable);
        //让任务在指定的线程池中执行，不指定的话，通常任务是在 ForkJoinPool.commonPool() 线程池中执行的
        CompletableFuture.runAsync(Runnable runnable, Executor executor);

        //supplyAsync 方法对应的是有返回值的情况
        CompletableFuture.supplyAsync(Supplier<U> supplier);
        //让任务在指定的线程池中执行，不指定的话，通常任务是在 ForkJoinPool.commonPool() 线程池中执行的
        CompletableFuture.supplyAsync(Supplier<U> supplier, Executor executor)
     */

    //任务之间的顺序执行
    public static void test1(){
        // 任务 A 无返回值，所以对应的，resultA 其实是 null。
        CompletableFuture.runAsync(() -> {}).thenRun(() -> {});
        CompletableFuture.runAsync(() -> {}).thenAccept(resultA -> {});
        CompletableFuture.runAsync(() -> {}).thenApply(resultA -> "resultB");

        //thenRun(Runnable runnable)，任务 A 执行完执行 B，并且 B 不需要 A 的结果。
        CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {});
        // thenAccept(Consumer action)，任务 A 执行完执行 B，B 需要 A 的结果，但是任务 B 不返回值。
        CompletableFuture.supplyAsync(() -> "resultA").thenAccept(resultA -> {});
        // thenApply(Function fn)，任务 A 执行完执行 B，B 需要 A 的结果，同时任务 B 有返回值。
        String join = CompletableFuture.supplyAsync(() -> "resultA").thenApply(resultA -> resultA + " resultB").join();
        System.out.println(join);
    }


    //异常处理
    public static void test2(){
        //任务 A、B、C、D 依次执行，如果任务 A 抛出异常（当然上面的代码不会抛出异常），那么后面的任务都得不到执行。如果任务 C 抛出异常，那么任务 D 得不到执行。
        CompletableFuture.supplyAsync(() -> "resultA")
                .thenApply(resultA -> resultA + " resultB")
                .thenApply(resultB -> resultB + " resultC")
                .thenApply(resultC -> resultC + " resultD");

        System.out.println("------");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException();
        })
                .exceptionally(ex -> "errorResultA")
                .thenApply(resultA -> resultA + " resultB")
                .thenApply(resultB -> resultB + " resultC")
                .thenApply(resultC -> resultC + " resultD");

        System.out.println(future.join());


        System.out.println("===========");

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "resultA")
                .thenApply(resultA -> resultA + " resultB")
                // 任务 C 抛出异常
                .thenApply(resultB -> {throw new RuntimeException();})
                // 处理任务 C 的返回值或异常
                .handle(new BiFunction<Object, Throwable, Object>() {
                    @Override
                    public Object apply(Object re, Throwable throwable) {
                        if (throwable != null) {
                            return "errorResultC";
                        }
                        return re;
                    }
                })
                .thenApply(resultC -> resultC + " resultD");

        System.out.println(future1.join());
    }

    // 取两个任务的结果
    public static void test3(){
        CompletableFuture<String> cfA = CompletableFuture.supplyAsync(() -> "resultA");
        CompletableFuture<String> cfB = CompletableFuture.supplyAsync(() -> "resultB");

        // thenAcceptBoth 表示后续的处理不需要返回值
        cfA.thenAcceptBoth(cfB, (resultA, resultB) -> {});
        // thenCombine 表示需要返回值
        cfA.thenCombine(cfB, (resultA, resultB) -> "result A + B");
        cfA.runAfterBoth(cfB, () -> {});

        // 上面代码等同于下面
//        CompletableFuture<String> cfA = CompletableFuture.supplyAsync(() -> "resultA");

//        cfA.thenAcceptBoth(CompletableFuture.supplyAsync(() -> "resultB"),
//                (resultA, resultB) -> {});

    }

    // 取多个任务的结果
    public static void test4(){
        CompletableFuture cfA = CompletableFuture.supplyAsync(() -> "resultA");
        CompletableFuture cfB = CompletableFuture.supplyAsync(() -> 123);
        CompletableFuture cfC = CompletableFuture.supplyAsync(() -> "resultC");

        CompletableFuture<Void> future = CompletableFuture.allOf(cfA, cfB, cfC);
// 所以这里的 join() 将阻塞，直到所有的任务执行结束
        future.join();
    }

    //compose
    public static void test5(){
        CompletableFuture<String> result = CompletableFuture.supplyAsync(() -> {
            // 第一个实例的结果
            return "hello";
        }).thenCompose(resultA -> CompletableFuture.supplyAsync(() -> {
            // 把上一个实例的结果传递到这里
            return resultA + " world";
        })).thenCompose(resultAB -> CompletableFuture.supplyAsync(() -> {
            // 到这里大家应该很清楚了
            return resultAB + ", I'm robot";
        }));

        System.out.println(result.join()); // hello world, I'm robot
    }
}
