package com.ruyicai.code.jc.zq;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.ruyicai.activity.buy.jc.JcMainView.Info;
import com.ruyicai.code.jc.JcType;
import com.ruyicai.util.PublicMethod;

public class FootZJQ {
	JcType jcType;

	public FootZJQ(Context context) {
		jcType = new JcType(context);
	}

	/**
	 * 
	 * ��ȡע��
	 * 
	 */
	public String getCode(String key, List<Info> listInfo) {
		String code = jcType.getValues(key) + "@";
		for (int i = 0; i < listInfo.size(); i++) {
			Info info = (Info) listInfo.get(i);
			if (info.onclikNum > 0) {
				code += info.getDay() + "|" + info.getWeek() + "|"
						+ info.getTeamId() + "|";
				for (int j = 0; j < info.check.length; j++) {
					if (info.check[j].isChecked()) {
						code += j;
					}
				}
				code += "^";
			}

		}
		return code;
	}

	/**
	 * ��ȡ����List
	 */

	public List<double[]> getOddsList(List<Info> listInfo) {
		List<double[]> oddsList = new ArrayList<double[]>();/* ����ѡ���������б� */
		for (int i = 0; i < listInfo.size(); i++) {
			Info info = (Info) listInfo.get(i);
			if (info.onclikNum > 0) {

				try {
					double[] aa = new double[info.check.length];
					for (int j = 0; j < info.check.length; j++) {
						if (info.check[j].isChecked()) {
							aa[j] = Double.parseDouble((info.getVStrs())[j]);
							Log.v("aa["+j+"]",""+aa[j]);
						}
					}
					Log.v("aa.length",""+aa.length);
					double[] result = PublicMethod.getDoubleArrayNoZero(aa);
					Log.e("result[0]",""+result[0]);
					oddsList.add(result);

				} catch (NumberFormatException e) {
					double[] result = {1};
					oddsList.add(result);
				}
			}
		}
		return oddsList;
	}
}
