package com.ruyicai.activity.buy.guess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.guess.bean.ItemDetailInfoBean;
import com.ruyicai.activity.buy.guess.bean.ItemOptionBean;
import com.ruyicai.activity.buy.guess.util.RuyiGuessConstant;
import com.ruyicai.activity.buy.guess.util.RuyiGuessUtil;
import com.ruyicai.activity.buy.guess.view.CustomThumbDrawable;
import com.ruyicai.activity.buy.guess.view.RectangularProgressBar;
import com.ruyicai.controller.Controller;
import com.ruyicai.net.newtransaction.RuyiGuessInterface;
import com.ruyicai.util.PublicMethod;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/***
 * @author yejc
 *
 */
public class RuyiGuessDetailActivity extends Activity{
	
	/**
	 * 竞猜标题
	 */
	private String mTitle = "";
	
	/** 
	 * 问题详情 
	 */
	private String mDetail = "";
	
	/** 
	 * 我的积分
	 */
	private String mScore = "";
	
	/** 
	 * 我投入的积分
	 */
	private int mThrowScore = 200;
	
	/** 
	 * 竞猜Id 
	 */
	private String mId = "";
	
	/** 
	 * 用户名 
	 */
	private String mUserNo = "";
	
	/**
	 * 竞猜选项ID
	 */
	private String mOptionId = "";
	
	/**
	 * 开奖状态
	 */
	private String mIsLottery = "";
	
	/**
	 * 赞或踩的状态
	 */
	private String mPraiseOrTreadState = "";
	
	/**
	 * 赞或踩的状态
	 */
	private String mServerPraiseOrTreadState = "";
	
	/**
	 * 分享图片的名字
	 */
	private String mSharePictureName = "";
	
	/**
	 * 用于存放分享图片的路径
	 */
	private String LOCAL_DIR = "/ruyicai/";
	
	/**
	 * 少于一分钟的秒数
	 */
//	private long mLessSecond = 0L;
	
	/**
	 * 竞彩截止的剩余秒数
	 */
	private long mRemainSecond = 0L;
	
	/**
	 * 竞彩的参与人数
	 */
	private long mParticiptePeopleCount = 0L;
	
	/**
	 * 赞的数量
	 */
	private long mPraiseCount = 0L;
	
	/**
	 * 踩的数量
	 */
	private long mTreadCount = 0L;
	
	/**
	 * 是否參加了竞彩
	 */
	private boolean mIsSelected = false;
	
	/** 
	 * 竞彩是否截止
	 */
	private boolean mIsEnd = false;
	
	/** 
	 * 是否从我的竞猜进入
	 */
//	private boolean mIsMySelected = false;
	
	/** 
	 * 参与成功标识 
	 */
	private boolean mIsSuccess = false;
	
	/**
	 * 倒计时的线程是否继续运行
	 */
	private boolean mIsRun = true;
	
	/** 
	 * 竞猜是否正确
	 */
	private boolean mIsGuessCorrect = false;
	
	/** 
	 * 是否点击了+、—图标来滑动seekbar 
	 */
	private boolean mIsThumbMove = false;
	
//	/** 
//	 * 是否点击了赞和踩 
//	 */
//	private boolean mIsPraiseOrTread = false;
	
	/** 
	 * 问题描述 
	 */
	private TextView mDescription = null;
	
	/**
	 * 动态添加选项布局
	 */
	private LinearLayout mDynamicLayout = null;
	
	/**
	 * 参与人次
	 */
	private TextView mParticipatePeopleTV = null;
	
	/**
	 * 奖池积分
	 */
	private TextView mPrizePoolScoreTV = null;
	
	/**
	 * 我的积分
	 */
//	private TextView mMyScoreTV = null;
	
	/**
	 * 投入的积分
	 */
	private TextView mThrowScoreTV = null;
	
	/**
	 * 竞猜剩余时间
	 */
	private TextView mRemainTimeTV = null;
	
	/**
	 * 参与状态
	 */
	private TextView mParticipateStateTV = null;
	
	/**
	 * 参与状态
	 */
	private ImageView mAwardIconIV = null;
	
	/**
	 * 答案
	 */
	private TextView mAnswerTV = null;
	
	/**
	 * 奖励积分
	 */
	private TextView mAwardScoreTV = null;
	
	/**
	 * 赞的图标
	 */
	private TextView mPraiseIconTV = null;
	
	/**
	 * 赞的数量
	 */
	private TextView mPraiseCountTV = null;
	
	/**
	 * 踩的图标
	 */
	private TextView mTreadIconTV = null;
	
	/**
	 * 踩的数量
	 */
	private TextView mTreadCountTV = null;
	
	/**
	 * 答案布局
	 */
	private RelativeLayout mAnswerLayout = null;
	
	/**
	 * 用于倒计时的线程池
	 */
	private ScheduledExecutorService mScheduledExecutorService = null;
	
	/**
	 * 投入积分SeekBar
	 */
	private SeekBar mScoreSeekBar = null;
	
	/**
	 * 减少投入积分按钮
	 */
	private ImageButton mSubtractScoreBtn = null;
	
	/**
	 * 增加投入积分按钮
	 */
	private ImageButton mAddScoreBtn = null;
	
