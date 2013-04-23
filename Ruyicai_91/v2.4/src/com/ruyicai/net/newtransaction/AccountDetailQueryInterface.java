package com.ruyicai.net.newtransaction;


import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.AccountDetailQueryPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;
/**
 * �˻���ϸ��ѯ�ӿ�
 * @author miao
 *
 */
public class AccountDetailQueryInterface {

	private static String COMMAND = "accountdetail";


	private static AccountDetailQueryInterface instance = null;
	private AccountDetailQueryInterface(){
	}
	public synchronized static AccountDetailQueryInterface getInstance(){
		if(instance == null){
			instance = new AccountDetailQueryInterface();
		}
		return instance;
	}
	/**
	 * 	�˻���ϸ��ѯ����
	 * 
	 * 	@param userno    �û����  �û�ע��ɹ����ص��û����<br>
	 *	@param startline ��ʼ����<br>
	 *	@param stopline  ��ֹ����<br>
	 *	@param pageindex �ڼ�ҳ������ڶ�ҳ,pageindex=2<br>
	 *	@param sessionid <br>
	 * 	@param	type     
	 *  			 1:��ֵ��<br>
	 * 				 2:֧��,<br>
	 *  	     	 3:�ɽ�,<br>
	 *  			 4:����<br>
	 * 	@param accountDetailPojo
	 * 	@return 
	 *	   error_code | message	<br>
	 * 		  000000  |�ɹ�<br>
	 * 		  000047  |�޼�¼<br>
	 *	      999999  |����ʧ��<br>
	 * 		type_memo :	��������<br>
	 *		amount	  :�䶯���	��λΪ��<br>
	 *		plattime  :	����ʱ��	<br>
	 */
	public  String accountDetailQuery(AccountDetailQueryPojo accountDetailPojo){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, accountDetailPojo.getUserno());
			jsonProtocol.put(ProtocolManager.SESSIONID, accountDetailPojo.getSessionid());
			jsonProtocol.put(ProtocolManager.MAXRESULT, accountDetailPojo.getMaxresult());
			jsonProtocol.put(ProtocolManager.PAGEINDEX, accountDetailPojo.getPageindex());
			jsonProtocol.put(ProtocolManager.PAGEINDEX, accountDetailPojo.getPageindex());
			jsonProtocol.put(ProtocolManager.TYPE,accountDetailPojo.getType());
			jsonProtocol.put(ProtocolManager.TRANSACTIONTYPE,accountDetailPojo.getTransactiontype());
			jsonProtocol.put(ProtocolManager.PHONE_NUM, accountDetailPojo.getPhonenum());				
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";	
	}
	

}
