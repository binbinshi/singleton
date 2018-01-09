package com.kobin.singleton.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by @author shibinbin on 2017/12/12.
 */

public class HitAndRun implements Runnable{
    private int tnum = 1;
    private ReentrantLock lock = new ReentrantLock();

    private Condition redCondition = lock.newCondition();
    private Condition greenCondition = lock.newCondition();

    public static void main(String[] args) {
        new HitAndRun().run();
    }

    @Override
    public void run() {

        new Thread(new RedThread(), "red light").start();

        new Thread(new GreenThread(), "green light").start();
    }



    class RedThread implements Runnable{
        @Override
        public void run() {
            while(true){
                try {
                    lock.lock();
                    while (tnum != 1) {
                        redCondition.await();
                    }
                    System.out.println(Thread.currentThread().getName() + "is flashing...");
                    TimeUnit.SECONDS.sleep(1);
                    tnum = 2;
                    greenCondition.signal();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }


    class GreenThread implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    lock.lock();
                    while (tnum != 2) {
                        greenCondition.await();
                    }
                    System.out.println(Thread.currentThread().getName() + "is flashing...");
                    TimeUnit.SECONDS.sleep(1);
                    tnum = 1;
                    redCondition.signal();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }
}
