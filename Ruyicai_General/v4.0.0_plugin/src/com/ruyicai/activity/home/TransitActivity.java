package com.ruyicai.activity.home;

import com.ruyicai.util.RWSharedPreferences;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class TransitActivity extends Activity {
	private RWSharedPreferences shellRW;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		turnActivity();
	}

	public void turnActivity() {
		// 读取本地渠道号
		shellRW = new RWSharedPreferences(this, "addInfo");
		boolean isFirst = shellRW.getBooleanValue("isFirst");
//		if (isFirst) {
			Intent in = new Intent(TransitActivity.this, MainGroup.class);
			startActivity(in);
			TransitActivity.this.finish();
//		} else {
//			shellRW.putBooleanValue("isFirst", true);
//			Intent in = new Intent(TransitActivity.this, PhotoActivity.class);
//			startActivity(in);
//			TransitActivity.this.finish();
//		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
