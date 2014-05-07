package com.ruyicai.controller.service;

import org.jivesoftware.smack.XMPPException;

import android.content.Context;
import android.content.IntentFilter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.controller.listerner.msglisterner.MessageReceiver;
import com.ruyicai.controller.listerner.msglisterner.NotificationMessageListener;
import com.ruyicai.model.ChatServer;
import com.ruyicai.model.HttpUser;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.json.JsonUtils;
import com.ruyicai.xmpp.MessageRouter;
import com.ruyicai.xmpp.ReconnectionManager;
import com.ruyicai.xmpp.RuyicaiConnectionListener;
import com.ruyicai.xmpp.XmppService;
import com.ruyicai.xmpp.XmppService.IXmppAddressGetter;

@Singleton
public class InitBackGroundService {
	
	private static String TAG = "InitService";
    @Inject private MessageRouter messageRouter;
	@Inject private NotificationMessageListener notificationMessageListener;
	@Inject private XmppService xmppService;
	@Inject private MsgServerReceiver msgServerReceiver;
	@Inject private Context context;
	@Inject private ReconnectionManager reconnectionManager;
	
	/**
	 * 初始化服务
	 */
	public void initService(RuyicaiConnectionListener connectListener) {
		PublicMethod.outLog(TAG, "InitBackGroundService   initService()");
		messageRouter.addMessageListener(notificationMessageListener);
	    xmppService.addConnectionListener(connectListener);
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
	/**
	 * 连接xmpp
	 */
    public void connectedXmppService() {
		reconnectionManager.reconnectNow();
    }
	/**
	 * 注册服务端接受器
	 * @param context
	 */
	public void registerReceiver(Context context){
		registerNotificationReceiver(context);
	}
	/**
	 *注销服务端接受器
	 * @param context
	 */
	public void unRegisterReceiver(Context context){
		unregisterMsgReceiver(context);
	}
	/**
	 * 注销消息接收器
	 * @param context
	 */
	public void unregisterMsgReceiver(Context context){
		try{
			if (msgServerReceiver != null) {
				context.unregisterReceiver(msgServerReceiver);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 注册推送广播
	 */
	private void registerNotificationReceiver(Context context) {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_SHOW_BACKGROUND_NOTIFICATION);
		context.registerReceiver(msgServerReceiver, filter);
	}

}
