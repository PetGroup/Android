package com.ruyicai.xmpp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;


import com.google.inject.Singleton;
import com.ruyicai.model.MyMessage;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.StringUtils;

@Singleton
public class MessageRouter implements PacketListener {

	private static final String TAG="MessageRouter";
	List<IMessageListerner> messageListerners=new ArrayList<IMessageListerner>();
	
	@Override
	public void processPacket(Packet packet) {
		if(packet instanceof Message){
			Message message =  (Message)packet;
			PublicMethod.outLog(TAG, "收到-->>"+message.getPacketID()+"--"+message.getBody()+"--"+message.getMsgTime());
			for(IMessageListerner messageListerner:messageListerners){
				if(this.messageListerners==null){
					continue;
				}
				messageListerner.onMessage(getMyMessage(message));
			}
		}
	}
	private MyMessage getMyMessage(Message message) {
        MyMessage myMessage = new MyMessage();
        myMessage.setFrom(message.getFrom());
        myMessage.setMsgtype(message.getMsgtype());
        myMessage.setBody(message.getBody());
        myMessage.setType(message.getType());
        myMessage.setMsgTime(getTime(message.getMsgTime()));
        myMessage.setId(message.getPacketID());
        myMessage.setPayLoad(message.getPayload());
        myMessage.setToWho(message.getTo());
        myMessage.setPushPage(message.getPushpage());
        
        return myMessage;
	}
	/**
	 * 
	 * @param msgTime
	 * @return
	 */
	private String getTime(String msgTime){
		if(!StringUtils.isNotEmty(msgTime)){
			return String.valueOf(new Date().getTime());
		}
		return msgTime;
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
