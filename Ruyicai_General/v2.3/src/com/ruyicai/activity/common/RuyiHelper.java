package com.ruyicai.activity.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.palmdream.RuyicaiAndroid.R;

import com.ruyicai.dialog.Accoutdialog;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.handler.MyDialogListener;
import com.ruyicai.net.JrtLot;
import com.ruyicai.net.transaction.BettingInterface;
import com.ruyicai.net.transaction.ChangePasswordInterface;
import com.ruyicai.net.transaction.ExpertAnalysisInterface;
import com.ruyicai.net.transaction.FeedbackInterface;
import com.ruyicai.net.transaction.GetAccountDetail;
import com.ruyicai.net.transaction.GetCashInterface;
import com.ruyicai.net.transaction.GetUserInfoInterface;
import com.ruyicai.net.transaction.GiftLotteryInterface;
import com.ruyicai.net.transaction.PackageInterface;
import com.ruyicai.pojo.AddNumDetail;
import com.ruyicai.pojo.BettingDetail;
import com.ruyicai.pojo.GiftDetail;
import com.ruyicai.pojo.GiftedDetail;
import com.ruyicai.pojo.WinningDetail;
import com.ruyicai.util.Constants;
import com.ruyicai.util.GT;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.ShellRWSharesPreferences;
import com.ruyicai.view.OneBallView;
import com.ruyicai.view.RuyiExpressFeelView;

/*
 * �������֡��û����ġ�������Ȩ��ר�ҷ���
 */
