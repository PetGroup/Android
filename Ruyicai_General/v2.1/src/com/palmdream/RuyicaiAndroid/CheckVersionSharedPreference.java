package com.palmdream.RuyicaiAndroid;

import android.content.Context;

/**
 * @���ߣ�����
 * @���ã���sharedpreference�ж�ȡ�汾��Ϣ����
 * @���ڣ�2011-03-07
 */
public class CheckVersionSharedPreference extends RWSharedPreferences {
	
	public Context context;
	RWSharedPreferences rwsp;

	/**
	 * @���ߣ�����
	 * @����: c
	 * @����: name
	 */
	public CheckVersionSharedPreference(Context c, String name) {
		super(c, name);
		context = c;
		rwsp = new RWSharedPreferences(context, "Version");
	}
	
}
