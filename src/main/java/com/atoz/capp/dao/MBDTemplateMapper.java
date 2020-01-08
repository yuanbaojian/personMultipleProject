package com.atoz.capp.dao;

import java.util.List;

import com.atoz.capp.model.MBDTemplate;


public interface MBDTemplateMapper {
	
	long hasMBDTemplate(Long nodeId);
	
    int deleteByPrimaryKey(Long oid);

    int insert(MBDTemplate record);

    int insertSelective(MBDTemplate record);

    MBDTemplate selectByPrimaryKey(Long oid);

    int updateByPrimaryKeySelective(MBDTemplate record);

    int updateByPrimaryKey(MBDTemplate record);

	List<MBDTemplate> getAll(Long categoryId);

	int selectByTemplateName(String templateName);
	
	int selectByTemplateCode(String templateCode);

	List<MBDTemplate> getUnAuditMBD(Long oid);

	List<MBDTemplate> getAllReleased(Long categoryId);

	int checkTemplateNameUpdate(MBDTemplate mbdTemplate);

	int checkTemplateCodeUpdate(MBDTemplate mbdTemplate);
} 