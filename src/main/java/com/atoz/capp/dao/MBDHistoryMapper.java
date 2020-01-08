package com.atoz.capp.dao;

import java.util.List;

import com.atoz.capp.model.MBDHistory;


public interface MBDHistoryMapper {
    int deleteByPrimaryKey(Long oid);

    int insert(MBDHistory record);

    int insertSelective(MBDHistory record);

    MBDHistory selectByPrimaryKey(Long oid);

    int updateByPrimaryKeySelective(MBDHistory record);

    int updateByPrimaryKey(MBDHistory record);

	List<MBDHistory> getByMBDOid(Long mbdOid);
	
	int deleteByMBDOid(Long mbdOid);
}