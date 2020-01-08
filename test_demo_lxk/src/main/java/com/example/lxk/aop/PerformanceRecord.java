package com.example.lxk.aop;

public class PerformanceRecord {

    private final String methodName;//方法名称
    private final long begin;//开始时间

    public PerformanceRecord(String method) {
        this.methodName = method;
        this.begin = System.currentTimeMillis();
    }

    /**
     * 打印性能信息
     */
    public void print() {
        long end = System.currentTimeMillis();
        long elapse = end - begin;
        System.out.println(methodName + " 耗费时间：" + elapse + " 毫秒");
    }
}
