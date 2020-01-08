package com.example.lxk.demolxk;

import java.util.HashMap;
import java.util.Map;

/**
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 -  0000000000 0000000000 000000000 - 0 - 0 - 00000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 29位时间截(秒级)，注意，29位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。29位的时间截，可以使用17年，年T = (1L << 29) / (60 * 60 * 24 * 365) = 17<br>
 * 2位的数据机器位，可以部署在4个节点，包括1位datacenterId和1位workerId<br>
 * 8位序列，秒内的计数，8位的计数顺序号支持每个节点每秒(同一机器，同一时间截)产生256个ID序号<br>
 * 加起来40位，为一个Long型。<br>
 */
public class SnowflakeIdWorker2 {

    // ==============================Fields===========================================
    /** 开始时间截 (2015-01-01)秒级 */
    private final long twepoch = 1420041600L;

    /** 机器id所占的位数 */
    private final long workerIdBits = 1L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 1L;

    /** 支持的最大机器id，结果是2 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是2 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 8L;

    /** 机器ID向左移8位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移9位(8+1) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移10位(8+1+1) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，255 */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~1) */
    private long workerId;

    /** 数据中心ID(0~1) */
    private long datacenterId;

//    /** 秒内序列(0~255) */
//    private long sequence = 0L;
//
//    /** 上次生成ID的时间截 */
//    private long lastTimestamp = -1L;
//==============================添加了map，key为vendorId，改造成每个商家下每秒256并发=====================================
    /** 秒内序列(0~255) */
    private Map<Long,Long> sequenceMap =new HashMap<>();

    /** 上次生成ID的时间截 */
    private Map<Long,Long> lastTimestampMap =new HashMap<>();

    //==============================Constructors=====================================
    /**
     * 构造函数
     * @param workerId 工作ID (0~1)
     * @param datacenterId 数据中心ID (0~1)
     */
    public SnowflakeIdWorker2(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId(Long vendorId) {
        long timestamp = timeGen();
        long sequence =getSequence(vendorId);
        long lastTimestamp =getlastTimestamp(vendorId);

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            setSequence(vendorId,sequence);
            //秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，秒内序列重置
        else {
            sequence = 0L;
            setSequence(vendorId,sequence);
        }

        //上次生成ID的时间截
        setlastTimestamp(vendorId,timestamp);

        //移位并通过或运算拼到一起组成39位的ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     *
     * @return
     */
    private long getSequence(Long vendorId){
        if(!sequenceMap.containsKey(vendorId)){
            sequenceMap.put(vendorId,0L);
        }
        return sequenceMap.get(vendorId);
    }
    private void setSequence(Long vendorId,Long sequence){
        sequenceMap.put(vendorId,sequence);
    }
    private long getlastTimestamp(Long vendorId){
        if(!lastTimestampMap.containsKey(vendorId)){
            lastTimestampMap.put(vendorId,-1L);
        }
        return lastTimestampMap.get(vendorId);
    }
    private void setlastTimestamp(Long vendorId,Long lastTimestamp){
        lastTimestampMap.put(vendorId,lastTimestamp);
    }

    /**
     * 阻塞到下一秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以秒为单位的当前时间
     * @return 当前时间(秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis()/1000;
    }

    //==============================Test=============================================
    /** 测试 */
    public static void main(String[] args) {
        Long time=System.currentTimeMillis();
        SnowflakeIdWorker2 idWorker = new SnowflakeIdWorker2(1, 1);
        for (int i = 0; i < 100; i++) {
            long id = idWorker.nextId(0l);
//            System.out.println(Long.toBinaryString(id));
//            System.out.println("0");
            System.out.println(id);
            long id1 = idWorker.nextId(1l);
//            System.out.println("1");
//            System.out.println(Long.toBinaryString(id));
            System.out.println(id1);
            long id2 = idWorker.nextId(2l);
//            System.out.println("2");
//            System.out.println(Long.toBinaryString(id));
            System.out.println(id2);
        }
        Long time2=System.currentTimeMillis();
        System.out.println(time2-time);
//        Long time=System.currentTimeMillis();
//        System.out.println(time);
//        System.out.println(Long.toBinaryString(time));
//        System.out.println((1L << 29) / (60 * 60 * 24 * 365));
//        System.out.println(-1L << 8);
//        System.out.println( -1L ^ (-1L << 8));
//        System.out.println(1420041600000L/1000);

    }
}
