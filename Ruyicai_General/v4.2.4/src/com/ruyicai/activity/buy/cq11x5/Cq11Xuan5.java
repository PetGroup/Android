package com.ruyicai.activity.buy.cq11x5;

import java.util.List;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.inject.Inject;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.jlk3.JiLinK3;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.adapter.ElevenSelectorFiveHistoryLotteryAdapter;
import com.ruyicai.adapter.JiLinK3HistoryLotteryAdapter;
import com.ruyicai.code.cq11xuan5.Cq11xuan5Code;
import com.ruyicai.code.cq11xuan5.Cq11xuan5DanTuoCode;
import com.ruyicai.component.custom.jc.button.MyButton;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView.ElevenSelectFiveTopViewClickListener;
import com.ruyicai.constant.Constants;
import com.ruyicai.controller.listerner.LotteryListener;
import com.ruyicai.controller.service.LotteryService;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.jixuan.DlcRxBalls;
import com.ruyicai.json.miss.CQ11X5MissJson;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.json.miss.SscZMissJson;
import com.ruyicai.model.HistoryLotteryBean;
import com.ruyicai.model.PrizeInfoBean;
import com.ruyicai.model.PrizeInfoList;
import com.ruyicai.model.ReturnBean;
import com.ruyicai.net.newtransaction.GetLotNohighFrequency;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.CheckUtil;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.json.JsonUtils;

public class Cq11Xuan5 extends ZixuanAndJiXuan implements LotteryListener {
	/**选号按钮图片*/
	protected int BallResId[] = { R.drawable.cq_11_5_ball_normal, R.drawable.cq_11_5_ball_select };
	/**玩法标识:1普通，2胆拖*/
	private int playMethodTag=1;
	private static final String TITLE="重庆11选5";
	protected String pt_types[] = { "PT_R2", "PT_R3", "PT_R4", "PT_R5", "PT_R6", "PT_R7","PT_R8",
			"PT_QZ1", "PT_QZ2", "PT_QZ3", "PT_ZU2", "PT_ZU3" };// 普通类型
	protected String dt_types[] = { "DT_R2", "DT_R3", "DT_R4", "DT_R5", "DT_R6", "DT_R7", "DT_R8",
			"DT_ZU2", "DT_ZU3" };// 胆拖类型
	protected int nums[] = { 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 2, 3 };// 单式机选个数
	protected int dannums[] = { 2, 3, 4, 5, 6, 7, 8, 2, 3 };// 单式机选个数
	private int mins[] = { 3, 4, 5, 6, 7, 8, 9, 3, 4 };// 选区最小小球数
	public static String state;// 当前类型
	int lesstime;// 剩余时间
	public static String batchCode;// 期号
	private boolean isRun = true;
    private String lotNo=Constants.LOTNO_CQ_ELVEN_FIVE;
	private String showMessage = "";
	private int itemId=3;
	private int checkedId;
	public AddView addView = new AddView(this);
    private ProgressDialog progressdialog;
	
