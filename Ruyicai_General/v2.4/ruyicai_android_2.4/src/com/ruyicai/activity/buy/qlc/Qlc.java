/**
 * 
 */
package com.ruyicai.activity.buy.qlc;

import android.os.Bundle;

import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.util.Constants;

/**
 * ���ֲ�
 * @author Administrator
 *
 */
public class Qlc extends BuyActivityGroup{
	
	private String[] titles ={"ֱѡ","����","��ѡ"};
	private String[] topTitles ={"���ֲ�ֱѡ","���ֲʵ���","���ֲʻ�ѡ"};
	private Class[] allId ={QlcZiZhiXuan.class,QlcZiDanTuo.class,QlcJiXuan.class};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        init(titles, topTitles, allId);
        setIssue(Constants.LOTNO_QLC);
	}
}
