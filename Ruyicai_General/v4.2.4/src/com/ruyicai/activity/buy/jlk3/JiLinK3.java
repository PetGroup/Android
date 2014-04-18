package com.ruyicai.activity.buy.jlk3;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.google.inject.Inject;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.cq11x5.Cq11Xuan5;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.nmk3.Nmk3Activity;
import com.ruyicai.activity.buy.nmk3.Nmk3HeZhiActivity;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.adapter.JiLinK3HistoryLotteryAdapter;
import com.ruyicai.component.DiceAnimation;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView.ElevenSelectFiveTopViewClickListener;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.controller.listerner.AnimationListener;
import com.ruyicai.controller.listerner.LotteryListener;
import com.ruyicai.controller.service.AnimationService;
import com.ruyicai.controller.service.HighZhuMaCenterService;
import com.ruyicai.controller.service.LotteryService;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.json.miss.JiLinK3MissJson;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.model.HistoryLotteryBean;
import com.ruyicai.model.PrizeInfoBean;
import com.ruyicai.model.PrizeInfoList;
import com.ruyicai.model.ReturnBean;
import com.ruyicai.net.newtransaction.GetLotNohighFrequency;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.CheckUtil;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.ruyicai.util.json.JsonUtils;

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
import android.widget.CompoundButton.OnCheckedChangeListener;


/**
 * 吉林新快三
 *
 */

public class JiLinK3 extends ZixuanAndJiXuan implements AnimationListener, LotteryListener{
	
