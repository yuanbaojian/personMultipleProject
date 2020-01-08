package com.atoz.capp.service;

import java.util.List;

import com.atoz.capp.model.Role;

/**
 * 角色管理接口类
 * @author caicai.gao
 */
public interface RoleServiceI {

	Role getRoleById(Long id);
	
	List<Role> getAll();
	
	int deleteByPrimaryKey(Long id);

	int total(Long id);
	
	Role saveRole(int flg, String oid, String roleName, String remark, Long createdBy, Long updatedBy);

	void deleteRolePermission(Integer id);
	
}
