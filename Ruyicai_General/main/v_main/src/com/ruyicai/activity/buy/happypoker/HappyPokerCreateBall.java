package com.ruyicai.activity.buy.happypoker;

import java.util.List;
import java.util.Map;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.high.HighItemView;
import com.ruyicai.activity.buy.high.ZixuanAndJiXuan;
import com.ruyicai.activity.buy.miss.BuyViewItemMiss;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.code.CodeInterface;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.pojo.OneBallView;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class HappyPokerCreateBall implements OnClickListener {
	
	private LayoutInflater inflater;
	private AddView addView;
	private List<BuyViewItemMiss> itemViewArray;
	private EditText editZhuma;
	private Map<Integer, HighItemView> missView;
	private LinearLayout buyview;
	private CodeInterface code;
	private Context context;
	private boolean istoShowLotteryHistory=true;
	private int iScreenWidth;
	private AreaNum areaNums[];
	private int iProgressBeishu;
	public final static int HAPPY_POKER_OTHER=1;
	public final static int HAPPY_POKER_TONGHUA=2;//同花
	public final static int HAPPY_POKER_TONGHUASHUN=3;//同花顺
	public final static int HAPPY_POKER_DUIZI=4;//对子
	public final static int HAPPY_POKER_BAOZI=5;//豹子
	public final static int HAPPY_POKER_SHUNZI=6;//顺子
	
	
	public HappyPokerCreateBall(Context context,LayoutInflater inflater,AddView addView,List<BuyViewItemMiss> itemViewArray
			,EditText editZhuma,Map<Integer, HighItemView> missView,LinearLayout buyview,CodeInterface code){
		this.context=context;
		this.inflater=inflater;
		this.addView=addView;
		this.itemViewArray=itemViewArray;
		this.editZhuma=editZhuma;
		this.missView=missView;
		this.buyview=buyview;
		this.code=code;
	}
	
	public void createHappyPokerView(AreaNum areaNum[], CodeInterface code, int type,
			int id, boolean isMiss,int iProgressBeishu){
		this.areaNums=areaNum;
		this.iProgressBeishu=iProgressBeishu;
		this.code = code;
		buyview.removeAllViews();
		if (missView.get(id) == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View zhixuanview = inflater.inflate(R.layout.activity_happy_poker,null);
			final Button isToHideLotteryView=(Button) zhixuanview.findViewById(R.id.isToHideLotteryView);
			TextView tongXuanMessageText=(TextView) zhixuanview.findViewById(R.id.happy_poker_tong_xuan_message_text);
			((ZixuanAndJiXuan) context).initZixuanView(zhixuanview);
			initView(areaNum, zhixuanview, isMiss, type);
			((ZixuanAndJiXuan) context).initBotm(zhixuanview);
			missView.put(id, new HighItemView(zhixuanview, areaNum, addView,itemViewArray, editZhuma));
			if(type==HAPPY_POKER_TONGHUA){
				tongXuanMessageText.setVisibility(View.VISIBLE);
				tongXuanMessageText.setText("同花包选：任意数字，只要开出同花即中500元");
			}else if(type==HAPPY_POKER_TONGHUASHUN){
				tongXuanMessageText.setVisibility(View.VISIBLE);
				tongXuanMessageText.setText("同花顺包选：任意花色，只要开出同花即中500元");
			}else if(type==HAPPY_POKER_DUIZI){
				tongXuanMessageText.setVisibility(View.VISIBLE);
				tongXuanMessageText.setText("对子包选：任意数字，只要开出对子即中7元");
			}else if(type==HAPPY_POKER_BAOZI){
				tongXuanMessageText.setVisibility(View.VISIBLE);
				tongXuanMessageText.setText("豹子包选：任意数字，只要开出对子即中500元");
			}else if(type==HAPPY_POKER_SHUNZI){
				tongXuanMessageText.setVisibility(View.VISIBLE);
				tongXuanMessageText.setText("顺子包选：任意数字，只要开出对子即中500元");
			}
			isToHideLotteryView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(istoShowLotteryHistory){
						istoShowLotteryHistory=false;
						isToHideLotteryView.setBackgroundResource(R.drawable.happy_poker_lottery_click);
					}else{
						istoShowLotteryHistory=true;
						isToHideLotteryView.setBackgroundResource(R.drawable.happy_poker_lottery_normal);
					}
				}
			});
		} else {
			((ZixuanAndJiXuan) context).refreshView(type, id);
		}
	}
	
	/**
	 * 初始化快乐扑克选区
	 */
	private void initView(AreaNum[] areaNums, View zhixuanview,boolean isMiss, int type){
		iScreenWidth=PublicMethod.getDisplayWidth(context);
		int tableLayoutIds[]={R.id.buy_zixuan_table_one,
				R.id.buy_zixuan_table_two, R.id.buy_zixuan_table_third,
				R.id.buy_zixuan_table_four, R.id.buy_zixuan_table_five};
		int textViewIds[] = { R.id.buy_zixuan_text_one,
				R.id.buy_zixuan_text_two, R.id.buy_zixuan_text_third,
				R.id.buy_zixuan_text_four, R.id.buy_zixuan_text_five };
		int textViewTishiIds[] = { R.id.buy_zixuan_tishi_text_one,
				R.id.buy_zixuan_tishi_text_two,
				R.id.buy_zixuan_tishi_text_third,
				R.id.buy_zixuan_tishi_text_four,
				R.id.buy_zixuan_tishi_text_five };
		int linearViewIds[] = { R.id.buy_zixuan_linear_one,
				R.id.buy_zixuan_linear_two, R.id.buy_zixuan_linear_third,
				R.id.buy_zixuan_linear_four, R.id.buy_zixuan_linear_five };
		for (int i = 0; i < areaNums.length; i++) {
			areaNums[i].initView(tableLayoutIds[i], textViewIds[i],linearViewIds[i], textViewTishiIds[i], zhixuanview);
			AreaNum areaNum = areaNums[i];
			if (i != 0) {
				areaNum.aIdStart = areaNums[i - 1].areaNum+ areaNums[i - 1].aIdStart;
			}
			areaNums[i].table = makeBallTable(areaNums[i].tableLayout,
					iScreenWidth, areaNum.areaNum, areaNum.ballResId,
					areaNum.aIdStart, areaNum.aBallViewText, context, this, true,
					null, isMiss, type, i, areaNum.area);
			areaNums[i].init(type);
			areaNums[i].initTishi(type);
			if (!TextUtils.isEmpty(areaNums[i].textTtitle)) {
				areaNums[i].initTextBgColor(Color.rgb(177, 96, 0), Color.rgb(177, 96, 0));
				areaNums[i].initTextColor(Color.WHITE,Color.WHITE);
			}
		}
		
		for (int i = 0; i < areaNums.length; i++) {
			int rowNum = areaNums[i].table.ballViewVector.size();
			for (int row_j = 0; row_j < rowNum; row_j++) {
				
			}
		}
	}

	/**
	 * 
	 * 创建快乐扑克BallTable
	 */
	private BallTable makeBallTable(TableLayout tableLayout, int aFieldWidth,
			int aBallNum, int[] aResId, int aIdStart, int aBallViewText,
			Context context, OnClickListener onclick, boolean isTen,
			List<String> missValues, boolean isMiss, int type, int area,
			int[] areaNum) {
		TableLayout tabble = tableLayout;
		BallTable iBallTable = new BallTable(aIdStart, context);
		int iBallViewNo = 0;
		int[] rankInt = null;
		if (missValues != null) {
			rankInt = rankList(missValues);
		}
		/* 任选玩法图片资源  */
		int[][] happyPokerRXPic={
				{R.drawable.happy_poker_rx_dice1_normal,R.drawable.happy_poker_rx_dice1_click},
				{R.drawable.happy_poker_rx_dice2_normal,R.drawable.happy_poker_rx_dice2_click},
				{R.drawable.happy_poker_rx_dice3_normal,R.drawable.happy_poker_rx_dice3_click},
				{R.drawable.happy_poker_rx_dice4_normal,R.drawable.happy_poker_rx_dice4_click},
				{R.drawable.happy_poker_rx_dice5_normal,R.drawable.happy_poker_rx_dice5_click},
				{R.drawable.happy_poker_rx_dice6_normal,R.drawable.happy_poker_rx_dice6_click},
				{R.drawable.happy_poker_rx_dice7_normal,R.drawable.happy_poker_rx_dice7_click},
				{R.drawable.happy_poker_rx_dice8_normal,R.drawable.happy_poker_rx_dice8_click},
				{R.drawable.happy_poker_rx_dice9_normal,R.drawable.happy_poker_rx_dice9_click},
				{R.drawable.happy_poker_rx_dice10_normal,R.drawable.happy_poker_rx_dice10_click},
				{R.drawable.happy_poker_rx_dice11_normal,R.drawable.happy_poker_rx_dice11_click},
				{R.drawable.happy_poker_rx_dice12_normal,R.drawable.happy_poker_rx_dice12_click},
				{R.drawable.happy_poker_rx_dice13_normal,R.drawable.happy_poker_rx_dice13_click},
			};
		/* 对子玩法图片资源  */
		int[][] happyPokerDZPic={
				{R.drawable.happy_poker_dz_dice1_normal,R.drawable.happy_poker_dz_dice1_click},
				{R.drawable.happy_poker_dz_dice2_normal,R.drawable.happy_poker_dz_dice2_click},
				{R.drawable.happy_poker_dz_dice3_normal,R.drawable.happy_poker_dz_dice3_click},
				{R.drawable.happy_poker_dz_dice4_normal,R.drawable.happy_poker_dz_dice4_click},
				{R.drawable.happy_poker_dz_dice5_normal,R.drawable.happy_poker_dz_dice5_click},
				{R.drawable.happy_poker_dz_dice6_normal,R.drawable.happy_poker_dz_dice6_click},
				{R.drawable.happy_poker_dz_dice7_normal,R.drawable.happy_poker_dz_dice7_click},
				{R.drawable.happy_poker_dz_dice8_normal,R.drawable.happy_poker_dz_dice8_click},
				{R.drawable.happy_poker_dz_dice9_normal,R.drawable.happy_poker_dz_dice9_click},
				{R.drawable.happy_poker_dz_dice10_normal,R.drawable.happy_poker_dz_dice10_click},
				{R.drawable.happy_poker_dz_dice11_normal,R.drawable.happy_poker_dz_dice11_click},
				{R.drawable.happy_poker_dz_dice12_normal,R.drawable.happy_poker_dz_dice12_click},
				{R.drawable.happy_poker_dz_dice13_normal,R.drawable.happy_poker_dz_dice13_click},
			};
		/* 豹子玩法图片资源  */
		int[][] happyPokerBZPic={
				{R.drawable.happy_poker_bz_dice1_normal,R.drawable.happy_poker_bz_dice1_click},
				{R.drawable.happy_poker_bz_dice2_normal,R.drawable.happy_poker_bz_dice2_click},
				{R.drawable.happy_poker_bz_dice3_normal,R.drawable.happy_poker_bz_dice3_click},
				{R.drawable.happy_poker_bz_dice4_normal,R.drawable.happy_poker_bz_dice4_click},
				{R.drawable.happy_poker_bz_dice5_normal,R.drawable.happy_poker_bz_dice5_click},
				{R.drawable.happy_poker_bz_dice6_normal,R.drawable.happy_poker_bz_dice6_click},
				{R.drawable.happy_poker_bz_dice7_normal,R.drawable.happy_poker_bz_dice7_click},
				{R.drawable.happy_poker_bz_dice8_normal,R.drawable.happy_poker_bz_dice8_click},
				{R.drawable.happy_poker_bz_dice9_normal,R.drawable.happy_poker_bz_dice9_click},
				{R.drawable.happy_poker_bz_dice10_normal,R.drawable.happy_poker_bz_dice10_click},
				{R.drawable.happy_poker_bz_dice11_normal,R.drawable.happy_poker_bz_dice11_click},
				{R.drawable.happy_poker_bz_dice12_normal,R.drawable.happy_poker_bz_dice12_click},
				{R.drawable.happy_poker_bz_dice13_normal,R.drawable.happy_poker_bz_dice13_click},
			};
		/* 顺子玩法图片资源  */
		int[][] happyPokerSZPic={
				{R.drawable.happy_poker_sz_dice1_normal,R.drawable.happy_poker_sz_dice1_click},
				{R.drawable.happy_poker_sz_dice2_normal,R.drawable.happy_poker_sz_dice2_click},
				{R.drawable.happy_poker_sz_dice3_normal,R.drawable.happy_poker_sz_dice3_click},
				{R.drawable.happy_poker_sz_dice4_normal,R.drawable.happy_poker_sz_dice4_click},
				{R.drawable.happy_poker_sz_dice5_normal,R.drawable.happy_poker_sz_dice5_click},
				{R.drawable.happy_poker_sz_dice6_normal,R.drawable.happy_poker_sz_dice6_click},
				{R.drawable.happy_poker_sz_dice7_normal,R.drawable.happy_poker_sz_dice7_click},
				{R.drawable.happy_poker_sz_dice8_normal,R.drawable.happy_poker_sz_dice8_click},
				{R.drawable.happy_poker_sz_dice9_normal,R.drawable.happy_poker_sz_dice9_click},
				{R.drawable.happy_poker_sz_dice10_normal,R.drawable.happy_poker_sz_dice10_click},
				{R.drawable.happy_poker_sz_dice11_normal,R.drawable.happy_poker_sz_dice11_click},
				{R.drawable.happy_poker_sz_dice12_normal,R.drawable.happy_poker_sz_dice12_click},
			};
		/* 同花玩法图片资源  */
		int[][] happyPokerTHPic={
				{R.drawable.happy_poker_th_hongxin_normal,R.drawable.happy_poker_th_hongxin_click},
				{R.drawable.happy_poker_th_heitao_normal,R.drawable.happy_poker_th_heitao_click},
				{R.drawable.happy_poker_th_meihua_normal,R.drawable.happy_poker_th_meihua_click},
				{R.drawable.happy_poker_th_fangkuai_normal,R.drawable.happy_poker_th_fangkuai_click},
			};
		/* 同花顺玩法图片资源  */
		int[][] happyPokerTHSPic={
				{R.drawable.happy_poker_hongxinshun_normal,R.drawable.happy_poker_hongxinshun_click},
				{R.drawable.happy_poker_heitaoshun_normal,R.drawable.happy_poker_heitaoshun_click},
				{R.drawable.happy_poker_meihuashun_normal,R.drawable.happy_poker_meihuashun_click},
				{R.drawable.happy_poker_fangkuaishun_normal,R.drawable.happy_poker_fangkuaishun_click},
			};
		/* 对子通选图片资源  */
		int[] duiZiTongXuan={R.drawable.happy_poker_dz_tong_normal,R.drawable.happy_poker_dz_tong_click};
		/* 豹子通选图片资源  */
		int[] baoZiTongXuan={R.drawable.happy_poker_bz_tong_normal,R.drawable.happy_poker_bz_tong_click};
		/* 顺子通选图片资源  */
		int[] shunZiTongXuan={R.drawable.happy_poker_sz_tong_normal,R.drawable.happy_poker_sz_tong_click};
		/* 同花通选图片资源  */
		int[] tongHuaTongXuan={R.drawable.happy_poker_th_tong_normal,R.drawable.happy_poker_th_tong_click};
		/* 同花顺通选图片资源  */
		int[] tongHuaShunTongXuan={R.drawable.happy_poker_ths_tong_normal,R.drawable.happy_poker_ths_tong_click};
		
		for (int i = 0; i < areaNum.length; i++) {
			TableRow tableRowText = new TableRow(context);
			TableRow tableRow = new TableRow(context);
			tableRow.setGravity(Gravity.CENTER_HORIZONTAL);
			tableRowText.setGravity(Gravity.CENTER);
			for(int j=0;j<areaNum[i];j++){
				RelativeLayout layout=new RelativeLayout(context);
				int viewId=aIdStart + iBallViewNo;
				OneBallView tempView = null;
				OneBallView sameView = null;
				if(type==HAPPY_POKER_DUIZI){//对子玩法
					if(areaNum[i]==1){
						tempView =createOneBallViewTong(i,j, areaNum, viewId,onclick,duiZiTongXuan,true);
					}else{
						tempView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerDZPic,true);
					}
				}else if(type==HAPPY_POKER_SHUNZI){//顺子玩法
					if(areaNum[i]==1){
						tempView =createOneBallViewTong(i,j, areaNum, viewId,onclick,shunZiTongXuan,true);
					}else{
						tempView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerSZPic,true);
					}
				}else if(type==HAPPY_POKER_BAOZI){//豹子玩法
					if(areaNum[i]==1){
						tempView =createOneBallViewTong(i,j, areaNum, viewId,onclick,baoZiTongXuan,true);
					}else{
						tempView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerBZPic,true);
					}
				}else if(type==HAPPY_POKER_TONGHUA){//同花玩法
					if(areaNum[i]==1){
						tempView =createOneBallViewTong(i,j, areaNum, viewId,onclick,tongHuaTongXuan,true);
					}else{
						tempView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerTHPic,true);
					}
                }else if(type==HAPPY_POKER_TONGHUASHUN){//同花顺玩法
                	if(areaNum[i]==1){
						tempView =createOneBallViewTong(i,j, areaNum, viewId,onclick,tongHuaShunTongXuan,true);
					}else{
						tempView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerTHSPic,true);
					}
				}else{//任选玩法
					tempView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerRXPic,true);
				}
				if((type==HAPPY_POKER_DUIZI
						||type==HAPPY_POKER_SHUNZI
						||type==HAPPY_POKER_BAOZI
						||type==HAPPY_POKER_TONGHUA
						||type==HAPPY_POKER_TONGHUASHUN)&&areaNum[i]==1){
					layout.addView(tempView,new LayoutParams(230,100));
				}else{
					if(type==HAPPY_POKER_TONGHUA||type==HAPPY_POKER_TONGHUASHUN){
						RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(
								65, 90); 
						layout.addView(tempView,mLayoutParams);
					}else{
						layout.addView(tempView,new LayoutParams(65,90));
					}
				}
				
				if(type==HAPPY_POKER_DUIZI){//对子玩法
					if(areaNum[i]==1){
						sameView =createOneBallViewTong(i,j, areaNum, viewId,onclick,duiZiTongXuan,false);
					}else{
						sameView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerDZPic,false);
					}
				}else if(type==HAPPY_POKER_SHUNZI){//顺子玩法
					if(areaNum[i]==1){
						sameView =createOneBallViewTong(i,j, areaNum, viewId,onclick,shunZiTongXuan,false);
					}else{
						sameView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerSZPic,false);
					}
				}else if(type==HAPPY_POKER_BAOZI){//豹子玩法
					if(areaNum[i]==1){
						sameView =createOneBallViewTong(i,j, areaNum, viewId,onclick,baoZiTongXuan,false);
					}else{
						sameView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerBZPic,false);
					}
				}else if(type==HAPPY_POKER_TONGHUA){//同花玩法
					if(areaNum[i]==1){
						sameView =createOneBallViewTong(i,j, areaNum, viewId,onclick,tongHuaTongXuan,false);
					}else{
						sameView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerTHPic,false);
					}
                }else if(type==HAPPY_POKER_TONGHUASHUN){//同花顺玩法
                	if(areaNum[i]==1){
						sameView =createOneBallViewTong(i,j, areaNum, viewId,onclick,tongHuaShunTongXuan,false);
					}else{
						sameView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerTHSPic,false);
					}
				}else{//任选玩法
					sameView =createOneBallView(i,j, areaNum, viewId,onclick,happyPokerRXPic,false);
				}
				
				RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(
						65, 90);
				mLayoutParams.topMargin = 20;
