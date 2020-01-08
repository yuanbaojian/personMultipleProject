package com.atoz.capp.service;

import java.util.List;

import com.atoz.capp.model.TreeJsonData;

/**
 * 权限接口类
 * @author caicai.gao
 */
public interface PermissionServiceI {

	/**
	 * 获取某个权限下的权限
	 * @param integer 权限ID
	 * @return List<TreeJsonData> 权限节点列表
	 */
	List<TreeJsonData> getPermissionTree(Integer integer);

}
