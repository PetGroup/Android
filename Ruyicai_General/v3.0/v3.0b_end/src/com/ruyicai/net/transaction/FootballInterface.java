package com.ruyicai.net.transaction;

import org.json.JSONObject;

import android.util.Log;

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
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("lotno", lotno);
			paras.put("batchCode", batchCode);
			para = URLEncoder.encode(paras.toString());
			
			String re = InternetUtils.GetMethodOpenHttpConnectJrt(Constants.SERVER_URL + action
					+ ";jsessionid=" + Constants.sessionId + "?method=" + method
					+ "&jsonString=" + para);
		
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
			String re = InternetUtils.GetMethodOpenHttpConnectJrt(Constants.SERVER_URL + action
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
