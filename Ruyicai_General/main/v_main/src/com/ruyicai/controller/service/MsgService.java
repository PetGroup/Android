package com.ruyicai.controller.service;

import roboguice.service.RoboService;
import android.content.Intent;
import android.os.IBinder;

import com.google.inject.Inject;
import com.ruyicai.model.HttpUser;
import com.ruyicai.net.ConnectivityReceiver;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.xmpp.RuyicaiConnectionListener;
import com.ruyicai.xmpp.ReconnectionManager;

public class MsgService extends RoboService implements RuyicaiConnectionListener {
	private static final String TAG = "MsgService";
	@Inject ConnectivityReceiver connectivityReceiver;

	@Inject private InitBackGroundService initService;
	@Override
	public void onCreate() {
		super.onCreate();
		PublicMethod.outLog(TAG, "onCreate()");
		initService.initService(this);
        HttpUser.channel = "";
		HttpUser.Imei = PublicMethod.getImei(MsgService.this);
		HttpUser.MAC = PublicMethod.getMacAdress(MsgService.this);
		HttpUser.URl_ALl= PublicMethod.getUrlBase(MsgService.this);//正式线
		//HttpUser.URL_POST = HttpUser.URl_ALl + "/gamepro/request";//数据请求接口
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
//		Notification notification = new Notification(R.drawable.icon, "如意彩", System.currentTimeMillis());
//		Intent notificationIntent = new Intent(this, StartActivity.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//		notification.setLatestEventInfo(this, "如意彩","如意彩正在后台运行", pendingIntent);
//		startForeground(0, notification);
		PublicMethod.outLog(TAG, "onStartCommand()");	
		initService.registerReceiver(MsgService.this);
		initService.connectedXmppService();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		PublicMethod.outLog(TAG, "onDestroy()");
		initService.unRegisterReceiver(MsgService.this);
		connectivityReceiver.unbind(MsgService.this);
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

	@Override
	public void reconnectingStoped() {
		// TODO Auto-generated method stub
		
	}
}

