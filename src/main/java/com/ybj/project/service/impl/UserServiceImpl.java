package com.ybj.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ybj.project.authentication.jwt.JWTToken;
import com.ybj.project.dao.RoleMapper;
import com.ybj.project.model.Role;
import com.ybj.project.model.User;
import com.ybj.project.dao.UserMapper;
import com.ybj.project.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ybj
 * @since 2020-01-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public User selectUserByUsername(String username) {
        QueryWrapper queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("loginName", username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public Map<String, Object> getUserWithToken(JWTToken jwtToken, User user) {
        //1- 封装token
        Map<String,Object> userInfoMap = new HashMap<>();
        userInfoMap.put("token",jwtToken.getToken());
        userInfoMap.put("expireTime",jwtToken.getExipreAt());
        // 2- 得到用户的所有角色， 需要自定义sql（连接查询）
        List<Role> roles = roleMapper.selectRoleListByUsername(user.getUsername());
        userInfoMap.put("roles",roles);
        userInfoMap.put("user",user);
        return userInfoMap;
    }
}
