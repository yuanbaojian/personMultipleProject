package com.atoz.capp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atoz.capp.common.constant.ConfigConstants;
import com.atoz.capp.server.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import com.atoz.capp.common.Constants;
import com.atoz.capp.dao.PermissionMapper;
import com.atoz.capp.dao.UserMapper;
import com.atoz.capp.dao.UserRoleMapper;
import com.atoz.capp.exception.BusinessException;
import com.atoz.capp.model.Permission;
import com.atoz.capp.model.User;
import com.atoz.capp.model.UserRole;
import com.atoz.capp.server.util.MD5EncryptionUtil;
import com.atoz.capp.server.util.PropertiesUtil;
import com.atoz.capp.service.UserServiceI;
import org.springframework.stereotype.Service;

/**
 * 用户管理实现类
 * @author caicai.gao
 */
@Service("UserServiceI")
public class UserServiceImpl implements UserServiceI {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private PermissionMapper permissionMapper;

	/**
	 * 更新用户信息
	 * @param user 用户信息
	 * @return int 是否更新成功标识
	 */
	@Override
	public int updateByPrimaryKeySelective(User user) {
		return userMapper.updateById(user);
	}

	/**
	 * 删除用户
	 * @param userId 用户ID
	 * @return 删除成功标识
	 */
	@Override
	public int deleteByPrimaryKey(Integer userId) {
		// 删除用户
		try {
			// 先删除关联角色信息
			userRoleMapper.deleteById(userId);
			// 尝试直接删除，若出错则返回
			return userMapper.deleteById(userId);
		} catch (Exception ex) {
			throw new BusinessException(Constants.ERROR_CODE_BUSINESS, Constants.ORG_STRUC_MSG001);
		}
	}


	/**
	 * 删除用户关联的角色信息
	 * @param userId 用户ID
	 */
	@Override
	public void deleteUserRole(Integer userId) {
		try {
			// 删除关联角色信息
			userRoleMapper.deleteById(userId);
		} catch (Exception ex) {
			throw new BusinessException(Constants.ERROR_CODE_BUSINESS, Constants.ORG_STRUC_MSG001);
		}
	}

	/**
	 * 根据用户ID获取用户信息
	 * @param userId 用户ID
	 * @return User 用户对象
	 */
	@Override
	public User selectByPrimaryKey(Integer userId) {
		return userMapper.selectById(userId);
	}

