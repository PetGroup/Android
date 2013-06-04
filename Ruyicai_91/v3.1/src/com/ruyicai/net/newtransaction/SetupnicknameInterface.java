package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.UserScroeDetailQueryPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

public class SetupnicknameInterface {
	private static String COMMAND = "updateUserInfo";


	private static SetupnicknameInterface instance = null;
	private SetupnicknameInterface(){
	}
	public synchronized static SetupnicknameInterface getInstance(){
		if(instance == null){
			instance = new SetupnicknameInterface();
		}
		return instance;
	}
	/**
	 * 	������ϸ
	 * 
	 * 	@param userno    �û����  �û�ע��ɹ����ص��û����<br>
	 *	@param startline ��ʼ����<br>
	 *	@param stopline  ��ֹ����<br>
	 *	@param pageindex �ڼ�ҳ������ڶ�ҳ,pageindex=2<br>
	 *	@param sessionid <br>
	
	 * 	@return 
	 *	   error_code | message	<br>
	 * 		  000000  |�ɹ�<br>
	 * 		  000047  |�޼�¼<br>
	 *	      999999  |����ʧ��<br>
	 * 		type_memo :	��������<br>
	 *		amount	  :�䶯���	��λΪ��<br>
	 *		plattime  :	����ʱ��	<br>
	 */
	public  String setupnickname(String userno, String nickname){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.TYPE,"updateNickName");
//			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);		
			jsonProtocol.put("nickName", nickname);		
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";	
	}

}
