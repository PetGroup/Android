package com.ruyicai.controller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.Packet;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.controller.listerner.MessageStatusListener;
import com.ruyicai.data.db.DbHelper;
import com.ruyicai.model.MyMessage;
import com.ruyicai.util.json.JsonUtils;
import com.ruyicai.xmpp.IMessageListerner;

@Singleton
public class MessageService implements IMessageListerner {
	
	@Inject DbHelper dbHelper;
	public MyMessage createMessage(String toUserId,String fromUserId,String text){
		return createMessage(toUserId,fromUserId, text,"normalchat",Type.chat,Packet.nextID());
	}
	public MyMessage createGroupMessage(String toUserId,String fromUserId,String text){
		return createMessage(toUserId,fromUserId, text,"groupchat",Type.chat,Packet.nextID());
	}
	public MyMessage createMessage(String toUserId,String fromUserId,String text,String msgType,Type type,String packetId){
		return createMessage(toUserId, fromUserId, text, msgType, type, packetId, null);
	}
	public MyMessage createMessage(String toUserId,String fromUserId,String text,String msgType,Type type,String packetId,String payLoad){
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
	
	public String createPayLoad(String thumb,String title,String shiptype,String messageid,String msg,String type){
		Map<String, String> map=new HashMap<String, String>();
		map.put("thumb", thumb);
		map.put("title", title);
		map.put("shiptype", shiptype);
		map.put("messageid", messageid);
		map.put("msg", msg);
		map.put("type", type);
		return JSON.toJSONString(map);
	}
	public String createMsgBody(String msgId,String msgStatus){
		Map<String, String> map=new HashMap<String, String>();
		map.put("src_id", msgId);
		map.put("received", "true");
		map.put("msgStatus", msgStatus);
		return JSON.toJSONString(map);
	}
	List<MessageStatusListener> messageStatusListeners=new CopyOnWriteArrayList<MessageStatusListener>();

	public void messageSendFail(MyMessage myMessage){
		for(MessageStatusListener messageStatusListener:messageStatusListeners){
			messageStatusListener.messageSendFail(myMessage);
		}
	}

	private void messageServerReceived(MyMessage myMessage){
		for(MessageStatusListener messageStatusListener:messageStatusListeners){
			messageStatusListener.messageServerReceived(myMessage);
		}
	}
	
	private void messageUserReceived(MyMessage myMessage){
		for(MessageStatusListener messageStatusListener:messageStatusListeners){
			messageStatusListener.messageUserReceived(myMessage);
		}
	}
	
	private void messageUserRead(MyMessage myMessage){
		for(MessageStatusListener messageStatusListener:messageStatusListeners){
			messageStatusListener.messageUserRead(myMessage);
		}
	}
	
	public void addMessageStatusListener(MessageStatusListener messageStatusListener){
		if(!this.messageStatusListeners.contains(messageStatusListener)){
			this.messageStatusListeners.add(messageStatusListener);
		}
	}
	
	public void removeMessageStatusListener(MessageStatusListener messageStatusListener){
		if(this.messageStatusListeners.contains(messageStatusListener)){
			this.messageStatusListeners.remove(messageStatusListener);
		}
	}

	public void beforeSendMessage(MyMessage myMessage) {
		for(MessageStatusListener messageStatusListener:messageStatusListeners){
			messageStatusListener.beforeSendMessage(myMessage);
		}
	}

	@Override
	public void onMessage(MyMessage message) {
		String newUserId = message.getFrom().substring(0,message.getFrom().indexOf("@"));
		if(message.getMsgtype()==null||message.getMsgtype().equals("msgStatus")||"".equals(message.getMsgtype())){
			String msgBody=message.getBody();
			if("messageAck".equalsIgnoreCase(newUserId)){
			//	MyMessage receiceMessage=dbHelper.selectMsgByPacketId(JsonUtils.readjsonString("src_id", msgBody));
//				if(JsonUtils.readjsonString("received",msgBody).equals("true")){
//					this.messageServerReceived(receiceMessage);
//				}
			}
			if("msgStatus".equals(message.getMsgtype())){
			//	MyMessage receiceMessage=dbHelper.selectMsgByPacketId(message.getId());
//				if(JsonUtils.readjsonString("msgStatus",msgBody).equals("Delivered")&&JsonUtils.readjsonString("received",msgBody).equals("true")){
//					this.messageUserReceived(receiceMessage);
//				}else if (JsonUtils.readjsonString("msgStatus",msgBody).equals("Displayed")&&JsonUtils.readjsonString("received",msgBody).equals("true")) {
//					this.messageUserRead(receiceMessage);
//				}
			}
		}		
	}
	
	/**
	 * 分享动态的消息处理
	 */
	private List<OnShareMessageListener> onShareMessageListeners=new ArrayList<MessageService.OnShareMessageListener>();
	
	public interface OnShareMessageListener{
		public void onShareMessage(MyMessage message);
	}
	public void sendShareMessage(MyMessage message){
		for(OnShareMessageListener onShareMessageListener:onShareMessageListeners){
			onShareMessageListener.onShareMessage(message);
		}
	}
	public void addOnShareMessageListener(OnShareMessageListener onShareMessageListener){
		onShareMessageListeners.add(onShareMessageListener);
	}
	public void removeOnShareMessageListener(OnShareMessageListener onShareMessageListener){
		onShareMessageListeners.remove(onShareMessageListener);
	}
}
