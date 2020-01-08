package com.atoz.capp.dao;

import java.util.List;

import com.atoz.capp.model.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户角色关联管理 MAPPER
 * @author caicai.gao
 */
public interface UserRoleMapper  extends BaseMapper<UserRole> {

    /**
     *  获取用户已选角色的ID列表
     * @param userId
     * @return
     */
    @Select("select ROLE_ID from TBL_USER_ROLE where USER_ID = #{userId,jdbcType=INTEGER}")
    List<Integer> selectRoleIdByUser(Integer userId);

}