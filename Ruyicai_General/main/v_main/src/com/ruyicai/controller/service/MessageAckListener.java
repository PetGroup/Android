package com.ruyicai.controller.service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.controller.listerner.MessageStatusListener;
import com.ruyicai.model.MyMessage;

@Singleton
public class MessageAckListener extends Thread implements MessageStatusListener {
	
	private ConcurrentHashMap<MyMessage,Long> myMessages=new ConcurrentHashMap<MyMessage,Long>();
	private boolean isStoped=false;
	
	@Inject MessageService messageService;
	
	@Override
	public void run() {
		setName("Message Ack");
		messageService.addMessageStatusListener(this);
		while(!isStoped){
			for(MyMessage myMessage:myMessages.keySet()){
				if(new Date().getTime()-myMessages.get(myMessage)>15*1000){
					messageService.messageSendFail(myMessage);
				}
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		messageService.removeMessageStatusListener(this);
		super.run();
	}

	@Override
	public void messageSendFail(MyMessage myMessage) {
		myMessages.remove(myMessage);
	}
	@Override
	public void messageServerReceived(MyMessage message) {
		for(MyMessage myMessage:myMessages.keySet()){
			if(message.getId().equals(myMessage.getId())){
				myMessages.remove(myMessage);
			}
		}
	}
	@Override
	public void messageUserReceived(MyMessage myMessage) {
	}
	@Override
	public void messageUserRead(MyMessage myMessage) {
	}
	@Override
	public void beforeSendMessage(MyMessage myMessage) {
		myMessages.put(myMessage,new Date().getTime());
	}
}
