package com.ruyicai.activity.buy.guess;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import com.palmdream.RuyicaiAndroid.R;
import com.palmdream.RuyicaiAndroid.R.layout;
import com.palmdream.RuyicaiAndroid.R.menu;
import com.ruyicai.activity.buy.guess.view.CustomExpandableListView;
import com.ruyicai.activity.buy.guess.view.PullRefreshLoadListView;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.adapter.ChattingListViewAdapter;
import com.ruyicai.adapter.GatherSubjectListAdapter;
import com.ruyicai.adapter.RuyiGuessGroupListAdapter;
import com.ruyicai.adapter.RuyiGuessListAdapter;
import com.ruyicai.adapter.ScoreUsageListAdapter;
import com.ruyicai.component.SlidingView;
import com.ruyicai.component.SlidingView.SlidingViewPageChangeListener;
import com.ruyicai.component.SlidingView.SlidingViewSetCurrentItemListener;
import com.ruyicai.component.view.ChatListView;
import com.ruyicai.component.view.TitleBar;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
	
	@InjectView(R.id.gather_gather_name)
	private TextView mGatherName;
	
	@InjectView(R.id.gather_sponsor_name)
	private TextView mSponsorName;
	
	@InjectView(R.id.gather_score)
	private TextView mScore;
	
	@InjectView(R.id.gather_people_num)
	private TextView mPeople;
	
	@InjectView(R.id.gather_head_img)
	private ImageView mHeadImg;
	
	@InjectView(R.id.gather_main_layout)
	private LinearLayout mMainLayout;
	
	@InjectView(R.id.gather_add_subject_layout)
	private View mAddBtnLayout;
	
	@InjectView(R.id.gather_add_subject_btn)
	private Button mAddSubjectBtn;
	
	private LayoutInflater mInflater;
	
	private String[] mTitleGroups = {"大厅", "说两句", "我的积分"};

	private List<View> mSlidingListViews;
	
	private SlidingView mSlidingView;
	
	//我的积分
	private LinearLayout mMyScoreLayout;
	private ListView mScoreUsageList;
	private ScoreUsageListAdapter mScoreUsageListAdapter;
	//说两句
	private LinearLayout mTalkLayout;
	//竞猜大厅
	private View mRuyiGuessListLayout;
	private ListView mGuessSubjectList;
	private GatherSubjectListAdapter mSubjectListAdapter;

	private PullRefreshLoadListView mPullListView;

	
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
		mGatherName.setText("扎堆的名字");
		mPeople.setText("100人");
		mScore.setText("20000");
		mSponsorName.setText("某某");
		mIntroduce.setText("介绍 ：");
	}
	
	/**
	 * 初始化大厅的listview
	 */
	private void initGuessListView() {
		mRuyiGuessListLayout = mInflater.inflate(R.layout.buy_ruyigather_subject_list, null);
		mGuessSubjectList = (ListView) mRuyiGuessListLayout.findViewById(R.id.gather_subject_listview);
		//设置一个空页脚防止最后一条数据被覆盖
		TextView footer = new TextView(this);
		footer.setHeight(mAddBtnLayout.getLayoutParams().height);
		mGuessSubjectList.addFooterView(footer);
		
		mSubjectListAdapter = new GatherSubjectListAdapter(getLayoutInflater());
		mGuessSubjectList.setAdapter(mSubjectListAdapter);
	}
	
	/**
	 * 初始化我的积分页面
	 */
	private void initMyScoreLayout() {
		mMyScoreLayout = (LinearLayout) mInflater.inflate(R.layout.buy_ruyiguess_myscore_layout, null);
		mScoreUsageList = (ListView) mMyScoreLayout.findViewById(R.id.myscore_usage_list);
		mScoreUsageListAdapter = new ScoreUsageListAdapter(getLayoutInflater());
		mScoreUsageList.setAdapter(mScoreUsageListAdapter);
	}
	
	/**
	 * 初始化聊天室页面
	 */
	private void initTalkLayout(){
		mTalkLayout = (LinearLayout) mInflater.inflate(R.layout.buy_ruyiguess_mysubject_layout, null);
//		mTalkLayout = (LinearLayout) mInflater.inflate(R.layout.buy_ruyiguess_myscore_layout, null);
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
		titleBar.setTitleText("如意竞猜");
	}
	
	/**
	 * 添加SlindingView事件回调
	 */
	private void setSlidingViewListener(){
		mSlidingView.addSlidingViewPageChangeListener(new SlidingViewPageChangeListener() {

			@Override
			public void SlidingViewPageChange(int index) {
				if (index == 1) {
					addTalkLayout();
				}
			}
		});
		
		mSlidingView.addSlidingViewSetCurrentItemListener(new SlidingViewSetCurrentItemListener() {

			@Override
			public void SlidingViewSetCurrentItem(int index) {
				if(index != 0){
					mAddBtnLayout.setVisibility(View.GONE);
				}else{
					mAddBtnLayout.setVisibility(View.VISIBLE);
				}
				if (index == 1) {
					addTalkLayout();
				}
			}
		});
	}
	
	private void addTalkLayout() {
		if (mTalkLayout != null && mTalkLayout.getChildCount() == 0) {
			View view  = mInflater.inflate(R.layout.buy_ruyiguess_talk_layout, null);
//			ChatListView chatList = (ChatListView) view.findViewById(R.id.chatList);
//			ChattingListViewAdapter adapter = new ChattingListViewAdapter();
//			chatList.setAdapter(adapter);
			mTalkLayout.addView(view);
		}
	}
	
	
	

}
