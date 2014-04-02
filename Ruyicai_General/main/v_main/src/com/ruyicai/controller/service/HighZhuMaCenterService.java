package com.ruyicai.controller.service;

import com.google.inject.Singleton;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.PublicMethod;

@Singleton
public class HighZhuMaCenterService {
	
	/**
	 * 求a取b的组合数 
	 * C(b,a) = A(b,a) / A(b,b)
	 * @param a
	 * @param b
	 * @return
	 */
	public int zuHe(int a, int b) {
		int up = 1;
		for (int up_i = 0; up_i < b; up_i++) {
			up = up * a;
			a--;
		}
		int down = jieCheng(b);
		return up / down;
	}

	/**
	 * 求b的阶乘
	 * @param b
	 * @return
	 */
	public int jieCheng(int b) {
		int result = 0;
		if (b == 1 || b == 0) {
			result = b;
		} else {
			result = b * jieCheng(b - 1);
		}
		return result;
	}
	
	/**
	 * 获取注码格式必备字段
	 * @return
	 */
	public String getMutiplePart() {
		return "0001";
	}
	
	/**
	 * 获取选择的小球个数
	 * @param areaNum
	 * @return
	 */
	public int getHighlightBallNums(AreaNum areaNum) {
		if (areaNum != null && areaNum.table != null) {
			return areaNum.table.getHighlightBallNums();
		}
		return 0;
	}
	
	/**
	 * 选择胆码部分的注码格式
	 * @param areaNum
	 * @return
	 */
	public String getDanNumbersPart(AreaNum areaNum) {
		StringBuffer numbersPart = new StringBuffer();
		int[] areaNumbers = areaNum.table.getHighlightBallNOs();
		numbersPart.append("*");
		for (int number_i = 0; number_i < areaNumbers.length; number_i++) {
			String numberString = PublicMethod.getZhuMa(areaNumbers[number_i]);
			numbersPart.append(numberString);
		}
		return numbersPart.toString();
	}
}
