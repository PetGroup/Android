package com.ruyicai.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.net.newtransaction.GiftReciveCodeInterface;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

public class GiftReciveInterface {

	private static String COMMAND = "betLot";

	private static GiftReciveInterface instance = null;
	private GiftReciveInterface(){
	}
	public  synchronized static GiftReciveInterface getInstance(){
		if(instance == null){
			instance = new GiftReciveInterface();
		}
		return instance;
	}
	/**
	 * ��ȡ��Ʊ��ȡ��֤��
	 * @param giftQueryPojo
	 * @return
	 */
	public  String giftReciveQuery(String id,String code){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.BETTYPE, "receivePresent");
			jsonProtocol.put(ProtocolManager.GIFTID, id);
			jsonProtocol.put(ProtocolManager.GIFTCODE, code);
				
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";	
	}
}
