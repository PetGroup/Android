package com.ruyicai.activity.buy.guess;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.component.view.TitleBar;

import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * 积分充值页面
 * @author win
 *
 */
public class RuyiGuessBuyScoreActivity extends RoboActivity {

	@InjectView(R.id.buy_ruyiguess_balance)
	private TextView mBalance;
	
	@InjectView(R.id.buy_ruyiguess_myscore)
	private TextView mScore;
	
	@InjectView(R.id.buy_ruyiguess_buy_score)
	private TextView mGetScore;
	
	@InjectView(R.id.buy_ruyiguess_money)
	private EditText mRechargeAmount;
	
	private String TITLE = "title";
	private String ISHANDINGFREE = "isHandingFree";
	private String PICTURE = "";
	
	
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
