package com.ruyicai.controller.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.MyMessage;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.xmpp.IMessageListerner;

@Singleton
public class SendServerMessage implements IMessageListerner{
	@Inject private Context context;
	@Override
	public void onMessage(MyMessage message) {
		if (context != null) {
			Intent intent = new Intent(Constants.CLIENT_MSG_RECIVER_ACTION);
			intent.putExtra("sendMsg", message);
			context.sendBroadcast(intent);
		}
	}
}
