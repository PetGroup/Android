package com.ruyicai.constant;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;

import com.ruyicai.activity.info.LotInfoDomain;
/**
 * ��̬����������
 * @author Administrator
 *
 */   
public class Constants {             
   
	public static final String APPNAME = "RUYICAI";	
	public static  int MEMUTYPE= 0;
	


	public static final String SERVER_URL = "http://www.ruyicai.com/jrtLot/";//jrtLot��ʽ��
//    public static final String SERVER_URL = "http://192.168.0.118:8080/jrtLot/";//����  

	public static String LOT_SERVER = "http://www.ruyicai.com/lotserver/RuyicaiServlet";//��ʽ��
//	public static String LOT_SERVER = "http://202.43.151.10:8080/lotserver/RuyicaiServlet";//������/

//	public static String LOT_SERVER = "http://192.168.0.171:8080/lotserver/RuyicaiServlet";//������/
	public final static String server_url = "https://msp.alipay.com/x.htm";//֧�������֧��server_url
	public static final String ALIPAY_PLUGIN_NAME = "alipay_plugin223_0309.apk";	
//	public static String LOT_SERVER = "http://192.168.0.114:8000/lotserver/RuyicaiServlet";//����
	/**
	 * ���°���Ҫ�޸�
	 */
	public static String SOFTWARE_VERSION = "3.3.0.1";	
	public static String MERCHANT_PACKAGE = "com.palmdream.RuyicaiAndroid";//�̻�����
	/**
	 * ���˷����޸�
	 */
	public static String CONSUMER_KEY = "2143826468";// �滻Ϊ�����ߵ�appkey������"1646212960";
	public static String CONSUMER_SECRET = "f3199c4912660f1bcbdee7cfc37c636e";// �滻Ϊ�����ߵ�appkey����
	public static String CONSUMER_URL = "http://wap.ruyicai.com/w/client/download.jspx";
	/**
	 * ���˷����޸�
	 */
	public static String apiKey = "bd3277851a9941fe8b223d152cf7696a";
	public static String secret = "ab29383377224ae99fcd9b1654aebbed";
	public static String appId = "166208";

	public static String COOP_ID = "643";//�����г�
//	public static String COOP_ID = "641";//��׿�г�
//	public static String COOP_ID = "533";//wap����
//	public static String COOP_ID = "790";//���Ե�1
//	public static String COOP_ID = "791";//���Ե�2
//	public static String COOP_ID = "792";//���Ե�3
//	public static String COOP_ID = "535";//��Ѷ
//	public static String COOP_ID = "788";//����1
//	public static String COOP_ID = "789";//����2
//	public static String COOP_ID = "184";//ŷ�˰�
//	public static String COOP_ID = "764";//91��׿
//	public static String COOP_ID = "238";//�ݽ�
//	public static String COOP_ID = "208";//�����г�
//	public static String COOP_ID = "561";//��������
//	public static String COOP_ID = "625";//������
//	public static String COOP_ID = "652";//������
//	public static String COOP_ID = "655";//��ô�г�
//	public static String COOP_ID = "676";//֪����
//	public static String COOP_ID = "741";//ǧ��1
//	public static String COOP_ID = "743";//ǧ��2
//	public static String COOP_ID = "497";//��Ȥ
//	public static String COOP_ID = "806";//һ��
	
	
	
	public static boolean hasLogin = false;//�û��Ƿ��Ѿ���¼��ʾ
	public static String sessionId = "";
	public static final String KEY = "<>hj12@#$$%^~~ff";
	public static final String  NOTIFICATION_MARKS = "marks";
	public static String IMEI = "";
	public static String ISCOMPRESS = "1";//ѹ���������ֵ 
	public static String SMS_CENTER = "";
	public static String DRAWBALANCE = "drawbalance";
	
	public static String MACHINE_ID = "";
	public static String PLATFORM_ID = "android";
	public static boolean isProxyConnect = false;   //�Ƿ�������������
	public static String mProxyHost;               //�����������ַ
	public static int mProxyPort = 0;              //����������˿�
	
	//���ÿ������ĺ�̨������ʱ�����ڣ���λ ms��
	public static int PRIZECIRCLETIME = 10*60*1000;
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
	public static List<JSONObject> ydjList = new ArrayList<JSONObject>();
	public static List<JSONObject> twentylist = new ArrayList<JSONObject>();
	public static List<JSONObject> dlcList = new ArrayList<JSONObject>();
	public static List<JSONObject> pl5List = new ArrayList<JSONObject>();
	public static List<JSONObject> qxcList = new ArrayList<JSONObject>();
	public static String noticeJcz = "" ;
	public static String noticeJcl = "" ;
	
	public static  boolean isInitTop = false;//����mainGroup�Ƿ�initTop�����û����Զ���¼ʱisInitTop = true��mainGroup����initTop�����򡭡�
	//������Ϣjson
	public static JSONObject noticAllJson = null;
	public static JSONObject ssqJson = null;
	public static JSONObject fc3dJson =  null;
	public static JSONObject qlcJson =  null;
	public static JSONObject pl3Json =  null;
	public static JSONObject dltJson =  null;
	public static JSONObject sscJson =  null;
	public static JSONObject sfcJson =  null;
	public static JSONObject rx9Json =  null;
	public static JSONObject half6Json =  null;
	public static JSONObject jqcJson =  null;
	public static JSONObject ydjJson =  null;
	public static JSONObject twentyJson = null;
	public static JSONObject dlcJson =  null;
	public static JSONObject pl5Json =  null;
	public static JSONObject qxcJson =  null;
	//�û�����
	public static String feedBackData = "";
	public static JSONArray feedBackJSONArray = null;
	//�н�����List
	public static   String prizeRankJSON = "";
	
