package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;
/**
 * ȡ��׷�Žӿ�
 * @author miao
 *
 */
public class CancelTrackInterface {

	private static String COMMAND = "cancelTrack";
	private CancelTrackInterface() {
		
	}

	private static CancelTrackInterface instance = null;
	
	public synchronized static CancelTrackInterface getInstance(){
		if(instance == null){
			instance = new CancelTrackInterface();
		}
		return instance;
	}
	/**
	 * ȡ��׷�ŵķ���
	 * @param userno  �û���ţ��û�ע��ɹ����ظ��û��ı��
	 * @param sessionid  sessionid
	 * @param command    �˴�Ϊ ��cancelTrack��
	 * @param tsubscribeid  ׷�ż�¼id
	 * @param phonenum   �ֻ���
	 * @return {error_code,message}	  000000:ȡ���ɹ�	070002:δ��¼    �����������Ĵ���ʧ��
	 */
	public  String canceltrack(String userno,String sessionid,String tsubscribeid,String  phonenum){

			JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
//			Log.v("getDefaultJsonProtocol",jsonProtocol+"");
			try {
				jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
				jsonProtocol.put(ProtocolManager.USERNO, userno);
				jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);	
				jsonProtocol.put(ProtocolManager.SESSIONID, sessionid);
				jsonProtocol.put(ProtocolManager.TSUSBSCRIBEID, tsubscribeid);
				
				return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
				
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				return "";
		}


}
