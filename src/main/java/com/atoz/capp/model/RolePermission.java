package com.atoz.capp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 角色权限关联关系
 * @author caicai.gao
 */
@Data
public class RolePermission {
    /** 角色ID */
    @TableId(value = "ROLE_ID", type = IdType.INPUT)
    private Integer roleId;
    /** 权限ID */
    private Integer permissionId;
    /** 权限名称*/
    @TableField(exist = false)
    private String permissionName;

}