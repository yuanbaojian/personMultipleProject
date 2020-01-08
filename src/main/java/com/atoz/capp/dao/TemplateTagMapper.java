package com.atoz.capp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atoz.capp.model.TemplateTag;
public interface TemplateTagMapper {
	
    int deleteByPrimaryKey(Long oid);

    int insert(TemplateTag record);

    int insertSelective(TemplateTag record);

    TemplateTag selectByPrimaryKey(Long oid);

    int updateByPrimaryKeySelective(TemplateTag record);

    int updateByPrimaryKey(TemplateTag record);

	List<TemplateTag> selectByTemplateId(Long templateId);

	int hasChild(Long parentTagId);

	List<TemplateTag> selectByParentTag(@Param("parent") String parent, @Param("templateId") Long templateId);

	List<TemplateTag> selectByRootTag(Long oid);

	Long getParentTagId(@Param("tagName") String tagName, @Param("oldTemplateId") Long oldTemplateId, @Param("newTemplateId") Long newTemplateId);

	int checkNameExisted(Map<String, Object> map);

	List<TemplateTag> selectTagByParent(Long oldParent);

	int getMaxSort(Long parentTag);

    List<TemplateTag> getAttrValueByTid(Long templateId);

	  
	/**  
	 * @Title: deleteByNBDId  
	 * @Description: 删除MBD模板下的所有tag  
	 * @param oid    参数  
	 * @return void    返回类型  
	 * @throws  
	 */  
	            
	void deleteByNBDId(Long oid);
}