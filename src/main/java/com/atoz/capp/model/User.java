package com.atoz.capp.model;

import com.baomidou.mybatisplus.annotation.*;
import com.diboot.core.binding.annotation.BindEntityList;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户模型
 * @author caicai.gao
 */

@Data
@KeySequence("SEQ_USER")
/**
 * TableName 用于解决数据库表名与model名称不匹配的问题
 */
@TableName("TBL_USER")
public class User implements Serializable{
	/** 用户ID */
	/**
	 * TableId 用于解决主键不是ID的情况
	 */
	@TableId(value = "USER_ID", type = IdType.AUTO)
	private Integer userId;
	/** 登录名 */
	/**
	 * TableField 用于解决model变量名与数据库字段不匹配的问题
	 */
	@TableField("LOGIN_NAME")
	private String loginName;
	/** 用户姓名 */
	private String userFullName;
	/** 密码-加密 */
	private String password;
	/** 邮箱 */
	private String email;
	/** 联系方式 */
	private String mobilePhone;
	/** 状态 0-启用 1-禁用 */
	private Integer status;
	/** 创建者 */
	private Integer createBy;
	/** 创建时间 */
	private Date createTime;
	/** 更新者 */
	private Integer updatedBy;
	/** 更新时间 */
	private Date updateTime;
	/** 登录失败次数 */
	private Integer failCount;
	/** 上次成功登录时间 */
	private Date lastLoginTime;
	/** 对于数据库中不存在的字段的三种处理方法
	 * 1. transient  不参与序列化
	 * 2. static 标识为静态变量 lombok不为静态变量生成get/set方法
	 * 3. @TableField(exist=false)  推荐使用
	 */
	/** 是否记住登录账号-数据库中没有对应字段 */
	@TableField(exist = false)
	private boolean rememberMe;
	/** 状态名称 0-启用 1-禁用 */
	@TableField(exist = false)
	private String statusName;
	/** 密级 */
	private Integer securityLevel;
	/** 密级名称 */
	@TableField(exist = false)
	private String securityLevelName;
	/** 用户关联的角色 */
	@TableField(exist = false)
	/** 支持关联条件+附加条件绑定多个Entity */
	@BindEntityList(entity = UserRole.class, condition = "USER_ID=USER_ID")
	private List<UserRole> userRoles;
	/** 删除标志 */
	private Integer deleted;
	/** 密码 */
	@TableField(exist = false)
	private String pwd;

}