package com.ybj.cbt.Learn.ThinkingInJava.Ch18;

import org.testng.annotations.Test;

public class GetBytes {

    @Test
    public void getBytes(){
        String name="abc";

        byte[] bytes = name.getBytes();
        for (byte aByte : bytes) {
          System.out.println("aByte = " + aByte);
        }
    }

}
