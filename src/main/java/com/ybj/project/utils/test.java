package com.ybj.project.utils;

import org.testng.annotations.Test;

/**
 * @Author test
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
public class test {


    @Test
    public void testEquals(){
//      基本数据类型, 存放到栈内,不需要在堆申请内存
        int e = 1;
        int f = 1;


//      包装类, 需要在堆里申请内存
        String a = "a";
        String b = "a";
        String A = new String("a");
        String B = new String("a");
        System.out.println("B = " + B);

        Integer c = 1;
        Integer d = 1;


//      对象
        Thread t1=new Thread("tt");
        Thread t2=new Thread("tt");
        person p1=new person();
        person p2=new person();
        System.out.println("p2 = " + p2);
    }


}

class person{
    String name;
}
