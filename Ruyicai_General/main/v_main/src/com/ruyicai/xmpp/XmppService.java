package com.ruyicai.xmpp;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.PrivacyProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.bytestreams.socks5.provider.BytestreamsProvider;
import org.jivesoftware.smackx.packet.ChatStateExtension;
import org.jivesoftware.smackx.packet.LastActivity;
import org.jivesoftware.smackx.packet.OfflineMessageInfo;
import org.jivesoftware.smackx.packet.OfflineMessageRequest;
import org.jivesoftware.smackx.packet.SharedGroupsInfo;
import org.jivesoftware.smackx.provider.AdHocCommandDataProvider;
import org.jivesoftware.smackx.provider.DataFormProvider;
import org.jivesoftware.smackx.provider.DelayInformationProvider;
import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.provider.MUCAdminProvider;
import org.jivesoftware.smackx.provider.MUCOwnerProvider;
import org.jivesoftware.smackx.provider.MUCUserProvider;
import org.jivesoftware.smackx.provider.MessageEventProvider;
import org.jivesoftware.smackx.provider.MultipleAddressesProvider;
import org.jivesoftware.smackx.provider.RosterExchangeProvider;
import org.jivesoftware.smackx.provider.StreamInitiationProvider;
import org.jivesoftware.smackx.provider.VCardProvider;
import org.jivesoftware.smackx.provider.XHTMLExtensionProvider;
import org.jivesoftware.smackx.search.UserSearch;

//import com.chatgame.application.MyApplication;
//import com.chatgame.data.service.MessageSendListener;
//import com.chatgame.data.service.MessageService;
//import com.chatgame.model.HttpUser;
//import com.chatgame.model.MyMessage;
//import com.chatgame.utils.common.PublicMethod;
//import com.chatgame.utils.common.StringUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.controller.listerner.msglisterner.MessageSendListener;
import com.ruyicai.controller.service.MessageService;
import com.ruyicai.model.HttpUser;
import com.ruyicai.model.MyMessage;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.StringUtils;

@Singleton
public class XmppService implements ConnectionListener{
	private String TAG = "XmppService";
	private String servername;
	private XMPPConnection connection;
	private IXmppAddressGetter xmppAddressGetter;


	@Inject private MessageRouter packetListener;
	private List<RuyicaiConnectionListener> connectionListeners=new CopyOnWriteArrayList<RuyicaiConnectionListener>();
	private List<ConnectionCreationListener> connectionCreationListeners=new CopyOnWriteArrayList<ConnectionCreationListener>();
	private List<MessageSendListener> messageSendListeners=new CopyOnWriteArrayList<MessageSendListener>();
	@Inject private ReconnectionManager reconnectionManager;

	public void sendMsg(MyMessage myMessage) {
		try {
			Message mCustomMessage = new Message();
			mCustomMessage.setPacketID(myMessage.getId());
			mCustomMessage.setType(myMessage.getType());
			mCustomMessage.setBody(myMessage.getBody());
			mCustomMessage.setMsgtype(myMessage.getMsgtype());
			mCustomMessage.setMsgTime(myMessage.getMsgTime());
//			mCustomMessage.setMsgTime(myMessage.getMsgTime().getTime()+"");
			if(StringUtils.isNotEmty(myMessage.getPayLoad())){
				mCustomMessage.setPayload(myMessage.getPayLoad());
			}
			if (mCustomMessage.getFrom() == null) {
				mCustomMessage.setFrom(connection.getUser());
	        }
			if (PublicMethod.isGroupChat(mCustomMessage.getMsgtype())) {
				mCustomMessage.setTo(myMessage.getToWho()+"@group."+connection.getServiceName());
			} else {
				mCustomMessage.setTo(myMessage.getToWho()+"@"+connection.getServiceName());
			}
			PublicMethod.outLog(TAG, "发送的消息-->>"+mCustomMessage.toXML().toString());
	        connection.sendPacket(mCustomMessage);
			for(MessageSendListener messageSendListener:messageSendListeners){
				messageSendListener.onSendSuccess();
			}
		} catch (Exception e) {
			e.printStackTrace();
			PublicMethod.outLog(TAG, "sendMsg: " + e.getMessage());
		}
	}
	
