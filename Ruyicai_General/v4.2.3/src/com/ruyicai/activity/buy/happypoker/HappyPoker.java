package com.ruyicai.activity.buy.happypoker;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView.ElevenSelectFiveTopViewClickListener;
import com.ruyicai.constant.Constants;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.pojo.AreaNum;
import com.umeng.analytics.MobclickAgent;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioGroup;

/**
 * 快乐扑克
 *
 */
public class HappyPoker extends ZixuanAndJiXuan{
	
	private ElevenSelectFiveTopView happyPokerTopView;
	private String[] ptPlayMethod={"猜对子","猜顺子","猜豹子","猜同花","猜同花顺","任选一","任选二",
			"任选三","任选四","任选五","任选六"};
	private String[] dtPlayMethod={"任选二","任选三","任选四","任选五","任选六"};
	private String[] ptPlayMethodDescribe={"最高奖88元","最高奖400元","最高奖6400元","最高奖90元",
			"最高奖2150元","最高奖5元","最高奖33元","最高奖116元","最高奖46元","最高奖22元","最高奖12元"};
	private String[] pt_types={"DZ","SZ","BZ","TH","THS","R1","R2","R3","R4","R5","R6"};// 普通类型
	private String[] dt_types={"R2","R3","R4","R5","R6"};// 胆拖类型
	private String state;// 当前类型
	private int[] playMethodTextColor={Color.BLACK,Color.rgb(182,60, 0)};
	private int[] itemClickPicture={R.drawable.happy_poker_playmethod_normal,R.drawable.happy_poker_playmethod_click};
	private String lotno;
	public int noticeLotNo;
	private int playMethodTag=1;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAddView(addView);
		super.lotno = Constants.LOTNO_HAPPY_POKER;
		setContentView(R.layout.activity_buy_happy_poker_main);
		setLotno();
		initView();
		MobclickAgent.onEvent(this, "happypoker"); 
		MobclickAgent.onEvent(this, "gaopingoucaijiemian ");
	}
	
	private void setLotno(){
		this.lotno = Constants.LOTNO_HAPPY_POKER;
		this.noticeLotNo=NoticeActivityGroup.ID_SUB_HAPPY_POKER;
		lotnoStr = lotno;
	}
	
	private void initView(){
		happyPokerTopView=(ElevenSelectFiveTopView) findViewById(R.id.happyPokerTopView);
		happyPokerTopView.removeZoushiBtnBackGround();
		happyPokerTopView.isHappyPoker(true);
		happyPokerTopView.setPtPlayMessage(getResources().getStringArray(R.array.pappy_poker_choose_pt_type));
		happyPokerTopView.setDtPlayMessage(getResources().getStringArray(R.array.pappy_poker_choose_dt_type));
		happyPokerTopView.setTopViewBackGround(R.drawable.new_nmk3_top);
		happyPokerTopView.setTopViewTitleBackGround(R.drawable.new_nmk3_top_title_click);
		happyPokerTopView.setPuTongPlayMessage(ptPlayMethod);
		happyPokerTopView.setDanTuoPlayMessage(dtPlayMethod);
		happyPokerTopView.removeTopViewTitleBackGround();
		happyPokerTopView.setPoupWindowItemClickPicture(itemClickPicture);
		happyPokerTopView.setPtPlayMethodDescribeList(ptPlayMethodDescribe);
		happyPokerTopView.setQueryMessage(lotno, noticeLotNo,"快乐扑克",5);
		happyPokerTopView.setZhMissBtnBackGround(0);
		happyPokerTopView.setZhMissBtnText("摇一摇机选");
		happyPokerTopView.setPlayDescribeTextSize(12);
		happyPokerTopView.setZhMissBtnTextColor(Color.BLACK);
		happyPokerTopView.removeMissCheckbox();
		happyPokerTopView.setNJkThreeDownIconShow();
		happyPokerTopView.setLotteryInfoBackColor(Color.rgb(244,222,185));
		happyPokerTopView.setElevenSelectFiveEndTimeColor(Color.BLACK);
		happyPokerTopView.setPlayMethodTextColor(playMethodTextColor);
		happyPokerTopView.setLotteryMessageTextColor(this.getResources().getColor(R.color.white));
		happyPokerTopView.addElevenSelectFiveTopViewClickListener(new ElevenSelectFiveTopViewClickListener() {
			
			/* 
			 * 普通玩法选择
			 */
			@Override
			public void TouchPTPlayMethod(int position) {
				playMethodTag=1;
				state=pt_types[position];
			}
			
			/* 
			 * 胆拖玩法选择
			 */
			@Override
			public void TouchDTPlayMethod(int position) {
				playMethodTag=2;
				state=dt_types[position];
			}
			
			/* 
			 * 摇一摇机选
			 */
			@Override
			public void ElevenSelectFiveOmission() {
				
			}
			
			/* 
			 * 头部刷新按钮
			 */
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
		return null;
	}

	@Override
	public String isTouzhu() {
		return null;
	}

	@Override
	public int getZhuShu() {
		return 0;
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
		if(playMethodTag==1){
			createPtView(checkedId);
		}else{
			createDtView(checkedId);
		}
	}
	
	/**
	 * 创建普通玩法界面
	 */
	private void createPtView(int id){
		
	}
	
	/**
	 * 创建胆拖玩法界面
	 */
	private void createDtView(int id){
		
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		radioId = checkedId;
		onCheckAction(checkedId);
	}
}
