package com.ruyicai.activity.buy.pl3;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanActivity;
import com.ruyicai.code.pl3.PL3ZiHeZhiCode;
import com.ruyicai.code.pl3.PL3ZiZhiXuanCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;

public class PL3ZhiXuan extends ZixuanActivity implements BuyImplement, OnCheckedChangeListener{
	private int iCurrentButton;
	private LinearLayout topLinear;
	private LinearLayout topLinearTwo;
	private RadioGroup topButton;
	private String topTitle[]={"��ֱͨѡ","ֱѡ��ֵ"};
	
	private int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private AreaInfo areaInfosZH[] = new AreaInfo[1];//ֱѡ��ֵ 1��ѡ��
	private AreaInfo areaInfosZ[] = new AreaInfo[3];// ֱѡ3��ѡ��
	private PL3ZiHeZhiCode pl3CodeH = new  PL3ZiHeZhiCode();
	private PL3ZiZhiXuanCode pl3CodeZ = new PL3ZiZhiXuanCode();
	//����3Dֱѡ������BallTable
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
		case PublicConst.BUY_PL3_HEZHI_ZHIXUAN:
			if(iZhuShu==0){
				mTextSumMoney = "��Ҫ1����";
			}else{
				mTextSumMoney = "��"+ (iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ");
			}
			break;
		case PublicConst.BUY_PL3_ZHIXUAN:
			if (baiBallTable.getHighlightBallNums() >0 && shiBallTable.getHighlightBallNums() >0&& geBallTable.getHighlightBallNums() >0)  {
				mTextSumMoney = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
			} else if(baiBallTable.getHighlightBallNums()==0){
				mTextSumMoney = "���ٻ���Ҫ1����λС��";
			} else if(shiBallTable.getHighlightBallNums()==0){
				mTextSumMoney = "���ٻ���Ҫ1��ʮλС��";
			} else if(geBallTable.getHighlightBallNums()==0){
				mTextSumMoney = "���ٻ���Ҫ1����λС��";
			}
			break;
		}
		return mTextSumMoney;
	}

	@Override
	public void isTouzhu() {
		// TODO Auto-generated method stub
		switch (iCurrentButton) {
		case PublicConst.BUY_PL3_HEZHI_ZHIXUAN:
			if (ballTable.getHighlightBallNums() < 1) {
				alertInfo("��ѡ��С��������Ͷע");
			} else if (ballTable.getHighlightBallNums() == 1) {
				// wangyl 7.13 ��ϳ³�Ͷעʱ��
				int iZhuShu = getZhuShu();
				String fushiStr = PublicMethod.getZhuMa(ballTable.getHighlightBallNOs()[0])+"";
				if (iZhuShu * 2 > 100000) {
					dialogExcessive();
				} else {
					setZhuShu(iZhuShu);
					alert( "ע�룺" + fushiStr);
				}
			}
			break;

		case PublicConst.BUY_PL3_ZHIXUAN:
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
		       alertInfo("���ڰ�λ��ʮλ����λ������ѡ��һ��С�����Ͷע");
	        } else {
		    int iZhuShu = getZhuShu();
	       if (iZhuShu / iProgressBeishu > 600) {
			      dialogZhixuan();
		    } else if (iZhuShu * 2 > 100000) {
			      dialogExcessive();
		    } else {
		    	 setZhuShu(iZhuShu);
			     alert("ע�룺" + "\n" + "��λ��" + baiweistr + "\n"
					+ "ʮλ��" + shiweistr + "\n" + "��λ��"
					+ geweistr);
		          }
	         }
			break;
		}
	}

	@Override
	public void touzhuNet() {
		// TODO Auto-generated method stub
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_PL3);
	}
	/**
	 * ��дRadioGroup��������onCheckedChanged
	 * 
	 * @param RadioGroup   RadioGroup
	 * @param int checkedId ��ǰ��ѡ���RadioId
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (group.getId()) {
		case R.id.buy_zixuan_radiogroup_top:
			switch (checkedId) {
			case 0:// ��ֱֵѡ
				iCurrentButton=PublicConst.BUY_PL3_ZHIXUAN;
				create_PL3_ZHIXUAN();
				break;
			case 1:// ��ֵ����
				iCurrentButton=PublicConst.BUY_PL3_HEZHI_ZHIXUAN;
				create_PL3_HEZHI_ZHIXUAN();
				break;
			}
		}
	}
	 /**
	   * ��ȡע��
	   * 
	   */
	public int getZhuShu(){
		int zhushu = 0;
		switch(iCurrentButton){
		case PublicConst.BUY_PL3_HEZHI_ZHIXUAN:
			zhushu= getPL3ZhixuanHezhiZhushu();
			break;
		case PublicConst.BUY_PL3_ZHIXUAN:
			zhushu = getPL3ZhixuanZhushu();
		}
		return zhushu* iProgressBeishu;
	}
	/**
	 * ��ø���3Dֱѡע��
	 * @return
	 */
	private int getPL3ZhixuanZhushu(){
		int zhushu = 0;
		zhushu = baiBallTable.getHighlightBallNums()* shiBallTable.getHighlightBallNums()* geBallTable.getHighlightBallNums();
		return zhushu;
	}
	/**
	 * ��ø���3Dֱѡ��ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getPL3ZhixuanHezhiZhushu() {
		int iZhuShu = 0;
		int[] BallNos = ballTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���1�����1��
		int[] BallNoZhushus = { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 63, 69,
				73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15, 10, 6, 3, 1 };// 0~27

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i]) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С������1��ʼ���ʼ�ȥ1
					// ɾ������ cc 20100713
					iZhuShu = BallNoZhushus[j];// *iProgressBeishu;
	
				}
			}
		}
		return iZhuShu;
	}
	private  void create_PL3_ZHIXUAN(){
		initZXArea();
		createView(areaInfosZ, pl3CodeZ,this,true);
		LinearLayout thirdLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_third);
		thirdLinear.setVisibility(LinearLayout.VISIBLE);
		baiBallTable = areaNums[0].table;
		shiBallTable = areaNums[1].table;
		geBallTable = areaNums[2].table;
		
	}
	private void initZXArea(){
		String baiTitle = getResources().getString(R.string.fc3d_text_bai_title).toString();
        String shiTitle = getResources().getString(R.string.fc3d_text_shi_title).toString();
        String geTitle = getResources().getString(R.string.fc3d_text_ge_title).toString();

		areaInfosZ[0] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,baiTitle);
		areaInfosZ[1] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,shiTitle);
		areaInfosZ[2] = new AreaInfo(10, 10, ballResId, 0, 0,Color.RED,geTitle);
	}
	
	/**
	 * ����3D��ֱֵѡ
	 */
	private void create_PL3_HEZHI_ZHIXUAN() {
		pl3CodeH.setiCurrentButton(iCurrentButton);
        String title = getResources().getString(R.string.fc3d_text_hezhi_title).toString();
		areaInfosZH[0] = new AreaInfo(28, 1, ballResId, 0, 0,Color.RED,title);
		createView(areaInfosZH, pl3CodeH,this,false);
		ballTable = areaNums[0].table;
	}
	/**
	 * ����С��id�ж����ĸ�ѡ��
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId){
		switch (iCurrentButton) {
		case PublicConst.BUY_PL3_HEZHI_ZHIXUAN:
			areaNums[0].table.clearAllHighlights();
			areaNums[0].table.changeBallState(areaNums[0].info.chosenBallSum, iBallId);
			break;
		default:
			int nBallId = 0;
			if(iBallId>=0&&iBallId<10){
				nBallId = iBallId;
				areaNums[0].table.changeBallState(areaNums[0].info.chosenBallSum, nBallId);
			}else if(iBallId<20&&iBallId>=10){
				nBallId = iBallId - 10;
				areaNums[1].table.changeBallState(areaNums[1].info.chosenBallSum, nBallId);
			}else{
				nBallId = iBallId - 20;
				areaNums[2].table.changeBallState(areaNums[2].info.chosenBallSum, nBallId);
			}
			break;
		}


	}

}