package com.example.lxk.thread;

public class TestThreadCiycle implements  Runnable{
    String name;
    boolean live = true;// 标记变量，表示线程是否可中止；
    public TestThreadCiycle(String name) {
        super();
        this.name = name;
    }
    public void run() {
        int i = 0;
        //当live的值是true时，继续线程体；false则结束循环，继而终止线程体；
        while (true) {
            i++;
            if(live){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + i);
            }else {
                return;
            }
        }
    }
    public void terminate() {
        live = false;
    }

    public static void main(String[] args) {
        TestThreadCiycle ttc = new TestThreadCiycle("线程A:");
        Thread t1 = new Thread(ttc);// 新生状态
        t1.start();// 就绪状态
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("主线程" + i);
        }
        ttc.terminate();
        for (int i = 0; i < 100; i++) {
            System.out.println("主线程" + i);
        }
        System.out.println("ttc stop!");
    }
}
