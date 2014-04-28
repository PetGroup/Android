package com.ruyicai.activity.buy.guess;

import com.palmdream.RuyicaiAndroid.R;

import android.os.Bundle;
import android.view.Window;
import roboguice.activity.RoboActivity;

/**
 * 短信分享-通讯录列表
 * @author wangw
 *
 */
public class RuyiGuessShareContacts extends RoboActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_guess_share_contacts);
	}
}
