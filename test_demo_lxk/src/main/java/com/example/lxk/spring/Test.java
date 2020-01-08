package com.example.lxk.spring;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
//        ApplicationContext aaa = new ClassPathXmlApplicationContext("spring.xml");

        List<Person> lisiList = new ArrayList<>();
        Consumer<Person> consumer  = x -> {
            if (x.getName().equals("lisi")){
                lisiList.add(x);
            }
        };
        Stream.of(
                new Person(21,"zhangsan"),
                new Person(22,"lisi"),
                new Person(23,"wangwu"),
                new Person(24,"wangwu"),
                new Person(23,"lisi"),
                new Person(26,"lisi"),
                new Person(26,"zhangsan")
        ).forEach(consumer);

        System.out.println(lisiList);
    }
}
