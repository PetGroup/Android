package com.ruyicai.xmpp;

import java.util.Random;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.StreamError;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ruyicai.net.ConnectivityReceiver;
import com.ruyicai.net.ConnectivityReceiver.OnNetworkAvailableListener;
/**
 * Handles the automatic reconnection process. Every time a connection is dropped without
 * the application explictly closing it, the manager automatically tries to reconnect to
 * the server.<p>
 *
 * The reconnection mechanism will try to reconnect periodically:
 * <ol>
 *  <li>For the first minute it will attempt to connect once every ten seconds.
 *  <li>For the next five minutes it will attempt to connect once a minute.
 *  <li>If that fails it will indefinitely try to connect once every five minutes.
 * </ol>
 *
 * @author Francisco Vives
 */
@Singleton
public class ReconnectionManager implements ConnectionListener,OnNetworkAvailableListener {

    // Holds the connection to the server
    private Thread reconnectionThread;
    private int randomBase = new Random().nextInt(5) + 2; // between 2 and 4 seconds
    @Inject private XmppService xmppService;
    @Inject private ConnectivityReceiver connectivityReceiver;
    
    // Holds the state of the reconnection
    boolean done = false;
    private Boolean networkAvailable;

	private int remainingSeconds;
    /**
     * Holds the current number of reconnection attempts
     */
    private int attempts = 0;


    /**
     * Returns true if the reconnection mechanism is enabled.
     *
     * @return true if automatic reconnections are allowed.
     */
    private boolean isReconnectionAllowed() {
    	if(done){
    		return false;
    	}
    	if(networkAvailable==null){
    		networkAvailable=connectivityReceiver.checkConnectionOnDemand();
    	}
    	if(!networkAvailable){
    		return false;
    	}
    	if(xmppService.isConnected()){
    		return false;
    	}
        return true ;
    }
     

    public void reconnectNow() {
    	this.done=false;
    	this.attempts=0;
    	this.remainingSeconds=0;
    	this.reconnect();
    }
    /**
     * Starts a reconnection mechanism if it was configured to do that.
     * The algorithm is been executed when the first connection error is detected.
     * <p/>
     * The reconnection mechanism will try to reconnect periodically in this way:
     * <ol>
     * <li>First it will try 6 times every 10 seconds.
     * <li>Then it will try 10 times every 1 minute.
     * <li>Finally it will try indefinitely every 5 minutes.
     * </ol>
     */
    synchronized protected void reconnect() {
        if (this.isReconnectionAllowed()) {
            // Since there is no thread running, creates a new one to attempt
            // the reconnection.
            // avoid to run duplicated reconnectionThread -- fd: 16/09/2010
            if (reconnectionThread!=null && reconnectionThread.isAlive()) return;
            
            attempts=0;
            reconnectionThread = new Thread() {
             			

                /**
                 * Returns the number of seconds until the next reconnection attempt.
                 *
                 * @return the number of seconds until the next reconnection attempt.
                 */
                private int timeDelay() {
                    attempts++;
                    if (attempts > 13) {
                	return randomBase*6*5;      // between 2.5 and 7.5 minutes (~5 minutes)
                    }
                    if (attempts > 7) {
                	return randomBase*6;       // between 30 and 90 seconds (~1 minutes)
                    }
                    if(attempts<=1){
                    	return 0;
                    }
                    return randomBase;       // 10 seconds
                }

                /**
                 * The process will try the reconnection until the connection succeed or the user
                 * cancell it
                 */
                public void run() {
                    // The process will try to reconnect until the connection is established or
                    // the user cancel the reconnection process {@link Connection#disconnect()}
                    while (ReconnectionManager.this.isReconnectionAllowed()) {
                        remainingSeconds = timeDelay();
                        // Sleep until we're ready for the next reconnection attempt. Notify
                        // listeners once per second about how much time remains before the next
                        // reconnection attempt.
                        while (ReconnectionManager.this.isReconnectionAllowed() &&
                                remainingSeconds > 0)
                        {
                            try {
                                Thread.sleep(1000);
                                remainingSeconds--;
                                ReconnectionManager.this
                                        .notifyAttemptToReconnectIn(remainingSeconds);
                            }
                            catch (InterruptedException e1) {
                                e1.printStackTrace();
                                // Notify the reconnection has failed
                                ReconnectionManager.this.notifyReconnectionFailed(e1);
                            }
                        }

                        // Makes a reconnection attempt
                        try {
                            if (ReconnectionManager.this.isReconnectionAllowed()) {
                                xmppService.openConnection();
                               	xmppService.reconnectionSuccessful();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            ReconnectionManager.this.notifyReconnectionFailed(e);
                        }
                    }
                }
            };
            reconnectionThread.setName("Smack Reconnection Manager");
            reconnectionThread.setDaemon(true);
            reconnectionThread.start();
        }
    }

    /**
     * Fires listeners when a reconnection attempt has failed.
     *
     * @param exception the exception that occured.
     */
    protected void notifyReconnectionFailed(Exception exception) {
        xmppService.reconnectionFailed(exception);
    }

    /**
     * Fires listeners when The Connection will retry a reconnection. Expressed in seconds.
     *
     * @param seconds the number of seconds that a reconnection will be attempted in.
     */
    protected void notifyAttemptToReconnectIn(int seconds) {
    	if (isReconnectionAllowed()) {
        	xmppService.reconnectingIn(seconds);
        }
    }

    public void connectionClosed() {
        done = true;
    }

    public void connectionClosedOnError(Exception e) {
        done = false;
        if (e instanceof XMPPException) {
            XMPPException xmppEx = (XMPPException) e;
            StreamError error = xmppEx.getStreamError();

            // Make sure the error is not null
            if (error != null) {
                String reason = error.getCode();

                if ("conflict".equals(reason)) {
                    return;
                }
            }
        }

        if (this.isReconnectionAllowed()) {
            this.reconnect();
        }
    }

    public void reconnectingIn(int seconds) {
        // ignore
    }

    public void reconnectionFailed(Exception e) {
        // ignore
    }

    /**
     * The connection has successfull gotten connected.
     */
    public void reconnectionSuccessful() {
        // ignore
    }


	@Override
	public void onNetworkAvailable() {
		networkAvailable=true;
		this.reconnectNow();
	}
	
	


	@Override
	public void onNetworkUnavailable() {
		networkAvailable=false;
		
	}

}