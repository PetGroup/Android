package com.ruyicai.code.Gdeleven;

import com.ruyicai.code.CodeInterface;
import com.ruyicai.pojo.AreaNum;

public class GdelevenDanTuoCode extends CodeInterface {

	@Override
	public String zhuma(AreaNum[] areaNums, int beishu, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String zhuma(AreaNum[] areaNums, String type) {
		String str = "";
		if (type.equals("DT_R2")) {
			int[] dan = areaNums[0].table.getHighlightBallNOs();
			int[] tuo = areaNums[1].table.getHighlightBallNOs();
			str += "D|R2|";
			str += getRstring(dan) + "-";
			str += getRstring(tuo);

		} else if (type.equals("DT_R3")) {
			int[] dan = areaNums[0].table.getHighlightBallNOs();
			int[] tuo = areaNums[1].table.getHighlightBallNOs();
			str += "D|R3|";
			str += getRstring(dan) + "-";
			str += getRstring(tuo);
		} else if (type.equals("DT_R4")) {
			int[] dan = areaNums[0].table.getHighlightBallNOs();
			int[] tuo = areaNums[1].table.getHighlightBallNOs();
			str += "D|R4|";
			str += getRstring(dan) + "-";
			str += getRstring(tuo);
		} else if (type.equals("DT_R5")) {
			int[] dan = areaNums[0].table.getHighlightBallNOs();
			int[] tuo = areaNums[1].table.getHighlightBallNOs();
			str += "D|R5|";
			str += getRstring(dan) + "-";
			str += getRstring(tuo);
		} else if (type.equals("DT_R6")) {
			int[] dan = areaNums[0].table.getHighlightBallNOs();
			int[] tuo = areaNums[1].table.getHighlightBallNOs();
			str += "D|R6|";
			str += getRstring(dan) + "-";
			str += getRstring(tuo);
		} else if (type.equals("DT_R7")) {
			int[] dan = areaNums[0].table.getHighlightBallNOs();
			int[] tuo = areaNums[1].table.getHighlightBallNOs();
			str += "D|R7|";
			str += getRstring(dan) + "-";
			str += getRstring(tuo);
		} else if (type.equals("DT_ZU2")) {
			int[] dan = areaNums[0].table.getHighlightBallNOs();
			int[] tuo = areaNums[1].table.getHighlightBallNOs();
			str += "D|Z2|";
			str += getRstring(dan) + "-";
			str += getRstring(tuo);
		} else if (type.equals("DT_ZU3")) {
			int[] dan = areaNums[0].table.getHighlightBallNOs();
			int[] tuo = areaNums[1].table.getHighlightBallNOs();
			str += "D|Z3|";
			str += getRstring(dan) + "-";
			str += getRstring(tuo);
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

}
