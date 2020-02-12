package com.ybj.cbt.auth;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm {

//    @Autowired
//    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //密码验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        String username = (String) token.getPrincipal();
//        UsernamePasswordToken passwordTokentoken = (UsernamePasswordToken) token;
//
//        User loginUser=userService.findByName(username);
//
//        if (loginUser==null) {
//            throw new UnknownAccountException("账户不存在!");
//        }
//        return new SimpleAuthenticationInfo(loginUser, loginUser.getPassword(), getName());
        return null;
    }
}

