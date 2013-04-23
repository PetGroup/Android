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
	 public  String BettingNew(String bet_code,String count ,String amount ,String sessionid){
	  	  
	  	   JSONObject obj = null;
			while(iretry<3&&iretry>0){
				try {
			    String re=jrtLot.addNumber(bet_code,count, amount, "Y", sessionid);
				obj = new JSONObject(re);
			    error_code=obj.getString("error_code");	
				PublicMethod.myOutput("---------"+error_code);
			//	if(error_code.equals("0000")){
				//	amount=obj.getString("amount");
			//		term_begin_datetime=obj.getString("term_begin_datetime");
			//		deposit_sum=obj.getString("deposit_sum");
			//		sell_term_code=obj.getString("sell_term_code");
			//	}
				iretry=3;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				iretry--;
				
			  }
			}
				return error_code;
				
	     }
	
}
