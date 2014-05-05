package com.ruyicai.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Environment;

import com.ruyicai.activity.info.LotInfoDomain;

/**
 * 静态常量参数类
 * 
 * @author Administrator
 * 
 */
public class Constants {

	public static final String APPNAME = "RUYICAI";
	public static int MEMUTYPE = 0;

	/* Add by fansm 20130412 start */
	/* debug mode */
	public static boolean isDebug = true;
	public static String TAG = "RUYICAI";
	/* Add by fansm 20130412 end */

//	public static String LOT_SERVER = "http://www.ruyicai.com/lotserver/RuyicaiServlet";// 正式线
	public static String LOT_SERVER = "http://202.43.152.173:8099/lotserver/RuyicaiServlet";// 测试线
//	public static String LOT_SERVER = "http://192.168.0.118:80/lotserver/RuyicaiServlet";// 测试线
	/**
	 * mMode参数解释：
	 * "00" - 启动银联正式环境
	 * "01" - 连接银联测试环境
	 */
    public static String BANCK_Mode = "00";
    /**
  	 * 检验彩种设置信息
  	 */
  	public static List<Map<String, String>> shellRWList;
	/**
	 * 彩种设置
	 * 
	 */
	public static final String CAIZHONG_OPEN = "on";
	public static final String CAIZHONG_CLOSE = "off";
	public static final String RYJC_LAST_STATE = "ryjc_last_state";
	/**
	 * 支付宝
	 */
	public final static String server_url = "https://msp.alipay.com/x.htm";// 支付宝快捷支付server_url
	public static final String ALIPAY_PLUGIN_NAME = "alipay_plugin223_0309.apk";
	public static final String sinaweibo = "https://api.weibo.com/2/users/show.json";// 新浪微博获取昵称
	/**
	 * 打新包需要修改
	 */
	public static String SOFTWARE_VERSION = "4.2.4";
	public static String MERCHANT_PACKAGE = "com.palmdream.RuyicaiAndroid";// 商户包名
	/**
	 * 新浪分享修改
	 */
	public static String CONSUMER_KEY = "2143826468";// 替换为开发者的appkey，例如"1646212960";
	public static String CONSUMER_SECRET = "f3199c4912660f1bcbdee7cfc37c636e";// 替换为开发者的appkey，例
	public static String CONSUMER_URL = "http://wap.ruyicai.com/w/client/download.jspx";
//	public static String SCOPE = 
//	            "email,direct_messages_read,direct_messages_write,"
//	                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
//	                    + "follow_app_official_microblog," + "invitation_write";
	/**
	 * 腾讯微博
	 */
	public static String kAppKey = "801184275";
	public static long kAppKey1 = 801184275;
	public static String kAppSecret = "3439c0843c81965196b165b09bb6edf3";
	public static String kAppRedirectURI = "http://www.ruyicai.com";
	/**
	 * 微信
	 */
//	public static final String APP_ID = "wxeda3b3b79897e78e"; //old
	public static final String APP_ID = "wx165e130157b9e135"; //20140328 新生成的
	

	public static boolean hasLogin = false;// 用户是否已经登录标示
	public static String sessionId = "";
	public static final String KEY = "<>hj12@#$$%^~~ff";
	public static final String NOTIFICATION_MARKS = "marks";
	public static String IMEI = "";
	public static String ISCOMPRESS = "1";// 压缩请求参数值

	public static Bitmap grey_long = null;
	public static Bitmap red_long = null;

	public static String PHONE_SIM = "";
	public static String DRAWBALANCE = "drawbalance";

	public static String MACHINE_ID = "";
	public static String PLATFORM_ID = "android";
	public static boolean isProxyConnect = false; // 是否代理服务器联网
	public static String mProxyHost; // 代理服务器地址
	public static int mProxyPort = 0; // 代理服务器端口

	// 设置开奖订阅后台联网的时间周期（单位 ms）
	public static int PRIZECIRCLETIME = 10 * 60 * 1000;
	// 登录的用户余额
	public static String LOGIN_USER_BALANCE = "";

	public static String type = "";
	public static String UMPAY_CHANNEL_ID = "";

	// 开奖信息list
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
	public static List<JSONObject> gd115List = new ArrayList<JSONObject>();
	public static List<JSONObject> gd10List = new ArrayList<JSONObject>();
	public static List<JSONObject> nmk3List = new ArrayList<JSONObject>();
	public static List<JSONObject> cq11x5List = new ArrayList<JSONObject>();
	public static List<JSONObject> jlk3List = new ArrayList<JSONObject>();
	public static List<JSONObject> klpkList = new ArrayList<JSONObject>();
	public static String noticeJcz = "";
	public static String noticeJcl = "";

