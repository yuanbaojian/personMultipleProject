package com.atoz.capp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.diboot.core.binding.annotation.BindField;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色关联关系
 * @author caicai.gao
 */
@Data
@TableName("TBL_USER_ROLE")
public class UserRole implements Serializable{
    /** 用户ID */
    @TableId(value = "USER_ID", type = IdType.INPUT)
    private Long userId;
    /** 角色ID */
    private Long roleId;
    /** 角色名称 */
    @TableField(exist = false)
    /** 支持关联条件+附加条件绑定字段 */
    @BindField(entity=Role.class, field="NAME", condition="OID=ROLE_ID")
    private String roleName;

}