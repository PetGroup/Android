/**
 * 
 */
package com.ruyicai.activity.buy.dlc;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.inject.Inject;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.BuyGameDialog;
import com.ruyicai.activity.buy.HighFrequencyNoticeHistroyActivity;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.adapter.ElevenSelectorFiveHistoryLotteryAdapter;
import com.ruyicai.code.dlc.DlcCode;
import com.ruyicai.code.dlc.DlcDanTuoCode;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView.ElevenSelectFiveTopViewClickListener;
import com.ruyicai.constant.Constants;
import com.ruyicai.controller.Controller;
import com.ruyicai.controller.listerner.LotteryListener;
import com.ruyicai.controller.service.LotteryService;
import com.ruyicai.custom.jc.button.MyButton;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.DlcRxBalls;
import com.ruyicai.json.miss.DlcMissJson;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.json.miss.SscZMissJson;
import com.ruyicai.model.PrizeInfoBean;
import com.ruyicai.model.PrizeInfoList;
import com.ruyicai.model.ReturnBean;
import com.ruyicai.net.newtransaction.GetLotNohighFrequency;
import com.ruyicai.net.newtransaction.PrizeInfoInterface;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.CheckUtil;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.ruyicai.util.json.JsonUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * 多乐彩（11选5）
 * 
 * @author Administrator
 * 
 */
public class Dlc extends ZixuanAndJiXuan implements LotteryListener {
	protected int BallResId[] = { R.drawable.cq_11_5_ball_normal, R.drawable.cq_11_5_ball_select };
	protected String pt_types[] = { "PT_R2", "PT_R3", "PT_R4", "PT_R5", "PT_R6", "PT_R7","PT_R8",
			"PT_QZ1", "PT_QZ2", "PT_QZ3", "PT_ZU2", "PT_ZU3" };// 普通类型
	protected String dt_types[] = { "DT_R2", "DT_R3", "DT_R4", "DT_R5", "DT_R6", "DT_R7", "DT_R8",
			"DT_ZU2", "DT_ZU3" };// 胆拖类型
	protected int nums[] = { 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 2, 3 };// 单式机选个数
	protected int dannums[] = { 2, 3, 4, 5, 6, 7, 8, 2, 3 };// 单式机选个数
	protected int numsdantuo[] = {};
	protected int maxs[] = { 3, 4, 7, 10, 8, 9, 8, 6, 11, 11, 8, 9 };// 选区最大小球数
	public static String state = "";// 当前类型
	public int num = 1;// 当前单式机选个数
	protected int max = 6;// 选区最大小球数
	public boolean isJiXuan = false;
	protected boolean is11_5DanTuo = false;
	public static String batchCode;// 期号
	private int lesstime;// 剩余时间
	private DlcHandler handler = new DlcHandler(this);
	public String lotno;
	private boolean isRun = true;
	private PopupWindow popupwindow;
	private BuyGameDialog gameDialog;
	private Context context;
	private String showMessage = "";
	private boolean isFirst = true;
	public AddView addView = new AddView(this);
	private Controller controller = null;
	private RWSharedPreferences rw;
	
	int[] cqArea={5,6};
	private int itemId=3;
	private int playMethodTag=1;
	private boolean isZhMiss=false;
	public int noticeLotNo;
	private ProgressDialog progressdialog;
	int[] dtNum={1,2,3,4,5,6,7,1,2};// 胆拖选区最大小球数
	private static final String dtTPrompt="我认为可能出的号码  选2-10个";//拖码投注提示
	@Inject private LotteryService lotteryService;
	private static final int GET_PRIZEINFO_ERROR = 0;
	private static final int GET_PRIZEINFO_SUCCESS = 3;
	private ElevenSelectorFiveHistoryLotteryAdapter historyLotteryAdapter;
	//胆码投注提示
	private String dtDPrompt(int a) {
		String str = "";
		if (state.equals("DT_R2") || state.equals("DT_ZU2")) {
			str = "我认为必出的号码   选1个";
		} else if (state.equals("DT_ZU3")) {
			str = "我认为必出的号码   至少选1个，最多2个";
		} else {
			str = "我认为必出的号码   至少选1个，最多" + (itemId + 1) + "个";
		}
		return str;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAddView(addView);
		setContentView(R.layout.buy_dlc_main);
		super.lotno = Constants.LOTNO_11_5;
		lotteryService.addLotteryListeners(Dlc.this);
		/* Add by fansm 20130416 start */
		if (Constants.isDebug)
			PublicMethod.outLog(this.getClass().getSimpleName(), "onCreate()");
		/* Add by fansm 20130416 end */
		batchCode = ""; // add by yejc 20130708
		context = this;
		highttype = "DLC";
		setLotno();
		initView();
		state = "PT_R5";
		action(3);
		setIssue(lotno);
		setlastbatchcode(lotno);
		MobclickAgent.onEvent(this, "jiangxi11xuan5"); // BY贺思明 点击首页的“江西11选5”图标
		MobclickAgent.onEvent(this, "gaopingoucaijiemian ");// BY贺思明 高频购彩页面
		rw=new RWSharedPreferences(this,"addInfo");
		historyLotteryAdapter=new ElevenSelectorFiveHistoryLotteryAdapter(Dlc.this);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (Constants.isDebug)
			PublicMethod.outLog(this.getClass().getSimpleName(), "onRestart()");
	}

