package com.ruyicai.controller.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.controller.listerner.msglisterner.ActiveMessageListener;
import com.ruyicai.controller.listerner.msglisterner.MessageStoreService;
import com.ruyicai.data.db.DbHelper;
import com.ruyicai.receiver.MsgClientReceiver;
import com.ruyicai.util.PublicMethod;

@Singleton
public class InitService {
	
	private static String TAG = "InitService";
	
	//@Inject private UserService userService;
	@Inject private MessageStoreService messageStoreService;
	//@Inject private MessageReceiver messageReceiver;
    @Inject private MsgClientReceiver msgClientReceiver;
	//@Inject private SayHelloMessageListener sayHelloMessageListener;
	//@Inject private ActiveMessageListener activeMessageListener;
	//@Inject private RoleAndTitleMessageListener roleAndTitleMessageListener;
	//@Inject private ConnectivityBroadcaster connectivityBroadcaster;
	@Inject private MessageService messageService;
	@Inject private MessageAckListener messageAckListener;
	//@Inject private NotificationMessageListener notificationMessageListener;
	@Inject private DbHelper dbHelper;
	//@Inject private XmppService xmppService;
	
	//@Inject SayHelloService sayHelloService;
	

	/**
	 * 初始化服务
	 */
	public void initService() {
		PublicMethod.outLog(TAG, "LoginService   initService()");
		//this.dbHelper.switchToUser(HttpUser.userId);
		//sayHelloService.getSayHelloConstant("");
		msgClientReceiver.addMessageListener(messageStoreService);
		//messageRouter.addMessageListener(messageReceiver);
		//messageRouter.addMessageListener(notificationMessageListener);
		//messageRouter.addMessageListener(sayHelloMessageListener);
		//messageRouter.addMessageListener(activeMessageListener);
		//messageRouter.addMessageListener(roleAndTitleMessageListener);
		//messageRouter.addMessageListener(messageService);
		//userService.addUserInfoUpdateListeners(notificationMessageListener);
		//connectivityBroadcaster.addListener(userService);
	    //messageRouter.addMessageListener(notifyService);
	    //xmppService.addConnectionListener(connectListener);
//		if(!messageAckListener.isAlive()){
//			messageAckListener.start();
//		}
	}
}
