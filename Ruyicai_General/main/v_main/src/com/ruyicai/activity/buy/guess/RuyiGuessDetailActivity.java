package com.ruyicai.activity.buy.guess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid.R;
import com.palmdream.RuyicaiAndroid.wxapi.WXEntryActivity;
import com.ruyicai.activity.account.AccountListActivity;
import com.ruyicai.activity.account.DirectPayActivity;
import com.ruyicai.activity.buy.guess.bean.ItemDetailInfoBean;
import com.ruyicai.activity.buy.guess.bean.ItemOptionBean;
import com.ruyicai.activity.buy.guess.util.RuyiGuessConstant;
import com.ruyicai.activity.buy.guess.util.RuyiGuessUtil;
import com.ruyicai.activity.buy.guess.view.CustomThumbDrawable;
import com.ruyicai.activity.buy.guess.view.RectangularProgressBar;
import com.ruyicai.activity.common.SharePopWindow;
import com.ruyicai.activity.common.SharePopWindow.OnChickItem;
import com.ruyicai.activity.join.JoinDetailActivity;
import com.ruyicai.component.view.TitleBar;
import com.ruyicai.constant.Constants;
import com.ruyicai.controller.Controller;
import com.ruyicai.net.newtransaction.RuyiGuessInterface;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.ruyicai.util.json.JsonUtils;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import com.third.share.ShareActivity;
import com.third.share.Token;
import com.third.share.Weibo;
import com.third.share.WeiboDialogListener;
import com.third.tencent.TencentShareActivity;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/***
 * @author yejc
 *
 */
public class RuyiGuessDetailActivity extends Activity implements View.OnClickListener, IWXAPIEventHandler{
	
	/**
	 * 竞猜标题
	 */
	private String mTitle = "";
	
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
	 * 竞猜是否正确
	 */
	private boolean mIsGuessCorrect = false;
	
	/** 
	 * 是否点击了+、—图标来滑动seekbar 
	 */
	private boolean mIsThumbMove = false;
	
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
	 * 参与成功popwindow
	 */
	private PopupWindow mPopupWindow = null;
	
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
	
	private ItemDetailInfoBean mDetailInfoBean = null;
	
	private int[] mProgressBarColor = {R.color.ruyi_guess_progress_color_first,
			R.color.ruyi_guess_progress_color_second,
			R.color.ruyi_guess_progress_color_third,
			R.color.ruyi_guess_progress_color_fourth,
			R.color.ruyi_guess_progress_color_fifth};
	
	private Context context = RuyiGuessDetailActivity.this;
	
	private TextView mTitleView = null;
	
	
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
		
