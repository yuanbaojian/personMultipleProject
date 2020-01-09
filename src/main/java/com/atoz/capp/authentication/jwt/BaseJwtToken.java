package com.atoz.capp.authentication.jwt;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author yabo.wang
 * @Date 2020/1/8 10:13
 * @Descripyion:使用jwt的token替换shiro原生的token
 */
@Data
public class BaseJwtToken implements AuthenticationToken {

    /**秘钥令牌*/
    private String token;

    public BaseJwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
