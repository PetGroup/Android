/**
 * 
 */
package com.ruyicai.activity.buy.ssq;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.miss.BuyViewItem;
import com.ruyicai.activity.buy.miss.MainViewPagerAdapter;
import com.ruyicai.activity.buy.miss.NumViewItem;
import com.ruyicai.activity.buy.miss.ZixuanActivity;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.code.ssq.SsqZiDanTuoCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.json.miss.SsqMissJson;
import com.ruyicai.net.newtransaction.BetAndGiftInterface;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;



/**
 * ˫ɫ����ѡ����
 * @author Administrator
 *
 */
public class SsqZiDanTuo extends ZixuanActivity {
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private int blueBallResId[] = { R.drawable.grey, R.drawable.blue };// ѡ��С���л�ͼƬ
    private SsqZiDanTuoCode ssqCode =new SsqZiDanTuoCode(); 
	BallTable redBallTable ;
	BallTable redTuoBallTable;
	BallTable blueBallTable;
	ZixuanActivity zixuan;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		zixuan = this;
		setCode(ssqCode);
		setIsTen(false);
//		initViewItem();
		initGallery();
//		LinearLayout thirdLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_third);
//		thirdLinear.setVisibility(LinearLayout.VISIBLE);
		redBallTable = itemViewArray.get(0).areaNums[0].table;
		redTuoBallTable = itemViewArray.get(0).areaNums[1].table;
		blueBallTable = itemViewArray.get(0).areaNums[2].table;
		getMissNet(new SsqMissJson(),MissConstant.SSQ_Miss,MissConstant.SSQ_DAN);
	}
	/**
	 * ��ʼ������
	 */
	public void initGallery() {
				BuyViewItem buyView = new BuyViewItem(this,initArea());
				NumViewItem numView = new NumViewItem(this,initArea());
				// �����Ҫ���һ���Ч������ͼ������������
				itemViewArray.add(buyView);
				itemViewArray.add(numView);
				// ���� ViewPager �� Adapter
				MainViewPagerAdapter MianAdapter = new MainViewPagerAdapter(null);
				View view = numView.createView();
				numView.leftBtn(view);
				MianAdapter.addView(buyView.createView());
				MianAdapter.addView(view);
				viewPagerContainer.setAdapter(MianAdapter);
				// ���õ�һ��ʾҳ��
				viewPagerContainer.setCurrentItem(0);
	}
	/**
	 * ��ʼ��ѡ������
	 */
	public void initViewItem() {
		// TODO Auto-generated method stub
		   BuyViewItem buyView = new BuyViewItem(this,initArea());
		   NumViewItem numView = new NumViewItem(this,initArea());
		   itemViewArray.add(buyView);
		   itemViewArray.add(numView);
//		   mGallery.setViews(buyView.createView(),buyView.height,numView.createView(),numView.height);
//		   mGallery.setAreaWith(100);
	}
	/**
	 * ��ʼ��ѡ��
	 */
	public AreaNum[] initArea(){
	  AreaNum areaNums[] = new AreaNum[3];
	  String danma = getResources().getString(R.string.ssq_dantuo_text_red_danma_title).toString();
	  String tuoma = getResources().getString(R.string.ssq_dantuo_text_red_tuoma_title).toString();
	  String blue = getResources().getString(R.string.ssq_dantuo_text_blue_title).toString();
	  
	  areaNums[0] = new AreaNum(33,8, 5, redBallResId , 0, 1,Color.RED,danma);		
	  areaNums[1] = new AreaNum(33,8, 20, redBallResId , 0, 1,Color.RED,tuoma);	
	  areaNums[2] = new AreaNum(16,8, 16, blueBallResId , 0, 1,Color.BLUE,blue);	
	  return areaNums;
	}
	/**
	 * ����С��id�ж����ĸ�ѡ��
	 * 
	 * @param iBallId
	 */
	public void isBallTable(int iBallId){
		int aBallId = iBallId;
		for(int j=0;j<itemViewArray.size();j++){
		   AreaNum[] areaNums =  itemViewArray.get(j).areaNums;
		   int nBallId = 0;
		   iBallId = aBallId;
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
				}else {
					areaNums[2].table.changeBallState(areaNums[2].chosenBallSum, nBallId);
	
				}
				
				break;
			 }
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
    /**
     * �ж��Ƿ�����Ͷע����    
     * @return 
     */
	public String isTouzhu() {
		String isTouzhu = "";
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
			isTouzhu = "��ѡ��:\n1~5����ɫ���룻\n" + " 2~20����ɫ���룻\n"+ " 1~16����ɫ��\n" + " �������������֮����7~25֮��";
		} else if (iZhuShu * 2 > 100000) {
			isTouzhu = "false";
		} else {
			isTouzhu = "true";
		}
		return isTouzhu;
	}


}
