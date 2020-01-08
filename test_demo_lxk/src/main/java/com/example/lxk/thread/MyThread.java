package com.example.lxk.thread;

public class MyThread extends Thread {
    @Override
    public void run() {
        long beginTime=System.currentTimeMillis();
        int count=0;
        for (int i=0;i<50000;i++){
            count=count+(i+1);
//            Thread.yield();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("用时："+(endTime-beginTime)+" 毫秒！");
    }
}