	private ElevenSelectFiveTopView newNmk3TopView;
	private String[] ptPlayMethod={"和值","三同号","二同号单选","三不同","二不同","二同号复选"};
	private String[] dtPlayMethod={"三不同","二不同"};
	private String[] ptPlayMethodDescribe={"奖金9-240元","奖金9-240元","奖金80元","奖金40元","奖金8元","奖金15元"};
//	private String[] dtPlayMethodDescribe={"奖金10-40元","奖金8元"};
	private int[] itemClickPicture={R.drawable.new_nmk3_playmethod_normal,R.drawable.new_nmk3_playmethod_click};
	private int noticeLotNo=NoticeActivityGroup.ID_SUB_JLK3_LISTVIEW;
	/**玩法标识:1普通，2胆拖*/
	private int playMethodTag=1;
	public AddView addView = new AddView(this);
	private int[] cqArea={6};
	protected int BallResId[] = { R.drawable.new_nmk3_num_status_normal, R.drawable.new_nmk3_num_status_click };
	protected int nums[] = { 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 2, 3 };// 单式机选个数
	protected int dannums[] = { 4, 3 };
	private int itemId=0;
	private String[][] clickBallText = { { "1", "2", "3" , "4", "5", "6"} };
	private String pt_types[] = { "PT_HZ", "PT_3T", "PT_2TD", "PT_3BT", "PT_2BT", "PT_2TF" };// 普通类型
	private String dt_types[] = { "DT_3BT", "DT_2BT"};// 胆拖类型
	private String state;// 当前类型
	private int threeDiffZhuShu = 0;
	private int threeLinkZhuShu = 0;
	private int threeSameBallZhuShu;
	private int threeSameTongBallZhuShu;
	public String batchCode;
	int lesstime;// 剩余时间
	private boolean isRun = true;
	@Inject private AnimationService  animationService;
	@Inject private HighZhuMaCenterService computingCenterService;
	@Inject private LotteryService lotteryService;
	private JiLinK3HistoryLotteryAdapter historyLotteryAdapter;
	private static final int GET_PRIZEINFO_ERROR = 0;
	private static final int GET_PRIZEINFO_SUCCESS = 3;
	private ProgressDialog progressdialog;
	private boolean isJixuan = true;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAddView(addView);
		lotnoStr=Constants.LOTNO_JLK3;
		setContentView(R.layout.activity_new_faster_three_main);
		animationService.addAnimationListeners(JiLinK3.this);
		lotteryService.addLotteryListeners(JiLinK3.this);
		lotno = Constants.LOTNO_JLK3;
		state = "PT_HZ";
		initView();
		action();
		setIssue(lotno);
		historyLotteryAdapter=new JiLinK3HistoryLotteryAdapter(JiLinK3.this);
		RWSharedPreferences shellRW = new RWSharedPreferences(this, "addInfo");
		isJixuan = shellRW.getBooleanValue(ShellRWConstants.ISJIXUAN, true);
	}

	/*
	 * 设置投注信息类的彩种编号和投注类型
	 */
	void setLotoNoAndType(CodeInfo codeInfo) {
		codeInfo.setLotoNo(Constants.LOTNO_JLK3);
		if(state.equals("PT_HZ")){
			codeInfo.setTouZhuType("hezhi");
		}else if(state.equals("PT_3T")){
			codeInfo.setTouZhuType("threesame");
		}else if(state.equals("PT_2TD")){
			codeInfo.setTouZhuType("twosame_dan");
		} else if (state.equals("PT_3BT")) {
			codeInfo.setTouZhuType("different_three");
		} else if (state.equals("PT_2BT")) {
			codeInfo.setTouZhuType("different_two");
		} else if (state.equals("PT_2TF")) {
			codeInfo.setTouZhuType("twosame_fu");
		} else if (state.equals("DT_3BT")) {
			codeInfo.setTouZhuType("dantuo_different_three");
		} else if (state.equals("DT_2BT")) {
			codeInfo.setTouZhuType("dantuo_different_two");
		}
		
	}
	
	void setLotoNoAndType2(CodeInfo codeInfo) {
		codeInfo.setLotoNo(Constants.LOTNO_JLK3);
		if(state.equals("PT_3T")){
			codeInfo.setTouZhuType("threesame_tong");
		} else if (state.equals("PT_3BT")) {
			codeInfo.setTouZhuType("threelink");
		}
	}
	
	private void action(){
		missView.clear();
		childtype = new String[] { "直选" };
		init();
		childtypes.setVisibility(View.GONE);
	}
	
	private void initView(){
		newNmk3TopView=(ElevenSelectFiveTopView) findViewById(R.id.newNmk3TopView);
		newNmk3TopView.setPtPlayMessage(getResources().getStringArray(R.array.new_nmk3_choose_pt_type));
		newNmk3TopView.setDtPlayMessage(getResources().getStringArray(R.array.new_nmk3_choose_dt_type));
		newNmk3TopView.isNewNmkThree(true);
		newNmk3TopView.setTopViewBackGround(R.drawable.new_nmk3_top);
		newNmk3TopView.setTopViewTitleBackGround(R.drawable.new_nmk3_top_title_click);
		newNmk3TopView.setPuTongPlayMessage(ptPlayMethod);
		newNmk3TopView.setDanTuoPlayMessage(dtPlayMethod);
		newNmk3TopView.removeTopViewTitleBackGround();
		newNmk3TopView.setPoupWindowItemClickPicture(itemClickPicture);
		newNmk3TopView.setPtPlayMethodDescribeList(ptPlayMethodDescribe);
//		newNmk3TopView.setDtPlayMethodDescribeList(dtPlayMethodDescribe);
		newNmk3TopView.setTextColor(this.getResources().getColor(R.color.white));
		newNmk3TopView.setQueryMessage(lotno, noticeLotNo);
		newNmk3TopView.setZhMissBtnBackGround(R.drawable.new_jilink3_top_btn);
		newNmk3TopView.setZhMissBtnText("摇一摇机选");
		newNmk3TopView.setZouShiBtnBackGround(R.drawable.new_jilink3_top_btn);
		newNmk3TopView.setZouShiBtnText("走势图");
		newNmk3TopView.setLotteryMessageTextColor(this.getResources().getColor(R.color.white));
		
		newNmk3TopView.addElevenSelectFiveTopViewClickListener(new ElevenSelectFiveTopViewClickListener() {
			
			@Override
			public void TouchPTPlayMethod(int position) {
				playMethodTag=1;
				state = pt_types[position];
				itemId=position;
				action();
			}
			
			@Override
			public void TouchDTPlayMethod(int position) {
				playMethodTag=2;
				state = dt_types[position];
				itemId=position;
				action();
			}
			
			@Override
			public void ElevenSelectFiveOmission() {
				if(isJixuan){
					baseSensor.action();
				}
			}
			
			@Override
			public void ElevenSelectFiveFresh() {
				progressdialog=PublicMethod.creageProgressDialog(JiLinK3.this);
				lotteryService.getNoticePrizeInfoList(Constants.LOTNO_JLK3);
			}
			
			@Override
			public void ChooseIsToShowMissMessage(boolean isChecked) {
				
			}
		});
	}

	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		int zhuShu = getZhuShu();
		return "您已选择了" + zhuShu + "注，共" + zhuShu * 2 + "元";
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		radioId = checkedId;
		onCheckAction(checkedId);
	}

	@Override
	public String isTouzhu() {
		if(state.equals("DT_3BT")){
			 if (getZhuShu() > 1) {
				 return "true";
			 } else {
				 return String.format(getResources().getString(R.string.buy_nmk3_dan_tuo_message), "1~2", "4");
			 }
		}else if(state.equals("DT_2BT")){
			 if (getZhuShu() > 1) {
				 return "true";
			 } else {
				 return String.format(getResources().getString(R.string.buy_nmk3_dan_tuo_message), "1", "3");
			 }
		}else{
			if (getZhuShu() == 0) {
				return "请至少选择一注";
			} else if (getZhuShu() > 10000) {
				return "false";
			} else {
				return "true";
			}
		}
	}

	@Override
	public int getZhuShu() {
		if(state.equals("PT_3BT")){
			// 获取三不同号单选择的小球个数
			int threeDiffBallNums = areaNums[0].table.getHighlightBallNums();
			// 获取三连号通选选择小球的个数
			int threeLinkBallNums = areaNums[1].table.getHighlightBallNums();
			threeDiffZhuShu = 0;
			// 计算三不同号的注数
			if (threeDiffBallNums >= 3) {
				threeDiffZhuShu = computingCenterService.zuHe(threeDiffBallNums, 3);
			}
			threeLinkZhuShu = 0;
			// 计算三连号通选的注数
			if (threeLinkBallNums > 0) {
				threeLinkZhuShu = 1;
			}
			// 返回注数总和
			return threeDiffZhuShu + threeLinkZhuShu;
		}else if(state.equals("PT_2BT")){
			int num = areaNums[0].table.getHighlightBallNums();
			int zhuShu = 0;
			if (num >= 2) {
				zhuShu = computingCenterService.zuHe(num, 2);
			}
			return zhuShu;
		}else if(state.equals("PT_2TD")){
			int sameNum = areaNums[0].table.getHighlightBallNums()/2;
			int diffNum = areaNums[1].table.getHighlightBallNums();
			return sameNum * diffNum;
		}else if(state.equals("PT_2TF")){
			return areaNums[0].table.getHighlightBallNums()/2;
		}else if(state.equals("PT_3T")){
			//获取三同号注数
			threeSameBallZhuShu = areaNums[0].table.getHighlightBallNums();
			//获取三同号通选注数
			threeSameTongBallZhuShu = areaNums[1].table.getHighlightBallNums();
			return threeSameBallZhuShu + threeSameTongBallZhuShu;
		}else if(state.equals("DT_3BT")){
			int danNum = computingCenterService.getHighlightBallNums(areaNums[0]);
			int tuoNum = computingCenterService.getHighlightBallNums(areaNums[1]);
			if (danNum == 0 || tuoNum == 0) {
				return 0;
			} else {
				if((danNum+tuoNum)>=4){
					if (danNum == 1) {
						return computingCenterService.zuHe(tuoNum, 2);
					} else if (danNum == 2) {
						return tuoNum;
					}
				}else{
					return 0;
				}
				return 0;
				
			}
		}else if(state.equals("DT_2BT")){
			int danNum = computingCenterService.getHighlightBallNums(areaNums[0]);
			int tuoNum = computingCenterService.getHighlightBallNums(areaNums[1]);
			if (danNum == 0 || tuoNum == 0) {
				return 0;
			} else {
				if((danNum+tuoNum)>=3){
					return computingCenterService.zuHe(tuoNum, 1);
				}else{
					return 0;
				}
			}
		}else{
			return areaNums[0].table.getHighlightBallNums();
		}
	}
	
	int getThreeLinkZhuShu() {
		if (state.equals("PT_3T")) {
			return threeSameTongBallZhuShu;
		} else {
			return threeLinkZhuShu;
		}
	}

	int getThreeDiffZhuShu() {
		if(state.equals("PT_3T")){
			return threeSameBallZhuShu;
		} else  {
			return threeDiffZhuShu;
		}
	}

	@Override
	public String getZhuma() {
		// 拼接投注的注码格式，用户投注与后台使用
		String zhuMa = "";

		// 获取注码的各个部分
		String playMethodPart = getPlayMethodPart();
		String mutiplePart = computingCenterService.getMutiplePart();
		String numberNumsPart = getNumberNumsPart();
		String numbersPart = getNumbersPart();
		String endFlagPart = "^";

		if(state.equals("PT_3T")) {
			if (getThreeSameBallZhuShu() > 1) {
				zhuMa = playMethodPart + mutiplePart + numberNumsPart + numbersPart
						+ endFlagPart;
			} else {
				zhuMa = playMethodPart + mutiplePart + numbersPart + endFlagPart;
			}
		} else if (state.equals("PT_2TD")) {
				zhuMa = playMethodPart + mutiplePart + numbersPart + endFlagPart;
		} else if (state.equals("PT_2TF")) {
				zhuMa = playMethodPart + mutiplePart + numberNumsPart + numbersPart
						+ endFlagPart;
		} else if (state.equals("PT_3BT")) {
			if (radioId == 0 && getThreeDiffZhuShu() == 1) {
				zhuMa = playMethodPart + mutiplePart + numbersPart + endFlagPart;
			} else {
				zhuMa = playMethodPart + mutiplePart + numberNumsPart + numbersPart
						+ endFlagPart;
			}
		}  else if (state.equals("DT_3BT")) {
			numbersPart = numbersPart + computingCenterService.getDanNumbersPart(areaNums[1]);
			zhuMa = playMethodPart + mutiplePart + numberNumsPart + numbersPart
					+ endFlagPart;
		} else if (state.equals("DT_2BT")) {
			numbersPart = numbersPart + computingCenterService.getDanNumbersPart(areaNums[1]);
			zhuMa = playMethodPart + mutiplePart + numberNumsPart + numbersPart
					+ endFlagPart;
		} else {
			zhuMa = playMethodPart + mutiplePart + numberNumsPart + numbersPart
					+ endFlagPart;
		}

		return zhuMa;
	}
	
	public int getThreeSameBallZhuShu(){
		return areaNums[0].table.getHighlightBallNums();
	}
	
	/**
	 * 获取号码部分
	 * 
	 * @return 号码部分
	 */
	private String getNumbersPart() {
		String numbersParts="";
		StringBuffer numbersPart = new StringBuffer();
		if (state.equals("PT_HZ")) {
			// 获取高亮小球号码数组
			int[] numbers = areaNums[0].table.getHighlightBallNOs();
			// 循环号码数组，并拼接
			for (int num_i = 0; num_i < numbers.length; num_i++) {
				numbersPart.append(PublicMethod.getZhuMa(numbers[num_i]));
			}
			numbersParts = numbersPart.toString();
		} else if (state.equals("PT_3T")) {
			// 获取高亮小球号码数组
			int[] numbers = areaNums[0].table.getHighlightBallNOs();
			// 如果是单选复试
			if (numbers.length > 1) {
				for (int number_i = 0; number_i < numbers.length; number_i++) {
					String numberPart = PublicMethod
							.getZhuMa(numbers[number_i] % 10);
					numbersPart.append(numberPart);
				}
			}
			// 如果是单选单式
			else if (numbers.length == 1) {
				String numberString = String.valueOf(numbers[0]);
				for (int number_i = 0; number_i < numberString.length(); number_i++) {
					numbersPart.append(PublicMethod.getZhuMa(Integer
							.valueOf(numberString.substring(number_i,
									number_i + 1))));
				}
			}
			numbersParts = numbersPart.toString();
		} else if (state.equals("PT_2TD") || state.equals("PT_2TF")) {
			if(state.equals("PT_2TD")){
				radioId=1;
			}
			// 如果是复选
			if (radioId == 0) {
				// 获取高亮小球号码数组
				int[] numbers = areaNums[0].table.getHighlightBallNOs();
				List list =getNumbersPartList(numbers,true);
				for (int number_i = 0; number_i < list.size(); number_i++) {
					String numberPart = PublicMethod.getZhuMa(Integer
							.valueOf(list.get(number_i).toString()) % 10);
					numbersPart.append(numberPart);
				}
			}
			// 如果是单选
			else if (radioId == 1) {
				// 如果是复式
				if (getZhuShu() > 1) {
					for (int aear_i = 0; aear_i < areaNums.length; aear_i++) {
						int[] aearnumbers = areaNums[aear_i].table
								.getHighlightBallNOs();
						List list = new ArrayList();
						if (aear_i == 0) {
							list =getNumbersPartList(aearnumbers,true);
						} else {
							list =getNumbersPartList(aearnumbers,false);
						}
						StringBuffer areanumberPart = new StringBuffer();
						for (int number_j = 0; number_j < list.size(); number_j++) {
							String numberPart = "";
							if (aear_i == 0) {
								numberPart = PublicMethod
										.getZhuMa(Integer.valueOf(list.get(
												number_j).toString()) % 10);

							} else {
								numberPart = PublicMethod
										.getZhuMa(Integer.valueOf(list.get(
												number_j).toString()));
							}
							areanumberPart.append(numberPart);
						}
						numbersPart.append(areanumberPart);
						if (aear_i != areaNums.length - 1) {
							numbersPart.append("*");
						}
					}
				}
				// 如果是单式
				else if (getZhuShu() == 1) {
					// 分别获取两个选号面板的号码
					int[] aearnumbers0 = areaNums[0].table
							.getHighlightBallNOs();
					List list =getNumbersPartList(aearnumbers0,false);
					int[] aearnumbers1 = areaNums[1].table
							.getHighlightBallNOs();
					String numberPart = "";
					// 判断面板号码的大小
					if ((Integer.valueOf(list.get(0).toString()) % 10) > aearnumbers1[0]) {
						montageSmallNumber(numbersPart, aearnumbers1);
						montageBigNumber(numbersPart, list);
					} else {
						montageBigNumber(numbersPart, list);
						montageSmallNumber(numbersPart, aearnumbers1);
					}
				}
			}
			numbersParts = numbersPart.toString();
		} else if (state.equals("PT_3BT")||state.equals("DT_3BT")) {
			int[] areaNumbers = areaNums[0].table.getHighlightBallNOs();
			for (int number_i = 0; number_i < areaNumbers.length; number_i++) {
				String numberString = PublicMethod
						.getZhuMa(areaNumbers[number_i]);
				numbersPart.append(numberString);
			}
			numbersParts = numbersPart.toString();
		} else if (state.equals("PT_2BT")||state.equals("DT_2BT")) {
			int[] areaNumbers = areaNums[0].table.getHighlightBallNOs();
			for (int number_i = 0; number_i < areaNumbers.length; number_i++) {
				String numberString = "";
				if (getZhuShu() > 1) {
					numberString = PublicMethod.getZhuMa(areaNumbers[number_i]);
				} else {
					numberString = String.valueOf(areaNumbers[number_i]);
				}
				numbersPart.append(numberString);
			}
			numbersParts = numbersPart.toString();
		} 

		return numbersParts;
	}
	
	/**
	 * 
	 * 对获取到的数组重新组合
	 */
	private List getNumbersPartList(int[] numbers,boolean isDelete){
		List list = new ArrayList();
		for (int i = 0; i < numbers.length; i++) {
			if(isDelete){
				if (i % 2 == 0) {
					list.add(numbers[i]);
				}
			}else{
				list.add(numbers[i]);
			}
		}
		return list;
	}
	
	private void montageSmallNumber(StringBuffer numbersPart, int[] aearnumbers1) {
		String numberPart;
		// 拼接后面小的号码
		numberPart = PublicMethod.getZhuMa(aearnumbers1[0]);
		numbersPart.append(numberPart);
	}
	
	private void montageBigNumber(StringBuffer numbersPart, List list) {
		String numberPart;
		// 在拼接前面大的号码
		String numbers ="";
		for(int i=0;i<list.size();i++){
			numbers+= String.valueOf(list.get(i));
		}
		for (int number_j = 0; number_j < numbers.length(); number_j++) {
			numberPart = PublicMethod.getZhuMa(Integer.valueOf((String) numbers
					.subSequence(number_j, number_j + 1)));
			numbersPart.append(numberPart);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		showEditText();
	}

	/**
	 * 获取号码个数部分
	 * 
	 * @return 号码个数部分
	 */
	private String getNumberNumsPart() {
		String numberNumsPart="";
		if (state.equals("PT_HZ")) {
			numberNumsPart=PublicMethod
					.getZhuMa(areaNums[0].table.getHighlightBallNOs().length);
		} else if (state.equals("PT_3T")) {
			numberNumsPart=PublicMethod
					.getZhuMa(areaNums[0].table.getHighlightBallNOs().length);
		} else if (state.equals("PT_2TD")||state.equals("PT_2TF")) {
			numberNumsPart=PublicMethod
					.getZhuMa(areaNums[0].table.getHighlightBallNOs().length/2);
		} else if (state.equals("PT_3BT")) {
			numberNumsPart=PublicMethod
					.getZhuMa(areaNums[0].table.getHighlightBallNOs().length);
		} else if (state.equals("PT_2BT")) {
			if (getZhuShu() == 1) {
				return "01";
			} else {
				return PublicMethod.getZhuMa(areaNums[0].table
						.getHighlightBallNOs().length);
			}
		} else if (state.equals("DT_3BT")) {
			numberNumsPart="";
		} else if (state.equals("DT_2BT")) {
			numberNumsPart="";
		}
		return numberNumsPart;
	}

	/**
	 * 获取玩法部分
	 * 
	 * @return 玩法部分
	 */
	private String getPlayMethodPart() {
		String playMethodPart = "";
		if(state.equals("PT_HZ")){
			playMethodPart = "10";
		}else if(state.equals("PT_3T")){
			if (getThreeSameBallZhuShu() > 1) {
				playMethodPart = "81";
			} else {
				playMethodPart = "02";
			}
		}else if(state.equals("PT_2TD")){
			if (getZhuShu() > 1) {
				playMethodPart = "71";
			} else {
				playMethodPart = "01";
			}
		} else if(state.equals("PT_2TF")){
			playMethodPart = "30";
		} else if (state.equals("PT_3BT")) {
			if (getThreeDiffZhuShu() > 1) {
				playMethodPart = "63";
			} else {
				playMethodPart = "00";
			}
		} else if (state.equals("PT_2BT")) {
			if (getZhuShu() > 1) {
				playMethodPart = "21";
			} else {
				playMethodPart = "20";
			}
		} else if (state.equals("DT_3BT")) {
			if (getZhuShu() > 1) {
				playMethodPart = "64";
			} else {
				playMethodPart = "00";
			}
		} else if (state.equals("DT_2BT")) {
			if (getZhuShu() > 1) {
				playMethodPart = "22";
			} else {
				playMethodPart = "20";
			}
		}
		return playMethodPart;
	}

	@Override
	public String getZhuma(Balls ball) {
		return null;
	}

	@Override
	public void touzhuNet() {
		betAndGift.setLotno(Constants.LOTNO_JLK3);
		betAndGift.setBet_code(getZhuma());
		int zhuShu = getZhuShu();
		betAndGift.setAmount("" + zhuShu * 200);
		betAndGift.setBatchcode(batchCode);
	}

	@Override
	public void onCheckAction(int checkedId) {
		switch (checkedId) {
		case 0:
			if(playMethodTag==1){
				newNmk3TopView.setZhMissButtonShow();
				startSensor();
				createViewPT(checkedId);
			}else if (playMethodTag==2) {
				newNmk3TopView.removeZhMissButton();
				stopSensor();
				createViewDT(checkedId);
			}
			lotteryService.getNoticePrizeInfoList(Constants.LOTNO_JLK3);
			break;
		default:
			break;
		}
	}
	
	private void createViewPT(int checkedId) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
		if(state.equals("PT_HZ")){
			highttype="JLK3_HEZHI";
			int[] cqArea={6,6,4};
			String[][] clickBallText = { { "3", "4", "5", "6" , "7", "8" },
					{  "9", "10", "11", "12", "13" , "14"},
					{ "15", "16", "17", "18" } };
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_HEZHI,checkedId, true,clickBallText);
			isMissNet(new JiLinK3MissJson(), MissConstant.JLK3_HEZHI, false);// 获取遗漏值
		}else if(state.equals("PT_3T")){
			highttype="JLK3_THREE_SAME";
			int[] cqArea={3,3};
			String[][] clickBallText = { { "111", "222", "333" },{  "444", "555", "666"} };
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(cqArea, 1, 6, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] cqArea1={1};
			areaNums[1] = new AreaNum(cqArea1, 0, 1, BallResId, 0, 1,Color.RED, "任意一个豹子号开出，即中40元！","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_THREESAME,checkedId, true,clickBallText);
			isMissNet(new JiLinK3MissJson(), MissConstant.JLK3_THREE_DAN_FU + ";" + MissConstant.JLK3_THREESAME_TONG, false);
		}else if(state.equals("PT_2TD")){
			highttype="JLK3_TWO_SAME_DAN";
			int[] cqArea={3,3};
			String[][] clickBallText = { { "1", "2", "3" , "4","5", "6"},{  "4", "5", "6"} };
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(cqArea, 1, 12, BallResId, 0, 1,Color.RED, "","", false, true, true);
			int[] cqArea1={6};
			areaNums[1] = new AreaNum(cqArea1, 1, 6, BallResId, 0, 1,Color.RED, "","", false, true, true);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_TWOSAME_DAN,checkedId, true,clickBallText);
			isMissNet(new JiLinK3MissJson(), MissConstant.JLK3_TWO_DAN, false);// 获取遗漏值
		} else if (state.equals("PT_3BT")) {
			highttype="JLK3_THREE_DIFF";
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(cqArea, 3, 6, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] cqArea={1};
			areaNums[1] = new AreaNum(cqArea, 0, 1, BallResId, 0, 1,Color.RED, "123/234/345/456任一开出即中10元！","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_DIFF_THREE,checkedId, true,clickBallText);
			isMissNet(new JiLinK3MissJson(), MissConstant.JLK3_THREE_TWO + ";" + MissConstant.JLK3_THREE_LINK_TONG, false);
		} else if (state.equals("PT_2BT")) {
			highttype="JLK3_TWO_DIFF";
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, 2, 6, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_DIFF_TWO,checkedId, true,clickBallText);
			isMissNet(new JiLinK3MissJson(), MissConstant.JLK3_THREE_TWO, false);
		} else if (state.equals("PT_2TF")) {
			highttype="JLK3_TWO_SAME_FU";
			int[] cqArea={3,3};
			String[][] clickBallText = { { "1", "2", "3" },{  "4", "5", "6"} };
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, 1, 12, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_TWOSAME_FU,checkedId, true,clickBallText);
			isMissNet(new JiLinK3MissJson(), MissConstant.JLK3_THREE_TWO, false);// 获取遗漏值
		}
		
	}

	private void createViewDT(int checkedId) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
		if(state.equals("DT_3BT")){
			highttype="JLK3_THREE_DIFF_DANTUO";
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(cqArea, 1, 2, BallResId, 0, 1,Color.RED, "胆码区","（可选1-2个，胆码+拖码>=4个）", false, false, true);
			areaNums[1] = new AreaNum(cqArea, 3, 5, BallResId, 0, 1,Color.RED, "拖码区","（可选2-5个）", false, false, true);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NEW_NK3_THREE_DIFF_DANTUO,checkedId, true,clickBallText);
			isMissNet(new JiLinK3MissJson(), MissConstant.JLK3_THREE_TWO + ";" + MissConstant.JLK3_THREE_LINK_TONG, false);
		}else if(state.equals("DT_2BT")){
			highttype="JLK3_TWO_DIFF_DANTUO";
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(cqArea, 1, 1, BallResId, 0, 1,Color.RED, "胆码区","（可选1个，胆码+拖码>=3个）", false, false, true);
			areaNums[1] = new AreaNum(cqArea, 2, 5, BallResId, 0, 1,Color.RED, "拖码区","（可选2-5个）", false, false, true);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NEW_NK3_TWO_DIFF_DANTUO,checkedId, true,clickBallText);
			isMissNet(new JiLinK3MissJson(), MissConstant.JLK3_THREE_TWO, false);
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
					if(state.equals("PT_2TD")){
						if(i == 0){
							int isHighLight = areaNums[0].table.changeDoubleBallState(
									areaNums[0].chosenBallSum, nBallId);
							if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT
									&& areaNums[1].table.getOneBallStatue(nBallId) != 0) {
								areaNums[1].table.clearOnBallHighlight(nBallId);
							}
						}else{
							int isHighLight = areaNums[1].table.changeBallState(
									areaNums[1].chosenBallSum, nBallId);
							if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT
									&& areaNums[0].table.getDoubleBallStatue(nBallId) != 0) {
								areaNums[0].table.clearDoubleBallHighlight(nBallId);
							}
						}
					}else if(state.equals("PT_2TF")){
						areaNums[i].table.changeDoubleBallState(
								areaNums[i].chosenBallSum, nBallId);
					}else{
						areaNums[i].table.changeBallState(
								areaNums[i].chosenBallSum, nBallId);
					}
				}
				break;
			}

		}

	}
	
	/**
	 * 设置投注金额提示
	 */
	public void showEditText(){
		editZhuma.setText(textSumMoney(areaNums, iProgressBeishu));
		showEditTitle(NULL);
	}
	
	String getZhuma2() {
		// 拼接投注的注码格式，用户投注与后台使用
		String zhuMa = "";

		// 获取注码的各个部分
		String playMethodPart = getPlayMethodPart2();
		String mutiplePart = computingCenterService.getMutiplePart();
		String numberNumsPart = getNumberNumsPart2();
		String numbersPart = getNumbersPart2();
		String endFlagPart = "^";

		if(state.equals("PT_3T")){
			zhuMa = playMethodPart + mutiplePart + endFlagPart;
		} else if (state.equals("PT_3BT")) {
			// 拼接注码
			zhuMa = playMethodPart + mutiplePart + numberNumsPart + numbersPart
					+ endFlagPart;
		}

		return zhuMa;
	}

	private String getNumbersPart2() {
		return "";
	}

	private String getNumberNumsPart2() {
		String playMethodPart = "";
		if(state.equals("PT_3T")){
			playMethodPart = PublicMethod
					.getZhuMa(areaNums[1].table.getHighlightBallNOs().length);
		} else if (state.equals("PT_3BT")) {
			playMethodPart = "";
		}
		return playMethodPart;
	}

	private String getPlayMethodPart2() {
		String playMethodPart = "";
		if(state.equals("PT_3T")){
			playMethodPart = "40";
		} else if (state.equals("PT_3BT")) {
			playMethodPart = "50";
		}
		return playMethodPart;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeMediaPlayer();
		DiceAnimation.flag = true;
		animationService.removeAnimationListeners(JiLinK3.this);
		lotteryService.removeLotteryListeners(JiLinK3.this);
	}
	
	/**
	 * 赋值给当前期
	 * 
	 * @param type彩种编号
	 */
	public void setIssue(final String lotno) {
		final Handler sscHandler = new Handler();
		newNmk3TopView.setElevenSelectFiveEndTime("获取中...");
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
										newNmk3TopView.setElevenSelectFiveEndTime("距"
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
										newNmk3TopView
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
								newNmk3TopView.setElevenSelectFiveEndTime("获取期号失败");
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
		
		try {
			new AlertDialog.Builder(JiLinK3.this)
			.setTitle("提示")
			.setMessage(
					newNmk3TopView.getElevenSelectFiveTitleText() + "第" + batchCode
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
							JiLinK3.this.finish();
						}
					}).create().show();
		} catch (Exception e) {
			
		}
	}

	@Override
	public void stopAnimation() {
		closeMediaPlayer();
	}

	@Override
	public void updateLatestLotteryList(String lotno) {
		
	}

	@Override
	public void updateNoticePrizeInfo(String lotno, PrizeInfoList prizeInfoList) {
		if (Constants.LOTNO_JLK3.equals(lotno)) {
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
	
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			CharSequence msgString = (CharSequence) msg.getData().get("msg");
			switch (msg.what) {
			case GET_PRIZEINFO_ERROR:
				Toast.makeText(JiLinK3.this, "历史获奖信息获取失败..." + msgString,
						Toast.LENGTH_LONG).show();
				break;
			case GET_PRIZEINFO_SUCCESS:
				String data=(String) msg.getData().get("result");
				List<HistoryLotteryBean> lotteryData=JsonUtils.getList(data, HistoryLotteryBean.class);
				if(lotteryData!=null){
					historyLotteryAdapter.setLotteryList(lotteryData);
					jilinK3LotteryListView.setAdapter(historyLotteryAdapter);
				}
				PublicMethod.closeProgressDialog(progressdialog);
				break;
			}

		}
	};
}
