package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;
/**
 * <b>ע��ӿ�</b>
 * @author miao
 */
public class LoginInterface {
	private static String COMMAND = "login";
	private static LoginInterface instance = null;
	
	
	
	
	private LoginInterface() {
		
	}
	
	public  static synchronized LoginInterface getInstance(){
		if(instance == null){
			instance = new LoginInterface();
		}
		return instance;
	}
	/**
	 * �ͻ��˵�¼����
	 * @param phonenum �ֻ���
 	 * @param password ����
	 * @return
	 * error_code |  message<br>	
	 * 	000000	  |  �ɹ�<br>
	 * 999999     |  ����ʧ��<br>
	 * sessionid  :	��̨���ص�sessionid	<br>
	 *	userno	  : ��̨���ص��û����	
	 */
	public static String login(String phonenum,String password,String isAutoLogin){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);
			jsonProtocol.put(ProtocolManager.PASSWORD, password);
			jsonProtocol.put(ProtocolManager.ISAUTOLOGIN, isAutoLogin);
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	

}