//				mLayoutParams.addRule(RelativeLayout.BELOW, aIdStart + iBallViewNo);
//				mLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                if((type==HAPPY_POKER_DUIZI
						||type==HAPPY_POKER_SHUNZI
						||type==HAPPY_POKER_BAOZI
						||type==HAPPY_POKER_TONGHUA
						||type==HAPPY_POKER_TONGHUASHUN)&&areaNum[i]==1){
                	mLayoutParams = new RelativeLayout.LayoutParams(230, 100);
                	mLayoutParams.topMargin = 20;
					layout.addView(sameView, mLayoutParams);
				}else{
					if(type==HAPPY_POKER_TONGHUA||type==HAPPY_POKER_TONGHUASHUN){
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
								65, 90); 
						params.topMargin = 20;
						layout.addView(sameView,params);
					}else{
						layout.addView(sameView, mLayoutParams);
					}
				}
				
				/**
				 * 开始画遗漏值
				 */
				TextView textView;
				textView = PaindMiss(missValues, iBallViewNo, rankInt,R.drawable.happy_poker_miss_bg);
				TableRow.LayoutParams lpMiss = new TableRow.LayoutParams();
				lpMiss.setMargins(0, 5, 0, 5);
				tableRowText.addView(textView, lpMiss);
				iBallTable.textList.add(textView);
				
				iBallTable.addBallView(tempView);
				iBallTable.addBallView(sameView);
				
				if((type==HAPPY_POKER_TONGHUA||type==HAPPY_POKER_TONGHUASHUN)&&areaNum[i]!=1){
					TableRow.LayoutParams lp = new TableRow.LayoutParams();
					if(j==0){
						lp.setMargins(0, 0, 10, 0);
					}else if(j==areaNum[i]-1){
						lp.setMargins(10, 0, 0, 0);
					}else{
						lp.setMargins(10, 0, 10, 0);
					}
					tableRow.addView(layout,lp);
				}else{
					tableRow.addView(layout);
				}
				viewId++;
			}
