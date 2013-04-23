package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.BetAndWinAndTrackAndGiftQueryPojo;
import com.ruyicai.util.ProtocolManager;
/**
 * �ͻ���׷�Ų�ѯ�ӿ�
 * @author miao
 *
 */
public class TrackQueryInterface {

	private static String COMMAND = "AllQuery";

	private TrackQueryInterface(){
	}
	private static TrackQueryInterface instance = null;
	public synchronized static TrackQueryInterface getInstance(){
		if(instance == null){
			instance = new TrackQueryInterface();
		}
		return instance;
	}
	/**
	 * ׷�Ų�ѯ����
	 * @param trackQueryPojo
	 * @return
	 * error_code	|	message	<br>
	 * 000000		|�ɹ�<br>
	 * 000047		|�޼�¼<br>
	 * 999999		|����ʧ��<br>
	 * bettime		:	Ͷעʱ��	Ͷעʱ��<br>
	 * lotno		:	����<br>
	 * batchcode	:	�ں�<br>
	 * lotmulti		:	����<br>
	 * amount		:   ׷���ܽ��	��λΪ��<br>
	 * stat			:	׷��״̬��0 ������   2 ��ȡ��  3 ��׷�꣩<br>
	 * beginbatch	��	��ʼ��	׷�ŵĿ�ʼ��<br>
	 * lastbatch	��	׷�ŵĽ�ֹ��<br>
	 * batchnum		�� 	׷������	<br>
	 * betnum	           ��	ע��	<br>
	 * lastnum		 ��	�Ѿ����������	<br>
	 */
	public String trackQuery(BetAndWinAndTrackAndGiftQueryPojo trackQueryPojo){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, trackQueryPojo.getUserno());
			jsonProtocol.put(ProtocolManager.SESSIONID, trackQueryPojo.getSessionid());
			jsonProtocol.put(ProtocolManager.BATCHCODE, trackQueryPojo.getBatchcode());
			jsonProtocol.put(ProtocolManager.PAGEINDEX, trackQueryPojo.getPageindex());
			jsonProtocol.put(ProtocolManager.MAXRESULT, trackQueryPojo.getMaxresult());
			jsonProtocol.put(ProtocolManager.TYPE, trackQueryPojo.getType());
			jsonProtocol.put(ProtocolManager.LOTNO, trackQueryPojo.getLotno());
			jsonProtocol.put(ProtocolManager.PHONE_NUM, trackQueryPojo.getPhonenum());				
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	
	}

}
