package com.atoz.capp.dao;

import java.util.List;

import com.atoz.capp.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author caicai.gao
 */
public interface RoleMapper  extends BaseMapper<Role> {
    @Select("select T0.OID, T0.ROLE_NAME, T0.REMARK, T2.PERMISSION_NAME " +
            "from TBL_ROLE T0 left join ROLE_PERMISSION T1 on T1.ROLE_ID = T0.OID " +
            "left join TBL_PERMISSION T2 on T2.OID = T1.PERMISSION_ID order by T0.UPDATE_TIME")
	List<Role> getAll();

}