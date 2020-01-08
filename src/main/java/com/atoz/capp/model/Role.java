package com.atoz.capp.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 角色模型
 * @author caicai.gao
 */
@Data
@KeySequence("SEQ_ROLE")
@TableName("TBL_ROLE")
public class Role {
    /** 角色ID */
    @TableId(value = "OID", type = IdType.AUTO)
    private Long oid;
    /** 角色名称 */
    private String roleName;
    /** 角色编号 */
    private String roleNo;
    /** 备注 */
    private String remark;
    /** 创建者 */
    private Long createdBy;
    /** 创建时间 */
    private Date createTime;
    /** 更新者 */
    private Long updatedBy;
    /** 更新时间 */
    private Date updateTime;
    /** 创建者名称 */
    @TableField(exist = false)
    private String createdUser;
    /** 更新者名称 */
    @TableField(exist = false)
    private String updatedUser;
    /** 角色下的权限列表*/
    @TableField(exist = false)
    private List<RolePermission> rolePermission;

}