package com.ruyicai.controller.service;



import org.jivesoftware.smack.XMPPException;

import roboguice.service.RoboService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;

//import com.chatgame.activity.guide.StartActivity;
//import com.chatgame.chatActivty.R;
//import com.chatgame.constants.Constants;
//import com.chatgame.model.ChatServer;
//import com.chatgame.model.HttpUser;
//import com.chatgame.net.ConnectivityReceiver;
//import com.chatgame.utils.common.PublicMethod;
//import com.chatgame.utils.jaon.JsonUtils;
//import com.chatgame.xmpp.GameConnectionListener;
//import com.chatgame.xmpp.MessageRouter;
//import com.chatgame.xmpp.ReconnectionManager;
//import com.chatgame.xmpp.XmppService;
//import com.chatgame.xmpp.XmppService.IXmppAddressGetter;
import com.google.inject.Inject;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.ChatServer;
import com.ruyicai.model.HttpUser;
import com.ruyicai.net.ConnectivityReceiver;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.json.JsonUtils;
import com.ruyicai.xmpp.GameConnectionListener;
import com.ruyicai.xmpp.MessageRouter;
import com.ruyicai.xmpp.ReconnectionManager;
import com.ruyicai.xmpp.XmppService;
import com.ruyicai.xmpp.XmppService.IXmppAddressGetter;

public class MsgService extends RoboService implements GameConnectionListener {
	private static final String TAG = "MsgService";
	@Inject ConnectivityReceiver connectivityReceiver;
	@Inject private NotificationBackGroundReceiver notificationReceiver;
	@Inject private XmppService xmppService;
	@Inject private MessageRouter messageRouter;
	@Inject private NotifyService notifyService;
	@Inject private ReconnectionManager reconnectionManager;
	@Override
	public void onCreate() {
		super.onCreate();
		PublicMethod.outLog(TAG, "onCreate()");
	    messageRouter.addMessageListener(notifyService);
	    xmppService.addConnectionListener(this);
        HttpUser.channel = "";
		HttpUser.Imei = PublicMethod.getImei(MsgService.this);
		HttpUser.MAC = PublicMethod.getMacAdress(MsgService.this);
		HttpUser.URl_ALl=getUrlBase();//正式线
		HttpUser.URL_POST = HttpUser.URl_ALl + "/gamepro/request";//数据请求接口
		connectivityReceiver.bind();
	}

	@Override
	public IBinder onBind(Intent intent) {
		PublicMethod.outLog(TAG, "onBind()");
		return null;
	}
	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		startForeground(true);
		Notification notification = new Notification(R.drawable.icon, "陌游", System.currentTimeMillis());
//		Intent notificationIntent = new Intent(this, StartActivity.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//		notification.setLatestEventInfo(this, "陌游","陌游正在后台运行", pendingIntent);
		startForeground(0, notification);
		PublicMethod.outLog(TAG, "onStartCommand()");
	    registerNotificationReceiver();//开始注册通知消息广播
	    connectedXmppService();
		return START_STICKY;
	}
	private String getUrlBase() {
		try {
			ApplicationInfo appInfo = this.getPackageManager().getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
			return appInfo.metaData.getString("BASE_URL");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		PublicMethod.outLog(TAG, "onDestroy()");
		unregisterNotificationReceiver();//取消通知消息注册广播
		connectivityReceiver.unbind(MsgService.this);
	}

	private void connectedXmppService() {
		PublicMethod.outLog(TAG, "start connect to xmpp notification");
		xmppService.setXmppAddressGetter(new IXmppAddressGetter() {
			
			@Override
			public Object[] getXmppAddress() throws XMPPException {
				PublicMethod.outLog(TAG, "try to get xmpp notification config");
				String result=HttpService.getXmppNotifyServiceInfo();
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
				ret[3]=HttpUser.Imei;
				ret[4]=HttpUser.Imei;
				PublicMethod.outLog(TAG, "xmpp notification config is "+ret[0]+ret[1]+ret[2]+ret[3]+ret[4]);
				return ret;
			}
		});
		reconnectionManager.reconnectNow();
	}
	@Override
	public void connectionClosed() {
		// TODO Auto-generated method stub
		PublicMethod.outLog(TAG, "connectionClosed");
		//sendNotifi("消息(未连接)",2);
	}
	@Override
	public void connectionClosedOnError(Exception e) {
		PublicMethod.outLog(TAG, "connectionClosedOnError");
		//sendNotifi("消息(未连接)",3);
	}
	@Override
	public void reconnectingIn(int seconds) {
		PublicMethod.outLog(TAG, "reconnectingIn");
		//sendNotifi("消息("+seconds+"后重新连接)",4);
	}
	@Override
	public void reconnectionSuccessful() {
		PublicMethod.outLog(TAG, "reconnectionSuccessful");
		//sendNotifi("消息(已连接)",5);
	}
	@Override
	public void reconnectionFailed(Exception e) {
		PublicMethod.outLog(TAG, "reconnectionFailed");
		//sendNotifi("消息(重连失败)",6);
	}

	/**
	 * 注册通知消息广播
	 */
	private void registerNotificationReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_SHOW_BACKGROUND_NOTIFICATION);
		registerReceiver(notificationReceiver, filter);
	}
	/**
	 * 取消通知消息广播
	 */
	private void unregisterNotificationReceiver() {
		try{
			if (notificationReceiver != null) {
				unregisterReceiver(notificationReceiver);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void reconnectingStoped() {
		// TODO Auto-generated method stub
		
	}
}

