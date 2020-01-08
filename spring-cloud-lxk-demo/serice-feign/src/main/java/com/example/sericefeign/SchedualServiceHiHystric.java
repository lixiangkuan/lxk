package com.example.sericefeign;

import org.springframework.stereotype.Component;

/**
 * @Author: lxk
 * @Description:
 * @Date: 2018/2/26
 * @Modified by:
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}