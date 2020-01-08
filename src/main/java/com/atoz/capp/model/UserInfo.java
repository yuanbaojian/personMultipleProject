package com.atoz.capp.model;

import lombok.Data;

/**
 * 用户简单信息
 * @author caicai.gao
 */
@Data
public class UserInfo {
	/** 用户Id */
	private int userId;
	/** 登陆名称 */
	private String loginName;
 	/** 用户全名称 */
	private String userFullName;
	
}
