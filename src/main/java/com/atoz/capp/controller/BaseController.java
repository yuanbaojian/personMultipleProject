package com.atoz.capp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.atoz.capp.common.constant.ConfigConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



/**
 * 基础控制器
 * @author caicai.gao
 */
@Controller
public class BaseController {

	/** 日志对象 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 管理基础路径
	 */

	
	/**
	 * 前端基础路径
	 */

	
	/**
	 * 前端URL后缀
	 */

	
	@GetMapping(value="/login")
	public String control() {
		Subject subject = SecurityUtils.getSubject();
		// 如果已经登录，则跳转到管理首页
		if (subject != null) {
			SecurityUtils.getSubject().logout();
			
		}
		return "auth/login";
	}
	
	/**
	 * home
	 * @return String 跳转路径
	 */
	@GetMapping(value="/home")
	public String home() {
		return "index";
	}

	/**
	 * 客户端返回JSON字符串
	 * @param response  HttpServletResponse
	 * @param object Object
	 * @return IOException
	 */
	String renderString(HttpServletResponse response, Object object) throws IOException {
		return renderString(response, JSON.toJSONString(object));
	}

	/**
	 * 客户端返回字符串
	 * @param response HttpServletResponse
	 * @param string String
	 * @return IOException
	 */
	private String renderString(HttpServletResponse response, String string) throws IOException {
		response.reset();
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(string);
		return null;
	}


}