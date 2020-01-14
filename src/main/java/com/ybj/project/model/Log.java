package com.ybj.project.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ybj
 * @since 2020-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_log")
public class Log implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 日志记录表主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 操作描述
     */
    private String actionDescription;

    /**
     * 请求的类名
     */
    private String className;

    /**
     * 请求的参数
     */
    private String params;

    /**
     * 用户ip
     */
    private String ip;

    /**
     * 请求时间
     */
    private LocalDateTime operateTime;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 日志类型
     */
    private String operateType;


}
