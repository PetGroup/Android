/**
 * 
 */
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
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;

/**
 * ��������ֵ
 * @author Administrator
 *
 */
public class PL3ZiHeZhi extends ZixuanActivity implements BuyImplement,OnCheckedChangeListener{
	private LinearLayout topLinear;	
	private LinearLayout topLinearTwo;	
	private RadioGroup topButton;
	private String topTitle[]={"ֱѡ��ֵ","������ֵ","������ֵ"};
	public static int iCurrentButton;//��ǩ
	private int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private AreaInfo areaInfos[] = new AreaInfo[1];// 1��ѡ��
	private PL3ZiHeZhiCode fc3dCode = new PL3ZiHeZhiCode();
	BallTable ballTable ;
	
	public void onCreate(Bundle savedInstanceState) {
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
	/**
	 * ����3D��ֱֵѡ
	 */
	private void create_FC3D_HEZHI_ZHIXUAN() {
        String title = getResources().getString(R.string.fc3d_text_hezhi_title).toString();
		areaInfos[0] = new AreaInfo(28, 1, ballResId, 0, 0,Color.RED,title);
		createView(areaInfos, fc3dCode,this);
		ballTable = areaNums[0].table;
	}
	/**
	 * ����3D��ֵ��3
	 */
	private void create_FC3D_HEZHI_ZU3() {
        String title = getResources().getString(R.string.fc3d_text_hezhi_title).toString();
		areaInfos[0] = new AreaInfo(26, 1, ballResId, 0, 1,Color.RED,title);
		createView(areaInfos, fc3dCode,this);
		ballTable = areaNums[0].table;
	}
	/**
	 * ����3D��ֵ��6
	 */
	private void create_FC3D_HEZHI_ZU6() {
        String title = getResources().getString(R.string.fc3d_text_hezhi_title).toString();
		areaInfos[0] = new AreaInfo(22, 1, ballResId, 0, 3,Color.RED,title);
		createView(areaInfos, fc3dCode,this);
		ballTable = areaNums[0].table;
	}
	/**
	 * ��дRadioGroup��������onCheckedChanged
	 * 
	 * @param RadioGroup
	 *            RadioGroup
	 * @param int checkedId ��ǰ��ѡ���RadioId
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (group.getId()) {
		case R.id.buy_zixuan_radiogroup_top:
			switch (checkedId) {
			case 0:// ��ֱֵѡ
				iCurrentButton=PublicConst.BUY_PL3_HEZHI_ZHIXUAN;
				create_FC3D_HEZHI_ZHIXUAN();
				break;
			case 1:// ��ֵ����
				iCurrentButton=PublicConst.BUY_PL3_HEZHI_ZU3;
				create_FC3D_HEZHI_ZU3();
				break;
			case 2:// ��ֵ����
				iCurrentButton=PublicConst.BUY_PL3_HEZHI_ZU6;
				create_FC3D_HEZHI_ZU6();
				break;
			}
		}
		
	}
	/**
	 * ����С��id�ж����ĸ�ѡ��
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId){
		int nBallId = 0; 
		for(int i=0;i<areaNums.length;i++){
			nBallId = iBallId;
			iBallId = iBallId - areaNums[i].info.areaNum;
			if(iBallId<0){
				  areaNums[i].table.clearAllHighlights();
				  areaNums[i].table.changeBallState(areaNums[i].info.chosenBallSum, nBallId);
				  break;
			}

	     }

	}

	/**
	 * �ж��Ƿ�����Ͷע����
	 */
	public void isTouzhu() {
		// TODO Auto-generated method stub
			if (ballTable.getHighlightBallNums() < 1) {
				alertInfo("��ѡ��С��������Ͷע");
			} else if (ballTable.getHighlightBallNums() == 1) {
				// wangyl 7.13 ��ϳ³�Ͷעʱ��
				int iZhuShu = getZhuShu();
				String fushiStr = ballTable.getHighlightBallNOs()[0]+ "";
				if (iZhuShu * 2 > 100000) {
					dialogExcessive();
				} else {
					alert( "ע����"
							+ iZhuShu / iProgressBeishu + "ע" + "\n"
							+ "������" + iProgressBeishu + "��" + "\n"
							+ "׷�ţ�" + iProgressQishu + "��" + "\n"
							+ "��" + iZhuShu * 2 + "Ԫ" + "\n"
							+ "�����"
							+ (2 * (iProgressQishu - 1) * iZhuShu)
							+ "Ԫ"  , "ע�룺\n" + fushiStr + "\n" );
				}
			}
	}

	 /**
	    * ���С����ʾ���
	    * @param areaNum
	    * @param iProgressBeishu
	    * @return
	  */
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		// TODO Auto-generated method stub
		String mTextSumMoney = "";
		int iZhuShu = getZhuShu() ;
		if(iZhuShu==0){
			mTextSumMoney = "��Ҫ1����";
		}else{
			mTextSumMoney = "��"+ (iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ");
		}
		return mTextSumMoney;
	}
  /**
   * ��ȡע��
   * 
   */
	public int getZhuShu(){
		int zhushu = 0;
		switch(iCurrentButton){
		case PublicConst.BUY_PL3_HEZHI_ZHIXUAN:
			zhushu= getPL3ZhixuanHezhiZhushu() ;
			break;
		case PublicConst.BUY_PL3_HEZHI_ZU3:
			zhushu= getPL3Zu3HezhiZhushu();
			break;
		case PublicConst.BUY_PL3_HEZHI_ZU6:
			zhushu= getPL3Zu6HezhiZhushu() ;
			break;
		}
		return zhushu* iProgressBeishu;
	}
	/**
	 * ���������ֱѡ��ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getPL3ZhixuanHezhiZhushu() {
		int iZhuShu = 0;
		int[] BallNos = ballTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���1�����0������ʵ��ѡ��ļ�ȥ1
		int[] BallNoZhushus = { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 63, 69,
				73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15, 10, 6, 3, 1 };// 0~27

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i]) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С������1��ʼ���ʼ�ȥ1
					iZhuShu += BallNoZhushus[j];
				}
			}
		}
		return iZhuShu;
	}

	/**
	 * �����������3��ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getPL3Zu3HezhiZhushu() {
		int iZhuShu = 0;
		int[] BallNos = ballTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���3�����3��
		int[] BallNoZhushus = { 1, 2, 1, 3, 3, 3, 4, 5, 4, 5, 5, 4, 5, 5, 4, 5,
				5, 4, 5, 4, 3, 3, 3, 1, 2, 1 };// 1~26

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i] - 1) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С��ʵ��Id��0��ʼ
					iZhuShu += BallNoZhushus[j];
				}
			}
		}
		return iZhuShu;
	}

	/**
	 * �����������6��ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getPL3Zu6HezhiZhushu() {
		int iZhuShu = 0;
		int[] BallNos = ballTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���3�����1��
		int[] BallNoZhushus = { 1, 1, 2, 3, 4, 5, 7, 8, 9, 10, 10, 10, 10, 9,
				8, 7, 5, 4, 3, 2, 1, 1 };// 3~24

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i] - 3) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С��ʵ��Id��1��ʼ���ʼ�ȥ1
					iZhuShu += BallNoZhushus[j];
				}
			}
		}
		return iZhuShu;
	}

	/**
	 * Ͷע����
	 */
	public void touzhuNet() {
		// TODO Auto-generated method stub
		int zhuShu=getZhuShu();
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_PL3);
		betAndGift.setAmount(""+zhuShu*200);
	}

}
