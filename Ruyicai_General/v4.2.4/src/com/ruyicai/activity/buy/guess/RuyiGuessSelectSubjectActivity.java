package com.ruyicai.activity.buy.guess;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.custom.view.TitleBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * 选择创建竞猜题目界面
 * @author win
 *
 */
public class RuyiGuessSelectSubjectActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_ruyiguess_select_subject);
		initView();
	}
	
	private void initView() {
		TitleBar titleBar = (TitleBar)findViewById(R.id.ruyicai_titlebar_layout);
		titleBar.setTitleText(R.string.buy_ruyi_guess_create_subject);
	}

}
