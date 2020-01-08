package com.atoz.capp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * MBD模板对象
 * @author caicai.gao
 */
@Data
@KeySequence("SEQ_MBDTemplate")
@TableName("TBL_MBD_TEMPLATE")
public class MBDTemplate {
    /** MBD模板ID */
    @TableId(value = "OID", type = IdType.AUTO)
    private Long oid;
    /**  */
    private String templateName;
    /**  */
    private String templateCode;
    /**  */
    private Short status;
    /**  */
    private Long createdBy;
    /**  */
    private Date createTime;
    /**  */
    private Long updatedBy;
    /**  */
    private Date updateTime;
    /**  */
    private String statusName;
    /**  */
    private String createdByName;
    /**  */
    private String updatedByName;
    /**  */
    private Long auditedBy;
    /**  */
    private Date auditTime;
    /**  */
    private String auditedByName;
    /**  */
    private  MBDHistory mbdHistory;
    /**  */
    private String submitedByName;
    /**  */
    private Date submitTime;
    /**  */
    private String opinion;
    /**  */
    private Long belongcategory;

}