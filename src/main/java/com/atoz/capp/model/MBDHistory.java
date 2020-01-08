package com.atoz.capp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * MBD模板发布历史
 * @author caicai.gao
 */
@Data
@KeySequence("SEQ_MBDTemplate_History")
@TableName("TBL_MBD_HISTORY")
public class MBDHistory {
    /**  */
    @TableId(value = "OID", type = IdType.AUTO)
    private Long oid;
    /**  */
    private Long mbdOid;
    /**  */
    private String action;
    /**  */
    private Long releasedBy;
    /**  */
    private Date releaseTime;
    /**  */
    private String opinion;
    /**  */
    private String releasedByName;

}