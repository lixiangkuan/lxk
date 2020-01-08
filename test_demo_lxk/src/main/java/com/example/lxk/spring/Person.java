package com.example.lxk.spring;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Person {
    private Integer age;
    private String name;

    Person(Integer age, String name){
        this.age=age;
        this.name=name;
    }
}