		RW=new RWSharedPreferences(this, "shareweixin");
	}
	
	private void getIntentInfo() {
		Intent intent = getIntent();
		mUserNo = intent.getStringExtra(RuyiGuessConstant.USER_NO);
		mId = intent.getStringExtra(RuyiGuessConstant.ITEM_ID);
		mIsEnd = intent.getBooleanExtra(RuyiGuessConstant.ISEND, false);
//		mIsMySelected = intent.getBooleanExtra(RuyiGuessConstant.MYSELECTED, false);
		mIsLottery = intent.getStringExtra(RuyiGuessConstant.ISLOTTERY);
	}
	
	private void initView(){
		TitleBar titleBar = (TitleBar)findViewById(R.id.ruyicai_titlebar_layout);
		titleBar.setTitleText(R.string.buy_ruyi_guess);
		TextView title = (TextView)findViewById(R.id.ruyi_guess_item_subtitle);
		title.setText(mTitle);
		Button shareBtn = (Button)findViewById(R.id.ruyi_guess_share_btn);
		shareBtn.setOnClickListener(this);
		
		mParentFrameLayout = (FrameLayout)findViewById(R.id.ruyi_guess_detail_parent_layout);
		mTitleView = (TextView)findViewById(R.id.ruyi_guess_item_subtitle);
		mPrizePoolScoreTV = (TextView)findViewById(R.id.ruyi_guess_item_prizepool_score);
		mDescription = (TextView)findViewById(R.id.ruyi_guess_item_description);
		mDynamicLayout = (LinearLayout)findViewById(R.id.ruyi_guess_itme_layout);
		mParticipatePeopleTV = (TextView)findViewById(R.id.ruyi_guess_item_participate_people);
		mThrowScoreTV = (TextView)findViewById(R.id.ruyi_guess_item_throw_score);
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
		mSubtractScoreBtn = (ImageButton)findViewById(R.id.ruyi_guess_seekbar_subtract);
		mSubtractScoreBtn.setOnClickListener(this);
		mAddScoreBtn = (ImageButton)findViewById(R.id.ruyi_guess_seekbar_add);
		mAddScoreBtn.setOnClickListener(this);
		mPraiseCountTV = (TextView)findViewById(R.id.ruyi_guess_praise_count);
		mTreadCountTV = (TextView)findViewById(R.id.ruyi_guess_tread_count);
		mPraiseIconTV = (TextView)findViewById(R.id.ruyi_guess_praise);
		mTreadIconTV = (TextView)findViewById(R.id.ruyi_guess_tread);
		mSubmitBtn = (Button)findViewById(R.id.ruyi_guess_submit);
		mSubmitBtn.setOnClickListener(this);
	}
	
	private void setMyThrowScore() {
		String score = "";
		if (!mIsSelected && (mIsEnd || mRemainSecond <= 0)) {
			score = "0";
		} else {
			score = String.valueOf(mThrowScore);
		}
		setSpanableForView(mThrowScoreTV, score, R.string.buy_ruyi_guess_throw_score);
	}
	
	private void setSpanableForView(TextView tv, String text, int resId) {
		String str = PublicMethod.formatString(this, resId, text);
		SpannableString span = new SpannableString(str);
		span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.ruyi_guess_progress_red_color)),
				0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(span);
	}
	
	class MessageHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String data = (String)msg.obj;
			int type = msg.what;
			if (data == null || "".equals(data)) {
				Toast.makeText(context, "网络异常！", Toast.LENGTH_SHORT).show();
				PublicMethod.closeProgressDialog(mProgressdialog);
			} else {
				if (type == RuyiGuessConstant.RUYI_GUESS_DETAIL) { //请求后台数据完成
					parserDetailJSON(data);
					if (mIsSuccess) {
						showSubmitSucessPopWindow();
					}
				} else if (type == RuyiGuessConstant.RUYI_GUESS_SUBMIT_INFO){ //提交竞猜结果完成
					sendResultSuccess(data);
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
				mDescription.setText(quizObject.getString("detail"));
				mScore = quizObject.getString("score");
				String result = jsonObj.getJSONArray("result").getJSONObject(0).toString();
				mDetailInfoBean = JsonUtils.resultData(result, ItemDetailInfoBean.class);

				if (mDetailInfoBean == null) return; 
				setPraiseAndTread();
				setDetailDate();
				setInfoForView();
				createDynamicView();
				setSubmitState();
			} else {
				String message = jsonObj.getString("message");
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			PublicMethod.closeProgressDialog(mProgressdialog);
		}
	}
	
	private void setPraiseAndTread() {
		try {
			mPraiseCount = Long.parseLong(mDetailInfoBean.getPraise().trim());
			mTreadCount = Long.parseLong(mDetailInfoBean.getTread().trim());
			mPraiseOrTreadState = mDetailInfoBean.getPraiseOrTread();
			mServerPraiseOrTreadState = mPraiseOrTreadState;
			if ("".equals(mPraiseOrTreadState)) {
				mPraiseIconTV.setOnClickListener(this);
				mTreadIconTV.setOnClickListener(this);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setDetailDate() {
		try {
			if (mDetailInfoBean.getOptions() == null) return;
			for(int i = 0; i < mDetailInfoBean.getOptions().size(); i++) {
				ItemOptionBean optionsBean = mDetailInfoBean.getOptions().get(i);
				if (optionsBean != null) {
					if ("1".equals(optionsBean.getIsSelected())) {
						mIsSelected = true;
						String remainTime = mDetailInfoBean.getTime_remaining();
						//当满足竞猜结束、 剩余时间为0、答案公布这些条件后才显示答案
						if (optionsBean.getId().equals(mDetailInfoBean.getAnswerId())
								&& ("".equals(remainTime) || ("0".equals(remainTime)))
								&& "2".equals(mIsLottery) && mIsEnd) {
							mIsGuessCorrect = true;
						}
					}
					if (optionsBean.getId().equals(mDetailInfoBean.getAnswerId())) {
						mDetailInfoBean.setAnswer(optionsBean.getOption());
					}
					Long count = Long.parseLong(optionsBean.getParticipants().trim());
					mParticiptePeopleCount = mParticiptePeopleCount + count;
					if (!"".equals(optionsBean.getPayScore()) && !"0".equals(optionsBean.getPayScore())) {
						mThrowScore = Integer.parseInt(optionsBean.getPayScore().trim());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 提交竞猜结果成功
	 * @param data
	 */
	private void sendResultSuccess(String data) {
		PublicMethod.closeProgressDialog(mProgressdialog);
		try {
			JSONObject jsonObj = new JSONObject(data);
			String errorCode = jsonObj.getString("error_code");
			if ("0000".equals(errorCode)) {
				mIsSuccess = true;
				mParticiptePeopleCount = 0L;
				mProgressdialog = PublicMethod.creageProgressDialog(context);
				Controller.getInstance(context).getRuyiGuessDetailList(mHandler, mUserNo, mId, "0", 0);
			} else {
				mIsSuccess = false;
				String message = jsonObj.getString("message");
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void setPraiseState() {
		if ("0".equals(mPraiseOrTreadState)) {
			mPraiseOrTreadState = "";
			mPraiseIconTV.setBackgroundResource(R.drawable.ruyi_guess_praise_normal);
			mPraiseCount = mPraiseCount - 1;
		} else if("".equals(mPraiseOrTreadState)){
			mPraiseOrTreadState = "0";
			mPraiseIconTV.setBackgroundResource(R.drawable.ruyi_guess_praise_click);
			mPraiseCount = mPraiseCount + 1;
		}
		setPraiseTreadCount(mPraiseIconTV, mPraiseCount);
		setPraiseTreadCountAnimation(mPraiseCountTV);
	}
	
	private void setTreadState() {
		if ("1".equals(mPraiseOrTreadState)) {
			mPraiseOrTreadState = "";
			mTreadIconTV.setBackgroundResource(R.drawable.ruyi_guess_tread_normal);
			mTreadCount = mTreadCount - 1;
		} else if("".equals(mPraiseOrTreadState)){
			mPraiseOrTreadState = "1";
			mTreadIconTV.setBackgroundResource(R.drawable.ruyi_guess_tread_click);
			mTreadCount = mTreadCount + 1;
		}
		setPraiseTreadCount(mTreadIconTV, mTreadCount);
		setPraiseTreadCountAnimation(mTreadCountTV);
	}
	
	private void setInfoForView() {
		mParticipatePeopleTV.setText(PublicMethod.formatString(this, R.string.buy_ruyi_guess_participate_people, 
				String.valueOf(mParticiptePeopleCount)));
		setPraiseOrTreadIcon();
		setSeekBarProgress();
		setPrizePoolScore();
		setRemainTimeState();
		setAwardIcon();
		setAwardScore();
		setMyThrowScore();
		setPraiseTreadCount(mPraiseIconTV, mPraiseCount);
		setPraiseTreadCount(mTreadIconTV, mTreadCount);
	}
	
	private void setSeekBarProgress() {
		int progress = (mThrowScore - 200)/100;
		if (progress > 0) {
			mScoreSeekBar.setProgress(progress);
		}
	}
	
	private void setPraiseOrTreadIcon() {
		if ("0".equals(mPraiseOrTreadState)) {
			mPraiseIconTV.setBackgroundResource(R.drawable.ruyi_guess_praise_click);
		} else if ("1".equals(mPraiseOrTreadState)) {
			mTreadIconTV.setBackgroundResource(R.drawable.ruyi_guess_tread_click);
		}
	}
	
	private void setPrizePoolScore() {
		String prizePoolScore = mDetailInfoBean.getPrizePoolScore();
		setSpanableForView(mPrizePoolScoreTV, prizePoolScore, R.string.buy_ruyi_guess_item_prizepool_score);
	}
	
	private void setRemainTimeState() {
		String remainTime = mDetailInfoBean.getTime_remaining();
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
	}
	
	private void setAwardIcon() {
		if (mIsGuessCorrect) {
			mAwardIconIV.setVisibility(View.VISIBLE);
			mAwardIconIV.setImageResource(R.drawable.guess_jiang);
		} else if (mIsEnd && mRemainSecond == 0){
			mAwardIconIV.setVisibility(View.VISIBLE);
			mAwardIconIV.setImageResource(R.drawable.ruyiguess_stop);
		}
	}
	
	private void setAwardScore() {
		if (mIsEnd && !"".equals(mDetailInfoBean.getAnswer())
				&& "2".equals(mIsLottery)
				&& ("".equals(mDetailInfoBean.getTime_remaining())
						|| "0".equals(mDetailInfoBean.getTime_remaining()))) {
			mAnswerLayout.setVisibility(View.VISIBLE);
			mAnswerTV.setText("答案: "+mDetailInfoBean.getAnswer());
			if ("".equals(mDetailInfoBean.getPrizeScore())) {
				mAwardScoreTV.setText("积分: +0");
			} else {
				mAwardScoreTV.setText("积分: +"+mDetailInfoBean.getPrizeScore());
			}
		}
	}
	
	private void setPraiseTreadCount(TextView tv, long count) {
		tv.setText(String.valueOf(count));
	}
	
	private void setPraiseTreadCountAnimation(TextView tv) {
		Animation topAnimation = AnimationUtils.loadAnimation(this, 
        		R.anim.push_top_out);
		topAnimation.setAnimationListener(new AnimationEndListener(tv));
		tv.setVisibility(View.VISIBLE);
		setTextColor(tv);
		tv.startAnimation(topAnimation);
	}
	
	private void setTextColor(TextView tv) {
		if ("".equals(mPraiseOrTreadState)) {
			tv.setTextColor(getResources().getColor(R.color.blue));
			tv.setText("-1");
		} else {
			tv.setTextColor(getResources().getColor(R.color.red));
			tv.setText("+1");
		}
	}
	
	/**
	 * 动态创建选项视图
	 */
	private void createDynamicView() {
		mDynamicLayout.removeAllViews();
		List<ItemOptionBean> options = mDetailInfoBean.getOptions();
		if (options != null && options.size() > 0) {
			View myScoreLayout = (View) mInflater.inflate(
					R.layout.buy_ruyiguess_textview, null);
			TextView myScore = (TextView)myScoreLayout.findViewById(R.id.ruyi_guess_my_score_text);
			myScore.setText(PublicMethod.formatString(this, R.string.buy_ruyi_guess_my_score, mScore));
			TextView buyScore = (TextView)myScoreLayout.findViewById(R.id.ruyi_guess_buy_score);
			buyScore.setOnClickListener(this);
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
		setParticipateStateForView();
		if (mIsSelected) {
			setSubmitBtnState(R.drawable.buy_ruyiguess_item_gray, R.string.buy_ruyi_guess_btn_participate, false);
		} else {
			if (mIsEnd) {
				setSubmitBtnState(R.drawable.buy_ruyiguess_item_gray, R.string.buy_ruyi_guess_btn_end, false);
			} else {
				setSubmitBtnState(R.drawable.loginselecter, R.string.buy_ruyi_guess_submit_result, true);
			}
		}
	}
	
	private void submitData() {
		int score = 0;
		try {
			score = Integer.parseInt(mScore);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if ("".equals(mOptionId)) {
			Toast.makeText(context, R.string.buy_ruyi_guess_please_select, 
					Toast.LENGTH_SHORT).show();
		} else if (mThrowScore > score) {
			createRechargeDialog();
		} else {
			mProgressdialog = PublicMethod.creageProgressDialog(context);
			Controller.getInstance(context)
					.sendDateToService(mHandler, mUserNo, mId, getSubmitInfo());
		}
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
	private void showSubmitSucessPopWindow() {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.buy_ruyiguess_success_dialog, null);
		LinearLayout shareLayout = (LinearLayout)view.findViewById(R.id.ruyi_guess_share_layout);
		shareLayout.setOnClickListener(this);
		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,PublicMethod.getPxInt(130, context));
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.update();
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.showAtLocation(mParentFrameLayout, Gravity.BOTTOM, 0, 0);
		ScheduledExecutorService mClosePopWindowExecutorService = Executors.newSingleThreadScheduledExecutor();
		mClosePopWindowExecutorService.schedule(new ScrollTask(), 8, TimeUnit.SECONDS);
	}
	
	private class ScrollTask implements Runnable {
		public void run() {
			if (mPopupWindow != null) {
				synchronized (mPopupWindow) {
					runOnUiThread(new Runnable() {
						public void run() {
							mPopupWindow.dismiss();
						}
					});
				}
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mIsSuccess && keyCode == KeyEvent.KEYCODE_BACK) {
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
		RuyiGuessUtil.deleteSharePicture(mSharePictureName); //删除分享图片
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
			boolean isRun = true;
			while (isRun) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				mRemainSecond = mRemainSecond - 1;
				runOnUiThread(new Runnable() {
					public void run() {
						mDetailInfoBean.setTime_remaining(String.valueOf(mRemainSecond));
						setRemainTime();
						setEndState();
					}
				});
				if (!(mRemainSecond > 0)) {
					isRun = false;
				}
			}
		}
	}
	
	private void setRemainTime() {
		String timeString = PublicMethod.formatString(this, 
				R.string.buy_ruyi_guess_remain_time, RuyiGuessUtil.formatLongToString(mRemainSecond));
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
			}
			MobclickAgent.onEvent(context, "ruyijingcai_ding");
			break;

		case R.id.ruyi_guess_tread:
			if (!"0".equals(mPraiseOrTreadState)) {
				setTreadState();
			}
			MobclickAgent.onEvent(context, "ruyijingcai_cai");
			break;
			
		case R.id.ruyi_guess_buy_score:
			turnToRecharge();
			break;
			
		case R.id.ruyi_guess_share_layout:
			createSharePopWindow();
			if (mPopupWindow != null) {
				mPopupWindow.dismiss();
			}
			break;
			
		case R.id.ruyi_guess_submit:
			submitData();
			MobclickAgent.onEvent(context, "ruyijingcai_tijiao");
			break;
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
		SharePopWindow shareWindow = SharePopWindow.getInstance();
		shareWindow.createSharePopWindow(RuyiGuessDetailActivity.this,
				new PopOnItemChick(), mParentFrameLayout, "分享到:");
	}
	
	public class PopOnItemChick implements OnChickItem {

		@Override
		public void onClickItem(int viewId) {
			switch (viewId) {
			case 0: // 微信分享
				toWeiXin();
				break;
			case 1: // 微信朋友圈分享
				toPengYouQuan();
				break;
			case 2:
				oauthOrShare();
				break;
			case 3: // 腾讯微博分享
				tenoauth();
				break;
			}

		}
	}
	
	/**
	 * 对该页面截屏并保存图片
	 */
	private void saveBitmap(){
		mParentFrameLayout.buildDrawingCache();
		Bitmap bitmap1 = mParentFrameLayout.getDrawingCache();
		mSharePictureName=PublicMethod.saveBitmap(PublicMethod.matrixBitmap(bitmap1, 400, 600));
	}
	
	/**
	 * 分享到微信
	 */
	private void toWeiXin() {
		saveBitmap();
		
		RW.putStringValue("weixin_pengyou", "toweixin");
		Intent intent = new Intent(RuyiGuessDetailActivity.this,
				WXEntryActivity.class);
		intent.putExtra("sharecontent","参与如意竞猜赚彩金中大奖"+"http://iphone.ruyicai.com/html/share.html?shareRuyiGuess");
		intent.putExtra("mSharePictureName",mSharePictureName);
		intent.putExtra("url","http://iphone.ruyicai.com/html/share.html?shareRuyiGuess");
		startActivity(intent);
	}

	/**
	 * 分享到朋友圈
	 */
	private void toPengYouQuan() {
		saveBitmap();
		RW.putStringValue("weixin_pengyou", "topengyouquan");
		Intent intent = new Intent(RuyiGuessDetailActivity.this,
				WXEntryActivity.class);
		intent.putExtra("sharecontent","参与如意竞猜赚彩金中大奖!"+"http://iphone.ruyicai.com/html/share.html?shareRuyiGuess");
		intent.putExtra("mSharePictureName",mSharePictureName);
		intent.putExtra("url","http://iphone.ruyicai.com/html/share.html?shareRuyiGuess");
		startActivity(intent);
	}
	
	/**
	 * 分享到新浪微博
	 */
	private void oauthOrShare() {
		shellRW = new RWSharedPreferences(RuyiGuessDetailActivity.this, "addInfo");
		token = shellRW.getStringValue("token");
		expires_in = shellRW.getStringValue("expires_in");
		if (token.equals("")) {
			oauth();
		} else {
			isSinaTiaoZhuan = true;
			initAccessToken(token, expires_in);
		}
		
	}
	
	private void initAccessToken(String token, String expires_in) {
		Token accessToken = new Token(token, Weibo.getAppSecret());
		accessToken.setExpiresIn(expires_in);
		Weibo.getInstance().setAccessToken(accessToken);
		share2weibo("参与如意竞猜赚彩金中大奖，下载Android手机客户端:"
				+"http://iphone.ruyicai.com/html/share.html?shareRuyiGuess"/* Constants.shareContent */);
		if (isSinaTiaoZhuan) {
			Intent intent = new Intent();
			intent.setClass(RuyiGuessDetailActivity.this, ShareActivity.class);
			startActivity(intent);
		}
	}
	
	private void share2weibo(String content) {
		Weibo weibo = Weibo.getInstance();
		weibo.share2weibo(this, weibo.getAccessToken().getToken(), weibo
				.getAccessToken().getSecret(), content, mSharePictureName);
	}
	
	private void oauth() {
		Weibo weibo = Weibo.getInstance();
		weibo.setupConsumerConfig(Constants.CONSUMER_KEY,
				Constants.CONSUMER_SECRET);
		// Oauth2.0
		// 隐式授权认证方式
		weibo.setRedirectUrl(Constants.CONSUMER_URL);// 此处回调页内容应该替换为与appkey对应的应用回调页
		// 对应的应用回调页可在开发者登陆新浪微博开发平台之后，
		// 进入我的应用--应用详情--应用信息--高级信息--授权设置--应用回调页进行设置和查看，
		// 应用回调页不可为空
		weibo.authorize(RuyiGuessDetailActivity.this, new AuthDialogListener());
	}
	
	class AuthDialogListener implements WeiboDialogListener {

		@Override
		public void onComplete(Bundle values) {
			PublicMethod.myOutLog("token111",
					"zhiqiande" + shellRW.getStringValue("token"));
			PublicMethod.myOutLog("onComplete", "12131321321321");
			String token = values.getString("access_token");
			PublicMethod.myOutLog("token", token);
			String expires_in = values.getString("expires_in");
			shellRW.putStringValue("token", token);
			shellRW.putStringValue("expires_in", expires_in);
			// is_sharetosinaweibo.setBackgroundResource(R.drawable.on);
			initAccessToken(token, expires_in);
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 分享到腾讯微博
	 */
	private void tenoauth() {
		saveBitmap();
		Intent intent = new Intent(RuyiGuessDetailActivity.this,
				TencentShareActivity.class);
		intent.putExtra("tencent","参与如意竞猜赚彩金中大奖，下载Android手机客户端:"
				+"http://iphone.ruyicai.com/html/share.html?shareRuyiGuess");
		intent.putExtra("bitmap",mSharePictureName);
		startActivity(intent);
		
	}
	
	private RWSharedPreferences RW,shellRW;
	private String token, expires_in;
	private boolean isSinaTiaoZhuan = true;
	
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
	 * 如果积分不足 充值对话框
	 */
	private void createRechargeDialog() {
		final Dialog mDialog = new AlertDialog.Builder(this).create();
		View view = LayoutInflater.from(this)
				.inflate(R.layout.buy_ruyiguess_recharge_dialog, null);
		Button directPay = (Button)view.findViewById(R.id.ruyi_guess_direct_payment);
		directPay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				turnToDirectPay();
				mDialog.dismiss();
			}
		});
		Button rechargeBtn = (Button)view.findViewById(R.id.ruyi_guess_recharge);
		rechargeBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				turnToRecharge();
				mDialog.dismiss();
			}
		});
		mDialog.show();
		mDialog.getWindow().setContentView(view);
	}
	
	private void turnToDirectPay() {
		Intent intent = new Intent(context, DirectPayActivity.class);
		startActivity(intent);
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResp(BaseResp arg0) {
		switch (arg0.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
			PublicMethod.addScoreForShare(RuyiGuessDetailActivity.this);
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			Toast.makeText(context, "取消分享", Toast.LENGTH_SHORT).show();
			break;
		}
	}


	/**
	 * 跳转到购买积分界面
	 */
	private void turnToRecharge() {
		Intent intent = new Intent(context, AccountListActivity.class);
//		Intent intent = new Intent(context, RuyiGuessCreateGroupSuccessActivity.class);
		intent.putExtra("isonKey", "fasle");
		startActivity(intent);
	}
	
	private class AnimationEndListener implements AnimationListener{
		
		private TextView textView;
		public AnimationEndListener(TextView tv) {
			textView = tv;
		}
		@Override
		public void onAnimationStart(Animation animation) {}

		@Override
		public void onAnimationEnd(Animation animation) {
			textView.setVisibility(View.GONE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}
	}
}