package com.ruyicai.net.newtransaction;

import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;

/**
 * ������ϸ��ѯ�ӿ�
 * @author miao
 *
 */
public class NoticePrizeDetailInterface {
	private static final String COMMAND = "AllQuery";
	private static NoticePrizeDetailInterface prizedetail = null;
	
	private NoticePrizeDetailInterface(){
		
	}
	
	public synchronized static NoticePrizeDetailInterface getInstance(){
		if(prizedetail == null){
			prizedetail = new NoticePrizeDetailInterface();
		}
		return prizedetail;
	}
	/**
	 * ���ݲ��ֺͲ��ֵ��ں�����ȡ���ںŶ�Ӧ�Ŀ�������
	 * @param lotno  ���ֱ��
	 * @param batchcode ���ֵ��ں�
	 * @return  ����һ�����п�������������ݵ�JSONObject����
	 */
	public  JSONObject getNoticePrizeDetail(String lotno,String batchcode){
		String result = "";
		try {
			JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
			jsonProtocol.put(ProtocolManager.COMMAND, COMMAND);
			jsonProtocol.put(ProtocolManager.TYPE, "winInfoDetail");
			jsonProtocol.put(ProtocolManager.LOTNO, lotno);
			jsonProtocol.put(ProtocolManager.BATCHCODE, batchcode);
			result = InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER,jsonProtocol.toString());
			return new JSONObject(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

}
