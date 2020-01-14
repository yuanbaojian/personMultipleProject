package com.ybj.project.authentication.shiro;

import com.ybj.project.authentication.jwt.JWTUtil;
import org.apache.shiro.SecurityUtils;

/**
 * shiro工具类
 * @author melo、lh
 * @createTime 2019-10-30 16:08:27
 */

public class ShiroUtils {

    /**
     * 得到当前用户名
     * @return java.lang.String
     * @author melo、lh
     * @createTime 2019-10-30 16:40:20
     */
    public static String getUsername(){
        //得到token
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        // 得到用户名
        return JWTUtil.getUsername(token.toString());
    }
}
