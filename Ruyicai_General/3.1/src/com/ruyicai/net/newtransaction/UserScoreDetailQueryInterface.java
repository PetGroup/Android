package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.AccountDetailQueryPojo;
import com.ruyicai.net.newtransaction.pojo.UserScroeDetailQueryPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

public class UserScoreDetailQueryInterface {
	
	private static String COMMAND = "updateUserInfo";


	private static UserScoreDetailQueryInterface instance = null;
	private UserScoreDetailQueryInterface(){
	}
	public synchronized static UserScoreDetailQueryInterface getInstance(){
		if(instance == null){
			instance = new UserScoreDetailQueryInterface();
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
	public  String scroeDetailQuery(UserScroeDetailQueryPojo scroeDetailPojo){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, scroeDetailPojo.getUserno());
			jsonProtocol.put(ProtocolManager.SESSIONID, scroeDetailPojo.getSessionid());
			jsonProtocol.put(ProtocolManager.MAXRESULT, scroeDetailPojo.getMaxresult());
			jsonProtocol.put(ProtocolManager.PAGEINDEX, scroeDetailPojo.getPageindex());
			jsonProtocol.put(ProtocolManager.TYPE,scroeDetailPojo.getType());
			jsonProtocol.put(ProtocolManager.PHONE_NUM, scroeDetailPojo.getPhonenum());	
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";	
	}

}
