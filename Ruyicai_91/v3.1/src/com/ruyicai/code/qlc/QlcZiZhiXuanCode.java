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
public class QlcZiZhiXuanCode extends CodeInterface {

	@Override
	public String zhuma(AreaNum[] areaNums, int beishu, int type) {
		int zhuma[] = areaNums[0].table.getHighlightBallNOs();
		int  ballNums = areaNums[0].table.getHighlightBallNums();
		long sendzhushu = getQLCFSZhuShu(ballNums);
		String t_str = "";
		if(ballNums==7){//��ʽ
			t_str += "00";
			t_str += "01";
		}else {
			t_str += "10";
			t_str += "01";
			t_str += "*";
		}
		for (int i = 0; i < zhuma.length; i++) {
			if (zhuma[i] >= 10) {
				t_str += zhuma[i];
			} else if (zhuma[i] < 10) {
				t_str += "0" + zhuma[i];
			}
		}
		t_str += "^";
		return t_str;
	}
	/*
	 * ��ʽ�淨ע�����㷽��
	 * 
	 * @param int aRedBalls �������
	 * 
	 * @param int aBlueBalls �������
	 * 
	 * @return long ע��
	 */
	private long getQLCFSZhuShu(int aRedBalls) {
		long qlcZhuShu = 0L;
		if (aRedBalls > 0) {
			qlcZhuShu += (PublicMethod.zuhe(7, aRedBalls));
		}
		return qlcZhuShu;	
	}

	

}