	/**
	 * 最顶层布局用于截取整个屏幕
	 */
	private FrameLayout mParentFrameLayout = null;
	
	/** 
	 * 提交竞猜信息按钮 
	 */
	private Button mSubmitBtn = null;
	
	private CustomThumbDrawable mThumbDrawable = null;
	
	private LayoutInflater mInflater = null;

	private ProgressDialog mProgressdialog = null;
	
	private MessageHandler mHandler = new MessageHandler();
	
	private ItemDetailInfoBean mDetailInfoBean = new ItemDetailInfoBean();
	
	private int[] mProgressBarColor = {R.color.ruyi_guess_progress_color_first,
			R.color.ruyi_guess_progress_color_second,
			R.color.ruyi_guess_progress_color_third,
			R.color.ruyi_guess_progress_color_fourth,
			R.color.ruyi_guess_progress_color_fifth};
	
	private Context context = RuyiGuessDetailActivity.this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_ruyiguess_detail);
		LOCAL_DIR = LOCAL_DIR + getPackageName() + "/ruyijc/share/";
		mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		getIntentInfo();
		initView();
		mProgressdialog = PublicMethod.creageProgressDialog(this);
		Controller.getInstance(this).getRuyiGuessDetailList(mHandler, mUserNo, mId, "0", 0);
	}
	
	private void getIntentInfo() {
		Intent intent = getIntent();
		mUserNo = intent.getStringExtra(RuyiGuessConstant.USER_NO);
		mId = intent.getStringExtra(RuyiGuessConstant.ITEM_ID);
		mTitle = intent.getStringExtra(RuyiGuessConstant.TITLE);
		mIsEnd = intent.getBooleanExtra(RuyiGuessConstant.ISEND, false);
//		mIsMySelected = intent.getBooleanExtra(RuyiGuessConstant.MYSELECTED, false);
		mIsLottery = intent.getStringExtra(RuyiGuessConstant.ISLOTTERY);
	}
	
	private void initView(){
		mParentFrameLayout = (FrameLayout)findViewById(R.id.ruyi_guess_detail_parent_layout);
		TextView title = (TextView)findViewById(R.id.ruyi_guess_item_subtitle);
		title.setText(mTitle);
		mPrizePoolScoreTV = (TextView)findViewById(R.id.ruyi_guess_item_prizepool_score);
		mDescription = (TextView)findViewById(R.id.ruyi_guess_item_description);
		mDynamicLayout = (LinearLayout)findViewById(R.id.ruyi_guess_itme_layout);
		mParticipatePeopleTV = (TextView)findViewById(R.id.ruyi_guess_item_participate_people);
		mThrowScoreTV = (TextView)findViewById(R.id.ruyi_guess_item_throw_score);
//		mMyScoreTV = (TextView)findViewById(R.id.ruyi_guess_item_my_score);
		mRemainTimeTV = (TextView)findViewById(R.id.ruyi_guess_item_time);
		mParticipateStateTV = (TextView)findViewById(R.id.ruyi_guess_item_participate_stateing);
		mAwardIconIV = (ImageView)findViewById(R.id.ruyi_guess_detail_item_state);
		mAnswerTV = (TextView)findViewById(R.id.ruyi_guess_item_answer);
		mAwardScoreTV = (TextView)findViewById(R.id.ruyi_guess_award_score);
		mAnswerLayout = (RelativeLayout)findViewById(R.id.ruyi_guess_answer_layout);
		mScoreSeekBar = (SeekBar)findViewById(R.id.ruyi_guess_seekbar);
		mThumbDrawable = new CustomThumbDrawable(this);
		mScoreSeekBar.setThumb(mThumbDrawable);
		mScoreSeekBar.setOnSeekBarChangeListener(new MySeekBar());
		
		ViewClickListener clickListener = new ViewClickListener();
		mSubtractScoreBtn = (ImageButton)findViewById(R.id.ruyi_guess_seekbar_subtract);
		mSubtractScoreBtn.setOnClickListener(clickListener);
		
		mAddScoreBtn = (ImageButton)findViewById(R.id.ruyi_guess_seekbar_add);
		mAddScoreBtn.setOnClickListener(clickListener);
		
		Button shareBtn = (Button)findViewById(R.id.ruyi_guess_share_btn);
		shareBtn.setOnClickListener(clickListener);
		
		mPraiseCountTV = (TextView)findViewById(R.id.ruyi_guess_praise_count);
		mTreadCountTV = (TextView)findViewById(R.id.ruyi_guess_tread_count);
		mPraiseIconTV = (TextView)findViewById(R.id.ruyi_guess_praise);
//		mPraiseIconTV.setOnClickListener(clickListener);
		mTreadIconTV = (TextView)findViewById(R.id.ruyi_guess_tread);
//		mTreadIconTV.setOnClickListener(clickListener);

		mSubmitBtn = (Button)findViewById(R.id.ruyi_guess_submit);
//		if (mIsMySelected) {
//			setSubmitBtnState(R.drawable.loginselecter, R.string.buy_ruyi_guess_go_work, true);
//			mSubmitBtn.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					List<Activity> activityList = Controller.getInstance(RuyiGuessDetailActivity.this).getActivityList();
//					for (int i = 0; i < activityList.size(); i++) {
//						Activity activity = activityList.get(i);
//						activity.finish();
//					}
//					Intent intent = new Intent(RuyiGuessDetailActivity.this,
//							RuyiGuessActivity.class);
//					startActivity(intent);
//					finish();
//				}
//			});
//		}
	}
	
	private void setMyThrowScore() {
		String score = "";
		if (!mIsSelected && (mIsEnd || mRemainSecond <= 0)) {
			score = "0";
		} else {
			score = String.valueOf(mThrowScore);
		}
		String scoreString = PublicMethod.formatString(this, R.string.buy_ruyi_guess_throw_score, 
				score);
		SpannableString scoreSpan = getSpannableString(
				scoreString, 0, score.length());
		mThrowScoreTV.setText(scoreSpan);
	}
	
	private SpannableString getSpannableString(String text, int start, int end) {
		SpannableString span = new SpannableString(text);
		span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.ruyi_guess_progress_red_color)),
				start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return span;
	}
	
	
	class MessageHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String data = (String)msg.obj;
			int type = msg.what;
			if (data == null || "".equals(data)) {
				Toast.makeText(RuyiGuessDetailActivity.this, "网络异常！", Toast.LENGTH_SHORT).show();
				dismissDialog();
			} else {
				if (type == RuyiGuessConstant.RUYI_GUESS_DETAIL) { //请求后台数据完成
					parserDetailJSON(data);
				} else if (type == RuyiGuessConstant.RUYI_GUESS_SUBMIT_INFO){ //提交竞猜结果完成
					sendResultSuccess(data);
				} else if (type == RuyiGuessConstant.RUYI_GUESS_PRAISE) {
					setPraiseState(data);
				} else if (type == RuyiGuessConstant.RUYI_GUESS_TREAD) {
					setTreadState(data);
				}
			}
		}
	}
	
	private void setParticipateStateForView() {
		if (mIsSelected || mIsSuccess) {
			mParticipateStateTV.setVisibility(View.VISIBLE);
			mParticipateStateTV.setText(R.string.buy_ruyi_guess_btn_participate);
		} else if (!mIsEnd && mRemainSecond > 0){
			mParticipateStateTV.setVisibility(View.VISIBLE);
			mParticipateStateTV.setText(R.string.buy_ruyi_guess_btn_doing);
		}
		
		if (mIsSelected || mIsSuccess || mIsEnd || mRemainSecond <= 0) {
			mSubtractScoreBtn.setClickable(false);
			mAddScoreBtn.setClickable(false);
		}
	}
	
	/**
	 * 解析json串
	 * @param data json串
	 */
	private void parserDetailJSON(String data) {
		try {
			JSONObject jsonObj = new JSONObject(data);
			String errorCode = jsonObj.getString("error_code");
			if ("0000".equals(errorCode)) {
				JSONObject quizObject = jsonObj.getJSONObject("quiz");
				mDetail = quizObject.getString("detail");
				mScore = quizObject.getString("score");
				JSONArray jsonArray = jsonObj.getJSONArray("result");
				JSONObject itemObj = jsonArray.getJSONObject(0);
				mDetailInfoBean.setId(itemObj.getString("id"));
				mDetailInfoBean.setQuestion(itemObj.getString("question"));
				mDetailInfoBean.setAnswerId(itemObj.getString("answerId"));
				mDetailInfoBean.setRemainTime(itemObj.getString("time_remaining"));
				mDetailInfoBean.setPrizePoolScore(itemObj.getString("prizePoolScore"));
				mDetailInfoBean.setPrizeScore(itemObj.getString("prizeScore"));

				try {
					mPraiseCount = Long.parseLong(itemObj.getString("praise").trim());
				} catch(NumberFormatException e) {
					e.printStackTrace();
				}
				
				try {
					mTreadCount = Long.parseLong(itemObj.getString("tread").trim());
				} catch(NumberFormatException e) {
					e.printStackTrace();
				}
				
				mPraiseOrTreadState = itemObj.getString("praiseOrTread");
				mServerPraiseOrTreadState = mPraiseOrTreadState;
				if ("".equals(mPraiseOrTreadState)) {
					mPraiseIconTV.setOnClickListener(new ViewClickListener());
					mTreadIconTV.setOnClickListener(new ViewClickListener());
				}
				mDetailInfoBean.setPraiseOrTread(itemObj.getString("praiseOrTread"));
				JSONArray optionsArray = itemObj.getJSONArray("options");
				List<ItemOptionBean> optionList = new ArrayList<ItemOptionBean>();
				for (int j = 0; j < optionsArray.length(); j++) {
					ItemOptionBean optionsBean = new ItemOptionBean();
					JSONObject item = optionsArray.getJSONObject(j);
					String optionId = item.getString("id");
					optionsBean.setId(optionId);
					String selected = item.getString("isSelected");
					if ("1".equals(selected)) {
						mIsSelected = true;
						String remainTime = itemObj.getString("time_remaining");
						//当满足竞猜结束、 剩余时间为0、答案公布这些条件后才显示答案
						if (optionId.equals(itemObj.getString("answerId"))
								&& ("".equals(remainTime) || ("0".equals(remainTime)))
								&& "2".equals(mIsLottery)
								&& mIsEnd) {
							mIsGuessCorrect = true;
						}
					}
					if (optionId.equals(itemObj.getString("answerId"))) {
						mDetailInfoBean.setAnswer(item.getString("option"));
					}
					
					optionsBean.setIsSelected(selected);
					optionsBean.setOption(item.getString("option"));
					String participants = item.getString("participants");
					try {
						Long count = Long.parseLong(participants);
						mParticiptePeopleCount = mParticiptePeopleCount + count;
					} catch(NumberFormatException e) {
						e.printStackTrace();
					}
					optionsBean.setParticipants(participants);
					String payScore = item.getString("payScore");
					if (!"".equals(payScore) && !"0".equals(payScore)) {
						try {
							mThrowScore = Integer.parseInt(payScore);
						} catch(NumberFormatException e) {
							e.printStackTrace();
						}
					}
					optionsBean.setPayScore(item.getString("payScore"));
					optionList.add(optionsBean);
				}
				mDetailInfoBean.setOptionList(optionList);
				setInfoForView();
				createDynamicView();
				setSubmitState();
			} else {
				String message = jsonObj.getString("message");
				Toast.makeText(RuyiGuessDetailActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			dismissDialog();
		}
	}
	
	/**
	 * 提交竞猜结果成功
	 * @param data
	 */
	private void sendResultSuccess(String data) {
		dismissDialog();
		try {
			JSONObject jsonObj = new JSONObject(data);
			String errorCode = jsonObj.getString("error_code");
			if ("0000".equals(errorCode)) {
				mIsSuccess = true;
				mParticiptePeopleCount = 0L;
				mProgressdialog = PublicMethod.creageProgressDialog(RuyiGuessDetailActivity.this);
				Controller.getInstance(RuyiGuessDetailActivity.this).getRuyiGuessDetailList(mHandler, mUserNo, mId, "0", 0);
				createDialog();
			} else {
				mIsSuccess = false;
				String message = jsonObj.getString("message");
				Toast.makeText(RuyiGuessDetailActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void setPraiseState() {
		if ("0".equals(mPraiseOrTreadState)) {
			mPraiseOrTreadState = "";
			mPraiseIconTV.setBackgroundResource(R.drawable.ruyi_guess_praise_gray);
			mPraiseCount = mPraiseCount - 1;
			setPraiseCount();
		} else if("".equals(mPraiseOrTreadState)){
			mPraiseOrTreadState = "0";
			mPraiseIconTV.setBackgroundResource(R.drawable.ruyi_guess_praise);
			mPraiseCount = mPraiseCount + 1;
			setPraiseCount(R.string.buy_ruyi_guess_praise_cancel);
		}
	}
	
	private void setTreadState() {
		if ("1".equals(mPraiseOrTreadState)) {
			mPraiseOrTreadState = "";
			mTreadIconTV.setBackgroundResource(R.drawable.ruyi_guess_tread_gray);
			mTreadCount = mTreadCount - 1;
			setTreadCount();
		} else if("".equals(mPraiseOrTreadState)){
			mPraiseOrTreadState = "1";
			mTreadIconTV.setBackgroundResource(R.drawable.ruyi_guess_tread);
			mTreadCount = mTreadCount + 1;
			setTreadCount(R.string.buy_ruyi_guess_tread_cancel);
		}
	}
	
	private void setPraiseState(String data) {
		try {
			JSONObject jsonObj = new JSONObject(data);
			String errorCode = jsonObj.getString("error_code");
			if ("0000".equals(errorCode)) {
				if ("0".equals(mPraiseOrTreadState)) {
					mPraiseOrTreadState = "";
					mPraiseIconTV.setBackgroundResource(R.drawable.ruyi_guess_praise_gray);
					mPraiseCount = mPraiseCount - 1;
				} else if("".equals(mPraiseOrTreadState)){
					mPraiseOrTreadState = "0";
					mPraiseIconTV.setBackgroundResource(R.drawable.ruyi_guess_praise);
					mPraiseCount = mPraiseCount + 1;
				}
				setPraiseCount(R.string.buy_ruyi_guess_tread_already);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			dismissDialog();
		}
	}
	
	private void setTreadState(String data) {
		try {
			JSONObject jsonObj = new JSONObject(data);
			String errorCode = jsonObj.getString("error_code");
			if ("0000".equals(errorCode)) {
				if ("1".equals(mPraiseOrTreadState)) {
					mPraiseOrTreadState = "";
					mTreadIconTV.setBackgroundResource(R.drawable.ruyi_guess_tread_gray);
					mTreadCount = mTreadCount - 1;
				} else if("".equals(mPraiseOrTreadState)){
					mPraiseOrTreadState = "1";
					mTreadIconTV.setBackgroundResource(R.drawable.ruyi_guess_tread);
					mTreadCount = mTreadCount + 1;
				}
				setTreadCount(R.string.buy_ruyi_guess_tread_already);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			dismissDialog();
		}
	}
	
	private void setInfoForView() {
		mDescription.setText(mDetail);
		String prizePoolScore = mDetailInfoBean.getPrizePoolScore();
		String result = PublicMethod.formatString(this, R.string.buy_ruyi_guess_item_prizepool_score, 
				prizePoolScore);
		SpannableString span = getSpannableString(
				result, 0, prizePoolScore.length());
		mPrizePoolScoreTV.setText(span);
		
		if ("0".equals(mPraiseOrTreadState)) {
			mPraiseIconTV.setBackgroundResource(R.drawable.ruyi_guess_praise);
		} else if ("1".equals(mPraiseOrTreadState)) {
			mTreadIconTV.setBackgroundResource(R.drawable.ruyi_guess_tread);
		}
		
		mParticipatePeopleTV.setText(PublicMethod.formatString(this, R.string.buy_ruyi_guess_participate_people, 
				String.valueOf(mParticiptePeopleCount)));
		int progress = (mThrowScore - 200)/100;
		if (progress > 0) {
			mScoreSeekBar.setProgress(progress);
		}
		String remainTime = mDetailInfoBean.getRemainTime();
		if (!"".equals(remainTime) && !"0".equals(remainTime)) {
			Long time = 0L;
			try {
				time = Long.parseLong(remainTime);
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
			mRemainSecond = time;
			setRemainTime();
			if (mScheduledExecutorService == null) {
				mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
				mScheduledExecutorService.execute(new RemainTiemRunnable());
			} else {
				if (mScheduledExecutorService.isShutdown()) {
					mScheduledExecutorService.execute(new RemainTiemRunnable());
				}
			}
		} else {
			mRemainTimeTV.setText(PublicMethod.formatString(this, R.string.buy_ruyi_guess_remain_time, "已截止"));
		}
		
		if (mIsGuessCorrect) {
			mAwardIconIV.setVisibility(View.VISIBLE);
			mAwardIconIV.setImageResource(R.drawable.guess_jiang);
		} else if (mIsEnd && mRemainSecond == 0){
			mAwardIconIV.setVisibility(View.VISIBLE);
			mAwardIconIV.setImageResource(R.drawable.ruyiguess_stop);
		}
		
		if (mIsEnd && !"".equals(mDetailInfoBean.getAnswer())
				&& "2".equals(mIsLottery)
				&& ("".equals(mDetailInfoBean.getRemainTime())
						|| "0".equals(mDetailInfoBean.getRemainTime()))) {
			mAnswerLayout.setVisibility(View.VISIBLE);
			mAnswerTV.setText("答案: "+mDetailInfoBean.getAnswer());
			if ("".equals(mDetailInfoBean.getPrizeScore())) {
				mAwardScoreTV.setText("积分: +0");
			} else {
				mAwardScoreTV.setText("积分: +"+mDetailInfoBean.getPrizeScore());
			}
		}
		setMyThrowScore();
		setPraiseCount();
		setTreadCount();
	}
	
	/**
	 * 设置赞的显示数量
	 */
	private void setPraiseCount() {
		if (mPraiseCount == 0) {
			setPraiseCount(R.string.buy_ruyi_guess_praise);
		} else {
			setPraiseCount(R.string.buy_ruyi_guess_praise_already);
		}
	}
	
	/**
	 * 设置赞的显示数量
	 */
	private void setPraiseCount(int resId) {
		String praiseCount = PublicMethod.formatString(this, resId, 
				String.valueOf(mPraiseCount));
		mPraiseCountTV.setText(praiseCount);
	}
	
	/**
	 * 设置踩的显示数量
	 */
	private void setTreadCount(int resId) {
		String treadCount = PublicMethod.formatString(this, resId, 
				String.valueOf(mTreadCount));
		mTreadCountTV.setText(treadCount);
	}
	
	private void setTreadCount() {
		if (mTreadCount == 0) {
			setTreadCount(R.string.buy_ruyi_guess_tread);
		} else {
			setTreadCount(R.string.buy_ruyi_guess_tread_already);
		}
	}
	
	/**
	 * 动态创建选项视图
	 */
	private void createDynamicView() {
		mDynamicLayout.removeAllViews();
		List<ItemOptionBean> options = mDetailInfoBean.getOptionList();
		if (options != null && options.size() > 0) {
			View myScoreLayout = (View) mInflater.inflate(
					R.layout.buy_ruyiguess_textview, null);
			TextView myScore = (TextView)myScoreLayout.findViewById(R.id.ruyi_guess_my_score_text);
			myScore.setText(PublicMethod.formatString(this, R.string.buy_ruyi_guess_my_score, mScore));
			mDynamicLayout.addView(myScoreLayout);
			int length = options.size(); // 选项的个数
			final View[] mViews = new View[length];
			for (int i = 0; i < length; i++) {
				View itemLayout = (View) mInflater.inflate(
						R.layout.buy_ruyiguess_progressbar, null);
				mViews[i] = itemLayout;
				mDynamicLayout.addView(itemLayout);
				TextView number = (TextView) itemLayout
						.findViewById(R.id.ruyi_guess_dynamic_number);
				TextView icon = (TextView) itemLayout
						.findViewById(R.id.ruyi_guess_dynamic_icon);
				icon.setTag(options.get(i).getId());
				TextView text = (TextView) itemLayout
						.findViewById(R.id.ruyi_guess_dynamic_text);
				text.setText(options.get(i).getOption());
				RectangularProgressBar progress = (RectangularProgressBar) itemLayout
						.findViewById(R.id.ruyi_guess_progressbar);
				String participants = options.get(i).getParticipants();
				int progressColor = getResources().getColor(mProgressBarColor[i % 5]);
				if ("".equals(participants) || "0".equals(participants)) {
					progress.init(progressColor, 0f);
					number.setText("0%");
				} else {
					Long people = 0L;
					float percentage = 0f;
					try {
						people = Long.parseLong(participants);
					} catch(NumberFormatException e) {
						e.printStackTrace();
					}
					if (people > 0) {
						percentage = (float)people / (float)mParticiptePeopleCount;
						DecimalFormat df = new DecimalFormat("0.000");// 格式化小数，不足的补0
						String formatStr = df.format(percentage);
						percentage = Float.valueOf(formatStr);
						progress.init(progressColor, percentage);
					}
					String result = new DecimalFormat("000.0").format(percentage*100);
					percentage = Float.valueOf(result);
					number.setText(percentage + "%");
				}
				
				if (mIsEnd || mIsSelected || mIsSuccess || mRemainSecond == 0) {
					itemLayout.setClickable(false);
					if ("1".equals(options.get(i).getIsSelected())
							 || mOptionId.equals(options.get(i).getId())) {
						icon.setBackgroundResource(R.drawable.buy_ruyi_guess_radio_selected);
					} else {
						icon.setBackgroundResource(R.drawable.buy_ruyi_guess_radio_normal);
					}
				} else {
					itemLayout.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							TextView icon = (TextView) v
									.findViewById(R.id.ruyi_guess_dynamic_icon);
							if ("true".equals((String) v.getTag())) {
								icon.setBackgroundResource(R.drawable.buy_ruyi_guess_radio_normal);
								mOptionId = "";
								v.setTag("false");
							} else {
								for (int i = 0; i < mViews.length; i++) {
									TextView tv = (TextView) mViews[i]
											.findViewById(R.id.ruyi_guess_dynamic_icon);
									tv.setBackgroundResource(R.drawable.buy_ruyi_guess_radio_normal);
									mViews[i].setTag("false");
								}
								mOptionId = (String) icon.getTag();
								v.setTag("true");
								icon.setBackgroundResource(R.drawable.buy_ruyi_guess_radio_selected);
							}
							MobclickAgent.onEvent(context, "ruyijingcai_detail_timuxuanze");
						}
					});
				}
			}
		}
	}
	
	/**
	 * 设置提交按钮的状态
	 */
	private void setSubmitState() {
//		if (!mIsMySelected) {
			setParticipateStateForView();
			if (mIsSelected) {
				setSubmitBtnState(R.drawable.buy_ruyiguess_item_gray, R.string.buy_ruyi_guess_btn_participate, false);
			} else {
				if (mIsEnd) {
					setSubmitBtnState(R.drawable.buy_ruyiguess_item_gray, R.string.buy_ruyi_guess_btn_end, false);
				} else {
					setSubmitBtnState(R.drawable.loginselecter, R.string.buy_ruyi_guess_submit_result, true);
					mSubmitBtn.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							MobclickAgent.onEvent(context, "ruyijingcai_tijiao");
							int score = 0;
							try {
								score = Integer.parseInt(mScore);
							} catch(Exception e) {
								e.printStackTrace();
							}
							
							if ("".equals(mOptionId)) {
								Toast.makeText(RuyiGuessDetailActivity.this, 
										R.string.buy_ruyi_guess_please_select, 
										Toast.LENGTH_SHORT).show();
							} else if (mThrowScore > score) {
								Toast.makeText(RuyiGuessDetailActivity.this, 
										R.string.buy_ruyi_guess_no_participate, 
										Toast.LENGTH_SHORT).show();
							} else {
								mProgressdialog = PublicMethod
										.creageProgressDialog(RuyiGuessDetailActivity.this);
								Controller.getInstance(RuyiGuessDetailActivity.this)
										.sendDateToService(mHandler, mUserNo, mId, getSubmitInfo());
							}
							
						}
					});
				}
			}
//		}
	}
	
	private void setSubmitBtnState(int picResId, int textResId, boolean isClickable) {
		mSubmitBtn.setClickable(isClickable);
		mSubmitBtn.setVisibility(View.VISIBLE);
		mSubmitBtn.setBackgroundResource(picResId);
		mSubmitBtn.setText(textResId);
	}
	
	/**
	 * 参与竞猜信息
	 * @return
	 */
	private String getSubmitInfo() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(mDetailInfoBean.getId());
		buffer.append("_");
		buffer.append(mOptionId);
		buffer.append("_");
		buffer.append(String.valueOf(mThrowScore));
		return buffer.toString();
	}
	
	/**
	 * 创建参与成功对话框
	 */
	private void createDialog() {
		final Dialog mDialog = new AlertDialog.Builder(this).create();
		View view = LayoutInflater.from(this)
				.inflate(R.layout.buy_ruyiguess_success_dialog, null);
		Button okBtn = (Button)view.findViewById(R.id.ruyi_guess_ok);
		okBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mDialog != null && mDialog.isShowing()) {
					mDialog.dismiss();
				}
				MobclickAgent.onEvent(context, "ruyijingcai_canyuchenggong_ok");
			}
		});
		Button backBtn = (Button)view.findViewById(R.id.ruyi_guess_back);
		backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mIsSuccess) {
					setResult(RESULT_OK);
				}
				finish();
				MobclickAgent.onEvent(context, "ruyijingcai_back");
			}
		});
		mDialog.show();
		mDialog.getWindow().setContentView(view);
	}
	
	/**
	 * 关闭联网对话框
	 */
	private void dismissDialog() {
		if (mProgressdialog != null && mProgressdialog.isShowing()) {
			mProgressdialog.dismiss();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mIsSuccess /*&& !mIsMySelected*/ 
				&& keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_OK);
		}
		finish();
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mScheduledExecutorService != null
				&& !mScheduledExecutorService.isShutdown()) {
			mScheduledExecutorService.shutdown();
		}
		mParentFrameLayout.destroyDrawingCache(); //释放资源
		deleteSharePicture(); //删除分享图片
		if (mServerPraiseOrTreadState != mPraiseOrTreadState) {
			sendPraiseOrTreadState();
		}
	}
	
	/**
	 * 倒计时线程 
	 */
	private class RemainTiemRunnable implements Runnable {

		@Override
		public void run() {
			while (mIsRun) {
//				int sleep = 60 * 1000;
//				if (mLessSecond > 0 && mLessSecond < 60) {
//					sleep = (int)mLessSecond * 1000;
//					mLessSecond = 0;
//				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				try {
//					Long time = Long.parseLong(mDetailInfoBean.getRemainTime());
//					mRemainSecond = time - 1;
//				} catch(NumberFormatException e) {
//					e.printStackTrace();
//				}
				
				mRemainSecond = mRemainSecond - 1;
				runOnUiThread(new Runnable() {
					public void run() {
						mDetailInfoBean.setRemainTime(String.valueOf(mRemainSecond));
						setRemainTime();
						setEndState();
					}
				});
				if (!(mRemainSecond > 0)) {
					mIsRun = false;
				}
			}
		}
	}
	
	private void setRemainTime() {
		String timeString = PublicMethod.formatString(this, 
				R.string.buy_ruyi_guess_remain_time, formatLongToString(mRemainSecond));
		mRemainTimeTV.setText(timeString);
	}
	
	private void setEndState() {
		if (!(mRemainSecond > 0)) {
			mRemainTimeTV.setText(PublicMethod.formatString(this, R.string.buy_ruyi_guess_remain_time, "已截止"));
			setSubmitBtnState(R.drawable.buy_ruyiguess_item_gray, R.string.buy_ruyi_guess_btn_end, false);
			setParticipateStateForView();
			createDynamicView();
			setMyThrowScore();
		}
	}
	
	/**
	 * 格式化剩余时间 
	 */
	public String formatLongToString(long time) {
		if (!(time > 0)) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		int day = 0;
		int hour = 0;
		long minute = 0;
		buffer.append("剩");
		if (time > 60) {
			minute = time / 60;
			time = time % 60;
//			if (time > 0) {
//				minute = minute + 1;
//				mLessSecond = time;
//			}
		} /*else if (time > 0 && time <= 60) {
			minute = 1;
		}*/

//		if (minute > 0) {
//			buffer.append("剩");
//		} else {
//			return "";
//		}

		if (minute >= 60) {
			hour = (int) (minute / 60);
			minute = minute % 60;
		}

		if (hour >= 24) {
			day = hour / 24;
			hour = hour % 24;
		}

//		if (day > 0) {
//			buffer.append(day).append("天");
//		}
//
//		if (hour > 0) {
//			buffer.append(hour).append("时");
//		} else {
//			if (day > 0) {
//				buffer.append("0").append("时");
//			}
//		}
//
//		if (minute > 0) {
//			buffer.append(minute).append("分");
//		} else {
//			buffer.append("0").append("分");
//		}
		buffer.append(day).append("天");
		buffer.append(hour).append("时");
		buffer.append(minute).append("分");
		buffer.append(time).append("秒");
		return buffer.toString();
	}
	
	private class MySeekBar implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (mIsEnd || mIsSelected || mIsSuccess || mRemainSecond == 0) {
				int sbProgress = (mThrowScore - 200)/100;
				seekBar.setProgress(sbProgress);
			} else {
				mThrowScore = progress*100 + 200;
				setMyThrowScore();
				if (mIsThumbMove) {
					mIsThumbMove = false;
					setThumState(false);
				} else {
					setThumState(true);
				}
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			if (!(mIsEnd || mIsSelected || mIsSuccess || mRemainSecond == 0)) {
				setThumState(true);
			}
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if (!(mIsEnd || mIsSelected || mIsSuccess || mRemainSecond == 0)) {
				setThumState(false);
			}
		}
	}
	
	/**
	 * 在滑动 seekbar时 thumb放大显示 
	 */
	private void setThumState(boolean flag) {
		LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) mScoreSeekBar.getLayoutParams();
		mScoreSeekBar.setLayoutParams(layoutParams);
		if (flag) {
			mThumbDrawable.setScore(String.valueOf(mThrowScore));
		}
		mThumbDrawable.setIsShowText(flag);
		mScoreSeekBar.setThumb(mThumbDrawable);
	}
	
	class ViewClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ruyi_guess_seekbar_subtract:
				subtractSeekBar();
				MobclickAgent.onEvent(context, "ruyijingcai_jifen_subtract");
				break;

			case R.id.ruyi_guess_seekbar_add:
				addSeekBar();
				MobclickAgent.onEvent(context, "ruyijingcai_jifen_add");
				break;

			case R.id.ruyi_guess_share_btn:
				createSharePopWindow();
				MobclickAgent.onEvent(context, "ruyijingcai_fenxiang");
				break;

			case R.id.ruyi_guess_praise:
				if (!"1".equals(mPraiseOrTreadState)) {
					setPraiseState();
					/**如果需要每次点击联网打开下列代码*/
//					sendPraiseOrTreadState(RuyiGuessConstant.PRAISE_STATE, 
//							RuyiGuessConstant.RUYI_GUESS_PRAISE);
				}
				MobclickAgent.onEvent(context, "ruyijingcai_ding");
				break;

			case R.id.ruyi_guess_tread:
				if (!"0".equals(mPraiseOrTreadState)) {
					setTreadState();
					/**如果需要每次点击联网打开下列代码*/
//					sendPraiseOrTreadState(RuyiGuessConstant.TREAD_STATE, 
//							RuyiGuessConstant.RUYI_GUESS_TREAD);
				}
				MobclickAgent.onEvent(context, "ruyijingcai_cai");
				break;
			}
		}
	}
	
	private void subtractSeekBar () {
		if (mScoreSeekBar != null) {
			int progress = mScoreSeekBar.getProgress();
			if (progress > 0) {
				mThrowScore = progress * 100 + 200 - 100;
				mIsThumbMove = true;
				mScoreSeekBar.setProgress(progress - 1);
			}
			setMyThrowScore();
		}
	}
	
	private void addSeekBar() {
		if (mScoreSeekBar != null) {
			int progress = mScoreSeekBar.getProgress();
			mThrowScore = progress * 100 + 200;
			if (progress == mScoreSeekBar.getMax()) {
				mThrowScore = 2000;
			} else {
				mThrowScore = mThrowScore + 100;
				mIsThumbMove = true;
				mScoreSeekBar.setProgress(progress + 1);
			}
			setMyThrowScore();
		}
	}
	
	/**
	 * 创建分享窗口
	 */
	private void createSharePopWindow() {
		mParentFrameLayout.buildDrawingCache();
		Bitmap bitmap = mParentFrameLayout.getDrawingCache();
		saveBitmap(bitmap);
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(mSharePictureName))); 
		String shareContent = getResources().getString(R.string.buy_ruyi_guess_down_title);
		intent.putExtra(Intent.EXTRA_TEXT, shareContent);
