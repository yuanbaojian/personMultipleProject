
package com.atoz.capp.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atoz.capp.common.constant.ConfigConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.atoz.capp.common.Constants;
import com.atoz.capp.common.JsonResult;
import com.atoz.capp.common.utils.AesEncryptUtil;
import com.atoz.capp.common.utils.CacheUtils;
import com.atoz.capp.common.utils.CookieUtils;
import com.atoz.capp.common.utils.Global;
import com.atoz.capp.model.Messages;
import com.atoz.capp.model.User;
import com.atoz.capp.server.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import com.atoz.capp.service.MessagesServiceI;
import com.atoz.capp.service.UserServiceI;

/**
 * 登录Controller
 * @author kuaiduoli
 * @version 2017-3-2
 */
@Controller
@RequestMapping(value = "/auth")
public class LoginController extends BaseController{
	
	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private MessagesServiceI messagesService;
	
	/**
	 * 管理登录
	 * @throws IOException 
	 */
	@PostMapping(value = "/login")
	public void login(HttpServletRequest request, HttpServletResponse response,  HttpSession session,User user) throws IOException {
		Subject currentUser = SecurityUtils.getSubject();
		logger.info("isAuthenticated = {}", currentUser.isAuthenticated());
		String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
		JsonResult result = new JsonResult();
		if (shiroLoginFailure!=null) {
			if (shiroLoginFailure.equals(UnknownAccountException.class.getName())){
				logger.info("对用户{}进行登录验证..验证未通过,未知账户", user.getLoginName());
                result.setCode(1);
				result.put(ConfigConstants.ERROR_MSG, "验证未通过,未知账户");
			} else if (shiroLoginFailure.equals(IncorrectCredentialsException.class.getName())){
				logger.info("对用户{}进行登录验证..验证未通过,错误的凭证", user.getLoginName());
                result.setCode(1);
				result.put(ConfigConstants.ERROR_MSG, "验证未通过,错误的凭证");
			} else if (shiroLoginFailure.equals(LockedAccountException.class.getName())){
				logger.info("对用户{}进行登录验证..验证未通过,账户已禁用", user.getLoginName());
	            result.setCode(1);
				result.put(ConfigConstants.ERROR_MSG, "验证未通过,账户已禁用");
			} else if (shiroLoginFailure.equals(ExcessiveAttemptsException.class.getName())){
				logger.info("对用户{}进行登录验证..验证未通过,错误次数过多", user.getLoginName());
                result.setCode(1);
				result.put(ConfigConstants.ERROR_MSG, "验证未通过,错误次数过多");
			} else if (shiroLoginFailure.equals(AuthenticationException.class.getName())){
				result.setCode(1);
				result.put(ConfigConstants.ERROR_MSG, "用户名或密码错误");
				logger.info("用户名或密码错误");
			} else {
				result.setCode(1);
				result.put(ConfigConstants.ERROR_MSG, "未知错误");
				logger.info("未知错误");
			}
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}

	/**
	 * 登陆成功，设置会话信息，进入首页
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @param session HttpSession
	 * @return ModelAndView 返回类型
	 */
	@GetMapping(value = "/arriveHomePage")
	public ModelAndView arriveHomePage(HttpServletRequest req,HttpServletResponse res, HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		User userInfo = (User) subject.getPrincipal();
		String rootPath = PropertiesUtil.getMsgsMap().get(Constants.STOREHOUSE_PATH);
		String modelUrl = rootPath.substring(rootPath.lastIndexOf('\\') + 1) + '/';
		// 将模型存储路径信息存入session
		session.setAttribute("modelUrl", modelUrl);
		User suser = userService.getUserByLoginName(userInfo.getLoginName());
		session.setAttribute("suser", suser);
		session.setAttribute("userName", userInfo.getLoginName());
		// 更新消息个数
		Messages messagesInfos = messagesService.selectByUser(suser.getUserId());
		if (messagesInfos!=null) {
			// 响应首页信息提取-待审核消息总数
			req.getSession().setAttribute("AllMessagesCount", messagesInfos.getAllMessagesCount());
			// 响应首页信息提取-待审核MBD模板数
			req.getSession().setAttribute("MBDTemplateCount", messagesInfos.getMbdTemplateCount());
			// 响应首页信息提取-待审核标准数
			req.getSession().setAttribute("StandardCount", messagesInfos.getStandardCount());
			// 响应首页信息提取-待审核成品件数
			req.getSession().setAttribute("FinishedCount", messagesInfos.getFinishedProductCount());
			// 响应首页信息提取-待审核注释数
			req.getSession().setAttribute("AnnotationCount", messagesInfos.getAnnotationCount());
			// 响应首页信息提取-待审核材料数
			req.getSession().setAttribute("MaterialCount", messagesInfos.getMaterialCount());
		} else {
			// 响应首页信息提取-待审核消息总数
			req.getSession().setAttribute("AllMessagesCount", 0);
			// 响应首页信息提取-待审核工装模板数
			req.getSession().setAttribute("ToolingTemplateCount", 0);
			// 响应首页信息提取-待审核标准数
			req.getSession().setAttribute("StandardCount", 0);
			// 响应首页信息提取-待审核成品件数
			req.getSession().setAttribute("FinishedCount", 0);
			// 响应首页信息提取-待审核注释数
			req.getSession().setAttribute("AnnotationCount", 0);
			// 响应首页信息提取-待审核材料数
			req.getSession().setAttribute("MaterialCount", 0);
		}
		
		String key = "toolingDesignant";
		try {
			Cookie c = new Cookie("td_ue",
					URLEncoder.encode(Objects.requireNonNull(AesEncryptUtil.encrypt(userInfo.getLoginName(), key, key)), ConfigConstants.CHARACTER_ENCODING));
			c.setMaxAge(30 * 24 * 60 * 60);
			c.setPath("/");
			res.addCookie(c);
			Cookie coo = new Cookie("td_pwd",
					URLEncoder.encode(Objects.requireNonNull(AesEncryptUtil.encrypt(userInfo.getPwd(), key, key)),ConfigConstants.CHARACTER_ENCODING));
			coo.setMaxAge(30 * 24 * 60 * 60);
			coo.setPath("/");
			res.addCookie(coo);
		} catch (Exception e) {
			logger.debug(e.getMessage());

		}
		return new ModelAndView("redirect:/home");
	}

	/**
	 * 注销
	 * @throws IOException 
	 */
	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		// 如果已经登录，则跳转到管理首页
		if (subject != null) {
			SecurityUtils.getSubject().logout();
			
		}
	   // 如果是手机客户端退出跳转到login，则返回JSON字符串
			String ajax = request.getParameter("__ajax");
			if (	ajax!=null) {
				model.addAttribute("success", "1");
				model.addAttribute("msg", "退出成功");
				return renderString(response, model);
			}
		 return "redirect:/login";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			} else if (StringUtils.equals(logined, "true")){
				SecurityUtils.getSubject().logout();
				return "redirect:"  + "/login";
			}
		}
		
		// 默认风格
		String indexStyle = "default";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
				continue;
			}
			if (cookie.getName().equalsIgnoreCase("theme")) {
				indexStyle = cookie.getValue();
			}
		}
		// 要添加自己的风格，复制下面三行即可
		if (StringUtils.isNotEmpty(indexStyle)
				&& indexStyle.equalsIgnoreCase("ace")) {
			return "modules/sys/sysIndex-ace";
		}
		
		return "modules/sys/sysIndex";
	}
	
	/**
	 * 获取主题方案
	 */
	@PostMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		} else {
			CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}

	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean) {
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null) {
			loginFailMap = new HashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null) {
			loginFailNum = 0;
		}
		if (isFail) {
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean) {
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}

}
