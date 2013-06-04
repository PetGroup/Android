package com.palmdream.RuyicaiAndroid;

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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.palmdream.netintface.GT;
import com.palmdream.netintface.jrtLot;

/**
 * �������֡��û����ġ�������Ȩ��ר�ҷ���
 */
public class RuyiHelper extends Activity implements MyDialogListener {
	public String phonenum;
	public String sessionid;
	String re;
	String error_code = "00";
	JSONObject obj;
	JSONArray jsonArray;
	JSONArray jsonArray_gifted;
	int iretrytimes = 2;
	String play_name = null;
	String batchcode = null;
	String betcode = null;
	String sell_datetime = null;
	String beishu = null;
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
	int iType=0;   //�ж�λ�������б����ӱ�ǩ  �³�20100803
	boolean iDetail = true; //�ж��Ƿ�����Ϣ   �³�20100803

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
	/* ר�ҷ��� */
	private static final String[] expertAnalyzeTypeName = { "˫ɫ�����", "����3D����",
			"���ֲʷ���" };
	// private static final String[] expertAnalyzeMore = {">>>>",">>>>",">>>>"};
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
	/* ���������б�������adapter����String[] From�Ĵ���� */
	public final static String ICON = "ICON";/* ͼ�� */
	public final static String TITLE = "TITLE"; /* ���� */

	// ���������б����ID
	public final static int ID_GAMEINTRODUTION = 1;/* �淨���� */
	public final static int ID_FEQUENTQUESTION = 2; /* �������� */
	public final static int ID_AUTHORIZING = 3;/* ������Ȩ�ַŵ������б��� */

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

	private static final int DIALOG1_KEY = 0;
	ProgressDialog progressdialog;

	private ImageButton btnreturn;/* ���� */// ������ 7.3 �����޸ģ���Button����ImageButton

	List<Map<String, Object>> list;/* �б�������������Դ */
	TextView text;/* ���� */

	// �û�����-�˻���ϸ-5��ǩ��ť
	ImageButton allbtn;/* ȫ�� */
	ImageButton rechargebtn;/* ��ֵ */
	ImageButton paybtn;/* ֧�� */
	ImageButton awardschoolbtn;/* ���� */
	ImageButton withdrawbtn;/* ���� */
	ImageButton returnbtn;/* ���� */

	/* �û�����-�˻���ϸ��̬���� */
	// private static String[] UserCenter_AccountDetail_TradingDate_All=
	// {"��������", "2010-04-01", "2010-04-02", "2010-04-03", "2010-04-05"
	// ,"2010-06-10"};
	// private static String[] UserCenter_AccountDetail_TradingMode_All =
	// {"��������", "��ֵ", "֧��", "����", "����", "����" };
	// private static String[] UserCenter_AccountDetail_Yu_E_All= {
	// "���","50.00(Ԫ)", "10.00(Ԫ)", "180.00(Ԫ)", "180.00(Ԫ)", "23.00(Ԫ)" };

	// private static String[] UserCenter_AccountDetail_TradingDate_Recharge=
	// {"��������", "2010-04-01", "2010-04-02", "2010-04-03", "2010-04-05" };
	// private static String[] UserCenter_AccountDetail_TradingMode_Recharge =
	// {"��������", "��ֵ", "��ֵ", "��ֵ", "��ֵ" };
	// private static String[] UserCenter_AccountDetail_Yu_E_Recharge= {
	// "���","10.00(Ԫ)", "23.00(Ԫ)", "120.00(Ԫ)", "140.00(Ԫ)" };

	// private static String[] UserCenter_AccountDetail_TradingDate_Pay=
	// {"��������", "2010-04-01", "2010-04-02", "2010-04-03", "2010-04-05" };
	// private static String[] UserCenter_AccountDetail_TradingMode_Pay =
	// {"��������", "֧��", "֧��", "֧��", "֧��" };
	// private static String[] UserCenter_AccountDetail_Yu_E_Pay= {
	// "���","30.00(Ԫ)", "40.00(Ԫ)", "100.00(Ԫ)", "110.00(Ԫ)" };
	//	
	// private static String[] UserCenter_AccountDetail_TradingDate_AwardSchool=
	// {"��������", "2010-04-01", "2010-04-02", "2010-04-03", "2010-04-05" };
	// private static String[] UserCenter_AccountDetail_TradingMode_AwardSchool
	// = {"��������", "����", "����", "����", "����" };
	// private static String[] UserCenter_AccountDetail_Yu_E_AwardSchool= {
	// "���","35.00(Ԫ)", "44.00(Ԫ)", "10.00(Ԫ)", "80.00(Ԫ)" };
	//	
	// private static String[] UserCenter_AccountDetail_TradingDate_Withdraw=
	// {"��������", "2010-04-01", "2010-04-02", "2010-04-03", "2010-04-05" };
	// private static String[] UserCenter_AccountDetail_TradingMode_Withdraw =
	// {"��������", "����", "����", "����", "����" };
	// private static String[] UserCenter_AccountDetail_Yu_E_Withdraw= {
	// "���","50.00(Ԫ)", "60.00(Ԫ)", "10.00(Ԫ)", "60.00(Ԫ)" };

	private static String[] UserCenter_AccountDetail_TradingDate;
	private static String[] UserCenter_AccountDetail_TradingMode;
	private static String[] UserCenter_AccountDetail_Yu_E;

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
				showUserCenterWinningCheck();
				break;
			case 2:
				progressdialog.dismiss();
				showUserCenterBettingDetails();
				break;
			case 3:
				progressdialog.dismiss();
				iQuitFlag = 30; // ������ 7.5 �����޸ģ��������û�����
				showUserCenterAccountDetails();
				break;
			case 4:
				progressdialog.dismiss();
				iQuitFlag = 30; // ������ 7.5 �����޸ģ����������б�
				showUserCenterGiftCheck();
				break;
			case 5:
				progressdialog.dismiss();
				Toast.makeText(RuyiHelper.this, "�����޸ĳɹ�", Toast.LENGTH_SHORT)
						.show();
				break;
			case 6:
				progressdialog.dismiss();
				showUserCenterAddLotteryQuery();
				// Toast.makeText(getBaseContext(), "��¼�ɹ�",
				// Toast.LENGTH_LONG).show();
				break;
			case 7:
				progressdialog.dismiss();
				// 30���Ӻ��ٴε�¼ �³� 20100719
				type="";
				Intent intentSession = new Intent(RuyiHelper.this,
						UserLogin.class);
				startActivityForResult(intentSession, 0);
				// Toast.makeText(RuyiHelper.this, "���¼",
				// Toast.LENGTH_SHORT).show();
				break;
			case 8:
				progressdialog.dismiss();
				Toast.makeText(RuyiHelper.this, "ϵͳ����", Toast.LENGTH_SHORT)
						.show();
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣", Toast.LENGTH_LONG)
						.show();
				break;
			case 10:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "��ѯʧ��", Toast.LENGTH_LONG)
						.show();
				break;
			case 11:
				progressdialog.dismiss();
				Toast.makeText(RuyiHelper.this, "ԭ�������", Toast.LENGTH_SHORT)
						.show();
				break;
			case 12:
				progressdialog.dismiss();
				Toast.makeText(RuyiHelper.this, "��¼Ϊ��", Toast.LENGTH_SHORT)
						.show();
				break;
			case 13:
				Toast
						.makeText(RuyiHelper.this, "�����������벻��ȷ",
								Toast.LENGTH_SHORT).show();
				break;
