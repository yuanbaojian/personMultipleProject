package com.atoz.capp.dao;

import java.util.List; 

import com.atoz.capp.model.Permission;

/**
 * 权限管理mapper
 * @author caicai.gao
 */
public interface PermissionMapper {
    
    List<Permission> selectPermissionByUser(Integer userId);


}