package com.ruyicai.activity.buy.guess;

import com.palmdream.RuyicaiAndroid.R;

import android.os.Bundle;
import android.view.Window;
import roboguice.activity.RoboActivity;

/**
 * 积分兑换列表Activity
 * @author wangw
 *
 */
public class RuyiGuessScorePrizeList extends RoboActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_ruyiguess_score_prizelist);
	}
	
}
