package com.ruyicai.activity.buy.guess;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.guess.view.GuessSubjectList;
import com.ruyicai.adapter.CreateGatherSubjectListAdapter;
import com.ruyicai.component.view.TitleBar;

/**
 * 创建扎堆界面
 * @author wangw
 *
 */
public class RuyiGuessCreateGather extends RoboActivity {

	
	@InjectView(R.id.create_gather_btn)
	private Button mCreateBtn;
	
	@InjectView(R.id.create_gather_layout)
	private View mCreateBtnLayout;
	
	@InjectView(R.id.guesssubjectlist)
	private GuessSubjectList mSubjectList;
	
	private CreateGatherSubjectListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_guess_create_gather);
		initViews();
	}
	
	
	private void initViews() {
		initTitleBar();
		initSubjectList();
	}


	/**
	 * 初始化竞猜题目列表
	 */
	private void initSubjectList() {
		mAdapter = new CreateGatherSubjectListAdapter(getLayoutInflater());
		mSubjectList.setOnItemClickListener(listListener);
		
		//添加一个空页脚，防止创建按钮遮挡数据
		TextView footer = new TextView(this);
		footer.setHeight(mCreateBtnLayout.getLayoutParams().height);
		footer.setVisibility(View.INVISIBLE);
		mSubjectList.addFooterView(footer);
		
		mSubjectList.setAdapter(mAdapter);
		mSubjectList.setFooterDividersEnabled(false);
		
	}

	/**
	 * 监听点击Item事件，改变背景颜色
	 */
	OnItemClickListener listListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			mAdapter.setSelectItem(arg2);
		}
	};
	

	/**
	 * 初始化上边的标题栏
	 */
	private void initTitleBar() {
		TitleBar titleBar = (TitleBar)findViewById(R.id.ruyicai_titlebar_layout);
		titleBar.setTitleText("创建扎堆");
	}
	
}
