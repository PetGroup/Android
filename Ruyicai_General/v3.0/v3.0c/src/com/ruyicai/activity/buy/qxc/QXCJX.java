package com.ruyicai.activity.buy.qxc;

import android.os.Bundle;

import com.ruyicai.activity.buy.jixuan.DanshiJiXuan;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.QXCJXBalls;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.Constants;

public class QXCJX extends DanshiJiXuan implements BuyImplement{

	public QXCJXBalls  jixuanBalls = new  QXCJXBalls();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createZhiXuan();
	}
	/**
	 * ֱѡ
	 */
	private void createZhiXuan(){
		createView(jixuanBalls,this,false);
	}
	@Override
	public void isTouzhu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String textSumMoney(AreaNum[] areaNum, int iProgressBeishu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void touzhuNet() {
		// TODO Auto-generated method stub
		betAndGift.setSellway("1");//1�����ѡ 0������ѡ
		betAndGift.setLotno(Constants.LOTNO_QXC);
	}

}
