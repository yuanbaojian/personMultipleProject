package com.atoz.capp.service;

import java.util.List;

import com.atoz.capp.model.MBDHistory;
import com.atoz.capp.model.MBDTemplate;
import com.atoz.capp.model.TemplateAttr;
import com.atoz.capp.model.TemplateTag;
import com.atoz.capp.model.TemplateTagAttr;
import com.atoz.capp.model.TrainningTreeData;
import com.atoz.capp.model.User;

/**
 * MBD模板接口类
 * @author caicai.gao
 */
public interface MBDTemplateServiceI {
	
	long hasMbdTemplate(String nodeId);

	void insertMbdTemplate(MBDTemplate mbdTemplate);

	void updateMbdTemplate(MBDTemplate mbdTemplate, User loginUser);

	MBDTemplate selectByPrimaryKey(Long oid);

	void deleteByPrimaryKey(Long oid);

	List<MBDTemplate> getAll(String categoryId);

	void insertMbdTemplateTag(TemplateTag templateTag);

	/**
	 * 获取MBD模板的分类及属性信息
	 * @param oid mbd模板ID
	 * @return List<TemplateTagAttr>    返回类型  
	 */
	List<TemplateTagAttr> getAttrTreeTable(Long oid);

	List<TrainningTreeData> getTagTree(String parent, Long templateId);

	List<TemplateAttr> getAttrList(Long tagId);

	void insertMbdTemplateAttr(TemplateAttr templateAttr);

	void updateMbdTemplateAttr(TemplateAttr templateAttr);

	TemplateAttr selectAttrByPrimaryKey(Long oid);

	void deleteAttrByPrimaryKey(Long oid, Long attrTag);

	void updateMbdTemplateAttrSort(Long oid, Long attrTag, String operType);

	void resort(Long attrTag);

	void copyMbdTemplate(Long oid, MBDTemplate mbdTemplate, User loginUser);

	int checkTemplateName(String templateName);
	
	int checkTemplateCode(String templateCode);

	TemplateTag saveTag(int i, String oid, Long templateId, Long parentTag, String tagName, User loginUser);

	int deleteTag(Long oid);

	int dragTag(Long oid, Long parentTag, Long sortId);

	void insertHistory(MBDHistory history);

	void unRelease(String mbdId, User loginUser);

	List<MBDHistory> getHistory(String mbdId);

	List<MBDTemplate> getAllReleased(String categoryId);
	  
	/**  
	 * @Title: hasAttrChild  
	 * @Description: 查询标签下是否有属性节点  
	 * @param  tagId
	 * @param @return    参数  
	 * @return List<TemplateAttr>    返回类型  
	 * @throws  
	 */
	List<TemplateAttr> hasAttrChild(String tagId);

	boolean hasStructure(Long oid);

	int checkTemplateNameUpdate(MBDTemplate mbdTemplate);

	int checkTemplateCodeUpdate(MBDTemplate mbdTemplate);

	
}
