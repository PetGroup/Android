package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

/**
 * �û�ע��ӿڵ���
 * 
 * @author haojie
 * 
 */
public class RegisterInterface {
	
	private static RegisterInterface instance = null;
	private static String COMMAND = "register";
	
	private RegisterInterface() {
		
	}
	
	public static synchronized RegisterInterface getInstance(){
		if(instance == null){
			instance = new RegisterInterface();
		}
		return instance;
	}
	
	String error_code = "00";

	public String userregister(String login_User, String login_password, String login_Id,String realname,String isBindPhone) {

		JSONObject obj = null;
			//String re = register(login_User, login_password, login_Id);
			
			String re = registerLotserver(login_User, login_password, login_Id, realname,isBindPhone);

		return re;

	}
	
	
	/**
	 * �û�ע��ĵ��÷���
	 * @param mobileCode   �û����ֻ���
	 * @param password      �û�������
	 * @param certid      �û������֤��
	 * @param realname     �û�����ʵ����
	 * @return
	 * 	error_code 	 |	message	<br>
	 * 		000000	 |  �ɹ�,<br>
	 *		999999	 |  ����ʧ�� <br>
	 */

	public String registerLotserver(String mobileCode,String password,String certid,String realname,String isBindPhone){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.PHONE_NUM, mobileCode);
			jsonProtocol.put(ProtocolManager.PASSWORD, password);
			jsonProtocol.put(ProtocolManager.CERTID	, certid);
			jsonProtocol.put(ProtocolManager.NAME, realname);
			jsonProtocol.put(ProtocolManager.ISBINDPHONE, isBindPhone);
			
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	
}
