package com.ruyicai.controller.listerner;

import com.ruyicai.model.MyMessage;


public interface MessageStatusListener {
	
	public void messageSendFail(MyMessage myMessage);

	public void messageServerReceived(MyMessage myMessage);
	
	public void messageUserReceived(MyMessage myMessage);
	
	public void messageUserRead(MyMessage myMessage);

	public void beforeSendMessage(MyMessage myMessage);
}
