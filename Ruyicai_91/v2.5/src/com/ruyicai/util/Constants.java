package com.ruyicai.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.ruyicai.activity.info.LotInfoDomain;

import android.graphics.Bitmap;
/**
 * ��̬����������
 * @author Administrator
 *
 */
public class Constants {         
   
	public static final String APPNAME = "RUYICAI";
	public static  int MEMUTYPE= 0;
//    public static final String SERVER_URL = "http://219.148.162.70:8000/jrtLot/";  
	public static final String SERVER_URL = "http://www.ruyicai.com/jrtLot/";
//	public static final String SERVER_URL = "http://192.168.0.114:8000/lotserver";
//	public static String LOT_SERVER = "http://www.ruyicai.com/lotserver/RuyicaiServlet";
	public static String LOT_SERVER = "http://219.148.162.70:9080/lotserver/RuyicaiServlet";
//	public static String LOT_SERVER = "http://192.168.0.114:8000/lotserver/RuyicaiServlet";//����
	public static String sessionId = "";
	public static final String KEY = "<>hj12@#$$%^~~ff";
	public static final String  NOTIFICATION_MARKS = "marks";
	public static String IMEI = "";
	public static String IMSI = "";
	public static String ISCOMPRESS = "1";//ѹ���������ֵ 
	public static String SOFTWARE_VERSION = "2.5.1.91";	
	public static String SMS_CENTER = "";
	public static String DRAWBALANCE = "drawbalance";
	public static String SHELLRWPHONE = "phonenum";//sharepreference�еĵ绰����
	public static String SHELLRWSESSIONID = "sessionid";//sharepreference�е�sessionid
	public static String SHELLRWUSERNO = "userno";//sharepreference�е�userno
//	public static String COOP_ID = "304";//n����
//	public static String COOP_ID = "545";//n����
//	public static String COOP_ID = "232";//tompda
//	public static String COOP_ID = "208";//�����г�
//	public static String COOP_ID = "497";//��Ȥ
//	public static String COOP_ID = "373";//����
//	public static String COOP_ID = "238";//�ݽ�
//	public static String COOP_ID = "383";//���
//	public static String COOP_ID = "166";//������
//	public static String COOP_ID = "543";//�Ѻ�
//	public static String COOP_ID = "625";//������
//	public static String COOP_ID = "279";//����Ӧ�û�
//	public static String COOP_ID = "370";//ľ����
//	public static String COOP_ID = "450";//coolmart
//	public static String COOP_ID = "184";//ŷ�˰�
//	public static String COOP_ID = "535";//qqtencent
//	public static String COOP_ID = "534";//wab����
//	public static String COOP_ID = "533";//wap����
//	public static String COOP_ID = "537";//��������
	public static String COOP_ID = "545";//91
//	public static String COOP_ID = "208";//eoe
//	public static String COOP_ID = "497";//liqu
//	public static String COOP_ID = "373";//dangle
//	public static String COOP_ID = "238";//�ݽ�
//	public static String COOP_ID = "383";//softbar
//	public static String COOP_ID = "370";//mumayi
//	public static String COOP_ID = "279";//zhangshang
//	public static String COOP_ID = "625";//anfeng
//	public static String COOP_ID = "543";//sohu
//	public static String COOP_ID = "166";//gfan
//	public static String COOP_ID = "561";//feiliu
	public static String MACHINE_ID = "";
	public static String PLATFORM_ID = "android";
	
	public static boolean isProxyConnect = false;   //�Ƿ�������������
	public static String mProxyHost;               //�����������ַ
	public static int mProxyPort = 0;              //����������˿�
	
	
	// ��¼���û����
	public static String LOGIN_USER_BALANCE = "";

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
	public static List<JSONObject> dlcList = new ArrayList<JSONObject>();

	// ��Ʊ��Ѷ List
	
	public static List<LotInfoDomain> quwenInfoList = new ArrayList<LotInfoDomain>();//��ƱȤ��
	public static List<LotInfoDomain> zhuanjiaInfoList = new ArrayList<LotInfoDomain>();//ר�ҷ���
	public static List<LotInfoDomain> footballInfoList = new ArrayList<LotInfoDomain>();//������
	public static List<LotInfoDomain> huodongInfoList = new ArrayList<LotInfoDomain>();//����
	
	
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
	
	public static final int SSC_TWOSTAR_ZHIXUAN=1;
	public static final int SSC_TWOSTAR_ZUXUAN=2;
	public static final int SSC_TWOSTAR_HEZHI=3;
	public static final int SSC_FIVESTAR_ZHIXUAN=4;
	public static final int SSC_FIVESTAR_TONGXUAN=5;

	public static String NEWS = ""; // �ͻ�����ҳ��ʾ����Ϣ

	public static int SCREEN_WIDTH = 0;
	public static int SCREEN_HEIGHT = 0;
    public static int SCREEN_DENSITYDPI=0;
    public static float SCREEN_DENSITY=0f;

	public static final String GAME_CLASS = "RUYICAI_GAME_CLASS";

	public static final String GAME_CLICK_SUM = "GAME_CLICK_SUM";

	public static final int STAT_INFO_CACHE_NUM = 10;
	
	public static final String ACCOUNTRECHARGE_EIXT_TYPE="ACCOUNTRECHARGE_EIXT_TYPE_CHANGE";
	
	public static final int  RUYIHELPERSHOWLISTTYPE=0;
	
	public static Bitmap grey = null;
	public static Bitmap red = null;
	public static Bitmap blue = null;
	public static int PADDING= 40;
	public static String avdiceStr[] = null;
	
}
