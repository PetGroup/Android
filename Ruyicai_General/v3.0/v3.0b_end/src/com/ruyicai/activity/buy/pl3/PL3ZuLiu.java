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
import com.ruyicai.code.pl3.PL3ZiZuXuanCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;

public class PL3ZuLiu extends ZixuanActivity implements BuyImplement,OnCheckedChangeListener{
	private int iCurrentButton;
	private LinearLayout topLinear;
	private LinearLayout topLinearTwo;
	private RadioGroup topButton;
	private String topTitle[] = { "����", "������ֵ" };
	private AreaInfo areaInfos[];// ѡ��
	private PL3ZiZuXuanCode pl3Code = new PL3ZiZuXuanCode();
	private PL3ZiHeZhiCode pl3CodeHZ = new PL3ZiHeZhiCode();
	private int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	BallTable oneBallTable;

	/**
	 * ��ʼ�����
	 */
	public void init() {
		topLinear = (LinearLayout) findViewById(R.id.buy_zixuan_linear_top);
		topLinearTwo = (LinearLayout) findViewById(R.id.buy_zixuan_linear_top_two);
		topLinear.setVisibility(LinearLayout.VISIBLE);
		topLinearTwo.setVisibility(LinearLayout.VISIBLE);
		initRadioGroup();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}
	
