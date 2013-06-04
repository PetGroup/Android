/**
 * 
 */
package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;

/**
 * �������
 * @author Administrator
 *
 */
public class JoinInInterface {
	private static String COMMAND = "betLot";
	private static  JoinInInterface instance = null;
	private  JoinInInterface(){
	}
	
	public synchronized static  JoinInInterface getInstance(){
		if(instance == null){
			instance = new  JoinInInterface();
		}
		return instance;
	}
/**
 * �������ķ���
 */
	public static String  betLotJoin(String userno,String phonenum,String caseId,String amount,String safeAmt){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);	
			jsonProtocol.put(ProtocolManager.AMOUNT, amount);
			jsonProtocol.put(ProtocolManager.SAFEAMT, safeAmt);
			jsonProtocol.put(ProtocolManager.CASEID, caseId);
			jsonProtocol.put(ProtocolManager.BETTYPE,"betcase");
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
