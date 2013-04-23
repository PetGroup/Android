package com.ruyicai.net.newtransaction;

import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

/**
 * ������ѯ��ȡ����ӿ�
 * @author miao
 *
 */
public class PrizeInfoInterface {
	private static final String COMMAND = "QueryLot";
	private static PrizeInfoInterface prize = null;
	
	private PrizeInfoInterface(){
		
	}
	
	public synchronized static PrizeInfoInterface getInstance(){
		if(prize == null){
			prize = new PrizeInfoInterface();
		}
		return prize;
	}
	/**
	 * ���ݲ��ֺͲ��ֵ��ں�����ȡ���ںŶ�Ӧ�Ŀ�������
	 * @param lotno  ���ֱ��
	 * @param pageindex ��ǰҳ
	 * @param maxresult ��ǰҳ���ص��������
	 * @return  ����һ�����п�������������ݵ�JSONObject����
	 */
	public  JSONObject getNoticePrizeInfo(String lotno,String pageindex,String maxresult){
		Log.e("222222222222222222222222222222222222222222","2222222222222222222222222222222222222222222");
		String result = "";
		try {
			JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.TYPE, "winInfoList");
			jsonProtocol.put(ProtocolManager.LOTNO, lotno);
			jsonProtocol.put(ProtocolManager.PAGEINDEX, pageindex);
			jsonProtocol.put(ProtocolManager.MAXRESULT, maxresult);
			result = InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER,jsonProtocol.toString());
			return new JSONObject(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
