package com.ruyicai.net.newtransaction;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ruyicai.net.InternetUtils;
import com.ruyicai.net.newtransaction.pojo.RechargePojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.ProtocolManager;

/**
 * �û���ֵ�Ľӿ�
 * @author miao
 */
public class RechargeInterface {
	
	private static  String COMMAND = "recharge";
	

	private static RechargeInterface instance = null;
	private RechargeInterface(){
	}
	public synchronized static RechargeInterface getInstance(){
		if(instance == null){
			instance = new RechargeInterface();
		}		
		return instance;
	}
	/**
	 * ��ֵ�ķ���
	 *@param userno   �û����
	 *@param amount   �û���ֵ����λΪ��
	 *@param cardtype ������ <br>
	  						0203:�ƶ���ֵ��<br>
                            0206:��ͨ��ֵ��<br>
							0221:���ų�ֵ��<br>
							0101:��������<br>
							0102:��������<br>
							0103:��������<br>
							0204:��;��<br>
							0201:����һ��ͨ<br>
							0202:ʢ��<br>

	 *@param rechargetype ��ֵ����  <br>
	 						01:���п��绰��ֵ,<br>
         					02:�ֻ���ֵ����ֵ<br>
         					03:�ֻ����г�ֵ<br>
         					04:��Ϸ�㿨��ֵ<br>
         					05:֧������ֵ<br>

	 *@param cardno   ����
	 *@param cardpwd   ��Ӧ���š�cardno��������
	 *@param sessionid 
	 *@param name    �ֿ�������
	 *@param certid  ���֤��
	 *@param araeaname 
	 * @return error_code|message<br/>
	           070002   |δ��¼<br/>
			   000000   |��ֵ�������������Ժ��ѯ���<br/>
			   001200   |���Ż�������� <br/>
			   001300   |�������ύ<br/>
			   001400   |����д��ϸ��Ϣ<br/>
			   001500   |�ݲ�֧�ֿ���<br/>
			   999999   |����ʧ��<br/>			
			   transation_id	����id<br/>
			   return_url	֧������Ӧ��ַ
	 */
	public  static String recharge(RechargePojo chargePojo){
		String re = "";
		JSONObject jsonProtocol = ProtocolManager.getInstance().getDefaultJsonProtocol();
		try {
			jsonProtocol.put(ProtocolManager.COMMAND,COMMAND);
			jsonProtocol.put(ProtocolManager.USERNO, chargePojo.getUserno());
			jsonProtocol.put(ProtocolManager.AMOUNT,chargePojo.getAmount()+"00");
			jsonProtocol.put(ProtocolManager.CARDTYPE, chargePojo.getCardtype());
			jsonProtocol.put(ProtocolManager.RECHARGETYPE, chargePojo.getRechargetype());
			jsonProtocol.put(ProtocolManager.CARDNO, chargePojo.getCardno());
			jsonProtocol.put(ProtocolManager.CARDPWD, chargePojo.getCardpwd());
			jsonProtocol.put(ProtocolManager.NAME, chargePojo.getName());
			jsonProtocol.put(ProtocolManager.CERTID,chargePojo.getCertid());
			jsonProtocol.put(ProtocolManager.ADDRESSNAME,chargePojo.getAddressname());
			jsonProtocol.put(ProtocolManager.SESSIONID, chargePojo.getSessionid());			
			jsonProtocol.put(ProtocolManager.PHONE_NUM, chargePojo.getPhonenum());	
			jsonProtocol.put(ProtocolManager.ISWHITE, chargePojo.getIsIswhite());	
			jsonProtocol.put(ProtocolManager.BANKADDRESS, chargePojo.getBankaddress());	
			Log.e("url===",jsonProtocol.toString());
			re = InternetUtils.GetMethodOpenHttpConnectSecurity(Constants.LOT_SERVER, jsonProtocol.toString());
			Log.e("re=====",re);
			return re;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return re;
	}

}
