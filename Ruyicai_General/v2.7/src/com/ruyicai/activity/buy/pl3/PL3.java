/**
 * 
 */
package com.ruyicai.activity.buy.pl3;

import android.os.Bundle;

import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.util.Constants;

/**
 * ������
 * @author Administrator
 *
 */
public class PL3 extends BuyActivityGroup{
	private String[] titles ={"ֱѡ","��ѡ","��ֵ","��ѡ"};
	private String[] topTitles ={"������ֱѡ","��������ѡ","��������ֵ","��������ѡ"};
	private Class[] allId ={PL3ZiZhiXuan.class,PL3ZiZuXuan.class,PL3ZiHeZhi.class,PL3JiXuan.class};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        init(titles, topTitles, allId);
        setIssue(Constants.LOTNO_PL3);
	}
}
