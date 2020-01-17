package com.ybj.project.service.impl;

import com.ybj.project.model.User;
import com.ybj.project.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void selectUserByUsername() {
        User admin = userService.selectUserByUsername("admin");
        System.out.println("admin = " + admin);
    }
}