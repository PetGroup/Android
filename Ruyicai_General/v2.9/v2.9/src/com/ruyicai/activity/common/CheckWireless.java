/**
 * Copyright 2010 PalmDream
 * All right reserved.
 * Development History:
 * Date             Author          Version            Modify
 * 2010-5-18        fqc              1.5                none
 */

package com.ruyicai.activity.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class CheckWireless extends Activity {
	Context context;
	private boolean connectGPRS = false;
	private boolean connectWIFI = false;
	private boolean connectMobNetInfo = false;


	/* �ж���������״̬�Ľӿں��� */
	public boolean getConnectGPRS() {
		return connectGPRS;
	}

	public boolean getConnectWIFI() {
		return connectWIFI;
	}

	public boolean connectMobNetInfo() {
		return connectMobNetInfo;
	}

	/* ���õ����������б�Ľӿں��� */
	public void showNoConnectionDialog() {
		context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
	}

	/* ���캯�� */
	public CheckWireless(Context aContext) {
		context = aContext;
		checkWirelessStatus();
	}

	/* ��ȡ�������������״̬ */
	private void checkWirelessStatus() {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		NetworkInfo mobNetInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (activeNetInfo != null) {
			if (connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
				connectWIFI = true;
			}
			if (connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED){
				connectGPRS = true;
			}
		}
		if (mobNetInfo != null) {
			connectMobNetInfo = true;

		}

	}

}
