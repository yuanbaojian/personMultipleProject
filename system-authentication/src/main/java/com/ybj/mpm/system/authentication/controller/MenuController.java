package com.ybj.mpm.system.authentication.controller;


import com.ybj.mpm.system.authentication.model.Menu;
import com.ybj.mpm.system.authentication.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author ybj
 * @since 2020-02-01
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @PostMapping("/getAll")
    public List<Menu> getAllMenu(){
        List<Menu> menuList = menuService.getAll();
        return menuList;
    }

}

