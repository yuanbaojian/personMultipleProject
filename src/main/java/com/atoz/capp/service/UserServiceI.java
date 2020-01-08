package com.atoz.capp.service;

import java.util.List;

import com.atoz.capp.model.Permission;
import com.atoz.capp.model.User;
import com.atoz.capp.model.UserRole;

/**
 * 用户管理接口类
 * @author caicai.gao
 */
public interface UserServiceI {

	/**
	 * 查询所有用户
	 * @return 除管理员外所有用户信息
	 */
	List<User> getAll();

	/**
	 * 更新用户信息
	 * @param user 用户信息
	 * @return int 是否更新成功标识
	 */
	int updateByPrimaryKeySelective(User user);

	/**
	 * 删除用户
	 * @param id 用户ID
	 * @return 删除成功标识
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 根据用户ID获取用户信息
	 * @param userId 用户ID
	 * @return User 用户对象
	 */
	User selectByPrimaryKey(Integer userId);

	/**
	 * 根据登录名获取用户信息
	 * @param loginName 登录名
	 * @return User 用户对象
	 */
	User getUserByLoginName(String loginName);

	/**
	 * 保存用户信息
	 * @param flag 新增OR修改标识 0-新增 1-修改
	 * @param record 用户信息
	 * @return User 用户对象
	 */
	User saveUser(int flag, User record);

	/**
	 * 获取除当前登录用户外的其他用户
	 * @param loginUserId 当前登录用户ID
	 * @return List<User> 用户列表
	 */
	List<User> getUsersExcludeLoginUser(Integer loginUserId);

	/**
	 * 获取用户权限
	 * @param userId 用户ID
	 * @return List<Permission> 权限列表
	 */
	List<Permission> selectPermissionByUser(Integer userId);

	/**
	 * 为用户分配角色
	 * @param list 用户角色关联关系
	 * @param userId 用户ID
	 */
	void assignRole(List<UserRole> list, Integer userId);

	/**
	 * 以用户名和密码查询用户
	 *
	 * @param  user 用户名或者密码或者两者都有
	 * @return 用户信息
	 */
	User checkUser(User user);

	/**
	 * 获取用户已选角色的ID列表
	 * @param userId 用户ID
	 * @return 关联角色ID列表
	 */
	List<Integer> getUserSelectedRole(Integer userId);

	/**
	 * 获取所有人员-用于审核
	 * @param type 权限（拥有审核XX权限的用户才会被选中）
	 * @return List<User>    返回类型
	 */
	List<User> searchAuditor(String type);
	  
	/**  
	 * 获取所有人员-用于转交
	 * @return List<User>    返回类型  
	 */
	List<User> searchUser();

	  
	/**  
	 * 查看用户是否拥有某种权限
	 * @param userId  用户ID 
	 * @param permissionName  权限名称
	 * @return void    返回类型  
	 */
	Boolean hasPermission(Integer userId, String permissionName);

	/**
	 * 删除用户关联的角色信息
	 * @param userId 用户ID
	 */
	void deleteUserRole(Integer userId);

}
