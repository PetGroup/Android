/**
 * 
 */
package com.ruyicai.activity.buy.fc3d;

import android.os.Bundle;

import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.util.Constants;

/**
 * ����3d
 * @author Administrator
 *
 */
public class Fc3d extends BuyActivityGroup{
	private String[] titles ={"ֱѡ","��ѡ","��ֵ","��ѡ"};
	private String[] topTitles ={"����3Dֱѡ","����3D��ѡ","����3D��ֵ","����3D��ѡ"};
	private Class[] allId ={Fc3dZiZhiXuan.class,Fc3dZiZuXuan.class,Fc3dZiHeZhi.class,Fc3dJiXuan.class};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        init(titles, topTitles, allId);
        setIssue(Constants.LOTNO_FC3D);
	}
}
