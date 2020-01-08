package com.atoz.capp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atoz.capp.common.constant.ConfigConstants;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.atoz.capp.common.Constants;
import com.atoz.capp.common.JsonResult;
import com.atoz.capp.common.utils.StringUtils;
import com.atoz.capp.exception.CustomGenericException;
import com.atoz.capp.model.MBDHistory;
import com.atoz.capp.model.MBDTemplate;
import com.atoz.capp.model.Messages;
import com.atoz.capp.model.TemplateAttr;
import com.atoz.capp.model.TemplateTag;
import com.atoz.capp.model.TemplateTagAttr;
import com.atoz.capp.model.TrainningTreeData;
import com.atoz.capp.model.User;
import com.atoz.capp.service.MBDTemplateServiceI;
import com.atoz.capp.service.MessagesServiceI;

/**
 * MBD模板 Controller
 * @author caicai.gao
 */
@Controller
@RequestMapping("/MBDTemplate")
public class MBDTemplateController {

	@Autowired
	private MBDTemplateServiceI mbdTemplateService;
	
	@Autowired
	private MessagesServiceI messagesService;
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * MBD模板页面URL
	 * 
	 * @return
	 */
	@PostMapping(value = "/list")
	public String list(HttpServletRequest req) {
		return "mbdTemplate/mbdTemplateList";
	}
	
