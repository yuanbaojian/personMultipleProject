package com.atoz.capp.interceptor;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * SessionTimeOutInterceptor
 * @author duoli.kuai
 */
public class SessionTimeOutInterceptor extends HandlerInterceptorAdapter {
	/** 不需要拦截的url */
	private List<String> excludeRegExp;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			// 如果当前访问地址是不需要拦截的url，直接通过
			if ("/auth/login".equals(url)){
				return true;
			}
			for (String urlReg : excludeRegExp) {
				// 如果当前访问地址是不需要拦截的url，直接通过
				if (Pattern.compile(urlReg.trim()).matcher(url).find()) {
					return true;
				}
			}

			// 如果是ajax请求响应头会有，x-requested-with
            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {

				// 在响应头设置session状态
                response.setHeader("sessionStatus", "timeout");
    			return false;
            } else {
            	request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    			return false;
            }
        }
		return true;
	}

	public List<String> getExcludeRegExp() {
		return excludeRegExp;
	}

	public void setExcludeRegExp(List<String> excludeRegExp) {
		this.excludeRegExp = excludeRegExp;
	}

}
