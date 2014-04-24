package com.ruyicai.controller.listerner.msglisterner;

public interface MessageSendListener {
	
	public void onSendFail();
	public void onSendSuccess();
	public void onReceive();
	public void onMessageRead();
}