	public static boolean isInitTop = false;// 设置mainGroup是否initTop，当用户是自动登录时isInitTop
											// = true，mainGroup界面initTop；否则……
	// 开奖信息json
	public static JSONObject noticAllJson = null;
	public static JSONObject ssqJson = null;
	public static JSONObject fc3dJson = null;
	public static JSONObject qlcJson = null;
	public static JSONObject pl3Json = null;
	public static JSONObject dltJson = null;
	public static JSONObject sscJson = null;
	public static JSONObject sfcJson = null;
	public static JSONObject rx9Json = null;
	public static JSONObject half6Json = null;
	public static JSONObject jqcJson = null;
	public static JSONObject ydjJson = null;
	public static JSONObject twentyJson = null;
	public static JSONObject dlcJson = null;
	public static JSONObject pl5Json = null;
	public static JSONObject qxcJson = null;
	public static JSONObject gd115Json = null;
	public static JSONObject gd10Json = null;
	public static JSONObject nmk3Json = null;
	public static JSONObject cq11x5Json = null;
	public static JSONObject jlk3Json=null;
	public static JSONObject hlpkJson=null;

	public static JSONObject todayjosn = null;
	// 用户反馈
	public static String feedBackData = "";
	public static JSONArray feedBackJSONArray = null;
	// 中奖排行List
	public static String prizeRankJSON = "";
	public static String buydataJSON = "";

	// 用户分享内容
	public static String shareContent = "Hi，我刚使用了如意彩手机客户端买彩票，很方便呢！"
			+ "你也试试吧，彩票随身投，大奖时时有！中奖了记的要请客啊！下载地址:http://wap.ruyicai.com/w/client/download.jspx";

	public static String source = "3";// source String 微博分享类型 可选
										// 1:新闻;2:开奖;3:下载地址;4:合买;5:发起合买

	// 彩票资讯 List

	public static List<LotInfoDomain> quwenInfoList = new ArrayList<LotInfoDomain>();// 彩票趣闻
	public static List<LotInfoDomain> zhuanjiaInfoList = new ArrayList<LotInfoDomain>();// 专家分析
	public static List<LotInfoDomain> footballInfoList = new ArrayList<LotInfoDomain>();// 足彩天地
	public static List<LotInfoDomain> huodongInfoList = new ArrayList<LotInfoDomain>();// 如意活动

//	public static List<Class> turnLotnoList = new ArrayList<Class>();
	public static final String NOTIFICATIONTURNFLAG = "notificationturnflag";
	public static final String NOTIFICATIONTURNLOTNO = "notificationturnlotno";
	// 订阅信息存放在啥repreference中的标示
	public static String[] orderPrize = { "orderSSQ", "orderFC3D", "orderQLC",
			"orderDLT", "orderQXC", "orderPL3", "orderPL5", "order225" };

	public static long lastNoticeTime = 0;
	public static final long NOTICE_CACHE_TIME_SECOND = 120;// 开奖信息缓存60秒,如果超过60秒则重新联网获取数据

	public static JSONObject currentLotnoInfo = new JSONObject();

	public static final String LOTNO_SSQ = "F47104"; // 双色球
	public static final String LOTNO_QLC = "F47102"; // 七乐彩
	public static final String LOTNO_FC3D = "F47103"; // 福彩3D
	public static final String LOTNO_GD115 = "T01014"; // 广东11-5

	public static final String LOTNO_eleven = "T01012"; // 11运夺金
	public static final String LOTNO_ten = "T01015"; // 快乐十分
	public static final String LOTNO_11_5 = "T01010"; // 11选5
	public static final String LOTNO_GD_11_5 = "T01014"; // 广东11选5
	public static final String LOTNO_SSC = "T01007"; // 时时彩
	public static final String LOTNO_DLT = "T01001"; // 大乐透
	public static final String LOTNO_PL3 = "T01002"; // 排列三
	public static final String LOTNO_QXC = "T01009"; // 七星彩
	public static final String LOTNO_PL5 = "T01011"; // 排列五
	public static final String LOTNO_JC = "J00001"; // 竞彩
	public static final String LOTNO_NMK3 = "F47107";// 内蒙快三
	public static final String LOTNO_BJ_SINGLE = "BD";// 北京单场
	public static final String LOTNO_CQ_ELVEN_FIVE = "T01016";// 重庆11选五
	public static final String LOTNO_JLK3 = "F47108";//吉林快三
	public static final String LOTNO_HAPPY_POKER="T01020";//快乐扑克

	public static final String LOTNO_ZC = "ZC"; // 进球彩
	public static final String LOTNO_JQC = "T01005"; // 进球彩
	public static final String LOTNO_LCB = "T01006"; // 足彩六场半
	public static final String LOTNO_SFC = "T01003"; // 足彩胜负彩
	public static final String LOTNO_RX9 = "T01004"; // 足彩任选9
	public static final String LOTNO_22_5 = "T01013"; // 体彩22选5
	public static final String LOTNO_JCZQ_HUN = "J00011"; // 竞彩足球混合
	public static final String LOTNO_JCZQ = "J00001"; // 竞彩足球胜平负
	public static final String LOTNO_JCZQ_RQSPF = "J00013";
	public static final String LOTNO_JCZQ_ZQJ = "J00003"; // 竞彩足球总进球
	public static final String LOTNO_JCZQ_BF = "J00002"; // 竞彩足球比分
	public static final String LOTNO_JCZQ_BQC = "J00004"; // 竞彩足球半全场
	public static final String LOTNO_JCLQ_HUN = "J00012"; // 竞彩篮球混合
	public static final String LOTNO_JCLQ = "J00005"; // 竞彩篮球胜负
	public static final String LOTNO_JCLQ_RF = "J00006"; // 竞彩篮球让分胜负
	public static final String LOTNO_JCLQ_SFC = "J00007"; // 竞彩篮球胜分差
	public static final String LOTNO_JCLQ_DXF = "J00008"; // 竞彩篮球大小分
	public static final String LOTNO_JCL = "JC_L";// 合买查询中查询所有竞彩篮球的标示
	public static final String LOTNO_JCZ = "JC_Z";// 合买查询中查询所有竞彩足球的标示
	public static final String LOTNO_JCZQ_GJ = "J00009"; // 竞彩足球冠军
	public static final String LOTNO_JC_GYJ = "JC_GYJ"; //用于购彩大厅图标显示
	

