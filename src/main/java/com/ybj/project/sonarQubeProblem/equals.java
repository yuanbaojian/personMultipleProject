package com.ybj.project.sonarQubeProblem;

import lombok.Data;
import org.testng.annotations.Test;

/**
 * @Author test
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
public class equals {

    public static  String constantName = "hello";
    public static  Integer constantAge = 18;


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
        Person p1=new Person();
        Person p2=new Person();
        System.out.println("p2 = " + p2);


    }


//    为什么之前的 ==  不出错
    @Test
    public void test2(){
        Person person = new Person();
        person.setName("hello");
        person.setAge(18);

        String newName=new String("hello");
        Integer newAge=new Integer(18);

//        1. person的name属性与 公共变量比较
        boolean a = person.getName() == constantName;
        System.out.println("a = " + a);
//        2. person的name属性与 new出来的String 比较
        boolean b = person.getName() == newName;
        System.out.println("b = " + b);
//        3. person的name属性与 直接字符串"hello"比较
        boolean c = person.getName() == "hello";
        System.out.println("c = " + c);



    }




}

@Data
class Person {
    String name;
    Integer age;
}
