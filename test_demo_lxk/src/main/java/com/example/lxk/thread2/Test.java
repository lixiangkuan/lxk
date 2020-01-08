package com.example.lxk.thread2;

public class Test {
    public static void main(String[] args) {
        Storage storage = new Storage();
        Producer producer = new Producer(storage.getStorage());
        Consumer consumer = new Consumer(storage.getStorage());
        producer.start();
        consumer.start();
    }
}
