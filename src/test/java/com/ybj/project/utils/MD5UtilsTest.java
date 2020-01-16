package com.ybj.project.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MD5UtilsTest {

    @Test
    void encrypt() {
        MD5Utils.encrypt("yuanbaojian","123456");
    }
}