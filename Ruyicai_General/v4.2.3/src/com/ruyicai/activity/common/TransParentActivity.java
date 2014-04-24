package com.ruyicai.activity.common;

import roboguice.activity.RoboActivity;

import com.google.inject.Inject;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.controller.service.AnimationService;
import com.ruyicai.util.PublicMethod;

import android.os.Bundle;
import android.view.Window;

public class TransParentActivity extends RoboActivity {
	
	@Inject private AnimationService  animationService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.transparent_activity);
	}
	@Override
	protected void onResume() {
		super.onResume();
		PublicMethod.outLog(this.getClass().getSimpleName(), "onResume()");
	}
	@Override
	protected void onPause() {
		super.onPause();
		PublicMethod.outLog(this.getClass().getSimpleName(), "onPause()");
		animationService.stopAnimation();

	}
}
