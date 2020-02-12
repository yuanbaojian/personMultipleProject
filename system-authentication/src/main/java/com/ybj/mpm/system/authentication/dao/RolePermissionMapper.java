package com.ybj.mpm.system.authentication.dao;

import com.ybj.mpm.system.authentication.model.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 角色权限管理 mapper
 * @author caicai.gao
 */
public interface RolePermissionMapper  extends BaseMapper<RolePermission> {

    /**
     * 获取角色关联的权限
     * @param roleId 角色ID
     * @return List<RolePermission> 角色-权限列表
     */
    List<RolePermission> getPermissionByRoleId(String roleId);
}