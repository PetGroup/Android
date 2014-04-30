package com.ruyicai.receiver;

import com.ruyicai.constant.Constants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MsgBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Constants.SERVER_MSG_RECIVER_ACTION.equals(intent.getAction())) {
			
		} else if (Constants.CLIENT_MSG_RECIVER_ACTION.equals(intent.getAction())){
			
		}
		Log.i("yejc", "========intent.getAction()="+intent.getAction());
	}

}
