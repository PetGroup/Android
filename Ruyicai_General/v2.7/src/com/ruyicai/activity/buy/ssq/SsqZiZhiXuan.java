/**
 * 
 */
package com.ruyicai.activity.buy.ssq;

import android.graphics.Color;
import android.os.Bundle;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanActivity;
import com.ruyicai.code.ssq.SsqZiZhiXuanCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

/**
 * ˫ɫ����ѡֱѡ
 * 
 * @author Administrator
 * 
 */
public class SsqZiZhiXuan extends ZixuanActivity implements BuyImplement{
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private int blueBallResId[] = { R.drawable.grey, R.drawable.blue };// ѡ��С���л�ͼƬ
	private AreaInfo areaInfos[] = new AreaInfo[2];// 2��ѡ��
	private SsqZiZhiXuanCode ssqCode = new SsqZiZhiXuanCode();
	BallTable redBallTable ;
	BallTable blueBallTable;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initArea();
		createView(areaInfos, ssqCode,this);
		redBallTable = areaNums[0].table;
		blueBallTable = areaNums[1].table;
	}

	/**
	 * ��ʼ��ѡ��
	 */
	public void initArea() {
        String redTitle = getResources().getString(R.string.ssq_zhixuan_text_red_title).toString();
        String blueTitle = getResources().getString(R.string.ssq_zhixuan_text_blue_title).toString();
		areaInfos[0] = new AreaInfo(33, 20, redBallResId, 0, 1,Color.RED,redTitle);
		areaInfos[1] = new AreaInfo(16, 16, blueBallResId, 0, 1,Color.BLUE,blueTitle);
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
		if (redBallTable.getHighlightBallNums() < 6 && blueBallTable.getHighlightBallNums() < 1) {
			alertInfo("������ѡ��6�������1������	");
		} else if (redBallTable.getHighlightBallNums() < 6) {
			alertInfo("��ѡ������6������");
		} else if (blueBallTable.getHighlightBallNums() < 1) {
			alertInfo("��ѡ��1������");
		} else if (iZhuShu * 2 > 100000) {
			dialogExcessive();
		} else {
			String sTouzhuAlert = "";
			sTouzhuAlert = getTouzhuAlert();
			alert(sTouzhuAlert,getZhuma());
		}
	}
	/**
	 * Ͷע��ʾ���е���Ϣ
	 */
	private String getTouzhuAlert() {
		int iZhuShu = getZhuShu();

	  return "ע����"+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"
					+ "\n" ;

	}
	/**
	 * Ͷע��ʾ���е���Ϣ(ע��)
	 * @return
	 */
	public String getZhuma(){
		String red_zhuma_string = " ";
		int[] redZhuMa = redBallTable.getHighlightBallNOs();
		for (int i = 0; i < redBallTable.getHighlightBallNOs().length; i++) {
			red_zhuma_string = red_zhuma_string + String.valueOf(redZhuMa[i]);
			if (i != redBallTable.getHighlightBallNOs().length - 1) {
				red_zhuma_string = red_zhuma_string + ",";
			}
		}
		String blue_zhuma_string = " ";
		int[] blueZhuMa = blueBallTable.getHighlightBallNOs();
		for (int i = 0; i < blueBallTable.getHighlightBallNOs().length; i++) {
			blue_zhuma_string = blue_zhuma_string
					+ String.valueOf(blueZhuMa[i]);
			if (i != blueBallTable.getHighlightBallNOs().length - 1) {
				blue_zhuma_string = blue_zhuma_string + ",";
			}
		}
		return "ע�룺\n" + red_zhuma_string + " | "+ blue_zhuma_string + "\n";
		
	}
	/**
	 * ����ע��
	 */
	public int getZhuShu(){
		int iZhuShu = 0;
		int iRedHighlights = areaNums[0].table.getHighlightBallNums();
		int iBlueHighlights = areaNums[1].table.getHighlightBallNums();
		iZhuShu = (int) getSSQZXZhuShu(iRedHighlights,iBlueHighlights,iProgressBeishu);
		return iZhuShu;		
	}
    /**
     * Ͷע����
     */
	public void touzhuNet() {
		// TODO Auto-generated method stub
		int zhuShu=getZhuShu();
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_SSQ);
		betAndGift.setAmount(""+zhuShu*200);
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
//			return context.getResources().getString(R.string.please_choose_red_number);
			int num = 6-iRedHighlights;
			return "���ٻ���"+num+"������";
		}
		// �������ﵽ���Ҫ��
		else if (iRedHighlights == 6) {
			if (iBlueHighlights < 1) {
//				return	context.getResources().getString(R.string.please_choose_blue_number);
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
//				return getResources().getString(R.string.please_choose_blue_number);
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
