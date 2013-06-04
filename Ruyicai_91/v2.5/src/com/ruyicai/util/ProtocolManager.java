package com.ruyicai.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


/**
 *@author haojie
 * Э�������<br>
 * �����Ǵ���ͨѶЭ�鷢�ͣ����յ���װ<br>
 * ͨѶЭ��ʹ��json��ʽ<br>
 * ���������������˼���<br>
 * singleton ģʽ
 */
public class ProtocolManager {
	public final static String RECOMMANDER = "recommander";//
	/**
	 * �Ƿ�ѹ��
	 */
	public final static String ISZIP = "isCompress";
	
	private final static String TAG = "ProtocolManager";
	/**
	 *transactiontype
	 *���˻���ϸ�� ����ʹ��
	 */
	public static String TRANSACTIONTYPE = "transactiontype";
	/**
	 *startdate
	 *���˻���ϸ�� ����ʹ��
	 */
	public static String START_DATE = "startdate";
	/**
	 * bettype
	 * Ͷע�����ͽӿ��е���
	 */
	public static String BETTYPE = "bettype";
	
	/**
	 * enddate
	 * ���˻���ϸ������ʹ��
	 */
	public static String END_DATE = "enddate";
	/**
	 * pageindex
	 * ���˻���ϸ�����Ͳ�ѯ��Ͷע��ѯ���н���ѯ��ʹ��
	 */
	public static String PAGEINDEX = "pageindex";
	/**
	 * type 
	 * ���˻���ϸ������ѯ��Ͷע��ѯ�����Ͳ�ѯ����ѯDNA����ѯ������֡�׷�Ų�ѯ�ӿ�
	 * �н���ѯ��ʹ��
	 */
	public static String TYPE = "type";
	
	
    /**
     * batchcode
     * ��Ͷע�����ͽӿڡ�Ͷע��ѯ�����Ͳ�ѯ���н���ѯ��ʹ��
     */
	public static String BATCHCODE = "batchcode";
	/**
     * batchnum
     * ��Ͷע�����ͽӿڡ�����ʹ��
     */
	public static String BATCHNUM = "batchnum";
	/**
     * bet_code
     * ��Ͷע�����ͽӿڡ�����ʹ��
     */
	public static String BETCODE = "bet_code";
	/**
     * lotno
     * ��Ͷע�����ͽӿڡ�Ͷע��ѯ�����Ͳ�ѯ���н���ѯ��ʹ��
     */
	public static String LOTNO = "lotno";
	/**
     * lotmulti
     * ��Ͷע�����ͽӿڡ�����ʹ��
     */
	public static String LOTMULTI = "lotmulti";
	/**
     * sellway
     * ��Ͷע�����ͽӿڡ�����ʹ��
     */
	public static String SELLWAY = "sellway";
	/**
	 * infoway 
	 * ͨ����ѯͶע
	 */
	public static String INFOWAY = "infoway"; 
	/**
     * amount
     * ��Ͷע�����ͽӿڡ��޸����֡���ʹ��
     */
	public static String AMOUNT = "amount";
	/**
     * to_mobile_code
     * ��Ͷע�����ͽӿڡ�����ʹ��
     */
	public static String TOMOBILECODE = "to_mobile_code";
	
	public static String ISSUPER = "issuperaddition";
	/**
     * advice
     * ��Ͷע�����ͽӿڡ�����ʹ��
     */
	public static String ADVICE = "advice";
	/**
	 * tsubscribeNo
	 * ȡ��׷�š���ʹ��
	 */
	public static String TSUSBSCRIBEID = "tsubscribeNo";
	/**
	 * cashtype
	 * ȡ�����֡��޸����֡���ʹ��
	 */
	public static String CASHTYPE = "cashtype";
	/**
	 * old_pass
	 * �޸����롢��ʹ��
	 */
	public  static   String OLDPASS = "old_pass";
	/**
	 * new_pass
	 * �޸����롢��ʹ��
	 */
	public  static   String NEWPASS = "new_pass";
	/**
	 * araeaname
	 * �޸����֡���ʹ��
	 */
	public static String ARAEANAME = "areaname";
	/**
	 * bankname
	 * �޸����֡���ʹ��
	 */
	public static String BANKNAME = "bankname";
	
	public static String BANKCARDNO = "bankcardno";
	public static String CASHID = "cashdetailid";
	

	/**
	 * statinfo
	 * ������½ӿ���ʹ��
	 */
	public static String GAME_STATINFO = "statinfo";
	/**
	 * downloadurl
	 */
	public static String DOWNLOAD_URL = "downloadurl";
	/**
	 * content
	 * �û������С���ʹ��
	 */
	public static String FEEDBACK_CONTENT = "content";
	/**
	 * password
	 * ��¼�ӿڡ�ע��ӿ���ʹ��
	 */
	public static String PASSWORD = "password";
	
