package com.atoz.capp.model;

import lombok.Data;

import java.util.Date;

/**
 * 发布记录
 * @author caicai.gao
 */
@Data
public class ReleasedRecord {
	/** 操作人员 */
	private UserInfo releasedBy;
	/** 操作时间 */
	private Date releasedTime;
	/** 操作 */
	private String action;
	/** 意见 */
	private String opinion;
	
}
