package com.ybj.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ybj.project.Exception.RainbowException;
import com.ybj.project.mapper.RoleMapper;
import com.ybj.project.model.Role;
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
 * @since 2020-01-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> selectRoleListByUsername(String username) {
        List<Role> roleList = null;
        try {
            roleList =roleMapper.selectRoleListByUsername(username);
        }catch (Exception e){
            throw  new RainbowException("拉取角色信息失败");
        }
        return roleList;
    }
}
