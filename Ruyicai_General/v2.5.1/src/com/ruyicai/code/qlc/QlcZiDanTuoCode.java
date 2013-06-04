/**
 * 
 */
package com.ruyicai.code.qlc;

import com.ruyicai.code.CodeInterface;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.PublicMethod;

/**
 * @author Administrator
 *
 */
public class QlcZiDanTuoCode extends CodeInterface{



	public String zhuma(AreaNum[] areaNums, int beishu, int type) {		
		int[] zhuma = areaNums[0].table.getHighlightBallNOs();
		int[] zhumablue = areaNums[1].table.getHighlightBallNOs();
		int danBallNums = areaNums[0].table.getHighlightBallNums();
		int tuoBallNums = areaNums[1].table.getHighlightBallNums();
		long sendzhushu = getQLCDTZhuShu(danBallNums, tuoBallNums);
//		String t_str = "1512-F47102-";
		String t_str = "";
//		t_str += "20-";
//		if (sendzhushu < 10) {
//			t_str += "0" + sendzhushu;
//		}
//		if (sendzhushu >= 10) {
//			t_str += sendzhushu;
//		}
//		t_str += "-20";
		t_str += "20";
		if (beishu < 10) {
			t_str += "0" + beishu;
		}
		if (beishu >= 10) {
			t_str += beishu;
		}

		for (int i = 0; i < zhuma.length; i++) {
			if (zhuma[i] >= 10) {
				t_str += zhuma[i];
			} else if (zhuma[i] < 10) {
				t_str += "0" + zhuma[i];
			}
		}
		t_str += "*";
		for (int i = 0; i < zhumablue.length; i++) {
			if (zhumablue[i] >= 10) {
				t_str += zhumablue[i];
			} else if (zhumablue[i] < 10) {
				t_str += "0" + zhumablue[i];
			}
		}
		t_str += "^";
		return t_str;

	}
	/*
	 * �����淨ע�����㷽��
	 * 
	 * @param int aRedBalls �������
	 * 
	 * @param int aRedTuoBalls �����������
	 * 
	 * @param int aBlueBalls �������
	 * 
	 * @return long ע��
	 */
	private long getQLCDTZhuShu(int aRedBalls, int aRedTuoBalls) {// �õ�˫ɫ���ϵ�ע��
		long qlcZhuShu = 0L;
		if (aRedBalls > 0 && aRedTuoBalls > 0) {
			qlcZhuShu += (PublicMethod.zuhe(7 - aRedBalls, aRedTuoBalls));
		}
		return qlcZhuShu;
	}


	

}
