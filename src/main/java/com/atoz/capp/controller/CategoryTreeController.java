package com.atoz.capp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atoz.capp.common.constant.BusniessConstants;
import com.atoz.capp.common.constant.ConfigConstants;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.atoz.capp.common.Constants;
import com.atoz.capp.common.JsonResult;
import com.atoz.capp.exception.CustomGenericException;
import com.atoz.capp.model.CategoryTree;
import com.atoz.capp.model.TrainningTreeData;
import com.atoz.capp.model.User;
import com.atoz.capp.service.CategoryTreeServiceI;

/**
 * 分类树控制器
 * @author caicai.gao
 */
@Controller
@RequestMapping("/categoryTree")
public class CategoryTreeController {

	@Autowired
	private CategoryTreeServiceI categoryTreeService;

	/** 日志对象 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 获取分类树
	 * 
	 * @param req  HttpServletRequest
	 * @param response HttpServletResponse
	 */
	@PostMapping(value = "/getCategoryTree")
	public void getCategoryTree (HttpServletRequest req, HttpServletResponse response) throws IOException {
		String parentId =req.getParameter("parent");
		String treeType =req.getParameter(BusniessConstants.TREE_TYPE);
		logger.info("分类树类型：{}", treeType);
		List<TrainningTreeData> treeData = categoryTreeService.getCategoryTree(parentId,treeType);
		// 以json格式返回数据
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(treeData, ConfigConstants.DATE_FORMAT));

	}

	/**
	 * 分类保存
	 * @param req HttpServletRequest
	 * @param response HttpServletResponse
	 * @param categoryTree CategoryTree
	 * @throws IOException IOException
	 */
	@PostMapping(value = "/saveCategory")
	public void saveCategory(HttpServletRequest req, HttpServletResponse response, CategoryTree categoryTree) throws IOException {
		String flg =req.getParameter("flg");
		JsonResult result = new JsonResult();
		User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
		try {
			if (flg.equals("false")) {
				// 新增分类
				categoryTreeService.insertCategory(categoryTree, loginUser);
			} else if (flg.equals("true")) {
				// 修改分类
				categoryTreeService.updateCategory(categoryTree, loginUser);
			}
			result.setCode(Constants.ERROR_CODE_NONE);
			result.put("user", loginUser);

		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}


	/**
	 * 分类删除
	 * @param req HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException IOException
	 */
	@PostMapping(value="/deleteCategory")
	public void deleteCategory(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String id = req.getParameter("id");
		String treeType = req.getParameter(BusniessConstants.TREE_TYPE);
		JsonResult result = new JsonResult();
		try {
			if (id != null && id.length()>0) {
				Long oid = Long.valueOf(id);
				// 需实现级联删除
				categoryTreeService.deleteCategory(oid,treeType);
				result.setCode(Constants.ERROR_CODE_NONE);
			} 
		}  catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}

	/**
	 * 拖动标签，重组结构
	 * @param req HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException IOException
	 */
	@PostMapping(value="/dragNode")
	public void dragNode(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String oid = req.getParameter("id");
		String oldParentId = req.getParameter("oldParent");
		String parentId = req.getParameter("parent");
		String position = req.getParameter("position");
		String name = req.getParameter("name");
		String treeType = req.getParameter(BusniessConstants.TREE_TYPE);
		Long sortId = Long.parseLong(position);
		sortId = sortId + 1;
		JsonResult result = new JsonResult();
		
		try {
			if (oid != null && parentId != null) {
				categoryTreeService.dragNode(oid, oldParentId, parentId,name,treeType,sortId);
				result.setCode(Constants.ERROR_CODE_NONE);
			} 
		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}
	
}
