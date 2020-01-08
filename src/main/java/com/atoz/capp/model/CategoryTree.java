package com.atoz.capp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 分类树
 * @author caicai.gao
 */
@Data
@KeySequence("SEQ_TREE")
@TableName("TBL_CATEGORY")
public class CategoryTree {
    /** 分类ID */
    @TableId(value = "OID", type = IdType.AUTO)
    private Long oid;
    /** 分类名称 */
    private String name;
    /** 节点类型 */
    private String treeType;
    /** 父节点ID */
    private Long parentId;
    /** 顺序号 */
    private Short sortId;
    /** 创建者 */
    private Long createdBy;
    /** 创建时间 */
    private Date createTime;
    /** 更新者 */
    private Long updatedBy;
    /** 更新时间 */
    private Date updateTime;

}