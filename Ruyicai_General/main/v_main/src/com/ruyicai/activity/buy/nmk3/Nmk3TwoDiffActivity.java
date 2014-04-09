package com.ruyicai.activity.buy.nmk3;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioGroup;
import com.google.inject.Inject;
import com.palmdream.RuyicaiAndroid.R;
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
public class Nmk3TwoDiffActivity extends ZixuanAndJiXuan implements AnimationListener {
	int num;
	private final String twoDiffDanTuo = "NMK3-DIFFER-TWO-DANTUO";
	@Inject private AnimationService  animationService;
	@Inject private HighZhuMaCenterService computingCenterService;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setAddView(((Nmk3Activity) getParent()).addView);
		super.onCreate(savedInstanceState);
		animationService.addAnimationListeners(Nmk3TwoDiffActivity.this);
		lotno = Constants.LOTNO_NMK3;
		childtype = new String[] { "标准", "胆拖" };
		BallResId[0] = R.drawable.nmk3_normal;
		BallResId[1] = R.drawable.nmk3_click;
		highttype = "NMK3-DIFFER-TWO";
		init();
		//2013-10-18徐培松
		zixuanLayout.setBackgroundResource(R.color.transparent);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		onCheckAction(checkedId);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (twoDiffDanTuo.equals(highttype)) {
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
		if (zhuShu == 0) {
			return "请选择投注号码";
		} else {
			return "你已选择了" + zhuShu + "注，共" + zhuShu * 2 + "元";
		}
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
		if (twoDiffDanTuo.equals(highttype)) {
			 if (getZhuShu() > 1) {
				 return "true";
			 } else {
				 return String.format(getResources().getString(R.string.buy_nmk3_dan_tuo_message), "1", "3");
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
		if (twoDiffDanTuo.equals(highttype)) {
			int danNum = computingCenterService.getHighlightBallNums(areaNums[0]);
			int tuoNum = computingCenterService.getHighlightBallNums(areaNums[1]);
			if (danNum == 0 || tuoNum == 0) {
				return 0;
			} else {
				if (danNum == 1) {
					return computingCenterService.zuHe(tuoNum, 1);
				}
				return 0;
			}
		} else {
			num = areaNums[0].table.getHighlightBallNums();
			int zhuShu = 0;
			if (num >= 2) {
				zhuShu = computingCenterService.zuHe(num, 2);
			}
			return zhuShu;
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
		if (twoDiffDanTuo.equals(highttype)) {
			numbersPart = numbersPart + computingCenterService.getDanNumbersPart(areaNums[1]);
		}
		String endFlagPart = "^";
		
		zhuMa = playMethodPart + mutiplePart + numberNumsPart + numbersPart
				+ endFlagPart;
		return zhuMa;
	}

	private String getNumbersPart() {
		StringBuffer numbersPart = new StringBuffer();
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

		return numbersPart.toString();
	}
	
	private String getNumberNumsPart() {
		if (twoDiffDanTuo.equals(highttype)) {
			return "";
		} else {
			if (getZhuShu() == 1) {
				return "01";
			} else {
				return PublicMethod.getZhuMa(areaNums[0].table
						.getHighlightBallNOs().length);
			}
		}
		
	}

	private String getPlayMethodPart() {
		String playMethod = "";
		if (twoDiffDanTuo.equals(highttype)) {
			if (getZhuShu() > 1) {
				playMethod = "22";
			} else {
				playMethod = "20";
			}
		} else {
			if (getZhuShu() > 1) {
				playMethod = "21";
			} else {
				playMethod = "20";
			}
		}
		
		return playMethod;
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
		lotnoStr=Constants.LOTNO_NMK3;
		initArea(checkedId);
		switch (checkedId) {
		case 0:
			createView(areaNums, sscCode, ZixuanAndJiXuan.NMK3_DIFF_TWO, true,
					checkedId, true);
			setShakeShow(false);
			startSensor();
			break;
			
		case 1:
			createView(areaNums, sscCode, ZixuanAndJiXuan.NMK3_TWO_DIFF_DANTUO, true,
					checkedId, true);
			setShakeShow(true);
			stopSensor();
			break;
		}
		
		// 获取遗漏值
		isMissNet(new Nmk3MissJson(), MissConstant.NMK3_THREE_TWO, false);
	}

	public AreaNum[] initArea(int checkedId) {
		switch (checkedId) {
		case 0:
			areaNums = new AreaNum[1];
			highttype = "NMK3-DIFFER-TWO";
			areaNums[0] = new AreaNum(6, 4, 1, 6, BallResId, 0, 1, Color.RED,"猜开奖号码两个指定的不同号码，奖金8元！", false, true);
			break;
			
		case 1:
			areaNums = new AreaNum[2];
			highttype = twoDiffDanTuo;
			areaNums[0] = new AreaNum(6, 4, 1, 1, BallResId, 0, 1, Color.RED,"猜开奖号码两个指定的不同号码，奖金8元！", false, true);
			areaNums[1] = new AreaNum(6, 4, 1, 5, BallResId, 0, 1, Color.RED,"", false, true);
			break;	
		}

		return areaNums;
	}


	/*
	 * 设置投注信息类的彩种编号和投注类型
	 */
	void setLotoNoAndType(CodeInfo codeInfo) {
		codeInfo.setLotoNo(Constants.LOTNO_NMK3);
		if (twoDiffDanTuo.equals(highttype)) {
			codeInfo.setTouZhuType("different_two_dantuo");
		} else {
			codeInfo.setTouZhuType("different_two");
		}
	}

	@Override
	public String getZhuma(Balls ball) {
		return null;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeMediaPlayer();
		DiceAnimation.flag = true;
		animationService.removeAnimationListeners(Nmk3TwoDiffActivity.this);
	}

	@Override
	public void stopAnimation() {
		// TODO Auto-generated method stub
		closeMediaPlayer();
	}
}
