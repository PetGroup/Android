package com.ruyicai.activity.more;


import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.util.RWSharedPreferences;

public class UserSettingsActivity extends RoboActivity {
	
	String TAG = UserSettingsActivity.class.getName();
	private RWSharedPreferences mSharedPreferences = null;
	private String mUserNo;
	private ProgressDialog progressdialog;
	private Context context;
	// open
	@InjectView(R.id.chbChatRemind)
	private CheckBox chbChatRemind;
	
	@InjectView(R.id.userSettingsSave)
	private Button userSettingsSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// remove title
		
		context = UserSettingsActivity.this;
		mSharedPreferences = new RWSharedPreferences(context, "addInfo");
		mUserNo = mSharedPreferences.getStringValue(ShellRWConstants.USERNO);
		
		setContentView(R.layout.preview_user_setting);
	}
	
	//read
	
	//save
	private void Save() {
		userSettingsSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
