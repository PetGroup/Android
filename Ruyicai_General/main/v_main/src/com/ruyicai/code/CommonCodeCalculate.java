package com.ruyicai.code;

import java.util.List;

import com.ruyicai.pojo.AreaNum;

public class CommonCodeCalculate extends CodeInterface{

	public static String getCode(List<Integer> firstListOne,
			List<Integer> secondListOne, List<Integer> thirdListOne,
			int oneSelectItem, String[] lotnoCode) {
		String str = "";
		int[] temp1 = null;
		if (firstListOne.size() > 0) {
			temp1 = new int[firstListOne.size()];
			for (int i = 0; i < firstListOne.size(); i++) {
				temp1[i] = firstListOne.get(i);
			}
		}
		int[] temp2 = null;
		if (secondListOne.size() > 0) {
			temp2 = new int[secondListOne.size()];
			for (int i = 0; i < secondListOne.size(); i++) {
				temp2[i] = secondListOne.get(i);
			}
		}
		int[] temp3 = null;
		if (thirdListOne.size() > 0) {
			temp3 = new int[thirdListOne.size()];
			for (int i = 0; i < thirdListOne.size(); i++) {
				temp3[i] = thirdListOne.get(i);
			}
		}
		if (oneSelectItem == 0) {
			int[] R = temp1;
			if (R.length > 1) {// 复试
				str += "101@*";
				str += getRstring(R) + "^";
			} else {
				str += "101@*";
				str += getRstring(R) + "^";
			}

		} else if (oneSelectItem == 1) {
			int[] q2w = temp1;
			int[] q2q = temp2;
			if (q2w.length > 1 || q2q.length > 1) {// 复试
				str += "142@";
				str += getRstring(q2w) + "*" + getRstring(q2q) + "^";
			} else {
				str += "141@";
				str += getRstring(q2w) + getRstring(q2q) + "^";
			}

		} else if (oneSelectItem == 2) {
			int[] q3w = null;
			int[] q3q = null;
			int[] q3b = null;
			if (isZHmiss()) {
				int[] R = toInt(getIsZHcode().split("\\,"));
				q3w = new int[1];
				q3w[0] = R[0];
				q3q = new int[1];
				q3q[0] = R[1];
				q3b = new int[1];
				q3b[0] = R[2];
			} else {
				q3w = temp1;
				q3q = temp2;
				q3b = temp3;
			}
			if (q3w.length > 1 || q3q.length > 1 || q3b.length > 1) {// 复试
				str += "162@";
				str += getRstring(q3w) + "*" + getRstring(q3q) + "*"
						+ getRstring(q3b) + "^";
			} else {
				str += "161@";
				str += getRstring(q3w) + getRstring(q3q) + getRstring(q3b)
						+ "^";
			}
		} else if (oneSelectItem == 3) {
			int[] R = temp1;
			if (R.length > 2) {// 复试
				str += "108@*";
				str += getRstring(R) + "^";
			} else {
				str += "131@";
				str += getRstring(R) + "^";
			}
		} else if (oneSelectItem == 4) {
			int[] R = temp1;
			if (R.length > 3) {// 复试
				str += "109@*";
				str += getRstring(R) + "^";
			} else {
				str += "151@";
				str += getRstring(R) + "^";
			}
		}
		return str;
	}
	
	public static String getRstring(int[] num) {
		String str = "";
		for (int i = 0; i < num.length; i++) {
			if (num[i] < 10) {
				str += "0" + num[i];
			} else {
				str += num[i];
			}
		}
		return str;
	}

	@Override
	public String zhuma(AreaNum[] areaNums, int beishu, int type) {
		return "";
	}
}
