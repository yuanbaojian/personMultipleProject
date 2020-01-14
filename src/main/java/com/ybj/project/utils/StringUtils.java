package com.ybj.project.utils;

import org.testng.annotations.Test;

import java.util.List;

public class StringUtils {

    @Test
    public void test(){
        List<String> stringList = null;
        stringList.add("hello");
        stringList.toArray();
    }
}