//		intent.putExtra(Intent.EXTRA_TITLE, "title");
//		intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, getTitle()));  
		
		
		/**创建自己的分享对话框*/
//		SharePopWindow shareWindow = SharePopWindow.getInstance();
//		mParentFrameLayout.buildDrawingCache();
//		Bitmap bitmap = mParentFrameLayout.getDrawingCache();
//		shareWindow.setBitmap(bitmap);
//		shareWindow.createSharePopWindow(RuyiGuessDetailActivity.this,
//				new ShareOnClickItem(), mParentFrameLayout);
	}
	
	/**
	 * 保存bitmap到文件
	 * @param bitmap
	 */
	public void saveBitmap(Bitmap bitmap) {
		String filePath = RuyiGuessUtil.getSaveFilePath(LOCAL_DIR);
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		deleteSharePicture();
		mSharePictureName = filePath + System.currentTimeMillis()+".jpg";
		try {
			FileOutputStream out = new FileOutputStream(mSharePictureName);
			bitmap.compress(Bitmap.CompressFormat.PNG, 60, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送赞或踩的状态
	 * @param type
	 * @param state
	 */
	private void sendPraiseOrTreadState(String type, int state) {
		mProgressdialog = PublicMethod.creageProgressDialog(this);
		Controller.getInstance(this).sendPraiseOrTreadState(mHandler, type,
				mUserNo, mDetailInfoBean.getId(), state);
	}
	
	/**
	 * 20131225 需求改为退出时保存数据
	 */
	private void sendPraiseOrTreadState() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (!"".equals(mPraiseOrTreadState)) {
					String type = "";
					if ("0".equals(mPraiseOrTreadState)) {
						type = RuyiGuessConstant.PRAISE_STATE;
					} else if ("1".equals(mPraiseOrTreadState)) {
						type = RuyiGuessConstant.TREAD_STATE;
					} else {
						return;
					}
					RuyiGuessInterface.getInstance()
					.sendPraiseOrThredState(type, mUserNo, mDetailInfoBean.getId());
				}
			}
		}).start();
	}
	
	/**
	 * 删除分享图片
	 */
	private void deleteSharePicture() {
		if (!"".equals(mSharePictureName)) {
			File image = new File(mSharePictureName);
			if (image.exists()) {
				image.delete();
			}
		}
	}
	
	
	/**
	 * 点击+、-时 seekbar thumb放大显示 
	 * 如果需要次功能放开代码
	 */
//	private void setThumbState() {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(300);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				runOnUiThread(new Runnable() {
//					public void run() {
//						setThumState(false);
//					}
//				});
//			}
//		}).start();
//	}

}