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
@TableName("sys_login_log")
public class LoginLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 唯一主键
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登陆时间
     */
    private LocalDateTime loginTime;

    /**
     * 登陆地址
     */
    private String location;

    /**
     * 登陆ip
     */
    private String ip;


}
