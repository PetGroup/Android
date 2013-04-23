package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

public class QueryLatelyWithdrawInterface {
	

	private static String COMMAND = "getCash";
	private static QueryLatelyWithdrawInterface instance = null;
	private QueryLatelyWithdrawInterface(){
	}
	
	public synchronized static QueryLatelyWithdrawInterface getInstance(){
		if(instance == null){
			instance = new QueryLatelyWithdrawInterface();
		}
		return instance;
	}
/**
 * ��ѯ�û�������ֵķ���
 * @param userno  �û����
 * @param sessionid sessionid
 * @param phonenum �û�ע���ֻ���
 * @return  error_code|message<br/>
 *           000000   | �ɹ�<br/>
 *           000047   |�����ּ�¼<br>
 *           999999   |����ʧ��<br>
 *           cashdetailid �����ּ�¼id<br>
 *           amount       �����<br>
 *			 araeaname    ����ַ<br>
 *           bankname     ����������<br>
 *           stat         ������״̬��1�����,2����У�3�ɹ���4����ȡ����<br>
 *			 allbankname  �����п������ֵ���������<br>
 */
	public static String queryLatelyWithdraw(String userno,String sessionid,String phonenum){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.SESSIONID, sessionid);
			jsonProtocol.put(ProtocolManager.CASHTYPE,"queryCash");
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);	
			
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
