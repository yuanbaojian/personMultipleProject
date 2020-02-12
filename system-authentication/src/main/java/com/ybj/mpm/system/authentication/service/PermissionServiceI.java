package com.ybj.mpm.system.authentication.service;


import com.ybj.mpm.system.authentication.model.TreeJsonData;

import java.util.List;

/**
 * 权限接口类
 * @author caicai.gao
 */
public interface PermissionServiceI {

	/**
	 * 获取某个角色下的权限
	 * @param roleId 角色ID
	 * @return List<TreeJsonData> 权限节点列表
	 */
	List<TreeJsonData> getPermissionTree(String roleId);

}
