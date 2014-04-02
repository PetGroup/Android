package com.ruyicai.activity.buy.nmk3;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioGroup;
import com.google.inject.Inject;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.component.DiceAnimation;
import com.ruyicai.constant.Constants;
import com.ruyicai.controller.listerner.AnimationListener;
import com.ruyicai.controller.service.AnimationService;
import com.ruyicai.controller.service.HighZhuMaCenterService;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.json.miss.Nmk3MissJson;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.PublicMethod;

/**
 * Nmk3DiffActivity:内蒙古快三三不同号
 * 
 * @author PengCX
 * 
 */
public class Nmk3ThreeDiffActivity extends ZixuanAndJiXuan implements AnimationListener {
	//选择的三不同单选号码小球的个数
	int threeDiffBallNums;
	//选择的三连号通选的小球的个数
	int threeLinkBallNums;
	
	int threeDiffZhuShu;
	int threeLinkZhuShu;
	private final String threeDiffDanTuo = "NMK3-DIFFER-THREE-DAN-TUO";
	
	@Inject private AnimationService  animationService;
	@Inject private HighZhuMaCenterService computingCenterService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setAddView(((Nmk3Activity) getParent()).addView);
		super.onCreate(savedInstanceState);
		animationService.addAnimationListeners(Nmk3ThreeDiffActivity.this);
		//设置彩种信息
		lotno = Constants.LOTNO_NMK3;
		highttype = "NMK3-DIFFER-THREE";
		lotnoStr=Constants.LOTNO_NMK3;
		BallResId[0] = R.drawable.changbtn_normal;
		BallResId[1] = R.drawable.changbtn_click;
		//设置单选按钮
		
		init();childtype = new String[] { "标准", "胆拖" };
		//设置背景图片
		zixuanLayout.setBackgroundResource(R.color.transparent);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		//页面启动之后，由于RadioGroup自动调用监听方法，进行页面的初始化显示
		 onCheckAction(checkedId);
		 ((BuyActivityGroup) getParent()).showBetInfo(textSumMoney(areaNums, iProgressBeishu));
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (threeDiffDanTuo.equals(highttype)) {
			stopSensor();
		} else {
			startSensor();
		}
//		sensor.stopAction();
//		baseSensor.stopAction();
		editZhuma.setText(textSumMoney(areaNums, iProgressBeishu));
		editZhuma.setTextColor(Color.BLACK);
	}

	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		int zhuShu = getZhuShu();
