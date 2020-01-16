package com.ybj.project.service;

import com.ybj.project.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
