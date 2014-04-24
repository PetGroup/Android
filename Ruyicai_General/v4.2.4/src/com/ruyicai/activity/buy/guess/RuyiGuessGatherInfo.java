package com.ruyicai.activity.buy.guess;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import com.palmdream.RuyicaiAndroid.R;
import com.palmdream.RuyicaiAndroid.R.layout;
import com.palmdream.RuyicaiAndroid.R.menu;
import com.ruyicai.activity.buy.guess.view.PullRefreshLoadListView;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.adapter.RuyiGuessListAdapter;
import com.ruyicai.component.SlidingView;
import com.ruyicai.component.SlidingView.SlidingViewPageChangeListener;
import com.ruyicai.component.SlidingView.SlidingViewSetCurrentItemListener;
import com.ruyicai.component.custom.view.TitleBar;
import com.ruyicai.util.PublicMethod;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 竞猜扎堆详情页面
 * @author Administrator
 *
 */
public class RuyiGuessGatherInfo extends RoboActivity {

	@InjectView(R.id.gather_introduce)
	private TextView mIntroduce;
	
	@InjectView(R.id.gather_guess_num)
	private TextView mGuessNum;
	
	@InjectView(R.id.gather_score)
	private TextView mScore;
	
	@InjectView(R.id.gather_people_num)
	private TextView mPeople;
	
	@InjectView(R.id.gather_head_img)
	private ImageView mHeadImg;
	
	@InjectView(R.id.gather_main_layout)
	private LinearLayout mMainLayout;
	
	private LayoutInflater mInflater;
	
	private String[] mTitleGroups = {"大厅", "说两句", "我的积分"};

	private List<View> mSlidingListViews = null;
	
	private SlidingView mSlidingView = null;
	
	private View mRuyiGuessListLayout = null;
	
	//我的积分
	private LinearLayout mMyScoreLayout = null;
	//说两句
	private LinearLayout mTalkLayout = null;
	//竞猜大厅
	private LinearLayout mGuessLayout = null;

	private PullRefreshLoadListView mPullListView;

	private RuyiGuessListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_guess_gatherinfo);
		initViews();
	}

	private void initViews() {
		initTitleBar();
		mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		initProjectInfo();
		initGuessListView();
		initMyScoreLayout();
		initTalkLayout();
		initSlidingView();
	}
	
	/**
	 * 初始化项目介绍信息
	 */
	private void initProjectInfo(){
		mHeadImg.setImageResource(R.drawable.guess_info_head);
		mPeople.setText("人数 : 100人");
		mScore.setText("积分 : 20000");
		mGuessNum.setText("竞猜 : 20");
		mIntroduce.setText("介绍 ：。。。。。");
	}
	
	/**
	 * 初始化大厅的listview
	 */
	private void initGuessListView() {
		mRuyiGuessListLayout = mInflater.inflate(R.layout.buy_ruyiguess_subject_list, null);
	}
	
	/**
	 * 初始化我的积分页面
	 */
	private void initMyScoreLayout() {
		mMyScoreLayout = (LinearLayout) mInflater.inflate(R.layout.buy_ruyiguess_layout, null);
	}
	
	/**
	 * 初始化聊天室页面
	 */
	private void initTalkLayout(){
		mTalkLayout = (LinearLayout) mInflater.inflate(R.layout.buy_ruyiguess_layout, null);
	}
	
	/**
	 * 初始化侧滑控件
	 */
	private void initSlidingView() {	
		mSlidingListViews = new ArrayList<View>();
		mSlidingListViews.add(mRuyiGuessListLayout);
		mSlidingListViews.add(mTalkLayout);
		mSlidingListViews.add(mMyScoreLayout);
		
		mSlidingView = new SlidingView(this, mTitleGroups, mSlidingListViews, 
				mMainLayout, 17, getResources().getColor(R.color.red));
		setSlidingViewListener();
		mSlidingView.setTabHeight(40);
		mSlidingView.resetCorsorViewValue(PublicMethod.getDisplayWidth(this)/3, 0, R.drawable.jc_gyj_tab_bg);
	}
	
	/**
	 * 初始化上边的标题栏
	 */
	private void initTitleBar() {
		TitleBar titleBar = (TitleBar)findViewById(R.id.ruyicai_titlebar_layout);
		titleBar.setTitleText("扎堆的名字");
	}
	
	/**
	 * 添加SlindingView事件回调
	 */
	private void setSlidingViewListener(){
		mSlidingView.addSlidingViewPageChangeListener(new SlidingViewPageChangeListener() {

			@Override
			public void SlidingViewPageChange(int index) {
			}
		});
		
		mSlidingView.addSlidingViewSetCurrentItemListener(new SlidingViewSetCurrentItemListener() {

			@Override
			public void SlidingViewSetCurrentItem(int index) {
			}
		});
	}
	
	
	

}
