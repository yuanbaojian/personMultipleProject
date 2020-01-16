package com.ybj.project.dao;

import com.ybj.project.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author ybj
 * @since 2020-01-14
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectRoleListByUsername(@Param("username") String username);
}