	/**
	 * 设置彩种编号
	 * 
	 * @param lotno
	 */
	public void setLotno() {
		this.lotno = Constants.LOTNO_11_5;
		this.noticeLotNo=NoticeActivityGroup.ID_SUB_DLC_LISTVIEW;
		lotnoStr = lotno;
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		elevenSelectFiveTopView=(ElevenSelectFiveTopView) findViewById(R.id.elevenSelectFiveView);
		elevenSelectFiveTopView.setQueryMessage(lotno, noticeLotNo);
		elevenSelectFiveTopView.isShowLuckSelectNumLayout(true);
		elevenSelectFiveTopView.setLotteryInfoBackGround(R.drawable.nmk3_head_bg);
		elevenSelectFiveTopView.addElevenSelectFiveTopViewClickListener(new ElevenSelectFiveTopViewClickListener() {
			
			@Override
			public void TouchPTPlayMethod(int position) {
				intitZhMissBtn();
				itemId=position;
				playMethodTag=1;
				changeState(playMethodTag,position);
				action(position);
			}
			
			@Override
			public void TouchDTPlayMethod(int position) {
				intitZhMissBtn();
				playMethodTag=2;
				itemId=position;
				changeState(playMethodTag,position);
				action(position);
			}
			
			@Override
			public void ElevenSelectFiveOmission() {
				if(isZhMiss){
					intitZhMissBtn();
				}else{
					isZhMiss=true;
					isMove = true;
					isElevenSelectFive=true;
					lotteryNumberLayout.setVisibility(View.GONE);
					elevenSelectFiveZhMissLayout.setVisibility(View.VISIBLE);
					elevenSelectFiveTopView.setOmissionBtnBackGround(R.drawable.eleven_select_five_main_touzhu);
				}
			}
			
			@Override
			public void ElevenSelectFiveFresh() {
				rw.putBooleanValue("isShowDialog",true);
				initLatestLotteryList();
				//historyLotterList.setHistoryLotteryList(progressdialog,lotno);
				lotteryService.getNoticePrizeInfoList(lotno);
			}
			
			@Override
			public void ChooseIsToShowMissMessage(boolean isChecked) {
				controlShowMiss(isChecked);
			}
		});
	}
	
	private void intitZhMissBtn(){
		isZhMiss=false;
		isMove = false;
		isElevenSelectFive=false;
		lotteryNumberLayout.setVisibility(View.VISIBLE);
		elevenSelectFiveZhMissLayout.setVisibility(View.GONE);
		elevenSelectFiveTopView.setOmissionBtnBackGround(R.drawable.eleven_select_five_yilou_zuhe);
	}
	
	public void changeState(int playMethodTag,int position){
		if(playMethodTag==1){
			state = pt_types[position];
		}else{
			state = dt_types[position];
		}
	}


	public void turnHosity() {
		Intent intent = new Intent(Dlc.this,
				HighFrequencyNoticeHistroyActivity.class);
		intent.putExtra("lotno", lotno);
		startActivity(intent);
	}

	/**
	 * RadioGroup是否隐藏
	 * 
	 * @param lotno
	 */
	public void setGroupVisable(boolean isVisable) {
		if (isVisable) {
			group.setVisibility(RadioGroup.VISIBLE);
		} else {
			group.setVisibility(RadioGroup.GONE);
		}
	}

