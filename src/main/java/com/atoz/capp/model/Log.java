package com.atoz.capp.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志信息模型
 * @author caicai.gao
 */
@Data
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 日志编号 */
	private String id;
	/** 系统编号 */
	private static String system = "3DMPMSystem";
	/** 业务编号 */
	private Integer bussiness;
	/** 类别 */
	private String module;
	/** 类别 */
	private String kind;
	/** 动作 */
	private String action;
	/** 创建人 */
	private String creator;
	/** 创建时间 */
	private Date createTime;
	/** 备注 */
	private String remark;
	/** 日志明细 */
	private String content;

}