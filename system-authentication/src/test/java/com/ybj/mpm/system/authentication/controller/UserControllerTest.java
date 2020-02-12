package com.ybj.mpm.system.authentication.controller;

import com.ybj.mpm.system.authentication.model.User;
import com.ybj.mpm.system.authentication.service.UserServiceI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserServiceI userService;

    @Test
    void getAllUsers() {
        List<User> list = userService.list();
        System.out.println("list = " + list);
    }
}