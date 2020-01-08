package com.atoz.capp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atoz.capp.model.TemplateAttr;
public interface TemplateAttrMapper {
    int deleteByPrimaryKey(Long oid);

	int insert(TemplateAttr record);

	int insertSelective(TemplateAttr record);

	TemplateAttr selectByPrimaryKey(Long oid);

	int updateByPrimaryKeySelective(TemplateAttr record);

	int updateByPrimaryKey(TemplateAttr record);

	List<TemplateAttr> selectByTemplateId(Long templateId);  //add by caicai.gao 根据MBD模板获取属性列表

	List<TemplateAttr> selectByTagId(Long oid);

	int getMaxSort(Long attrTag);

	TemplateAttr selectBySort(@Param("attrTag") Long attrTag, @Param("sort") Long sort);

	int selectByAttrName(@Param("attrTag") Long attrTag, @Param("attrName") String attrName, @Param("oid") Long oid);
	
	int checkNameExits(@Param("attrTag") Long attrTag, @Param("attrName") String attrName);

	  
	/**  
	 * @Title: deleteByMBDId  
	 * @Description: 删除MBD模板下的所有属性)  
	 * @param oid    参数  
	 * @return void    返回类型  
	 * @throws  
	 */  
	            
	void deleteByMBDId(Long oid);

	  
	/**  
	 * @Title: deleteByTagId  
	 * @Description: 删除某一标签下所有的属性 
	 * @param @param oid    参数  
	 * @return void    返回类型  
	 * @throws  
	 */  
	            
	void deleteByTagId(Long oid);
}