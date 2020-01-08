package com.atoz.capp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atoz.capp.common.constant.ConfigConstants;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.atoz.capp.common.Constants;
import com.atoz.capp.common.JsonResult;
import com.atoz.capp.exception.CustomGenericException;
import com.atoz.capp.model.User;
import com.atoz.capp.model.UserRole;
import com.atoz.capp.server.util.MD5EncryptionUtil;
import com.atoz.capp.service.UserServiceI;

/**
 * 用户管理控制类
 * @author caicai.gao
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserServiceI userService;

	/**
	 * 用户一览URL
	 * @return String
	 */
	@PostMapping(value = "/userlist")
	public String list() {
		return "user/userlist";
	}

	/**
	 * 用户一览列表ajax获取入口
	 * @param response HttpServletResponse
	 * @throws IOException IOException
	 */
	@PostMapping(value = "/users")
	public void getUsers(HttpServletResponse response) throws IOException {
		List<User> userList = userService.getAll();
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(userList, ConfigConstants.DATE_FORMAT));
	}
	
	
	/**
	 * 获取审核人员列表
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/searchAuditor", params = "json")
	public void searchAuditor(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String type = req.getParameter("type");
		List<User> l = userService.searchAuditor(type);
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(l, ConfigConstants.DATE_FORMAT));
	}
	
	/**
	 * 获取转交人员列表
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/searchUser", params = "json")
	public void searchUser(HttpServletRequest req, HttpServletResponse response) throws IOException {
		List<User> l = userService.searchUser();
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(l, ConfigConstants.DATE_FORMAT));
	}
	
	/**
	 * 删除用户（逻辑删除，将用户记录中是否删除状态更改为1-即已删除）
	 * 
	 * @return
	 */
	@PostMapping(value = "/delete")
	public void delete(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String userId = req.getParameter("userId");
		// 消息
		JsonResult result = new JsonResult();
		try {
			result.setCode(Constants.ERROR_CODE_NONE);
			User deleteUser = userService.selectByPrimaryKey(Integer.parseInt(userId));
			deleteUser.setDeleted(1);
			if (deleteUser.getLoginName().equals(Constants.ADMIN_USER)){
				result.setCode(Constants.ERROR_CODE_BUSINESS);
				result.put(ConfigConstants.ERROR_MSG, Constants.ORG_STRUC_MSG004);
			} else {
				// 先删除与角色的关联信息
				userService.deleteUserRole(Integer.parseInt(userId));
				// 更新是否已删除的状态
				userService.updateByPrimaryKeySelective(deleteUser);
			}
		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}

	/**
	 * 用户保存
	 * 
	 * @return
	 */
	@PostMapping(value = "/save")
	public void save(HttpServletRequest req, HttpServletResponse response, User user) throws IOException {
		JsonResult result = new JsonResult();
		User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
		try {
			user.setCreateBy(loginUser.getUserId());
			user.setUpdatedBy(loginUser.getUserId());
			user.setDeleted(0);
			if (user.getUserId()!=null) {
				// 修改用户
				user = userService.saveUser(1, user);
			} else {
				// 新建用户
				user = userService.saveUser(0, user);
			}

			result.setCode(Constants.ERROR_CODE_NONE);
			result.put("user", user);

		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}

	/**
	 * 
	 * 禁用用户
	 * 
	 * @param req HttpServletRequest
	 * @param response HttpServletResponse
	 */
	@PostMapping(value = "/forbidden")
	public void forbidden(HttpServletRequest req, HttpServletResponse response) {
		String userId = req.getParameter("userId");
		String[] userList = userId.split(",");
		String id;

		for (int i = 0; i < userList.length; i++) {
			id = userList[i];
			if (id == null || "".equals(id)) {
				continue;
			}
			User forbiddenUserById = userService.selectByPrimaryKey(Integer.parseInt(id));

			forbiddenUserById.setStatus(1);

			userService.updateByPrimaryKeySelective(forbiddenUserById);
		}

	}

	/**
	 * 启用用户
	 * 
	 * @param req HttpServletRequest
	 * @param response HttpServletResponse
	 */

	@PostMapping(value = "/enable")
	public void enable(HttpServletRequest req, HttpServletResponse response)  {
		String userId = req.getParameter("userId");
		String[] userList = userId.split(",");
		String id;
		for (int i = 0; i < userList.length; i++) {
			id = userList[i];
			if (id == null || "".equals(id)) {
				continue;
			}
			User enableUserById = userService.selectByPrimaryKey(Integer.parseInt(id));
			enableUserById.setStatus(0);
			userService.updateByPrimaryKeySelective(enableUserById);
		}

	}

	/**
	 * 修改密码
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@PostMapping(value = "/updatePassword")
	public void updatePassword(HttpServletRequest req, HttpServletResponse response) throws IOException {
		JsonResult result = new JsonResult();
		String loginname = req.getParameter("loginName");
		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");
		User user = new User();
		user.setLoginName(loginname);
		user.setPassword(MD5EncryptionUtil.convertMD5(oldPassword));
		// 用户验证
		User loginUser = userService.checkUser(user);
		if (loginUser != null) {
			result.setCode(0);
			loginUser.setPassword(MD5EncryptionUtil.convertMD5(newPassword));
			userService.saveUser(1, loginUser);
		} else {
			result.setCode(1);
			result.put(ConfigConstants.ERROR_MSG, "旧密码错误，请重新输入");
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}

	/**
	 * 重置密码
	 * 
	 * @param req HttpServletRequest
	 * @param response HttpServletResponse
	 */
	@PostMapping(value = "/resetPassword")
	public void resetPassword(HttpServletRequest req, HttpServletResponse response) {
		String userId = req.getParameter("userId");
		User oldUser = userService.selectByPrimaryKey(Integer.parseInt(userId));
		oldUser.setPassword(MD5EncryptionUtil.convertMD5(Constants.PASSWORD));
		oldUser.setUpdateTime(new Date());
		userService.updateByPrimaryKeySelective(oldUser);
	}
	

	/**
	 * 通过搜索获取审核人
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@PostMapping(value = "/auditors", params = "json")
	public void getAuditor(HttpServletRequest req, HttpServletResponse response) throws IOException{
		Integer loginUserId=Integer.parseInt(req.getParameter("loginUserId"));
		List<User> l = userService.getUsersExcludeLoginUser(loginUserId);
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(l, ConfigConstants.DATE_FORMAT));
	}
	
	/**
	 * 给用户分配角色
	 */
	@PostMapping(value = "/assignRole")
	public void assignRole(String roles, Integer userId) {
		// 选择的用户
		List<UserRole> list = JSON.parseArray(roles, UserRole.class);
		userService.assignRole(list, userId);
	}
	
	/**
	 * 获取用户已分配角色
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@PostMapping(value = "/getUserSelectedRole")
	public  void getUserSelectedRole(HttpServletRequest req, HttpServletResponse response, String roles, Integer userId) throws IOException {
		// 选择的用户
		List<Integer> list = userService.getUserSelectedRole( userId);
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(list, ConfigConstants.DATE_FORMAT));
		
	}
	
	
	/**
	 * 检查用户是否具有指定权限
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@PostMapping(value = "/hasPermission")
	public void hasPermission(HttpServletRequest req, HttpServletResponse response) throws IOException {
		JsonResult result = new JsonResult();
		String permissionCode = req.getParameter("permissionCode");
		User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
		try {
			Boolean hasPermission = userService.hasPermission(loginUser.getUserId(),permissionCode);

			result.setCode(Constants.ERROR_CODE_NONE);
			result.put("hasPermission", hasPermission);

		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}
	
}