	public static final String LOTNO_BEIJINGSINGLEGAME_WINTIELOSS = "B00001";// 北京单场胜平负
	public static final String LOTNO_BEIJINGSINGLEGAME_TOTALGOALS = "B00002";// 北京单场总进球数
	public static final String LOTNO_BEIJINGSINGLEGAME_OVERALL = "B00005";// 北京单场全场总比分
	public static final String LOTNO_BEIJINGSINGLEGAME_HALFTHEAUDIENCE = "B00003";// 北京单半全场
	public static final String LOTNO_BEIJINGSINGLEGAME_UPDOWNSINGLEDOUBLE = "B00004";// 北京单场上下单双
	
	public static final String LOTNO_RUYI_GUESS = "RyJc";// 如意竞猜

	public static final int SSC_TWOSTAR_ZHIXUAN = 1;
	public static final int SSC_TWOSTAR_ZUXUAN = 2;
	public static final int SSC_TWOSTAR_HEZHI = 3; 
	public static final int SSC_FIVESTAR_ZHIXUAN = 4;
	public static final int SSC_FIVESTAR_TONGXUAN = 5;
	public static final int SSC_THREE = 30;
	public static final int SSC_THREE_GROUP_THREE = 31;
	public static final int SSC_THREE_GROUP_SIX = 32;

	public static final String BEIJINGSINGLE = "beiDan";
	public static final String PLAY_METHOD_TYPE = "playMethodType";
	public static final String NEW_JINGCAI = "jingCai";

	public static String currentTickType = "";
	public static String currentLoto = "";
	public static String currentTab = "";

	/**
	 * 彩种代码
	 */
	public static final String WEEK = "weekArray";
	public static final String MONTH = "monthArray";
	public static final String TOTAL = "totalArray";

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
	public static final String TWENTYBEL = "22-5";
	public static final String SFCLABEL = "sfc";
	public static final String RXJLABEL = "rxj";
	public static final String LCBLABEL = "lcb";
	public static final String GD115 = "gd115";
	public static final String NMK3LABEL = "nmk3";
	public static final String BDLABEL = "beijingsinglegame";
	public static final String JCZLABEL = "jcz";
	public static final String GDLABEL = "gd-11-5";
	public static final String ZCLABEL = "zc";
	public static final String RYJCLABEL = "ruyiguess";
	public static final String GYJLABEL = "Gyj";
	public static final String CQELVENFIVE = "cq-11-5";
	public static final String JLK3 = "jlk3";
	public static final String HAPPUPOKER="happy-poker";

	/** add by fansm 20130515 start */
	public static String[][] lotnoNameList = { { "hmdt", "hmdt" }, { LOTNO_RUYI_GUESS, RYJCLABEL },
			{ LOTNO_SSQ, SSQLABEL }, { LOTNO_DLT, DLTLABEL },
			{ LOTNO_FC3D, FC3DLABEL }, { LOTNO_11_5, DLCLABEL },
			{ LOTNO_SSC, SSCLABEL }, { LOTNO_JC_GYJ, GYJLABEL },
			{ LOTNO_JCZ, JCZLABEL },
			{LOTNO_HAPPY_POKER,HAPPUPOKER}, { LOTNO_JLK3, JLK3 },
			{ LOTNO_NMK3, NMK3LABEL } ,{ LOTNO_eleven, YDJLABEL },
			{ "zjjh", "zjjh" }, { LOTNO_GD_11_5, GDLABEL },
			{ LOTNO_PL3, PL3LABEL }, { LOTNO_QLC, QLCLABEL },
			{ LOTNO_22_5, TWENTYBEL }, { LOTNO_PL5, PL5LABEL },
			{ LOTNO_QXC, QXCLABEL }, { LOTNO_ZC, ZCLABEL },
			{ LOTNO_JCL, "jcl" }, { LOTNO_ten, "gd-10" },
			{ LOTNO_BJ_SINGLE, BDLABEL },{ LOTNO_CQ_ELVEN_FIVE, CQELVENFIVE }};
	/** add by fansm 20130515 end */

	/**
	 * 彩票状态
	 */
	public static final String NMK3WILLSALES = "nmk3-willsale";
	public static final String TWENWILLSALES = "22-5-willsale";
	public static final String TWENCLOSED = "22-5-closed";
	public static final String BDWILLSATES = "beijingsinglegame-willsale";
	public static final String JCGYJWILLSALES = "Gyj-willsale";
	