//		if (threeDiffBallNums < 3) {
//			return "还需要选择" + (3 - threeDiffBallNums) + "个球";
//		}
		return "您已选择了" + zhuShu + "注，共" + zhuShu * 2 + "元";
	}
	/**
	 * 设置投注金额提示
	 */
	public void showEditText(){
		editZhuma.setText(textSumMoney(areaNums, iProgressBeishu));
		showEditTitle(NULL);
	}
	@Override
	public String isTouzhu() {
		if (threeDiffDanTuo.equals(highttype)) {
			 if (getZhuShu() > 1) {
				 return "true";
			 } else {
				 return String.format(getResources().getString(R.string.buy_nmk3_dan_tuo_message), "1~2", "4");
			 }
		} else {
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
		if (threeDiffDanTuo.equals(highttype)) {
			int danNum = computingCenterService.getHighlightBallNums(areaNums[0]);
			int tuoNum = computingCenterService.getHighlightBallNums(areaNums[1]);
			if (danNum == 0 || tuoNum == 0) {
				return 0;
			} else {
				if (danNum == 1) {
					return computingCenterService.zuHe(tuoNum, 2);
				} else if (danNum == 2) {
					return tuoNum;
				}
				return 0;
			}
		} else {
			// 获取三不同号单选择的小球个数
			if (areaNums[0] != null && areaNums[0].table != null) {
				threeDiffBallNums = areaNums[0].table.getHighlightBallNums();
			}
			// 获取三连号通选选择小球的个数
			if (areaNums[1] != null && areaNums[1].table != null) {
				threeLinkBallNums = areaNums[1].table.getHighlightBallNums();
			}
			
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
		}
	}
	
	int getThreeLinkZhuShu() {
		return threeLinkZhuShu;
	}

	int getThreeDiffZhuShu() {
		return threeDiffZhuShu;
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
		if (threeDiffDanTuo.equals(highttype)) {
			numbersPart = numbersPart + computingCenterService.getDanNumbersPart(areaNums[1]);
		}
		String endFlagPart = "^";

		if (radioId == 0 && getThreeDiffZhuShu() == 1) {
			zhuMa = playMethodPart + mutiplePart + numbersPart + endFlagPart;
		} else {
			zhuMa = playMethodPart + mutiplePart + numberNumsPart + numbersPart
					+ endFlagPart;
		}

		return zhuMa;
	}
	
	String getZhuma2() {
		// 拼接投注的注码格式，用户投注与后台使用
		String zhuMa = "";

		// 获取注码的各个部分
		String playMethodPart = getPlayMethodPart2();
		String mutiplePart = computingCenterService.getMutiplePart();
		String endFlagPart = "^";

		// 拼接注码
		zhuMa = playMethodPart + mutiplePart + endFlagPart;

		return zhuMa;
	}

	private String getPlayMethodPart2() {
		return "50";
	}

	private String getNumbersPart() {
		StringBuffer numbersPart = new StringBuffer();
		int[] areaNumbers = areaNums[0].table.getHighlightBallNOs();

		for (int number_i = 0; number_i < areaNumbers.length; number_i++) {
			String numberString = PublicMethod.getZhuMa(areaNumbers[number_i]);
			numbersPart.append(numberString);
		}

		return numbersPart.toString();
	}
	

	private String getNumberNumsPart() {
		if (threeDiffDanTuo.equals(highttype)) {
			return "";
		}
		return PublicMethod
				.getZhuMa(areaNums[0].table.getHighlightBallNOs().length);
	}

	private String getPlayMethodPart() {
		String playMethod = "";
		if (threeDiffDanTuo.equals(highttype)) {
			if (getZhuShu() > 1) {
				playMethod = "64";
			} else {
				playMethod = "00";
			}
		} else {
			if (getThreeDiffZhuShu() > 1) {
				playMethod = "63";
			} else {
				playMethod = "00";
			}
		}
		

		return playMethod;
	}

	@Override
	public String getZhuma(Balls ball) {
		return null;
	}

	@Override
	public void touzhuNet() {
		// 设置投注信息彩种，注码，金额和期号等投注信息
		betAndGift.setLotno(Constants.LOTNO_NMK3);
		betAndGift.setBet_code(getZhuma());
		int zhuShu = getZhuShu();
		betAndGift.setAmount("" + zhuShu * 200);
		betAndGift.setBatchcode(Nmk3Activity.batchCode);
	}

	@Override
	public void onCheckAction(int checkedId) {
		// 创建页面内的选号面板对象
		initArea(checkedId);
		//根据单选按钮的id初始化页面
		switch (checkedId) {
		case 0:
			// 根据创建页面的选号面板对象，创建页面的视图
			createView(areaNums, sscCode, ZixuanAndJiXuan.NMK3_DIFF_THREE,
					true, checkedId, true);
			startSensor();
			setShakeShow(false);
			break;
		case 1:
			createView(areaNums, sscCode, ZixuanAndJiXuan.NMK3_THREE_DIFF_DANTUO, true, checkedId,
					true);
			stopSensor();
			setShakeShow(true);
			break;
		}
		zixuanLayout.setBackgroundResource(R.color.transparent);
		// 获取遗漏值
		isMissNet(new Nmk3MissJson(), MissConstant.NMK3_THREE_TWO + ";" + MissConstant.NMK3_THREE_LINK_TONG, false);
		
	}

	public AreaNum[] initArea(int checkedId) {
		//创建页面额选号面板
		areaNums = new AreaNum[2];
		switch (checkedId) {
		case 0:
			highttype = "NMK3-DIFFER-THREE";
			areaNums[0] = new AreaNum(6, 4, 1, 6, BallResId, 0, 1, Color.RED,
					"三不同号单选：猜开奖的3个号码，奖金40元！", false, true);
			areaNums[1] = new AreaNum(1, 1, 1, 1, BallResId, 0, 1, Color.RED, "三连号通选：123,234,345,456任一开出即中10元！",
					false, true);
			break;
			
		case 1:
			highttype = threeDiffDanTuo;
			areaNums[0] = new AreaNum(6, 4, 1, 2, BallResId, 0, 1, Color.RED,
					"猜开奖的3个不同号码，奖金40元", false, true);
			areaNums[1] = new AreaNum(6, 4, 1, 5, BallResId, 0, 1, Color.RED,
					"", false, true);
			break;
		}

		return areaNums;
	}



	/*
	 * 设置投注信息类的彩种编号和投注类型
	 */
	void setLotoNoAndType(CodeInfo codeInfo) {
		codeInfo.setLotoNo(Constants.LOTNO_NMK3);
		if (threeDiffDanTuo.equals(highttype)) {
			codeInfo.setTouZhuType("different_three_dantuo");
		} else {
			codeInfo.setTouZhuType("different_three");
		}
		
	}
	
	void setLotoNoAndType2(CodeInfo codeInfo) {
		codeInfo.setLotoNo(Constants.LOTNO_NMK3);
		codeInfo.setTouZhuType("threelink");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeMediaPlayer();
		DiceAnimation.flag = true;
		animationService.removeAnimationListeners(Nmk3ThreeDiffActivity.this);
	}

	@Override
	public void stopAnimation() {
		// TODO Auto-generated method stub
		closeMediaPlayer();
	}
}
