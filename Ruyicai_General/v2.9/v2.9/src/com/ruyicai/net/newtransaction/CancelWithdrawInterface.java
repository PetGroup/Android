package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;
/**
 * ȡ�����ֽӿ�
 * @author miao
 *
 */
public class CancelWithdrawInterface {
	
	private static String COMMAND = "getCash";
	private static CancelWithdrawInterface instance = null;
	private CancelWithdrawInterface(){
	}
	
	public  synchronized static CancelWithdrawInterface getInstance(){
		if(instance == null){
			instance = new CancelWithdrawInterface();
		}
		return instance;
	}
	/**
	 * ȡ�����ֵ��õķ���
	 * @param userno
	 * @param sessionid
	 * @param phonenum
	 * @return error_code|message<br/>
	 *             000000| �ɹ�<br/>,
	 *             000045|���ּ�¼�Ѿ�ȡ��<br/>
	 *             999999|����ʧ��
	 */
	public static String cancelWithdraw(String userno,String sessionid,String phonenum,String cashdetailId){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.SESSIONID, sessionid);
			jsonProtocol.put(ProtocolManager.CASHID, cashdetailId);
			jsonProtocol.put(ProtocolManager.CASHTYPE,"cancelCash");
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);	
			
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