	public static final String SSQLABELCLOSED = "ssq-closed";
	public static final String FC3DLABELCLOSED = "fc3d-closed";
	public static final String QLCLABELCLOSED = "qlc-closed";
	public static final String PL3LABELCLOSED = "pl3-closed";
	public static final String PL5LABELCLOSED = "pl5-closed";
	public static final String QXCLABELCLOSED = "qxc-closed";
	public static final String DLTLABELCLOSED = "cjdlt-closed";
	public static final String SSCLABELCLOSED = "ssc-closed";
	public static final String DLCLABELCLOSED = "11-5-closed";
	public static final String YDJLABELCLOSED = "11-ydj-closed";
	public static final String SFCLABELCLOSED = "sfc-closed";
	public static final String RXJLABELCLOSED = "rxj-closed";
	public static final String LCBLABELCLOSED = "lcb-closed";
	public static final String GD115CLOSED = "gd115-closed";
	public static final String NMK3LABELCLOSED = "nmk3-closed";
	public static final String BDLABELCLOSED = "beijingsinglegame-closed";
	public static final String JCZLABELCLOSED = "jcz-closed";
	public static final String JCJLABELCLOSED = "jcl-closed";
	public static final String GDLABELCLOSED = "gd-11-5-closed";
	public static final String GDTENLABELCLOSED = "gd-10-closed";
	public static final String ZCLABELCLOSED = "zc-closed";
	public static final String RYJCLABELCLOSED = "ruyiguess-closed";
	public static final String GYJLABELCLOSED = "Gyj-closed";
	public static final String CQELVENFIVECLOSED = "cq-11-5-closed";
	public static final String JLK3CLOSED = "jlk3-closed";
	public static final String HAPPUPOKERCLOSED="happy-poker-closed";

	public static String[][] lotnoNameCloseList = {{ LOTNO_RUYI_GUESS, RYJCLABELCLOSED },
		{ LOTNO_SSQ, SSQLABELCLOSED }, { LOTNO_DLT, DLTLABELCLOSED },
		{ LOTNO_FC3D, FC3DLABELCLOSED }, { LOTNO_11_5, DLCLABELCLOSED },
		{ LOTNO_SSC, SSCLABELCLOSED },  { LOTNO_JC_GYJ, GYJLABELCLOSED },
		{ LOTNO_JCZ, JCZLABELCLOSED },
		{ LOTNO_JLK3, JLK3CLOSED } ,
		{ LOTNO_NMK3, NMK3LABELCLOSED }, { LOTNO_eleven, YDJLABELCLOSED },
		 { LOTNO_GD_11_5, GDLABELCLOSED },
		{ LOTNO_PL3, PL3LABELCLOSED }, { LOTNO_QLC, QLCLABELCLOSED},
		{ LOTNO_22_5, TWENCLOSED }, { LOTNO_PL5, PL5LABELCLOSED },
		{ LOTNO_QXC, QXCLABELCLOSED }, { LOTNO_ZC, ZCLABELCLOSED },
		{ LOTNO_JCL, JCJLABELCLOSED }, { LOTNO_ten, GDTENLABELCLOSED },
		{ LOTNO_BJ_SINGLE, BDLABEL },{ LOTNO_CQ_ELVEN_FIVE, CQELVENFIVE }
//		,{LOTNO_HAPPY_POKER,HAPPUPOKER}
		};	
	/**
	 * 竞彩足球TYpe标示
	 */
	public static final String JCFOOT = "1";
	/**
	 * 竞彩篮球Type标示
	 */
	public static final String JCBASKET = "0";
	public static String NEWS = ""; // 客户端首页提示语信息

	public static int SCREEN_WIDTH = 0;
	public static int SCREEN_HEIGHT = 0;
	public static int SCREEN_DENSITYDPI = 0;
	public static float SCREEN_DENSITY = 0f;

	public static final String GAME_CLASS = "RUYICAI_GAME_CLASS";

	public static final String GAME_CLICK_SUM = "GAME_CLICK_SUM";

	public static final int STAT_INFO_CACHE_NUM = 10;

	public static final String ACCOUNTRECHARGE_EIXT_TYPE = "ACCOUNTRECHARGE_EIXT_TYPE_CHANGE";

	public static final int RUYIHELPERSHOWLISTTYPE = 0;

	public static Bitmap grey = null;
	public static Bitmap red = null;
	public static Bitmap blue = null;
	public static int PADDING = 40;
	public static String avdiceStr[] = null;
	public static String IMSI;
	public static String MAC;

	public static final String SUCCESS_CODE = "0000";
	public static final String RETURN_CODE = "error_code";

	public static final String SALE_STOPED = "0";
	public static final String SALEINGL = "1";
	public static final String SALE_WILLING = "2";
	public static final String ADD_AWARD_OK = "1";
	public static final String ADD_AWARD_NO = "0";

