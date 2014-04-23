package com.ruyicai.activity.buy.guess;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.controller.Controller;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
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
	 * 选择的竞猜Id
	 */
//	private int mSelectedId = 0;
	
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
	 * 把参与题目的状态保存到map中，不用再去请求后台来改变当前页面的显示状态状态
	 */
//	private Map<Integer, Boolean> mLocalDataMap = new HashMap<Integer, Boolean>();
	
	/**
	 * 分页请求的题目数、默认是10条
	 */
	private String mItemCount = "10";
	
	/**
	 * 用于存放图片的地址
	 */
	private List<String> mImageUrlList = new ArrayList<String>();
	
	/**
	 * 用于存放图片的名字
	 */
	private List<String> mImageNameList = new ArrayList<String>();
	
	/**
	 * 用于自动循环播放logo图片
	 */
	private ScheduledExecutorService mScheduledExecutorService = null;
	
	/**
	 * 用于自动循环播放logo图片的ViewFlipper
	 */
	private ViewFlipper mViewFlipper = null;
	
	/**
	 * 用于存放展示logo的view
	 */
	private List<View> mViewList = new ArrayList<View>();
	
	/**
	 * 用于存放下载图片的路径
	 */
	private String LOCAL_DIR = "/ruyicai/";
	
	private String mTitleId = "";
	
	/**
	 * 当图片没有下载完成时显示的默认图片
	 */
	private ImageView mDefaultIcon = null;
	
	private ProgressDialog mProgressdialog = null;
	