	/**
	 * 赋值给当前期
	 * 
	 * @param type彩种编号
	 */
	public void setIssue(final String lotno) {
		final Handler sscHandler = new Handler();
		elevenSelectFiveTopView.setElevenSelectFiveEndTime("期号获取中....");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				String re = "";
				String message = "";
				re = GetLotNohighFrequency.getInstance().getInfo(lotno);
				if (!re.equalsIgnoreCase("")) {
					try {
						JSONObject obj = new JSONObject(re);
						message = obj.getString("message");
						error_code = obj.getString("error_code");
						lesstime = Integer.valueOf(CheckUtil.isNull(obj
								.getString("time_remaining")));
						batchCode = obj.getString("batchcode");
						while (isRun) {
							if (isEnd(lesstime)) {
								sscHandler.post(new Runnable() {
									public void run() {
										elevenSelectFiveTopView.setElevenSelectFiveEndTime("距"
												+ batchCode.substring(batchCode.length()-2)
												+ "期截止:"
												+ PublicMethod
														.isTen(lesstime / 60)
												+ "分"
												+ PublicMethod
														.isTen(lesstime % 60)
												+ "秒");
									}
								});
								Thread.sleep(1000);
								lesstime--;
							} else {
								sscHandler.post(new Runnable() {
									public void run() {
										elevenSelectFiveTopView
												.setElevenSelectFiveEndTime("距"
														+ batchCode
																.substring(8)
														+ "期截止:00分00秒");
										nextIssue();
									}
								});
								break;
							}
						}
					} catch (Exception e) {
						sscHandler.post(new Runnable() {
							public void run() {
								elevenSelectFiveTopView.setElevenSelectFiveEndTime("获取期号失败");
							}
						});
					}
				} else {

				}
			}
		});
		thread.start();
	}

	public void setlastbatchcode(final String type) {
		/* Add by fansm 20130417 start */
		if (Constants.isDebug)
			PublicMethod.outLog(this.getClass().getSimpleName(),
					"setlastbatchcode()");
		/* Add by fansm 20130417 end */
		final Handler tHandler = new Handler();
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				final JSONObject prizemore = PrizeInfoInterface.getInstance()
						.getNoticePrizeInfo(type, "1", "1");
				try {
					final String msg = prizemore.getString("message");
					final String code = prizemore.getString("error_code");
					if (code.equals("0000")) {
						JSONArray prizeArray = prizemore.getJSONArray("result");
						JSONObject prizeJson = (JSONObject) prizeArray.get(0);
						final String wincode = prizeJson.getString("winCode");
						/* Add by fansm 20130417 start */
						final String batchCode = prizeJson
								.getString("batchCode");
						/* Add by fansm 20130417 end */
						tHandler.post(new Runnable() {
							@Override
							public void run() {
//								lastcode.setText(parseStrforcode(type, wincode));
								/* Add by fansm 20130417 start */
//								lastCodeTxt.setText("第" + batchCode + "期开奖：");
								/* Add by fansm 20130417 end */
							}
						});

					} else {
						tHandler.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(Dlc.this, msg,
										Toast.LENGTH_SHORT).show();
							}
						});
					}
				} catch (JSONException e) {
				}
			}
		});
		thread.start();
	}

	public SpannableStringBuilder parseStrforcode(String type, String str) {
		StringBuffer strb = new StringBuffer();
		SpannableStringBuilder builder = new SpannableStringBuilder();
		if (!str.equals("")) {
			strb.append(str.replace(" ", ""));
			if (type.equals(Constants.LOTNO_ten)) {
				strb.insert(2, ",");
				strb.insert(5, ",");
				strb.insert(8, ",");
				strb.insert(11, ",");
				strb.insert(14, ",");
				strb.insert(17, ",");
				strb.insert(20, ",");
				String[] strgd = strb.toString().split(",");
				int upLength = 0;
				for (int i = 0; i < strgd.length; i++) {
					String code = strgd[i];
					builder.append(code);
					if (code.equals("19") || code.equals("20")) {
						builder.setSpan(new ForegroundColorSpan(Color.RED),
								upLength, code.length() + upLength,
								Spanned.SPAN_COMPOSING);
					} else {
						builder.setSpan(new ForegroundColorSpan(Color.BLUE),
								upLength, code.length() + upLength,
								Spanned.SPAN_COMPOSING);
					}
					if (i != strgd.length - 1) {
						builder.append(",");
					}
					upLength = builder.length();
				}

			} else {
				strb.insert(2, ",");
				strb.insert(5, ",");
				strb.insert(8, ",");
				strb.insert(11, ",");
				builder.append(strb);
				ForegroundColorSpan span_RED = new ForegroundColorSpan(
						Color.RED);
				builder.setSpan(span_RED, 0, strb.length(),
						Spanned.SPAN_COMPOSING);
			}
		}
		return builder;

	}

	private boolean isEnd(int time) {
		if (time > 0) {
			return true;
		} else {
			return false;
		}
	}

	private void nextIssue() {
		new AlertDialog.Builder(Dlc.this)
				.setTitle("提示")
				.setMessage(
						elevenSelectFiveTopView.getElevenSelectFiveTitleText() + "第" + batchCode
								+ "期已经结束,是否转入下一期")
				.setNegativeButton("转入下一期", new Dialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						setIssue(lotno);
					}

				})
				.setNeutralButton("返回主页面",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								Dlc.this.finish();
							}
						}).create().show();
	}

	public void updatePage() {
		Intent intent = new Intent(Dlc.this, Dlc.class);
		startActivity(intent);
		finish();
	}

	protected void onPause() {
		super.onPause();
		if (Constants.isDebug)
			PublicMethod.outLog(this.getClass().getSimpleName(), "onPause()");
	}

	/**
	 * 单选框切换直选，机选
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		onCheckAction(checkedId);
	}

	public void onCheckAction(int checkedId) {
		radioId = checkedId;
		switch (checkedId) {
		case 0:
			isJiXuan = false;
			if(playMethodTag==1){
				startSensor();
				is11_5DanTuo = false;
				createViewZx(checkedId);
			}else{
				stopSensor();
				is11_5DanTuo = true;
				createViewDT(checkedId);
			}
			
			
			if (!state.equals("PT_QZ2") && !state.equals("PT_QZ3")
					&& !state.equals("PT_QZ1") && !state.equals("PT_ZU2")
					&& !state.equals("PT_ZU2") && !state.equals("PT_ZU3")) {
				lotteryService.getNoticePrizeInfoList(lotno);
			}
			setBottomView();
			
			break;
		case 1:
			stopSensor();
			is11_5DanTuo = true;
			isJiXuan = false;
			createViewDT(checkedId);
			break;
		}
	}
	
	/**
	 * 设置号码栏下开奖信息显示方式
	 */
	private void setBottomView(){
		if(state.equals("PT_QZ1")
				||state.equals("PT_QZ2")
				||state.equals("PT_QZ3")
				||state.equals("PT_ZU2")
				||state.equals("PT_ZU3")
				||state.equals("DT_ZU2")
				||state.equals("DT_ZU3")){
			latestLotteryList.setVisibility(View.VISIBLE);
			elevenSelectFiveHistoryLotteryView.setVisibility(View.GONE);
			buy_choose_history_list.setVisibility(View.GONE);
		}else {
			latestLotteryList.setVisibility(View.GONE);
			elevenSelectFiveHistoryLotteryView.setVisibility(View.VISIBLE);
		}
	}
	
	private void controlShowMiss(boolean isChecked) {
		if (!isChecked) {
			for (int area_i = 0; area_i < areaNums.length; area_i++) {
				int rowNum = areaNums[area_i].tableLayout
						.getChildCount();
				for (int row_j = 0; row_j < rowNum; row_j++) {
					if(row_j % 2 != 0){
						areaNums[area_i].tableLayout
						.getChildAt(row_j)
						.setVisibility(View.GONE);
					}
				}
			}

		} else {
			for (int area_i = 0; area_i < areaNums.length; area_i++) {
				int rowNum = areaNums[area_i].tableLayout
						.getChildCount();
				for (int row_j = 0; row_j < rowNum; row_j++) {
					if(row_j % 2 != 0){
						areaNums[area_i].tableLayout
						.getChildAt(row_j)
						.setVisibility(View.VISIBLE);
					}
				}
			}
		}
	}

	/**
	 * 初始化自选选区
	 */
	public void createViewZx(int id) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
