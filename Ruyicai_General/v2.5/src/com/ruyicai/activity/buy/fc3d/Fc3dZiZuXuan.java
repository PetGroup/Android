/**
 * 
 */
package com.ruyicai.activity.buy.fc3d;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.ZixuanActivity;
import com.ruyicai.code.fc3d.Fc3dZiZuXuanCode;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.AreaInfo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;

/**
 * @author Administrator
 * 
 */
public class Fc3dZiZuXuan extends ZixuanActivity implements BuyImplement,OnCheckedChangeListener {
	private LinearLayout topLinear;
	private LinearLayout topLinearOne;
	private LinearLayout topLinearTwo;
	private Button zu3Button, zu6Button;
	private RadioGroup topButton;
	private String topTitle[] = { "��ʽ", "��ʽ" };
	public static int iCurrentButton;// ��ǩ
	private int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private AreaInfo areaInfos[];// ѡ��
	private Fc3dZiZuXuanCode fc3dCode = new Fc3dZiZuXuanCode();
	BallTable oneBallTable;
	BallTable twoBallTable;
	BallTable thirdBallTable;
	public static boolean isDanFu = true;// true�ǵ�ʽ

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	/**
	 * ��ʼ�����
	 */
	public void init() {
		topLinear = (LinearLayout) findViewById(R.id.buy_zixuan_linear_top);
		topLinearOne = (LinearLayout) findViewById(R.id.buy_zixuan_linear_top_one);
		topLinearTwo = (LinearLayout) findViewById(R.id.buy_zixuan_linear_top_two);
		topLinear.setVisibility(LinearLayout.VISIBLE);
		topLinearOne.setVisibility(LinearLayout.VISIBLE);
		topLinearTwo.setVisibility(LinearLayout.VISIBLE);
		initButton();
		initRadioGroup();
		create_ZU3();// Ĭ��
	}

