/**
 * 
 */
package com.ruyicai.code.fc3d;

import com.ruyicai.code.CodeInterface;
import com.ruyicai.pojo.AreaNum;

/**
 * @author Administrator
 * 
 */
public class Fc3dZiZhiXuanCode extends CodeInterface {
	String CityCode = "1512-";// ���б��
	String DDD_falg = "F47103-";// ���ֱ��
	String typeCode = "";// �淨
	String staticCode = "-01-";
	String endCode = "^";// ������־
	String dateCode = "0";// ����
	String zxfs = "20";// ��ѡ��ʽ


	public String zhuma(AreaNum[] areaNums, int beishu,int type) {
		String[] zhuma = null;
		String t_str = "";
		int iZhuShu = areaNums[0].table.getHighlightBallNums()* areaNums[1].table.getHighlightBallNums()* areaNums[2].table.getHighlightBallNums();
		int[] zhuma_baiwei = areaNums[0].table.getHighlightBallNOs();
		int[] zhuma_shiwei = areaNums[1].table.getHighlightBallNOs();
		int[] zhuma_gewei = areaNums[2].table.getHighlightBallNOs();
		if (zhuma_baiwei.length == 1 && zhuma_shiwei.length == 1 && zhuma_gewei.length == 1) {
			typeCode = zxfs;
			// ֱѡ��ʽע���ʽ�б仯 �³� 2010/7/11
			String[] str = { "01", "0" + (zhuma_baiwei[0]), "^", "01",
					"0" + (zhuma_shiwei[0]), "^", "01", "0" + (zhuma_gewei[0]) };
			zhuma = str;
		} else {
			// 3Dֱѡ��ʽע���淨 2010/7/4 �³�
			if (zhuma_baiwei.length != 0 && zhuma_shiwei.length != 0 && zhuma_gewei.length != 0) {
				typeCode = zxfs;
				String[] str = new String[zhuma_baiwei.length + zhuma_shiwei.length + zhuma_gewei.length + 5];
				if (zhuma_baiwei.length < 10) {
					str[0] = "0" + zhuma_baiwei.length;
				} else {
					str[0] = zhuma_baiwei.length + "";
				}
				for (int i = 0; i < zhuma_baiwei.length; i++) {
					str[i + 1] = "0" + (zhuma_baiwei[i]);
				}
				str[zhuma_baiwei.length + 1] = "^";

				if (zhuma_shiwei.length < 10) {
					str[zhuma_baiwei.length + 2] = "0" + zhuma_shiwei.length;
				} else {
					str[zhuma_baiwei.length + 2] = zhuma_shiwei.length + "";
				}
				for (int i = 0; i < zhuma_shiwei.length; i++) {
					str[zhuma_baiwei.length + 3 + i] = "0" + (zhuma_shiwei[i]);
				}
				str[zhuma_baiwei.length + zhuma_shiwei.length + 3] = "^";
				if (zhuma_gewei.length < 10) {
					str[zhuma_baiwei.length + zhuma_shiwei.length + 4] = "0"
							+ zhuma_gewei.length;
				} else {
					str[zhuma_baiwei.length + zhuma_shiwei.length + 4] = zhuma_gewei.length
							+ "";
				}
				for (int i = 0; i < zhuma_gewei.length; i++) {
					str[zhuma_baiwei.length + zhuma_shiwei.length + 5 + i] = "0"
							+ (zhuma_gewei[i]);
				}
				zhuma = str;
			}

		}
		String zhushu = "";
		if (iZhuShu < 10) {
			zhushu += "0" + iZhuShu;
		} else if (iZhuShu >= 10) {
			zhushu += "" + iZhuShu;
		}

		String beishu_ = "";
		if (beishu < 10) {
			beishu_ += "0" + beishu;
		} else if (beishu >= 10) {
			beishu_ += "" + beishu;
		}
//		t_str += CityCode + DDD_falg + typeCode + "-" + zhushu + "-" + typeCode + beishu_;
		t_str += typeCode + beishu_;
		for (int i = 0; i < zhuma.length; i++) {
			t_str += zhuma[i];
		}
		t_str += endCode;

		return t_str;

	}


	

}
