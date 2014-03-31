package com.ruyicai.activity.buy.jlk3;

import java.util.List;
import org.json.JSONObject;
import roboguice.inject.InjectView;
import roboguice.inject.ContentView;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.cq11x5.Cq11Xuan5;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.nmk3.Nmk3Activity;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveHistoryLottery;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView.ElevenSelectFiveTopViewClickListener;
import com.ruyicai.constant.Constants;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.net.newtransaction.GetLotNohighFrequency;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.OneBallView;
import com.ruyicai.util.CheckUtil;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.json.JsonUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * 吉林新快三
 *
 */

public class JiLinK3 extends ZixuanAndJiXuan {
	
	private ElevenSelectFiveTopView newNmk3TopView;
	private String[] ptPlayMethod={"和值","三同号","二同号单选","三不同","二不同","二同号复选"};
	private String[] dtPlayMethod={"三不同","二不同"};
	private String[] ptPlayMethodDescribe={"奖金9-240元","奖金9-240元","奖金80元","奖金10-40元","奖金8元","奖金15元"};
	private String[] dtPlayMethodDescribe={"奖金10-40元","奖金8元"};
	private int[] itemClickPicture={R.drawable.new_nmk3_playmethod_normal,R.drawable.new_nmk3_playmethod_click};
	private int noticeLotNo=NoticeActivityGroup.ID_SUB_JLK3_LISTVIEW;
	private int checkedId;
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
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAddView(addView);
		setContentView(R.layout.activity_new_faster_three_main);
		lotno = Constants.LOTNO_JLK3;
		state = "PT_HZ";
		initView();
		action();
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
	
	private void action(){
		missView.clear();
		childtype = new String[] { "自选" };
		init();
		childtypes.setVisibility(View.GONE);
		group.setOnCheckedChangeListener(this);
		group.check(0);
	}
	
	private void initView(){
		newNmk3TopView=(ElevenSelectFiveTopView) findViewById(R.id.newNmk3TopView);
		newNmk3TopView.isNewNmkThree(true);
		newNmk3TopView.setTopViewBackGround(R.drawable.new_nmk3_top);
		newNmk3TopView.setTopViewTitleBackGround(R.drawable.new_nmk3_top_title_click);
		newNmk3TopView.setPuTongPlayMessage(ptPlayMethod);
		newNmk3TopView.setDanTuoPlayMessage(dtPlayMethod);
		newNmk3TopView.removeTopViewTitleBackGround();
		newNmk3TopView.setPoupWindowItemClickPicture(itemClickPicture);
		newNmk3TopView.setPtPlayMethodDescribeList(ptPlayMethodDescribe);
		newNmk3TopView.setDtPlayMethodDescribeList(dtPlayMethodDescribe);
		newNmk3TopView.setTextColor(this.getResources().getColor(R.color.white));
		newNmk3TopView.setQueryMessage(lotno, noticeLotNo);
		newNmk3TopView.setZhMissBtnBackGround(R.drawable.new_nmk3_yao);
		newNmk3TopView.setZouShiBtnBackGround(R.drawable.new_nmk3_zoushi);
		newNmk3TopView.setLotteryMessageTextColor(this.getResources().getColor(R.color.white));
		newNmk3TopView.setPtPlayMessage(getResources().getStringArray(R.array.new_nmk3_choose_pt_type));
		newNmk3TopView.setDtPlayMessage(getResources().getStringArray(R.array.new_nmk3_choose_dt_type));
		
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
				
			}
			
			@Override
			public void ElevenSelectFiveFresh() {
				
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
		this.checkedId=checkedId;
		onCheckAction(checkedId);
	}

	@Override
	public String isTouzhu() {
		if (getZhuShu() == 0) {
			return "请至少选择一注";
		} else if (getZhuShu() > 10000) {
			return "false";
		} else {
			return "true";
		}
	}

