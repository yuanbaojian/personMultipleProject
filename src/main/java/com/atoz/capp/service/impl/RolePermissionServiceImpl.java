package com.atoz.capp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atoz.capp.dao.RolePermissionMapper;
import com.atoz.capp.model.RolePermission;
import com.atoz.capp.service.RolePermissionServiceI;

/**
 * 角色权限实现类
 * @author caicai.gao
 */
@Service("RolePermissionServiceI")
public class RolePermissionServiceImpl implements RolePermissionServiceI {

	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Override
	public void savePermissionTree(RolePermission rolePermission) {
		rolePermissionMapper.insert(rolePermission);
	}

	
	@Override
	public void deleteOriPermission(Integer roleId) {
		rolePermissionMapper.deleteById(roleId);
	}
	
}
