package com.example.lxk.thread2;

import java.util.LinkedList;

public class ProducerConsumerPattern {
    public static final int MAX_CAPACITY = 5;      //缓冲区最大容量
    static LinkedList<Object> list = new LinkedList<Object>();   //用一个Linked作为缓冲区
    static class Producer implements Runnable {         //生产者
        public void run() {
            while(true) {                    //循环生产产品
                synchronized(list) {    //获取缓冲区的锁
                    while(list.size()==MAX_CAPACITY) {   // 如果缓冲区已满，
                        try {
                            System.out.println("当前产品个数为" + list.size() + "已达到最大容量，等待消费者消费。。。");
                            list.wait();                  //则调用wait进行阻塞，并释放缓冲区的锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("当前产品个数为" + list.size());
                    list.add(new Object());               //缓冲区未满，则生产产品
                    System.out.println("生产了一个产品，当前产品个数为" + list.size());
                    list.notifyAll();             //生产完一个产品之后，通知所有阻塞在wait调用中的线程
                }
                try {
                    Thread.sleep(1000);   //模拟花费一段时间进行生产
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {    //消费者
        public void run() {
            while(true) {         //不断消费产品
                synchronized(list) {    //获取缓冲区的锁
                    while(list.size()==0) {       //如果缓冲区为空，
                        try {
                            System.out.println("当前产品个数为" + list.size() + "，等待生产者生产。。。");
                            list.wait();     //则进行阻塞，并释放缓冲区的锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("当前产品个数为" + list.size());
                    list.remove();     //缓冲区不空，则消费一个产品
                    System.out.println("消费了一个产品，当前产品个数为" + list.size());
                    list.notifyAll();    //消费一个产品之后，通知所有阻塞在wait调用上的线程
                }
                try {
                    Thread.sleep(2000);   //模拟花费一段时间进行消费
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        for(int i=0; i<3; i++) {     //创建3个生产者进行生产
            new Thread(new Producer()).start();
        }
        for(int i=0; i<5; i++) {    //创建5个消费者进行消费
            new Thread(new Consumer()).start();
        }

    }
}
