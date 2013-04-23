/**
 * 
 */
package com.ruyicai.activity.buy.qlc;

import android.graphics.Color;
import android.os.Bundle;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.zixuan.BuyViewItem;
import com.ruyicai.activity.buy.zixuan.ZixuanActivity;
import com.ruyicai.code.qlc.QlcZiDanTuoCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;

/**
 * ���ֲ���ѡ����
 * @author Administrator
 *
 */
public class QlcZiDanTuo extends ZixuanActivity {
	private int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private AreaInfo areaInfos[] = new AreaInfo[2];// 2��ѡ��
	private QlcZiDanTuoCode qlcCode = new QlcZiDanTuoCode();
	BallTable danBallTable ;
	BallTable tuoBallTable;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCode(qlcCode);
		initViewItem();
		danBallTable = itemViewArray.get(0).areaNums[0].table;
		tuoBallTable = itemViewArray.get(0).areaNums[1].table;
	}
	/**
	 * ��ʼ��ѡ������
	 */
	public void initViewItem() {
		// TODO Auto-generated method stub
		   BuyViewItem buyView = new BuyViewItem(this,initArea());
		   itemViewArray.add(buyView);
		   layoutView.addView(buyView.createView());
	}
	/**
	 * ��ʼ��ѡ��
	 */
	public AreaNum[] initArea(){
	  AreaNum areaNums[] = new AreaNum[2];
	  String danma = getResources().getString(R.string.ssq_dantuo_text_red_danma_title).toString();
	  String tuoma = getResources().getString(R.string.ssq_dantuo_text_red_tuoma_title).toString();
	  areaNums[0] = new AreaNum(30,8, 6, ballResId, 0, 1,Color.RED,danma);
	  areaNums[1] = new AreaNum(30,8, 20, ballResId, 0, 1,Color.RED,tuoma);	
	  return areaNums;
	}
	/**
	 * ����С��id�ж����ĸ�ѡ��
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId){
		AreaNum[] areaNums = itemViewArray.get(0).areaNums;
		int nBallId = 0; 
		for(int i=0;i<areaNums.length;i++){
			nBallId = iBallId;
			iBallId = iBallId - areaNums[i].areaNum;
			if(iBallId<0){
				if(i==0){
					int isHighLight = areaNums[0].table.changeBallState(areaNums[0].chosenBallSum, nBallId);
					if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT&& areaNums[1].table.getOneBallStatue(nBallId) !=0) {
						areaNums[1].table.clearOnBallHighlight(nBallId);

						toast.setText(getResources().getString(R.string.ssq_toast_danma_title));
						toast.show();
					}
	
				}else if(i==1){
					int isHighLight = areaNums[1].table.changeBallState(areaNums[1].chosenBallSum, nBallId);
					if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT&& areaNums[0].table.getOneBallStatue(nBallId) !=0) {
						areaNums[0].table.clearOnBallHighlight(nBallId);

						toast.setText(getResources().getString(R.string.ssq_toast_tuoma_title));
						toast.show();
					}
				}
				
				break;
			}

	     }

	}
    public int getZhuShu(){
		int iRedHighlights = danBallTable.getHighlightBallNums();
		int iRedTuoHighlights = tuoBallTable.getHighlightBallNums();
		int iReturnValue = (int) getQLCDTZhuShu(iRedHighlights,iRedTuoHighlights);
		return iReturnValue;
    }
	/**
	 * Ͷע��ʾ���е���Ϣ(ע��)
	 * @return
	 */
	public String getZhuma(){
		String red_zhuma_string = " ";
		int[] redZhuMa = danBallTable.getHighlightBallNOs();
		for (int i = 0; i < danBallTable.getHighlightBallNOs().length; i++) {
			red_zhuma_string += PublicMethod.getZhuMa(redZhuMa[i]);
			if (i != danBallTable.getHighlightBallNOs().length - 1)
				red_zhuma_string += ",";
		}
		String red_tuo_zhuma_string = " ";
		int[] redTuoZhuMa = tuoBallTable.getHighlightBallNOs();
		for (int i = 0; i < tuoBallTable.getHighlightBallNOs().length; i++) {
			red_tuo_zhuma_string += PublicMethod.getZhuMa(redTuoZhuMa[i]);
			if (i != tuoBallTable.getHighlightBallNOs().length - 1)
				red_tuo_zhuma_string += ",";
		}
		return "ע�룺\n" + "���룺" + red_zhuma_string + "\n" + "���룺"+ red_tuo_zhuma_string  ;
		
	}
	 /**
	  * �ж��Ƿ�����Ͷע����
	 */
	public String isTouzhu() {
        String isTouzhu = "";
		int iZhuShu = getZhuShu();
		int redNumber = danBallTable.getHighlightBallNums();
		int redTuoNumber = tuoBallTable.getHighlightBallNums();
		// int blueNumber = blueBallTable.getHighlightBallNums();
		 if ((redNumber < 1 || redNumber > 6)&& (redTuoNumber < 1 || redTuoNumber > 29)) {
			 isTouzhu = "��ѡ��1~6�����룬1~29�����룡";
		} else if (redNumber + redTuoNumber < 8) {
			isTouzhu = "���������֮������Ϊ8����";
		} else if (iZhuShu <= 0) {
			isTouzhu = "���������֮������Ϊ8����";
		} else if (iZhuShu * 2 > 100000) {
			isTouzhu = "false";
		} else {
			isTouzhu = "true";
		}
		 return isTouzhu;
	}
	 /**
	    * ���С����ʾ���
	    * @param areaNum
	    * @param iProgressBeishu
	    * @return
	    */
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		// TODO Auto-generated method stub
		String iTempString = "";
		int iRedDanHighlights = areaNum[0].table.getHighlightBallNums();
		int iRedTuoHighlights = areaNum[1].table.getHighlightBallNums();

		if (iRedDanHighlights + iRedTuoHighlights < 8) {
			int num = 8-iRedDanHighlights-iRedTuoHighlights;
       	    if(iRedDanHighlights==0){
       	    	iTempString = "ѡ��1������";  
        	  }else{
        		iTempString = "���ٻ���Ҫ"+num+"������";
        	  }
		}else {
			int iZhuShu = (int) getQLCDTZhuShu(iRedDanHighlights, iRedTuoHighlights);
			iTempString = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
		}
		
		return iTempString;
	}
	/*
	 * �����淨ע�����㷽��
	 * 
	 * @param int aRedBalls �������
	 * 
	 * @param int aRedTuoBalls �����������
	 * 
	 */
	private long getQLCDTZhuShu(int aRedBalls, int aRedTuoBalls) {
		long qlcZhuShu = 0L;
		if (aRedBalls > 0 && aRedTuoBalls > 0) {
			qlcZhuShu += (PublicMethod.zuhe(7 - aRedBalls, aRedTuoBalls) * iProgressBeishu);
		}
		return qlcZhuShu;
	}
	/**
	    * Ͷע����
	    */
	public void touzhuNet() {
		// TODO Auto-generated method stub
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_QLC);
		
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
}
