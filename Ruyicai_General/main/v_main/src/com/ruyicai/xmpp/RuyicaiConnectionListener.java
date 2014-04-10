package com.ruyicai.xmpp;

import org.jivesoftware.smack.ConnectionListener;

public interface RuyicaiConnectionListener extends ConnectionListener {
	
	public void reconnectingStoped();
	
}
