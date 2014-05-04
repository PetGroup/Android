package com.ruyicai.receiver;

import java.util.ArrayList;
import java.util.List;

import roboguice.receiver.RoboBroadcastReceiver;

import com.google.inject.Inject;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.MyMessage;
import com.ruyicai.xmpp.IMessageListerner;
import com.ruyicai.xmpp.XmppService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MsgClientReceiver extends RoboBroadcastReceiver {
	List<IMessageListerner> messageListerners=new ArrayList<IMessageListerner>();
	@Override
    protected void handleReceive(Context context, Intent intent) {
        // proper template method to handle the receive
		if (Constants.CLIENT_MSG_RECIVER_ACTION.equals(intent.getAction())){
			MyMessage myMessage = intent.getParcelableExtra("sendMsg");
			//xmppService.sendMsg(myMessage);
			//保存数据库
			//前台数据刷新,(用监听)
	
		}
		
    }
	public void addMessageListener(IMessageListerner messageListerner){
		if(this.messageListerners.contains(messageListerner)){
			return;
		}
		this.messageListerners.add(messageListerner);
	}

	public void removeMessageListener(IMessageListerner messageListerner){
		if(this.messageListerners.contains(messageListerner)){
			this.messageListerners.remove(messageListerner);
		}
	}
}
