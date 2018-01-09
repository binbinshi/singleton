package com.kobin.singleton.threads;

import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by @author shibinbin on 2017/12/14.
 */

public class ABCConcurent {

    private static String result = "";

    private static String Method(String a) throws InterruptedException {
        System.out.println("A start "+Instant.now().toString());
        Thread.sleep(3000);
        return a;
    }
    private static  String BMethod() throws InterruptedException {
        System.out.println("b start "+Instant.now().toString());
        Thread.sleep(2000);
        return "b";
    }
    private static String CMethod() throws InterruptedException {
        System.out.println("c start "+Instant.now().toString());
        Thread.sleep(1000);
        return "c";
    }

    static  ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(4,4,600, TimeUnit
                .MILLISECONDS, new ArrayBlockingQueue(1024));
//        poolExecutor.submit(new MyThreadB());
//        poolExecutor.submit(new MyThreadC());


        CountDownLatch countDownLatch = new CountDownLatch(3);
        Executor e = poolExecutor;
        e.execute(new MyThread(countDownLatch, "A"));
        e.execute(new MyThreadB(countDownLatch, "B"));
        e.execute(new MyThreadC(countDownLatch, "C"));
        System.out.println(countDownLatch.getCount());
        countDownLatch.countDown();
        countDownLatch.await();
        System.out.println(result);
    }


   static class MyThread implements Runnable{

        private CountDownLatch countDownLatch = null;
        private String method;
        MyThread(CountDownLatch countDownLatch, String method){
            this.countDownLatch = countDownLatch;
            this.method = method;
       }

        @Override
        public void run() {
            try {
                this.countDownLatch.countDown();
                this.countDownLatch.await();
                lock.lock();
                while("".equals(result)){
                    result = Method(method);
                    System.out.println("a--con");
                    condition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Instant.now().toString() + "：a");
        }
    }

    static class MyThreadB implements Runnable{

        private CountDownLatch countDownLatch = null;
        private String method;
        MyThreadB(CountDownLatch countDownLatch, String method){
            this.countDownLatch = countDownLatch;
            this.method = method;
        }

        @Override
        public void run() {
            try {
                this.countDownLatch.countDown();
                this.countDownLatch.await();
                result = BMethod();
                lock.lock();
                while(!"".equals(result)){
                    System.out.println("b--con");
                    condition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Instant.now().toString() + "：b" );
        }
    }
    static class MyThreadC implements Runnable{

        private CountDownLatch countDownLatch = null;
        private String method;
        MyThreadC(CountDownLatch countDownLatch, String method){
            this.countDownLatch = countDownLatch;
            this.method = method;
        }

        @Override
        public void run() {
            try {
                this.countDownLatch.countDown();
                this.countDownLatch.await();
                result = CMethod();
                lock.lock();
                while(!"".equals(result)){
                    System.out.println("c--con");
                    condition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Instant.now().toString() + "：c");
        }
    }

}
