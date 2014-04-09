package com.ruyicai.net;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.inject.Singleton;
import com.ruyicai.net.ConnectivityReceiver.OnNetworkAvailableListener;

@Singleton
public class ConnectivityBroadcaster {
	
	private List<OnNetworkAvailableListener> listeners=new CopyOnWriteArrayList<OnNetworkAvailableListener>();

	public List<OnNetworkAvailableListener> getListeners() {
		return listeners;
	}
	
	public void addListener(OnNetworkAvailableListener listener){
		if(!this.listeners.contains(listener)){
			this.listeners.add(listener);
		}
	}

	public void removeListener(OnNetworkAvailableListener listener){
		if(this.listeners.contains(listener)){
			this.listeners.remove(listener);
		}
	}
	
	public void onNetworkAvailable(){
		for(OnNetworkAvailableListener listener:listeners){
			if(listener!=null){
				listener.onNetworkAvailable();
			}
		}
	}
	
	public void onNetworkUnavailable(){
		for(OnNetworkAvailableListener listener:listeners){
			if(listener!=null){
				listener.onNetworkUnavailable();
			}
		}
	}
	
}
