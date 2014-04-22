package com.ruyicai.component.elevenselectfive;

import java.util.Arrays;
import java.util.List;


import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.BuyGameDialog;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.more.LuckChoose2;
import com.ruyicai.activity.notice.NoticeActivityGroup;
import com.ruyicai.activity.usercenter.BetQueryActivity;
import com.ruyicai.component.MyGridView;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveChooseDtPopuAdapter.OnDtChickItem;
import com.ruyicai.component.elevenselectfive.ElevenSelectFiveChoosePtPopuAdapter.OnChickItem;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.util.RWSharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ElevenSelectFiveTopView extends LinearLayout implements OnCheckedChangeListener{
	
	/** 上下文对象 */
	private Context context;
	
	/** 头部单击下拉普通、胆拖选择window */
	private TextView eleven_select_five_title_textview;
	
	/** 刷新按钮 */
	private Button eleven_select_five_refresh_Btn;
	
	/** 头部单击下拉选择玩法介绍、开奖走势、投注查询*/
	private Button eleven_select_five_down_window;
	
	/** 遗漏组合按钮 */
	private Button eleven_select_five_omission_button;
	
	/** 走势按钮 */
	private Button eleven_select_five__zoushitu_button;
	
	/** 期号显示与截止时间倒计时*/
	private TextView elect_select_five__endtime_textview;
	
	/** 玩法介绍*/
	private TextView elect_select_five_palymethod_textview;
	
	private TextView missTextView;
	
	/** 遗漏值选择框*/
	private CheckBox elect_select_five_miss_checkbox;
	
	/** 玩法介绍、开奖走势、投注查询下拉框*/
	private PopupWindow popupwindow;
	
	/** 彩种编号*/
	private String lotNo;
	
	/** 查询开奖走势彩种编号*/
	private int noticeLotNo;
	
	private BuyGameDialog gameDialog;
	
	private PopupWindow popupWindow;
	
	private String topViewTitle="重庆11选5";
	
	private String[] danTuoPlayMessage;
	
	private String[] puTongPlayMessage;
	
	/** 是否显示幸运选号*/
	private boolean isShowLuckSelectNumLayout=false;
	
	private Handler gameHandler=new Handler();

	private MyGridView pTGridView;

	private MyGridView dTGridView;
	
	private boolean isDanTuoPlay=false;

	/** 普通玩法存放Adapter*/
	private ElevenSelectFiveChoosePtPopuAdapter showPtMenuAdapter;

	/** 胆拖玩法存放Adapter*/
	private ElevenSelectFiveChooseDtPopuAdapter showDtMenuAdapter;

	/** 用于区分普通与胆拖*/
	public int playMethodTag=1;

	/** 标记选择玩法标记位*/
	public int itemId=3;
	
	/** 单击tab表头刷新自定义监听*/
	private ElevenSelectFiveTopViewClickListener elevenSelectFiveTopViewClickListener;
	
	private RelativeLayout eleven_select_five_title_relativelayout;
	
	private ImageView topViewTitleBackGround;
	
	private boolean isNewNmk3=false;
	
	private String[] ptPlayMethodDescribeList;
	
	private String[] dtPlayMethodDescribeList;
	
	private int[] popupWindowItemClckBackGroud={R.drawable.shaixuanbutton_normal,R.drawable.shaixuanbutton_click};
	
	private int colorId=this.getResources().getColor(R.color.black);
	
	private RelativeLayout eleven_select_five_lotteryinfo_relativelayout;
	
	private RelativeLayout touzhuMessage;
	
	private ImageView newNmkThreeDownIcon;
	
	private String[] ptPlayMessage=getResources().getStringArray(R.array.eleven_select_five_choose_pt_type);
	
	private String[] dtPlayMessage=getResources().getStringArray(R.array.eleven_select_five_choose_dt_type);
	
	public void addElevenSelectFiveTopViewClickListener(ElevenSelectFiveTopViewClickListener elevenSelectFiveTopViewClickListener) {
		this.elevenSelectFiveTopViewClickListener = elevenSelectFiveTopViewClickListener;
	}

	public interface ElevenSelectFiveTopViewClickListener {
		
		/** 刷新按钮*/
		public void ElevenSelectFiveFresh();
		
		/** 选择是否显示遗漏值信息*/
		public void ChooseIsToShowMissMessage(boolean isChecked);
		
		/** 选择胆拖玩法*/
		public void TouchDTPlayMethod(int position);
		
		/** 切换组合遗漏界面*/
		public void ElevenSelectFiveOmission();
		
		/** 选择普通玩法*/
		public void TouchPTPlayMethod(int position);
	}
	
	{
		// 初始化代码块
		LayoutInflater layoutInflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.activity_eleven_select_five_top, this);
		eleven_select_five_title_textview=(TextView) findViewById(R.id.eleven_select_five_title_textview);
		eleven_select_five_refresh_Btn=(Button) findViewById(R.id.eleven_select_five_refresh_Btn);
		eleven_select_five_down_window=(Button) findViewById(R.id.eleven_select_five_down_window);
		eleven_select_five_omission_button=(Button) findViewById(R.id.eleven_select_five_omission_button);
		eleven_select_five__zoushitu_button=(Button) findViewById(R.id.eleven_select_five__zoushitu_button);
		elect_select_five__endtime_textview=(TextView) findViewById(R.id.elect_select_five_endtime_textview);
		elect_select_five_palymethod_textview=(TextView) findViewById(R.id.elect_select_five_palymethod_textview);
		elect_select_five_miss_checkbox=(CheckBox) findViewById(R.id.elect_select_five_miss_checkbox);
		missTextView=(TextView) findViewById(R.id.missTextView);
		eleven_select_five_title_relativelayout=(RelativeLayout) findViewById(R.id.eleven_select_five_title_relativelayout);
		topViewTitleBackGround=(ImageView) findViewById(R.id.topViewTitleBackGround);
		touzhuMessage=(RelativeLayout) findViewById(R.id.touzhuMessage);
		newNmkThreeDownIcon=(ImageView) findViewById(R.id.newNmkThreeDownIcon);
		eleven_select_five_lotteryinfo_relativelayout=(RelativeLayout) findViewById(R.id.eleven_select_five_lotteryinfo_relativelayout);
		
		eleven_select_five_title_textview.setOnClickListener(new ElevenSelectFiveTopOnClickListener());
		eleven_select_five_refresh_Btn.setOnClickListener(new ElevenSelectFiveTopOnClickListener());
		eleven_select_five_down_window.setOnClickListener(new ElevenSelectFiveTopOnClickListener());
		eleven_select_five_omission_button.setOnClickListener(new ElevenSelectFiveTopOnClickListener());
		eleven_select_five__zoushitu_button.setOnClickListener(new ElevenSelectFiveTopOnClickListener());
		elect_select_five_miss_checkbox.setOnCheckedChangeListener(this);
		topViewTitleBackGround.setOnClickListener(new ElevenSelectFiveTopOnClickListener());
	}
	
	public ElevenSelectFiveTopView(Context context) {
		super(context);
		danTuoPlayMessage=getResources().getStringArray(R.array.choose_type);
		puTongPlayMessage=getResources().getStringArray(R.array.dlc_type);
	}
	
	public void setPtPlayMethodDescribeList(String[] playMethodDescribeList){
		this.ptPlayMethodDescribeList=playMethodDescribeList;
	}
	
	public void setDtPlayMethodDescribeList(String[] playMethodDescribeList){
		this.dtPlayMethodDescribeList=playMethodDescribeList;
	}
	
	public void removeMissCheckbox(){
		elect_select_five_miss_checkbox.setVisibility(View.GONE);
		missTextView.setVisibility(View.GONE);
	}
	
	public void showMissCheckbox(){
		missTextView.setVisibility(View.VISIBLE);
		elect_select_five_miss_checkbox.setVisibility(View.VISIBLE);
	}
	
	public void setTextColor(int colorId){
		this.colorId=colorId;
	}
	
	public void setInitPopupPosition(int itemId){
		this.itemId=itemId;
	}

	public ElevenSelectFiveTopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		danTuoPlayMessage=getResources().getStringArray(R.array.choose_type);
		puTongPlayMessage=getResources().getStringArray(R.array.dlc_type);
	}
	
	public void setPtPlayMessage(String[] ptPlayMessage){
		this.ptPlayMessage=ptPlayMessage;
	}
	
	public void setDtPlayMessage(String[] dtPlayMessage){
		this.dtPlayMessage=dtPlayMessage;
	}
	
	
	public void setZouShiBtnBackGround(int resid){
		eleven_select_five__zoushitu_button.setBackgroundResource(resid);
	}
	
	public void setZouShiBtnText(String text){
		eleven_select_five__zoushitu_button.setText(text);
	}
	
	public void removeZoushiBtnBackGround(){
		eleven_select_five__zoushitu_button.setVisibility(View.GONE);
	}
	
	public void setLotteryMessageTextColor(int colorId){
		elect_select_five_palymethod_textview.setTextColor(colorId);
	}
	
	public void setZhMissBtnBackGround(int resid){
		eleven_select_five_omission_button.setBackgroundResource(resid);
	}
	
	public void setZhMissBtnText(String text){
		eleven_select_five_omission_button.setText(text);
	}
	
	public void setLotteryInfoBackGround(int resid){
		eleven_select_five_lotteryinfo_relativelayout.setBackgroundResource(resid);
	}
	
	public void setTouZhuMessageBackGround(int resid){
		touzhuMessage.setBackgroundResource(resid);
	}
	
	public void setPoupWindowItemClickPicture(int[] backGroundid ){
		this.popupWindowItemClckBackGroud=backGroundid;
	}
	
	public void setDanTuoPlayMessage(String[] danTuoPlayMessage){
		this.danTuoPlayMessage=danTuoPlayMessage;
	}
	
	public void setPuTongPlayMessage(String[] puTongPlayMessage){
		this.puTongPlayMessage=puTongPlayMessage;
	}
	
	public void removeZhMissButton(){
		eleven_select_five_omission_button.setVisibility(View.GONE);
	}
	
	public void setZhMissButtonShow(){
		eleven_select_five_omission_button.setVisibility(View.VISIBLE);
	}
	
	public void setOmissionBtnBackGround(int id){
		eleven_select_five_omission_button.setBackgroundResource(id);
	}
	
	public void setTopViewBackGround(int id){
		eleven_select_five_title_relativelayout.setBackgroundResource(id);
	}
	
	public void setTopViewTitleBackGround(int id){
		topViewTitleBackGround.setBackgroundResource(id);
	}
	
	public void setShowTopViewTitleBackGround(){
		topViewTitleBackGround.setVisibility(View.VISIBLE);
	}
	
	public void removeTopViewTitleBackGround(){
		topViewTitleBackGround.setVisibility(View.GONE);
	}
	
	public void isNewNmkThree(boolean isNewNmk3){
		this.isNewNmk3=isNewNmk3;
	}
	
	/**
	 * 设置期号与截止时间显示
	 */
	public void setElevenSelectFiveEndTime(String batchCodeAndEndTime){
		elect_select_five__endtime_textview.setText(batchCodeAndEndTime);
	}
	
	public String getElevenSelectFiveTitleText() {
		return eleven_select_five_title_textview.getText().toString();
	}

	class ElevenSelectFiveTopOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.eleven_select_five_title_textview:
			case R.id.topViewTitleBackGround:
				if(isNewNmk3){
					topViewTitleBackGround.setVisibility(View.VISIBLE);
					newNmkThreeDownIcon.setBackgroundResource(R.drawable.new_nmk3_top_title_up);
				}
				showPlayMethodMenuDialog();
				break;
            case R.id.eleven_select_five_refresh_Btn:
            	elevenSelectFiveTopViewClickListener.ElevenSelectFiveFresh();
				break;
            case R.id.eleven_select_five_down_window:
            	createDialog();
	            break;
            case R.id.eleven_select_five_omission_button:
            	elevenSelectFiveTopViewClickListener.ElevenSelectFiveOmission();
            	if(isNewNmk3){
            		missTextView.setVisibility(View.GONE);
            		elect_select_five_miss_checkbox.setVisibility(View.GONE);
            	}
	            break;
            case R.id.eleven_select_five__zoushitu_button:
            	NoticeActivityGroup.LOTNO = noticeLotNo;
				Intent intent = new Intent(context,
						NoticeActivityGroup.class);
				intent.putExtra("position", 0);
				context.startActivity(intent);
	            break;

			default:
				break;
			}
		}
		
	}
	
	public void setQueryMessage(String lotNo,int noticeLotNo){
		this.lotNo=lotNo;
		this.noticeLotNo=noticeLotNo;
		if(Constants.LOTNO_CQ_ELVEN_FIVE.equals(lotNo)){//重庆11选5
			topViewTitle="重庆11选5";
		}else if(Constants.LOTNO_GD115.equals(lotNo)){//广东11选5
			topViewTitle="广东11选5";
		}else if(Constants.LOTNO_eleven.equals(lotNo)){//11运夺金
			topViewTitle="11运夺金";
		}else if(Constants.LOTNO_JLK3.equals(lotNo)){
			topViewTitle="新快三";
		}else {//江西11选5
			topViewTitle="江西11选5";
		}
		initPopWindow();
	}
	
	private void initPopWindow(){
		if(isNewNmk3){
			itemId=0;
			setTitle("和值");
			setPtBetPrompt(0);
			missTextView.setVisibility(View.GONE);
			elect_select_five_miss_checkbox.setVisibility(View.GONE);
			newNmkThreeDownIcon.setVisibility(View.VISIBLE);
		}else{
			setTitle("任选五");
			setPtBetPrompt(3);
		}
	}
	
	public void isShowLuckSelectNumLayout(boolean isShowLuckSelectNumLayout){
		this.isShowLuckSelectNumLayout=isShowLuckSelectNumLayout;
	}
	
	/**
	 * 创建下拉列表
	 */
	private void createDialog() {
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = (LinearLayout) inflate.inflate(
				R.layout.buy_group_window, null);
		
		LinearLayout popupWindowLayout=(LinearLayout)popupView. findViewById(R.id.popupWindowLayout);
		LinearLayout beijing_single_mani_history=(LinearLayout)popupView. findViewById(R.id.beijing_single_mani_history);
		LinearLayout buy_group_one_layout4=(LinearLayout)popupView. findViewById(R.id.buy_group_one_layout4);
		
		
		
		popupwindow = new PopupWindow(popupView, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		popupwindow.setTouchable(true); // 设置PopupWindow可触摸
		popupwindow.setOutsideTouchable(true);
		popupwindow.setFocusable(true);
		popupwindow.update();
		popupwindow.setBackgroundDrawable(new BitmapDrawable());
		popupView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
					popupwindow = null;
				}
				return false;
			}
		});
		popupwindow.showAsDropDown(eleven_select_five_down_window);
		// 玩法介绍
		final LinearLayout layoutGame = (LinearLayout) popupView
				.findViewById(R.id.buy_group_layout1);
		layoutGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutGame.setBackgroundResource(R.drawable.buy_group_layout_b);
				if (gameDialog == null) {
					gameDialog = new BuyGameDialog(context, lotNo, gameHandler);
				}
				gameDialog.showDialog();
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
				}
			}
		});

		// 历史开奖
		final LinearLayout layoutHosity = (LinearLayout) popupView
				.findViewById(R.id.buy_group_layout2);
		layoutHosity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutHosity
						.setBackgroundResource(R.drawable.buy_group_layout_b);
					NoticeActivityGroup.LOTNO = noticeLotNo;
					Intent intent = new Intent(context,NoticeActivityGroup.class);
					intent.putExtra("position", 1);
					context.startActivity(intent);
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
				}
			}

		});

		// 投注查询
		final LinearLayout layoutQuery = (LinearLayout) popupView
				.findViewById(R.id.buy_group_layout4);
		layoutQuery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RWSharedPreferences shellRW = new RWSharedPreferences(context,
						"addInfo");
				String userno = shellRW.getStringValue(ShellRWConstants.USERNO);
				if (userno == null || userno.equals("")) {
					Intent intentSession = new Intent(context, UserLogin.class);
					context.startActivity(intentSession);
				} else {
					Intent intent = new Intent(context,
							BetQueryActivity.class);
					intent.putExtra("lotno", lotNo);
					context.startActivity(intent);
				}
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
				}
			}

		});
		final LinearLayout layoutParentLuck = (LinearLayout) popupView
				.findViewById(R.id.buy_group_one_layout3);
		final LinearLayout layoutLuck = (LinearLayout) popupView
				.findViewById(R.id.buy_group_layout3);
		final LinearLayout layoutPicture = (LinearLayout) popupView
				.findViewById(R.id.buy_group_layout6);
		final LinearLayout layoutParentPicture = (LinearLayout) popupView
				.findViewById(R.id.buy_group_one_layout6);
		layoutParentPicture.setVisibility(View.VISIBLE);
		layoutPicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutPicture
						.setBackgroundResource(R.drawable.buy_group_layout_b);
				NoticeActivityGroup.LOTNO = noticeLotNo;
				Intent intent = new Intent(context, NoticeActivityGroup.class);
				intent.putExtra("position", 0);
				context.startActivity(intent);
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
				}
			}
		});
		if (isShowLuckSelectNumLayout) {
			layoutParentLuck.setVisibility(LinearLayout.VISIBLE);
			layoutLuck.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					layoutLuck
							.setBackgroundResource(R.drawable.buy_group_layout_b);
					Intent intent = new Intent(context, LuckChoose2.class);
					intent.putExtra("lotno", lotNo);
					intent.putExtra("caipiaoWanfaIndex",itemId);
					if(isDanTuoPlay){
						intent.putExtra("isdantuo", true);
					}
					context.startActivity(intent);
					if(popupwindow != null && popupwindow.isShowing()){
						popupwindow.dismiss();
					}
					
				}
			});
			layoutParentLuck.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
		} else {
			layoutParentLuck.setVisibility(LinearLayout.GONE);
		}
		
		if(isNewNmk3){
			popupWindowLayout.setBackgroundResource(R.drawable.buy_group_jilink3_top_down1);
			layoutParentPicture.setBackgroundResource(R.drawable.buy_group_jilink3_top_down2);
			beijing_single_mani_history.setBackgroundResource(R.drawable.buy_group_jilink3_top_down2);
			buy_group_one_layout4.setBackgroundResource(R.drawable.buy_group_jilink3_top_down2);
		}
	}
	
	/**
	 * 显示玩法菜单
	 */
	public void showPlayMethodMenuDialog(){
		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = (LinearLayout) inflate.inflate(R.layout.eleven_choose_five_list, null);
			
		pTGridView = (MyGridView) popupView.findViewById(R.id.chooseviewfirst);
		List<String> stoogesFirst = Arrays.asList(puTongPlayMessage);
		showPtMenuAdapter = new ElevenSelectFiveChoosePtPopuAdapter(context, new popPTOnItemChick(), stoogesFirst);
		showPtMenuAdapter.setItemClickBackground(popupWindowItemClckBackGroud);
		showPtMenuAdapter.setTextColor(colorId);
		if(ptPlayMethodDescribeList!=null&&ptPlayMethodDescribeList.length>0){
			List<String> ptPlayMethodDescribe = Arrays.asList(ptPlayMethodDescribeList);
			showPtMenuAdapter.setplayMethodDescribeList(ptPlayMethodDescribe);
		}
		pTGridView.setAdapter(showPtMenuAdapter);
			
		dTGridView=(MyGridView)popupView.findViewById(R.id.chooseviewsecond);
		List<String> stoogesSecond=Arrays.asList(danTuoPlayMessage);
		showDtMenuAdapter = new ElevenSelectFiveChooseDtPopuAdapter(context,new popDTOnItemChick(),stoogesSecond);
		showDtMenuAdapter.setItemClickBackground(popupWindowItemClckBackGroud);
		showDtMenuAdapter.setTextColor(colorId);
		if(dtPlayMethodDescribeList!=null&&dtPlayMethodDescribeList.length>0){
			List<String> dtPlayMethodDescribe = Arrays.asList(dtPlayMethodDescribeList);
			showDtMenuAdapter.setplayMethodDescribeList(dtPlayMethodDescribe);
		}
		dTGridView.setAdapter(showDtMenuAdapter);
			
		popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(eleven_select_five_title_textview);
		
		popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				if(isNewNmk3){
					topViewTitleBackGround.setVisibility(View.GONE);
					newNmkThreeDownIcon.setBackgroundResource(R.drawable.new_nmk3_top_title_down);
				}
			}
		});
		
		if(playMethodTag==1){
			showPtMenuAdapter.setItemSelect(itemId);
			showPtMenuAdapter.notifyDataSetInvalidated();
			showDtMenuAdapter.setItemSelect(-1);
			showDtMenuAdapter.notifyDataSetInvalidated();
		}else if(playMethodTag==2){
			showDtMenuAdapter.setItemSelect(itemId);
			showDtMenuAdapter.notifyDataSetInvalidated();
			showPtMenuAdapter.setItemSelect(-1);
			showPtMenuAdapter.notifyDataSetInvalidated();
		}
		
		
		
	}
	
	/**
	 * 普通点击事件
	 * @author 
	 *
	 */
	public class popPTOnItemChick implements OnChickItem{

		@Override
		public void onChickItem(View view, int position, String text) {
			isDanTuoPlay=false;
			playMethodTag=1;
			itemId=position;
			setTitle(text);
			showDtMenuAdapter.setItemSelect(-1);
			showDtMenuAdapter.notifyDataSetInvalidated();
			
			showPtMenuAdapter.setItemSelect(position);
			showPtMenuAdapter.notifyDataSetInvalidated();
			popupWindow.dismiss();
			setPtBetPrompt(position);
			elevenSelectFiveTopViewClickListener.TouchPTPlayMethod(position);
			elevenSelectFiveTopViewClickListener.ChooseIsToShowMissMessage(elect_select_five_miss_checkbox.isChecked());
		}
			
	}
	/**
	 * 胆拖点击事件
	 * @author 
	 *
	 */
	public class popDTOnItemChick implements OnDtChickItem{
		@Override
		public void onChickItem(View view, int position, String text) {
			isDanTuoPlay=true;
			playMethodTag=2;
			itemId=position;
			setTitle(text);
			showPtMenuAdapter.setItemSelect(-1);
			showPtMenuAdapter.notifyDataSetInvalidated();
			
			showDtMenuAdapter.setItemSelect(position);
			showDtMenuAdapter.notifyDataSetInvalidated();
			
			popupWindow.dismiss();
			setDtBetPrompt(position);
			elevenSelectFiveTopViewClickListener.TouchDTPlayMethod(position);
			elevenSelectFiveTopViewClickListener.ChooseIsToShowMissMessage(elect_select_five_miss_checkbox.isChecked());
		}
	}
	
	/**
	 * 设置标题
	 * @param titleType玩法名称
	 */
	private void setTitle(String titleType){
		if(isNewNmk3){
			if(playMethodTag==1){
				eleven_select_five_title_textview.setText(topViewTitle+"-"+titleType+"-普通");
			}else if (playMethodTag==2) {
				eleven_select_five_title_textview.setText(topViewTitle+"-"+titleType+"-胆拖");
			}
		}else{
			if(playMethodTag==1){
				eleven_select_five_title_textview.setText(topViewTitle+"-"+titleType+"-普通");
			}else if (playMethodTag==2) {
				eleven_select_five_title_textview.setText(topViewTitle+"-"+titleType+"-胆拖");
			}
		}
	}
	
	/**
	 * 普通投注提示
	 */
	private void setPtBetPrompt(int type) {
		elect_select_five_palymethod_textview.setText(ptPlayMessage[type]);
	}
	/**
	 * 胆拖投注提示
	 */
	private void setDtBetPrompt(int type) {
		elect_select_five_palymethod_textview.setText(dtPlayMessage[type]);
	}
	

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		elevenSelectFiveTopViewClickListener.ChooseIsToShowMissMessage(elect_select_five_miss_checkbox.isChecked());
	}
	
}
