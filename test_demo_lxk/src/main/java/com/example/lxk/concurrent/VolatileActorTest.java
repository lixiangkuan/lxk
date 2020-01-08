package com.example.lxk.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileActorTest {

    volatile  int i;

    public void addI() {
        i++;
    }

    static  AtomicInteger j=new AtomicInteger(0);
    public static void main(String[] args) throws Exception {
        VolatileActorTest volatileActorTest = new VolatileActorTest();
        for (int i=0; i< 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //每个线程让count自增100次
                    for (int i = 0; i < 100; i++) {
                        volatileActorTest.addI();
                        j.incrementAndGet();
                    }
                }
            }).start();
        }
        Thread.sleep(1000);//等待10秒，保证上面程序执行完成

        System.out.println(volatileActorTest.i);
        System.out.println(volatileActorTest.j);

    }
}
