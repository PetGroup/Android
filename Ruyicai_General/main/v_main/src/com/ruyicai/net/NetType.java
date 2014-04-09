package com.ruyicai.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetType {

	/**
	 * 判断 是否开启 wifi网络连接
	 * 
	 * @param context
	 * @return
	 */

	public static boolean CheckNetWIFI(Context context) {
		boolean flag = false;
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		flag = mWifi.isConnected();
		return flag;
	}

	/**
	 * 判断 是否开启非wifi网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean CheckNetwork(Context context) {
		boolean flag = false;
		boolean flag2 = false;
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo mWifi2 = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (mWifi != null) {
			flag = mWifi.isConnected();
		}
		if (mWifi2 != null) {
			flag2 = mWifi2.isConnected();
		}
		return (flag || flag2);

	}

}
