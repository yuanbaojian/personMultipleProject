package com.ybj.cbt.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class NetWorkUtilsTest {

    @Test
    public void test() throws IOException {
        String url="https://www.google.com/";
        String get = NetWorkUtils.getPageContent(url, "GET");
        System.out.println("get = " + get);
    }

}