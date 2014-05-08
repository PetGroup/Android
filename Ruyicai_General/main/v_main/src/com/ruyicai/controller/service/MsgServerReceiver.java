package com.ruyicai.controller.service;

import java.util.Date;

import roboguice.receiver.RoboBroadcastReceiver;

import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.util.PullParseXml;
import com.ruyicai.util.date.MyDate;
import com.ruyicai.xmpp.XmppService;

/**
 * 网络状态广播接收器
 * @author
 *
 */
@Singleton
public final class MsgServerReceiver extends RoboBroadcastReceiver{
	
	   @Inject XmppService xmppService;

		@Override
	    protected void handleReceive(Context context, Intent intent) {
		String action = intent.getAction();
		/**
		 * 接受客户端发送消息的处理
		 */
          if (Constants.ACTION_SHOW_BACKGROUND_NOTIFICATION.equals(action)) {//推送消息
				
  			String fromUserName = intent.getStringExtra("nickName");
  			String content = intent.getStringExtra("body");
//  			String notifyId = intent.getStringExtra("userId");
//  			String packetId = intent.getStringExtra("packetId");
  			String pushPage = intent.getStringExtra("pushpage");
  			String pushValue = intent.getStringExtra("pushValue");
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
  			
  			//notifier.notify(notifyId,fromUserName,content,packetId);
          	//title,body,pushpage
          	notifier.notify(fromUserName,content,pushPage,pushValue);
		}
	}
}
