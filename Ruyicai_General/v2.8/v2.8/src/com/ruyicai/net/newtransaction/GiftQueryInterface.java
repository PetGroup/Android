package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.BetAndWinAndTrackAndGiftQueryPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;
/**
 * ���Ͳ�ѯ�ӿ�
 * @author miao
 */
public class GiftQueryInterface {

	private static String COMMAND = "AllQuery";

	private static GiftQueryInterface instance = null;
	private GiftQueryInterface(){
	}
	public  synchronized static GiftQueryInterface getInstance(){
		if(instance == null){
			instance = new GiftQueryInterface();
		}
		return instance;
	}
	/**
	 * ���Ͳ�ѯ����
	 * @param giftQueryPojo
	 * @return
	 * error_code	| message<br>	
	 *	000000		| �ɹ�<br>
	 * 	000047		| �޼�¼��<br>
	 * 	999999		| ����ʧ��<br>
	 *	gift_phonenum		�������ֻ���<br>
	 *	gifted_phonenum		���������ֻ�����<br>
	 *	betTime				����ʱ��<br>
 	 *	lotno				����<br>
 	 *	batchcode			�ں�<br>
 	 *	lotmulti			����<br>
 	 *	amount				���	��λΪ��<br>
 	 *	bet_code			ע��	<br>
	 */
	public  String giftQuery(BetAndWinAndTrackAndGiftQueryPojo giftQueryPojo){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, giftQueryPojo.getUserno());
			jsonProtocol.put(ProtocolManager.SESSIONID, giftQueryPojo.getSessionid());
			jsonProtocol.put(ProtocolManager.MAXRESULT, giftQueryPojo.getMaxresult());
			jsonProtocol.put(ProtocolManager.PAGEINDEX, giftQueryPojo.getPageindex());
			jsonProtocol.put(ProtocolManager.LOTNO, giftQueryPojo.getLotno());
			jsonProtocol.put(ProtocolManager.TYPE, giftQueryPojo.getType());
			jsonProtocol.put(ProtocolManager.BATCHCODE, giftQueryPojo.getBatchcode());
			jsonProtocol.put(ProtocolManager.PHONE_NUM, giftQueryPojo.getPhonenum());				
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";	
	}
}