	@Override
	public int getZhuShu() {
		if(state.equals("PT_3BT")){
			// 获取三不同号单选择的小球个数
			int threeDiffBallNums = areaNums[0].table.getHighlightBallNums();
			int threeDiffZhuShu = 0;
			if (threeDiffBallNums >= 3) {
				threeDiffZhuShu = zuHe(threeDiffBallNums, 3);
			}
			return threeDiffZhuShu;
		}else if(state.equals("PT_2BT")){
			int num = areaNums[0].table.getHighlightBallNums();
			int zhuShu = 0;
			if (num >= 2) {
				zhuShu = zuHe(num, 2);
			}
			return zhuShu;
		}else if(state.equals("PT_2TD")){
			int sameNum = areaNums[0].table.getHighlightBallNums()/2;
			int diffNum = areaNums[1].table.getHighlightBallNums();
			return sameNum * diffNum;
		}else if(state.equals("PT_2TF")){
			return areaNums[0].table.getHighlightBallNums()/2;
		}else if(state.equals("DT_3BT")||state.equals("DT_2BT")){
			int dan = areaNums[0].table.getHighlightBallNums();
			int tuo = areaNums[1].table.getHighlightBallNums();
			return (int) getDTZhuShu(dan, tuo, iProgressBeishu);
		}else{
			return areaNums[0].table.getHighlightBallNums();
		}
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
	 * 求a取b的组合数
	 */
	private int zuHe(int a, int b) {
		int up = 1;
		for (int up_i = 0; up_i < b; up_i++) {
			up = up * a;
			a--;
		}

		int down = jieCheng(b);

		return up / down;
	}
	
	/**
	 * 求b的阶乘
	 */
	private int jieCheng(int b) {
		int result = 0;

		if (b == 1 || b == 0) {
			result = b;
		} else {
			result = b * jieCheng(b - 1);
		}

		return result;
	}

	@Override
	public String getZhuma() {
		// 拼接投注的注码格式，用户投注与后台使用
				String zhuMa = "";

				// 获取注码的各个部分
				String playMethodPart = getPlayMethodPart();
				String mutiplePart = getMutiplePart();
				String numberNumsPart = getNumberNumsPart();
				String numbersPart = getNumbersPart();
				String endFlagPart = "^";

				// 拼接注码
				zhuMa = playMethodPart + mutiplePart + numberNumsPart + numbersPart
						+ endFlagPart;

				return zhuMa;
	}
	
	/**
	 * 获取号码部分
	 * 
	 * @return 号码部分
	 */
	private String getNumbersPart() {
		// 获取高亮小球号码数组
		int[] numbers = areaNums[0].table.getHighlightBallNOs();
		StringBuffer numbersPart = new StringBuffer();

		// 循环号码数组，并拼接
		for (int num_i = 0; num_i < numbers.length; num_i++) {
			numbersPart.append(PublicMethod.getZhuMa(numbers[num_i]));
		}

		return numbersPart.toString();
	}

	/**
	 * 获取号码个数部分
	 * 
	 * @return 号码个数部分
	 */
	private String getNumberNumsPart() {

		return PublicMethod
				.getZhuMa(areaNums[0].table.getHighlightBallNOs().length);

	}

	/**
	 * 获取倍数字段
	 * 
	 * @return 倍数部分
	 */
	private String getMutiplePart() {
		// 获取注码的时候默认使用1倍，在投注详情界面的倍数才对后台有效
		return "0001";
	}

	/**
	 * 获取玩法部分
	 * 
	 * @return 玩法部分
	 */
	private String getPlayMethodPart() {
		return "10";
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
		betAndGift.setBatchcode(Nmk3Activity.batchCode);
	}

	@Override
	public void onCheckAction(int checkedId) {
		switch (checkedId) {
		case 0:
			if(playMethodTag==1){
				createViewPT(checkedId);
			}else if (playMethodTag==2) {
				createViewDT(checkedId);
			}
			break;
		default:
			break;
		}
	}
	
	private void createViewPT(int checkedId) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
		if(state.equals("PT_HZ")){
			highttype="NEW_NMK3_HEZHI";
			int[] cqArea={6,6,4};
			String[][] clickBallText = { { "3", "4", "5", "6" , "7", "8" },
					{  "9", "10", "11", "12", "13" , "14"},
					{ "15", "16", "17", "18" } };
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_HEZHI,checkedId, true,clickBallText);
		}else if(state.equals("PT_3T")){
			highttype="NEW_NMK3_THREE_SAME";
			int[] cqArea={3,3};
			String[][] clickBallText = { { "1", "2", "3" },{  "4", "5", "6"} };
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, 1, 6, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_THREESAME,checkedId, true,clickBallText);
		}else if(state.equals("PT_2TD")){
			highttype="NEW_NMK3_TWO_SAME_DAN";
			int[] cqArea={3,3};
			String[][] clickBallText = { { "1", "2", "3" , "4","5", "6"},{  "4", "5", "6"} };
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(cqArea, 1, 18, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] cqArea1={6};
			areaNums[1] = new AreaNum(cqArea1, 1, 18, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_TWOSAME_DAN,checkedId, true,clickBallText);
		} else if (state.equals("PT_3BT")) {
			highttype="NEW_NMK3_THREE_DIFF";
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, 1, 6, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_DIFF_THREE,checkedId, true,clickBallText);
		} else if (state.equals("PT_2BT")) {
			highttype="NEW_NMK3_TWO_DIFF";
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, 1, 6, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_DIFF_TWO,checkedId, true,clickBallText);
		} else if (state.equals("PT_2TF")) {
			highttype="NEW_NMK3_TWO_SAME_FU";
			int[] cqArea={3,3};
			String[][] clickBallText = { { "1", "2", "3" },{  "4", "5", "6"} };
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, 1, 12, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_TWOSAME_FU,checkedId, true,clickBallText);
		}
		
	}

	private void createViewDT(int checkedId) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
		if(state.equals("DT_3BT")){
			highttype="NEW_NMK3_THREE_DIFF_DANTUO";
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(cqArea, 1, 2, BallResId, 0, 1,Color.RED, "胆码区","（可选1-2个，胆码+拖码>=4个）", false, true, false);
			areaNums[1] = new AreaNum(cqArea, 3, 5, BallResId, 0, 1,Color.RED, "拖码区","（可选2-5个）", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NEW_NK3_THREE_DIFF_DANTUO,checkedId, true,clickBallText);
		}else if(state.equals("DT_2BT")){
			highttype="NEW_NMK3_TWO_DIFF_DANTUO";
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(cqArea, 1, 1, BallResId, 0, 1,Color.RED, "胆码区","（可选1个，胆码+拖码>=3个）", false, true, false);
			areaNums[1] = new AreaNum(cqArea, 2, 5, BallResId, 0, 1,Color.RED, "拖码区","（可选2-5个）", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NEW_NK3_TWO_DIFF_DANTUO,checkedId, true,clickBallText);
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
		if(state.equals("PT_2TD")){
			
		}
		editZhuma.setText(textSumMoney(areaNums, iProgressBeishu));
		showEditTitle(NULL);
	}
	
}
