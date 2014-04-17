package com.ruyicai.controller.service;

import org.jivesoftware.smack.XMPPException;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.controller.listerner.msglisterner.NotificationMessageListener;
import com.ruyicai.model.ChatServer;
import com.ruyicai.model.HttpUser;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.json.JsonUtils;
import com.ruyicai.xmpp.MessageRouter;
import com.ruyicai.xmpp.RuyicaiConnectionListener;
import com.ruyicai.xmpp.XmppService;
import com.ruyicai.xmpp.XmppService.IXmppAddressGetter;

@Singleton
public class InitService {
	
	private static String TAG = "InitService";
	
	//@Inject private UserService userService;
	//@Inject private MessageStoreService messageStoreService;
	//@Inject private MessageReceiver messageReceiver;
    @Inject private MessageRouter messageRouter;
	//@Inject private SayHelloMessageListener sayHelloMessageListener;
	//@Inject private ActiveMessageListener activeMessageListener;
	//@Inject private RoleAndTitleMessageListener roleAndTitleMessageListener;
	//@Inject private ConnectivityBroadcaster connectivityBroadcaster;
	//@Inject private MessageService messageService;
	//@Inject private MessageAckListener messageAckListener;
	@Inject private NotificationMessageListener notificationMessageListener;
	//@Inject private DbHelper dbHelper;
	@Inject private XmppService xmppService;
	
	//@Inject SayHelloService sayHelloService;
	

	/**
	 * 初始化服务
	 */
	public void initService(RuyicaiConnectionListener connectListener) {
		PublicMethod.outLog(TAG, "LoginService   initService()");
		//this.dbHelper.switchToUser(HttpUser.userId);
		//sayHelloService.getSayHelloConstant("");
		//messageRouter.addMessageListener(messageStoreService);
		//messageRouter.addMessageListener(messageReceiver);
		messageRouter.addMessageListener(notificationMessageListener);
		//messageRouter.addMessageListener(sayHelloMessageListener);
		//messageRouter.addMessageListener(activeMessageListener);
		//messageRouter.addMessageListener(roleAndTitleMessageListener);
		//messageRouter.addMessageListener(messageService);
		//userService.addUserInfoUpdateListeners(notificationMessageListener);
		//connectivityBroadcaster.addListener(userService);
	    //messageRouter.addMessageListener(notifyService);
	    xmppService.addConnectionListener(connectListener);
//		if(!messageAckListener.isAlive()){
//			messageAckListener.start();
//		}

		xmppService.setXmppAddressGetter(new IXmppAddressGetter() {
			@Override
			public Object[] getXmppAddress() throws XMPPException {
				PublicMethod.outLog(TAG, "try to get xmpp notification config");
				//String result=HttpService.getXmppNotifyServiceInfo();
//				if (result == null || "".equals(result)) {
//					throw new XMPPException("获取消息服务器地址失败");
//				}
//				if (!Constants.SUCCESS_CODE.equals(JsonUtils.readjsonString(
//						"errorcode", result))) {
//					throw new XMPPException("获取消息服务器地址失败");
//				}
//				测试服务器 IP：202.43.152.174
//				端口：5222 或 5322 
//				域：ruyicai.com 
				//ChatServer chatServerInfo = JsonUtils.resultData("entity",result,ChatServer.class);
				//String[] host = PublicMethod.getXmppInfo(chatServerInfo.getAddress());
				String[] ret=new String[5];
				//ret[0]=host[0];
				ret[0]="202.43.152.174";
				//ret[1]=host[1];
				ret[1]="5222";
				//ret[2]=chatServerInfo.getName();
				ret[2]="ruyicai.com";
				ret[3]=HttpUser.Imei;
				ret[4]=HttpUser.Imei;
				PublicMethod.outLog(TAG, "xmpp notification config is "+ret[0]+ret[1]+ret[2]+ret[3]+ret[4]);
				return ret;
			}
		});
	}
}
