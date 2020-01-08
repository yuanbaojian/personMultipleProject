package com.atoz.capp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * MBD模板属性
 * @author caicai.gao
 */
@Data
@KeySequence("SEQ_ATTR")
@TableName("TBL_TEMPLATE_ATTR")
public class TemplateAttr {
	/**  */
	@TableId(value = "OID", type = IdType.AUTO)
    private Long oid;
	/**  */
	private String attrName;
	/**  */
	private Short attrSort;
	/**  */
	private Short attrType;
	/**  */
	private String attrDefaultValue;
	/**  */
	private Long attrTag;
	/**  */
	private Long mbdTemplate;
	/**  */
	private Long createdBy;
	/**  */
	private Date createTime;
	/**  */
	private Long updatedBy;
	/**  */
	private Date updateTime;
	/**  */
	private Short isMultiple;
	/**  */
	private String attrTypeName;
	/**  */
	private String isMultipleName;

}