	/** add by yejc 20130527 start */
	/**
	 * 联动优势话费充值
	 */
	public final static String UMPAY_SERVER_URL = "http://payment.umpay.com/hfwebbusi/pay/wxVersionUpdate.do";
	// 话付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致
	public static final String PAY_PLUGIN_NAME = "huafubaops1.3.1.apk";

	public static final String ALIPAY_PACK_NAME = "com.alipay.android.app";
	public static final String UMPAY_PHONE_PACK_NAME = "com.umpay.huafubao";
	public static final String UMPAY_BANKID = "ump003";
	public static final String isSSQON = "isSSQON";
	public static final String isDLTON = "isDLTON";
	public static final String ADWALL_DISPLAY_STATE = "adwall_display_state";
	public static final String YINLIAN_CARD_DISPLAY_STATE = "yinlian_card_display_state";
	public static final String YINLIAN_SOUND_DISPLAY_STATE = "yinlian_sound_display_state";
	public static final String ZHIFUBAO_SECURE_PAYMENT_DISPLAY_STATE = "zhifubao_secure_payment_display_state";
	public static final String ZHIFUBAO_RECHARGE_DISPLAY_STATE = "zhifubao_recharge_display_state";
	public static final String UMPAY_DISPLAY_STATE = "umpay_display_state";
	public static final String UMPAY_PHONE_DISPLAY_STATE = "umpay_phone_display_state";
	public static final String LAKALA_PAYMENT_DISPLAY_STATE = "lakala_payment_display_state";
	public static final String BANK_RECHARGE_DISPLAY_STATE = "bank_recharge_display_state";
	public static final String BANK_TRANSFER_DISPLAY_STATE = "bank_transfer_display_state";
	public static final String PHONE_RECHARGE_CARD_DISPLAY_STATE = "phone_recharge_card_display_state";
	public static final String EXCHANGE_DISPLAY_STATE = "exchange_display_state";
	public static final String JC_TOUZHU_TEXT_COLOR = "#990000";
	public static final String JC_TOUZHU_TITLE_TEXT_COLOR = "red";
	/** add by yejc 20130527 start */
	public static final String LOTNO = "Lotno";
	public static final String PAGENUM = "10";// 每页显示的条数
	public static final String ISSUE = "Issue";
	
	public static final int SEND_FROM_SIMULATE = 40;
	
public static String MSG_STATUS="msgStatus";//反馈消息
	

	public static final String SEARCHTAG = "searchTag";
	public static final String FIRST_START_PUSH="first_start_push";
	public static final String FIRST_START_PUSH2="first_start_push2";
	public static final String FIRST_START_PUSH3="first_start_push3";
	
	public static String REQUEST_REGISTER_CODE = "100";//注册
	public static String REQUEST_LOGIN_CODE = "101";//登陆
	public static String REQUEST_LOGOUT_CODE = "102";//退出登陆
	public static String REQUEST_OPEN_CODE = "103";//开机信息
	public static String REQUEST_MODIFY_USER_CODE = "104";//用户信息修改
	public static String REQUEST_CHECK_USER_CODE = "105";//检查用户是否存在	
	public static String REQUEST_GET_USER_CODE = "106";//获取用户
	public static String REQUEST_SEARCH_USER_CODE = "107";//搜索用户
	public static String REQUEST_UPDATE_USER_LOCATION_CODE = "108";//更新用户坐标
	public static String REQUEST_ADD_FRIEND_CODE = "109";//添加好友
	public static String REQUEST_DELETE_FRIEND_CODE = "110";//删除好友
	public static String REQUEST_FRIED_LIST_CODE = "111";//好友列表
	public static String REQUEST_GET_VERITY_CODE = "112";//获取短信验证码
	public static String REQUEST_CHECK_VERITY_CODE = "113";//校验短信验证码
	public static String REQUEST_ERROR_LIST_CODE = "114";//内部接口，获取所有errorcode错误码信息
	public static String REQUEST_GAME_ROSE_INFO_CODE = "115";//获取魔兽用户
	public static String REQUEST_XMPP_SERVICE_INFO_CODE = "116";//获取xmpp聊天服务器
	public static String REQUEST_MODIFY_FRIED_INFO_CODE = "117";//修改好友别名
	public static String REQUEST_GAME_ROSE_BINDING_CODE = "118";//添加游戏人物绑定
	public static String REOUEST_DELETE_GAME_ROLE="119";//获取游戏角色认证
	public static String REQUEST_NEARYBY_PLAYER_INFO_CODE = "120";//附近的玩家
	public static String REQUEST_PLAYERS_SAME_SERVER_INFO_CODE = "121";//找同服用户
	public static String REQUEST_FIND_ROLE_LIST="122";                 //查找角色                
	public static String REQUEST_USER_ROLE_LIST_CODE="125";//获取游戏角色列表
	public static String REOUEST_GET_GAME_ROLE_AUTHENTICATE_EQUIP="126";//获取游戏角色认证装备
	public static String REOUEST_GET_GAME_ROLE_AUTHENTICATE="127";//获取游戏角色认证
	public static String REQUEST_REVISE_USER_HONOR_CODE="128";//修改角色头衔
	public static String REQUEST_USER_TITLE_LIST_CODE="129";//头衔列表
	public static String REQUEST_USER_TITLE_RANKING_CODE="130";//头衔排行
	public static String REQUEST_USER_DYNAMIC_CODE="131";//用户动态
	public static String REQUEST_USER_DYNAMIC_DETAIL_CODE = "136" ;//用户动态详情
	public static String PUBLISH_DYNAMIC_MESSAGE_CODE = "134"; //发表动态消息
	public static String GET_FRIEND_DYNAMIC_MESSAGE_CODE = "132"; //获取好友动态
	public static String GET_DYNAMIC_PINGLUN_LIST_CODE = "137"; //获取动态评论列表
	public static String REVISEPASSWORD_BY_CODE="138";          //重置密码
	public static String FEEDBACK_CODE = "139"; //意见反馈
	public static String REQUEST_OPEN_CODE_NEW = "142"; //新的Open接口
	public static String REQUEST_USER_TOKEN_FAIL="141";
	public static String REQUEST_XMPP_NOTIFY_SERVICE_INFO_CODE = "143";//获取xmpp聊天服务器
	public static String REQUEST_SHARE_DYNAMIC = "144";//分享动态
	public static String BROAD_CAST_DYNAMIC = "145";//广播动态
	public static String REQUEST_ROLE_INFO = "146";//角色详情
	public static String UPDATE_REQUEST_ROLE_INFO = "160";//更新游戏角色排名
	public static String UPDATE_REQUEST_STATUS_ROLE_INFO = "159";//更新排名查询系统状态
	public static String SAY_HELLO_FIRST_CODE = "153";//添加第一次打招呼
	public static String SAY_HELLO_LIST_CODE = "154";//打过招呼的列表
	public static String REQUEST_MEET = "149";//许愿池
	public static String SEARCH_USER_BY_NICKNAME_CODE = "150";//根据用户昵称查找用户
	public static String REPORT_CODE = "155";//举报
	public static String MEET_SAY_HELLO_CODE = "158";//许愿池打招呼
	public static String ACTIVATE_CODE = "156";//激活码
	public static String AUTHENTICATION_CODE = "157";//妹子认证
	public static String CONTACTS_CODE = "162";//上传通讯录
	public static String GAME_FRIEND_CODE = "163";//导入游戏内好友
	public static String NEW_REQUEST_MEET = "164";//新许愿池
	public static String NEW_MEET_SAY_HELLO_CODE = "165"; //新愿池打招呼
	public static String USED_CD_KEY_CODE = "167"; //检查兑换券
	public static String CHECK_CD_KEY_CODE = "168"; //检查兑换券
	public static String DELETE_CODE_KEY_CODE = "177"; //检查兑换券
	public static boolean isRefreshContactsPeople = false;
	
