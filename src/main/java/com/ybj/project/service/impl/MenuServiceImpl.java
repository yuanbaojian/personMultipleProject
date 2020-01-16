package com.ybj.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ybj.project.model.Menu;
import com.ybj.project.dao.MenuMapper;
import com.ybj.project.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author ybj
 * @since 2020-01-14
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> selectMenuListByUsername(String username) {
        QueryWrapper queryWrapper=new QueryWrapper<Menu>();
        queryWrapper.eq("name", username);
        List menuList = menuMapper.selectList(queryWrapper);
        return menuList;
    }
}