//				������Ϣ���� 14 �û������ӱ�ǩ�����ɹ� 15 û�м�¼ʱ�Ĵ��� 20100803 �³�
			case 14:
				progressdialog.dismiss();
				iDetail = true;
				AccountList(accountView);
				break;
			case 15:
				progressdialog.dismiss();
				iDetail = false;
				AccountList(accountView);
			}
		}
	};
	// ������ 7.5 �����޸ģ�����˳����Ĵ��롪���������
	private int iQuitFlag = 0; // �����˳�

	// private boolean iCallOnKeyDownFlag=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// ��ʾ�����ࡱ�б�
		showListView(ID_MORELISTVIEW);

	}

	/**
	 * �б�ѡ��
	 * 
	 * @param int listviewid �б�ID
	 */
	private void showListView(int listviewid) {
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
		// ���������б�
		case ID_RUYIHELPERLISTVIEW:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			showRuyiHelperListView();
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
			break;
		}
	}

	// ������ 7.5 �����޸ģ�����˳����
	/**
	 * �˳����
	 * 
	 * @param keyCode
	 *            ���ذ����ĺ���
	 * @param event
	 *            �¼�
	 * @return
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		PublicMethod.myOutput("--->>NoticePrizesOfLottery key:"
				+ String.valueOf(keyCode));
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
				WhetherQuitDialog iQuitDialog = new WhetherQuitDialog(this,
						this);
				iQuitDialog.show();
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

			}
			break;
		}
		}
		return false;
		// return super.onKeyDown(keyCode, event);
	}

	// ������ 7.5 �����޸ģ�����˳����
	/**
	 * ȡ��
	 */
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

	/**
	 * �����ࡱѡ���б�
	 */
	private void showMoreListView() {

		setContentView(R.layout.ruyihelper_listview);

		// ����Դ
		list = getListForMoreAdapter();

		ListView listview = (ListView) findViewById(R.id.ruyihelper_listview_id);

		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.ruyihelper_listview_icon_item, new String[] { ICON,
						TITLE }, new int[] { R.id.ruyihelper_icon,
						R.id.ruyihelper_icon_text });

		listview.setAdapter(adapter);

		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				String str = text.getText().toString();
				/* ר�ҷ��� */
				if (getString(R.string.zhuanjiafenxi).equals(str)) {
					showListView(ID_EXPERTANALYZE);
				}
				/* �û����� */
				if (getString(R.string.yonghuzhongxin).equals(str)) {
					showListView(ID_USERCENTER);
				}
				/* �������� */
				if (getString(R.string.ruyizhushou).equals(str)) {
					showListView(ID_RUYIHELPERLISTVIEW);
				}
				/* ������Ȩ */
				if (getString(R.string.ruyihelper_authorizing).equals(str)) {
					showInfo(ID_AUTHORIZING);
				}
			}

		};
		listview.setOnItemClickListener(clickListener);

	}

	/**
	 * ��ʾר�ҷ���
	 */
	private void showExpertAnalyzeListView() {
		setContentView(R.layout.expert_analyze_main_layout);
		PublicMethod.myOutput("-----------Analyze!----------------");
		// ������ 7.3 �����޸ģ���Button����ImageButton
		ImageButton returnBtn = new ImageButton(this);
		returnBtn = (ImageButton) findViewById(R.id.expert_analyze_return_id);
		returnBtn.setOnClickListener(new OnClickListener() {

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
		PublicMethod.myOutput("-----------Analyze!----------------");
		// ���õ������
		OnItemClickListener clickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				typeName = (TextView) view
						.findViewById(R.id.expert_analyze_typename_id);
				String str = typeName.getText().toString();
				// ���˫ɫ����ת��˫ɫ�����б��� ������ 7.4 �����޸ģ���ת��ר�ҷ������б�
				if (getString(R.string.shuangseqiufenxi).equals(str)) {
					iQuitFlag = 20; // ������ 7.5 �����޸ģ����������б�
					// iCallOnKeyDownFlag=false;
					// showSubExpertAnalyzeListViewTwo(subExpertAnalyzeTitle ,
					// ID_EXPERTANALYZE_SSQ);
					// �����ȡ˫ɫ��ר�ҷ��������� 2010/7/4 �³�
					// �������Ϊ�� ʱ�Ĵ��� 20100711
					try {
						String[] analysis = expertAnalysis();
						if (analysis[0].equals("06007")
								|| analysis[0].equals("06008")) {
							Toast.makeText(getBaseContext(), "��ʱû��ר�ҷ���",
									Toast.LENGTH_LONG).show();
						} else if (analysis[0].equals("00")) {
							Toast.makeText(getBaseContext(), "�����쳣",
									Toast.LENGTH_LONG).show();
						} else {
							for (int i = 0; i < analysis.length; i++) {
								PublicMethod.myOutput("------------------"
										+ analysis[i]);
							}
							String[] analysis_title = { analysis[0],
									analysis[2], analysis[4] };
							String[] analysis_content = { analysis[1],
									analysis[3], analysis[5] };
							mTitles = analysis_title;
							mDialogue = analysis_content;
							showSubExpertAnalyzeListViewTwo();
						}
					} catch (Exception e) {
						Toast.makeText(getBaseContext(), "��ʱû��ר�ҷ���",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();

						// }
						// showExpertAnalyzeDialog(ID_EXPERTANALYZE_SSQ);
					}
				}
				// �������3D��ת������3D���б��� ������ 7.4 �����޸ģ���ת��ר�ҷ������б�
				if (getString(R.string.fucaifenxi).equals(str)) {
					iQuitFlag = 20; // ������ 7.5 �����޸ģ����������б�
					// iCallOnKeyDownFlag=false;
					// showExpertAnalyzeDialog(ID_EXPERTANALYZE_FC3D);
					try {
						String[] analysis = expertAnalysis();
						if (analysis[0].equals("06007")
								|| analysis[0].equals("06008")) {
							Toast.makeText(getBaseContext(), "��ʱû��ר�ҷ���",
									Toast.LENGTH_LONG).show();
						} else if (analysis[0].equals("00")) {
							Toast.makeText(getBaseContext(), "�����쳣",
									Toast.LENGTH_LONG).show();
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
							showSubExpertAnalyzeListViewTwo();
						}
					} catch (Exception e) {
						Toast.makeText(getBaseContext(), "��ʱû��ר�ҷ���",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				}
				// ������ֲ���ת�����ֲ����б��� ������ 7.4 �����޸ģ���ת��ר�ҷ������б�
				if (getString(R.string.qilecaifenxi).equals(str)) {
					iQuitFlag = 20; // ������ 7.5 �����޸ģ����������б�
					// iCallOnKeyDownFlag=false;
					// showExpertAnalyzeDialog(ID_EXPERTANALYZE_QLC);
					try {
						String[] analysis = expertAnalysis();
						if (analysis[0].equals("06007")
								|| analysis[0].equals("06008")) {
							Toast.makeText(getBaseContext(), "��ʱû��ר�ҷ���",
									Toast.LENGTH_LONG).show();
						} else if (analysis[0].equals("00")) {
							Toast.makeText(getBaseContext(), "�����쳣",
									Toast.LENGTH_LONG).show();
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
							showSubExpertAnalyzeListViewTwo();
						}
					} catch (Exception e) {
						Toast.makeText(getBaseContext(), "��ʱû��ר�ҷ���",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				}
			}

		};

		listview.setOnItemClickListener(clickListener);
	}

	/**
	 * ��ʾ���������б�
	 */
	private void showRuyiHelperListView() {

		setContentView(R.layout.ruyihelper_listview_ruyihelper);

		ListView listview = (ListView) findViewById(R.id.ruyihelper_listview_ruyihelper_id);
		// ���� ������ 7.3 �����޸ģ���Button����ImageButton
		//btnreturn = new ImageButton(this);
		ImageView btnreturn = (ImageView)findViewById(R.id.ruyizhushou_btn_return);
		
		btnreturn.setImageResource(R.drawable.return_btn);
		btnreturn.setBackgroundColor(Color.TRANSPARENT);
		//btnreturn.ssetGravity(Gravity.LEFT);
		btnreturn.setOnClickListener(new ImageView.OnClickListener(){

			public void onClick(View v) {
				showListView(ID_MORELISTVIEW);
			}
			
		});


		//wangyl 7.23  �޸ı���Ͱ�ť����
		
		/*//paramtitle.setMargins(0, 0, 0, 0);
		LinearLayout myLinearLayout = new LinearLayout(this);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT
		);
		//��ӷ���view
		TextView title = new TextView(this);
		title.setText(getString(R.string.ruyizhushou));
		title.setTextColor(Color.BLACK);
		title.setTypeface(Typeface.SERIF);
		title.setTextSize(18);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setTextAppearance(this, android.R.attr.textAppearanceMedium);
		LinearLayout.LayoutParams paramtitle = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		);
		param.setMargins(0, 0, 0, 0);
		//myLinearLayout.setGravity(Gravity.RIGHT);
		myLinearLayout.addView(btnreturn, param);
		myLinearLayout.addView(title,paramtitle);
		
		
		listview.addHeaderView(myLinearLayout);
		registerForContextMenu(listview);*/
		
		// ����Դ
		list = getListForRuyiHelperAdapter();
		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.ruyihelper_listview_icon_item, new String[] { ICON,
						TITLE }, new int[] { R.id.ruyihelper_icon,
						R.id.ruyihelper_icon_text });

		listview.setAdapter(adapter);

		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				String str = text.getText().toString();
				/* ��������֮�淨���� */
				if (getString(R.string.ruyihelper_gameIntroduction).equals(str)) {
					showInfo(ID_GAMEINTRODUTION);
				}
				/* ��������֮�������� */
				if (getString(R.string.ruyihelper_operationAssistant).equals(
						str)) {
					showListView(ID_OPERATIONASSISTANTLISTVIEW);
				}
				/* ��������֮�������� */
				if (getString(R.string.ruyihelper_frequentQuestion).equals(str)) {
					showInfo(ID_FEQUENTQUESTION);
				}
			}

		};
		listview.setOnItemClickListener(clickListener);

	}

	/**
	 * ��ʾ����������Ĳ��������б�
	 */
	private void showOperationAssistantListView() {

		setContentView(R.layout.usercenter_operationhelper_listview);

		List<String> list = new ArrayList<String>();
		list.add(getString(R.string.ruyihelper_userRegister));/* �û�ע�� */
		list.add(getString(R.string.ruyihelper_userLogin));/* �û���¼ */
		list.add(getString(R.string.ruyihelper_userBetting));/* �û�Ͷע */
		list.add(getString(R.string.ruyihelper_presentLottery));/* ���Ͳ�Ʊ */
		list.add(getString(R.string.ruyihelper_accountWithdraw));/* �˺����� */
		list.add(getString(R.string.ruyihelper_balanceInquiry)); /* ����ѯ */

		//wangyl 7.23 �޸ı���ͷ��ز���
		
		ListView listview = (ListView) findViewById(R.id.operation_listview_id);
		
		//����                   ������  7.3 �����޸ģ���Button ����ImageButton
		//btnreturn = new ImageButton(this);
		ImageView btnreturn = (ImageView)findViewById(R.id.operation_btn_return);
		//btnreturn.setImageResource(R.drawable.return_btn);
	//	btnreturn.setBackgroundColor(Color.TRANSPARENT);
		//btnreturn.setGravity(Gravity.LEFT);
		btnreturn.setOnClickListener(new ImageView.OnClickListener(){

			public void onClick(View v) {
				showListView(ID_RUYIHELPERLISTVIEW);
			}
			
		});
		//��ӷ���view
		/*TextView title = new TextView(this);
		title.setText(getString(R.string.ruyihelper_operationAssistant));
		title.setTextColor(Color.BLACK);
		LinearLayout.LayoutParams paramtitle = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		);
		paramtitle.setMargins(50, 0, 50, 0);
		LinearLayout myLinearLayout = new LinearLayout(this);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT
		);
		param.setMargins(50, 0, 1, 0);
		myLinearLayout.setGravity(Gravity.RIGHT);
		myLinearLayout.addView(title,paramtitle);
		myLinearLayout.addView(btnreturn, param);
		LinearLayout myLinearLayout = new LinearLayout(this);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT
		);
		//��ӷ���view
		TextView title = new TextView(this);
		title.setText(getString(R.string.ruyizhushou));
		title.setTextColor(Color.BLACK);
		title.setTypeface(Typeface.SERIF);
		title.setTextSize(18);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setTextAppearance(this, android.R.attr.textAppearanceMedium);
		LinearLayout.LayoutParams paramtitle = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		);
		param.setMargins(0, 0, 0, 0);
		//myLinearLayout.setGravity(Gravity.RIGHT);
		myLinearLayout.addView(btnreturn, param);
		myLinearLayout.addView(title,paramtitle);
		
		listview.addHeaderView(myLinearLayout);
		registerForContextMenu(listview);*/
		
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
		setContentView(R.layout.expert_analyze_specify_listview);
		ListView listview = (ListView) findViewById(R.id.expert_analyze_specify_listview_id);

		ImageButton returnBtn = new ImageButton(this);
		returnBtn = (ImageButton) findViewById(R.id.expert_analyze_specify_return_btn);
		returnBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showListView(ID_EXPERTANALYZE);
			}
		});
		adapterExpert = new SpeechListAdapter(this);
		listview.setAdapter(adapterExpert);
		listview.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// position = subExpertAnalyzeList.size();
				for (int i = 0; i < mTitles.length; i++) {
					if (position == i) {
						// showExpertAnalyzeDialog(expertId);
						adapterExpert.toggle(i);
					}
				}
			}
		});
	}

	// ������ 7.4 ������ӣ�ר�ҷ������б� ������
	/**
	 * ר�ҷ������б� ������
	 */
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
	/**
	 * ר�ҷ������б�����
	 */
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
			mTitle.setPadding(80, 10, 0, 0);
			mTitle.setTextAppearance(context,
					android.R.attr.textAppearanceMedium);
			addView(mTitle, new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			mDialogue = new TextView(context);
			mDialogue.setText(dialogue);
			mDialogue.setTextColor(Color.BLACK);
			addView(mDialogue, new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			mDialogue.setVisibility(expanded ? VISIBLE : GONE);
		}

		public void setTitle(String title) {
			mTitle.setText(title);
		}

		public void setDialogue(String words) {
			mDialogue.setText(words);
		}

		public void setExpanded(boolean expanded) {
			mDialogue.setVisibility(expanded ? VISIBLE : GONE);
		}

		private TextView mTitle;
		private TextView mDialogue;
	}

	/*
	 * //ר�ҷ������б� private void showSubExpertAnalyzeListView(String[]
	 * aExpertAnalyzeInfo , int aExpertId){ expertId = aExpertId;
	 * specifyExpertAnalyzeInfo = aExpertAnalyzeInfo;
	 * setContentView(R.layout.expert_analyze_specify_listview); ListView
	 * listview = (ListView)
	 * findViewById(R.id.expert_analyze_specify_listview_id);
	 * 
	 * ImageButton returnBtn = new ImageButton(this); returnBtn = (ImageButton)
	 * findViewById(R.id.expert_analyze_specify_return_btn);
	 * returnBtn.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub showListView(ID_EXPERTANALYZE); } });
	 * 
	 * subExpertAnalyzeList = new ArrayList<Map<String, Object>>(1);;
	 * if(subExpertAnalyzeList!=null){ for(int
	 * i=0;i<specifyExpertAnalyzeInfo.length;i++){ Map<String, Object> map = new
	 * HashMap<String, Object>(); map.put(SUB_EXPERT_ANALYZE_TITLE,
	 * specifyExpertAnalyzeInfo[i]); subExpertAnalyzeList.add(map); }
	 * 
	 * } //������ SimpleAdapter adapter = new SimpleAdapter(this,
	 * subExpertAnalyzeList, R.layout.expert_analyze_specify_listview_layout,
	 * new String[] { SUB_EXPERT_ANALYZE_TITLE }, new int[] {
	 * R.id.expert_analyze_specify_listview_layout_text_id });
	 * 
	 * listview.setAdapter(adapter);
	 * 
	 * listview.setOnItemClickListener(new ListView.OnItemClickListener(){
	 * 
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) { // TODO Auto-generated method stub //position =
	 * subExpertAnalyzeList.size(); for(int i=0 ;i<subExpertAnalyzeList.size();
	 * i++){ if(position == i){ showExpertAnalyzeDialog(expertId); } } }}); }
	 */
	// ��ʾ�û������б�
	/**
	 * ��ʾ�û������б�
	 */
	private void showUserCenterListView() {

		// ShellRWSharesPreferences shellRW = new
		// ShellRWSharesPreferences(RuyiHelper.this, "addInfo");
		// phonenum = shellRW.getUserLoginInfo("phonenum");
		// sessionid = shellRW.getUserLoginInfo("sessionid");

		setContentView(R.layout.usercenter_listview);

		ListView listview = (ListView) findViewById(R.id.usercenter_listview_id);
		//����                       ������  7.3 �����޸ģ���Button ����ImageButton
		//btnreturn = new ImageButton(this);
		ImageView btnreturn = (ImageView)findViewById(R.id.usercenter_btn_return);
		//btnreturn.setBackgroundResource(R.drawable.return_btn);
		//btnreturn.setImageResource(R.drawable.return_btn);
	//	btnreturn.setBackgroundColor(Color.TRANSPARENT);
		//btnreturn.setMaxWidth(15);
		//btnreturn.setGravity(Gravity.LEFT);
		btnreturn.setOnClickListener(new ImageView.OnClickListener(){

			public void onClick(View v) {
				showListView(ID_MORELISTVIEW);
			}
			
		});
		
		
		//7.23 wangyl �޸Ĳ���
		
		/*//paramtitle.setMargins(0, 0, 0, 0);
		LinearLayout myLinearLayout = new LinearLayout(this);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT
		);
		//��ӷ���view
		TextView title = new TextView(this);
		title.setText(getString(R.string.yonghuzhongxin));
		title.setTextColor(Color.BLACK);
		title.setTypeface(Typeface.SERIF);
		title.setTextSize(18);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setTextAppearance(this, android.R.attr.textAppearanceMedium);
		LinearLayout.LayoutParams paramtitle = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT
		);
		param.setMargins(0, 0, 0, 0);
		//myLinearLayout.setGravity(Gravity.RIGHT);
		myLinearLayout.addView(btnreturn, param);
		myLinearLayout.addView(title,paramtitle);
		
		
		listview.addHeaderView(myLinearLayout);
		registerForContextMenu(listview);*/

		// ����Դ
		list = getListForUserCenterAdapter();
		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.ruyihelper_listview_icon_item, new String[] { ICON,
						TITLE }, new int[] { R.id.ruyihelper_icon,
						R.id.ruyihelper_icon_text });

		listview.setAdapter(adapter);

		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view.findViewById(R.id.ruyihelper_icon_text);
				String str = text.getText().toString();
				iType=0;

				/* �˺����� */

				if (getString(R.string.ruyihelper_accountWithdraw).equals(str)) {
					showUserCenterAccountWithdraw();
				} else {
					ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
							RuyiHelper.this, "addInfo");
					phonenum = shellRW.getUserLoginInfo("phonenum");
					sessionid = shellRW.getUserLoginInfo("sessionid");
					if (sessionid.equals("")) {
						Intent intentSession = new Intent(RuyiHelper.this,
								UserLogin.class);
						// startActivity(intentSession);
						startActivityForResult(intentSession, 0);
					} else {
						/* ����ѯ */

						// if
						// (getString(R.string.ruyihelper_balanceInquiry).equals(str))
						// {
						// ������ 7.5 �����޸ģ���ӵ�½�ж�
						// ShellRWSharesPreferences shellRW = new
						// ShellRWSharesPreferences(RuyiHelper.this, "addInfo");
						// phonenum = shellRW.getUserLoginInfo("phonenum");
						// sessionid = shellRW.getUserLoginInfo("sessionid");
						// if(sessionid.equals("")){
						// Intent intentSession = new Intent(RuyiHelper.this ,
						// UserLogin.class);
						// // startActivity(intentSession);
						// startActivityForResult(intentSession, 0);
						// } else {
						UserCenterDetail();
						// showDialog(DIALOG1_KEY);
						// Thread t = new Thread(new Runnable(){
						// public void run(){
						// String error_code="00";
						// while(iretrytimes<3&&iretrytimes>0){
						//								
						// re=jrtLot.findUserBalance(phonenum, sessionid);
						// try {
						// obj = new JSONObject(re);
						// error_code = obj.getString("error_code");
						// iretrytimes=3;
						// }catch(JSONException e){
						// iretrytimes--;
						// }
						// }
						// iretrytimes=2;
						// if(error_code.equals("0000")){
						// Message msg=new Message();
						// msg.what=0;
						// handler.sendMessage(msg);
						//							    
						// }else if(error_code.equals("070002")){
						// Message msg=new Message();
						// msg.what=7;
						// handler.sendMessage(msg);
						//		        			     
						// }else if(error_code.equals("4444")){
						// Message msg=new Message();
						// msg.what=8;
						// handler.sendMessage(msg);
						//								
						// }else if(error_code.equals("00")){
						// Message msg=new Message();
						// msg.what=9;
						// handler.sendMessage(msg);
						// }else{
						// Message msg=new Message();
						// msg.what=10;
						// handler.sendMessage(msg);
						// }
						// }
						// });
						// t.start();
						// }
						// }
						//					
						//					
						// // }
						// /*�н���ѯ*/
						// if
						// (getString(R.string.usercenter_winningCheck).equals(str))
						// {
						// ShellRWSharesPreferences shellRW = new
						// ShellRWSharesPreferences(RuyiHelper.this, "addInfo");
						// phonenum = shellRW.getUserLoginInfo("phonenum");
						// sessionid = shellRW.getUserLoginInfo("sessionid");
						// //������ 7.4 �����޸ģ���ӵ�½���ж�
						// if(sessionid.equals("")){
						// Intent intentSession = new Intent(RuyiHelper.this ,
						// UserLogin.class);
						// startActivity(intentSession);
						// // startActivityForResult(intentSession, 0);
						// } else {
						// UserCenterDetail();
						// showDialog(DIALOG1_KEY);
						// Thread t = new Thread(new Runnable(){
						// public void run(){
						// String error_code="00";
						// while(iretrytimes<3&&iretrytimes>0){
						// try {
						// String re=jrtLot.winingSelect(phonenum,sessionid);
						// PublicMethod.myOutput("-----------------re:"+re);
						//				    			
						// try
						// {
						// JSONObject obj = new JSONObject(re);
						// error_code=obj.getString("error_code");
						// PublicMethod.myOutput("---------------try error-code"+error_code);
						// }
						// catch(Exception e){
						// jsonArray=new JSONArray(re);
						//
						// JSONObject obj = jsonArray.getJSONObject(0);
						// error_code=obj.getString("error_code");
						// PublicMethod.myOutput("--------------error_code"+error_code);
						// }
						//				    			       
						// iretrytimes=3;
						// } catch (JSONException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// iretrytimes--;
						// }
						// }
						// iretrytimes=2;
						// if(error_code.equals("0000")){
						// Message msg=new Message();
						// msg.what=1;
						// handler.sendMessage(msg);
						//				    			
						// }else if(error_code.equals("070002")){
						// Message msg=new Message();
						// msg.what=7;
						// handler.sendMessage(msg);
						// }else if(error_code.equals("000047")){
						// Message msg=new Message();
						// msg.what=12;
						// handler.sendMessage(msg);
						// }
						// else if(error_code.equals("0047")){
						// Message msg=new Message();
						// msg.what=12;
						// handler.sendMessage(msg);
						// }
						// else if(error_code.equals("00")){
						// Message msg=new Message();
						// msg.what=9;
						// handler.sendMessage(msg);
						// }else{
						// Message msg=new Message();
						// msg.what=10;
						// handler.sendMessage(msg);
						// }
						// }
						//					
						// });
						//				    
						// t.start();
						// }
						//					
						// }

						/* Ͷע��ϸ */
						// if
						// (getString(R.string.usercenter_bettingDetails).equals(str))
						// {
						// ShellRWSharesPreferences shellRW = new
						// ShellRWSharesPreferences(RuyiHelper.this, "addInfo");
						// phonenum = shellRW.getUserLoginInfo("phonenum");
						// sessionid = shellRW.getUserLoginInfo("sessionid");
						// //������ 7.4 �����޸ģ���ӵ�½���ж�
						// if(sessionid.equals("")){
						// Intent intentSession = new Intent(RuyiHelper.this ,
						// UserLogin.class);
						// startActivity(intentSession);
						// // startActivityForResult(intentSession, 0);
						// } else {
						// UserCenterDetail();
						// showDialog(DIALOG1_KEY);
						// Thread t = new Thread(new Runnable(){
						// public void run(){
						// String error_code="00";
						// while(iretrytimes<3&&iretrytimes>0){
						// try {
						// // String
						// re=jrtLot.bettingSelect(phonenum,sessionid);
						// // Ͷע��ѯ�½ӿ� 20100714
						// String re=jrtLot.bettingSelect(sessionid);//phonenum,
						// PublicMethod.myOutput("-----------------re:"+re);
						//				    			
						// try
						// {
						// JSONObject obj = new JSONObject(re);
						// error_code=obj.getString("error_code");
						// PublicMethod.myOutput("---------------try error-code"+error_code);
						// }
						// catch(Exception e){
						// jsonArray=new JSONArray(re);
						// JSONObject obj = jsonArray.getJSONObject(0);
						// error_code=obj.getString("error_code");
						// PublicMethod.myOutput("--------------error_code"+error_code);
						// }
						//				    			       
						// iretrytimes=3;
						// } catch (JSONException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// iretrytimes--;
						// }
						// }
						// iretrytimes=2;
						// if(error_code.equals("000000")){
						// Message msg=new Message();
						// msg.what=2;
						// handler.sendMessage(msg);
						//							  
						// }else if(error_code.equals("070002")){
						// Message msg=new Message();
						// msg.what=7;
						// handler.sendMessage(msg);
						//		        			     
						// }else if(error_code.equals("4444")){
						// Message msg=new Message();
						// msg.what=8;
						// handler.sendMessage(msg);
						//								
						// }else if(error_code.equals("00")){
						// Message msg=new Message();
						// msg.what=9;
						// handler.sendMessage(msg);
						// }else if(error_code.equals("000047")){
						// Message msg=new Message();
						// msg.what=12;
						// handler.sendMessage(msg);
						// }else{
						// Message msg=new Message();
						// msg.what=10;
						// handler.sendMessage(msg);
						// }
						// }
						// });
						// t.start();
						// }
						//	
						// }
						// /*�˺���ϸ*/
						// if
						// (getString(R.string.usercenter_accountDetails).equals(str))
						// {
						// ShellRWSharesPreferences shellRW = new
						// ShellRWSharesPreferences(RuyiHelper.this, "addInfo");
						// phonenum = shellRW.getUserLoginInfo("phonenum");
						// sessionid = shellRW.getUserLoginInfo("sessionid");
						// //������ 7.4 �����޸ģ���ӵ�½���ж�
						// if(sessionid.equals("")){
						// Intent intentSession = new Intent(RuyiHelper.this ,
						// UserLogin.class);
						// startActivity(intentSession);
						// // startActivityForResult(intentSession, 0);
						// } else {
						// UserCenterDetail();
						// showDialog(DIALOG1_KEY);
						// Thread t = new Thread(new Runnable(){
						// public void run(){
						// String error_code="00";
						// while(iretrytimes<3&&iretrytimes>0){
						// try {
						// // ��ȡ��ǰ���� �Լ�ǰһ��������
						// Calendar now = Calendar.getInstance();
						// String monthNow;
						// String dayNow;
						// String startTime="";
						// String endTime="";
						// String monthPre;
						// int monthpre=now.get(Calendar.MONTH);
						// int month=now.get(Calendar.MONTH)+1;
						// int day=now.get(Calendar.DAY_OF_MONTH);
						// if(month<10){
						// monthNow="0"+month;
						// }else{
						// monthNow=""+month;
						// }if(day<10){
						// dayNow="0"+day;
						// }else{
						// dayNow=""+day;
						// }
						// if(monthpre<10){
						// monthPre="0"+monthpre;
						// }else{
						// monthPre=""+monthpre;
						// }
						// if(month==1){
						// startTime=(now.get(Calendar.YEAR)-1)+""+12+dayNow;
						// }else{
						// startTime=now.get(Calendar.YEAR)+""+monthPre+dayNow;
						// }
						// PublicMethod.myOutput("--------------starttime"+startTime);
						// endTime=now.get(Calendar.YEAR)+monthNow+dayNow;
						// PublicMethod.myOutput("--------------endtime"+endTime);
						// // String re=jrtLot.accountDetailSelect(phonenum,
						// startTime, endTime, sessionid);
						// String re=jrtLot.accountDetailSelect(phonenum,
						// startTime, endTime, sessionid);
						// PublicMethod.myOutput("???????????"+startTime+"------"+endTime);
						// //String
						// re=jrtLot.accountDetailSelect(phonenum,"20100608",
						// "20100708", sessionid);
						// PublicMethod.myOutput("-----------------re:"+re);
						//				    			
						// try
						// {
						// JSONObject obj = new JSONObject(re);
						// error_code=obj.getString("error_code");
						// PublicMethod.myOutput("---------------try error-code"+error_code);
						// }
						// catch(Exception e){
						// jsonArray=new JSONArray(re);
						// JSONObject obj = jsonArray.getJSONObject(0);
						// error_code=obj.getString("error_code");
						// // String memo=obj.getString("memo");
						// // Toast.makeText(RuyiHelper.this, memo,
						// Toast.LENGTH_SHORT).show();
						// PublicMethod.myOutput("--------------error_code"+error_code);
						// }
						//				    			    
						// iretrytimes=3;
						// } catch (JSONException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// iretrytimes--;
						// }
						// }
						// iretrytimes=2;
						//				    			
						// if(error_code.equals("0000")){
						// Message msg=new Message();
						// msg.what=3;
						// handler.sendMessage(msg);
						//							  
						// }else if(error_code.equals("070002")){
						// Message msg=new Message();
						// msg.what=7;
						// handler.sendMessage(msg);
						//		        			     
						// }else if(error_code.equals("4444")){
						// Message msg=new Message();
						// msg.what=8;
						// handler.sendMessage(msg);
						//								
						// }else if(error_code.equals("0047")){
						// Message msg=new Message();
						// msg.what=12;
						// handler.sendMessage(msg);
						// }else if(error_code.equals("00")){
						// Message msg=new Message();
						// msg.what=9;
						// handler.sendMessage(msg);
						// }else{
						// Message msg=new Message();
						// msg.what=10;
						// handler.sendMessage(msg);
						// }
						// }
						// });
						// t.start();
						// }
						//	
						// }
						// /*���Ͳ�ѯ*/
						// if
						// (getString(R.string.usercenter_giftCheck).equals(str))
						// {
						// ShellRWSharesPreferences shellRW = new
						// ShellRWSharesPreferences(RuyiHelper.this, "addInfo");
						// phonenum = shellRW.getUserLoginInfo("phonenum");
						// sessionid = shellRW.getUserLoginInfo("sessionid");
						// //������ 7.4 �����޸ģ���ӵ�½���ж�
						// if(sessionid.equals("")){
						// Intent intentSession = new Intent(RuyiHelper.this ,
						// UserLogin.class);
						// startActivity(intentSession);
						// // startActivityForResult(intentSession, 0);
						// } else {
						// UserCenterDetail();
						//	
						// showDialog(DIALOG1_KEY);
						// Thread t = new Thread(new Runnable(){
						// public void run(){
						// // String error_code="00";
						// // String error_code_gifted="00";
						// while(iretrytimes<3&&iretrytimes>0){
						// try {
						// String re=jrtLot.giftSelect(sessionid);
						// String re_gifted=jrtLot.giftedSelect(sessionid);
						// PublicMethod.myOutput("-----------------re:"+re);
						// PublicMethod.myOutput("-----------------re_gifted:"+re_gifted);
						//				    			
						// try
						// {
						// JSONObject obj = new JSONObject(re);
						// JSONObject obj_gifted=new JSONObject(re);
						// error_code_gift=obj.getString("error_code");
						// error_code_gifted=obj_gifted.getString("error_code");
						// PublicMethod.myOutput("---------------try error-code"+error_code);
						// }
						// catch(Exception e){
						// jsonArray=new JSONArray(re);
						// jsonArray_gifted=new JSONArray(re_gifted);
						// JSONObject obj = jsonArray.getJSONObject(0);
						// JSONObject obj_gifted =
						// jsonArray_gifted.getJSONObject(0);
						// error_code_gift=obj.getString("error_code");
						// error_code_gifted=obj_gifted.getString("error_code");
						// PublicMethod.myOutput("--------------error_code"+error_code);
						// }
						//				    			    
						// iretrytimes=3;
						// } catch (JSONException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// iretrytimes--;
						// }
						// }
						// iretrytimes=2;
						// if(error_code_gift.equals("000000")||error_code_gifted.equals("000000")){
						// Message msg=new Message();
						// msg.what=4;
						// handler.sendMessage(msg);
						//								
						// }else
						// if(error_code_gift.equals("070002")||error_code_gifted.equals("0700002")){
						// Message msg=new Message();
						// msg.what=7;
						// handler.sendMessage(msg);
						// }else if(error_code_gift.equals("4444")){
						// Message msg=new Message();
						// msg.what=8;
						// handler.sendMessage(msg);
						//								
						// }else
						// if(error_code_gift.equals("000047")&&error_code_gifted.equals("000047")){
						// Message msg=new Message();
						// msg.what=12;
						// handler.sendMessage(msg);
						// }else
						// if(error_code_gift.equals("00")||error_code_gifted.equals("00")){
						// Message msg=new Message();
						// msg.what=9;
						// handler.sendMessage(msg);
						// }else{
						// Message msg=new Message();
						// msg.what=10;
						// handler.sendMessage(msg);
						// }
						// }
						// });
						// t.start();
						// }
						//	
						// }
						// /*�����޸�*/
						// if
						// (getString(R.string.usercenter_passwordChange).equals(str))
						// {
						// ShellRWSharesPreferences shellRW = new
						// ShellRWSharesPreferences(RuyiHelper.this, "addInfo");
						// phonenum = shellRW.getUserLoginInfo("phonenum");
						// sessionid = shellRW.getUserLoginInfo("sessionid");
						// //������ 7.4 �����޸ģ���ӵ�½���ж�
						// if(sessionid.equals("")){
						// Intent intentSession = new Intent(RuyiHelper.this ,
						// UserLogin.class);
						// //startActivity(intentSession);
						// startActivityForResult(intentSession, 0);
						// } else {
						// UserCenterDetail();
						// // showUserCenterPasswordChange();
						// }
						// }
						/* �˺����� */
						// if
						// (getString(R.string.ruyihelper_accountWithdraw).equals(str))
						// {
						// showDialog(DIALOG1_KEY);
						// String error_code="00";
						// while(iretrytimes<3&&iretrytimes>0){
						// try {
						// String re=jrtLot.accountDetailSelect(phonenum,
						// "20100202", "20100628", sessionid);
						// PublicMethod.myOutput("-----------------re:"+re);
						//		    			
						// try
						// {
						// JSONObject obj = new JSONObject(re);
						// error_code=obj.getString("error_code");
						// PublicMethod.myOutput("---------------try error-code"+error_code);
						// }
						// catch(Exception e){
						// jsonArray=new JSONArray(re);
						// JSONObject obj = jsonArray.getJSONObject(0);
						// error_code=obj.getString("error_code");
						// PublicMethod.myOutput("--------------error_code"+error_code);
						// }
						//		    			    
						// iretrytimes=3;
						// } catch (JSONException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// iretrytimes--;
						// }
						// }
						// iretrytimes=2;
						// if(error_code.equals("000000")){
						// showUserCenterAccountWithdraw();
						// }else if(error_code.equals("070002")){
						// Toast.makeText(RuyiHelper.this, "���¼",
						// Toast.LENGTH_SHORT).show();
						// }else if(error_code.equals("00")){
						// Toast.makeText(RuyiHelper.this, "�����쳣",
						// Toast.LENGTH_SHORT).show();
						// }else{
						// Toast.makeText(RuyiHelper.this, "��ѯʧ��",
						// Toast.LENGTH_SHORT).show();
						// }
						// }
						// /*׷�Ų�ѯ*/
						// if
						// (getString(R.string.usercenter_trackNumberInquiry).equals(str))
						// {
						// ShellRWSharesPreferences shellRW = new
						// ShellRWSharesPreferences(RuyiHelper.this, "addInfo");
						// phonenum = shellRW.getUserLoginInfo("phonenum");
						// sessionid = shellRW.getUserLoginInfo("sessionid");
						// //������ 7.4 �����޸ģ���ӵ�½���ж�
						// if(sessionid.equals("")){
						// Intent intentSession = new Intent(RuyiHelper.this ,
						// UserLogin.class);
						// startActivity(intentSession);
						// // startActivityForResult(intentSession,0);
						// } else {
						// UserCenterDetail();
						// ������ 7.5 �����޸ģ�����û�����--׷�Ų�ѯ ���� �³�2010/7/5
						// showDialog(DIALOG1_KEY);
						// Thread t = new Thread(new Runnable(){
						// public void run(){
						// String error_code="00";
						// while(iretrytimes<3&&iretrytimes>0){
						// try {
						// // String re=jrtLot.giftSelect(sessionid);
						// String re=jrtLot.addNumQuery(sessionid);
						// PublicMethod.myOutput("-----------------re:"+re);
						//					    			
						// try
						// {
						// JSONObject obj = new JSONObject(re);
						// error_code=obj.getString("error_code");
						// PublicMethod.myOutput("---------------try error-code"+error_code);
						// }
						// catch(Exception e){
						// jsonArray=new JSONArray(re);
						// JSONObject obj = jsonArray.getJSONObject(0);
						// error_code=obj.getString("error_code");
						// PublicMethod.myOutput("--------------error_code"+error_code);
						// }
						//					    			    
						// iretrytimes=3;
						// } catch (JSONException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// iretrytimes--;
						// }
						// }
						// iretrytimes=2;
						// if(error_code.equals("000000")){
						// Message msg=new Message();
						// msg.what=6;
						// handler.sendMessage(msg);
						//									
						// }else if(error_code.equals("070002")){
						// Message msg=new Message();
						// msg.what=7;
						// handler.sendMessage(msg);
						// }else if(error_code.equals("4444")){
						// Message msg=new Message();
						// msg.what=8;
						// handler.sendMessage(msg);
						//									
						// }else if(error_code.equals("000047")){
						// Message msg=new Message();
						// msg.what=12;
						// handler.sendMessage(msg);
						// }else if(error_code.equals("00")){
						// Message msg=new Message();
						// msg.what=9;
						// handler.sendMessage(msg);
						// }else{
						// Message msg=new Message();
						// msg.what=10;
						// handler.sendMessage(msg);
						// }
						// }
						// });
						// t.start();
						// }
						// }
					}
				}

			}

		};

		listview.setOnItemClickListener(clickListener);

	}

	// ��ÿ���������������
	private void UserCenterDetail() {
		String str = text.getText().toString();
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		phonenum = shellRW.getUserLoginInfo("phonenum");
		sessionid = shellRW.getUserLoginInfo("sessionid");

		if (getString(R.string.ruyihelper_balanceInquiry).equals(str)) {
			showDialog(DIALOG1_KEY);
			Thread t = new Thread(new Runnable() {
				public void run() {
					String error_code = "00";
					while (iretrytimes < 3 && iretrytimes > 0) {

						re = jrtLot.findUserBalance(phonenum, sessionid);
						try {
							obj = new JSONObject(re);
							error_code = obj.getString("error_code");
							iretrytimes = 3;
						} catch (JSONException e) {
							iretrytimes--;
						}
					}
					iretrytimes = 2;
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
		if (getString(R.string.usercenter_winningCheck).equals(str)) {
			showDialog(DIALOG1_KEY);
			Thread t = new Thread(new Runnable() {
				public void run() {
					String error_code = "00";
					while (iretrytimes < 3 && iretrytimes > 0) {
						try {
							String re = jrtLot
									.winingSelect(phonenum, sessionid);
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
								PublicMethod
										.myOutput("--------------error_code"
												+ error_code);
							}

							iretrytimes = 3;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							iretrytimes--;
						}
					}
					iretrytimes = 2;
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
		if (getString(R.string.usercenter_bettingDetails).equals(str)) {
			showDialog(DIALOG1_KEY);
			Thread t = new Thread(new Runnable() {
				public void run() {
					String error_code = "00";
					while (iretrytimes < 3 && iretrytimes > 0) {
						try {
							// String
							// re=jrtLot.bettingSelect(phonenum,sessionid);
							// Ͷע��ѯ�½ӿ� 20100714
							String re = jrtLot.bettingSelect(sessionid);// phonenum,
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
								PublicMethod
										.myOutput("--------------error_code"
												+ error_code);
							}

							iretrytimes = 3;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							iretrytimes--;
						}
					}
					iretrytimes = 2;
					if (error_code.equals("000000")) {
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
					} else if (error_code.equals("000047")) {
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
		if (getString(R.string.usercenter_accountDetails).equals(str)) {
			showDialog(DIALOG1_KEY);
			type = "0";
			Thread t = new Thread(new Runnable() {
				public void run() {
					String error_code = "00";
					while (iretrytimes < 3 && iretrytimes > 0) {
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
								startTime = (now.get(Calendar.YEAR) - 1) + ""
										+ 12 + dayNow;
							} else {
								startTime = now.get(Calendar.YEAR) + ""
										+ monthPre + dayNow;
							}
							PublicMethod.myOutput("--------------starttime"
									+ startTime);
							endTime = now.get(Calendar.YEAR) + monthNow
									+ dayNow;
							PublicMethod.myOutput("--------------endtime"
									+ endTime);
							// String re=jrtLot.accountDetailSelect(phonenum,
							// startTime, endTime, sessionid);
							String re = jrtLot.accountDetailSelect(phonenum,
									startTime, endTime, type,sessionid);
							PublicMethod.myOutput("???????????" + startTime
									+ "------" + endTime);
							// String
							// re=jrtLot.accountDetailSelect(phonenum,"20100608",
							// "20100708", sessionid);
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

							iretrytimes = 3;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							iretrytimes--;
						}
					}
					iretrytimes = 2;

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
		if (getString(R.string.usercenter_giftCheck).equals(str)) {
			showDialog(DIALOG1_KEY);
			Thread t = new Thread(new Runnable() {
				public void run() {
					// String error_code="00";
					// String error_code_gifted="00";
					while (iretrytimes < 3 && iretrytimes > 0) {
						try {
							String re = jrtLot.giftSelect(sessionid);
							String re_gifted = jrtLot.giftedSelect(sessionid);
							PublicMethod.myOutput("-----------------re:" + re);
							PublicMethod.myOutput("-----------------re_gifted:"
									+ re_gifted);

							try {
								JSONObject obj = new JSONObject(re);
								JSONObject obj_gifted = new JSONObject(re);
								error_code_gift = obj.getString("error_code");
								error_code_gifted = obj_gifted
										.getString("error_code");
								PublicMethod
										.myOutput("---------------try error-code"
												+ error_code);
							} catch (Exception e) {
								jsonArray = new JSONArray(re);
								jsonArray_gifted = new JSONArray(re_gifted);
								JSONObject obj = jsonArray.getJSONObject(0);
								JSONObject obj_gifted = jsonArray_gifted
										.getJSONObject(0);
								error_code_gift = obj.getString("error_code");
								error_code_gifted = obj_gifted
										.getString("error_code");
								PublicMethod
										.myOutput("--------------error_code"
												+ error_code);
							}

							iretrytimes = 3;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							iretrytimes--;
						}
					}
					iretrytimes = 2;
					if (error_code_gift.equals("000000")
							|| error_code_gifted.equals("000000")) {
						Message msg = new Message();
						msg.what = 4;
						handler.sendMessage(msg);

					} else if (error_code_gift.equals("070002")
							|| error_code_gifted.equals("0700002")) {
						Message msg = new Message();
						msg.what = 7;
						handler.sendMessage(msg);
					} else if (error_code_gift.equals("4444")) {
						Message msg = new Message();
						msg.what = 8;
						handler.sendMessage(msg);

					} else if (error_code_gift.equals("000047")
							&& error_code_gifted.equals("000047")) {
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
		if (getString(R.string.usercenter_passwordChange).equals(str)) {
			showUserCenterPasswordChange();
		}
		if (getString(R.string.usercenter_trackNumberInquiry).equals(str)) {
			showDialog(DIALOG1_KEY);
			Thread t = new Thread(new Runnable() {
				public void run() {
					String error_code = "00";
					while (iretrytimes < 3 && iretrytimes > 0) {
						try {
							// String re=jrtLot.giftSelect(sessionid);
							String re = jrtLot.addNumQuery(sessionid);
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
								PublicMethod
										.myOutput("--------------error_code"
												+ error_code);
							}

							iretrytimes = 3;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							iretrytimes--;
						}
					}
					iretrytimes = 2;
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

					} else if (error_code.equals("000047")) {
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

		// }
	}

	/**
	 * ��ʾ�淨���ܡ��������⡢������Ȩ
	 * 
	 * @param aInfoFlag
	 */
	private void showInfo(int aInfoFlag) {
		String iFileName = null;
		switch (aInfoFlag) {
		/* �淨���� */
		case ID_GAMEINTRODUTION:
			iQuitFlag = 40; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			// iFileName="ruyihelper_gameIntroduction.html";
			iFileName = "ruyihelper_gameIntroduction.html";
			break;
		/* �������� */
		case ID_FEQUENTQUESTION:
			iQuitFlag = 40; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			iFileName = "ruyihelper_frequentQuestion.html";
			break;
		/* ������Ȩ */
		case ID_AUTHORIZING:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			iFileName = "ruyihelper_authorizing.html";
			break;
		}

		WebView webView = new WebView(this);
		String url = "file:///android_asset/" + iFileName;
		webView.loadUrl(url);

		// ���� ������ 7.3 �����޸ģ���Button ����ImageButton
		btnreturn = new ImageButton(this);
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
		RelativeLayout myRelativeLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		myRelativeLayout.addView(btnreturn, param);
		myRelativeLayout.setGravity(Gravity.RIGHT);
		// param.setMargins(260, 0, 0, 0);

		webView.addView(myRelativeLayout);
		setContentView(webView);

	}

	/**
	 * (˫ɫ�򡢸���3D�����ֲ�)ר�ҷ���
	 * 
	 * @param aInfoFlag
	 *            ���
	 */
	private void showExpertAnalyzeDialog(int aInfoFlag) {
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

	/**
	 * ����������Ի������ʾ
	 * 
	 * @param aInfoFlag
	 */
	private void showListViewDialog(int aInfoFlag) {
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

	/**
	 * �û�����-����ѯ
	 */
	public void showUserCenterBalanceInquiry() {
		String deposit_amount = "";
		String Valid_amount = "";
		String freeze_amout = "";
		String totalBalance = "";
		// �����ȡ��Ϣ
		// UserCenter usercenter=new UserCenter();
		// String error_code=usercenter.findUserBalance(phonenum, sessionid);
		// if(error_code.equals("0000")){
		// deposit_amount=usercenter.deposit_amount;
		// Valid_amount=usercenter.Valid_amount;
		try {
			// 20100710 cc �޸�����ʽ
			deposit_amount = PublicMethod.changeMoney(obj
					.getString("deposit_amount"));
			Valid_amount = PublicMethod.changeMoney(obj
					.getString("Valid_amount"));
			freeze_amout = PublicMethod.changeMoney(obj
					.getString("freeze_amout"));
			totalBalance = PublicMethod.changeMoney((Integer.parseInt(obj
					.getString("deposit_amount")) + Integer.parseInt(obj
					.getString("freeze_amout")))
					+ "");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		TextView textview = new TextView(this);
		textview.setText(getString(R.string.total_balance) + totalBalance
				+ getString(R.string.unit) + "\n"
				+ getString(R.string.freeze_amout) + freeze_amout
				+ getString(R.string.unit) + "\n"
				+ getString(R.string.usercenter_currentBalance) + deposit_amount
				+ getString(R.string.unit) + "\n"
				+ getString(R.string.usercenter_withdrawBalance)
				+ Valid_amount + getString(R.string.unit) + "\n"
				+ getString(R.string.usercenter_textDisplay) + "\n"
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

	/**
	 * �û�����-���Ͳ�ѯ
	 */
	private void showUserCenterGiftCheck() {
		/*
		 * iQuitFlag = 30; //������ 7.5 �����޸ģ����������б� iCallOnKeyDownFlag=false;
		 */
		bettingindex = 0;
		showgiftdetailview(bettingindex);
		showgifteddetail(bettingindex);
		setContentView(R.layout.usercenter_giftcheck);

		final TextView presentedView = (TextView) findViewById(R.id.usercenter_giftcheck_presentedlottery_body);
		final TextView receivedView = (TextView) findViewById(R.id.usercenter_giftcheck_receivedlottery_body);
		presentedView
				.setText(getString(R.string.usercenter_giftCheck_presentedNumber)
						+ to_mobile_code
						+ "\n"
						+ getString(R.string.usercenter_giftCheck_drawDate)
						+ term_begin_datetime
						+ "\n"
						+ getString(R.string.usercenter_giftCheck_giftAmount)
						+ amount
						+ "\n"
						+ getString(R.string.usercenter_giftCheck_lotteryIssue)
						+ sell_term_code
						+ "\n"
						+ getString(R.string.usercenter_giftCheck_lotteryCategory)
						+ play_name
						+ "\n"
						+ getString(R.string.usercenter_giftCheck_lotteryBets)
						+ "\n" + betcode + "\n");
		receivedView
				.setText(getString(R.string.usercenter_giftCheck_friendNumber)
						+ from_mobile_code
						+ "\n"
						+ getString(R.string.usercenter_giftCheck_drawDate)
						+ term_begin_datetime_gifted
						+ "\n"
						+ getString(R.string.usercenter_giftCheck_lotteryAmount)
						+ amount_gifted
						+ "\n"
						+ getString(R.string.usercenter_giftCheck_lotteryIssue)
						+ sell_term_code_gifted
						+ "\n"
						+ getString(R.string.usercenter_giftCheck_lotteryCategory)
						+ play_name_gifted + "\n"
						+ getString(R.string.usercenter_giftCheck_lotteryBets)
						+ "\n" + betcode_gifted + "\n");
		Button b_uppage_gift = (Button) findViewById(R.id.usercenter_giftcheck_presentedlottery_upPage);
		b_uppage_gift.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (bettingindex > 0) {
					bettingindex = bettingindex - 1;
				}

				showgiftdetailview(bettingindex);
				presentedView.setText("");
				presentedView
						.setText(getString(R.string.usercenter_giftCheck_presentedNumber)
								+ to_mobile_code
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_drawDate)
								+ term_begin_datetime
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_giftAmount)
								+ amount
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryIssue)
								+ sell_term_code
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryCategory)
								+ play_name
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryBets)
								+ "\n" + betcode + "\n");
			}
		});
		Button b_downpage_gift = (Button) findViewById(R.id.usercenter_giftcheck_presentedlottery_downPage);
		b_downpage_gift.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (bettingindex < jsonArray.length() - 1) {
					bettingindex += 1;
				}
				showgiftdetailview(bettingindex);
				presentedView.setText("");

				presentedView
						.setText(getString(R.string.usercenter_giftCheck_presentedNumber)
								+ to_mobile_code
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_drawDate)
								+ term_begin_datetime
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_giftAmount)
								+ amount
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryIssue)
								+ sell_term_code
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryCategory)
								+ play_name
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryBets)
								+ "\n" + betcode + "\n");
			}
		});
		Button b_uppage_gifted = (Button) findViewById(R.id.usercenter_giftcheck_receivedlottery_upPage);
		b_uppage_gifted.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bettingindex > 0) {
					bettingindex = bettingindex - 1;
				}
				showgifteddetail(bettingindex);
				receivedView.setText("");
				receivedView
						.setText(getString(R.string.usercenter_giftCheck_friendNumber)
								+ from_mobile_code
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_drawDate)
								+ term_begin_datetime_gifted
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryAmount)
								+ amount_gifted
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryIssue)
								+ sell_term_code_gifted
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryCategory)
								+ play_name_gifted
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryBets)
								+ "\n" + betcode_gifted + "\n");
			}
		});
		Button b_downpage_gifted = (Button) findViewById(R.id.usercenter_giftcheck_receivedlottery_downPage);
		b_downpage_gifted.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bettingindex < jsonArray_gifted.length() - 1) {
					bettingindex += 1;
				}
				showgifteddetail(bettingindex);
				receivedView.setText("");
				receivedView
						.setText(getString(R.string.usercenter_giftCheck_friendNumber)
								+ from_mobile_code
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_drawDate)
								+ term_begin_datetime_gifted
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryAmount)
								+ amount_gifted
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryIssue)
								+ sell_term_code_gifted
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryCategory)
								+ play_name_gifted
								+ "\n"
								+ getString(R.string.usercenter_giftCheck_lotteryBets)
								+ "\n" + betcode_gifted + "\n");
			}
		});

		// ������ 7.8 �����޸ģ���Button����ImageButton
		ImageButton btn = (ImageButton) findViewById(R.id.usercenter_giftcheck_return);
		btn.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				showListView(ID_USERCENTER);
			}

		});
	}

	// ������ 7.9 �û����� Ͷע��ϸ �����ĶԻ���
	/**
	 * �û�����-Ͷע��ϸ
	 */
	private void showUserCenterBettingDetails() {
		bettingindex = 0;
		bettingselect(bettingindex);

		/*
		 * touzhuStr[0]=play_name; touzhuStr[1]=batchcode; touzhuStr[2]="";
		 * touzhuStr[3]=betcode; touzhuStr[4]=""; touzhuStr[5]=beishu;
		 * touzhuStr[6]=sell_datetime; touzhuStr[7]=""; touzhuStr[8]="";
		 * touzhuStr[9]="";
		 * 
		 * UserCenterBettingDetailsDialog ibettingDialog = new
		 * UserCenterBettingDetailsDialog(this,touzhuStr);
		 * ibettingDialog.show();
		 */
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.usercenter_bettingdetails, null);
		final TextView textview = (TextView) view
				.findViewById(R.id.usercenter_bettingDetails_id);
		textview.setText(getString(R.string.usercenter_bettingDetails_type)
				+ play_name
				+ "\n"// "˫ɫ��ʽ��ѡ"
				+ getString(R.string.usercenter_bettingDetails_issue)
				+ batchcode
				+ "\n"// "20100607"
				// +getString(R.string.usercenter_bettingDetails_pay)+"\n"
				+ getString(R.string.usercenter_bettingDetails_noteCode)
				+ "\n"
				+ betcode// +"\n"
				// +getString(R.string.usercenter_bettingDetails_noteNumber)+"\n"
				// +getString(R.string.usercenter_bettingDetails_multiple)+beishu+"\n"
				+ getString(R.string.usercenter_bettingDetails_bettingTime)
				+ sell_datetime + "\n"
		// +getString(R.string.usercenter_bettingDetails_drawDate)+"\n"
				// +getString(R.string.usercenter_bettingDetails_bet)+"\n"
				// +getString(R.string.usercenter_bettingDetails_prizeMoney)

				);
		Button b_uppage = (Button) view
				.findViewById(R.id.usercenter_btn_uppage);
		b_uppage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (bettingindex > 0) {
					bettingindex = bettingindex - 1;
				}

				bettingselect(bettingindex);
				// TODO Auto-generated method stub
				textview.setText("");
				textview
						.setText(getString(R.string.usercenter_bettingDetails_type)
								+ play_name
								+ "\n"// "˫ɫ��ʽ��ѡ"
								+ getString(R.string.usercenter_bettingDetails_issue)
								+ batchcode
								+ "\n"// "20100607"
								// +getString(R.string.usercenter_bettingDetails_pay)+"\n"
								+ getString(R.string.usercenter_bettingDetails_noteCode)
								+ "\n"
								+ betcode
								+ "\n"
								// +getString(R.string.usercenter_bettingDetails_noteNumber)+"\n"
								// +getString(R.string.usercenter_bettingDetails_multiple)+beishu+"\n"
								+ getString(R.string.usercenter_bettingDetails_bettingTime)
								+ sell_datetime + "\n"
						// +getString(R.string.usercenter_bettingDetails_drawDate)+"\n"
						// +getString(R.string.usercenter_bettingDetails_bet)+"\n"
						// +getString(R.string.usercenter_bettingDetails_prizeMoney)
						);
			}

		});

		Button b_downpage = (Button) view
				.findViewById(R.id.usercenter_btn_downpage);
		b_downpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bettingindex < jsonArray.length() - 1) {
					bettingindex += 1;
				}
				bettingselect(bettingindex);
				// TODO Auto-generated method stub
				textview.setText("");
				textview
						.setText(getString(R.string.usercenter_bettingDetails_type)
								+ play_name
								+ "\n"// "˫ɫ��ʽ��ѡ"
								+ getString(R.string.usercenter_bettingDetails_issue)
								+ batchcode
								+ "\n"// "20100607"
								// +getString(R.string.usercenter_bettingDetails_pay)+"\n"
								+ getString(R.string.usercenter_bettingDetails_noteCode)
								+ "\n"
								+ betcode
								+ "\n"
								// +getString(R.string.usercenter_bettingDetails_noteNumber)+"\n"
								// +getString(R.string.usercenter_bettingDetails_multiple)+beishu+"\n"
								+ getString(R.string.usercenter_bettingDetails_bettingTime)
								+ sell_datetime + "\n"
						// +getString(R.string.usercenter_bettingDetails_drawDate)+"\n"
						// +getString(R.string.usercenter_bettingDetails_bet)+"\n"
						// +getString(R.string.usercenter_bettingDetails_prizeMoney)
						);
			}

		});

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.usercenter_bettingDetails);
		builder.setView(view);
		builder.setNegativeButton("����", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}

		});
		builder.show();

	}

	// ������ 7.9 �û����� Ͷע��ϸ �����ĶԻ���
	// ����ע�ͱ���
	/**
	 * �û����� Ͷע��ϸ �����ĶԻ���
	 * 
	 * @author Administrator
	 * 
	 */
	/*
	 * public class UserCenterBettingDetailsDialog extends Dialog implements
	 * OnClickListener{
	 * 
	 * private Button upButton; private Button downButton; private Button
	 * returnButton; private TextView textView; public String[] touzhuString;
	 * 
	 * public UserCenterBettingDetailsDialog(Context context) { super(context);
	 * } public UserCenterBettingDetailsDialog(Context context,String [] str) {
	 * super(context); this.touzhuString = str; }
	 * 
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * setContentView(R.layout.usercenter_bettingdetails);
	 * 
	 * upButton = (Button) findViewById(R.id.usercenter_btn_uppage); downButton
	 * = (Button)findViewById(R.id.usercenter_btn_downpage); returnButton =
	 * (Button) findViewById(R.id.usercenter_btn_return); textView =
	 * (TextView)findViewById(R.id.usercenter_bettingDetails_id);
	 * textView.setText
	 * (getString(R.string.usercenter_bettingDetails_type)+touzhuString
	 * [0]+"\n"//"˫ɫ��ʽ��ѡ"
	 * +getString(R.string.usercenter_bettingDetails_issue)+touzhuString
	 * [1]+"\n"//"20100607" //
	 * +getString(R.string.usercenter_bettingDetails_pay)+"\n"
	 * +getString(R.string
	 * .usercenter_bettingDetails_noteCode)+"\n"+touzhuString[3]//+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_noteNumber)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_multiple)+beishu+"\n"
	 * +getString
	 * (R.string.usercenter_bettingDetails_bettingTime)+touzhuString[6]+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_drawDate)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_bet)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_prizeMoney)
	 * 
	 * ); upButton.setOnClickListener(this);
	 * downButton.setOnClickListener(this);
	 * returnButton.setOnClickListener(this);
	 * setTitle(R.string.usercenter_bettingDetails); }
	 * 
	 * public void onClick(View v) { switch (v.getId()) { case
	 * R.id.usercenter_btn_uppage: if(bettingindex>0){
	 * bettingindex=bettingindex-1; }
	 * 
	 * bettingselect(bettingindex); touzhuStr[0]=play_name;
	 * touzhuStr[1]=batchcode; touzhuStr[2]=""; touzhuStr[3]=betcode;
	 * touzhuStr[4]=""; touzhuStr[5]=beishu; touzhuStr[6]=sell_datetime;
	 * touzhuStr[7]=""; touzhuStr[8]=""; touzhuStr[9]="";
	 * textView.setText(getString
	 * (R.string.usercenter_bettingDetails_type)+touzhuString[0]+"\n"//"˫ɫ��ʽ��ѡ"
	 * +
	 * getString(R.string.usercenter_bettingDetails_issue)+touzhuString[1]+"\n"/
	 * /"20100607" // +getString(R.string.usercenter_bettingDetails_pay)+"\n"
	 * +getString
	 * (R.string.usercenter_bettingDetails_noteCode)+"\n"+touzhuString[3]//+"\n"
	 * // +getString(R.string.usercenter_bettingDetails_noteNumber)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_multiple)+beishu+"\n"
	 * +getString
	 * (R.string.usercenter_bettingDetails_bettingTime)+touzhuString[6]+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_drawDate)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_bet)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_prizeMoney)
	 * 
	 * ); break; case R.id.usercenter_btn_downpage:
	 * if(bettingindex<jsonArray.length()-1){ bettingindex+=1; }
	 * bettingselect(bettingindex); touzhuStr[0]=play_name;
	 * touzhuStr[1]=batchcode; touzhuStr[2]=""; touzhuStr[3]=betcode;
	 * touzhuStr[4]=""; touzhuStr[5]=beishu; touzhuStr[6]=sell_datetime;
	 * touzhuStr[7]=""; touzhuStr[8]=""; touzhuStr[9]="";
	 * textView.setText(getString
	 * (R.string.usercenter_bettingDetails_type)+touzhuString[0]+"\n"//"˫ɫ��ʽ��ѡ"
	 * +
	 * getString(R.string.usercenter_bettingDetails_issue)+touzhuString[1]+"\n"/
	 * /"20100607" // +getString(R.string.usercenter_bettingDetails_pay)+"\n"
	 * +getString
	 * (R.string.usercenter_bettingDetails_noteCode)+"\n"+touzhuString[3]//+"\n"
	 * // +getString(R.string.usercenter_bettingDetails_noteNumber)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_multiple)+beishu+"\n"
	 * +getString
	 * (R.string.usercenter_bettingDetails_bettingTime)+touzhuString[6]+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_drawDate)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_bet)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_prizeMoney)
	 * 
	 * ); break; case R.id.usercenter_btn_return: this.dismiss(); break; } } }
	 */

	// wangyl 7.13 ����ע�Ϳ�ɾ��
	// -------------------------------------------------------------------------------
	/**
	 * �û�����-Ͷע��ϸ��һҳ
	 */
	/*
	 * public void upPage(){
	 * 
	 * LayoutInflater inflater = LayoutInflater.from(this); View view =
	 * inflater.inflate(R.layout.usercenter_bettingdetails, null); final
	 * TextView textview =
	 * (TextView)view.findViewById(R.id.usercenter_bettingDetails_id);
	 * 
	 * if(bettingindex>0){ bettingindex=bettingindex-1; }
	 * 
	 * bettingselect(bettingindex); touzhuStr[0]=play_name;
	 * touzhuStr[1]=batchcode; touzhuStr[2]=""; touzhuStr[3]=betcode;
	 * touzhuStr[4]=""; touzhuStr[5]=beishu; touzhuStr[6]=sell_datetime;
	 * touzhuStr[7]=""; touzhuStr[8]=""; touzhuStr[9]=""; for(int
	 * i=0;i<touzhuStr.length;i++){
	 * PublicMethod.myOutput("----------touzhuStr["+
	 * i+"]------up----------"+touzhuStr[i]); } //
	 * UserCenterBettingDetailsDialog ibettingDialog1 = ibettingDialog;
	 * ibettingDialog.touzhuString=touzhuStr; ibettingDialog.show();
	 * textview.setText("");
	 * textview.setText(getString(R.string.usercenter_bettingDetails_type
	 * )+play_name+"\n"//"˫ɫ��ʽ��ѡ"
	 * +getString(R.string.usercenter_bettingDetails_issue
	 * )+batchcode+"\n"//"20100607" //
	 * +getString(R.string.usercenter_bettingDetails_pay)+"\n"
	 * +getString(R.string.usercenter_bettingDetails_noteCode)+"\n"+betcode+"\n"
	 * // +getString(R.string.usercenter_bettingDetails_noteNumber)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_multiple)+beishu+"\n"
	 * +getString
	 * (R.string.usercenter_bettingDetails_bettingTime)+sell_datetime+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_drawDate)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_bet)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_prizeMoney) );
	 * UserCenterBettingDetailsDialog ibettingDialog = new
	 * UserCenterBettingDetailsDialog(this,this,textview);
	 * ibettingDialog.show();
	 * 
	 * }
	 */
	// ������ 7.9 �û����� Ͷע��ϸ �����ĶԻ���
	// ����ע��ɾ��
	/**
	 * �û�����-Ͷע��ϸ��һҳ
	 */
	/*
	 * public void downPage(){
	 * 
	 * LayoutInflater inflater = LayoutInflater.from(this); View view =
	 * inflater.inflate(R.layout.usercenter_bettingdetails, null); final
	 * TextView textview =
	 * (TextView)view.findViewById(R.id.usercenter_bettingDetails_id);
	 * 
	 * // TODO Auto-generated method stub if(bettingindex<jsonArray.length()-1){
	 * bettingindex+=1; } bettingselect(bettingindex); touzhuStr[0]=play_name;
	 * touzhuStr[1]=batchcode; touzhuStr[2]=""; touzhuStr[3]=betcode;
	 * touzhuStr[4]=""; touzhuStr[5]=beishu; touzhuStr[6]=sell_datetime;
	 * touzhuStr[7]=""; touzhuStr[8]=""; touzhuStr[9]="";
	 * 
	 * for(int i=0;i<touzhuStr.length;i++){
	 * PublicMethod.myOutput("----------touzhuStr["
	 * +i+"]------down----------"+touzhuStr[i]); }
	 * 
	 * // UserCenterBettingDetailsDialog ibettingDialog2 = ibettingDialog;
	 * ibettingDialog.touzhuString=touzhuStr; ibettingDialog.show(); // TODO
	 * Auto-generated method stub textview.setText("");
	 * textview.setText(getString
	 * (R.string.usercenter_bettingDetails_type)+play_name+"\n"//"˫ɫ��ʽ��ѡ"
	 * +getString
	 * (R.string.usercenter_bettingDetails_issue)+batchcode+"\n"//"20100607" //
	 * +getString(R.string.usercenter_bettingDetails_pay)+"\n"
	 * +getString(R.string.usercenter_bettingDetails_noteCode)+"\n"+betcode+"\n"
	 * // +getString(R.string.usercenter_bettingDetails_noteNumber)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_multiple)+beishu+"\n"
	 * +getString
	 * (R.string.usercenter_bettingDetails_bettingTime)+sell_datetime+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_drawDate)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_bet)+"\n" //
	 * +getString(R.string.usercenter_bettingDetails_prizeMoney) );
	 * UserCenterBettingDetailsDialog ibettingDialog = new
	 * UserCenterBettingDetailsDialog(this,this,textview);
	 * ibettingDialog.show();
	 * 
	 * }
	 */
	// -------------------------------------------------------------------------------

	// ������ 7.5 �����޸ģ����׷�Ų�ѯ
	/**
	 * �û�����--׷�Ų�ѯ
	 */
	private void showUserCenterAddLotteryQuery() {
		bettingindex = 0;
		showAddNum(bettingindex);
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.usercenter_add_lottery_query,
				null);
		final TextView textview = (TextView) view
				.findViewById(R.id.usercenter_add_lottery_query_text_id);
		textview
				.setText(getString(R.string.usercenter_add_lottery_query_lotterytypename)
						+ play_name
						+ "\n"
						+ getString(R.string.usercenter_add_lottery_query_notecode)
						+ "\n"
						+ betcode
						+ getString(R.string.usercenter_add_lottery_query_notenum)
						+ betNum
						+ "\n"
						+ getString(R.string.usercenter_add_lottery_query_addqishu)
						+ batchNum
						+ "\n"
						+ getString(R.string.usercenter_add_lottery_query_recentaddqishu)
						+ (addednum == null ? "" : addednum)
						+ "\n"
						+ getString(R.string.usercenter_add_lottery_query_recentmoneytotal)
						+ (addedamount == null ? "" : addedamount)
						+ "\n"
						+ getString(R.string.usercenter_add_lottery_query_startqishu)
						+ beginBatch
						+ "\n"
						+ getString(R.string.usercenter_add_lottery_query_lastqishu)
						+ (lastBatch == null ? "" : lastBatch));

		Button b_uppage = (Button) view
				.findViewById(R.id.usercenter_add_lottery_query_btn_uppage);
		b_uppage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (bettingindex > 0) {
					bettingindex = bettingindex - 1;
				}
				showAddNum(bettingindex);
				textview.setText("");
				textview
						.setText(getString(R.string.usercenter_add_lottery_query_lotterytypename)
								+ play_name
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_notecode)
								+ "\n"
								+ betcode//
								+ getString(R.string.usercenter_add_lottery_query_notenum)
								+ betNum
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_addqishu)
								+ batchNum
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_recentaddqishu)
								+ (addednum == null ? "" : addednum)
								+ ""
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_recentmoneytotal)
								+ (addedamount == null ? "" : addedamount)
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_startqishu)
								+ beginBatch
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_lastqishu)
								+ (lastBatch == null ? "" : lastBatch));

			}
		});

		Button b_downpage = (Button) view
				.findViewById(R.id.usercenter_add_lottery_query_btn_downpage);
		b_downpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bettingindex < jsonArray.length() - 1) {
					bettingindex += 1;
				}
				showAddNum(bettingindex);
				textview.setText("");
				textview
						.setText(getString(R.string.usercenter_add_lottery_query_lotterytypename)
								+ play_name
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_notecode)
								+ "\n"
								+ betcode//
								+ getString(R.string.usercenter_add_lottery_query_notenum)
								+ betNum
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_addqishu)
								+ batchNum
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_recentaddqishu)
								+ (addednum == null ? "" : addednum)
								+ ""
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_recentmoneytotal)
								+ (addedamount == null ? "" : addedamount)
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_startqishu)
								+ beginBatch
								+ "\n"
								+ getString(R.string.usercenter_add_lottery_query_lastqishu)
								+ (lastBatch == null ? "" : lastBatch));

			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.usercenter_add_lottery_query_zhuihaochaxun);
		builder.setView(view);
		builder.setNegativeButton("����", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}

		});
		builder.show();
	}

	// wangyl 1.13 ����ע��ɾ��
	// ---------------------------------------------------------------------
	/**
	 * �û����� ׷�Ų�ѯ �����ĶԻ���
	 * 
	 * @author Administrator
	 * 
	 */
	/*
	 * public class UserCenterZhuihaoQueryDialog extends Dialog implements
	 * OnClickListener{
	 * 
	 * private Button upButton; private Button downButton; private Button
	 * returnButton; private TextView textView; MyDialogUpOrDownPageListener
	 * iListener;
	 * 
	 * public UserCenterZhuihaoQueryDialog(Context context) { super(context); }
	 * public UserCenterZhuihaoQueryDialog(Context
	 * context,MyDialogUpOrDownPageListener aListener,TextView textview) {
	 * super(context); this.iListener=aListener; this.textView = textview; }
	 * 
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * setContentView(R.layout.usercenter_add_lottery_query);
	 * 
	 * upButton = (Button)
	 * findViewById(R.id.usercenter_add_lottery_query_btn_uppage); downButton =
	 * (Button)findViewById(R.id.usercenter_add_lottery_query_btn_downpage);
	 * returnButton = (Button)
	 * findViewById(R.id.usercenter_add_lottery_query_return); TextView text
	 * =(TextView)findViewById(R.id.usercenter_add_lottery_query_text_id);
	 * text.setText(textView.getText()); upButton.setOnClickListener(this);
	 * downButton.setOnClickListener(this);
	 * returnButton.setOnClickListener(this);
	 * setTitle(R.string.usercenter_add_lottery_query_zhuihaochaxun); }
	 * 
	 * public void onClick(View v) { switch (v.getId()) { case
	 * R.id.usercenter_add_lottery_query_btn_uppage: this.dismiss();
	 * iListener.zuihaoupPage(); break; case
	 * R.id.usercenter_add_lottery_query_btn_downpage: this.dismiss();
	 * iListener.zuihaodownPage(); break; case
	 * R.id.usercenter_add_lottery_query_return: this.dismiss(); break; } } }
	 * //������ 7.9 �޸ĶԻ���
	 *//**
	 * �û�����--׷�Ų�ѯ ��һҳ
	 */
	/*
	 * public void zuihaoupPage(){ LayoutInflater inflater =
	 * LayoutInflater.from(this); View view =
	 * inflater.inflate(R.layout.usercenter_add_lottery_query, null); final
	 * TextView textview =
	 * (TextView)view.findViewById(R.id.usercenter_add_lottery_query_text_id);
	 * 
	 * if(bettingindex>0){ bettingindex=bettingindex-1; }
	 * showAddNum(bettingindex); textview.setText("");
	 * textview.setText(getString
	 * (R.string.usercenter_add_lottery_query_lotterytypename)+play_name+"\n"
	 * +getString(R.string.usercenter_add_lottery_query_notecode)+"\n"+betcode//
	 * +getString(R.string.usercenter_add_lottery_query_notenum)+betNum+"\n"
	 * +getString(R.string.usercenter_add_lottery_query_addqishu)+batchNum+"\n"
	 * +
	 * getString(R.string.usercenter_add_lottery_query_recentaddqishu)+addednum+
	 * ""+"\n"
	 * +getString(R.string.usercenter_add_lottery_query_recentmoneytotal)
	 * +addedamount+"\n"
	 * +getString(R.string.usercenter_add_lottery_query_startqishu
	 * )+beginBatch+"\n"
	 * +getString(R.string.usercenter_add_lottery_query_lastqishu)+lastBatch );
	 * // UserCenterZhuihaoQueryDialog izhuihaoDialog = new
	 * UserCenterZhuihaoQueryDialog(this,this,textview); //
	 * izhuihaoDialog.show(); } //������ 7.9 �޸ĶԻ���
	 *//**
	 * �û�����--׷�Ų�ѯ ��һҳ
	 */
	/*
	 * public void zuihaodownPage(){ LayoutInflater inflater =
	 * LayoutInflater.from(this); View view =
	 * inflater.inflate(R.layout.usercenter_add_lottery_query, null); final
	 * TextView textview =
	 * (TextView)view.findViewById(R.id.usercenter_add_lottery_query_text_id);
	 * if(bettingindex<jsonArray.length()-1){ bettingindex+=1; }
	 * showAddNum(bettingindex); textview.setText("");
	 * textview.setText(getString
	 * (R.string.usercenter_add_lottery_query_lotterytypename)+play_name+"\n"
	 * +getString(R.string.usercenter_add_lottery_query_notecode)+"\n"+betcode//
	 * +getString(R.string.usercenter_add_lottery_query_notenum)+betNum+"\n"
	 * +getString(R.string.usercenter_add_lottery_query_addqishu)+batchNum+"\n"
	 * +
	 * getString(R.string.usercenter_add_lottery_query_recentaddqishu)+addednum+
	 * ""+"\n"
	 * +getString(R.string.usercenter_add_lottery_query_recentmoneytotal)
	 * +addedamount+"\n"
	 * +getString(R.string.usercenter_add_lottery_query_startqishu
	 * )+beginBatch+"\n"
	 * +getString(R.string.usercenter_add_lottery_query_lastqishu)+lastBatch );
	 * 
	 * // UserCenterZhuihaoQueryDialog izhuihaoDialog = new
	 * UserCenterZhuihaoQueryDialog(this,this,textview); //
	 * izhuihaoDialog.show(); }
	 */
	// -------------------------------------------------------------------

	/**
	 * �û�����-�н���ѯ
	 */
	private void showUserCenterWinningCheck() {
		bettingindex = 0;
		int index = 0;
		winningselect(bettingindex);

		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.usercenter_winningcheck, null);
		final TextView textview = (TextView) view
				.findViewById(R.id.usercenter_winningCheck_id);
		textview
				.setText(getString(R.string.usercenter_winningCheck_lotteryCategory)
						+ play_name
						+ "\n"
						+ getString(R.string.usercenter_winningCheck_lotteryIssue)
						+ batchcode
						+ "\n"
						+ getString(R.string.usercenter_winningCheck_awardLevel)
						+ prizeinfo
						+ "\n"
						+ getString(R.string.usercenter_winningCheck_prizeMoney)
						+ prizeamt
						+ "\n"
						+ getString(R.string.usercenter_winningCheck_winningMark)
						+ encash_flag
						+ "\n"
						+ getString(R.string.usercenter_winningCheck_bettingTime)
						+ sell_datetime
						+ "\n"
						+ getString(R.string.usercenter_winningCheck_abandonWinningTime)
						+ abandon_date_time
						+ "\n"
						+ getString(R.string.usercenter_winningCheck_WinningNoteCode)
						+ "\n"
						+ betcode// +"\n"
						+ getString(R.string.usercenter_winningCheck_lotteryMultiple)
						+ beishu

				);
		/* ��ť��һҳ */
		Button upbtn = (Button) view
				.findViewById(R.id.usercenter_btn_winningcheck_uppage);
		if (index == 0) {
		}
		upbtn.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				if (bettingindex > 0) {
					bettingindex = bettingindex - 1;
				}
				winningselect(bettingindex);
				textview.setText("");
				textview
						.setText(getString(R.string.usercenter_winningCheck_lotteryCategory)
								+ play_name
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_lotteryIssue)
								+ batchcode
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_awardLevel)
								+ prizeinfo
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_prizeMoney)
								+ prizeamt
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_winningMark)
								+ encash_flag
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_bettingTime)
								+ sell_datetime
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_abandonWinningTime)
								+ abandon_date_time
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_WinningNoteCode)
								+ "\n"
								+ betcode// +"\n"
								+ getString(R.string.usercenter_winningCheck_lotteryMultiple)
								+ beishu

						);

			}

		});
		/* ��ť��һҳ */
		Button downbtn = (Button) view
				.findViewById(R.id.usercenter_btn_winningcheck_downpage);
		downbtn.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				if (bettingindex < jsonArray.length() - 1) {
					bettingindex += 1;
				}
				winningselect(bettingindex);
				textview.setText("");
				textview
						.setText(getString(R.string.usercenter_winningCheck_lotteryCategory)
								+ play_name
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_lotteryIssue)
								+ batchcode
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_awardLevel)
								+ prizeinfo
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_prizeMoney)
								+ prizeamt
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_winningMark)
								+ encash_flag
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_bettingTime)
								+ sell_datetime
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_abandonWinningTime)
								+ abandon_date_time
								+ "\n"
								+ getString(R.string.usercenter_winningCheck_WinningNoteCode)
								+ "\n"
								+ betcode// +"\n"
								+ getString(R.string.usercenter_winningCheck_lotteryMultiple)
								+ beishu

						);
			}

		});
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.usercenter_winningCheck);
		builder.setView(view);
		// wangyl 7.13 ��ӷ���
		builder.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ����
					}

				});
		builder.show();
	}

	/**
	 * �û�����-�˺�����
	 */
	private void showUserCenterAccountWithdraw() {

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

	/**
	 * �û�����-�����޸�
	 */
	private void showUserCenterPasswordChange() {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View textView = inflater.inflate(
				R.layout.usercenter_passwordchange, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.usercenter_passwordChange);
		builder.setView(textView);

		// ������Щ�Ƶ������� �³� 20100712

		// EditText usercenter_edittext_originalpwd =
		// (EditText)textView.findViewById(R.id.usercenter_edittext_originalpwd);
		// // Editable usercenter_edittext_originalpwd_text =
		// usercenter_edittext_originalpwd.getText();
		// final String usercenter_edittext_originalpwd_string =
		// usercenter_edittext_originalpwd.getText().toString();
		// PublicMethod.myOutput("--------------usercenter_textview_originalpwd_string"+usercenter_edittext_originalpwd_string);
		//		
		// EditText usercenter_edittext_newpwd =
		// (EditText)textView.findViewById(R.id.usercenter_edittext_newpwd);
		// // Editable usercenter_edittext_newpwd_text =
		// usercenter_edittext_newpwd.getText();
		// final String usercenter_edittext_newpwd_string =
		// usercenter_edittext_newpwd.getText().toString();
		// PublicMethod.myOutput("----------usercenter_edittext_newpwd_string-------"+usercenter_edittext_newpwd_string);
		//		
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ��Ҫ�жϺ󵯳���ʾ��
						// �¼��� ��ʾ�� 20100711 �³�
						editPassword(textView);

						// �����޸��߳��ᵽ������ �³� 20100712
						// showDialog(DIALOG1_KEY);
						// Thread t = new Thread(new Runnable(){
						// public void run(){
						//						
						// String error_code="00";
						// while(iretrytimes<3&&iretrytimes>0){
						// try {
						// re=jrtLot.changePassword(phonenum,
						// usercenter_edittext_originalpwd_string,
						// usercenter_edittext_newpwd_string, sessionid);
						// obj = new JSONObject(re);
						// error_code = obj.getString("error_code");
						// iretrytimes=3;
						// }catch(JSONException e){
						// iretrytimes--;
						// }
						// }
						// if(error_code.equals("0000")){
						// Message msg=new Message();
						// msg.what=5;
						// handler.sendMessage(msg);
						//								
						// }else if(error_code.equals("070002")){
						// Message msg=new Message();
						// msg.what=7;
						// handler.sendMessage(msg);
						// }else if(error_code.equals("0015")){
						// Message msg=new Message();
						// msg.what=11;
						// handler.sendMessage(msg);
						//								
						// }else if(error_code.equals("00")){
						// Message msg=new Message();
						// msg.what=9;
						// handler.sendMessage(msg);
						// }else{
						// Message msg=new Message();
						// msg.what=10;
						// handler.sendMessage(msg);
						// }
						// }
						// });
						// t.start();
						// Toast.makeText(RuyiHelper.this,getString(R.string.usercenter_successPassword),Toast.LENGTH_LONG).show();
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
	 * �༭����
	 * 
	 * @param v
	 */
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
						while (iretrytimes < 3 && iretrytimes > 0) {
							try {
								re = jrtLot.changePassword(phonenum,
										usercenter_edittext_originalpwd_string,
										usercenter_edittext_newpwd_string,
										sessionid);
								obj = new JSONObject(re);
								error_code = obj.getString("error_code");
								iretrytimes = 3;
							} catch (JSONException e) {
								iretrytimes--;
							}
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
			Toast.makeText(RuyiHelper.this, "���볤��ӦΪ6~15λ", Toast.LENGTH_SHORT)
					.show();
		}

	}

	String[][] currentShow = null;

	void getTotalPage() {
		lastItems = currentShow.length % 5;
		if (lastItems == 0) {
			lastItems = 5;
			totalPage = currentShow.length / 5;
		} else {
			totalPage = currentShow.length / 5 + 1;
		}
	}
// 20100803 �³�  ɾ���ӱ�ǩ��Ϣ����Ϊ������ȡ
	// ��ȡȫ����Ϣ�Ķ�ά���� �³� 2010/7/7/
	String[][] getAccount() {
		int chargeJiShu = 0;
		account = getAccountDetail_array();
		System.out.println("----account.length-----"+account.length);
		for (int i = 0; i < account.length; i++) {
			if (account[i][3].equals("��ֵ")||account[i][3].equals("֧��")||
					account[i][3].equals("����")||account[i][3].equals("����")) {
				chargeJiShu++;
			}
		}
		System.out.println("----chargeJiShu-----"+chargeJiShu);
		all = new String[chargeJiShu][7];
		int arrayIndex = 0;
		for (int i = 0; i < account.length; i++) {

			if (account[i][3].equals("��ֵ")||account[i][3].equals("֧��")||
					account[i][3].equals("����")||account[i][3].equals("����"))  {
				arrayIndex++;
				all[arrayIndex - 1] = account[i];
				PublicMethod.myOutput("---------" + all[arrayIndex - 1][2]);
			}
		}
		return all;
//		account = getAccountDetail_array();
//		return account;
	}
//
//	// ��ȡ��ֵ�Ķ�ά���� 2010/8/5/
	String[][] getCharge() {
		int chargeJiShu = 0;
		account = getAccountDetail_array();
		for (int i = 0; i < account.length; i++) {
			if (account[i][3].equals("��ֵ")) {
				chargeJiShu++;
			}
		}
		charge = new String[chargeJiShu][7];
		int arrayIndex = 0;
		for (int i = 0; i < account.length; i++) {

			if (account[i][3].equals("��ֵ")) {
				arrayIndex++;
				charge[arrayIndex - 1] = account[i];
				PublicMethod.myOutput("---------" + charge[arrayIndex - 1][2]);
			}
		}
		return charge;
	}
//
//	// ��ȡ֧���Ķ�ά���� 2010/7/7/
	String[][] getPay() {
		int payJiShu = 0;
		account = getAccountDetail_array();
		for (int i = 0; i < account.length; i++) {
			if (account[i][3].equals("֧��")) {
				payJiShu++;
			}
		}
		pay = new String[payJiShu][7];
		int arrayIndex = 0;
		for (int i = 0; i < account.length; i++) {

			if (account[i][3].equals("֧��")) {
				arrayIndex++;
				pay[arrayIndex - 1] = account[i];
				PublicMethod.myOutput("---------" + pay[arrayIndex - 1][2]);
			}
		}
		return pay;
	}
//
//	// ��ȡ���ɵĶ�ά���� 2010/7/7/
//	String[][] getReward() {
//		int rewardJiShu = 0;
//		account = getAccountDetail_array();
//		for (int i = 0; i < account.length; i++) {
//			if (account[i][3].equals("����")) {
//				rewardJiShu++;
//			}
//		}
//		reward = new String[rewardJiShu][7];
//		int arrayIndex = 0;
//		for (int i = 0; i < account.length; i++) {
//
//			if (account[i][3].equals("����")) {
//				arrayIndex++;
//				reward[arrayIndex - 1] = account[i];
//				PublicMethod.myOutput("---------" + reward[arrayIndex - 1][2]);
//			}
//		}
//		return reward;
//	}
//
//	// ��ȡ���ֵĶ�ά���� 2010/7/7/�³��޸�
//	String[][] getCash() {
//		int cashJiShu = 0;
//		account = getAccountDetail_array();
//		for (int i = 0; i < account.length; i++) {
//			if (account[i][3].equals("����")) {
//				cashJiShu++;
//			}
//		}
//		cash = new String[cashJiShu][7];
//		int arrayIndex = 0;
//		for (int i = 0; i < account.length; i++) {
//
//			if (account[i][3].equals("����")) {
//				arrayIndex++;
//				cash[arrayIndex - 1] = account[i];
//				PublicMethod.myOutput("---------" + cash[arrayIndex - 1][2]);
//			}
//		}
//		return cash;
//	}

	/**
	 * �û�����-�˻���ϸ
	 */
	private void showUserCenterAccountDetails() {
		/*
		 * iQuitFlag = 30; //������ 7.5 �����޸ģ��������û����� iCallOnKeyDownFlag=false;
		 */
		setContentView(R.layout.usercenter_accountdetails);
		currentShow =getAccount();// getAccountDetail_array();
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
					iType=1;
//					AccountList(v);
					accountView = v;
//					20100803 �³� �ӱ�ǩ������ȡ��Ϣ
					accountDetail();

//					int btnid = v.getId();
////					currentPage = 0;
//					switch (btnid) {
////					// ȫ��
//					case R.id.usercenter_all:
//						if(!type.equalsIgnoreCase("0")){
//							type="0";
//							AccountDetailThread(type);
//						}
//						break;
						
						
//   
//						// ������ 7.8 �޸ı���ͼƬ
//						rechargebtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.rechargedisable));
//						paybtn.setImageDrawable(getResources().getDrawable(
//								R.drawable.zhifudisable));
//						awardschoolbtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.jiangpaidisable));
//						withdrawbtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.tixiandisable));
//						allbtn.setImageDrawable(getResources().getDrawable(
//								R.drawable.allenable));
//						// allbtn.set
//						currentShow = getAccount();
//						PublicMethod.myOutput("------------all==="
//								+ getAccount());
//
//						if (currentShow == null || currentShow.length == 0) {
//							// ���Ϊ������ռ�¼ 2010/7/7 �³�
//							String[] str = { "��¼Ϊ��" };
//							String[] str_mode = { "" };
//							String[] str_yu_e = { "" };
//							UserCenter_AccountDetail_TradingDate = str;
//							UserCenter_AccountDetail_TradingMode = str_mode;
//							UserCenter_AccountDetail_Yu_E = str_yu_e;
//							ListView listview = (ListView) findViewById(R.id.usercenter_accountdetails_listview_id);
//							UserCenterAccountDetailsTabsAdapter adapter = new UserCenterAccountDetailsTabsAdapter(
//									RuyiHelper.this);
//							// adapter.isEmpty();
//							listview.setAdapter(adapter);
//							// TextView noInfo = (TextView)
//							// findViewById(R.id.usercenter_accountdetails_no_info_text);
//							// noInfo.setText(R.string.noinfo);
//						} else {
//							// ����м�¼���title �³� 2010/7/8 ע�͵�
//							// TextView noInfo = (TextView)
//							// findViewById(R.id.usercenter_accountdetails_no_info_text);
//							// noInfo.setText("");
//							getTotalPage();
//							showUserCenterAccountDetailsTabs(currentPage);
//						}
//						break;
//					// ��ֵ
//					case R.id.usercenter_recharge:
//
//						// ������ 7.8 �޸ı���ͼƬ
//						rechargebtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.rechargeenable));
//						paybtn.setImageDrawable(getResources().getDrawable(
//								R.drawable.zhifudisable));
//						awardschoolbtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.jiangpaidisable));
//						withdrawbtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.tixiandisable));
//						allbtn.setImageDrawable(getResources().getDrawable(
//								R.drawable.alldisable));
//						if(!type.equalsIgnoreCase("1")){
//							type="1";
//							AccountDetailThread(type);
//						}
//						break;
						
//						currentShow = getCharge();
//						
//
//						// ������ 7.7 �����޸ģ��ж�ȡ������Ϣ�Ƿ�Ϊ��
//						if (currentShow == null || currentShow.length == 0) {
//							PublicMethod
//									.myOutput("---------------?????????????????????");
//							// ���Ϊ������ռ�¼ 2010/7/8 �³�
//							String[] str = { "��¼Ϊ��" };
//							String[] str_mode = { "" };
//							String[] str_yu_e = { "" };
//							UserCenter_AccountDetail_TradingDate = str;
//							UserCenter_AccountDetail_TradingMode = str_mode;
//							UserCenter_AccountDetail_Yu_E = str_yu_e;
//							ListView listview = (ListView) findViewById(R.id.usercenter_accountdetails_listview_id);
//							UserCenterAccountDetailsTabsAdapter adapter = new UserCenterAccountDetailsTabsAdapter(
//									RuyiHelper.this);
//							listview.setAdapter(adapter);
//							// TextView noInfo = (TextView)
//							// findViewById(R.id.usercenter_accountdetails_no_info_text);
//							// noInfo.setText(R.string.noinfo);
//						} else {
//							// TextView noInfo = (TextView)
//							// findViewById(R.id.usercenter_accountdetails_no_info_text);
//							// noInfo.setText("");
//							getTotalPage();
//							showUserCenterAccountDetailsTabs(currentPage);
//						}
//						break;
////					// ֧��
//					case R.id.usercenter_pay:
//						if(!type.equalsIgnoreCase("2")){
//							type="2";
//							AccountDetailThread(type);
//						}
//						break;
//
//						// ������ 7.8 �޸ı���ͼƬ
//						rechargebtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.rechargedisable));
//						paybtn.setImageDrawable(getResources().getDrawable(
//								R.drawable.zhifuenable));
//						awardschoolbtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.jiangpaidisable));
//						withdrawbtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.tixiandisable));
//						allbtn.setImageDrawable(getResources().getDrawable(
//								R.drawable.alldisable));
//						currentShow = getPay();
//
//						// ������ 7.7 �����޸ģ��ж�ȡ������Ϣ�Ƿ�Ϊ��
//						if (currentShow == null || currentShow.length == 0) {
//							// ���Ϊ������ռ�¼ 2010/7/8�³�
//							String[] str = { "��¼Ϊ��" };
//							String[] str_mode = { "" };
//							String[] str_yu_e = { "" };
//							UserCenter_AccountDetail_TradingDate = str;
//							UserCenter_AccountDetail_TradingMode = str_mode;
//							UserCenter_AccountDetail_Yu_E = str_yu_e;
//							ListView listview = (ListView) findViewById(R.id.usercenter_accountdetails_listview_id);
//							UserCenterAccountDetailsTabsAdapter adapter = new UserCenterAccountDetailsTabsAdapter(
//									RuyiHelper.this);
//							// adapter.isEmpty();
//							listview.setAdapter(adapter);
//							// TextView noInfo = (TextView)
//							// findViewById(R.id.usercenter_accountdetails_no_info_text);
//							// noInfo.setText(R.string.noinfo);
//						} else {
//							// TextView noInfo = (TextView)
//							// findViewById(R.id.usercenter_accountdetails_no_info_text);
//							// noInfo.setText("");
//							getTotalPage();
//							showUserCenterAccountDetailsTabs(currentPage);
//						}
//						break;
////					// ����
//					case R.id.usercenter_awardschool:
//						if(!type.equalsIgnoreCase("3")){
//							type="3";
//							AccountDetailThread(type);
//						}
//						break;
//
//						// ������ 7.8 �޸ı���ͼƬ
//						rechargebtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.rechargedisable));
//						paybtn.setImageDrawable(getResources().getDrawable(
//								R.drawable.zhifudisable));
//						awardschoolbtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.jiangpaienable));
//						withdrawbtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.tixiandisable));
//						allbtn.setImageDrawable(getResources().getDrawable(
//								R.drawable.alldisable));
//						currentShow = getReward();
//
//						// ������ 7.7 �����޸ģ��ж�ȡ������Ϣ�Ƿ�Ϊ��
//						if (currentShow == null || currentShow.length == 0) {
//							// ���Ϊ������ռ�¼ 2010/7/8�³�
//							String[] str = { "��¼Ϊ��" };
//							String[] str_mode = { "" };
//							String[] str_yu_e = { "" };
//							UserCenter_AccountDetail_TradingDate = str;
//							UserCenter_AccountDetail_TradingMode = str_mode;
//							UserCenter_AccountDetail_Yu_E = str_yu_e;
//							ListView listview = (ListView) findViewById(R.id.usercenter_accountdetails_listview_id);
//							UserCenterAccountDetailsTabsAdapter adapter = new UserCenterAccountDetailsTabsAdapter(
//									RuyiHelper.this);
//							// adapter.isEmpty();
//							listview.setAdapter(adapter);
//							// TextView noInfo = (TextView)
//							// findViewById(R.id.usercenter_accountdetails_no_info_text);
//							// noInfo.setText(R.string.noinfo);
//						} else {
//							// TextView noInfo = (TextView)
//							// findViewById(R.id.usercenter_accountdetails_no_info_text);
//							// noInfo.setText("");
//							getTotalPage();
//							showUserCenterAccountDetailsTabs(currentPage);
//						}
//						break;
//////					// ����
//					case R.id.usercenter_withdraw:
//						type="4";
//						AccountDetailThread(type);
//						break;
//						// ����
//					case R.id.usercenter_return:
//						showListView(ID_USERCENTER);
//						break;
					}
					
					
//
//						// ������ 7.8 �޸ı���ͼƬ
//						rechargebtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.rechargedisable));
//						paybtn.setImageDrawable(getResources().getDrawable(
//								R.drawable.zhifudisable));
//						awardschoolbtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.jiangpaidisable));
//						withdrawbtn.setImageDrawable(getResources()
//								.getDrawable(R.drawable.tixianenable));
//						allbtn.setImageDrawable(getResources().getDrawable(
//								R.drawable.alldisable));
//						currentShow = getCash();
//
//						// ������ 7.7 �����޸ģ��ж�ȡ������Ϣ�Ƿ�Ϊ��
//						if (currentShow == null || currentShow.length == 0) {
//							// ���Ϊ������ռ�¼ 2010/7/8 �³�
//							String[] str = { "��¼Ϊ��" };
//							String[] str_mode = { "" };
//							String[] str_yu_e = { "" };
//							UserCenter_AccountDetail_TradingDate = str;
//							UserCenter_AccountDetail_TradingMode = str_mode;
//							UserCenter_AccountDetail_Yu_E = str_yu_e;
//							ListView listview = (ListView) findViewById(R.id.usercenter_accountdetails_listview_id);
//							UserCenterAccountDetailsTabsAdapter adapter = new UserCenterAccountDetailsTabsAdapter(
//									RuyiHelper.this);
//							// adapter.isEmpty();
//							listview.setAdapter(adapter);
//							// TextView noInfo = (TextView)
//							// findViewById(R.id.usercenter_accountdetails_no_info_text);
//							// noInfo.setText(R.string.noinfo);
//						} else {
//							// TextView noInfo = (TextView)
//							// findViewById(R.id.usercenter_accountdetails_no_info_text);
//							// noInfo.setText("");
//							getTotalPage();
//							showUserCenterAccountDetailsTabs(currentPage);
//						}
//						break;
					
//
//					}
//				}

			};
			allbtn.setOnClickListener(listener);
			rechargebtn.setOnClickListener(listener);
			paybtn.setOnClickListener(listener);
			awardschoolbtn.setOnClickListener(listener);
			withdrawbtn.setOnClickListener(listener);
			returnbtn.setOnClickListener(listener);
		}
	}