public class RuyiHelper extends Activity implements
		SeekBar.OnSeekBarChangeListener, MyDialogListener {
	JSONArray jsonArray = null;
	JSONArray jsonArray_gifted;
	String play_name = null;
	String batchcode = null;
	String betcode = null;
	String sell_datetime = null;
	// String beishu = null;
	String to_mobile_code;// �������ֻ���
	String sell_term_code;
	String amount;
	String term_begin_datetime;
	String play_name_gifted;
	String batchcode_gifted;
	String sell_term_code_gifted;
	String from_mobile_code;// �������ֻ���
	String amount_gifted;
	String term_begin_datetime_gifted;
	String sell_datetime_gifted;
	String betcode_gifted;
	String valid_term_code;
	// String prize_info;
	String abandon_date_time;// ����ʱ��
	String prizeamt;// �н��ܽ��
	String encash_flag;// �н����
	String prizetime;
	String prizeinfo;// �н���Ϣ��
	String prizelttle;
	// sell_term_code_gifted
	int bettingindex;
	String betNum;// ��Ʊע��
	String batchNum;// ��Ʊ��������
	String lastNum;// ʣ�๺������
	String addamount;// ׷�Ž�һ�ڵģ�
	String beginBatch;// ��ʼͶע��Ʊ�ں�
	String lastBatch;// �ϴ�Ͷע��Ʊ�ں�
	String addednum;// ��׷����
	String addedamount;// ��׷���
	int currentPage = 0; // ��ǰҳ
	int totalPage = 0;// ��ҳ��
	int lastItems = 0;// 
	int iType = 0; // �ж�λ�������б����ӱ�ǩ �³�20100803
	boolean iDetail = true; // �ж��Ƿ�����Ϣ �³�20100803
	int iPage = 0;
	int iPageGifted = 0;
	int iTotaPage = 0;
	int iTotaPageGifted = 0;
	int Separate = 0;
	int SeparateGifted = 0;

	int specialPage;

	String error_code_gift = "00";
	String error_code_gifted = "00";
	String[][] account;
	String[][] all;
	String[][] charge;
	String[][] pay;
	String[][] reward;
	String[][] cash;
	String type;
	View accountView;
	View betView;
	View winningView;
	View addNumView;
	// View giftView;

	Vector bettingVector;
	Vector WinningVector;
	Vector AddNumVector;
	Vector GiftVector;
	Vector GiftedVector;

	Button b_uppage_gift;
	Button b_downpage_gift;
	Button b_uppage_gifted;
	Button b_downpage_gifted;
	TextView presentedView;
	TextView receivedView;

	Button b_canceltranking;

	/* ר�ҷ��� */
	protected static final String[] expertAnalyzeTypeName = { "˫ɫ�����",
			"����3D����", "���ֲʷ���", "����������", "��������͸����", "ʤ���ʷ���", }; // zlm
	// ����������������͸����
	// protected static final String[] expertAnalyzeMore =
	// {">>>>",">>>>",">>>>"};
	public final static int ID_EXPERTANALYZE = 30;/* ר�ҷ����б� */
	public final static int ID_EXPERTANALYZE_SSQ = 31;/* ˫ɫ��ר�ҷ��� */
	public final static int ID_EXPERTANALYZE_FC3D = 32;/* ����3Dר�ҷ��� */
	public final static int ID_EXPERTANALYZE_QLC = 33;/* ���ֲ�ר�ҷ��� */
	TextView typeName;
	public final static String SUB_EXPERT_ANALYZE_TITLE = "SUB_EXPERT_ANALYZE_TITLE"; // ������
	// 7.4���������
	List<Map<String, Object>> subExpertAnalyzeList; // ������ 7.4���������: �б�������������Դ
	public static String[] subExpertAnalyzeTitle = { "����20100910�ڷ���",
			"���20100910�ڷ���", "����20100910�ڷ���", "����20100910�ڷ���" };
	int expertId;
	String[] specifyExpertAnalyzeInfo;
	SpeechListAdapter adapterExpert; // ר�ҷ������б�������
	// ������ 7.4���������:
	public String[] mTitles = { "����20100910�ڷ���", "���20100910�ڷ���",
			"����20100910�ڷ���", "����20100910�ڷ���" };
	// ������ 7.4���������:
	public String[] mDialogue = {
			"So shaken as we are, so wan with care,"
					+ "What yesternight our council did decree"
					+ "In forwarding this dear expedience.",

			"Hear him but reason in divinity,"
					+ "From open haunts and popularity.",

			"I come no more to make you laugh: things now,"
					+ "A man may weep upon his wedding-day.",

			"First, heaven be the record to my speech!"
					+ "In the devotion of a subject's love,"
					+ "And wish, so please my sovereign, ere I move,"
					+ "What my tongue speaks my right drawn sword may prove." };

	// ���������б����ID
	public final static int ID_GAMEINTRODUTION = 1;/* �淨���� */
	public final static int ID_FEQUENTQUESTION = 2; /* �������� */
	public final static int ID_AUTHORIZING = 3;/* ������Ȩ�ַŵ������б��� */
	public final static int ID_GOUCAI = 47;/* �������� */
	public final static int ID_ZHONGJIANG = 48;/* �н��콱 */
	public final static int ID_SHUOMING = 49;/* ����˵�� */
	public final static int ID_MIMAZHAOHUI = 50;/* �����һ� */
	public final static int ID_KEFUDIANHUA = 51;/* �ͷ��绰 */

	// ���������б��в��������б�ĸ���ID
	public final static int ID_USERREGISTER = 4;/* �û�ע�� */
	public final static int ID_USERLOGIN = 5;/* �û���¼ */
	public final static int ID_USERBETTING = 6;/* �û�Ͷע */
	public final static int ID_PRESENTLOTTREY = 7;/* ���Ͳ�Ʊ */
	public final static int ID_ACCOUNTRECHARGE = 8;/* �˺ų�ֵ */
	public final static int ID_ACCOUNTWITHDRAW = 9;/* �˺����� */
	public final static int ID_BALANCEINQUIRY = 10;/* ����ѯ */

	// �б�ID
	public final static int ID_RUYIHELPERLISTVIEW = 11;/* ���������б� */
	public final static int ID_OPERATIONASSISTANTLISTVIEW = 12;/* ���������еĲ��������б� */
	public final static int ID_MORELISTVIEW = 13;/* �����ࡱ�б� */
	public final static int ID_USERCENTER = 14;/* �û������б� */
	public final static int ID_RUYITAOCAN = 41;/* �����ײ��б� */
	public final static int ID_XINGYUNXUANHAO = 42;/* ����ѡ���б� */
	public final static int ID_TESETOUZHU = 43;/* ��ɫͶע�б� */
	public final static int ID_CHACKINFO = 44;/* ��ѯ��Ϣ�б� */
	public final static int ID_ACOUNT = 45;/* �˻������б� */
	public final static int ID_JIESHAO = 46;/* ��˾��� */
	public final static int ID_XITONGSHEZHI = 52;/* ϵͳ���� */

	// ��ʱδ�õ�------�û������б����(����ѯ���˺ų�ֵ���˺����ָ������������в��������б��ѡ��)
	public final static int ID_WINNGINGCHECK = 15;/* �н���ѯ */
	public final static int ID_BETTINGDTAILS = 16;/* Ͷע��ϸ */
	public final static int ID_ACCOUNTDTAILS = 17;/* �˺Ų�ѯ */
	public final static int ID_GIFTCHECK = 18;/* ���Ͳ�ѯ */
	public final static int ID_PASSWORDCHANGE = 19;/* �����޸� */
	public final static int ID_TRACKNUMBERINQUIRY = 20; /* ׷�Ų�ѯ */

	// �û�����-�˻���ϸ-5��ǩID
	public final static int ID_ALL = 21;/* ȫ�� */
	public final static int ID_RECHARGE = 22;/* ��ֵ */
	public final static int ID_PAY = 23;/* ֧�� */
	public final static int ID_AWARDSCHOOL = 24;/* ���� */
	public final static int ID_WITHDRAW = 25;/* ���� */

	protected static final int DIALOG1_KEY = 0;
	private static final int DIALOG_FORGET_PASSWORD = 1;
	ProgressDialog progressdialog;

	// �û�����-�˻���ϸ-5��ǩ��ť
	ImageButton allbtn;/* ȫ�� */
	ImageButton rechargebtn;/* ��ֵ */
	ImageButton paybtn;/* ֧�� */
	ImageButton awardschoolbtn;/* ���� */
	ImageButton withdrawbtn;/* ���� */
	ImageButton returnbtn;/* ���� */

	static String[] UserCenter_AccountDetail_TradingDate;
	static String[] UserCenter_AccountDetail_TradingMode;
	static String[] UserCenter_AccountDetail_Yu_E;

	// ���⴫�飬�����ײͣ�����ѡ��
	/* �б�������adapter����String[] From�Ĵ���� */
	// public final static String ICON = "ICON";/* ͼ�� */
	static final String IICON = "IICON";
	public final static String TITLE = "TITLE"; /* ���� */
	public final static String TITLETEXT = "TITLETEXT"; /* �������� */
	public final static String SUBSCRIBEBUTTON = "SUBSCRIBEBUTTON";
	public final static String EDITBUTTON = "EDITBUTTON";
	public final static String UNSUBSCRIBEBUTTON = "UNSUBSCRIBEBUTTON";

	// ���⴫�����б����ID
	public final static int ID_MAINRUYIEXPRESSFEEL = 81;/* ���⴫�����б� */
	public final static int ID_RUYIPACKAGE = 82; /* �����ײ� */
	public final static int ID_LUCKNUMBER = 83;/* ����ѡ�� */
	public final static int ID_RUYICHUANQING = 89;/* ���⴫�� */
	public final static int ID_RUYICHUANQING_SSQ = 90;/* ���⴫��˫ɫ�� */
	public final static int ID_RUYICHUANQING_FC3D = 91;/* ���⴫�鸣��3D */
	public final static int ID_RUYICHUANQING_QLC = 92;/* ���⴫�����ֲ� */

	// �����ײͲ��ֶ���״̬ trueΪ������falseΪδ����

	boolean[] subscribeFlag = { true, true, true, true, true };
	int viewid;
	boolean Flag;
	public final static int LAYOUT_INDEX1 = 0;
	// true δ���� false ����
	public Integer[] imageGroup1 = { R.drawable.sub1, R.drawable.edit2,
			R.drawable.unsub2 };
	public Integer[] imageGroup2 = { R.drawable.sub2, R.drawable.edit1,
			R.drawable.unsub1 };
	public Integer[] imageGroup = new Integer[3];
	TextView holoderTextView; // �ײ�����
	String[] holderText = new String[5];
	String text_str;

	RuyiPackageEfficientAdapter ruyiPackageAdapter;
	String packageName;// �ײͲ�������
	int iPosition;
	boolean[][] states = new boolean[5][3];
	int iadapter;
	// �ײͼ�Ԫ�Ļ��� 0709 wyl
	String ssqamount = "";
	String fc3damount = "";
	String qlcamount = "";
	// wangyl 8.27 add pl3 and dlt
	String pl3amount = "";
	String dltamount = "";

	ImageButton btnreturn;/* ���� */// 7.3�����޸ģ���Button����ImageButton

	List<Map<String, Object>> list;/* �б�������������Դ */
	TextView texttitle;/* ���� */

	TextView title;/* ���� */
	TextView textview;/* �������� */
	Button subBtn;
	Button editBtn;
	Button unsubBtn;

	// �����ײͶԻ���
	RadioGroup radioGroup;
	RadioButton rb10;
	RadioButton rb8;
	RadioButton rb6;
	RadioButton rb4;
	RadioButton rb2;
	TextView dialog_tips;
	Button ruyipackage_okBtn;
	Button ruyipackage_cancelBtn;
	String ruyipackage_str = "10";
	String ruyipackage_str_subscribe = "10";
	boolean editchanged = false;// true Ϊ�ı�
	boolean subscribechanged = false;// true Ϊ�ı�

	// ���⴫��
	TextView ruyichuanqing_ssq;
	TextView ruyichuanqing_fc3d;
	TextView ruyichuanqing_qlc;
	// wangyl 8.25 ����������ʹ���͸
	TextView ruyichuanqing_dlt;// ����͸
	TextView ruyichuanqing_pl3;// ������
	LinearLayout ruyichuanqing_sub_view;
	// wangyl 8.25 ���⴫������Ż�
	TextView[] textViews = new TextView[5];// ���⴫���ǩ��

	boolean aState = true;// true ��ʾ����Ի��� false ����ʾ

	int iCurrentBtFlag;
	int iCurrentId;
	int viewId;

	// ����ѡ��
	public static final String[] chooseLuckLotteryNum_zhonglei = { "˫ɫ��",
			"����3D", "���ֲ�", "������", "��������͸" };

	public static final String[] chooseLuckLotteryNum_title = { "˫ɫ��", "����3D",
			"���ֲ�", "������", "����͸" };
	// ������ 7.7 �����޸ģ�����ѡ���´���
	public final static int LAYOUT_INDEX = 0;
	public final int CHECKBOX_INDEX = 100;

	Cursor cursor;
	int id = 0;

	public Integer[] gridIcon;
	public String[] gridIconName;

	ImageView xingzuo_btn, shengxiao_btn, xingming_btn, shengri_btn; // ���Դ���
	String str;
	String type01, type02, type03, type04, type05, type06, type07, type08; // ��Ʊ����
	EditText editTextXingming, editTextYear, editTextMonth, editTextDay;
	AlertDialog.Builder builderXingming; // �����ĶԻ���

	static int temp;
	int year, month, day;
	// �����
	int[] randomNumGroup_1_ssq, randomNumGroup_1_fc3d, randomNumGroup_1_qlc,
			randomNumGroup_2_ssq, randomNumGroup_2_fc3d, randomNumGroup_2_qlc,
			randomNumGroup_3_ssq, randomNumGroup_3_fc3d, randomNumGroup_3_qlc,
			randomNumGroup_4_ssq, randomNumGroup_4_fc3d, randomNumGroup_4_qlc,
			randomNumGroup_5_ssq, randomNumGroup_5_fc3d, randomNumGroup_5_qlc;
	// ������ 7.7 �����޸ģ��������ѡ��ͼ��
	public static final String[] textContent = { "����", "��Ф", "����", "����" };
	public static final Integer[] imageId = { R.drawable.xingzuo,
			R.drawable.shengxiao, R.drawable.xingming, R.drawable.shengri };

	String[] gridText = new String[12];
	Integer[] gridImage = new Integer[12];

	public static final Integer[] mIcon = { R.drawable.list_ssq,
			R.drawable.list_fc3d, R.drawable.list_qlc, R.drawable.list_pl3,
			R.drawable.list_dlt }; // zlm 8.9 �������������������͸ͼ��
	public static final Integer[] mShengxiaoIcon = {// ��ʾ��ͼƬ����

	R.drawable.shengxiao_1_mouse, R.drawable.shengxiao_2_bull,
			R.drawable.shengxiao, R.drawable.shengxiao_4_rabbit,
			R.drawable.shengxiao_5_dragon, R.drawable.shengxiao_6_snake,
			R.drawable.a, R.drawable.shengxiao_8_sheep,
			R.drawable.shengxiao_9_monkey, R.drawable.shengxiao_10_chicken,
			R.drawable.shengxiao_11_dog, R.drawable.shengxiao_12_pig, };

	public static final Integer[] mXingzuoIcon = { R.drawable.xingzuo_shuiping,
			R.drawable.xingzuo_shuangyu, R.drawable.xingzuo_baiyang,
			R.drawable.xingzuo_jinniu, R.drawable.xingzuo_shuangzi,
			R.drawable.xingzuo_juxie, R.drawable.xingzuo_shizi,
			R.drawable.xingzuo_chunv, R.drawable.xingzuo_tianping,
			R.drawable.xingzuo_tianxie, R.drawable.xingzuo_sheshou,
			R.drawable.xingzuo_mojie };

	public static final String[] xingzuoName = { "ˮƿ��", "˫����", "������", "��ţ��",
			"˫����", "��з��", "ʨ����", "��Ů��", "�����", "��Ы��", "������", "ħЫ��" };

	public static final String[] shengxiaoName = { "��", "ţ", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��" };

	/**
	 * ����ѡ��(��ʾС��ע����Ǯ���Ľ��沿�ֵĶ���)
	 */
	public static final int BALL_WIDTH = 35;
	protected static int[] aRedColorResId = { R.drawable.red };
	static int[] aBlueColorResId = { R.drawable.blue };

	// zlm 7.13 �����޸ģ��ж�С���Ƿ����ڻ�
	boolean isDrawing;
	LinearLayout[] layoutAll;
	int countLinearLayout = 0;
	int[][] receiveRandomNum = new int[5][];
	int iProgressJizhu = 1;
	int iProgressBeishu = 1;
	int iProgressQishu = 1;

	LinearLayout ballLayoutGroup;
	LinearLayout layout01; // ��һ��С�򲼾�
	LinearLayout layout02; // �ڶ���С�򲼾�
	LinearLayout layout03; // ������С�򲼾�
	LinearLayout layout04; // ������С�򲼾�
	LinearLayout layout05; // ������С�򲼾�

	SeekBar mSeekBarJizhu; // ע��
	SeekBar mSeekBarBeishu;// ����
	SeekBar mSeekBarQishu;// ����

	TextView mTextMoney;
	TextView mTextJizhu; // ��ע
	TextView mTextBeishu;// ����
	TextView mTextQishu;// ����

	TextView agreeAndpayTitleView; // ����ѡ����ȷ��ҳ��ı��� ������ �����޸ģ�7.3��ӵĴ���

	LinearLayout agreePayBallLayout01; // ������ �����޸ģ�7.3��ӵĴ���
	LinearLayout agreePayBallLayout02;
	LinearLayout agreePayBallLayout03;
	LinearLayout agreePayBallLayout04;
	LinearLayout agreePayBallLayout05;

	/**
	 * ����ѡ�Ÿ���ID
	 */
	public final static int ID_CLLN_MAINLISTVIEW = 104;
	public final static int ID_CLLN_GRID_VIEW = 105;
	public final static int ID_CLLN_XINGMING_DIALOG_LISTVIEW = 106;
	public final static int ID_CLLN_SHENGRI_DIALOG_LISTVIEW = 107;
	public final static int ID_CLLN_BUTTON_SET = 108;
	public final static int ID_CLLN_SSQ_BUTTON_SET = 116;
	public final static int ID_CLLN_SHOWBALLMONRY = 117;
	public final static int ID_CLLN_SHOW_ZHIFU_DIALOG = 118;
	public final static int ID_CLLN_SHOW_TRADE_SUCCESS = 119;

	// ���� 2010/7/9�³�
	// protected static final int PROGRESS_VALUE = 0;
	// ProgressDialog progressDialog;
	PopupWindow popupwindow;
	TextView text, mingCheng;

	// �ӿ�
	JSONArray jsonObject3;
	String lottery_type;
//	int iretrytimes = 2;
	String re;
	String error_code;
	JSONObject obj;
	String phonenum;
	String sessionid;
	String tsubscribeIdssq;
	String tsubscribeIdfc3d;
	String tsubscribeIdqlc;

	String tsubscribeIdpl3;
	String tsubscribeIddlt;
	String tsubscribeId;
	String textssq;
	String textfc3d;
	String textqlc;

	String textpl3;
	String textdlt;
	ShellRWSharesPreferences shellRWtext;
	String lotterytype;
	String state;
	private ShellRWSharesPreferences shellRW;
    private int pageNum=10;//ÿҳ����Ϣ��
	// ��������ѡ��Ͷע������ 2010/7/4 �³�
	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.getData().getString("get");
			switch (msg.what) {
			case 0:

				// ������7.9�޸��ײͽӿ�
				progressdialog.dismiss();// ȡ����ʾ��
				// ����Ϣд��preferences ����գ���д�� 2010/7/10 �³�
				ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
						RuyiHelper.this, "addInfo");
				if (iCurrentBtFlag == -1) {// ��ѯ

					PublicMethod.myOutput("--------into chaxun-----"
							+ ssqamount + "---fc3damount-----" + fc3damount
							+ "---qlcamount----" + qlcamount);
					shellRW.setUserLoginInfo("ssqtext", "");
					shellRW.setUserLoginInfo("fc3dtext", "");
					shellRW.setUserLoginInfo("qlctext", "");

					shellRW.setUserLoginInfo("pl3text", "");
					shellRW.setUserLoginInfo("dlttext", "");

					if (!ssqamount.equals("")) {

						shellRW.setUserLoginInfo("ssqtext", ssqamount);
						PublicMethod.myOutput("-------------------handle------"
								+ shellRW.getUserLoginInfo("ssqtext"));
					}
					if (!fc3damount.equals("")) {

						shellRW.setUserLoginInfo("fc3dtext", fc3damount);
						PublicMethod.myOutput("-------------------handle------"
								+ shellRW.getUserLoginInfo("fc3dtext"));
					}
					if (!qlcamount.equals("")) {

						shellRW.setUserLoginInfo("qlctext", qlcamount);
						PublicMethod.myOutput("-------------------handle------"
								+ shellRW.getUserLoginInfo("qlctext"));
					}

					if (!pl3amount.equals("")) {
						shellRW.setUserLoginInfo("pl3text", pl3amount);
						PublicMethod.myOutput("-------------------handle------"
								+ shellRW.getUserLoginInfo("pl3text"));
					}
					if (!dltamount.equals("")) {
						shellRW.setUserLoginInfo("dlttext", dltamount);
						PublicMethod.myOutput("-------------------handle------"
								+ shellRW.getUserLoginInfo("dlttext"));
					}
				} else if (iCurrentBtFlag == -2) {

					shellRW.setUserLoginInfo("ssqtext", "");
					shellRW.setUserLoginInfo("fc3dtext", "");
					shellRW.setUserLoginInfo("qlctext", "");

					shellRW.setUserLoginInfo("pl3text", "");
					shellRW.setUserLoginInfo("dlttext", "");
				} else {

					int iTempIcon = (iCurrentId - 100) % 3;
					if (iTempIcon == 0) {// ����
						PublicMethod.myOutput("------fff---" + iCurrentId + " "
								+ ruyipackage_str_subscribe);
						int iTempLine = (iCurrentId - 100) / 3;
						if (iTempLine == 0) {
							shellRW.setUserLoginInfo("ssqtext", ""
									+ ruyipackage_str_subscribe);
						} else if (iTempLine == 1) {
							shellRW.setUserLoginInfo("fc3dtext", ""
									+ ruyipackage_str_subscribe);
						} else if (iTempLine == 2) {
							shellRW.setUserLoginInfo("qlctext", ""
									+ ruyipackage_str_subscribe);
						}
						// wangyl 8.27 add pl3 and dlt
						else if (iTempLine == 3) {
							shellRW.setUserLoginInfo("pl3text", ""
									+ ruyipackage_str_subscribe);
						} else if (iTempLine == 4) {
							shellRW.setUserLoginInfo("dlttext", ""
									+ ruyipackage_str_subscribe);
						}
					} else if (iTempIcon == 1) { // �޸�
						int iTempLine = (iCurrentId - 100) / 3;
						if (iTempLine == 0) {
							shellRW.setUserLoginInfo("ssqtext", ""
									+ ruyipackage_str);
						} else if (iTempLine == 1) {
							shellRW.setUserLoginInfo("fc3dtext", ""
									+ ruyipackage_str);
						} else if (iTempLine == 2) {
							shellRW.setUserLoginInfo("qlctext", ""
									+ ruyipackage_str);
						}

						else if (iTempLine == 3) {
							shellRW.setUserLoginInfo("pl3text", ""
									+ ruyipackage_str);
						} else if (iTempLine == 4) {
							shellRW.setUserLoginInfo("dlttext", ""
									+ ruyipackage_str);
						}
					} else if (iTempIcon == 2) { // �˶�
						int iTempLine = (iCurrentId - 100) / 3;
						if (iTempLine == 0) {
							shellRW.setUserLoginInfo("ssqtext", "");
						} else if (iTempLine == 1) {
							shellRW.setUserLoginInfo("fc3dtext", "");
						} else if (iTempLine == 2) {
							shellRW.setUserLoginInfo("qlctext", "");
						}

						else if (iTempLine == 3) {
							shellRW.setUserLoginInfo("pl3text", "");
						} else if (iTempLine == 4) {
							shellRW.setUserLoginInfo("dlttext", "");
						}
					}
				}
				showRuyiPackageListView();
				break;
			case 1:
				progressdialog.dismiss();
				if(isFinishing() == false){
				    Accoutdialog.getInstance().createAccoutdialog(RuyiHelper.this, getResources().getString(R.string.goucai_Account_dialog_msg).toString());
				}
				break;
			case 2:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ѽ�����", Toast.LENGTH_LONG)
						.show();
				break;
			case 3:
				progressdialog.dismiss();
				Toast
						.makeText(getBaseContext(), "ϵͳ���㣬���Ժ�",
								Toast.LENGTH_LONG).show();
				break;
			case 4:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�޿����߼�����", Toast.LENGTH_LONG)
						.show();
				break;

			case 6:
				// //��Ҫ���AlertDialog��ʾ�û���¼�ɹ�
				progressdialog.dismiss();
				if (type06.equals("ssq") || type06.equals("fc3d")
						|| type06.equals("qlc")) {
					Toast
							.makeText(getBaseContext(), "Ͷע�ɹ���",
									Toast.LENGTH_LONG).show();
				} else if (type06.equals("pl3") || type06.equals("cjdlt")) {
					Toast.makeText(getBaseContext(), "Ͷע������",
							Toast.LENGTH_LONG).show();
				}
				showListView(ID_XINGYUNXUANHAO); // zlm 7.28 �����޸� ��������ѡ�����б�
				break;
			case 7:
				progressdialog.dismiss();

				progressdialog.dismiss();// δ��¼
				Intent intent1 = new Intent(UserLogin.UNSUCCESS);
				sendBroadcast(intent1);
				ShellRWSharesPreferences shellRW1 = new ShellRWSharesPreferences(
						RuyiHelper.this, "addInfo");
				shellRW1.setUserLoginInfo("sessionid", "");
				Intent intent2 = new Intent(RuyiHelper.this, UserLogin.class);
				startActivity(intent2);

				break;
			case 8:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)
						.show();
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;

			case 10:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�ײ��Ѷ��ƣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 11:
				progressdialog.dismiss();
				if(isFinishing() == false){
					Accoutdialog.getInstance().createAccoutdialog(RuyiHelper.this, getResources().getString(R.string.goucai_Account_dialog_msg).toString());
				}
				break;
			case 12:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ײ�ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 13:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�û����ײͼ�¼��", Toast.LENGTH_LONG)
						.show();
				break;
			case 14:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�˶�ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 15:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�ײ��޸�ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 16:
				// ��ѯ��֮���޸Ķ��� �³� 20100729
				progressdialog.dismiss();
				if (viewId == 101 || viewId == 104 || viewId == 107
						|| viewId == 110 || viewId == 113) {
					showRuyiPackageEdit(viewId);

				}
				// �˶�
				if (viewId == 102 || viewId == 105 || viewId == 108
						|| viewId == 111 || viewId == 114) {

					showRuyiPackageUnSubscribe(viewId);
				}

				break;
			case 17:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷע�ɹ���", Toast.LENGTH_LONG)
						.show();
				break;
			case 18:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "��ƱͶע�У�", Toast.LENGTH_LONG)
						.show();
				break;
			// wangyl 8.30 ����ײͶ����ӿ�

			case 20:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�ںŲ��Ϸ���", Toast.LENGTH_LONG)
						.show();
				break;
			case 21:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�˻��쳣��", Toast.LENGTH_LONG)
						.show();
				break;
			case 22:
				Toast.makeText(RuyiHelper.this,
						RuyiHelper.this.getString(R.string.feedbacksuccess),
						Toast.LENGTH_SHORT).show();
				progressdialog.dismiss();
				showListView(ID_MORELISTVIEW);
				break;
			case 23:
				Toast.makeText(RuyiHelper.this,
						RuyiHelper.this.getString(R.string.feedbackfield),
						Toast.LENGTH_SHORT).show();
				progressdialog.dismiss();
				break;
			}
			//				
		}
	};

	/* ����Ϊ�û�����-�˻���ϸ��̬���� */
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				progressdialog.dismiss();
				showUserCenterBalanceInquiry();
				break;
			case 1:
				progressdialog.dismiss();
				showUserCenterWinningCheckNew();

				break;
			case 2:
				progressdialog.dismiss();
				showUserCenterBettingDetailsNew();

				break;
			case 3:
				progressdialog.dismiss();
				iQuitFlag = 30; // ������ 7.5 �����޸ģ��������û�����
				showUserCenterAccountDetails();
				break;
			case 4:
				progressdialog.dismiss();
				iQuitFlag = 30; // ������ 7.5 �����޸ģ����������б�
				showUserCenterGiftCheckNew();
				break;
			case 5:
				progressdialog.dismiss();
				Toast.makeText(RuyiHelper.this, "�����޸ĳɹ���", Toast.LENGTH_SHORT)
						.show();
				break;
			case 6:
				progressdialog.dismiss();

				showUserCenterAddLotteryQueryNew();

				break;
			case 7:
				progressdialog.dismiss();
				// 30���Ӻ��ٴε�¼ �³� 20100719
				type = "";
				Intent intentSession = new Intent(RuyiHelper.this,
						UserLogin.class);
				startActivityForResult(intentSession, 0);

				break;
			case 8:
				progressdialog.dismiss();
				Toast.makeText(RuyiHelper.this, "ϵͳ���㣡", Toast.LENGTH_SHORT)
						.show();
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)
						.show();
				break;
			case 10:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "��ѯʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 11:
				progressdialog.dismiss();
				Toast.makeText(RuyiHelper.this, "ԭ�������", Toast.LENGTH_SHORT)
						.show();
				break;
			case 12:
				progressdialog.dismiss();
				Toast.makeText(RuyiHelper.this, "��¼Ϊ�գ�", Toast.LENGTH_SHORT)
						.show();
				break;
			case 13:
				Toast.makeText(RuyiHelper.this, "�����������벻��ȷ��",
						Toast.LENGTH_SHORT).show();
				break;
			// ������Ϣ���� 14 �û������ӱ�ǩ�����ɹ� 15 û�м�¼ʱ�Ĵ��� 20100803 �³�
			case 14:
				progressdialog.dismiss();
				iDetail = true;
				AccountList(accountView);
				break;
			case 15:
				progressdialog.dismiss();
				iDetail = false;
				AccountList(accountView);
				break;
			case 16:
				progressdialog.dismiss();
				iPage = Separate * 20;
				JsonBetting(jsonArray);

				iTotaPage = bettingVector.size();
				showUserCenterBettingDetailsNewTab(iPage, betView);
				break;
			case 17:
				progressdialog.dismiss();
				iPage = Separate * 20;
				JsonWinningSelect(jsonArray);

				iTotaPage = WinningVector.size();
				showUserCenterWinningCheckNewTab(iPage, winningView);
				break;
			case 18:
				progressdialog.dismiss();
				showSubExpertAnalyzeListViewTwo();
				break;
			case 19:
				progressdialog.dismiss();
				Toast
						.makeText(getBaseContext(), "��ʱû��ר�ҷ�����",
								Toast.LENGTH_LONG).show();
				break;
			case 20:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)
						.show();
				break;
			case 21:
				progressdialog.dismiss();
				iPage = Separate * 20;
				JsonAddNumSelect(jsonArray);

				iTotaPage = AddNumVector.size();
				showUserCenterAddLotteryQueryNewTab(iPage, addNumView);
				break;
			case 22:
				progressdialog.dismiss();
				iPage = Separate * 20;
				JsonGiftSelect(jsonArray);

				iTotaPage = GiftVector.size();
				showUserCenterGiftCheckNewTab(iPage, presentedView);
				break;
			case 23:
				progressdialog.dismiss();
				iPageGifted = SeparateGifted * 20;
				JsonGiftedSelect(jsonArray_gifted);

				iTotaPageGifted = GiftedVector.size();
				showUserCenterGiftedCheckNewTab(iPageGifted, receivedView);
				break;
			case 24:
				progressdialog.dismiss();
				showUserCenterAddLotteryQueryNewTab(specialPage, addNumView);
				b_canceltranking.setClickable(false);
				Toast.makeText(getBaseContext(), "׷��ȡ���ɹ���", Toast.LENGTH_LONG)
						.show();
				break;
			case 25:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "׷�ż�¼�����ڣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 26:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "û��׷�ŵ�����", Toast.LENGTH_LONG)
						.show();
				break;
			case 27:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "׷���Ѿ�ȡ����", Toast.LENGTH_LONG)
						.show();
				break;
			case 28:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "׷�Žⶳʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 29:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "׷��ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 30:// ���ִ���
				progressdialog.dismiss();
				getMoneyDialog();
				break;
			case 31:// �޸����ִ���
				progressdialog.dismiss();
				changeDialog();
				break;
			case 32:// �����
				progressdialog.dismiss();
				waitDialog();
				break;
			case 33:// �����
				progressdialog.dismiss();
				waitingDialog();
				break;
			case 34:// ����
				progressdialog.dismiss();
				failDialog();
				break;
			case 35:// ��ͨ�����
				progressdialog.dismiss();
				successDialog();
				break;
			case 36:// �Ѿ�ȡ��
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�Ѿ�ȡ����", Toast.LENGTH_LONG)
						.show();
				break;
			case 37:// 
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "ȡ��ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 38:// �޸ĳɹ�
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�޸ĳɹ���", Toast.LENGTH_LONG)
						.show();
				break;
			case 39:// �޸�ʧ��
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�޸�ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 40:// ���ֳɹ�
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "����������", Toast.LENGTH_LONG)
						.show();
				break;
			case 41:// δ��¼
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "δ��¼��", Toast.LENGTH_LONG)
						.show();
				break;
			case 42:// ��������
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�ύ��Ϣ����", Toast.LENGTH_LONG)
						.show();
				break;
			case 43:// ����ʧ�� �û��˻����������С�����ֽ��
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "����ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 44:// �û��˻����������С�����ֽ��
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�û��˻����������С�����ֽ�",
						Toast.LENGTH_LONG).show();
				break;
			case 45:// �û��˻������ֽ��������
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�û��˻������ֽ�������",
						Toast.LENGTH_LONG).show();
				break;
			case 46:// �û�����������ȥ������С�����ֽ��
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�û�����������ȥ������С�����ֽ�",
						Toast.LENGTH_LONG).show();
				break;
			case 47:// �û�����������ȥ������С�����ֽ��
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�û�����������ȥ������С�����ֽ�",
						Toast.LENGTH_LONG).show();
				break;
			case 48:// �û�����������
				progressdialog.dismiss();
				Toast
						.makeText(getBaseContext(), "�û��������㣡",
								Toast.LENGTH_LONG).show();
				break;
			}
		}
	};
	// ������ 7.5 �����޸ģ�����˳����Ĵ��롪���������
	protected int iQuitFlag = 0; // �����˳�

	// protected boolean iCallOnKeyDownFlag=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		bettingVector = new Vector();// 8.13cc
		WinningVector = new Vector();
		AddNumVector = new Vector();
		GiftVector = new Vector();
		GiftedVector = new Vector();
		super.onCreate(savedInstanceState);
		// ��ʾ�����ࡱ�б�
	//	showListView(ID_MORELISTVIEW);

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		switch(Constants.RUYIHELPERSHOWLISTTYPE){
		case 0:
	    showListView(ID_MORELISTVIEW);
	    break;
		case 1:
		showListView(ID_XINGYUNXUANHAO);
		
		break;
		case 2:
		showListView(ID_USERCENTER);
		
		break;
		case 3:
		showFeedBack();
		
		break;
		case 4:
		showListView(ID_RUYIHELPERLISTVIEW);
		
		break;
		case 5:
	    showInfo(ID_AUTHORIZING);
	    
		break;
		default:
			
			break;
			}
	}
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	// TODO Auto-generated method stub
    	super.onWindowFocusChanged(hasFocus);
    	if(hasFocus==false){
    		Constants.RUYIHELPERSHOWLISTTYPE=6;
    	}
    }
    
   
	// �б�ѡ��
	void showListView(int listviewid) {
		switch (listviewid) {
		// �����ࡱ�б�
		case ID_MORELISTVIEW:
			iQuitFlag = 0; // ������ 7.5 �����޸ģ������˳�
			// iCallOnKeyDownFlag=false;
			showMoreListView();
			break;
		// ר�ҷ����б�
		case ID_EXPERTANALYZE:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			showExpertAnalyzeListView();
			break;
		// ���������б�֮���������б�
		case ID_OPERATIONASSISTANTLISTVIEW:
			iQuitFlag = 40; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			showOperationAssistantListView();
			break;
		// �û������б�
		case ID_USERCENTER:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			showUserCenterListView();
			showAccountListView();
			break;
		// ���⴫���б�
		case ID_RUYICHUANQING:
			iQuitFlag = 90;
			ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
					RuyiHelper.this, "addInfo");
			String sessionid = pre.getUserLoginInfo("sessionid");
			showRuYiChuanQing();

			break;
		// �����ײ��б�
		case ID_RUYITAOCAN:
			iQuitFlag = 90;
			iCurrentBtFlag = -1;
			// showDialog(DIALOG1_KEY);
			ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
					RuyiHelper.this, "addInfo");
			sessionid = shellRW.getUserLoginInfo("sessionid");
			if (sessionid == null || sessionid.equals("")) {
				iCurrentBtFlag = -2;
				Message msg = new Message();
				msg.what = 0;
				handler1.sendMessage(msg);
			}
			setFlag();
			break;
		// ����ѡ���б�
		case ID_XINGYUNXUANHAO:
			iQuitFlag = 90;
			showCLLNMainListView();
			break;
		// ��ɫͶע�б�
		case ID_TESETOUZHU:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			showEspecialListView();
			break;
		// ��ѯ��Ϣ�б�
		case ID_CHACKINFO:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			showUserCenterListView();
			break;
		// �˻������б�
		case ID_ACOUNT:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			showAccountListView();
			break;
		// ���������б�
		case ID_RUYIHELPERLISTVIEW:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			showRuyiHelperListView();
			break;
		// ϵͳ����
		case ID_XITONGSHEZHI:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			showXiTongSheZhi();
			break;
		}
	}

	// ������ 7.5 �����޸ģ�����˳����
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case 4: {
			break;
		}
			// ������ 7.8 �����޸ģ�����µ��ж�
		case 0x12345678: {
			/*
			 * if(iCallOnKeyDownFlag==false){ iCallOnKeyDownFlag=true;}
			 */
			switch (iQuitFlag) {
			case 0:
				ExitDialogFactory.createExitDialog(this);
				break;
			case 10:
				setContentView(R.layout.ruyihelper_listview);
				showListView(ID_MORELISTVIEW);
				break;
			case 20:
				setContentView(R.layout.expert_analyze_main_layout);
				showListView(ID_EXPERTANALYZE);
				break;
			case 30:
				setContentView(R.layout.usercenter_listview);
				showListView(ID_USERCENTER);
				break;
			case 40:
				setContentView(R.layout.ruyihelper_listview_ruyihelper);
				showListView(ID_RUYIHELPERLISTVIEW);
				break;
			case 80:
				setContentView(R.layout.choose_luck_lottery_num_main);
				showListView(ID_XINGYUNXUANHAO);
				break;
			case 70:
				setContentView(R.layout.usercenter_listview);
				showListView(ID_ACOUNT);
				break;
			case 90:
				setContentView(R.layout.ruyihelper_listview);
				showListView(ID_MORELISTVIEW);
				break;
			case 100:
				popupwindow.dismiss();
				iQuitFlag=90;
				break;
			}
			break;
		}
		}
		return false;
		// return super.onKeyDown(keyCode, event);
	}

	// ������ 7.5 �����޸ģ�����˳����
	public void onCancelClick() {
		// TODO Auto-generated method stub
		// iCallOnKeyDownFlag=false;
	}

	// ������ 7.5 �����޸ģ�����˳����
	public void onOkClick(int[] nums) {
		// TODO Auto-generated method stub
		// �˳�
		this.finish();
	}

	// �����ࡱѡ���б�
	void showMoreListView() {

		setContentView(R.layout.ruyihelper_listview);

		// ����Դ
		list = getListForMoreAdapter();

		ListView listview = (ListView) findViewById(R.id.ruyihelper_listview_id);

		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.ruyihelper_listview_icon_itemnew, new String[] {
						TITLE, IICON }, new int[] { R.id.ruyihelper_icon_text,
						R.id.ruyihelper_iicon });
		listview.setAdapter(adapter);
		PublicMethod.setmydividerHeight(listview);

		/* �б�ĵ����ı��� */
		Drawable drawable = getResources().getDrawable(
				R.drawable.list_selector_red);
		listview.setSelector(drawable);
		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				String str = text.getText().toString();
				/* ���⴫�� */
				if (getString(R.string.ruyichuangqing).equals(str)) {
					showListView(ID_RUYICHUANQING);
				}
				/* �����ײ� */
				if (getString(R.string.ruyitaocan).equals(str)) {
					showListView(ID_RUYITAOCAN);
				}
				/* ����ѡ�� */
				if (getString(R.string.xingyunxuanhao).equals(str)) {
					showListView(ID_XINGYUNXUANHAO);
				}
				/* ר�Ҽ��� */
				if (getString(R.string.zhuanjiafenxi).equals(str)) {
					showListView(ID_EXPERTANALYZE);
				}
				/* �û����� */
				if (getString(R.string.yonghuzhongxin).equals(str)) {
					showListView(ID_USERCENTER);
				}
				/* �������� */
				if (getString(R.string.bangzhuzhongxin).equals(str)) {
					showListView(ID_RUYIHELPERLISTVIEW);
				}
				/* ϵͳ���� */
				if (getString(R.string.xitongshezhi).equals(str)) {
					showListView(ID_XITONGSHEZHI);
				}
				/* ��˾���� */
				if (getString(R.string.gongsijianjie).equals(str)) {
					showInfo(ID_AUTHORIZING);
				}
				/* �û����� */
				if (getString(R.string.yonghufankui).equals(str)) {
					showFeedBack();
				}
			}

		};
		listview.setOnItemClickListener(clickListener);

	}

	// ��ʾר�ҷ���
	void showExpertAnalyzeListView() {
		setContentView(R.layout.expert_analyze_main_layout);
		PublicMethod.myOutput("-----------Analyze!----------------");
		// ������ 7.3 �����޸ģ���Button����ImageButton
		ImageView back = (ImageView) findViewById(R.id.expert_img_view_return);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showListView(ID_MORELISTVIEW);
			}
		});
		ListView listview = (ListView) findViewById(R.id.expert_analyze_listview_id);

		ExpertAnalyzeEfficientAdapter adapterExpertAnalyze = new ExpertAnalyzeEfficientAdapter(
				this);
		listview.setAdapter(adapterExpertAnalyze);

		PublicMethod.setmydividerHeight(listview);
		/* �б�ĵ����ı��� */
		Drawable drawable = getResources().getDrawable(
				R.drawable.list_selector_red);
		listview.setSelector(drawable);
		PublicMethod.myOutput("-----------Analyze!----------------");
		// ���õ������
		OnItemClickListener clickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				typeName = (TextView) view
						.findViewById(R.id.expert_analyze_typename_id);
				String str = typeName.getText().toString();
				// ���˫ɫ����ת��˫ɫ�����б��� ������ 7.4 �����޸ģ���ת��ר�ҷ������б�
				if (getString(R.string.shuangseqiufenxi).equals(str)) {
					iQuitFlag = 20; // ������ 7.5 �����޸ģ����������б�

					// �����ȡ˫ɫ��ר�ҷ��������� 2010/7/4 �³�
					// �������Ϊ�� ʱ�Ĵ��� 20100711
					showDialog(DIALOG1_KEY); // ��ʾ������ʾ��
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								showDialog(DIALOG1_KEY);
								String[] analysis = expertAnalysis();
								if (analysis[0].equals("06007")|| analysis[0].equals("06008")) {
									Message msg = new Message();
									msg.what = 19;
									handler.sendMessage(msg);
								} else if (analysis[0].equals("00")) {
									Message msg = new Message();
									msg.what = 20;
									handler.sendMessage(msg);
								} else {
									String[] analysis_title = { analysis[0],analysis[2], analysis[4] };
									String[] analysis_content = { analysis[1],analysis[3], analysis[5] };
									mTitles = analysis_title;
									mDialogue = analysis_content;
									Message msg = new Message();
									msg.what = 18;
									handler.sendMessage(msg);
									// showSubExpertAnalyzeListViewTwo();
								}
							} catch (Exception e) {
								Message msg = new Message();
								msg.what = 19;
								handler.sendMessage(msg);
							}
						}
					});
					t.start();
					
				}
				// �������3D��ת������3D���б��� ������ 7.4 �����޸ģ���ת��ר�ҷ������б�
				if (getString(R.string.fucaifenxi).equals(str)) {
					iQuitFlag = 20; // ������ 7.5 �����޸ģ����������б�
					showDialog(DIALOG1_KEY);
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
					try {		
						String[] analysis = expertAnalysis();
						if (analysis[0].equals("06007")
								|| analysis[0].equals("06008")) {
							Message msg = new Message();
							msg.what = 19;
							handler.sendMessage(msg);
						} else if (analysis[0].equals("00")) {
							Message msg = new Message();
							msg.what = 20;
							handler.sendMessage(msg);
						} else {
							for (int i = 0; i < 10; i++) {
								PublicMethod.myOutput("------------------"
										+ analysis[i]);
							}
							String[] analysis_title = { analysis[6],
									analysis[8], analysis[10] };
							String[] analysis_content = { analysis[7],
									analysis[9], analysis[11] };
							mTitles = analysis_title;
							mDialogue = analysis_content;
							Message msg = new Message();
							msg.what = 18;
							handler.sendMessage(msg);
							// showSubExpertAnalyzeListViewTwo();
						}
					} catch (Exception e) {
						Message msg = new Message();
						msg.what = 19;
						handler.sendMessage(msg);
						e.printStackTrace();
					}
						}
				     });
					t.start();
				}

				if (getString(R.string.qilecaifenxi).equals(str)) {
					iQuitFlag = 20; // ������ 7.5 �����޸ģ����������б�
					showDialog(DIALOG1_KEY); // ��ʾ������ʾ��
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
					try {
						String[] analysis = expertAnalysis();
						if (analysis[0].equals("06007")
								|| analysis[0].equals("06008")) {
							Message msg = new Message();
							msg.what = 19;
							handler.sendMessage(msg);
						} else if (analysis[0].equals("00")) {
							Message msg = new Message();
							msg.what = 20;
							handler.sendMessage(msg);
						} else {
							for (int i = 0; i < 10; i++) {
								PublicMethod.myOutput("------------------"
										+ analysis[i]);
							}
							String[] analysis_title = { analysis[12],
									analysis[14], analysis[16] };
							String[] analysis_content = { analysis[13],
									analysis[15], analysis[17] };
							mTitles = analysis_title;
							mDialogue = analysis_content;
							Message msg = new Message();
							msg.what = 18;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						Message msg = new Message();
						msg.what = 19;
						handler.sendMessage(msg);
						e.printStackTrace();
					}
						}
				     });
					t.start();
				}
				// zlm ������
				if (getString(R.string.pailiesanfenxi).equals(str)) {
					iQuitFlag = 20;
					showDialog(DIALOG1_KEY); // ��ʾ������ʾ��
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
					try {
						String[] analysis = expertAnalysis();
						if (analysis[0].equals("06007")
								|| analysis[0].equals("06008")) {
							Message msg = new Message();
							msg.what = 19;
							handler.sendMessage(msg);
						} else if (analysis[0].equals("00")) {
							Message msg = new Message();
							msg.what = 20;
							handler.sendMessage(msg);
						} else {
							for (int i = 0; i < 10; i++) {
								PublicMethod.myOutput("------------------"
										+ analysis[i]);
							}
							String[] analysis_title = { analysis[18],
									analysis[20], analysis[22] };
							String[] analysis_content = { analysis[19],
									analysis[21], analysis[23] };
							mTitles = analysis_title;
							mDialogue = analysis_content;
							Message msg = new Message();
							msg.what = 18;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						Message msg = new Message();
						msg.what = 19;
						handler.sendMessage(msg);
						e.printStackTrace();
					}
				}
		     });
			t.start();
				}
				// zlm ��������͸
				if (getString(R.string.chaojidaletoufenxi).equals(str)) {
					iQuitFlag = 20;
					showDialog(DIALOG1_KEY); // ��ʾ������ʾ��
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
					   try {
						String[] analysis = expertAnalysis();
						if (analysis[0].equals("06007")
								|| analysis[0].equals("06008")) {
							Message msg = new Message();
							msg.what = 19;
							handler.sendMessage(msg);
						} else if (analysis[0].equals("00")) {
							Message msg = new Message();
							msg.what = 20;
							handler.sendMessage(msg);
						} else {
							for (int i = 0; i < 10; i++) {
								PublicMethod.myOutput("------------------"
										+ analysis[i]);
							}
							String[] analysis_title = { analysis[24],
									analysis[26], analysis[28] };
							String[] analysis_content = { analysis[25],
									analysis[27], analysis[29] };
							mTitles = analysis_title;
							mDialogue = analysis_content;
							Message msg = new Message();
							msg.what = 18;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						Message msg = new Message();
						msg.what = 19;
						handler.sendMessage(msg);
						e.printStackTrace();
					}
				}
            });
	          t.start();
				}
				// ʤ����
				if (getString(R.string.shengfucaifenxi).equals(str)) {
					iQuitFlag = 20;
					// showSubExpertAnalyzeListViewTwo();
					showDialog(DIALOG1_KEY); // ��ʾ������ʾ��
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
					try {
						showDialog(DIALOG1_KEY);
						String[] analysis = expertAnalysis();
						if (analysis[0].equals("06007")
								|| analysis[0].equals("06008")) {
							Message msg = new Message();
							msg.what = 19;
							handler.sendMessage(msg);
						} else if (analysis[0].equals("00")) {
							Message msg = new Message();
							msg.what = 20;
							handler.sendMessage(msg);
						} else {
							for (int i = 0; i < 10; i++) {
								PublicMethod.myOutput("------------------"
										+ analysis[i]);
							}
							String[] analysis_title = { analysis[30] ,analysis[32],analysis[34]};
							String[] analysis_content = { analysis[31] ,analysis[33],analysis[35]};
							mTitles = analysis_title;
							mDialogue = analysis_content;
							Message msg = new Message();
							msg.what = 18;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						Message msg = new Message();
						msg.what = 19;
						handler.sendMessage(msg);
						e.printStackTrace();
					}
						}
		            });
			          t.start();
				}
			}

		};

		listview.setOnItemClickListener(clickListener);
	}

	// ��ʾ���������б�
	void showRuyiHelperListView() {

		setContentView(R.layout.ruyihelper_listview_ruyihelper);
		ListView listview = (ListView) findViewById(R.id.ruyihelper_listview_ruyihelper_id);
		// ���� ������ 7.3 �����޸ģ���Button����ImageButton
		// btnreturn = new ImageButton(this);
		ImageView btnreturn = (ImageView) findViewById(R.id.ruyizhushou_btn_return);

		btnreturn.setImageResource(R.drawable.return_btn);
		btnreturn.setBackgroundColor(Color.TRANSPARENT);

		btnreturn.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
				showListView(ID_MORELISTVIEW);
			}

		});

		// ����Դ
		list = getListForRuyiHelperAdapter();
		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.ruyihelper_listview_icon_item, new String[] { TITLE },
				new int[] { R.id.ruyihelper_icon_text });
		listview.setAdapter(adapter);

		PublicMethod.setmydividerHeight(listview);

		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				String str = text.getText().toString();

				/* ��������֮�淨���� */
				if (getString(R.string.ruyihelper_gameIntroduction).equals(str)) {
					showInfo(ID_GAMEINTRODUTION);
				}
				// /* ��������֮�������� */

				// }
				/* ��������֮�н��콱 */
				if (getString(R.string.ruyihelper_zhongjiang).equals(str)) {
					showInfo(ID_ZHONGJIANG);
				}
				/* ��������֮�������� */
				if (getString(R.string.ruyihelper_goucailiucheng).equals(str)) {
					showInfo(ID_GOUCAI);
				}
				/* ��������֮�����һ� */
				if (getString(R.string.ruyihelper_mimazhaohui).equals(str)) {
					showDialog(DIALOG_FORGET_PASSWORD);
				}
				/* ��������֮�ͷ��绰 */
				if (getString(R.string.ruyihelper_kefudianhua).equals(str)) {
					showInfo(ID_KEFUDIANHUA);
				}
			}

		};
		listview.setOnItemClickListener(clickListener);

	}

	// ��ʾ����������Ĳ��������б�
	void showOperationAssistantListView() {

		setContentView(R.layout.usercenter_operationhelper_listview);

		List<String> list = new ArrayList<String>();
		list.add(getString(R.string.ruyihelper_userRegister));/* �û�ע�� */
		list.add(getString(R.string.ruyihelper_userLogin));/* �û���¼ */
		list.add(getString(R.string.ruyihelper_userBetting));/* �û�Ͷע */
		list.add(getString(R.string.ruyihelper_presentLottery));/* ���Ͳ�Ʊ */
		list.add(getString(R.string.ruyihelper_accountWithdraw));/* �˺����� */
		list.add(getString(R.string.ruyihelper_balanceInquiry)); /* ����ѯ */

		// wangyl 7.23 �޸ı���ͷ��ز���

		ListView listview = (ListView) findViewById(R.id.operation_listview_id);

		// ���� ������ 7.3 �����޸ģ���Button ����ImageButton
		// btnreturn = new ImageButton(this);
		ImageView btnreturn = (ImageView) findViewById(R.id.operation_btn_return);

		btnreturn.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
				showListView(ID_RUYIHELPERLISTVIEW);
			}

		});
		// ��ӷ���view

		// ����������Դ
		List<Map<String, Object>> listadapter = new ArrayList<Map<String, Object>>(
				1);
		;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(TITLE, list.get(i));
				listadapter.add(map);
			}

		}
		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, listadapter,
				R.layout.ruyihelper_listview_string_item,
				new String[] { TITLE },
				new int[] { R.id.ruyihelper_string_text });
		listview.setAdapter(adapter);

		PublicMethod.setmydividerHeight(listview);
		/* �б�ĵ����ı��� */
		Drawable drawable = getResources().getDrawable(
				R.drawable.list_selector_red);
		listview.setSelector(drawable);
		listview.setOnItemClickListener(new ListView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				text = (TextView) view
						.findViewById(R.id.ruyihelper_string_text);
				String title = text.getText().toString();
				/* �û�ע�� */
				if (getString(R.string.ruyihelper_userRegister).equals(title)) {
					showListViewDialog(ID_USERREGISTER);
				}
				/* �û���¼ */
				if (getString(R.string.ruyihelper_userLogin).equals(title)) {
					showListViewDialog(ID_USERLOGIN);
				}
				/* �û�Ͷע */
				if (getString(R.string.ruyihelper_userBetting).equals(title)) {
					showListViewDialog(ID_USERBETTING);
				}
				/* ���Ͳ�Ʊ */
				if (getString(R.string.ruyihelper_presentLottery).equals(title)) {
					showListViewDialog(ID_PRESENTLOTTREY);
				}
				/* �˺����� */
				if (getString(R.string.ruyihelper_accountWithdraw)
						.equals(title)) {
					showListViewDialog(ID_ACCOUNTWITHDRAW);
				}
				/* ����ѯ */
				if (getString(R.string.ruyihelper_balanceInquiry).equals(title)) {
					showListViewDialog(ID_BALANCEINQUIRY);
				}

			}

		});
	}

	/**
	 * ר�ҷ������б� ������ 7.4 ������ӣ�ר�ҷ������б�
	 * 
	 * @param aExpertAnalyzeInfo
	 */
	private void showSubExpertAnalyzeListViewTwo() {
		/*
		 * expertId = aExpertId; specifyExpertAnalyzeInfo = aExpertAnalyzeInfo;
		 */
		iQuitFlag = 20;// �������б�
	
		setContentView(R.layout.expert_analyze_specify_listview);
		ListView listview = (ListView) findViewById(R.id.expert_analyze_specify_listview_id);

		ImageButton returnBtn = new ImageButton(this);
		returnBtn = (ImageButton) findViewById(R.id.expert_analyze_specify_return_btn);
		returnBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			showExpertAnalyzeListView();
			}
		});
		adapterExpert = new SpeechListAdapter(this);
	
		listview.setAdapter(adapterExpert);
	//	PublicMethod.setmydividerHeight(listview);
		listview.setDividerHeight(0);
		listview.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
	
			}
		});
		for (int i = 0; i < mTitles.length; i++) {
			// showExpertAnalyzeDialog(expertId);
			adapterExpert.toggle(i);

		}
	}

	// ������ 7.4 ������ӣ�ר�ҷ������б� ������
	private class SpeechListAdapter extends BaseAdapter {
		private Context mContext;

		public SpeechListAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			return mTitles.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			SpeechView sv;
			if (convertView == null) {
				sv = new SpeechView(mContext, mTitles[position],
						mDialogue[position], mExpanded[position]);
				// convertView =
				// mInflater.inflate(R.layout.notice_prizes_main_layout, null);
			} else {
				sv = (SpeechView) convertView;// .findViewById(R.id.expert_analyze_specify_listview_layout_text_id);
				sv.setTitle(mTitles[position]);
				sv.setDialogue(mDialogue[position]);
				sv.setExpanded(mExpanded[position]);
			}
			
//			sv.setBackgroundColor(Integer.valueOf(colors[position]));

			return sv;
		}

		public void toggle(int position) {
			mExpanded[position] = !mExpanded[position];
			notifyDataSetChanged();
		}

		private boolean[] mExpanded = { false, false, false, false, false,
				false, false, false };

	}

	// ������ 7.4 ������ӣ�ר�ҷ������б�����
	private class SpeechView extends LinearLayout {
		public SpeechView(Context context, String title, String dialogue,
				boolean expanded) {
			super(context);

			this.setOrientation(VERTICAL);

			// Here we build the child views in code. They could also have
			// been specified in an XML file.

			mTitle = new TextView(context);
			mTitle.setText(title);
			mTitle.setHeight(40);
			mTitle.setTextColor(Color.BLACK);
			mTitle.setTypeface(Typeface.SERIF);
			mTitle.setPadding(0, 10, 0, 0);
			mTitle.setGravity(Gravity.CENTER_HORIZONTAL);
			mTitle.setTextAppearance(context,
					android.R.attr.textAppearanceMedium);
			addView(mTitle, new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// ����ַ���
			String[] str = dialogue.split("��");
			String words_new = "";
			for (int i = 0; i < str.length; i++) {
				if (i != str.length - 1) {
					words_new += str[i] + "\n";
				} else {
					words_new += str[i];
				}
			}
			mDialogue = new TextView(context);
			mDialogue.setText(words_new);
			mDialogue.setTextColor(Color.BLUE);
			mDialogue.setPadding(10, 0, 0, 20);
			addView(mDialogue, new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			this.setBackgroundResource(R.drawable.list_luck_bg);
			mDialogue.setVisibility(expanded ? VISIBLE : GONE);
		}

		public void setTitle(String title) {
			mTitle.setText(title);
		}

		public void setDialogue(String words) {
			String[] str = words.split("��");
			String words_new = "";
			for (int i = 0; i < str.length; i++) {
				if (i != str.length - 1) {
					words_new += str[i] + "\n";
				} else {
					words_new += str[i];
				}
			}
			mDialogue.setText(words_new);
		}

		public void setExpanded(boolean expanded) {
			mDialogue.setVisibility(expanded ? VISIBLE : GONE);
		}

		private TextView mTitle;
		private TextView mDialogue;
	}


	// ��ɫͶע�б�
	void showEspecialListView() {
		setContentView(R.layout.usercenter_listview);
		TextView title = (TextView) findViewById(R.id.ruyipackage_title);
		title.setText(getString(R.string.tesetouzhu));
		ListView listview = (ListView) findViewById(R.id.usercenter_listview_id);
		ImageView btnreturn = (ImageView) findViewById(R.id.usercenter_btn_return);
		btnreturn.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
				showListView(ID_MORELISTVIEW);
			}

		});

		// ����Դ
		list = getListForEspecialAdapter();
		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.ruyihelper_listview_icon_item, new String[] { TITLE },
				new int[] { R.id.ruyihelper_icon_text });
		listview.setAdapter(adapter);
		PublicMethod.setmydividerHeight(listview);
		/* �б�ĵ����ı��� */
		Drawable drawable = getResources().getDrawable(
				R.drawable.list_selector_red);
		listview.setSelector(drawable);
		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				String str = text.getText().toString();
				iType = 0;

				/* ���⴫�� */
				if (getString(R.string.ruyichuangqing).equals(str)) {
					iType = 3;
					showListView(ID_RUYICHUANQING);
				}
				/* �����ײ� */
				if (getString(R.string.ruyitaocan).equals(str)) {
					iType = 4;
					showListView(ID_RUYITAOCAN);
				}
				/* ����ѡ�� */
				if (getString(R.string.xingyunxuanhao).equals(str)) {
					iType = 5;
					showListView(ID_XINGYUNXUANHAO);
				}
			}

		};

		listview.setOnItemClickListener(clickListener);
	}

	// ��ѯ��Ϣ�б�
	void showUserCenterListView() {

		setContentView(R.layout.usercenter_listview);
		ListView listview = (ListView) findViewById(R.id.usercenter_listview_id);
		// ���� ������ 7.3 �����޸ģ���Button ����ImageButton

		ImageView btnreturn = (ImageView) findViewById(R.id.usercenter_btn_return);

		btnreturn.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
				showListView(ID_MORELISTVIEW);
			}

		});

		// 7.23 wangyl �޸Ĳ���

		// ����Դ
		list = getListForUserCenterAdapter();
		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.usercenter_listview_item, new String[] { TITLE },
				new int[] { R.id.ruyihelper_icon_text });
		listview.setAdapter(adapter);
		PublicMethod.setmydividerHeight(listview);
		/* �б�ĵ����ı��� */
		Drawable drawable = getResources().getDrawable(
				R.drawable.list_selector_red);
		listview.setSelector(drawable);
		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				String str = text.getText().toString();
				iType = 0;

				ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
						RuyiHelper.this, "addInfo");
				phonenum = shellRW.getUserLoginInfo("phonenum");
				sessionid = shellRW.getUserLoginInfo("sessionid");
				if (sessionid == null || sessionid.equals("")) {
					Intent intentSession = new Intent(RuyiHelper.this,
							UserLogin.class);

					startActivityForResult(intentSession, 0);
				} else {
					UserCenterDetail();
				}
			}

		};

		listview.setOnItemClickListener(clickListener);

	}

	// �˻������б�
	void showAccountListView() {
		ListView listview = (ListView) findViewById(R.id.usercenter_listview_id_two);
		// ����Դ
		list = getListForAccountAdapter();
		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.usercenter_listview_item, new String[] { TITLE },
				new int[] { R.id.ruyihelper_icon_text });
		listview.setAdapter(adapter);
		PublicMethod.setmydividerHeight(listview);

		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				iType = 2;

				ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
						RuyiHelper.this, "addInfo");
				phonenum = shellRW.getUserLoginInfo("phonenum");
				sessionid = shellRW.getUserLoginInfo("sessionid");
				if (sessionid == null || sessionid.equals("")) {
					Intent intentSession = new Intent(RuyiHelper.this,
							UserLogin.class);
					startActivityForResult(intentSession, 0);
				} else {
					accountDetailTwo();
				}
			}

		};

		listview.setOnItemClickListener(clickListener);

	}

	// ��ÿ��������������� ��ѯ��Ϣ�б��Ӧѡ��
	void UserCenterDetail() {
		String str = text.getText().toString();
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		phonenum = shellRW.getUserLoginInfo("phonenum");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		// String userId=shellRW.getUserLoginInfo("userId");
		// ����ѯ
		if (getString(R.string.ruyihelper_balanceInquiry).equals(str)) {
			showDialog(DIALOG1_KEY);
			Thread t = new Thread(new Runnable() {
				public void run() {
					String error_code = "00";

					re = GetUserInfoInterface.findUserInfo(phonenum, sessionid);
					try {
						obj = new JSONObject(re);
						error_code = obj.getString("error_code");
					} catch (JSONException e) {

					}

					if (error_code.equals("0000")) {
						Message msg = new Message();
						msg.what = 0;
						handler.sendMessage(msg);

					} else if (error_code.equals("070002")) {
						Message msg = new Message();
						msg.what = 7;
						handler.sendMessage(msg);

					} else if (error_code.equals("4444")) {
						Message msg = new Message();
						msg.what = 8;
						handler.sendMessage(msg);

					} else if (error_code.equals("00")) {
						Message msg = new Message();
						msg.what = 9;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 10;
						handler.sendMessage(msg);
					}
				}
			});
			t.start();
		}
		// �н���ѯ
		if (getString(R.string.usercenter_winningCheck).equals(str)) {
			PublicMethod
					.myOutput("-------sessionid-usercenter_winningCheck-----"
							+ sessionid);
			PublicMethod.myOutput("-------sessionid-usercenter_winningCheck"
					+ phonenum);
			WinningVector.removeAllElements();
			iPage = 0;
			Separate = 0;
			showDialog(DIALOG1_KEY);
			Thread t = new Thread(new Runnable() {
				public void run() {
					String error_code = "00";
					try {
						String re = GetUserInfoInterface.winingSelectTC(
								phonenum, "1", "20", sessionid);
						try {
							JSONObject obj = new JSONObject(re);
							error_code = obj.getString("error_code");
						} catch (Exception e) {
							jsonArray = new JSONArray(re);

							JSONObject obj = jsonArray.getJSONObject(0);
							error_code = obj.getString("error_code");
						}

					} catch (JSONException e) {

					}

					if (error_code.equals("0000")) {
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);

					} else if (error_code.equals("070002")) {
						Message msg = new Message();
						msg.what = 7;
						handler.sendMessage(msg);
					} else if (error_code.equals("000047")) {
						Message msg = new Message();
						msg.what = 12;
						handler.sendMessage(msg);
					} else if (error_code.equals("0047")) {
						Message msg = new Message();
						msg.what = 12;
						handler.sendMessage(msg);
					} else if (error_code.equals("00")) {
						Message msg = new Message();
						msg.what = 9;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 10;
						handler.sendMessage(msg);
					}
				}

			});

			t.start();

		}
		// Ͷע��ѯ
		if (getString(R.string.usercenter_bettingDetails).equals(str)) {
			bettingVector.removeAllElements();
			iPage = 0;
			Separate = 0;
			// bettingConnect("1","20");
			showDialog(DIALOG1_KEY);
			// Ͷע��ѯ�½ֿ�

			Thread t = new Thread(new Runnable() {
				public void run() {
					String error_code = "00";
					try {

						String re = GetUserInfoInterface.bettingSelectTC(
								phonenum, "", "1", "20", sessionid);// cc 8.13

						PublicMethod.myOutput("-----------------re:" + re);

						try {
							JSONObject obj = new JSONObject(re);
							error_code = obj.getString("error_code");

							PublicMethod
									.myOutput("---------------try error-code"
											+ error_code);
						} catch (Exception e) {
							jsonArray = new JSONArray(re);
							JSONObject obj = jsonArray.getJSONObject(0);
							error_code = obj.getString("error_code");
						}

					} catch (JSONException e) {

					}

					if (error_code.equals("000000")
							|| error_code.equals("0000")) {
						Message msg = new Message();
						msg.what = 2;
						handler.sendMessage(msg);
					} else if (error_code.equals("070002")) {
						Message msg = new Message();
						msg.what = 7;
						handler.sendMessage(msg);

					} else if (error_code.equals("4444")) {
						Message msg = new Message();
						msg.what = 8;
						handler.sendMessage(msg);
					} else if (error_code.equals("00")) {
						Message msg = new Message();
						msg.what = 9;
						handler.sendMessage(msg);
					} else if (error_code.equals("0047")
							|| error_code.equals("000047")) {
						Message msg = new Message();
						msg.what = 12;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 10;
						handler.sendMessage(msg);
					}
				}
			});
			t.start();
		}

		// ���Ͳ�ѯ
		if (getString(R.string.usercenter_giftCheck).equals(str)) {
			GiftVector.removeAllElements();
			GiftedVector.removeAllElements();
			iPage = 0;
			iPageGifted = 0;
			Separate = 0;
			SeparateGifted = 0;
			showDialog(DIALOG1_KEY);
			Thread t = new Thread(new Runnable() {
				public void run() {

					try {
						String re = GiftLotteryInterface.giftSelectTC(phonenum,
								sessionid, "0", "20");
						String re_gifted = GiftLotteryInterface.giftedSelectTC(
								phonenum, sessionid, "0", "20");

						try {
							JSONObject obj = new JSONObject(re);
							error_code_gift = obj.getString("error_code");
						} catch (Exception e) {
							jsonArray = new JSONArray(re);
							// jsonArray_gifted = new JSONArray(re_gifted);
							JSONObject obj = jsonArray.getJSONObject(0);
							error_code_gift = obj.getString("error_code");
						}
						try {
							JSONObject obj_gifted = new JSONObject(re_gifted);
							error_code_gifted = obj_gifted.getString("error_code");
						} catch (Exception e) {
							jsonArray_gifted = new JSONArray(re_gifted);
							JSONObject obj_gifted = jsonArray_gifted
									.getJSONObject(0);
							error_code_gifted = obj_gifted
									.getString("error_code");
						}

					} catch (JSONException e) {

					}

					if (error_code_gift.equals("0000")
							|| error_code_gifted.equals("0000")) {
						Message msg = new Message();
						msg.what = 4;
						handler.sendMessage(msg);

					} else if (error_code_gift.equals("070002")
							|| error_code_gifted.equals("070002")) {
						Message msg = new Message();
						msg.what = 7;
						handler.sendMessage(msg);
					} else if (error_code_gift.equals("4444")) {
						Message msg = new Message();
						msg.what = 8;
						handler.sendMessage(msg);

					} else if (error_code_gift.equals("0047")
							&& error_code_gifted.equals("0047")) {
						Message msg = new Message();
						msg.what = 12;
						handler.sendMessage(msg);
					} else if (error_code_gift.equals("00")
							|| error_code_gifted.equals("00")) {
						Message msg = new Message();
						msg.what = 9;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 10;
						handler.sendMessage(msg);
					}
				}
			});
			t.start();
		}

		// ׷�Ų�ѯ
		if (getString(R.string.usercenter_trackNumberInquiry).equals(str)) {
			AddNumVector.removeAllElements();
			iPage = 0;
			Separate = 0;
			showDialog(DIALOG1_KEY);
			Thread t = new Thread(new Runnable() {
				public void run() {
					String error_code = "00";
					try {
						String re = GetUserInfoInterface.addNumQueryTC(sessionid, phonenum, "1", "20");
						try {
							JSONObject obj = new JSONObject(re);
							error_code = obj.getString("error_code");
						} catch (Exception e) {
							jsonArray = new JSONArray(re);
							JSONObject obj = jsonArray.getJSONObject(0);
							error_code = obj.getString("error_code");
						}
					} catch (JSONException e) {

					}
					if (error_code.equals("000000")) {
						Message msg = new Message();
						msg.what = 6;
						handler.sendMessage(msg);

					} else if (error_code.equals("070002")) {
						Message msg = new Message();
						msg.what = 7;
						handler.sendMessage(msg);
					} else if (error_code.equals("4444")) {
						Message msg = new Message();
						msg.what = 8;
						handler.sendMessage(msg);

					} else if (error_code.equals("0047")
							|| error_code.equals("000047")) {
						Message msg = new Message();
						msg.what = 12;
						handler.sendMessage(msg);
					} else if (error_code.equals("00")) {
						Message msg = new Message();
						msg.what = 9;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 10;
						handler.sendMessage(msg);
					}
				}
			});
			t.start();
		}

	}

	// �˻������Ӧ��
	protected void accountDetailTwo() {
		String str = text.getText().toString();
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		phonenum = shellRW.getUserLoginInfo("phonenum");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		// �˻���ϸ
		if (getString(R.string.usercenter_accountDetails).equals(str)) {
			iQuitFlag = 70;
			showDialog(DIALOG1_KEY);
			type = "0";
			Thread t = new Thread(new Runnable() {
				public void run() {
					String error_code = "00";
					String startTime = "";
					String endTime = "";
					
						try {
							// ��ȡ��ǰ���� �Լ�ǰһ��������
							Calendar now = Calendar.getInstance();
							String monthNow;
							String dayNow;
							// String startTime = "";
							// String endTime = "";
							String monthPre;
							int monthpre = now.get(Calendar.MONTH);
							int month = now.get(Calendar.MONTH) + 1;
							int day = now.get(Calendar.DAY_OF_MONTH);
							if (month < 10) {
								monthNow = "0" + month;
							} else {
								monthNow = "" + month;
							}
							if (day < 10) {
								dayNow = "0" + day;
							} else {
								dayNow = "" + day;
							}
							if (monthpre < 10) {
								monthPre = "0" + monthpre;
							} else {
								monthPre = "" + monthpre;
							}
							if (month == 1) {
								startTime = (now.get(Calendar.YEAR) - 1) + ""
										+ 12 + dayNow;
							} else {
								startTime = now.get(Calendar.YEAR) + ""
										+ monthPre + dayNow;
							}
							PublicMethod.myOutput("--------------starttime"+ startTime);
							endTime = now.get(Calendar.YEAR) + monthNow+ dayNow;
							PublicMethod.myOutput("--------------endtime"+ endTime);
							String re = GetAccountDetail.accountDetailSelect(phonenum, startTime, endTime, type,sessionid);
							PublicMethod.myOutput("-----------------re:" + re);
							try {
								JSONObject obj = new JSONObject(re);
								error_code = obj.getString("error_code");
								PublicMethod
										.myOutput("---------------try error-code"
												+ error_code);
							} catch (Exception e) {

								jsonArray = new JSONArray(re);
								JSONObject obj = jsonArray.getJSONObject(0);
								error_code = obj.getString("error_code");
								// String memo=obj.getString("memo");
								// Toast.makeText(RuyiHelper.this, memo,
								// Toast.LENGTH_SHORT).show();
								PublicMethod
										.myOutput("--------------error_code"
												+ error_code);
							}

//							iretrytimes = 3;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
//							iretrytimes--;
						}
					
					// �����Ƿ�ı�������ж� �³� 8.11
			//		iretrytimes = 2;

					if (error_code.equals("0000")) {
						Message msg = new Message();
						msg.what = 3;
						handler.sendMessage(msg);

					} else if (error_code.equals("070002")) {
						Message msg = new Message();
						msg.what = 7;
						handler.sendMessage(msg);

					} else if (error_code.equals("4444")) {
						Message msg = new Message();
						msg.what = 8;
						handler.sendMessage(msg);

					} else if (error_code.equals("0047")) {
						Message msg = new Message();
						msg.what = 12;
						handler.sendMessage(msg);
					} else if (error_code.equals("00")) {
						Message msg = new Message();
						msg.what = 9;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 10;
						handler.sendMessage(msg);
					}
				}
			});
			t.start();
		}
		// �˻�����
		if (getString(R.string.ruyihelper_accountWithdraw).equals(str)) {

			showDialog(DIALOG1_KEY);
			new Thread(new Runnable() {
				public void run() {
					String error_code = "00";
					int state = 0;
					re = GetCashInterface.stateQuset(phonenum, sessionid);
					try {
						obj = new JSONObject(re);
						error_code = obj.getString("error_code");
					} catch (JSONException e) {

					}
					if (error_code.equals("000000")) {
						try {
							state = obj.getInt("STATE");
						} catch (JSONException e1) {

						}
						if (state == 1) {// �����
							try {
								moneyNum = Integer.toString(Integer
										.parseInt(obj.getString("AMT")) / 100);
								bankaccount = obj.getString("BANKACCOUNT");
								name = obj.getString("NAME");
								bankName = obj.getString("BANKNAME");
								areaName = obj.getString("AREANAME");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							Message msg = new Message();
							msg.what = 32;
							handler.sendMessage(msg);
						} else if (state == 102 || state == 103) {// �����
							Message msg = new Message();
							msg.what = 33;
							handler.sendMessage(msg);
						} else if (state == 105) {// �ɹ�
							// start=true;
							Message msg = new Message();
							msg.what = 35;
							handler.sendMessage(msg);
						} else if (state == 104 || state == 106) {// ���غ�ȡ��
							Message msg = new Message();
							msg.what = 34;
							handler.sendMessage(msg);
						}

					} else if (error_code.equals("090001")) {// ���ִ���
						Message msg = new Message();
						msg.what = 30;
						handler.sendMessage(msg);
					}

				}
			}).start();
		}
		// �����޸�
		if (getString(R.string.usercenter_passwordChange).equals(str)) {
			showUserCenterPasswordChange();
		}
	}

	// cc 8.16
	protected void winningSelectConnect(final String startLine,
			final String endLine) {
		showDialog(DIALOG1_KEY);
		Thread t = new Thread(new Runnable() {
			public void run() {
				String error_code = "00";
					try {
						String re = GetUserInfoInterface.winingSelectTC(
								phonenum, startLine, endLine, sessionid);

						try {
							JSONObject obj = new JSONObject(re);
							error_code = obj.getString("error_code");
						} catch (Exception e) {
							jsonArray = new JSONArray(re);

							JSONObject obj = jsonArray.getJSONObject(0);
							error_code = obj.getString("error_code");
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				if (error_code.equals("0000")) {
					Message msg = new Message();
					msg.what = 17;
					handler.sendMessage(msg);

				} else if (error_code.equals("070002")) {
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);
				} else if (error_code.equals("000047")) {
					Message msg = new Message();
					msg.what = 12;
					handler.sendMessage(msg);
				} else if (error_code.equals("0047")) {
					Message msg = new Message();
					msg.what = 12;
					handler.sendMessage(msg);
				} else if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 9;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 10;
					handler.sendMessage(msg);
				}
			}

		});

		t.start();
	}

	// cc 8.16
	protected void bettingConnect(final String startLine, final String endLine) {
		showDialog(DIALOG1_KEY);
		Thread t = new Thread(new Runnable() {
			public void run() {
				String error_code = "00";
					try {
						String re = GetUserInfoInterface.bettingSelectTC(
								phonenum, "", startLine, endLine, sessionid);// cc
						// 8.13
						try {
							JSONObject obj = new JSONObject(re);
							error_code = obj.getString("error_code");
						} catch (Exception e) {
							jsonArray = new JSONArray(re);
							JSONObject obj = jsonArray.getJSONObject(0);
							error_code = obj.getString("error_code");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				if (error_code.equals("000000") || error_code.equals("0000")) {
					Message msg = new Message();
					msg.what = 16;
					handler.sendMessage(msg);

				} else if (error_code.equals("070002")) {
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);

				} else if (error_code.equals("4444")) {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);

				} else if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 9;
					handler.sendMessage(msg);
				} else if (error_code.equals("0047")
						|| error_code.equals("000047")) {
					Message msg = new Message();
					msg.what = 12;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 10;
					handler.sendMessage(msg);
				}
			}
		});
		t.start();
	}

	protected void addNumConnect(final String startLine, final String endLine) {
		showDialog(DIALOG1_KEY);
		Thread t = new Thread(new Runnable() {
			public void run() {

				String error_code = "00";
					try {
						String re = GetUserInfoInterface.addNumQueryTC(
								sessionid, phonenum, startLine, endLine);

						try {
							JSONObject obj = new JSONObject(re);
							error_code = obj.getString("error_code");
						} catch (Exception e) {
							jsonArray = new JSONArray(re);
							JSONObject obj = jsonArray.getJSONObject(0);
							error_code = obj.getString("error_code");

						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				if (error_code.equals("000000") || error_code.equals("0000")) {
					Message msg = new Message();
					msg.what = 21;
					handler.sendMessage(msg);

				} else if (error_code.equals("070002")) {
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);

				} else if (error_code.equals("4444")) {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);

				} else if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 9;
					handler.sendMessage(msg);
				} else if (error_code.equals("0047")
						|| error_code.equals("000047")) {
					Message msg = new Message();
					msg.what = 12;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 10;
					handler.sendMessage(msg);
				}
			}
		});
		t.start();
	}

	protected void giftConnect(final String startLine, final String endLine) {
		showDialog(DIALOG1_KEY);
		Thread t = new Thread(new Runnable() {
			public void run() {
				String error_code = "00";
					try {
						String re = GiftLotteryInterface.giftSelectTC(phonenum,
								sessionid, startLine, endLine);
						PublicMethod.myOutput("-----------------re:" + re);

						try {
							JSONObject obj = new JSONObject(re);
							error_code = obj.getString("error_code");
							PublicMethod
									.myOutput("---------------try error-code"
											+ error_code);
						} catch (Exception e) {
							jsonArray = new JSONArray(re);
							JSONObject obj = jsonArray.getJSONObject(0);
							error_code = obj.getString("error_code");
							PublicMethod.myOutput("--------------error_code"
									+ error_code);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				if (error_code.equals("0000")) {
					Message msg = new Message();
					msg.what = 22;
					handler.sendMessage(msg);

				} else if (error_code.equals("070002")) {
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);

				} else if (error_code.equals("4444")) {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);

				} else if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 9;
					handler.sendMessage(msg);
				} else if (error_code.equals("0047")) {
					Message msg = new Message();
					msg.what = 12;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 10;
					handler.sendMessage(msg);
				}
			}
		});
		t.start();
	}

	protected void giftedConnect(final String startLine, final String endLine) {
		showDialog(DIALOG1_KEY);
		Thread t = new Thread(new Runnable() {
			public void run() {
				String error_code_gifted = "00";
					try {
						String re = GiftLotteryInterface.giftedSelectTC(
								phonenum, sessionid, startLine, endLine);

						try {
							JSONObject obj = new JSONObject(re);
							error_code_gifted = obj.getString("error_code");
						} catch (Exception e) {
							jsonArray_gifted = new JSONArray(re);
							JSONObject obj = jsonArray_gifted.getJSONObject(0);
							error_code_gifted = obj.getString("error_code");
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				if (error_code_gifted.equals("0000")) {
					Message msg = new Message();
					msg.what = 23;
					handler.sendMessage(msg);

				} else if (error_code_gifted.equals("070002")) {
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);

				} else if (error_code_gifted.equals("4444")) {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);

				} else if (error_code_gifted.equals("00")) {
					Message msg = new Message();
					msg.what = 9;
					handler.sendMessage(msg);
				} else if (error_code_gifted.equals("0047")) {
					Message msg = new Message();
					msg.what = 12;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 10;
					handler.sendMessage(msg);
				}
			}
		});
		t.start();
	}

	protected void showInfo(int aInfoFlag) {
		setContentView(R.layout.layout_ruyizhushou);
		// ���� ������ 7.3 �����޸ģ���Button����ImageButton
		// btnreturn = new ImageButton(this);
		ImageView btnreturn = (ImageView) findViewById(R.id.ruyizhushou_btn_return);
		TextView title = (TextView) findViewById(R.id.ruyipackage_title);
		btnreturn.setImageResource(R.drawable.return_btn);
		btnreturn.setBackgroundColor(Color.TRANSPARENT);
		// btnreturn.setGravity(Gravity.RIGHT);
		if (ID_AUTHORIZING == aInfoFlag) {
			btnreturn.setOnClickListener(new Button.OnClickListener() {

				public void onClick(View v) {
					/* ���ء����ࡱ�б� */
					showListView(ID_MORELISTVIEW);
				}

			});
		} else {
			btnreturn.setOnClickListener(new Button.OnClickListener() {

				public void onClick(View v) {
					/* �������������б� */
					showListView(ID_RUYIHELPERLISTVIEW);
				}

			});
		}

		WebView webView = (WebView) findViewById(R.id.ruyipackage_webview);

		String iFileName = null;
		switch (aInfoFlag) {
		/* �淨���� */
		case ID_GAMEINTRODUTION:
			iQuitFlag = 40; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			// iFileName="ruyihelper_gameIntroduction.html";
			iFileName = "ruyihelper_gameIntroduction.html";
			title.setText("�淨����");
			break;
		/* �������� */
		case ID_FEQUENTQUESTION:
			iQuitFlag = 40; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			iFileName = "ruyihelper_frequentQuestion.html";
			title.setText("��������");
			break;
		/* �������� */
		case ID_GOUCAI:
			iQuitFlag = 40; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			iFileName = "ruyihelper_userBetting.html";
			title.setText("��������");
			break;
		/* �н��콱 */
		case ID_ZHONGJIANG:
			iQuitFlag = 40; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			iFileName = "ruyihelper_howtogetmoney.html";
			title.setText("�н��콱");
			break;
		/* ����˵�� */
		case ID_SHUOMING:
			iQuitFlag = 40; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			iFileName = "ruyihelper_chongzhifeilv.html";
			title.setText("����˵��");
			break;
		/* �����һ� */
		case ID_MIMAZHAOHUI:
			iQuitFlag = 40; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			iFileName = "ruyihelper_queerpassword.html";
			title.setText("�����һ�");
			break;
		/* �ͷ��绰 */
		case ID_KEFUDIANHUA:
			iQuitFlag = 40; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			iFileName = "ruyihelper_kefudianhua.html";
			title.setText("�ͷ��绰");
			break;
		/* ������Ȩ */
		case ID_AUTHORIZING:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			iFileName = "ruyihelper_authorizing.html";
			title.setText("������Ȩ");
			break;

		}
		String url = "file:///android_asset/" + iFileName;
		webView.loadUrl(url);

	}

	protected void showFeedBack() {
		iQuitFlag = 10;
		
		setContentView(R.layout.yonghufankuiview);
		// ���� ������ 7.3 �����޸ģ���Button����ImageButton
		// btnreturn = new ImageButton(this);
		final InputMethodManager imm = (InputMethodManager)RuyiHelper.this.getSystemService(INPUT_METHOD_SERVICE);

		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
		ImageView btnReturn = (ImageView) findViewById(R.id.ruyizhushou_btn_return);
		TextView title = (TextView) findViewById(R.id.ruyipackage_title);
		title.setText("�û�����");
//		btnReturn.setImageResource(R.drawable.return_btn);
//		btnReturn.setBackgroundColor(Color.TRANSPARENT);
		final EditText feedbackContent = (EditText) findViewById(R.id.yonghufankui);
		final EditText feedbackConnect = (EditText) findViewById(R.id.feedbackconnection);

		feedbackContent.setFocusable(true);
		feedbackContent.requestFocus();
		Button btnCommitFeedback = (Button) findViewById(R.id.tijiao);
		btnCommitFeedback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String backContent = feedbackContent.getText().toString();
				final String backContettpuls = backContent+"|"+feedbackConnect.getText().toString();
				if (backContent.equals("")) {
					Toast.makeText(RuyiHelper.this,
							RuyiHelper.this.getString(R.string.feedbacknull),
							Toast.LENGTH_SHORT).show();
				} else if (backContent.length() < 5) {
					Toast.makeText(RuyiHelper.this, RuyiHelper.this.getString(R.string.feedbackless),
							Toast.LENGTH_SHORT).show();
				} else {
					showDialog(0);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							String feedBackRetrun = FeedbackInterface
									.getInstance().feedback(backContettpuls);
							// feedbackContent.setText("");
							try {
								JSONObject obj = new JSONObject(feedBackRetrun);
								String errorCode = obj.getString("errorCode");
								if (errorCode.equals("success")) {
									Message msg = new Message();
									msg.what = 22;
									handler1.sendMessage(msg);
									imm.hideSoftInputFromWindow(feedbackContent.getWindowToken(), 0);

								} else {
									Message msg = new Message();
									msg.what = 23;
									handler1.sendMessage(msg);
									imm.hideSoftInputFromWindow(feedbackContent.getWindowToken(), 0);

								}
							} catch (JSONException e) {
								Message msg = new Message();
								msg.what = 23;
								handler1.sendMessage(msg);
								imm.hideSoftInputFromWindow(feedbackContent.getWindowToken(), 0);

							}
						}
					}).start();

				}
			}
		});

		// btnreturn.setGravity(Gravity.RIGHT);
		btnReturn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				/* ���ء����ࡱ�б� */
				showListView(ID_MORELISTVIEW);
				imm.hideSoftInputFromWindow(feedbackContent.getWindowToken(), 0);
			}
		});
	}

	// (˫ɫ�򡢸���3D�����ֲ�)ר�ҷ���
	protected void showExpertAnalyzeDialog(int aInfoFlag) {
		String iFileName = null;
		int iTitleStringId = 0;
		switch (aInfoFlag) {
		/* ˫ɫ��ר�ҷ��� */
		case ID_EXPERTANALYZE_SSQ:
			iFileName = "ruyihelper_expertanalyze_ssq.html";
			iTitleStringId = R.string.expertanalyze_ssq;
			break;
		/* ����3Dר�ҷ��� */
		case ID_EXPERTANALYZE_FC3D:
			iFileName = "ruyihelper_expertanalyze_fc3d.html";
			iTitleStringId = R.string.expertanalyze_fc3d;
			break;
		/* ���ֲ�ר�ҷ��� */
		case ID_EXPERTANALYZE_QLC:
			iFileName = "ruyihelper_expertanalyze_qlc.html";
			iTitleStringId = R.string.expertanalyze_qlc;
			break;
		}

		WebView webView = new WebView(this);
		String url = "file:///android_asset/" + iFileName;
		webView.loadUrl(url);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(iTitleStringId);
		builder.setView(webView);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}

				});
		builder.show();
	}

	protected void showListViewDialog(int aInfoFlag) {
		String iFileName = null;
		int iTitleStringId = 0;
		switch (aInfoFlag) {
		/* �û�ע�� */
		case ID_USERREGISTER:
			iFileName = "ruyihelper_userRegister.html";
			iTitleStringId = R.string.ruyihelper_userRegister;
			break;
		/* �û���¼ */
		case ID_USERLOGIN:
			iFileName = "ruyihelper_userLogin.html";
			iTitleStringId = R.string.ruyihelper_userLogin;
			break;
		/* �û�Ͷע */
		case ID_USERBETTING:
			iFileName = "ruyihelper_userBetting.html";
			iTitleStringId = R.string.ruyihelper_userBetting;
			break;
		/* ���Ͳ�Ʊ */
		case ID_PRESENTLOTTREY:
			iFileName = "ruyihelper_presentLottery.html";
			iTitleStringId = R.string.ruyihelper_presentLottery;
			break;
		/* �˺����� */
		case ID_ACCOUNTWITHDRAW:
			iFileName = "ruyihelper_accountWithdraw.html";
			iTitleStringId = R.string.ruyihelper_accountWithdraw;
			break;
		/* ����ѯ */
		case ID_BALANCEINQUIRY:
			iFileName = "ruyihelper_balanceInquiry.html";
			iTitleStringId = R.string.ruyihelper_balanceInquiry;
			break;
		}

		WebView webView = new WebView(this);
		String url = "file:///android_asset/" + iFileName;
		webView.loadUrl(url);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(iTitleStringId);
		builder.setView(webView);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}

				});
		builder.show();
	}

	// �û�����-����ѯ
	public void showUserCenterBalanceInquiry() {
		String deposit_amount = "";
		String Valid_amount = "";
		String freeze_amout = "";
		String totalBalance = "";
		try {
			// 20100710 cc �޸�����ʽ
			deposit_amount = PublicMethod.changeMoney(obj.getString("deposit_amount"));
			Valid_amount = PublicMethod.changeMoney(obj.getString("Valid_amount"));
			freeze_amout = PublicMethod.changeMoney(obj.getString("freeze_amout"));
			totalBalance = PublicMethod.changeMoney((Integer.parseInt(obj.getString("deposit_amount")) + Integer.parseInt(obj
					                                .getString("freeze_amout")))+ "");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		TextView textview = new TextView(this);
		textview.setText(getString(R.string.total_balance) + totalBalance
				+ getString(R.string.unit) + "\n"
				+ getString(R.string.freeze_amout) + freeze_amout
				+ getString(R.string.unit) + "\n"
				+ getString(R.string.usercenter_currentBalance)
				+ deposit_amount + getString(R.string.unit) + "\n"
				+ getString(R.string.usercenter_withdrawBalance) + Valid_amount
				+ getString(R.string.unit) + "\n"
				// + getString(R.string.usercenter_textDisplay) + "\n"
				+ getString(R.string.usercenter_remind));

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.ruyihelper_balanceInquiry);
		builder.setView(textview);

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}

				});
		builder.show();
	}

	// �û�����-���Ͳ�ѯ
	protected void showUserCenterGiftCheckNew() {
		Separate = 0;
		iPage = Separate * 20;
		iPageGifted = SeparateGifted * 20;

		setContentView(R.layout.usercenter_giftcheck);

		presentedView = (TextView) findViewById(R.id.usercenter_giftcheck_presentedlottery_body);
		receivedView = (TextView) findViewById(R.id.usercenter_giftcheck_receivedlottery_body);
		b_uppage_gift = (Button) findViewById(R.id.usercenter_giftcheck_presentedlottery_upPage);
		b_downpage_gift = (Button) findViewById(R.id.usercenter_giftcheck_presentedlottery_downPage);
		b_uppage_gifted = (Button) findViewById(R.id.usercenter_giftcheck_receivedlottery_upPage);
		b_downpage_gifted = (Button) findViewById(R.id.usercenter_giftcheck_receivedlottery_downPage);

		// LayoutInflater inflater = LayoutInflater.from(this);
		// giftView = inflater.inflate(R.layout.usercenter_giftcheck, null);

		JsonGiftSelect(jsonArray);
		JsonGiftedSelect(jsonArray_gifted);
		// iTotaPage=jsonArray.length();
		iTotaPage = GiftVector.size();
		iTotaPageGifted = GiftedVector.size();

		PublicMethod.myOutput("----iTotaPage--------" + iTotaPage);

		showUserCenterGiftCheckNewTab(iPage, presentedView);
		showUserCenterGiftedCheckNewTab(iPageGifted, receivedView);

		// ������ 7.8 �����޸ģ���Button����ImageButton
		ImageButton btn = (ImageButton) findViewById(R.id.usercenter_giftcheck_return);
		btn.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				showListView(ID_USERCENTER);
			}

		});

		// AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setCancelable(true);
		// builder.setTitle(R.string.usercenter_giftCheck);
		// builder.setView(giftView);
		// builder.setNegativeButton("����", new DialogInterface.OnClickListener()
		// {
		//
		// public void onClick(DialogInterface dialog, int which) {
		// // bettingVector.removeAllElements();
		// }
		//
		// });
		//
		// builder.show();
	}

	public void showUserCenterGiftCheckNewTab(int page, final TextView itextView) {

		// final TextView presentedView = (TextView)
		// iView.findViewById(R.id.usercenter_giftcheck_presentedlottery_body);
		// final TextView receivedView = (TextView)
		// iView.findViewById(R.id.usercenter_giftcheck_receivedlottery_body);
		final GiftDetail giftDetail;
		// giftDetail=new GiftDetail();
		if (GiftVector.size() != 0) {
			giftDetail = (GiftDetail) GiftVector.elementAt(page);
			// }

			itextView.setText("");
			itextView// .setText("1234");
					.setText(getString(R.string.usercenter_giftCheck_presentedNumber)
							+ giftDetail.to_mobile_code
							+ "\n"
							+ getString(R.string.usercenter_giftCheck_giftDate)
							+ giftDetail.term_begin_datetime
							+ "\n"
							+ getString(R.string.usercenter_giftCheck_giftAmount)
							+ giftDetail.amount
							+ "\n"
							+ getString(R.string.usercenter_giftCheck_lotteryIssue)
							+ giftDetail.sell_term_code
							+ "\n"
							+ getString(R.string.usercenter_giftCheck_lotteryCategory)
							+ giftDetail.play_name
							+ "\n"
							+ getString(R.string.usercenter_giftCheck_lotteryBets)
							+ "\n" + giftDetail.betcode);
			// final Button b_uppage_gift = (Button)
			// iView.findViewById(R.id.usercenter_giftcheck_presentedlottery_upPage);
			// final Button b_downpage_gift = (Button)
			// iView.findViewById(R.id.usercenter_giftcheck_presentedlottery_downPage);

			b_uppage_gift.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (iPage > 0) {
						b_uppage_gift.setClickable(true);
						iPage--;
						showUserCenterGiftCheckNewTab(iPage, itextView);
					}
					if (iPage < iTotaPage - 1) {
						b_downpage_gift.setClickable(true);
					} else {
						b_downpage_gift.setClickable(false);
					}
					if (iPage == 0) {
						b_uppage_gift.setClickable(false);
					}

				}

			});

			b_downpage_gift.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (iPage == iTotaPage - 1) {

						if (iTotaPage < Integer.parseInt(giftDetail.maxLine)) {
							Separate++;
							PublicMethod.myOutput("------Separate-----"
									+ Separate);
							giftConnect(Separate * 20 + 1 + "", (Separate + 1)
									* 20 + "");
						} else {
							b_downpage_gift.setClickable(false);

						}

					}
					if (iPage < iTotaPage - 1) {
						b_downpage_gift.setClickable(true);
						iPage++;
						showUserCenterGiftCheckNewTab(iPage, itextView);
						PublicMethod.myOutput("----iPage---------" + iPage);
					} else {
						b_downpage_gift.setClickable(false);
					}
					if (iPage > 0) {
						b_uppage_gift.setClickable(true);
					} else {
						b_uppage_gift.setClickable(false);
					}

				}

			});
		} else {
			itextView.setText("");
			itextView// .setText("1234");
					.setText("��û�����Ͳ�Ʊ��¼");

			b_uppage_gift.setClickable(false);
			b_downpage_gift.setClickable(false);

		}
	}

	protected void showUserCenterGiftedCheckNewTab(int page,
			final TextView itextView) {
		// final TextView presentedView = (TextView)iView.
		// findViewById(R.id.usercenter_giftcheck_presentedlottery_body);
		// final TextView receivedView = (TextView)
		// iView.findViewById(R.id.usercenter_giftcheck_receivedlottery_body);
		if (GiftedVector.size() != 0) {
			final GiftedDetail giftedDetail;
			giftedDetail = (GiftedDetail) GiftedVector.elementAt(page);
			itextView.setText("");
			itextView
					.setText(getString(R.string.usercenter_giftCheck_friendNumber)
							+ giftedDetail.from_mobile_code
							+ "\n"
							+ getString(R.string.usercenter_giftCheck_giftedDate)
							+ giftedDetail.term_begin_datetime
							+ "\n"
							+ getString(R.string.usercenter_giftCheck_lotteryAmount)
							+ giftedDetail.amount
							+ "\n"
							+ getString(R.string.usercenter_giftCheck_lotteryIssue)
							+ giftedDetail.sell_term_code
							+ "\n"
							+ getString(R.string.usercenter_giftCheck_lotteryCategory)
							+ giftedDetail.play_name
							+ "\n"
							+ getString(R.string.usercenter_giftCheck_lotteryBets)
							+ "\n" + giftedDetail.betcode);
			// final Button b_uppage_gifted = (Button)
			// iView.findViewById(R.id.usercenter_giftcheck_receivedlottery_upPage);
			// final Button b_downpage_gifted = (Button)
			// iView.findViewById(R.id.usercenter_giftcheck_receivedlottery_downPage);

			b_uppage_gifted.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (iPageGifted > 0) {
						b_uppage_gifted.setClickable(true);
						iPageGifted--;
						showUserCenterGiftedCheckNewTab(iPageGifted, itextView);
					}
					if (iPageGifted < iTotaPageGifted - 1) {
						b_downpage_gifted.setClickable(true);
					} else {
						b_downpage_gifted.setClickable(false);
					}
					if (iPageGifted == 0) {
						b_uppage_gifted.setClickable(false);
					}

				}

			});

			b_downpage_gifted.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (iPageGifted == iTotaPageGifted - 1) {
						if (iTotaPageGifted < Integer
								.parseInt(giftedDetail.maxLine)) {
							SeparateGifted++;
							PublicMethod.myOutput("------SeparateGifted-----"
									+ SeparateGifted);
							giftedConnect(SeparateGifted * 20 + 1 + "",
									(SeparateGifted + 1) * 20 + "");
						} else {
							b_downpage_gifted.setClickable(false);
						}

					}
					if (iPageGifted < iTotaPageGifted - 1) {
						b_downpage_gifted.setClickable(true);
						iPageGifted++;
						showUserCenterGiftedCheckNewTab(iPageGifted, itextView);
						PublicMethod.myOutput("----iPageGifted---------"
								+ iPageGifted);
					} else {
						b_downpage_gifted.setClickable(false);
					}
					if (iPageGifted > 0) {
						b_uppage_gifted.setClickable(true);
					} else {
						b_uppage_gifted.setClickable(false);
					}

				}

			});
		} else {
			itextView.setText("");
			itextView// .setText("1234");
					.setText("��û���յ���Ʊ��¼");

			b_uppage_gifted.setClickable(false);
			b_downpage_gifted.setClickable(false);

		}

	}

	protected void showUserCenterBettingDetailsNew() {
		Separate = 0;
		iPage = Separate * 20;

		LayoutInflater inflater = LayoutInflater.from(this);
		betView = inflater.inflate(R.layout.usercenter_bettingdetails, null);
		JsonBetting(jsonArray);
		// iTotaPage=jsonArray.length();
		iTotaPage = bettingVector.size();
		PublicMethod.myOutput("----iTotaPage--------" + iTotaPage);

		showUserCenterBettingDetailsNewTab(iPage, betView);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.usercenter_bettingDetails);
		builder.setView(betView);
		builder.setNegativeButton("����", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
			}

		});
		builder.show();
	}

	public void showUserCenterBettingDetailsNewTab(int page, final View iView) {

		final TextView textview = (TextView) iView.findViewById(R.id.usercenter_bettingDetails_id);
		final BettingDetail bettingDetail;
		bettingDetail = (BettingDetail) bettingVector.elementAt(page);
		textview.setText("");
		textview.setText(getString(R.string.usercenter_bettingDetails_type)
				+ bettingDetail.playName
				+ "\n"// "˫ɫ��ʽ��ѡ"
				+ getString(R.string.usercenter_bettingDetails_issue)
				+ bettingDetail.batchCode
				+ "\n"// "20100607"
				+ getString(R.string.usercenter_bettingDetails_noteCode)
				+ "\n"
				+ bettingDetail.betCode// +"\n"
				+ getString(R.string.usercenter_bettingDetails_amt)
				+ bettingDetail.amt + " Ԫ" + "\n"
				+ getString(R.string.usercenter_bettingDetails_bettingTime)
				+ bettingDetail.sell_datetime + "\n"
				+ getString(R.string.usercenter_bettingDetails_cash_date_time)
				+ bettingDetail.cash_date_time + "\n"

				);
		// PublicMethod.myOutput("----bettingDetail.maxLine--------"+Integer.parseInt(bettingDetail.maxLine));
		final Button b_uppage = (Button) iView
				.findViewById(R.id.usercenter_btn_uppage);
		final Button b_downpage = (Button) iView
				.findViewById(R.id.usercenter_btn_downpage);
		b_uppage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (iPage > 0) {
					b_uppage.setClickable(true);
					iPage--;
					showUserCenterBettingDetailsNewTab(iPage, iView);
				}
				if (iPage < iTotaPage - 1) {
					b_downpage.setClickable(true);
				} else {
					b_downpage.setClickable(false);
				}
				if (iPage == 0) {
					b_uppage.setClickable(false);
				}

			}

		});

		b_downpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (iPage == iTotaPage - 1) {
					if (iTotaPage < Integer.parseInt(bettingDetail.maxLine)) {
						Separate++;
						PublicMethod.myOutput("------Separate-----" + Separate);
						bettingConnect(Separate * 20 + 1 + "", (Separate + 1)
								* 20 + "");
					} else {
						b_downpage.setClickable(false);
					}

				}
				if (iPage < iTotaPage - 1) {
					b_downpage.setClickable(true);
					iPage++;
					showUserCenterBettingDetailsNewTab(iPage, iView);
				} else {
					b_downpage.setClickable(false);
				}
				if (iPage > 0) {
					b_uppage.setClickable(true);
				} else {
					b_uppage.setClickable(false);
				}
			}
		});
	}

	// ������ 7.9 �û����� Ͷע��ϸ �����ĶԻ���
	// ����ע�ͱ���
	/**
	 * �û����� Ͷע��ϸ �����ĶԻ���
	 * 
	 * @author Administrator
	 * 
	 */

	// ������ 7.5 �����޸ģ����׷�Ų�ѯ
	// �û�����--׷�Ų�ѯ
	protected void showUserCenterAddLotteryQueryNew() {
		Separate = 0;
		iPage = 0;

		LayoutInflater inflater = LayoutInflater.from(this);
		addNumView = inflater.inflate(R.layout.usercenter_add_lottery_query,
				null);

		JsonAddNumSelect(jsonArray);
		iTotaPage = AddNumVector.size();
		showUserCenterAddLotteryQueryNewTab(iPage, addNumView);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.usercenter_add_lottery_query_zhuihaochaxun);
		builder.setView(addNumView);
		builder.setNegativeButton("����", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// bettingVector.removeAllElements();
			}

		});
		builder.show();
	}

	public void showUserCenterAddLotteryQueryNewTab(final int page,
			final View iView) {

		// LayoutInflater inflater = LayoutInflater.from(this);
		// View view = inflater.inflate(R.layout.usercenter_bettingdetails,
		// null);
		final TextView textview = (TextView) iView
				.findViewById(R.id.usercenter_add_lottery_query_text_id);
		final AddNumDetail addNumDetail;
		addNumDetail = (AddNumDetail) AddNumVector.elementAt(page);
		textview.setText("");
		textview
				.setText(getString(R.string.usercenter_add_lottery_query_addqishu)
						+ addNumDetail.batchNum+ "\n"
						+ getString(R.string.usercenter_add_lottery_query_recentmoneytotal)
						+ (addNumDetail.addedamount == null ? ""
								: addNumDetail.addedamount)
						+ "\n"
						+ getString(R.string.usercenter_add_lottery_query_recentaddqishu)
						+ (addNumDetail.addednum == null ? ""
								: addNumDetail.addednum)
						+ "\n"
						+ getString(R.string.usercenter_add_lottery_time)
						+ addNumDetail.orderTime
						+ "\n"
						+ getString(R.string.usercenter_add_lottery_query_lotterytypename)
						+ addNumDetail.play_name
						+ "\n"
						+ getString(R.string.usercenter_add_lottery_query_notecode)
						+ "\n"
						+ addNumDetail.betcode
						+ getString(R.string.usercenter_add_lottery_query_notenum)
						+ addNumDetail.betNum
						+ "\n"
						+ getString(R.string.usercenter_bettingDetails_multiple)
						+ addNumDetail.lotMulti
						+ "\n"
						+ getString(R.string.usercenter_add_lottery_query_state)
						+ (addNumDetail.state == null ? "" : addNumDetail.state));
		final Button b_uppage = (Button) iView
				.findViewById(R.id.usercenter_add_lottery_query_btn_uppage);
		final Button b_downpage = (Button) iView
				.findViewById(R.id.usercenter_add_lottery_query_btn_downpage);
		b_canceltranking = (Button) iView
				.findViewById(R.id.usercenter_add_lottery_query_btn_canceltranking);
		String state1 = addNumDetail.state;
		if (state1.equals("������")) {
			b_canceltranking.setClickable(true);
			b_canceltranking.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showalert(addNumDetail.tsubscribeId, page, iView);
				}

			});
		} else {
			b_canceltranking.setClickable(false);
			// b_canceltranking.setBackgroundColor(Color.WHITE);
		}
		b_uppage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (iPage > 0) {
					b_uppage.setClickable(true);
					iPage--;
					showUserCenterAddLotteryQueryNewTab(iPage, iView);
				}
				if (iPage < iTotaPage - 1) {
					b_downpage.setClickable(true);
				} else {
					b_downpage.setClickable(false);
				}
				if (iPage == 0) {
					b_uppage.setClickable(false);
				}

			}

		});

		b_downpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (iPage == iTotaPage - 1) {
					if (iTotaPage < Integer.parseInt(addNumDetail.maxLine)) {
						Separate++;
						PublicMethod.myOutput("------Separate-----" + Separate);
						addNumConnect(Separate * 20 + 1 + "", (Separate + 1)
								* 20 + "");
					} else {
						b_downpage.setClickable(false);
					}

				}
				if (iPage < iTotaPage - 1) {
					b_downpage.setClickable(true);
					iPage++;
					showUserCenterAddLotteryQueryNewTab(iPage, iView);
					PublicMethod.myOutput("----iPage---------" + iPage);
				} else {
					b_downpage.setClickable(false);
				}
				if (iPage > 0) {
					b_uppage.setClickable(true);
				} else {
					b_uppage.setClickable(false);
				}

			}

		});

	}

	public void CancelTranking(final String string, final int page,
			final View view) {
		specialPage = page;
		showDialog(DIALOG1_KEY);
		Thread t = new Thread(new Runnable() {
			public void run() {

				String error_code = "00";

					try {

						String re = JrtLot.cancelTrankingNumber(phonenum,
								sessionid, string);
						JSONObject obj = new JSONObject(re);
						error_code = obj.getString("error_code");

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}

				if (error_code.equals("000000")) {
					final AddNumDetail addNumDetail;
					addNumDetail = (AddNumDetail) AddNumVector.elementAt(page);
					addNumDetail.state = "��ȡ��";

					AddNumVector.set(page, addNumDetail);
					final AddNumDetail addNumDetailed;
					addNumDetailed = (AddNumDetail) AddNumVector
							.elementAt(page);


					Message msg = new Message();
					msg.what = 24;
					handler.sendMessage(msg);

				} else if (error_code.equals("060004")) {
					Message msg = new Message();
					msg.what = 25;
					handler.sendMessage(msg);

				} else if (error_code.equals("360008")) {
					Message msg = new Message();
					msg.what = 26;
					handler.sendMessage(msg);

				} else if (error_code.equals("360007")) {
					Message msg = new Message();
					msg.what = 27;
					handler.sendMessage(msg);
				} else if (error_code.equals("20100701")) {
					Message msg = new Message();
					msg.what = 28;
					handler.sendMessage(msg);
				} else if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 9;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 29;
					handler.sendMessage(msg);
				}
			}
		});
		t.start();
	}


	// �û�����-�н���ѯ
	protected void showUserCenterWinningCheckNew() {
		Separate = 0;
		iPage = Separate * 20;

		LayoutInflater inflater = LayoutInflater.from(this);
		winningView = inflater.inflate(R.layout.usercenter_winningcheck, null);

		JsonWinningSelect(jsonArray);
		// iTotaPage=jsonArray.length();
		iTotaPage = WinningVector.size();
		PublicMethod.myOutput("----iTotaPage--------" + iTotaPage);

		showUserCenterWinningCheckNewTab(iPage, winningView);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.usercenter_winningCheck);
		builder.setView(winningView);
		builder.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// WinningVector.removeAllElements();
					}

				});
		builder.show();
	}

	public void showUserCenterWinningCheckNewTab(int page, final View iView) {

		// LayoutInflater inflater = LayoutInflater.from(this);
		// View view = inflater.inflate(R.layout.usercenter_bettingdetails,
		// null);
		final TextView textview = (TextView) iView
				.findViewById(R.id.usercenter_winningCheck_id);
		final WinningDetail winningDetail;
		winningDetail = (WinningDetail) WinningVector.elementAt(page);
		textview.setText("");
		textview
				.setText(getString(R.string.usercenter_winningCheck_lotteryCategory)
						+ winningDetail.playName
						+ "\n"
						+ getString(R.string.usercenter_winningCheck_lotteryIssue)
						+ winningDetail.batchCode
						+ "\n"
						// +
						// getString(R.string.usercenter_winningCheck_awardLevel)
						// + winningDetail.prizeinfo
						// + "\n"
						+ getString(R.string.usercenter_winningCheck_prizeMoney)
						+ winningDetail.prizeamt
						+ "\n"
						// +
						// getString(R.string.usercenter_winningCheck_winningMark)
						// + winningDetail.encash_flag
						// + "\n"
						+ getString(R.string.usercenter_winningCheck_bettingTime)
						+ winningDetail.sell_datetime
						+ "\n"
						// +
						// getString(R.string.usercenter_winningCheck_abandonWinningTime)
						// + winningDetail.abandon_date_time
						// + "\n"
						+ getString(R.string.usercenter_winningCheck_WinningNoteCode)
						+ "\n" + winningDetail.betCode// +"\n"
				// +
				// getString(R.string.usercenter_winningCheck_lotteryMultiple)
				// + winningDetail.beishu
				);
		// PublicMethod.myOutput("----bettingDetail.maxLine--------"+Integer.parseInt(bettingDetail.maxLine));
		final Button upbtn = (Button) iView
				.findViewById(R.id.usercenter_btn_winningcheck_uppage);
		final Button downbtn = (Button) iView
				.findViewById(R.id.usercenter_btn_winningcheck_downpage);
		upbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (iPage > 0) {
					upbtn.setClickable(true);
					iPage--;
					showUserCenterWinningCheckNewTab(iPage, iView);
				}
				if (iPage < iTotaPage - 1) {
					downbtn.setClickable(true);
				} else {
					downbtn.setClickable(false);
				}
				if (iPage == 0) {
					upbtn.setClickable(false);
				}

			}

		});

		downbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PublicMethod.myOutput("----iPage---------" + iPage);
				if (iPage == iTotaPage - 1) {// &&iTotaPage<Integer.parseInt(winningDetail.maxLine)
					if (iTotaPage < Integer.parseInt(winningDetail.maxLine)) {
						Separate++;
						PublicMethod.myOutput("------Separate-----" + Separate);
						winningSelectConnect(Separate * 20 + 1 + "",
								(Separate + 1) * 20 + "");
					} else {
						downbtn.setClickable(false);
					}
				}
				if (iPage < iTotaPage - 1) {
					downbtn.setClickable(true);
					iPage++;
					showUserCenterWinningCheckNewTab(iPage, iView);

				} else {
					downbtn.setClickable(false);
				}
				if (iPage > 0) {
					upbtn.setClickable(true);
				} else {
					upbtn.setClickable(false);
				}

			}

		});

	}

	// �û�����-�˺�����
	protected void showUserCenterAccountWithdraw() {

		LayoutInflater inflater = LayoutInflater.from(this);
		View textView = inflater.inflate(R.layout.usercenter_accountwithdraw,
				null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.ruyihelper_accountWithdraw);
		builder.setView(textView);

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}

				});
		builder.show();
	}

	// �û�����-�����޸�
	protected void showUserCenterPasswordChange() {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View textView = inflater.inflate(
				R.layout.usercenter_passwordchange, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.usercenter_passwordChange);
		builder.setView(textView);

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ��Ҫ�жϺ󵯳���ʾ��
						// �¼��� ��ʾ�� 20100711 �³�
						editPassword(textView);

					}

				});
		builder.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ����
					}

				});
		builder.show();
	}

	public void editPassword(View v) {

		EditText usercenter_edittext_originalpwd = (EditText) v
				.findViewById(R.id.usercenter_edittext_originalpwd);
		final String usercenter_edittext_originalpwd_string = usercenter_edittext_originalpwd
				.getText().toString();
		PublicMethod
				.myOutput("--------------usercenter_textview_originalpwd_string"
						+ usercenter_edittext_originalpwd_string);

		EditText usercenter_edittext_newpwd = (EditText) v
				.findViewById(R.id.usercenter_edittext_newpwd);
		final String usercenter_edittext_newpwd_string = usercenter_edittext_newpwd
				.getText().toString();
		PublicMethod
				.myOutput("----------usercenter_edittext_newpwd_string-------"
						+ usercenter_edittext_newpwd_string);

		// ��ȡ��������ȷ�Ͽ��ֵ 20100713
		EditText usercenter_edittext_confirmnewpwd = (EditText) v
				.findViewById(R.id.usercenter_edittext_confirmnewpwd);
		final String usercenter_edittext_confirmnewpwd_string = usercenter_edittext_confirmnewpwd
				.getText().toString();
		PublicMethod
				.myOutput("----------usercenter_edittext_confirmnewpwd_string-------"
						+ usercenter_edittext_confirmnewpwd_string);

		// wangyl 7.21 ��֤���볤��
		if (usercenter_edittext_originalpwd.length() >= 6
				&& usercenter_edittext_originalpwd.length() <= 15
				&& usercenter_edittext_newpwd.length() >= 6
				&& usercenter_edittext_newpwd.length() <= 15
				&& usercenter_edittext_confirmnewpwd.length() >= 6
				&& usercenter_edittext_confirmnewpwd.length() <= 15) {

			// ֻ����������������ȷ���������޸����� ������ʾ���벻��ȷ �³� 20100713
			if (usercenter_edittext_confirmnewpwd_string
					.equalsIgnoreCase(usercenter_edittext_newpwd_string)) {
				showDialog(DIALOG1_KEY);
				Thread t = new Thread(new Runnable() {
					public void run() {
						String error_code = "00";
						try {
							re = ChangePasswordInterface.changePassword(
									phonenum,
									usercenter_edittext_originalpwd_string,
									usercenter_edittext_newpwd_string,
									sessionid);
							obj = new JSONObject(re);
							error_code = obj.getString("error_code");
						//	iretrytimes = 3;
						} catch (JSONException e) {
						//	iretrytimes--;
						}
						if (error_code.equals("0000")) {
							Message msg = new Message();
							msg.what = 5;
							handler.sendMessage(msg);

						} else if (error_code.equals("070002")) {
							Message msg = new Message();
							msg.what = 7;
							handler.sendMessage(msg);
						} else if (error_code.equals("0015")) {
							Message msg = new Message();
							msg.what = 11;
							handler.sendMessage(msg);

						} else if (error_code.equals("00")) {
							Message msg = new Message();
							msg.what = 9;
							handler.sendMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = 10;
							handler.sendMessage(msg);
						}
					}
				});
				t.start();
			} else {
				Message msg = new Message();
				msg.what = 13;
				handler.sendMessage(msg);
			}
		} else {
			Toast.makeText(RuyiHelper.this, "����ӦΪ6~15λ��", Toast.LENGTH_SHORT)
					.show();
		}

	}

	String[][] currentShow = null;

	void getTotalPage() {
		lastItems = currentShow.length % pageNum;
		if (lastItems == 0) {
			lastItems = pageNum;
			totalPage = currentShow.length / pageNum;
		} else {
			totalPage = currentShow.length / pageNum + 1;
		}
	}

	// 20100803 �³� ɾ���ӱ�ǩ��Ϣ����Ϊ������ȡ
	// ��ȡȫ����Ϣ�Ķ�ά���� �³� 2010/7/7/
	String[][] getAccount() {
		int chargeJiShu = 0;
		account = getAccountDetail_array();
		PublicMethod.myOutput("----account.length-----" + account.length);
		for (int i = 0; i < account.length; i++) {
				chargeJiShu++;

		}
		PublicMethod.myOutput("----chargeJiShu-----" + chargeJiShu);
		all = new String[chargeJiShu][7];
		int arrayIndex = 0;
		for (int i = 0; i < account.length; i++) {

				arrayIndex++;
				all[arrayIndex - 1] = account[i];
				PublicMethod.myOutput("---------" + all[arrayIndex - 1][2]);
		}
		return all;
		// account = getAccountDetail_array();
		// return account;
	}

	//
	// // ��ȡ��ֵ�Ķ�ά���� 2010/8/5/
	String[][] getCharge() {
		int chargeJiShu = 0;
		account = getAccountDetail_array();
		for (int i = 0; i < account.length; i++) {
				chargeJiShu++;

		}
		charge = new String[chargeJiShu][7];
		int arrayIndex = 0;
		for (int i = 0; i < account.length; i++) {

				arrayIndex++;
				charge[arrayIndex - 1] = account[i];
				PublicMethod.myOutput("---------" + charge[arrayIndex - 1][2]);

		}
		return charge;
	}

	//
	// // ��ȡ֧���Ķ�ά���� 2010/7/7/
	String[][] getPay() {
		int payJiShu = 0;
		account = getAccountDetail_array();
		for (int i = 0; i < account.length; i++) {
				payJiShu++;

		}
		pay = new String[payJiShu][7];
		int arrayIndex = 0;
		for (int i = 0; i < account.length; i++) {


				arrayIndex++;
				pay[arrayIndex - 1] = account[i];
				PublicMethod.myOutput("---------" + pay[arrayIndex - 1][2]);
			
		}
		return pay;
	}

	//
	// ��ȡ���ɵĶ�ά���� 2010/7/7/
	String[][] getReward() {
		int rewardJiShu = 0;
		account = getAccountDetail_array();
		for (int i = 0; i < account.length; i++) {

				rewardJiShu++;
			
		}
		reward = new String[rewardJiShu][7];
		int arrayIndex = 0;
		for (int i = 0; i < account.length; i++) {

				arrayIndex++;
				reward[arrayIndex - 1] = account[i];
				PublicMethod.myOutput("---------" + reward[arrayIndex - 1][2]);
			
		}
		return reward;
	}

	//
	// ��ȡ���ֵĶ�ά���� 2010/7/7/�³��޸�
	String[][] getCash() {
		int cashJiShu = 0;
		account = getAccountDetail_array();
		for (int i = 0; i < account.length; i++) {

				cashJiShu++;
			
		}
		cash = new String[cashJiShu][7];
		int arrayIndex = 0;
		for (int i = 0; i < account.length; i++) {


				arrayIndex++;
				cash[arrayIndex - 1] = account[i];
				PublicMethod.myOutput("---------" + cash[arrayIndex - 1][2]);

		}
		return cash;
	}

	// �û�����-�˻���ϸ
	protected void showUserCenterAccountDetails() {

		/*
		 * iQuitFlag = 30; //������ 7.5 �����޸ģ��������û����� iCallOnKeyDownFlag=false;
		 */
		setContentView(R.layout.usercenter_accountdetails);
		currentShow = getAccount();// getAccountDetail_array();
		// currentShow =getAccountDetail_array();
		currentPage = 0;

		// ������ 7.7 �����޸ģ��ж�ȡ������Ϣ�Ƿ�Ϊ��
		if (currentShow == null || currentShow.length == 0) {
			PublicMethod.myOutput("---------%%%%%%%%%");
			// TextView noInfo = (TextView)
			// findViewById(R.id.usercenter_accountdetails_no_info_text);
			// noInfo.setText(R.string.noinfo);
		} else {
			getTotalPage();
			showUserCenterAccountDetailsTabs(currentPage);

			// ������ 7.8 �޸ı���ͼƬ ��Button ����ImageView
			allbtn = (ImageButton) findViewById(R.id.usercenter_all);
			rechargebtn = (ImageButton) findViewById(R.id.usercenter_recharge);
			paybtn = (ImageButton) findViewById(R.id.usercenter_pay);
			awardschoolbtn = (ImageButton) findViewById(R.id.usercenter_awardschool);
			withdrawbtn = (ImageButton) findViewById(R.id.usercenter_withdraw);
			returnbtn = (ImageButton) findViewById(R.id.usercenter_return);

			ImageView.OnClickListener listener = new ImageView.OnClickListener() {

				public void onClick(View v) {
					iType = 1;
					// AccountList(v);
					accountView = v;
					// 20100803 �³� �ӱ�ǩ������ȡ��Ϣ
					accountDetail();

				}

				//

			};
			allbtn.setOnClickListener(listener);
			rechargebtn.setOnClickListener(listener);
			paybtn.setOnClickListener(listener);
			awardschoolbtn.setOnClickListener(listener);
			withdrawbtn.setOnClickListener(listener);
			returnbtn.setOnClickListener(listener);
		}
	}

	// 20100803 �³� �û�������ϸ�ӱ�ǩ����
	protected void accountDetail() {

		int btnid = accountView.getId();
		// PublicMethod.myOutput("-????///////"+btnid);
		// currentPage = 0;
		switch (btnid) {
		// // ȫ��
		case R.id.usercenter_all:
			if (!type.equalsIgnoreCase("0")) {
				type = "0";
				AccountDetailThread(type);
			}
			break;
		case R.id.usercenter_recharge:
			if (!type.equalsIgnoreCase("1")) {
				type = "1";
				AccountDetailThread(type);
			}
			break;
		// ֧��
		case R.id.usercenter_pay:
			if (!type.equalsIgnoreCase("2")) {
				type = "2";
				AccountDetailThread(type);
			}
			break;
		// ����
		case R.id.usercenter_awardschool:
			if (!type.equalsIgnoreCase("3")) {
				type = "3";
				AccountDetailThread(type);
			}
			break;
		// ����
		case R.id.usercenter_withdraw:
			if (!type.equalsIgnoreCase("4")) {
				type = "4";
				AccountDetailThread(type);
			}
			break;
		// ����
		case R.id.usercenter_return:
			showListView(ID_USERCENTER);
			break;
		}

	}

	// 20100803 �³� �û�������ϸ�ӱ�ǩ�б�
	protected void AccountList(View v) {
		int btnid = v.getId();
		currentPage = 0;
		switch (btnid) {
		// ȫ��
		case R.id.usercenter_all:

			// ������ 7.8 �޸ı���ͼƬ
			rechargebtn.setImageDrawable(getResources().getDrawable(
					R.drawable.rechargedisable));
			paybtn.setImageDrawable(getResources().getDrawable(
					R.drawable.zhifudisable));
			awardschoolbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.jiangpaidisable));
			withdrawbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.tixiandisable));
			allbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.allenable));
			// allbtn.set
			if (iDetail == true) {
				currentShow = getAccount();
				// currentShow =getAccountDetail_array();
			} else if (iDetail == false) {
				currentShow = null;
			}
			PublicMethod.myOutput("------------all===" + currentShow);
			// PublicMethod.WriteSettings(RuyiHelper.this, currentShow,
			// "currentShow");

			if (currentShow == null || currentShow.length == 0) {
				// ���Ϊ������ռ�¼ 2010/7/7 �³�
				String[] str = { "��¼Ϊ��" };
				String[] str_mode = { "" };
				String[] str_yu_e = { "" };
				UserCenter_AccountDetail_TradingDate = str;
				UserCenter_AccountDetail_TradingMode = str_mode;
				UserCenter_AccountDetail_Yu_E = str_yu_e;
				ListView listview = (ListView) findViewById(R.id.usercenter_accountdetails_listview_id);
				UserCenterAccountDetailsTabsAdapter adapter = new UserCenterAccountDetailsTabsAdapter(
						RuyiHelper.this);
				// adapter.isEmpty();
				// PublicMethod.addHeight(this, listview);
				listview.setAdapter(adapter);
				PublicMethod.setmydividerHeight(listview);
				// TextView noInfo = (TextView)
				// findViewById(R.id.usercenter_accountdetails_no_info_text);
				// noInfo.setText(R.string.noinfo);
			} else {
				// ����м�¼���title �³� 2010/7/8 ע�͵�
				// TextView noInfo = (TextView)
				// findViewById(R.id.usercenter_accountdetails_no_info_text);
				// noInfo.setText("");
				getTotalPage();
				showUserCenterAccountDetailsTabs(currentPage);
			}
			break;
		// ��ֵ
		case R.id.usercenter_recharge:

			// ������ 7.8 �޸ı���ͼƬ
			rechargebtn.setImageDrawable(getResources().getDrawable(
					R.drawable.rechargeenable));
			paybtn.setImageDrawable(getResources().getDrawable(
					R.drawable.zhifudisable));
			awardschoolbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.jiangpaidisable));
			withdrawbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.tixiandisable));
			allbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.alldisable));
			// type="1";
			// AccountDetailThread(type);
			if (iDetail == true) {
				currentShow = getCharge();
			} else if (iDetail == false) {
				currentShow = null;
			}
			PublicMethod.myOutput("------------recharge===" + currentShow);

			// ������ 7.7 �����޸ģ��ж�ȡ������Ϣ�Ƿ�Ϊ��
			if (currentShow == null || currentShow.length == 0) {
				PublicMethod.myOutput("---------------?????????????????????");
				// ���Ϊ������ռ�¼ 2010/7/8 �³�
				String[] str = { "��¼Ϊ��" };
				String[] str_mode = { "" };
				String[] str_yu_e = { "" };
				UserCenter_AccountDetail_TradingDate = str;
				UserCenter_AccountDetail_TradingMode = str_mode;
				UserCenter_AccountDetail_Yu_E = str_yu_e;
				ListView listview = (ListView) findViewById(R.id.usercenter_accountdetails_listview_id);
				UserCenterAccountDetailsTabsAdapter adapter = new UserCenterAccountDetailsTabsAdapter(
						RuyiHelper.this);
				// PublicMethod.addHeight(this, listview);
				listview.setAdapter(adapter);

				PublicMethod.setmydividerHeight(listview);
				// TextView noInfo = (TextView)
				// findViewById(R.id.usercenter_accountdetails_no_info_text);
				// noInfo.setText(R.string.noinfo);
			} else {
				// TextView noInfo = (TextView)
				// findViewById(R.id.usercenter_accountdetails_no_info_text);
				// noInfo.setText("");
				getTotalPage();
				showUserCenterAccountDetailsTabs(currentPage);
			}
			break;
		// ֧��
		case R.id.usercenter_pay:

			// ������ 7.8 �޸ı���ͼƬ
			rechargebtn.setImageDrawable(getResources().getDrawable(
					R.drawable.rechargedisable));
			paybtn.setImageDrawable(getResources().getDrawable(
					R.drawable.zhifuenable));
			awardschoolbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.jiangpaidisable));
			withdrawbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.tixiandisable));
			allbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.alldisable));
			if (iDetail == true) {
				currentShow = getPay();
			} else if (iDetail == false) {
				currentShow = null;
			}
			PublicMethod.myOutput("------------pay===" + currentShow);

			// ������ 7.7 �����޸ģ��ж�ȡ������Ϣ�Ƿ�Ϊ��
			if (currentShow == null || currentShow.length == 0) {
				// ���Ϊ������ռ�¼ 2010/7/8�³�
				String[] str = { "��¼Ϊ��" };
				String[] str_mode = { "" };
				String[] str_yu_e = { "" };
				UserCenter_AccountDetail_TradingDate = str;
				UserCenter_AccountDetail_TradingMode = str_mode;
				UserCenter_AccountDetail_Yu_E = str_yu_e;
				ListView listview = (ListView) findViewById(R.id.usercenter_accountdetails_listview_id);
				UserCenterAccountDetailsTabsAdapter adapter = new UserCenterAccountDetailsTabsAdapter(
						RuyiHelper.this);
				// adapter.isEmpty();
				// PublicMethod.addHeight(this, listview);
				listview.setAdapter(adapter);
				PublicMethod.setmydividerHeight(listview);
				// TextView noInfo = (TextView)
				// findViewById(R.id.usercenter_accountdetails_no_info_text);
				// noInfo.setText(R.string.noinfo);
			} else {
				// TextView noInfo = (TextView)
				// findViewById(R.id.usercenter_accountdetails_no_info_text);
				// noInfo.setText("");
				getTotalPage();
				showUserCenterAccountDetailsTabs(currentPage);
			}
			break;
		// ����
		case R.id.usercenter_awardschool:

			// ������ 7.8 �޸ı���ͼƬ
			rechargebtn.setImageDrawable(getResources().getDrawable(
					R.drawable.rechargedisable));
			paybtn.setImageDrawable(getResources().getDrawable(
					R.drawable.zhifudisable));
			awardschoolbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.jiangpaienable));
			withdrawbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.tixiandisable));
			allbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.alldisable));
			if (iDetail == true) {
				currentShow = getReward();// getAccountDetail_array();
			} else if (iDetail == false) {
				currentShow = null;
			}
			PublicMethod.myOutput("------------award===" + currentShow);

			// ������ 7.7 �����޸ģ��ж�ȡ������Ϣ�Ƿ�Ϊ��
			if (currentShow == null || currentShow.length == 0) {
				// ���Ϊ������ռ�¼ 2010/7/8�³�
				String[] str = { "��¼Ϊ��" };
				String[] str_mode = { "" };
				String[] str_yu_e = { "" };
				UserCenter_AccountDetail_TradingDate = str;
				UserCenter_AccountDetail_TradingMode = str_mode;
				UserCenter_AccountDetail_Yu_E = str_yu_e;
				ListView listview = (ListView) findViewById(R.id.usercenter_accountdetails_listview_id);
				UserCenterAccountDetailsTabsAdapter adapter = new UserCenterAccountDetailsTabsAdapter(
						RuyiHelper.this);
				// adapter.isEmpty();
				// PublicMethod.addHeight(this, listview);
				listview.setAdapter(adapter);
				PublicMethod.setmydividerHeight(listview);
				// TextView noInfo = (TextView)
				// findViewById(R.id.usercenter_accountdetails_no_info_text);
				// noInfo.setText(R.string.noinfo);
			} else {
				// TextView noInfo = (TextView)
				// findViewById(R.id.usercenter_accountdetails_no_info_text);
				// noInfo.setText("");
				getTotalPage();
				showUserCenterAccountDetailsTabs(currentPage);
			}
			break;
		// ����
		case R.id.usercenter_withdraw:

			// ������ 7.8 �޸ı���ͼƬ
			rechargebtn.setImageDrawable(getResources().getDrawable(
					R.drawable.rechargedisable));
			paybtn.setImageDrawable(getResources().getDrawable(
					R.drawable.zhifudisable));
			awardschoolbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.jiangpaidisable));
			withdrawbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.tixianenable));
			allbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.alldisable));
			if (iDetail == true) {
				currentShow = getCash();// getAccountDetail_array();
			} else if (iDetail == false) {
				currentShow = null;
			}
			PublicMethod.myOutput("-----------withdraw===" + currentShow);

			// ������ 7.7 �����޸ģ��ж�ȡ������Ϣ�Ƿ�Ϊ��
			if (currentShow == null || currentShow.length == 0) {
				// ���Ϊ������ռ�¼ 2010/7/8 �³�
				String[] str = { "��¼Ϊ��" };
				String[] str_mode = { "" };
				String[] str_yu_e = { "" };
				UserCenter_AccountDetail_TradingDate = str;
				UserCenter_AccountDetail_TradingMode = str_mode;
				UserCenter_AccountDetail_Yu_E = str_yu_e;
				ListView listview = (ListView) findViewById(R.id.usercenter_accountdetails_listview_id);
				UserCenterAccountDetailsTabsAdapter adapter = new UserCenterAccountDetailsTabsAdapter(
						RuyiHelper.this);
				// adapter.isEmpty();
				// PublicMethod.addHeight(this, listview);
				listview.setAdapter(adapter);
				PublicMethod.setmydividerHeight(listview);
				// TextView noInfo = (TextView)
				// findViewById(R.id.usercenter_accountdetails_no_info_text);
				// noInfo.setText(R.string.noinfo);
			} else {
				// TextView noInfo = (TextView)
				// findViewById(R.id.usercenter_accountdetails_no_info_text);
				// noInfo.setText("");
				getTotalPage();
				showUserCenterAccountDetailsTabs(currentPage);
			}
			break;
		// ����
		case R.id.usercenter_return:
			showListView(ID_USERCENTER);
			break;

		}
	}

	int getStartIndex(int aPage) {
		int s = aPage * pageNum;
		return s;
	}

	int getEndIndex(int aPage) {
		int e;
		if (aPage < totalPage - 1) {
			e = pageNum + pageNum * aPage;
		} else {
			if (currentShow.length % pageNum == 0)
				e = pageNum + pageNum * aPage;
			else
				e = currentShow.length % pageNum + pageNum * aPage;
		}
		return e;
	}

	// �û�����-�˻���ϸ-ȫ��
	protected void showUserCenterAccountDetailsTabs(int aPage) {

		iQuitFlag = 30;
		// ��ʾȫ����Ϣ�е�ǰ���� �³� 2010/7/3
		int startindex = getStartIndex(aPage);
		int endindex = getEndIndex(aPage);
		String[] UserCenter_AccountDetail_TradingDate_All = new String[endindex
				- startindex + 1];
		String[] UserCenter_AccountDetail_TradingMode_All = new String[endindex
				- startindex + 1];
		String[] UserCenter_AccountDetail_Yu_E_All = new String[endindex
				- startindex + 1];
		UserCenter_AccountDetail_TradingDate_All[0] = "            ����ʱ��";
		UserCenter_AccountDetail_TradingMode_All[0] = "��������";
		UserCenter_AccountDetail_Yu_E_All[0] = "�����  ";

		for (int i = startindex; i < endindex; i++) {
			UserCenter_AccountDetail_TradingDate_All[i - startindex + 1] = currentShow[i][6];
			UserCenter_AccountDetail_TradingMode_All[i - startindex + 1] = currentShow[i][3];
			UserCenter_AccountDetail_Yu_E_All[i - startindex + 1] = currentShow[i][4];
		}

		UserCenter_AccountDetail_TradingDate = UserCenter_AccountDetail_TradingDate_All;
		UserCenter_AccountDetail_TradingMode = UserCenter_AccountDetail_TradingMode_All;
		UserCenter_AccountDetail_Yu_E = UserCenter_AccountDetail_Yu_E_All;

		ListView listview = (ListView) findViewById(R.id.usercenter_accountdetails_listview_id);
		// listview.set
		UserCenterAccountDetailsTabsAdapter adapter = new UserCenterAccountDetailsTabsAdapter(
				this);
		// PublicMethod.addHeight(this, listview);
		listview.setAdapter(adapter);

		PublicMethod.setmydividerHeight(listview);

		final Button accountdetail_uppage = (Button) findViewById(R.id.usercenter_upPage);
		final Button accountdetail_downpage = (Button) findViewById(R.id.usercenter_downPage);

		// ��ӷ�ҳ���� 2010/7/7 �³�
		if (totalPage < 2) {
			accountdetail_uppage.setClickable(false);
			accountdetail_downpage.setClickable(false);

		} else {
			accountdetail_downpage.setOnClickListener(new OnClickListener() {
				// int page=0;
				// �ж��Ƿ���Է�ҳ

				@Override
				public void onClick(View v) {
					PublicMethod.myOutput("-----totalPage--------" + totalPage);
					if (currentShow == null || currentShow.length == 0
							|| currentPage >= totalPage) {
						return;
					} else {
						if (currentPage < totalPage - 1) {
							currentPage++;

							showUserCenterAccountDetailsTabs(currentPage);
						}
						if (currentPage > 0) {
							accountdetail_uppage.setClickable(true);
						} else {
							accountdetail_uppage.setClickable(false);
							// accountdetail_uppage dis
						}
						if (currentPage < totalPage - 1) {
							accountdetail_downpage.setClickable(true);
							// accountdetail_downpage en
						} else {
							accountdetail_downpage.setClickable(false);
							// accountdetail_downpage dis
						}
					}
				}
			});

			// ��ȡ��һҳ����
			accountdetail_uppage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (currentShow == null || currentShow.length == 0
							|| currentPage >= totalPage) {
						return;
					} else {
						if (currentPage > 0) {
							currentPage--;
							showUserCenterAccountDetailsTabs(currentPage);
						}
						if (currentPage > 0) {
							accountdetail_uppage.setClickable(true);
						} else {
							accountdetail_uppage.setClickable(false);
						}
						if (currentPage < totalPage - 1) {
							accountdetail_downpage.setClickable(true);
						} else {
							accountdetail_downpage.setClickable(false);
						}
					}
				}
			});
		}
	}

	// �û�����-�˻���ϸ-��ǩ������
	public static class UserCenterAccountDetailsTabsAdapter extends BaseAdapter {

		protected LayoutInflater mInflater;

		public UserCenterAccountDetailsTabsAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return UserCenter_AccountDetail_TradingDate.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;

			if (convertView == null) {

				convertView = mInflater.inflate(
						R.layout.usercenter_accountdetails_listview_layout,
						null);
				holder = new ViewHolder();

				holder.UserCenter_AccountDetail_TradingDate_View = (TextView) convertView
						.findViewById(R.id.user_center_account_detail_trading_date_id);
				holder.UserCenter_AccountDetail_TradingMode_View = (TextView) convertView
						.findViewById(R.id.user_center_account_detail_trading_mode_id);
				holder.UserCenter_AccountDetail_Yu_E_View = (TextView) convertView
						.findViewById(R.id.user_center_account_detail_yu_e_id);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			for (int i = 0; i < getCount(); i++) {

				if (position == i) {

					holder.UserCenter_AccountDetail_TradingDate_View
							.setText(UserCenter_AccountDetail_TradingDate[position]);
					holder.UserCenter_AccountDetail_TradingMode_View
							.setText(UserCenter_AccountDetail_TradingMode[position]);
					holder.UserCenter_AccountDetail_Yu_E_View
							.setText(UserCenter_AccountDetail_Yu_E[position]);

				}

			}

			return convertView;
		}

		static class ViewHolder {
			TextView UserCenter_AccountDetail_TradingDate_View;
			TextView UserCenter_AccountDetail_TradingMode_View;
			TextView UserCenter_AccountDetail_Yu_E_View;
		}
	}

	// ר�ҷ���������
	public static class ExpertAnalyzeEfficientAdapter extends BaseAdapter {

		protected LayoutInflater mInflater;
		protected Bitmap mShuangSeQiu;
		protected Bitmap mFuCai;
		protected Bitmap mQiLeCai;
		protected Bitmap mPaiLieSan;
		protected Bitmap mDaLeTou;
		// protected Bitmap mSSC;
		protected Bitmap mSFC;

		// protected Bitmap mRX9;
		// protected Bitmap mJQC;
		// protected Bitmap mLCB;

		public ExpertAnalyzeEfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);

			mShuangSeQiu = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.list_ssq);
			mFuCai = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.list_fc3d);
			mQiLeCai = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.list_qlc);
			mPaiLieSan = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.list_pl3);
			mDaLeTou = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.list_dlt);
			// mSSC = BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.ssc);
			mSFC = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.list_zu);
			//
			// mJQC = BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.jinqiucai);
			// mLCB = BitmapFactory.decodeResource(context.getResources(),
			// R.drawable.liuchangban);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return expertAnalyzeTypeName.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.expert_analyze_listview, null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView
						.findViewById(R.id.expert_analyze_icon_id);
				holder.expertAnalyzeTypeNameView = (TextView) convertView
						.findViewById(R.id.expert_analyze_typename_id);
				// holder.expertAnalyzeMoreView = (TextView)
				// convertView.findViewById(R.id.expert_analyze_more_id);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == 0) {
				holder.icon.setImageBitmap(mShuangSeQiu);
				holder.expertAnalyzeTypeNameView
						.setText(expertAnalyzeTypeName[position]);
				// holder.expertAnalyzeMoreView.setText(expertAnalyzeMore[position]);
			} else if (position == 1) {
				holder.icon.setImageBitmap(mFuCai);
				holder.expertAnalyzeTypeNameView
						.setText(expertAnalyzeTypeName[position]);
				// holder.expertAnalyzeMoreView.setText(expertAnalyzeMore[position]);
			} else if (position == 2) {
				holder.icon.setImageBitmap(mQiLeCai);
				holder.expertAnalyzeTypeNameView
						.setText(expertAnalyzeTypeName[position]);
				// holder.expertAnalyzeMoreView.setText(expertAnalyzeMore[position]);
			} else if (position == 3) {
				holder.icon.setImageBitmap(mPaiLieSan);
				holder.expertAnalyzeTypeNameView
						.setText(expertAnalyzeTypeName[position]);
				// holder.expertAnalyzeMoreView.setText(expertAnalyzeMore[position]);
			} else if (position == 4) {
				holder.icon.setImageBitmap(mDaLeTou);
				holder.expertAnalyzeTypeNameView
						.setText(expertAnalyzeTypeName[position]);
				// holder.expertAnalyzeMoreView.setText(expertAnalyzeMore[position]);

			} else if (position == 5) {
				holder.icon.setImageBitmap(mSFC);
				holder.expertAnalyzeTypeNameView
						.setText(expertAnalyzeTypeName[position]);

			}

			return convertView;
		}

		static class ViewHolder {
			ImageView icon;
			TextView expertAnalyzeTypeNameView;
			// TextView expertAnalyzeMoreView;
		}
	}

	// ��á����ࡱ�б�������������Դ
	protected List<Map<String, Object>> getListForMoreAdapter() {

		String[] titles = { getString(R.string.ruyichuangqing),
				getString(R.string.ruyitaocan),
				getString(R.string.xingyunxuanhao),
				getString(R.string.zhuanjiafenxi),
				getString(R.string.yonghuzhongxin),
				getString(R.string.bangzhuzhongxin),
				getString(R.string.xitongshezhi),
				getString(R.string.yonghufankui),
				getString(R.string.gongsijianjie) };
		int it = R.drawable.xiangyou;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, titles[i]);
			map.put(IICON, it);

			list.add(map);

		}

		return list;
	}

	// ������������б�������������Դ
	protected List<Map<String, Object>> getListForRuyiHelperAdapter() {

		String[] titles = { getString(R.string.ruyihelper_gameIntroduction),
				getString(R.string.ruyihelper_zhongjiang),
				getString(R.string.ruyihelper_goucailiucheng),
				getString(R.string.ruyihelper_mimazhaohui),
				getString(R.string.ruyihelper_kefudianhua) };
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);

		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, titles[i]);
			list.add(map);
		}

		return list;

	}

	protected List<Map<String, Object>> getListForEspecialAdapter() {
		// int[] drawIds = { R.drawable.ruyichuanqing1, R.drawable.ruyitaocan1,
		// R.drawable.xingyunxuanhao1 };
		String[] titles = { getString(R.string.ruyichuangqing),
				getString(R.string.ruyitaocan),
				getString(R.string.xingyunxuanhao) };

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);

		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			// map.put(ICON, drawIds[i]);
			map.put(TITLE, titles[i]);
			list.add(map);
		}

		return list;
	}

	// ����û������б�������������Դ
	protected List<Map<String, Object>> getListForUserCenterAdapter() {

		String[] titles = { getString(R.string.usercenter_winningCheck),
				getString(R.string.usercenter_bettingDetails),
				getString(R.string.usercenter_trackNumberInquiry),
				getString(R.string.ruyihelper_balanceInquiry),
				getString(R.string.usercenter_giftCheck) };

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);

		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			// map.put(ICON, drawIds[i]);
			map.put(TITLE, titles[i]);
			list.add(map);
		}

		return list;

	}

	// �˻���������Դ
	protected List<Map<String, Object>> getListForAccountAdapter() {
		// int[] drawIds = { R.drawable.zhanghumingxi, R.drawable.zhanghutixian,
		// R.drawable.mimaxiugai };
		String[] titles = { getString(R.string.usercenter_accountDetails),
				getString(R.string.ruyihelper_accountWithdraw),
				getString(R.string.usercenter_passwordChange) };

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);

		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, titles[i]);
			list.add(map);
		}

		return list;
	}

	// Ͷע��ѯ��Ҫ��ȡ����Ϣ// ֮ǰ˫ɫ��ʽ����ע���ʽ��������, 2010/7/4 �³� 2010/7/5֮ǰ����3D��������
	// cc 8.13
	// public void BettingVector(){

	public void JsonBetting(JSONArray aJsonArray) {

		for (int i = 0; i < aJsonArray.length(); i++) {
			BettingDetail bettingDetail = new BettingDetail();
			try {
				String[] betStr = getBetcode(aJsonArray.getJSONObject(i)
						.getString("betcode"), aJsonArray.getJSONObject(i)
						.getString("play_name"));
				bettingDetail.batchCode = aJsonArray.getJSONObject(i)
						.getString("batchcode");
				bettingDetail.playName = betStr[0];
				bettingDetail.betCode = betStr[1];
				bettingDetail.sell_datetime = aJsonArray.getJSONObject(i)
						.getString("sell_datetime");
				bettingDetail.cash_date_time = aJsonArray.getJSONObject(i)
						.getString("cash_date_time");
				bettingDetail.amt = Integer
						.toString(Integer.parseInt(aJsonArray.getJSONObject(i)
								.getString("amt")) / 100);
				bettingDetail.maxLine = aJsonArray.getJSONObject(i).getString(
						"maxLine");

			} catch (JSONException e) {
				e.printStackTrace();
			}
			bettingVector.add(bettingDetail);
		}
	}

	public String encashFlag(String encash_flag) {
		String encashFlag = "";
		if (encash_flag.equals("0")) {
			encashFlag = "δ�ҽ�";
		} else if (encash_flag.equals("1")) {
			encashFlag = "�Ѷҽ�";
		} else if (encash_flag.equals("9")) {
			encashFlag = "��";
		}
		return encashFlag;
	}

	public String prizeInfo(String prizeinfo) {
		String prizeInfo = "";
		int num = 0;
		int startIndex = 0;
		int endIndex = -1;
		for (int i = 0; i < prizeinfo.length(); i++) {
			if (prizeinfo.charAt(i) == ',') {
				startIndex = endIndex + 1;
				endIndex = i;
				num++;
				if (!prizeinfo.substring(startIndex, endIndex).equals("0")) {
					prizeInfo = num + "�Ƚ�"
							+ prizeinfo.substring(startIndex, endIndex) + "ע";
				}
			}

		}
		return prizeInfo;
	}

	public void JsonWinningSelect(JSONArray aJsonArray) {

		for (int i = 0; i < aJsonArray.length(); i++) {
			WinningDetail winningDetail = new WinningDetail();
			try {
				String[] betStr = getBetcode(aJsonArray.getJSONObject(i)
						.getString("code"), aJsonArray.getJSONObject(i)
						.getString("play_name"));
				winningDetail.batchCode = aJsonArray.getJSONObject(i)
						.getString("batchcode");
				winningDetail.playName = betStr[0];
				winningDetail.betCode = betStr[1];
				winningDetail.sell_datetime = aJsonArray.getJSONObject(i)
						.getString("sell_datetime");
				winningDetail.maxLine = aJsonArray.getJSONObject(i).getString(
						"maxLine");
				winningDetail.prizeamt = PublicMethod.changeMoney(aJsonArray
						.getJSONObject(i).getString("prizeamt"));
				winningDetail.prizetime = aJsonArray.getJSONObject(i)
						.getString("prizetime");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			WinningVector.add(winningDetail);
		}
	}

	// ׷�Ų�ѯjson���� cc 8.27
	protected String getState(String string, String lastNum) {
		int lastnum = Integer.parseInt(lastNum);
		String state = "";
		if (string.equals("0")) {
			if (lastnum > 0) {
				state = "������";
			} else {
				state = "��׷��";
			}
		}
		// if(string.equals("1")){
		// state="��ͣ";
		// }
		if (string.equals("2")) {
			if (lastnum > 0) {
				state = "��ȡ��";
			} else {
				state = "��׷��";
			}
		}
		if (string.equals("3")) {
			state = "��׷��";
		}
		return state;
	}

	public void JsonAddNumSelect(JSONArray aJsonArray) {

		for (int i = 0; i < aJsonArray.length(); i++) {
			AddNumDetail addNumDetail = new AddNumDetail();
			String[] betStr = null;
			try {
				betStr = getBetcode(aJsonArray.getJSONObject(i).getString("betcode"), aJsonArray.getJSONObject(i).getString("lotNo"));
				addNumDetail.play_name = betStr[0];
				addNumDetail.betcode = betStr[1];
				addNumDetail.betNum = aJsonArray.getJSONObject(i).getString("betNum");
				addNumDetail.batchNum = aJsonArray.getJSONObject(i).getString("batchNum");
				addNumDetail.lastNum = aJsonArray.getJSONObject(i).getString("lastNum");
				addNumDetail.addamount = PublicMethod.changeMoney(aJsonArray.getJSONObject(i).getString("amount"));
				addNumDetail.beginBatch = aJsonArray.getJSONObject(i).getString("beginBatch");
				addNumDetail.addednum = Integer.parseInt(addNumDetail.batchNum)- Integer.parseInt(addNumDetail.lastNum) + "";
				try {
					addNumDetail.addedamount = Integer.parseInt(addNumDetail.addamount)* Integer.parseInt(addNumDetail.batchNum) + "";
				} catch (Exception e) {
					addNumDetail.addedamount = Double.parseDouble(addNumDetail.addamount)* Integer.parseInt(addNumDetail.batchNum) + "";
				}
				addNumDetail.maxLine = aJsonArray.getJSONObject(i).getString("maxLine");
				addNumDetail.state = getState(aJsonArray.getJSONObject(i).getString("state"), addNumDetail.lastNum);
				addNumDetail.tsubscribeId = aJsonArray.getJSONObject(i).getString("flowNo");
				addNumDetail.orderTime = aJsonArray.getJSONObject(i).getString("orderTime");
				addNumDetail.lotMulti = aJsonArray.getJSONObject(i).getString("lotMulti");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				addNumDetail.lotMulti = betStr[2];
				e.printStackTrace();
			}
			AddNumVector.add(addNumDetail);
			PublicMethod.myOutput("----------" + AddNumVector.elementAt(i));
			// bettingVector.bettingVector.elementAt(i);
		}
	}

	// ���Ͳ�ѯjson���� cc 8.27
	public void JsonGiftSelect(JSONArray aJsonArray) {
		if (aJsonArray != null) {
			for (int i = 0; i < aJsonArray.length(); i++) {
				GiftDetail giftDetail = new GiftDetail();
				try {
					String[] betStr = getBetcode(aJsonArray.getJSONObject(i)
							.getString("bet_code"), aJsonArray.getJSONObject(i)
							.getString("lotno"));
					giftDetail.play_name = betStr[0];
					giftDetail.betcode = betStr[1];
					giftDetail.to_mobile_code = aJsonArray.getJSONObject(i)
							.getString("to_mobile_code");
					giftDetail.term_begin_datetime = aJsonArray
							.getJSONObject(i).getString("sell_datetime");
					giftDetail.sell_term_code = aJsonArray.getJSONObject(i)
							.getString("sell_term_code");
					giftDetail.amount = PublicMethod.changeMoney(aJsonArray
							.getJSONObject(i).getString("amount"));
					giftDetail.maxLine = aJsonArray.getJSONObject(i).getString(
							"maxLine");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				GiftVector.add(giftDetail);
				PublicMethod.myOutput("----------" + GiftVector.elementAt(i));
				// bettingVector.bettingVector.elementAt(i);
			}
		}

	}

	// �����Ͳ�ѯjson���� cc 8.27
	public void JsonGiftedSelect(JSONArray aJsonArray) {
		if (aJsonArray != null) {
			for (int i = 0; i < aJsonArray.length(); i++) {
				GiftedDetail giftedDetail = new GiftedDetail();
				try {
					String[] betStr = getBetcode(aJsonArray.getJSONObject(i)
							.getString("bet_code"), aJsonArray.getJSONObject(i)
							.getString("lotno"));
					giftedDetail.play_name = betStr[0];
					giftedDetail.betcode = betStr[1];
					giftedDetail.from_mobile_code = aJsonArray.getJSONObject(i)
							.getString("from_mobile_code");
					giftedDetail.term_begin_datetime = aJsonArray
							.getJSONObject(i).getString("sell_datetime");
					giftedDetail.sell_term_code = aJsonArray.getJSONObject(i)
							.getString("sell_term_code");
					giftedDetail.amount = PublicMethod.changeMoney(aJsonArray
							.getJSONObject(i).getString("amount"));
					giftedDetail.maxLine = aJsonArray.getJSONObject(i)
							.getString("maxLine");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				GiftedVector.add(giftedDetail);
				PublicMethod.myOutput("----------" + GiftedVector.elementAt(i));
			}
			// bettingVector.bettingVector.elementAt(i);
		}

	}

	/**
	 * ��ȡר�ҷ���
	 * @return
	 */
	public String[] expertAnalysis() {

		String contents = null;
		String[] str = { "", "" };
		try {
			String re = ExpertAnalysisInterface.getInstance().analysis(sessionid);
			JSONObject obj = new JSONObject(re);
			contents = obj.getString("content");
			str = mySplict(contents, '|');
		} catch (JSONException e) {

		}
		return str;
	}

	// �и��ַ������� ר�ҷ��� 2010/7/4 �³�
	protected String[] mySplict(String str, char chr) {
		String[] data = null;
		try {
			// vector���ܺܵ�,��System.arraycopy������vector;������System.arraycopy��ʹ�÷������ŵ�
			Vector vector = new Vector();
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if (chr == c) {
					// PublicMethod.myOutput(i);
					vector.addElement(new Integer(i));
				}
			}
			// �ַ�����û��Ҫ�и���ַ�
			if (vector.size() == 0) {
				data = new String[] { str };
			}
			// int =((Integer)vector.elementAt(0)).intValue();
			// int index2=((Integer)vector.elementAt(1)).intValue();
			// String user1=str.substring(0,index );
			// PublicMethod.myOutput(user1);
			//	
			// String user2=str.substring(index+1, index2);
			// PublicMethod.myOutput(user2);
			//	
			// String user3=str.substring(index2+1);
			// PublicMethod.myOutput(user3);
			if (vector.size() >= 1) {
				data = new String[vector.size() + 1];
			}
			for (int i = 0; i < vector.size(); i++) {
				int index = ((Integer) vector.elementAt(i)).intValue();
				String temp = "";
				if (i == 0)// ��һ��#
				{
					if (vector.size() == 1) {
						temp = str.substring(index + 1);
						data[1] = temp;
					}
					temp = str.substring(0, index);
					// PublicMethod.myOutput(temp);
					data[0] = temp;
				} else if (i == vector.size() - 1)// //���һ��#
				{
					int preIndex = ((Integer) vector.elementAt(i - 1))
							.intValue();
					temp = str.substring(preIndex + 1, index);// ���һ��#ǰ�������
					// PublicMethod.myOutput(temp);
					data[i] = temp;

					temp = str.substring(index + 1);// ���һ��#���������
					// PublicMethod.myOutput(temp);
					data[i + 1] = temp;
				} else {
					int preIndex = ((Integer) vector.elementAt(i - 1))
							.intValue();
					temp = str.substring(preIndex + 1, index);// ���һ��#ǰ�������
					// PublicMethod.myOutput(temp);
					data[i] = temp;
				}

			}
			// �����õ�
			// if (data != null) {
			// for (int i = 0; i < data.length; i++) {
			// //PublicMethod.myOutput(i + "=" + data[i]);
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return data;
		}
	}

	// �˻���ϸ��Ҫ��ȡ����Ϣ �³� 2010/7/3

	protected String[] getAccountDetail(int index) {
		String[] accountdetail = null;
		try {

			JSONObject obj = jsonArray.getJSONObject(index);
			String amt = obj.getString("amt");
			String drawamt = obj.getString("drawamt");
			String blsign = obj.getString("blsign");
//			String memo = obj.getString("memo");
//			String memotemp = typeReset(memo);	
			String type = obj.getString("type");
//			String memotemp = changeStr(type);	
			String balance = PublicMethod.changeMoney(obj.getString("balance"));
			String drawamtBalance = obj.getString("drawamtBalance");
			String Date = obj.getString("plattime");
			String[] str = { amt, drawamt, blsign, type, balance,
					drawamtBalance, Date };
			accountdetail = str;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountdetail;
	}

	protected String[][] getAccountDetail_array() {
		String[][] accountdetail_array = new String[jsonArray.length()][7];
		PublicMethod.myOutput("------------+++++++++++"
				+ accountdetail_array[0].length);
		PublicMethod.myOutput("------------++++++=======" + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			PublicMethod.myOutput("-------------@@@@@@@@@"
					+ getAccountDetail(i)[6]);

			accountdetail_array[i] = getAccountDetail(i);
			PublicMethod.myOutput("-------------@@@@@@@@@+++++++"
					+ accountdetail_array[i][0]);
		}
		PublicMethod.myOutput("-----------------getAccountDetail_array"
				+ accountdetail_array[0][0]);
		return accountdetail_array;

	}

	// public void showAddNum(int index) {
	// try {
	// JSONObject obj = jsonArray.getJSONObject(index);
	// play_name = obj.getString("lotNo");
	// betcode = obj.getString("betcode");
	// betNum = obj.getString("betNum");
	// batchNum = obj.getString("batchNum");
	// lastNum = obj.getString("lastNum");
	// addamount = obj.getString("amount");
	// beginBatch = obj.getString("beginBatch");
	// lastBatch = obj.getString("lastBatch");
	// addednum = Integer.parseInt(batchNum) - Integer.parseInt(lastNum)
	// + "";
	// addedamount = Integer.parseInt(addamount)
	// * Integer.parseInt(addednum) + "";
	//
	// // sell_datetime=obj.getString("sell_datetime");
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// PublicMethod.myOutput("@@@@@" + play_name + "@@@@" + batchcode + "@@@@"
	// + betNum + "@@@@@" + betcode);
	// int wayCode = Integer.parseInt(betcode.substring(0, 2));
	//
	// PublicMethod.myOutput("--------------wayCode?????" + wayCode);
	//
	// beishu = betcode.substring(2, 4);
	//
	// if (play_name.equals("B001")) {
	// if (wayCode == 00) {
	// play_name = "˫ɫ��ʽ";
	// String mp[] = GT.splitBetCode(betcode);
	// betcode = "";
	// for (int i = 0; i < mp.length; i++) {
	//
	// betcode += (GT.makeString("F47104", wayCode, mp[i]
	// .substring(4)) + "\n");
	// }
	// } else if (wayCode == 40 || wayCode == 50) {
	// play_name = "˫ɫ���ϸ�ʽ";
	// int index1 = 0;
	// for (int i = 0; i < betcode.length(); i++) {
	// if (betcode.charAt(i) == '*') {
	// index1 = i;
	// PublicMethod.myOutput("--------------index1" + index1);
	// }
	// }
	// int index2 = 0;// ���ҡ�~��
	// for (int i = 0; i < betcode.length(); i++) {
	// if (betcode.charAt(i) == '~') {
	// index2 = i;
	// PublicMethod.myOutput("--------------index2" + index2);
	// }
	// }
	// // String tmp = betcode.substring(0, index1)+"*";
	// // PublicMethod.myOutput("----------------tmp"+tmp);
	// String danma = GT.makeString("F47104", wayCode, betcode
	// .substring(4, index1));
	// String tuoma = GT.makeString("F47104", wayCode, betcode
	// .substring(index1 + 1, index2));
	// String lanqiu = GT.makeString("F47104", wayCode, betcode
	// .substring(index2 + 1, betcode.length() - 1));
	// betcode = "������: " + danma + "\n" + "��������: " + tuoma + "\n"
	// + "����" + lanqiu + "\n";
	//
	// } else {
	// play_name = "˫ɫ�������ʽ";
	// int index1 = 0;// ���ҡ�*��
	// int index2 = 0;// ����"~"
	// for (int i = 0; i < betcode.length(); i++) {
	// if (betcode.charAt(i) == '~') {
	// index1 = i;
	// PublicMethod.myOutput("--------------index1" + index1);
	// }
	// }
	//
	// String redball = GT.makeString("F47104", wayCode, betcode
	// .substring(5, index1));
	// String blueball = GT.makeString("F47104", wayCode, betcode
	// .substring(index1 + 1, betcode.length() - 1));
	//
	// betcode = "����: " + redball + "\n" + "����: " + blueball + "\n";
	// }
	// PublicMethod.myOutput("---------------@@@@@" + betcode);
	// } else if (play_name.equals("D3")) {
	// if (wayCode == 54) {
	// play_name = "����3D����";
	// int index1 = 0;
	// for (int i = 0; i < betcode.length(); i++) {
	// if (betcode.charAt(i) == '*') {
	// index1 = i;
	// PublicMethod.myOutput("--------------index1" + index1);
	// }
	// }
	// String danma = GT.makeString("F47103", wayCode, betcode
	// .substring(4, index1));
	// String tuoma = GT.makeString("F47103", wayCode, betcode
	// .substring(index1 + 1, betcode.length() - 1));
	// betcode = "����: " + danma + "\n" + "����: " + tuoma + "\n";
	// // ��ѡ��ʽ����� 2010/7/5 �³�
	// } else if (wayCode == 00) {
	// // 3D��ѡע���ʽ ���� �³� 20100714
	// // if(wayCode==00){
	// PublicMethod.myOutput("------intointointo--");
	// PublicMethod.myOutput("------betcode0000------" + betcode);
	// play_name = "����3Dֱѡ��ʽ";
	// String mp[] = GT.splitBetCode(betcode);
	// PublicMethod.myOutput("-----mp.length--------" + mp.length);
	// betcode = "";
	// for (int i = 0; i < mp.length; i++) {
	// PublicMethod.myOutput("--------????????????mp[]------"
	// + mp[i]);
	// betcode = GT.makeString("F47103", wayCode, mp[i]
	// .substring(4)
	// + "\n");
	// }
	//
	// // }
	//
	// } else if (wayCode == 20) {
	// // if(wayCode==00){
	// // play_name="����3Dֱѡ��ʽ";
	// // }
	// // if(wayCode==20){
	// play_name = "����3Dֱѡ��ʽ";
	// // }
	// int index1 = 0;
	// int index2 = 0;
	// for (int i = 0; i < betcode.length(); i++) {
	// if (betcode.charAt(i) == '^') {
	// index1 = i;
	// i = betcode.length();
	// PublicMethod.myOutput("--------------index====="
	// + index1);
	// }
	// }
	// for (int j = index1 + 1; j < betcode.length(); j++) {
	// if (betcode.charAt(j) == '^') {
	// index2 = j;
	// j = betcode.length();
	// PublicMethod.myOutput("--------------index1" + index1);
	// }
	// }
	// String baiwei = GT.makeString("F47103", wayCode, betcode
	// .substring(6, index1 + 1));
	// String shiwei = GT.makeString("F47103", wayCode, betcode
	// .substring(index1 + 3, index2));
	// String gewei = GT.makeString("F47103", wayCode, betcode
	// .substring(index2 + 3, betcode.length() - 1));
	// betcode = "��λ: " + baiwei + "\n" + "ʮλ: " + shiwei + "\n"
	// + "��λ: " + gewei + "\n";
	//
	// // 3Dֱѡ��ʽ ע�����
	// }
	//
	// else {
	// // if(wayCode==00){
	// // play_name="����3D��ʽ";
	// //
	// // }
	// if (wayCode == 01) {
	// play_name = "����3D��3";
	// } else if (wayCode == 02) {
	// play_name = "����3D��6";
	// } else if (wayCode == 10) {
	// play_name = "����3Dֱѡ��ֵ";
	// } else if (wayCode == 11) {
	// play_name = "����3D��3��ֵ";
	// } else if (wayCode == 12) {
	// play_name = "����3D��6��ֵ";
	// } else if (wayCode == 31) {
	// play_name = "����3D��3��ʽ";
	// } else if (wayCode == 31) {
	// play_name = "����3D��6��ʽ";
	// }
	// String mp[] = GT.splitBetCode(betcode);
	// betcode = "";
	// for (int i = 0; i < mp.length; i++) {
	// betcode += (GT.makeString("F47103", wayCode, mp[i]
	// .substring(4)) + "\n");
	// }
	// }
	// } else if (play_name.equals("QL730")) {
	// if (wayCode == 00) {
	// int index_q;
	// String mp[] = GT.splitBetCode(betcode);
	// play_name = "���ֲʵ�ʽ";
	// betcode = "";
	// for (int i = 0; i < mp.length; i++) {
	// betcode += (GT.makeString("F47102", wayCode, mp[i]
	// .substring(4)) + "\n");
	// }
	// } else if (wayCode == 10) {
	// play_name = "���ֲʸ�ʽ";
	// betcode = GT.makeString("F47102", wayCode, betcode.substring(5,
	// betcode.length() - 1))
	// + "\n";
	// } else if (wayCode == 20) {
	// play_name = "���ֲʵ���";
	// int index1 = 0;
	// for (int i = 0; i < betcode.length(); i++) {
	// if (betcode.charAt(i) == '*') {
	// index1 = i;
	// PublicMethod.myOutput("--------------index1" + index1);
	// }
	// }
	// // String tmp = betcode.substring(0, index1)+"*";
	// // PublicMethod.myOutput("----------------tmp"+tmp);
	// String danma = GT.makeString("F47102", wayCode, betcode
	// .substring(4, index1));
	// String tuoma = GT.makeString("F47102", wayCode, betcode
	// .substring(index1 + 1, betcode.length() - 1));
	// betcode = "����: " + danma + "\n" + "����: " + tuoma + "\n";
	// }
	// // String mp[]= GT.splitBetCode(betcode);
	//
	// }
	//
	// }

	/**
	 * �˻�������ʽ���� �³� 2010/7/3
	 * 
	 * @param str
	 *            �������˵Ĳ�������
	 */
	protected String typeReset(String str) {
		String strTemp = "";

		if (str.equals("�㿨��ֵ") || str.equals("���п���ֵ") || str.equals("��ֵ")
				|| str.equals("��ʿ���ֵ") || str.equals("����ʿ���ֵ")) {
			strTemp = "��ֵ";
		} else if (str.equals("�û����ֿ�������") || str.equals("�û�׷��Ͷע�ۿ�")
				|| str.equals("�㿨��ֵ�۳�������") || str.equals("��Ʊ���Ϳۿ�")
				|| str.equals("�û�Ͷע�ۿ�")) {
			strTemp = "֧��";// str.equals("�û��ҽ�����") ||
			// ||str.equals("�û��˻��ⶳ")||str.equals("�û��˻�����")
		} else if (str.equals("�û��ҽ�����") || str.equals("��ʶҽ�����")) {// ||str.equals("������򷽰��н��û�����")||str.equals("��������ʣ�ཱ�𻮿�")
			strTemp = "����";
		} else if (str.equals("�û����ֿ����ֽ��")) {
			strTemp = "����";
		} else if (str.equals("�û��˻��ⶳ")) {
			strTemp = "�˻��ⶳ";
		} else if (str.equals("�û��˻�����")) {
			strTemp = "�˻�����";
		}

		return strTemp;
	}

	// �������ӿ�
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_FORGET_PASSWORD:
			LayoutInflater factory = LayoutInflater.from(this);
			final View forgetPwd = factory.inflate(
					R.layout.alert_dialog_forget_password, null);
			return new AlertDialog.Builder(RuyiHelper.this).setIcon(
					R.drawable.star_big_on).setTitle(R.string.forget_password)
					.setView(forgetPwd).setPositiveButton(R.string.giveCall,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									Intent myIntentDial = new Intent(
											"android.intent.action.CALL", Uri
													.parse("tel:4006651000"));
									startActivity(myIntentDial);
								}
							}).setNegativeButton(R.string.return_button,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).create();

		case DIALOG1_KEY: {
			progressdialog = new ProgressDialog(this);
			// progressdialog.setTitle("Indeterminate");
			progressdialog.setMessage("����������...");
			progressdialog.setIndeterminate(true);
			// progressdialog.setCancelable(true);
			return progressdialog;
		}
		}
		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			PublicMethod.myOutput("-------iType----" + iType);
			if (iType == 1) {
				accountDetail();
			} else if (iType == 0) {
				UserCenterDetail();
			} else if (iType == 2) {
				accountDetailTwo();
			} else if (iType == 3) {
				showListView(ID_RUYICHUANQING);
			} else if (iType == 4) {
				showListView(ID_RUYITAOCAN);
			} else if (iType == 5) {
				showListView(ID_XINGYUNXUANHAO);
			} else if (iType == 6) {
				buyLuck();
			}
			break;
		// default:
		// Toast.makeText(RuyiHelper.this, "δ��¼�ɹ�", Toast.LENGTH_SHORT).show();
		// break;
		}
	}

	protected void showChangePasswordDialog() {
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		phonenum = shellRW.getUserLoginInfo("phonenum");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		// ������ 7.4 �����޸ģ���ӵ�½���ж�
		if (sessionid == null || sessionid.equals("")) {
			Intent intentSession = new Intent(RuyiHelper.this, UserLogin.class);
			// startActivity(intentSession);
			startActivityForResult(intentSession, 0);
		} else {

			showUserCenterPasswordChange();
		}
	}

	// 20100803 �³� �û�������ϸ�ӱ�ǩ��������
	protected void AccountDetailThread(final String type) {
		PublicMethod.myOutput("-------++++====------");
		showDialog(DIALOG1_KEY);
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		Thread t = new Thread(new Runnable() {
			public void run() {
				String error_code = "00";
					try {
						// ��ȡ��ǰ���� �Լ�ǰһ��������
						Calendar now = Calendar.getInstance();
						String monthNow;
						String dayNow;
						String startTime = "";
						String endTime = "";
						String monthPre;
						int monthpre = now.get(Calendar.MONTH);
						int month = now.get(Calendar.MONTH) + 1;
						int day = now.get(Calendar.DAY_OF_MONTH);
						if (month < 10) {
							monthNow = "0" + month;
						} else {
							monthNow = "" + month;
						}
						if (day < 10) {
							dayNow = "0" + day;
						} else {
							dayNow = "" + day;
						}
						if (monthpre < 10) {
							monthPre = "0" + monthpre;
						} else {
							monthPre = "" + monthpre;
						}
						if (month == 1) {
							startTime = (now.get(Calendar.YEAR) - 1) + "" + 12
									+ dayNow;
						} else {
							startTime = now.get(Calendar.YEAR) + "" + monthPre
									+ dayNow;
						}
						endTime = now.get(Calendar.YEAR) + monthNow + dayNow;
						String re = GetAccountDetail.accountDetailSelect(
								phonenum, startTime, endTime, type, sessionid);
						try {
							JSONObject obj = new JSONObject(re);
							error_code = obj.getString("error_code");
						} catch (Exception e) {
							jsonArray = new JSONArray(re);
							JSONObject obj = jsonArray.getJSONObject(0);
							error_code = obj.getString("error_code");
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				if (error_code.equals("0000")) {
					Message msg = new Message();
					msg.what = 14;
					handler.sendMessage(msg);

				} else if (error_code.equals("070002")) {
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);

				} else if (error_code.equals("4444")) {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);

				} else if (error_code.equals("0047")) {
					Message msg = new Message();
					msg.what = 15;
					handler.sendMessage(msg);
				} else if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 9;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 10;
					handler.sendMessage(msg);
				}
			}
		});
		t.start();

	}

	protected String[] getBetcode(String betcode, String play_name) {
		String lotNo = "";
		String betCode = "";
		String beishu = "";
		int wayCode = 0;
		if (play_name.equals("B001") || play_name.equals("F47104")
				|| play_name.equals("QL730") || play_name.equals("F47102")
				|| play_name.equals("D3") || play_name.equals("F47103")) {
			wayCode = Integer.parseInt(betcode.substring(0, 2));

			PublicMethod.myOutput("--------------wayCode?????" + wayCode);

			beishu = betcode.substring(2, 4);
		}

		if (play_name.equals("B001") || play_name.equals("F47104")) {
			if (wayCode == 00) {
				//xiugai
				lotNo = "˫ɫ��ʽ";
				String mp[] = GT.splitBetCode(betcode);
				betCode = "";
				for (int i = 0; i < mp.length; i++) {

					betCode += (GT.makeString("F47104", wayCode, mp[i]
							.substring(4)) + "\n");
				}
			} else if (wayCode == 40 || wayCode == 50) {
				lotNo = "˫ɫ���ϸ�ʽ";
				int index1 = 0;
				for (int i = 0; i < betcode.length(); i++) {
					if (betcode.charAt(i) == '*') {
						index1 = i;
						PublicMethod.myOutput("--------------index1" + index1);
					}
				}
				int index2 = 0;// ���ҡ�~��
				for (int i = 0; i < betcode.length(); i++) {
					if (betcode.charAt(i) == '~') {
						index2 = i;
						PublicMethod.myOutput("--------------index2" + index2);
					}
				}
				// String tmp = betcode.substring(0, index1)+"*";
				// PublicMethod.myOutput("----------------tmp"+tmp);
				String danma = GT.makeString("F47104", wayCode, betcode
						.substring(4, index1));
				String tuoma = GT.makeString("F47104", wayCode, betcode
						.substring(index1 + 1, index2));
				String lanqiu = GT.makeString("F47104", wayCode, betcode
						.substring(index2 + 1, betcode.length() - 1));
				betCode = "������: " + danma + "\n" + "��������: " + tuoma + "\n"
						+ "����" + lanqiu + "\n";

			} else {
				lotNo = "˫ɫ�������ʽ";
				int index1 = 0;// ���ҡ�*��
				for (int i = 0; i < betcode.length(); i++) {
					if (betcode.charAt(i) == '~') {
						index1 = i;
						PublicMethod.myOutput("--------------index1" + index1);
					}
				}

				String redball = GT.makeString("F47104", wayCode, betcode
						.substring(5, index1));
				String blueball = GT.makeString("F47104", wayCode, betcode
						.substring(index1 + 1, betcode.length() - 1));

				betCode = "����: " + redball + "\n" + "����: " + blueball + "\n";
			}
			PublicMethod.myOutput("---------------@@@@@" + betcode);
		} else if (play_name.equals("D3") || play_name.equals("F47103")) {
			if (wayCode == 54) {
				lotNo = "����3D����";
				int index1 = 0;
				for (int i = 0; i < betcode.length(); i++) {
					if (betcode.charAt(i) == '*') {
						index1 = i;
						PublicMethod.myOutput("--------------index1" + index1);
					}
				}
				String danma = GT.makeString("F47103", wayCode, betcode
						.substring(4, index1));
				String tuoma = GT.makeString("F47103", wayCode, betcode
						.substring(index1 + 1, betcode.length() - 1));
				betCode = "����: " + danma + "\n" + "����: " + tuoma + "\n";
				// ��ѡ��ʽ����� 2010/7/5 �³�
			} else if (wayCode == 00) {
				//xiugai
				// 3D��ѡע���ʽ ���� �³� 20100714
				// if(wayCode==00){
				PublicMethod.myOutput("------intointointo--");
				PublicMethod.myOutput("------betcode0000------" + betcode);
				lotNo = "����3Dֱѡ";
				String mp[] = GT.splitBetCode(betcode);
				PublicMethod.myOutput("-----mp.length--------" + mp.length);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {
					PublicMethod.myOutput("--------????????????mp[]------"
							+ mp[i]);
					betCode += (GT.makeString("F47103", wayCode, mp[i]
							.substring(4)) + "\n");
				}

				// }

			} else if (wayCode == 20) {
				// if(wayCode==00){
				// play_name="����3Dֱѡ��ʽ";
				// }
				// if(wayCode==20){
				lotNo = "����3Dֱѡ";
				// }
				int index1 = 0;
				int index2 = 0;
				for (int i = 0; i < betcode.length(); i++) {
					if (betcode.charAt(i) == '^') {
						index1 = i;
						i = betcode.length();
						PublicMethod.myOutput("--------------index====="
								+ index1);
					}
				}
				for (int j = index1 + 1; j < betcode.length(); j++) {
					if (betcode.charAt(j) == '^') {
						index2 = j;
						j = betcode.length();
						PublicMethod.myOutput("--------------index1" + index1);
					}
				}
				String baiwei = GT.makeString("F47103", wayCode, betcode
						.substring(6, index1 + 1));
				String shiwei = GT.makeString("F47103", wayCode, betcode
						.substring(index1 + 3, index2));
				String gewei = GT.makeString("F47103", wayCode, betcode
						.substring(index2 + 3, betcode.length() - 1));
				betCode = "��λ: " + baiwei + "\n" + "ʮλ: " + shiwei + "\n"
						+ "��λ: " + gewei + "\n";

				// 3Dֱѡ��ʽ ע�����
			}

			else {
				// if(wayCode==00){
				// play_name="����3D��ʽ";
				//						
				// }
				if (wayCode == 01) {
					lotNo = "����3D��3";
				} else if (wayCode == 02||wayCode == 32) {
					lotNo = "����3D��6";
				} else if (wayCode == 10) {
					lotNo = "����3Dֱѡ��ֵ";
				} else if (wayCode == 11) {
					lotNo = "����3D��3��ֵ";
				} else if (wayCode == 12) {
					lotNo = "����3D��6��ֵ";
				} else if (wayCode == 31) {
					lotNo = "����3D��3��ʽ";
				} else if (wayCode == 31) {
					lotNo = "����3D��6��ʽ";
				}
				String mp[] = GT.splitBetCode(betcode);
				betCode = "";
				for (int i = 0; i < mp.length; i++) {
					betCode += (GT.makeString("F47103", wayCode, mp[i].substring(4)) + "\n");
				}
			}
		} else if (play_name.equals("QL730") || play_name.equals("F47102")) {
			if (wayCode == 00) {
				int index_q;
				String mp[] = GT.splitBetCode(betcode);
				lotNo = "���ֲʵ�ʽ";
				betCode = "";
				for (int i = 0; i < mp.length; i++) {
					betCode += (GT.makeString("F47102", wayCode, mp[i].substring(4)) + "\n");
				}
			} else if (wayCode == 10) {
				lotNo = "���ֲʸ�ʽ";
				betCode = GT.makeString("F47102", wayCode, betcode.substring(5,
						betcode.length() - 1))
						+ "\n";
			} else if (wayCode == 20) {
				lotNo = "���ֲʵ���";
				int index1 = 0;
				for (int i = 0; i < betcode.length(); i++) {
					if (betcode.charAt(i) == '*') {
						index1 = i;
						PublicMethod.myOutput("--------------index1" + index1);
					}
				}
				// String tmp = betcode.substring(0, index1)+"*";
				// PublicMethod.myOutput("----------------tmp"+tmp);
				String danma = GT.makeString("F47102", wayCode, betcode
						.substring(4, index1));
				String tuoma = GT.makeString("F47102", wayCode, betcode
						.substring(index1 + 1, betcode.length() - 1));
				betCode = "����: " + danma + "\n" + "����: " + tuoma + "\n";
			}

			// String mp[]= GT.splitBetCode(betcode);

		} else if (play_name.equals("T01001") || play_name.equals("DLT_23529")) {
			// String play_name="";
			String[] checkType = new String[2];
			String[] headArea = new String[2];
			String[] rearArea = new String[2];
			// String betCode = "";
			boolean check01 = false; // ����Ƿ���"-"
			boolean check02 = false; // ����Ƿ���"$"
			int index = 0;
			/*
			 * try{ JSONObject obj = jsonArray.getJSONObject(index); betcode =
			 * obj.getString("betcode"); } catch (JSONException e) {
			 * e.printStackTrace(); }
			 */
			for (int i = 0; i < betcode.length(); i++) {
				if (betcode.charAt(i) == '-' || betcode.charAt(i) == '+') {
					check01 = true;
					index = i;
					i = betcode.length();
				}
			}
			for (int i = 0; i < betcode.length(); i++) {
				if (betcode.charAt(i) == '$') {
					check02 = true;
				}
			}
			checkType[0] = betcode.substring(0, index);
			checkType[1] = betcode.substring(index + 1);

			if (check01) {
				if (check02) {
					int index01 = 0;
					int index02 = 0;
					boolean check03 = false;
					boolean check04 = false;
					lotNo = "��������͸����";

					for (int i = 0; i < checkType[0].length(); i++) {
						if (checkType[0].charAt(i) == '$') {
							index01 = i;
							check03 = true;
						}
					}

					if (check03) {
						if (index01 != 0) {
							headArea[0] = checkType[0].substring(0, index01);
							headArea[1] = checkType[0].substring(index01 + 1);
						} else {
							headArea[0] = " ";
							headArea[1] = checkType[0].substring(1);
						}
					}

					for (int i = 0; i < checkType[1].length(); i++) {
						if (checkType[1].charAt(i) == '$') {
							index02 = i;
							check04 = true;
						}
					}

					if (check04) {
						if (index02 != 0) {
							rearArea[0] = checkType[1].substring(0, index02);
							rearArea[1] = checkType[1].substring(index02 + 1);
						} else {
							rearArea[0] = " ";
							rearArea[1] = checkType[1].substring(1);
						}
					}

					betCode = "ǰ�����룺 " + headArea[0] + "\n" + "ǰ�����룺 "
							+ headArea[1] + "\n" + "�������룺 " + rearArea[0]
							+ "\n" + "�������룺 " + rearArea[1] + "\n";
				} else {
					String[] mp = GT.splitBetCodeTC(betcode);

					int iStr = 0;
					for (int i = 0; i < mp[0].length(); i++) {
						if (mp[0].charAt(i) == '-') {
							iStr = i;
							i = mp[0].length();
						}
					}
					if (mp[0].substring(0, iStr).length() == 14
							&& mp[0].substring(iStr + 1).length() == 5) {
						lotNo = "��������͸��ʽ";
						// betCode = checkType[0] + " | " + checkType[1]+"\n";
						for (int i = 0; i < mp.length; i++) {
							betCode += (GT.makeString("T01001", 0, mp[i]) + "\n");
						}

					} else if (checkType[0].length() != 14|| checkType[1].length() != 5) {
						if(checkType[1].contains("-")){
							lotNo = "��������͸ֱѡ";
							String strs[]=betcode.split(";");
							for(int i=0;i<strs.length;i++){
								String str[]=strs[i].split("-");
								betCode +=str[0]+" | "+str[1]+"\n";		
							}
						}else{
							lotNo = "��������͸��ʽ";
							betCode = checkType[0] + " | " + checkType[1] + "\n";
						}
				
					}
				}
			} else {
				// betcode = betcode01;
				if (betcode.length() == 5) {
					lotNo = "��Ф�ֵ�ʽ";
					betCode = betcode + "\n";
				} else {
					lotNo = "��Ф�ָ�ʽ";
					String strs[]=betcode.split(";");
					for(int i=0;i<strs.length;i++){
						betCode +=strs[i]+"\n";		
					}
				}
			}
		} else if (play_name.equals("T01002") || play_name.equals("PL3_33")) {
			String[] betcodes=betcode.split("\\;");
			for(int m=0;m<betcodes.length;m++){
			   String[] checkType = new String[2];
			/*
			 * try{ JSONObject obj = jsonArray.getJSONObject(index); betcode =
			 * obj.getString("betcode"); } catch (JSONException e) {
			 * e.printStackTrace(); }
			 */
			int index = 0;
			for (int i = 0; i < betcodes[m].length(); i++) {
				if (betcodes[m].charAt(i) == '|') {
					index = i;
					i = betcodes[m].length();
				}
			}
			checkType[0] = betcodes[m].substring(0, index);
			checkType[1] = betcodes[m].substring(index + 1);
			for (int i = 0; i < 2; i++) {
				PublicMethod.myOutput("------checkType[" + i + "]-------"
						+ checkType[i]);
				PublicMethod.myOutput("------checkType[" + i + "]-------"
						+ checkType[i].length());
			}

			if (checkType[0].equalsIgnoreCase("1")) {
				String[] mp = GT.splitBetCodeTC(betcodes[m]);

				if (mp[0].length() == 7) {
					lotNo = "������ֱѡ��ʽ";
					for (int i = 0; i < mp.length; i++) {
						betCode += (GT.makeString("T01002", 0, mp[i]) + "\n");
					}
					// String subStr = checkType[1];
					// String[] subStrSplit = new String[3];
					// for(int i=0 ;i<3;i++){
					// subStrSplit[i] = subStr.substring(2*i, 2*i+1);
					// }
					// betCode = "��λ: " + subStrSplit[0] + "\n" + "ʮλ: " +
					// subStrSplit[1] + "\n" + "��λ: " + subStrSplit[2]+"\n";
				} else if (checkType[1].length() > 5) {
					lotNo = "������ֱѡ��ʽ";
					String subStr = checkType[1]; // �ָ��ĺ���
					String[] subStrSplit = subStr.split(",", 3); // ����λ��ʮλ����λ�ֿ�
					for (int i = 0; i < 3; i++) {
						PublicMethod.myOutput("------subStrSplit[" + i
								+ "]-------" + subStrSplit[i]);
					}
					String[] subStrSplitLast = new String[3];

					for (int i = 0; i < 3; i++) {
						String str03 = "";
						String[] str02 = new String[subStrSplit[i].length()];
						String str01 = subStrSplit[i];
						PublicMethod.myOutput("-----str01--" + i + "----"
								+ str01);
						PublicMethod.myOutput("-----str01---" + i + "---"
								+ str01.length());
						for (int j = 0; j < str01.length(); j++) {
							str02[j] = str01.substring(j, j + 1);
							str03 += str02[j] + " ";
						}
						subStrSplitLast[i] = str03;
					}

					betCode = "��λ�� " + subStrSplitLast[0] + "\n" + "ʮλ�� "
							+ subStrSplitLast[1] + "\n" + "��λ�� "
							+ subStrSplitLast[2] + "\n";
				}
			} else if (checkType[0].equalsIgnoreCase("6")) {
				String subStr =null;
				subStr = checkType[1];
				int[] subStrLast = new int[3];
				for (int i = 0; i < 3; i++) {
					subStrLast[i] = Integer.valueOf(subStr.substring(2 * i,
							2 * i + 1));
				}
				if (subStrLast[0] == subStrLast[1]
						|| subStrLast[1] == subStrLast[2]) {
					lotNo = "����������";
					betCode += subStr+"\n" ;
				} else {
					lotNo = "����������";
					betCode += subStr+"\n" ;
				}
	
			} else {
				String[] gameType = { "S1", "S9", "S3", "S6" };
				String[] gameType01 = { "F3", "F6" };
				String[] gameTitle = { "������ֱѡ��ֵ", "��������ѡ��ֵ", "������������ֵ",
						"������������ֵ" };
				String[] gameTitle01 = { "��������������", "��������������" };
				for (int i = 0; i < 4; i++) {
					if (checkType[0].equalsIgnoreCase(gameType[i])) {
						String subStr = checkType[1];
						lotNo = gameTitle[i];
						betCode = subStr + "\n";
					}
				}
				for (int i = 0; i < 2; i++) {
					if (checkType[0].equalsIgnoreCase(gameType01[i])) {
						String subStr = checkType[1];
						String[] subStrLast = new String[subStr.length()];
						String finalStr = "";
						lotNo = gameTitle01[i];
						for (int j = 0; j < subStr.length(); j++) {
							subStrLast[j] = subStr.substring(j, j + 1);
							finalStr += subStrLast[j] + " ";
						}
						betCode = finalStr + "\n";
					}
				}
			  }
			}
		} else if (play_name.equals("T01007") || play_name.equals("SSC_10401")) {
			String betType = "";
			String betCodeView = "";

			String[] bet_codes = betcode.split(";");
			for (String bet_code : bet_codes) {
				if (bet_code.length() > 0 && !bet_code.contains("DD")) {
					betCodeView = betCodeView
							+ bet_code.replace("+", "-").substring(3) + "\n";
				} else {

					char c[] = bet_code.substring(3).toCharArray();

					for (int i = 0; i < c.length; i++) {

						switch (c[i]) {
						case '2':
							betCodeView = betCodeView + "��";

							break;
						case '1':
							betCodeView = betCodeView + "С";
							break;
						case '5':
							betCodeView = betCodeView + "��";
							break;
						case '4':
							betCodeView = betCodeView + "˫";
							break;
						}
					}
					betCodeView += "\n";

				}
			}

			betType = getSSCBetType(betcode);
			betCode = betCodeView;
			lotNo = betType;
		} else if (play_name.equals("T01003")) {// ʤ����
			String betType = "";
			String betCodeView = "";

			String[] bet_codes = betcode.split(";");
			for (String bet_code : bet_codes) {
				if (bet_code.length() > 0) {
					betCodeView = betCodeView + bet_code + "\n";
				}
			}
			betCode = betCodeView;
			lotNo = "ʤ����";
		} else if (play_name.equals("T01004")) {// ��ѡ��
			String betType = "";
			String betCodeView = "";

			String[] bet_codes = betcode.split(";");
			for (String bet_code : bet_codes) {
				if (bet_code.length() > 0) {
					betCodeView = betCodeView + bet_code + "\n";
				}
			}
			betCode = betCodeView;
			lotNo = "��ѡ��";
		} else if (play_name.equals("T01005")) {// �����
			String betType = "";
			String betCodeView = "";

			String[] bet_codes = betcode.split(";");
			for (String bet_code : bet_codes) {
				if (bet_code.length() > 0) {
					betCodeView = betCodeView + bet_code + "\n";
				}
			}

			betCode = betCodeView;
			lotNo = "�����";
		} else if (play_name.equals("T01006")) {// ������
			String betType = "";
			String betCodeView = "";

			String[] bet_codes = betcode.split(";");
			for (String bet_code : bet_codes) {
				if (bet_code.length() > 0) {
					betCodeView = betCodeView + bet_code + "\n";
				}
			}
			betCode = betCodeView;
			lotNo = "������";
		}else if(play_name.equals("T01009")){
			lotNo = "���ǲ�";
		}else if(play_name.equals("T01008")){
			lotNo = "��������";
		}else if(play_name.equals("T01010")){
			lotNo = "11ѡ5";
		}else if(play_name.equals("T01011")){
			lotNo = "������";
		}
		String[] str = { lotNo, betCode, beishu };
		return str;
	}

	/**
	 * ���ʱʱ�ʵ�Ͷע��ʽ
	 * 
	 * @param betCode
	 * @return
	 */
	public static String getSSCBetType(String betCode) {
		String betType = "";
		if (betCode.contains("1D")) {
			betType = "һ��";
		} else if (betCode.contains("2D")) {
			betType = "����";
		} else if (betCode.contains("3D")) {
			betType = "����";
		} else if (betCode.contains("5D")) {
			betType = "����";
		} else if (betCode.contains("5F")) {
			betType = "���Ǹ�ѡ";
		} else if (betCode.contains("5T")) {
			betType = "����ͨѡ";
		} else if (betCode.contains("3F")) {
			betType = "���Ǹ�ѡ";
		} else if (betCode.contains("2F")) {
			betType = "���Ǹ�ѡ";
		} else if (betCode.contains("H2")) {
			betType = "���Ǻ�ֵ";
		} else if (betCode.contains("S2")) {
			betType = "���ǰ���";
		} else if (betCode.contains("DD")) {
			betType = "��С��˫";
		} else if (betCode.contains("Z2")) {
			betType = "������ѡ";
		} else if (betCode.contains("F2")) {
			betType = "������ѡ";
		}
		return betType;
	}

	public void showalert(final String string, final int page, final View v) {
		Builder dialog = new AlertDialog.Builder(this).setTitle("��ʾ")
				.setMessage("�Ƿ�Ҫȡ��׷��").setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								CancelTranking(string, page, v);
							}

						}).setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								// ����˳���Ӧ�¼� 20100715 �³�

							}

						});
		dialog.show();
	}

	// ���⴫�飬�����ײͣ�����ѡ��

	/**
	 * ���⴫��������
	 */
	protected void showRuYiChuanQing() {
		setContentView(R.layout.ruyichuanqing_layout_main);
		// ������7.3�����޸ģ���Button����ImageButton
		ImageView btn = (ImageView) findViewById(R.id.ruyichuanqing_return);
		btn.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				showListView(ID_MORELISTVIEW);
			}

		});
		ruyichuanqing_sub_view = (LinearLayout) findViewById(R.id.ruyichuanqing_layout_sub);

		ruyichuanqing_ssq = (TextView) findViewById(R.id.tv_ruyichuanqing_ssq);
		ruyichuanqing_fc3d = (TextView) findViewById(R.id.tv_ruyichuanqing_fc3d);
		ruyichuanqing_qlc = (TextView) findViewById(R.id.tv_ruyichuanqing_qlc);
		// wangyl 8.25 ��������͸��������
		ruyichuanqing_dlt = (TextView) findViewById(R.id.tv_ruyichuanqing_dlt);
		ruyichuanqing_pl3 = (TextView) findViewById(R.id.tv_ruyichuanqing_pl3);

		textViews[0] = ruyichuanqing_ssq;
		textViews[1] = ruyichuanqing_fc3d;
		textViews[2] = ruyichuanqing_qlc;
		textViews[3] = ruyichuanqing_dlt;
		textViews[4] = ruyichuanqing_pl3;
		ruyichuanqing_ssq.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] ints = { 1, 0, 0, 0, 0 };
				setRuyichuanqingTabs(textViews, ints);
				setRuyichuanqingViews("ssq");
			}

		});

		ruyichuanqing_fc3d.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] ints = { 0, 1, 0, 0, 0 };
				setRuyichuanqingTabs(textViews, ints);
				setRuyichuanqingViews("fc3d");
			}

		});

		ruyichuanqing_qlc.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] ints = { 0, 0, 1, 0, 0 };
				setRuyichuanqingTabs(textViews, ints);
				setRuyichuanqingViews("qlc");
			}

		});
		ruyichuanqing_dlt.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] ints = { 0, 0, 0, 1, 0 };
				setRuyichuanqingTabs(textViews, ints);
				setRuyichuanqingViews("dlt");
			}

		});
		ruyichuanqing_pl3.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] ints = { 0, 0, 0, 0, 1 };
				setRuyichuanqingTabs(textViews, ints);
				setRuyichuanqingViews("pl3");
			}

		});
		int[] ints = { 1, 0, 0, 0, 0 };
		setRuyichuanqingTabs(textViews, ints);
		setRuyichuanqingViews("ssq");

	}

	// wangyl 8.11 �����Ż� ���⴫���ǩ
	/**
	 * ����״̬����TextView�ı���ͼƬ
	 * 
	 * @param textviews
	 *            ��ǩ��
	 * @param ints
	 *            ��ǩ���״̬��1Ϊ�������0Ϊδ���
	 */
	protected void setRuyichuanqingTabs(TextView[] textviews, int[] ints) {
		for (int i = 0; i < ints.length; i++) {
			if (ints[i] == 1) {
				textviews[i].setBackgroundDrawable(getResources().getDrawable(
						R.drawable.frame_rectangle_user));
			} else if (ints[i] == 0) {
				textviews[i].setBackgroundDrawable(getResources().getDrawable(
						R.drawable.frame_rectangle_user_d));
			}
		}
	}

	// wangyl 8.11 �����Ż� ���⴫���ǩ
	/**
	 * ��ʾ�������ֵ�view
	 * 
	 * @param lotteryType
	 *            ����
	 */
	protected void setRuyichuanqingViews(String lotteryType) {
		ruyichuanqing_sub_view.removeAllViews();
		RuyiExpressFeelView ruyiExpressFeelView = new RuyiExpressFeelView(this,
				lotteryType);
		View view = ruyiExpressFeelView.getView();
		ruyichuanqing_sub_view.addView(view);
	}

	/**
	 * �����ײ��и������ֵĶ���״̬
	 */
	protected void setFlag() {
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		phonenum = shellRW.getUserLoginInfo("phonenum");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		// �ȸ�ֵΪ��
		ssqamount = "";
		fc3damount = "";
		qlcamount = "";
		// wangyl 8.27 add pl3 and dlt
		pl3amount = "";
		dltamount = "";
		showDialog(DIALOG1_KEY);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
//				while (iretrytimes < 3 && iretrytimes > 0) {
					try {
						try {
							re = PackageInterface.packageSelect(sessionid);
							PublicMethod
									.myOutput("--------phonenum----------showRuyiPackageListView-------------------"
											+ phonenum);
							PublicMethod
									.myOutput("---------sessionid------------showRuyiPackageListView-----------------"
											+ sessionid);
							PublicMethod.myOutput("-------re:" + re);

							obj = new JSONObject(re);

							error_code = obj.getString("error_code");
							// String state4 = obj.getString("state");//String
							// PublicMethod.myOutput("-------state------"+state);
							// PublicMethod.myOutput("-------error_code------"+error_code);

							if (error_code.equals("000000")) {
								tsubscribeId = obj.getString("tsubscribeId");
								String state4 = obj.getString("state");// String
								String lotterytype4 = obj.getString("lotNo");
								String amount_str = obj.getString("amount");
								int amount = Integer.parseInt(amount_str) / 100;
								PublicMethod.myOutput("-------state------"
										+ state);
								PublicMethod.myOutput("-------error_code------"
										+ error_code);
								PublicMethod
										.myOutput("-------lotterytype------"
												+ lotterytype);

								// ˫ɫ��
								if (state4.equals("����")
										&& lotterytype4.equals("B001")) {
									// ssqFlag = true;
									subscribeFlag[0] = false;
									ssqamount = amount + "";
									// ��һ�ε�¼ʱ��ס�ײ��Ƕ���Ԫ��
									// ShellRWSharesPreferences shellRW =new
									// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
									// shellRW.setUserLoginInfo("textssq",
									// "��ѡÿ��"+ssqamount+"Ԫ�ײ�");
								} else {
									// ssqFlag = false;
									subscribeFlag[0] = true;
								}
								// ����3D
								if (state4.equals("����")
										&& lotterytype4.equals("D3")) {
									// fc3dFlag = true;
									subscribeFlag[1] = false;
									fc3damount = amount + "";
									// ��һ�ε�¼ʱ��ס�ײ��Ƕ���Ԫ��
									// ShellRWSharesPreferences shellRW =new
									// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
									// shellRW.setUserLoginInfo("textfc3d",
									// "��ѡÿ��"+fc3damount+"Ԫ�ײ�");
								} else {
									// fc3dFlag = false;
									subscribeFlag[1] = true;
								}

								// ���ֲ�
								if (state4.equals("����")
										&& lotterytype4.equals("QL730")) {
									// qlcFlag = true;
									subscribeFlag[2] = false;
									qlcamount = amount + "";
									// ��һ�ε�¼ʱ��ס�ײ��Ƕ���Ԫ��
									// ShellRWSharesPreferences shellRW =new
									// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
									// shellRW.setUserLoginInfo("textqlc",
									// "��ѡÿ��"+qlcamount+"Ԫ�ײ�");
								} else {
									// qlcFlag = false;
									subscribeFlag[2] = true;
								}
								// wangyl 8.27 add pl3 and dlt
								// ������
								if (state4.equals("����")
										&& lotterytype4.equals("PL3_33")) {
									subscribeFlag[3] = false;
									pl3amount = amount + "";
								} else {
									subscribeFlag[3] = true;
								}
								// ����͸
								if (state4.equals("����")
										&& lotterytype4.equals("DLT_23529")) {
									subscribeFlag[4] = false;
									dltamount = amount + "";
								} else {
									subscribeFlag[4] = true;
								}

								Message msg = new Message();
								msg.what = 0;
								handler1.sendMessage(msg);
							} else if (error_code.equals("060004")) {
								Message msg = new Message();
								msg.what = 0;
								handler1.sendMessage(msg);
							}
							// else if(error_code.equals("070002")){
							// Message msg=new Message();
							// msg.what=7;
							// handler.sendMessage(msg);
							// // �޷���ȡ�û���Ϣ
							// }
				//			iretrytimes = 3;
						} catch (Exception e) {
							jsonObject3 = new JSONArray(re);

							PublicMethod
									.myOutput("-------------jsonObject3.length();--------------"
											+ jsonObject3.length());
							for (int i = 0; i < jsonObject3.length(); i++) {

								PublicMethod
										.myOutput("---------jsonObject---------"
												+ i);
								obj = jsonObject3.getJSONObject(i);
								// String state1 =
								// obj.getString("state");//String
								// // Toast.makeText(getBaseContext(), state,
								// Toast.LENGTH_SHORT);
								// String lotterytype1 = obj.getString("lotNo");
								// String amount_str = obj.getString("amount");
								// int amount =
								// Integer.parseInt(amount_str)/100;
								// PublicMethod.myOutput("-------lotterytype1------"+i+"------------"+lotterytype1);
								// PublicMethod.myOutput("-------state1------"+i+"------------"+state1);
								error_code = obj.getString("error_code");
								if (("000000").equals(error_code)) {

									// ��������ƶ���if�� 2010/7/10 �³� ֻ�гɹ�ʱ���ܻ�ȡ�����������

									String state1 = obj.getString("state");// String
									// Toast.makeText(getBaseContext(), state,
									// Toast.LENGTH_SHORT);
									String lotterytype1 = obj
											.getString("lotNo");
									// String amount_str =
									// obj.getString("amount");
									// int amount =
									// Integer.parseInt(amount_str)/100;
									PublicMethod
											.myOutput("-------lotterytype1------"
													+ i
													+ "------------"
													+ lotterytype1);
									PublicMethod.myOutput("-------state1------"
											+ i + "------------" + state1);
									// tsubscribeIdssq=obj.getString("tsubscribeId");
									// ˫ɫ�� ��������ƶ���if�� 2010/7/10 �³�

									if (state1.equals("����")
											&& lotterytype1.equals("B001")) {
										// ssqFlag = true;
										// ������ʱ��Ǯ������ˮ��д�� 2010/7/10 �³�
										tsubscribeIdssq = obj
												.getString("tsubscribeId");
										String amount_str = obj
												.getString("amount");
										int amount = Integer
												.parseInt(amount_str) / 100;
										subscribeFlag[0] = false;
										ssqamount = amount + "";
										// ShellRWSharesPreferences shellRW =new
										// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
										// shellRW.setUserLoginInfo("textssq",
										// "��ѡÿ��"+ssqamount+"Ԫ�ײ�");
										PublicMethod
												.myOutput("-------subscribeFlag[0]------B001----------"
														+ ssqamount);
										PublicMethod
												.myOutput("-------subscribeFlag[0]------B001----------"
														+ subscribeFlag[0]);
										i = jsonObject3.length();
									} else {
										// ssqFlag = false;
										subscribeFlag[0] = true;
										ssqamount = "";
										PublicMethod
												.myOutput("-------subscribeFlag[0]------B001----------"
														+ subscribeFlag[0]);
									}
									PublicMethod
											.myOutput("-------tsubscribeId------"
													+ i
													+ "------------"
													+ tsubscribeId);

								}

							}
							for (int i = 0; i < jsonObject3.length(); i++) {

								// PublicMethod.myOutput("---------jsonObject---------"+i);
								obj = jsonObject3.getJSONObject(i);

								if (("000000").equals(error_code)) {
									tsubscribeIdfc3d = obj
											.getString("tsubscribeId");
									// ��������ƶ���if�� 2010/7/10 �³�
									String state2 = obj.getString("state");
									String lotterytype2 = obj
											.getString("lotNo");
									PublicMethod
											.myOutput("-------lotterytype2------"
													+ i
													+ "------------"
													+ lotterytype2);
									PublicMethod.myOutput("-------state2------"
											+ i + "------------" + state2);
									PublicMethod
											.myOutput("-------tsubscribeId------"
													+ i
													+ "------------"
													+ tsubscribeId);
									// ����3D ��������ƶ���if�� 2010/7/10 �³�
									if (state2.equals("����")
											&& lotterytype2.equals("D3")) {
										// fc3dFlag = true;
										subscribeFlag[1] = false;
										// ������ʱ��Ǯ������ˮ��д�� 2010/7/10 �³�
										String amount_str = obj
												.getString("amount");
										int amount = Integer
												.parseInt(amount_str) / 100;
										fc3damount = amount + "";
										tsubscribeIdfc3d = obj
												.getString("tsubscribeId");
										PublicMethod
												.myOutput("-------fc3damount---------D3-------"
														+ fc3damount);
										PublicMethod
												.myOutput("-------subscribeFlag[1]---------D3-------"
														+ subscribeFlag[1]);
										i = jsonObject3.length();
									} else {
										// fc3dFlag = false;
										subscribeFlag[1] = true;
										fc3damount = "";
										PublicMethod
												.myOutput("-------subscribeFlag[1]---------D3-------"
														+ subscribeFlag[1]);
									}
								}

							}
							for (int i = 0; i < jsonObject3.length(); i++) {

								// PublicMethod.myOutput("---------jsonObject---------"+i);
								obj = jsonObject3.getJSONObject(i);

								if (("000000").equals(error_code)) {
									// tsubscribeIdqlc=obj.getString("tsubscribeId");

									// ��������ƶ���if�� 2010/7/10 �³�
									String state3 = obj.getString("state");
									String lotterytype3 = obj
											.getString("lotNo");

									PublicMethod
											.myOutput("-------lotterytype3------"
													+ i
													+ "------------"
													+ lotterytype3);
									PublicMethod.myOutput("-------state3------"
											+ i + "------------" + state3);

									// ���ֲ� ��������ƶ���if�� 2010/7/10 �³�
									if (state3.equals("����")
											&& lotterytype3.equals("QL730")) {
										// qlcFlag = true;
										// ������ʱ��Ǯ������ˮ��д��
										tsubscribeIdqlc = obj
												.getString("tsubscribeId");
										subscribeFlag[2] = false;
										String amount_str = obj
												.getString("amount");
										int amount = Integer
												.parseInt(amount_str) / 100;
										qlcamount = amount + "";
										// ShellRWSharesPreferences shellRW =new
										// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
										// shellRW.setUserLoginInfo("textqlc",
										// "��ѡÿ��"+qlcamount+"Ԫ�ײ�");
										PublicMethod
												.myOutput("-------subscribeFlag[2]-------qlcamount-----------"
														+ qlcamount);
										// PublicMethod.myOutput("-------subscribeFlag[2]--------shellRW.getUserLoginInfo-----------"+shellRW.getUserLoginInfo("textqlc"));
										PublicMethod
												.myOutput("-------subscribeFlag[2]--------QL730-----------"
														+ subscribeFlag[2]);
										i = jsonObject3.length();
									} else {
										// qlcFlag = false;
										subscribeFlag[2] = true;
										qlcamount = "";
										PublicMethod
												.myOutput("-------subscribeFlag[2]--------QL730-----------"
														+ subscribeFlag[2]);
									}
									// Message msg = new Message();
									// msg.what=0;
									// handler.sendMessage(msg);
									PublicMethod
											.myOutput("-------tsubscribeId------"
													+ i
													+ "------------"
													+ tsubscribeId);
								}

							}
							// wangyl 8.27 add pl3 and dlt
							for (int i = 0; i < jsonObject3.length(); i++) {

								obj = jsonObject3.getJSONObject(i);

								if (("000000").equals(error_code)) {
									String state4 = obj.getString("state");
									String lotterytype4 = obj
											.getString("lotNo");

									if (state4.equals("����")
											&& lotterytype4.equals("PL3_33")) {
										// ������ʱ��Ǯ������ˮ��д��
										tsubscribeIdpl3 = obj
												.getString("tsubscribeId");
										subscribeFlag[3] = false;
										String amount_str = obj
												.getString("amount");
										int amount = Integer
												.parseInt(amount_str) / 100;
										pl3amount = amount + "";
										i = jsonObject3.length();
									} else {
										subscribeFlag[3] = true;
										pl3amount = "";
									}
								}

							}

							for (int i = 0; i < jsonObject3.length(); i++) {

								obj = jsonObject3.getJSONObject(i);

								if (("000000").equals(error_code)) {
									String state5 = obj.getString("state");
									String lotterytype5 = obj
											.getString("lotNo");

									if (state5.equals("����")
											&& lotterytype5.equals("DLT_23529")) {
										// ������ʱ��Ǯ������ˮ��д��
										tsubscribeIddlt = obj
												.getString("tsubscribeId");
										subscribeFlag[4] = false;
										String amount_str = obj
												.getString("amount");
										int amount = Integer
												.parseInt(amount_str) / 100;
										dltamount = amount + "";
										i = jsonObject3.length();
									} else {
										subscribeFlag[4] = true;
										dltamount = "";
									}
								}

							}
							// handel�ɹ�
							Message msg = new Message();
							msg.what = 0;
							handler1.sendMessage(msg);
						}
			//			iretrytimes = 3;
					} catch (JSONException e) {
						e.printStackTrace();
			//			iretrytimes--;
						error_code = "00";
					}
//				}
				if (error_code.equalsIgnoreCase("00")) {
					Message msg = new Message();
					msg.what = 8;
					handler1.sendMessage(msg);
				}
			}

		});
		t.start();
	}

	// �����ײ��б�
	protected void showRuyiPackageListView() {
		// setFlag(); // ��ȡ����״̬
		setContentView(R.layout.ruyipackage_listview);

		// ����
		// ������7.3�����޸ģ���Button����ImageButton
		ImageView imageview = (ImageView) findViewById(R.id.ruyipackage_btn_return);
		imageview.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
				showListView(ID_MORELISTVIEW);
			}

		});

		PublicMethod.myOutput("---------setRuyiPackageListView--------");

		textview = (TextView) findViewById(R.id.ruyipackage_text);

		ListView listview = (ListView) findViewById(R.id.ruyipackage_listview_id);
		for (int ii = 0; ii < 3; ii++) {
			PublicMethod.myOutput("-----ff--" + subscribeFlag[ii]);
		}
		// ������
		ruyiPackageAdapter = new RuyiPackageEfficientAdapter(this);
		listview.setAdapter(ruyiPackageAdapter);
		// PublicMethod.setmydividerHeight(listview);
		listview.setDividerHeight(0);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			}
		});

		listview.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

	}

	protected void showRuyiPackageUnSubscribe(int aViewId/* ,TextView textView */) {

		// boolean isatus = states[(view.getId() - 100) / 3][(view.getId() -
		// 100) % 3];
		boolean isatus = states[(aViewId - 100) / 3][(aViewId - 100) % 3];
		PublicMethod.myOutput("------" + isatus);
		if (!isatus) {
			return;
		}
		viewid = aViewId;
		// LayoutInflater mInflater = LayoutInflater.from(this);
		// View convertView =
		// mInflater.inflate(R.layout.ruyipackage_listview_item, null);
		// textview =textView;

		// ��ʱ���涩�����ײ����࣬�����´ε�¼��ʱ����ʾ����
		ShellRWSharesPreferences shellRWtext = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		textssq = shellRWtext.getUserLoginInfo("ssqtext");
		textfc3d = shellRWtext.getUserLoginInfo("fc3dtext");
		textqlc = shellRWtext.getUserLoginInfo("qlctext");
		// wangyl 8.27 add pl3 and dlt
		textpl3 = shellRWtext.getUserLoginInfo("pl3text");
		textdlt = shellRWtext.getUserLoginInfo("dlttext");

		if (aViewId == 102) {
			packageName = chooseLuckLotteryNum_zhonglei[0];// ˫ɫ��
			// textview.setText(textssq);
			lottery_type = "B001";
			holderText[0] = textssq;
			/*
			 * if(textssq.equals("unsubscribe")){ holderText[0]=""; }else{
			 * holderText[0] = textssq; }
			 */

			text_str = "��ѡÿ��" + holderText[0] + "Ԫ�ײ�";
			// text_str=ssqamount;
			tsubscribeId = tsubscribeIdssq;
		}
		if (aViewId == 105) {
			packageName = chooseLuckLotteryNum_zhonglei[1];// ����3D
			// textview.setText(textfc3d);
			lottery_type = "D3";
			holderText[1] = textfc3d;

			/*
			 * if(textfc3d.equals("unsubscribe")){ holderText[1]=""; }else{
			 * holderText[1] = textfc3d; }
			 */

			text_str = "��ѡÿ��" + holderText[1] + "Ԫ�ײ�";
			// text_str=fc3damount;
			tsubscribeId = tsubscribeIdfc3d;
		}
		if (aViewId == 108) {
			packageName = chooseLuckLotteryNum_zhonglei[2];// ���ֲ�
			// textview.setText(textqlc);
			lottery_type = "QL730";
			holderText[2] = textqlc;
			/*
			 * if(textqlc.equals("unsubscribe")){ holderText[2]=""; }else{
			 * holderText[2] = textqlc; }
			 */
			text_str = "��ѡÿ��" + holderText[2] + "Ԫ�ײ�";
			// text_str=qlcamount;
			tsubscribeId = tsubscribeIdqlc;
		}
		// wangyl 8.27 add pl3 and dlt
		if (aViewId == 111) {
			packageName = chooseLuckLotteryNum_zhonglei[3];// ������
			lottery_type = "PL3_33";
			holderText[3] = textpl3;
			text_str = "��ѡÿ��" + holderText[3] + "Ԫ�ײ�";
			tsubscribeId = tsubscribeIdpl3;
		}
		if (aViewId == 114) {
			packageName = chooseLuckLotteryNum_zhonglei[4];// ����͸
			lottery_type = "DLT_23529";
			holderText[4] = textdlt;
			text_str = "��ѡÿ��" + holderText[4] + "Ԫ�ײ�";
			tsubscribeId = tsubscribeIddlt;
		}

		// ������ 7.5 �����޸ģ���ӵ�½�ж�
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		if (sessionid == null || sessionid.equals("")) {
			Intent intentSession = new Intent(RuyiHelper.this, UserLogin.class);
			startActivity(intentSession);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					RuyiHelper.this);
			builder.setTitle(R.string.ruyipackage_unsubscribe);
			builder.setMessage(getString(R.string.ruyipackage_unsubscirbe_sure)
					+ "\n" + packageName + text_str);

			// ȷ��
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							showDialog(DIALOG1_KEY);
							Thread t = new Thread(new Runnable() {

								@Override
								public void run() {
					//				while (iretrytimes < 3 && iretrytimes > 0) {

										PublicMethod
												.myOutput("-------------tsubscribeId------------------"
														+ tsubscribeId);
										PublicMethod
												.myOutput("-------------sessionid------------------"
														+ sessionid);

										re = PackageInterface.packageFreeze(
												sessionid, tsubscribeId);// login_User,

										try {
											obj = new JSONObject(re);
											error_code = obj
													.getString("error_code");
											PublicMethod
													.myOutput("---------unsub-----------"
															+ error_code);
									//		iretrytimes = 3;
										} catch (JSONException e) {
											e.printStackTrace();
									//		iretrytimes--;

										}
						//			}
								//	iretrytimes = 2;
									if (error_code.equals("000000")) {

										ShellRWSharesPreferences shellRWtextunsub = new ShellRWSharesPreferences(
												RuyiHelper.this, "addInfo");
										// ˫ɫ��
										if (getString(R.string.shuangseqiu)
												.equals(packageName)) {
											shellRWtextunsub.setUserLoginInfo(
													"ssqtext", "");
										}
										// ����3D
										if (getString(R.string.fucai3d).equals(
												packageName)) {
											shellRWtextunsub.setUserLoginInfo(
													"fc3dtext", "");
										}
										// ���ֲ�
										if (getString(R.string.qilecai).equals(
												packageName)) {
											shellRWtextunsub.setUserLoginInfo(
													"qlctext", "");
										}

										// wangyl 8.27 add pl3 and dlt
										// ������
										if (getString(R.string.pailie3).equals(
												packageName)) {
											shellRWtextunsub.setUserLoginInfo(
													"pl3text", "");
										}
										// ����͸
										if (getString(R.string.daletou).equals(
												packageName)) {
											shellRWtextunsub.setUserLoginInfo(
													"dlttext", "");
										}

										// showRuyiPackageOperaciton(title,textview,subBtn,editBtn,unsubBtn,Flag);
										// true δ���� false ����
										if (viewid == 102) {
											subscribeFlag[0] = true;
											holderText[0] = "";
										}
										if (viewid == 105) {
											subscribeFlag[1] = true;
											holderText[1] = "";
										}
										if (viewid == 108) {
											subscribeFlag[2] = true;
											holderText[2] = "";
										}
										// wangyl 8.27 add pl3 and dlt
										if (viewid == 111) {
											subscribeFlag[3] = true;
											holderText[3] = "";
										}
										if (viewid == 114) {
											subscribeFlag[4] = true;
											holderText[4] = "";
										}

										PublicMethod
												.myOutput("----unsub-----subscribeFlag[0]----------"
														+ subscribeFlag[0]);
										PublicMethod
												.myOutput("-----unsub----subscribeFlag[1]----------"
														+ subscribeFlag[1]);
										PublicMethod
												.myOutput("-----unsub----subscribeFlag[2]----------"
														+ subscribeFlag[2]);

										Message msg = new Message();
										msg.what = 0;
										handler1.sendMessage(msg);

									} else if (error_code.equals("070002")) {
										Message msg = new Message();
										msg.what = 7;
										handler1.sendMessage(msg);
									} else if (error_code.equals("060004")) {
										Message msg = new Message();
										msg.what = 13;
										handler1.sendMessage(msg);
									} else if (error_code.equals("00")) {
										Message msg = new Message();
										msg.what = 8;
										handler1.sendMessage(msg);
									} else {
										Message msg = new Message();
										msg.what = 14;
										handler1.sendMessage(msg);
									}
								}

							});
							t.start();

						}

					});
			// ȡ��
			builder.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

						}

					});
			builder.show();
		}

	}

	/**
	 * �����ײ����޸İ�ť��ִ�в���
	 * 
	 * @param view
	 *            ������İ�ť
	 */
	// �޸ĸı���� �³� 20100729
	protected void showRuyiPackageEdit(int aViewId/* ,TextView textView */) {
		boolean isatus = states[(aViewId - 100) / 3][(aViewId - 100) % 3];
		PublicMethod.myOutput("------" + isatus);
		if (!isatus) {
			return;
		} else {

			viewid = aViewId;

			// ��ʱ���涩�����ײ����࣬�����´ε�¼��ʱ����ʾ����

			if (aViewId == 101) {
				packageName = chooseLuckLotteryNum_zhonglei[0];// ˫ɫ��
				// textview.setText(textssq);
				lottery_type = "B001";
				tsubscribeId = tsubscribeIdssq;
			}
			if (aViewId == 104) {
				packageName = chooseLuckLotteryNum_zhonglei[1];// ����3D
				// textview.setText(textfc3d);
				lottery_type = "D3";
				tsubscribeId = tsubscribeIdfc3d;
			}
			if (aViewId == 107) {
				packageName = chooseLuckLotteryNum_zhonglei[2];// ���ֲ�
				// textview.setText(textqlc);
				lottery_type = "QL730";
				tsubscribeId = tsubscribeIdqlc;
			}
			// wangyl 8.27 add pl3 and dlt
			if (aViewId == 110) {
				packageName = chooseLuckLotteryNum_zhonglei[3];// ������
				lottery_type = "PL3_33";
				tsubscribeId = tsubscribeIdpl3;

			}
			if (aViewId == 113) {
				packageName = chooseLuckLotteryNum_zhonglei[4];// ����͸
				lottery_type = "DLT_23529";
				tsubscribeId = tsubscribeIddlt;

			}
			// ������ 7.5 �����޸ģ���ӵ�½�ж�
			ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
					RuyiHelper.this, "addInfo");
			sessionid = shellRW.getUserLoginInfo("sessionid");
			if (sessionid == null || sessionid.equals("")) {
				Intent intentSession = new Intent(RuyiHelper.this,
						UserLogin.class);
				startActivity(intentSession);
			} else {
				LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View myView = factory
						.inflate(R.layout.ruyipackage_dialog, null);
				radioGroup = (RadioGroup) myView
						.findViewById(R.id.ruyipackage_radiogroup);
				rb10 = (RadioButton) myView.findViewById(R.id.ruyipackage_10);
				rb8 = (RadioButton) myView.findViewById(R.id.ruyipackage_8);
				rb6 = (RadioButton) myView.findViewById(R.id.ruyipackage_6);
				rb4 = (RadioButton) myView.findViewById(R.id.ruyipackage_4);
				rb2 = (RadioButton) myView.findViewById(R.id.ruyipackage_2);
				dialog_tips = (TextView) myView
						.findViewById(R.id.ruyipackage_tips);
				dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ" + packageName + "10Ԫ�ײ�?");
				radioGroup
						.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(RadioGroup group,
									int checkedId) {
								editchanged = true;
								if (checkedId == rb10.getId()) {
									dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ"
											+ packageName + "10Ԫ�ײ�?");
									ruyipackage_str = "10";
								}
								if (checkedId == rb8.getId()) {
									dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ"
											+ packageName + "8Ԫ�ײ�?");
									ruyipackage_str = "8";
								}
								if (checkedId == rb6.getId()) {
									dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ"
											+ packageName + "6Ԫ�ײ�?");
									ruyipackage_str = "6";
								}
								if (checkedId == rb4.getId()) {
									dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ"
											+ packageName + "4Ԫ�ײ�?");
									ruyipackage_str = "4";
								}
								if (checkedId == rb2.getId()) {
									dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ"
											+ packageName + "2Ԫ�ײ�?");
									ruyipackage_str = "2";
								}
							}

						});
				if (!editchanged) {
					ruyipackage_str = "10";
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(
						RuyiHelper.this);
				builder.setTitle("��ѡ���ײ�����");
				builder.setView(myView);
				// ȷ��
				builder.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								showDialog(DIALOG1_KEY);
								Thread t = new Thread(new Runnable() {

									@Override
									public void run() {
//										while (iretrytimes < 3
//												&& iretrytimes > 0) {
											re = PackageInterface
													.packageUpdate(
															tsubscribeId,
															Integer
																	.parseInt(ruyipackage_str)
																	* 100 + "",
															sessionid);// login_User,
											editchanged = false;

											try {
												obj = new JSONObject(re);
												error_code = obj
														.getString("error_code");
												PublicMethod
														.myOutput("----------edit-----------"
																+ error_code);
			//									iretrytimes = 3;
											} catch (JSONException e) {
												e.printStackTrace();
//												iretrytimes--;

											}
						//				}
								//		iretrytimes = 2;
										if (error_code.equals("000000")) {
											Message msg = new Message();
											msg.what = 0;
											handler1.sendMessage(msg);
										} else if (error_code.equals("070002")) {
											Message msg = new Message();
											msg.what = 7;
											handler1.sendMessage(msg);
										} else if (error_code.equals("040101")) {
											Message msg = new Message();
											msg.what = 15;// �޸� �ײͼ�¼Ϊ��
											// �³�2010/7/10
											handler1.sendMessage(msg);
										} else if (error_code.equals("00")) {
											Message msg = new Message();
											msg.what = 8;
											handler1.sendMessage(msg);
										} else {
											Message msg = new Message();
											msg.what = 15;// �޸�ʧ�� �³�2010/7/10
											handler1.sendMessage(msg);
										}
									}

								});
								t.start();

							}

						});
				// ȡ��
				builder.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
							}

						});
				builder.show();

			}
		}
	}

	// ������7.9�޸��ײͽӿ�
	/**
	 * �����ײ��ж�����ť��ִ�в���
	 * 
	 * @param view
	 *            ������İ�ť
	 */
	protected void showRuyiPackageSubscribe(int aViewId) {// View view
		// boolean isatus = states[(view.getId() - 100) / 3][(view.getId() -
		// 100) %
		// 3];
		boolean isatus = states[(aViewId - 100) / 3][(aViewId - 100) % 3];
		PublicMethod.myOutput("---isatus---------------" + isatus);
		if (!isatus) {
			return;
		}
		viewid = aViewId;// view.getId();

		// ��ʱ���涩�����ײ����࣬�����´ε�¼��ʱ����ʾ����

		if (aViewId == 100) {
			packageName = chooseLuckLotteryNum_zhonglei[0];// ˫ɫ��
			// textview.setText(textssq);
			lottery_type = "B001";
			tsubscribeId = tsubscribeIdssq;

		}
		if (aViewId == 103) {
			packageName = chooseLuckLotteryNum_zhonglei[1];// ����3D
			// textview.setText(textfc3d);
			lottery_type = "D3";
			tsubscribeId = tsubscribeIdfc3d;

		}
		if (aViewId == 106) {
			packageName = chooseLuckLotteryNum_zhonglei[2];// ���ֲ�
			// textview.setText(textqlc);
			lottery_type = "QL730";
			tsubscribeId = tsubscribeIdqlc;

		}
		// wangyl 8.27 add pl3 and dlt
		if (aViewId == 109) {
			packageName = chooseLuckLotteryNum_zhonglei[3];// ������
			lottery_type = "PL3_33";
			tsubscribeId = tsubscribeIdpl3;

		}
		if (aViewId == 112) {
			packageName = chooseLuckLotteryNum_zhonglei[4];// ����͸
			lottery_type = "DLT_23529";
			tsubscribeId = tsubscribeIddlt;

		}

		// ������ 7.5 �����޸ģ���ӵ�½�ж�
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		if (sessionid == null || sessionid.equals("")) {
			Intent intentSession = new Intent(RuyiHelper.this, UserLogin.class);
			startActivityForResult(intentSession, 0);
			// startActivity(intentSession);
		} else {
			LayoutInflater factory = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView = factory.inflate(R.layout.ruyipackage_dialog, null);
			radioGroup = (RadioGroup) myView
					.findViewById(R.id.ruyipackage_radiogroup);
			rb10 = (RadioButton) myView.findViewById(R.id.ruyipackage_10);
			rb8 = (RadioButton) myView.findViewById(R.id.ruyipackage_8);
			rb6 = (RadioButton) myView.findViewById(R.id.ruyipackage_6);
			rb4 = (RadioButton) myView.findViewById(R.id.ruyipackage_4);
			rb2 = (RadioButton) myView.findViewById(R.id.ruyipackage_2);
			dialog_tips = (TextView) myView.findViewById(R.id.ruyipackage_tips);
			dialog_tips.setText("��ȷ�϶���" + packageName + "10Ԫ�ײ�?");
			radioGroup
					.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							subscribechanged = true;
							if (checkedId == rb10.getId()) {
								dialog_tips.setText("��ȷ�϶���" + packageName
										+ "10Ԫ�ײ�?");
								ruyipackage_str_subscribe = "10";
							}
							if (checkedId == rb8.getId()) {
								dialog_tips.setText("��ȷ�϶���" + packageName
										+ "8Ԫ�ײ�?");
								ruyipackage_str_subscribe = "8";
							}
							if (checkedId == rb6.getId()) {
								dialog_tips.setText("��ȷ�϶���" + packageName
										+ "6Ԫ�ײ�?");
								ruyipackage_str_subscribe = "6";
							}
							if (checkedId == rb4.getId()) {
								dialog_tips.setText("��ȷ�϶���" + packageName
										+ "4Ԫ�ײ�?");
								ruyipackage_str_subscribe = "4";
							}
							if (checkedId == rb2.getId()) {
								dialog_tips.setText("��ȷ�϶���" + packageName
										+ "2Ԫ�ײ�?");
								ruyipackage_str_subscribe = "2";
							}
						}

					});

			if (!subscribechanged) {
				ruyipackage_str_subscribe = "10";
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(
					RuyiHelper.this);
			builder.setTitle("��ѡ���ײ�����");
			builder.setView(myView);
			// ȷ��
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// wangyl 20101220 ��������֤
							ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
									RuyiHelper.this, "addInfo");
							String sessionId = pre
									.getUserLoginInfo("sessionid");
							showDialog(DIALOG1_KEY);// ��ʾ������ʾ�� 2010/7/10�³�
							// ��Ϊ�߳�
							Thread t = new Thread(new Runnable() {

								@Override
								public void run() {

									ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
											RuyiHelper.this, "addInfo");
									sessionid = shellRW
											.getUserLoginInfo("sessionid");
									phonenum = shellRW
											.getUserLoginInfo("phonenum");
									// wangyl 8.30 ����븣�ʶ����ӿڲ�ͬ
									if (lottery_type.equals("B001")
											|| lottery_type.equals("D3")
											|| lottery_type.equals("QL730")) {

										re = PackageInterface
												.packageDeal(
														lottery_type,
														Integer
																.parseInt(ruyipackage_str_subscribe)
																* 100 + "",
														sessionid);// login_User,

									} else if (lottery_type.equals("PL3_33")) {

										re = PackageInterface
												.packageDealTC(
														phonenum,
														"T01002",
														Integer
																.parseInt(ruyipackage_str_subscribe)
																* 100 + "",
														sessionid);

									} else if (lottery_type.equals("DLT_23529")) {

										re = PackageInterface
												.packageDealTC(
														phonenum,
														"T01001",
														Integer
																.parseInt(ruyipackage_str_subscribe)
																* 100 + "",
														sessionid);

									}

									subscribechanged = false;
									try {
										obj = new JSONObject(re);
										error_code = obj
												.getString("error_code");
									} catch (JSONException e) {

									}

									// wangyl 8.30 ����븣�ʶ����ӿڲ�ͬ
									if (lottery_type.equals("B001")
											|| lottery_type.equals("D3")
											|| lottery_type.equals("QL730")) {
										if (error_code.equals("000000")) {
											// Toast.makeText(RuyiExpressFeel.this,
											// "�����ײͳɹ�����",
											// Toast.LENGTH_SHORT).show();
											Message msg = new Message();
											msg.what = 0;
											handler1.sendMessage(msg);

										} else if (error_code.equals("070002")) {
											Message msg = new Message();
											msg.what = 7;
											handler1.sendMessage(msg);
											// Toast.makeText(RuyiExpressFeel.this,
											// "���¼",
											// Toast.LENGTH_SHORT).show();
										} else if (error_code.equals("350002")) {
											Message msg = new Message();
											msg.what = 10;
											handler1.sendMessage(msg);
											// Toast.makeText(RuyiExpressFeel.this,
											// "�ײ��Ѷ���",
											// Toast.LENGTH_SHORT).show();
										} else if (error_code.equals("040006")) {
											Message msg = new Message();
											msg.what = 11;
											handler1.sendMessage(msg);
											// Toast.makeText(RuyiExpressFeel.this,
											// "�ײ��Ѷ��ƣ��޸�ʧ��",
											// Toast.LENGTH_SHORT).show();
										} else if (error_code.equals("00")) {
											Message msg = new Message();
											msg.what = 8;
											handler1.sendMessage(msg);
										}
										// Toast.makeText(RuyiExpressFeel.this,
										// "�����쳣",
										// Toast.LENGTH_SHORT).show();
										else {
											Message msg = new Message();
											msg.what = 12;
											handler1.sendMessage(msg);
											// Toast.makeText(RuyiExpressFeel.this,
											// "�����ײ�ʧ�ܣ���",
											// Toast.LENGTH_SHORT).show();
										}
									} else if (lottery_type.equals("PL3_33")
											|| lottery_type.equals("DLT_23529")) {
										// �ײͶ��Ƴɹ�
										if (error_code.equals("350002")) {
											Message msg = new Message();
											msg.what = 0;
											handler1.sendMessage(msg);
										}
										// ��¼ʧ��
										if (error_code.equals("07002")) {
											Message msg = new Message();
											msg.what = 7;
											handler1.sendMessage(msg);
										}
										// �ںŲ��Ϸ�
										if (error_code.equals("040003")) {
											Message msg = new Message();
											msg.what = 20;
											handler1.sendMessage(msg);
										}
										// �ײͶ���ʧ��
										if (error_code.equals("350003")) {
											Message msg = new Message();
											msg.what = 12;
											handler1.sendMessage(msg);
										}
										// �˻��쳣
										if (error_code.equals("040002")) {
											Message msg = new Message();
											msg.what = 21;
											handler1.sendMessage(msg);
										}

									}
								}

							});
							t.start();

						}

					});
			// ȡ��
			builder.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
						}

					});
			builder.show();
		}

	}

	public class RuyiPackageEfficientAdapter extends BaseAdapter {

		protected LayoutInflater mInflater; // �������б���
		Context context;

		public RuyiPackageEfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			this.context = context;
		}

		@Override
		public int getCount() {
			return chooseLuckLotteryNum_zhonglei.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// �������б����е���ϸ����
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
					RuyiHelper.this, "addInfo");
			sessionid = shellRW.getUserLoginInfo("sessionid");
			if (sessionid == null || sessionid.equals("")) {
				holderText[0] = "";
				holderText[1] = "";
				holderText[2] = "";
				holderText[3] = "";
				holderText[4] = "";
			} else {
				// ��ʾ�Ѷ����ײ�
				ShellRWSharesPreferences shellRWtext = new ShellRWSharesPreferences(
						RuyiHelper.this, "addInfo");
				textssq = shellRWtext.getUserLoginInfo("ssqtext");
				PublicMethod.myOutput("-------holderText [0]textssq--------"
						+ textssq);

				textfc3d = shellRWtext.getUserLoginInfo("fc3dtext");
				PublicMethod.myOutput("-------holderText [0]textfc3d--------"
						+ textfc3d);

				textqlc = shellRWtext.getUserLoginInfo("qlctext");
				PublicMethod.myOutput("-------holderText [0]textqlc--------"
						+ textqlc);
				textpl3 = shellRWtext.getUserLoginInfo("pl3text");
				PublicMethod.myOutput("-------holderText [0]textqlc--------"
						+ textpl3);

				textdlt = shellRWtext.getUserLoginInfo("dlttext");
				PublicMethod.myOutput("-------holderText [0]textqlc--------"
						+ textdlt);
				if (textssq != null && !textssq.equalsIgnoreCase("")) {
					holderText[0] = "��ѡÿ��" + textssq + "Ԫ�ײ�";
					subscribeFlag[0] = false;
					PublicMethod.myOutput("------holderText [0]--------"
							+ holderText[0]);
				} else {
					holderText[0] = "";
					subscribeFlag[0] = true;
					// holderText [0]="��ѡÿ��"+ssqamount+"Ԫ�ײ�";
					// PublicMethod.myOutput("------holderText
					// [0]else--------"+holderText
					// [0]);
				}
				if (textfc3d != null && !textfc3d.equalsIgnoreCase("")) {
					holderText[1] = "��ѡÿ��" + textfc3d + "Ԫ�ײ�";
					subscribeFlag[1] = false;
					PublicMethod.myOutput("------holderText [1]--------"
							+ holderText[1]);
				} else {
					holderText[1] = "";
					subscribeFlag[2] = true;
					// holderText [1]="��ѡÿ��"+fc3damount+"Ԫ�ײ�";
					// PublicMethod.myOutput("------holderText
					// [1]else--------"+holderText
					// [1]);
				}
				if (textqlc != null && !textqlc.equalsIgnoreCase("")) {
					holderText[2] = "��ѡÿ��" + textqlc + "Ԫ�ײ�";
					subscribeFlag[2] = false;
					PublicMethod.myOutput("------holderText [2]--------"
							+ holderText[2]);
				} else {
					holderText[2] = "";
					subscribeFlag[2] = true;
					// holderText [2]="��ѡÿ��"+qlcamount+"Ԫ�ײ�";
					// PublicMethod.myOutput("------holderText
					// [2]else--------"+holderText
					// [2]);
				}
				// wangyl 8.27 add pl3 and dlt
				if (textpl3 != null && !textpl3.equalsIgnoreCase("")) {
					holderText[3] = "��ѡÿ��" + textpl3 + "Ԫ�ײ�";
					subscribeFlag[3] = false;
					PublicMethod.myOutput("------holderText [3]--------"
							+ holderText[3]);
				} else {
					holderText[3] = "";
					subscribeFlag[3] = true;
				}
				if (textdlt != null && !textdlt.equalsIgnoreCase("")) {
					holderText[4] = "��ѡÿ��" + textdlt + "Ԫ�ײ�";
					subscribeFlag[4] = false;
					PublicMethod.myOutput("------holderText [4]--------"
							+ holderText[4]);
				} else {
					holderText[4] = "";
					subscribeFlag[4] = true;
				}
			}

			// �벼���е���Ϣ��������
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.ruyipackage_listview_item, null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView
						.findViewById(R.id.ruyipackage_icon_id);
				holder.icon.setImageResource(mIcon[position]);
				holder.ruyipackage_title = (TextView) convertView
						.findViewById(R.id.ruyipackage_title);
				holder.ruyipackage_title
						.setText(chooseLuckLotteryNum_zhonglei[position]);
				holder.ruyipackage_text = (TextView) convertView
						.findViewById(R.id.ruyipackage_text);
				holder.ruyipackage_text.setText(holderText[position]);

				// ���ð�ť
				holder.iButtonGroupLayout = (LinearLayout) convertView
						.findViewById(R.id.ruyipackage_listview_layout_button_group);
				holder.iButtonGroupLayout.setId(position + LAYOUT_INDEX1);
				iPosition = position;
				LinearLayout iImageButton = new LinearLayout(context);
				iImageButton.setOrientation(LinearLayout.HORIZONTAL);
				for (iadapter = 0; iadapter < 3; iadapter++) {
					ImageView iImage = new ImageView(context);
					PublicMethod.myOutput("-----iImage-------subscribeFlag["
							+ position + "]----------------"
							+ subscribeFlag[position]);
					// states = new boolean[3];
					// trueΪδ����
					if (subscribeFlag[position]) {
						imageGroup = imageGroup1;
						states[position][0] = true;
						states[position][1] = false;
						states[position][2] = false;
					} else {
						imageGroup = imageGroup2;
						states[position][0] = false;
						states[position][1] = true;
						states[position][2] = true;
					}
					iImage.setImageResource(imageGroup[iadapter]);
					iImage.setPadding(6, 0, 0, 0);
					iImage.setId(iadapter + 3 * position + 100);
					iImage.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// trueΪδ����
							// PublicMethod.myOutput("-----"+states.length+"
							// "+iadapter);
							iCurrentBtFlag = iadapter;
							iCurrentId = arg0.getId();
							showRuyipackageImageGroup(arg0);
						}
					});
					iImageButton.addView(iImage);
				}
				holder.iButtonGroupLayout.addView(iImageButton);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView ruyipackage_title;
			TextView ruyipackage_text;
			LinearLayout iButtonGroupLayout;
		}
	}

	// ��������ײ��б�������������Դ

	protected void showRuyipackageImageGroup(View view) {
		// int viewId = view.getId();
		viewId = view.getId();
		// ����
		if (viewId == 100 || viewId == 103 || viewId == 106 || viewId == 109
				|| viewId == 112) {
			showRuyiPackageSubscribe(viewId);// cc 8.11
		} else {
			Qurey();
		}
	}

	// �³� �ײͲ�ѯ 20100729
	protected void Qurey() {
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		phonenum = shellRW.getUserLoginInfo("phonenum");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		// �ȸ�ֵΪ��
		ssqamount = "";
		fc3damount = "";
		qlcamount = "";
		// wangyl 8.27 add pl3 and dlt
		pl3amount = "";
		dltamount = "";
		if (aState = true) {
			showDialog(DIALOG1_KEY);
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
						
						try {
							try {
								re = PackageInterface.packageSelect(sessionid);
								obj = new JSONObject(re);

								error_code = obj.getString("error_code");
								if (error_code.equals("000000")) {
									tsubscribeId = obj
											.getString("tsubscribeId");
									String state4 = obj.getString("state");// String
									String lotterytype4 = obj
											.getString("lotNo");
									String amount_str = obj.getString("amount");
									int amount = Integer.parseInt(amount_str) / 100;

									// ˫ɫ��
									if (state4.equals("����")
											&& lotterytype4.equals("B001")) {
										// ssqFlag = true;
										subscribeFlag[0] = false;
										ssqamount = amount + "";
									} else {
										// ssqFlag = false;
										subscribeFlag[0] = true;
									}
									// ����3D
									if (state4.equals("����")
											&& lotterytype4.equals("D3")) {
										// fc3dFlag = true;
										subscribeFlag[1] = false;
										fc3damount = amount + "";
									} else {
										// fc3dFlag = false;
										subscribeFlag[1] = true;
									}

									// ���ֲ�
									if (state4.equals("����")
											&& lotterytype4.equals("QL730")) {
										// qlcFlag = true;
										subscribeFlag[2] = false;
										qlcamount = amount + "";
										// ��һ�ε�¼ʱ��ס�ײ��Ƕ���Ԫ��
										// ShellRWSharesPreferences shellRW =new
										// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
										// shellRW.setUserLoginInfo("textqlc",
										// "��ѡÿ��"+qlcamount+"Ԫ�ײ�");
									} else {
										// qlcFlag = false;
										subscribeFlag[2] = true;
									}
									// wangyl 8.27 add pl3 and dlt
									// ������
									if (state4.equals("����")
											&& lotterytype4.equals("PL3_33")) {
										subscribeFlag[3] = false;
										pl3amount = amount + "";
									} else {
										subscribeFlag[3] = true;
									}
									// ����͸
									if (state4.equals("����")
											&& lotterytype4.equals("DLT_23529")) {
										subscribeFlag[4] = false;
										dltamount = amount + "";
									} else {
										subscribeFlag[4] = true;
									}
									Message msg = new Message();
									// ��ѯ���ٵ��� �˶� �޸� 20100729 �³�
									msg.what = 16;
									handler1.sendMessage(msg);
								} else if (error_code.equals("060004")
										|| error_code.equals("070002")) {
									// cc 8.11
									iCurrentBtFlag = -1;
									Message msg = new Message();
									msg.what = 0;
									handler1.sendMessage(msg);
								}
							//	iretrytimes = 3;
							} catch (Exception e) {
								jsonObject3 = new JSONArray(re);

								for (int i = 0; i < jsonObject3.length(); i++) {

									obj = jsonObject3.getJSONObject(i);
									error_code = obj.getString("error_code");
									if (("000000").equals(error_code)) {

										// ��������ƶ���if�� 2010/7/10 �³�
										// ֻ�гɹ�ʱ���ܻ�ȡ�����������

										String state1 = obj.getString("state");// String
										String lotterytype1 = obj
												.getString("lotNo");
										// tsubscribeIdssq=obj.getString("tsubscribeId");
										// ˫ɫ�� ��������ƶ���if�� 2010/7/10 �³�

										if (state1.equals("����")
												&& lotterytype1.equals("B001")) {
											// ssqFlag = true;
											// ������ʱ��Ǯ������ˮ��д�� 2010/7/10 �³�
											tsubscribeIdssq = obj
													.getString("tsubscribeId");
											String amount_str = obj
													.getString("amount");
											int amount = Integer
													.parseInt(amount_str) / 100;
											subscribeFlag[0] = false;
											ssqamount = amount + "";
											i = jsonObject3.length();
										} else {
											// ssqFlag = false;
											subscribeFlag[0] = true;
											ssqamount = "";
										}

									}

								}
								for (int i = 0; i < jsonObject3.length(); i++) {

									obj = jsonObject3.getJSONObject(i);

									if (("000000").equals(error_code)) {
										// Message msg = new Message();
										// msg.what=0;
										// handler.sendMessage(msg);
										tsubscribeIdfc3d = obj
												.getString("tsubscribeId");
										// ��������ƶ���if�� 2010/7/10 �³�
										String state2 = obj.getString("state");
										String lotterytype2 = obj
												.getString("lotNo");

										// ����3D ��������ƶ���if�� 2010/7/10 �³�
										if (state2.equals("����")
												&& lotterytype2.equals("D3")) {
											// fc3dFlag = true;
											subscribeFlag[1] = false;
											// ������ʱ��Ǯ������ˮ��д�� 2010/7/10 �³�
											String amount_str = obj
													.getString("amount");
											int amount = Integer
													.parseInt(amount_str) / 100;
											fc3damount = amount + "";
											tsubscribeIdfc3d = obj
													.getString("tsubscribeId");
											i = jsonObject3.length();
										} else {
											// fc3dFlag = false;
											subscribeFlag[1] = true;
											fc3damount = "";
										}
									}

								}
								for (int i = 0; i < jsonObject3.length(); i++) {

									obj = jsonObject3.getJSONObject(i);

									if (("000000").equals(error_code)) {
										// tsubscribeIdqlc=obj.getString("tsubscribeId");

										// ��������ƶ���if�� 2010/7/10 �³�
										String state3 = obj.getString("state");
										String lotterytype3 = obj
												.getString("lotNo");

										// ���ֲ� ��������ƶ���if�� 2010/7/10 �³�
										if (state3.equals("����")
												&& lotterytype3.equals("QL730")) {
											// qlcFlag = true;
											// ������ʱ��Ǯ������ˮ��д��
											tsubscribeIdqlc = obj
													.getString("tsubscribeId");
											subscribeFlag[2] = false;
											String amount_str = obj
													.getString("amount");
											int amount = Integer
													.parseInt(amount_str) / 100;
											i = jsonObject3.length();
										} else {
											// qlcFlag = false;
											subscribeFlag[2] = true;
											qlcamount = "";
										}
									}

								}
								// wangyl 8.27 add pl3 and dlt
								for (int i = 0; i < jsonObject3.length(); i++) {

									obj = jsonObject3.getJSONObject(i);

									if (("000000").equals(error_code)) {
										String state4 = obj.getString("state");
										String lotterytype4 = obj
												.getString("lotNo");

										if (state4.equals("����")
												&& lotterytype4
														.equals("PL3_33")) {
											// ������ʱ��Ǯ������ˮ��д��
											tsubscribeIdpl3 = obj
													.getString("tsubscribeId");
											subscribeFlag[3] = false;
											String amount_str = obj
													.getString("amount");
											int amount = Integer
													.parseInt(amount_str) / 100;
											pl3amount = amount + "";
											i = jsonObject3.length();
										} else {
											subscribeFlag[3] = true;
											pl3amount = "";
										}
									}

								}

								for (int i = 0; i < jsonObject3.length(); i++) {
									obj = jsonObject3.getJSONObject(i);
									if (("000000").equals(error_code)) {
										String state5 = obj.getString("state");
										String lotterytype5 = obj
												.getString("lotNo");

										if (state5.equals("����")
												&& lotterytype5
														.equals("DLT_23529")) {
											// ������ʱ��Ǯ������ˮ��д��
											tsubscribeIddlt = obj
													.getString("tsubscribeId");
											subscribeFlag[4] = false;
											String amount_str = obj
													.getString("amount");
											int amount = Integer
													.parseInt(amount_str) / 100;
											dltamount = amount + "";
											i = jsonObject3.length();
										} else {
											subscribeFlag[4] = true;
											dltamount = "";
										}
									}

								}
								// handel�ɹ�
								Message msg = new Message();
								msg.what = 16;
								handler1.sendMessage(msg);
							}
						} catch (JSONException e) {
							error_code = "00";
						}
	//				}

					if (error_code.equalsIgnoreCase("00")) {
						Message msg = new Message();
						msg.what = 8;
						handler1.sendMessage(msg);
					}
				}

			});
			t.start();
		}
		aState = true;

	}

	/**
	 * ����ѡ�ŵ����б�
	 */
	protected void showCLLNMainListView() {

		setContentView(R.layout.choose_luck_lottery_num_main);
		ImageView tvreturn = (ImageView) findViewById(R.id.tv_choose_luck_lottery_num_return);
		tvreturn.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(popupwindow!=null){
					popupwindow.dismiss();
				}			
				showListView(ID_MORELISTVIEW);
			}
		});
		ListView listview = (ListView) findViewById(R.id.choose_luck_lottery_num_listview_id);
		ChooseLuckLotteryNum_EfficientAdapter adapter = new ChooseLuckLotteryNum_EfficientAdapter(this);
		listview.setAdapter(adapter);
		listview.setDividerHeight(0);
		// PublicMethod.setmydividerHeight(listview);
		// ��ʾ������ͼ��

	}

	/**
	 * ����ѡ�ŵ�������
	 */
	public class ChooseLuckLotteryNum_EfficientAdapter extends BaseAdapter {

		protected LayoutInflater mInflater; // �������б���
		Context context;
		int id;

		public ChooseLuckLotteryNum_EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			this.context = context;
		}

		@Override
		public int getCount() {
			return chooseLuckLotteryNum_title.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// �������б����е���ϸ����
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			// �벼���е���Ϣ��������
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.choose_luck_lottery_num_listview_layout_two,
						null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView
						.findViewById(R.id.choose_luck_lottery_num_icon_id);
				holder.icon.setImageResource(mIcon[position]);
				holder.chooseLuckLotteryNum_zhonglei_View = (TextView) convertView
						.findViewById(R.id.choose_luck_lottery_num_zhonglei);
				holder.chooseLuckLotteryNum_zhonglei_View
						.setText(chooseLuckLotteryNum_title[position]);

				// ���ð�ť
				holder.iButtonGroupLayout = (LinearLayout) convertView
						.findViewById(R.id.choose_luck_num_listview_layout_button_group);
				holder.iButtonGroupLayout.setId(position + LAYOUT_INDEX);

				int i;
				for (i = 0; i < 4; i++) {
					LinearLayout iImageButton = new LinearLayout(context);
					iImageButton.setOrientation(LinearLayout.VERTICAL);
					iImageButton.setPadding(5, 0, 0, 0);
					ImageView iImage = new ImageView(context);
					iImage.setImageResource(imageId[i]);
					iImageButton.addView(iImage);

					TextView iText = new TextView(context);
					iText.setText(textContent[i]);
					iText.setGravity(Gravity.CENTER);
					iText.setTextColor(Color.BLACK);
					iImageButton.addView(iText);

					iImageButton.setId(i + 4 * position + 100);
					if (i == 0) {
						iImageButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								// id = arg0.getId() - LAYOUT_INDEX;
								showGridView(arg0, mXingzuoIcon, xingzuoName);
							}
						});
					} else if (i == 1) {
						iImageButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								// id = arg0.getId() - LAYOUT_INDEX;
								showGridView(arg0, mShengxiaoIcon,
										shengxiaoName);
							}
						});
					} else if (i == 2) {
						iImageButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								// id = arg0.getId() - LAYOUT_INDEX;
								showXingMingGialog(arg0);
							}
						});
					} else if (i == 3) {
						iImageButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								// id = arg0.getId() - LAYOUT_INDEX;
								showShengRiDialog(arg0);
							}
						});
					}

					holder.iButtonGroupLayout.addView(iImageButton);
				}
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView chooseLuckLotteryNum_zhonglei_View;
			LinearLayout iButtonGroupLayout;
		}

	}

	// ����ѡ���б�ѡ��
	protected void showXingYunXuanHaoListView(int listviewid, String aGameType) {

		switch (listviewid) {
		case ID_CLLN_SHOWBALLMONRY:
			iQuitFlag = 100; 
			// iCallOnKeyDownFlag=false;
			// chooseLuckNumShowBallMoney(aGameType);
			dialogLuck(aGameType);
			break;
		case ID_CLLN_SHOW_ZHIFU_DIALOG:
			showAgreeAndPayDialog(aGameType);
			break;
		case ID_CLLN_SHOW_TRADE_SUCCESS:
			showTradeSuccess();
			break;
		}
	}

	/**
	 * ����ѡ�ŶԻ���
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	private void dialogLuck(String aGameType) {
		iType = 6;
		type06 = aGameType;
		String title = null;
		if (aGameType.equalsIgnoreCase("ssq")) {
			title = getResources().getString(R.string.xyxh_shuangseqiu);
			// �����޸ģ��޸ı���
		} else if (aGameType.equalsIgnoreCase("fc3d")) {

			title = getResources().getString(R.string.xyxh_fucai3d);
		} else if (aGameType.equalsIgnoreCase("qlc")) {
			title = getResources().getString(R.string.xyxh_qilecai);
		} else if (aGameType.equalsIgnoreCase("pl3")) {
			title = getResources().getString(R.string.xyxh_pailiesan);
		} else if (aGameType.equalsIgnoreCase("cjdlt")) {
			title = getResources().getString(R.string.xyxh_chaojidaletou);
		}
		LayoutInflater factory = LayoutInflater.from(this);
		final View view = factory.inflate(
				R.layout.choose_luck_num_trade_showball, null);
		// ��ʼ��view
		chooseLuckNumShowBallMoney(aGameType, view);
		Builder dialog = new AlertDialog.Builder(this).setTitle(title).setView(
				view).setPositiveButton("֧��",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						buyLuck();
					}

				}).setNegativeButton("����",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}

				});
		dialog.show();

	}

	/**
	 * ��ʾ֧���ɹ��Ի���
	 */
	protected void showTradeSuccess() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("��Ͷע�ɹ���");

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						showListView(ID_XINGYUNXUANHAO);
					}

				});
		builder.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ����
					}

				});
		builder.show();
	}

	/**
	 * ����ѡ�ţ�����ȷ��֧��Dailog
	 */
	// ������ 7.3 �����޸ģ��ڸöԻ��������С�����ʾ
	protected void showAgreeAndPayDialog(String aGameType) {
		type06 = aGameType;

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		String[] gameType = { "ssq", "fc3d", "qlc", "pl3", "cjdlt" };
		int[] titleID = { R.string.shuangseqiu, R.string.fucai3d,
				R.string.qilecai, R.string.pailiesan, R.string.chaojidaletou };

		LayoutInflater inflater = LayoutInflater.from(this);
		View textView = inflater.inflate(
				R.layout.choose_luck_lottery_num_agree_and_pay_dialog, null);

		agreeAndpayTitleView = (TextView) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_title);
		TextView tx01 = (TextView) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text01);
		tx01.setText("��" + hour + ":" + minute + "��������ѡ���ǣ�" + "\n");

		TextView tx02 = (TextView) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text02);
		agreePayBallLayout01 = (LinearLayout) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_linearlayout01);
		agreePayBallLayout02 = (LinearLayout) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_linearlayout02);
		agreePayBallLayout03 = (LinearLayout) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_linearlayout03);
		agreePayBallLayout04 = (LinearLayout) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_linearlayout04);
		agreePayBallLayout05 = (LinearLayout) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_linearlayout05);

		// zlm 7.13 �����޸ģ��任���� zlm 8.11 �����޸�
		for (int i = 0; i < 5; i++) {
			if (type06.equalsIgnoreCase(gameType[i])) {
				agreeAndpayTitleView.setText(titleID[i]);
				showAgreeAndPayBall();
			}
		}

		TextView tx03 = (TextView) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text03);
		tx03.setText("ע����  " + "" + iProgressJizhu + "   ע" + "\n");

		TextView tx04 = (TextView) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text04);
		tx04.setText("������  " + "" + iProgressBeishu + "   ��" + "\n");

		TextView tx05 = (TextView) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text05);
		tx05.setText("׷�ţ�  " + "" + iProgressQishu + "   ��" + "\n");

		TextView tx06 = (TextView) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text06);
		tx06.setText("�ܽ�  " + "" + iProgressJizhu * iProgressBeishu * 2
				+ "   Ԫ" + "\n");// *iProgressQishu ȡ�� zlm 20100713

		TextView tx07 = (TextView) textView
				.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text07);
		tx07.setText("ȷ��֧����" + "\n");

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setView(textView);

		builder.setPositiveButton(R.string.zhifu,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// wangyl 20101220 ������֤��֤
						ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
								RuyiHelper.this, "addInfo");
						String sessionIdStr = pre.getUserLoginInfo("sessionid");
						showDialog(DIALOG1_KEY);// ��ʾ������ʾ�� �³�2010/7/10
						Thread t = new Thread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								String strBet;
								String[] strBetTC = new String[2];
								// strBet = bet(type06 , iProgressJizhu ,
								// iProgressBeishu);
								// �½ӿ� �³�20100711
								PublicMethod.myOutput("----iProgressQishu-----"
										+ iProgressQishu
										+ "---iProgressJizhu---"
										+ iProgressJizhu);
								if (type06.equals("ssq")
										|| type06.equals("fc3d")
										|| type06.equals("qlc")) {
									strBet = bet(type06, iProgressQishu,
											iProgressJizhu, iProgressBeishu);
									// if(strBet.equals("0000")){
									if (strBet.equals("000000")) {
										Message msg = new Message();
										msg.what = 6;
										handler1.sendMessage(msg);
									} else if (strBet.equals("070002")) {
										Message msg = new Message();
										msg.what = 7;
										handler1.sendMessage(msg);
									} else if (strBet.equals("040006")) {
										Message msg = new Message();
										msg.what = 1;
										handler1.sendMessage(msg);
									} else if (strBet.equals("1007")) {
										Message msg = new Message();
										msg.what = 2;
										handler1.sendMessage(msg);
									} else if (strBet.equals("040007")) {
										Message msg = new Message();
										msg.what = 4;
										handler1.sendMessage(msg);
									} else if (strBet.equals("4444")) {
										Message msg = new Message();
										msg.what = 3;
										handler1.sendMessage(msg);
									} else if (strBet.equals("00")) {
										Message msg = new Message();
										msg.what = 8;
										handler1.sendMessage(msg);
									} else {
										Message msg = new Message();
										msg.what = 9;
										handler1.sendMessage(msg);
									}
								} else if (type06.equals("pl3")
										|| type06.equals("cjdlt")) {
									strBetTC = betTC(type06, iProgressQishu,
											iProgressJizhu, iProgressBeishu);
									if (strBetTC[0].equals("0000")
											&& strBetTC[1].equals("000000")) {
										Message msg = new Message();
										msg.what = 6;
										handler1.sendMessage(msg);
									} else if (strBetTC[0].equals("070002")) {
										Message msg = new Message();
										msg.what = 7;
										handler1.sendMessage(msg);
									} else if (strBetTC[0].equals("0000")
											&& strBetTC[1].equals("000001")) {
										Message msg = new Message();
										msg.what = 17;
										handler1.sendMessage(msg);
									} else if (strBetTC[0].equals("1007")) {
										Message msg = new Message();
										msg.what = 2;
										handler1.sendMessage(msg);
									} else if (strBetTC[0].equals("040006")
											|| strBetTC[0].equals("201015")) {
										Message msg = new Message();
										msg.what = 1;
										handler1.sendMessage(msg);
									} else if (strBetTC[1].equals("002002")) {
										Message msg = new Message();
										msg.what = 18;
										handler1.sendMessage(msg);
									} else if (strBetTC[0].equals("00")
											&& strBetTC[1].equals("00")) {
										Message msg = new Message();
										msg.what = 8;
										handler1.sendMessage(msg);
									} else {
										Message msg = new Message();
										msg.what = 9;
										handler1.sendMessage(msg);
									}

								}

							}

						});
						t.start();
					}

				});
		builder.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ����
					}

				});
		builder.show();
	}

	/**
	 * ȷ��֧��ҳ����ʾС��
	 */
	public void showAgreeAndPayBall() {
		// zlm 8.11 �����޸�
		LinearLayout[] agreePayBallLayoutGroup = { agreePayBallLayout01,
				agreePayBallLayout02, agreePayBallLayout03,
				agreePayBallLayout04, agreePayBallLayout05 };
		for (int i = 1; i < 6; i++) {
			if (iProgressJizhu == i) {
				for (int j = 0; j < i; j++) {
					showAgreeAndPayBallLayout(type06,
							agreePayBallLayoutGroup[j], receiveRandomNum[j]);
				}
			}
		}
	}

	/**
	 * ��ȷ��֧��ҳ�������С�򲼾�
	 * 
	 * @param aGameType
	 * @param aLinearLayout
	 * @param aRandomNum
	 */
	// ������ 7.3 ������ӣ��������֧���Ի�����С�����ʾ
	public void showAgreeAndPayBallLayout(String aGameType,
			LinearLayout aLinearLayout, int[] aRandomNum) {

		if (aGameType.equalsIgnoreCase("ssq")) {
			OneBallView showBallView;
			for (int i = 0; i < 6; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ aRandomNum[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				aLinearLayout.addView(showBallView, lp);
				// PublicMethod.myOutput("----------------showBall");
			}
			showBallView = new OneBallView(this);
			showBallView.initBall(BALL_WIDTH, BALL_WIDTH, "" + aRandomNum[6],
					aBlueColorResId);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			aLinearLayout.addView(showBallView, lp);
		} else if (aGameType.equalsIgnoreCase("fc3d")
				|| aGameType.equalsIgnoreCase("pl3")) {
			OneBallView showBallView;
			for (int i = 0; i < 3; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ aRandomNum[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				aLinearLayout.addView(showBallView, lp);
				// PublicMethod.myOutput("----------------showBall");
			}
		} else if (aGameType.equalsIgnoreCase("qlc")) {
			OneBallView showBallView;
			for (int i = 0; i < 7; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ aRandomNum[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				aLinearLayout.addView(showBallView, lp);
			}
		} else if (aGameType.equalsIgnoreCase("cjdlt")) { // zlm ��������͸
			OneBallView showBallView;
			for (int i = 0; i < 5; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ aRandomNum[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				aLinearLayout.addView(showBallView, lp);
			}
			for (int i = 5; i < 7; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ aRandomNum[i], aBlueColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				aLinearLayout.addView(showBallView, lp);
			}
		}
	}

	/**
	 * Ͷע����ӿ�
	 * 
	 * @param aGameType
	 * @param zhuShu
	 * @param beiShu
	 * @return
	 */
	// �½ӿ� �³� 20100711
	public String bet(String aGameType, int qiShu, int zhuShu, int beiShu) {
		// ������ 7.6 �����޸ģ���Ӵ���
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		String sessionid = pre.getUserLoginInfo("sessionid");
		String phonenum = pre.getUserLoginInfo("phonenum");

		String error_code = "00";
		BettingInterface betting = BettingInterface.getInstance();
		if (!phonenum.equals("") || phonenum != null) {
			if (aGameType.equalsIgnoreCase("ssq")) {
				int type = 0;
				int[][] iZhuShu;
				String strBets;
				// GT gt = new GT();
				// �����Ƿ�ı�������ж� �³� 8.11
				iZhuShu = changeShuZu(zhuShu, aGameType);
				strBets = GT.betCodeToString(type, zhuShu, "00", beiShu,
						iZhuShu);
				// �½ӿ� �³�20100711
				// error_code=betting.Betting(phonenum, strBets, ""+zhuShu
				// ,sessionid);
				// ���Ӧ�ó��Ա��� �³� 20100713
				error_code = betting.bettingNew(strBets, qiShu + "", zhuShu
						* 200 * iProgressBeishu * qiShu + "", sessionid);
			} else if (aGameType.equalsIgnoreCase("fc3d")) {
				int type = 1;
				int[][] iZhuShu;
				String strBets;
				// GT gt = new GT();
				// �����Ƿ�ı�������ж� �³� 8.11
				iZhuShu = changeShuZu(zhuShu, aGameType);
				strBets = GT.betCodeToString(type, zhuShu, "00", beiShu,
						iZhuShu);
				// error_code=betting.BettingNew(phonenum, strBets, ""+zhuShu
				// ,sessionid);
				// �½ӿ� �³� 20100711
				// ���Ӧ�ó��Ա��� �³� 20100713
				error_code = betting.bettingNew(strBets, qiShu + "", zhuShu
						* 200 * iProgressBeishu * qiShu + "", sessionid);
			} else if (aGameType.equalsIgnoreCase("qlc")) {
				int type = 2;
				int[][] iZhuShu;
				String strBets;
				// GT gt = new GT();
				iZhuShu = changeShuZu(zhuShu, aGameType);
				strBets = GT.betCodeToString(type, zhuShu, "00", beiShu,
						iZhuShu);
				// error_code = betting.Betting(phonenum, strBets, ""+zhuShu
				// ,sessionid);
				// �½ӿ� �³� 20100711
				// ���Ӧ�ó��Ա��� �³� 20100713

				error_code = betting.bettingNew(strBets, qiShu + "", zhuShu
						* 200 * iProgressBeishu * qiShu + "", sessionid);
			}
		}
		return error_code;
	}

	/**
	 * ���Ͷע����ӿ�
	 * 
	 * @param aGameType
	 * @param qiShu
	 * @param zhuShu
	 * @param beiShu
	 * @return
	 */
	public String[] betTC(String aGameType, int qiShu, int zhuShu, int beiShu) {
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		String sessionid = pre.getUserLoginInfo("sessionid");
		String phonenum = pre.getUserLoginInfo("phonenum");

		String[] error_code = new String[2];

		BettingInterface betting = BettingInterface.getInstance();
		if (aGameType.equalsIgnoreCase("pl3")) {
			int type = 3;
			int[][] iZhuShu;
			String strBets;
			// GT gt = new GT();
			iZhuShu = changeShuZu(zhuShu, aGameType);
			strBets = GT.betCodeToStringTC(type, iZhuShu);
			// error_code = betting.Betting(phonenum, strBets, ""+zhuShu
			// ,sessionid);
			// �½ӿ� �³� 20100711
			// ���Ӧ�ó��Ա��� �³� 20100713
			error_code = betting.BettingTC(phonenum, "T01002", strBets, beiShu
					+ "", zhuShu * 2 * beiShu + "", "2", qiShu + "", sessionid);
		} else if (aGameType.equalsIgnoreCase("cjdlt")) {
			int type = 4;
			int[][] iZhuShu;
			String strBets;
			// GT gt = new GT();
			iZhuShu = changeShuZu(zhuShu, aGameType);
			strBets = GT.betCodeToStringTC(type, iZhuShu);
			// error_code = betting.Betting(phonenum, strBets, ""+zhuShu
			// ,sessionid);
			// �½ӿ� �³� 20100711
			// ���Ӧ�ó��Ա��� �³� 20100713
			error_code = betting.BettingTC(phonenum, "T01001", strBets, beiShu
					+ "", zhuShu * 2 * beiShu + "", "2", qiShu + "", sessionid);
		}
		return error_code;
	}

	/**
	 * ������õ�������任�ɶ�ά����
	 * 
	 * @param jiZhu
	 * @param aGameType
	 * @return
	 */
	public int[][] changeShuZu(int jiZhu, String aGameType) {
		int[][] zhuShu = null;
		if (aGameType.equalsIgnoreCase("ssq")
				|| aGameType.equalsIgnoreCase("qlc")
				|| aGameType.equalsIgnoreCase("cjdlt")) {
			switch (jiZhu) {
			case 1:
				zhuShu = new int[1][7];
				zhuShu[0] = receiveRandomNum[0];
				break;
			case 2:
				zhuShu = new int[2][7];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				break;
			case 3:
				zhuShu = new int[3][7];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				break;
			case 4:
				zhuShu = new int[4][7];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				zhuShu[3] = receiveRandomNum[3];
				break;
			case 5:
				zhuShu = new int[5][7];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				zhuShu[3] = receiveRandomNum[3];
				zhuShu[4] = receiveRandomNum[4];
				break;
			}
		} else if (aGameType.equalsIgnoreCase("fc3d")
				|| aGameType.equalsIgnoreCase("pl3")) {
			switch (jiZhu) {
			case 1:
				zhuShu = new int[1][3];
				zhuShu[0] = receiveRandomNum[0];
				break;
			case 2:
				zhuShu = new int[2][3];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				break;
			case 3:
				zhuShu = new int[3][3];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				break;
			case 4:
				zhuShu = new int[4][3];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				zhuShu[3] = receiveRandomNum[3];
				break;
			case 5:
				zhuShu = new int[5][3];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				zhuShu[3] = receiveRandomNum[3];
				zhuShu[4] = receiveRandomNum[4];
				break;
			}
		}
		return zhuShu;
	}

	/**
	 * ����ѡ��֮ѡ�Ų���
	 */

	private void chooseLuckNumShowBallMoney(String aGameType, View view) {
		// setContentView(R.layout.choose_luck_num_trade_showball);
		type05 = aGameType;

		// zlm 7.15 �����޸ģ����С���Ƿ�ɻ����ж�
		isDrawing = false;

		// zlm 7.13 �����޸ģ���ӳ�ʼ��
		iProgressJizhu = 1;
		iProgressBeishu = 1;
		iProgressQishu = 1;

		int iStartZhuShu = iProgressJizhu;
		int iStartBeiShu = iProgressBeishu;
		int iStartQiShu = iProgressQishu;

		mTextMoney = (TextView) view
				.findViewById(R.id.choose_luck_num_show_money_total);
		mTextJizhu = (TextView) view
				.findViewById(R.id.choose_luck_num_text_zhushu);
		mTextBeishu = (TextView) view
				.findViewById(R.id.choose_luck_num_text_beishu);
		mTextQishu = (TextView) view
				.findViewById(R.id.choose_luck_num_text_qishu);

		String iTempString = "�ܽ�"
				+ (iStartZhuShu * iStartBeiShu * iStartQiShu * 2) + "Ԫ\n\n"
				+ "ȷ��֧����";
		mTextMoney.setText(iTempString);
		mTextJizhu.setText("" + iProgressJizhu);
		mTextBeishu.setText("" + iProgressBeishu);
		mTextQishu.setText("" + iProgressQishu);

		// mSeekBarJizhu = (SeekBar) view
		// .findViewById(R.id.choose_luck_num_seek_jizhu);
		// mSeekBarJizhu.setOnSeekBarChangeListener(this);
		// mSeekBarJizhu.setProgress(iProgressJizhu);

		mSeekBarBeishu = (SeekBar) view
				.findViewById(R.id.choose_luck_num_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		mSeekBarBeishu.setProgress(iProgressBeishu);

		mSeekBarQishu = (SeekBar) view
				.findViewById(R.id.choose_luck_num_seek_qishu);
		mSeekBarQishu.setOnSeekBarChangeListener(this);
		mSeekBarQishu.setProgress(iProgressQishu);

		// showSsqLayout01(type05);
		// zlm 7.16 �����޸ģ��任��ʾ����
		drawSuccess(iProgressJizhu, view);

		// ������7.3������ӣ����ѡ��ע���ġ��Ӻš�

		// ������7.3������ӣ����ѡ�����ġ����š�
		ImageButton subtractBeishuBtn = (ImageButton) view
				.findViewById(R.id.choose_luck_num_seekbar_subtract_beishu);
		subtractBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {

				mSeekBarBeishu.setProgress(--iProgressBeishu);
			}

		});
		// ������7.3������ӣ����ѡ�����ġ��Ӻš�
		ImageButton addBeishuBtn = (ImageButton) view
				.findViewById(R.id.choose_luck_num_seekbar_add_beishu);
		addBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				mSeekBarBeishu.setProgress(++iProgressBeishu);
			}

		});
		// ������7.3������ӣ����ѡ�������ġ����š�
		ImageButton subtractQihaoBtn = (ImageButton) view
				.findViewById(R.id.choose_luck_num_seekbar_subtract_qishu);
		subtractQihaoBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				mSeekBarQishu.setProgress(--iProgressQishu);
			}

		});
		// ������7.3������ӣ����ѡ�������ġ��Ӻš�
		ImageButton addQihaoBtn = (ImageButton) view
				.findViewById(R.id.choose_luck_num_seekbar_add_qishu);
		addQihaoBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				mSeekBarQishu.setProgress(++iProgressQishu);
			}

		});

		// ������7.3�����޸ģ����ͶעImageButton�Ĵ���
		ImageButton payImageBtn = new ImageButton(this);
		payImageBtn = (ImageButton) view
				.findViewById(R.id.choose_luck_num_touzhu);
		payImageBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});

		// ������7.4�����޸ģ� ��Button����ImageButton
		ImageButton returnBtn = new ImageButton(this);
		returnBtn = (ImageButton) view
				.findViewById(R.id.choose_luck_num_return);
		returnBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showListView(ID_XINGYUNXUANHAO);
			}
		});
	}

	/**
	 * ���ַ��� zlm 8.11 �����޸�
	 * @param v
	 */
	public void gameClassify(View v) {
		if (Math.floor(v.getId() / 4) == 25) {
			type02 = "ssq";
		} else if (Math.floor(v.getId() / 4) == 26) {
			type02 = "fc3d";
		} else if (Math.floor(v.getId() / 4) == 27) {
			type02 = "qlc";
		} else if (Math.floor(v.getId() / 4) == 28) { // ������
			type02 = "pl3";
		} else if (Math.floor(v.getId() / 4) == 29) { // ��������͸
			type02 = "cjdlt";
		}
	}

	/**
	 * ������ʾ---��ʾ��������Ф
	 * 
	 * @param v
	 *            ��ͼ
	 * @param gridIcon
	 *            ����/��Ф��ͼƬ
	 * @param gridIconName
	 *            ����/��Ф������
	 */
	protected void showGridView(View v, Integer[] gridIcon,
			String[] gridIconName) {	
		gameClassify(v); // ��Ʊ�������

		gridImage = gridIcon;
		gridText = gridIconName;

		View popupView = this.getLayoutInflater().inflate(
				R.layout.choose_luck_lottery_num_main_grid, null);
		popupwindow = new PopupWindow(popupView, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT, true);

		GridView gridview = (GridView) popupView
				.findViewById(R.id.chooose_luck_lottery_num_gridview_id);
		// ������7.3�����޸ģ��������ء�Button����ImageButton
		ImageButton button = (ImageButton) popupView
				.findViewById(R.id.chooose_luck_lottery_num_return);
		button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				iQuitFlag=90;
				popupwindow.dismiss();
			}

		});

		// ������ 7.8 �����޸ģ�����µ�Grid������
		ChooseLuckLotteryNum_GridAdapter gridAdapter = new ChooseLuckLotteryNum_GridAdapter(
				this);
		gridview.setAdapter(gridAdapter);

		// ������ 7.7 �����޸�
		// if (type02.equalsIgnoreCase("ssq") || type02.equalsIgnoreCase("fc3d")
		// || type02.equalsIgnoreCase("qlc")) {
		// popupwindow.showAsDropDown(v);
		// } else if (type02.equalsIgnoreCase("pl3")
		// || type02.equalsIgnoreCase("cjdlt")) {
		// popupwindow.showAtLocation(
		// findViewById(R.id.choose_luck_lottery_num_listview_id),
		// Gravity.CENTER, 0, 0);
		// }
		popupwindow.showAtLocation(
				findViewById(R.id.choose_luck_lottery_num_listview_id),
				Gravity.CENTER, 0, 0);
	}

	/**
	 * GridView������
	 */
	public class ChooseLuckLotteryNum_GridAdapter extends BaseAdapter {

		protected LayoutInflater mInflater; // �������б���
		Context context;

		public ChooseLuckLotteryNum_GridAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mXingzuoIcon.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.choose_luck_lottery_num_main_grid_specific,
						null);
				holder = new ViewHolder();
				// ���ð�ť
				holder.iButtonGroupLayout = (LinearLayout) convertView
						.findViewById(R.id.choose_luck_num_gridview_layout_button_group);

				LinearLayout iImageButton = new LinearLayout(context);
				iImageButton.setOrientation(LinearLayout.VERTICAL);

				ImageView iImage = new ImageView(context);
				iImage.setImageResource(gridImage[position]);
				iImageButton.addView(iImage);

				TextView iText = new TextView(context);
				iText.setText(gridText[position]);
				iText.setGravity(Gravity.CENTER);
				iText.setTextColor(Color.BLACK);
				iImageButton.addView(iText);

				iImageButton.setId(position);
				iImageButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// popupwindow.dismiss();
						showXingYunXuanHaoListView(ID_CLLN_SHOWBALLMONRY,
								type02);
					}
				});

				holder.iButtonGroupLayout.addView(iImageButton);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}

		class ViewHolder {
			LinearLayout iButtonGroupLayout;
		}
	}

	/**
	 * ����ѡ��
	 * 
	 * @param v
	 *            ��ͼ
	 */
	protected void showXingMingGialog(View v) {
		gameClassify(v); // ��Ʊ�������

		LayoutInflater inflater = LayoutInflater.from(this);
		View textView = inflater.inflate(
				R.layout.choose_luck_lottery_num_xingming_dialog_layout, null);
		editTextXingming = (EditText) textView
				.findViewById(R.id.clln_xingming_edit_dialog_id);
		builderXingming = new AlertDialog.Builder(this);
		builderXingming.setCancelable(true);
		builderXingming.setTitle(R.string.qingshuruxingming);
		builderXingming.setView(textView);

		builderXingming.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						if (editTextXingming.length() == 0) {
							showAttentionImportNameDialog(type02);

						} else {
							showXingYunXuanHaoListView(ID_CLLN_SHOWBALLMONRY,
									type02);
						}
					}

				});
		builderXingming.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ����
					}

				});
		builderXingming.show();
	}

	/**
	 * ��ʾ�û����������ĶԻ���
	 */
	protected void showAttentionImportNameDialog(String aGameType) {
		type07 = aGameType;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("���������Ϣ�������ݲ���Ϊ�գ�����������������");

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						showXingYunXuanHaoListView(
								ID_CLLN_XINGMING_DIALOG_LISTVIEW, type07);
					}

				});

		builder.show();
	}

	// ������7.3�����޸ģ�������ȫ���ˣ�ʹ���Դ���������ʾ��
	/*---------����ѡ��------------*/
	// ������ 7.7 �����޸ģ�����������Ϣ
	/**
	 * ����ѡ��
	 * 
	 * @param v
	 *            ��ͼ
	 */
	protected void showShengRiDialog(View v) {
		gameClassify(v); // ��Ʊ�������

		LayoutInflater inflater = LayoutInflater.from(this);
		View textView = inflater.inflate(
				R.layout.choose_luck_num_shengri_date_picker, null);
		DatePicker dp = (DatePicker) textView
				.findViewById(R.id.choose_luck_num_date_picker_id);
		Calendar calendar = Calendar.getInstance();
		int calYear = calendar.get(Calendar.YEAR);
		int calMonth = calendar.get(Calendar.MONTH);
		int calDay = calendar.get(Calendar.DAY_OF_MONTH);
		dp.init(calYear, calMonth, calDay, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.qingshurushengri);
		builder.setView(textView);

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

						showXingYunXuanHaoListView(ID_CLLN_SHOWBALLMONRY,
								type02);
					}

				});
		builder.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ����
					}

				});
		builder.show();
	}

	Handler seekBarHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case -6:
				if (isDrawing)
					return;
				else {
					if (countLinearLayout != iProgressJizhu)
						drawSuccess(iProgressJizhu, null);
				}
				break;
			}
		}
	};

	public void drawSuccess(int aChangeTo, View view) {
		isDrawing = true;

		layoutAll = new LinearLayout[5];
		layoutAll[0] = (LinearLayout) view
				.findViewById(R.id.choose_luck_num_ball_linearlayout_01);
		layoutAll[1] = (LinearLayout) view
				.findViewById(R.id.choose_luck_num_ball_linearlayout_02);
		layoutAll[2] = (LinearLayout) view
				.findViewById(R.id.choose_luck_num_ball_linearlayout_03);
		layoutAll[3] = (LinearLayout) view
				.findViewById(R.id.choose_luck_num_ball_linearlayout_04);
		layoutAll[4] = (LinearLayout) view
				.findViewById(R.id.choose_luck_num_ball_linearlayout_05);
		int i;
		countLinearLayout = 0;
		for (i = 0; i < 5; i++) {
			if (layoutAll[i] != null && layoutAll[i].getChildCount() > 0) {
				countLinearLayout = countLinearLayout + 1; // zlm 7.14
				// �����޸ģ��޸���ʽ
			}
		}

		if (countLinearLayout < aChangeTo) {
			for (i = countLinearLayout; i < aChangeTo; i++) {
				// showAllBallLayout(type05 , layoutAll[i]);
				receiveRandomNum[i] = showAllBallLayout(type05, layoutAll[i]);
				layoutAll[i].invalidate();
			}
		} else {// if(countLinearLayout > iProgressJizhu)
			for (i = aChangeTo; i < countLinearLayout; i++) {
				layoutAll[i].removeAllViewsInLayout();
				layoutAll[i].invalidate();
			}
		}

		// isDrawing = false;
		// Message msg = new Message();
		// msg.what = -6;
		// seekBarHandler.sendMessage(msg);
	}

	/**
	 * ����ѡ��������С��Ĳ��� zlm 7.13 �����޸ģ���Ӵ���
	 * 
	 * @param aGameType
	 * @param layout
	 */
	public int[] showAllBallLayout(String aGameType, LinearLayout layout) {
		// zlm 7.16 �����޸ģ����С���Ƿ�ɻ����ж�
		isDrawing = false;
		int[] numRandomGroup = new int[7];
		if (aGameType.equalsIgnoreCase("ssq")) {
			OneBallView showBallView;
			int[] group01 = new int[7];
			int[] group02 = new int[6];
			int[] group03 = new int[6];
			int[] group = new int[7];

			group01 = getBallNum(aGameType, 7);

			for (int i = 0; i < 6; i++) {
				group02[i] = group01[i];
			}

			group03 = sort(group02);

			for (int i = 0; i < 6; i++) {
				group[i] = group03[i];
			}

			group[6] = group01[6];
			numRandomGroup = group;
			for (int i = 0; i < 6; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ numRandomGroup[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
				// PublicMethod.myOutput("----------------showBall");
			}
			showBallView = new OneBallView(this);
			showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
					+ numRandomGroup[6], aBlueColorResId);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layout.addView(showBallView, lp);
			// ballLayoutGroup.addView(layout01, lp);
		} else if (aGameType.equalsIgnoreCase("fc3d")) {
			OneBallView showBallView;
			int[] group = new int[3];
			int[] group01 = new int[7];
			group01 = getBallNum(aGameType, 3);
			for (int i = 0; i < 3; i++) {
				group[i] = group01[i];
			}
			// int[] randomNumGroup ;
			numRandomGroup = group;
			for (int i = 0; i < 3; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ numRandomGroup[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
				// PublicMethod.myOutput("----------------showBall");
			}
		} else if (aGameType.equalsIgnoreCase("qlc")) {
			OneBallView showBallView;
			numRandomGroup = sort(getBallNum(aGameType, 7));
			for (int i = 0; i < 7; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ numRandomGroup[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
			}
		} else if (aGameType.equalsIgnoreCase("pl3")) { // zlm ������
			OneBallView showBallView;
			int[] group = new int[3];
			int[] group01 = new int[7];
			group01 = getBallNum(aGameType, 3);
			for (int i = 0; i < 3; i++) {
				group[i] = group01[i];
			}
			numRandomGroup = group;
			for (int i = 0; i < 3; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ numRandomGroup[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
			}
		} else if (aGameType.equalsIgnoreCase("cjdlt")) { // zlm ��������͸
			OneBallView showBallView;
			int[] group01 = new int[7];
			int[] group02 = new int[5];
			int[] group03 = new int[5];
			int[] group04 = new int[2];
			int[] group05 = new int[2];
			int[] group = new int[7];

			group01 = getBallNum(aGameType, 7);

			for (int i = 0; i < 5; i++) {
				group02[i] = group01[i];
			}

			group03 = sort(group02);

			for (int i = 0; i < 5; i++) {
				group[i] = group03[i];
			}

			group04[0] = group01[5];
			group04[1] = group01[6];

			group05 = sort(group04);

			group[5] = group05[0];
			group[6] = group05[1];
			numRandomGroup = group;

			for (int i = 0; i < 5; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ numRandomGroup[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
			}
			for (int i = 5; i < 7; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ numRandomGroup[i], aBlueColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
			}
		}
		return numRandomGroup;
	}

	/**
	 * ��������
	 * 
	 * @param t
	 * @return
	 */
	public static int[] sort(int t[]) {
		int t_s[] = t;

		for (int i = 0; i < t_s.length; i++) {
			PublicMethod.myOutput("----------------------t_s0000" + i + "----"
					+ t_s[i]);
		}
		// int t_a = 0;
		for (int i = 0; i < t_s.length; i++) {
			for (int j = i + 1; j < t_s.length; j++) {
				if (t_s[i] > t_s[j]) {
					temp = t_s[i];
					t_s[i] = t_s[j];
					t_s[j] = temp;
				}
			}
		}
		for (int i = 0; i < t_s.length; i++) {
			PublicMethod.myOutput("----------------------t_s" + i + t_s[i]);
		}
		return t_s;
	}

	/**
	 * ��ȡС��������/
	 * 
	 * @param aGameType
	 * @param aRandomNums
	 * @return
	 */
	public int[] getBallNum(String aGameType, int aRandomNums) {
		int[] ballNumStr = new int[7];
		// int[] ballNumStr = {1,2,3,4,5,6,7};
		if (aGameType.equalsIgnoreCase("ssq")) {
			int[] iShowRedBallNum = PublicMethod.getRandomsWithoutCollision(
					aRandomNums - 1, 1, 33);
			int[] iShowBlueBallNum = PublicMethod.getRandomsWithoutCollision(1,
					1, 16);

			for (int i = 0; i < 6; i++) {
				ballNumStr[i] = iShowRedBallNum[i];
			}
			ballNumStr[6] = iShowBlueBallNum[0];
		} else if (aGameType.equalsIgnoreCase("fc3d")
				|| aGameType.equalsIgnoreCase("pl3")) { // zlm ������
			int[] iShowBallNum01 = PublicMethod.getRandomsWithoutCollision(
					aRandomNums - 2, 0, 9);
			int[] iShowBallNum02 = PublicMethod.getRandomsWithoutCollision(
					aRandomNums - 2, 0, 9);
			int[] iShowBallNum03 = PublicMethod.getRandomsWithoutCollision(
					aRandomNums - 2, 0, 9);
			ballNumStr[0] = iShowBallNum01[0];
			ballNumStr[1] = iShowBallNum02[0];
			ballNumStr[2] = iShowBallNum03[0];
		} else if (aGameType.equalsIgnoreCase("qlc")) {
			int[] iShowRedBallNum = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 1, 30);
			ballNumStr = iShowRedBallNum;
		} else if (aGameType.equalsIgnoreCase("cjdlt")) { // zlm ��������͸
			int[] iShowRedBallNum = PublicMethod.getRandomsWithoutCollision(
					aRandomNums - 2, 1, 35);
			int[] iShowBlueBallNum = PublicMethod.getRandomsWithoutCollision(2,
					1, 12);

			for (int i = 0; i < 5; i++) {
				ballNumStr[i] = iShowRedBallNum[i];
			}
			ballNumStr[5] = iShowBlueBallNum[0];
			ballNumStr[6] = iShowBlueBallNum[1];
		}

		return ballNumStr;
	}

	/**
	 * seekBar�Ĵ��� //zlm 7.13 �����޸ģ���seekBar�����Handler��Ϣ
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		if (progress < 1)
			seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();

		switch (seekBar.getId()) {
		case R.id.choose_luck_num_seek_jizhu:
			iProgressJizhu = iProgress;
			mTextJizhu.setText("" + iProgressJizhu);
			showTextSumMoney();

			// Message msg = new Message();
			// msg.what = -6;
			// seekBarHandler.sendMessage(msg);
			break;

		case R.id.choose_luck_num_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			showTextSumMoney();
			break;

		case R.id.choose_luck_num_seek_qishu:
			iProgressQishu = iProgress;
			mTextQishu.setText("" + iProgressQishu);
			showTextSumMoney();
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// zlm 7.14 �����޸ģ���Ӵ���
		// Message msg = new Message();
		// msg.what = -6;
		// seekBarHandler.sendMessage(msg);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// zlm 7.14 �����޸ģ���Ӵ���
		// Message msg = new Message();
		// msg.what = -6;
		// seekBarHandler.sendMessage(msg);
	}

	/**
	 * ע����Ǯ��
	 */
	public void showTextSumMoney() {
		int iZhuShu = iProgressJizhu;
		int iBeiShu = iProgressBeishu;
		int iQiShu = iProgressQishu;
		String iTempString = "�ܽ�" + (iZhuShu * iBeiShu * 2) + "Ԫ\n\n"
				+ "ȷ��֧����";// *iQiShuȡ�� zlm 20100713
		mTextMoney.setText(iTempString);
	}

	// protected EditText name;
	// protected EditText money_bank;
	// protected EditText money_bank_start;
	// protected EditText money_bank_num;
	// protected EditText money;

	/**
	 * ���ִ���
	 */
	protected void getMoneyDialog() {
		start = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		View getView = inflater.inflate(R.layout.get_money_submit, null);
		final EditText name = (EditText) getView
				.findViewById(R.id.get_money_name_edit);
		final Spinner money_brank = (Spinner) getView
				.findViewById(R.id.get_money_bank_spinner);
		money_brank.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				int position = money_brank.getSelectedItemPosition();
				PublicMethod.myOutput("*********position " + position);
				if (position == 0) {
					bankName = "�й���������";
				} else if (position == 1) {
					bankName = "�й���������";
				} else if (position == 2) {
					bankName = "�й���������";
				} else if (position == 3) {
					bankName = "�й�ũҵ����";
				} else if (position == 4) {
					bankName = "�й���ͨ����";
				} else if (position == 5) {
					bankName = "�й���������";
				} else if (position == 6) {
					bankName = "���ڷ�չ����";
				} else if (position == 7) {
					bankName = "�Ϻ��ֶ���չ����";
				} else if (position == 8) {
					bankName = "�й��������";
				} else if (position == 9) {
					bankName = "�㶫��չ����";
				} else if (position == 10) {
					bankName = "��ҵ����";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		final EditText money_bank_start = (EditText) getView
				.findViewById(R.id.get_money_bank_start_edit);
		final EditText money_bank_num = (EditText) getView
				.findViewById(R.id.get_money_bank_num_edit);
		final EditText money = (EditText) getView
				.findViewById(R.id.get_money_money_edit);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.get_money_title);
		builder.setView(getView);
		builder.setPositiveButton(R.string.get_money_submit,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						if (start) {
							start = false;

							String str = "";
							if (money.getText().toString() != null
									&& !money.getText().toString().equals("")) {
								str = Integer.toString(Integer.parseInt(money
										.getText().toString()) * 100);
							}
							final String strMoney = str;
							WinningVector.removeAllElements();
							showDialog(DIALOG1_KEY);
							new Thread(new Runnable() {
								@Override
								public void run() {
									String error_code = "00";

									re = GetCashInterface.getMoneyQuest(name.getText().toString(), sessionid,
											strMoney, bankName, money_bank_num.getText().toString(),
											money_bank_start.getText().toString());
									try {
										obj = new JSONObject(re);
										error_code = obj
												.getString("error_code");
									} catch (JSONException e) {

									}

									if (error_code.equals("0000")) {
										Message msg = new Message();
										msg.what = 40;
										handler.sendMessage(msg);

									} else if (error_code.equals("070002")) {
										Message msg = new Message();
										msg.what = 41;
										handler.sendMessage(msg);
									} else if (error_code.equals("0017")) {
										Message msg = new Message();
										msg.what = 43;
										handler.sendMessage(msg);
									} else if (error_code.equals("0018")) {
										Message msg = new Message();
										msg.what = 42;
										handler.sendMessage(msg);
									} else if (error_code.equals("0016")) {
										Message msg = new Message();
										msg.what = 48;
										handler.sendMessage(msg);
									}

								}

							}).start();
						}
					}

				});
		builder.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}

				});
		Dialog dialog = builder.create();
		dialog.show();
		dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	}

	protected boolean start = true;

	String bankName = "00";
	String moneyNum = "00";
	String bankaccount;
	String name;
	String areaName;

	/**
	 * �޸����ִ���
	 */
	protected void changeDialog() {
		start = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		View getView = inflater.inflate(R.layout.get_money_submit, null);

		final EditText name = (EditText) getView.findViewById(R.id.get_money_name_edit);
		final Spinner money_brank = (Spinner) getView.findViewById(R.id.get_money_bank_spinner);
		if (bankName.equals("�й���������")) {
			money_brank.setSelection(0);
		} else if (bankName.equals("�й���������")) {
			money_brank.setSelection(1);
		} else if (bankName.equals("�й���������")) {
			money_brank.setSelection(2);
		} else if (bankName.equals("�й�ũҵ����")) {
			money_brank.setSelection(3);
		} else if (bankName.equals("�й���ͨ����")) {
			money_brank.setSelection(4);
		} else if (bankName.equals("�й���������")) {
			money_brank.setSelection(5);
		} else if (bankName.equals("���ڷ�չ����")) {
			money_brank.setSelection(6);
		} else if (bankName.equals("�Ϻ��ֶ���չ����")) {
			money_brank.setSelection(7);
		} else if (bankName.equals("�й��������")) {
			money_brank.setSelection(8);
		} else if (bankName.equals("�㶫��չ����")) {
			money_brank.setSelection(9);
		} else if (bankName.equals("��ҵ����")) {
			money_brank.setSelection(10);
		}
		money_brank.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int position = money_brank.getSelectedItemPosition();
				if (position == 0) {
					bankName = "��������";
				} else if (position == 1) {
					bankName = "�й���������";
				} else if (position == 2) {
					bankName = "�й���������";
				} else if (position == 3) {
					bankName = "�й�ũҵ����";
				} else if (position == 4) {
					bankName = "��ͨ����";
				} else if (position == 5) {
					bankName = "�й���������";
				} else if (position == 6) {
					bankName = "���ڷ�չ����";
				} else if (position == 7) {
					bankName = "�Ϻ��ֶ���չ����";
				} else if (position == 8) {
					bankName = "�й��������";
				} else if (position == 9) {
					bankName = "�㶫��չ����";
				} else if (position == 10) {
					bankName = "��ҵ����";
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		final EditText money_bank_start = (EditText) getView.findViewById(R.id.get_money_bank_start_edit);
		final EditText money_bank_num = (EditText) getView.findViewById(R.id.get_money_bank_num_edit);
		final EditText money = (EditText) getView.findViewById(R.id.get_money_money_edit);
		money_bank_start.setText(areaName);
		name.setText(this.name);
		money_bank_num.setText(bankaccount);
		money.setText(this.moneyNum);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.get_money_title);
		builder.setView(getView);
		builder.setPositiveButton(R.string.get_money_submit,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						if (start) {
							start = false;
							String str = "";
							if (!money.getText().toString().equals(null)) {
								str = Integer.toString(Integer.parseInt(money
										.getText().toString()) * 100);
							}
							final String strMoney = str;
							showDialog(DIALOG1_KEY);
							new Thread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									String error_code = "00";

									re = GetCashInterface.changeQuest(name.getText().toString(), sessionid,
													money_bank_num.getText().toString(),
													bankName, 1, phonenum,money_bank_start.getText().toString(),strMoney);
									try {
										obj = new JSONObject(re);
										error_code = obj.getString("error_code");
									} catch (JSONException e) {

									}
									if (error_code.equals("000000")) {
										Message msg = new Message();
										msg.what = 38;
										handler.sendMessage(msg);

									} else if (error_code.equals("070002")) {// �û�δ��¼
										Message msg = new Message();
										msg.what = 41;
										handler.sendMessage(msg);
									} else if (error_code.equals("090021")) {// "090021";//�û��˻����������С�����ֽ��
										Message msg = new Message();
										msg.what = 44;
										handler.sendMessage(msg);
									} else if (error_code.equals("090022")) {// "090022";//�û��˻������ֽ��������
										Message msg = new Message();
										msg.what = 45;
										handler.sendMessage(msg);
									} else if (error_code.equals("090023")) {// "090023";//�û�����������ȥ������С�����ֽ��
										Message msg = new Message();
										msg.what = 46;
										handler.sendMessage(msg);
									} else if (error_code.equals("090024")) {// "090024";//�û�����������ȥ������С�����ֽ��
										Message msg = new Message();
										msg.what = 47;
										handler.sendMessage(msg);
									} else if (error_code.equals("090012")) {
										Message msg = new Message();
										msg.what = 48;
										handler.sendMessage(msg);
									} else {
										Message msg = new Message();
										msg.what = 39;
										handler.sendMessage(msg);
									}
								}
							}).start();
						}
					}

				});
		builder.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}

				});
		Dialog dialog = builder.create();
		dialog.show();
		dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	/**
	 * ���ִ���˴���
	 */
	protected void waitDialog() {
		start = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		View waitView = inflater.inflate(R.layout.get_money_state, null);
		TextView content = (TextView) waitView
				.findViewById(R.id.get_money_state_text);
		content.setText(R.string.wait_text);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.get_money_title);
		builder.setView(waitView);
		builder.setPositiveButton("�޸�", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// WinningVector.removeAllElements();
				if (start) {
					start = false;
					changeDialog();

				}
			}

		});
		builder.setNeutralButton("ȡ��", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// WinningVector.removeAllElements();
				if (start) {
					start = false;
					showDialog(DIALOG1_KEY);
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							String error_code = "00";

							re = GetCashInterface.cancelQuest(phonenum,
									sessionid);
							try {
								obj = new JSONObject(re);
								error_code = obj.getString("error_code");
							} catch (JSONException e) {

							}

							if (error_code.equals("000000")) {
								Message msg = new Message();
								msg.what = 36;
								handler.sendMessage(msg);

							} else {
								Message msg = new Message();
								msg.what = 37;
								handler.sendMessage(msg);
							}

						}

					}).start();
				}
			}

		});
		builder.setNegativeButton("����", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.show();
	}

	/**
	 * ��������д���
	 */
	protected void waitingDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View waitingView = inflater.inflate(R.layout.get_money_state, null);
		TextView content = (TextView) waitingView
				.findViewById(R.id.get_money_state_text);
		content.setText(R.string.waiting_text);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.get_money_title);
		builder.setView(waitingView);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// WinningVector.removeAllElements();
					}

				});
		builder.show();
	}

	/**
	 * ����ʧ�ܴ���
	 */
	protected void failDialog() {
		start = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		View failView = inflater.inflate(R.layout.get_money_state, null);
		TextView content = (TextView) failView
				.findViewById(R.id.get_money_state_text);
		content.setText(R.string.fail_text);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.get_money_title);
		builder.setView(failView);
		builder.setPositiveButton("�����ύ",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// WinningVector.removeAllElements();
						if (start) {
							start = false;
							getMoneyDialog();
						}
					}

				});
		builder.setNegativeButton(R.string.get_money_back,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// WinningVector.removeAllElements();
					}

				});
		builder.show();
	}

	/**
	 * ��ͨ����˴���
	 */
	protected void successDialog() {
		start = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		View successView = inflater.inflate(R.layout.get_money_state, null);
		TextView content = (TextView) successView
				.findViewById(R.id.get_money_state_text);
		content.setText(R.string.success_text);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.get_money_title);
		builder.setView(successView);
		builder.setPositiveButton("�ٴ�����",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// WinningVector.removeAllElements();
						if (start) {
							start = false;
							getMoneyDialog();
						}
					}

				});
		builder.setNegativeButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		builder.show();
	}

	/**
	 * 
	 * ϵͳ���ý���
	 * 
	 */
	protected void showXiTongSheZhi() {
		setContentView(R.layout.ruyihelper_xitongshezhi);
		final CheckBox isOnCheck = (CheckBox) findViewById(R.id.xitongshezhi_check_zhengdong);
		ImageView tvreturn = (ImageView) findViewById(R.id.tv_choose_luck_lottery_num_return);
		tvreturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showListView(ID_MORELISTVIEW);
			}
		});
		// ��ȡ�Ƿ��𶯲���
		shellRW = new ShellRWSharesPreferences(this, "addInfo");
		boolean isOn = shellRW.getBoolean("isOn");
		isOnCheck.setChecked(isOn);
		isOnCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					shellRW.setBoolean("isOn", true);
					isOnCheck.setText(getResources().getString(
							R.string.xitongshezhi_check_on).toString());
				} else {
					shellRW.setBoolean("isOn", false);
					isOnCheck.setText(getResources().getString(
							R.string.xitongshezhi_check_off).toString());
				}
			}
		});
	}

	/**
	 * ����ѡ��֧������
	 */
	protected void buyLuck() {
		// ������ 7.4 �����޸ģ���ӵ�½���ж�
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		String sessionidStr = shellRW.getUserLoginInfo("sessionid");
		if (sessionidStr == null || sessionidStr.equals("")) {
			Intent intentSession = new Intent(RuyiHelper.this, UserLogin.class);
			startActivityForResult(intentSession, 0);
		} else {
			// showXingYunXuanHaoListView(ID_CLLN_SHOW_ZHIFU_DIALOG,
			// type05);
			ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
					RuyiHelper.this, "addInfo");
			String sessionIdStr = pre.getUserLoginInfo("sessionid");
			showDialog(DIALOG1_KEY);// ��ʾ������ʾ�� �³�2010/7/10
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String strBet;
					String[] strBetTC = new String[2];
					// strBet = bet(type06 , iProgressJizhu ,
					// iProgressBeishu);
					// �½ӿ� �³�20100711
					PublicMethod.myOutput("----iProgressQishu-----"
							+ iProgressQishu + "---iProgressJizhu---"
							+ iProgressJizhu);
					if (type06.equals("ssq") || type06.equals("fc3d")
							|| type06.equals("qlc")) {
						strBet = bet(type06, iProgressQishu, iProgressJizhu,
								iProgressBeishu);
						// if(strBet.equals("0000")){
						if (strBet.equals("000000")) {
							Message msg = new Message();
							msg.what = 6;
							handler1.sendMessage(msg);
						} else if (strBet.equals("070002")) {
							Message msg = new Message();
							msg.what = 7;
							handler1.sendMessage(msg);
						} else if (strBet.equals("040006")) {
							Message msg = new Message();
							msg.what = 1;
							handler1.sendMessage(msg);
						} else if (strBet.equals("1007")) {
							Message msg = new Message();
							msg.what = 2;
							handler1.sendMessage(msg);
						} else if (strBet.equals("040007")) {
							Message msg = new Message();
							msg.what = 4;
							handler1.sendMessage(msg);
						} else if (strBet.equals("4444")) {
							Message msg = new Message();
							msg.what = 3;
							handler1.sendMessage(msg);
						} else if (strBet.equals("00")) {
							Message msg = new Message();
							msg.what = 8;
							handler1.sendMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = 9;
							handler1.sendMessage(msg);
						}
					} else if (type06.equals("pl3") || type06.equals("cjdlt")) {
						strBetTC = betTC(type06, iProgressQishu,
								iProgressJizhu, iProgressBeishu);
						if (strBetTC[0].equals("0000")
								&& strBetTC[1].equals("000000")) {
							Message msg = new Message();
							msg.what = 6;
							handler1.sendMessage(msg);
						} else if (strBetTC[0].equals("070002")) {
							Message msg = new Message();
							msg.what = 7;
							handler1.sendMessage(msg);
						} else if (strBetTC[0].equals("0000")
								&& strBetTC[1].equals("000001")) {
							Message msg = new Message();
							msg.what = 17;
							handler1.sendMessage(msg);
						} else if (strBetTC[0].equals("1007")) {
							Message msg = new Message();
							msg.what = 2;
							handler1.sendMessage(msg);
						} else if (strBetTC[0].equals("040006")
								|| strBetTC[0].equals("201015")) {
							Message msg = new Message();
							msg.what = 1;
							handler1.sendMessage(msg);
						} else if (strBetTC[1].equals("002002")) {
							Message msg = new Message();
							msg.what = 18;
							handler1.sendMessage(msg);
						} else if (strBetTC[0].equals("00")
								&& strBetTC[1].equals("00")) {
							Message msg = new Message();
							msg.what = 8;
							handler1.sendMessage(msg);
						} else {
							Message msg = new Message();
							msg.what = 9;
							handler1.sendMessage(msg);
						}

					}

				}

			});
			t.start();
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
	//	Constants.RUYIHELPERSHOWLISTTYPE=6;
		if(popupwindow!=null){
			popupwindow.dismiss();
			
			
		}
	}
	/**
	 * ֧������
	 */
	public String changeStr(String str){
		String typeStr=str;
		if(str.equals("1")){
			typeStr="��ֵ";		
		}else if(str.equals("2")){
			typeStr="֧��";
		}else if(str.equals("3")){
			typeStr="�ɽ�";
		}else if(str.equals("4")){
			typeStr="����";
		}
		
		return typeStr;
		
	}
}
