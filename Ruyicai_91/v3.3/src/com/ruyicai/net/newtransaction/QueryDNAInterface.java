package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import com.ruyicai.constant.Constants;
import com.ruyicai.net.InternetUtils;
import com.ruyicai.util.ProtocolManager;

/**
 * ��ѯDNA��ֵ�ӿ�
 * @author miao
 *
 */
public class QueryDNAInterface {
	
	private static String COMMAND = "AllQuery";

	private static QueryDNAInterface instance = null;
	private QueryDNAInterface(){
		
	}

	public synchronized static QueryDNAInterface getInstance(){
		if(instance == null){
			instance = new QueryDNAInterface();
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
	 *        ����json{ bindstate ��״̬, name ����,bankcardno���п���,certid���֤��,addressname�������ڵ�,binddate��ʱ��,araeaname���е�ַ,phonenum�ֻ���}
	 */
	public static String queryDNA(String phonenum,String sessionid,String userno){
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, userno);
			jsonProtocol.put(ProtocolManager.TYPE, "dna");
			jsonProtocol.put(ProtocolManager.SESSIONID, sessionid);				
			jsonProtocol.put(ProtocolManager.PHONE_NUM, phonenum);	
						
			return InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "";
		
	}

}
