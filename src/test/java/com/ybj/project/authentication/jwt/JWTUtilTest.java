package com.ybj.project.authentication.jwt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JWTUtilTest {

    @Test
    void getUsername() {
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NzkyNTcyNjUsInVzZXJuYW1lIjoiYWRtaW4ifQ.yfbnyzSP8H_u7i3KIbYSkOEH_9SymDRM4LhDSrGPexU";
        JWTUtil.getUsername(token);
        System.out.println("token = " + token);
    }
}