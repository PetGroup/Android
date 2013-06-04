/**
 * 
 */
package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;

/**
 *  ��������ѯ
 * @author Administrator
 *
 */
public class QueryJoinCheckInterface {
	private static String COMMAND = "QueryLot";
	private static  QueryJoinCheckInterface instance = null;
	private  QueryJoinCheckInterface(){
	}
	
	public synchronized static  QueryJoinCheckInterface getInstance(){
		if(instance == null){
			instance = new  QueryJoinCheckInterface();
		}
		return instance;
	}
/**
 * ��ѯ�������ķ���
 */
	public static String  queryLotJoinCheck(String userno,String phonenum,String newPage,String maxresult){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.MAXRESULT, maxresult);
			jsonProtocol.put(ProtocolManager.PAGEINDEX, newPage);
			jsonProtocol.put(ProtocolManager.TYPE,"caselot");
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);	
			
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
