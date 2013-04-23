package com.ruyicai.activity.buy.zc;

import android.os.Bundle;
import android.widget.TabHost;

import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.util.Constants;

public class FootballLottery extends BuyActivityGroup{

	private String[] titles ={"ʤ����","��ѡ��","�����","������"};
	private String[] topTitles ={"���-ʤ����","���-��ѡ��","���-�����","���-������"};
	private String[] batch = {Constants.LOTNO_SFC,Constants.LOTNO_RX9,Constants.LOTNO_JQC,Constants.LOTNO_LCB};
	private Class[] allId ={FootballSFLottery.class,FootballChooseNineLottery.class,FootballGoalsLottery.class,FootballSixSemiFinal.class};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        init(titles, topTitles, allId);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			public void onTabChanged(String tabId) {	
				for(int i=0;i<titles.length;i++){
					if(tabId.equals(titles[i])){
						title.setText(topTitles[i]);
						setIssue(batch[i]);
					}
				}
				           
			}
		});
        setIssue(batch[0]);
	}
	

}