//	private GestureDetector mGestureDetector = null;
	
	private Context mContext = null;
	
	private LayoutInflater mInflater = null;
	
	private RWSharedPreferences mSharedPreferences = null;
	
	private MessageHandler mHandler = new MessageHandler();
	
	private ListViewAdapter mAdapter = new ListViewAdapter();
	
	private int[] mIconArray = { R.drawable.textview_red_style,
			R.drawable.textview_orange_style,
			R.drawable.textview_yellow_style };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_ruyiguess);
		LOCAL_DIR = LOCAL_DIR + getPackageName() + "/ruyijc/";
		readUserInfo();
		String jumpFlag = getIntent().getStringExtra(RuyiGuessConstant.JUMP_FLAG);
		mTitleId = getIntent().getStringExtra(Constants.PUSH_PAGE_GUESS_TOPIC_ID);
		if (mTitleId != null && !"".equals(mTitleId)) {
			turnToDetail(false, mTitleId, "0");
		}
		if (RuyiGuessConstant.JUMP_FLAG.equals(jumpFlag)) {
			mIsMySelected = true;
		}
		mContext = this;
		mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initView();
		mProgressdialog = PublicMethod.creageProgressDialog(this);
		if (mIsMySelected) {
			Controller.getInstance(this).getRuyiGuessList(mHandler, mUserNo, 1, "1", mPageIndex, mItemCount);
		} else {
//			mGestureDetector = new GestureDetector(this);
			Controller.getInstance(this).getRuyiGuessImage(mHandler, 3);
			Controller.getInstance(this).getRuyiGuessList(mHandler, mUserNo, 1, "0", mPageIndex, mItemCount);
		}
		Controller.getInstance(this).addActivity(this); //添加到Activity list用于管理
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
		TextView title = (TextView)findViewById(R.id.ruyi_guess_title);
		if (mIsMySelected) {
			title.setText(R.string.buy_ruyi_myguess);
		} else {
			title.setText(R.string.buy_ruyi_guess);
		}
		mViewFlipper = (ViewFlipper)findViewById(R.id.guess_viewflipper);
		mDefaultIcon = (ImageView)findViewById(R.id.ruyiguess_default_icon);
		if (mIsMySelected) {
			mViewFlipper.setVisibility(View.GONE);
			mDefaultIcon.setVisibility(View.GONE);
		}
		mPullListView = (PullRefreshLoadListView)findViewById(R.id.ruyi_guess_listview);
		mPullListView.setAdapter(mAdapter);
		mPullListView.setPullLoadEnable(true);
		mPullListView.setXListViewListener(this);
		mPullListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				int selectedId = arg2 - mPullListView.getHeaderViewsCount();
				boolean isEnd = false;
				if ("1".equals(mQuestionsList.get(selectedId).getEndState())) {
					isEnd = true;
				}
				turnToDetail(isEnd, mQuestionsList.get(selectedId).getId(), mQuestionsList.get(selectedId).getLotteryState());
				MobclickAgent.onEvent(RuyiGuessActivity.this, "ruyijingcai_listView_Item");
			}
		});
	}
	
	private void turnToDetail(boolean isEnd, String titleId, String lottery) {
		if (!mIsLogin) {
			startActivityForResult(new Intent(RuyiGuessActivity.this,
					UserLogin.class), 1000);
		} else {
			Intent intent = new Intent(RuyiGuessActivity.this,
					RuyiGuessDetailActivity.class);
			intent.putExtra(RuyiGuessConstant.ITEM_ID, titleId);
			intent.putExtra(RuyiGuessConstant.USER_NO, mUserNo);
			intent.putExtra(RuyiGuessConstant.MYSELECTED, mIsMySelected);
			intent.putExtra(RuyiGuessConstant.ISLOTTERY, lottery);
			intent.putExtra(RuyiGuessConstant.ISEND, isEnd);
			startActivityForResult(intent, 1001);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
//			if (requestCode == 1000) {//登陆成功后重新加载当前账户的参与状态
				mPageIndex = 0;
				mIsLogin = true;
				mUserNo = mSharedPreferences.getStringValue(ShellRWConstants.USERNO);
				mProgressdialog = PublicMethod.creageProgressDialog(this);
				String count = String.valueOf(mQuestionsList.size());
				Controller.getInstance(this).getRuyiGuessList(mHandler, mUserNo, 2, "0", 0, count);
//			} else if (requestCode == 1001){ //如果参与成功后把参与题目的状态保存到map对象中并重新加载
//				mLocalDataMap.put(mSelectedId, true);
//				mAdapter.notifyDataSetChanged();
//			}
		}
	}

	private class ListViewAdapter extends BaseAdapter {
		/**
		 * 存放剩余秒数
		 */
		Map<Integer, Long> remainTimeMap = Collections.synchronizedMap(new HashMap<Integer, Long>());
		
		/**
		 * 存放是否计时完成的状态
		 */
		Map<Integer, Boolean> flagMap = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
		
		/**
		 * 存放计时线程
		 */
		Map<Integer, Thread> threadMap = Collections.synchronizedMap(new HashMap<Integer, Thread>());
		
		/**
		 * 存放少于一分钟的秒数
		 */
		Map<Integer, Long> lessMinuteMap = Collections.synchronizedMap(new HashMap<Integer, Long>());
		
		/**
		 * 存放少于一分钟的秒数的状态
		 */
		Map<Integer, Boolean> lessMinuteFlagMap = Collections.synchronizedMap(new HashMap<Integer, Boolean>());

		@Override
		public int getCount() {
			if (mQuestionsList != null) {
				return mQuestionsList.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			return mQuestionsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemInfoBean info = mQuestionsList.get(position);
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.buy_ruyiguess_listview_item, null);
				holder = new ViewHolder();
				holder.integral = (TextView) convertView
						.findViewById(R.id.ruyi_guess_item_integral);
				holder.title = (TextView) convertView
						.findViewById(R.id.ruyi_guess_item_title);
				holder.detail = (TextView) convertView
						.findViewById(R.id.ruyi_guess_item_detail);
				holder.time = (TextView) convertView
						.findViewById(R.id.ruyi_guess_item_time);
				holder.participate = (TextView) convertView
						.findViewById(R.id.ruyi_guess_item_participate);
				holder.endState = (TextView) convertView
						.findViewById(R.id.ruyi_myguess_item_participate);
				holder.divider = (View) convertView
						.findViewById(R.id.ruyi_guess_divider);
				holder.itemLayout = (LinearLayout) convertView
						.findViewById(R.id.ruyi_guess_item_layout);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			FrameLayout.LayoutParams params = (LayoutParams) holder.itemLayout.getLayoutParams();
			int mUnitPadValue = PublicMethod.getPxInt(1, RuyiGuessActivity.this);
			if (position == mQuestionsList.size()-1) {
				holder.divider.setVisibility(View.VISIBLE);
				params.height = PublicMethod.getPxInt(93, RuyiGuessActivity.this);
				holder.itemLayout.setLayoutParams(params);
				holder.itemLayout.setPadding(10*mUnitPadValue, 8*mUnitPadValue, 8*mUnitPadValue, 7*mUnitPadValue);
			} else {
				holder.divider.setVisibility(View.GONE);
				params.height = PublicMethod.getPxInt(90, RuyiGuessActivity.this);
				holder.itemLayout.setLayoutParams(params);
				holder.itemLayout.setPadding(10*mUnitPadValue, 8*mUnitPadValue, 8*mUnitPadValue, 4*mUnitPadValue);
			}
			
			holder.title.setText(info.getTitle());
			holder.detail.setText(info.getDetail());
			boolean isLottery = false;
			if ("2".equals(info.getLotteryState())) {
				isLottery = true;
			} else {
				isLottery = false;
			}
			setTimeStyle(position, info.getTimeRemaining(), holder.time, isLottery, holder.endState, info);
			holder.integral.setBackgroundResource(mIconArray[position%3]);
			if (mIsMySelected) {
				holder.integral.setText(PublicMethod.formatString(mContext, 
						R.string.buy_ruyi_guess_mythrow_score, info.getPayScore()));
				
				if (info.getTimeRemaining() > 0) {
					holder.endState.setVisibility(View.GONE);
				} else {
					holder.endState.setVisibility(View.VISIBLE);
					if (isLottery) {
						if ("1".equals(info.getIsWin())) {
							holder.endState.setText(R.string.buy_ruyi_guess_iswin);
						} else {
							holder.endState.setText(R.string.buy_ruyi_guess_nowin);
						}
					} else {
						holder.endState.setText(R.string.buy_ruyi_guess_wait_open);
					}
				}
				
				holder.participate.setVisibility(View.GONE);
			} else {
				holder.endState.setVisibility(View.GONE);
				holder.participate.setVisibility(View.VISIBLE);
				holder.integral.setText(PublicMethod.formatString(mContext, 
						R.string.buy_ruyi_guess_item_integral_two, info.getPrizePoolScore()));
				if ("0".equals(info.getEndState())) { //竞猜是否结束 0:未结束;1:已结束
					if (mIsLogin) {
//						if (mLocalDataMap.containsKey(position) 
//								&& mLocalDataMap.get(position)) {
//							holder.participate.setText(R.string.buy_ruyi_guess_btn_participate);//根据状态判断显示
//							holder.participate.setBackgroundResource(R.drawable.buy_ruyiguess_item_state_gray);
//						} else {
							if ("0".equals(info.getParticipate())) { //是否参与竞猜 0:未参与;1:已参与
								holder.participate.setText(R.string.buy_ruyi_guess_btn_participate_jc);//根据状态判断显示
								holder.participate.setBackgroundResource(R.drawable.buy_ruyiguess_item_state_green);
							} else {
								holder.participate.setText(R.string.buy_ruyi_guess_btn_participate);//根据状态判断显示
								holder.participate.setBackgroundResource(R.drawable.buy_ruyiguess_item_state_gray);
							}
//						}
					} else {
						holder.participate.setText(R.string.buy_ruyi_guess_btn_participate_jc);//根据状态判断显示
						holder.participate.setBackgroundResource(R.drawable.buy_ruyiguess_item_state_green);
					}
				} else {
					holder.participate.setBackgroundResource(R.drawable.buy_ruyiguess_item_state_gray);
					if (mIsLogin) {
						if ("0".equals(info.getParticipate())) { //是否参与竞猜 0:未参与;1:已参与
							holder.participate.setText(R.string.buy_ruyi_guess_btn_participate_jc);//根据状态判断显示
						} else {
							holder.participate.setText(R.string.buy_ruyi_guess_btn_participate);//根据状态判断显示
						}
					} else {
						holder.participate.setText(R.string.buy_ruyi_guess_btn_participate_jc);//根据状态判断显示
					}
				}
			}
			
			return convertView;
		}
		
		class ViewHolder {
			TextView integral; //积分
			TextView title; //竞猜题目
			TextView detail; //竞猜题目详情
			TextView time; //竞猜剩余时间
			TextView participate; //参与状态
			TextView endState; //待公布、已公布状态
			View divider;  //分割线
			LinearLayout itemLayout;
		}
		
		public void clearData() {
			remainTimeMap.clear();
			flagMap.clear();
			lessMinuteFlagMap.clear();
			lessMinuteMap.clear();
			if (threadMap.size() > 0) {
				for (int key : threadMap.keySet()) {
					if (threadMap.get(key) != null){
						threadMap.get(key).interrupt();
					}
				}
				threadMap.clear();
			}
		}
		
		private void setTimeStyle(final int position, Long time, 
				final TextView timeTv, boolean isLottery, TextView state, ItemInfoBean info) {
			if (time > 0) {
				timeTv.setVisibility(View.VISIBLE);
				if (!remainTimeMap.containsKey(position)) {
					remainTimeMap.put(position, time);
					timeTv.setText(formatLongToString(position, time));
					flagMap.put(position, true);
					if (!threadMap.containsKey(position)) {
						threadMap.put(position, new TimeThread(timeTv, state, position, info));
						Thread thread = threadMap.get(position);
						if (!(thread.isAlive())) {
							thread.start();
						}
					}
				} else {
					if (remainTimeMap.containsKey(position)) {
						timeTv.setText(formatLongToString(position, remainTimeMap.get(position)));
					} else {
						timeTv.setText("");
					}
				}
				timeTv.setTextColor(getResources().getColor(R.color.ruyi_guess_progress_red_color));
			} else {
				if (mIsMySelected) {
					timeTv.setVisibility(View.GONE);
				} else {
					timeTv.setVisibility(View.VISIBLE);
					if (isLottery) {
						timeTv.setText(R.string.buy_ruyi_guess_open);
						timeTv.setTextColor(getResources().getColor(R.color.ruyi_guess_progress_red_color));
					} else {
						timeTv.setText(R.string.buy_ruyi_guess_wait_open);
						timeTv.setTextColor(getResources().getColor(R.color.ruyi_guess_progress_green_color));
					}
				}
			}
		}
		
		private class TimeThread extends Thread {
			
			TextView timeTv;
			TextView mState;
			int position;
			ItemInfoBean mInfo;
			public TimeThread(TextView timeTv, TextView state, int position, ItemInfoBean info) {
				this.timeTv = timeTv;
				this.position = position;
				this.mState = state;
				this.mInfo = info;
			}

			@Override
			public void run() {
				boolean flag = false;
				if (flagMap != null && flagMap.containsKey(position)) {
					flag = flagMap.get(position);
				}
				while (flag) {
					long currentTime = 0L;
					synchronized (remainTimeMap) {
						if (remainTimeMap == null || 
								(!(remainTimeMap.containsKey(position))
								&& !(flagMap.containsKey(position)))) {
							return;
						}
						currentTime = remainTimeMap.get(position);
					}
					if (currentTime > 0) {
						int sleep = 60 * 1000;
						if (lessMinuteFlagMap.containsKey(position) && lessMinuteFlagMap.get(position)) {
							lessMinuteFlagMap.put(position, false);
							long tempTime = lessMinuteMap.get(position);
							sleep = (int)tempTime* 1000;
						}
						if (isInterrupted()) {
							return;
						}
						try {
							Thread.sleep(sleep);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						long newTime = currentTime - sleep/1000;
						if (!remainTimeMap.containsKey(position)) {
							return;
						}
						remainTimeMap.put(position, newTime);
						runOnUiThread(new Runnable() {
							public void run() {
								if ((flagMap.containsKey(position))) {
									mInfo.setTimeRemaining(remainTimeMap.get(position));
									String timeStr = formatLongToString(position, remainTimeMap.get(position));
									if (mIsMySelected) {
										if ("".equals(timeStr)) {
											mState.setVisibility(View.VISIBLE);
											mState.setText(R.string.buy_ruyi_guess_wait_open);
											timeTv.setVisibility(View.GONE);
										} else {
											timeTv.setText(timeStr);
											timeTv.setVisibility(View.VISIBLE);
											mState.setVisibility(View.GONE);
										}
									} else {
										timeTv.setText(timeStr);
										timeTv.setVisibility(View.VISIBLE);
										mState.setVisibility(View.GONE);
									}
									
									mAdapter.notifyDataSetChanged();
								}
							}
						});
						if (!(newTime > 0)) {
							flagMap.put(position, false);
						}
						
						if ((flagMap.get(position) == null)
								|| !(flagMap.containsKey(position))) {
							flag = false;
						}
					}
				}
			}
		}
		
		public String formatLongToString(int position, long time) {
			StringBuffer buffer = new StringBuffer();
			int day = 0;
			int hour = 0;
			long minute = 0;
			if (time > 60) {
				minute = time / 60;
				time = time % 60;
				if (time > 0) {
					minute = minute + 1;
					lessMinuteMap.put(position, time);
					if (!lessMinuteFlagMap.containsKey(position)) {
						lessMinuteFlagMap.put(position, true);
					}
				}
			} else if (time > 0 && time <= 60){
				minute = 1;
			}
			
			if (minute > 0) {
				buffer.append("剩");
			} else {
				return "";
			}
			
			if (minute >= 60) {
				hour = (int)(minute / 60);
				minute = minute % 60;
			}
			
			if (hour >= 24) {
				day = hour / 24;
				hour = hour % 24;
			}
			buffer.append(day).append("天");
			buffer.append(hour).append("时");
			buffer.append(minute).append("分");
			return buffer.toString();
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
				parserJSON(str, msg.what);
				break;
				
			case 3:
				initImageArray(str);
				break;
			}
		}
	}
	
	/**
	 * 解析json串
	 * @param str
	 * @param type
	 */
	private void parserJSON(String str, int type) {
		if (str == null || "".equals(null)) {
			Toast.makeText(RuyiGuessActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
			onLoad();
			dismissDialog();
		} else {
			try {
				JSONObject jsonObj = new JSONObject(str);
				String errorCode = jsonObj.getString("error_code");
				if ("0000".equals(errorCode)) {
					if (type == 2) {
						mPageIndex = 0;
						mQuestionsList.clear();
//						mLocalDataMap.clear();
						if (mAdapter != null) {
							mAdapter.clearData();
						}
					}
					
					JSONArray jsonArray = jsonObj.getJSONArray("result");
					mTotalPage = Integer.valueOf(jsonObj.getString("totalPage").trim());
					for (int i = 0; i < jsonArray.length(); i++) {
						ItemInfoBean info = new ItemInfoBean();
						JSONObject itemObj = jsonArray.getJSONObject(i);
						info.setId(itemObj.getString("id"));  //竞猜Id
						info.setTitle(itemObj.getString("title")); //竞猜题目
						info.setDetail(itemObj.getString("detail"));//竞猜题目详情
						info.setParticipate(itemObj.getString("isParticipate"));//竞猜参与状态 0:未参与;1:已参与
						info.setPrizePoolScore(itemObj.getString("prizePoolScore"));//竞猜奖池积分
						info.setEndState(itemObj.getString("isEnd"));//竞猜是否截止 0:未结束;1:已结束
						info.setPayScore(itemObj.getString("payScore"));  //我的投入
						info.setIsWin(itemObj.getString("isWin"));  //是否中奖
						info.setLotteryState(itemObj.getString("state"));//竞猜开奖状态 0:未开奖;1:开奖中;2:已开奖
						String remainTime = itemObj.getString("time_remaining").trim();//竞猜剩余时间
						try {
							Long time = Long.parseLong(remainTime);
							info.setTimeRemaining(time);
						} catch(NumberFormatException e) {
							e.printStackTrace();
						}
						mQuestionsList.add(info);
					}
					mAdapter.notifyDataSetChanged();
				} else if ("0047".equals(errorCode)) {
					TextView tv = (TextView) findViewById(R.id.ruyi_guest_no_record);
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
				dismissDialog();
			}
		}
	}
	
	/**
	 * 关闭联网对话框
	 */
	private void dismissDialog() {
		if (mProgressdialog != null && mProgressdialog.isShowing()) {
			mProgressdialog.dismiss();
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
				int length = jsonArray.length();
				String path = RuyiGuessUtil.getSaveFilePath(LOCAL_DIR);
				File directory = new File(path);
				if (!directory.exists()) {
					directory.mkdirs();
				}
				for (int i = 0; i < length; i++) {
					JSONObject itemObj = jsonArray.getJSONObject(i);
					String url = itemObj.getString("url");
					mImageUrlList.add(url);
					ImageView imageView = (ImageView)mInflater.inflate(
							R.layout.buy_ruyiguess_imageview, null);
					imageView.setImageResource(R.drawable.ruyiguess_default_bg);
					mViewFlipper.addView(imageView);
					mViewList.add(imageView);
					imageView.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							PublicMethod.turnPageByPushPage(RuyiGuessActivity.this, "F47104");
						}
					});
					int index = url.lastIndexOf("/");
					if (index >= 0) {
						String imageName = url.substring(index+1, url.length());
						mImageNameList.add(imageName);
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
				if (mScheduledExecutorService == null) {
					mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
					mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5, TimeUnit.SECONDS);
				} else {
					if (mScheduledExecutorService.isShutdown()) {
						mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5, TimeUnit.SECONDS);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
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
//			mPullListView.getFooterView().setVisibility(View.GONE);
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
	
	/**
	 * 手滑上边的logo图片。 如果需要开启此功能去掉下面注释掉的代码即可 
	 */
	/***************************start****************************/
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		if (mGestureDetector != null) {
//			return mGestureDetector.onTouchEvent(event);
//		}
//		return false;
//	}
//
//	@Override
//	public boolean onDown(MotionEvent e) {
//		return false;
//	}
//
//	@Override
//	public void onShowPress(MotionEvent e) {
//	}
//
//	@Override
//	public boolean onSingleTapUp(MotionEvent e) {
//		return false;
//	}
//
//	@Override
//	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//			float distanceY) {
//		return false;
//	}
//
//	@Override
//	public void onLongPress(MotionEvent e) {
//	}
//
//	@Override
//	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//			float velocityY) {
//		if(e1.getX()-e2.getX()>120){
//			mViewFlipper.setInAnimation(mContext, R.anim.left_in);
//			mViewFlipper.setOutAnimation(mContext, R.anim.left_out);
//			mViewFlipper.showNext();//向右滑动
//			return true;
//		}else if(e1.getX()-e2.getX()<-120){
//			mViewFlipper.setInAnimation(mContext, R.anim.right_in);
//			mViewFlipper.setOutAnimation(mContext, R.anim.right_out);
//			mViewFlipper.showPrevious();//向左滑动
//			return true;
//		}
//		return false;
//	}
	/***************************end****************************/

}
