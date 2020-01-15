package com.ybj.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ybj.project.model.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ybj
 * @since 2020-01-15
 */
public interface UserService extends IService<User> {

    User selectUserByUsername(String username);
}