//			tableRowText.setBackgroundResource(R.drawable.happy_poker_miss_bg);
			tabble.addView(tableRow, new TableLayout.LayoutParams(PublicConst.WC, PublicConst.WC));
			tabble.addView(tableRowText, new TableLayout.LayoutParams(PublicConst.WC, PublicConst.WC));
		}
		return iBallTable;
	}
	
	/**
	 * 创建一个oneballview
	 * 
	 */
	private OneBallView createOneBallView(int i,int j,int[] areaNum,int viewId,OnClickListener onclick,
			int[][] happyPokerPic,boolean flag){
		OneBallView tempView =new OneBallView(context);
		if(flag){
			tempView.setOnClick(true);
		}
		if(i==areaNum.length-1&&i!=0){
			tempView.initBg(happyPokerPic[j+6]);
		}else{
			tempView.initBg(happyPokerPic[j]);
		}
		tempView.switchBg();
		tempView.setAdjustViewBounds(true);
		tempView.setId(viewId);
		tempView.setOnClickListener(onclick);
		return tempView;
	}
	
	/**
	 * 
	 * 创建一个通选oneballview
	 */
	private OneBallView createOneBallViewTong(int i,int j,int[] areaNum,int viewId,OnClickListener onclick,
			int[] happyPokerPic,boolean flag){
		OneBallView tempView =new OneBallView(context);
		if(flag){
			tempView.setOnClick(true);
		}
		tempView.initBg(happyPokerPic);
		tempView.switchBg();
		tempView.setAdjustViewBounds(true);
		tempView.setId(viewId);
		tempView.setOnClickListener(onclick);
		return tempView;
	}

	@Override
	public void onClick(View v) {
		int iBallId = v.getId();
		((ZixuanAndJiXuan) context).isBallTable(iBallId);
		((ZixuanAndJiXuan) context).showEditText();
		String text = ((ZixuanAndJiXuan) context).textSumMoney(areaNums, iProgressBeishu);
		((ZixuanAndJiXuan) context).showBetMoney(v);
		((ZixuanAndJiXuan) context).showBetInfo(text);
	}
	
	public int[] rankList(List<String> myArray) {
		int[] rankInt = new int[myArray.size()];
		for (int n = 0; n < myArray.size(); n++) {
			rankInt[n] = Integer.parseInt(myArray.get(n));
		}
		// 取长度最长的词组 -- 冒泡法
		for (int j = 1; j < rankInt.length; j++) {
			for (int i = 0; i < rankInt.length - 1; i++) {
				// 如果 myArray[i] > myArray[i+1] ，则 myArray[i] 上浮一位
				if (rankInt[i] < rankInt[i + 1]) {
					int temp = rankInt[i];
					rankInt[i] = rankInt[i + 1];
					rankInt[i + 1] = temp;
				}
			}
		}
		return rankInt;
	}
	
	/**
	 * 画遗漏值
	 * 
	 * @param missValues遗漏值数据
	 * @param iBallViewNo
	 * @param rankInt
	 * @return
	 */
	private TextView PaindMiss(List<String> missValues, int iBallViewNo,int[] rankInt,int textbg) {
		TextView textView = new TextView(context);
		if(textbg!=0){
			textView.setBackgroundResource(textbg);
		}
		
		if (missValues != null) {
			String missValue = missValues.get(iBallViewNo);
			textView.setText(missValue);
			if (rankInt[0] == Integer.parseInt(missValue) || rankInt[1] == Integer.parseInt(missValue)) {
				textView.setTextColor(Color.BLUE);
			}
		} else {
			textView.setText("0");
			textView.setTextColor(Color.WHITE);
		}
		textView.setGravity(Gravity.CENTER);
		textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 
				LayoutParams.FILL_PARENT, 1));
		return textView;
	}

}
