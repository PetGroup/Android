package com.ruyicai.net.newtransaction;

import org.json.JSONObject;


import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;
import com.ruyicai.util.PublicMethod;

/**
 * ���������������
 * @author haojie
 * {"errorCode":"A030000",
 *  "clientnametype":"000001",
 *  "currentversion":"2.2",
 *  "downurl":"http://219.148.162.68/upload/client/1305623731500.apk"
 *  "news":"��ʾ��Ϣ"
 *  "currentBatchCode":"{"F47102"��{��ǰ�ں�,����ʱ��}}"
 *  }
 */
public class SoftwareUpdateInterface {
	private static final String TAG = "SoftwareUpdateInterface";
	
	private static String COMMAND = "softwareupdate";
	
	private SoftwareUpdateInterface() {
		
	}
	
	private static SoftwareUpdateInterface instance = null ;
	
	public synchronized static SoftwareUpdateInterface getInstance(){
		if(instance == null){
			instance = new SoftwareUpdateInterface();
		}
		return instance;
	}
	
	
	/**
	 * ����Ƿ�����°汾
	 * statInfo ͳ����Ϣ
	 */
	public String softwareupdate(JSONObject statInfo) {
		String reValue = "";
		try {
			JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.IS_EMULATOR, PublicMethod.isEmulator());
			
			if(statInfo != null){
				//��ͳ����Ϣ
				jsonProtocol.put(ProtocolManager.GAME_STATINFO, statInfo);
			}
			reValue = InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER,jsonProtocol.toString());
		//	Log.e("reValue====", reValue);
		} catch (Exception e) {
		//	Log.e(TAG, "softwareupeate error");
		}
		
		return reValue;

	}
	
}
