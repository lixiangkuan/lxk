package com.example.lxk.singleLinkedList;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DataNode {
    private int data;
    private DataNode next;
    public DataNode(int data) {
        this.data = data;
    }
}
