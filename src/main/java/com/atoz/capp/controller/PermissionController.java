package com.atoz.capp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.atoz.capp.common.constant.ConfigConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.atoz.capp.common.JsonResult;
import com.atoz.capp.exception.CustomGenericException;
import com.atoz.capp.model.RolePermission;
import com.atoz.capp.model.TreeJsonData;
import com.atoz.capp.service.PermissionServiceI;
import com.atoz.capp.service.RolePermissionServiceI;

/**
 * 权限管理控制类
 * @author caicai.gao
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionServiceI permissionService;

	@Autowired
	private RolePermissionServiceI rolePermissionService;

	/**
	 * 获取角色权限PermissionTree
	 * @param req HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException IOException
	 */
	@PostMapping(value = "/getPermissionTree")
	public void getPermissionTree(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String roleId = req.getParameter("oid");
		List<TreeJsonData> permissionData = permissionService.getPermissionTree(Integer.valueOf(roleId));
		// 以json格式返回数据
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONString(permissionData));
	}

	/**
	 * 角色权限修改后保存
	 * @param req HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException IOException
	 */
	@PostMapping(value = "/savePermissionTree")
	public void savePermissionTree(HttpServletRequest req, HttpServletResponse response) throws IOException {
		JsonResult result = new JsonResult();
		RolePermission rolePermission = new RolePermission();
		try {
			// 获取参数角色id，即表RolePermission中的RoleId
			Integer oid = Integer.valueOf(req.getParameter("oid"));
			rolePermissionService.deleteOriPermission(oid);
			// 获取修改后角色拥有的权限id
			String permissions = req.getParameter("perStr");
			// 将获取的权限id转换成集合
			JSONArray json = JSON.parseArray(permissions);
			// 遍历集合并保存
			for (int i = 0; i < json.size(); i++) {
				Integer permissionId = Integer.valueOf(json.getString(i));
				rolePermission.setPermissionId(permissionId);
				rolePermission.setRoleId(oid);
				rolePermissionService.savePermissionTree(rolePermission);
			}
			result.setCode(1);
		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}

}
