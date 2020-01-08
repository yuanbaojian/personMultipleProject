package com.atoz.capp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
/**
 * MBD模板属性分类标签
 * @author caicai.gao
 */
@Data
@KeySequence("SEQ_TAG")
@TableName("TBL_TEMPLATE_TAG")
public class TemplateTag {
    /** */
    @TableId(value = "OID", type = IdType.AUTO)
    private Long oid;
    /** */
    private String tagName;
    /** */
    private Long parentTag;
    /** */
    private Long templateId;
    /** */
    private Short tagSort;
    /** */
    private Long createdBy;
    /** */
    private Date createTime;
    /** */
    private Long updatedBy;
    /** */
    private Date updateTime;
    /** */
    private String templateAttrName;
    /** */
    private String templateAttrValue;

}