	/**
	 * 验证分类下是否存在符号
	 * 
	 * @return
	 */
	@PostMapping(value="/hasMBDTemplate")
	public void hasSymbol(HttpServletRequest request, HttpServletResponse response) throws IOException{
		JsonResult result = new JsonResult();
		//查询分类下是否有符号存在
		String nodeId = StringUtils.toString(request.getParameter("nodeId"));
		long count = mbdTemplateService.hasMbdTemplate(nodeId);
		result.setCode(Constants.ERROR_CODE_NONE);
		if (count>0) {
			result.setCode(1);
			result.put(ConfigConstants.ERROR_MSG, Constants.MBD_MSG005);
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}

	/**
	 * MBD模板一览列表ajax获取入口
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/mbdTemplateList", params = "json")
	public void getModels(HttpServletRequest req, HttpServletResponse response) throws IOException {
		// 分类ID
		String categoryId = req.getParameter("id");
		List<MBDTemplate> l = mbdTemplateService.getAll(categoryId);
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(l, ConfigConstants.DATE_FORMAT));
	}
	
	/**
	 * 保存MBD模板URL
	 * 
	 * @return
	 */
	@PostMapping(value = "/save")
	public void save(HttpServletRequest req, HttpServletResponse response, MBDTemplate mbdTemplate) {
		JsonResult result = new JsonResult();
		User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
		try {
			mbdTemplate.setUpdatedBy(loginUser.getUserId().longValue());
			mbdTemplate.setUpdateTime(new Date());
			// 重名验证
			int l = mbdTemplateService.checkTemplateNameUpdate(mbdTemplate);
			int k = mbdTemplateService.checkTemplateCodeUpdate(mbdTemplate);
			if (l>0 && k>0) {
				result.setCode(Constants.ERROR_CODE_SYSTEM);
				result.put(ConfigConstants.ERROR_MSG, Constants.MBD_MSG002);
			} else if (l>0) {
				result.setCode(Constants.ERROR_CODE_SYSTEM);
				result.put(ConfigConstants.ERROR_MSG, Constants.MBD_MSG003);
			} else if (k>0) {
				result.setCode(Constants.ERROR_CODE_SYSTEM);
				result.put(ConfigConstants.ERROR_MSG, Constants.MBD_MSG004);
			} else {
				if (mbdTemplate.getOid()!=null) {
					// 更新MBD模板信息
					mbdTemplateService.updateMbdTemplate(mbdTemplate, loginUser);
				} else {
					mbdTemplate.setStatus(Constants.RELEASE_STATUS_0);
					mbdTemplate.setCreatedBy(loginUser.getUserId().longValue());
					mbdTemplate.setCreateTime(new Date());
				    // 保存MBD模板
					mbdTemplateService.insertMbdTemplate(mbdTemplate);
					TemplateTag templateTag = new TemplateTag();
					templateTag.setTagName(mbdTemplate.getTemplateName());
					templateTag.setTemplateId(mbdTemplate.getOid());
					templateTag.setTagSort(Constants.RELEASE_STATUS_0);
					templateTag.setCreatedBy(loginUser.getUserId().longValue());
					templateTag.setCreateTime(new Date());
					templateTag.setUpdatedBy(loginUser.getUserId().longValue());
					templateTag.setUpdateTime(new Date());
					// 新增属性标签的根节点
					mbdTemplateService.insertMbdTemplateTag(templateTag);
					result.setCode(Constants.ERROR_CODE_NONE);
				}
			}
			
		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		try {
			response.getWriter().print(JSON.toJSONString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * MBD模板删除页面URL
	 * 
	 * @return
	 */
	@PostMapping(value = "/delete")
	public void delete(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String id = req.getParameter("oid");
		Long oid = Long.valueOf(id);
		// 消息
		JsonResult result = new JsonResult();
		try {
			mbdTemplateService.deleteByPrimaryKey(oid);
			result.setCode(Constants.ERROR_CODE_NONE);
		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}
	
	/**
	 * 获取MBD模板属性列表
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@PostMapping(value = "/makeTreeTable")
	public void makeTreeTable(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String id = req.getParameter("oid");
		Long oid = Long.valueOf(id);
		List<TemplateTagAttr> l = mbdTemplateService.getAttrTreeTable(oid);
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(l, ConfigConstants.DATE_FORMAT));
	}
	

	/**
	 * 发布MBD模板URL
	 * 
	 * @return
	 */
	@PostMapping(value = "/saveRelease")
	public void saveRelease(HttpServletRequest req, HttpServletResponse response, MBDTemplate mbdTemplate) {
		JsonResult result = new JsonResult();
		String id = req.getParameter("oid");
		Long oid = Long.valueOf(id);
		String auditorS = req.getParameter("auditor");
		Long auditor = Long.valueOf(auditorS);
		MBDTemplate releaseMBD = mbdTemplateService.selectByPrimaryKey(oid);
		User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
		try {
			// status = 1;待审核
			Short i = 1;
			releaseMBD.setStatus(i);
			// 设置审核人
			releaseMBD.setAuditedBy(auditor);
			releaseMBD.setUpdatedBy(loginUser.getUserId().longValue());
			releaseMBD.setUpdateTime(new Date());
		    // 保存MBD模板
			mbdTemplateService.updateMbdTemplate(releaseMBD, loginUser);
			// 添加发布历史记录
			MBDHistory history = new MBDHistory();
			history.setAction("提交");
			history.setMbdOid(oid);
			history.setReleasedBy(Long.valueOf(loginUser.getUserId()));
			history.setReleaseTime(new Date());
			mbdTemplateService.insertHistory(history);
			// 添加消息通知
			Messages message = new Messages();
			message.setUnauditId(releaseMBD.getOid().toString());
			message.setUnauditType(Constants.MBD_TEMPALTE);
			message.setUserId(auditor.intValue());
			messagesService.saveMessage(message);
			// 更新消息个数
			Messages messagesInfos = messagesService.selectByUser(loginUser.getUserId());
			if (messagesInfos!=null) {
				// 响应首页信息提取-待审核消息总数
				req.getSession().setAttribute("AllMessagesCount", messagesInfos.getAllMessagesCount());
				// 响应首页信息提取-待审核MBD模板数
				req.getSession().setAttribute("MBDTemplateCount", messagesInfos.getMbdTemplateCount());
			} else {
				// 响应首页信息提取-待审核消息总数
				req.getSession().setAttribute("AllMessagesCount", 0);
				// 响应首页信息提取-待审核MBD模板数
				req.getSession().setAttribute("MBDTemplateCount", 0);
			}
			result.setCode(Constants.ERROR_CODE_NONE);

		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		try {
			response.getWriter().print(JSON.toJSONString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 模板复制URL
	 * 
	 * @return
	 */
	@PostMapping(value = "/saveCopy")
	public void saveCopy(HttpServletRequest req, HttpServletResponse response, MBDTemplate mbdTemplate) throws IOException{
		JsonResult result = new JsonResult();
		User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
		String id = req.getParameter("oid");
		Long oid = Long.valueOf(id);
		// 重名验证
		int l = mbdTemplateService.checkTemplateName(mbdTemplate.getTemplateName());
		int k = mbdTemplateService.checkTemplateCode(mbdTemplate.getTemplateCode());
		if (l>0 && k>0) {
			result.setCode(Constants.ERROR_CODE_SYSTEM);
			result.put(ConfigConstants.ERROR_MSG, Constants.MBD_MSG002);
		} else if (l>0) {
			result.setCode(Constants.ERROR_CODE_SYSTEM);
			result.put(ConfigConstants.ERROR_MSG, Constants.MBD_MSG003);
		} else if (k>0) {
			result.setCode(Constants.ERROR_CODE_SYSTEM);
			result.put(ConfigConstants.ERROR_MSG, Constants.MBD_MSG004);
		} else {
			try {
				//复制MBD模板
				mbdTemplateService.copyMbdTemplate(oid,mbdTemplate,loginUser);
				result.setCode(Constants.ERROR_CODE_NONE);

			} catch (CustomGenericException ex1) {
				result.setCode(ex1.getErrCode());
				result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
			}
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
		
	}
	
	
	/**
	 * tag保存
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@PostMapping(value="/saveTag")
	public void saveFolder(HttpServletRequest req, HttpServletResponse response) throws IOException {

		JsonResult result = new JsonResult();
		User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
		//request值获取
		String oid = req.getParameter("id");
		String parentId= req.getParameter("parentId");
		Long parentTag = Long.valueOf(parentId);
		String tagName = req.getParameter("tagName");
		String template = req.getParameter("templateId");
		Long templateId = Long.valueOf(template);
		try {
			
			TemplateTag tag = null;
			if (oid == null || oid.length() < 1) {
				tag = mbdTemplateService.saveTag(0, oid, templateId,parentTag, tagName, loginUser);
			} else {
				tag = mbdTemplateService.saveTag(1, oid, templateId,parentTag, tagName, loginUser);
			}
			
			result.setCode(Constants.ERROR_CODE_NONE);
			result.put("tag", tag);
			
		} catch(CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}
	
	/**
	 * tag删除
	 * @return
	 */
	@PostMapping(value="/deleteTag")
	public void deleteFolder(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String id = req.getParameter("id");
		JsonResult result = new JsonResult();
		try {
			if (id != null && id.length()>1) {
				Long oid = Long.valueOf(id);
				//需实现级联删除
				mbdTemplateService.deleteTag(oid);
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
	 * @return
	 */
	@PostMapping(value="/dragTag")
	public void dragTag(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String id = req.getParameter("id");
		Long oid = Long.valueOf(id);
		String parentId = req.getParameter("parent");
		Long parentTag = Long.valueOf(parentId);
		String position = req.getParameter("position");
		Long sortId = Long.parseLong(position);
		sortId = sortId+1;
		JsonResult result = new JsonResult();
		
		try {
			if (oid != null && parentTag != null) {
				mbdTemplateService.dragTag(oid, parentTag,sortId);
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
	
	/**
	 * 获取MBD模板tagTree
	 * 
	 * @param req HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException IOException
	 */
	@PostMapping(value = "/getTagTree")
	public void getTagTree(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String id = req.getParameter("oid");
		Long oid = Long.valueOf(id);
		String parent =req.getParameter("parent");
		List<TrainningTreeData> lstData = mbdTemplateService.getTagTree(parent,oid);
		// 以json格式返回数据
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(lstData, ConfigConstants.DATE_FORMAT));
	}
	
	
	/**
	 * MBD模板一览列表ajax获取入口
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/getAttrList")
	public void getAttrList(HttpServletRequest req, HttpServletResponse response,@RequestParam(value="tagId") String tagId) throws IOException {
		List<TemplateAttr> l = mbdTemplateService.getAttrList(Long.valueOf(tagId));
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(l, ConfigConstants.DATE_FORMAT));
	}
	
	/**
	 * 保存属性URL
	 * 
	 * @return
	 */
	@PostMapping(value = "/saveAttr")
	public void saveAttr(HttpServletRequest req, HttpServletResponse response, TemplateAttr templateAttr) throws IOException {
		JsonResult result = new JsonResult();
		User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
		try {
			if (templateAttr.getOid() == null) {
		        templateAttr.setCreatedBy(loginUser.getUserId().longValue());
				templateAttr.setCreateTime(new Date());
				templateAttr.setUpdatedBy(loginUser.getUserId().longValue());
				templateAttr.setUpdateTime(new Date());
				// 保存新增属性
				mbdTemplateService.insertMbdTemplateAttr(templateAttr);
			} else {
				templateAttr.setUpdatedBy(loginUser.getUserId().longValue());
				templateAttr.setUpdateTime(new Date());
				// 保存编辑属性
				mbdTemplateService.updateMbdTemplateAttr(templateAttr);
			}
			result.setCode(Constants.ERROR_CODE_NONE);
			result.put("templateAttr", templateAttr);
		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}
	

	/**
	 * 对属性重新排序
	 * 
	 * @return
	 */
	@PostMapping(value = "/attrResort")
	public void attrResort(HttpServletRequest req, HttpServletResponse response, TemplateAttr templateAttr) throws IOException {
		JsonResult result = new JsonResult();
		String id = req.getParameter("oid");
		Long oid = Long.valueOf(id);
		String operType = req.getParameter("operType");
		String tag = req.getParameter("attrTag");
		Long attrTag = Long.valueOf(tag);
		try {
			mbdTemplateService.updateMbdTemplateAttrSort(oid,attrTag,operType);
			result.setCode(Constants.ERROR_CODE_NONE);
			result.put("templateAttr", templateAttr);
		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}
	
	/**
	 * MBD模板删除页面URL
	 * 
	 * @return
	 */
	@PostMapping(value = "/deleteAttr")
	public void deleteAttr(HttpServletRequest req, HttpServletResponse response) throws IOException {
		String id = req.getParameter("oid");
		Long oid = Long.valueOf(id);
		// 消息
		JsonResult result = new JsonResult();
		try {
			
			TemplateAttr templateAttr = mbdTemplateService.selectAttrByPrimaryKey(oid);
			mbdTemplateService.deleteAttrByPrimaryKey(oid,templateAttr.getAttrTag());
			mbdTemplateService.resort(templateAttr.getAttrTag());

			result.setCode(Constants.ERROR_CODE_NONE);
		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}
	
	/**
	 * 取消发布
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@PostMapping(value ="/unRelease")
	public void unRelease(HttpServletRequest req, HttpServletResponse response) throws IOException{
		logger.info("----unRelease---");
		JsonResult result = new JsonResult();
		String mbdId = req.getParameter("mbdId");
		User loginUser = (User)SecurityUtils.getSubject().getPrincipal();
		try{
			mbdTemplateService.unRelease(mbdId,loginUser);
			result.setCode(Constants.ERROR_CODE_NONE);
		}catch (Exception e) {
			result.setCode(1);
			result.put(ConfigConstants.ERROR_MSG, "保存审核员失败！");
		}
	
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(result, ConfigConstants.DATE_FORMAT));
	}
	
	/**
	 * 获取发布历史
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/showHistory", params = "json")
	public void showHistory(HttpServletRequest req, HttpServletResponse response) throws IOException {
		// 分类ID
		String mbdId = req.getParameter("mbdId");
		List<MBDHistory> l = mbdTemplateService.getHistory(mbdId);
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		PrintWriter out = response.getWriter();
		out.print(JSON.toJSONStringWithDateFormat(l, ConfigConstants.DATE_FORMAT));
	}
	
	/**
	 * @Title: hasAttrChild  
	 * @Description: 查询标签下是否有属性节点
	 * @param req
	 * @param response
	 * @param @throws IOException    参数  
	 * @return void    返回类型  
	 * @throws
	 */
	@PostMapping(value = "/hasAttrChild")
	public void hasAttrChild(HttpServletRequest req, HttpServletResponse response) throws IOException {
		JsonResult result = new JsonResult();
		// 标签ID
		String tagId = req.getParameter("id");
		int flg = 0;
		try {
			List<TemplateAttr> l = mbdTemplateService.hasAttrChild(tagId);
			if ( !l.isEmpty()) {
				flg = 1;
			}
			result.setCode(Constants.ERROR_CODE_NONE);
			result.put("hasAttrChild", flg);
			 
		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}
	
	/**
	 * @Title: hasStructure  
	 * @Description: 查询MBD模板结构是否为空
	 * @param req
	 * @param response
	 * @param @throws IOException    参数  
	 * @return void    返回类型  
	 * @throws
	 */
	@PostMapping(value = "/hasStructure")
	public void hasStructure(HttpServletRequest req, HttpServletResponse response) throws IOException {
		JsonResult result = new JsonResult();
		// MBD模板OID
		String oid = req.getParameter("oid");
		boolean hasStructure = false;
		try {
			hasStructure= mbdTemplateService.hasStructure(Long.valueOf(oid));
			result.setCode(Constants.ERROR_CODE_NONE);
			result.put("hasStructure", hasStructure);
			 
		} catch (CustomGenericException ex1) {
			result.setCode(ex1.getErrCode());
			result.put(ConfigConstants.ERROR_MSG, ex1.getMessage());
		}
		response.setContentType(ConfigConstants.CONTENT_TYPE);
		response.setCharacterEncoding(ConfigConstants.CHARACTER_ENCODING);
		response.getWriter().print(JSON.toJSONString(result));
	}
	
}
