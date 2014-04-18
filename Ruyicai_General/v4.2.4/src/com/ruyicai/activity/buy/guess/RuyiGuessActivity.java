package com.ruyicai.activity.buy.guess;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.guess.bean.ItemInfoBean;
import com.ruyicai.activity.buy.guess.util.RuyiGuessConstant;
import com.ruyicai.activity.buy.guess.util.RuyiGuessUtil;
import com.ruyicai.activity.buy.guess.view.PullRefreshLoadListView;
import com.ruyicai.activity.buy.guess.view.PullRefreshLoadListView.IXListViewListener;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.adapter.RuyiGuessListAdapter;
import com.ruyicai.component.SlidingView;
import com.ruyicai.component.SlidingView.SlidingViewPageChangeListener;
import com.ruyicai.component.SlidingView.SlidingViewSetCurrentItemListener;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.controller.Controller;
import com.ruyicai.custom.view.TitleBar;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.ruyicai.util.json.JsonUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/***
 * @author yejc
 *
 */
public class RuyiGuessActivity extends Activity implements IXListViewListener/*, OnGestureListener*/{
	/**
	 * 用户编号
	 */
	private String mUserNo = "";
	
	/**
	 * 当前列表显示了多少页数据
	 */
	private int mPageIndex = 0;
	
	/**
	 * 服务器端总共有多少页数据
	 */
	private int mTotalPage = 0;
	
	/**
	 * 是否登陆
	 */
	private boolean mIsLogin = false;
	
	/**
	 * 是否从我的竞猜进入
	 */
	private boolean mIsMySelected = false;
	
	/**
	 * 自定义listview 用于下拉刷新
	 */
	private PullRefreshLoadListView mPullListView = null;
	
	/**
	 * 存放竞猜问题的list
	 */
	private List<ItemInfoBean> mQuestionsList = new ArrayList<ItemInfoBean>();
	
	/**
	 * 分页请求的题目数、默认是10条
	 */
	private String mItemCount = "10";
	
	/**
	 * 用于自动循环播放logo图片
	 */
	private ScheduledExecutorService mScheduledExecutorService = null;
	
	/**
	 * 用于自动循环播放logo图片的ViewFlipper
	 */
	private ViewFlipper mViewFlipper = null;
	
	/**
	 * 用于存放下载图片的路径
	 */
	private String LOCAL_DIR = "/ruyicai/";
	
	/**
	 * 当图片没有下载完成时显示的默认图片
	 */
	private ImageView mDefaultIcon = null;
	
	private ProgressDialog mProgressdialog = null;
	
	private Context mContext = null;
	
	private LayoutInflater mInflater = null;
	
	private RWSharedPreferences mSharedPreferences = null;
	
	private MessageHandler mHandler = new MessageHandler();
	
	private RuyiGuessListAdapter mAdapter = null;
	
	private SlidingView mSlidingView = null;
	
	private List<View> mSlidingListViews = null;
	
	private LinearLayout mMainLayout = null;
	
	private String[] mTitleGroups = {"竞猜大厅", "扎堆看球", "我的积分"};
	
	private LinearLayout mGroupWatchLayout = null;
	
	private LinearLayout mMyScoreLayout = null;
	
	private View mRuyiGuessListLayout = null;
	
	private Button mCreateGroupBtn = null;
	