	public void initButton() {
		zu3Button = (Button) findViewById(R.id.buy_zixuan_button_zu3);
		zu6Button = (Button) findViewById(R.id.buy_zixuan_button_zu6);
		zu3Button.setText("����");
		zu6Button.setText("����");
		zu3Button.setBackgroundResource(R.drawable.zu_button_b);
		zu6Button.setBackgroundResource(R.drawable.zu_button);
		zu3Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zu3Button.setBackgroundResource(R.drawable.zu_button_b);
				zu6Button.setBackgroundResource(R.drawable.zu_button);
				topLinearTwo.setVisibility(LinearLayout.VISIBLE);
				create_ZU3();
			}
		});

		zu6Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zu3Button.setBackgroundResource(R.drawable.zu_button);
				zu6Button.setBackgroundResource(R.drawable.zu_button_b);
				topLinearTwo.setVisibility(LinearLayout.GONE);
				create_ZU6();
			}
		});

	}

	/**
	 * ��ʼ����ѡ��ť��
	 */
	public void initRadioGroup() {
		topButton = (RadioGroup) findViewById(R.id.buy_zixuan_radiogroup_top);
		for (int i = 0; i < topTitle.length; i++) {
			RadioButton radio = new RadioButton(this);
			radio.setText(topTitle[i]);
			radio.setTextColor(Color.BLACK);
			radio.setTextSize(13);
			radio.setId(i);
			radio.setButtonDrawable(R.drawable.radio_select);
			radio.setPadding(Constants.PADDING, 0, 10, 0);
			topButton.addView(radio);

		}
		topButton.setOnCheckedChangeListener(this);
		topButton.check(0);
	}

	/**
	 * ����
	 */
	public void create_ZU3() {
		iCurrentButton = PublicConst.BUY_FC3D_ZU3;
		topButton.check(0);
		isDanFu = true;
		create_ZU3_DAN();
	}

	/**
	 * ������ʽ
	 */
	public void create_ZU3_DAN() {
		areaInfos = new AreaInfo[3];
		String baiTitle = getResources()
				.getString(R.string.fc3d_text_bai_title).toString();
		String shiTitle = getResources()
				.getString(R.string.fc3d_text_shi_title).toString();
		String geTitle = getResources().getString(R.string.fc3d_text_ge_title)
				.toString();
		areaInfos[0] = new AreaInfo(10, 1, ballResId, 0, 0, Color.RED, baiTitle);
		areaInfos[1] = new AreaInfo(10, 1, ballResId, 0, 0, Color.RED, shiTitle);
		areaInfos[2] = new AreaInfo(10, 1, ballResId, 0, 0, Color.RED, geTitle);
		createView(areaInfos, fc3dCode, this);
		oneBallTable = areaNums[0].table;
		twoBallTable = areaNums[1].table;
		thirdBallTable = areaNums[2].table;
	}

	/**
	 * ������ʽ
	 * 
	 */
	public void create_ZU3_FU() {
		areaInfos = new AreaInfo[1];
		String title = getResources().getString(R.string.fc3d_text_hezhi_title)
				.toString();
		areaInfos[0] = new AreaInfo(10, 10, ballResId, 0, 0, Color.RED, title);
		createView(areaInfos, fc3dCode, this);
		oneBallTable = areaNums[0].table;
	}

	/**
	 * ����
	 */
	public void create_ZU6() {
		areaInfos = new AreaInfo[1];
		iCurrentButton = PublicConst.BUY_FC3D_ZU6;
		String title = getResources().getString(R.string.fc3d_text_hezhi_title)
				.toString();
		areaInfos[0] = new AreaInfo(10, 9, ballResId, 0, 0, Color.RED, title);
		createView(areaInfos, fc3dCode, this);
		oneBallTable = areaNums[0].table;
	}

	/**
	 * ��дRadioGroup��������onCheckedChanged
	 * 
	 * @param RadioGroup
	 *            RadioGroup
	 * @param int checkedId ��ǰ��ѡ���RadioId
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (group.getId()) {
		case R.id.buy_zixuan_radiogroup_top:
			switch (checkedId) {
			case 0:// ��ʽ
				isDanFu = true;
				create_ZU3_DAN();
				break;
			case 1:// ��ʽ
				isDanFu = false;
				create_ZU3_FU();
				break;
			}
		}
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
				if(iCurrentButton==PublicConst.BUY_FC3D_ZU3&&isDanFu==true){
					if(i==0||i==1){
						 areaNums[0].table.clearAllHighlights();
						 areaNums[1].table.clearAllHighlights();
						 areaNums[1].table.changeBallState(areaNums[0].info.chosenBallSum, nBallId);
						 int isHighLight = areaNums[0].table.changeBallState(areaNums[0].info.chosenBallSum, nBallId);
						 if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT&& areaNums[2].table.getOneBallStatue(nBallId) !=0) {
							areaNums[2].table.clearOnBallHighlight(nBallId);
						 }
					}else{
						areaNums[2].table.clearAllHighlights();
						int isHighLight = areaNums[2].table.changeBallState(areaNums[2].info.chosenBallSum, nBallId);
						if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT&& areaNums[0].table.getOneBallStatue(nBallId) !=0) {
							areaNums[0].table.clearOnBallHighlight(nBallId);
							areaNums[1].table.clearOnBallHighlight(nBallId);
						}
					}
				}else{
					  areaNums[i].table.changeBallState(areaNums[i].info.chosenBallSum, nBallId);
				}
				  break;
			}

	     }

	}
	/**
	 * �ж��Ƿ�����Ͷע����
	 */
	public void isTouzhu() {
		// TODO Auto-generated method stub
		// ����3D��3
		if (iCurrentButton == PublicConst.BUY_FC3D_ZU3) {

			int iZhuShu = 0;
            if (isDanFu) {
					int baiweiNums = oneBallTable.getHighlightBallNums();
					int shiweiNums = twoBallTable.getHighlightBallNums();
					int geweiNums = thirdBallTable.getHighlightBallNums();

					if (baiweiNums < 1 || shiweiNums < 1 || geweiNums < 1) {
						alertInfo("���ٰ�λ��ʮλ�� ��λ�о�ѡ��һ��С�����Ͷע");
					} else if (baiweiNums == 1 && shiweiNums == 1&& geweiNums == 1) {
						iZhuShu = mSeekBarBeishu.getProgress();
						String baiweistr = oneBallTable.getHighlightBallNOs()[0] + "";// ǰ2λ��ͬ
						String geweistr = thirdBallTable.getHighlightBallNOs()[0]+ "";// ǰ2λ��ͬ
						if (iZhuShu * 2 > 100000) {
							dialogExcessive();
						} else {
							alert( "ע����" + 1 + "ע"
									+ "\n" + "������" + iProgressBeishu + "��"
									+ "\n" + "׷�ţ�" + iProgressQishu + "��"
									+ "\n" + "��" + iZhuShu * 2 + "Ԫ"
									+ "\n" + "�����"
									+ (2 * (iProgressQishu - 1) * iZhuShu)
									+ "Ԫ","ע�룺" + baiweistr + "," + baiweistr + ","
									+ geweistr + "\n" );
						}
					}
			} else{
				if (oneBallTable.getHighlightBallNums() < 2) {
					alertInfo("������ѡ��2��С�����Ͷע");
				} else {
					// wangyl 7.12 �޸�ȷ��Ͷע�ɹ���ĶԻ���
					int[] fushiNums = oneBallTable.getHighlightBallNOs();
					String fushiStr = "";
					for (int i = 0; i < fushiNums.length; i++) {
						fushiStr += (fushiNums[i]) + ",";
						if (i == fushiNums.length - 1) {
							fushiStr = fushiStr.substring(0, fushiStr
									.length() - 1);
						}
					}
					iZhuShu = getZhuShu();

					if (iZhuShu * 2 > 100000) {
						dialogExcessive();
					} else {
						alert( "ע����"
								+ iZhuShu / iProgressBeishu + "ע" + "\n"
								+ "������" + iProgressBeishu + "��" + "\n"
								+ "׷�ţ�" + iProgressQishu + "��" + "\n"
								+ "��" + iZhuShu * 2 + "Ԫ" + "\n"
								+ "�����"
								+ (2 * (iProgressQishu - 1) * iZhuShu)
								+ "Ԫ" , "ע�룺" + fushiStr + "\n" );
					}
				}
			}
		}
		// ����3D��6
		if (iCurrentButton == PublicConst.BUY_FC3D_ZU6) {

			if (oneBallTable.getHighlightBallNums() < 3) {
				alertInfo("������ѡ��3��С�����Ͷע");
			} else {
				int[] fushiNums = oneBallTable.getHighlightBallNOs();
				String fushiStr = "";
				for (int i = 0; i < fushiNums.length; i++) {
					fushiStr += (fushiNums[i]) + ",";
					if (i == fushiNums.length - 1) {
						fushiStr = fushiStr.substring(0,
								fushiStr.length() - 1);
					}
				}
				int iZhuShu = getZhuShu();

				if (iZhuShu * 2 > 100000) {
					dialogExcessive();
				} else {
					alert(  "ע����"
							+ iZhuShu / iProgressBeishu + "ע" + "\n"
							+ "������" + iProgressBeishu + "��" + "\n" + "׷�ţ�"
							+ iProgressQishu + "��" + "\n" + "��"
							+ iZhuShu * 2 + "Ԫ" + "\n" + "�����"
							+ (2 * (iProgressQishu - 1) * iZhuShu) + "Ԫ"
							, "ע�룺" + fushiStr + "\n");
				}
			}

		}
	}

	/**
	 * ���С����ʾ���
	 * 
	 * @param areaNum
	 * @param iProgressBeishu
	 * @return
	 */
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		// TODO Auto-generated method stub
		String mTextSumMoney = "";
		// ����3D��3
		switch (iCurrentButton) {
		case PublicConst.BUY_FC3D_ZU3:
			if(isDanFu){
				if (oneBallTable.getHighlightBallNums() == 1&& twoBallTable.getHighlightBallNums() == 1&& thirdBallTable.getHighlightBallNums() == 1) {
					int iZhuShu = iProgressBeishu;
					mTextSumMoney = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
				}else if(oneBallTable.getHighlightBallNums() == 0){
					mTextSumMoney = "��λ����Ҫ1����";
				}else if(thirdBallTable.getHighlightBallNums() == 0){
					mTextSumMoney = "��λ����Ҫ1����";
				}
			}else{	
				if (oneBallTable.getHighlightBallNums() > 1) {
					int iZhuShu = getZhuShu();
					mTextSumMoney = "��" + iZhuShu + "ע����"+ (iZhuShu * 2) + "Ԫ";
				}else{
					int num = 2 - oneBallTable.getHighlightBallNums();
					mTextSumMoney = "����Ҫ"+num+"����";
				}
			}

			break;
		// ����3D��6
		case PublicConst.BUY_FC3D_ZU6:
			if (oneBallTable.getHighlightBallNums() > 2) {
				int iZhuShu = getZhuShu();
				mTextSumMoney = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
			}else{
				int num = 3 - oneBallTable.getHighlightBallNums();
				mTextSumMoney = "����Ҫ"+num+"����";
			}
			break;
		}
		return mTextSumMoney;
	}
	/**
	 * 
	 * @return
	 */
     public int getZhuShu(){
    	int iReturnValue = 0;
 		// ����3D��3
 		switch (iCurrentButton) {
 		case PublicConst.BUY_FC3D_ZU3:
 			if (isDanFu) {
				iReturnValue = 1;
			}else {// ��3 ��ʽע���Ļ�ȡ 20100912 �³�
				int iZu3Balls = oneBallTable.getHighlightBallNums();
				iReturnValue = (int) getFc3dZu3FushiZhushu(iZu3Balls);
			}
 			break;
 		// ����3D��6
 		case PublicConst.BUY_FC3D_ZU6:
			int iZu6Balls = oneBallTable.getHighlightBallNums();
			iReturnValue = (int) getFc3dZu6FushiZhushu(iZu6Balls);
 			break;
 		}
		return iReturnValue * iProgressBeishu;
    	 
     }

 	/**
 	 * ��ø���3D��6��ʽע��
 	 * 
 	 * @param iZu6balls
 	 *            ѡ��С�����
 	 * @return ����ע��
 	 */
 	private long getFc3dZu6FushiZhushu(int iZu6balls) {
 		long tempzhushu = 0l;
 		if (iZu6balls > 0) {
 			tempzhushu += PublicMethod.zuhe(3, iZu6balls);
 		}
 		return tempzhushu;

 	}

 	/**
 	 * ��ø���3D��3��ʽע��
 	 * 
 	 * @param iZu3balls
 	 *            ѡ��С�����
 	 * @return ����ע��
 	 */
 	private long getFc3dZu3FushiZhushu(int iZu3balls) {
 		long tempzhushu = 0l;
 		if (iZu3balls > 0) {
 			tempzhushu += PublicMethod.zuhe(2, iZu3balls) * 2;
 		}
 		return tempzhushu;

 	}
	/**
	 * Ͷע����
	 */
	public void touzhuNet() {
		// TODO Auto-generated method stub
		int zhuShu=getZhuShu();
		betAndGift.setSellway("0");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_FC3D);
		betAndGift.setAmount(""+zhuShu*200);
	}

}
