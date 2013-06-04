package com.palmdream.netintface;


import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid.PublicMethod;

public class BettingInterface {
     /**
      * @param amount ���ʽ��ȷ��
      * @param String term_begin_datetime ��������
      * @param String deposit_sum  �˻����
      * @param String  sell_term_code��Ʊ����
      * @param bets ע��
      * @param bets_zhu_num ע��
      * @param sessionid ��¼��
      */
	
	
	String error_code="00";
	int iretry=2;
	String amount;//���ʽ��
	String term_begin_datetime;//��������
	String deposit_sum;//�˻����
	String  sell_term_code;//��Ʊ����
	String error_code_DYJ="00";
	 public  String Betting(String login_User,String bets, String bets_zhu_num, String sessionid){
  	  
  	   JSONObject obj = null;
		while(iretry<3&&iretry>0){
			try {
		    String re=jrtLot.betting( login_User,bets, Integer.parseInt(bets_zhu_num)*200+"", sessionid);
			obj = new JSONObject(re);
		    error_code=obj.getString("error_code");	
			PublicMethod.myOutput("---------"+error_code);
			if(error_code.equals("0000")){
				amount=obj.getString("amount");
				term_begin_datetime=obj.getString("term_begin_datetime");
				deposit_sum=obj.getString("deposit_sum");
				sell_term_code=obj.getString("sell_term_code");
			}
			iretry=3;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			iretry--;
			
		  }
		}
		if (iretry == 0 && iHttp.whetherChange == false) {
			iHttp.whetherChange = true;
			if (iHttp.conMethord == iHttp.CMWAP) {
				iHttp.conMethord = iHttp.CMNET;
			} else {
				iHttp.conMethord = iHttp.CMWAP;
			}
			Betting(login_User,  bets, bets_zhu_num,
					sessionid);
		}
			return error_code;
			
     }
// Ͷע�½ӿ� 20100711 �³�
	 /**
	  * Ͷע�½ӿ�
      * @param bet_code ע��
      * @param count ����
      * @param amount  Ͷע�ܽ��
      * @param sessionid ��¼��
      */
				
	public String BettingNew(String bet_code, String count, String amount,
			String sessionid) {

		JSONObject obj = null;

		while (iretry < 3 && iretry > 0) {
			try {
				String re = jrtLot.addNumber(bet_code, count, amount, "Y",
						sessionid);
				obj = new JSONObject(re);
				error_code = obj.getString("error_code");
				PublicMethod.myOutput("---------" + error_code);
				// if(error_code.equals("0000")){
				// amount=obj.getString("amount");
				// term_begin_datetime=obj.getString("term_begin_datetime");
				// deposit_sum=obj.getString("deposit_sum");
				// sell_term_code=obj.getString("sell_term_code");
				// }
				iretry = 3;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				iretry--;

			}
		}
//		�����Ƿ�ı�������ж� �³� 8.11
		if (iretry == 0 && iHttp.whetherChange == false) {
			iHttp.whetherChange = true;
			if (iHttp.conMethord == iHttp.CMWAP) {
				iHttp.conMethord = iHttp.CMNET;
			} else {
				iHttp.conMethord = iHttp.CMWAP;
			}
			iretry=2;
			BettingNew(bet_code,count, amount,
					sessionid);
		}
		return error_code;

	}
	// ���Ͷע�ӿ� 20100712 �³�
	public String[] BettingTC(String mobileCode,String lotNo,String betCode, String lotMulti, String amount,String oneMoney,String batchnum,String sessionid) {

		JSONObject obj = null;
		String error_code = "00";
		String error_code_DYJ="00";
		while (iretry < 3 && iretry > 0) {
			try {
//				String re = jrtLot.addNumQueryTC(sessionid, mobileCode, startLine, stopLine)(mobileCode, lotNo, betCode, lotMulti, amount+"00", oneMoney,batchnum,sessionid);
				String re = jrtLot.bettingTC(mobileCode, lotNo, betCode, lotMulti, amount+"00", oneMoney,batchnum,sessionid);
				obj = new JSONObject(re);
				error_code = obj.getString("error_code");
				error_code_DYJ = obj.getString("error_code_DYJ");
				PublicMethod.myOutput("---------" + error_code);
				iretry = 3;
			} catch (JSONException e) {
				if(error_code.equals("00")&&obj!=null){
					try {
						error_code_DYJ = obj.getString("error_code_DYJ");
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				PublicMethod.myOutput("---------" + error_code+"-----error_code_DYJ--"+error_code_DYJ);
				e.printStackTrace();
				if(error_code.equalsIgnoreCase("00")&&error_code_DYJ.equalsIgnoreCase("00")){
				  iretry--;
				}else{
					iretry = 3;
				}

			}
		}
//		�����Ƿ�ı�������ж� �³� 8.12
		if (iretry == 0 && iHttp.whetherChange == false) {
			iHttp.whetherChange = true;
			if (iHttp.conMethord == iHttp.CMWAP) {
				iHttp.conMethord = iHttp.CMNET;
			} else {
				iHttp.conMethord = iHttp.CMWAP;
			}
			iretry=2;
			BettingTC(mobileCode,lotNo,betCode, lotMulti, amount,oneMoney,batchnum,sessionid);
		}
		iretry =2;
		String[] errorCode={error_code,error_code_DYJ};
		return errorCode;

	}

	     
	
}
