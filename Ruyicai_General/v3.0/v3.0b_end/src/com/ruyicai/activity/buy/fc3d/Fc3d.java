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
    public static 	int iCurrentButton ;//��ǩ
	private String[] titles ={"ֱѡ","����","����","��ѡ"};
	private String[] topTitles ={"����3Dֱѡ","����3D����","����3D����","����3D��ѡ"};
	private Class[] allId ={FC3DZhiXuan.class,FC3DZuSan.class,FC3DZuLiu.class,Fc3dJiXuan.class};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        init(titles, topTitles, allId);
        setIssue(Constants.LOTNO_FC3D);
	}
}
