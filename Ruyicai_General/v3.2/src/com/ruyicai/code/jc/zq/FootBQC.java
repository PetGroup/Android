package com.ruyicai.code.jc.zq;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.ruyicai.activity.buy.jc.JcMainView.Info;
import com.ruyicai.code.jc.JcType;
import com.ruyicai.util.PublicMethod;

public class FootBQC {
	private static final String BQC_00 = "00";// ����
	private static final String BQC_01 = "01";// ��ƽ
	private static final String BQC_03 = "03";// ��ʤ
	private static final String BQC_10 = "10";// ƽ��
	private static final String BQC_11 = "11";// ƽƽ
	private static final String BQC_13 = "13";// ƽʤ
	private static final String BQC_30 = "30";// ʤ��
	private static final String BQC_31 = "31";// ʤƽ
	private static final String BQC_33 = "33";// ʤʤ
	public static String bqcType[] = { BQC_33, BQC_31, BQC_30, BQC_13, BQC_11,
			BQC_10, BQC_03, BQC_01, BQC_00 };
	public static String titleStrs[] = { "ʤʤ", "ʤƽ", "ʤ��", "ƽʤ", "ƽƽ", "ƽ��",
			"��ʤ", "��ƽ", "����" };
	JcType jcType;

	public FootBQC(Context context) {
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
						code += bqcType[j];
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
							aa[j] = Double.parseDouble(info.getVStrs()[j]);
						}
					}
					double[] insertdouble = PublicMethod .getDoubleArrayNoZero(aa);
					oddsList.add(insertdouble);

				} catch (NumberFormatException e) {
					double[] result = { 1 };
					oddsList.add(result);
				}
			}
		}
		return oddsList;
	}
}
