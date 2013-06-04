package com.ruyicai.net.transaction;

import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.URLEncoder;

public class GetCashInterface {
	/**
	 * ��������ӿ�
	 * @param mobileCode  �ֻ���
	 * @param sessionid
	 * @param withdrawel_money  ���ֽ��
	 * @param bank_name         ���б�ʶ
	 * @param ID_number         ���п���
	 * @return
	 */
	public static String getMoneyQuest(String name, String sessionid,
			String withdrawel_money, String bank_name, String ID_number,
			String areaName) {
		String action = "cashaction.do";
		String method = "cash";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("NAME", name);
			paras.put("withdrawel_money", withdrawel_money);
			paras.put("bank_name", bank_name);
			paras.put("ID_number", ID_number);
			paras.put("PROVNAME", "");
			paras.put("AREANAME", areaName);
			paras.put("channel", Constants.COOP_ID);
			
			para = URLEncoder.encode(paras.toString(), "UTF-8");
			String re = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);

			if (re != null && re.length() > 0) {
				reValue = re;
			}
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}
		return reValue;
	}

	/**
	 * �����޸Ľӿ�
	 * @param name�˻���
	 * @param sessionid
	 * @param bankAccount�˻�
	 * @param bankName����������
	 * @param provName������������ʡ
	 * @param areaName��������������
	 * @param state����״̬
	 * @param userId
	 * @return
	 */
	public static String changeQuest(String name, String sessionid,
			String bankAccount, String bankName, int state, String mobileCode,
			String areaName, String amt) {
		String action = "WithdrawTeviewAction.do";
		String method = "editCashDetail";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("name", name);
			paras.put("bankAccount", bankAccount);
			paras.put("bankName", bankName);
			paras.put("provName", "");
			paras.put("areaName", areaName);
			paras.put("state", state);
			paras.put("mobile_code", mobileCode);
			paras.put("subBankName", "");
			paras.put("amt", amt);
			paras.put("channel", Constants.COOP_ID);
			para = URLEncoder.encode(paras.toString());
			String re = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);

			if (re != null && re.length() > 0) {
				reValue = re;
			}
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}

		return reValue;
	}

	/**
	 * ����ȡ���ӿ�
	 * @param mobile_code
	 * @param sessionid
	 * @return
	 */
	public static String cancelQuest(String mobileCode, String sessionid) {
		String action = "WithdrawTeviewAction.do";
		String method = "cancelTcashDetail";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("mobile_code", mobileCode);
			para = URLEncoder.encode(paras.toString());
			String re = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);

			if (re != null && re.length() > 0) {
				reValue = re;
			}
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}

		return reValue;
	}

	/**
	 * ��ѯ����״̬�ӿ�
	 * @param mobile_code�ֻ���
	 * @param sessionid
	 * @return
	 */
	public static String stateQuset(String mobileCode, String sessionid) {
		String action = "WithdrawTeviewAction.do";
		String method = "getCashDetailState";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("mobile_code", mobileCode);
			para = URLEncoder.encode(paras.toString());
			String re = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);
			if (re != null && re.length() > 0) {
				reValue = re;
			}
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}

		return reValue;
	}
}
