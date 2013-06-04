package com.ruyicai.activity.buy.fc3d;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.buy.zixuan.BuyViewItem;
import com.ruyicai.activity.buy.zixuan.ZixuanActivity;
import com.ruyicai.code.fc3d.F3dZiHeZhiCode;
import com.ruyicai.code.fc3d.Fc3dZiZuXuanCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;

public class FC3DZuSan extends ZixuanActivity implements OnCheckedChangeListener{
	private int iCurrentButton;
	private LinearLayout topLinear;
	private LinearLayout topLinearTwo;
	private RadioGroup topButton;
	private String topTitle[]={"��ʽ","��ʽ","��ֵ"};
	private int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private AreaInfo areaInfos[];// ѡ��
	private F3dZiHeZhiCode fc3dCodeHZ = new  F3dZiHeZhiCode();
	private Fc3dZiZuXuanCode fc3dCodeZX = new Fc3dZiZuXuanCode();
	BallTable ballTable ;
	
	BallTable oneBallTable;
	BallTable twoBallTable;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		topLinear = (LinearLayout) findViewById(R.id.buy_zixuan_linear_top);
		topLinear.setVisibility(LinearLayout.VISIBLE);
		topLinearTwo = (LinearLayout) findViewById(R.id.buy_zixuan_linear_top_two);
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
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (group.getId()) {
		case R.id.buy_zixuan_radiogroup_top:
			switch (checkedId) {
			case 0:// ������ʽ
				iCurrentButton = PublicConst.BUY_FC3D_ZU3_DAN;
				create_ZU3_DAN();
				break;
			case 1:// ������ʽ
				iCurrentButton = PublicConst.BUY_FC3D_ZU3_FU;
				create_ZU3_FU();
				break;
			case 2:// ������ֵ
				iCurrentButton=PublicConst.BUY_FC3D_HEZHI_ZU3;
				create_FC3D_HEZHI_ZU3();
				break;
			}
		}
		
	}
	/**
	 * ������ʽ
	 */
	public void create_ZU3_DAN() {
		fc3dCodeZX.setiCurrentButton(iCurrentButton);
		areaInfos = new AreaInfo[2];
        setCode(fc3dCodeZX);
        setIsTen(true);
		initViewItem();
		oneBallTable = itemViewArray.get(0).areaNums[0].table;
		twoBallTable = itemViewArray.get(0).areaNums[1].table;
	}

	/**
	 * ������ʽ
	 * 
	 */
	public void create_ZU3_FU() {
		fc3dCodeZX.setiCurrentButton(iCurrentButton);
		setCode(fc3dCodeZX);
		setIsTen(true);
		initViewItem();
		oneBallTable = itemViewArray.get(0).areaNums[0].table;
	}
	
	/**
	 * ����3D������ֵ
	 */
	private void create_FC3D_HEZHI_ZU3() {
		fc3dCodeHZ.setiCurrentButton(iCurrentButton);
		setCode(fc3dCodeHZ);
		setIsTen(false);
		initViewItem();
		ballTable = itemViewArray.get(0).areaNums[0].table;
	}
	/**
	 * ��ʼ��ѡ������
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
	 * ��ʼ��ѡ��
	 */
	public AreaNum[] initArea() {
		AreaNum areaNums[] = null;
		switch (iCurrentButton) {
		case PublicConst.BUY_FC3D_ZU3_DAN:
			areaNums = new AreaNum[2];
			String baiTitle = getResources().getString(R.string.fc3d_text_zu3_may2).toString();
			String shiTitle = getResources().getString(R.string.fc3d_text_zu3_may1).toString();
			areaNums[0] = new AreaNum(10,10, 1, ballResId, 0, 0, Color.RED, baiTitle);
			areaNums[1] = new AreaNum(10,10, 1, ballResId, 0, 0, Color.RED, shiTitle);
	        return areaNums;
		case PublicConst.BUY_FC3D_ZU3_FU:
			areaNums = new AreaNum[1];
			String title2 = getResources().getString(R.string.fc3d_text_hezhi_title).toString();
			areaNums[0] = new AreaNum(10,10, 10, ballResId, 0, 0, Color.RED, title2);
	        return areaNums;
		case PublicConst.BUY_FC3D_HEZHI_ZU3:
			areaNums = new AreaNum[1];
	        String title3 = getResources().getString(R.string.fc3d_text_hezhi_title).toString();
	        areaNums[0] = new AreaNum(26,8, 1, ballResId, 0, 1,Color.RED,title3);
	        return areaNums;
		}
		return areaNums;
	}

	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		String mTextSumMoney = "";
		switch (iCurrentButton) {
		case  PublicConst.BUY_FC3D_ZU3_DAN:
			if (oneBallTable.getHighlightBallNums() == 1&& twoBallTable.getHighlightBallNums() == 1) {
				int iZhuShu = iProgressBeishu;
				mTextSumMoney = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
			}else if(oneBallTable.getHighlightBallNums() == 0){
				mTextSumMoney = "��ѡ��������ε�С��";
			}else if(twoBallTable.getHighlightBallNums() == 0){
				mTextSumMoney = "��ѡ��ֻ����һ�ε�С��";
			}
			break;

		case  PublicConst.BUY_FC3D_ZU3_FU:
			if (oneBallTable.getHighlightBallNums() > 1) {
				int iZhuShu = getZhuShu();
				mTextSumMoney = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
			}else{
				int num = 2 - oneBallTable.getHighlightBallNums();
				mTextSumMoney = "����Ҫ"+num+"����";
			}
			break;
		case  PublicConst.BUY_FC3D_HEZHI_ZU3:
			int iZhuShu = getZhuShu();
			if(iZhuShu==0){
				mTextSumMoney = "��Ҫ1����";
			}else{
				mTextSumMoney = "��"+ (iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ");
			}
			break;
		}
		return mTextSumMoney;
	}

	@Override
	public String isTouzhu() {
		String isTouzhu = "";
		int iZhuShu = 0;
		switch (iCurrentButton) {
		case PublicConst.BUY_FC3D_ZU3_DAN:
		
			int baiweiNums = oneBallTable.getHighlightBallNums();
			int shiweiNums = twoBallTable.getHighlightBallNums();

			if (baiweiNums < 1 || shiweiNums < 1 ) {
				isTouzhu = "��ѡ�����һ�ε�С��ͳ������ε�С��";
			} else if (baiweiNums == 1 && shiweiNums == 1) {
				iZhuShu = 1;
				String baiweistr = oneBallTable.getHighlightBallNOs()[0]+"";// �������εĺ���
				String geweistr = 	twoBallTable.getHighlightBallNOs()[0]+"";//����һ�εĺ���
				if (iZhuShu * 2 > 100000) {
//					dialogExcessive();
					isTouzhu = "false";
				} else {
//					setZhuShu(iZhuShu);
//					if( oneBallTable.getHighlightBallNOs()[0]>twoBallTable.getHighlightBallNOs()[0]){
//						alert(  "ע�룺" + geweistr + ","+ baiweistr + "," + baiweistr  );
//					}else{
//						
//						alert(  "ע�룺" + baiweistr + "," + baiweistr + ","+ geweistr );
//					}
					isTouzhu = "true";
				}
			}
	
			break;
		case PublicConst.BUY_FC3D_ZU3_FU:
			if (oneBallTable.getHighlightBallNums() < 2) {
				isTouzhu = "������ѡ��2��С�����Ͷע";
			} else {
				// wangyl 7.12 �޸�ȷ��Ͷע�ɹ���ĶԻ���
				int[] fushiNums = oneBallTable.getHighlightBallNOs();
				String fushiStr = "";
				for (int i = 0; i < fushiNums.length; i++) {
					fushiStr += fushiNums[i] + ",";
					if (i == fushiNums.length - 1) {
						fushiStr = fushiStr.substring(0, fushiStr.length() - 1);
					}
				}
				iZhuShu = getZhuShu();

				if (iZhuShu * 2 > 100000) {
//					dialogExcessive();
					isTouzhu = "false";
				} else {
//					setZhuShu(iZhuShu);
//					alert("ע�룺" + fushiStr );
					isTouzhu = "true";
				}
			}
		
			
			break;	
		case PublicConst.BUY_FC3D_HEZHI_ZU3:

		// TODO Auto-generated method stub
			if (ballTable.getHighlightBallNums() < 1) {
				isTouzhu = "��ѡ��С��������Ͷע";
			} else if (ballTable.getHighlightBallNums() == 1) {
				// wangyl 7.13 ��ϳ³�Ͷעʱ��
				iZhuShu = getZhuShu();
				String fushiStr = PublicMethod.getZhuMa(ballTable.getHighlightBallNOs()[0])+"";
				if (iZhuShu * 2 > 100000) {
//					dialogExcessive();
					isTouzhu = "false";
				} else {
//					setZhuShu(iZhuShu);
//					alert( "ע�룺" + fushiStr);
					isTouzhu = "true";
				}
			}
		
			break;
		}
		return isTouzhu;
	}
