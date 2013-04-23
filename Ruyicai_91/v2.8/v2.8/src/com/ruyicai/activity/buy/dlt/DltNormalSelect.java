package com.ruyicai.activity.buy.dlt;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.buy.ZixuanActivity;
import com.ruyicai.code.dlt.DltNormalSelectCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

public class DltNormalSelect extends ZixuanActivity implements BuyImplement{
	
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };
	private int blueBallResId[] = { R.drawable.grey, R.drawable.blue };
	private  ToggleButton  zhuijiatouzhu;
	private int singleLotteryValue = 2;
	
	AreaInfo[] dltNormalAreaInfos = new AreaInfo[2];
	/**
	 * ����͸ֱѡ��������������
	 */
	BallTable redBallTable,blueBallTable;
	/**
	 * ʵ��������͸ֱѡע����
	 */
	DltNormalSelectCode dltNormalcode = new DltNormalSelectCode();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout toggleLinear = (LinearLayout)findViewById(R.id.buy_zixuan_linear_toggle);
		toggleLinear.setVisibility(LinearLayout.VISIBLE);
		zhuijiatouzhu = (ToggleButton)findViewById(R.id.dlt_zhuijia);
		zhuijiatouzhu.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				if(isChecked){
					singleLotteryValue = 3;
					betAndGift.setIssuper("0");
				}else{
					singleLotteryValue = 2;
					betAndGift.setIssuper("");
				}
				changeTextSumMoney();
			}
		});
		initDltNormalArea();
		createView(dltNormalAreaInfos, dltNormalcode,this);
		redBallTable = areaNums[0].table;
		blueBallTable = areaNums[1].table;
		
	}
	/**
	 * ��ʼ������͸ֱѡѡȡ
	 */
	public void initDltNormalArea(){
		String redTitle = getResources().getString(R.string.ssq_zhixuan_text_red_title).toString();
        String blueTitle = getResources().getString(R.string.ssq_zhixuan_text_blue_title).toString();
        dltNormalAreaInfos[0] = new AreaInfo(35, 22, redBallResId, 0, 1,Color.RED,redTitle);
        dltNormalAreaInfos[1] = new AreaInfo(12, 12, blueBallResId, 0, 1,Color.BLUE,blueTitle);
	}
	/**
     * �ж��Ƿ�����Ͷע����    
     */
	public void isTouzhu() {
		int iZhuShu = getZhuShu();
		if (redBallTable.getHighlightBallNums() < 5 && blueBallTable.getHighlightBallNums() < 2) {
			alertInfo("������ѡ��5�������2������	");
		} else if (redBallTable.getHighlightBallNums() < 5) {
			alertInfo("��ѡ������5������");
		} else if (blueBallTable.getHighlightBallNums() < 2) {
			alertInfo("��ѡ��2������");
		} else if (iZhuShu * 2 > 100000) {
			dialogExcessive();
		} else {
			String sTouzhuAlert = "";
			sTouzhuAlert = getTouzhuAlert();
			alert(sTouzhuAlert,getZhuma());
		}
	}
	/**
	 * ����ע��
	 */
	public int getZhuShu(){
		int iZhuShu = 0;
		int iRedHighlights = areaNums[0].table.getHighlightBallNums();
		int iBlueHighlights = areaNums[1].table.getHighlightBallNums();
		iZhuShu = (int) getDltNormalZhuShu(iRedHighlights,iBlueHighlights,iProgressBeishu);
		return iZhuShu;		
	}
	/**
	 * ��ʽ�淨ע�����㷽�� 
	 * @param int aRedBalls �������
	 * @param int aBlueBalls �������
	 * @param int iProgressBeishu ������ʾ��
	 * @return long ע��
	 */
	private long getDltNormalZhuShu(int aRedBalls, int aBlueBalls,int iProgressBeishu) {
		long dltZhuShu = 0L;
		if (aRedBalls > 0 && aBlueBalls > 0) {
			dltZhuShu += (PublicMethod.zuhe(5, aRedBalls)* PublicMethod.zuhe(2, aBlueBalls) * iProgressBeishu);
		}
		return dltZhuShu;
	}
	/**
	 * Ͷע��ʾ���е���Ϣ
	 */
	private String getTouzhuAlert() {
		int iZhuShu = getZhuShu();

		StringBuffer alertcontent = new StringBuffer();
		alertcontent.append("ע����").append(iZhuShu / mSeekBarBeishu.getProgress()).append("ע").append("\n")
			.append("������").append(mSeekBarBeishu.getProgress()).append("��").append("\n")
			.append("׷�ţ�").append(mSeekBarQishu.getProgress()).append("��").append("\n")
			.append("��").append((iZhuShu * singleLotteryValue)).append("Ԫ").append("\n")
			.append("�����").append((singleLotteryValue * (mSeekBarQishu.getProgress() - 1) * iZhuShu)).append("Ԫ").append("\n");
		
		return  alertcontent.toString();

	}
	/**
	 * Ͷע��ʾ���е���Ϣ(ע��)
	 * @return
	 */
	private String getZhuma(){
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
	 * ���С������ʾ��Ϣ
	 */
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		int iRedHighlights = areaNum[0].table.getHighlightBallNums();
		int iBlueHighlights = areaNum[1].table.getHighlightBallNums();
		String iTempString = "";
		int iZhuShu = 0;
		// ������ ����
		if (iRedHighlights < 5) {
			int num = 5-iRedHighlights;
			return "���ٻ���"+num+"������";
		}else if (iRedHighlights >= 5) {
			if (iBlueHighlights < 2) {
				int bluenum = 2-iBlueHighlights;
				return "���ٻ���"+bluenum+"������";
			} else {
				iZhuShu = (int) getDltNormalZhuShu(iRedHighlights,iBlueHighlights,iProgressBeishu);
				iTempString = "��" + iZhuShu + "ע����"+ (iZhuShu *singleLotteryValue) + "Ԫ";
			} 
		}
		return iTempString;
	}
	/**
	 * Ͷע����
	 */
	public void touzhuNet() {
		int zhuShu=getZhuShu();
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_DLT);
		betAndGift.setAmount(""+zhuShu*singleLotteryValue*100);
		betAndGift.setAmt(singleLotteryValue);
	}

}
