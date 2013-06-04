package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;
/**
 * <b>�޸�����Ľӿ���</b>
 * @author miao
 */
public class ChangePasswordInterface {
	
	private  static   String COMMAND = "updatePass";
	
	
	private static ChangePasswordInterface instance = null;
	private ChangePasswordInterface() {
		
	}
	public synchronized static ChangePasswordInterface getInstance(){
		if(instance == null){
			instance = new ChangePasswordInterface();
		}
		return instance;
	}
	/**
	 * �޸�����
	 * @param phonenum �û�ע���ֻ���
	 * @param oldPass  �û�ԭ��������
	 * @param newPass  �û���������
	 * @return
	 * error_code	|	message	<br>
	 * 	000000		|	�ɹ�<br>
	 *  000005		|	ʧ�� <br>
	 *  999999		|	����ʧ��
	 */
	public  String changePass(String phonenum,String oldPass,String newPass,String sessionid,String userno){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);
			jsonProtocol.put(ProtocolManager.OLDPASS, oldPass);		
			jsonProtocol.put(ProtocolManager.NEWPASS, newPass);
			jsonProtocol.put(ProtocolManager.SESSIONID, sessionid);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
