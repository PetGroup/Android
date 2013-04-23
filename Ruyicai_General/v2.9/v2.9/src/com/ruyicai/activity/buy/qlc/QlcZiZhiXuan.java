/**
 * 
 */
package com.ruyicai.activity.buy.qlc;

import android.graphics.Color;
import android.os.Bundle;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanActivity;
import com.ruyicai.code.qlc.QlcZiZhiXuanCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

/**
 * ���ֲ���ѡֱѡ
 * @author Administrator
 *
 */
public class QlcZiZhiXuan extends ZixuanActivity implements BuyImplement{
	private int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private AreaInfo areaInfos[] = new AreaInfo[1];// 1��ѡ��
	private QlcZiZhiXuanCode qlcCode = new QlcZiZhiXuanCode();
	BallTable ballTable ;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initArea();
		createView(areaInfos, qlcCode,this,true);
		ballTable = areaNums[0].table;
	}

	/**
	 * ��ʼ��ѡ��
	 */
	public void initArea() {
        String redTitle = getResources().getString(R.string.qlc_zhixuan_text_zhixuan_title).toString();
		areaInfos[0] = new AreaInfo(30, 16, ballResId, 0, 1,Color.RED,redTitle);
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	public int getZhuShu(){
		int iRedHighlights = ballTable.getHighlightBallNums();
		int iZhuShu = (int) getQLCFSZhuShu(iRedHighlights);
		return iZhuShu;
	}
	/**
	 * Ͷע��ʾ���е���Ϣ(ע��)
	 * @return
	 */
	public String getZhuma(){
		String red_zhuma_string = " ";
		int[] redZhuMa =  ballTable.getHighlightBallNOs();
		for (int i = 0; i <  ballTable.getHighlightBallNOs().length; i++) {
			red_zhuma_string += PublicMethod.getZhuMa(redZhuMa[i]);
			if (i !=  ballTable.getHighlightBallNOs().length - 1)
				red_zhuma_string += ",";
		}
		return "ע�룺" + "\n" + red_zhuma_string ;
		
	}
	 /**
	  * �ж��Ƿ�����Ͷע����
	 */
	public void isTouzhu() {
		// TODO Auto-generated method stub
		int iZhuShu = getZhuShu();
		 if (ballTable.getHighlightBallNums() < 7|| ballTable.getHighlightBallNums() > 16) {
			alertInfo("������ѡ��7~16����");
		} else if (iZhuShu * 2 > 100000) {
			dialogExcessive();
		} else {
			setZhuShu(iZhuShu);
			alert(getZhuma());
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
		String iTempString = "";
		int iRedHighlights = areaNum[0].table.getHighlightBallNums();
		// int iBlueHighlights = blueBallTable.getHighlightBallNums();

		// ������ ����//fqc edit ���������������ʱ����ʾ��Ӧ����ʾ
		if (iRedHighlights < 7) {
			int num = 7-iRedHighlights;
			return "���ٻ���Ҫ"+num+"��С��";
		} else {
			int iZhuShu = (int) getQLCFSZhuShu(iRedHighlights);
			return iTempString = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
		} 
	}
	/*
	 * ��ʽ�淨ע�����㷽��
	 * 
	 * @param int aRedBalls �������
	 * 
	 * 
	 * @return long ע��
	 */
	private long getQLCFSZhuShu(int aRedBalls) {
		long qlcZhuShu = 0L;
		if (aRedBalls > 0) {
			qlcZhuShu += (PublicMethod.zuhe(7, aRedBalls) * iProgressBeishu);
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
