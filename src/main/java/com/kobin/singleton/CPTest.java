package com.kobin.singleton;

import java.util.LinkedList;

/**
 * Created by @author shibinbin on 2017/12/12.
 */

public class CPTest {

    public static void main(String[] args){

        Resource r = new Resource();

        // 生产者对象
        Producer p1 = new Producer(r);

        // 消费者对象
        Consumer c1 = new Consumer(r);

        // 设置生产者产品生产数量
        p1.setNum(10);

        // 设置消费者产品消费数量
        c1.setNum(100);

        // 线程开始执行
        c1.start();

        p1.start();

    }
}

//资源类
class Resource{
    private LinkedList<Object> list = new LinkedList<Object>();
    private volatile int MAX_SIZE = 10;

    public synchronized void consume(int num){
        while (true){
            //库存不够,当前线程阻塞
            while (list.size()<num){
                try {
                    System.out.println("消费线程："+Thread.currentThread()+"【要消费的产品数量】:" + num + " 【库存量】:"
                            + list.size() + "  暂时不能消费!");

                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //库存满足
            for (int i=0;i<num;i++){
                list.remove();
            }

            System.out.println("消费线程："+Thread.currentThread()+"【已经消费产品数】:" + num + " 【现仓储量为】:" + list.size());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.notify();

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void produce(int num){
        while (true){
            while (list.size()+num > MAX_SIZE){
                System.out.println("生产线程："+Thread.currentThread()+"【要生产的产品数量】:" + num + " 【库存量】:"
                        + list.size() + "  暂时不能生产!");
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(int i=0;i<num;i++){
                list.add(new Object());
            }
            System.out.println("生产线程："+Thread.currentThread()+"【已经生产产品数】:" + num + "  【现仓储量为】:" + list.size());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.notify();

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer extends Thread{
    // 每次生产的产品数量
    private int num;

    Resource r;

    public Producer(Resource r){
        this.r = r;
    }
    @Override
    public void run(){
        r.produce(num);
    }

    public void setNum(int num)
    {
        this.num = num;
    }
}

class Consumer extends Thread{
    // 每次消费的产品数量
    private int num;

    private Resource r;

    public Consumer(Resource r){
        this.r = r;
    }

    @Override
    public void run(){
        r.consume(num);
    }

    public void setNum(int num)
    {
        this.num = num;
    }
}
