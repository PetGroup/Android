/**
 * 
 */
package com.ruyicai.activity.buy.qlc;

import android.os.Bundle;

import com.ruyicai.activity.buy.jixuan.DanshiJiXuan;
import com.ruyicai.constant.Constants;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.QlcBalls;
import com.ruyicai.pojo.AreaNum;

/**
 * @author Administrator
 *
 */
public class QlcJiXuan extends DanshiJiXuan implements BuyImplement{
	
	public QlcBalls qlcBalls = new QlcBalls();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createView(qlcBalls,this,true);
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
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
