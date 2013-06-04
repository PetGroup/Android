package com.ruyicai.activity.buy.dlt;

import android.graphics.Color;
import android.os.Bundle;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanActivity;
import com.ruyicai.code.dlt.DltTwoInDozenCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

public class DltTwoInDozenSelect extends ZixuanActivity implements BuyImplement{
	
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };
	
	AreaInfo[] dltTwoInDozenAreaInfos = new AreaInfo[1];
	/**
	 * ����͸���к�����
	 */
	BallTable redBallTable;
	/**
	 * ʵ��������͸ֱѡע����
	 */
	DltTwoInDozenCode dltTwoInDozencode = new DltTwoInDozenCode();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initDltNormalArea();
		createView(dltTwoInDozenAreaInfos, dltTwoInDozencode,this,true);
		redBallTable = areaNums[0].table;
		
	}
	/**
	 * ��ʼ������͸12ѡ2ѡ��
	 */
	public void initDltNormalArea(){
		String redTitle = getResources().getString(R.string.zi_xuanzedezhuma).toString();
        dltTwoInDozenAreaInfos[0] = new AreaInfo(12, 12, redBallResId, 0, 1,Color.RED,redTitle);
        
	}
	@Override
	public void isTouzhu() {
		// TODO Auto-generated method stub
		int iRedHighlights = redBallTable.getHighlightBallNums();
		int iZhuShu = (int) getDltTwoInDozenZhuShu(iRedHighlights,1);
		if (redBallTable.getHighlightBallNums()  < 2) {
			alertInfo("������ѡ��2��С��	");
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
			red_zhuma_string += PublicMethod.getZhuMa(redZhuMa[i]);
			if (i != redBallTable.getHighlightBallNOs().length - 1) {
				red_zhuma_string += ",";
			}
		}
		return "ע�룺\n" + red_zhuma_string ;
		
	}
	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		int iRedHighlights = areaNum[0].table.getHighlightBallNums();
		StringBuffer iTempString = new StringBuffer();
		int iZhuShu = 0;
		// ������ ����
		if (iRedHighlights < 2) {
			int num = 2-iRedHighlights;
			return "���ٻ���"+num+"������";
		} else {
				iZhuShu = (int) getDltTwoInDozenZhuShu(iRedHighlights,iProgressBeishu);
				iTempString.append("��").append(iZhuShu).append("ע����").append(iZhuShu * 2).append("Ԫ");	
		} 
		
		return iTempString.toString();
	}
	/**
	 * ����͸ʮ��ѡ�� �ļ��㷽��
	 * @param iRedHighlights
	 * @param iProgressBeishu
	 * @return
	 */
	private long getDltTwoInDozenZhuShu(int iRedHighlights, int iProgressBeishu) {
		long dltZhuShu = 0L;
		dltZhuShu += (PublicMethod.zuhe(2, iRedHighlights) * iProgressBeishu);
		return  dltZhuShu;
	}
	@Override
	public void touzhuNet() {
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_DLT);
	}

}
