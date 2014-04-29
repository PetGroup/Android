package com.ruyicai.activity.buy.guess;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.component.view.TitleBar;
import android.os.Bundle;
import android.view.Window;
import roboguice.activity.RoboActivity;

public class RuyiGuessBuyScoreActivity extends RoboActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_ruyiguess_buy_score);
		initView();
	}
	
	private void initView() {
		TitleBar titleBar = (TitleBar)findViewById(R.id.ruyicai_titlebar_layout);
		titleBar.setTitleText(R.string.buy_ruyi_guess_buy_score);
	}

}
