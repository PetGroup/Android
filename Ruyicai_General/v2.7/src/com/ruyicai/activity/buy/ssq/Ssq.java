/**
 * 
 */
package com.ruyicai.activity.buy.ssq;
import android.os.Bundle;

import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.util.Constants;

public class Ssq extends BuyActivityGroup {
	
	private String[] titles ={"ֱѡ","����","��ѡ"};
	private String[] topTitles ={"˫ɫ��ֱѡ","˫ɫ����","˫ɫ���ѡ"};
	private Class[] allId ={SsqZiZhiXuan.class,SsqZiDanTuo.class,SsqJiXuan.class};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        init(titles, topTitles, allId);
        setIssue(Constants.LOTNO_SSQ);
	}
}
