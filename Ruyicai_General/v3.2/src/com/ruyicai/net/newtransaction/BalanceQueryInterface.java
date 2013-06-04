package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;
/**
 * �û����ģ�����ѯ
 * @author miao
 *
 */
public class BalanceQueryInterface {
	
	private static String COMMAND = "AllQuery";
	private static BalanceQueryInterface instance = null;
	private BalanceQueryInterface(){
	}
	
	public synchronized static BalanceQueryInterface getInstance(){
		if(instance == null){
			instance = new BalanceQueryInterface();
		}
		return instance;
	}
	/**
	 * ����ѯ����
	 * @param userno
	 * @param sessionid
	 * @param phonenum
	 * @return
	 * error_code|	message<br>
	 * 	000000   |   �ɹ�<br>
	 * 000047    |  �޼�¼<br>
	 * 999999    |����ʧ��<br>
	 *balance	   :���	�û����<br>
	 *drawbalance  :���������<br>
	 *freezebalance:	�û�������<br>
	 *bet_balance  :	Ͷע���<br>

	 */
	public static String balanceQuery(String userno,String sessionid,String phonenum){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.SESSIONID, sessionid);
			jsonProtocol.put(ProtocolManager.TYPE,"balance");
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);				
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
