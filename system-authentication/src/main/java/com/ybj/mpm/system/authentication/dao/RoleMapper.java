package com.ybj.mpm.system.authentication.dao;

import com.ybj.mpm.system.authentication.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

/**
 * 角色管理Mapper
 * @author caicai.gao
 */
public interface RoleMapper  extends BaseMapper<Role> {

    /**
     * 获取所有角色信息
     * @return List<Role>
     */
   IPage<Role> getAll(IPage<Role> page, Map<String, Object> searchParams);


}