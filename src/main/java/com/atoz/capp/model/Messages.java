package com.atoz.capp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 消息模板
 * @author caicai.gao
 */
@Data
@KeySequence("SEQ_MESSAGES")
@TableName("TBL_MESSAGES")
public class Messages {
	@TableId(value = "OID", type = IdType.AUTO)
    private Long messageId;

    private String unauditId;

    private String unauditType;
    
    private Integer userId;
    
    private Integer allMessagesCount;
    
    private Integer mbdTemplateCount;
    
    private Integer standardCount;
    
    private Integer featureCount;
    
	private Integer annotationCount;
    
    private Integer materialCount;
    
    private Integer finishedProductCount;

}