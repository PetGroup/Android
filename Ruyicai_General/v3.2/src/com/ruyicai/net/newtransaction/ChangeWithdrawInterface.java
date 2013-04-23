package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.ChangeWithdrawPojo;
import com.ruyicai.util.ProtocolManager;

/**
 * �޸���������
 * @author Administrator
 *
 */
public class ChangeWithdrawInterface {
	private static String COMMAND = "getCash";
	
	private ChangeWithdrawInterface(){
	}
	private static ChangeWithdrawInterface instance = null;
	public  synchronized static ChangeWithdrawInterface getInstance(){
		if(instance == null){
			instance = new ChangeWithdrawInterface();
		}
		return instance;
	}
	/**
	 * �޸����ֵ��õķ���
	 * @param changeWithdPojo
	 * @return error_code|message<br/>
	 *            000000 |�ɹ�<br/>
     *            999999 |����ʧ��
	 */
	public static String changeWithdraw(ChangeWithdrawPojo changeWithdPojo){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, changeWithdPojo.getUserno());
			jsonProtocol.put(ProtocolManager.AMOUNT, changeWithdPojo.getAmount());
			jsonProtocol.put(ProtocolManager.ARAEANAME,changeWithdPojo.getAraeaname());
			jsonProtocol.put(ProtocolManager.BANKNAME, changeWithdPojo.getBankname());
			jsonProtocol.put(ProtocolManager.SESSIONID, changeWithdPojo.getSessionid());
			jsonProtocol.put(ProtocolManager.NAME, changeWithdPojo.getName());
			jsonProtocol.put(ProtocolManager.BANKCARDNO, changeWithdPojo.getBankcardno());
			jsonProtocol.put(ProtocolManager.CASHTYPE,"cash");
			jsonProtocol.put(ProtocolManager.PASSWORD, changeWithdPojo.getPassword());	
			jsonProtocol.put(ProtocolManager.PHONE_NUM, changeWithdPojo.getPhonenum());	
			
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
