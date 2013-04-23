package com.ruyicai.net.transaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.ProtocolManager;
import com.ruyicai.util.Constants;

/**
 * 
 * @author haojie
 * ������Ϣ�������ݽӿ�
 */
public class NoticeInterface {
	
	private static NoticeInterface noticeInterface = null;
	
	private static String COMMAND = "lotteryinfomation";
	
	private NoticeInterface (){
		
	}
	
	public synchronized static NoticeInterface getInstance(){
		if(noticeInterface == null){
			noticeInterface = new NoticeInterface();
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
			result = InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER,jsonProtocol.toString());
			Log.e("noticeresult", result);
			return new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
			//����ʧ����
		}
		
		return null;
	}
	
}
