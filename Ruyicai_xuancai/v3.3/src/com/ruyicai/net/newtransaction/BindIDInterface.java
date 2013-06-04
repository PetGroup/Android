package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;

public class BindIDInterface {

	private  static   String COMMAND = "updateUserInfo";
	
	private static BindIDInterface instance = null;
	private BindIDInterface() {
		
	}
	public synchronized static BindIDInterface getInstance(){
		if(instance == null){
			instance = new BindIDInterface();
		}
		return instance;
	}
	/**
	 * ����ݷ���
	 * @param phonenum �û�ע���ֻ���
	 * @param name     �û���ʵ����
	 * @param certid   �û����֤��
	 * @param userno   userno
	 * @return
	 * error_code	|	message	<br>
	 * 	000000		|	�ɹ�<br>
	 *  000005		|	ʧ�� <br>
	 *  999999		|	����ʧ��
	 */
	public  String bindID(String phonenum,String name,String certid,String userno){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.NAME, name);
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);
			jsonProtocol.put(ProtocolManager.CERTID, certid);		
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
