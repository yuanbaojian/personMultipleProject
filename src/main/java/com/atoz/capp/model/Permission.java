package com.atoz.capp.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 权限模型
 * @author caicai.gao
 */
@Data
@KeySequence("SEQ_PERMISSION")
@TableName("TBL_PERMISSION")
public class Permission {
    /** 权限ID */
    @TableId(value = "OID", type = IdType.AUTO)
    private Integer oid;
    /** 权限编码 */
    private String permissionCode;
    /** 权限名称 */
    private String permissionName;
    /** 父节点ID */
    private Integer parentId;
    /** 顺序号 */
    private Integer sortId;
    /** 角色权限关联关系 */
    @TableField(exist = false)
    private RolePermission rolePermission;

}