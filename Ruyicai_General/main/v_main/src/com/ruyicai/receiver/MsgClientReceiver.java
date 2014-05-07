package com.ruyicai.receiver;

import java.util.ArrayList;
import java.util.List;

import roboguice.receiver.RoboBroadcastReceiver;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.constant.Constants;
import com.ruyicai.controller.service.MessageService;
import com.ruyicai.model.MyMessage;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.xmpp.IMessageListerner;
import android.content.Context;
import android.content.Intent;

@Singleton
public class MsgClientReceiver extends RoboBroadcastReceiver {
	@Inject MessageService messageService;
//	List<IMessageListerner> messageListerners=new ArrayList<IMessageListerner>();
	private static String TAG = "MsgClientReceiver";
	List<IMessageListerner> messageClientListerners=new ArrayList<IMessageListerner>();
	@Override
    protected void handleReceive(Context context, Intent intent) {
        // proper template method to handle the receive
		if (Constants.CLIENT_MSG_RECIVER_ACTION.equals(intent.getAction())){
			MyMessage myMessage = intent.getParcelableExtra("sendMsg");
			//xmppService.sendMsg(myMessage);
			//保存数据库
			//前台数据刷新,(用监听)
			PublicMethod.outLog(TAG, "client 收到-->>"+myMessage.getFrom()+"--"+myMessage.getBody()+"--"+myMessage.getMsgTime());
			for(IMessageListerner messageListerner:messageClientListerners){
				if(messageListerner == null){
					continue;
				}
				messageListerner.onMessage(myMessage);
			}
	
		} else if (Constants.MSG_SEND_FAIL_RECIVER_ACTION.equals(intent.getAction())) {
			MyMessage myMessage = intent.getParcelableExtra("sendMsg");
			messageService.messageSendFail(myMessage);
		}
		
    }
	public void addMessageListener(IMessageListerner messageListerner){
		if(this.messageClientListerners.contains(messageListerner)){
			return;
		}
		this.messageClientListerners.add(messageListerner);
	}

	public void removeMessageListener(IMessageListerner messageListerner){
		if(this.messageClientListerners.contains(messageListerner)){
			this.messageClientListerners.remove(messageListerner);
		}
	}
}
