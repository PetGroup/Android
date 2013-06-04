package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;
/**
 * ���ֻ������ύ��֤��Ľӿ�
 * @author miao
 * @time  2012-2-13  2:00pm
 */
public class BindPhoneInterface {
	
	private  static   String COMMAND = "updateUserInfo";
	
	private static BindPhoneInterface instance = null;
	private BindPhoneInterface() {
		
	}
	public synchronized static BindPhoneInterface getInstance(){
		if(instance == null){
			instance = new BindPhoneInterface();
		}
		return instance;
	}
	/**
	 * �ύҪ�󶨵��ֻ�����
	 * @param bindPhoneNum �û�Ҫ�󶨵��ֻ���
	 * @return
	 * error_code	|	message	<br>
	 * 	000000		|	�ɹ�<br>
	 *  000005		|	ʧ�� <br>
	 *  999999		|	����ʧ��
	 */
	public  String submitPhonenum(String bindPhoneNum,String phonenum,String userno){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);
			jsonProtocol.put(ProtocolManager.BINDPHONENUM, bindPhoneNum);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.TYPE, "bindPhoneSecurityCode");
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	/**
	 * �ύ���͵����ֻ��ϵ���֤��
	 * @param SecurityCode 
	 * @param phonenum  �û��ֻ���
	 * @param userno   �û���userno
	 * @param bindphonenum
	 * @return
	 */
	public  String submitSecurityCode(String SecurityCode,String bindphonenum,String phonenum,String userno){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);
			jsonProtocol.put(ProtocolManager.SECURITYCODE, SecurityCode);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.BINDPHONENUM, bindphonenum);
			jsonProtocol.put(ProtocolManager.TYPE, "bindPhone");
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	/**
	 * �Ӵ���
	 * @param phonenum
	 * @param userno
	 * @return
	 */
	public  String unBind(String phonenum,String userno){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.TYPE, "removeBindPhone");
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
