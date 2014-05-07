package com.ruyicai.activity.buy.happypoker;

import java.util.List;
import com.google.inject.Inject;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.adapter.HappyPokerLotteryAdapter;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveTopView.ElevenSelectFiveTopViewClickListener;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.controller.listerner.LotteryListener;
import com.ruyicai.controller.service.LotteryService;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.model.HistoryLotteryBean;
import com.ruyicai.model.PrizeInfoList;
import com.ruyicai.model.ReturnBean;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.ruyicai.util.json.JsonUtils;
import com.umeng.analytics.MobclickAgent;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * 快乐扑克
 *
 */
public class HappyPoker extends ZixuanAndJiXuan implements LotteryListener{
	
	private ElevenSelectFiveTopView happyPokerTopView;
	private String[] ptPlayMethod={"猜对子","猜顺子","猜豹子","猜同花","猜同花顺","任选一","任选二",
			"任选三","任选四","任选五","任选六"};
	private String[] dtPlayMethod={"任选二","任选三","任选四","任选五","任选六"};
	private String[] ptPlayMethodDescribe={"最高奖88元","最高奖400元","最高奖6400元","最高奖90元",
			"最高奖2150元","最高奖5元","最高奖33元","最高奖116元","最高奖46元","最高奖22元","最高奖12元"};
	private String[] pt_types={"DZ","SZ","BZ","TH","THS","R1","R2","R3","R4","R5","R6"};// 普通类型
	private String[] dt_types={"R2","R3","R4","R5","R6"};// 胆拖类型
	private int[] randomNum={1,1,1,1,1,1,2,3,4,5,6};
	private int[] danSelectNums={2,3,4,5,6};
	private int itemId=1;
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
	private boolean isJixuan = true;
	private int singleSelectBallNums;
	private int tongXuanBallNums;
	@Inject private LotteryService lotteryService;
	private static final int GET_PRIZEINFO_ERROR = 0;
	private static final int GET_PRIZEINFO_SUCCESS = 3;
	private ProgressDialog progressdialog;
	private HappyPokerLotteryAdapter lotteryAdapter;
	public static String batchCode;// 期号
	private int lesstime;// 剩余时间

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAddView(addView);
		lotnoStr=Constants.LOTNO_HAPPY_POKER;
		super.lotno = Constants.LOTNO_HAPPY_POKER;
		setContentView(R.layout.activity_buy_happy_poker_main);
		highttype="HappyPoker";
		state="R1";
		setLotno();
		initView();
		action();
		lotteryService.addLotteryListeners(HappyPoker.this);
		lotteryService.addLotteryTimeListeners(HappyPoker.this);
		happyPokerTopView.setElevenSelectFiveEndTime("期号获取中");
		lotteryService.setLotteryTime(context, lotno);
		RWSharedPreferences shellRW = new RWSharedPreferences(this, "addInfo");
		isJixuan = shellRW.getBooleanValue(ShellRWConstants.ISJIXUAN, true);

