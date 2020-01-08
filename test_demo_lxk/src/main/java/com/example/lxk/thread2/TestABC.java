package com.example.lxk.thread2;

import java.util.concurrent.atomic.AtomicInteger;

public class TestABC {
    public static void main(String[] args) {
        AtomicInteger synobj = new AtomicInteger(0);

        ThreadThree a = new ThreadThree(synobj, "A", 0);
        ThreadThree a1 = new ThreadThree(synobj, "B", 1);
        ThreadThree a2 = new ThreadThree(synobj, "C", 2);
        a.start();
        a1.start();
        a2.start();

    }


}
class ThreadThree extends Thread {

    private AtomicInteger synobj;
    private int count = 0;
    private String name;
    int f;

    public ThreadThree(AtomicInteger synobj, String name, int f) {
        this.synobj = synobj;
        this.name = name;
        this.f = f;

    }
    public void run() {
        while (true) {
            synchronized (synobj) {
                if (synobj.get() % 3 == f) {
                    synobj.set(synobj.get() + 1);
                    System.out.println(name);
                    count++;
                    synobj.notifyAll();
                    if (count > 10)
                        break;
                } else {
                    try {
                        synobj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }


}

