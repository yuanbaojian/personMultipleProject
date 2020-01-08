package com.atoz.capp.service;

import com.atoz.capp.model.RolePermission;


/**
 * 角色权限接口类
 * @author caicai.gao
 */
public interface RolePermissionServiceI {
	/**
	 * 保存权限
	 * @param rolePermission 角色与权限关联信息
	 */
	void savePermissionTree(RolePermission rolePermission);

	/**
	 * 删除权限
	 * @param oid 权限ID
	 */
	void deleteOriPermission(Integer oid);

}
