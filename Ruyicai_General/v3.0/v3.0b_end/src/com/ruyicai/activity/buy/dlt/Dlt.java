package com.ruyicai.activity.buy.dlt;

import android.os.Bundle;

import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.util.Constants;



public class Dlt extends BuyActivityGroup{
	private String[] titles ={"��ѡ","����","��ѡ","12ѡ2"};
	private String[] topTitles ={"����͸��ѡ","����͸����","����͸��ѡ","����͸12ѡ2"};
	private Class[] allId ={DltNormalSelect.class,DltDantuoSelect.class,DltRandomChose.class,DltTwoInDozenSelect.class};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        init(titles, topTitles, allId);
        setIssue(Constants.LOTNO_DLT);
	}
}
