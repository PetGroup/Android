package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.BetAndWinAndTrackAndGiftQueryPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;
/**
 * �ͻ���Ͷע��ѯ�ӿ�
 * @author miao
 *
 */
public class BetQueryInterface {
	private static String COMMAND = "QueryLot";	
	
	private static BetQueryInterface instance = null;
	private BetQueryInterface(){
	}
	
	public synchronized static BetQueryInterface getInstance(){
		if(instance == null){
			instance = new BetQueryInterface();
		}
		return instance;
	}
	/**
	 * Ͷע��ѯ�ķ���
	 * 	@param betQueryPojo  ������ betQueryPojo�еı���
	 * 	@param   userno	�û����	��¼�ɹ����ص��û������00000001<br>
	 *	@param  startline	��ʼ����	��ʼ����<br>
	 *	@param  stopline	��ֹ����	��ֹ����<br>
	 *	@param  lotno	(Ԥ���Ŀ��Դ���)����	���֣����Դ���Ԥ����<br>
	 *	@param  sessionid	sessionid<br>	
	 *	@param  batchcode	�ں�	�ں�<br>
	 *	@param  type	��ѯ����	type  Ͷע��ѯΪbet<br>
	 *	@param  command	����	command:QueryLot<br>
	 * @return
	 * error_code|message<br>	
	 *  000000   |�ɹ�<br>
	 *  000047   |�޼�¼<br>
	 *  999999   |����ʧ��<br>
	 * bettime	  ��Ͷעʱ��	<br>
	 * lotno	  ������	<br>
	 * batchcode :	�ں�<br>
	 * lotmulti	 :����<br>
     * amount	 :���	��λΪ��<br>
     *bet_code   :	ע��	 <br>
	 */
	public String betQuery(BetAndWinAndTrackAndGiftQueryPojo betQueryPojo){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, betQueryPojo.getUserno());
			jsonProtocol.put(ProtocolManager.SESSIONID, betQueryPojo.getSessionid());
			jsonProtocol.put(ProtocolManager.MAXRESULT, betQueryPojo.getMaxresult());
			jsonProtocol.put(ProtocolManager.PAGEINDEX, betQueryPojo.getPageindex());
			jsonProtocol.put(ProtocolManager.LOTNO, betQueryPojo.getLotno());
			jsonProtocol.put(ProtocolManager.TYPE, betQueryPojo.getType());
			jsonProtocol.put(ProtocolManager.BATCHCODE, betQueryPojo.getBatchcode());
			jsonProtocol.put(ProtocolManager.PHONE_NUM, betQueryPojo.getPhonenum());
			jsonProtocol.put(ProtocolManager.ISSELLWAYS, "1");
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