	/**
	 * Open Connection 连接超时未完成 待续
	 * */
	private void openConnection(String userName,String passwd)throws Exception {
		if (connection.isConnected()) {
			return;
		}
		Roster roster = connection.getRoster();
	    roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
		connection.addPacketListener(packetListener,null); // 包监听
		connection.connect();// 连接到服务器
		PublicMethod.outLog(TAG," connection =" + connection.isConnected());
		// 配置各种Provider，如果不配置，则会无法解析数据
		configureConnection(ProviderManager.getInstance());
		if (!connection.isAuthenticated()) {
			connection.login(userName, passwd,HttpUser.Imei);
			PublicMethod.outLog(TAG, "login "+"success");

			if (connection.isConnected()) {
				PublicMethod.outLog(TAG + " 登陆成功", connection.getUser());
				connection.addConnectionListener(this);
				connection.addConnectionListener(reconnectionManager);
			}
		    roster.addRosterListener(new RosterListener() {
				@Override
				public void entriesAdded(Collection<String> addresses) {
					PublicMethod.outLog(TAG, "entriesAdded");
				}
				@Override
				public void entriesUpdated(Collection<String> addresses) {
					PublicMethod.outLog(TAG, "entriesUpdated");
				}
				@Override
				public void entriesDeleted(Collection<String> addresses) {
					PublicMethod.outLog(TAG, "entriesDeleted");
				}
				@Override
				public void presenceChanged(Presence presence) {
					PublicMethod.outLog(TAG, "presenceChanged - >" + presence.getStatus()
							+ "---------" + presence.getType());
				}
			});
		}
	}
	
	
	public void openConnection() throws NumberFormatException, Exception{
		Object[] xmppAddress = xmppAddressGetter.getXmppAddress();
		openConnection(xmppAddress[0].toString(),Integer.parseInt(xmppAddress[1].toString()),xmppAddress[2].toString(),xmppAddress[3].toString(),xmppAddress[4].toString());
	}

	/**
	 * Configuration
	 * @throws Exception 
	 * */

	private void openConnection(String host, int port, String servername,String userName,String passwd) throws Exception {
		PublicMethod.outLog(TAG, "------- host=" + host.toString()+" port="+port+ " server="+servername+ " userName="+userName);
		if (servername.contains("@")) {
			this.servername = servername.replace("@", "");
		} else {
			this.servername = servername;
		}
		if (null == connection) {
			// Connection.DEBUG_ENABLED = true;// 开启DEBUG模式
			// 配置连接
			if (host == null || servername == null) {
				return;
			}
			ConnectionConfiguration config = new ConnectionConfiguration(host,port, this.servername);
//			ConnectionConfiguration config = new ConnectionConfiguration("192.168.0.201",port,this.servername);
			config.setDebuggerEnabled(true);
			config.setReconnectionAllowed(true);			
			config.setCompressionEnabled(false);
			config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
			config.setSASLAuthenticationEnabled(true);
			// config .setTruststorePath("/system/etc/security/cacerts.bks");
			// config.setTruststorePassword("changeit");
			// config.setTruststoreType("bks");
			config.setSendPresence(true);
			config.setSecurityMode(SecurityMode.disabled);
			connection = new XMPPConnection(config);
		}
		openConnection(userName,passwd);
	}

