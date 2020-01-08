package com.atoz.capp.service;

import com.atoz.capp.model.Messages;


/**
 * 消息接口类
 * @author caicai.gao
 */
public interface MessagesServiceI {
	/**  
	 * 在消息列表中添加一条待审核消息
	 * @param message    消息
	 */
	void saveMessage(Messages message);
	  
	/**  
	 * 获取某用户的待审核消息列表
	 * @param userId 用户ID
	 * @return Messages    返回类型  
	 */
	Messages selectByUser(Integer userId);

}
