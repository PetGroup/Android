package com.ruyicai.net.transaction;

import java.util.Random;

import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.URLEncoder;

public class PackageInterface {
	/**
	 * �ͻ����ײ�����//�Ѳ���
	 * @param mobile_number
	 * @param sessionID
	 * @param lottry_type �ײ���� (����)
	 * @param amount      ÿ���ײͽ�� �Է�Ϊ��λ
	 * @param agency_code ������
	 * @return
	 */
	public static String packageDeal(String lottery_type, String amount,
			String sessionid) {
		// String action = "newPackage.do";
		String action = "packDealAttemper.do";
		String method = "packageDeal";
		String reValue = "";
		try {
			Random rdm = new Random();
			int transctionId = rdm.nextInt();
			String para = "";
			JSONObject paras = new JSONObject();

			// ͨ��׼��
			paras.put("inputCharset", 2);
			paras.put("version", "v2.0");
			paras.put("language", 1);
			paras.put("transctionid", transctionId);

			// paras.put("mobile_code", mobile_number);
			paras.put("lottery_type", lottery_type);
			paras.put("amount", amount);
			paras.put("agency_code", "");
			para = URLEncoder.encode(paras.toString());

			reValue = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);

		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}
		return reValue;
	}

	/**
	 * �ͻ����ײͲ�ѯ����//�Ѳ���
	 * @param mobile_number
	 * @param sessionID
	 * @return
	 */
	public static String packageSelect(String sessionid) {
		String action = "packDealQuery.do";
		String method = "packageSelect";
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
			para = URLEncoder.encode(paras.toString());

			reValue = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}

		return reValue;
	}

	/**
	 * �ͻ����ײ��޸�����//�Ѳ���
	 * @param mobile_number
	 * @param lottry_type   ��Ʊ����
	 * @param amount        �ײͽ��
	 * @param sessionid
	 * @return
	 */
	public static String packageUpdate(String tsubscribeId, String amount,
			String sessionid) {
		String action = "packDealUpdate.do";
		String method = "packageUpdate";
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
			paras.put("tsubscribeId", tsubscribeId);
			paras.put("amount", amount);
			para = URLEncoder.encode(paras.toString());

			reValue = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}
		return reValue;
	}

	/**
	 * �ͻ����ײͶ�������//�Ѳ���
	 * @param mobile_number
	 * @param sessionid
	 * @return
	 */
	public static String packageFreeze(String sessionid, String tsubscribeId) {
		String action = "packDealStop.do";
		String method = "packageFreeze";
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
			paras.put("tsubscribeId", tsubscribeId);
			para = URLEncoder.encode(paras.toString());

			reValue = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}
		return reValue;
	}
	
	/**
	 * ����ײ�����//�Ѳ���
	 * @param mobile_number
	 * @param sessionID
	 * @param lottry_type         �ײ���� (����)
	 * @param amount              ÿ���ײͽ�� �Է�Ϊ��λ
	 * @param agency_code         ������
	 * @return
	 */
	public static String packageDealTC(String mobileCode, String lotNo,
			String amount, String sessionid) {
		String action = "lotTCBet.do";
		String method = "addTc";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();

			paras.put("mobileCode", mobileCode);
			paras.put("lotNo", lotNo);
			paras.put("amount", amount);
			para = URLEncoder.encode(paras.toString());

			reValue = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);
		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}

		return reValue;
	}
}
