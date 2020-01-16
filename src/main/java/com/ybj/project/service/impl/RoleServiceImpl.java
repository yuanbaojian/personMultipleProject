package com.ybj.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ybj.project.model.Role;
import com.ybj.project.dao.RoleMapper;
import com.ybj.project.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author ybj
 * @since 2020-01-14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> selectRoleListByUsername(String username) {
        QueryWrapper queryWrapper=new QueryWrapper<Role>();
        queryWrapper.eq("name", username);
        List<Role> roleList = roleMapper.selectList(queryWrapper);
        return roleList;
    }
}
