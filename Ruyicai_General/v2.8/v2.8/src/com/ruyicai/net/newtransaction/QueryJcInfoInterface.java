package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

/**
 * ���ʶ����ѯ
 * @author Administrator
 *
 */
public class QueryJcInfoInterface {
	private static String COMMAND = "QueryLot";
	private static  QueryJcInfoInterface instance = null;
	private  QueryJcInfoInterface(){
	}
	
	public synchronized static  QueryJcInfoInterface getInstance(){
		if(instance == null){
			instance = new  QueryJcInfoInterface();
		}
		return instance;
	}
/**
 * ��ѯ�������ķ���
 */
	public static String  queryJcInfo(){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.TYPE,"jcDuiZhen");
			jsonProtocol.put(ProtocolManager.JCTYPE,"1");
			jsonProtocol.put(ProtocolManager.JCVALUETYPE,"1");
			Log.e("js===", jsonProtocol.toString());
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
