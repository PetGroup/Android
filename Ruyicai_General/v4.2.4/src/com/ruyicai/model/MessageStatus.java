package com.ruyicai.model;

public enum MessageStatus {
	


	Sending(0),SendFail(1),ServerReceivd(2),UserReceived(3),UserRead(4);
	
	private int value;
	
	public int getValue() {
		return value;
	}

	private MessageStatus(int value) {
		this.value=value;
	}
	public static MessageStatus getMessageStatusForValue(int value){
		for (MessageStatus messageStatus : MessageStatus.values()) {  
	         if (messageStatus.getValue() == value) {  
	            	return messageStatus;
	         }  
	    }  
		return null;
	}
}
