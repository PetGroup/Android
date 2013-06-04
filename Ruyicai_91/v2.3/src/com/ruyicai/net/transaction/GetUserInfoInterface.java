package com.ruyicai.net.transaction;

import java.util.Random;

import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.URLEncoder;

public class GetUserInfoInterface {

	/**
	 * �ͻ�������ѯ
	 * @param mobile_code
	 * @param sessionID
	 * @return
	 */
	public static String findUserInfo(String mobile_code, String sessionid) {
		String action = "user.do";
		String method = "findUserBalance";
		String reValue = "";
		try {
			Random rdm = new Random();
			int transctionId = rdm.nextInt();
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("inputCharset", 2);
			paras.put("version", "v2.0");
			paras.put("language", 1);
			paras.put("transctionid", transctionId);
			paras.put("mobile_code", mobile_code);
			para = URLEncoder.encode(paras.toString());
			
			reValue = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL+action+";jsessionid="+sessionid+"?method="+method+"&jsonString="+para);
			
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}
		return reValue;
	}
	
	/**
	 * �ͻ�������н���ѯ����
	 * @param mobile_code
	 * @param bet_code  Ͷע��
	 * @param amount    ���ʽ��
	 * @param sessionid
	 * @return
	 */
	public static String winingSelectTC(String mobileCode, String startLine,
			String stopLine, String sessionid) {
		String action = "TCallSelect.do";
		String method = "zhongjiangSelect";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("mobileCode", mobileCode);
			para = URLEncoder.encode(paras.toString());
			String where = "";
			JSONObject jwhere = new JSONObject();
			jwhere.put("lotNo", "");
			jwhere.put("batchCode", "");
			jwhere.put("startDate", "");
			jwhere.put("stopDate", "");
			jwhere.put("startLine", startLine);
			jwhere.put("stopLine", stopLine);
			where = URLEncoder.encode(jwhere.toString());
			String re = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para + "&jsonWhere=" + where);
			if (re != null && re.length() > 0) {
				reValue = re;

			}
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}

		return reValue;
	}

	/**
	 * �ͻ��˹��ʲ�ѯ����(����������������͸)
	 * @param mobile_code
	 * @param startDate
	 * @param endDate
	 * @param sessionID
	 * @return
	 */
	public static String bettingSelectTC(String mobileCode, String lotNo,
			String startLine, String stopLine, String sessionid) {// String
	
		String action = "TCallSelect.do";
		String method = "touzhuSelect";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("mobileCode", mobileCode);
			paras.put("lotNo", lotNo);
			para = URLEncoder.encode(paras.toString());

			String where = "";
			JSONObject jwhere = new JSONObject();
			jwhere.put("lotNo", "");
			jwhere.put("batchCode", "");
			jwhere.put("startDate", "");
			jwhere.put("stopDate", "");
			jwhere.put("startLine", startLine);
			jwhere.put("stopLine", stopLine);
			where = URLEncoder.encode(jwhere.toString());
	
			reValue = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para + "&jsonWhere=" + where);
			
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}

		return reValue;
	}

	

	/**
	 * �ͻ���׷���ײͲ�ѯ
	 * @param mobile_number
	 * @param sessionID
	 * @return
	 */
	public static String addNumQueryTC(String sessionid, String mobileCode,
			String startLine, String stopLine) {// String mobile_number,
		String action = "TCallSelect.do";
		String method = "trackingNumberSelect";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("mobileCode", mobileCode);
			para = URLEncoder.encode(paras.toString());

			String where = "";
			JSONObject jwhere = new JSONObject();
			jwhere.put("lotNo", "");
			jwhere.put("batchCode", "");
			jwhere.put("startDate", "");
			jwhere.put("stopDate", "");
			jwhere.put("startLine", startLine);
			jwhere.put("stopLine", stopLine);
			where = URLEncoder.encode(jwhere.toString());
			String re = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para + "&jsonWhere=" + where);
			if (re != null && re.length() > 0) {
				reValue = re;

			}
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}

		return reValue;
	}
}
