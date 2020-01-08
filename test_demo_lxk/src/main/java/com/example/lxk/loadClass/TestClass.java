package com.example.lxk.loadClass;

public class TestClass {
    private String name;
    public  TestClass(String text){
        System.out.println("这是构造器："+text);
    }
    public static void sayHello(){
        System.out.println("hello");
    }
    static {
        sayHello();
    }
}
