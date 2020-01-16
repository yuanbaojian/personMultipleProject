package com.ybj.project.sonarQubeProblem;

import org.testng.annotations.Test;

public class Cast {

    @Test
    public void test(){
        String number="22";
        Double a = Double.valueOf(number);
        double b = Double.parseDouble(number);
        System.out.println("b = " + b);
    }

}
