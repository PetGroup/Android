	package com.ruyicai.net.transaction;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.URLEncoder;

public class BettingInterface {
	
	String error_code = "00";
	String amount;// ���ʽ��
	String term_begin_datetime;// ��������
	String deposit_sum;// �˻����
	String sell_term_code;// ��Ʊ����
	String error_code_DYJ = "00";

	/**
	 * Ͷע�½ӿ�
	 * @param bet_code     ע��
	 * @param count        ����
	 * @param amount       Ͷע�ܽ��
	 * @param sessionid    ��¼��
	 */
	private static BettingInterface bettingInterface = null;
	private final static String TAG = "BettingInterface";
	
	private BettingInterface() {
		
	}
	public synchronized static BettingInterface getInstance(){
		if(bettingInterface == null){
			bettingInterface = new BettingInterface();
		}
		return bettingInterface;
	}
	
	
	/**
	 * ����lotserver����Ͷע����
	 * �˷�������lotserver������,���յ��õ����µ�lotserver
	 * ֧������Ͷע��ʽ
	 * @return
	 */
	public String bettingLotServer(){
		return null;
	}
	
    /**
     * �ύ��������ע
     */
	public String bettingNew(String bet_code, String count, String amount,String sessionid) {
				Log.e(TAG, "bet_code="+bet_code);
		Log.e(TAG, "count="+count);
		Log.e(TAG, "amount="+amount);
		Log.e(TAG, "sessionid="+sessionid);
		
		JSONObject obj = null;
		try {
			String re = addNumber(bet_code, count, amount, "Y",	sessionid);
			obj = new JSONObject(re);
			error_code = obj.getString("error_code");
		} catch (JSONException e) {

		}
		return error_code;
	}

	// ���Ͷע�ӿ� 20100712 �³�
	public String[] BettingTC(String mobileCode, String lotNo, String betCode,
			String lotMulti, String amount, String oneMoney, String batchnum,
			String sessionid) {

		JSONObject obj = null;
		String error_code = "00";
		String error_code_DYJ = "00";
		try {
			String re = bettingTC(mobileCode, lotNo, betCode,
					lotMulti, amount + "00", oneMoney, batchnum, sessionid);
			obj = new JSONObject(re);
			error_code = obj.getString("error_code");
			error_code_DYJ = obj.getString("error_code_DYJ");
		} catch (JSONException e) {
			if (error_code.equals("00") && obj != null) {
				try {
					error_code_DYJ = obj.getString("error_code_DYJ");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		String[] errorCode = { error_code, error_code_DYJ };
		return errorCode;

	}

	// ���Ͷע�ӿ� 20100712 �³�
	public String[] BettingTC(String mobileCode, String lotNo, String betCode,
			String lotMulti, String amount, String oneMoney, String batchnum,
			String sessionid, String batchCode) {

		JSONObject obj = null;
		String error_code = "00";
		String error_code_DYJ = "00";
		try {
			String re = bettingTC(mobileCode, lotNo, betCode,
					lotMulti, amount + "00", oneMoney, batchnum, sessionid,
					batchCode);
			obj = new JSONObject(re);
			error_code = obj.getString("error_code");
			error_code_DYJ = obj.getString("error_code_DYJ");
		} catch (JSONException e) {
			if (error_code.equals("00") && obj != null) {
				try {
					error_code_DYJ = obj.getString("error_code_DYJ");
				} catch (JSONException e1) {
					
				}
			}

		}
		String[] errorCode = { error_code, error_code_DYJ };
		return errorCode;

	}
	
	// ���Ͷע�ӿ� �³� 8.12
	/**
	 * �ͻ���ʱʱ��Ͷע����
	 * @param mobile_code
	 * @param bet_code          Ͷע��
	 * @param amount            ���ʽ��
	 * @param sessionid
	 * @return
	 */
	public static String bettingTC(String mobileCode, String lotNo,
			String betCode, String lotMulti, String amount, String oneMoney,
			String batchnum, String sessionid, String batchcode) {
		String action = "lotTCBet.do";
		String method = "addZh";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("mobileCode", mobileCode);
			// paras.put("sessionid", sessionid);
			paras.put("lotNo", lotNo);
			paras.put("betCode", betCode);
			paras.put("lotMulti", lotMulti);
			paras.put("amount", amount);
			paras.put("oneMoney", oneMoney);
			paras.put("batchnum", batchnum);
			paras.put("batchcode", batchcode);// �ں�
			paras.put("channel", Constants.COOP_ID);
			
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
	 * �ͻ���Ͷע׷������
	 * @param bet_code           Ͷע��
	 * @param count              ׷������
	 * @param amount             Ͷע���
	 * @param type               �����Ƿ�Ͷע Y��ʾ���ھ�Ͷע����Ͷע+׷�š�N��ʾ���ڲ�Ͷע����ִֻ��׷�Ŷ��ơ�
	 * @param sessionid
	 * @return
	 */
	public static String addNumber(String bet_code, String count, String amount, String type, String sessionid) {
		String action = "addNumAttemper.do";
		String method = "addNumber";
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
			paras.put("bet_code", bet_code);
			paras.put("amount", amount);
			paras.put("Type", type);
			paras.put("count", count);
			paras.put("channel", Constants.COOP_ID);
			
			para = URLEncoder.encode(paras.toString());
			reValue = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action
					+ ";jsessionid=" + sessionid + "?method=" + method
					+ "&jsonString=" + para);
		} catch (Exception e) {
			Log.e(TAG, "BettingInterfaceError");
		}

		return reValue;
	}
	
	// ���Ͷע�ӿ� �³� 8.12
	/**
	 * �ͻ������Ͷע����
	 * @param mobile_code
	 * @param bet_code    Ͷע��
	 * @param amount      ���ʽ��
	 * @param sessionid
	 * @return
	 */
	public static String bettingTC(String mobileCode, String lotNo,
			String betCode, String lotMulti, String amount, String oneMoney,
			String batchnum, String sessionid) {
		String action = "lotTCBet.do";
		String method = "addZh";
		String reValue = "";
		try {
			String para = "";
			JSONObject paras = new JSONObject();
			paras.put("mobileCode", mobileCode);
			paras.put("lotNo", lotNo);
			paras.put("betCode", betCode);
			paras.put("lotMulti", lotMulti);
			paras.put("amount", amount);
			paras.put("oneMoney", oneMoney);
			paras.put("batchnum", batchnum);

			paras.put("channel", Constants.COOP_ID);

			para = URLEncoder.encode(paras.toString());
			
			reValue = InternetUtils.GetMethodOpenHttpConnect(Constants.SERVER_URL + action+ ";jsessionid=" + sessionid + "?method=" + method+ "&jsonString=" + para);

		} catch (Exception e) {
			PublicMethod.myOutput(e.getMessage());
		}

		return reValue;
	}
}
