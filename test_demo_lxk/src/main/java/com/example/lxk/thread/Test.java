package com.example.lxk.thread;

public class Test {
    public static void main(String[] args)  {
        MyThread myThread=new MyThread();
        System.out.println("begin =="+myThread.isAlive());
        myThread.start();
        System.out.println("end =="+myThread.isAlive());
    }
}
