package com.ruyicai.net;

import roboguice.receiver.RoboBroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

import com.google.inject.Inject;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.xmpp.ReconnectionManager;

public class ConnectivityReceiver extends RoboBroadcastReceiver {

	public static interface OnNetworkAvailableListener {
		public void onNetworkAvailable();

		public void onNetworkUnavailable();
	}
	
	@Inject	Context context;
	@Inject	ReconnectionManager reconnectionManager;
	@Inject	ConnectivityBroadcaster  connectivityBroadcaster;

	public void bind() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(this, filter);
		checkConnectionOnDemand();
	}

	public void unbind(Context context) {
		context.unregisterReceiver(this);
	}

	public boolean checkConnectionOnDemand() {
		final NetworkInfo info = ( (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		if (info == null || info.getState() != State.CONNECTED) {
				return false;
		} else {
				return true;
		}
	}

	@Override
	public void handleReceive(Context context, Intent intent) {
		if (intent.getBooleanExtra(
						ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
			connectivityBroadcaster.onNetworkUnavailable();
			reconnectionManager.onNetworkUnavailable();
			PublicMethod.showMessage(context, "网络异常,请检查网络");
		} else if (!intent.getBooleanExtra(
						ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
			connectivityBroadcaster.onNetworkAvailable();
			reconnectionManager.onNetworkAvailable();
		}
	}

}
