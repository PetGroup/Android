package com.ruyicai.activity.buy.happypoker;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView.ElevenSelectFiveTopViewClickListener;
import com.ruyicai.constant.Constants;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.PublicConst;
import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
	private int[] hpArea={6,7};
	private String state;// 当前类型
	private int[] playMethodTextColor={Color.BLACK,Color.rgb(182,60, 0)};
	private int[] itemClickPicture={R.drawable.happy_poker_playmethod_normal,R.drawable.happy_poker_playmethod_click};
	private String lotno;
	public int noticeLotNo;
	private int playMethodTag=1;
	private HappyPokerCreateBall createBallView;
	private Context context=HappyPoker.this;
	public AddView addView = new AddView(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAddView(addView);
		lotnoStr=Constants.LOTNO_JLK3;
		super.lotno = Constants.LOTNO_HAPPY_POKER;
		setContentView(R.layout.activity_buy_happy_poker_main);
		state="R1";
		setLotno();
		initView();
		action();
		
		MobclickAgent.onEvent(this, "happypoker"); 
		MobclickAgent.onEvent(this, "gaopingoucaijiemian ");
	}
	
	private void setLotno(){
		this.lotno = Constants.LOTNO_HAPPY_POKER;
		this.noticeLotNo=NoticeActivityGroup.ID_SUB_HAPPY_POKER;
		lotnoStr = lotno;
	}
	
	private void action(){
		missView.clear();
		childtype = new String[] { "直选" };
		init();
		childtypes.setVisibility(View.GONE);
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
				action();
			}
			
			/* 
			 * 胆拖玩法选择
			 */
			@Override
			public void TouchDTPlayMethod(int position) {
				playMethodTag=2;
				state=dt_types[position];
				action();
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
		switch (checkedId) {
		case 0:
			if(playMethodTag==1){
				createPtView(checkedId);
			}else{
				createDtView(checkedId);
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 创建普通玩法界面
	 */
	private void createPtView(int checkedId){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		if(state.equals("DZ")){//对子玩法
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(hpArea, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] hpArea={1};
			areaNums[1] = new AreaNum(hpArea, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,editZhuma, missView, buyview, code);
			createBallView.createHappyPokerView(areaNums, sscCode, HappyPokerCreateBall.HAPPY_POKER_DUIZI,checkedId, true,iProgressBeishu);
		}else if(state.equals("SZ")){//顺子玩法
			int[] area={6,6};
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(area, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] hpArea={1};
			areaNums[1] = new AreaNum(hpArea, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,editZhuma, missView, buyview, code);
			createBallView.createHappyPokerView(areaNums, sscCode, HappyPokerCreateBall.HAPPY_POKER_SHUNZI,checkedId, true,iProgressBeishu);
		}else if(state.equals("BZ")){//豹子玩法
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(hpArea, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] hpArea={1};
			areaNums[1] = new AreaNum(hpArea, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,editZhuma, missView, buyview, code);
			createBallView.createHappyPokerView(areaNums, sscCode, HappyPokerCreateBall.HAPPY_POKER_BAOZI,checkedId, true,iProgressBeishu);
		}else if(state.equals("TH")){//同花玩法
			int[] area={4};
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(area, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] hpArea={1};
			areaNums[1] = new AreaNum(hpArea, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,editZhuma, missView, buyview, code);
			createBallView.createHappyPokerView(areaNums, sscCode, HappyPokerCreateBall.HAPPY_POKER_TONGHUA,checkedId, true,iProgressBeishu);
		}else if(state.equals("THS")){//同花顺玩法
			int[] area={4};
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(area, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] hpArea={1};
			areaNums[1] = new AreaNum(hpArea, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,editZhuma, missView, buyview, code);
			createBallView.createHappyPokerView(areaNums, sscCode, HappyPokerCreateBall.HAPPY_POKER_TONGHUASHUN,checkedId, true,iProgressBeishu);
		}else{//任选玩法
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(hpArea, 1, 16, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,editZhuma, missView, buyview, code);
			createBallView.createHappyPokerView(areaNums, sscCode, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
		}
	}
	
	/**
	 * 创建胆拖玩法界面
	 */
	private void createDtView(int checkedId){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		areaNums = new AreaNum[2];
		if(state.equals("R2")){
			areaNums[0] = new AreaNum(hpArea, 1, 1, BallResId, 0, 1,Color.RED, "胆码区","我认为必出的号码     选1个", false, false, true);
			areaNums[1] = new AreaNum(hpArea, 2, 12, BallResId, 0, 1,Color.RED, "拖码区","我认为可能出的号码    选2-12个", false, false, true);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray,editZhuma, missView, buyview, code);
			createBallView.createHappyPokerView(areaNums, sscCode, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
		}else if(state.equals("R3")){
			areaNums[0] = new AreaNum(hpArea, 1, 2, BallResId, 0, 1,Color.RED, "胆码区","我认为必出的号码     选1-2个", false, false, true);
			areaNums[1] = new AreaNum(hpArea, 3, 12, BallResId, 0, 1,Color.RED, "拖码区","我认为可能出的号码    选2-12个", false, false, true);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray,editZhuma, missView, buyview, code);
			createBallView.createHappyPokerView(areaNums, sscCode, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
		}else if(state.equals("R4")){
			areaNums[0] = new AreaNum(hpArea, 1, 3, BallResId, 0, 1,Color.RED, "胆码区","我认为必出的号码     选1-3个", false, false, true);
			areaNums[1] = new AreaNum(hpArea, 3, 12, BallResId, 0, 1,Color.RED, "拖码区","我认为可能出的号码    选2-12个", false, false, true);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray,editZhuma, missView, buyview, code);
			createBallView.createHappyPokerView(areaNums, sscCode, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
		}else if(state.equals("R5")){
			areaNums[0] = new AreaNum(hpArea, 1, 4, BallResId, 0, 1,Color.RED, "胆码区","我认为必出的号码     选1-4个", false, false, true);
			areaNums[1] = new AreaNum(hpArea, 3, 12, BallResId, 0, 1,Color.RED, "拖码区","我认为可能出的号码    选2-12个", false, false, true);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray,editZhuma, missView, buyview, code);
			createBallView.createHappyPokerView(areaNums, sscCode, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
		}else if(state.equals("R6")){
			areaNums[0] = new AreaNum(hpArea, 1, 4, BallResId, 0, 1,Color.RED, "胆码区","我认为必出的号码     选1-4个", false, false, true);
			areaNums[1] = new AreaNum(hpArea, 3, 12, BallResId, 0, 1,Color.RED, "拖码区","我认为可能出的号码    选2-12个", false, false, true);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray,editZhuma, missView, buyview, code);
			createBallView.createHappyPokerView(areaNums, sscCode, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
		}
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		radioId = checkedId;
		onCheckAction(checkedId);
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
}
