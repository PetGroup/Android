package com.ruyicai.activity.buy.twentytwo;

import android.graphics.Color;
import android.os.Bundle;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.buy.zixuan.BuyViewItem;
import com.ruyicai.activity.buy.zixuan.ZixuanActivity;
import com.ruyicai.code.twenty.TwentyZhiXuanCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.PublicMethod;

public class TwentyZiXuan extends ZixuanActivity {
	
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private AreaInfo areaInfos[] = new AreaInfo[1];// 1��ѡ��
	private TwentyZhiXuanCode Code = new TwentyZhiXuanCode();
	BallTable redBallTable ;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCode(Code);
		initViewItem();
		redBallTable = itemViewArray.get(0).areaNums[0].table;
	}
	/**
	 * ��ʼ��ѡ������
	 */
	public void initViewItem() {
		   BuyViewItem buyView = new BuyViewItem(this,initArea());
		   itemViewArray.add(buyView);
		   layoutView.addView(buyView.createView());
	}

	/**
	 * ��ʼ��ѡ��
	 */
	public AreaNum[] initArea() {
		AreaNum areaNums[] = new AreaNum[1];
		String redTitle = getResources().getString(R.string.please_choose_number).toString();
		areaNums[0] = new AreaNum(22,8, 18, redBallResId, 0, 1,Color.RED,redTitle);
        return areaNums;
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
		// TODO Auto-generated method stub
        String isTouzhu = "";
		int iZhuShu = getZhuShu();
		if (redBallTable.getHighlightBallNums() < 5) {
			isTouzhu = "������ѡ��5��С��	";
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
		
		return "ע�룺" + red_zhuma_string ;
		
	}
	/**
	 * ����ע��
	 */
	public int getZhuShu(){
		int iZhuShu = 0;
		int iRedHighlights = itemViewArray.get(0).areaNums[0].table.getHighlightBallNums();
		iZhuShu = (int) getTwentyZXZhuShu(iRedHighlights,iProgressBeishu);
		return iZhuShu;		
	}
    /**
     * Ͷע����
     */
	public void touzhuNet() {
		// TODO Auto-generated method stub
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_22_5);
		
	}
    /**
     * ѡ��С����ʾ���
     */
	public String textSumMoney(AreaNum areaNum[], int iProgressBeishu) {
		// TODO Auto-generated method stub
		int iRedHighlights = areaNum[0].table.getHighlightBallNums();
		String iTempString = "";
		int iZhuShu = 0;
		// ������ ����
		if (iRedHighlights < 5) {
			int num = 5-iRedHighlights;
			return "���ٻ���"+num+"��С��";
		}else{
			iZhuShu = (int) getTwentyZXZhuShu(iRedHighlights, iProgressBeishu);
			iTempString = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
		}
		// �������ﵽ���Ҫ��
		
		return iTempString;
	}
	
	/**
	 * ��ʽ�淨ע�����㷽��
	 * 
	 * @param int aRedBalls �������
	 * 
	 * 
	 * @return long ע��
	 */
	private long getTwentyZXZhuShu(int aRedBalls,int iProgressBeishu) {
		long ssqZhuShu = 0L;
		if (aRedBalls > 0 ) {
			ssqZhuShu += (PublicMethod.zuhe(5,aRedBalls) * iProgressBeishu);
		}
		return ssqZhuShu;
	}

}
