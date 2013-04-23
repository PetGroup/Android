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
 * jcType:1��������0��������
 */
	public static String  queryJcInfo(String jcType,String jcVauetype){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.TYPE,"jcDuiZhenLimit");
			jsonProtocol.put(ProtocolManager.JCTYPE,jcType);
			jsonProtocol.put(ProtocolManager.JCVALUETYPE,jcVauetype);
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
