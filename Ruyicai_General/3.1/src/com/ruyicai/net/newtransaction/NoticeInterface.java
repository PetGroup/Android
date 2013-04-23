package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

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
			return new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
			//����ʧ����
		}
		
		return null;
	}
	
}
