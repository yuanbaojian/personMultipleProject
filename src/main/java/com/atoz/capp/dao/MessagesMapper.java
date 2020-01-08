package com.atoz.capp.dao;

import com.atoz.capp.model.Messages;

public interface MessagesMapper {
    int deleteByPrimaryKey(Long messageId);

    int insert(Messages record);

    int insertSelective(Messages record);

    Messages selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(Messages record);

    int updateByPrimaryKey(Messages record);

	/**  
	 * @Title: deleteMessage  
	 * @Description: 删除消息通知
	 * @param @param message
	 * @param @return    参数  
	 * @return int    返回类型  
	 * @throws  
	 */  
	            
	int deleteMessage(Messages message);

	  
	/**  
	 * @Title: selectByUser  
	 * @Description: 获取某用户的待审核消息列表 
	 * @param userId
	 * @return Messages    返回类型  
	 * @throws  
	 */  
	            
	Messages selectByUser(Integer userId);
}