package com.ybj.mpm.system.authentication.service.impl;


import com.ybj.mpm.system.authentication.dao.RolePermissionMapper;
import com.ybj.mpm.system.authentication.model.RolePermission;
import com.ybj.mpm.system.authentication.service.RolePermissionServiceI;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色权限实现类
 * @author caicai.gao
 */
@Service("RolePermissionServiceI")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionServiceI {

}
