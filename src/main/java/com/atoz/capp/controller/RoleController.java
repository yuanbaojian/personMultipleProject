package com.atoz.capp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atoz.capp.common.constant.ConfigConstants;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.atoz.capp.common.Constants;
import com.atoz.capp.common.JsonResult;
import com.atoz.capp.exception.CustomGenericException;
import com.atoz.capp.model.Role;
import com.atoz.capp.model.User;
import com.atoz.capp.service.RoleServiceI;

/**
 * 角色管理Controller
 * @author caicai.gao
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleServiceI roleService;

	/**
	 * 角色一览URL
	 *
	 */
	@PostMapping(value = "/list")
	public String list() {
		return "role/roleList";
	}
	
	/**
	 * 角色一览列表ajax获取入口
	 * 
	 * @param response HttpServletResponse
	 * @throws IOException IOException
	 */
	@PostMapping(value = "/roles", params = "json")
	public void getRoles(HttpServletResponse response) throws IOException {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		List<Role> l = roleService.getAll();
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(l, ConfigConstants.DATE_FORMAT));
	}

	/**
	 * 角色保存或修改页面URL
	 * 
	 */
	@PostMapping(value = "/save")
	public void save(HttpServletRequest req, HttpServletResponse response) throws IOException {
		User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
		// request值获取
		String oid = req.getParameter("oid");
		String roleName = req.getParameter("roleName");
		String remark = req.getParameter("remark");
		Long createdBy = loginUser.getUserId().longValue();
		Long updatedBy = loginUser.getUserId().longValue();

		JsonResult result = new JsonResult();

		try {
			Role role;
			if (oid != null && !"".equals(oid)) {
				role = roleService.saveRole(0, oid, roleName, remark, createdBy, updatedBy);
			} else {
				role = roleService.saveRole(1, oid, roleName, remark, createdBy, updatedBy);
			}
			result.setCode(Constants.ERROR_CODE_NONE);
			result.put("role", role);
		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}

	/**
	 * 角色删除页面URL
	 * 
	 */
	@PostMapping(value = "/delete")
	public void delete(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String oid = req.getParameter("oid");
		JsonResult result = new JsonResult();
		try {
				if (oid != null && !"".equals(oid)) {
				   if (roleService.total(Long.valueOf(oid)) == 0) {
					   // 删除角色与权限的关联
					   roleService.deleteRolePermission(Integer.valueOf(oid));
					   // 删除角色
					   roleService.deleteByPrimaryKey(Long.valueOf(oid));
					   result.setCode(Constants.ERROR_CODE_NONE);
				   } else {
						// ORA-02292-找到子记录，表明该角色被引用，不可以删除
						result.setCode(1);
						result.put(ConfigConstants.ERROR_MSG, "该角色已经被引用，不可以删除！");
				   }
				}
		} catch (CustomGenericException e) {
			result.setCode(e.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, e.getMessage());
		}

		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}

	
}
