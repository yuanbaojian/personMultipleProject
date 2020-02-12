package com.ybj.mpm.system.authentication.service.impl;

import com.ybj.mpm.system.authentication.model.Menu;
import com.ybj.mpm.system.authentication.dao.MenuMapper;
import com.ybj.mpm.system.authentication.service.MenuService;
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
 * @since 2020-02-01
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        List<Menu> allMenu = menuMapper.getAllMenu();
        return allMenu;
    }
}