	private LinearLayout mCreateGroupLayout = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_ruyiguess);
		LOCAL_DIR = LOCAL_DIR + getPackageName() + "/ruyijc/";
		String jumpFlag = getIntent().getStringExtra(RuyiGuessConstant.JUMP_FLAG);
		if (RuyiGuessConstant.JUMP_FLAG.equals(jumpFlag)) {
			mIsMySelected = true;
		}
		mContext = this;
		mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		readUserInfo();
		initView();
		networking();
	}
	
	private void readUserInfo() {
		mSharedPreferences = new RWSharedPreferences(RuyiGuessActivity.this,
				"addInfo");
		String sessionId = mSharedPreferences.getStringValue(ShellRWConstants.SESSIONID);
		if (!"".equals(sessionId)) {
			mIsLogin = true;
			mUserNo = mSharedPreferences.getStringValue(ShellRWConstants.USERNO);
		}
	}
	
	private void initView(){
		initTitleBar();
		mMainLayout = (LinearLayout)findViewById(R.id.ruyi_guess_main_layout);
		mViewFlipper = (ViewFlipper)findViewById(R.id.guess_viewflipper);
		mDefaultIcon = (ImageView)findViewById(R.id.ruyiguess_default_icon);
		mCreateGroupBtn = (Button)findViewById(R.id.buy_guess_create_group_btn);
		mCreateGroupLayout = (LinearLayout)findViewById(R.id.buy_guess_create_group_layout);
		if (mIsMySelected) {
			mViewFlipper.setVisibility(View.GONE);
			mDefaultIcon.setVisibility(View.GONE);
		}
		initPullListView();
		initSlidingView();
	}
	
	/**
	 * 初始化上边的标题栏
	 */
	private void initTitleBar() {
		TitleBar titleBar = (TitleBar)findViewById(R.id.ruyicai_titlebar_layout);
		titleBar.setTitleText(R.string.buy_ruyi_guess);
		if (mIsMySelected) {
			titleBar.setTitleText(R.string.buy_ruyi_myguess);
		} else {
			titleBar.setTitleText(R.string.buy_ruyi_guess);
		}
	}
	
	/**
	 * 初始化下拉刷新listview
	 */
	private void initPullListView() {
		mRuyiGuessListLayout = mInflater.inflate(R.layout.buy_ruyiguess_subject_list, null);
		mPullListView = (PullRefreshLoadListView)mRuyiGuessListLayout.findViewById(R.id.ruyi_guess_listview);
		mAdapter = new RuyiGuessListAdapter(mContext, mQuestionsList, mInflater, mIsMySelected, mIsLogin);
		mPullListView.setAdapter(mAdapter);
		mPullListView.setPullLoadEnable(true);
		mPullListView.setXListViewListener(this);
		mPullListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (!mIsLogin) {
					startActivityForResult(new Intent(RuyiGuessActivity.this,
							UserLogin.class), 1000);
				} else {
					int selectedId = arg2 - mPullListView.getHeaderViewsCount();
					turnToDetail(selectedId);
				}
				MobclickAgent.onEvent(RuyiGuessActivity.this, "ruyijingcai_listView_Item");
			}
		});
	}
	
	private void initSlidingView() {
		mGroupWatchLayout = (LinearLayout) mInflater.inflate(R.layout.buy_ruyiguess_layout, null);
		mMyScoreLayout = (LinearLayout) mInflater.inflate(R.layout.buy_ruyiguess_layout, null);
		mSlidingListViews = new ArrayList<View>();
		mSlidingListViews.add(mRuyiGuessListLayout);
		mSlidingListViews.add(mGroupWatchLayout);
		mSlidingListViews.add(mMyScoreLayout);
		mSlidingView = new SlidingView(mContext, mTitleGroups, mSlidingListViews, 
				mMainLayout, 17, getResources().getColor(R.color.red));
		setViewPagerListener();
		mSlidingView.setTabHeight(40);
		mSlidingView.resetCorsorViewValue(PublicMethod.getDisplayWidth(this)/3, 0, R.drawable.jc_gyj_tab_bg);
	}
	
	
	/**
	 * 联网获取数据
	 */
	private void networking() {
		mProgressdialog = PublicMethod.creageProgressDialog(this);
		if (mIsMySelected) {
			Controller.getInstance(this).getRuyiGuessList(mHandler, mUserNo, 1, "1", mPageIndex, mItemCount);
		} else {
			Controller.getInstance(this).getRuyiGuessImage(mHandler, 3);
			Controller.getInstance(this).getRuyiGuessList(mHandler, mUserNo, 1, "0", mPageIndex, mItemCount);
		}
		Controller.getInstance(this).addActivity(this); //添加到Activity list用于管理
	}
	
	/**
	 * 跳转到竞猜详情页面
	 * @param selectedId
	 */
	private void turnToDetail(int selectedId) {
		Intent intent = new Intent(RuyiGuessActivity.this,
				RuyiGuessDetailActivity.class);
		intent.putExtra(RuyiGuessConstant.ITEM_ID, mQuestionsList.get(selectedId).getId());
		intent.putExtra(RuyiGuessConstant.USER_NO, mUserNo);
		intent.putExtra(RuyiGuessConstant.TITLE, mQuestionsList.get(selectedId).getTitle());
		intent.putExtra(RuyiGuessConstant.MYSELECTED, mIsMySelected);
		intent.putExtra(RuyiGuessConstant.ISLOTTERY, mQuestionsList.get(selectedId).getState());
		if ("1".equals(mQuestionsList.get(selectedId).getIsEnd())) {
			intent.putExtra(RuyiGuessConstant.ISEND, true);
		} else {
			intent.putExtra(RuyiGuessConstant.ISEND, false);
		}
		startActivityForResult(intent, 1001);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			mPageIndex = 0;
			mIsLogin = true;
			if (mAdapter != null) {
				mAdapter.setLoginState(mIsLogin);
			}
			mUserNo = mSharedPreferences.getStringValue(ShellRWConstants.USERNO);
			mProgressdialog = PublicMethod.creageProgressDialog(this);
			String count = String.valueOf(mQuestionsList.size());
			Controller.getInstance(this).getRuyiGuessList(mHandler, mUserNo, 2, "0", 0, count);
		}
	}

	private class MessageHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String str = (String)msg.obj;
			switch (msg.what) {
			case 1:
			case 2:
				processResult(str, msg.what);
				break;
				
			case 3:
				initImageArray(str);
				break;
			}
		}
	}
	
	/**
	 * 处理返回结果
	 * @param str
	 * @param type
	 */
	private void processResult(String str, int type) {
		if (str == null || "null".equals(str)) {
			Toast.makeText(RuyiGuessActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
			onLoad();
			PublicMethod.closeProgressDialog(mProgressdialog);
		} else {
			try {
				JSONObject jsonObj = new JSONObject(str);
				String errorCode = jsonObj.getString("error_code");
				mTotalPage = Integer.valueOf(jsonObj.getString("totalPage").trim());
				if ("0000".equals(errorCode)) {
					if (type == 2) {
						mPageIndex = 0;
						mQuestionsList.clear();
						if (mAdapter != null) {
							mAdapter.clearData();
						}
					}
					List<ItemInfoBean> list = JsonUtils.getList(jsonObj.getString("result"), ItemInfoBean.class);
					if (list != null) {
						mQuestionsList.addAll(list);
					}
					mAdapter.notifyDataSetChanged();
				} else if ("0047".equals(errorCode)) {
					TextView tv = (TextView) mRuyiGuessListLayout.findViewById(R.id.ruyi_guest_no_record);
					tv.setVisibility(View.VISIBLE);
					mPullListView.setVisibility(View.GONE);
				} else {
					String message = jsonObj.getString("message");
					if (message == null || "null".equals(message) ||"".equals(message)) {
						message = "网络异常";
					}
					Toast.makeText(RuyiGuessActivity.this, message, Toast.LENGTH_SHORT).show();
				}
				onLoad();
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				if (type == 2) {
					onLoad();
				}
				PublicMethod.closeProgressDialog(mProgressdialog);
			}
		}
	}
	
	/**
	 * 初始化图片列表
	 * @param str
	 */
	private void initImageArray(String str) {
		try {
			JSONObject jsonObj = new JSONObject(str);
			String errorCode = jsonObj.getString("error_code");
			if ("0000".equals(errorCode)) {
				JSONArray jsonArray = jsonObj.getJSONArray("result");
				String path = RuyiGuessUtil.getSaveFilePath(LOCAL_DIR);
				File directory = new File(path);
				if (!directory.exists()) {
					directory.mkdirs();
				}
				loadImageForViewFlipper(jsonArray, path);
				startScrollTask();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 为logo添加资源图片
	 * @param jsonArray
	 * @param path
	 */
	private void loadImageForViewFlipper(JSONArray jsonArray, String path) {
		try {
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject itemObj = jsonArray.getJSONObject(i);
					String url = itemObj.getString("url");
					ImageView imageView = (ImageView)mInflater.inflate(
							R.layout.buy_ruyiguess_imageview, null);
					imageView.setImageResource(R.drawable.ruyiguess_default_bg);
					mViewFlipper.addView(imageView);
					int index = url.lastIndexOf("/");
					if (index >= 0) {
						String imageName = url.substring(index+1, url.length());
						File file = new File(path + imageName);
						if (file.exists()) {
							Bitmap bitmap = RuyiGuessUtil.decodeFile(file);
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
						} else {
							imageView.setImageResource(R.drawable.ruyiguess_default_bg);
							RuyiGuessUtil.downLoadImage(file, url, imageView);
						}
					}
				}
				mDefaultIcon.setVisibility(View.GONE);
				mViewFlipper.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 启动一个循环播放的异步任务
	 */
	private void startScrollTask() {
		if (mScheduledExecutorService == null) {
			mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5, TimeUnit.SECONDS);
		} else {
			if (mScheduledExecutorService.isShutdown()) {
				mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5, TimeUnit.SECONDS);
			}
		}
	}
	
	/**
	 * logo自动滚动线程
	 * @author win
	 *
	 */
	private class ScrollTask implements Runnable {
		public void run() {
			synchronized (mViewFlipper) {
				runOnUiThread(new Runnable() {
					public void run() {
						mViewFlipper.setInAnimation(mContext, R.anim.left_in);
						mViewFlipper.setOutAnimation(mContext, R.anim.left_out);
						mViewFlipper.showNext();
					}
				});
			}
		}
	}

	@Override
	public void onRefresh() {
		mProgressdialog = PublicMethod.creageProgressDialog(this);
		int maxResult = 0;
		if (mQuestionsList.size() > 10) {
			maxResult = mQuestionsList.size();
		} else {
			maxResult = 10;
		}
		mItemCount = String.valueOf(maxResult);
		if (mIsMySelected) {
			Controller.getInstance(this).getRuyiGuessList(mHandler, mUserNo, 2, "1", 0, mItemCount);
		} else {
			Controller.getInstance(this).getRuyiGuessList(mHandler, mUserNo, 2, "0", 0, mItemCount);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mScheduledExecutorService != null && !mScheduledExecutorService.isShutdown()) {
			mScheduledExecutorService.shutdown();
		}
	}

	
	@Override
	public void onLoadMore() {
		addmore();
	}
	
	/**
	 * 获取更多数据
	 */
	private void addmore() {
		mPageIndex++;
		if (mPageIndex > mTotalPage - 1) {
			mPageIndex = mTotalPage - 1;
			onLoad();
			Toast.makeText(this,
					R.string.usercenter_hasgonelast, Toast.LENGTH_SHORT).show();
		} else {
			mProgressdialog = PublicMethod.creageProgressDialog(this);
			if (mIsMySelected) {
				Controller.getInstance(this).getRuyiGuessList(mHandler, mUserNo, 1, "1", mPageIndex, mItemCount);
			} else {
				Controller.getInstance(this).getRuyiGuessList(mHandler, mUserNo, 1, "0", mPageIndex, mItemCount);
			}
		}
	}
	
	private void onLoad() {
		mPullListView.stopRefresh();
		mPullListView.stopLoadMore();
		mPullListView.setRefreshTime(PublicMethod.dateToStrLong(
				new Date(System.currentTimeMillis())));
	}
	
	private void setViewPagerListener(){
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
