package com.ruyicai.json.miss.dlc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.constant.MissConstant;

/**
 * ���ֲʺ�ʮһ�˶����©ֵ����
 * @author Administrator
 *
 */
public class DlcMissJson {
	private final String GE = "ge";
	private final String SHI = "shi";
	private final String BAI = "bai";
	private final String MISS = "miss";
	public  List<List> missList5X = null;
	public DlcMissJson() {
	}
	public  List<List>  jsonToList(String sellWay,JSONObject json){
		if(sellWay.equals(MissConstant.DLC_MV_Q3)||sellWay.equals(MissConstant.ELV_MV_Q3)){
			missList5X = jsonQx(json);
			return missList5X;
		}else{
			missList5X = jsonRx(json);
			return missList5X;
		}
	}

	/**
	 * �������ֲ�5��ͨѡ
	 */
	public List<List>  jsonQx(JSONObject json) {
		List<List> missList = new ArrayList<List>();
		try {
			missList.add(getJsonArray(json.getJSONArray(BAI)));
			missList.add(getJsonArray(json.getJSONArray(SHI)));
			missList.add(getJsonArray(json.getJSONArray(GE)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return missList;
	}
	/**
	 * �������ֲ�
	 */
	public List<List>  jsonRx(JSONObject json) {
		List<List> missList = new ArrayList<List>();
		try {
			missList.add(getJsonArray(json.getJSONArray(MISS)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return missList;
	}

	private List<String> getJsonArray(JSONArray jsonArray) throws JSONException {
		List<String> missValues = new ArrayList<String>();
		for(int i=0;i<jsonArray.length();i++){
			missValues.add(jsonArray.getString(i));
		}
		return missValues;
	}
}
