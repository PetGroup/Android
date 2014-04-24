package com.ruyicai.controller.service;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;

import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.util.PullParseXml;
import com.ruyicai.util.date.MyDate;

/**
 * 网络状态广播接收器
 * @author
 *
 */
@Singleton
public final class NotificationBackGroundReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		/**
		 * 判断Action是否是自己注册的Action（注册广播的时候）
		 */
		if (Constants.ACTION_SHOW_BACKGROUND_NOTIFICATION.equals(action)) {
			
			String fromUserName = intent.getStringExtra("nickName");
			String content = intent.getStringExtra("body");
			String notifyId = intent.getStringExtra("userId");
			String packetId = intent.getStringExtra("packetId");
			Notifier notifier = new Notifier(context);
			
			String timeStr = PullParseXml.getStringFromSd(context, Constants.MSG_TIME_FRAME_SETTING);
			boolean night = MyDate.isNight(MyDate.dateToStrLong(new Date()),timeStr);
        	if(!night){
        		String soundStr = PullParseXml.getStringFromSd(context, Constants.MSG_REMIND_SOUND_SETTING);
        		String vibrateStr = PullParseXml.getStringFromSd(context, Constants.MSG_REMIND_VIBRATE_SETTING);
        		if("".equals(soundStr) || soundStr == null){
        			notifier.setSound(true);
        		} 
        		if("1".equals(soundStr)){
        			notifier.setSound(true); //声音
        		}
        		if("".equals(vibrateStr) || vibrateStr == null){
        			notifier.setVibrate(true);
        		} 
        		if("1".equals(vibrateStr)){
        			notifier.setVibrate(true); //震动
        		}
			}
			notifier.notify(notifyId,fromUserName,content,packetId);
		}
	}
}
