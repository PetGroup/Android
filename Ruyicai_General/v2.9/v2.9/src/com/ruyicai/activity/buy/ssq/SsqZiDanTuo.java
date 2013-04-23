/**
 * 
 */
package com.ruyicai.activity.buy.ssq;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanActivity;
import com.ruyicai.code.ssq.SsqZiDanTuoCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;

/**
 * ˫ɫ����ѡ����
 * @author Administrator
 *
 */
public class SsqZiDanTuo extends ZixuanActivity implements BuyImplement{
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private int blueBallResId[] = { R.drawable.grey, R.drawable.blue };// ѡ��С���л�ͼƬ
    private AreaInfo areaInfos[]= new AreaInfo[3];//2��ѡ��
    private SsqZiDanTuoCode ssqCode =new SsqZiDanTuoCode(); 
	BallTable redBallTable ;
	BallTable redTuoBallTable;
	BallTable blueBallTable;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initArea();
		createView(areaInfos,ssqCode,this,true);
		LinearLayout thirdLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_third);
		thirdLinear.setVisibility(LinearLayout.VISIBLE);
		redBallTable = areaNums[0].table;
		redTuoBallTable = areaNums[1].table;
		blueBallTable = areaNums[2].table;
	}
	/**
	 * ��ʼ��ѡ��
	 */
	public void initArea(){
	  String danma = getResources().getString(R.string.ssq_dantuo_text_red_danma_title).toString();
	  String tuoma = getResources().getString(R.string.ssq_dantuo_text_red_tuoma_title).toString();
	  String blue = getResources().getString(R.string.ssq_dantuo_text_blue_title).toString();
	  
      areaInfos[0] = new AreaInfo( 33, 5, redBallResId , 0, 1,Color.RED,danma);		
      areaInfos[1] = new AreaInfo(33, 20, redBallResId , 0, 1,Color.RED,tuoma);	
      areaInfos[2] = new AreaInfo(16, 16, blueBallResId , 0, 1,Color.BLUE,blue);	
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
				if(i==0){
					int isHighLight = areaNums[0].table.changeBallState(areaNums[0].info.chosenBallSum, nBallId);
					if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT&& areaNums[1].table.getOneBallStatue(nBallId) !=0) {
						areaNums[1].table.clearOnBallHighlight(nBallId);

//						Toast.makeText(this,getResources().getString(R.string.ssq_toast_danma_title), Toast.LENGTH_SHORT).show();
						toast.setText(getResources().getString(R.string.ssq_toast_danma_title));
						toast.show();
					}
	
				}else if(i==1){
					int isHighLight = areaNums[1].table.changeBallState(areaNums[1].info.chosenBallSum, nBallId);
					if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT&& areaNums[0].table.getOneBallStatue(nBallId) !=0) {
						areaNums[0].table.clearOnBallHighlight(nBallId);

//						Toast.makeText(this,getResources().getString(R.string.ssq_toast_tuoma_title), Toast.LENGTH_SHORT).show();
						toast.setText(getResources().getString(R.string.ssq_toast_tuoma_title));
						toast.show();
					}
				}else {
					areaNums[2].table.changeBallState(areaNums[2].info.chosenBallSum, nBallId);
	
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
	public void isTouzhu() {
		// TODO Auto-generated method stub
		int iZhuShu = getZhuShu();
		// //������ 7.4 �����޸ģ���ӵ�½���ж�

		int redNumber = redBallTable.getHighlightBallNums();
		int redTuoNumber = redTuoBallTable.getHighlightBallNums();
		int blueNumber = blueBallTable.getHighlightBallNums();
		if (redNumber + redTuoNumber > 25
				|| redNumber + redTuoNumber < 7 || redNumber < 1
				|| redNumber > 5 || blueNumber < 1 || blueNumber > 16
				|| redTuoNumber < 2 || redTuoNumber > 20) {
			alertInfo("��ѡ��:\n1~5����ɫ���룻\n" + " 2~20����ɫ���룻\n"+ " 1~16����ɫ��\n" + " �������������֮����7~25֮��");
		} else if (iZhuShu * 2 > 100000) {
			dialogExcessive();
		} else {
			setZhuShu(iZhuShu);
			alert(getZhuma());
		}
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
		String blue_zhuma_string = " ";
		int[] blueZhuMa = blueBallTable.getHighlightBallNOs();
		for (int i = 0; i < blueBallTable.getHighlightBallNOs().length; i++) {
			blue_zhuma_string = blue_zhuma_string + PublicMethod.isTen(blueZhuMa[i]);
			if (i != blueBallTable.getHighlightBallNOs().length - 1) {
				blue_zhuma_string = blue_zhuma_string + ",";
			}
		}
		String red_tuo_zhuma_string = " ";
		int[] redTuoZhuMa = redTuoBallTable.getHighlightBallNOs();
		for (int i = 0; i < redTuoBallTable.getHighlightBallNOs().length; i++) {
			red_tuo_zhuma_string = red_tuo_zhuma_string + PublicMethod.isTen(redTuoZhuMa[i]);
			if (i != redTuoBallTable.getHighlightBallNOs().length - 1)
				red_tuo_zhuma_string = red_tuo_zhuma_string + ",";
		}
		return "ע��:\n" + "��������" + red_zhuma_string + "\n" + "��������" + red_tuo_zhuma_string + "\n" + "��������"
		       + blue_zhuma_string ;
		
	}
	/**
	 * ����ע��
	 */
	public int getZhuShu(){
		int iZhuShu = 0;
		int iRedHighlights = redBallTable.getHighlightBallNums();
		int iRedTuoHighlights = redTuoBallTable.getHighlightBallNums();
		int iBlueHighlights = blueBallTable.getHighlightBallNums();
		iZhuShu = (int) getSSQDTZhuShu(iRedHighlights, iRedTuoHighlights, iBlueHighlights, iProgressBeishu);
		return iZhuShu;		
	}
    /**
     * Ͷע����
     */
	public void touzhuNet() {
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_SSQ);
	}

	 /**
     * ѡ��С����ʾ���
     */
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		// TODO Auto-generated method stub
		int iRedHighlights = areaNum[0].table.getHighlightBallNums();
		int iRedTuoHighlights = areaNum[1].table.getHighlightBallNums();
		int iBlueHighlights = areaNum[2].table.getHighlightBallNums();
		String iTempString = "" ;

		if (iRedHighlights + iRedTuoHighlights < 7 ) {
			int num = 7-iRedHighlights-iRedTuoHighlights;
         	  if(iRedHighlights==0){
          		 return "����ѡ��1������";  
          	  }else{
          		 return "���ٻ���Ҫ"+num+"������";
          	  } 
		} else if(iRedHighlights==0){
			 return "����ѡ��1������";  
		}else if (iBlueHighlights < 1) {
			return "���ٻ���Ҫ1������";
		} else {
			int iZhuShu = (int) getSSQDTZhuShu(iRedHighlights, iRedTuoHighlights, iBlueHighlights, iProgressBeishu);
			iTempString = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
	
		}
		return iTempString;
	}
	/**
	 * ��ʽ�淨ע�����㷽��
	 * 
	 * @param int aRedBalls �������
	 * 
	 * @param int aBlueBalls �������
	 * 
	 * @return long ע��
	 */
	private long getSSQDTZhuShu(int aRedBalls, int aRedTuoBalls, int aBlueBalls,int iProgressBeishu) {
		long ssqZhuShu = 0L;
		if (aRedBalls > 0 && aRedTuoBalls > 0 && aBlueBalls > 0) {
			ssqZhuShu += (PublicMethod.zuhe(6 - aRedBalls, aRedTuoBalls)* PublicMethod.zuhe(1, aBlueBalls) * iProgressBeishu);
		}
		return ssqZhuShu;
	}

}
