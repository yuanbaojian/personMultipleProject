package com.atoz.capp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atoz.capp.dao.RolePermissionMapper;
import com.atoz.capp.dao.TrainningTreeDataMapper;
import com.atoz.capp.model.RolePermission;
import com.atoz.capp.model.TreeJsonData;
import com.atoz.capp.service.PermissionServiceI;

/**
 * 权限实现类
 * @author caicai.gao
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionServiceI {
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	@Autowired
	private TrainningTreeDataMapper trainningTreeDataMapper;

	
	@Override
	public List<TreeJsonData> getPermissionTree(Integer roleId) {
		List<TreeJsonData> lstResult = new ArrayList<>();
		QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("ROLE_ID",roleId);
		List<RolePermission>  rolePermission = rolePermissionMapper.selectList(queryWrapper);
		List<Integer>  rolePermissionList = new ArrayList<>();
		for (RolePermission rolePer :rolePermission) {
			rolePermissionList.add(rolePer.getPermissionId());
		}
		List<TreeJsonData> treeDatas = trainningTreeDataMapper.getChildOfRoot();
		for (TreeJsonData data :treeDatas) {
			List<TreeJsonData> treeDatas2 = getChildOfPermission(roleId,Integer.valueOf(data.getId()));
			data.setChildren(treeDatas2);
			Map<String, Boolean> map2 = new HashMap<>();
			if (rolePermissionList.contains(Integer.valueOf(data.getId()))){
				if (treeDatas2.isEmpty()) {
					map2.put("selected", true);
				} else {
					map2.put("selected", false);
				}
				map2.put("opened", true);
				data.setState(map2);
			} else {
				map2.put("selected", false);
				map2.put("opened", true);
				data.setState(map2);
				for (TreeJsonData data2 : treeDatas) {
					if (data2.getNodeId().equals(data.getId())){
						data2.setState(map2);
					}
				}
			}
			lstResult.add(data);
		}
		return lstResult;
	}
	
	private List<TreeJsonData> getChildOfPermission(Integer roleId,Integer nodelId) {
		List<TreeJsonData> lstResult = new ArrayList<>();
		QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("ROLE_ID",roleId);
		List<RolePermission>  rolePermission = rolePermissionMapper.selectList(queryWrapper);
		List<Integer>  rolePermissionList = new ArrayList<>();
		for (RolePermission rolePer :rolePermission) {
			rolePermissionList.add(rolePer.getPermissionId());
		}
		List<TreeJsonData> treeDatas = trainningTreeDataMapper.getChildOfPermission(nodelId.longValue());
		for (TreeJsonData data :treeDatas) {
			List<TreeJsonData> treeDatas2 = getChildOfPermission(roleId,Integer.valueOf(data.getId()));
			data.setChildren(treeDatas2);
			Map<String, Boolean> map2 = new HashMap<>();
			if (rolePermissionList.contains(Integer.valueOf(data.getId()))) {
				if (treeDatas2.isEmpty()) {
					map2.put("selected", true);
				} else {
					map2.put("selected", false);
				}
				map2.put("opened", true);
				data.setState(map2);
			} else {
				map2.put("selected", false);
				map2.put("opened", true);
				data.setState(map2);
				for (TreeJsonData data2 : treeDatas) {
					if (data2.getNodeId().equals(nodelId.toString())) {
						data2.setState(map2);
					}
				}
			}
			lstResult.add(data);
		}
		return lstResult;
	}

}
