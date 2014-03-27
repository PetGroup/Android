package com.ruyicai.activity.buy.jlk3;

import roboguice.inject.ContentView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveHistoryLottery;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView.ElevenSelectFiveTopViewClickListener;
import com.ruyicai.constant.Constants;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.OneBallView;
import com.ruyicai.util.PublicConst;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
	private int noticeLotNo=NoticeActivityGroup.ID_SUB_DLC_LISTVIEW;
	private int checkedId;
	/**玩法标识:1普通，2胆拖*/
	private int playMethodTag=1;
	public AddView addView = new AddView(this);
	private int[] cqArea={6};
	protected int BallResId[] = { R.drawable.new_nmk3_num_status_normal, R.drawable.new_nmk3_num_status_click };
	protected int nums[] = { 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 2, 3 };// 单式机选个数
	private int itemId=3;
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
				action();
			}
			
			@Override
			public void TouchDTPlayMethod(int position) {
				playMethodTag=2;
				state = dt_types[position];
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
		return areaNums[0].table.getHighlightBallNums();
	}

	@Override
	public String getZhuma() {
		return null;
	}

	@Override
	public String getZhuma(Balls ball) {
		return null;
	}

	@Override
	public void touzhuNet() {
		
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
			areaNums[0] = new AreaNum(cqArea, nums[itemId], 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_HEZHI,checkedId, true,clickBallText);
		}else if(state.equals("PT_3T")){
			highttype="NEW_NMK3_THREE_SAME";
			int[] cqArea={3,3};
			String[][] clickBallText = { { "1", "2", "3" },{  "4", "5", "6"} };
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, nums[itemId], 6, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_THREESAME,checkedId, true,clickBallText);
		}else if(state.equals("PT_2TD")){
			highttype="NEW_NMK3_TWO_SAME_DAN";
			int[] cqArea={3,3,6};
			String[][] clickBallText = { { "1", "2", "3" },{  "4", "5", "6"} ,{ "1", "2", "3" ,  "4", "5", "6"}};
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, nums[itemId], 18, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_TWOSAME_DAN,checkedId, true,clickBallText);
		} else if (state.equals("PT_3BT")) {
			highttype="NEW_NMK3_THREE_DIFF";
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, nums[itemId], 6, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_DIFF_THREE,checkedId, true,clickBallText);
		} else if (state.equals("PT_2BT")) {
			highttype="NEW_NMK3_TWO_DIFF";
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, nums[itemId], 6, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_DIFF_TWO,checkedId, true,clickBallText);
		} else if (state.equals("PT_2TF")) {
			highttype="NEW_NMK3_TWO_SAME_FU";
			int[] cqArea={3,3};
			String[][] clickBallText = { { "1", "2", "3" },{  "4", "5", "6"} };
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(cqArea, nums[itemId], 12, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NMK3_TWOSAME_FU,checkedId, true,clickBallText);
		}
		
	}

	private void createViewDT(int checkedId) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
		if(state.equals("DT_3BT")){
			highttype="NEW_NMK3_THREE_DIFF_DANTUO";
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(cqArea, nums[itemId], 2, BallResId, 0, 1,Color.RED, "胆码区","（可选1-2个，胆码+拖码>=4个）", false, true, false);
			areaNums[1] = new AreaNum(cqArea, nums[itemId], 5, BallResId, 0, 1,Color.RED, "拖码区","（可选2-5个）", false, true, false);
			createViewNewNmkThree(areaNums, sscCode, ZixuanAndJiXuan.NEW_NK3_THREE_DIFF_DANTUO,checkedId, true,clickBallText);
		}else if(state.equals("DT_2BT")){
			highttype="NEW_NMK3_TWO_DIFF_DANTUO";
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(cqArea, nums[itemId], 1, BallResId, 0, 1,Color.RED, "胆码区","（可选1个，胆码+拖码>=3个）", false, true, false);
			areaNums[1] = new AreaNum(cqArea, nums[itemId], 5, BallResId, 0, 1,Color.RED, "拖码区","（可选2-5个）", false, true, false);
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
						if(nBallId<6){
							areaNums[i].table.changeDoubleBallState(
									areaNums[i].chosenBallSum, nBallId);
						}else{
							areaNums[i].table.changeBallState(
									areaNums[i].chosenBallSum, nBallId+6);
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
	
}
