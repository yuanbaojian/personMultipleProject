package com.atoz.capp.dao;

import com.atoz.capp.model.LogDetail;

public interface LogDetailMapper {
    int insert(LogDetail record);

    int insertSelective(LogDetail record);
    
    int deleteByPrimaryKey(String id);
}