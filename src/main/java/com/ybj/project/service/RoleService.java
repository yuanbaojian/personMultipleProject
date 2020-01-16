package com.ybj.project.service;

import com.ybj.project.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author ybj
 * @since 2020-01-14
 */
public interface RoleService extends IService<Role> {

    List<Role> selectRoleListByUsername(String username);
}
