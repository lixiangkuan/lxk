package com.example.lxk.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PerformanceHandler  implements InvocationHandler {

    private final Object target;//目标类

    public PerformanceHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PerformanceMonitor.begin(target.getClass().getName()+"."+method.getName());
        Object obj=method.invoke(target,args);//调用业务类的目标方法
        PerformanceMonitor.end();
        return obj;
    }
}