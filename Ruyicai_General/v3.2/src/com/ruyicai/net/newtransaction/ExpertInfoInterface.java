package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;

public class ExpertInfoInterface {
	private static String COMMAND = "information";
	private static ExpertInfoInterface instance = null;
	private ExpertInfoInterface(){
	}
	public synchronized static ExpertInfoInterface getInstance(){
		if(instance == null){
			instance = new ExpertInfoInterface();
		}
		return instance;
	}
	/**
	 * ר�Ҽ��Ų�ѯ
	 * type=1:������Ϣ 
     * type=2:˫ɫ�� 
     * type=3:����3D
     * type=4:������ 
	 */
	public static String expertInfoQuery(String type,String maxresult,String index){
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.TYPE,type);
			jsonProtocol.put(ProtocolManager.MAXRESULT,maxresult);
			jsonProtocol.put(ProtocolManager.PAGEINDEX,index);
			jsonProtocol.put(ProtocolManager.NEWSTYPE,"expertCode");
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
