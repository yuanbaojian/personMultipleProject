package com.atoz.capp.model;

import lombok.Data;

import java.util.Date;
/**
 * MBD模板属性列表treetable数据格式(tag + attr)
 * @author caicai.gao  
 * @date 2017年3月14日  
 *
 */
@Data
public class TemplateTagAttr {
	/** 通用-start */
	/** 节点id */
	private String id;
	/** 父节点id */
	private String parentid;
	/** 是否有自节点 */
	private boolean hasChild;
	/** 节点名称 */
	private String name;
	/** 通用-end */
	
	/** tag（分类）-start */
	/** 所属MBD模型ID */
	private Long templateId;
	/** 顺序号 */
    private Short tagSort;
	/** 创建者 */
    private Long tagCreatedBy;
	/** 创建时间 */
    private Date tagCreateTime;
	/** 更新者 */
    private Long tagUpdatedBy;
	/** 更新时间 */
    private Date tagUpdateTime;
    /** tag-end */
	
    /** attr（属性）-start */
	/** 顺序号 */
    private Short attrSort;
	/** 属性值类型,0-文字类，1-数字类 */
    private Short attrType;
	/** 属性默认值 */
    private String attrDefaultValue;
	/** 所属MBD模型ID */
    private Long mbdTemplate;
	/** 创建者 */
    private Long attrCreatedBy;
	/** 创建时间 */
    private Date attrCreateTime;
	/** 更新者 */
    private Long attrUpdatedBy;
	/** 更新时间 */
    private Date attrUpdateTime;
	/** 是否多值0-否，1-是 */
	private Short isMultiple;
	/** 属性值类型中文名称 */
    private String attrTypeName;
	/** 是否多值-中文 */
	private String isMultipleName;
	/** attr-end */

}