	public static String REQUEST_PERSON_DETAIL_INFO = "request_person_detail_info";
	public static String INTENT_PERSON_PICTURE = "intent_person_picture";
	public static String INTENT_PERSON_CENTER_INFO_EDIT = "intent_person_center_info_edit";
	public static String INTENT_PERSON_CENTER_CONTENT_EDIT = "intent_person_center_content_edit";
	public static String INTENT_USER_INFO_MODIFY = "intent_user_info_modify";
	public static String INTENT_CHARACTERS_INFO_MANGER = "intent_characters_info_manger";
	public static String INTENT_GAME_NAME = "intent_game_name";
	public static String INTENT_USER_NAME = "intent_user_name";
	public static String INTENT_IS_PERSON_CENTER = "intent_is_person_center";
	public static String INTENT_IS_ADD_FRIEND_BY_ROLE = "intent_is_add_friend_by_role";
	public static String INTENT_IS_ROLE_MANAGER_BY_ROLE = "intent_is_role_manager_by_role";
	public static String INTENT_USER_ID = "intent_user_id";
	public static String USER_DYNAMIC_INFO = ""; //用户信息
	public static String UPLOAD_PICTURE_TYPE_ALBUM = "album"; //上传的图片为头像图片
	public static String UPLOAD_PICTURE_TYPE_TITLE = "title";//上传的图片为头衔图片
	public static String UPLOAD_PICTURE_TYPE_GIRL = "girl";//上传的图片为妹子认证图片
	public static String UPLOAD_PICTURE_TYPE_DYNAMICMSG = "dynamicmsg";//上传动态图片
	public static final String ACTION_SHOW_BACKGROUND_NOTIFICATION = "com_ruyicai_show_background_notification";
	public static final String ACTION_SHOW_MESSAGE_NOTIFICATION = "com_ruyicai_show_message_notification";
	public static final String ACTION_NET_WORK_STATUS_NOTIFICATION = "com_ruyicai_net_work_status_notification";
	public static final String ACTION_SEND_MESSAGE_NOTIFICATION = "com_ruyicai_send_message_notification";
	public static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";
	public static final String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";
	public static  final String PERSON_CENTER_BROADCAST_ACTION  = "person_center_broadcast_action";
	public static String PERSON_CENTER_BROADCAST_TYPE= "person_center_broadcast_type";
	public static String PERSON_CENTER_BROADCAST_TYPE_ROLE= "role";
	public static String PERSON_CENTER_BROADCAST_TYPE_TITLE= "title";
	public static final String SYSTEM_ID="10000";
	
