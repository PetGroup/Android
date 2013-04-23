package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.BetAndWinAndTrackAndGiftQueryPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

/**
 * �н���ѯ�ӿ�
 * @author miao
 *
 */
public class WinQueryInterface {

	private static String COMMAND = "QueryLot";
	
	private WinQueryInterface(){
	}
	private static WinQueryInterface instance = null;
	public synchronized static WinQueryInterface getInstance(){
		if(instance == null){
			instance = new WinQueryInterface();
		}
		return instance;
	}
	/**
	 * �н���ѯ�ķ���
	 * @param winQueryPojo<br>
	 *  startline	��ʼ����	��ʼ����<br>
	 *	stopline	��ֹ����	��ֹ����<br>
	 *	lotno	����	���֣����Դ���Ԥ����<br>
 	 *  batchcode	�ں�	�ں�<br>
	 * @return
	 *  error_code |message	<br>
	 *  000000     |�ɹ�<br>
     * 000047      |�޼�¼<br>
     * bettime	        ��Ͷעʱ��<br>
     * lotno	   :����	<br>
     * batchcode   :�ں�<br>
     * lotmulti	   :����<br>
     * amount	   :���	 ����xxxԪ<br>
     * bet_code	   :ע��	
	 */
	public String winQuery(BetAndWinAndTrackAndGiftQueryPojo winQueryPojo){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, winQueryPojo.getUserno());
			jsonProtocol.put(ProtocolManager.SESSIONID, winQueryPojo.getSessionid());
			jsonProtocol.put(ProtocolManager.MAXRESULT, winQueryPojo.getMaxresult());
			jsonProtocol.put(ProtocolManager.PAGEINDEX, winQueryPojo.getPageindex());
			jsonProtocol.put(ProtocolManager.LOTNO, winQueryPojo.getLotno());
			jsonProtocol.put(ProtocolManager.TYPE, winQueryPojo.getType());
			jsonProtocol.put(ProtocolManager.BATCHCODE, winQueryPojo.getBatchcode());
			jsonProtocol.put(ProtocolManager.PHONE_NUM, winQueryPojo.getPhonenum());				
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
