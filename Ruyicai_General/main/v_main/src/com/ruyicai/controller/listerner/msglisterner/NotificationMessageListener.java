package com.ruyicai.controller.listerner.msglisterner;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.MyMessage;
import com.ruyicai.model.PushMessage;
import com.ruyicai.model.ReturnBean;
import com.ruyicai.model.User;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.json.JsonUtils;
import com.ruyicai.xmpp.IMessageListerner;

/**
 * 
 * @author ruyicai
 * 
 *         把收到的消息在应用非活跃或者机器锁屏时发送给通知栏
 */
@Singleton
public class NotificationMessageListener implements IMessageListerner {

	@Inject
	private Context mContext;
	// @Inject private UserService userService;

	private HashMap<String, List<MyMessage>> map = new HashMap<String, List<MyMessage>>();

	@Override
	public void onMessage(MyMessage message) {
		if (!PublicMethod.isRunningForeground(mContext)
				|| PublicMethod.isScreenLocked(mContext)
				|| PublicMethod.isPushMessage(message.getMsgtype())) {
			if (message.getMsgtype() == null
					|| "msgStatus".equals(message.getMsgtype())
					|| "".equals(message.getMsgtype())
					|| PublicMethod.isSystem(message.getMsgtype())
					|| PublicMethod.isSayHello(message.getMsgtype())) {
				return;
			}
			sendBackGroundNotifi(message);
		}
	}

	private synchronized void sendBackGroundNotifi(MyMessage message) {
		// String fromUserName =
		// message.getFrom().substring(0,message.getFrom().indexOf("@"));
		// if(Constants.DAILY_NEWS.equals(message.getMsgtype())){
		// sendBackGroundNotifiByNews(message,fromUserName);
		// return ;
		// }
		// User user = userService.getConstactUser(fromUserName);
		// if(user == null){
		// List<MyMessage> list = map.get(fromUserName);
		// if(list==null){
		// list=new LinkedList<MyMessage>();
		// }
		// list.add(message);
		// map.put(fromUserName, list);
		// userService.updateContactsUser(fromUserName);
		// }else{
		// notifySend(user,message);
		notifySend(message);
		// }
	}

	private void sendBackGroundNotifiByNews(MyMessage message, String userId) {
		if (message == null)
			return;
		Intent intent = new Intent(
				Constants.ACTION_SHOW_BACKGROUND_NOTIFICATION);
		intent.putExtra("nickName", "每日一闻");
		intent.putExtra("body", message.getBody());
		intent.putExtra("userId", userId);
		intent.putExtra("packetId", message.getId());
		mContext.sendBroadcast(intent);
	}

	private synchronized void notifyMessageForUser(User user) {
		List<MyMessage> list = this.map.get(user.getId());
		if (list == null) {
			return;
		}
		for (MyMessage message : list) {
			notifySend(user, message);
		}
		this.map.remove(user.getId());
	}

	/**
	 * 临时方法
	 * 
	 * @param myMessage
	 */
	private void notifySend(MyMessage myMessage) {
		String fromUserName = myMessage.getFrom().substring(0,
				myMessage.getFrom().indexOf("@"));
		Intent intent = new Intent(Constants.ACTION_SHOW_MESSAGE_NOTIFICATION);
		String mType = myMessage.getMsgtype();

		if (PublicMethod.isPushMessage(mType)) {

			String body = myMessage.getBody();

			PushMessage pushMessgae = JsonUtils.resultData(body,
					PushMessage.class);

			if (pushMessgae != null) {
				intent.putExtra("title", pushMessgae.getTitle());
				intent.putExtra("content", pushMessgae.getContent());
				intent.putExtra("pushpage", pushMessgae.getPushpage());
				mContext.sendBroadcast(intent);
			}
		}

////		intent.putExtra("nickName", user.getNickname());
////		intent.putExtra("body",myMessage.getBody());
////		intent.putExtra("userId", user.getId());
////		intent.putExtra("packetId", myMessage.getId());
//		intent.putExtra("nickName", fromUserName);
//		intent.putExtra("body", myMessage.getBody());
//		mContext.sendBroadcast(intent);
	}

	private void notifySend(User user, MyMessage myMessage) {
		Intent intent = new Intent(Constants.ACTION_SHOW_MESSAGE_NOTIFICATION);
		if ("".equals(user.getNickname()) || user.getNickname() == null) {
			user.setNickname("");
		}
		intent.putExtra("nickName", user.getNickname());
		intent.putExtra("body", myMessage.getBody());
		intent.putExtra("userId", user.getId());
		intent.putExtra("packetId", myMessage.getId());
		mContext.sendBroadcast(intent);
	}
}
