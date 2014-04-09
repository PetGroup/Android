package com.ruyicai.model;

import java.io.Serializable;
import java.util.Date;

import org.jivesoftware.smack.packet.Message.Type;

public class MyMessage implements Serializable,Cloneable {
	private static final long serialVersionUID = 2686634339535727881L;
	/** 时间 **/
//	private Date msgTime;
	private String msgTime;
	private Date receiveTime;
	/** 来自who **/
	private String toWho;
	/** 发给哪位 **/
	private String from;
	/** packetId**/
	private String id;
	private Type type;
	private String msgtype;//自定义字段
    private String payLoad;
    private String body;
    private String msgTag;
    private MessageStatus status=MessageStatus.Sending;

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(String payLoad) {
		this.payLoad = payLoad;
	}


    public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(String date) {
		this.msgTime = date;
	}
//	public Date getMsgTime() {
//		return msgTime;
//	}
//	
//	public void setMsgTime(Date date) {
//		this.msgTime = date;
//	}

	public String getToWho() {
		return toWho;
	}

	public void setToWho(String toWho) {
		this.toWho = toWho;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String fromWho) {
		this.from = fromWho;
	}
	
	
	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getMsgTag() {
		return msgTag;
	}

	public void setMsgTag(String msgTag) {
		this.msgTag = msgTag;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof MyMessage){
			MyMessage other=(MyMessage) o;
			if(!other.getId().equals(this.getId())){
				return false;
			}
			if(!other.getStatus().equals(this.getStatus())){
				return false;
			}
			return true;
			
		}else{
			return false;
		}
		
	}
	
	@Override
	public Object clone() { 
		MyMessage myMessage = new MyMessage();
		myMessage.setId(this.getId());
		myMessage.setStatus(this.getStatus());
		return myMessage;
	}
	
	
}
