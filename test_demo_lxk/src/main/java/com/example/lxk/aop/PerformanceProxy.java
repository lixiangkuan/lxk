package com.example.lxk.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class PerformanceProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        enhancer.setSuperclass(clazz);//设置父类
        enhancer.setCallback(this);
        return enhancer.create();//使用字节码技术动态创建子类的实例
    }

    /**
     * 拦截父类所有方法的调用
     *
     * @param o           目标类实例
     * @param method      目标类方法的反射对象
     * @param args        方法的动态入参
     * @param methodProxy 代理类实例
     * @return
     * @throws Throwable
     */
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        PerformanceMonitor.begin(o.getClass().getName() + "." + method.getName());
        Object result = methodProxy.invokeSuper(o, args);
        PerformanceMonitor.end();
        return result;
    }
}