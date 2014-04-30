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

public class MsgBroadcastReceiver extends RoboBroadcastReceiver {
@Inject XmppService xmppService;
//	@Override
//	public void onReceive(Context context, Intent intent) {
//		if (Constants.SERVER_MSG_RECIVER_ACTION.equals(intent.getAction())) {
//			MyMessage myMessage = intent.getParcelableExtra("sendMsg");
//			
//		} else if (Constants.CLIENT_MSG_RECIVER_ACTION.equals(intent.getAction())){
//			
//		}
//		Log.i("yejc", "========intent.getAction()="+intent.getAction());
//	}
	@Override
    protected void handleReceive(Context context, Intent intent) {
        // proper template method to handle the receive
		if (Constants.SERVER_MSG_RECIVER_ACTION.equals(intent.getAction())) {
			MyMessage myMessage = intent.getParcelableExtra("sendMsg");
			xmppService.sendMsg(myMessage);
		} else if (Constants.CLIENT_MSG_RECIVER_ACTION.equals(intent.getAction())){
			
		}
		
    }
}
