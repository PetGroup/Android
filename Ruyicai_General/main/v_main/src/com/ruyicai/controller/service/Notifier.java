package com.ruyicai.controller.service;

import java.util.ArrayList;
import java.util.List;


import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.constant.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.PullParseXml;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * 消息栏显示新消息类
 * @author
 *
 */
public class Notifier {
	private Context context;
	private NotificationManager notificationManager;
	private Notification notification;
	boolean soundEnabled;
	boolean vibrateEnabled;
    
	public Notifier(Context context) {
		this.context = context;
		this.notification = new Notification();
		this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}
	/**
	 * 声音提示
	 * @param soundEnabled
	 */
	public void setSound(boolean soundEnabled) {
		this.soundEnabled = soundEnabled;
	}
	/**
	 * 震动提示
	 * @param vibrateEnabled
	 */
	public void setVibrate(boolean vibrateEnabled) {
		this.vibrateEnabled = vibrateEnabled;
	}
	public void notify(String title, String message,String pushpage) {
//		if(packetId==null){
//			return ;
//		}
//		String packetIds=PullParseXml.getStringFromSd(context,Constants.OFF_LINE_MSG_PACKET_IDS);
//		List<String> packetidss=getPacketId(packetIds);
//		if(packetidss==null||packetidss.size()==0){
//			PullParseXml.SaveFile(context,packetId.trim()+",", Constants.OFF_LINE_MSG_PACKET_IDS, true);
//			packetIds=PullParseXml.getStringFromSd(context,Constants.OFF_LINE_MSG_PACKET_IDS);
//			packetidss=getPacketId(packetIds);
//		}
//		if(packetidss != null && packetidss.size() != 0 && packetidss.contains(packetId)){
//			return;
//		}
//		PullParseXml.SaveFile(context,packetId.trim()+",", Constants.OFF_LINE_MSG_PACKET_IDS, true);
//		
		notification.icon = R.drawable.icon;
		notification.defaults = Notification.DEFAULT_LIGHTS;
		if (soundEnabled) {
			notification.defaults |= Notification.DEFAULT_SOUND;
		}
		if (vibrateEnabled) {
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.when = System.currentTimeMillis();
//		String tvStr = PullParseXml.getStringFromSd(context, Constants.MSG_TV_SHOW_SETTING);
//		if(!"".equals(tvStr)){
//			if("1".equals(tvStr)){
//				notification.tickerText = "陌游新消息";
//			} 
//		} else {
//			notification.tickerText = title+":"+message;
//		}
		
		
		Intent intent=getIntent(pushpage);
		intent.putExtra(Constants.NOTIFICATION_TITLE, title);//通知栏标题
		intent.putExtra(Constants.NOTIFICATION_MESSAGE, message);//通知栏显示的消息
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
//		PullParseXml.SaveFile(context,userId.trim()+",", Constants.OFF_LINE_USER_NUM, true);
//		String userIdStr=PullParseXml.getStringFromSd(context,Constants.OFF_LINE_USER_NUM);
//		
//		int userIds=getofferLineUserIdNum(userIdStr);
//		
//		if(userIds!=0){
//			int msgNum=getofferLineMsgNum();
//			msgNum+=1;
//			PullParseXml.SaveFile(context,String.valueOf(msgNum), Constants.OFF_LINE_MSG_NUM, false);
//			if(userIds ==1){
//				if("1".equals(tvStr) && !"每日一闻".equals(tvStr)){
//					notification.setLatestEventInfo(context, "陌游", msgNum + "条新消息", contentIntent);
//				} else {
//					notification.setLatestEventInfo(context, title+"(" + msgNum + "条新消息)", message, contentIntent);
//				}
//			} else {
//				notification.setLatestEventInfo(context, "陌游", "有"+userIds+"个联系人给你发来"+msgNum+"条消息", contentIntent);
//			}

//		}
	notification.setLatestEventInfo(context, "如意彩", "新通知", contentIntent);
	notificationManager.notify(0, notification);
	}
	public void notify(String userId,String title, String message,String packetId) {
		if(packetId==null){
			return ;
		}
		String packetIds=PullParseXml.getStringFromSd(context,Constants.OFF_LINE_MSG_PACKET_IDS);
		List<String> packetidss=getPacketId(packetIds);
		if(packetidss==null||packetidss.size()==0){
			PullParseXml.SaveFile(context,packetId.trim()+",", Constants.OFF_LINE_MSG_PACKET_IDS, true);
			packetIds=PullParseXml.getStringFromSd(context,Constants.OFF_LINE_MSG_PACKET_IDS);
			packetidss=getPacketId(packetIds);
		}
		if(packetidss != null && packetidss.size() != 0 && packetidss.contains(packetId)){
			return;
		}
		PullParseXml.SaveFile(context,packetId.trim()+",", Constants.OFF_LINE_MSG_PACKET_IDS, true);
		
		notification.icon = R.drawable.icon;
		notification.defaults = Notification.DEFAULT_LIGHTS;
		if (soundEnabled) {
			notification.defaults |= Notification.DEFAULT_SOUND;
		}
		if (vibrateEnabled) {
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.when = System.currentTimeMillis();
		String tvStr = PullParseXml.getStringFromSd(context, Constants.MSG_TV_SHOW_SETTING);
		if(!"".equals(tvStr)){
			if("1".equals(tvStr)){
				notification.tickerText = "陌游新消息";
			} 
		} else {
			notification.tickerText = title+":"+message;
		}
		
		
		Intent intent=getIntent(userId, title, message);
		intent.putExtra(Constants.NOTIFICATION_TITLE, title);//通知栏标题
		intent.putExtra(Constants.NOTIFICATION_MESSAGE, message);//通知栏显示的消息
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
		PullParseXml.SaveFile(context,userId.trim()+",", Constants.OFF_LINE_USER_NUM, true);
		String userIdStr=PullParseXml.getStringFromSd(context,Constants.OFF_LINE_USER_NUM);
		
		int userIds=getofferLineUserIdNum(userIdStr);
		
		if(userIds!=0){
			int msgNum=getofferLineMsgNum();
			msgNum+=1;
			PullParseXml.SaveFile(context,String.valueOf(msgNum), Constants.OFF_LINE_MSG_NUM, false);
			if(userIds ==1){
				if("1".equals(tvStr) && !"每日一闻".equals(tvStr)){
					notification.setLatestEventInfo(context, "陌游", msgNum + "条新消息", contentIntent);
				} else {
					notification.setLatestEventInfo(context, title+"(" + msgNum + "条新消息)", message, contentIntent);
				}
			} else {
				notification.setLatestEventInfo(context, "陌游", "有"+userIds+"个联系人给你发来"+msgNum+"条消息", contentIntent);
			}
			notificationManager.notify(0, notification);
		}
	}
	/**
	 * 点击通知栏的消息跳转意图
	 * @param notifyIdStr//用户Id
	 * @param message 消息内容
	 * @return
	 */
	private Intent getIntent(String pushpage){
		if (Constants.BUYHALL.equals(pushpage)) {
			//return new Intent(context, MainActivity.class);
		} else if (Constants.USERCENTER.equals(pushpage)) {
			//return new Intent(context, MainActivity.class);
		} else if (Constants.JOINBUYHALL.equals(pushpage)) {
			//return new Intent(context, MainActivity.class);
		} else if (Constants.OPENCENTER.equals(pushpage)) {
			
		}
		return null;
	}
	/**
	 * 点击通知栏的消息跳转意图
	 * @param notifyIdStr//用户Id
	 * @param message 消息内容
	 * @return
	 */
	private Intent getIntent(String userId,String title,String message){
		ActivityManager activityManager = (ActivityManager) (context.getSystemService(android.content.Context.ACTIVITY_SERVICE));
		List<RunningTaskInfo> r=activityManager.getRunningTasks(100);
		if(r.size()>1){
			RunningTaskInfo rt=r.get(1);
			String topActivity=rt.topActivity.getClassName();
			if("com.chatgame.activity.message.ChatActivity".equals(topActivity)){
				//Intent intent = new Intent(context, ChatActivity.class);
				//intent.putExtra(Constants.INTENT_USER_KEY, notifyIdStr);
				//return intent;
			}else {
				if (PublicMethod.isAppRunning(context)) {
					//return new Intent(context, MainActivity.class);
				} else {
					//return new Intent(context, StartActivity.class);
				}
			}
		}else {
			if (PublicMethod.isAppRunning(context)) {
				//return new Intent(context, MainActivity.class);
			} else {
				//return new Intent(context, StartActivity.class);
			}
		}

		return null;
	}
	/**
	 * 离线消息的数量
	 * @return
	 */
	private int getofferLineMsgNum(){
		String offlineMsgNum=PullParseXml.getStringFromSd(context,Constants.OFF_LINE_MSG_NUM);
		int msgNum=0;
		if(offlineMsgNum==null||"".equals(offlineMsgNum)){
			msgNum=0;
		}else {
			msgNum=Integer.parseInt(offlineMsgNum);
		}
		return msgNum;
	}
	/**
	 * 离线消息用户Id的数量
	 * @param userIdStr
	 * @return
	 */
	private int getofferLineUserIdNum(String userIdStr){
		if(userIdStr==null){
			return 0;
		}
		List<String> list=new ArrayList<String>();
		String[] userId=userIdStr.trim().split(",");
		for(String str:userId){
			if(!list.contains(str)){
				list.add(str);
			}
		}
		return list.size();
	}
	/**
	 * 离线消息Id
	 * @param packetIds
	 * @return
	 */
	private List<String> getPacketId(String packetIds){
		if(packetIds==null){
			return null;
		}
		List<String> list=new ArrayList<String>();
		String[] userId=packetIds.trim().split(",");
		for(String str:userId){
			list.add(str);
		}
		return list;
	}
}
