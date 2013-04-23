package com.ruyicai.activity.buy.dlt;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.buy.zixuan.BuyViewItem;
import com.ruyicai.activity.buy.zixuan.ZixuanActivity;
import com.ruyicai.code.dlt.DltTwoInDozenCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

public class DltTwoInDozenSelect extends ZixuanActivity {
	
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
		setCode(dltTwoInDozencode);
		setIsTen(false);
		initViewItem();
		redBallTable = itemViewArray.get(0).areaNums[0].table;
		
	}
	/**
	 * ��ʼ��ѡ������
	 */
	public void initViewItem() {
		// TODO Auto-generated method stub
		   BuyViewItem buyView = new BuyViewItem(this, initDltNormalArea());
		   itemViewArray.add(buyView);
		   View view = buyView.createView();
		   layoutView.addView(view);
	}
	/**
	 * ��ʼ������͸12ѡ2ѡ��
	 */
	public AreaNum[] initDltNormalArea(){
		AreaNum areaNums[] = new AreaNum[1]; 
		String redTitle = getResources().getString(R.string.zi_xuanzedezhuma).toString();
		areaNums[0] = new AreaNum(12,8, 12, redBallResId, 0, 1,Color.RED,redTitle);
        return areaNums;
	}
	@Override
	public String isTouzhu() {
		// TODO Auto-generated method stub
		String isTouzhu = "";
		int iRedHighlights = redBallTable.getHighlightBallNums();
		int iZhuShu = (int) getDltTwoInDozenZhuShu(iRedHighlights,1);
		if (redBallTable.getHighlightBallNums()  < 2) {
			isTouzhu = "������ѡ��2��С��	";
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
	@Override
	public int getZhuShu() {
		// TODO Auto-generated method stub
		int iRedHighlights = itemViewArray.get(0).areaNums[0].table.getHighlightBallNums();
		int iZhuShu = (int) getDltTwoInDozenZhuShu(iRedHighlights,iProgressBeishu);
		return  iZhuShu;
	}

}
