package com.ruyicai.controller.service;

import roboguice.event.EventManager;
import android.app.Application;
import android.content.Intent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.model.HttpUser;
import com.ruyicai.net.ConnectivityReceiver;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.xmpp.ReconnectionManager;
import com.ruyicai.xmpp.XmppService;

@Singleton
public class LoginService {
	
	private static String TAG = "LoginService";
	//private List<LogoutListener> logoutListeners=new ArrayList<LogoutListener>();
	private Intent mServiceIntent;
	@Inject private Application application;
	//@Inject private DbHelper dbHelper;
    @Inject private XmppService xmppService;
    @Inject private ReconnectionManager reconnectionManager;
   //@Inject private LocationService locationService;
	@Inject ConnectivityReceiver connectivityReceiver;
	@Inject EventManager eventManager;
	//@Inject CommonsharedPreferences commonsharedPreferences;
	//@Inject UserService userService;


	//@Inject FriendListService friendListService;
	
	
	public void postLogin(String userid,String token,final String xmppAddress,final String domain){
		PublicMethod.outLog(TAG, "postLogin"+"userid:"+userid+"   token:"+token);
		HttpUser.isLogin=true;
		HttpUser.userId = userid;
		HttpUser.token = token;
		PublicMethod.outLog(TAG, "new token= "+HttpUser.token);
		reconnectionManager.reconnectNow();
		startMsgService();
		//isFirstLogin();
		//userService.updateOffLineMsg();
	}
//	private void isFirstLogin() {
//		PackageInfo info;
//		try {
//			info = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
//			int currentVersion = info.versionCode;
//			commonsharedPreferences.putStringValue(Constants._VERSIONNAME, info.versionName);
//			int lastVersion = commonsharedPreferences.getVersionCode();  
//			PublicMethod.outLog(TAG, "versionCode>>>>>>   "+currentVersion);
//			PublicMethod.outLog(TAG, "versionName>>>>>>   "+info.versionName);
//			if (currentVersion != lastVersion) {  
//				commonsharedPreferences.putBooleanValue("isFriendFirstLogin", true);
//				commonsharedPreferences.putBooleanValue("isAttentionFirstLogin", true);
//				commonsharedPreferences.putBooleanValue("isFansFirstLogin", true);
//				commonsharedPreferences.putIntValue(Constants.VERSIONCODE, currentVersion);
//			} 
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//		}  
//	}
	public void startLocation(){
		//locationService.refreshLocation();
	}
	/**
	 * 开启消息监听服务
	 */
	public void startMsgService() {
		mServiceIntent = new Intent(application, MsgService.class);
		application.startService(mServiceIntent);
		
	}

	/**
	 * 关闭消息监听服务
	 */
	public void stopMsgService() {
		try {
			application.stopService(mServiceIntent);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void postLogout(){
		
		HttpUser.isLogin=false;
		HttpUser.token="";
		HttpUser.userId="";
		initSharedPre();
		//PhotoUtils.deleteImageCacheFile(PhotoUtils.UPLOAD_TEMP_IMAGE);
//		dbHelper.CleanContacts();
		xmppService.logout();
		stopMsgService();
//		for(LogoutListener logoutListener:logoutListeners){
//			logoutListener.onLogout();
//		}
	}
	
	private void initSharedPre() {
//		commonsharedPreferences.putStringValue(Constants.TOKEN_SHARED_KEY, "");
//		commonsharedPreferences.putStringValue(Constants.USERID_SHARED_KEY, "");
//		commonsharedPreferences.putBooleanValue("isFriendFirstLogin", true);
//		commonsharedPreferences.putBooleanValue("isAttentionFirstLogin", true);
//		commonsharedPreferences.putBooleanValue("isFansFirstLogin", true);
//		commonsharedPreferences.clearNearPeopleScreen();
//		commonsharedPreferences.clearSameServiceScreen();
//		commonsharedPreferences.clearRankName();
	}

}
