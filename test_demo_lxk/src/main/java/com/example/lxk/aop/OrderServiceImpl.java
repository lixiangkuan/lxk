package com.example.lxk.aop;

public class OrderServiceImpl implements OrderService {

    /**
     * 新增
     */
    public void add() {
        PerformanceMonitor.begin("net.deniro.spring4.aop.add");//开启监视

        System.out.println("模拟新增订单");
        try {
            Thread.currentThread().sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        PerformanceMonitor.end();//结束监视

    }

    /**
     * 新增（JDK 代理）
     */
    public void addByJDKProxy() {
        addOrder();
    }

    /**
     * 新增订单
     */
    protected void addOrder() {
        System.out.println("模拟新增订单");
        try {
            Thread.currentThread().sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}