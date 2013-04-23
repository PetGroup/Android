package com.ruyicai.activity.buy.ssq;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.miss.BuyViewItem;
import com.ruyicai.activity.buy.miss.MainViewPagerAdapter;
import com.ruyicai.activity.buy.miss.NumViewItem;
import com.ruyicai.activity.buy.miss.ZixuanActivity;
import com.ruyicai.code.ssq.SsqZiZhiXuanCode;
import com.ruyicai.constant.Constants;
import com.ruyicai.json.miss.MissConstant;
import com.ruyicai.json.miss.SsqMissJson;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.PublicMethod;

/**
 * ˫ɫ����ѡֱѡ
 * 
 * @author Administrator
 * 
 */
public class SsqZiZhiXuan extends ZixuanActivity {
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private int blueBallResId[] = { R.drawable.grey, R.drawable.blue };// ѡ��С���л�ͼƬ
	private SsqZiZhiXuanCode ssqCode = new SsqZiZhiXuanCode();
	BallTable redBallTable ;
	BallTable blueBallTable;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCode(ssqCode);
		setIsTen(false);
		initGallery();
		redBallTable = itemViewArray.get(0).areaNums[0].table;
		blueBallTable = itemViewArray.get(0).areaNums[1].table;
		getMissNet(new SsqMissJson(),MissConstant.SSQ_Miss,MissConstant.SSQ_ZI);
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
//		   BuyViewItem buyView = new BuyViewItem(this,initArea());
//		   NumViewItem numView = new NumViewItem(this,initArea());
//		   itemViewArray.add(buyView);
//		   itemViewArray.add(numView);
//		   mGallery.setViews(buyView.createView(),buyView.height,numView.createView(),numView.height);
//		   mGallery.setAreaWith(100);
	}

	/**
	 * ��ʼ��ѡ��
	 */
	public AreaNum[] initArea() {
		AreaNum areaNums[] = new AreaNum[2];
        String redTitle = getResources().getString(R.string.ssq_zhixuan_text_red_title).toString();
        String blueTitle = getResources().getString(R.string.ssq_zhixuan_text_blue_title).toString();
        areaNums[0] = new AreaNum(33,9, 20, redBallResId, 0, 1,Color.RED,redTitle);
        areaNums[1] = new AreaNum(16,9, 16, blueBallResId, 0, 1,Color.BLUE,blueTitle);
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
     * �ж��Ƿ�����Ͷע��Ͷע����    
     */
	public String isTouzhu() {
		String isTouzhu = "";
		int iZhuShu = getZhuShu();
		if (redBallTable.getHighlightBallNums() < 6 && blueBallTable.getHighlightBallNums() < 1	) {
			isTouzhu = "������ѡ��6�������1������";
		} else if (redBallTable.getHighlightBallNums() < 6) {
			isTouzhu = "��ѡ������6������";
		} else if (blueBallTable.getHighlightBallNums() < 1) {
			isTouzhu = "��ѡ��1������";
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
		String blue_zhuma_string = " ";
		int[] blueZhuMa = blueBallTable.getHighlightBallNOs();
		for (int i = 0; i < blueBallTable.getHighlightBallNOs().length; i++) {
			blue_zhuma_string = blue_zhuma_string + PublicMethod.isTen(blueZhuMa[i]);
			if (i != blueBallTable.getHighlightBallNOs().length - 1) {
				blue_zhuma_string = blue_zhuma_string + ",";
			}
		}
		return "ע�룺" + red_zhuma_string + " | "+ blue_zhuma_string ;
		
	}
	/**
	 * ����ע��
	 */
	public int getZhuShu(){
		int iZhuShu = 0;
		int iRedHighlights = itemViewArray.get(0).areaNums[0].table.getHighlightBallNums();
		int iBlueHighlights = itemViewArray.get(0).areaNums[1].table.getHighlightBallNums();
		iZhuShu = (int) getSSQZXZhuShu(iRedHighlights,iBlueHighlights,iProgressBeishu);
		return iZhuShu;		
	}
    /**
     * Ͷע������Ϣ
     */
	public void touzhuNet() {
		// TODO Auto-generated method stub
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_SSQ);
		
	}
    /**
     * ѡ��С����ʾ���
     */
	public String textSumMoney(AreaNum areaNum[], int iProgressBeishu) {
		// TODO Auto-generated method stub
		int iRedHighlights = areaNum[0].table.getHighlightBallNums();
		int iBlueHighlights = areaNum[1].table.getHighlightBallNums();
		String iTempString = "";
		int iZhuShu = 0;
		// ������ ����
		if (iRedHighlights < 6) {
			int num = 6-iRedHighlights;
			return "���ٻ���"+num+"������";
		}
		// �������ﵽ���Ҫ��
		else if (iRedHighlights == 6) {
			if (iBlueHighlights < 1) {
				return "���ٻ���1������";
			} else if (iBlueHighlights == 1) {
				iZhuShu = (int) getSSQZXZhuShu(iRedHighlights,iBlueHighlights,iProgressBeishu);
				iTempString = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
			} else {
				iZhuShu = (int) getSSQZXZhuShu(iRedHighlights,iBlueHighlights,iProgressBeishu);
				iTempString = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
			}
		}
		// ����ʽ
		else {
			if (iBlueHighlights < 1) {
				return "���ٻ���1������";
			} else if (iBlueHighlights == 1) {
				iZhuShu = (int) getSSQZXZhuShu(iRedHighlights,iBlueHighlights,iProgressBeishu);
				iTempString = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
			} else {
				iZhuShu = (int) getSSQZXZhuShu(iRedHighlights,iBlueHighlights,iProgressBeishu);
				iTempString = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
			}
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
	private long getSSQZXZhuShu(int aRedBalls, int aBlueBalls,int iProgressBeishu) {
		long ssqZhuShu = 0L;
		if (aRedBalls > 0 && aBlueBalls > 0) {
			ssqZhuShu += (PublicMethod.zuhe(6, aRedBalls)* PublicMethod.zuhe(1, aBlueBalls) * iProgressBeishu);
		}
		return ssqZhuShu;
	}


}
