/**
 * 
 */
package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

/**
 * @author Administrator
 *
 */
public class QueryJoinInfoInterface {
	private static String COMMAND = "QueryLot";
	public static final String MINAMT="buyAmt";
	public static final String PROGRESS="progress";
	public static final String TOTALAMT="totalAmt";
	public static final String DESC="desc";
	public static final String ASC="asc";
	private static  QueryJoinInfoInterface instance = null;
	private  QueryJoinInfoInterface(){
	}
	
	public synchronized static  QueryJoinInfoInterface getInstance(){
		if(instance == null){
			instance = new  QueryJoinInfoInterface();
		}
		return instance;
	}
/**
 * ��ѯ�������ķ���
 *
 *����������orderBy=minAmt
 *����������orderBy=progress
 *���ܶ�����orderBy=totalAmt
 *orderDir=desc//����
 *orderDir=asc//����
 */
	public static String  queryLotJoinInfo(String lotno,String batchcode,String orderBy,String orderDir,String newPage,String maxresult){
		
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.LOTNO,lotno);
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.MAXRESULT, maxresult);
			jsonProtocol.put(ProtocolManager.PAGEINDEX, newPage);
			jsonProtocol.put(ProtocolManager.TYPE,"querycaselot");
			jsonProtocol.put(ProtocolManager.ORDERBY,orderBy);
			jsonProtocol.put(ProtocolManager.ORDERDIR,orderDir);
			jsonProtocol.put(ProtocolManager.BATCHCODE,batchcode);
			jsonProtocol.put(ProtocolManager.STATE,"");
			
			
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
