package com.ybj.crawler.serverImplement;

import com.ybj.crawler.mapper.UserMapper;
import com.ybj.crawler.model.User;
import com.ybj.crawler.serverInterface.UserServerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServerImpl implements UserServerInterface {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(String userId) {
        User user=userMapper.selectByPrimaryKey(Long.valueOf(userId));
        return user;
    }

    @Override
    public List<User> getAllUser() {
       return userMapper.selectAll();
    }
}