	public static String FRIEN_DYNAMINC_CACHE="friendcachedata";
	public static String MY_DYNAMINC_CACHE="mycachedata";
	public static String NEAR_PLAYER_CACHE="nearplayercachedata";
	
	public static String OFF_LINE_MSG_NUM="offer_msg_number";
	public static String OFF_LINE_USER_NUM="offer_user_number";
	public static String MSG_TV_SHOW_SETTING="msg_tv_show_setting";
	public static String MSG_REMIND_SOUND_SETTING="msg_remind_sound_setting";
	public static String MSG_REMIND_VIBRATE_SETTING="msg_remind_vibrate_setting";
	public static String MSG_TIME_FRAME_SETTING="msg_time_frame_setting";
	public static String OFF_LINE_MSG_PACKET_IDS="offer_msg_packetIds";
	public static final String OPEN_CONTACTS="open_contacts";
	public static String SAYHELLO = "sayHello";//打招呼
	public static String NORMALCHAT = "normalchat";//正常聊天
	public static String SYSTEM="system";//系统消息
	public static String PUSHMESSAGE="pushmesssage";
	public final static String TITLE_JSON_KEY_URL = "title";
	public final static String MSG_JSON_KEY_URL = "msg";
	public final static String IMG_JSON_KEY_URL = "img";
	public final static String URLLINK_JSON_KEY_URL = "urlLink";
	public final static String MESSAGEID_JSON_KEY_URL = "messageid";
	public final static String DAILYNEWS_JSON_KEY_URL = "dailyNewsId";
	public final static String METHOD_JSON_KEY_URL = "method";
	public final static String CHANNEL_JSON_KEY_URL = "channel";
	public final static String TOKEN_JSON_KEY_URL = "token";
	public final static String PARAMS_JSON_KEY_URL = "params";
	public final static String LONGITUDE_JSON_KEY_URL = "longitude";
	public final static String LATITUDE_JSON_KEY_URL = "latitude";
	public final static String GENDER_JSON_KEY_URL = "gender";
	public final static String TYPE_JSON_KEY_URL = "type";
	public final static String PAGEINDEX_JSON_KEY_URL = "pageIndex";
	public final static String MAXSIZE_JSON_KEY_URL = "maxSize";
	public final static String MAC_JSON_KEY_URL = "mac";
	public final static String IMEI_JSON_KEY_URL = "imei";
	public final static String CREATETIME_JSON_KEY_URL = "createTime";
	public final static String CONNECTTIME_JSON_KEY_URL = "connectTime";
	public final static String NEEDUPDATE_JSON_KEY_URL = "needUpdate";
	public final static String CITY_JSON_KEY_URL = "city";
	public final static String SN = "sn";
	public final static String ENCRYPT = "encrypt";
	public final static String COMPRESSION = "compression";
	// 主人信息的key
		public static final String SIGNATURE_JSON_KEY_USER = "signature";
		public static final String USERNAME_JSON_KEY_USER = "username";
		public static final String PASSWORD_JSON_KEY_USER = "password";
		public static final String DEVICETOKEN_JSON_KEY_USER = "deviceToken";
		public static final String USERID_JSON_KEY_USER = "id";
		public static final String USERID_JSON_KEY_USER_ID = "userid";
		public static final String CITY_JSON_KEY_USER = "city";
		public static final String CREATTDATE_JSON_KEY_USER = "createDate";
		public static final String NICKNAME_JSON_KEY_USER = "nickname";
		public static final String BIRTHDATE_JSON_KEY_USER = "birthdate";
		public static final String SEX_JSON_KEY_USER = "gender";
		public static final String AGE_JSON_KEY_USER = "age";
		public static final String IMG_JSON_KEY_USER = "img";
		public static final String HOBBY_JSON_KEY_USER = "hobby";
		public static final String LONGITUDE_JSON_KEY_USER = "longitude";
		public static final String LATITUDE_JSON_KEY_USER = "latitude";
		public static final String DISTANCE_JSON_KEY_USER = "distance";
		public static final String IMGID_JSON_KEY_USER = "imgId";
		public static final String IMGTYPE_JSON_KEY_USER = "imgType";
		public static final String TOKEN_JSON_KEY_USER = "token";
		public static final String PHONENUMBER_JSON_KEY_USER = "phoneNum";
		public static final String EMAIL_JSON_KEY_USER = "email";
		public static final String CODE_JSON_KEY_USER = "xcode";
		public static final String IMAEG_JSON_KEY_USER = "img";
		public static final String USERNAME="username";
		public static final String CRICLE_CONDITIONTYPE = "conditionType";//
		public static final String TIEZI_ID = "noteId";// 帖子id
		public static final String TIEZI_HUIFU_ID = "replyId";// 回复id
		public static final String TIEZI_NAME = "name";// 帖子名称
		public static final String TIEZI_CONTENT = "content";// 帖子内容
		public static final String TIEZI_PID = "pid";// 父帖Id
		public static final String FEED_BACK = "feedback";// 反馈
		public static final String EMAIL = "Email";
		public static final String SRCID = "srcid";// 被赞的对象
		public static final String TYPE = "type";// 被赞对象的类型
		public static final String HIDE="hide"; //显示头衔，0是显示， 1是隐藏， 如果不传两种都返回
		public static final String DAILY_NEWS_PAYLOAD="payload"; //每日新闻id
		public static final String RANKTYPE="ranktype";
		public static final String RANKVALTYPE="rankvaltype";
		public static String GAMEID = "gameid";
		public static String GAMEREALM = "gamerealm";
		public static String GAMENAME = "gamename";
		public static String CHARACTERID = "characterid";
		public static String CHARACTERIDBIG = "characterId";
		public static String EXCHANGECODE = "exchangeCode";
		public static String SERVICE_NAME = "serviceName";
		public static String PAGE_INDEX = "pageIndex";
		public static String MAX_SIZE = "maxSize";
		public static final String ROUSER_ID = "touserid";
		public static final String INDEX = "index";
		public static final String SAYHELLOTYPE = "sayHelloType";
		public static final String INVITATION_CODE = "invitationCode";
		public static final String IMG = "img";
		public static final String CONTACTS = "contacts";
		public static final String TOUSERID = "touserid";
		public static final String MESSAGE_ID = "messageId";
		public static String CHARACTERNAME = "charactername";
		public static String SERVERREALM = "realm";
		public final static String VERSION_JSON_KEY_URL = "version";
		public static final String GAME_FRIEND_USER_ID_JSON_KEY = "frienduserid";
		public static final String ROUTE_TYPE = "type";
		public static String SERVERGUILD = "guild";
		public static String SERVERCLASSID = "classid";
		public static String AUTHITEM="authitem";
		
