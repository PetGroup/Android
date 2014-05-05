package com.ruyicai.controller.listerner.msglisterner;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.controller.service.UserService;
import com.ruyicai.model.HttpUser;
import com.ruyicai.model.MyMessage;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.StringUtils;
import com.ruyicai.util.json.JsonUtils;
import com.ruyicai.xmpp.IMessageListerner;

/**
 * 激活用户消息
 * @author SKS
 *
 */
@Singleton
public class ActiveMessageListener implements IMessageListerner{

	@Inject UserService userService;
	@Override
	public void onMessage(MyMessage message) {
		if(PublicMethod.isNormalChat(message.getMsgtype())){
			changActive(message);
		}
	}
	/**
	 * 改变激活状态
	 * @param msg
	 */
	private void changActive(MyMessage msg) {
		String payLoad=msg.getPayLoad();
		if(!StringUtils.isNotEmty(payLoad)){
			return;
		}
		if(!payLoad.contains("active")){
			return;
		}
		userService.updateUserActive(HttpUser.userId,JsonUtils.readjsonString("active", payLoad));//更新一下自己的信息
	}
}
