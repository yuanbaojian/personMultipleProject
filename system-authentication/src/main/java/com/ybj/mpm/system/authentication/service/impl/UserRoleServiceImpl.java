package com.ybj.mpm.system.authentication.service.impl;

import com.ybj.mpm.system.authentication.dao.UserRoleMapper;
import com.ybj.mpm.system.authentication.model.UserRole;
import com.ybj.mpm.system.authentication.service.UserRoleServiceI;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户-角色管理实现类
 * @author caicai.gao
 */
@Service("UserRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleServiceI {
}
