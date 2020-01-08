package com.atoz.capp.dao;

import com.atoz.capp.model.Log;

/**
 * @author caicai.gao
 */

public interface LogMapper {
    int insert(Log record);

    int insertSelective(Log record);
    
    int deleteByPrimaryKey(String id);
}