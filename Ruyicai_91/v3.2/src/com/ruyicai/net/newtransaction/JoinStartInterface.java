/**
 * 
 */
package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.ProtocolManager;

/**
 * @author Administrator
 *
 */
public class JoinStartInterface {
	private static String COMMAND = "betLot";
	private static JoinStartInterface instance = null;
	private JoinStartInterface() {		
	}
	public static synchronized JoinStartInterface getInstance(){
		if(instance == null){
			instance = new JoinStartInterface();
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
	public  String joinStart(BetAndGiftPojo betPojo){
//		Log.v("betandgift","betandgift");
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, betPojo.getUserno());
			jsonProtocol.put(ProtocolManager.BATCHCODE, betPojo.getBatchcode());
			jsonProtocol.put(ProtocolManager.BETCODE, betPojo.getBet_code());
			jsonProtocol.put(ProtocolManager.LOTNO, betPojo.getLotno());
			jsonProtocol.put(ProtocolManager.LOTMULTI, betPojo.getLotmulti());
			jsonProtocol.put(ProtocolManager.SELLWAY, betPojo.getSellway());
			jsonProtocol.put(ProtocolManager.AMOUNT	, betPojo.getAmount());
			jsonProtocol.put(ProtocolManager.BETTYPE, betPojo.getBettype());
			jsonProtocol.put(ProtocolManager.PHONE_NUM, betPojo.getPhonenum());		
			jsonProtocol.put(ProtocolManager.ISSELLWAYS, betPojo.getIsSellWays());		
			jsonProtocol.put(ProtocolManager.ONEAMOUNT, betPojo.getOneAmount());
			jsonProtocol.put(ProtocolManager.SAFEAMT, betPojo.getSafeAmt());
			jsonProtocol.put(ProtocolManager.BUYAMT, betPojo.getBuyAmt());
			jsonProtocol.put(ProtocolManager.RATION, betPojo.getCommisionRation());
			jsonProtocol.put(ProtocolManager.VISIBILITY, betPojo.getVisibility());
			jsonProtocol.put(ProtocolManager.MINAMT, betPojo.getMinAmt());
			jsonProtocol.put(ProtocolManager.DESCRIPTION, betPojo.getDescription());
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
