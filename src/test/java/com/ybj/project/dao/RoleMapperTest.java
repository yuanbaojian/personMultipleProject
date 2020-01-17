package com.ybj.project.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleMapperTest {

    @Autowired
    RoleMapper roleMapper;

    @Test
    void selectRoleListByUsername() {
        System.out.println("roleMapper = " + roleMapper);
        roleMapper.selectRoleListByUsername("admin");
    }
}