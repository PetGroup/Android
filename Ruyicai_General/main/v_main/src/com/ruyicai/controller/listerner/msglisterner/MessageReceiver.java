package com.ruyicai.controller.listerner.msglisterner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.controller.service.MessageService;
import com.ruyicai.model.MyMessage;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.xmpp.IMessageListerner;
import com.ruyicai.xmpp.XmppService;

@Singleton
public class MessageReceiver implements IMessageListerner {
	
	@Inject XmppService xmppService;
	private Message message = null;
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	private void sendStatusMsg(MyMessage message){
		xmppService.sendMsg(getMessageToSend(message));
	}
	private MyMessage getMessageToSend(MyMessage message) {
		String fromUserId = message.getFrom().substring(0,message.getFrom().indexOf("@"));
		String toUserId = message.getToWho().substring(0,message.getToWho().indexOf("@"));
		return createMessage(fromUserId,toUserId, createMsgBody(message.getId().toString(),"Delivered"),"msgStatus",Type.normal,message.getId());
	}
	public void refreshMessage(Bundle message) {
	}
	@Override
	public void onMessage(final MyMessage message) {
		if(message.getId() == null || "".equals(message.getId())){
			return;
		}
		if(Constants.MSG_STATUS.equals(message.getMsgtype())){
			return ;
		}
		if (PublicMethod.isPushMessage(message.getMsgtype())) {
			return;
		}
		sendStatusMsg(message);
	}	
	private MyMessage createMessage(String toUserId,String fromUserId,String text,String msgType,Type type,String packetId){
		return createMessage(toUserId, fromUserId, text, msgType, type, packetId, null);
	}
	private MyMessage createMessage(String toUserId,String fromUserId,String text,String msgType,Type type,String packetId,String payLoad){
		MyMessage myMessage = new MyMessage();
		myMessage.setId(packetId);
		myMessage.setToWho(toUserId);
		myMessage.setFrom(fromUserId);
		myMessage.setBody(text);
		myMessage.setMsgTime(String.valueOf(new Date().getTime()));
//		myMessage.setMsgTime(new Date());
		myMessage.setMsgtype(msgType);
		myMessage.setType(type);
		myMessage.setPayLoad(payLoad);
		return myMessage;
	}
	private String createMsgBody(String msgId,String msgStatus){
		Map<String, String> map=new HashMap<String, String>();
		map.put("src_id", msgId);
		map.put("received", "true");
		map.put("msgStatus", msgStatus);
		return JSON.toJSONString(map);
	}
}