//	20100803 �³� �û�������ϸ�ӱ�ǩ����
	private void accountDetail(){
		
		int btnid = accountView.getId();
//		System.out.println("-????///////"+btnid);
//		currentPage = 0;
		switch (btnid) {
//		// ȫ��
		case R.id.usercenter_all:
		    if(!type.equalsIgnoreCase("0")){
				type="0";
				AccountDetailThread(type);
			}
			break;
		case R.id.usercenter_recharge:
			if(!type.equalsIgnoreCase("1")){
				type="1";
				AccountDetailThread(type);
			}
			break;
			// ֧��
		case R.id.usercenter_pay:
			if(!type.equalsIgnoreCase("2")){
				type="2";
				AccountDetailThread(type);
			}
			break;
			// ����
		case R.id.usercenter_awardschool:
			if(!type.equalsIgnoreCase("3")){
				type="3";
				AccountDetailThread(type);
			}
			break;
			// ����
		case R.id.usercenter_withdraw:
			if(!type.equalsIgnoreCase("4")){
				type="4";
				AccountDetailThread(type);
			}
			break;
			// ����
		case R.id.usercenter_return:
			showListView(ID_USERCENTER);
			break;
		}
			
			
	}
//	20100803 �³� �û�������ϸ�ӱ�ǩ�б�
	private void AccountList(View v){
		int btnid = v.getId();
		currentPage = 0;
		switch (btnid) {
		// ȫ��
		case R.id.usercenter_all:

			// ������ 7.8 �޸ı���ͼƬ
			rechargebtn.setImageDrawable(getResources()
					.getDrawable(R.drawable.rechargedisable));
			paybtn.setImageDrawable(getResources().getDrawable(
					R.drawable.zhifudisable));
			awardschoolbtn.setImageDrawable(getResources()
					.getDrawable(R.drawable.jiangpaidisable));
			withdrawbtn.setImageDrawable(getResources()
					.getDrawable(R.drawable.tixiandisable));
			allbtn.setImageDrawable(getResources().getDrawable(
					R.drawable.allenable));
			// allbtn.set
			if(iDetail== true){
			   currentShow = getAccount();
			}else if(iDetail== false){
				 currentShow = null;
			}
			System.out.println("------------all==="
					+ currentShow);
//			PublicMethod.WriteSettings(RuyiHelper.this, currentShow, "currentShow");

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
							listview.setAdapter(adapter);
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
						rechargebtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.rechargeenable));
						paybtn.setImageDrawable(getResources().getDrawable(
								R.drawable.zhifudisable));
						awardschoolbtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.jiangpaidisable));
						withdrawbtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.tixiandisable));
						allbtn.setImageDrawable(getResources().getDrawable(
								R.drawable.alldisable));