	/**
	 * ��ʼ����ѡ��ť��
	 */
	public void initRadioGroup() {
		topButton = (RadioGroup) findViewById(R.id.buy_zixuan_radiogroup_top);
		for (int i = 0; i < topTitle.length; i++) {
			RadioButton radio = new RadioButton(this);
			radio.setText(topTitle[i]);
			radio.setTextColor(Color.BLACK);
			radio.setTextSize(13);
			radio.setId(i);
			radio.setButtonDrawable(R.drawable.radio_select);
			radio.setPadding(Constants.PADDING, 0, 10, 0);
			topButton.addView(radio);

		}
		topButton.setOnCheckedChangeListener(this);
		topButton.check(0);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (group.getId()) {
		case R.id.buy_zixuan_radiogroup_top:
			switch (checkedId) {
			case 0://����ֱѡ
				iCurrentButton = PublicConst.BUY_PL3_ZU6;
				create_ZU6();
				break;
			case 1:// ������ֵ
				iCurrentButton = PublicConst.BUY_PL3_HEZHI_ZU6;
				create_PL3_HEZHI_ZU6();
				break;
			}
		}
		
	}
	/**
	 * ����3D��ֵ��6
	 */
	private void create_PL3_HEZHI_ZU6() {
		pl3CodeHZ.setiCurrentButton(iCurrentButton);
        String title = getResources().getString(R.string.fc3d_text_hezhi_title).toString();
		areaInfos[0] = new AreaInfo(22, 1, ballResId, 0, 3,Color.RED,title);
		createView(areaInfos, pl3CodeHZ,this,false);
		oneBallTable = areaNums[0].table;
	}
	/**
	 * ����
	 */
	private void create_ZU6() {
		pl3Code.setiCurrentButton(iCurrentButton);
		areaInfos = new AreaInfo[1];
		iCurrentButton = PublicConst.BUY_PL3_ZU6;
		String title = getResources().getString(R.string.fc3d_text_hezhi_title).toString();
		areaInfos[0] = new AreaInfo(10, 9, ballResId, 0, 0, Color.RED, title);
		createView(areaInfos, pl3Code, this,true);
		oneBallTable = areaNums[0].table;
	}

	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		// TODO Auto-generated method stub
		String mTextSumMoney = "";
		int iZhuShu = getZhuShu();
		switch (iCurrentButton) {
		
			case PublicConst.BUY_PL3_HEZHI_ZU6:
			
				if(iZhuShu==0){
					mTextSumMoney = "��Ҫ1����";
				}else{
					mTextSumMoney = "��"+ (iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ");
				}
			break;
			case PublicConst.BUY_PL3_ZU6:	// ����3D��6
				if (oneBallTable.getHighlightBallNums() > 2) {
					mTextSumMoney = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
				}else{
					int num = 3 - oneBallTable.getHighlightBallNums();
					mTextSumMoney = "����Ҫ"+num+"����";
				}
			break;
		}
		return mTextSumMoney;
	}
	/**
	 * 
	 * @return
	 */
     public int getZhuShu(){
    	int iReturnValue = 0;
 		// ����3D��3
 		switch (iCurrentButton) {
 		case PublicConst.BUY_PL3_ZU6:
			int iZu6Balls = oneBallTable.getHighlightBallNums();
			iReturnValue = (int) getFc3dZu6FushiZhushu(iZu6Balls);
 			break;
 		case PublicConst.BUY_PL3_HEZHI_ZU6:
 			iReturnValue= getFc3dZu6HezhiZhushu() ;
			break;
 		}
		return iReturnValue* iProgressBeishu ;
    	 
     }
 	/**
 	 * ��ø���3D��6��ֵע��
 	 * 
 	 * @return ����ע��
 	 */
 	private int getFc3dZu6HezhiZhushu() {
 		int iZhuShu = 0;
 		int[] BallNos = oneBallTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���3�����3��
 		int[] BallNoZhushus = { 1, 1, 2, 3, 4, 5, 7, 8, 9, 10, 10, 10, 10, 9,
 				8, 7, 5, 4, 3, 2, 1, 1 };// 3~24

 		for (int i = 0; i < BallNos.length; i++) {
 			for (int j = 0; j < BallNoZhushus.length; j++) {
 				if (j == BallNos[i] - 3) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С��ʵ��Id��1��ʼ���ʼ�ȥ1
 					// ɾ������ cc 20100713
 					iZhuShu = BallNoZhushus[j];// *iProgressBeishu;
 					// String temp = "��ǰ�淨Ϊ��ֵ��6����"
 					// +(iZhuShu+"ע����"+(iZhuShu*2)+"Ԫ");
 					// mTextSumMoney.setText(temp);
 				}
 			}
 		}
 		return iZhuShu;
 	}
 	/**
  	 * ��ø���3D��6��ʽע��
  	 * 
  	 * @param iZu6balls
  	 *            ѡ��С�����
  	 * @return ����ע��
  	 */
  	private long getFc3dZu6FushiZhushu(int iZu6balls) {
  		long tempzhushu = 0l;
  		if (iZu6balls > 0) {
  			tempzhushu += PublicMethod.zuhe(3, iZu6balls);
  		}
  		return tempzhushu;

  	}

	@Override
	public void isTouzhu() {
		// TODO Auto-generated method stub
		switch (iCurrentButton) {
		case PublicConst.BUY_PL3_ZU6:
			if (oneBallTable.getHighlightBallNums() < 3) {
				alertInfo("������ѡ��3��С�����Ͷע");
			} else {
				int[] fushiNums = oneBallTable.getHighlightBallNOs();
				String fushiStr = "";
				for (int i = 0; i < fushiNums.length; i++) {
					fushiStr += (fushiNums[i]) + ",";
					if (i == fushiNums.length - 1) {
						fushiStr = fushiStr.substring(0,
								fushiStr.length() - 1);
					}
				}
				int iZhuShu = getZhuShu();

				if (iZhuShu * 2 > 100000) {
					dialogExcessive();
				} else {
					setZhuShu(iZhuShu);
					alert(  "ע�룺" + fushiStr );
				}
			}
			break;

		case PublicConst.BUY_PL3_HEZHI_ZU6:
			if (oneBallTable.getHighlightBallNums() < 1) {
				alertInfo("��ѡ��С��������Ͷע");
			} else if (oneBallTable.getHighlightBallNums() == 1) {
				// wangyl 7.13 ��ϳ³�Ͷעʱ��
				int iZhuShu = getZhuShu();
				String fushiStr = PublicMethod.getZhuMa(oneBallTable.getHighlightBallNOs()[0])+"";
				if (iZhuShu * 2 > 100000) {
					dialogExcessive();
				} else {
					setZhuShu(iZhuShu);
					alert( "ע�룺" + fushiStr);
				}
			}
			break;
		}
	}

	/**
	 * ����С��id�ж����ĸ�ѡ��
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId){
		switch (iCurrentButton) {
		case PublicConst.BUY_PL3_ZU6:
			areaNums[0].table.changeBallState(areaNums[0].info.chosenBallSum, iBallId);
			break;

		case PublicConst.BUY_PL3_HEZHI_ZU6:
			areaNums[0].table.clearAllHighlights();
			areaNums[0].table.changeBallState(areaNums[0].info.chosenBallSum, iBallId);
			  break;
		}


	}
	@Override
	public void touzhuNet() {
		// TODO Auto-generated method stub
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_PL3);
	}

}