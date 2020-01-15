package com.ybj.project.service.impl;

import com.ybj.project.dao.MenuMapper;
import com.ybj.project.model.Menu;
import com.ybj.project.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author ybj
 * @since 2020-01-15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> selectMenuListByUsername(String username) {
        return null;
    }
}
