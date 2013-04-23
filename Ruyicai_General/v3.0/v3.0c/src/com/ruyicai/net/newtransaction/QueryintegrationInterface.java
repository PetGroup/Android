package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

/**
 * ��ѯDNA��ֵ�ӿ�
 * @author miao
 *
 */
public class QueryintegrationInterface {
	
	private static String COMMAND = "updateUserInfo";

	private static QueryintegrationInterface instance = null;
	private  QueryintegrationInterface(){
		
	}

	public synchronized static QueryintegrationInterface getInstance(){
		if(instance == null){
			instance = new QueryintegrationInterface();
		}
		return instance;
		
	}
	/**
	 * ��ѯ�ģΣ���ֵ�ķ���
	 * @param phonenum�������������û��ֻ���
	 * @param sessionid���������������������صģ���������
	 * @param userno�������������û���userno
	 * @return  error_code ���error_codeΪ"000047"Ϊmessage="�޼�¼"�����error_codeΪ"070002", message="δ��¼"
	 * ���error_codeΪ"999999",message="����ʧ��";���error_codeΪ"000000",message="�ɹ�"
	 *  **/      
	public static String queryintegration(String phonenum,String sessionid,String userno){
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.TYPE, "userCenter");
			jsonProtocol.put(ProtocolManager.SESSIONID, sessionid);				
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);	
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
		
	}

}
