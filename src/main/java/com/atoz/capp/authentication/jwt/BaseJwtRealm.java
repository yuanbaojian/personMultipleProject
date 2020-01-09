package com.atoz.capp.authentication.jwt;

import com.atoz.capp.model.Permission;
import com.atoz.capp.model.User;
import com.atoz.capp.service.UserServiceI;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author yabo.wang
 * @Date 2020/1/8 13:42
 * @Descripyion:
 */
public class BaseJwtRealm extends AuthorizingRealm {
    @Autowired
    HttpServletRequest request;
    @Autowired
    UserServiceI userService;

    /***
     * @param authToken
     * @Description:shiro登录认证
     * @author wangyabo 
     * @date 2020/1/8 13:44
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        String token = (String)authToken.getCredentials();
        String userName = JWTUtil.getUserName(token);
        User loginUser = new User();
        loginUser.setLoginName(userName);
        loginUser = new User();
        // 4.若用户不存在，则抛出异常
        if (loginUser == null) {
            throw new UnknownAccountException();
        }
        // 5.验证用户状态，是否已禁用,status=1,禁用，抛出异常
        if (loginUser.getStatus() == 1 ) {
            throw new LockedAccountException();
        }
        if(loginUser!= null){
            //密码验证
            if(!JWTUtil.verify(token,userName,loginUser.getPassword())){
                throw new UnknownAccountException();
            }
                return new SimpleAuthenticationInfo(loginUser,loginUser.getPassword(),getName());
        }else{
            throw new UnknownAccountException();
        }


    }

    /***
     * @param principals
     * @Description:shiro授权
     * @author wangyabo
     * @date 2020/1/8 13:44
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User loginUser = (User) getAvailablePrincipal(principals);
        // 权限名的集合
        Set<String> permissions = new HashSet<>();
        loginUser = userService.checkUser(loginUser);
        List<Permission> lstPermission = userService.selectPermissionByUser(loginUser.getUserId());
        for (Permission p : lstPermission) {
            permissions.add(p.getPermissionCode());
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 返回权限集合
        simpleAuthorizationInfo.addStringPermissions(permissions);
        HttpSession session = request.getSession();
        session.setAttribute("permissions", permissions);
        return simpleAuthorizationInfo;
    }
    /***
     * @param token
     * @Description:重写Realm的 supports()方法是通过 JWT 进行登录判断的关键
     *  因为前文中创建了 BaseJwtToken 用于替换 Shiro 原生 token
     *  所以必须在此方法中显式的进行替换，否则在进行判断时会一直失败
     * @author wangyabo
     * @date 2020/1/8 13:49
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof BaseJwtToken;
    }
}
