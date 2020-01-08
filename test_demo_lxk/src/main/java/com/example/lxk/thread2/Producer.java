package com.example.lxk.thread2;

import java.util.List;
import java.util.UUID;

public class Producer extends Thread{

    private List<String> storage;//生产者仓库
    public Producer(List<String> storage) {
        this.storage = storage;
    }
    public void run(){
        //生产者每隔1s生产1~100消息
        long oldTime = System.currentTimeMillis();
        while(true){
            synchronized(storage){
                if (System.currentTimeMillis() - oldTime >= 1000) {
                    oldTime = System.currentTimeMillis();
                    int size = (int)(Math.random()*100) + 1;
                    for (int i = 0; i < size; i++) {
                        String msg = UUID.randomUUID().toString();
                        storage.add(msg);
                    }
                    System.out.println("线程"+this.getName()+"生产消息"+size+"条");
                    storage.notify();
                }
            }
        }
    }
}