		MobclickAgent.onEvent(this, "happypoker"); 
		MobclickAgent.onEvent(this, "gaopingoucaijiemian ");
	}
	
	private void nextIssue() {
		try {
			new AlertDialog.Builder(HappyPoker.this)
			.setTitle("提示")
			.setMessage(
					happyPokerTopView.getElevenSelectFiveTitleText() + "第"
							+ batchCode + "期已经结束,是否转入下一期")
			.setNegativeButton("转入下一期", new Dialog.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					lotteryService.setLotteryTime(context, lotno);
				}

			})
			.setNeutralButton("返回主页面",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int which) {
							HappyPoker.this.finish();
						}
					}).create().show();
		} catch (Exception e) {
			
		}
	}
	
	private void setLotno(){
		this.lotno = Constants.LOTNO_HAPPY_POKER;
		this.noticeLotNo=NoticeActivityGroup.ID_SUB_HAPPY_POKER;
		lotnoStr = lotno;
	}
	
	void setLotoNoAndType(CodeInfo codeInfo) {
		codeInfo.setLotoNo(Constants.LOTNO_HAPPY_POKER);
		if(state.equals("DZ")){//对子玩法
			codeInfo.setTouZhuType("happy_poker_dz");
		}else if(state.equals("SZ")){//顺子玩法
			codeInfo.setTouZhuType("happy_poker_sz");
		}else if(state.equals("BZ")){//豹子玩法
			codeInfo.setTouZhuType("happy_poker_bz");
		}else if(state.equals("TH")){//同花玩法
			codeInfo.setTouZhuType("happy_poker_th");
		}else if(state.equals("THS")){//同花顺玩法
			codeInfo.setTouZhuType("happy_poker_ths");
		}else{
			codeInfo.setTouZhuType("happy_poker_rx");
		}
	}
	
	void setLotoNoAndType2(CodeInfo codeInfo) {
		codeInfo.setLotoNo(Constants.LOTNO_HAPPY_POKER);
		if(state.equals("DZ")){//对子玩法
			codeInfo.setTouZhuType("dz_tong");
		}else if(state.equals("SZ")){//顺子玩法
			codeInfo.setTouZhuType("sz_tong");
		}else if(state.equals("BZ")){//豹子玩法
			codeInfo.setTouZhuType("bz_tong");
		}else if(state.equals("TH")){//同花玩法
			codeInfo.setTouZhuType("th_tong");
		}else if(state.equals("THS")){//同花顺玩法
			codeInfo.setTouZhuType("ths_tong");
		}
	}
	
	/**
	 * 对子、顺子、豹子、同花、同花顺通选个数
	 */
	int getThreeLinkZhuShu() {
		return tongXuanBallNums;
	}

	/**
	 * 对子、顺子、豹子、同花、同花顺单选个数
	 */
	int getThreeDiffZhuShu() {
		return singleSelectBallNums;
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
				itemId=randomNum[position];
				action();
			}
			
			/* 
			 * 胆拖玩法选择
			 */
			@Override
			public void TouchDTPlayMethod(int position) {
				playMethodTag=2;
				state=dt_types[position];
				itemId=danSelectNums[position];
				action();
			}
			
			/* 
			 * 摇一摇机选
			 */
			@Override
			public void ElevenSelectFiveOmission() {
				if(isJixuan&&playMethodTag==1){
					baseSensor.action();
				}
			}
			
			/* 
			 * 头部刷新按钮
			 */
			@Override
			public void ElevenSelectFiveFresh() {
				progressdialog=PublicMethod.creageProgressDialog(HappyPoker.this);
				lotteryService.getNoticePrizeInfoList(Constants.LOTNO_HAPPY_POKER);
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
	public String isTouzhu() {
		if(playMethodTag==2){
			if(getZhuShu()>1){
				return "true";
			}else{
				String tempStr="";
                if(state.equals("R2")){
                	tempStr="请选择:\n1个胆码;\n2~12个拖码";
				}else if(state.equals("R3")){
					tempStr="请选择:\n1~2个胆码;\n2~12个拖码";
				}else if(state.equals("R4")){
					tempStr="请选择:\n1~3个胆码;\n2~12个拖码";
				}else if(state.equals("R5")||state.equals("R6")){
					tempStr="请选择:\n1~4个胆码;\n2~12个拖码";
				}
				return tempStr;
			}
		}else{
			if (getZhuShu() == 0) {
				return "请至少选择一注";
			}else if (getZhuShu() > 10000) {
				return "false";
			} else {
				return "true";
			}
		}
	}

	@Override
	public int getZhuShu() {
		int zhushu=0;
		if(playMethodTag==2){
			int dan = areaNums[0].table.getHighlightBallNums();
			int tuo = areaNums[1].table.getHighlightBallNums();
			if((dan+tuo)>=3){
				zhushu = (int) getDTZhuShu(dan, tuo, iProgressBeishu);
			}else{
				zhushu = 0;
			}
		}else{
			if(state.equals("DZ")
					||state.equals("SZ")
					||state.equals("BZ")
					||state.equals("TH")
					||state.equals("THS")){
				// 获取三不同号单选择的小球个数
				singleSelectBallNums = areaNums[0].table.getHighlightBallNums();
				// 获取三连号通选选择小球的个数
				tongXuanBallNums = areaNums[1].table.getHighlightBallNums();
				zhushu=singleSelectBallNums+tongXuanBallNums;
			}else{
				int ballNums = areaNums[0].table.getHighlightBallNums();
				zhushu = (int) PublicMethod.zuhe(itemId, ballNums)* iProgressBeishu;
			}
		}
		return zhushu;
	}
	
	protected long getDTZhuShu(int dan, int tuo, int iProgressBeishu) {
		long ssqZhuShu = 0L;
		if (dan > 0 && tuo > 0) {
			ssqZhuShu += (PublicMethod.zuhe(itemId - dan, tuo) * iProgressBeishu);
		}
		return ssqZhuShu;
	}

	@Override
	public String getZhuma() {
		// 拼接投注的注码格式，用户投注与后台使用
		String zhuMa = "";
		if(playMethodTag==1){
			zhuMa=getHappyPokerZhuMa();
		}else{
			zhuMa=getHappyPokerDtZhuMa();
		}
		return zhuMa;

	}
	
	String getZhuma2() {
		// 拼接投注的注码格式，用户投注与后台使用
		String zhuMa = "";
		zhuMa = getHappyPokerTongXuanZhuMa();
		return zhuMa;
	}
	
	private String getHappyPokerZhuMa(){
		String str = "";
		if(state.equals("DZ")){
			str += "DZ|";
		}else if(state.equals("SZ")){
			str += "SZ|";
		}else if(state.equals("BZ")){
			str += "BZ|";
		}else if(state.equals("TH")){
			str += "TH|";
		}else if(state.equals("THS")){
			str += "HS|";
		}else{
			str += "R"+itemId+"|";
		}
		int[] RxZhuShu = areaNums[0].table.getHighlightBallNOs();
		str += PublicMethod.getRstring(RxZhuShu);
		return str;
	}
	
	private String getHappyPokerDtZhuMa(){
		String str = "";
		str += "R"+itemId+"|";
		int[] RxDmZhuShu = areaNums[0].table.getHighlightBallNOs();
		int[] RxTmZhuShu = areaNums[1].table.getHighlightBallNOs();
		str += PublicMethod.getRstring(RxDmZhuShu);
		str +="$";
		str += PublicMethod.getRstring(RxTmZhuShu);
		return str;
	}
	
	private String getHappyPokerTongXuanZhuMa(){
		String str = "";
		if(state.equals("DZ")){
			str += "BX|11";
		}else if(state.equals("SZ")){
			str += "BX|09";
		}else if(state.equals("BZ")){
			str += "BX|10";
		}else if(state.equals("TH")){
			str += "BX|07";
		}else if(state.equals("THS")){
			str += "BX|08";
		}
		return str;
	}
	
	@Override
	public String getZhuma(Balls ball) {
		return null;
	}

	@Override
	public void touzhuNet() {
		betAndGift.setLotno(Constants.LOTNO_HAPPY_POKER);
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
				startSensor();
				createPtView(checkedId);
			}else{
				stopSensor();
				createDtView(checkedId);
			}
			lotteryAdapter=new HappyPokerLotteryAdapter(HappyPoker.this);
			lotteryService.getNoticePrizeInfoList(Constants.LOTNO_HAPPY_POKER);
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
			highttype="HAPPY_POKER_DZ";
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(hpArea, 1, 13, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] hpArea={1};
			areaNums[1] = new AreaNum(hpArea, 0, 1, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,missView, buyview, sscCode);
			createBallView.createHappyPokerView(areaNums, HappyPokerCreateBall.HAPPY_POKER_DUIZI,checkedId, true,iProgressBeishu);
		}else if(state.equals("SZ")){//顺子玩法
			highttype="HAPPY_POKER_SZ";
			int[] area={6,6};
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(area, 1, 12, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] hpArea={1};
			areaNums[1] = new AreaNum(hpArea, 0, 1, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,missView, buyview, sscCode);
			createBallView.createHappyPokerView(areaNums, HappyPokerCreateBall.HAPPY_POKER_SHUNZI,checkedId, true,iProgressBeishu);
		}else if(state.equals("BZ")){//豹子玩法
			highttype="HAPPY_POKER_BZ";
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(hpArea, 1, 13, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] hpArea={1};
			areaNums[1] = new AreaNum(hpArea, 0, 1, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,missView, buyview, sscCode);
			createBallView.createHappyPokerView(areaNums, HappyPokerCreateBall.HAPPY_POKER_BAOZI,checkedId, true,iProgressBeishu);
		}else if(state.equals("TH")){//同花玩法
			highttype="HAPPY_POKER_TH";
			int[] area={4};
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(area, 1, 4, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] hpArea={1};
			areaNums[1] = new AreaNum(hpArea, 0, 1, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,missView, buyview, sscCode);
			createBallView.createHappyPokerView(areaNums, HappyPokerCreateBall.HAPPY_POKER_TONGHUA,checkedId, true,iProgressBeishu);
		}else if(state.equals("THS")){//同花顺玩法
			highttype="HAPPY_POKER_THS";
			int[] area={4};
			areaNums = new AreaNum[2];
			areaNums[0] = new AreaNum(area, 1, 4, BallResId, 0, 1,Color.RED, "","", false, true, false);
			int[] hpArea={1};
			areaNums[1] = new AreaNum(hpArea, 0, 1, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,missView, buyview, sscCode);
			createBallView.createHappyPokerView(areaNums, HappyPokerCreateBall.HAPPY_POKER_TONGHUASHUN,checkedId, true,iProgressBeishu);
		}else{//任选玩法
			highttype="HAPPY_POKER_RX";
			areaNums = new AreaNum[1];
			areaNums[0] = new AreaNum(hpArea, itemId, 13, BallResId, 0, 1,Color.RED, "","", false, true, false);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray
					,missView, buyview, sscCode);
			createBallView.createHappyPokerView(areaNums, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
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
			highttype="HAPPY_POKER_RX";
			areaNums[0] = new AreaNum(hpArea, 1, 1, BallResId, 0, 1,Color.RED, "胆码区","我认为必出的号码     选1个", false, false, true);
			areaNums[1] = new AreaNum(hpArea, 2, 12, BallResId, 0, 1,Color.RED, "拖码区","我认为可能出的号码    选2-12个", false, false, true);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray,missView, buyview, sscCode);
			createBallView.createHappyPokerView(areaNums, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
		}else if(state.equals("R3")){
			highttype="HAPPY_POKER_RX";
			areaNums[0] = new AreaNum(hpArea, 1, 2, BallResId, 0, 1,Color.RED, "胆码区","我认为必出的号码     选1-2个", false, false, true);
			areaNums[1] = new AreaNum(hpArea, 3, 12, BallResId, 0, 1,Color.RED, "拖码区","我认为可能出的号码    选2-12个", false, false, true);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray,missView, buyview, sscCode);
			createBallView.createHappyPokerView(areaNums, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
		}else if(state.equals("R4")){
			highttype="HAPPY_POKER_RX";
			areaNums[0] = new AreaNum(hpArea, 1, 3, BallResId, 0, 1,Color.RED, "胆码区","我认为必出的号码     选1-3个", false, false, true);
			areaNums[1] = new AreaNum(hpArea, 3, 12, BallResId, 0, 1,Color.RED, "拖码区","我认为可能出的号码    选2-12个", false, false, true);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray,missView, buyview, sscCode);
			createBallView.createHappyPokerView(areaNums, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
		}else if(state.equals("R5")){
			highttype="HAPPY_POKER_RX";
			areaNums[0] = new AreaNum(hpArea, 1, 4, BallResId, 0, 1,Color.RED, "胆码区","我认为必出的号码     选1-4个", false, false, true);
			areaNums[1] = new AreaNum(hpArea, 3, 12, BallResId, 0, 1,Color.RED, "拖码区","我认为可能出的号码    选2-12个", false, false, true);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray,missView, buyview, sscCode);
			createBallView.createHappyPokerView(areaNums, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
		}else if(state.equals("R6")){
			highttype="HAPPY_POKER_RX";
			areaNums[0] = new AreaNum(hpArea, 1, 4, BallResId, 0, 1,Color.RED, "胆码区","我认为必出的号码     选1-4个", false, false, true);
			areaNums[1] = new AreaNum(hpArea, 3, 12, BallResId, 0, 1,Color.RED, "拖码区","我认为可能出的号码    选2-12个", false, false, true);
			createBallView=new HappyPokerCreateBall(context,inflater,addView,itemViewArray,missView, buyview, sscCode);
			createBallView.createHappyPokerView(areaNums, HappyPokerCreateBall.HAPPY_POKER_OTHER,checkedId, true,iProgressBeishu);
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
			iBallId = iBallId - areaNums[i].areaNum*2;
			if (iBallId < 0) {
				if (playMethodTag == 2) {
					if (i == 0) {
						int isHighLight = areaNums[0].table.changeHPBallState(
								areaNums[0].chosenBallSum, nBallId);
						if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT
								&& areaNums[1].table.getOneBallStatue(nBallId) != 1) {
							areaNums[1].table.clearOnBallHighlight(nBallId);
						}
					} else if (i == 1) {
						int isHighLight = areaNums[1].table.changeHPBallState(
								areaNums[1].chosenBallSum, nBallId);
						if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT
								&& areaNums[0].table.getOneBallStatue(nBallId) != 1) {
							areaNums[0].table.clearOnBallHighlight(nBallId);
						}
					}
				} else {
					areaNums[i].table.changeHPBallState(
							areaNums[i].chosenBallSum, nBallId);
				}
				break;
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		lotteryService.removeLotteryListeners(HappyPoker.this);
		lotteryService.removeLotteryTimeListeners(HappyPoker.this);
	}

	@Override
	public void updateLatestLotteryList(String lotno) {
		
	}

	@Override
	public void updateNoticePrizeInfo(String lotno, PrizeInfoList prizeInfoList) {
		if (Constants.LOTNO_HAPPY_POKER.equals(lotno)) {
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
				Toast.makeText(HappyPoker.this, "历史获奖信息获取失败..." + msgString,
						Toast.LENGTH_LONG).show();
				break;
			case GET_PRIZEINFO_SUCCESS:
				String data=(String) msg.getData().get("result");
				List<HistoryLotteryBean> lotteryData=JsonUtils.getList(data, HistoryLotteryBean.class);
				if(lotteryData!=null){
					lotteryAdapter.setLotteryList(lotteryData);
					createBallView.happyPokerLotteryListView.setAdapter(lotteryAdapter);
				}
				PublicMethod.closeProgressDialog(progressdialog);
				break;
			}

		}
	};

	@Override
	public void updateLotteryCountDown(String lotNo,final String batchCode, int time) {
		if(Constants.LOTNO_HAPPY_POKER.equals(lotNo)){
			this.batchCode=batchCode;
			lesstime = time;
			final Handler sscHandler = new Handler();
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while (lesstime > 0) {
							sscHandler.post(new Runnable() {
								public void run() {
									happyPokerTopView.setElevenSelectFiveEndTime("距"
											+ (batchCode != null
													&& !"".equals(batchCode) ? batchCode
													.substring(batchCode.length() - 2)
													: "")
											+ "期截止:"
											+ PublicMethod.isTen(lesstime / 60)
											+ "分"
											+ PublicMethod.isTen(lesstime % 60)
											+ "秒");
								}
							});
							Thread.sleep(1000);
							lesstime--;

						}
						sscHandler.post(new Runnable() {
							public void run() {
								happyPokerTopView.setElevenSelectFiveEndTime("距"
										+ batchCode.substring(8) + "期截止:00分00秒");
								nextIssue();
							}
						});
					} catch (Exception e) {
						sscHandler.post(new Runnable() {
							public void run() {
								happyPokerTopView
										.setElevenSelectFiveEndTime("获取期号失败");
							}
						});
					}

				}
			});
			thread.start();
		}
	}
	
}
