package com.ruyicai.activity.buy.guess;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.component.custom.view.TitleBar;
import com.ruyicai.adapter.RuyiGuessSubjectListAdapter;
import android.os.Bundle;
import android.view.Window;
import android.widget.ExpandableListView;

/**
 * 创建竞猜题目界面
 * @author win
 *
 */
public class RuyiGuessCreateSubjectActivity extends RoboActivity{

	@InjectView(R.id.buy_ruyi_guess_group_list)
	private ExpandableListView mExpandableListView;
	
	@InjectView(R.id.ruyicai_titlebar_layout)
	private TitleBar mTitleBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_ruyiguess_create_subject);
		initView();
	}
	
	private void initView() {
		mTitleBar.setTitleText(R.string.buy_ruyi_guess_create_subject);
		RuyiGuessSubjectListAdapter adapter = new RuyiGuessSubjectListAdapter(this);
	}

}
