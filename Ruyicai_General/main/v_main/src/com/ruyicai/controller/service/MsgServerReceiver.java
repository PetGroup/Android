package com.ruyicai.controller.service;

import java.util.Date;

import roboguice.receiver.RoboBroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.MyMessage;
import com.ruyicai.util.PullParseXml;
import com.ruyicai.util.date.MyDate;
import com.ruyicai.xmpp.XmppService;

/**
 * 网络状态广播接收器
 * @author
 *
 */
@Singleton
public final class MsgServerReceiver extends RoboBroadcastReceiver {
	
	   @Inject XmppService xmppService;

		@Override
	    protected void handleReceive(Context context, Intent intent) {
		String action = intent.getAction();
		/**
		 * 接受客户端发送消息的处理
		 */
		if (Constants.SERVER_MSG_RECIVER_ACTION.equals(action)) {
			MyMessage myMessage = intent.getParcelableExtra("sendMsg");
			xmppService.sendMsg(myMessage);
		} else if (Constants.ACTION_SHOW_BACKGROUND_NOTIFICATION.equals(action)) {//推送消息
			
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