	public boolean isJiXuan = false;
	private boolean isZhMiss=false;
	public int num = 1;// 当前单式机选个数
	@Inject private LotteryService lotteryService;
	private static final int GET_PRIZEINFO_ERROR = 0;
	private static final int GET_PRIZEINFO_SUCCESS = 3;
	private ElevenSelectorFiveHistoryLotteryAdapter historyLotteryAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAddView(addView);
		lotno = Constants.LOTNO_CQ_ELVEN_FIVE;
		setContentView(R.layout.buy_cq_eleven_five_main);
		lotteryService.addLotteryListeners(Cq11Xuan5.this);
		highttype = "CQ_ELEVEN_FIVE";
		state = "PT_R5";
		initShow();
		setIssue(lotno);
		lotnoStr = lotno;
		action();
		latestLotteryList.setVisibility(View.GONE);
		historyLotteryAdapter=new ElevenSelectorFiveHistoryLotteryAdapter(Cq11Xuan5.this);
	}
	
	private Handler handler = new Handler() {
		
		public void handleMessage(Message msg) {
			CharSequence msgString = (CharSequence) msg.getData().get("msg");
			switch (msg.what) {
				case GET_PRIZEINFO_ERROR:
					Toast.makeText(Cq11Xuan5.this,
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
			}

		}
	};
	@Override
	protected void onResume() {
		super.onResume();

		this.lotno = Constants.LOTNO_CQ_ELVEN_FIVE;
		lotnoStr = lotno;
		if (playMethodTag == 2) {
			baseSensor.stopAction();
		}
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		this.checkedId=checkedId;
		onCheckAction(checkedId);
	}
	
	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		int iZhuShu = getZhuShu();
		return "您已选择了" + iZhuShu + "注，共" + iZhuShu * 2 + "元";
	}
	
	/**
	 * 设置投注金额提示
	 */
	public void showEditText(){
		if(isMove){
			editZhuma.setText("");
			showEditTitle(NULL);
		}else{
			editZhuma.setText(textSumMoney(areaNums, iProgressBeishu));
			showEditTitle(NULL);
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
				if (playMethodTag == 2) {
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

	@Override
	public String isTouzhu() {
		String isTouzhu = "";
		int iZhuShu = getZhuShu();
		if (playMethodTag==2) {
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
	 * 获得胆拖投注的投注状态
	 * 
	 * @param iZhuShu
	 * @return
	 */
	private String getIsTouzhuStatus(int iZhuShu) {
		String isTouzhu = "";
		int tuoNum = 10;
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
	 * 判断球数
	 * 
	 * @param ballNums
	 * @param num
	 * @return
	 */
	private boolean checkBallNum(int ballNums, int num) {
		if ("PT_R8".equals(state) && Constants.LOTNO_GD_11_5.equals(lotno)) {
			if (ballNums > num) {
				showMessage = "只能选择" + num + "个球进行投注！";
				return false;
			}
		}
		return true;
	}


	@Override
	public int getZhuShu() {
		int zhushu = 0;
		if (isJiXuan) {
			zhushu = balls.size() * iProgressBeishu;
		}
		//如果是胆拖玩法
		else if (playMethodTag==2) {
			int dan = areaNums[0].table.getHighlightBallNums();
			int tuo = areaNums[1].table.getHighlightBallNums();
			if((dan+tuo)>=mins[itemId]){
				zhushu = (int) getDTZhuShu(dan, tuo, iProgressBeishu);
			}else{
				zhushu = 0;
			}
		}
		//如果是普通投注
		else {
			if(isMove){
				zhushu= getClickNum();
			}else{
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
			ssqZhuShu += (PublicMethod.zuhe(dannums[itemId] - dan, tuo) * iProgressBeishu);
		}
		return ssqZhuShu;
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

	@Override
	public String getZhuma() {
		String zhuma = "";
		if (playMethodTag == 2) {
			zhuma = Cq11xuan5DanTuoCode.zhuma(areaNums, state);
		} else {
			zhuma = Cq11xuan5Code.zhuma(areaNums, state);
		}
		return zhuma;
	}

	@Override
	public String getZhuma(Balls ball) {
		String zhuma = "";
		zhuma = DlcRxBalls.getZhuma(ball, state);
		return zhuma;
	}

	@Override
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

	@Override
	public void onCheckAction(int checkedId) {
		switch (checkedId) {
		case 0:
			elevenSelectFiveTopView.showMissCheckbox();
			isZhMiss=false;
			if(playMethodTag==1){
				createViewPT(checkedId);
			}else if (playMethodTag==2) {
				createViewDT(checkedId);
			}

			if (!state.equals("PT_QZ2") && !state.equals("PT_QZ3")
					&& !state.equals("PT_QZ1") && !state.equals("PT_ZU2")
					&& !state.equals("PT_ZU2") && !state.equals("PT_ZU3")) {
				lotteryService.getNoticePrizeInfoList(Constants.LOTNO_CQ_ELVEN_FIVE);
			}
			showEditText();
			break;
		default:
			break;
		}
	}
	
	private void initShow() {
		elevenSelectFiveTopView=(ElevenSelectFiveTopView) findViewById(R.id.elevenSelectFiveTopView);
		elevenSelectFiveTopView.setLotteryInfoBackGround(R.drawable.nmk3_head_bg);
		elevenSelectFiveTopView.setQueryMessage(Constants.LOTNO_CQ_ELVEN_FIVE, NoticeActivityGroup.ID_SUB_CQ11X5_LISTVIEW);
		elevenSelectFiveTopView.addElevenSelectFiveTopViewClickListener(new ElevenSelectFiveTopViewClickListener() {
			
			@Override
			public void ElevenSelectFiveFresh() {
				showDialog();
				initLatestLotteryList();
				lotteryService.getNoticePrizeInfoList(Constants.LOTNO_CQ_ELVEN_FIVE);
			}

			@Override
			public void ChooseIsToShowMissMessage(boolean isChecked) {
				controlShowMiss(isChecked);
			}

			@Override
			public void TouchDTPlayMethod(int position) {
				intitZhMissBtn();
				playMethodTag=2;
				itemId=position;
				state = dt_types[position];
				action();
			}

			@Override
			public void ElevenSelectFiveOmission() {
				if(isZhMiss){
					elevenSelectFiveTopView.showMissCheckbox();
					intitZhMissBtn();
					baseSensor.startAction();
				}else{
					elevenSelectFiveTopView.removeMissCheckbox();
					baseSensor.stopAction();
					isZhMiss=true;
					isMove = true;
					showEditText();
					isElevenSelectFive=true;
					lotteryNumberLayout.setVisibility(View.GONE);
					elevenSelectFiveZhMissLayout.setVisibility(View.VISIBLE);
					elevenSelectFiveTopView.setOmissionBtnBackGround(R.drawable.eleven_select_five_main_touzhu);
				}
			}

			@Override
			public void TouchPTPlayMethod(int position) {
				intitZhMissBtn();
				playMethodTag=1;
				itemId=position;
				state = pt_types[position];
				action();
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
	 * 赋值给当前期
	 * 
	 * @param type彩种编号
	 */
	public void setIssue(final String lotno) {
		final Handler sscHandler = new Handler();
		elevenSelectFiveTopView.setElevenSelectFiveEndTime("获取中...");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
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
												+ batchCode.substring(8)
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

	private boolean isEnd(int time) {
		if (time > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 转入下一期对话框
	 */
	private void nextIssue() {
		new AlertDialog.Builder(Cq11Xuan5.this)
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
								Cq11Xuan5.this.finish();
							}
						}).create().show();
	}
	int[] cqArea={5,6};
	
	/**
	 * 创建普通界面
	 */
	private void createViewPT(int id){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		baseSensor.startAction();
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
		setBottomView();
	}
	/**
	 * 创建胆拖界面
	 *
	 */
	int[] dtNum={1,2,3,4,5,6,7,1,2};// 胆拖选区最大小球数
	private static final String dtTPrompt="我认为可能出的号码  选2-10个";//拖码投注提示
	//胆码投注提示
	private String dtDPrompt(int a){
		String str="";
		if(state.equals("DT_R2")
				||state.equals("DT_ZU2")){
			str="我认为必出的号码   选1个";
		}else if (state.equals("DT_ZU3")) {
			str="我认为必出的号码   至少选1个，最多2个";
		}else {
			str="我认为必出的号码   至少选1个，最多"+(itemId+1)+"个";
		}
		return str;
	}

	private void createViewDT(int id) {
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

		setBottomView();
	}

	/**
	 * 设置底部显示
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
	/**
	 * 事件处理
	 */
	public void action() {
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
		if(state.equals("PT_QZ1")){
			isShowZhMissBtn=false;
			sellWay = MissConstant.CQ11X5_PT_Q1_Z;
			highttype = "CQ11X5_PT_QZ1";
		}else if(state.equals("PT_QZ2")){
			isShowZhMissBtn=false;
			sellWay = MissConstant.CQ11X5_PT_Q2_Z;
			highttype = "CQ11X5_PT_QZ2";
		}
		else if (state.equals("PT_ZU2") || state.equals("DT_ZU2")) {
			isShowZhMissBtn=false;
			sellWay = MissConstant.CQ11X5_PT_Q2_ZU;
		} else if (state.equals("PT_ZU3") || state.equals("DT_ZU3")) {
			isShowZhMissBtn=false;
			sellWay = MissConstant.CQ11X5_PT_Q3_ZU;
		}
		else if (state.equals("PT_R5")) {
			isMissNet(new SscZMissJson(), MissConstant.CQ11X5_PT_R5, true);// 获取遗漏值
			sellWay = MissConstant.CQ11X5_PT_RX;
		} else if (state.equals("PT_R7")) {
			isMissNet(new SscZMissJson(), MissConstant.CQ11X5_PT_R7, true);// 获取遗漏值
			sellWay = MissConstant.CQ11X5_PT_RX;
		} else if (state.equals("PT_R8")) {
			isMissNet(new SscZMissJson(), MissConstant.CQ11X5_PT_R8, true);// 获取遗漏值
			sellWay = MissConstant.CQ11X5_PT_RX;
		} else if (state.equals("PT_QZ3")) {
			sellWay = MissConstant.CQ11X5_PT_Q3_Z;
			isMissNet(new SscZMissJson(), MissConstant.CQ11X5_MV_Q3_ZH, true);// 获取遗漏值
		}  
		else {
			isShowZhMissBtn=false;
			sellWay = MissConstant.CQ11X5_PT_RX;
		}
		showZhMissBtn(isShowZhMissBtn);
		isMissNet(new CQ11X5MissJson(), sellWay, false);// 获取遗漏值
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
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		isRun = false;
		lotteryService.removeLotteryListeners(Cq11Xuan5.this);
	}
	
	void setLotoNoAndType(CodeInfo codeInfo) {
		codeInfo.setLotoNo(Constants.LOTNO_CQ_ELVEN_FIVE);
		if (playMethodTag == 1) {
			codeInfo.setTouZhuType("zhixuan");
		} else {
			codeInfo.setTouZhuType("dantuo");
		}
	}
	private void showDialog() {
		if (progressdialog == null) {
			progressdialog = new ProgressDialog(this);
		}
		progressdialog.show();
		progressdialog.setCancelable(true);
		View dialogView = PublicMethod.getView(this);
		progressdialog.getWindow().setContentView(dialogView);
	}

	@Override
	public void updateLatestLotteryList(String lotno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNoticePrizeInfo(String lotno, PrizeInfoList prizeInfoList) {
		if (Constants.LOTNO_CQ_ELVEN_FIVE.equals(lotno)) {
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