		// SD卡路径(有"/")
		public static String SDCARDPATH = Environment.getExternalStorageDirectory()+ "/";
		// 存贮图片的app文件夹名
		public final static String APP_WENJIANJIA = "ruyicai/";
		public final static String SD = Constants.SDCARDPATH
				+ Constants.APP_WENJIANJIA;
		
		// 开机图片的名字
		public static String STARTIMAGE = "startimage";
		// share里存的versionNane
		public static final String VERSIONNAME = "versionNane";
		public static final String VERSIONCODE = "versionCode";
		public static final String clientUpdateUrl = "clientUpdateUrl";
		// Shared的文件名
		public final static String SHARED_NAME = "pet";
		public final static String FRIEND_SORT_SHARED_KEY = "friendSort";
		public final static String ATTENTION_SORT_SHARED_KEY = "attentionSort";
		public final static String FAN_SORT_SHARED_KEY = "fansSort";
		public final static String REGISTER_NEED_MSG_SHARED_KEY = "registerNeedMsg";
		
		// Shared的Key
		public final static String USERID_SHARED_KEY = "userid";
		public final static String TOKEN_SHARED_KEY = "token";
		public final static String USRENAME_SHARED_KEY = "username";
		public final static String PASSWORD_SHARED_KEY = "password";
		public final static String OPEN_SHARED_KEY = "open";
		public final static String LONGITUDE_SHARED_KEY = "longitude";
		public final static String LATITUDE_SHARED_KEY = "latitude";
		public final static String CHATSERVER_SHARED_KEY = "chatserver";
		public final static String NEEDUPDATE_SHARED_KEY = "needUpdate";
		public final static String IMAGEID_SHARED_KEY = "imgId";
		public final static String CITY_SHARED_KEY = "city";
		public final static String ADDRESS_SHARED_KEY = "address";
		public final static String NAME_SHARED_KEY = "name";
		public final static String IMSI_KEY = "imsi";
		public final static String AUTHENTICATIONTOKEN_SHARED_KEY = "authenticationToken";
		public final static String GAME_LIST_MILLIS_SHARED_KEY = "gamelist_millis";
		public final static String WOW_REALMS_MILLIS_SHARED_KEY = "wow_realms_millis";
		public final static String WOW_CHARACTER_CLASSES_MILLIS_SHARED_KEY = "wow_characterclasses_millis";
		public final static String AUTH_LOGIN_SHARED_KEY = "authLogin";
		
		public final static String CLIENT_UPDATE_SHARED_KEY = "clientUpdate";
		public final static String CLIENT_MUST_UPDATE_SHARED_KEY = "clientMustUpdate";
		public final static String CLIENT_UPDATE_URL_SHARED_KEY = "clientUpdateUrl";
		
		public final static String BUYHALL = "buyhall";
		public final static String OPENCENTER = "opencenter";
		public final static String USERCENTER = "usercenter";	
		public final static String JOINBUYHALL = "joinbuyhall";
		public static String LOTNOTURNFLAG = "";
		
		public static String PUSH_PAGE_GUESS_TOPIC_ID = "guess_topic_ID";//竞猜题目页面　ID
		public static String PUSH_PAGE_URL = "url";
		public static final String CLIENT_MSG_RECIVER_ACTION = "com.android.client.msgreciver";
		public static final String SERVER_MSG_RECIVER_ACTION = "com.android.server.msgreciver";
		public static final String IS_FROM_LOTTERY_HALL = "is_from_lottery_hall";
		
}
