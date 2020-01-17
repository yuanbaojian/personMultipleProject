package com.ybj.project.service;

import com.ybj.project.authentication.jwt.JWTToken;
import com.ybj.project.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ybj
 * @since 2020-01-14
 */
public interface UserService extends IService<User> {

    User selectUserByUsername(String username);

    Map<String, Object> getUserWithToken(JWTToken jwtToken, User user);
}
