package com.palmdream.netintface;



import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid.PublicMethod;

public class ChargeInterface {
protected static String[] bankcharge = null;

	
	String error_code="00";
	String re;
	public String url;
	int iretrytimes=2;
	 JSONObject obj = null;
	    
	 /**
		 * ��ת���˻���ֵ
		 * @param accesstype  ���뷽ʽ     B��ʾweb��W��ʾwap��C��ʾ�ͻ���
		 * @param mobile_code ��¼���ֻ�����
		 * @param cardType    ֧����ʽ
		 * @param transaction_money   ��ֵ���	(��Ϊ��λ)
		 * @param bankId              ���б�ʶ
		 * @param expand              ��չ����
		 * @param sessionid
		 * @return
		 */
      public String phonebankcharge(String accesstype,String mobile_code,String cardType,String transaction_money,String bankId,String expand,String sessionid){
    	  
			while(iretrytimes<3&&iretrytimes>0){
				 re=jrtLot.wapAttemperRequest(accesstype, mobile_code, cardType, transaction_money+"00", bankId, expand, sessionid);
			try {
				obj = new JSONObject(re);
				error_code = obj.getString("error_code");
				PublicMethod.myOutput("---------------------"+error_code);
				iretrytimes=3;
				if(error_code.equals("000000")){
					url=obj.getString("requrl");
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				iretrytimes--;
				PublicMethod.myOutput("---------iretrytimes----------"+iretrytimes);
			}
	
		}
		PublicMethod.myOutput("---------iHttp.whetherChange----------"+iHttp.whetherChange);
//		�³� 8.11 �� ���ı��ж�
		if (iretrytimes == 0 && iHttp.whetherChange == false) {
			iHttp.whetherChange = true;
			if (iHttp.conMethord == iHttp.CMWAP) {
				iHttp.conMethord = iHttp.CMNET;
			} else {
				iHttp.conMethord = iHttp.CMWAP;
			}
			iretrytimes =2;
			phonebankcharge(accesstype, mobile_code,
					cardType, transaction_money, bankId,
					expand, sessionid);
		 }		
    	  return error_code;
      }
      
      /**
  	 * ����ת���˻���ֵ
  	 * @param accesstype    ���뷽ʽ     B��ʾweb��W��ʾwap��C��ʾ�ͻ���
  	 * @param mobile_code   ��¼���ֻ�����
  	 * @param cardType      ֧����ʽ
  	 * @param transaction_money ��ֵ���	(��Ϊ��λ)
  	 * @param totalAmount       ����ֵ
  	 * @param card_no           ����
  	 * @param card_pwd          ������
  	 * @param bankId            ���б�ʶ
  	 * @param expand            ��չ����
  	 * @param sessionid
  	 * @return
  	 */
      public String phonecardcharge(String accesstype,String mobile_code,String cardType,String transaction_money,String totalAmount,String card_no,String card_pwd,String bankId,String expand,String sessionid){
    	  while(iretrytimes<3&&iretrytimes>0){
				 re=jrtLot.attemperRequest(accesstype, mobile_code, cardType, transaction_money+"00",totalAmount+"00", card_no,card_pwd,bankId, expand, sessionid);
			try {
				obj = new JSONObject(re);
				error_code = obj.getString("error_code");
				PublicMethod.myOutput("---------------------"+error_code);
				iretrytimes=3;
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				iretrytimes--;
				PublicMethod.myOutput("---------iretrytimes----------"+iretrytimes);
			}
		}
//		�³� 8.11 �� ���ı��ж�	
		if (iretrytimes == 0 && iHttp.whetherChange == false) {
			iHttp.whetherChange = true;
			if (iHttp.conMethord == iHttp.CMWAP) {
				iHttp.conMethord = iHttp.CMNET;
			} else {
				iHttp.conMethord = iHttp.CMWAP;
			}
			iretrytimes =2;
			phonecardcharge(accesstype, mobile_code,
					cardType, transaction_money,totalAmount,
					card_no, card_pwd, bankId, expand,
					sessionid);
		 }	
    	  return error_code;
      }
}
