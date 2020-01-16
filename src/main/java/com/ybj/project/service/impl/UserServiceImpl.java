package com.ybj.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ybj.project.model.User;
import com.ybj.project.dao.UserMapper;
import com.ybj.project.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public User selectUserByUsername(String username) {
        QueryWrapper queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("loginName", username);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }
}