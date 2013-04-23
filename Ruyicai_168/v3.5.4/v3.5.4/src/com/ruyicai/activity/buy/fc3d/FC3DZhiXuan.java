package com.ruyicai.activity.buy.fc3d;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.palmdream.RuyicaiAndroid168.R;
import com.ruyicai.activity.buy.miss.BuyViewItemMiss;
import com.ruyicai.activity.buy.miss.MainViewPagerAdapter;
import com.ruyicai.activity.buy.miss.NumViewItem;
import com.ruyicai.activity.buy.miss.ZixuanActivity;
import com.ruyicai.activity.buy.zixuan.BuyViewItem;
import com.ruyicai.code.fc3d.F3dZiHeZhiCode;
import com.ruyicai.code.fc3d.Fc3dZiZhiXuanCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.json.miss.DltMissJson;
import com.ruyicai.json.miss.Fc3dMissJson;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;

public class FC3DZhiXuan  extends ZixuanActivity implements OnCheckedChangeListener{
	public static int iCurrentButton ;//标签
	private LinearLayout topLinear;
	private LinearLayout topLinearTwo;
	private RadioGroup topButton;
	private String topTitle[]={"普通直选","直选和值"};
	
	private int ballResId[] = { R.drawable.grey, R.drawable.red };// 选区小球切换图片
	private AreaInfo areaInfosZH[] = new AreaInfo[1];//直选和值 1个选区
	private AreaInfo areaInfosZ[] = new AreaInfo[3];// 直选3个选区
	private F3dZiHeZhiCode fc3dCodeH = new  F3dZiHeZhiCode();
	private Fc3dZiZhiXuanCode fc3dCodeZ = new Fc3dZiZhiXuanCode();
	//福彩3D直选的三个BallTable
	BallTable baiBallTable ;
	BallTable shiBallTable ;
	BallTable geBallTable ;
	