//		sscCode = new DlcCode();
		if (state.equals("PT_QZ2")) {
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(cqArea, 1, 11, BallResId, 0, 1,Color.RED, "万位","", false, true, false);
			areaNums[1] = new AreaNum(cqArea, 1, 11, BallResId, 0, 1,Color.RED, "千位","", false, true, false);
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.CQ_QE,id, true,clickBallText);
		}else if (state.equals("PT_QZ3")) {
			areaNums = new AreaNum[3];
			areaNums[0] = new AreaNum(cqArea, 1, 11, BallResId, 0, 1,Color.RED, "万位","", false, true, false);
			areaNums[1] = new AreaNum(cqArea, 1, 11, BallResId, 0, 1,Color.RED, "千位","", false, true, false);
			areaNums[2] = new AreaNum(cqArea, 1, 11, BallResId, 0, 1,Color.RED, "百位","", false, true, false);
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.CQ_QS,id, true,clickBallText);
		}else if(state.equals("PT_QZ1")){
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, 1, 11, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.CQ_QY,id, true,clickBallText);
		}else if (state.equals("PT_ZU2")) {
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, 2, 11, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.CQ_QE,id, true,clickBallText);
		}else if (state.equals("PT_ZU3")) {
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, 3, 11, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.CQ_QS,id, true,clickBallText);
		} else {
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, nums[itemId], 11, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.NULL,id, true,clickBallText);
		}
	}

	/**
	 * 初始化胆拖选区
	 */
	public void createViewDT(int id) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
		areaNums = new AreaNum[2];
		areaNums[0] = new AreaNum(cqArea, 1, dtNum[itemId], BallResId, 0, 1,
				Color.RED, "胆码", dtDPrompt(itemId), false, false, true);
		areaNums[1] = new AreaNum(cqArea, 10, 10, BallResId, 0, 1, Color.RED,
				"拖码", dtTPrompt, false, false, true);
		baseSensor.stopAction();
		if (state.equals("DT_ZU2")) {
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.CQ_QE, id, true,clickBallText);
		} else if (state.equals("DT_ZU3")) {
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.CQ_QS, id, true,clickBallText);
		} else {
			createViewCQ(areaNums, sscCode, ZixuanAndJiXuan.NULL, id, true,clickBallText);
		}
	}


	/**
	 * 初始化胆拖选区
	 */
	public void initDTArea() {
		areaNums = new AreaNum[2];
		areaNums[0] = new AreaNum(11, 10, num - 1, BallResId, 0, 1, Color.RED,
				"胆码");
		areaNums[1] = new AreaNum(11, 10, 10, BallResId, 0, 1, Color.RED, "拖码");
	}

	/**
	 * spinner处理事件
	 */
	public void action(int position) {
		num = nums[position];
		max = maxs[position];
		missView.clear();
		childtype = new String[] { "自选" };
		init();
		childtypes.setVisibility(View.GONE);
		group.setOnCheckedChangeListener(this);
		group.check(0);
		setSellWay();
	}
	
	

	public void setSellWay() {
		boolean isShowZhMissBtn=true;
		if (state.equals("PT_QZ2") || state.equals("PT_QZ1")) {
			isShowZhMissBtn=false;
			if (!sellWay.equals(MissConstant.DLC_MV_Q3)) {
				sellWay = MissConstant.DLC_MV_Q3;
			}
		} else if (state.equals("PT_ZU2")|| state.equals("DT_ZU2")) {
			isShowZhMissBtn=false;
			if (!sellWay.equals(MissConstant.DLC_MV_Q2Z)) {
				sellWay = MissConstant.DLC_MV_Q2Z;
			}
		} else if (state.equals("PT_ZU3")|| state.equals("DT_ZU3")) {
			isShowZhMissBtn=false;
			if (!sellWay.equals(MissConstant.DLC_MV_Q3Z)) {
				sellWay = MissConstant.DLC_MV_Q3Z;
			}
		} else if (state.equals("PT_R5")) {
			isMissNet(new SscZMissJson(), MissConstant.DLC_MV_ZH_R5, true);// 获取遗漏值
			sellWay = MissConstant.DLC_MV_RX;
		} else if (state.equals("PT_R7")) {
			isMissNet(new SscZMissJson(), MissConstant.DLC_MV_ZH_R7, true);// 获取遗漏值
			sellWay = MissConstant.DLC_MV_RX;
		} else if (state.equals("PT_R8")) {
			isMissNet(new SscZMissJson(), MissConstant.DLC_ZH_R8, true);// 获取遗漏值
			sellWay = MissConstant.DLC_MV_RX;
		} else if (state.equals("PT_QZ3")) {
			sellWay = MissConstant.DLC_MV_Q3;
			isMissNet(new SscZMissJson(), MissConstant.DLC_MV_Q3_ZH, true);// 获取遗漏值
		} else {
			isShowZhMissBtn=false;
			sellWay = MissConstant.DLC_MV_RX;
		}
		showZhMissBtn(isShowZhMissBtn);
		isMissNet(new DlcMissJson(), sellWay, false);// 获取遗漏值
		
	}
	
	/**
	 * 是否显示遗漏组合按钮
	 */
	public void showZhMissBtn(boolean isShowZhMissBtn){
		if(isShowZhMissBtn){
			elevenSelectFiveTopView.setZhMissButtonShow();
		}else{
			isElevenSelectFive=false;
			elevenSelectFiveTopView.removeZhMissButton();
		}
	}

	/**
	 * 根据小球id判断是哪个选区
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId) {
		int nBallId = 0;
		for (int i = 0; i < areaNums.length; i++) {
			nBallId = iBallId;
			iBallId = iBallId - areaNums[i].areaNum;

			if (iBallId < 0) {
				if (is11_5DanTuo) {
					if (i == 0) {
						int isHighLight = areaNums[0].table.changeBallState(
								areaNums[0].chosenBallSum, nBallId);
						if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT
								&& areaNums[1].table.getOneBallStatue(nBallId) != 0) {
							areaNums[1].table.clearOnBallHighlight(nBallId);
							showBetInfo(getResources().getString(
									R.string.ssq_toast_danma_title));
						}

					} else if (i == 1) {
						int isHighLight = areaNums[1].table.changeBallState(
								areaNums[1].chosenBallSum, nBallId);
						if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT
								&& areaNums[0].table.getOneBallStatue(nBallId) != 0) {
							areaNums[0].table.clearOnBallHighlight(nBallId);
							showBetInfo(getResources().getString(
									R.string.ssq_toast_tuoma_title));
						}
					}
				} else {
					areaNums[i].table.changeBallState(
							areaNums[i].chosenBallSum, nBallId);
				}
				break;
			}

		}

	}

	/**
	 * 点击小球提示金额
	 * 
	 * @param areaNum
	 * @param iProgressBeishu
	 * @return
	 */
	public String textSumMoney(AreaNum areaNum[], int iProgressBeishu) {
		String textSum = "";
		int iZhuShu = getZhuShu();
		if (is11_5DanTuo) {
			int dan = areaNum[0].table.getHighlightBallNums();
			int tuo = areaNum[1].table.getHighlightBallNums();
			if (dan + tuo < num + 1) {
				int num2 = num + 1 - dan - tuo;
				if (dan == 0) {
					textSum = "至少选择1个胆码";
				} else {
					textSum = "至少还需要" + num2 + "个拖码";
				}
			} else if (tuo == 0) {
				textSum = "至少选择1个胆码";
			} else {
				textSum = "共" + iZhuShu + "注，共" + (iZhuShu * 2) + "元";

			}
		} else if (state.equals("PT_QZ2")) {// 求排序
			int oneNum = areaNum[0].table.getHighlightBallNums();
			int twoNum = areaNum[1].table.getHighlightBallNums();
			if (oneNum == 0) {
				textSum = "第一位还需要1个小球";
			} else if (twoNum == 0) {
				textSum = "第二位还需要1个小球";
			} else {
				textSum = "共" + iZhuShu + "注，共" + (iZhuShu * 2) + "元";
			}
		} else if (state.equals("PT_QZ3")) {
			if (isMove && itemViewArray.get(newPosition).isZHmiss) {
				int onClickNum = getClickNum();
				if (onClickNum == 0) {
					textSum = getResources().getString(
							R.string.please_choose_number);
				} else {
					textSum = "共" + onClickNum + "注，共" + (onClickNum * 2) + "元";
				}
			} else {
				int oneNum = areaNum[0].table.getHighlightBallNums();
				int twoNum = areaNum[1].table.getHighlightBallNums();
				int thirdNum = areaNum[2].table.getHighlightBallNums();
				if (oneNum == 0) {
					textSum = "第一位还需要1个小球";
				} else if (twoNum == 0) {
					textSum = "第二位还需要1个小球";
				} else if (thirdNum == 0) {
					textSum = "第三位还需要1个小球";
				} else {
					textSum = "共" + iZhuShu + "注，共" + (iZhuShu * 2) + "元";
				}
			}
		} else if (state.equals("PT_R5") || state.equals("PT_R7")
				|| state.equals("PT_R8")) {// 求组合
			if (isMove && itemViewArray.get(newPosition).isZHmiss) {
				int onClickNum = getClickNum();
				if (onClickNum == 0) {
					textSum = getResources().getString(
							R.string.please_choose_number);
				} else {
					textSum = "共" + onClickNum + "注，共" + (onClickNum * 2) + "元";
				}
			} else {
				int ballNums = areaNum[0].table.getHighlightBallNums();
				int oneNum = num - ballNums;
				if (oneNum > 0) {
					textSum = "还需要" + oneNum + "个小球";
				} else {
					textSum = "共" + iZhuShu + "注，共" + (iZhuShu * 2) + "元";
				}
			}

		} else {
			int ballNums = areaNum[0].table.getHighlightBallNums();
			int oneNum = num - ballNums;
			if (oneNum > 0) {
				textSum = "还需要" + oneNum + "个小球";
			} else {
				textSum = "共" + iZhuShu + "注，共" + (iZhuShu * 2) + "元";
			}
		}
		return textSum;

	};

	/**
	 * 获得胆拖投注的投注状态
	 * 
	 * @param iZhuShu
	 * @return
	 */
	private String getIsTouzhuStatus(int iZhuShu) {
		String isTouzhu = "";
		int tuoNum = 10;
		if (Constants.LOTNO_ten.equals(lotno)) {
			tuoNum = 19;
		}
		int dan = areaNums[0].table.getHighlightBallNums();
		int tuo = areaNums[1].table.getHighlightBallNums();
		if (dan + tuo < dannums[itemId] + 1 || dan < 1 || dan > dannums[itemId] || tuo < 2
				|| tuo > tuoNum) {
			if (state.equals("DT_R2") || state.equals("DT_ZU2")) {
				isTouzhu = "请选择:\"1个胆码；\n" + " 2~" + tuoNum + "个拖码；\n"
						+ " 胆码与拖码个数之和不小于" + (dannums[itemId] + 1) + "个";
			} else if(state.equals("DT_ZU3")){
				isTouzhu = "请选择:\n1~2个胆码；\n" + " 2~" + tuoNum
						+ "个拖码；\n" + " 胆码与拖码个数之和不小于" + (dannums[itemId] + 1) + "个";
			}else{
				isTouzhu = "请选择:\n1~" + (dannums[itemId] - 1) + "个胆码；\n" + " 2~" + tuoNum
						+ "个拖码；\n" + " 胆码与拖码个数之和不小于" + (dannums[itemId] + 1) + "个";
			}
		} else if (iZhuShu > MAX_ZHU) {
			isTouzhu = "false";
		} else {
			isTouzhu = "true";
		}
		return isTouzhu;
	}

	/**
	 * 判断是否满足投注条件
	 */
	public String isTouzhu() {
		String isTouzhu = "";
		int iZhuShu = getZhuShu();
		if (is11_5DanTuo) {
			isTouzhu = getIsTouzhuStatus(iZhuShu);
		} else if (state.equals("PT_QZ2")) {

			if (iZhuShu == 0) {
				isTouzhu = "请在第一位和第二位至少选择一个球，再进行投注！";
			} else if (iZhuShu > MAX_ZHU) {
				isTouzhu = "false";
			} else {
				isTouzhu = "true";
			}
		} else if (state.equals("PT_QZ3")) {
			if (isMove && itemViewArray.get(newPosition).isZHmiss) {
				int onClickNum = getClickNum();
				if (onClickNum == 0) {
					isTouzhu = "请至少选择一注！";
				} else {
					isTouzhu = "true";
				}
			} else {
				if (iZhuShu == 0) {
					isTouzhu = "请在第一位、第二位和第三位至少选择一个球，再进行投注！";
				} else if (iZhuShu > MAX_ZHU) {
					isTouzhu = "false";
				} else {
					isTouzhu = "true";
				}
			}
		} else if (state.equals("PT_R5") || state.equals("PT_R7")
				|| state.equals("PT_R8")) {
			if (isMove && itemViewArray.get(newPosition).isZHmiss) {
				int onClickNum = getClickNum();
				if (onClickNum == 0) {
					isTouzhu = "请至少选择一注！";
				} else {
					isTouzhu = "true";
				}
			} else {
				int ballNums = areaNums[0].table.getHighlightBallNums();
				int oneNum = num - ballNums;
				if (!checkBallNum(ballNums, num)) {
					return isTouzhu = this.showMessage;
				}
				if (oneNum > 0) {
					isTouzhu = "请再选择" + oneNum + "球，再进行投注！";
				} else if (iZhuShu > MAX_ZHU) {
					isTouzhu = "false";
				} else {
					isTouzhu = "true";
				}
			}
		} else {
			int ballNums = areaNums[0].table.getHighlightBallNums();
			int oneNum = num - ballNums;
			if (oneNum > 0) {
				isTouzhu = "请再选择" + oneNum + "球，再进行投注！";
			} else if (iZhuShu > MAX_ZHU) {
				isTouzhu = "false";
			} else {
				isTouzhu = "true";
			}
		}
		return isTouzhu;
	}
	
	/**
	 * 判断球数
	 * 
	 * @param ballNums
	 * @param num
	 * @return
	 */
	private boolean checkBallNum(int ballNums, int num) {
		if ("R8".equals(state) && Constants.LOTNO_GD_11_5.equals(lotno)) {
			if (ballNums > num) {
				showMessage = "只能选择" + num + "个球进行投注！";
				return false;
			}
		}
		return true;
	}

	/**
	 * 投注注码
	 * 
	 * @return
	 */
	public String getZhuma() {
		String zhuma = "";
		if (is11_5DanTuo) {
			zhuma = DlcDanTuoCode.zhuma(areaNums, state);
		} else {
			zhuma = DlcCode.zhuma(areaNums, state);
		}
		return zhuma;
	}

	@Override
	public String getZhuma(Balls ball) {
		String zhuma = "";
		zhuma = DlcRxBalls.getZhuma(ball, state);
		return zhuma;
	}


	/**
	 * 获得总注数
	 * 
	 * @return
	 */
	public int getZhuShu() {
		int zhushu = 0;
		if (isJiXuan) {
			zhushu = balls.size() * iProgressBeishu;
		}
		//如果是胆拖玩法
		else if (playMethodTag==2) {
			int dan = areaNums[0].table.getHighlightBallNums();
			int tuo = areaNums[1].table.getHighlightBallNums();
			zhushu = (int) getDTZhuShu(dan, tuo, iProgressBeishu);
		}
		//如果是普通投注
		else {
			if (state.equals("PT_QZ2")) {//普通前二组选
				zhushu = getzhushuQ2(areaNums[0].table.getHighlightStr(),
						areaNums[1].table.getHighlightStr()) * iProgressBeishu;
			} else if (state.equals("PT_QZ3")) {//普通前三组选
				zhushu = getzhushuQ3(areaNums[0].table.getHighlightStr(),
						areaNums[1].table.getHighlightStr(),
						areaNums[2].table.getHighlightStr())
						* iProgressBeishu;
			} else {
				int ballNums = areaNums[0].table.getHighlightBallNums();
				zhushu = (int) PublicMethod.zuhe(nums[itemId], ballNums)* iProgressBeishu;
			}
		}
		return zhushu;
	}

	/**
	 * 前二直选玩法注数计算方法
	 * @param wan
	 * @param qian
	 * @return
	 */
	public int getzhushuQ2(String[] wan, String[] qian) {
		int zhushu = 0;
		for (int i = 0; i < wan.length; i++) {
			for (int j = 0; j < qian.length; j++) {
				if (!wan[i].equals(qian[j])) {
					zhushu++;
				}
			}
		}
		return zhushu;
	}
	/**
	 * 前三直选玩法注数计算方法
	 * @param wan
	 * @param qian
	 * @param bai
	 * @return
	 */
	public int getzhushuQ3(String[] wan, String[] qian, String[] bai) {
		int zhushu = 0;
		for (int i = 0; i < wan.length; i++) {
			for (int j = 0; j < qian.length; j++) {
				if (!wan[i].equals(qian[j])) {
					for (int k = 0; k < bai.length; k++) {
						if (!bai[k].equals(qian[j]) && !bai[k].equals(wan[i])) {
							zhushu++;

						}
					}
				}
			}
		}
		return zhushu;
	}

	/**
	 * 复式玩法注数计算方法
	 * 
	 * @param int aRedBalls 红球个数
	 * 
	 * @return long 注数
	 */
	protected long getDTZhuShu(int dan, int tuo, int iProgressBeishu) {
		long ssqZhuShu = 0L;
		if (dan > 0 && tuo > 0) {
			ssqZhuShu += (PublicMethod.zuhe(num - dan, tuo) * iProgressBeishu);
		}
		return ssqZhuShu;
	}

	/**
	 * 添加到号码篮
	 */
	public void getCodeInfo(AddView addView) {
		int zhuShu = getZhuShu();
		CodeInfo codeInfo = addView.initCodeInfo(getAmt(zhuShu), zhuShu);
		setLotoNoAndType(codeInfo);
		code.setZHmiss(false);
		codeInfo.setTouZhuCode(getZhuma());
		for (AreaNum areaNum : areaNums) {
			int[] codes = areaNum.table.getHighlightBallNOs();
			hightballs = codes.length;
			String codeStr = "";
			for (int i = 0; i < codes.length; i++) {
				codeStr += PublicMethod.isTen(codes[i]);
				if (i != codes.length - 1) {
					codeStr += ",";
				}
			}
			codeInfo.addAreaCode(codeStr, areaNum.textColor);
		}
		addView.addCodeInfo(codeInfo);
	}

	/**
	 * 组合遗漏添加到选号篮
	 * 
	 * @param addView
	 */
	public void getZCodeInfo(AddView addView) {
		List<MyButton> missBtnList = itemViewArray.get(newPosition).missBtnList;
		for (int i = 0; i < missBtnList.size(); i++) {
			MyButton myBtn = missBtnList.get(i);
			if (myBtn.isOnClick()) {
				int zhuShu = 1;
				CodeInfo codeInfo = addView
						.initCodeInfo(getAmt(zhuShu), zhuShu);
				String codeStr = myBtn.getBtnText();
				code.setZHmiss(true);
				code.setIsZHcode(codeStr);
				codeInfo.setTouZhuCode(getZhuma());
				codeInfo.addAreaCode(codeStr, Color.RED);
				addView.addCodeInfo(codeInfo);
			}
		}
	}

	/**
	 * 投注联网
	 */
	public void touzhuNet() {
		int zhuShu = getZhuShu();
		if (isJiXuan) {
			betAndGift.setSellway("1");
		} else {
			betAndGift.setSellway("0");
		}// 1代表机选 0代表自选
		betAndGift.setLotno(lotno);
		betAndGift.setBet_code(getZhuma());
		betAndGift.setAmount("" + zhuShu * 200);
		betAndGift.setBatchcode(batchCode);

	}

	protected void onStart() {
		super.onStart();
		if (Constants.isDebug)
			PublicMethod.outLog(this.getClass().getSimpleName(), "onStart()");

	}

	protected void onResume() {
		super.onResume();
		if (Constants.isDebug)
			PublicMethod.outLog(this.getClass().getSimpleName(), "onResume()");
		controller = Controller.getInstance(Dlc.this);
		controller.getIssueJSONObject(handler, lotno); 
		setLotno();
	}
    protected void setIssueJSONObject(JSONObject obj) {
		if (obj != null && !isFirst) {
			try {
				lesstime = Integer.valueOf(CheckUtil.isNull(obj
						.getString("time_remaining")));
				batchCode = obj.getString("batchcode");
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		isFirst = false;
    }
	protected void onStop() {
		super.onStop();
		if (Constants.isDebug)
			PublicMethod.outLog(this.getClass().getSimpleName(), "onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (Constants.isDebug)
			PublicMethod.outLog(this.getClass().getSimpleName(), "onDestroy()");
		isRun = false;
		// batchCode = ""; //move to onCreate by yejc 20130708
		lotteryService.removeLotteryListeners(Dlc.this);
	}

	void setLotoNoAndType(CodeInfo codeInfo) {
		codeInfo.setLotoNo(lotno);
		if (radioId == 1) {
			codeInfo.setTouZhuType("dantuo");
		} else {
			codeInfo.setTouZhuType("zhixuan");
		}
	}

	public void setNoticeLotno(int Lotno) {
		NoticeActivityGroup.LOTNO = Lotno;
	}

	class DlcHandler extends MyHandler {

		public DlcHandler(HandlerMsg msg) {
			super(msg);
		}

		public void handleMessage(Message msg) {
			
			switch (msg.what) {
				case GET_PRIZEINFO_ERROR:
					CharSequence msgString = (CharSequence) msg.getData().get("msg");
					Toast.makeText(Dlc.this,
							"历史获奖信息获取失败..." + msgString, Toast.LENGTH_LONG).show();
					break;
	
				case GET_PRIZEINFO_SUCCESS:
					String data=(String) msg.getData().get("result");
					List<PrizeInfoBean> prizeInfosList=JsonUtils.getList(data, PrizeInfoBean.class);
					historyLotteryAdapter.setLotteryPrizeList(prizeInfosList);
					elevenSelectFiveHistoryLotteryView.setAdapter(historyLotteryAdapter);
					if(progressdialog!=null && progressdialog.isShowing()){
						progressdialog.dismiss();
					}	
					break;
				default:
					if (controller != null) {
						JSONObject obj = controller.getRtnJSONObject();
						setIssueJSONObject(obj);
					}
					break;
			}
		}
	}

	@Override
	public void updateLatestLotteryList(String lotno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNoticePrizeInfo(String lotno, PrizeInfoList prizeInfoList) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (this.lotno.equals(lotno)) {
			Message messages = handler.obtainMessage();
			ReturnBean returnBtn = prizeInfoList.getReturnBean();
			Bundle bundle = new Bundle();
			if (!Constants.SUCCESS_CODE.equals(returnBtn.getError_code())) {
				bundle.putString("msg", returnBtn.getMessage());
				messages.what = GET_PRIZEINFO_ERROR;
			} else {
				bundle.putString("result", returnBtn.getResult());
				messages.setData(bundle);
				messages.what = GET_PRIZEINFO_SUCCESS;
			}
			messages.sendToTarget();
		}
	}

}
