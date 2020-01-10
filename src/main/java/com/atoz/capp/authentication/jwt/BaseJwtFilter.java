package com.atoz.capp.authentication.jwt;

import com.atoz.capp.common.constant.ConfigConstants;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author yabo.wang
 * @Date 2020/1/8 13:12
 * @Descripyion:
 */
public class BaseJwtFilter extends BasicHttpAuthenticationFilter {
    private static String LOGIN_SIGN = "Authorization";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                System.out.println("登录权限不足！");
                return false;
            }
        }
        return true;
    }
    /***   登录失败怎么近???
     * @param request
     * @param response
     * @Description:重写该方法避免循环调用doGetAuthenticationInfo方法
     * @author wangyabo
     * @date 2020/1/9 10:46
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
       this.sendChallenge(request,response);
       return false;
    }

    /***
     * @param request
     * @param response
     * @Description:判断用户是否想要登录即请求头中是否存在授权字段
     * @author wangyabo
     * @date 2020/1/9 10:41
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String authorization = getAuthzHeader(request);
        return authorization != null;
    }


    /***
     * @param request
     * @param response
     * @Description:getSubject(request, response).login(token) 就是触发 Shiro Realm 自身的登录控制
     * executeLogin() 始终返回 true 的原因是因为具体的是否登录成功的判断，需要在 Realm 中手动实现，此处不做统一判断
     * @author wangyabo
     * @date 2020/1/8 13:35
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        //得到授权的token
        String header = req.getHeader(LOGIN_SIGN);
        BaseJwtToken token = new BaseJwtToken(header);
        //提交给realm进行认证操作,如果失败则抛出异常被捕获
        getSubject(request, response).login(token);
        //认证成功返回true
        return true;
    }
    /***
     * @param request
     * @param response
     * @Description:对于跨域请求的支持
     * @author wangyabo
     * @date 2020/1/9 10:48
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        return super.preHandle(request, response);
    }

    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpResponse.setCharacterEncoding("utf-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        final String message = "未认证，请在系统进行认证";
        try (PrintWriter out = httpResponse.getWriter()) {
            String responseJson = "{\"message\":\"" + message + "\"}";
            out.print(responseJson);
        } catch (IOException e) {

        }
        return false;
    }
}
