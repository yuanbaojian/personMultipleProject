package com.atoz.capp.service.impl;

import java.util.Date;
import java.util.List;

import com.atoz.capp.model.UserRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atoz.capp.common.Constants;
import com.atoz.capp.dao.RoleMapper;
import com.atoz.capp.dao.RolePermissionMapper;
import com.atoz.capp.dao.UserRoleMapper;
import com.atoz.capp.exception.BusinessException;
import com.atoz.capp.model.Role;
import com.atoz.capp.service.RoleServiceI;

/**
 *角色管理实现类
 * @author caicai.gao
 */
@Service("roleService")
public class RoleServiceImpl implements RoleServiceI {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Override
	public List<Role> getAll() {

		return roleMapper.getAll();
	}
	@Override
	public Role getRoleById(Long id) {
		return roleMapper.selectById(id);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return roleMapper.deleteById(id);
	}

	@Override
	public int total(Long id) {
		QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
		// 使用数据库字段名
		queryWrapper.eq("ROLE_ID",id);
		return userRoleMapper.selectCount(queryWrapper);
	}


	@Override
	public Role saveRole(int flg, String oid, String roleName, String remark, Long createdBy, Long updatedBy) {
		Role role = null;
		int result = 0;
		if (flg == 0) {
			// 检查名称是否存在
			QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
			// 使用数据库字段名
			queryWrapper.eq("ROLE_NAME",roleName);
			if (roleMapper.selectCount(queryWrapper) > 0) {
				throw new BusinessException(Constants.ERROR_CODE_BUSINESS, Constants.ROLE_MSG001);
			}
			role = new Role();
			role.setRoleName(roleName);
			role.setRemark(remark);
			role.setCreatedBy(createdBy);
			role.setUpdatedBy(updatedBy);
			role.setCreateTime(new Date());
			role.setUpdateTime(new Date());
			result = roleMapper.insert(role);
		} else {
			role = roleMapper.selectById(Long.valueOf(oid));
			role.setRoleName(roleName);
			role.setUpdateTime(new Date());
			role.setUpdatedBy(updatedBy);
			role.setRemark(remark);
			result = roleMapper.updateById(role);
		}
		if (result > 0) {
			return role;
		} else {
			return null;
		}
	}

	@Override
	public void deleteRolePermission(Integer roleId) {
		rolePermissionMapper.deleteById(roleId);
	}

}
