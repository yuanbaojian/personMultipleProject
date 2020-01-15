package com.ybj.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ybj.project.model.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author ybj
 * @since 2020-01-15
 */
public interface MenuService extends IService<Menu> {

    List<Menu> selectMenuListByUsername(String username);

}
