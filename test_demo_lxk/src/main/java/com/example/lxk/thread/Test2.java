package com.example.lxk.thread;

public class Test2 {
    public static void main(String[] args)  {
            System.out.println("主线程ID："+Thread.currentThread().getId());
            MyRunnable runnable = new MyRunnable();
            Thread thread = new Thread(runnable);
            thread.start();
    }
}
