package com.atoz.capp.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import com.atoz.capp.model.User;

/**
 * 用户管理 MAPPER
 * @author caicai.gao
 */
public interface UserMapper extends BaseMapper<User> {

	List<User> getAll(Map<String, Object> map);

	User checkUser(User user);

	/**
	 * 查找所有用户-用于审核
	 * @param map 查询条件
	 * @return List<User> 用户列表
	 */
	List<User> searchAuditor(Map<String, Object> map);

	/**
	 * 查找所有用户-用于转交
	 * @param map 查询条件
	 * @return List<User> 用户列表
	 */
	List<User> searchUser(Map<String, Object> map);

	/**
	 * 查看用户是否拥有某种权限
	 * @param userId 用户Id
	 * @param permissionName 权限名称
	 * @return List<User> 用户列表
	 */
	List<User> hasPermission(@Param("userId") Integer userId, @Param("permissionName") String permissionName);
	
}