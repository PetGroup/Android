package com.ruyicai.json.miss;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class JiLinK3MissJson extends MissJson{

	private final String MISS = "miss";
	private final String DAN="dan";
	private final String SHUANG="shuang";

	public List<List> jsonToList(String type, JSONObject json) {
		try {
			// 和值，二不同
			if (type.equals(MissConstant.JLK3_HEZHI)
					|| type.equals(MissConstant.JLK3_THREE_TWO)
					|| type.equals(MissConstant.JLK3_TWOSAME_FU)) {
				missList.add(getJsonArray(json.getJSONArray(MISS)));
			}
			// 二同号单选
			else if (type.equals(MissConstant.JLK3_TWO_DAN)) {
				missList.add(getJsonArray(json.getJSONArray(DAN)));
				missList.add(getJsonArray(json.getJSONArray(SHUANG)));
			}
			// 三不同，三同号
			else if (type.equals(MissConstant.JLK3_THREE_TWO + ";"
					+ MissConstant.JLK3_THREE_LINK_TONG)
					|| type.equals(MissConstant.JLK3_THREE_DAN_FU + ";"
							+ MissConstant.JLK3_THREESAME_TONG)) {
				String[] types = type.split(";");
				missList.add(getJsonArray(json.getJSONObject(types[0])
						.getJSONArray(MISS)));
				missList.add(getJsonArray(json.getJSONObject(types[1])
						.getJSONArray(MISS)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return missList;
	}

}
