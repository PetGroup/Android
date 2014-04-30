package com.ruyicai.receiver;

import roboguice.receiver.RoboBroadcastReceiver;

import com.google.inject.Inject;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.MyMessage;
import com.ruyicai.xmpp.XmppService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MsgClientReceiver extends RoboBroadcastReceiver {
	@Override
    protected void handleReceive(Context context, Intent intent) {
        // proper template method to handle the receive
		if (Constants.CLIENT_MSG_RECIVER_ACTION.equals(intent.getAction())){
			MyMessage myMessage = intent.getParcelableExtra("sendMsg");
			//xmppService.sendMsg(myMessage);
			//保存数据库
			//前台数据刷新,(用监听)
			Log.i("yejc", "=============myMessage="+myMessage.getFrom());
		}
		
    }
}