/**
   * ��ȡע��
   * 
   */
	public int getZhuShu(){
		int zhushu = 0;
		switch(iCurrentButton){
		case PublicConst.BUY_FC3D_ZU3_DAN:
			zhushu = 1;
			break;
		case PublicConst.BUY_FC3D_ZU3_FU:
			int iZu3Balls = oneBallTable.getHighlightBallNums();
			zhushu = (int) getFc3dZu3FushiZhushu(iZu3Balls); ;
			break;
		case PublicConst.BUY_FC3D_HEZHI_ZU3:
			zhushu = getFc3dZu3HezhiZhushu() ;
			break;
		}
		return zhushu* iProgressBeishu;
	}
	/**
 	 * ��ø���3D��3��ʽע��
 	 * 
 	 * @param iZu3balls
 	 *            ѡ��С�����
 	 * @return ����ע��
 	 */
 	private long getFc3dZu3FushiZhushu(int iZu3balls) {
 		long tempzhushu = 0l;
 		if (iZu3balls > 0) {
 			tempzhushu += PublicMethod.zuhe(2, iZu3balls) * 2;
 		}
 		return tempzhushu;

 	}
	/**
	 * ��ø���3D��3��ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getFc3dZu3HezhiZhushu() {
		int iZhuShu = 0;
		int[] BallNos = ballTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���3�����3��
		int[] BallNoZhushus = { 1, 2, 1, 3, 3, 3, 4, 5, 4, 5, 5, 4, 5, 5, 4, 5,
				5, 4, 5, 4, 3, 3, 3, 1, 2, 1 };// 1~26

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i] - 1) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С��ʵ��Id��1��ʼ���ʼ�ȥ1
					// ɾ������ cc 20100713
					iZhuShu = BallNoZhushus[j];// *iProgressBeishu;
					// String temp = "��ǰ�淨Ϊ��ֵ��3����"
					// +(iZhuShu+"ע����"+(iZhuShu*2)+"Ԫ");
					// mTextSumMoney.setText(temp);
				}
			}
		}
		return iZhuShu;
	}
	@Override
	public void touzhuNet() {
		// TODO Auto-generated method stub
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_FC3D);
	}
	/**
	 * ����С��id�ж����ĸ�ѡ��
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId){
		AreaNum[] areaNums = itemViewArray.get(0).areaNums;
		int nBallId = 0; 
		switch (iCurrentButton) {
		case PublicConst.BUY_FC3D_ZU3_DAN:
			if(iBallId<areaNums[0].areaNum){
				nBallId = iBallId;
				areaNums[0].table.clearAllHighlights();
				areaNums[0].table.changeBallState(areaNums[0].chosenBallSum, nBallId);
				 if (areaNums[1].table.getOneBallStatue(nBallId) !=0) {
					areaNums[0].table.clearOnBallHighlight(nBallId);
				 }
			}else{
				nBallId = iBallId - areaNums[1].areaNum;
				areaNums[1].table.clearAllHighlights();
				areaNums[1].table.changeBallState(areaNums[1].chosenBallSum, nBallId);
				if (areaNums[0].table.getOneBallStatue(nBallId) !=0) {
					areaNums[1].table.clearOnBallHighlight(nBallId);
				 }
			}
			break;
		case PublicConst.BUY_FC3D_ZU3_FU:
			 areaNums[0].table.changeBallState(areaNums[0].chosenBallSum, iBallId);
			break;
		case PublicConst.BUY_FC3D_HEZHI_ZU3:
			 areaNums[0].table.clearAllHighlights();
			 areaNums[0].table.changeBallState(areaNums[0].chosenBallSum, iBallId);
			break;
		}

	     }

	@Override
	public String getZhuma() {
		// TODO Auto-generated method stub
		return null;
	}

}
