package com.ruyicai.xmpp;

import org.jivesoftware.smack.ConnectionListener;

public interface GameConnectionListener extends ConnectionListener {
	
	public void reconnectingStoped();
	
}