	/**
	 * 关闭连接
	 */
	public void logout() {
		if (connection != null && connection.isConnected()) {
			connection.disconnect();
		}
		connection = null;
	}
	
	
	public boolean isConnected(){
		return connection!=null&&connection.isConnected();
	}
	/**
	 * xmpp配置
	 */
	private void configureConnection(ProviderManager pm) {
		pm.addIQProvider("query", "jabber:iq:private",
				new PrivateDataManager.PrivateDataIQProvider());
		// Time
		try {
			pm.addIQProvider("query", "jabber:iq:time",
					Class.forName("org.jivesoftware.smackx.packet.Time"));
		} catch (Exception e) {
			e.printStackTrace();
			// PublicMethod.outLog("120_MyXmpp_configureConnection", e.getMessage());
		}
		// Roster Exchange
		pm.addExtensionProvider("x", "jabber:x:roster",
				new RosterExchangeProvider());
		// Message Events
		pm.addExtensionProvider("x", "jabber:x:event",
				new MessageEventProvider());
		// Chat State
		pm.addExtensionProvider("active",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("composing",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("paused",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("inactive",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		pm.addExtensionProvider("gone",
				"http://jabber.org/protocol/chatstates",
				new ChatStateExtension.Provider());
		// XHTML
		pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im",
				new XHTMLExtensionProvider());
		// Group Chat Invitations
		pm.addExtensionProvider("x", "jabber:x:conference",
				new GroupChatInvitation.Provider());
		// Service Discovery # Items //解析房间列表
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#items",
				new DiscoverItemsProvider());
		// Service Discovery # Info //某一个房间的信息
		pm.addIQProvider("query", "http://jabber.org/protocol/disco#info",
				new DiscoverInfoProvider());
		// Data Forms
		pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());
		// MUC User
		pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user",
				new MUCUserProvider());
		// MUC Admin
		pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin",
				new MUCAdminProvider());
		// MUC Owner
		pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner",
				new MUCOwnerProvider());
		// Delayed Delivery
		pm.addExtensionProvider("x", "jabber:x:delay",
				new DelayInformationProvider());
		// Version
		try {
			pm.addIQProvider("query", "jabber:iq:version",
					Class.forName("org.jivesoftware.smackx.packet.Version"));
		} catch (ClassNotFoundException e) {
			// Not sure what's happening here.
		}
		// VCard
		pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());
		// Offline Message Requests
		pm.addIQProvider("offline", "http://jabber.org/protocol/offline",
				new OfflineMessageRequest.Provider());
		// Offline Message Indicator
		pm.addExtensionProvider("offline",
				"http://jabber.org/protocol/offline",
				new OfflineMessageInfo.Provider());
		// Last Activity
		pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());
		// User Search
		pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());
		// SharedGroupsInfo
		pm.addIQProvider("sharedgroup",
				"http://www.jivesoftware.org/protocol/sharedgroup",
				new SharedGroupsInfo.Provider());
		// JEP-33: Extended Stanza Addressing
		pm.addExtensionProvider("addresses",
				"http://jabber.org/protocol/address",
				new MultipleAddressesProvider());
		pm.addIQProvider("si", "http://jabber.org/protocol/si",
				new StreamInitiationProvider());
		pm.addIQProvider("query", "http://jabber.org/protocol/bytestreams",
				new BytestreamsProvider());
		pm.addIQProvider("query", "jabber:iq:privacy", new PrivacyProvider());
		pm.addIQProvider("command", "http://jabber.org/protocol/commands",
				new AdHocCommandDataProvider());
		pm.addExtensionProvider("malformed-action",
				"http://jabber.org/protocol/commands",
				new AdHocCommandDataProvider.MalformedActionError());
		pm.addExtensionProvider("bad-locale",
				"http://jabber.org/protocol/commands",
				new AdHocCommandDataProvider.BadLocaleError());
		pm.addExtensionProvider("bad-payload",
				"http://jabber.org/protocol/commands",
				new AdHocCommandDataProvider.BadPayloadError());
		pm.addExtensionProvider("bad-sessionid",
				"http://jabber.org/protocol/commands",
				new AdHocCommandDataProvider.BadSessionIDError());
		pm.addExtensionProvider("session-expired",
				"http://jabber.org/protocol/commands",
				new AdHocCommandDataProvider.SessionExpiredError());
	}

	@Override
	public void connectionClosed() {
		PublicMethod.outLog(this.getClass().getSimpleName(), "connectionClosed");
		for(RuyicaiConnectionListener connectionListener:connectionListeners){
			connectionListener.connectionClosed();
		}
	}
	@Override
	public void connectionClosedOnError(Exception e) {
		PublicMethod.outLog(this.getClass().getSimpleName(), "connectionClosedOnError");
		for(RuyicaiConnectionListener connectionListener:connectionListeners){
			connectionListener.connectionClosedOnError(e);
		}
	}
	@Override
	public void reconnectingIn(int seconds) {

		PublicMethod.outLog(this.getClass().getSimpleName(), "reconnectingIn");
		for(RuyicaiConnectionListener connectionListener:connectionListeners){
			connectionListener.reconnectingIn(seconds);
		}
	}

	@Override
	public void reconnectionSuccessful() {
		PublicMethod.outLog(this.getClass().getSimpleName(), "reconnectionSuccessful");
		for(RuyicaiConnectionListener connectionListener:connectionListeners){
			connectionListener.reconnectionSuccessful();
		}
	}
	@Override
	public void reconnectionFailed(Exception e) {
		PublicMethod.outLog(this.getClass().getSimpleName(), "reconnectionFailed");
		for(RuyicaiConnectionListener connectionListener:connectionListeners){
			connectionListener.reconnectionFailed(e);
		}
	}
	
	public void addConnectionCreationListener(
			ConnectionCreationListener connectionCreationListener) {
		this.connectionCreationListeners.add(connectionCreationListener);
	}
	
	public void removeConnectionCreationListener(
			ConnectionCreationListener connectionCreationListener) {
		this.connectionCreationListeners.remove(connectionCreationListener);
	}
	public void addConnectionListener(RuyicaiConnectionListener connectionListener) {
		this.connectionListeners.add(connectionListener);
	}
	public void removeConnectionListener(RuyicaiConnectionListener connectionListener) {
		this.connectionListeners.remove(connectionListener);
	}
	
	public void addMessageSendListener(MessageSendListener messageSendListener){
		if(!this.messageSendListeners.contains(messageSendListener)){
			this.messageSendListeners.add(messageSendListener);
		}
	}
	
	public void removeMessageSendListener(MessageSendListener messageSendListener){
		if(this.messageSendListeners.contains(messageSendListener)){
			this.messageSendListeners.remove(messageSendListener);
		}
	}
	
	public static interface IXmppAddressGetter{
		public Object[] getXmppAddress() throws XMPPException;
	}
	
	public void setXmppAddressGetter(IXmppAddressGetter xmppAddressGetter) {
		this.xmppAddressGetter = xmppAddressGetter;
	}
}
