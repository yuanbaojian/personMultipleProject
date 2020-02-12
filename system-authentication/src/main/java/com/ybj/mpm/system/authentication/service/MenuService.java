package com.ybj.mpm.system.authentication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ybj.mpm.system.authentication.model.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author ybj
 * @since 2020-02-01
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getAll();
}