	//�û���������
	public static String shareContent = "Hi���Ҹ�ʹ����������ֻ��ͻ������Ʊ���ܷ����أ�" +
			"��Ҳ���԰ɣ���Ʊ����Ͷ����ʱʱ�У��н��˼ǵ�Ҫ��Ͱ������ص�ַ:http://wap.ruyicai.com/w/clientContrallor/clientDown.jspx";
	

	// ��Ʊ��Ѷ List
	
	public static List<LotInfoDomain> quwenInfoList = new ArrayList<LotInfoDomain>();//��ƱȤ��
	public static List<LotInfoDomain> zhuanjiaInfoList = new ArrayList<LotInfoDomain>();//ר�ҷ���
	public static List<LotInfoDomain> footballInfoList = new ArrayList<LotInfoDomain>();//������
	public static List<LotInfoDomain> huodongInfoList = new ArrayList<LotInfoDomain>();//����
	//������Ϣ�����ɶrepreference�еı�ʾ
	public static String[] orderPrize = {"orderSSQ","orderFC3D","orderQLC","orderDLT","orderQXC","orderPL3","orderPL5","order225"};
	
	public static long lastNoticeTime = 0;
	public static final long NOTICE_CACHE_TIME_SECOND = 120;// ������Ϣ����60��,�������60��������������ȡ����

	public static JSONObject currentLotnoInfo = new JSONObject();
               
	public static final String LOTNO_SSQ = "F47104"; // ˫ɫ��
	public static final String LOTNO_QLC = "F47102"; // ���ֲ�
	public static final String LOTNO_FC3D = "F47103"; // ����3D
	
	public static final String LOTNO_eleven = "T01012"; // 11�˶��
	public static final String LOTNO_11_5 = "T01010"; // 11ѡ5
	public static final String LOTNO_SSC = "T01007"; // ʱʱ��
	public static final String LOTNO_DLT = "T01001"; // ����͸
	public static final String LOTNO_PL3 = "T01002"; // ������
	public static final String LOTNO_QXC = "T01009"; // ���ǲ�
	public static final String LOTNO_PL5 = "T01011"; // ������
	public static final String LOTNO_JC= "J00001"; // ����

	public static final String LOTNO_JQC = "T01005"; // �����
	public static final String LOTNO_LCB = "T01006"; // ���������
	public static final String LOTNO_SFC = "T01003"; // ���ʤ����
	public static final String LOTNO_RX9 = "T01004"; // �����ѡ9
    public static final String LOTNO_22_5= "T01013"; //���22ѡ5
	public static final String LOTNO_JCZQ = "J00001"; // ��������ʤ��
	public static final String LOTNO_JCZQ_ZQJ = "J00003"; // ���������ܽ���
	public static final String LOTNO_JCZQ_BF = "J00002"; // ��������ȷ�
	public static final String LOTNO_JCZQ_BQC = "J00004"; // ���������ȫ��
	public static final String LOTNO_JCLQ = "J00005"; // ��������ʤ��
	public static final String LOTNO_JCLQ_RF = "J00006"; // ���������÷�ʤ��
	public static final String LOTNO_JCLQ_SFC = "J00007"; // ��������ʤ�ֲ�
	public static final String LOTNO_JCLQ_DXF = "J00008"; // ���������С��
	public static final String LOTNO_JCL = "JC_L";//�����ѯ�в�ѯ���о�������ı�ʾ
	public static final String LOTNO_JCZ = "JC_Z";//�����ѯ�в�ѯ���о�������ı�ʾ
	
	public static final int SSC_TWOSTAR_ZHIXUAN=1;
	public static final int SSC_TWOSTAR_ZUXUAN=2;
	public static final int SSC_TWOSTAR_HEZHI=3;
	public static final int SSC_FIVESTAR_ZHIXUAN=4;
	public static final int SSC_FIVESTAR_TONGXUAN=5;
	
	/**
	 * �н����е�һЩ��ʾ
	 */
	public static final String  WEEK = "weekArray";
	public static final String  MONTH = "monthArray";
	public static final String  TOTAL = "totalArray";
	
	public static final String SSQLABEL = "ssq";
	public static final String FC3DLABEL = "fc3d";
	public static final String QLCLABEL = "qlc";
	public static final String PL3LABEL = "pl3";
	public static final String PL5LABEL = "pl5";
	public static final String QXCLABEL = "qxc";
	public static final String DLTLABEL = "cjdlt";
	public static final String SSCLABEL = "ssc";
	public static final String DLCLABEL = "11-5";
	public static final String YDJLABEL = "11-ydj";
	public static final String TWENTYBEL ="22-5";
	public static final String SFCLABEL = "sfc";
	public static final String RXJLABEL = "rxj";
	public static final String LCBLABEL = "lcb";
	
	/**
	 * ��������TYpe��ʾ
	 */
	public static final String JCFOOT = "1";
	/**
	 * ��������Type��ʾ
	 */
	public static final String JCBASKET = "0";
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
	public static String IMSI;
	
}
