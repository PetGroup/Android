package com.ruyicai.activity.buy.qxc;

import android.os.Bundle;

import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.util.Constants;

public class QXC extends BuyActivityGroup{
	private String[] titles ={"ֱѡ","��ѡ"};
	private String[] topTitles ={"���ǲ�ֱѡ","���ǲʻ�ѡ"};
	private Class[] allId ={QXCZhiXuan.class,QXCJX.class};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        init(titles, topTitles, allId);
        setIssue(Constants.LOTNO_QXC);
	}
}
