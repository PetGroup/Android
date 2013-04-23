package com.ruyicai.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Constants {

	public static final String APPNAME = "RUYICAI";
	
//    public static final String SERVER_URL = "http://219.148.162.70:8000/jrtLot/";
	public static final String SERVER_URL = "http://www.ruyicai.com/jrtLot/";
    //public static final String SERVER_URL = "http://219.148.162.68/jrtLot/";
	public static String LOT_SERVER = "http://www.ruyicai.com/lotserver/RuyicaiServlet";
//	public static String MSG_URL = "http://219.148.162.68/wirelessSupport/";

	// public static String LOT_SERVER =
	// "http://192.168.0.191/lotserver/RuyicaiServlet";//���Է�����

	public static String sessionId = "";

	public static final String KEY = "<>hj12@#$$%^~~ff";

	public static String IMEI = "";
	public static String IMSI = "";
	public static String SOFTWARE_VERSION = "2.3.2.1";
	public static String SMS_CENTER = "";
//	public static String COOP_ID = "533";
//	public static String COOP_ID = "535";//��Ѷ�ĺ����ƹ�
//	public static String COOP_ID = "383";//���a
//	public static String COOP_ID = "371";//����a
//	public static String COOP_ID = "497";//��Ȥa
//	public static String COOP_ID = "450";//coolmart
//	public static String COOP_ID = "370";//ľ����
//	public static String COOP_ID = "534";//web����
//	public static String COOP_ID = "533";//wap����
//	public static String COOP_ID = "232";//tompda-android
//	public static String COOP_ID = "238";//�ݽ�android
//	public static String COOP_ID = "208";//�����г�
//	public static String COOP_ID = "293";//�滢360andriod
//	public static String COOP_ID = "184";//ŷ�ְ�
//	public static String COOP_ID = "166";//������
//	public static String COOP_ID = "151";//��׿�г�
//	public static String COOP_ID = "279";//����Ӧ�û�
//	public static String COOP_ID = "278";//����A
//	public static String COOP_ID = "291";//������
//	public static String COOP_ID = "537";//ruyicai-update
	public static String COOP_ID = "304";//n����
	public static String MACHINE_ID = "";
	public static String PLATFORM_ID = "android";

	// ��¼���û����
	public static String LOGIN_USER_BALANCE = "";
	// ���Ϊ0�Ƿ���ʾ��ʾ��
//	public static boolean BALANCE_SHOW = true;
//
//	public static boolean ACCOUT_SHOW = false;

	// ������Ϣlist
	public static List<JSONObject> ssqNoticeList = new ArrayList<JSONObject>();
	public static List<JSONObject> fc3dList = new ArrayList<JSONObject>();
	public static List<JSONObject> qlcList = new ArrayList<JSONObject>();
	public static List<JSONObject> pl3List = new ArrayList<JSONObject>();
	public static List<JSONObject> dltList = new ArrayList<JSONObject>();
	public static List<JSONObject> sscList = new ArrayList<JSONObject>();
	public static List<JSONObject> sfcList = new ArrayList<JSONObject>();
	public static List<JSONObject> rx9List = new ArrayList<JSONObject>();
	public static List<JSONObject> half6List = new ArrayList<JSONObject>();
	public static List<JSONObject> jqcList = new ArrayList<JSONObject>();

	public static long lastNoticeTime = 0;
	public static final long NOTICE_CACHE_TIME_SECOND = 120;// ������Ϣ����60��,�������60��������������ȡ����

	public static JSONObject currentLotnoInfo = new JSONObject();

	public static final String LOTNO_SSQ = "F47104"; // ˫ɫ��
	public static final String LOTNO_QLC = "F47102"; // ���ֲ�
	public static final String LOTNO_FC3D = "F47103"; // ����3D

	public static final String LOTNO_11_5 = "T01010"; // 11ѡ5
	public static final String LOTNO_SSC = "T01007"; // ʵʱ��
	public static final String LOTNO_DLT = "T01001"; // ����͸
	public static final String LOTNO_PL3 = "T01002"; // ������
	public static final String LOTNO_QXC = "T01009"; // ���ǲ�
	public static final String LOTNO_PL5 = "T01011"; // ������

	public static final String LOTNO_JQC = "T01005"; // �����
	public static final String LOTNO_LCB = "T01006"; // ���������
	public static final String LOTNO_SFC = "T01003"; // ���ʤ����
	public static final String LOTNO_RX9 = "T01004"; // �����ѡ9

	public static String NEWS = ""; // �ͻ�����ҳ��ʾ����Ϣ

	public static int SCREEN_WIDTH = 0;
	public static int SCREEN_HEIGHT = 0;
    public static int SCREEN_DENSITYDPI=0;
    public static float SCREEN_DENSITY=0f;

	public static final String GAME_CLASS = "RUYICAI_GAME_CLASS";

	public static final String GAME_CLICK_SUM = "GAME_CLICK_SUM";

	public static final int STAT_INFO_CACHE_NUM = 10;
	
	public final  static  String ACCOUNTRECHARGE_EIXT_TYPE="ACCOUNTRECHARGE_EIXT_TYPE_CHANGE";
	
	public static int  RUYIHELPERSHOWLISTTYPE=0;
}
