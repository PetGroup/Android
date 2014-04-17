package com.ruyicai.controller.service;

import org.jivesoftware.smack.XMPPException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.controller.listerner.msglisterner.NotificationMessageListener;
import com.ruyicai.model.ChatServer;
import com.ruyicai.model.HttpUser;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.json.JsonUtils;
import com.ruyicai.xmpp.MessageRouter;
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
	public void initService() {
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
//		if(!messageAckListener.isAlive()){
//			messageAckListener.start();
//		}
		xmppService.setXmppAddressGetter(new IXmppAddressGetter() {
			
			@Override
			public Object[] getXmppAddress() throws XMPPException {
				String result=HttpService.getXmppServiceInfo();
				if (result == null || "".equals(result)) {
					throw new XMPPException("获取消息服务器地址失败");
				}
				if (!Constants.SUCCESS_CODE.equals(JsonUtils.readjsonString(
						"errorcode", result))) {
					throw new XMPPException("获取消息服务器地址失败");
				}
				ChatServer chatServerInfo = JsonUtils.resultData("entity",result,ChatServer.class);
				String[] host = PublicMethod.getXmppInfo(chatServerInfo.getAddress());
				String[] ret=new String[5];
				ret[0]=host[0];
				ret[1]=host[1];
				ret[2]=chatServerInfo.getName();
				ret[3]=HttpUser.userId;
				ret[4]=HttpUser.token;
				return ret;
			}
		});
	}
}
