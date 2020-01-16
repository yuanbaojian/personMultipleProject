package com.ybj.project.service.impl;

import com.ybj.project.model.UserRole;
import com.ybj.project.dao.UserRoleMapper;
import com.ybj.project.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author ybj
 * @since 2020-01-14
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
