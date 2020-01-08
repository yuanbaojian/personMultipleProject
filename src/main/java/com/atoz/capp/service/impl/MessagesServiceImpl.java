package com.atoz.capp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atoz.capp.dao.MessagesMapper;
import com.atoz.capp.model.Messages;
import com.atoz.capp.service.MessagesServiceI;

/**
 * 消息实现类
 * @author caicai.gao
 */
@Service("messagesService")
public class MessagesServiceImpl implements MessagesServiceI {
	@Autowired
	private MessagesMapper messagesMapper;
	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param message  
	 * @see com.atoz.capp.service.MessagesServiceI#saveMessage(com.atoz.capp.model.Messages)
	 */  
	            
	@Override
	public void saveMessage(Messages message) {
		messagesMapper.insertSelective(message);
	}
	  
	/* (非 Javadoc)  
	 *   
	 *   
	 * @param userId
	 * @return  
	 * @see com.atoz.capp.service.MessagesServiceI#selectByUser(java.lang.Integer)
	 */  
	            
	@Override
	public Messages selectByUser(Integer userId) {
		return messagesMapper.selectByUser(userId);
	}

}
