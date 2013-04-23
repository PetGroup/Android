package com.ruyicai.activity.buy.twentytwo;

import android.graphics.Color;
import android.os.Bundle;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.buy.zixuan.BuyViewItem;
import com.ruyicai.activity.buy.zixuan.ZixuanActivity;
import com.ruyicai.code.twenty.TwentyDanTuoCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;

public class TwentyDanTuo extends ZixuanActivity {
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
    private AreaInfo areaInfos[]= new AreaInfo[2];//2��ѡ��
    private TwentyDanTuoCode Code =new TwentyDanTuoCode(); 
	BallTable redBallTable ;
	BallTable redTuoBallTable;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCode(Code);
		initViewItem();
		redBallTable = itemViewArray.get(0).areaNums[0].table;
		redTuoBallTable = itemViewArray.get(0).areaNums[1].table;
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
	  areaNums[0] = new AreaNum(22,8, 4, redBallResId , 0, 1,Color.RED,danma);		
	  areaNums[1] = new AreaNum(22,8, 20, redBallResId , 0, 1,Color.RED,tuoma);	
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
    /**
     * �ж��Ƿ�����Ͷע����    
     */
	public String isTouzhu() {
        String isTouzhu = "";
		int iZhuShu = getZhuShu();
		// //������ 7.4 �����޸ģ���ӵ�½���ж�

		int redNumber = redBallTable.getHighlightBallNums();
		int redTuoNumber = redTuoBallTable.getHighlightBallNums();
		if (redNumber + redTuoNumber > 25
				|| redNumber + redTuoNumber < 6 || redNumber < 1
				|| redNumber > 4 
				|| redTuoNumber < 2 || redTuoNumber > 21) {
			isTouzhu = "��ѡ��:\n1~4�����룻\n" + " 5~21�����룻\n" + " �������������֮�Ͳ�С��6��";
		} else if (iZhuShu * 2 > 100000) {
			isTouzhu = "false";
		} else {
			isTouzhu = "true";
		}
		return isTouzhu;
	}

	/**
	 * Ͷע��ʾ���е���Ϣ(ע��)
	 * @return
	 */
	public String getZhuma(){
		String red_zhuma_string = " ";
		int[] redZhuMa = redBallTable.getHighlightBallNOs();
		for (int i = 0; i < redBallTable.getHighlightBallNOs().length; i++) {
			red_zhuma_string = red_zhuma_string + PublicMethod.isTen(redZhuMa[i]);
			if (i != redBallTable.getHighlightBallNOs().length - 1) {
				red_zhuma_string = red_zhuma_string + ",";
			}
		}
		String red_tuo_zhuma_string = " ";
		int[] redTuoZhuMa = redTuoBallTable.getHighlightBallNOs();
		for (int i = 0; i < redTuoBallTable.getHighlightBallNOs().length; i++) {
			red_tuo_zhuma_string = red_tuo_zhuma_string + PublicMethod.isTen(redTuoZhuMa[i]);
			if (i != redTuoBallTable.getHighlightBallNOs().length - 1)
				red_tuo_zhuma_string = red_tuo_zhuma_string + ",";
		}
		return "ע��:\n" + "��������" + red_zhuma_string + "\n" + "��������" + red_tuo_zhuma_string ;
		      
//		
	}
	/**
	 * ����ע��
	 */
	public int getZhuShu(){
		int iZhuShu = 0;
		int iRedHighlights = redBallTable.getHighlightBallNums();
		int iRedTuoHighlights = redTuoBallTable.getHighlightBallNums();
		iZhuShu = (int) getTwentyDTZhuShu(iRedHighlights, iRedTuoHighlights,iProgressBeishu);
		return iZhuShu;		
	}
    /**
     * Ͷע����
     */
	public void touzhuNet() {
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_22_5);
	}

	 /**
     * ѡ��С����ʾ���
     */
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		// TODO Auto-generated method stub
		int iRedHighlights = areaNum[0].table.getHighlightBallNums();
		int iRedTuoHighlights = areaNum[1].table.getHighlightBallNums();
		String iTempString = "" ;

		if (iRedHighlights + iRedTuoHighlights < 6 ) {
			int num = 6-iRedHighlights-iRedTuoHighlights;
         	  if(iRedHighlights==0){
          		 return "����ѡ��1������";  
          	  }else{
          		 return "���ٻ���Ҫ"+num+"������";
          	  } 
		} else if(iRedHighlights==0){
			 return "����ѡ��1������";  
		}else {
			int iZhuShu = (int) getTwentyDTZhuShu(iRedHighlights, iRedTuoHighlights, iProgressBeishu);
			iTempString = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
	
		}
		return iTempString;
	}
	/**
	 * ��ʽ�淨ע�����㷽��
	 * @param int aRedBalls �������
	 * 
	 * @return long ע��
	 */
	private long getTwentyDTZhuShu(int aRedBalls, int aRedTuoBalls,int iProgressBeishu) {
		long ssqZhuShu = 0L;
		if (aRedBalls > 0 && aRedTuoBalls > 0 ) {
			ssqZhuShu += (PublicMethod.zuhe(5 - aRedBalls, aRedTuoBalls) * iProgressBeishu);
		}
		return ssqZhuShu;
	}

}