	/**
	 * cardtype
	 * ��ֵ�ӿ���ʹ��
	 */
	public static  String CARDTYPE = "cardtype";
	/**
	 * rechargetype
	 * ��ֵ�ӿ���ʹ��
	 */
	public static  String RECHARGETYPE = "rechargetype";
	/**
	 * cardno
	 * ��ֵ�ӿ���ʹ��
	 */
	public static  String CARDNO = "cardno";
	/**
	 * cardpwd
	 * ��ֵ�ӿ���ʹ��
	 */
	public static  String CARDPWD = "cardpwd";
	/**
	 * name
	 * ��ֵ�ӿڡ�ע��ӿ���ʹ��
	 */
	public static  String NAME = "name";
	public static  String CARD = "ruyicardnumber";
	public static  String CARDPASSWORD = "ruyicardpassword";
	/**
	 * certid
	 * ��ֵ�ӿڡ�ע����ʹ�á����֤��
	 */
	public static  String CERTID = "certid";
	/**
	 * araeaname
	 * ��ֵ�ӿ���ʹ��
	 */
	public static  String ADDRESSNAME = "addressname";
	/**
	 * ÿҳ��ʾ��
	 * �˻���ϸ�����Ͳ�ѯ��Ͷע��ѯ���н���ѯ
	 * maxresult
	 */
	public static  String MAXRESULT = "maxresult";
	/**
	 * ����״̬
	 */
	public static  String STATE = "state";
	public static  String ORDERBY = "orderBy";
	public static  String ORDERDIR = "orderDir";
	public static  String CASEID = "caseid";
	public static  String SAFEAMT = "safeAmt";

	public static String USERNO = "userno";
	public static String SESSIONID = "sessionid";
	public static String PHONE_NUM = "phonenum";
	public static String COMMAND = "command";
	public static String IS_EMULATOR = "isemulator";
	public static String IMEI = "imei";
	public static String IMSI = "imsi";
	public static String SOFTWARE_VERSION = "softwareversion";
	public static String SMS_CENTER = "smscenter";
	public static String COOP_ID = "coopid";
	public static String MACHINE_ID = "machineid";
	public static String PLATFORM = "platform";
    public static String INFORMATION_TYPE = "type";
    public static String ISWHITE="iswhite";
    public static String BANKADDRESS  ="bankaddress";
    /**
     * �������
     */
    public static String TOTALAMT  ="totalAmt";
    public static String ONEAMOUNT  ="oneAmount";
    public static String BUYAMT  ="buyAmt";
    public static String RATION  ="commisionRation";
    public static String VISIBILITY  ="visibility";
    public static String MINAMT  ="minAmt";
    public static String DESCRIPTION  ="description";
	
	//����Ϊ��Ƶ��
	public static String HIGHFREQENCYTPYE = "highFrequency";
	
	private static JSONObject defaultJsonObject  = null;
	
	private static ProtocolManager protocolManager = null;
	private ProtocolManager() {
		
	}
	
	public synchronized static ProtocolManager getInstance(){
		if(protocolManager == null){
			protocolManager = new ProtocolManager();			
		}
		defaultJsonObject = new JSONObject();
		return protocolManager;
	}
	/**
	 * ��ȡ��userno,sessionid,phonenum��������(imsi,imei,softwareversion,machineid,coopid,smscenter,platform)
	 * @return ���й���������jsonobject
	 */
    public JSONObject getDefaultJsonProtocol(){
    	try {
    		if(Constants.IMEI == null){
    			defaultJsonObject.put(IMEI, "");
			}else{
				defaultJsonObject.put(IMEI, Constants.IMEI);
			}
			if(Constants.IMSI == null){
				defaultJsonObject.put(IMSI, "");
			}else{
			    defaultJsonObject.put(IMSI, Constants.IMSI);
			}
			defaultJsonObject.put(SOFTWARE_VERSION, Constants.SOFTWARE_VERSION);
			defaultJsonObject.put(MACHINE_ID, Constants.MACHINE_ID);
			defaultJsonObject.put(COOP_ID, Constants.COOP_ID);
			defaultJsonObject.put(SMS_CENTER, Constants.SMS_CENTER);
			defaultJsonObject.put(PLATFORM, Constants.PLATFORM_ID);
			defaultJsonObject.put(ISZIP, Constants.ISCOMPRESS);
			
		} catch (JSONException e) {
			Log.e(TAG, "��ȡ��������������");
		}
    	return defaultJsonObject;
    }
}
