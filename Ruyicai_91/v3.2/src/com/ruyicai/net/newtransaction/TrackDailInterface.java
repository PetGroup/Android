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
public class TrackDailInterface {

	private static String COMMAND = "AllQuery";
	private TrackDailInterface() {
		
	}

	private static TrackDailInterface instance = null;
	
	public synchronized static TrackDailInterface getInstance(){
		if(instance == null){
			instance = new TrackDailInterface();
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
	public  String looktrack(String tsubscribeid){

			JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
//			Log.v("getDefaultJsonProtocol",jsonProtocol+"");
			try {
				jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
				jsonProtocol.put(ProtocolManager.TYPE, "trackDetail");
				jsonProtocol.put(ProtocolManager.TSUSBSCRIBEID, tsubscribeid);
				
				return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
				
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				return "";
		}


}
