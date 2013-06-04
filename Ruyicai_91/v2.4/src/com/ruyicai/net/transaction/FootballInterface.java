package com.ruyicai.net.transaction;

import org.json.JSONObject;


import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.URLEncoder;

public class FootballInterface {
	
	private static FootballInterface instance;
	private FootballInterface() {
		
	}
	
	public static synchronized FootballInterface getInstance(){
		if(instance == null){
			instance = new FootballInterface();
		}
		return instance;
	}
	
	/**
	 *  �����ʶ���
	 * @param lotno ���ֱ��
	 * @param batchCode ��ǰ��
	 * @return
	 */
	public static String getZCData(String lotno, String batchCode) {
		String action = "zcAction.do";
		String method = "getFlData";
	//	Log.v("��ȡ��������","3333333333333333333333");
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("lotno", lotno);
			paras.put("batchCode", batchCode);
		//	Log.v("�����ϴ�����",""+para);
			para = URLEncoder.encode(paras.toString());
			
			String re = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + Constants.sessionId + "?method=" + method
					+ "&jsonString=" + para);
		
		//	Log.v("���ز���",re+"444444444444444444444444444444444");
			if (re != null && re.length() > 0) {
				reValue = re;
			}
		} catch (Exception e) {
		}

		return reValue;
	}
	
	/**
	 * ��ȡ��ʵķ�����Ϣ
	 * @param lotno ����
	 * @param batchCode  �ں�
	 * @param num  ��Ӧ���е�num
	 * @return
	 */
	public String getZCInfo(String lotno, String batchCode, String num) {
		String action = "zcAction.do";
		String method = "getFlDataInfo";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("lotno", lotno);
			paras.put("batchCode", batchCode);
			paras.put("num", num);
			para = URLEncoder.encode(paras.toString());
			String re = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + Constants.sessionId + "?method=" + method
					+ "&jsonString=" + para);
			if (re != null && re.length() > 0) {
				reValue = re;
			}
		} catch (Exception e) {
		}

		return reValue;
	}
}
