package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;
/**
 * ���Ͳ�Ʊ��Ͷע��Ʊ�ǵ��õĽӿ�
 * @author miao
 */
public class BetAndGiftInterface {

	private static String COMMAND = "betLot";
	private static BetAndGiftInterface instance = null;
	private BetAndGiftInterface() {		
	}
	public static synchronized BetAndGiftInterface getInstance(){
		if(instance == null){
			instance = new BetAndGiftInterface();
		}
		return instance;
	}
	
	
	/**
	 * ���ʺ����Ͳ�Ʊ�ķ���
	 * @param userno      �û����  �û�ע��ɹ����ص��û����
	 * @param batchcode   Ͷע�ں�  �û�Ͷע�ĵ�ǰ�ڣ����Կգ�
	 * @param batchnum    ׷������ Ĭ��Ϊ1����׷�ţ�
	 * @param bet_code    ע��
	 * @param lotno       ���ֱ��  Ͷע���֣��磺˫ɫ��ΪF47104
	 * @param lotmulti    ����   Ͷע�ı���
	 * @param sellway     ���۷�ʽ (���Կ�)
	 * @param amount      ��� ��λΪ�֣��ܽ�
	 * @param bettype     Ͷע���� �������ֹ��ʻ�������  ͶעΪbet,����Ϊgift  
	 * @param sessionid   ���������ص�sessionid
	 * @param to_mobile_code  �û����͵��ֻ���(���Կ�)
	 * @param advice      �û����Ͳ�Ʊ�ǵ�������Կգ�
	 * @param infoway     ͨ����ѯͶע(���Կ�)
	 * @param command 
	 * @return   ���ط�ʽ{error_code��message}��Ϣ
	 *            error_code = 000000,message = Ͷע�ɹ�;
	 *            error_code = 070002,message = δ��¼;
	 *            error_code = 040006,message = ����;
	 *            error_code = 999999,message = ����ʧ��;
	 */
	public  String betOrGift(BetAndGiftPojo betPojo){
//		Log.v("betandgift","betandgift");
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, betPojo.getUserno());
			jsonProtocol.put(ProtocolManager.BATCHCODE, betPojo.getBatchcode());
			jsonProtocol.put(ProtocolManager.BATCHNUM,betPojo.getBatchnum());
			jsonProtocol.put(ProtocolManager.BETCODE, betPojo.getBet_code());
			jsonProtocol.put(ProtocolManager.LOTNO, betPojo.getLotno());
			jsonProtocol.put(ProtocolManager.LOTMULTI, betPojo.getLotmulti());
			jsonProtocol.put(ProtocolManager.SELLWAY, betPojo.getSellway());
			jsonProtocol.put(ProtocolManager.AMOUNT	, betPojo.getAmount());
			jsonProtocol.put(ProtocolManager.BETTYPE, betPojo.getBettype());
			jsonProtocol.put(ProtocolManager.SESSIONID, betPojo.getSessionid());
			jsonProtocol.put(ProtocolManager.TOMOBILECODE, betPojo.getTo_mobile_code());
			jsonProtocol.put(ProtocolManager.ADVICE, betPojo.getAdvice());	
			jsonProtocol.put(ProtocolManager.PHONE_NUM, betPojo.getPhonenum());	
			jsonProtocol.put(ProtocolManager.INFOWAY, betPojo.getInfoway());	
			jsonProtocol.put(ProtocolManager.ISSUPER, betPojo.getIssuper());	
//			Log.e("jsonProtocol-======", jsonProtocol.toString());
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
}
