package com.example.lxk.aop;

import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        //----------硬编码代理---------
        OrderService orderService=new OrderServiceImpl();
        orderService.add();

        //----------反射代理---------
        //编织目标业务类与横切代码
        PerformanceHandler handler=new PerformanceHandler(orderService);

        //创建代理实例
        OrderService proxy=(OrderService) Proxy.newProxyInstance(orderService.getClass()
                .getClassLoader(),orderService.getClass().getInterfaces(),handler);

        proxy.addByJDKProxy();

        //----------CGLib代理---------
        PerformanceProxy proxy2 = new PerformanceProxy();
        OrderServiceImpl orderService2 = (OrderServiceImpl) proxy2.getProxy(OrderServiceImpl.class);
        orderService2.addOrder();
    }
}
