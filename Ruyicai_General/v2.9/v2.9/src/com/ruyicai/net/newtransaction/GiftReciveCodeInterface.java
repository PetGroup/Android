package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.BetAndWinAndTrackAndGiftQueryPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

public class GiftReciveCodeInterface {

	private static String COMMAND = "betLot";

	private static GiftReciveCodeInterface instance = null;
	private GiftReciveCodeInterface(){
	}
	public  synchronized static GiftReciveCodeInterface getInstance(){
		if(instance == null){
			instance = new GiftReciveCodeInterface();
		}
		return instance;
	}
	/**
	 * ��ȡ��Ʊ��ȡ��֤��
	 * @param giftQueryPojo
	 * @return
	 */
	public  String giftCodeQuery(String id){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.BETTYPE, "receivePresentSecurityCode");
			jsonProtocol.put(ProtocolManager.GIFTID, id);
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";	
	}
}
