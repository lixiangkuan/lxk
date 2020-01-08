package com.example.lxk.demolxk;

import com.example.lxk.demolxk.lxk.ObjectFactory;
import com.example.lxk.demolxk.lxk.SaveDataJzgh;
import com.example.lxk.demolxk.lxk.Service;
import com.example.lxk.demolxk.lxk.ServiceSoap;

public class Test2 {
    public static void main(String[] args) {
        ObjectFactory factory=new ObjectFactory();
        SaveDataJzgh saveDataJzgh=factory.createSaveDataJzgh();
        saveDataJzgh.setStrJzgh("28DE7202079F44069224FB926C668010,jzh,2018-12-07 09:09:09,姓名,oeby0n4eezzC+Vla7N3u2D4DyrmN/n4bspeDwAlI8SU=,kP+dPoCM4kMfH7Z9TR7WsQ==,ybh,测试数据荣军总医院测试");

        Service service=new Service();
        ServiceSoap serviceSoap=service.getServiceSoap();
        String ss=serviceSoap.saveDataJzgh(saveDataJzgh.getStrJzgh());
        System.out.println(ss);
    }
}
