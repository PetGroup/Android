/**
 * 
 */
package com.ruyicai.activity.buy.pl3;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.buy.jixuan.DanshiJiXuan;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.PL3ZhiXuanBalls;
import com.ruyicai.jixuan.PL3Zu3Balls;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.Constants;


/**
 * ��������ѡ
 * @author Administrator
 *
 */
public class PL3JiXuan extends DanshiJiXuan implements BuyImplement,OnCheckedChangeListener{
	private LinearLayout topLinearOne;
	private LinearLayout topLinearTwo;
	private RadioGroup topButton;
	private String topTitle[]={"ֱѡ��ѡ","������ѡ"};
	public PL3ZhiXuanBalls zhixuanBalls = new  PL3ZhiXuanBalls();
	public PL3Zu3Balls zu3Balls = new  PL3Zu3Balls();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		topLinearOne = (LinearLayout) findViewById(R.id.buy_zixuan_linear_top_one);
		topLinearTwo = (LinearLayout) findViewById(R.id.buy_zixuan_linear_top_two);
		topLinearOne.setVisibility(LinearLayout.VISIBLE);
		topLinearTwo.setVisibility(LinearLayout.VISIBLE);
		initRadioGroup();
	}
	/**
	 * ��ʼ����ѡ��ť��
	 */
	public void initRadioGroup(){
		topButton = (RadioGroup) findViewById(R.id.buy_zixuan_radiogroup_top);
		for(int i=0;i<topTitle.length;i++){
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
	 * ֱѡ
	 */
	private void createZhiXuan(){
		createView(zhixuanBalls,this,false);
	}
	/**
	 * ��3
	 */
	private void createZu3(){
		createView(zu3Balls,this,false);
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
			case 0:// ֱѡ
				createZhiXuan();
				break;
			case 1:// ��3
				createZu3();
				break;
			}
		}
	}

	/**
	 * �ж��Ƿ�����Ͷע����
	 */
	public void isTouzhu() {
		// TODO Auto-generated method stub
		
	}

	 /**
	    * ���С����ʾ���
	    * @param areaNum
	    * @param iProgressBeishu
	    * @return
	    */
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Ͷע����
	 */
	public void touzhuNet() {
		// TODO Auto-generated method stub
		betAndGift.setSellway("1");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_PL3);
	}

}