//			type="1";
//			AccountDetailThread(type);
			if(iDetail== true){
				   currentShow = getCharge();
				}else if(iDetail== false){
					 currentShow = null;
				}
			System.out.println("------------recharge==="
					+ currentShow);

						// ������ 7.7 �����޸ģ��ж�ȡ������Ϣ�Ƿ�Ϊ��
						if (currentShow == null || currentShow.length == 0) {
							PublicMethod
									.myOutput("---------------?????????????????????");
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
							listview.setAdapter(adapter);
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
						rechargebtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.rechargedisable));
						paybtn.setImageDrawable(getResources().getDrawable(
								R.drawable.zhifuenable));
						awardschoolbtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.jiangpaidisable));
						withdrawbtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.tixiandisable));
						allbtn.setImageDrawable(getResources().getDrawable(
								R.drawable.alldisable));
			if(iDetail== true){
				   currentShow = getPay();
				}else if(iDetail== false){
					 currentShow = null;
				}
			System.out.println("------------pay==="
					+ currentShow);

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
							listview.setAdapter(adapter);
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
						rechargebtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.rechargedisable));
						paybtn.setImageDrawable(getResources().getDrawable(
								R.drawable.zhifudisable));
						awardschoolbtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.jiangpaienable));
						withdrawbtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.tixiandisable));
						allbtn.setImageDrawable(getResources().getDrawable(
								R.drawable.alldisable));
			if(iDetail== true){
				   currentShow = getAccountDetail_array();
				}else if(iDetail== false){
					 currentShow = null;
				}
			System.out.println("------------award==="
					+ currentShow);

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
							listview.setAdapter(adapter);
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
						rechargebtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.rechargedisable));
						paybtn.setImageDrawable(getResources().getDrawable(
								R.drawable.zhifudisable));
						awardschoolbtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.jiangpaidisable));
						withdrawbtn.setImageDrawable(getResources()
								.getDrawable(R.drawable.tixianenable));
						allbtn.setImageDrawable(getResources().getDrawable(
								R.drawable.alldisable));
			if(iDetail== true){
				   currentShow = getAccountDetail_array();
				}else if(iDetail== false){
					 currentShow = null;
				}
			System.out.println("-----------withdraw==="
					+ currentShow);

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
							listview.setAdapter(adapter);
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
		int s = aPage * 5;
		return s;
	}

	int getEndIndex(int aPage) {
		int e;
		if (aPage < totalPage - 1) {
			e = 5 + 5 * aPage;
		} else {
			if (currentShow.length % 5 == 0)
				e = 5 + 5 * aPage;
			else
				e = currentShow.length % 5 + 5 * aPage;
		}
		return e;
	}

	/**
	 * �û�����-�˻���ϸ-ȫ��
	 * 
	 * @param aPage
	 *            ҳ��
	 */
	private void showUserCenterAccountDetailsTabs(int aPage) {

		// ��ʾȫ����Ϣ�е�ǰ���� �³� 2010/7/3
		// final String[][] account=getAccountDetail_array();
		// Vector vector=new Vector();
		// Vector vector_mode=new Vector();
		// Vector vector_page=new Vector();
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
		UserCenter_AccountDetail_Yu_E_All[0] = "���  ";

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
		listview.setAdapter(adapter);

		final Button accountdetail_uppage = (Button) findViewById(R.id.usercenter_upPage);
		final Button accountdetail_downpage = (Button) findViewById(R.id.usercenter_downPage);

		// ��ӷ�ҳ���� 2010/7/7 �³�

		accountdetail_downpage.setOnClickListener(new OnClickListener() {
			// int page=0;
			// �ж��Ƿ���Է�ҳ

			@Override
			public void onClick(View v) {
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
				// TODO Auto-generated method stub

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
						// accountdetail_uppage en
					} else {
						accountdetail_uppage.setClickable(false);
						// accountdetail_uppage dis
					}
					//cc  8.5   ��currentShow.length��Ϊ totalPage
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
	}

	/**
	 * �û�����-�˻���ϸ-��ǩ������
	 */
	public static class UserCenterAccountDetailsTabsAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

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

	/**
	 * ר�ҷ���������
	 */
	public static class ExpertAnalyzeEfficientAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private Bitmap mShuangSeQiu;
		private Bitmap mFuCai;
		private Bitmap mQiLeCai;

		public ExpertAnalyzeEfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);

			mShuangSeQiu = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.shuangseqiu);
			mFuCai = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.fucai);
			mQiLeCai = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.qilecai);
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
			} else {
				holder.icon.setImageBitmap(mQiLeCai);
				holder.expertAnalyzeTypeNameView
						.setText(expertAnalyzeTypeName[position]);
				// holder.expertAnalyzeMoreView.setText(expertAnalyzeMore[position]);
			}

			return convertView;
		}

		static class ViewHolder {
			ImageView icon;
			TextView expertAnalyzeTypeNameView;
			// TextView expertAnalyzeMoreView;
		}
	}

	/**
	 * ��á����ࡱ�б�������������Դ
	 */
	private List<Map<String, Object>> getListForMoreAdapter() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
		/* ר�ҷ��� */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.zhuanjiafenxi);
		map.put(TITLE, getString(R.string.zhuanjiafenxi));
		list.add(map);

		/* �û����� */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.yonghuzhongxin);
		map.put(TITLE, getString(R.string.yonghuzhongxin));
		list.add(map);

		/* �������� */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.ruyizhushou);
		map.put(TITLE, getString(R.string.ruyizhushou));
		list.add(map);

		/* ������Ȩ */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.guanyushouquan);
		map.put(TITLE, getString(R.string.ruyihelper_authorizing));
		list.add(map);

		return list;
	}

	/**
	 * ������������б�������������Դ
	 */
	private List<Map<String, Object>> getListForRuyiHelperAdapter() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
		/* �淨���� */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.wangfajieshao);
		map.put(TITLE, getString(R.string.ruyihelper_gameIntroduction));
		list.add(map);

		/* �������� */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.caozuozhushou);
		map.put(TITLE, getString(R.string.ruyihelper_operationAssistant));
		list.add(map);

		/* �������� */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.changjianwenti);
		map.put(TITLE, getString(R.string.ruyihelper_frequentQuestion));
		list.add(map);

		return list;
	}

	/**
	 * ����û������б�������������Դ
	 */
	private List<Map<String, Object>> getListForUserCenterAdapter() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
		/* ����ѯ */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.yuechaxun);
		map.put(TITLE, getString(R.string.ruyihelper_balanceInquiry));
		list.add(map);

		/* �н���ѯ */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.zhongjiangchaxun);
		map.put(TITLE, getString(R.string.usercenter_winningCheck));
		list.add(map);

		/* Ͷע��ϸ */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.touzhumingxi);
		map.put(TITLE, getString(R.string.usercenter_bettingDetails));
		list.add(map);

		/* �˺���ϸ */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.zhanghumingxi);
		map.put(TITLE, getString(R.string.usercenter_accountDetails));
		list.add(map);

		/* ���Ͳ�ѯ */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.zengsongchaxun);
		map.put(TITLE, getString(R.string.usercenter_giftCheck));
		list.add(map);

		/* �����޸� */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.mimaxiugai);
		map.put(TITLE, getString(R.string.usercenter_passwordChange));
		list.add(map);

		/* �˺����� */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.zhanghutixian);
		map.put(TITLE, getString(R.string.ruyihelper_accountWithdraw));
		list.add(map);

		/* ׷�Ų�ѯ */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.zuihaochaxun);
		map.put(TITLE, getString(R.string.usercenter_trackNumberInquiry));
		list.add(map);

		return list;
	}

	// Ͷע��ѯ��Ҫ��ȡ����Ϣ// ֮ǰ˫ɫ��ʽ����ע���ʽ��������, 2010/7/4 �³� 2010/7/5֮ǰ����3D��������
	/**
	 * Ͷע��ѯjson����
	 * 
	 * @param index
	 * @param play_name
	 *            ����
	 * @param batchcode
	 *            �ں�
	 * @param betcode
	 *            ע��
	 * @param sell_datetime
	 *            Ͷעʱ��
	 * @return
	 */
	public void bettingselect(int index) {
		try {
			JSONObject obj = jsonArray.getJSONObject(index);
			play_name = obj.getString("play_name");
			batchcode = obj.getString("batchcode");
			betcode = obj.getString("betcode");
			sell_datetime = obj.getString("sell_datetime");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		PublicMethod.myOutput("@@@@@" + play_name + "@@@@" + batchcode + "@@@@"
				+ sell_datetime + "@@@@@" + betcode);
		int wayCode = Integer.parseInt(betcode.substring(0, 2));

		beishu = betcode.substring(2, 4);

		if (play_name.equals("B001")) {
			if (wayCode == 00) {
				play_name = "˫ɫ��ʽ";
				// �³� 20100714
				String mp[] = GT.splitBetCode(betcode);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {

					betcode += (GT.makeString("F47104", wayCode, mp[i]
							.substring(4)) + "\n");
				}
			} else if (wayCode == 40 || wayCode == 50) {
				play_name = "˫ɫ���ϸ�ʽ";
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
				betcode = "������: " + danma + "\n" + "��������: " + tuoma + "\n"
						+ "����" + lanqiu + "\n";

			} else {
				play_name = "˫ɫ�������ʽ";
				int index1 = 0;// ���ҡ�*��
				int index2 = 0;// ����"~"
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

				betcode = "����: " + redball + "\n" + "����: " + blueball + "\n";
			}
			PublicMethod.myOutput("---------------@@@@@" + betcode);
		} else if (play_name.equals("D3")) {
			if (wayCode == 54) {
				play_name = "����3D����";
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
				betcode = "����: " + danma + "\n" + "����: " + tuoma + "\n";
				// ��ѡ��ʽ����� 2010/7/5 �³�
			} else if (wayCode == 00) {
				// 3D��ѡע���ʽ ���� �³� 20100714
				if (wayCode == 00) {
					play_name = "����3Dֱѡ��ʽ";
				}
				// betcode = GT.makeString("F47103", wayCode,
				// betcode.substring(4, betcode.length()-1));
				// �³� 20100714 ������ʽ
				String mp[] = GT.splitBetCode(betcode);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {

					betcode += (GT.makeString("F47103", wayCode, mp[i]
							.substring(4)) + "\n");
				}
			} else if (wayCode == 20) {
				// if(wayCode==00){
				// play_name="����3Dֱѡ��ʽ";
				// }
				// if(wayCode==20){
				play_name = "����3Dֱѡ��ʽ";
				// play_name="����3D��ѡ��ʽ";
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
						.substring(6, index1));
				String shiwei = GT.makeString("F47103", wayCode, betcode
						.substring(index1 + 3, index2));
				String gewei = GT.makeString("F47103", wayCode, betcode
						.substring(index2 + 3, betcode.length() - 1));
				betcode = "��λ: " + baiwei + "\n" + "ʮλ: " + shiwei + "\n"
						+ "��λ: " + gewei + "\n";

			} else if (wayCode == 30 || wayCode == 31 || wayCode == 32) {
				// ��ʽ�������㷨��һ�������ó��� �³� 20100711
				if (wayCode == 30) {
					play_name = "����3D��ѡ��ʽ";
				} else if (wayCode == 31) {
					play_name = "����3D��3��ʽ";
				} else if (wayCode == 32) {
					play_name = "����3D��6��ʽ";
				}
				String mp[] = GT.splitBetCode(betcode);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {
					betcode += (GT.makeString("F47103", wayCode, mp[i]
							.substring(6)) + "\n");
				}
			} else {
				// if(wayCode==00){
				// play_name="����3D��ʽ";
				// }else
				if (wayCode == 01) {
					play_name = "����3D��3";
				} else if (wayCode == 02) {
					play_name = "����3D��6";
				} else if (wayCode == 10) {
					play_name = "����3Dֱѡ��ֵ";
				} else if (wayCode == 11) {
					play_name = "����3D��3��ֵ";
				} else if (wayCode == 12) {
					play_name = "����3D��6��ֵ";
				}
				// else if(wayCode==30){
				// play_name="����3D��ѡ��ʽ";
				// }else if(wayCode==31){
				// play_name="����3D��3��ʽ";
				// }else if(wayCode==32){
				// play_name="����3D��6��ʽ";
				// }
				// �ظ���ɾ�� �³�20100711
				// else if(wayCode==54){
				// play_name="����3D����";
				// }
				String mp[] = GT.splitBetCode(betcode);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {
					betcode += (GT.makeString("F47103", wayCode, mp[i]
							.substring(4)) + "\n");
				}
			}
		} else if (play_name.equals("QL730")) {
			if (wayCode == 00) {
				int index_q;
				String mp[] = GT.splitBetCode(betcode);
				play_name = "���ֲʵ�ʽ";
				betcode = "";
				for (int i = 0; i < mp.length; i++) {
					betcode += (GT.makeString("F47102", wayCode, mp[i]
							.substring(4)) + "\n");
				}
			} else if (wayCode == 10) {
				play_name = "���ֲʸ�ʽ";
				betcode = GT.makeString("F47102", wayCode, betcode.substring(5,
						betcode.length() - 1));
			} else if (wayCode == 20) {
				play_name = "���ֲʵ���";
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
				betcode = "����: " + danma + "\n" + "����: " + tuoma;
			}
			// String mp[]= GT.splitBetCode(betcode);

		}

	}

	// ���Ͳ�ѯ��Ҫ��ȡ����Ϣ
	/**
	 * ���Ͳ�ѯjson����
	 * 
	 * @param index
	 * @param
	 * @return
	 */

	public void showgiftdetailview(int index) {
		// �����������Ͳ�ѯ�ɹ����ȡ��Ϣ�������¼Ϊ����ֵΪ�� 2010/7/11 �³�
		if (error_code_gift.equalsIgnoreCase("000000")) {
			try {
				JSONObject obj = jsonArray.getJSONObject(index);
				play_name = obj.getString("lotno");
				// batchcode=obj.getString("batchcode");
				betcode = obj.getString("bet_code");
				// sell_datetime=obj.getString("sell_datetime");
				to_mobile_code = obj.getString("to_mobile_code");
				term_begin_datetime = obj.getString("term_begin_datetime");
				sell_term_code = obj.getString("sell_term_code");
				amount = PublicMethod.changeMoney(obj.getString("amount"));// �޸����Ͳ�ѯ�����ͽ���ʽ
				// 20100714
				// �³�

			} catch (JSONException e) {
				e.printStackTrace();
			}
			PublicMethod.myOutput("@@@@@" + play_name + "@@@@" + sell_term_code
					+ "@@@@" + term_begin_datetime + "@@@@@" + betcode);
			int wayCode = Integer.parseInt(betcode.substring(0, 2));

			beishu = betcode.substring(2, 4);

			// if(play_name.equals("B001")){
			// play_name="˫ɫ��ʽ";
			// String mp[]= GT.splitBetCode(betcode);
			// betcode="";
			// for(int i=0;i<mp.length;i++){
			//				
			// betcode += ( GT.makeString("F47104", wayCode,
			// mp[i].substring(4))+"\n");
			// }
			// PublicMethod.myOutput("---------------@@@@@"+betcode);
			// }
			if (play_name.equals("B001")) {
				if (wayCode == 00) {
					play_name = "˫ɫ��ʽ";
					String mp[] = GT.splitBetCode(betcode);
					betcode = "";
					for (int i = 0; i < mp.length; i++) {

						betcode += (GT.makeString("F47104", wayCode, mp[i]
								.substring(4)) + "\n");
					}
				} else if (wayCode == 40 || wayCode == 50) {
					play_name = "˫ɫ���ϸ�ʽ";
					int index1 = 0;
					for (int i = 0; i < betcode.length(); i++) {
						if (betcode.charAt(i) == '*') {
							index1 = i;
							PublicMethod.myOutput("--------------index1"
									+ index1);
						}
					}
					int index2 = 0;// ���ҡ�~��
					for (int i = 0; i < betcode.length(); i++) {
						if (betcode.charAt(i) == '~') {
							index2 = i;
							PublicMethod.myOutput("--------------index2"
									+ index2);
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
					betcode = "������: " + danma + "\n" + "��������: " + tuoma + "\n"
							+ "����" + lanqiu + "\n";

				} else {
					play_name = "˫ɫ�������ʽ";
					int index1 = 0;// ���ҡ�*��
					int index2 = 0;// ����"~"
					for (int i = 0; i < betcode.length(); i++) {
						if (betcode.charAt(i) == '~') {
							index1 = i;
							PublicMethod.myOutput("--------------index1"
									+ index1);
						}
					}

					String redball = GT.makeString("F47104", wayCode, betcode
							.substring(5, index1));
					String blueball = GT.makeString("F47104", wayCode, betcode
							.substring(index1 + 1, betcode.length() - 1));

					betcode = "����: " + redball + "\n" + "����: " + blueball
							+ "\n";
				}
				PublicMethod.myOutput("---------------@@@@@" + betcode);
			}

			// else if(play_name.equals("D3")){
			// play_name="����3D��ʽ";
			// String mp[]= GT.splitBetCode(betcode);
			// betcode="";
			// for(int i=0;i<mp.length;i++){
			// betcode += (GT.makeString("F47103", wayCode,
			// mp[i].substring(4))+"\n");
			// }
			else if (play_name.equals("D3")) {
				if (wayCode == 54) {
					play_name = "����3D����";
					int index1 = 0;
					for (int i = 0; i < betcode.length(); i++) {
						if (betcode.charAt(i) == '*') {
							index1 = i;
							PublicMethod.myOutput("--------------index1"
									+ index1);
						}
					}
					String danma = GT.makeString("F47103", wayCode, betcode
							.substring(4, index1));
					String tuoma = GT.makeString("F47103", wayCode, betcode
							.substring(index1 + 1, betcode.length() - 1));
					betcode = "����: " + danma + "\n" + "����: " + tuoma + "\n";
					// ��ѡ��ʽ����� 2010/7/5 �³�
				} else if (wayCode == 00) {
					// 3D��ѡע���ʽ ���� �³� 20100714
					if (wayCode == 00) {
						play_name = "����3Dֱѡ��ʽ";
					}
					// betcode = GT.makeString("F47103", wayCode,
					// betcode.substring(4, betcode.length()-1));
					// �³� 20100714 ������ʽ
					String mp[] = GT.splitBetCode(betcode);
					betcode = "";
					for (int i = 0; i < mp.length; i++) {

						betcode += (GT.makeString("F47103", wayCode, mp[i]
								.substring(4)) + "\n");
					}
				} else if (wayCode == 20) {
					// if(wayCode==00){
					// play_name="����3Dֱѡ��ʽ";
					// }
					// if(wayCode==20){
					play_name = "����3Dֱѡ��ʽ";
					// play_name="����3D��ѡ��ʽ";
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
							PublicMethod.myOutput("--------------index1"
									+ index1);
						}
					}
					String baiwei = GT.makeString("F47103", wayCode, betcode
							.substring(6, index1));
					String shiwei = GT.makeString("F47103", wayCode, betcode
							.substring(index1 + 3, index2));
					String gewei = GT.makeString("F47103", wayCode, betcode
							.substring(index2 + 3, betcode.length() - 1));
					betcode = "��λ: " + baiwei + "\n" + "ʮλ: " + shiwei + "\n"
							+ "��λ: " + gewei + "\n";

				}
			}
			// else if(play_name.equals("QL730")){
			// String mp[]= GT.splitBetCode(betcode);
			//				
			// play_name="���ֲʵ�ʽ";
			// betcode="";
			// for(int i=0;i<mp.length;i++){
			// betcode += (GT.makeString("F47102", wayCode,
			// mp[i].substring(4))+"\n");
			// }
			//			
			// }
			else if (play_name.equals("QL730")) {
				if (wayCode == 00) {
					int index_q;
					String mp[] = GT.splitBetCode(betcode);
					play_name = "���ֲʵ�ʽ";
					betcode = "";
					for (int i = 0; i < mp.length; i++) {
						betcode += (GT.makeString("F47102", wayCode, mp[i]
								.substring(4)) + "\n");
					}
				} else if (wayCode == 10) {
					play_name = "���ֲʸ�ʽ";
					betcode = GT.makeString("F47102", wayCode, betcode
							.substring(5, betcode.length() - 1));
				}
			}
		} else {
			play_name = "";
			// batchcode=obj.getString("batchcode");
			betcode = "";
			// sell_datetime=obj.getString("sell_datetime");
			to_mobile_code = "";
			term_begin_datetime = "";
			sell_term_code = "";
			amount = "";
		}

	}

	// �����Ͳ�ѯ���ȡ����Ϣ
	/**
	 * �����Ͳ�ѯjson����
	 * 
	 * @param index
	 * @param
	 * @return
	 */

	public void showgifteddetail(int index) {
		// �����������Ͳ�ѯ�ɹ����ȡ��Ϣ�������¼Ϊ����ֵΪ�� �³�20107011
		if (error_code_gifted.equalsIgnoreCase("000000")) {
			try {
				JSONObject obj = jsonArray_gifted.getJSONObject(index);
				play_name_gifted = obj.getString("lotno");
				betcode_gifted = obj.getString("bet_code");
				from_mobile_code = obj.getString("from_mobile_code");
				term_begin_datetime_gifted = obj
						.getString("term_begin_datetime");
				sell_term_code_gifted = obj.getString("sell_term_code");
				amount_gifted = PublicMethod.changeMoney(obj
						.getString("amount"));// �޸ı����Ͳ�ѯ�����ͽ���ʽ 20100714 �³�

			} catch (JSONException e) {
				e.printStackTrace();
			}
			PublicMethod.myOutput("@@@@@" + play_name_gifted + "@@@@"
					+ batchcode + "@@@@" + sell_datetime_gifted + "@@@@@"
					+ betcode_gifted);
			int wayCode = Integer.parseInt(betcode_gifted.substring(0, 2));

			beishu = betcode_gifted.substring(2, 4);

			// if(play_name_gifted.equals("B001")){
			// play_name_gifted="˫ɫ��ʽ";
			// String mp[]= GT.splitBetCode(betcode_gifted);
			// betcode_gifted="";
			// for(int i=0;i<mp.length;i++){
			// betcode_gifted += ( GT.makeString("F47104", wayCode,
			// mp[i].substring(4))+"\n");
			// }
			// PublicMethod.myOutput("---------------@@@@@"+betcode);
			// }
			if (play_name_gifted.equals("B001")) {
				if (wayCode == 00) {
					play_name_gifted = "˫ɫ��ʽ";
					String mp[] = GT.splitBetCode(betcode_gifted);
					betcode_gifted = "";
					for (int i = 0; i < mp.length; i++) {

						betcode_gifted += (GT.makeString("F47104", wayCode,
								mp[i].substring(4)) + "\n");
					}
				} else if (wayCode == 40 || wayCode == 50) {
					play_name_gifted = "˫ɫ���ϸ�ʽ";
					int index1 = 0;
					for (int i = 0; i < betcode_gifted.length(); i++) {
						if (betcode_gifted.charAt(i) == '*') {
							index1 = i;
							PublicMethod.myOutput("--------------index1"
									+ index1);
						}
					}
					int index2 = 0;// ���ҡ�~��
					for (int i = 0; i < betcode_gifted.length(); i++) {
						if (betcode_gifted.charAt(i) == '~') {
							index2 = i;
							PublicMethod.myOutput("--------------index2"
									+ index2);
						}
					}
					// String tmp = betcode.substring(0, index1)+"*";
					// PublicMethod.myOutput("----------------tmp"+tmp);
					String danma = GT.makeString("F47104", wayCode,
							betcode_gifted.substring(4, index1));
					String tuoma = GT.makeString("F47104", wayCode,
							betcode_gifted.substring(index1 + 1, index2));
					String lanqiu = GT.makeString("F47104", wayCode,
							betcode_gifted.substring(index2 + 1, betcode_gifted
									.length() - 1));
					betcode_gifted = "������: " + danma + "\n" + "��������: " + tuoma
							+ "\n" + "����" + lanqiu + "\n";

				} else {
					play_name_gifted = "˫ɫ�������ʽ";
					int index1 = 0;// ���ҡ�*��
					int index2 = 0;// ����"~"
					for (int i = 0; i < betcode_gifted.length(); i++) {
						if (betcode_gifted.charAt(i) == '~') {
							index1 = i;
							PublicMethod.myOutput("--------------index1"
									+ index1);
						}
					}

					String redball = GT.makeString("F47104", wayCode,
							betcode_gifted.substring(5, index1));
					String blueball = GT.makeString("F47104", wayCode,
							betcode_gifted.substring(index1 + 1, betcode_gifted
									.length() - 1));

					betcode_gifted = "����: " + redball + "\n" + "����: "
							+ blueball + "\n";
				}
				PublicMethod.myOutput("---------------@@@@@" + betcode);
			}
			// else if(play_name_gifted.equals("D3")){
			// play_name_gifted="����3D��ʽ";
			// String mp[]= GT.splitBetCode(betcode_gifted);
			// betcode_gifted="";
			// for(int i=0;i<mp.length;i++){
			// betcode_gifted += (GT.makeString("F47103", wayCode,
			// mp[i].substring(6))+"\n");
			// }
			else if (play_name_gifted.equals("D3")) {
				if (wayCode == 54) {
					play_name_gifted = "����3D����";
					int index1 = 0;
					for (int i = 0; i < betcode_gifted.length(); i++) {
						if (betcode_gifted.charAt(i) == '*') {
							index1 = i;
							PublicMethod.myOutput("--------------index1"
									+ index1);
						}
					}
					String danma = GT.makeString("F47103", wayCode,
							betcode_gifted.substring(4, index1));
					String tuoma = GT.makeString("F47103", wayCode,
							betcode_gifted.substring(index1 + 1, betcode_gifted
									.length() - 1));
					betcode_gifted = "����: " + danma + "\n" + "����: " + tuoma
							+ "\n";
					// ��ѡ��ʽ����� 2010/7/5 �³�
				} else if (wayCode == 00) {
					// 3D��ѡע���ʽ ���� �³� 20100714
					if (wayCode == 00) {
						play_name_gifted = "����3Dֱѡ��ʽ";
					}
					// betcode = GT.makeString("F47103", wayCode,
					// betcode.substring(4, betcode.length()-1));
					// �³� 20100714 ������ʽ
					String mp[] = GT.splitBetCode(betcode_gifted);
					betcode_gifted = "";
					for (int i = 0; i < mp.length; i++) {

						betcode_gifted += (GT.makeString("F47103", wayCode,
								mp[i].substring(4)) + "\n");
					}
				} else if (wayCode == 20) {
					// if(wayCode==00){
					// play_name="����3Dֱѡ��ʽ";
					// }
					// if(wayCode==20){
					play_name_gifted = "����3Dֱѡ��ʽ";
					// play_name="����3D��ѡ��ʽ";
					int index1 = 0;
					int index2 = 0;
					for (int i = 0; i < betcode_gifted.length(); i++) {
						if (betcode_gifted.charAt(i) == '^') {
							index1 = i;
							i = betcode_gifted.length();
							PublicMethod.myOutput("--------------index====="
									+ index1);
						}
					}
					for (int j = index1 + 1; j < betcode_gifted.length(); j++) {
						if (betcode_gifted.charAt(j) == '^') {
							index2 = j;
							j = betcode_gifted.length();
							PublicMethod.myOutput("--------------index1"
									+ index1);
						}
					}
					String baiwei = GT.makeString("F47103", wayCode,
							betcode_gifted.substring(6, index1));
					String shiwei = GT.makeString("F47103", wayCode,
							betcode_gifted.substring(index1 + 3, index2));
					String gewei = GT.makeString("F47103", wayCode,
							betcode_gifted.substring(index2 + 3, betcode_gifted
									.length() - 1));
					betcode_gifted = "��λ: " + baiwei + "\n" + "ʮλ: " + shiwei
							+ "\n" + "��λ: " + gewei + "\n";

				}
			}
			// else if(play_name_gifted.equals("QL730")){
			// String mp[]= GT.splitBetCode(betcode_gifted);
			// play_name_gifted="���ֲʵ�ʽ";
			// betcode_gifted="";
			// for(int i=0;i<mp.length;i++){
			// betcode_gifted += (GT.makeString("F47102", wayCode,
			// mp[i].substring(4))+"\n");
			// }
			//			
			// }
			else if (play_name_gifted.equals("QL730")) {
				if (wayCode == 00) {
					int index_q;
					String mp[] = GT.splitBetCode(betcode_gifted);
					play_name_gifted = "���ֲʵ�ʽ";
					betcode_gifted = "";
					for (int i = 0; i < mp.length; i++) {
						betcode_gifted += (GT.makeString("F47102", wayCode,
								mp[i].substring(4)) + "\n");
					}
				} else if (wayCode == 10) {
					play_name_gifted = "���ֲʸ�ʽ";
					betcode_gifted = GT.makeString("F47102", wayCode,
							betcode_gifted.substring(5,
									betcode_gifted.length() - 1));
				}
			}
		} else {
			play_name_gifted = "";
			// batchcode=obj.getString("batchcode");
			betcode_gifted = "";
			// sell_datetime=obj.getString("sell_datetime");
			from_mobile_code = "";
			term_begin_datetime_gifted = "";
			sell_term_code_gifted = "";
			amount_gifted = "";
		}

	}

	// �н���ѯ���ȡ����Ϣ

	/**
	 * �н���ѯjson����
	 * 
	 * @param index jsonArray�е�λ��
	 * @param
	 * @return
	 */

	public void winningselect(int index) {
		try {

			JSONObject obj = jsonArray.getJSONObject(index);
			play_name = obj.getString("play_name");
			batchcode = obj.getString("batchcode");
			betcode = obj.getString("code");
			sell_datetime = obj.getString("sell_datetime");
			// valid_term_code=obj.getString("valid_term_code");
			prizeamt = PublicMethod.changeMoney(obj.getString("prizeamt"));// �޸��н���ѯ�Ľ���ʽ
			// �³�
			// 20100714
			abandon_date_time = obj.getString("abandon_date_time");
			encash_flag = obj.getString("encash_flag");
			prizeinfo = obj.getString("prizeinfo");
			prizetime = obj.getString("prizetime");
			prizelttle = obj.getString("prizelttle");

		} catch (JSONException e) {
			e.printStackTrace();
		}
		PublicMethod.myOutput("@@@@@" + play_name + "@@@@" + batchcode + "@@@@"
				+ sell_datetime + "@@@@@" + betcode);

		if (encash_flag.equals("0")) {
			encash_flag = "δ�ҽ�";
		} else if (encash_flag.equals("1")) {
			encash_flag = "�Ѷҽ�";
		} else if (encash_flag.equals("9")) {
			encash_flag = "��";
		}

		int num = 0;
		int startIndex = 0;
		int endIndex = -1;
		for (int i = 0; i < prizeinfo.length(); i++) {
			if (prizeinfo.charAt(i) == ',') {
				startIndex = endIndex + 1;
				endIndex = i;
				num++;
				if (!prizeinfo.substring(startIndex, endIndex).equals("0")) {
					prizeinfo = num + "�Ƚ�"
							+ prizeinfo.substring(startIndex, endIndex) + "ע";
				}
			}

		}

		beishu = betcode.substring(2, 4);

		int wayCode = Integer.parseInt(betcode.substring(0, 2));
		if (play_name.equals("F47104")) {
			if (wayCode == 00) {
				play_name = "˫ɫ��ʽ";
				String mp[] = GT.splitBetCode(betcode);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {

					betcode += (GT.makeString("F47104", wayCode, mp[i]
							.substring(4)) + "\n");
				}
			} else if (wayCode == 40 || wayCode == 50) {
				play_name = "˫ɫ���ϸ�ʽ";
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
				betcode = "������: " + danma + "\n" + "��������: " + tuoma + "\n"
						+ "����" + lanqiu + "\n";

			} else {
				play_name = "˫ɫ�������ʽ";
				int index1 = 0;// ���ҡ�*��
				int index2 = 0;// ����"~"
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

				betcode = "����: " + redball + "\n" + "����: " + blueball + "\n";
			}

			PublicMethod.myOutput("---------------@@@@@" + betcode);
		} else if (play_name.equals("F47103")) {
			if (wayCode == 54) {
				play_name = "����3D����";
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
				betcode = "����: " + danma + "\n" + "����: " + tuoma + "\n";
			} else if (wayCode == 00) {
				// 3D��ѡע���ʽ ���� �³� 20100714
				if (wayCode == 00) {
					play_name = "����3Dֱѡ��ʽ";
				}
				// betcode = GT.makeString("F47103", wayCode,
				// betcode.substring(4, betcode.length()-1));
				// �³� 20100714 ������ʽ
				String mp[] = GT.splitBetCode(betcode);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {

					betcode += (GT.makeString("F47103", wayCode, mp[i]
							.substring(4)) + "\n");
				}
			} else if (wayCode == 20) {
				play_name = "����3Dֱѡ��ʽ";
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
						.substring(6, index1));
				String shiwei = GT.makeString("F47103", wayCode, betcode
						.substring(index1 + 3, index2));
				String gewei = GT.makeString("F47103", wayCode, betcode
						.substring(index2 + 3, betcode.length() - 1));
				betcode = "��λ: " + baiwei + "\n" + "ʮλ: " + shiwei + "\n"
						+ "��λ: " + gewei + "\n";

			} else if (wayCode == 30 || wayCode == 31 || wayCode == 32) {
				// ��ʽ�������㷨��һ�������ó��� �³� 20100711
				if (wayCode == 30) {
					play_name = "����3D��ѡ��ʽ";
				} else if (wayCode == 31) {
					play_name = "����3D��3��ʽ";
				} else if (wayCode == 32) {
					play_name = "����3D��6��ʽ";
				}
				String mp[] = GT.splitBetCode(betcode);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {
					betcode += (GT.makeString("F47103", wayCode, mp[i]
							.substring(6)) + "\n");
				}
			} else {
				// if(wayCode==00){
				// play_name="����3D��ʽ";
				// }else
				if (wayCode == 01) {
					play_name = "����3D��3";
				} else if (wayCode == 02) {
					play_name = "����3D��6";
				} else if (wayCode == 10) {
					play_name = "����3Dֱѡ��ֵ";
				} else if (wayCode == 11) {
					play_name = "����3D��3��ֵ";
				} else if (wayCode == 12) {
					play_name = "����3D��6��ֵ";
				}
				// else
				// {if(wayCode==30){
				// play_name="����3D��ѡ��ʽ";
				// }else if(wayCode==31){
				// play_name="����3D��3��ʽ";
				// }else if(wayCode==32){
				// play_name="����3D��6��ʽ";
				// ��������Ƶ����� �³� 20100714
				String mp[] = GT.splitBetCode(betcode);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {
					betcode += (GT.makeString("F47103", wayCode, mp[i]
							.substring(4)) + "\n");
				}
				// }
				// �ظ��� ɾ�� �³�20100711
				// else if(wayCode==54){
				// play_name="����3D����";
				// }
				// �Ƶ����� �³� 20100714
				// String mp[]= GT.splitBetCode(betcode);
				// betcode="";
				// for(int i=0;i<mp.length;i++){
				// betcode += (GT.makeString("F47103", wayCode,
				// mp[i].substring(4))+"\n");
				// }
			}
		} else if (play_name.equals("F47102")) {
			if (wayCode == 00) {
				int index_q;
				String mp[] = GT.splitBetCode(betcode);
				play_name = "���ֲʵ�ʽ";
				betcode = "";
				for (int i = 0; i < mp.length; i++) {
					betcode += (GT.makeString("F47102", wayCode, mp[i]
							.substring(4)) + "\n");
				}
			} else if (wayCode == 10) {
				play_name = "���ֲʸ�ʽ";
				betcode = GT.makeString("F47102", wayCode, betcode.substring(5,
						betcode.length() - 1))
						+ "\n";
			} else if (wayCode == 20) {
				play_name = "���ֲʵ���";
				int index1 = 0;
				for (int i = 0; i < betcode.length(); i++) {
					if (betcode.charAt(i) == '*') {
						index1 = i;
						PublicMethod.myOutput("--------------index1" + index1);
					}
				}
				String danma = GT.makeString("F47102", wayCode, betcode
						.substring(4, index1));
				String tuoma = GT.makeString("F47102", wayCode, betcode
						.substring(index1 + 1, betcode.length() - 1));
				betcode = "����: " + danma + "\n" + "����: " + tuoma + "\n";
			}

		}
	}

	// ר�ҷ���������ȡȫ����Ϣ 2010/7/4 �³�

	/**
	 * ר�ҷ���json����
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public String[] expertAnalysis() {

		String contents = null;
		String[] str = null;
		while (iretrytimes < 3 && iretrytimes > 0) {
			try {
				String re = jrtLot.analysis(sessionid);
				PublicMethod.myOutput("-----------------re:" + re);
				if (re != null && !re.equalsIgnoreCase("")) {
					JSONObject obj = new JSONObject(re);
					contents = obj.getString("content");
					// str=this.mySplict(contents,'|');//�и��ַ���
					str = mySplict(contents, '|');
				} else {
					str[0] = "00";
				}
				iretrytimes = 3;
			} catch (JSONException e) {
				iretrytimes--;
			}

		}
		iretrytimes = 2;

		// for(int i=0;i<analysis.length;i++){
		// Toast.makeText(context, analysis[i], Toast.LENGTH_SHORT).show();
		// }
		return str;
	}

	// �и��ַ������� ר�ҷ��� 2010/7/4 �³�
	/**
	 * �и��ַ������� ר�ҷ���
	 * 
	 * @param str
	 * @param chr
	 */
	private String[] mySplict(String str, char chr) {
		String[] data = null;
		try {
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
	/**
	 * �˻���ϸ��Ϣ��ȡ
	 * 
	 * @param index
	 * @return
	 */

	private String[] getAccountDetail(int index) {
		String[] accountdetail = null;
		try {

			JSONObject obj = jsonArray.getJSONObject(index);
			String amt = obj.getString("amt");
			String drawamt = obj.getString("drawamt");
			String blsign = obj.getString("blsign");
			String memo = obj.getString("memo");
			String memotemp = typeReset(memo);
			String balance = PublicMethod.changeMoney(obj.getString("balance"));
			String drawamtBalance = obj.getString("drawamtBalance");
			String Date = obj.getString("plattime");
			String[] str = { amt, drawamt, blsign, memotemp, balance,
					drawamtBalance, Date };
			accountdetail = str;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountdetail;
	}

	/**
	 * �˻���ϸ��Ϣ��ȡ �ŵ���ά������
	 * 
	 * @param 
	 * @return
	 */
	private String[][] getAccountDetail_array() {
		String[][] accountdetail_array = new String[jsonArray.length()][7];
		PublicMethod.myOutput("------------+++++++++++"
				+ accountdetail_array[0].length);
		PublicMethod.myOutput("------------++++++=======" + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			PublicMethod.myOutput("-------------@@@@@@@@@"
					+ getAccountDetail(0)[6]);

			accountdetail_array[i] = getAccountDetail(i);
			PublicMethod.myOutput("-------------@@@@@@@@@+++++++"
					+ accountdetail_array[i][0]);
		}
		PublicMethod.myOutput("-----------------getAccountDetail_array"
				+ accountdetail_array[0][0]);
		return accountdetail_array;

	}

	/**
	 * ׷�Ų�ѯ��Ϣ��ȡ
	 * 
	 * @param index
	 * @return
	 */
	public void showAddNum(int index) {
		try {
			JSONObject obj = jsonArray.getJSONObject(index);
			play_name = obj.getString("lotNo");
			betcode = obj.getString("betcode");
			betNum = obj.getString("betNum");
			batchNum = obj.getString("batchNum");
			lastNum = obj.getString("lastNum");
			addamount = obj.getString("amount");
			beginBatch = obj.getString("beginBatch");
			lastBatch = obj.getString("lastBatch");
			addednum = Integer.parseInt(batchNum) - Integer.parseInt(lastNum)
					+ "";
			addedamount = Integer.parseInt(addamount)
					* Integer.parseInt(addednum) + "";

			// sell_datetime=obj.getString("sell_datetime");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		PublicMethod.myOutput("@@@@@" + play_name + "@@@@" + batchcode + "@@@@"
				+ betNum + "@@@@@" + betcode);
		int wayCode = Integer.parseInt(betcode.substring(0, 2));

		PublicMethod.myOutput("--------------wayCode?????" + wayCode);

		beishu = betcode.substring(2, 4);

		if (play_name.equals("B001")) {
			if (wayCode == 00) {
				play_name = "˫ɫ��ʽ";
				String mp[] = GT.splitBetCode(betcode);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {

					betcode += (GT.makeString("F47104", wayCode, mp[i]
							.substring(4)) + "\n");
				}
			} else if (wayCode == 40 || wayCode == 50) {
				play_name = "˫ɫ���ϸ�ʽ";
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
				betcode = "������: " + danma + "\n" + "��������: " + tuoma + "\n"
						+ "����" + lanqiu + "\n";

			} else {
				play_name = "˫ɫ�������ʽ";
				int index1 = 0;// ���ҡ�*��
				int index2 = 0;// ����"~"
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

				betcode = "����: " + redball + "\n" + "����: " + blueball + "\n";
			}
			PublicMethod.myOutput("---------------@@@@@" + betcode);
		} else if (play_name.equals("D3")) {
			if (wayCode == 54) {
				play_name = "����3D����";
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
				betcode = "����: " + danma + "\n" + "����: " + tuoma + "\n";
				// ��ѡ��ʽ����� 2010/7/5 �³�
			} else if (wayCode == 00) {
				// 3D��ѡע���ʽ ���� �³� 20100714
				// if(wayCode==00){
				PublicMethod.myOutput("------intointointo--");
				PublicMethod.myOutput("------betcode0000------" + betcode);
				play_name = "����3Dֱѡ��ʽ";
				String mp[] = GT.splitBetCode(betcode);
				PublicMethod.myOutput("-----mp.length--------" + mp.length);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {
					PublicMethod.myOutput("--------????????????mp[]------"
							+ mp[i]);
					betcode = GT.makeString("F47103", wayCode, mp[i]
							.substring(4)
							+ "\n");
				}

				// }

			} else if (wayCode == 20) {
				// if(wayCode==00){
				// play_name="����3Dֱѡ��ʽ";
				// }
				// if(wayCode==20){
				play_name = "����3Dֱѡ��ʽ";
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
				betcode = "��λ: " + baiwei + "\n" + "ʮλ: " + shiwei + "\n"
						+ "��λ: " + gewei + "\n";

				// 3Dֱѡ��ʽ ע�����
			}

			else {
				// if(wayCode==00){
				// play_name="����3D��ʽ";
				//					
				// }
				if (wayCode == 01) {
					play_name = "����3D��3";
				} else if (wayCode == 02) {
					play_name = "����3D��6";
				} else if (wayCode == 10) {
					play_name = "����3Dֱѡ��ֵ";
				} else if (wayCode == 11) {
					play_name = "����3D��3��ֵ";
				} else if (wayCode == 12) {
					play_name = "����3D��6��ֵ";
				} else if (wayCode == 31) {
					play_name = "����3D��3��ʽ";
				} else if (wayCode == 31) {
					play_name = "����3D��6��ʽ";
				}
				String mp[] = GT.splitBetCode(betcode);
				betcode = "";
				for (int i = 0; i < mp.length; i++) {
					betcode += (GT.makeString("F47103", wayCode, mp[i]
							.substring(4)) + "\n");
				}
			}
		} else if (play_name.equals("QL730")) {
			if (wayCode == 00) {
				int index_q;
				String mp[] = GT.splitBetCode(betcode);
				play_name = "���ֲʵ�ʽ";
				betcode = "";
				for (int i = 0; i < mp.length; i++) {
					betcode += (GT.makeString("F47102", wayCode, mp[i]
							.substring(4)) + "\n");
				}
			} else if (wayCode == 10) {
				play_name = "���ֲʸ�ʽ";
				betcode = GT.makeString("F47102", wayCode, betcode.substring(5,
						betcode.length() - 1))
						+ "\n";
			} else if (wayCode == 20) {
				play_name = "���ֲʵ���";
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
				betcode = "����: " + danma + "\n" + "����: " + tuoma + "\n";
			}
			// String mp[]= GT.splitBetCode(betcode);

		}

	}

	/**
	 * �˻�������ʽ���� �³� 2010/7/3
	 * 
	 * @param str
	 *            �������˵Ĳ�������
	 */
	private String typeReset(String str) {
		String strTemp = "";

		if (str.equals("�㿨��ֵ") || str.equals("���п���ֵ") || str.equals("��ֵ")
				|| str.equals("��ʿ���ֵ") || str.equals("����ʿ���ֵ")) {
			strTemp = "��ֵ";
		} else if (str.equals("�û����ֿ�������")
				|| str.equals("�û�׷��Ͷע�ۿ�") || str.equals("�㿨��ֵ�۳�������")
				|| str.equals("��Ʊ���Ϳۿ�") || str.equals("�û�Ͷע�ۿ�")) {
			strTemp = "֧��";//str.equals("�û��ҽ�����") || 
		} else if (str.equals("�û��ҽ�����")) {
			strTemp = "����";
		} else if (str.equals("�û����ֿ����ֽ��")) {
			strTemp = "����";
		}

		return strTemp;
	}

	// �������ӿ�
	/**
	 * ������ʾ��
	 * 
	 * @param id
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG1_KEY: {
			progressdialog = new ProgressDialog(this);
			// progressdialog.setTitle("Indeterminate");
			progressdialog.setMessage("����������...");
			progressdialog.setIndeterminate(true);
			progressdialog.setCancelable(true);
			return progressdialog;
		}
		}
		return null;
	}

	@Override
	/**
	 * intent �ص�����
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			System.out.println("-------iType----"+iType);
			if(iType==1){
				accountDetail();
			}else{
			   UserCenterDetail();
			}
			// String str = text.getText().toString();
			//
			// if (getString(R.string.ruyihelper_balanceInquiry).equals(str)) {
			// showUserCenterBalanceInquiry();
			// }
			// if (getString(R.string.usercenter_winningCheck).equals(str)) {
			// showUserCenterWinningCheck();
			// }
			// if (getString(R.string.usercenter_bettingDetails).equals(str)) {
			// showUserCenterBettingDetails();
			// }
			// if (getString(R.string.usercenter_accountDetails).equals(str)) {
			// showUserCenterAccountDetails();
			// }
			// if (getString(R.string.usercenter_giftCheck).equals(str)) {
			// showUserCenterGiftCheck();
			// }
			// if (getString(R.string.usercenter_passwordChange).equals(str)) {
			// showUserCenterPasswordChange();
			// }
			// if
			// (getString(R.string.usercenter_trackNumberInquiry).equals(str)){
			// showUserCenterAddLotteryQuery();
			// }
			// showChangePasswordDialog();
			break;
		default:
			Toast.makeText(RuyiHelper.this, "δ��¼�ɹ�", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	/**
	 * �����޸�
	 * 
	 * @param id
	 */

	private void showChangePasswordDialog() {
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		phonenum = shellRW.getUserLoginInfo("phonenum");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		// ������ 7.4 �����޸ģ���ӵ�½���ж�
		if (sessionid.equals("")) {
			Intent intentSession = new Intent(RuyiHelper.this, UserLogin.class);
			// startActivity(intentSession);
			startActivityForResult(intentSession, 0);
		} else {

			showUserCenterPasswordChange();
		}
	}
//	20100803 �³� �û�������ϸ�ӱ�ǩ��������
	private void AccountDetailThread(final String type){
		System.out.println("-------++++====------");
		showDialog(DIALOG1_KEY);
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiHelper.this, "addInfo");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		Thread t = new Thread(new Runnable() {
			public void run() {
				String error_code = "00";
				System.out.println("================="+iretrytimes);
				while (iretrytimes < 3 && iretrytimes > 0) {
					System.out.println("================="+iretrytimes);
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
							startTime = (now.get(Calendar.YEAR) - 1) + ""
									+ 12 + dayNow;
						} else {
							startTime = now.get(Calendar.YEAR) + ""
									+ monthPre + dayNow;
						}
						PublicMethod.myOutput("--------------starttime"
								+ startTime);
						endTime = now.get(Calendar.YEAR) + monthNow
								+ dayNow;
						PublicMethod.myOutput("--------------endtime"
								+ endTime);
						// String re=jrtLot.accountDetailSelect(phonenum,
						// startTime, endTime, sessionid);
						System.out.println("-------type------"+type);
						String re = jrtLot.accountDetailSelect(phonenum,
								startTime, endTime, type,sessionid);
						PublicMethod.myOutput("???????????" + startTime
								+ "------" + endTime);
						// String
						// re=jrtLot.accountDetailSelect(phonenum,"20100608",
						// "20100708", sessionid);
						System.out.println("-----------------re:" + re);
//						PublicMethod.WriteSettings(RuyiHelper.this, re, "RE");
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

						iretrytimes = 3;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						iretrytimes--;
					}
				}
				iretrytimes = 2;

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
}