	BallTable ballTable ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		topLinear = (LinearLayout) findViewById(R.id.buy_zixuan_linear_top);
		topLinearTwo = (LinearLayout) findViewById(R.id.buy_zixuan_linear_top_two);
		topLinear.setVisibility(LinearLayout.VISIBLE);
		topLinearTwo.setVisibility(LinearLayout.VISIBLE);
		topButton = (RadioGroup) findViewById(R.id.buy_zixuan_radiogroup_top);
		initTopButton();
		topButton.setOnCheckedChangeListener(this);
		topButton.check(0);
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void initTopButton(){
		for(int i=0;i<topTitle.length;i++){
			RadioButton radio = new RadioButton(this);
			radio.setText(topTitle[i]);
			radio.setTextColor(Color.BLACK);
			radio.setTextSize(13);
			radio.setId(i);
			radio.setButtonDrawable(R.drawable.radio_select);
			radio.setPadding(Constants.PADDING, 0, 10, 0);
			topButton.addView(radio);
		}
	}
	
	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		// TODO Auto-generated method stub
		String mTextSumMoney = "";
		int iZhuShu = getZhuShu();
		switch (iCurrentButton) {
		case PublicConst.BUY_FC3D_HEZHI_ZHIXUAN:
			if(iZhuShu==0){
				mTextSumMoney = "需要1个球";
			}else{
				mTextSumMoney = "共"+ (iZhuShu + "注，共" + (iZhuShu * 2) + "元");
			}
			break;
		case PublicConst.BUY_FC3D_ZHIXUAN:
			if (baiBallTable.getHighlightBallNums() >0 && shiBallTable.getHighlightBallNums() >0&& geBallTable.getHighlightBallNums() >0)  {
				mTextSumMoney = "共" + iZhuShu + "注，共" + (iZhuShu * 2) + "元";
			} else if(baiBallTable.getHighlightBallNums()==0){
				mTextSumMoney = "至少还需要1个百位小球";
			} else if(shiBallTable.getHighlightBallNums()==0){
				mTextSumMoney = "至少还需要1个十位小球";
			} else if(geBallTable.getHighlightBallNums()==0){
				mTextSumMoney = "至少还需要1个个位小球";
			}
			break;
		}
		return mTextSumMoney;
	}

	@Override
	public String isTouzhu() {
		// TODO Auto-generated method stub
		String isTouzhu = "";
		switch (iCurrentButton) {
		case PublicConst.BUY_FC3D_HEZHI_ZHIXUAN:
			if (ballTable.getHighlightBallNums() < 1) {
				isTouzhu = "请选择小球号码后再投注";
			} else if (ballTable.getHighlightBallNums() == 1) {
				// wangyl 7.13 配合陈晨投注时用
				int iZhuShu = getZhuShu();
				String fushiStr = PublicMethod.getZhuMa(ballTable.getHighlightBallNOs()[0])+"";
				if (iZhuShu * 2 > 100000) {
					isTouzhu = "false";
				} else {
					isTouzhu = "true";
				}
			}
			break;

		case PublicConst.BUY_FC3D_ZHIXUAN:
			int baiweiNums = baiBallTable.getHighlightBallNums();
	        int shiweiNums = shiBallTable.getHighlightBallNums();
	        int geweiNums = geBallTable.getHighlightBallNums();
	        int[] baiweis = baiBallTable.getHighlightBallNOs();
	        int[] shiweis = shiBallTable.getHighlightBallNOs();
	        int[] geweis = geBallTable.getHighlightBallNOs();
	        String baiweistr = "";
	        String shiweistr = "";
	        String geweistr = "";
	        for (int i = 0; i < baiweiNums; i++) {
		        baiweistr += (baiweis[i]) + ",";
		        if (i == baiweiNums - 1) {
			     baiweistr = baiweistr.substring(0, baiweistr.length() - 1);
		        } 
	         }
	        for (int i = 0; i < shiweiNums; i++) {
		         shiweistr += (shiweis[i]) + ",";
		         if (i == shiweiNums - 1) {
			      shiweistr = shiweistr.substring(0, shiweistr.length() - 1);
		        }
	         }
	        for (int i = 0; i < geweiNums; i++) {
		         geweistr += (geweis[i]) + ",";
		        if (i == geweiNums - 1) {
			     geweistr = geweistr.substring(0,geweistr.length() - 1);
		         }
	        }
	       if (baiweiNums < 1 || shiweiNums < 1 || geweiNums < 1) {
		       isTouzhu = "请在百位，十位，个位均至少选择一个小球后再投注";
	        } else {
		    	isTouzhu = "true";
	         }
			break;
		}
		return isTouzhu;
	}

	@Override
	public void touzhuNet() {
		// TODO Auto-generated method stub
		betAndGift.setSellway("0");//1代表机选 0代表自选
		betAndGift.setLotno(Constants.LOTNO_FC3D);
	}
	/**
	 * 重写RadioGroup监听方法onCheckedChanged
	 * 
	 * @param RadioGroup   RadioGroup
	 * @param int checkedId 当前被选择的RadioId
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (group.getId()) {
		case R.id.buy_zixuan_radiogroup_top:
			switch (checkedId) {
			case 0:// 直选
				iCurrentButton=PublicConst.BUY_FC3D_ZHIXUAN;
				create_FC3D_ZHIXUAN();
				break;
			case 1:// 和值组三
				iCurrentButton=PublicConst.BUY_FC3D_HEZHI_ZHIXUAN;
				create_FC3D_HEZHI_ZHIXUAN();
				break;
			}
		}
	}
	 /**
	   * 获取注数
	   * 
	   */
	public int getZhuShu(){
		int zhushu = 0;
		switch(iCurrentButton){
		case PublicConst.BUY_FC3D_HEZHI_ZHIXUAN:
			zhushu= getFc3dZhixuanHezhiZhushu();
			break;
		case PublicConst.BUY_FC3D_ZHIXUAN:
			zhushu = getFc3dZhixuanZhushu();
		}
		return zhushu* iProgressBeishu;
	}
	/**
	 * 获得福彩3D直选注数
	 * @return
	 */
	private int getFc3dZhixuanZhushu(){
		int zhushu = 0;
		zhushu = baiBallTable.getHighlightBallNums()* shiBallTable.getHighlightBallNums()* geBallTable.getHighlightBallNums();
		return zhushu;
	}
	/**
	 * 获得福彩3D直选和值注数
	 * 
	 * @return 返回注数
	 */
	private int getFc3dZhixuanHezhiZhushu() {
		int iZhuShu = 0;
		int[] BallNos = ballTable.getHighlightBallNOs();// 被选择小球的号码（点击1，获得1）
		int[] BallNoZhushus = { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 63, 69,
				73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15, 10, 6, 3, 1 };// 0~27

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i]) {// 因为数组是从0开始的，小球号码从1开始，故减去1
					// 删除倍数 cc 20100713
					iZhuShu = BallNoZhushus[j];// *iProgressBeishu;
	
				}
			}
		}
		return iZhuShu;
	}
	private  void create_FC3D_ZHIXUAN(){
		setCode(fc3dCodeZ);
		setIsTen(true);
		initGallery();
		baiBallTable = itemViewArray.get(0).areaNums[0].table;
		shiBallTable = itemViewArray.get(0).areaNums[1].table;
		geBallTable = itemViewArray.get(0).areaNums[2].table;
		getMissNet(new Fc3dMissJson(),MissConstant.FC3D_ZX,MissConstant.FC3D_ZX);
	}
	/**
	 * 初始化滑动
	 */
	public void initGallery() {
		        itemViewArray.clear();
		        viewPagerContainer.removeAllViews();
				BuyViewItemMiss buyView = new BuyViewItemMiss(this,initArea());
				NumViewItem numView = new NumViewItem(this,initArea());
				numView.missList.clear();
				// 添加需要左右划屏效果的视图到缓存容器中
				itemViewArray.add(buyView);
				itemViewArray.add(numView);
				// 设置 ViewPager 的 Adapter
				MainViewPagerAdapter MianAdapter = new MainViewPagerAdapter(null);
				View view = numView.createView();
				numView.leftBtn(view);
				MianAdapter.addView(buyView.createView());
				MianAdapter.addView(view);
				viewPagerContainer.setAdapter(MianAdapter);
				// 设置第一显示页面
				viewPagerContainer.setCurrentItem(0);
	}

	/**
	 * 初始化选区界面
	 */
	public void initViewItem() {
		// TODO Auto-generated method stub
		   itemViewArray.clear();
		   layoutView.removeAllViews();
		   BuyViewItem buyView = new BuyViewItem(this,initArea());
		   itemViewArray.add(buyView);
		   layoutView.addView(buyView.createView());
	}

	/**
	 * 初始化选区
	 */
	public AreaNum[] initArea() {
		AreaNum areaNums[] = null;
		switch (iCurrentButton) {
		case PublicConst.BUY_FC3D_HEZHI_ZHIXUAN:
			areaNums = new AreaNum[1];
	        String title = getResources().getString(R.string.fc3d_text_hezhi_title).toString();
	        areaNums[0] = new AreaNum(28,8,1, 1, ballResId, 0, 0,Color.RED,title,false,true);
	        return areaNums;
		case PublicConst.BUY_FC3D_ZHIXUAN:
			areaNums = new AreaNum[3];
			String baiTitle = getResources().getString(R.string.fc3d_text_bai_title).toString();
	        String shiTitle = getResources().getString(R.string.fc3d_text_shi_title).toString();
	        String geTitle = getResources().getString(R.string.fc3d_text_ge_title).toString();
	        areaNums[0] = new AreaNum(10,10,1, 10, ballResId, 0, 0,Color.RED,baiTitle,false,true);
	        areaNums[1] = new AreaNum(10,10,1, 10, ballResId, 0, 0,Color.RED,shiTitle,false,true);
	        areaNums[2] = new AreaNum(10,10,1, 10, ballResId, 0, 0,Color.RED,geTitle,false,true);
	        return areaNums;
		}
		return areaNums;
	}
	
	/**
	 * 福彩3D和值直选
	 */
	private void create_FC3D_HEZHI_ZHIXUAN() {
		fc3dCodeH.setiCurrentButton(iCurrentButton);
		setCode(fc3dCodeH);
		setIsTen(false);
		initGallery();
		ballTable = itemViewArray.get(0).areaNums[0].table;
		getMissNet(new Fc3dMissJson(),MissConstant.FC3D_ZXHZ,MissConstant.FC3D_ZXHZ);
	}
	/**
	 * 根据小球id判断是哪个选区
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId){
	  
				switch (iCurrentButton) {
				case PublicConst.BUY_FC3D_HEZHI_ZHIXUAN:
					AreaNum[] areaNums1 = itemViewArray.get(0).areaNums;
					AreaNum[] areaNums2 = itemViewArray.get(1).areaNums;
					areaNums1[0].table.clearAllHighlights();
					areaNums1[0].table.changeBallState(areaNums[0].chosenBallSum, iBallId);
					areaNums2[0].table.clearAllHighlights();
					areaNums2[0].table.changeBallState(areaNums[0].chosenBallSum, iBallId);
					break;
				case PublicConst.BUY_FC3D_ZHIXUAN:
					int aBallId = iBallId;
					for(int j=0;j<itemViewArray.size();j++){
					AreaNum[] areaNums = itemViewArray.get(j).areaNums;
						int nBallId = 0;
						iBallId = aBallId;
						if(iBallId>=0&&iBallId<10){
							nBallId = iBallId;
							areaNums[0].table.changeBallState(areaNums[0].chosenBallSum, iBallId);
						}else if(iBallId<20&&iBallId>=10){
							nBallId = iBallId - 10;
							areaNums[1].table.changeBallState(areaNums[1].chosenBallSum, nBallId);
						}else{
							nBallId = iBallId - 20;
							areaNums[2].table.changeBallState(areaNums[2].chosenBallSum, nBallId);
						}
					}
					break;
				}
	}

	@Override
	public String getZhuma() {
		// TODO Auto-generated method stub
		return null;
	}

}
