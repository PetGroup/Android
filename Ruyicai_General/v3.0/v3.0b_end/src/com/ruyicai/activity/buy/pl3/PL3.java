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
	private String[] titles ={"ֱѡ","����","����","��ѡ"};
	private String[] topTitles ={"������ֱѡ","����������","����������","��������ѡ"};
	public static int iCurrentButton;
	private Class[] allId ={PL3ZhiXuan.class,PL3ZuSan.class,PL3ZuLiu.class,PL3JiXuan.class};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        init(titles, topTitles, allId);
        setIssue(Constants.LOTNO_PL3);
	}
}
