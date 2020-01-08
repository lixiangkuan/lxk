package com.example.lxk.singleLinkedList;

public class DataNodeUntils {
    /**
     * 无环情况下，判断两个链表是否相交，只需要遍历链表，判断尾节点是否相等即可。
     * @param h1
     * @param h2
     * @return
     */
    public static boolean isJoinNoLoop(DataNode h1,DataNode h2) {
        DataNode p1 = h1;
        DataNode p2 = h2;
        while(null != p1.getNext())
            p1 = p1.getNext();
        while(null != p2.getNext())
            p2 = p2.getNext();
        return p1 == p2;
    }

    /**
     * 无环情况下找到第一个相交点
     * 方法： 算出两个链表的长度差为x,长链表先移动x步，之后两链表同时移动，直到相遇的第一个交点。
     * @param h1
     * @param h2
     * @return
     */
    public static DataNode getFirstJoinNode(DataNode h1,DataNode h2) {
        int length1 = 0;
        int length2 = 0;
        DataNode h1Old=h1;
        DataNode h2Old=h2;
        while(null != h1.getNext()) {
            length1 ++;
            h1 = h1.getNext();
        }
        while(null != h2.getNext()) {
            length2 ++;
            h2 = h2.getNext();
        }
        return length1>=length2?getNode(h1Old,length1,h2Old,length2):getNode(h2Old,length2,h1Old,length1);
    }
    public static DataNode getNode(DataNode hL,int lengthL,DataNode hS,int lengthS) {
        int lengthD=lengthL-lengthS;
        DataNode hLNew=hL;
        while(lengthD>0) {
            lengthD --;
            hLNew = hL.getNext();
        }
        DataNode p1 = hLNew;
        DataNode p2 = hS;
        while(p1 != p2) {
            p1 = p1.getNext();
            p2 = p2.getNext();
        }
        return p2;
    }

    /**
     * 判断是否存在环
     * 步骤：设置两个指针同时指向head，其中一个一次前进一个节点（P1），另外一个一次前进两个节点(P2)。
     * p1和p2同时走，如果其中一个遇到null，则说明没有环，如果走了N步之后，二者指向地址相同，那么说明链表存在环。
     * @param h
     * @return
     */
    public static boolean isLoop(DataNode h) {
        DataNode p1 = h;
        DataNode p2 = h;
        while(p2.getNext() != null && p2.getNext().getNext()!=null){
            p1 = p1.getNext();
            p2 = p2.getNext().getNext();
            if(p1 == p2)
                break;
        }
        return p1!=null&&p2!=null&&p1 == p2;
    }



    /**
     *  方法二： 将其中一个链表首尾相连 从另外一个链表开始，检测是否存在环，如果存在，则说明二者相交。
     *  如果需要找出环的入口，则设P1 P2 两个指针，P1一次走两步，P2一次走一步，两者在环上某一点相遇。记下此位置。
     *  此时设置一个指针P3指向表头，然后P1和P3每次同时行走一步，每步前进一个节点。等到P1、P3重合时，则重合位置即使环入口。
     * @param h1
     * @param h2
     * @return
     */
    public static   DataNode entryNoLoop(DataNode h1,DataNode h2) {
        DataNode p = h1;
        while(null != p.getNext()){
            p = p.getNext();
        }
        p.setNext(h1);
        return entryLoop(h2);
    }

    /**
     * 获取环的入口点
     * @param h
     * @return
     */
    public static DataNode entryLoop(DataNode h) {
        DataNode p1 = h;
        DataNode p2 = h;
        DataNode p3 = h;

        while(null != p2.getNext() && null != p2.getNext().getNext()){
            p1 = p1.getNext();
            p2 = p2.getNext().getNext();
            if(p1 == p2)
                break;
        }
        while(p3 != p1) {
            p1 = p1.getNext();
            p3 = p3.getNext();
        }
        return p3;
    }


    /**
     *
     * @param h1
     *            链表1的头节点
     * @param l1
     *            链表1的环入口
     * @param h2
     *            链表2的头节点
     * @param l2
     *            链表2的头节点
     * @return
     */
    public static DataNode bothLoop(DataNode h1, DataNode l1, DataNode h2, DataNode l2) {
        DataNode p1 = null;
        DataNode p2 = null;
        if (l1 == l2) {
            p1 = h1;
            p2 = h2;
            int n = 0;
            while (p1 != l1) {
                n++;
                p1 = p1.getNext();
            }
            while (p2 != l2) {
                n--;
                p2 = p2.getNext();
            }
            p1 = n > 0 ? h1 : h2;
            p2 = p1 == h1 ? h2 : h1;
            n = Math.abs(n);
            while (n != 0) {
                n--;
                h1 = h1.getNext();
            }
            while (p1 != p2) {
                p1 = p1.getNext();
                p2 = p2.getNext();
            }
            return p1;
        } else {
            p1 = l1;
            while (p1 != l2) {
                p1 = p1.getNext();
            }
            return p1;
        }
    }

    /**
     *
     * @param h1
     * @param h2
     * @return
     */
    public static DataNode getJoinNode(DataNode h1, DataNode h2) {
        if (null == h1 || null == h2)
            return null;
        DataNode l1 = entryLoop(h1);
        DataNode l2 = entryLoop(h2);
        if (null == l1 && null == l2)
            return getFirstJoinNode(h1, h2);
        if (null != l1 && null != l2)
            return bothLoop(h1,l1,h2,l2);
        return null;
    }
}
