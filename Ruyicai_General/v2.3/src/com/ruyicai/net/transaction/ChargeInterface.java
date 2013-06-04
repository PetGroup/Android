package com.ruyicai.net.transaction;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.GT;
import com.ruyicai.util.URLEncoder;

public class ChargeInterface {
	
	private static ChargeInterface chargeInterface = null;
	
	private ChargeInterface() {
		
	}
	
	public synchronized static ChargeInterface getInstance(){
		if(chargeInterface == null){
			chargeInterface = new ChargeInterface();
		}
		return chargeInterface;
	}
	
	
	private static final String TAG = "ChargeInterface";
	
	protected static String[] bankcharge = null;

	String error_code = "00";
	String re;
	public String url;
	JSONObject obj = null;

	/**
	 * ��ת���˻���ֵ
	 * @param accesstype    ���뷽ʽ B��ʾweb��W��ʾwap��C��ʾ�ͻ���
	 * @param mobile_code   ��¼���ֻ�����
	 * @param cardType      ֧����ʽ
	 * @param transaction_money   ��ֵ��� (��Ϊ��λ)
	 * @param bankId              ���б�ʶ
	 * @param expand              ��չ����
	 * @param sessionid
	 * @return
	 */
	public String phonebankcharge(String accesstype, String mobile_code,
			String cardType, String transaction_money, String bankId,
			String expand, String sessionid) {

		re = wapAttemperRequest(accesstype, mobile_code, cardType,
				transaction_money + "00", bankId, expand, sessionid);
		try {
			Log.d(TAG, re);
			obj = new JSONObject(re);
			error_code = obj.getString("error_code");
			if (error_code.equals("000000")) {
				url = obj.getString("requrl");
			}

		} catch (JSONException e) {
			
		}
		return error_code;
	}

	/**
	 * ����ת���˻���ֵ
	 * 
	 * @param accesstype        ���뷽ʽ B��ʾweb��W��ʾwap��C��ʾ�ͻ���
	 * @param mobile_code       ��¼���ֻ�����
	 * @param cardType          ֧����ʽ
	 * @param transaction_money ��ֵ��� (��Ϊ��λ)
	 * @param totalAmount       ����ֵ
	 * @param card_no           ����
	 * @param card_pwd          ������
	 * @param bankId            ���б�ʶ
	 * @param expand            ��չ����
	 * @param sessionid
	 * @return
	 */
	public String phonecardcharge(String accesstype, String mobile_code,
		String cardType, String transaction_money, String totalAmount,
		String card_no, String card_pwd, String bankId, String expand,
		String sessionid) {
		  Log.e("yibang","=====re========");
		re = attemperRequest(accesstype, mobile_code, cardType,transaction_money +"00", totalAmount +"00", card_no,
				card_pwd, bankId, expand, sessionid);
		try {
			obj = new JSONObject(re);
			error_code = obj.getString("error_code");

		} catch (JSONException e) {

		}
		return error_code;
	}
	
	
	/**
	 * ��ת���˻���ֵ
	 * @param accesstype          ���뷽ʽ B��ʾweb��W��ʾwap��C��ʾ�ͻ���
	 * @param mobile_code         ��¼���ֻ�����
	 * @param cardType            ֧����ʽ
	 * @param transaction_money   ��ֵ��� (��Ϊ��λ)
	 * @param bankId              ���б�ʶ
	 * @param expand              ��չ����
	 * @param sessionid
	 * @return
	 */
	public static String wapAttemperRequest(String accesstype,
			String mobile_code, String cardType, String transaction_money,
			String bankId, String expand, String sessionid) {
		String reValue = "";
		String action = "attemper.do";
		String method = "attemperRequest";
		String para = "";
		try {
			Random rdm = new Random();
			int transctionId = rdm.nextInt();
			JSONObject paras = new JSONObject();
			paras.put("inputCharset", 2);
			paras.put("version", "v2.0");
			paras.put("language", 1);
			paras.put("transctionid", transctionId);

			paras.put("accesstype", accesstype);
			paras.put("mobile_code", mobile_code);
			paras.put("cardType", cardType);
			paras.put("transaction_money", transaction_money);
			paras.put("bankId", bankId);
			paras.put("expand", expand);
			paras.put("sessionId", sessionid);
			paras.put("channel", Constants.COOP_ID);
			
			// paras.put("card_no", "");
			para = URLEncoder.encode(paras.toString());

			String re = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);

			if (re != null && re.length() > 0) {
				reValue = re;
			}
		} catch (Exception e) {

		}

		return reValue;
	}
	
	/**
	 * ����ת���˻���ֵ
	 * @param accesstype         ���뷽ʽ B��ʾweb��W��ʾwap��C��ʾ�ͻ���
	 * @param mobile_code        ��¼���ֻ�����
	 * @param cardType           ֧����ʽ
	 * @param transaction_money  ��ֵ��� (��Ϊ��λ)
	 * @param totalAmount        ����ֵ
	 * @param card_no            ����
	 * @param card_pwd           ������
	 * @param bankId             ���б�ʶ
	 * @param expand             ��չ����
	 * @param sessionid
	 * @return
	 */
	public static String attemperRequest(String accesstype, String mobile_code,
			String cardType, String transaction_money, String totalAmount,
			String card_no, String card_pwd, String bankId, String expand,
			String sessionid) {
		String reValue = "";
		String action = "attemper.do";
		String method = "attemperRequest";
		String para = "";
		try {
			Random rdm = new Random();
			int transctionId = rdm.nextInt();
			JSONObject paras = new JSONObject();
			paras.put("inputCharset", 2);
			paras.put("version", "v2.0");
			paras.put("language", 1);
			paras.put("transctionid", transctionId);

			paras.put("accesstype", accesstype);
			paras.put("mobile_code", mobile_code);
			paras.put("cardType", cardType);
			paras.put("transaction_money", transaction_money);
			paras.put("totalAmount", totalAmount);
			paras.put("card_no", card_no);
			paras.put("card_pwd", card_pwd);
			paras.put("bankId", bankId);
			paras.put("expand", expand);
			paras.put("channel", Constants.COOP_ID);
			
            Log.e("para=================", paras.toString());
			para = URLEncoder.encode(paras.toString());
            
			String temp = Constants.SERVER_URL + action + ";jsessionid=" + sessionid
					+ "?method=" + method + "&jsonString=" + para;

			temp = GT.encodingString(temp, "GBK", "UTF-8");
			String re = InternetUtils.GetMethodOpenHttpConnect(temp);
			if (re != null && re.length() > 0) {
				reValue = re;
			}
		} catch (Exception e) {

		}

		return reValue;
	}
}
