package com.ruyicai.activity.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.util.Constants;

/**
 * Ϊ������Ϣ�ṩ����
 * @author haojie
 *
 */
public class NoticeDataProvider {
	
	public final static String LOTTERYTYPE = "LOTTERYTYPE";
	public final static String WINNINGNUM = "WINNINGNUM";
	public final static String DATE = "DATA";
	public final static String ISSUE = "ISSUE";
	public final static String FINALDATE = "FINALDATE";
	public final static String MONEYSUM = "MONEYSUM";
	
	
	/**
	 * ���б�����Ӧ������
	 */
	protected static List<Map<String, Object>> getListForMainListViewSimpleAdapter() {
		// �¼ӻ�ȡʱʱ����Ϣ
		String iGameName[] = {"ssq", "fc3d", "qlc",  "pl3",
				"cjdlt", "ssc", "11-5", "sfc", "rxj", "lcb", "jqc" }; // 8.9
		// ��ӣ�����������������͸
		
		
		JSONObject mainssq = Constants.ssqNoticeList.get(0);
		JSONObject mainfc3d = Constants.fc3dList.get(0);
		JSONObject mainqlc = Constants.qlcList.get(0);
		JSONObject mainpl3 = Constants.pl3List.get(0);
		JSONObject maindlt = Constants.dltList.get(0);
		JSONObject mainssc = Constants.sscList.get(0);
		JSONObject maindlc = Constants.dlcList.get(0);
		JSONObject mainsfc = Constants.sfcList.get(0);
		JSONObject mainrx9 = Constants.rx9List.get(0);
		JSONObject main6cb = Constants.half6List.get(0);
		JSONObject mainjqc = Constants.jqcList.get(0);
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();


		try {
			// zlm 7.16 �����޸ģ���ӿ�������
			map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, iGameName[0]);
			map.put(WINNINGNUM, mainssq.get("winno"));
			map.put(DATE, mainssq.getString("date"));
			map.put(ISSUE, "��" + mainssq.get("lotno") + "��");
			list.add(map);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			// zlm 7.16 �����޸ģ���ӿ�������
			map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, iGameName[1]);
			map.put(WINNINGNUM, mainfc3d.getString("winno"));
			map.put(DATE, mainfc3d.getString("date"));
			map.put(ISSUE, "��" + mainfc3d.get("lotno") + "��");
			list.add(map);
		} catch (JSONException e) {

		}

		try {
			// zlm 7.16 �����޸ģ���ӿ�������
			map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, iGameName[2]);
			map.put(WINNINGNUM, mainqlc.getString("winno"));
			map.put(DATE, mainqlc.getString("date"));
			map.put(ISSUE, "��" + mainqlc.get("lotno") + "��");
			list.add(map);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			// 8.9 ��ӣ�������
			map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, iGameName[3]);
			map.put(WINNINGNUM, mainpl3.get("winno"));
			map.put(DATE, mainpl3.get("date"));
			map.put(ISSUE, "��" + mainpl3.get("lotno") + "��");
			list.add(map);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		try {
			// 8.9 ��ӣ���������͸
			map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, iGameName[4]);
			map.put(WINNINGNUM, maindlt.getString("winno"));
			map.put(DATE, maindlt.getString("date"));
			map.put(ISSUE, "��" + maindlt.get("lotno") + "��");
			list.add(map);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			// ��ӣ�ʱʱ��
			map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, iGameName[5]);
			map.put(WINNINGNUM, mainssc.getString("winno"));
			map.put(DATE,mainssc.getString("date"));
			map.put(ISSUE, "��" + mainssc.get("lotno") + "��");
			list.add(map);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			// ���: 11-5
			map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, iGameName[6]);
			map.put(WINNINGNUM, maindlc.getString("winno"));
			map.put(DATE,maindlc.getString("date"));
			map.put(ISSUE, "��" + maindlc.get("lotno") + "��");
			list.add(map);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		try {
			// ��ӣ�ʤ����
			map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, iGameName[7]);
			map.put(WINNINGNUM, mainsfc.getString("winno"));
			map.put(DATE, mainsfc.getString("date"));
			map.put(ISSUE, "��" + mainsfc.get("lotno") + "��");
			list.add(map);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			// ��ӣ���ѡ�Ų�
			map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, iGameName[8]);
			map.put(WINNINGNUM, mainrx9.getString("winno"));
			map.put(DATE, mainrx9.getString("date"));
			map.put(ISSUE, "��" + mainrx9.get("lotno")+ "��");
			list.add(map);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			// ��ӣ�������
			map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, iGameName[9]);
			map.put(WINNINGNUM, main6cb.getString("winno"));
			map.put(DATE, main6cb.getString("date"));
			map.put(ISSUE, "��" + main6cb.get("lotno") + "��");
			list.add(map);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			// ��ӣ������
			map = new HashMap<String, Object>();
			map.put(LOTTERYTYPE, iGameName[10]);
			map.put(WINNINGNUM, mainjqc.getString("winno"));
			map.put(DATE,mainjqc.getString("date"));
			map.put(ISSUE, "��" + mainjqc.get("lotno") + "��");
			list.add(map);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	/**
	 * ���б�����Ӧ������
	 */
	protected static List<Map<String, Object>> getSubInfoForListView(String iGameName) {
		
		if(iGameName == null||iGameName.equals("")){
			return null;
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(6);
		
		
		List<JSONObject> _list = null;
		if(iGameName.equals("ssq")){
			_list = Constants.ssqNoticeList;
		}else if(iGameName.equals("cjdlt")){
			_list = Constants.dltList;
		}else if(iGameName.equals("fc3d")){
			_list = Constants.fc3dList;
		}else if(iGameName.equals("pl3")){
			_list = Constants.pl3List;
		}else if(iGameName.equals("qlc")){
			_list = Constants.qlcList;
		}else if(iGameName.equals("ssc")){
			_list = Constants.sscList;
		}else if(iGameName.equals("sfc")){
			_list = Constants.sfcList;
		}else if(iGameName.equals("rxj")){
			_list = Constants.rx9List;
		}else if(iGameName.equals("lcb")){
			_list = Constants.half6List;
		}else if(iGameName.equals("jqc")){
			_list = Constants.jqcList;
		}else if(iGameName.equals("11-5")){
			_list = Constants.dlcList;
		}
		
		try {
			//��list �е�����ת����map��ʽ
			for (int i = 0; i < _list.size(); i++) {
				//int ��1��ʼ��Ϊ��ȥ����һ������
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject object = _list.get(i);
				map.put(LOTTERYTYPE, iGameName);
				map.put(WINNINGNUM, object.getString("winno"));
				map.put(DATE, "�������ڣ� " + object.getString("date"));
				map.put(ISSUE, "��" + object.getString("lotno") + "��");
				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}
}
