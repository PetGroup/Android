package com.ruyicai.controller.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.controller.listerner.msglisterner.ActiveMessageListener;
import com.ruyicai.controller.listerner.msglisterner.MessageStoreService;
import com.ruyicai.data.db.DbHelper;
import com.ruyicai.model.HttpUser;
import com.ruyicai.receiver.MsgClientReceiver;
import com.ruyicai.util.PublicMethod;

@Singleton
public class InitService {
	
	private static String TAG = "InitService";
	
	//@Inject private UserService userService;
	@Inject private MessageStoreService messageStoreService;
    @Inject private MsgClientReceiver msgClientReceiver;
	//@Inject private ActiveMessageListener activeMessageListener;
	//@Inject private ConnectivityBroadcaster connectivityBroadcaster;
	@Inject private MessageService messageService;
	@Inject private MessageAckListener messageAckListener;
	@Inject private DbHelper dbHelper;

	/**
	 * 初始化服务
	 */
	public void initService() {
		PublicMethod.outLog(TAG, "LoginService   initService()");
		this.dbHelper.switchToUser(HttpUser.userId);//需要
		//sayHelloService.getSayHelloConstant("");
		msgClientReceiver.addMessageListener(messageStoreService);
		//messageRouter.addMessageListener(messageReceiver);//不需要
		//messageRouter.addMessageListener(notificationMessageListener);//不需要
		//messageRouter.addMessageListener(sayHelloMessageListener);//不需要
		//messageRouter.addMessageListener(activeMessageListener);//不需要
		//messageRouter.addMessageListener(roleAndTitleMessageListener);//不需要
		msgClientReceiver.addMessageListener(messageService);
		//userService.addUserInfoUpdateListeners(notificationMessageListener);//不需要
		//connectivityBroadcaster.addListener(userService);//待定
		if(!messageAckListener.isAlive()){
			messageAckListener.start();
		}
	}
}
