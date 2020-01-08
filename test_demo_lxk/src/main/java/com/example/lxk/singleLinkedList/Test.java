package com.example.lxk.singleLinkedList;

public class Test {
    public static void main(String[] args) {

        DataNode a0=new DataNode(0);
        DataNode a1=new DataNode(1);
        a1.setNext(a0);
        DataNode a2=new DataNode(2);
        a2.setNext(a1);
        DataNode a3=new DataNode(3);
        a3.setNext(a2);



        DataNode a4=new DataNode(4);
        a4.setNext(a3);
        DataNode a6=new DataNode(6);
        a6.setNext(a4);

        DataNode a5=new DataNode(5);
        a5.setNext(a3);
        DataNode a7=new DataNode(7);
        a7.setNext(a5);

//        boolean isJoinNoLoop=DataNodeUntils.isJoinNoLoop(a7,a6);
//        System.out.println("是否相交："+isJoinNoLoop);
//
//        a0.setNext(a5);
//        boolean isLoop=DataNodeUntils.isLoop(a7);
//        System.out.println("是否存在环："+isLoop);

//        DataNode firstJoinNode=DataNodeUntils.getFirstJoinNode(a7,a6);
//        System.out.println("第一个交点："+firstJoinNode.getData());

//        DataNode entryNoLoop=DataNodeUntils.entryNoLoop(a7,a6);
//        System.out.println("第一个交点："+entryNoLoop.getData());
//
//        a0.setNext(a2);
//        DataNode entryLoop=DataNodeUntils.entryLoop(a7);
//        System.out.println("环的入口："+entryLoop.getData());

        a0.setNext(a3);
        a4.setNext(a2);
        a5.setNext(a1);
        DataNode getJoinNode=DataNodeUntils.getJoinNode(a7,a6);
        System.out.println("双环链表的交点："+getJoinNode.getData());
    }
}
