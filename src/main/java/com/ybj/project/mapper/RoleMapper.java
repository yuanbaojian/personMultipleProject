package com.ybj.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ybj.project.model.Role;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author ybj
 * @since 2020-01-15
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectRoleListByUsername(String username);
}
