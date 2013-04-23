package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

public class NoticeWinInterface {
private static NoticeWinInterface noticeInterface = null;
	
	private static String COMMAND = "QueryLot";
	
	private NoticeWinInterface (){
		
	}
	
	public synchronized static NoticeWinInterface getInstance(){
		if(noticeInterface == null){
			noticeInterface = new NoticeWinInterface();
		}
		return noticeInterface;
	}
	
	/**
	 * ��ȡ���еĿ�����Ϣ
	 * @return
	 */
	public JSONObject getLotteryAllNotice(){
		String result = "";
		try {
			JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.TYPE,"winInfo");
			result = InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER,jsonProtocol.toString());
			return new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
			//����ʧ����
		}
		
		return null;
	}
}
