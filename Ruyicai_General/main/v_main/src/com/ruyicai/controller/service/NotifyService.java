package com.ruyicai.controller.service;

import java.util.List;

import org.jivesoftware.smack.packet.Message.Type;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.MyMessage;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.json.JsonUtils;
import com.ruyicai.xmpp.IMessageListerner;
import com.ruyicai.xmpp.XmppService;

@Singleton
public class NotifyService implements IMessageListerner {
	
	@Inject private Context mContext;
	@Inject XmppService xmppService;
	@Inject MessageService messageService;
	@Inject Application application;

	private void sendStatusMsg(MyMessage message){
		xmppService.sendMsg(getMessageToSend(message));
	}
	private MyMessage getMessageToSend(MyMessage message) {
		String fromUserId = message.getFrom().substring(0,message.getFrom().indexOf("@"));
		String toUserId = message.getToWho().substring(0,message.getToWho().indexOf("@"));
		return messageService.createMessage(fromUserId, toUserId,messageService.createMsgBody(message.getId().toString(),"Delivered"),"msgStatus",Type.normal,message.getId());
	}
	private void sendBackGroundNotifi(MyMessage message){
		if (message == null) return;
		Intent intent = new Intent(Constants.ACTION_SHOW_BACKGROUND_NOTIFICATION);
		String msgBody = JsonUtils.readjsonString("body",message.getBody());
		String userId = JsonUtils.readjsonString("from",message.getBody());
		String packetId =JsonUtils.readjsonString("id",message.getBody());
		String contactsPersonInfo[] = getLevaMesBodyInfo(msgBody);

		intent.putExtra("nickName", contactsPersonInfo[0]);
		intent.putExtra("body",contactsPersonInfo[1]);
		intent.putExtra("userId", userId);
		intent.putExtra("packetId", packetId);
		mContext.sendBroadcast(intent);
	}
	private String[] getLevaMesBodyInfo(String msgBody) {
		String[] contactsPersonInfo  = new String[2];
		if (msgBody != null && msgBody.contains(":")) {
			contactsPersonInfo = msgBody.split(":");
			String bodyContext = "";	
			if (contactsPersonInfo != null && contactsPersonInfo.length == 2) {
				bodyContext = contactsPersonInfo[1];
			}
			contactsPersonInfo[1] = bodyContext;
		} else {
			contactsPersonInfo[0] = msgBody;
			contactsPersonInfo[1] = "";
		}
		return contactsPersonInfo;
	}
	public void refreshMessage(Bundle message) {
		
	}
	@Override
	public void onMessage(MyMessage message) {
		if(!Constants.MSG_STATUS.equals(message.getMsgtype())){
			sendStatusMsg(message);
		}
		//if (!PublicMethod.isRunningForeground(mContext)) {
			if(message.getMsgtype()==null
					||"msgStatus".equals(message.getMsgtype())
					||"".equals(message.getMsgtype())
					||PublicMethod.isSystem(message.getMsgtype())
					||PublicMethod.isSayHello(message.getMsgtype())){
				return;
			}
			ActivityManager am=(ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
			for(RunningAppProcessInfo appProcessInfo:runningAppProcesses){
//				if(appProcessInfo.processName.equals("com.chatgame.chatActivity")){
//					sendGroundNotifiToMain(message);
//					return;
//				}
				PublicMethod.outLog("NotifyService", appProcessInfo.processName);
			}
			sendBackGroundNotifi(message);
		//} 
	}
}