	/**
	 * 获取除当前登录用户外的其他用户
	 * @param loginUserId 当前登录用户ID
	 * @return List<User> 用户列表
	 */
	@Override
	public List<User> getUsersExcludeLoginUser(Integer loginUserId) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("STATUS",0);
		queryWrapper.eq("DELETED",0);
		queryWrapper.ne("USER_ID",loginUserId);
		return userMapper.selectList(queryWrapper);
	}

	/**
	 * 根据登录名获取用户信息
	 * @param loginName 登录名
	 * @return User 用户对象
	 */
	@Override
	public User getUserByLoginName(String loginName) {
		Map<String, Object> columnMap = new HashMap<>();
		// Map<key.value>中 key为数据库的列名 不是model的属性名
		columnMap.put("LOGIN_NAME",loginName);
		List<User> l = userMapper.selectByMap(columnMap);
		return l.get(0);
	}

	/**
	 * 为用户分配角色
	 * @param list 用户角色关联关系
	 * @param userId 用户ID
	 */
	@Override
	public void assignRole(List<UserRole> list, Integer userId) {
		// 先全部删除
		userRoleMapper.deleteById(userId);
		// 在分别插入
		for (UserRole userRole : list) {
			userRole.setUserId(userId.longValue());
			userRoleMapper.insert(userRole);
		}
	}

	/**
	 * 查询所有用户
	 * 
	 * @return 除管理员外所有用户信息
	 */
	@Override
	public List<User> getAll() {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("DELETED",0);
//        queryWrapper.ne("LOGIN_NAME", ConfigConstants.ADMIN_USER);
//        return userMapper.selectList(queryWrapper);
		// 参数Map
		Map<String,Object> map = new HashMap<>();
		// 管理员名
		map.put("admin", "admin");
		return userMapper.getAll(map);
	}

	/**
	 * 保存用户信息
	 * @param flg 新增OR修改标识 0-新增 1-修改
	 * @param user 用户信息
	 * @return User 用户对象
	 */
	@Override
	public User saveUser(int flg, User user) {
		int result;
		if (flg == 0) {
			User oldUser = new User();
			oldUser.setLoginName(user.getLoginName());
			// 检查名称是否已存在
			if (checkUser(oldUser) !=null ) {
				throw new BusinessException(Constants.ERROR_CODE_BUSINESS, Constants.USER_MSG001);
			}
			user.setPassword(MD5EncryptionUtil.convertMD5(Constants.PASSWORD));
			Date date = new Date();
			user.setCreateTime(date);
			user.setUpdateTime(date);
			user.setStatus(0);
			result = userMapper.insert(user);
		} else {
			user.setUpdateTime(new Date());
			result = userMapper.updateById(user);
		}
		if (result > 0) {
			return user;
		}
		return null;
	}

	/**
	 * 获取用户权限
	 * @param userId 用户ID
	 * @return List<Permission> 权限列表
	 */
	@Override
	public List<Permission> selectPermissionByUser(Integer userId) {
		 return permissionMapper.selectPermissionByUser(userId);
	 }

	/**
	 * 以用户名和密码查询用户
	 * 
	 * @param  user 用户名或者密码或者两者都有
	 * @return 用户信息
	 */
	@Override
	public User checkUser(User user) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(StringUtil.isNotEmpty(user.getLoginName()),"LOGIN_NAME", user.getLoginName());
		queryWrapper.ne(StringUtil.isNotEmpty(user.getPassword()), "PASSWORD", user.getPassword());
		return userMapper.selectOne(queryWrapper);
	}

	/**
	 * 获取用户已选角色的ID列表
	 * @param userId 用户ID
	 * @return List<Integer> 返回类型
	 */
	@Override
	public List<Integer> getUserSelectedRole(Integer userId) {
		return userRoleMapper.selectRoleIdByUser(userId);
	}

	/**
	 * 获取所有人员-用于审核
	 * @param type 权限（拥有审核XX权限的用户才会被选中）
	 * @return List<User>    返回类型
	 */
	@Override
	public List<User> searchAuditor(String type) {
		// 参数Map
		Map<String,Object> map = new HashMap<>();
		// 管理员名
		map.put("admin", PropertiesUtil.getMsgsMap().get("ADMIN"));
		map.put("type", type);
		return userMapper.searchAuditor(map);
	}

	/**
	 * 获取所有人员-用于转交
	 * @return List<User>    返回类型
	 */
	@Override
	public List<User> searchUser() {
		// 参数Map
		Map<String,Object> map = new HashMap<>();
		// 管理员名
		map.put("admin", PropertiesUtil.getMsgsMap().get("ADMIN"));
		return userMapper.searchUser(map);
	}

	/**
	 * 判断用户是否觉有某个权限
 	 * @param userId  用户ID
	 * @param permissionName  权限名称
	 * @return Boolean 是否有权限的标识
	 */
	@Override
	public Boolean hasPermission(Integer userId, String permissionName) {
		int flg =  userMapper.hasPermission(userId, permissionName).size();
		return flg != 0;
	}

	/**
	 * 分页查询演示
	 */
	public void selectPage (){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("DELETED",0);
		Page<User> page = new Page<>(1, 10, true);

		// 分页查询方法一：selectPage
		IPage<User> iPage = userMapper.selectPage(page, wrapper);
		// 查询结果
		List<User> userList = iPage.getRecords();
		// 总页数
		iPage.getPages();
		// 总记录数
		iPage.getTotal();
		userList.forEach(System.out::println);

		/**
		// 分页查询方法二：selectMapsPage
		IPage<Map<String, Object>> iPageMap = userMapper.selectMapsPage(page, wrapper);
		 */
	}

	
}
