package com.ruyicai.controller.listerner.msglisterner;

import org.jivesoftware.smack.packet.Message.Type;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.data.db.DbHelper;
import com.ruyicai.model.MessageStatus;
import com.ruyicai.model.MyMessage;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.xmpp.IMessageListerner;

@Singleton
public class MessageStoreService implements IMessageListerner {
	private static final String TAG="MessageStoreService";
	@Inject DbHelper dbHelper;
	//@Inject UserService userService;
	//@Inject SayHelloService sayHelloService;

	@Override
	public void onMessage(MyMessage message) {
		if(message.getType().equals(Type.chat)||message.getType().equals(Type.normal)){
			if (PublicMethod.isNormalChat(message.getMsgtype())||(PublicMethod.isSystem(message.getMsgtype()))) {
				handlerNormalChat(message);
			}
//			} else if (PublicMethod.isMyDynamicMsg(message.getMsgtype())||PublicMethod.isFriendDynamicMsg(message.getMsgtype())) {
//				handlerDynamicMsgMsg(message);
//			} else {
//				handlerOtherMsg(message);
//			}
		}
	}
	private void handlerNormalChat(final MyMessage customMes) {
		String packetId=customMes.getId();
//		if(packetId!=null&&dbHelper.searchMsgNumByPacketId(packetId)>0){
//			return;
//		}
//		String userId = customMes.getFrom().substring(0,customMes.getFrom().indexOf("@"));
//		dbHelper.saveMsg(null,false,packetId,userId,customMes.getBody(),customMes.getMsgTime(), "true","false", customMes.getMsgtype(),"",customMes.getPayLoad(),userId,MessageStatus.UserReceived.getValue(),getMsgTab(userId));
	}
//	private void handlerDynamicMsgMsg(final MyMessage customMes) {
//		String packetId=customMes.getId();
//		if(packetId!=null&&dbHelper.searchMsgNumByPacketId(packetId)>0){
//			return;
//		}
//		String userId = customMes.getFrom().substring(0,customMes.getFrom().indexOf("@"));
//		String payLoad = customMes.getPayLoad();
//		dbHelper.saveMsg(null,false,packetId,userId,customMes.getBody(),customMes.getMsgTime(), "true","false", customMes.getMsgtype(),"",payLoad,userId,100,"100");
//	}
//	private void handlerOtherMsg(final MyMessage customMes) {
//		if(PublicMethod.isSayHello(customMes.getMsgtype())){
//			return;
//		}
//		if(customMes.getMsgtype()==null||"msgStatus".equals(customMes.getMsgtype())||"".equals(customMes.getMsgtype())||PublicMethod.isSystem(customMes.getMsgtype())){
//			PublicMethod.outLog(TAG, "收到的消息状态-->>"+customMes.toString());
//			return;
//		}
//		String packetId=customMes.getId();
//		if(packetId!=null&&dbHelper.searchMsgNumByPacketId(packetId)>0){
//			return;
//		}
//		String userId = customMes.getFrom().substring(0,customMes.getFrom().indexOf("@"));
//		String payload=customMes.getPayLoad();
//		String title="";
//		if(payload!=null&&!"".equals(payload)){
//			title=JsonUtils.readjsonString("title", payload);
//		}
//		dbHelper.saveOtherMsg(null,false,packetId,userId,customMes.getBody(),customMes.getMsgTime(), "true","false", customMes.getMsgtype(),title,payload,userId,100,"100");
//	}
//	
//	private String getMsgTab(String userId){
//		if (sayHelloService.getCacheUserIds()==null||sayHelloService.getCacheUserIds().size()==0) {
//			sayHelloService.setCacheUserIds(dbHelper.searchSayHelloUsers());
//			return getMsgTabFromDb(userId);
//		}
//		return getMsgTabFromCache(userId);
//	}
//	private String getMsgTabFromDb(String userId){
//		if(Constants.SYSTEM_ID.equals(userId)){
//			return "1";
//		}
//		int cur=dbHelper.selectSayHelloUser(userId);
//		if(cur==0){
//			dbHelper.changeMsgTagByUserId(userId,"0");
//			return "0";
//		}
//		dbHelper.changeMsgTagByUserId(userId,"1");
//		return "1";
//	}
//	private String getMsgTabFromCache(String userId){
//		if(Constants.SYSTEM_ID.equals(userId)){
//			return "1";
//		}
//		if(sayHelloService.getCacheUserIds().contains(userId)){
//			dbHelper.changeMsgTagByUserId(userId,"1");
//			return "1";
//		}
//		dbHelper.changeMsgTagByUserId(userId,"0");
//		return "0";
//	}
}
