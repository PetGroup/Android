package com.palmdream.RuyicaiAndroid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.palmdream.netintface.BettingInterface;
import com.palmdream.netintface.GT;
import com.palmdream.netintface.iHttp;
import com.palmdream.netintface.jrtLot;

/**
 * 
 * @author wangyl zhoulm ���⴫�顢�����ײ͡�����ѡ�Ų���
 * 
 */
public class RuyiExpressFeel extends Activity implements
		SeekBar.OnSeekBarChangeListener, MyDialogListener {

	/* �б�������adapter����String[] From�Ĵ���� */
	public final static String ICON = "ICON";/* ͼ�� */
	public final static String TITLE = "TITLE"; /* ���� */
	public final static String TITLETEXT = "TITLETEXT"; /* �������� */
	public final static String SUBSCRIBEBUTTON = "SUBSCRIBEBUTTON";
	public final static String EDITBUTTON = "EDITBUTTON";
	public final static String UNSUBSCRIBEBUTTON = "UNSUBSCRIBEBUTTON";

	// ���⴫�����б����ID
	public final static int ID_MAINRUYIEXPRESSFEEL = 1;/* ���⴫�����б� */
	public final static int ID_RUYIPACKAGE = 2; /* �����ײ� */
	public final static int ID_LUCKNUMBER = 3;/* ����ѡ�� */
	public final static int ID_RUYICHUANQING = 9;/* ���⴫�� */
	public final static int ID_RUYICHUANQING_SSQ = 10;/* ���⴫��˫ɫ�� */
	public final static int ID_RUYICHUANQING_FC3D = 11;/* ���⴫�鸣��3D */
	public final static int ID_RUYICHUANQING_QLC = 12;/* ���⴫�����ֲ� */

	// �����ײͲ��ֶ���״̬ trueΪ������falseΪδ����
	// boolean ssqFlag = false ;/*˫ɫ��*/
	// boolean fc3dFlag = true;/*����3D*/
	// boolean qlcFlag = false;/*���ֲ�*/
	boolean[] subscribeFlag = { true, true, true ,true,true};
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
	//wangyl 8.27 add pl3 and dlt
	String pl3amount = "";
	String dltamount = "";

	private ImageButton btnreturn;/* ���� */// 7.3�����޸ģ���Button����ImageButton

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
	//wangyl 8.25 ����������ʹ���͸
	TextView ruyichuanqing_dlt;//����͸
	TextView ruyichuanqing_pl3;//������
	LinearLayout ruyichuanqing_sub_view;
	//wangyl 8.25 ���⴫������Ż�
	TextView[] textViews = new TextView[5];//���⴫���ǩ��

	private static final String[] constrs = { "5", "4", "3", "2", "1" };
	private ArrayAdapter<String> adapter;
	//wangyl 8.25 ����ע�Ϳ�ɾ��
	/*// ���⴫��˫ɫ��
	Spinner spinner_ssq;
	String spinner_str_ssq;
	CheckBox cb_ssq_jixuan;
	CheckBox cb_ssq_zixuan;
	Button btn_sure_ssq;
	RelativeLayout relativeLayout_ssq;
	int isJixuanOrZixuan_ssq = 1;// 1Ϊ��ѡ��2Ϊ��ѡ
	boolean ischeck_ssq_jixuan = false;
	boolean ischeck_ssq_zixuan = true;
	// ���⴫�鸣��3D
	Spinner spinner_fc3d;
	String spinner_str_fc3d;
	CheckBox cb_fc3d_jixuan;
	CheckBox cb_fc3d_zixuan;
	Button btn_sure_fc3d;
	RelativeLayout relativeLayout_fc3d;
	int isJixuanOrZixuan_fc3d = 1;// 1Ϊ��ѡ��2Ϊ��ѡ
	boolean ischeck_fc3d = true;
	boolean ischeck_fc3d_jixuan = false;
	boolean ischeck_fc3d_zixuan = true;
	// ���⴫�����ֲ�
	Spinner spinner_qlc;
	String spinner_str_qlc;
	CheckBox cb_qlc_jixuan;
	CheckBox cb_qlc_zixuan;
	Button btn_sure_qlc;
	RelativeLayout relativeLayout_qlc;
	int isJixuanOrZixuan_qlc = 1;// 1Ϊ��ѡ��2Ϊ��ѡ
	boolean ischeck_qlc = true;
	boolean ischeck_qlc_jixuan = false;
	boolean ischeck_qlc_zixuan = true;*/
	
	boolean aState=true;//true ��ʾ����Ի��� false ����ʾ

	int iCurrentBtFlag;
	int iCurrentId;
	int viewId;

	// ����ѡ��
	public static final String[] chooseLuckLotteryNum_zhonglei = { "˫ɫ��",
			"����3D", "���ֲ�" ,"������","��������͸"};

	public static final String[] chooseLuckLotteryNum_title = { "˫ɫ��",
		"����3D", "���ֲ�", "������", "������"+"\n"+"��͸"};
	// ������ 7.7 �����޸ģ�����ѡ���´���
	public final static int LAYOUT_INDEX = 0;
	public final int CHECKBOX_INDEX = 100;

	Cursor cursor;
	int id = 0;

	public Integer[] gridIcon;
	public String[] gridIconName;
	/* Button xingzuo_btn , shengxiao_btn ,xingming_btn ,shengri_btn; */
	ImageView xingzuo_btn, shengxiao_btn, xingming_btn, shengri_btn; // ���Դ���
	String str;
	String type01, type02, type03, type04, type05, type06, type07, type08; // ��Ʊ����
	EditText editTextXingming, editTextYear, editTextMonth, editTextDay;
	AlertDialog.Builder builderXingming; // �����ĶԻ���
	// Integer y , m , d; //�ꡢ�¡���
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

	public static final Integer[] mIcon = { R.drawable.shuangseqiu,
			R.drawable.fucai, R.drawable.qilecai, R.drawable.pailiesan, R.drawable.daletou };    //zlm 8.9 �������������������͸ͼ��
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
			"˫����", "��з��", "ʨ����", "��Ů��", "������", "��Ы��", "������", "ħЫ��" };

	public static final String[] shengxiaoName = { "��", "ţ", "��", "��", "��",
			"��", "��", "��", "��", "��", "��", "��" };

	/**
	 * ����ѡ��(��ʾС��ע����Ǯ���Ľ��沿�ֵĶ���)
	 */
	public static final int BALL_WIDTH = 28;
	private static int[] aRedColorResId = { R.drawable.red };
	private static int[] aBlueColorResId = { R.drawable.blue };

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
	public final static int ID_CLLN_MAINLISTVIEW = 4;
	public final static int ID_CLLN_GRID_VIEW = 5;
	public final static int ID_CLLN_XINGMING_DIALOG_LISTVIEW = 6;
	public final static int ID_CLLN_SHENGRI_DIALOG_LISTVIEW = 7;
	public final static int ID_CLLN_BUTTON_SET = 8;
	public final static int ID_CLLN_SSQ_BUTTON_SET = 16;
	public final static int ID_CLLN_SHOWBALLMONRY = 17;
	public final static int ID_CLLN_SHOW_ZHIFU_DIALOG = 18;
	public final static int ID_CLLN_SHOW_TRADE_SUCCESS = 19;

	// ���� 2010/7/9�³�
	private static final int PROGRESS_VALUE = 0;
	ProgressDialog progressDialog;
	PopupWindow popupwindow;
	TextView text, mingCheng;

	// �ӿ�
	JSONArray jsonObject3;
	String lottery_type;
	int iretrytimes = 2;
	String re;
	String error_code;
	JSONObject obj;
	String phonenum;
	String sessionid;
	String tsubscribeIdssq;
	String tsubscribeIdfc3d;
	String tsubscribeIdqlc;
	//wangyl 8.27 add pl3 and dlt
	String tsubscribeIdpl3;
	String tsubscribeIddlt;
	String tsubscribeId;
	String textssq;
	String textfc3d;
	String textqlc;
	//wangyl 8.27 add pl3 and dlt
	String textpl3;
	String textdlt;
	ShellRWSharesPreferences shellRWtext;
	String lotterytype;
	String state;

	// ������ 7.5 �����޸ģ�����˳����Ĵ��롪���������
	private int iQuitFlag = 0; // �����˳�
	// private boolean iCallOnKeyDownFlag=false;

	// ��������ѡ��Ͷע������ 2010/7/4 �³�
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.getData().getString("get");
			switch (msg.what) {
			case 0:
				// Toast.makeText(getBaseContext(), "���¼",
				// Toast.LENGTH_LONG).show();
				// alert1("���¼");
				// ������7.9�޸��ײͽӿ�
				progressDialog.dismiss();// ȡ����ʾ��
				// ����Ϣд��preferences ����գ���д�� 2010/7/10 �³�
				ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
						RuyiExpressFeel.this, "addInfo");
				if (iCurrentBtFlag == -1) {// ��ѯ
					PublicMethod.myOutput("--------into chaxun-----"
							+ ssqamount + "---fc3damount-----" + fc3damount
							+ "---qlcamount----" + qlcamount);
					shellRW.setUserLoginInfo("ssqtext", "");
					shellRW.setUserLoginInfo("fc3dtext", "");
					shellRW.setUserLoginInfo("qlctext", "");
					//wangyl 8.27 add pl3 and dlt
					shellRW.setUserLoginInfo("pl3text", "");
					shellRW.setUserLoginInfo("dlttext", "");

					if (!ssqamount.equals("")) {
						// ShellRWSharesPreferences shellRW =new
						// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
						shellRW.setUserLoginInfo("ssqtext", ssqamount);
						PublicMethod.myOutput("-------------------handle------"
								+ shellRW.getUserLoginInfo("ssqtext"));
					}
					if (!fc3damount.equals("")) {
						// ShellRWSharesPreferences shellRW =new
						// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
						shellRW.setUserLoginInfo("fc3dtext", fc3damount);
						PublicMethod.myOutput("-------------------handle------"
								+ shellRW.getUserLoginInfo("fc3dtext"));
					}
					if (!qlcamount.equals("")) {
						// ShellRWSharesPreferences shellRW =new
						// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
						shellRW.setUserLoginInfo("qlctext", qlcamount);
						PublicMethod.myOutput("-------------------handle------"
								+ shellRW.getUserLoginInfo("qlctext"));
					}
					//wangyl 8.27 add pl3 and dlt
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
					//wangyl 8.27 add pl3 and dlt
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
						//wangyl 8.27 add pl3 and dlt
						else if(iTempLine == 3){
							shellRW.setUserLoginInfo("pl3text", ""+ ruyipackage_str_subscribe);
						}else if(iTempLine == 4){
							shellRW.setUserLoginInfo("dlttext", ""+ ruyipackage_str_subscribe);
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
						//wangyl 8.27 add pl3 and dlt
						else if(iTempLine == 3){
							shellRW.setUserLoginInfo("pl3text", ""+ ruyipackage_str);
						}else if(iTempLine == 4){
							shellRW.setUserLoginInfo("dlttext", ""+ ruyipackage_str);
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
						//wangyl 8.27 add pl3 and dlt
						else if(iTempLine == 3){
							shellRW.setUserLoginInfo("pl3text", "");
						}else if(iTempLine == 4){
							shellRW.setUserLoginInfo("dlttext", "");
						}
					}
				}
				showRuyiPackageListView();
				break;
			case 1:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ�����㣡", Toast.LENGTH_LONG)
						.show();
				break;
			case 2:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ѽ�����", Toast.LENGTH_LONG)
						.show();
				break;
			case 3:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "ϵͳ���㣬���Ժ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 4:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�޿����߼�����", Toast.LENGTH_LONG)
						.show();
				break;
			// case 5:
			// //��Ҫ���AlertDialogע��ʧ��
			// break;
			case 6:
				// //��Ҫ���AlertDialog��ʾ�û���¼�ɹ�
				progressDialog.dismiss();
				if(type06.equals("ssq") || type06.equals("fc3d") || type06.equals("qlc")){
				Toast.makeText(getBaseContext(), "Ͷע�ɹ���", Toast.LENGTH_LONG)
						.show();
				}else if(type06.equals("pl3") || type06.equals("cjdlt")){
					Toast.makeText(getBaseContext(), "Ͷע������", Toast.LENGTH_LONG)
					.show();
				}
				showListView(ID_CLLN_MAINLISTVIEW);      //zlm 7.28 �����޸�       ��������ѡ�����б�
				break;
			case 7:
				progressDialog.dismiss();

				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(RuyiExpressFeel.this,
						UserLogin.class);
				startActivity(intentSession);
				// Toast.makeText(getBaseContext(), "���¼",
				// Toast.LENGTH_LONG).show();
				// alert1("���¼");
				break;
			case 8:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)
						.show();
				break;
			case 9:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			// �¼ӵ���Ϣ����
			case 10:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�ײ��Ѷ��ƣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 11:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�û����㣡", Toast.LENGTH_LONG)
						.show();
				break;
			case 12:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ײ�ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 13:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�û����ײͼ�¼��", Toast.LENGTH_LONG)
						.show();
				break;
			case 14:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�˶�ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 15:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�ײ��޸�ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 16:
//				��ѯ��֮���޸Ķ��� �³� 20100729
				progressDialog.dismiss();
				if (viewId == 101 || viewId == 104 || viewId == 107 || viewId == 110 || viewId == 113) {
				    showRuyiPackageEdit(viewId);
//					showRuyiPackageEdit(view);
				}
				// �˶�
				if (viewId == 102 || viewId == 105 || viewId == 108 || viewId == 111 || viewId == 114) {
					// showRuyiPackageUnSubscribe(view,textView);
					showRuyiPackageUnSubscribe(viewId);
				}
//				showRuyiPackageUnSubscribe(viewId);
//				PackageUnSubscribe();
				break;
			case 17:
				 progressDialog.dismiss();
				 Toast.makeText(getBaseContext(), "Ͷע�ɹ���", Toast.LENGTH_LONG)
				.show();
				 break;
			case 18:
				 progressDialog.dismiss();
				 Toast.makeText(getBaseContext(), "��ƱͶע�У�", Toast.LENGTH_LONG)
				.show();
				 break;
			//wangyl 8.30 ����ײͶ����ӿ�
//			case 19:
//				 progressDialog.dismiss();
//				 Toast.makeText(getBaseContext(), "��¼ʧ��", Toast.LENGTH_LONG).show();
//				 break;	
			case 20:
				 progressDialog.dismiss();
				 Toast.makeText(getBaseContext(), "�ںŲ��Ϸ���", Toast.LENGTH_LONG).show();
				 break;
			case 21:
				 progressDialog.dismiss();
				 Toast.makeText(getBaseContext(), "�˻��쳣��", Toast.LENGTH_LONG).show();
				 break;
			}
			//				
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiExpressFeel.this, "addInfo");
		phonenum = shellRW.getUserLoginInfo("phonenum");
		sessionid = shellRW.getUserLoginInfo("sessionid");

		super.onCreate(savedInstanceState);
		// ��ʾ���⴫�����б�
		showListView(ID_MAINRUYIEXPRESSFEEL);

	}

	/**
	 * �б�ѡ��
	 * 
	 * @param int listviewid �б�ID
	 */
	private void showListView(int listviewid) {
		switch (listviewid) {
		// ���⴫�����б�
		case ID_MAINRUYIEXPRESSFEEL:
			iQuitFlag = 0; // ������ 7.5 �����޸ģ������˳�
			// iCallOnKeyDownFlag=false;
			showMainRuyiExpressFeelListView();
			break;
		case ID_RUYIPACKAGE:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			// handle�����ȡ�ײ���Ϣ 0709 wyl
			// showRuyiPackageListView();
			//cc 8.11
//			showDialog(PROGRESS_VALUE);
			iCurrentBtFlag = -1;
			ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
					RuyiExpressFeel.this, "addInfo");
			sessionid = shellRW.getUserLoginInfo("sessionid");
			if (sessionid.equalsIgnoreCase("")) {
				iCurrentBtFlag = -2;
				Message msg = new Message();
				msg.what = 0;
				handler.sendMessage(msg);
			}
			iHttp.whetherChange=false;
			setFlag();
			break;
		case ID_CLLN_MAINLISTVIEW:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			showCLLNMainListView();
			break;
		case ID_RUYICHUANQING:
			iQuitFlag = 10; // ������ 7.5 �����޸ģ����������б�
			// iCallOnKeyDownFlag=false;
			// wangyl 7.12 �ж��Ƿ��¼
			ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
					RuyiExpressFeel.this, "addInfo");
			String sessionid = pre.getUserLoginInfo("sessionid");
			if (sessionid.equals("")) {
				Intent intentSession = new Intent(RuyiExpressFeel.this,
						UserLogin.class);
				startActivity(intentSession);
				return;
			} else {
				showRuYiChuanQing();
			}
			break;
			//wangyl 8.25 ����ע�Ϳ�ɾ��
		/*case ID_RUYICHUANQING_SSQ:
			showRuyichuanqingSSQ();
			break;
		case ID_RUYICHUANQING_FC3D:
			showRuyichuanqingFC3D();
			break;
		case ID_RUYICHUANQING_QLC:
			showRuyichuanqingQLC();
			break;*/
		}

	}

	/**
	 * ����ѡ���б�ѡ��
	 * 
	 * @param listviewid
	 *            �б�ID
	 * @param aGameType
	 *            ��Ʊ����
	 */
	private void showXingYunXuanHaoListView(int listviewid, String aGameType) {
		switch (listviewid) {
		case ID_CLLN_SHOWBALLMONRY:
			iQuitFlag = 20; // ������ 7.5 �����޸ģ�����������ѡ�����б�
			// iCallOnKeyDownFlag=false;
			chooseLuckNumShowBallMoney(aGameType);
			break;
		case ID_CLLN_SHOW_ZHIFU_DIALOG:
			showAgreeAndPayDialog(aGameType);
			break;
		case ID_CLLN_SHOW_TRADE_SUCCESS:
			showTradeSuccess();
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
				showListView(ID_MAINRUYIEXPRESSFEEL);
				break;
			case 20:
				setContentView(R.layout.choose_luck_lottery_num_main);
				showListView(ID_CLLN_MAINLISTVIEW);
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
	/**
	 * ȷ��
	 */
	public void onOkClick(int[] nums) {
		// TODO Auto-generated method stub
		// �˳�
		this.finish();
	}

	/**
	 *���⴫�����б�
	 */
	private void showMainRuyiExpressFeelListView() {

		setContentView(R.layout.ruyihelper_listview);

		ListView listview = (ListView) findViewById(R.id.ruyihelper_listview_id);
		// ����
		/*
		 * TextView title = new TextView(this);
		 * title.setText(getString(R.string.ruyichuangqing));
		 * title.setTextColor(Color.BLACK); LinearLayout myLinearLayout = new
		 * LinearLayout(this); LinearLayout.LayoutParams paramtitle = new
		 * LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,
		 * LinearLayout.LayoutParams.WRAP_CONTENT );
		 * myLinearLayout.setGravity(Gravity.CENTER);
		 * myLinearLayout.addView(title,paramtitle);
		 * listview.addHeaderView(myLinearLayout);
		 * registerForContextMenu(listview);
		 */

		// ����Դ
		list = getListForMainExpressFeelAdapter();

		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.ruyihelper_listview_icon_item, new String[] { ICON,
						TITLE }, new int[] { R.id.ruyihelper_icon,
						R.id.ruyihelper_icon_text });

		listview.setAdapter(adapter);

		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				texttitle = (TextView) view
						.findViewById(R.id.ruyihelper_icon_text);
				String str = texttitle.getText().toString();
				/* ���⴫�� */
				if (getString(R.string.ruyichuangqing).equals(str)) {
					showListView(ID_RUYICHUANQING);
				}
				/* �����ײ� */
				if (getString(R.string.ruyitaocan).equals(str)) {
					showListView(ID_RUYIPACKAGE);
				}
				/* ����ѡ�� */
				if (getString(R.string.xingyunxuanhao).equals(str)) {
					showListView(ID_CLLN_MAINLISTVIEW);
				}
			}

		};
		listview.setOnItemClickListener(clickListener);

	}

	/**
	 * �����ײ��б�
	 */
	private void showRuyiPackageListView() {
		// setFlag(); // ��ȡ����״̬
		setContentView(R.layout.ruyipackage_listview);

		// ����
		// ������7.3�����޸ģ���Button����ImageButton
		ImageView imageview = (ImageView) findViewById(R.id.ruyipackage_btn_return);
		imageview.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
				showListView(ID_MAINRUYIEXPRESSFEEL);
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

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d("TAG", "onItemClick id:" + arg2);
			}
		});

		listview.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.d("TAG", "onItemSelected id:" + arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				Log.d("TAG", "onNothingSelected");
			}

		});

	}

	/*
	 * public void setRuyiPackageListView(){
	 * 
	 * setFlag();
	 * PublicMethod.myOutput("---------setRuyiPackageListView--------");
	 * 
	 * //�������״̬
	 * 
	 * LayoutInflater mInflater = LayoutInflater.from(this); View convertView =
	 * mInflater.inflate(R.layout.ruyipackage_listview, null); textview =
	 * (TextView) convertView.findViewById(R.id.ruyipackage_text);
	 * 
	 * ListView listview = (ListView)convertView.
	 * findViewById(R.id.ruyipackage_listview_id); for(int ii=0;ii<3;ii++){
	 * PublicMethod.myOutput("-----ff--"+subscribeFlag[ii]); } //������
	 * ruyiPackageAdapter = new RuyiPackageEfficientAdapter(this);
	 * 
	 * listview.setAdapter(ruyiPackageAdapter);
	 * 
	 * listview.setOnItemClickListener(new OnItemClickListener(){
	 * 
	 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
	 * arg2, long arg3) { Log.d("TAG","onItemClick id:"+arg2); } });
	 * 
	 * listview.setOnItemSelectedListener(new OnItemSelectedListener(){
	 * 
	 * @Override public void onItemSelected(AdapterView<?> arg0, View arg1, int
	 * arg2, long arg3) { Log.d("TAG","onItemSelected id:"+arg2); }
	 * 
	 * @Override public void onNothingSelected(AdapterView<?> arg0) {
	 * Log.d("TAG","onNothingSelected"); }
	 * 
	 * }); }
	 */
	/**
	 * �����ײ��б�������
	 */
	public class RuyiPackageEfficientAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // �������б���
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
					RuyiExpressFeel.this, "addInfo");
			sessionid = shellRW.getUserLoginInfo("sessionid");
			if (sessionid.equals("")) {
				holderText[0] = "";
				holderText[1] = "";
				holderText[2] = "";
				holderText[3] = "";
				holderText[4] = "";
			} else {
				// ��ʾ�Ѷ����ײ�
				ShellRWSharesPreferences shellRWtext = new ShellRWSharesPreferences(
						RuyiExpressFeel.this, "addInfo");
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
				PublicMethod.myOutput("-------holderText [0]textqlc--------"+ textpl3);
				
				textdlt = shellRWtext.getUserLoginInfo("dlttext");
				PublicMethod.myOutput("-------holderText [0]textqlc--------"+ textdlt);
				if (textssq != null && !textssq.equalsIgnoreCase("")) {
					holderText[0] = "��ѡÿ��" + textssq + "Ԫ�ײ�";
					subscribeFlag[0] = false;
					PublicMethod.myOutput("------holderText [0]--------"
							+ holderText[0]);
				} else {
					holderText[0] = "";
					subscribeFlag[0] = true;
					// holderText [0]="��ѡÿ��"+ssqamount+"Ԫ�ײ�";
					// PublicMethod.myOutput("------holderText [0]else--------"+holderText
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
					// PublicMethod.myOutput("------holderText [1]else--------"+holderText
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
					// PublicMethod.myOutput("------holderText [2]else--------"+holderText
					// [2]);
				}
				//wangyl 8.27 add pl3 and dlt
				if (textpl3 != null && !textpl3.equalsIgnoreCase("")) {
					holderText[3] = "��ѡÿ��" + textpl3 + "Ԫ�ײ�";
					subscribeFlag[3] = false;
					PublicMethod.myOutput("------holderText [3]--------"+ holderText[3]);
				} else {
					holderText[3] = "";
					subscribeFlag[3] = true;
				}
				if (textdlt != null && !textdlt.equalsIgnoreCase("")) {
					holderText[4] = "��ѡÿ��" + textdlt + "Ԫ�ײ�";
					subscribeFlag[4] = false;
					PublicMethod.myOutput("------holderText [4]--------"+ holderText[4]);
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
							// PublicMethod.myOutput("-----"+states.length+" "+iadapter);
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

// �³� �ײͲ�ѯ 20100729
private void Qurey(){
	PublicMethod.myOutput("??????????");
	ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
			RuyiExpressFeel.this, "addInfo");
	phonenum = shellRW.getUserLoginInfo("phonenum");
	sessionid = shellRW.getUserLoginInfo("sessionid");
	// �ȸ�ֵΪ��
	ssqamount = "";
	fc3damount = "";
	qlcamount = "";
	//wangyl 8.27 add pl3 and dlt
	pl3amount = "";
	dltamount = "";
	if(aState=true){
	 showDialog(PROGRESS_VALUE);
	Thread t = new Thread(new Runnable() {

		@Override
		public void run() {
			while (iretrytimes < 3 && iretrytimes > 0) {
				try {
					try {
						PublicMethod.myOutput("enterrrrrrr");
						re = jrtLot.packageSelect(sessionid);
						PublicMethod
								.myOutput("--------phonenum----------showRuyiPackageListView-------------------"
										+ phonenum);
						PublicMethod
								.myOutput("---------sessionid------------showRuyiPackageListView-----------------"
										+ sessionid);
						PublicMethod.myOutput("---------sessionid------------showRuyiPackageListView-----------------"
								+ sessionid);
						PublicMethod.myOutput("-------re:" + re);
						PublicMethod.myOutput("-------re:" + re);

						obj = new JSONObject(re);

						error_code = obj.getString("error_code");
						// String state4 = obj.getString("state");//String
						// PublicMethod.myOutput("-------state------"+state);
						// PublicMethod.myOutput("-------error_code------"+error_code);
                        PublicMethod.myOutput("error_code"+error_code);
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
							PublicMethod.myOutput("--------lotterytype----"+lotterytype);

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
//							��ѯ���ٵ��� �˶� �޸� 20100729 �³�
							msg.what = 16;
							handler.sendMessage(msg);
						} else if (error_code.equals("060004")||error_code.equals("070002")) {
//							cc 8.11
							iCurrentBtFlag = -1;
							Message msg = new Message();
							msg.what = 0;
							handler.sendMessage(msg);
						}
						// else if(error_code.equals("070002")){
						// Message msg=new Message();
						// msg.what=7;
						// handler.sendMessage(msg);
						// // �޷���ȡ�û���Ϣ
						// }
						iretrytimes = 3;
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
								PublicMethod.myOutput("--------lotterytype1----"+lotterytype1);
								// tsubscribeIdssq=obj.getString("tsubscribeId");
								// ˫ɫ�� ��������ƶ���if�� 2010/7/10 �³�

								if (state1.equals("����")
										&& lotterytype1.equals("B001")) {
									// ssqFlag = true;
									// ������ʱ��Ǯ������ˮ��д�� 2010/7/10 �³�
									tsubscribeIdssq = obj
											.getString("tsubscribeId");
									PublicMethod.myOutput("----tsubscribeIdssq------"+tsubscribeIdssq);
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
							// PublicMethod.myOutput("-------error_code------"+i+"------------"+error_code);
							// ˫ɫ��
							// if (state.equals("����") &&
							// lotterytype.equals("B001")) {
							// ssqFlag = true;
							// PublicMethod.myOutput("-------ssqFlag------"+i+"------------"+ssqFlag);
							// break;
							// }else{
							// ssqFlag = false;
							// }
							// if (state1.equals("����") &&
							// lotterytype1.equals("B001")) {
							// //ssqFlag = true;
							// subscribeFlag[0]=false;
							// ssqamount = amount+"";
							// ShellRWSharesPreferences shellRW =new
							// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
							// shellRW.setUserLoginInfo("textssq",
							// "��ѡÿ��"+ssqamount+"Ԫ�ײ�");
							// PublicMethod.myOutput("-------subscribeFlag[0]------B001----------"+subscribeFlag[0]);
							// i=jsonObject3.length();
							// }else{
							// //ssqFlag = false;
							// subscribeFlag[0]=true;
							// PublicMethod.myOutput("-------subscribeFlag[0]------B001----------"+subscribeFlag[0]);
							// }

						}
						for (int i = 0; i < jsonObject3.length(); i++) {

							// PublicMethod.myOutput("---------jsonObject---------"+i);
							obj = jsonObject3.getJSONObject(i);
							// �ƶ���if����� 2010/7/10 �³�
							// String state2 = obj.getString("state");
							// String lotterytype2 = obj.getString("lotNo");
							// String amount_str = obj.getString("amount");
							// int amount =
							// Integer.parseInt(amount_str)/100;
							// PublicMethod.myOutput("-------lotterytype2------"+i+"------------"+lotterytype2);
							// PublicMethod.myOutput("-------state2------"+i+"------------"+state2);

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
								// String amount_str =
								// obj.getString("amount");
								// int amount =
								// Integer.parseInt(amount_str)/100;
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
								
								PublicMethod.myOutput("-------tsubscribeId------"
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
									// ShellRWSharesPreferences shellRW =new
									// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
									// shellRW.setUserLoginInfo("textfc3d",
									// "��ѡÿ��"+fc3damount+"Ԫ�ײ�");
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

							// error_code = obj.getString("error_code");
							// PublicMethod.myOutput("-------error_code------"+i+"------------"+error_code);

							// ����3D

							// if (state.equals("����") &&
							// lotterytype.equals("D3")) {
							// fc3dFlag = true;
							// PublicMethod.myOutput("-------fc3dFlag------"+i+"------------"+fc3dFlag);
							// break;
							// }else{
							// fc3dFlag = false;
							// }
							// if (state2.equals("����") &&
							// lotterytype2.equals("D3")) {
							// //fc3dFlag = true;
							// subscribeFlag[1]=false;
							// fc3damount = amount+"";
							// ShellRWSharesPreferences shellRW =new
							// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
							// shellRW.setUserLoginInfo("textfc3d",
							// "��ѡÿ��"+fc3damount+"Ԫ�ײ�");
							// PublicMethod.myOutput("-------subscribeFlag[1]---------D3-------"+subscribeFlag[1]);
							// i=jsonObject3.length();
							// }else{
							// //fc3dFlag = false;
							// subscribeFlag[1]=true;
							// PublicMethod.myOutput("-------subscribeFlag[1]---------D3-------"+subscribeFlag[1]);
							// }

						}
						for (int i = 0; i < jsonObject3.length(); i++) {

							// PublicMethod.myOutput("---------jsonObject---------"+i);
							obj = jsonObject3.getJSONObject(i);
							// String state3 = obj.getString("state");
							// String lotterytype3 = obj.getString("lotNo");
							// String amount_str = obj.getString("amount");
							// int amount =
							// Integer.parseInt(amount_str)/100;
							// PublicMethod.myOutput("-------lotterytype3------"+i+"------------"+lotterytype3);
							// PublicMethod.myOutput("-------state3------"+i+"------------"+state3);

							if (("000000").equals(error_code)) {
								// tsubscribeIdqlc=obj.getString("tsubscribeId");

								// ��������ƶ���if�� 2010/7/10 �³�
								String state3 = obj.getString("state");
								String lotterytype3 = obj
										.getString("lotNo");
								// String amount_str =
								// obj.getString("amount");
								// int amount =
								// Integer.parseInt(amount_str)/100;

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
								PublicMethod
										.myOutput("-------tsubscribeId------"
												+ i
												+ "------------"
												+ tsubscribeId);
							}


						}
						//wangyl 8.27 add pl3 and dlt
						for (int i = 0; i < jsonObject3.length(); i++) {

							obj = jsonObject3.getJSONObject(i);

							if (("000000").equals(error_code)) {
								String state4 = obj.getString("state");
								String lotterytype4 = obj.getString("lotNo");

								if (state4.equals("����")&& lotterytype4.equals("PL3_33")) {
									// ������ʱ��Ǯ������ˮ��д��
									tsubscribeIdpl3 = obj.getString("tsubscribeId");
									subscribeFlag[3] = false;
									String amount_str = obj.getString("amount");
									int amount = Integer.parseInt(amount_str) / 100;
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
								String lotterytype5 = obj.getString("lotNo");

								if (state5.equals("����")&& lotterytype5.equals("DLT_23529")) {
									// ������ʱ��Ǯ������ˮ��д��
									tsubscribeIddlt = obj.getString("tsubscribeId");
									subscribeFlag[4] = false;
									String amount_str = obj.getString("amount");
									int amount = Integer.parseInt(amount_str) / 100;
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
						handler.sendMessage(msg);
					}
					iretrytimes = 3;
				} catch (JSONException e) {
					e.printStackTrace();
					iretrytimes--;
					error_code="00";
				}
			}
			
			if(error_code.equalsIgnoreCase("00")&&iHttp.whetherChange == true){
				Message msg = new Message();
				msg.what = 8;
				handler.sendMessage(msg);
			}
//			�����Ƿ�ı�������ж� �³� 8.11
			if (iretrytimes == 0 && iHttp.whetherChange == false) {
				iHttp.whetherChange = true;
				if (iHttp.conMethord == iHttp.CMWAP) {
					iHttp.conMethord = iHttp.CMNET;
				} else {
					iHttp.conMethord = iHttp.CMWAP;
				}
				iretrytimes=2;
				PublicMethod.myOutput("=====qierudian=Qurey()====");
				Qurey();
			}
			iretrytimes = 2;
		
		}

	});
	t.start();
	}
	aState=true;
					
}
    /**
	 * �����ײ��и������ֵĶ���״̬
	 */
	private void setFlag() {
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiExpressFeel.this, "addInfo");
		phonenum = shellRW.getUserLoginInfo("phonenum");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		// �ȸ�ֵΪ��
		ssqamount = "";
		fc3damount = "";
		qlcamount = "";
		//wangyl 8.27 add pl3 and dlt
		pl3amount = "";
		dltamount = "";
		showDialog(PROGRESS_VALUE);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (iretrytimes < 3 && iretrytimes > 0) {
					try {
						try {
							re = jrtLot.packageSelect(sessionid);
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
								//wangyl 8.27 add pl3 and dlt
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
								handler.sendMessage(msg);
							} else if (error_code.equals("060004")) {
								Message msg = new Message();
								msg.what = 0;
								handler.sendMessage(msg);
							}
							// else if(error_code.equals("070002")){
							// Message msg=new Message();
							// msg.what=7;
							// handler.sendMessage(msg);
							// // �޷���ȡ�û���Ϣ
							// }
							iretrytimes = 3;
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
								// PublicMethod.myOutput("-------error_code------"+i+"------------"+error_code);
								// ˫ɫ��
								// if (state.equals("����") &&
								// lotterytype.equals("B001")) {
								// ssqFlag = true;
								// PublicMethod.myOutput("-------ssqFlag------"+i+"------------"+ssqFlag);
								// break;
								// }else{
								// ssqFlag = false;
								// }
								// if (state1.equals("����") &&
								// lotterytype1.equals("B001")) {
								// //ssqFlag = true;
								// subscribeFlag[0]=false;
								// ssqamount = amount+"";
								// ShellRWSharesPreferences shellRW =new
								// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
								// shellRW.setUserLoginInfo("textssq",
								// "��ѡÿ��"+ssqamount+"Ԫ�ײ�");
								// PublicMethod.myOutput("-------subscribeFlag[0]------B001----------"+subscribeFlag[0]);
								// i=jsonObject3.length();
								// }else{
								// //ssqFlag = false;
								// subscribeFlag[0]=true;
								// PublicMethod.myOutput("-------subscribeFlag[0]------B001----------"+subscribeFlag[0]);
								// }

							}
							for (int i = 0; i < jsonObject3.length(); i++) {

								// PublicMethod.myOutput("---------jsonObject---------"+i);
								obj = jsonObject3.getJSONObject(i);
								// �ƶ���if����� 2010/7/10 �³�
								// String state2 = obj.getString("state");
								// String lotterytype2 = obj.getString("lotNo");
								// String amount_str = obj.getString("amount");
								// int amount =
								// Integer.parseInt(amount_str)/100;
								// PublicMethod.myOutput("-------lotterytype2------"+i+"------------"+lotterytype2);
								// PublicMethod.myOutput("-------state2------"+i+"------------"+state2);

								if (("000000").equals(error_code)) {
									tsubscribeIdfc3d = obj
											.getString("tsubscribeId");
									// ��������ƶ���if�� 2010/7/10 �³�
									String state2 = obj.getString("state");
									String lotterytype2 = obj
											.getString("lotNo");
									// String amount_str =
									// obj.getString("amount");
									// int amount =
									// Integer.parseInt(amount_str)/100;
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
										// ShellRWSharesPreferences shellRW =new
										// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
										// shellRW.setUserLoginInfo("textfc3d",
										// "��ѡÿ��"+fc3damount+"Ԫ�ײ�");
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

								// error_code = obj.getString("error_code");
								// PublicMethod.myOutput("-------error_code------"+i+"------------"+error_code);

								// ����3D

								// if (state.equals("����") &&
								// lotterytype.equals("D3")) {
								// fc3dFlag = true;
								// PublicMethod.myOutput("-------fc3dFlag------"+i+"------------"+fc3dFlag);
								// break;
								// }else{
								// fc3dFlag = false;
								// }
								// if (state2.equals("����") &&
								// lotterytype2.equals("D3")) {
								// //fc3dFlag = true;
								// subscribeFlag[1]=false;
								// fc3damount = amount+"";
								// ShellRWSharesPreferences shellRW =new
								// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
								// shellRW.setUserLoginInfo("textfc3d",
								// "��ѡÿ��"+fc3damount+"Ԫ�ײ�");
								// PublicMethod.myOutput("-------subscribeFlag[1]---------D3-------"+subscribeFlag[1]);
								// i=jsonObject3.length();
								// }else{
								// //fc3dFlag = false;
								// subscribeFlag[1]=true;
								// PublicMethod.myOutput("-------subscribeFlag[1]---------D3-------"+subscribeFlag[1]);
								// }

							}
							for (int i = 0; i < jsonObject3.length(); i++) {

								// PublicMethod.myOutput("---------jsonObject---------"+i);
								obj = jsonObject3.getJSONObject(i);
								// String state3 = obj.getString("state");
								// String lotterytype3 = obj.getString("lotNo");
								// String amount_str = obj.getString("amount");
								// int amount =
								// Integer.parseInt(amount_str)/100;
								// PublicMethod.myOutput("-------lotterytype3------"+i+"------------"+lotterytype3);
								// PublicMethod.myOutput("-------state3------"+i+"------------"+state3);

								if (("000000").equals(error_code)) {
									// tsubscribeIdqlc=obj.getString("tsubscribeId");

									// ��������ƶ���if�� 2010/7/10 �³�
									String state3 = obj.getString("state");
									String lotterytype3 = obj
											.getString("lotNo");
									// String amount_str =
									// obj.getString("amount");
									// int amount =
									// Integer.parseInt(amount_str)/100;

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

								// error_code = obj.getString("error_code");
								// PublicMethod.myOutput("-------error_code------"+i+"------------"+error_code);

								// ���ֲ�
								// if (state.equals("����") &&
								// lotterytype.equals("QL730")) {
								// qlcFlag = true;
								// PublicMethod.myOutput("-------qlcFlag------"+i+"------------"+qlcFlag);
								// break;
								// }else{
								// qlcFlag = false;
								// }
								// //���ֲ�
								// if (state3.equals("����") &&
								// lotterytype3.equals("QL730")) {
								// //qlcFlag = true;
								// subscribeFlag[2]=false;
								// qlcamount = amount+"";
								// ShellRWSharesPreferences shellRW =new
								// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
								// shellRW.setUserLoginInfo("textqlc",
								// "��ѡÿ��"+qlcamount+"Ԫ�ײ�");
								// PublicMethod.myOutput("-------subscribeFlag[2]--------QL730-----------"+subscribeFlag[2]);
								// i=jsonObject3.length();
								// }else{
								// //qlcFlag = false;
								// subscribeFlag[2]=true;
								// PublicMethod.myOutput("-------subscribeFlag[2]--------QL730-----------"+subscribeFlag[2]);
								// }

							}
							//wangyl 8.27 add pl3 and dlt
							for (int i = 0; i < jsonObject3.length(); i++) {

								obj = jsonObject3.getJSONObject(i);

								if (("000000").equals(error_code)) {
									String state4 = obj.getString("state");
									String lotterytype4 = obj.getString("lotNo");

									if (state4.equals("����")&& lotterytype4.equals("PL3_33")) {
										// ������ʱ��Ǯ������ˮ��д��
										tsubscribeIdpl3 = obj.getString("tsubscribeId");
										subscribeFlag[3] = false;
										String amount_str = obj.getString("amount");
										int amount = Integer.parseInt(amount_str) / 100;
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
									String lotterytype5 = obj.getString("lotNo");

									if (state5.equals("����")&& lotterytype5.equals("DLT_23529")) {
										// ������ʱ��Ǯ������ˮ��д��
										tsubscribeIddlt = obj.getString("tsubscribeId");
										subscribeFlag[4] = false;
										String amount_str = obj.getString("amount");
										int amount = Integer.parseInt(amount_str) / 100;
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
							handler.sendMessage(msg);
						}
						iretrytimes = 3;
					} catch (JSONException e) {
						e.printStackTrace();
						iretrytimes--;
                        error_code="00";
					}
				}
				if(error_code.equalsIgnoreCase("00")&&iHttp.whetherChange == true){
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);
				}
//				�����Ƿ�ı�������ж� �³� 8.11
				if (iretrytimes == 0 && iHttp.whetherChange == false) {
					iHttp.whetherChange = true;
					if (iHttp.conMethord == iHttp.CMWAP) {
						iHttp.conMethord = iHttp.CMNET;
					} else {
						iHttp.conMethord = iHttp.CMWAP;
					}
					iretrytimes=2;
					PublicMethod.myOutput("=====qierudian=setFlag()====");
					setFlag();
				}
				iretrytimes = 2;
			}

		});
		t.start();
	}

	/**
	 * ������⴫�����б�������������Դ
	 */
	private List<Map<String, Object>> getListForMainExpressFeelAdapter() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
		/* ���⴫�� */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.ruyichuanqing);
		map.put(TITLE, getString(R.string.ruyichuangqing));
		list.add(map);

		/* �����ײ� */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.ruyitaocan);
		map.put(TITLE, getString(R.string.ruyitaocan));
		list.add(map);

		/* ����ѡ�� */
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.xingyunxuanhao);
		map.put(TITLE, getString(R.string.xingyunxuanhao));
		list.add(map);

		return list;
	}

	/**
	 * ��������ײ��б�������������Դ
	 */
	private void showRuyipackageImageGroup(View view) {
//		int viewId = view.getId();
		viewId = view.getId();
		// ����
		if (viewId == 100 || viewId == 103 || viewId == 106 || viewId == 109 || viewId == 112) {
			// showRuyiPackageSubscribe(view,textView);
//			showRuyiPackageSubscribe(view);
			iHttp.whetherChange=false;
			showRuyiPackageSubscribe(viewId);// cc 8.11
		}else{
//			�Ȳ�ѯ��֮���޸Ķ��� �³� 20100729
			iHttp.whetherChange=false;
			Qurey();
		// �޸�
//		if (viewId == 101 || viewId == 104 || viewId == 107) {
//			// showRuyiPackageEdit(view,textView);
//			 Qurey();
////			showRuyiPackageEdit(view);
//		}
//		// �˶�
//		if (viewId == 102 || viewId == 105 || viewId == 108) {
//			// showRuyiPackageUnSubscribe(view,textView);
//			
////			showRuyiPackageUnSubscribe(view);
//			Qurey();
////			showRuyiPackageUnSubscribe(viewId);
		}
	}

	// ������7.9�޸��ײͽӿ�
	/**
	 * �����ײ����˶���ť��ִ�в���
	 * 
	 * @param view
	 *            ������İ�ť
	 */
//	�˶��޸Ĳ���  �³� 20100729
//	private void showRuyiPackageUnSubscribe(View view/* ,TextView textView */) {
	private void showRuyiPackageUnSubscribe(int aViewId/* ,TextView textView */) {
		
//		boolean isatus = states[(view.getId() - 100) / 3][(view.getId() - 100) % 3];
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
				RuyiExpressFeel.this, "addInfo");
		textssq = shellRWtext.getUserLoginInfo("ssqtext");
		textfc3d = shellRWtext.getUserLoginInfo("fc3dtext");
		textqlc = shellRWtext.getUserLoginInfo("qlctext");
		//wangyl 8.27 add pl3 and dlt
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
		//wangyl 8.27 add pl3 and dlt
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
				RuyiExpressFeel.this, "addInfo");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		if (sessionid.equals("")) {
			Intent intentSession = new Intent(RuyiExpressFeel.this,
					UserLogin.class);
			startActivity(intentSession);
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					RuyiExpressFeel.this);
			builder.setTitle(R.string.ruyipackage_unsubscribe);
			builder.setMessage(getString(R.string.ruyipackage_unsubscirbe_sure)
					+ "\n" + packageName + text_str);

			// ȷ��
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							 showDialog(PROGRESS_VALUE);
							Thread t = new Thread(new Runnable() {

								@Override
								public void run() {
									while (iretrytimes < 3 && iretrytimes > 0) {

										PublicMethod
												.myOutput("-------------tsubscribeId------------------"
														+ tsubscribeId);
										PublicMethod
												.myOutput("-------------sessionid------------------"
														+ sessionid);

										re = jrtLot.packageFreeze(sessionid,
												tsubscribeId);// login_User,

										try {
											obj = new JSONObject(re);
											error_code = obj
													.getString("error_code");
											PublicMethod
													.myOutput("---------unsub-----------"
															+ error_code);
											iretrytimes = 3;
										} catch (JSONException e) {
											e.printStackTrace();
											iretrytimes--;

										}
									}
									iretrytimes = 2;
									if (error_code.equals("000000")) {
										// Toast.makeText(RuyiExpressFeel.this,
										// "�˶��ײͳɹ�����",
										// Toast.LENGTH_SHORT).show();
										// textview.setText("");
										// Flag = false ;//Ӧ���޸ĸ��ײ͵Ķ���״̬

										ShellRWSharesPreferences shellRWtextunsub = new ShellRWSharesPreferences(
												RuyiExpressFeel.this, "addInfo");
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
										if (getString(R.string.qilecai).equals(packageName)) {
											shellRWtextunsub.setUserLoginInfo("qlctext", "");
										}
										
										//wangyl 8.27 add pl3 and dlt
										// ������
										if (getString(R.string.pailie3).equals(packageName)) {
											shellRWtextunsub.setUserLoginInfo("pl3text", "");
										}
										// ����͸
										if (getString(R.string.daletou).equals(packageName)) {
											shellRWtextunsub.setUserLoginInfo("dlttext", "");
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
										//wangyl 8.27 add pl3 and dlt
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

										// setFlag();
										// showRuyiPackageListView();
										Message msg = new Message();
										msg.what = 0;
										handler.sendMessage(msg);

									} else if (error_code.equals("070002")) {
										Message msg = new Message();
										msg.what = 7;
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "���¼", Toast.LENGTH_SHORT).show();
									}
									// else if(error_code.equals("350001")){
									// Message msg=new Message();
									// msg.what=10;
									// handler.sendMessage(msg);
									// // Toast.makeText(RuyiExpressFeel.this,
									// "�ײ�״̬Ϊ��ͣ��ע���������ٴζ���",
									// Toast.LENGTH_SHORT).show();
									// }
									else if (error_code.equals("060004")) {
										Message msg = new Message();
										msg.what = 13;
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "�ײ��Ѷ���", Toast.LENGTH_SHORT).show();
									} else if (error_code.equals("00")) {
										Message msg = new Message();
										msg.what = 8;
										handler.sendMessage(msg);
									}
									// Toast.makeText(RuyiExpressFeel.this,
									// "�����쳣", Toast.LENGTH_SHORT).show();
									else {
										Message msg = new Message();
										msg.what = 14;
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "�˶��ײ�ʧ�ܣ���",
										// Toast.LENGTH_SHORT).show();
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
//	�޸ĸı����  �³� 20100729
	private void showRuyiPackageEdit(int aViewId/* ,TextView textView */) {
		boolean isatus = states[( aViewId - 100) / 3][( aViewId - 100) % 3];
		PublicMethod.myOutput("------" + isatus);
		if (!isatus) {
			return;
		}
		viewid =  aViewId;
		// LayoutInflater mInflater = LayoutInflater.from(this);
		// View convertView =
		// mInflater.inflate(R.layout.ruyipackage_listview_item, null);
		// textview =textView;

		// ��ʱ���涩�����ײ����࣬�����´ε�¼��ʱ����ʾ����

		if ( aViewId == 101) {
			packageName = chooseLuckLotteryNum_zhonglei[0];// ˫ɫ��
			// textview.setText(textssq);
			lottery_type = "B001";
			tsubscribeId = tsubscribeIdssq;
		}
		if ( aViewId == 104) {
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
		//wangyl 8.27 add pl3 and dlt
		if ( aViewId == 110) {
			packageName = chooseLuckLotteryNum_zhonglei[3];// ������
			lottery_type = "PL3_33";
			tsubscribeId = tsubscribeIdpl3;
			System.out.println("------------tsubscribeIdpl3-------------"+tsubscribeId);
		}
		if ( aViewId == 113) {
			packageName = chooseLuckLotteryNum_zhonglei[4];// ����͸
			lottery_type = "DLT_23529";
			tsubscribeId = tsubscribeIddlt;
			System.out.println("------------tsubscribeIddlt-------------"+tsubscribeId);
		}
		// ������ 7.5 �����޸ģ���ӵ�½�ж�
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				RuyiExpressFeel.this, "addInfo");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		if (sessionid.equals("")) {
			Intent intentSession = new Intent(RuyiExpressFeel.this,
					UserLogin.class);
			startActivity(intentSession);
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
			dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ" + packageName + "10Ԫ�ײ�?");
			radioGroup
					.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							editchanged = true;
							if (checkedId == rb10.getId()) {
								dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ" + packageName
										+ "10Ԫ�ײ�?");
								ruyipackage_str = "10";
							}
							if (checkedId == rb8.getId()) {
								dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ" + packageName
										+ "8Ԫ�ײ�?");
								ruyipackage_str = "8";
							}
							if (checkedId == rb6.getId()) {
								dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ" + packageName
										+ "6Ԫ�ײ�?");
								ruyipackage_str = "6";
							}
							if (checkedId == rb4.getId()) {
								dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ" + packageName
										+ "4Ԫ�ײ�?");
								ruyipackage_str = "4";
							}
							if (checkedId == rb2.getId()) {
								dialog_tips.setText("��ȷ�Ͻ��ײ��޸�Ϊ" + packageName
										+ "2Ԫ�ײ�?");
								ruyipackage_str = "2";
							}
						}

					});
			if (!editchanged) {
				ruyipackage_str = "10";
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(
					RuyiExpressFeel.this);
			builder.setTitle("��ѡ���ײ�����");
			builder.setView(myView);
			// ȷ��
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							showDialog(PROGRESS_VALUE);
							Thread t = new Thread(new Runnable() {

								@Override
								public void run() {
									while (iretrytimes < 3 && iretrytimes > 0) {
										PublicMethod
												.myOutput("------edit-------lottery_type------------------"
														+ lottery_type);
										PublicMethod
												.myOutput("------edit-------ruyipackage_str------------------"
														+ ruyipackage_str);
										PublicMethod
												.myOutput("-----edit--------sessionid------------------"
														+ sessionid);
										re = jrtLot
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
											iretrytimes = 3;
										} catch (JSONException e) {
											e.printStackTrace();
											iretrytimes--;

										}
									}
									iretrytimes = 2;
									if (error_code.equals("000000")) {
										Message msg = new Message();
										msg.what = 0;
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "�޸��ײͳɹ�����",
										// Toast.LENGTH_SHORT).show();
										// if(viewid==101){
										// if(!editchanged){
										// //textview.setText("��ѡÿ��10Ԫ�ײ�");
										// holderText[0] = "��ѡÿ��10Ԫ�ײ�";
										// }else{
										// //textview.setText("��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
										// holderText[0] =
										// "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�";
										// editchanged=false;
										// }
										// }
										// if(viewid==104){
										// if(!editchanged){
										// //textview.setText("��ѡÿ��10Ԫ�ײ�");
										// holderText[1] = "��ѡÿ��10Ԫ�ײ�";
										// }else{
										// //textview.setText("��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
										// holderText[1] =
										// "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�";
										// editchanged=false;
										// }
										// }
										// if(viewid==107){
										// if(!editchanged){
										// //textview.setText("��ѡÿ��10Ԫ�ײ�");
										// holderText[2] = "��ѡÿ��10Ԫ�ײ�";
										// }else{
										// //textview.setText("��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
										// holderText[2] =
										// "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�";
										// editchanged=false;
										// }
										// }
										// ShellRWSharesPreferences
										// shellRWtextedit =new
										// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
										// //˫ɫ��
										// if
										// (getString(R.string.shuangseqiu).equals(packageName))
										// {
										// shellRWtextedit.setUserLoginInfo("ssqtext",
										// "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
										// }
										// //����3D
										// if
										// (getString(R.string.fucai3d).equals(packageName))
										// {
										// shellRWtextedit.setUserLoginInfo("fc3dtext",
										// "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
										// }
										// //���ֲ�
										// if
										// (getString(R.string.qilecai).equals(packageName))
										// {
										// shellRWtextedit.setUserLoginInfo("qlctext",
										// "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
										// // }
										// Message msg=new Message();
										// msg.what=0;
										// handler.sendMessage(msg);

										// showRuyiPackageListView();
									} else if (error_code.equals("070002")) {
										Message msg = new Message();
										msg.what = 7;
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "���¼", Toast.LENGTH_SHORT).show();
									} else if (error_code.equals("040101")) {
										Message msg = new Message();
										msg.what = 15;// �޸� �ײͼ�¼Ϊ�� �³�2010/7/10
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "���ֲ����ڣ�", Toast.LENGTH_SHORT).show();
									} else if (error_code.equals("00")) {
										Message msg = new Message();
										msg.what = 8;
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "�����쳣", Toast.LENGTH_SHORT).show();
									} else {
										Message msg = new Message();
										msg.what = 15;// �޸�ʧ�� �³�2010/7/10
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "�޸��ײ�ʧ�ܣ���",
										// Toast.LENGTH_SHORT).show();
									}
								}

							});
							t.start();
							// while(iretrytimes<3&&iretrytimes>0){
							// PublicMethod.myOutput("------edit-------lottery_type------------------"+lottery_type);
							// PublicMethod.myOutput("------edit-------ruyipackage_str_subscribe------------------"+ruyipackage_str_subscribe);
							// PublicMethod.myOutput("-----edit--------sessionid------------------"+sessionid);
							// re = jrtLot.packageUpdate(tsubscribeId,
							// Integer.parseInt(ruyipackage_str_subscribe)*100+"",
							// sessionid);//login_User,
							//					    		
							//					    		
							// try {
							// obj = new JSONObject(re);
							// error_code = obj.getString("error_code");
							// PublicMethod.myOutput("----------edit-----------"+error_code);
							// iretrytimes=3;
							// } catch (JSONException e) {
							// e.printStackTrace();
							// iretrytimes--;
							//									
							// }
							// }
							// iretrytimes=2;
							// if(error_code.equals("000000")){
							// Toast.makeText(RuyiExpressFeel.this, "�޸��ײͳɹ�����",
							// Toast.LENGTH_SHORT).show();
							// if(viewid==101){
							// if(!editchanged){
							// //textview.setText("��ѡÿ��10Ԫ�ײ�");
							// holderText[0] = "��ѡÿ��10Ԫ�ײ�";
							// }else{
							// //textview.setText("��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
							// holderText[0] = "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�";
							// editchanged=false;
							// }
							// }
							// if(viewid==104){
							// if(!editchanged){
							// //textview.setText("��ѡÿ��10Ԫ�ײ�");
							// holderText[1] = "��ѡÿ��10Ԫ�ײ�";
							// }else{
							// //textview.setText("��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
							// holderText[1] = "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�";
							// editchanged=false;
							// }
							// }
							// if(viewid==107){
							// if(!editchanged){
							// //textview.setText("��ѡÿ��10Ԫ�ײ�");
							// holderText[2] = "��ѡÿ��10Ԫ�ײ�";
							// }else{
							// //textview.setText("��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
							// holderText[2] = "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�";
							// editchanged=false;
							// }
							// }
							// ShellRWSharesPreferences shellRWtextedit =new
							// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
							// //˫ɫ��
							// if
							// (getString(R.string.shuangseqiu).equals(packageName))
							// {
							// shellRWtextedit.setUserLoginInfo("ssqtext",
							// "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
							// }
							// //����3D
							// if
							// (getString(R.string.fucai3d).equals(packageName))
							// {
							// shellRWtextedit.setUserLoginInfo("fc3dtext",
							// "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
							// }
							// //���ֲ�
							// if
							// (getString(R.string.qilecai).equals(packageName))
							// {
							// shellRWtextedit.setUserLoginInfo("qlctext",
							// "��ѡÿ��"+ruyipackage_str+"Ԫ�ײ�");
							// }
							// showRuyiPackageListView();
							// }else if (error_code.equals("070002")) {
							// Toast.makeText(RuyiExpressFeel.this, "���¼",
							// Toast.LENGTH_SHORT).show();
							// } else if (error_code.equals("040018")) {
							// Toast.makeText(RuyiExpressFeel.this, "���ֲ����ڣ�",
							// Toast.LENGTH_SHORT).show();
							// } else if(error_code.equals("350001")){
							// Toast.makeText(RuyiExpressFeel.this,
							// "�ײ�״̬Ϊ��ͣ��ע���������ٴζ���", Toast.LENGTH_SHORT).show();
							// }else if(error_code.equals("040003")){
							// Toast.makeText(RuyiExpressFeel.this, "�ںŲ��Ϸ�",
							// Toast.LENGTH_SHORT).show();
							// }else if(error_code.equals("350002")){
							// Toast.makeText(RuyiExpressFeel.this, "�ײ��Ѷ���",
							// Toast.LENGTH_SHORT).show();
							// }else if(error_code.equals("350006")){
							// Toast.makeText(RuyiExpressFeel.this,
							// "�ײ��Ѷ��ƣ��޸�ʧ��", Toast.LENGTH_SHORT).show();
							// }else if(error_code.equals("350003")){
							// Toast.makeText(RuyiExpressFeel.this,
							// "�ײͶ���ʧ�ܣ������쳣", Toast.LENGTH_SHORT).show();
							// } else if (error_code.equals("4444")) {
							// Toast.makeText(RuyiExpressFeel.this, "ϵͳ���㣬���Ժ�",
							// Toast.LENGTH_SHORT).show();
							// } else if(error_code.equals("00")){
							// Toast.makeText(RuyiExpressFeel.this, "�����쳣",
							// Toast.LENGTH_SHORT).show();
							// }
							// else {
							// Toast.makeText(RuyiExpressFeel.this, "�޸��ײ�ʧ�ܣ���",
							// Toast.LENGTH_SHORT).show();
							// }

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

	// ������7.9�޸��ײͽӿ�
	/**
	 * �����ײ��ж�����ť��ִ�в���
	 * 
	 * @param view
	 *            ������İ�ť
	 */
	private void showRuyiPackageSubscribe(int aViewId) {//View view
//		boolean isatus = states[(view.getId() - 100) / 3][(view.getId() - 100) % 3];
		boolean isatus = states[(aViewId - 100) / 3][(aViewId - 100) % 3];
		PublicMethod.myOutput("------" + isatus);
		if (!isatus) {
			return;
		}
		viewid = aViewId;//view.getId();

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
		//wangyl 8.27 add pl3 and dlt
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
				RuyiExpressFeel.this, "addInfo");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		if (sessionid.equals("")) {
			Intent intentSession = new Intent(RuyiExpressFeel.this,
					UserLogin.class);
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
					RuyiExpressFeel.this);
			builder.setTitle("��ѡ���ײ�����");
			builder.setView(myView);
			// ȷ��
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							showDialog(PROGRESS_VALUE);// ��ʾ������ʾ�� 2010/7/10�³�
							// ��Ϊ�߳�
							Thread t = new Thread(new Runnable() {

								@Override
								public void run() {
									while (iretrytimes < 3 && iretrytimes > 0) {

										ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
												RuyiExpressFeel.this, "addInfo");
										sessionid = shellRW.getUserLoginInfo("sessionid");
										phonenum = shellRW.getUserLoginInfo("phonenum");
										PublicMethod
												.myOutput("------sub-------lottery_type------------------"
														+ lottery_type);
										PublicMethod
												.myOutput("------sub-------ruyipackage_str_subscribe------------------"
														+ ruyipackage_str_subscribe);
										PublicMethod
												.myOutput("------sub-------sessionid------------------"
														+ sessionid);
										//wangyl 8.30 ����븣�ʶ����ӿڲ�ͬ
										if(lottery_type.equals("B001")||lottery_type.equals("D3")||lottery_type.equals("QL730")){
											
											re = jrtLot.packageDeal(lottery_type,Integer.parseInt(ruyipackage_str_subscribe)* 100 + "",sessionid);// login_User,
										
										}else if(lottery_type.equals("PL3_33")){
											
											re = jrtLot.packageDealTC(phonenum, "T01002", Integer.parseInt(ruyipackage_str_subscribe)* 100 + "", sessionid);
										
										}else if(lottery_type.equals("DLT_23529")){
											
											re = jrtLot.packageDealTC(phonenum, "T01001", Integer.parseInt(ruyipackage_str_subscribe)* 100 + "", sessionid);
										
										}
										
										
										subscribechanged = false;
										try {
											obj = new JSONObject(re);
											error_code = obj
													.getString("error_code");
											PublicMethod
													.myOutput("----------sub-----------"
															+ error_code);
											iretrytimes = 3;
										} catch (JSONException e) {
											e.printStackTrace();
											iretrytimes--;
										}
									}
//									�����Ƿ�ı�������ж� �³� 8.11
									if (iretrytimes == 0 && iHttp.whetherChange == false) {
										iHttp.whetherChange = true;
										if (iHttp.conMethord == iHttp.CMWAP) {
											iHttp.conMethord = iHttp.CMNET;
										} else {
											iHttp.conMethord = iHttp.CMWAP;
										}
										iretrytimes=2;
										PublicMethod.myOutput("=====qierudian=dinggou====");
										while(iretrytimes>0&&iretrytimes<3){
											ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
													RuyiExpressFeel.this, "addInfo");
											sessionid = shellRW
													.getUserLoginInfo("sessionid");
	
											PublicMethod
													.myOutput("------sub-------lottery_type------------------"
															+ lottery_type);
											PublicMethod
													.myOutput("------sub-------ruyipackage_str_subscribe------------------"
															+ ruyipackage_str_subscribe);
											PublicMethod
													.myOutput("------sub-------sessionid------------------"
															+ sessionid);
											//wangyl 8.30 ����븣�ʶ����ӿڲ�ͬ
											if(lottery_type.equals("B001")||lottery_type.equals("D3")||lottery_type.equals("QL730")){
												
												re = jrtLot.packageDeal(lottery_type,Integer.parseInt(ruyipackage_str_subscribe)* 100 + "",sessionid);// login_User,
											
											}else if(lottery_type.equals("PL3_33")){
												
												re = jrtLot.packageDealTC(phonenum, "T01002", Integer.parseInt(ruyipackage_str_subscribe)* 100 + "", sessionid);
											
											}else if(lottery_type.equals("DLT_23529")){
												
												re = jrtLot.packageDealTC(phonenum, "T01001", Integer.parseInt(ruyipackage_str_subscribe)* 100 + "", sessionid);
											
											}
	
											try {
												obj = new JSONObject(re);
												error_code = obj
														.getString("error_code");
												PublicMethod
														.myOutput("----------sub-----------"
																+ error_code);
												iretrytimes=3;
											}catch (JSONException e1) {
												iretrytimes--;
												e1.printStackTrace();
											}
										}
									}
									iretrytimes = 2;
									//wangyl 8.30 ����븣�ʶ����ӿڲ�ͬ
									if(lottery_type.equals("B001")||lottery_type.equals("D3")||lottery_type.equals("QL730")){
									if (error_code.equals("000000")) {
										// Toast.makeText(RuyiExpressFeel.this,
										// "�����ײͳɹ�����",
										// Toast.LENGTH_SHORT).show();
										Message msg = new Message();
										msg.what = 0;
										handler.sendMessage(msg);
										/*
										 * //Flag=true;//Ӧ���޸ĸ��ײ͵Ķ���״̬ //false ����
										 * if(viewid==100){
										 * subscribeFlag[0]=false;
										 * if(!subscribechanged){
										 * //textview.setText("��ѡÿ��10Ԫ�ײ�");
										 * holderText[0] ="��ѡÿ��10Ԫ�ײ�"; }else{
										 * //PublicMethod
										 * .myOutput("-------textview---------"
										 * +textview.getText().toString());
										 * //PublicMethod.myOutput(
										 * "-------ruyipackage_str_subscribe---------"
										 * +ruyipackage_str_subscribe); //
										 * textview
										 * .setText("��ѡÿ��"+ruyipackage_str_subscribe
										 * +"Ԫ�ײ�");holderText[0]="��ѡÿ��"+
										 * ruyipackage_str_subscribe+"Ԫ�ײ�";
										 * subscribechanged=false; } }
										 * if(viewid==103){
										 * subscribeFlag[1]=false;
										 * if(!subscribechanged){
										 * //textview.setText("��ѡÿ��10Ԫ�ײ�");
										 * holderText[1] ="��ѡÿ��10Ԫ�ײ�"; }else{
										 * //PublicMethod
										 * .myOutput("-------textview---------"
										 * +textview.getText().toString());
										 * //PublicMethod.myOutput(
										 * "-------ruyipackage_str_subscribe---------"
										 * +ruyipackage_str_subscribe); //
										 * textview
										 * .setText("��ѡÿ��"+ruyipackage_str_subscribe
										 * +"Ԫ�ײ�");holderText[1]="��ѡÿ��"+
										 * ruyipackage_str_subscribe+"Ԫ�ײ�";
										 * subscribechanged=false; } }
										 * if(viewid==106){
										 * subscribeFlag[2]=false;
										 * if(!subscribechanged){
										 * //textview.setText("��ѡÿ��10Ԫ�ײ�");
										 * holderText[2] ="��ѡÿ��10Ԫ�ײ�"; }else{
										 * //PublicMethod
										 * .myOutput("-------textview---------"
										 * +textview.getText().toString());
										 * //PublicMethod.myOutput(
										 * "-------ruyipackage_str_subscribe---------"
										 * +ruyipackage_str_subscribe); //
										 * textview
										 * .setText("��ѡÿ��"+ruyipackage_str_subscribe
										 * +"Ԫ�ײ�");holderText[2]="��ѡÿ��"+
										 * ruyipackage_str_subscribe+"Ԫ�ײ�";
										 * subscribechanged=false; } }
										 * //setFlag();PublicMethod.myOutput(
										 * "----sub-----subscribeFlag[0]----------"
										 * +subscribeFlag[0]);
										 * PublicMethod.myOutput
										 * ("-----sub----subscribeFlag[1]----------"
										 * +subscribeFlag[1]);
										 * PublicMethod.myOutput
										 * ("-----sub----subscribeFlag[2]----------"
										 * +subscribeFlag[2]);
										 */
										// showRuyiPackageListView();
										// Message msg=new Message();
										// msg.what=0;
										// handler.sendMessage(msg);

										/*
										 * if(!subscribechanged){
										 * //textview.setText("��ѡÿ��10Ԫ�ײ�");
										 * holderText ="��ѡÿ��10Ԫ�ײ�"; }else{
										 * //PublicMethod
										 * .myOutput("-------textview---------"
										 * +textview.getText().toString());
										 * //PublicMethod.myOutput(
										 * "-------ruyipackage_str_subscribe---------"
										 * +ruyipackage_str_subscribe); //
										 * textview
										 * .setText("��ѡÿ��"+ruyipackage_str_subscribe
										 * +"Ԫ�ײ�");holderText="��ѡÿ��"+
										 * ruyipackage_str_subscribe+"Ԫ�ײ�";
										 * subscribechanged=false; }
										 */

										// shellRWtextsub�����ݴ涩�����ײ����࣬�����´ε�¼ʱ�ɼ�
										// ShellRWSharesPreferences
										// shellRWtextsub =new
										// ShellRWSharesPreferences(RuyiExpressFeel.this,"addInfo");
										// //˫ɫ��
										// // ���preferrence
										// shellRWtextsub.setUserLoginInfo("ssqtext",
										// "");
										// shellRWtextsub.setUserLoginInfo("fc3dtext",
										// "");
										// shellRWtextsub.setUserLoginInfo("qlctext",
										// "");
										//						
										// if
										// (getString(R.string.shuangseqiu).equals(packageName))
										// {
										// shellRWtextsub.setUserLoginInfo("ssqtext",
										// "��ѡÿ��"+ruyipackage_str_subscribe+"Ԫ�ײ�");
										//							
										// PublicMethod.myOutput("---sub----ssqtext---------"+shellRWtextsub.getUserLoginInfo("ssqtext"));
										// }
										// //����3D
										// if
										// (getString(R.string.fucai3d).equals(packageName))
										// {
										// shellRWtextsub.setUserLoginInfo("fc3dtext",
										// "��ѡÿ��"+ruyipackage_str_subscribe+"Ԫ�ײ�");
										// PublicMethod.myOutput("---sub----fc3dtext---------"+shellRWtextsub.getUserLoginInfo("fc3dtext"));
										// }
										// //���ֲ�
										// if
										// (getString(R.string.qilecai).equals(packageName))
										// {
										// shellRWtextsub.setUserLoginInfo("qlctext",
										// "��ѡÿ��"+ruyipackage_str_subscribe+"Ԫ�ײ�");
										// PublicMethod.myOutput("---sub----qlctext---------"+shellRWtextsub.getUserLoginInfo("qlctext"));
										// }
										// ���뷵����Ĵ��� 2010/7/10�³�

									} else if (error_code.equals("070002")) {
										Message msg = new Message();
										msg.what = 7;
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "���¼", Toast.LENGTH_SHORT).show();
									} else if (error_code.equals("350002")) {
										Message msg = new Message();
										msg.what = 10;
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "�ײ��Ѷ���", Toast.LENGTH_SHORT).show();
									} else if (error_code.equals("040006")) {
										Message msg = new Message();
										msg.what = 11;
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "�ײ��Ѷ��ƣ��޸�ʧ��",
										// Toast.LENGTH_SHORT).show();
									} else if (error_code.equals("00")) {
										Message msg = new Message();
										msg.what = 8;
										handler.sendMessage(msg);
									}
									// Toast.makeText(RuyiExpressFeel.this,
									// "�����쳣", Toast.LENGTH_SHORT).show();
									else {
										Message msg = new Message();
										msg.what = 12;
										handler.sendMessage(msg);
										// Toast.makeText(RuyiExpressFeel.this,
										// "�����ײ�ʧ�ܣ���",
										// Toast.LENGTH_SHORT).show();
									}
									}else if(lottery_type.equals("PL3_33") ||lottery_type.equals("DLT_23529") ){
										//�ײͶ��Ƴɹ�
										if (error_code.equals("350002")) {
											Message msg = new Message();
											msg.what = 0;
											handler.sendMessage(msg);
										}
										//��¼ʧ��
										if (error_code.equals("07002")) {
											Message msg = new Message();
											msg.what = 7;
											handler.sendMessage(msg);
										}
										//�ںŲ��Ϸ�
										if (error_code.equals("040003")) {
											Message msg = new Message();
											msg.what = 20;
											handler.sendMessage(msg);
										}
										//�ײͶ���ʧ��
										if (error_code.equals("350003")) {
											Message msg = new Message();
											msg.what = 12;
											handler.sendMessage(msg);
										}
										//�˻��쳣
										if (error_code.equals("040002")) {
											Message msg = new Message();
											msg.what = 21;
											handler.sendMessage(msg);
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

	/**
	 * ����ѡ�ŵ����б�
	 */
	private void showCLLNMainListView() {

		setContentView(R.layout.choose_luck_lottery_num_main);

		ImageView tvreturn = (ImageView) findViewById(R.id.tv_choose_luck_lottery_num_return);
		tvreturn.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View v) {
				showListView(ID_MAINRUYIEXPRESSFEEL);
			}
		});

		ListView listview = (ListView) findViewById(R.id.choose_luck_lottery_num_listview_id);

		ChooseLuckLotteryNum_EfficientAdapter adapter = new ChooseLuckLotteryNum_EfficientAdapter(
				this);
		listview.setAdapter(adapter);

		// ��ʾ������ͼ��

	}

	/**
	 * ���ַ���                                         zlm 8.11 �����޸�
	 * @param v
	 */
	public void gameClassify(View v){
		if (Math.floor(v.getId() / 4) == 25) {
			type02 = "ssq";
		} else if (Math.floor(v.getId() / 4) == 26) {
			type02 = "fc3d";
		} else if (Math.floor(v.getId() / 4) == 27) {
			type02 = "qlc";
		} else if (Math.floor(v.getId() / 4) == 28) {    //������
			type02 = "pl3";
		} else if (Math.floor(v.getId() / 4) == 29) {    //��������͸
			type02 = "cjdlt";
		}
	}


	/*---------������ʾ------------*/
	// ������ 7.7 �����޸ģ��޸���showGridView()����
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
	private void showGridView(View v, Integer[] gridIcon, String[] gridIconName) {

		gameClassify(v);        //��Ʊ�������

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
				popupwindow.dismiss();
			}

		});

		// ������ 7.8 �����޸ģ�����µ�Grid������
		ChooseLuckLotteryNum_GridAdapter gridAdapter = new ChooseLuckLotteryNum_GridAdapter(
				this);
		gridview.setAdapter(gridAdapter);

		// ������ 7.7 �����޸�
		if(type02.equalsIgnoreCase("ssq") || type02.equalsIgnoreCase("fc3d") || type02.equalsIgnoreCase("qlc")){
			popupwindow.showAsDropDown(v);
		} else if(type02.equalsIgnoreCase("pl3") || type02.equalsIgnoreCase("cjdlt")){
			popupwindow.showAtLocation(findViewById(R.id.choose_luck_lottery_num_listview_id) , Gravity.CENTER, 0, 0);
		}
	}

	/**
	 * ����ѡ��
	 * 
	 * @param v
	 *            ��ͼ
	 */
	private void showXingMingGialog(View v) {
		gameClassify(v);        //��Ʊ�������
		
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

	// ������7.3�����޸ģ�������ȫ���ˣ�ʹ���Դ���������ʾ��
	/*---------����ѡ��------------*/
	// ������ 7.7 �����޸ģ�����������Ϣ
	/**
	 * ����ѡ��
	 * 
	 * @param v
	 *            ��ͼ
	 */
	private void showShengRiDialog(View v) {
		gameClassify(v);        //��Ʊ�������
		
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

	// ������ 7.8 �����޸ģ����GridView������
	// GridView������ 7.7 �����޸�
	/**
	 * GridView������
	 */
	public class ChooseLuckLotteryNum_GridAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // �������б���
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
						popupwindow.dismiss();
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
	 * ����ѡ�ŵ�������
	 */
	public class ChooseLuckLotteryNum_EfficientAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // �������б���
		Context context;
		int id;

		/*
		 * private Bitmap mShuangSeQiu; private Bitmap mFuCai; private Bitmap
		 * mQiLeCai;
		 */

		public ChooseLuckLotteryNum_EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			this.context = context;
			/*
			 * mShuangSeQiu =
			 * BitmapFactory.decodeResource(context.getResources(),
			 * R.drawable.shuangseqiu); mFuCai =
			 * BitmapFactory.decodeResource(context.getResources(),
			 * R.drawable.fucai); mQiLeCai =
			 * BitmapFactory.decodeResource(context.getResources(),
			 * R.drawable.qilecai);
			 */
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

			/*
			 * holder.chooseLuckLotteryNum_xingzuo_View.setText(chooseLuckLotteryNum_xingzuo
			 * [position]);holder.chooseLuckLotteryNum_shengxiao_View.setText(
			 * chooseLuckLotteryNum_shengxiao[position]);
			 * holder.chooseLuckLotteryNum_xingming_View
			 * .setText(chooseLuckLotteryNum_xingming[position]);
			 * holder.chooseLuckLotteryNum_shengri_View
			 * .setText(chooseLuckLotteryNum_shengri[position]);
			 */

			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView chooseLuckLotteryNum_zhonglei_View;
			LinearLayout iButtonGroupLayout;
		}

	}

	/**
	 * ���⴫��������
	 */
	private void showRuYiChuanQing() {
		setContentView(R.layout.ruyichuanqing_layout_main);
		// ������7.3�����޸ģ���Button����ImageButton
		ImageView btn = (ImageView) findViewById(R.id.ruyichuanqing_return);
		btn.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				showListView(ID_MAINRUYIEXPRESSFEEL);
			}

		});
		ruyichuanqing_sub_view = (LinearLayout) findViewById(R.id.ruyichuanqing_layout_sub);

		ruyichuanqing_ssq = (TextView) findViewById(R.id.tv_ruyichuanqing_ssq);
		ruyichuanqing_fc3d = (TextView) findViewById(R.id.tv_ruyichuanqing_fc3d);
		ruyichuanqing_qlc = (TextView) findViewById(R.id.tv_ruyichuanqing_qlc);
		//wangyl 8.25 ��������͸��������
		ruyichuanqing_dlt = (TextView) findViewById(R.id.tv_ruyichuanqing_dlt);
		ruyichuanqing_pl3 = (TextView) findViewById(R.id.tv_ruyichuanqing_pl3);
		
		textViews[0]=ruyichuanqing_ssq;
		textViews[1]=ruyichuanqing_fc3d;
		textViews[2]=ruyichuanqing_qlc;
		textViews[3]=ruyichuanqing_dlt;
		textViews[4]=ruyichuanqing_pl3;
		ruyichuanqing_ssq.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] ints={1,0,0,0,0};
				setRuyichuanqingTabs(textViews,ints);
				setRuyichuanqingViews("ssq");
			}

		});

		ruyichuanqing_fc3d.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] ints={0,1,0,0,0};
				setRuyichuanqingTabs(textViews,ints);
				setRuyichuanqingViews("fc3d");
			}

		});

		ruyichuanqing_qlc.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] ints={0,0,1,0,0};
				setRuyichuanqingTabs(textViews,ints);
				setRuyichuanqingViews("qlc");
			}

		});
		ruyichuanqing_dlt.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] ints={0,0,0,1,0};
				setRuyichuanqingTabs(textViews,ints);
				setRuyichuanqingViews("dlt");
			}

		});
		ruyichuanqing_pl3.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] ints={0,0,0,0,1};
				setRuyichuanqingTabs(textViews,ints);
				setRuyichuanqingViews("pl3");
			}

		});
		int[] ints={1,0,0,0,0};
		setRuyichuanqingTabs(textViews,ints);
		setRuyichuanqingViews("ssq");

	}
	//wangyl 8.11 �����Ż� ���⴫���ǩ
	/**
	 * ����״̬����TextView�ı���ͼƬ
	 * @param textviews  ��ǩ��
	 * @param ints  ��ǩ���״̬��1Ϊ�������0Ϊδ���
	 */
	private void setRuyichuanqingTabs(TextView[] textviews,int[] ints){
		for(int i=0;i<ints.length;i++){
			if(ints[i]==1){
				textviews[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.frame_rectangle_user));
			}else if(ints[i]==0){
				textviews[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.frame_rectangle_user_d));
			}
		}
	}
	
	//wangyl 8.11 �����Ż� ���⴫���ǩ
	/**
	 * ��ʾ�������ֵ�view
	 * @param lotteryType  ����
	 */
	private void setRuyichuanqingViews(String lotteryType){
		ruyichuanqing_sub_view.removeAllViews();
		RuyiExpressFeelView ruyiExpressFeelView = new RuyiExpressFeelView(this,lotteryType);
		View view = ruyiExpressFeelView.getView();
		ruyichuanqing_sub_view.addView(view);
	}
	
	//wangyl 8.25 ����ע�Ϳ���ɾ��
	//----------------------------------------------------------------------------------------------
	/**
	 * ���⴫��������
	 *//*
	private void showRuYiChuanQing() {
		setContentView(R.layout.ruyichuanqing_layout_main);
		// ������7.3�����޸ģ���Button����ImageButton
		ImageView btn = (ImageView) findViewById(R.id.ruyichuanqing_return);
		btn.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				showListView(ID_MAINRUYIEXPRESSFEEL);
			}

		});
		ruyichuanqing_sub_view = (LinearLayout) findViewById(R.id.ruyichuanqing_layout_sub);

		ruyichuanqing_ssq = (TextView) findViewById(R.id.tv_ruyichuanqing_ssq);
		ruyichuanqing_fc3d = (TextView) findViewById(R.id.tv_ruyichuanqing_fc3d);
		ruyichuanqing_qlc = (TextView) findViewById(R.id.tv_ruyichuanqing_qlc);

		ruyichuanqing_ssq.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				 * ruyichuanqing_ssq.setTextColor(Color.RED);
				 * ruyichuanqing_fc3d.setTextColor(Color.BLACK);
				 * ruyichuanqing_qlc.setTextColor(Color.BLACK);
				 
				// ������ 7.12 ���⴫���ǩ������
				ruyichuanqing_ssq.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.frame_rectangle_user));
				ruyichuanqing_fc3d.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.frame_rectangle_user_d));
				ruyichuanqing_qlc.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.frame_rectangle_user_d));
				showListView(ID_RUYICHUANQING_SSQ);
			}

		});

		ruyichuanqing_fc3d.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				 * ruyichuanqing_ssq.setTextColor(Color.BLACK);
				 * ruyichuanqing_fc3d.setTextColor(Color.RED);
				 * ruyichuanqing_qlc.setTextColor(Color.BLACK);
				 
				// ������ 7.12 ���⴫���ǩ������
				ruyichuanqing_ssq.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.frame_rectangle_user_d));
				ruyichuanqing_fc3d.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.frame_rectangle_user));
				ruyichuanqing_qlc.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.frame_rectangle_user_d));
				showListView(ID_RUYICHUANQING_FC3D);
			}

		});

		ruyichuanqing_qlc.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				 * ruyichuanqing_ssq.setTextColor(Color.BLACK);
				 * ruyichuanqing_fc3d.setTextColor(Color.BLACK);
				 * ruyichuanqing_qlc.setTextColor(Color.RED);
				 
				// ������ 7.12 ���⴫���ǩ������
				ruyichuanqing_ssq.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.frame_rectangle_user_d));
				ruyichuanqing_fc3d.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.frame_rectangle_user_d));
				ruyichuanqing_qlc.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.frame_rectangle_user));
				showListView(ID_RUYICHUANQING_QLC);
			}

		});
		
		 * ruyichuanqing_ssq.setTextColor(Color.RED);
		 * ruyichuanqing_fc3d.setTextColor(Color.BLACK);
		 * ruyichuanqing_qlc.setTextColor(Color.BLACK);
		 
		// ������ 7.12 ���⴫���ǩ������
		ruyichuanqing_ssq.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.frame_rectangle_user));
		ruyichuanqing_fc3d.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.frame_rectangle_user_d));
		ruyichuanqing_qlc.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.frame_rectangle_user_d));
		showListView(ID_RUYICHUANQING_SSQ);

	}

	*//**
	 * ���⴫��˫ɫ�򲼾�
	 *//*
	private void showRuyichuanqingSSQ() {

		if (ruyichuanqing_sub_view
				.findViewById(R.id.ruyichuanqing_qlc_layout_id) != null) {
			ruyichuanqing_sub_view.removeView(ruyichuanqing_sub_view
					.findViewById(R.id.ruyichuanqing_qlc_layout_id));
		}
		if (ruyichuanqing_sub_view
				.findViewById(R.id.ruyichuanqing_fc3d_layout_id) != null) {
			ruyichuanqing_sub_view.removeView(ruyichuanqing_sub_view
					.findViewById(R.id.ruyichuanqing_fc3d_layout_id));
		}
		if (ruyichuanqing_sub_view
				.findViewById(R.id.ruyichuanqing_ssq_layout_id) == null) {

			isJixuanOrZixuan_ssq = 1;
			ischeck_ssq_jixuan = false;// FALSE ѡ�� trueû��ѡ��
			ischeck_ssq_zixuan = true;

			LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout ssqlayout = (LinearLayout) inflate.inflate(
					R.layout.ruyichuanqing_ssq_layout_main, null);
			{
				relativeLayout_ssq = (RelativeLayout) ssqlayout
						.findViewById(R.id.ruyichuanqing_layout_ssq_zhushu);
				cb_ssq_jixuan = (CheckBox) ssqlayout
						.findViewById(R.id.ruyichuanqing_ssq_jixuan);
				cb_ssq_zixuan = (CheckBox) ssqlayout
						.findViewById(R.id.ruyichuanqing_ssq_zixuan);
				spinner_ssq = (Spinner) ssqlayout
						.findViewById(R.id.ruyichuanqing_spinner_zhushu);
				btn_sure_ssq = (Button) ssqlayout
						.findViewById(R.id.ruyichuanqing_ssq_sure);
				cb_ssq_jixuan
						.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									ischeck_ssq_jixuan = false;
									isJixuanOrZixuan_ssq = 1;
									cb_ssq_zixuan.setChecked(false);
									relativeLayout_ssq
											.setVisibility(View.VISIBLE);
								} else {
									ischeck_ssq_jixuan = true;
									relativeLayout_ssq
											.setVisibility(View.INVISIBLE);
								}
							}

						});
				cb_ssq_zixuan
						.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									ischeck_ssq_zixuan = false;
									isJixuanOrZixuan_ssq = 2;
									cb_ssq_jixuan.setChecked(false);
									relativeLayout_ssq
											.setVisibility(View.INVISIBLE);
								} else {
									ischeck_ssq_zixuan = true;
								}
							}

						});

				spinner_ssq = (Spinner) ssqlayout
						.findViewById(R.id.ruyichuanqing_spinner_zhushu);
				spinner_ssq
						.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								PublicMethod
										.myOutput("----------spinner_str------------"
												+ constrs[position]);
								spinner_str_ssq = constrs[position];// ���ڴ�ֵ,�ݲ���

							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {

							}

						});
				adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, constrs);
				adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner_ssq.setAdapter(adapter);
				btn_sure_ssq.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						// ��ѡ
						if (isJixuanOrZixuan_ssq == 1 && !ischeck_ssq_jixuan) {
							Intent intent = new Intent(RuyiExpressFeel.this,
									RuyiExpressFeelSuccess.class);
							Bundle Bundle = new Bundle();
							Bundle.putString("success", "ssqJixuan");
							Bundle.putString("ssqzhushu", spinner_str_ssq);
							intent.putExtras(Bundle);
							startActivity(intent);
						}
						// ��ѡ
						else if (isJixuanOrZixuan_ssq == 2
								&& !ischeck_ssq_zixuan) {
							Intent intent_zixuan = new Intent(
									RuyiExpressFeel.this,
									RuyiExpressFeelSsqZixuan.class);
							startActivity(intent_zixuan);
						} else if (ischeck_ssq_jixuan && ischeck_ssq_zixuan) {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									RuyiExpressFeel.this);
							builder.setTitle("��ѡ�����ͷ�ʽ");
							builder.setMessage("��ѡ�����ͷ�ʽ");
							// ȷ��
							builder.setPositiveButton(R.string.ok,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}

									});
							builder.show();

						}
					}

				});

			}
			ruyichuanqing_sub_view.addView(ssqlayout);

		}
	}

	*//**
	 * ���⴫�鸣��3D����
	 *//*
	private void showRuyichuanqingFC3D() {

		if (ruyichuanqing_sub_view
				.findViewById(R.id.ruyichuanqing_ssq_layout_id) != null) {
			ruyichuanqing_sub_view.removeView(ruyichuanqing_sub_view
					.findViewById(R.id.ruyichuanqing_ssq_layout_id));
		}
		if (ruyichuanqing_sub_view
				.findViewById(R.id.ruyichuanqing_qlc_layout_id) != null) {
			ruyichuanqing_sub_view.removeView(ruyichuanqing_sub_view
					.findViewById(R.id.ruyichuanqing_qlc_layout_id));
		}
		if (ruyichuanqing_sub_view
				.findViewById(R.id.ruyichuanqing_fc3d_layout_id) == null) {

			isJixuanOrZixuan_fc3d = 1;
			ischeck_fc3d_jixuan = false;// false ѡ�� true û��ѡ��
			ischeck_fc3d_zixuan = true;

			LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout fc3dlayout = (LinearLayout) inflate.inflate(
					R.layout.ruyichuanqing_fc3d_layout_main, null);
			{
				relativeLayout_fc3d = (RelativeLayout) fc3dlayout
						.findViewById(R.id.ruyichuanqing_layout_fc3d_zhushu);
				cb_fc3d_jixuan = (CheckBox) fc3dlayout
						.findViewById(R.id.ruyichuanqing_fc3d_jixuan);
				cb_fc3d_zixuan = (CheckBox) fc3dlayout
						.findViewById(R.id.ruyichuanqing_fc3d_zixuan);
				spinner_fc3d = (Spinner) fc3dlayout
						.findViewById(R.id.ruyichuanqing_spinner_zhushu);
				btn_sure_fc3d = (Button) fc3dlayout
						.findViewById(R.id.ruyichuanqing_fc3d_sure);
				cb_fc3d_jixuan
						.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									isJixuanOrZixuan_fc3d = 1;
									ischeck_fc3d_jixuan = false;
									cb_fc3d_zixuan.setChecked(false);
									relativeLayout_fc3d
											.setVisibility(View.VISIBLE);
								} else {
									ischeck_fc3d_jixuan = true;
									relativeLayout_fc3d
											.setVisibility(View.INVISIBLE);
								}
							}

						});
				cb_fc3d_zixuan
						.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									isJixuanOrZixuan_fc3d = 2;
									ischeck_fc3d_zixuan = false;
									cb_fc3d_jixuan.setChecked(false);
									relativeLayout_fc3d
											.setVisibility(View.INVISIBLE);
								} else {
									ischeck_fc3d_zixuan = true;
								}
							}

						});

				spinner_fc3d = (Spinner) fc3dlayout
						.findViewById(R.id.ruyichuanqing_spinner_zhushu);
				spinner_fc3d
						.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								PublicMethod
										.myOutput("----------spinner_str------------"
												+ constrs[position]);
								spinner_str_fc3d = constrs[position];// ���ڴ�ֵ,�ݲ���

							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {

							}

						});
				adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, constrs);
				adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner_fc3d.setAdapter(adapter);
				btn_sure_fc3d.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						// ��ѡ
						if (isJixuanOrZixuan_fc3d == 1 && !ischeck_fc3d_jixuan) {
							Intent intent = new Intent(RuyiExpressFeel.this,
									RuyiExpressFeelSuccess.class);
							Bundle Bundle = new Bundle();
							Bundle.putString("success", "fc3dJixuan");
							Bundle.putString("fc3dzhushu", spinner_str_fc3d);
							intent.putExtras(Bundle);
							startActivity(intent);
						}
						// ��ѡ
						else if (isJixuanOrZixuan_fc3d == 2
								&& !ischeck_fc3d_zixuan) {
							Intent intent_zixuan = new Intent(
									RuyiExpressFeel.this,
									RuyiExpressFeelFc3dZixuan.class);
							startActivity(intent_zixuan);
						} else if (ischeck_fc3d_jixuan && ischeck_fc3d_zixuan) {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									RuyiExpressFeel.this);
							builder.setTitle("��ѡ�����ͷ�ʽ");
							builder.setMessage("��ѡ�����ͷ�ʽ");
							// ȷ��
							builder.setPositiveButton(R.string.ok,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}

									});
							builder.show();

						}
					}

				});

			}
			ruyichuanqing_sub_view.addView(fc3dlayout);

		}
	}

	private void showRuyichuanqingQLC() {

		if (ruyichuanqing_sub_view
				.findViewById(R.id.ruyichuanqing_ssq_layout_id) != null) {
			ruyichuanqing_sub_view.removeView(ruyichuanqing_sub_view
					.findViewById(R.id.ruyichuanqing_ssq_layout_id));
		}
		if (ruyichuanqing_sub_view
				.findViewById(R.id.ruyichuanqing_fc3d_layout_id) != null) {
			ruyichuanqing_sub_view.removeView(ruyichuanqing_sub_view
					.findViewById(R.id.ruyichuanqing_fc3d_layout_id));
		}
		if (ruyichuanqing_sub_view
				.findViewById(R.id.ruyichuanqing_qlc_layout_id) == null) {

			isJixuanOrZixuan_qlc = 1;
			ischeck_qlc_jixuan = false;// false ѡ��
			ischeck_qlc_zixuan = true;

			LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout qlclayout = (LinearLayout) inflate.inflate(
					R.layout.ruyichuanqing_qlc_layout_main, null);
			{
				relativeLayout_qlc = (RelativeLayout) qlclayout
						.findViewById(R.id.ruyichuanqing_layout_qlc_zhushu);
				cb_qlc_jixuan = (CheckBox) qlclayout
						.findViewById(R.id.ruyichuanqing_qlc_jixuan);
				cb_qlc_zixuan = (CheckBox) qlclayout
						.findViewById(R.id.ruyichuanqing_qlc_zixuan);
				spinner_qlc = (Spinner) qlclayout
						.findViewById(R.id.ruyichuanqing_spinner_zhushu);
				btn_sure_qlc = (Button) qlclayout
						.findViewById(R.id.ruyichuanqing_qlc_sure);
				cb_qlc_jixuan
						.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									isJixuanOrZixuan_qlc = 1;
									ischeck_qlc_jixuan = false;
									cb_qlc_zixuan.setChecked(false);
									relativeLayout_qlc
											.setVisibility(View.VISIBLE);
								} else {
									ischeck_qlc_jixuan = true;
									relativeLayout_qlc
											.setVisibility(View.INVISIBLE);
								}
							}

						});
				cb_qlc_zixuan
						.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									isJixuanOrZixuan_qlc = 2;
									ischeck_qlc_zixuan = false;
									cb_qlc_jixuan.setChecked(false);
									relativeLayout_qlc
											.setVisibility(View.INVISIBLE);
								} else {
									ischeck_qlc_zixuan = true;
								}
							}

						});

				spinner_qlc = (Spinner) qlclayout
						.findViewById(R.id.ruyichuanqing_spinner_zhushu);
				spinner_qlc
						.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								PublicMethod
										.myOutput("----------spinner_str------------"
												+ constrs[position]);
								spinner_str_qlc = constrs[position];// ���ڴ�ֵ,�ݲ���

							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {

							}

						});
				adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, constrs);
				adapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner_qlc.setAdapter(adapter);
				btn_sure_qlc.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						// ��ѡ
						if (isJixuanOrZixuan_qlc == 1 && !ischeck_qlc_jixuan) {
							Intent intent = new Intent(RuyiExpressFeel.this,
									RuyiExpressFeelSuccess.class);
							Bundle Bundle = new Bundle();
							Bundle.putString("success", "qlcJixuan");
							Bundle.putString("qlczhushu", spinner_str_qlc);
							intent.putExtras(Bundle);
							startActivity(intent);
						}
						// ��ѡ
						else if (isJixuanOrZixuan_qlc == 2
								&& !ischeck_qlc_zixuan) {
							Intent intent_zixuan = new Intent(
									RuyiExpressFeel.this,
									RuyiExpressFeelQlcZixuan.class);
							startActivity(intent_zixuan);
						} else if (ischeck_qlc_jixuan && ischeck_qlc_zixuan) {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									RuyiExpressFeel.this);
							builder.setTitle("��ѡ�����ͷ�ʽ");
							builder.setMessage("��ѡ�����ͷ�ʽ");
							// ȷ��
							builder.setPositiveButton(R.string.ok,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}

									});
							builder.show();

						}
					}

				});

			}
			ruyichuanqing_sub_view.addView(qlclayout);

		}
	}*/
	//----------------------------------------------------------------------------------------------
	/**
	 * ����ѡ��֮ѡ�Ų���
	 * 
	 * @param aGameType
	 *            ��Ʊ����
	 */
	private void chooseLuckNumShowBallMoney(String aGameType) {
		setContentView(R.layout.choose_luck_num_trade_showball);

		// zlm 7.15 �����޸ģ����С���Ƿ�ɻ����ж�
		isDrawing = false;

		// zlm 7.13 �����޸ģ���ӳ�ʼ��
		iProgressJizhu = 1;
		iProgressBeishu = 1;
		iProgressQishu = 1;
		type05 = aGameType;

		int iStartZhuShu = iProgressJizhu;
		int iStartBeiShu = iProgressBeishu;
		int iStartQiShu = iProgressQishu;

		mTextMoney = (TextView) findViewById(R.id.choose_luck_num_show_money_total);
		mTextJizhu = (TextView) findViewById(R.id.choose_luck_num_text_zhushu);
		mTextBeishu = (TextView) findViewById(R.id.choose_luck_num_text_beishu);
		mTextQishu = (TextView) findViewById(R.id.choose_luck_num_text_qishu);

		String iTempString = "��" + iStartZhuShu + "ע����"
				+ (iStartZhuShu * iStartBeiShu * iStartQiShu * 2) + "Ԫ";
		mTextMoney.setText(iTempString);
		mTextJizhu.setText("" + iProgressJizhu);
		mTextBeishu.setText("" + iProgressBeishu);
		mTextQishu.setText("" + iProgressQishu);

		mSeekBarJizhu = (SeekBar) findViewById(R.id.choose_luck_num_seek_jizhu);
		mSeekBarJizhu.setOnSeekBarChangeListener(this);
		mSeekBarJizhu.setProgress(iProgressJizhu);

		mSeekBarBeishu = (SeekBar) findViewById(R.id.choose_luck_num_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		mSeekBarBeishu.setProgress(iProgressBeishu);

		mSeekBarQishu = (SeekBar) findViewById(R.id.choose_luck_num_seek_qishu);
		mSeekBarQishu.setOnSeekBarChangeListener(this);
		mSeekBarQishu.setProgress(iProgressQishu);

		// showSsqLayout01(type05);
		// zlm 7.16 �����޸ģ��任��ʾ����
		drawSuccess(iProgressJizhu);

		// ������7.3�����޸ģ����ͶעImageButton�Ĵ���
		ImageButton subtractJiZhuBtn = (ImageButton) findViewById(R.id.choose_luck_num_seekbar_subtract_jizhu);
		// ������7.3������ӣ����ѡ��ע���ġ����š�
		subtractJiZhuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			// zlm 7.13 �����޸ģ�ע���м��ŵĹ���
			@Override
			public void onClick(View v) {
				iProgressJizhu--;
				PublicMethod.myOutput("----- ---***");
				Message msg = new Message();
				msg.what = -6;
				seekBarHandler.sendMessage(msg);
				mSeekBarJizhu.setProgress(iProgressJizhu);
			}

		});
		// ������7.3������ӣ����ѡ��ע���ġ��Ӻš�
		ImageButton addJiZhuBtn = (ImageButton) findViewById(R.id.choose_luck_num_seekbar_add_jizhu);
		addJiZhuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			// zlm 7.13 �����޸ģ�ע���мӺŵĹ���
			@Override
			public void onClick(View v) {
				// zlm 7.14 �����޸ģ������ж�
				if (iProgressJizhu < 5) {
					// iProgressJizhu++;

					Message msg = new Message();
					msg.what = -6;
					seekBarHandler.sendMessage(msg);
					mSeekBarJizhu.setProgress(++iProgressJizhu);
				}
			}

		});
		// ������7.3������ӣ����ѡ�����ġ����š�
		ImageButton subtractBeishuBtn = (ImageButton) findViewById(R.id.choose_luck_num_seekbar_subtract_beishu);
		subtractBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				mSeekBarBeishu.setProgress(--iProgressBeishu);
			}

		});
		// ������7.3������ӣ����ѡ�����ġ��Ӻš�
		ImageButton addBeishuBtn = (ImageButton) findViewById(R.id.choose_luck_num_seekbar_add_beishu);
		addBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				mSeekBarBeishu.setProgress(++iProgressBeishu);
			}

		});
		// ������7.3������ӣ����ѡ�������ġ����š�
		ImageButton subtractQihaoBtn = (ImageButton) findViewById(R.id.choose_luck_num_seekbar_subtract_qishu);
		subtractQihaoBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				mSeekBarQishu.setProgress(--iProgressQishu);
			}

		});
		// ������7.3������ӣ����ѡ�������ġ��Ӻš�
		ImageButton addQihaoBtn = (ImageButton) findViewById(R.id.choose_luck_num_seekbar_add_qishu);
		addQihaoBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				mSeekBarQishu.setProgress(++iProgressQishu);
			}

		});

		TextView titleTextView = (TextView) findViewById(R.id.choose_luck_num_gametype_title);
		if (aGameType.equalsIgnoreCase("ssq")) {
			titleTextView.setText(R.string.xyxh_shuangseqiu); // zlm 7.9
																// �����޸ģ��޸ı���
		} else if (aGameType.equalsIgnoreCase("fc3d")) {
			titleTextView.setText(R.string.xyxh_fucai3d); // zlm 7.9 �����޸ģ��޸ı���
		} else if (aGameType.equalsIgnoreCase("qlc")) {
			titleTextView.setText(R.string.xyxh_qilecai); // zlm 7.9 �����޸ģ��޸ı���
		} else if (aGameType.equalsIgnoreCase("pl3")) {
			titleTextView.setText(R.string.xyxh_pailiesan); // zlm 8.9 ������
		} else if (aGameType.equalsIgnoreCase("cjdlt")) {
			titleTextView.setText(R.string.xyxh_chaojidaletou); // zlm 8.9 ��������͸
		}
		// ������7.3�����޸ģ����ͶעImageButton�Ĵ���
		ImageButton payImageBtn = new ImageButton(this);
		payImageBtn = (ImageButton) findViewById(R.id.choose_luck_num_touzhu);
		payImageBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ������ 7.4 �����޸ģ���ӵ�½���ж�
				ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
						RuyiExpressFeel.this, "addInfo");
				String sessionidStr = shellRW.getUserLoginInfo("sessionid");
				if (sessionidStr.equals("")) {
					Intent intentSession = new Intent(RuyiExpressFeel.this,
							UserLogin.class);
					startActivity(intentSession);
				} else {
					showXingYunXuanHaoListView(ID_CLLN_SHOW_ZHIFU_DIALOG,
							type05);
				}
			}

		});

		// ������7.4�����޸ģ� ��Button����ImageButton
		ImageButton returnBtn = new ImageButton(this);
		returnBtn = (ImageButton) findViewById(R.id.choose_luck_num_return);
		returnBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showListView(ID_CLLN_MAINLISTVIEW);
			}
		});
	}

	/**
	 * ��ȡС��������/
	 * 
	 * @param aGameType
	 *            ��Ʊ����
	 * @param aRandomNums
	 *            ������ĸ���
	/**
	 * ��ȡС��������/
	 * 
	 * @param aGameType
	 *            ��Ʊ����
	 * @param aRandomNums
	 *            ������ĸ���
	 * @return ���������
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
		} else if (aGameType.equalsIgnoreCase("fc3d") || aGameType.equalsIgnoreCase("pl3")) {  //zlm ������
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
		} else if (aGameType.equalsIgnoreCase("cjdlt")) {  //zlm ��������͸
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
	 * ����ѡ��������С��Ĳ��� zlm 7.13 �����޸ģ���Ӵ���
	 * 
	 * @param aGameType
	 *            ��Ʊ����
	 * @param layout
	 *            ����
	 * @return �����
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
			// randomNumGroup_5_ssq = group;

			// int[] randomNumGroup ;
			// randomNumGroup_5_ssq = getBallNum(aGameType , 7);
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
			// numRandomGroup = randomNumGroup_5_fc3d;
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
			// int[] randomNumGroup ;
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
		} else if (aGameType.equalsIgnoreCase("pl3")) {       //zlm ������
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
		} else if (aGameType.equalsIgnoreCase("cjdlt")) {             //zlm ��������͸
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
			for(int i = 5 ; i<7 ; i++){
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

	// zlm 7.13 �����޸ģ�С��滭�ɹ��ĺ���
	/**
	 * С��滭�ɹ�
	 * 
	 * @param aChangeTo
	 *            seekBar�ĵ�ǰ��ֵ
	 */
	public void drawSuccess(int aChangeTo) {
		isDrawing = true;

		layoutAll = new LinearLayout[5];
		layoutAll[0] = (LinearLayout) findViewById(R.id.choose_luck_num_ball_linearlayout_01);
		layoutAll[1] = (LinearLayout) findViewById(R.id.choose_luck_num_ball_linearlayout_02);
		layoutAll[2] = (LinearLayout) findViewById(R.id.choose_luck_num_ball_linearlayout_03);
		layoutAll[3] = (LinearLayout) findViewById(R.id.choose_luck_num_ball_linearlayout_04);
		layoutAll[4] = (LinearLayout) findViewById(R.id.choose_luck_num_ball_linearlayout_05);
		int i;
		countLinearLayout = 0;
		for (i = 0; i < 5; i++) {
			if (layoutAll[i] != null && layoutAll[i].getChildCount() > 0) {
				countLinearLayout = countLinearLayout + 1; // zlm 7.14 �����޸ģ��޸���ʽ
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

		isDrawing = false;
		Message msg = new Message();
		msg.what = -6;
		seekBarHandler.sendMessage(msg);
	}

	// zlm 7.13 �����޸ģ�handler��Ϣ����
	/**
	 * handler��Ϣ����
	 */
	Handler seekBarHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case -6:
				if (isDrawing)
					return;
				else {
					if (countLinearLayout != iProgressJizhu)
						drawSuccess(iProgressJizhu);
				}
				break;
			}
		}
	};

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

			Message msg = new Message();
			msg.what = -6;
			seekBarHandler.sendMessage(msg);
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
		// TODO Auto-generated method stub
		// zlm 7.14 �����޸ģ���Ӵ���
		Message msg = new Message();
		msg.what = -6;
		seekBarHandler.sendMessage(msg);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		// zlm 7.14 �����޸ģ���Ӵ���
		Message msg = new Message();
		msg.what = -6;
		seekBarHandler.sendMessage(msg);
	}

	/**
	 * ע����Ǯ��
	 */
	public void showTextSumMoney() {
		int iZhuShu = iProgressJizhu;
		int iBeiShu = iProgressBeishu;
		int iQiShu = iProgressQishu;
		String iTempString = "��" + iZhuShu + "ע����" + (iZhuShu * iBeiShu * 2)
				+ "Ԫ";// *iQiShuȡ�� zlm 20100713
		// mTextJizhu = (TextView)
		// findViewById(R.id.choose_luck_num_show_money_total);
		mTextMoney.setText(iTempString);
	}

	/**
	 * ����ѡ�ţ�����ȷ��֧��Dailog
	 */
	// ������ 7.3 �����޸ģ��ڸöԻ��������С�����ʾ
	private void showAgreeAndPayDialog(String aGameType) {
		type06 = aGameType;

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		String[] gameType = {"ssq" , "fc3d" , "qlc" , "pl3" , "cjdlt"};
		int[] titleID = {R.string.shuangseqiu , R.string.fucai3d ,R.string.qilecai ,R.string.pailiesan , R.string.chaojidaletou};

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

		// zlm 7.13 �����޸ģ��任����                   zlm 8.11 �����޸�
		for(int i = 0; i<5 ; i++){
			if(type06.equalsIgnoreCase(gameType[i])){
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
						showDialog(PROGRESS_VALUE);// ��ʾ������ʾ�� �³�2010/7/10
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
								if(type06.equals("ssq") || type06.equals("fc3d") || type06.equals("qlc") ){
								strBet = bet(type06, iProgressQishu,
										iProgressJizhu, iProgressBeishu);
								// if(strBet.equals("0000")){
								if (strBet.equals("000000")) {
									Message msg = new Message();
									msg.what = 6;
									handler.sendMessage(msg);
								} else if (strBet.equals("070002")) {
									Message msg = new Message();
									msg.what = 7;
									handler.sendMessage(msg);
								} else if (strBet.equals("040006")) {
									Message msg = new Message();
									msg.what = 1;
									handler.sendMessage(msg);
								} else if (strBet.equals("1007")) {
									Message msg = new Message();
									msg.what = 2;
									handler.sendMessage(msg);
								} else if (strBet.equals("040007")) {
									Message msg = new Message();
									msg.what = 4;
									handler.sendMessage(msg);
								} else if (strBet.equals("4444")) {
									Message msg = new Message();
									msg.what = 3;
									handler.sendMessage(msg);
								} else if (strBet.equals("00")) {
									Message msg = new Message();
									msg.what = 8;
									handler.sendMessage(msg);
								} else {
									Message msg = new Message();
									msg.what = 9;
									handler.sendMessage(msg);
								}
								} else if(type06.equals("pl3") || type06.equals("cjdlt")){
									strBetTC = betTC(type06, iProgressQishu,
											iProgressJizhu, iProgressBeishu);
									if (strBetTC[0].equals("0000")&&strBetTC[1].equals("000000")) {
										Message msg = new Message();
										msg.what = 6;
										handler.sendMessage(msg);
									} else if (strBetTC[0].equals("070002")) {
										Message msg = new Message();
										msg.what = 7;
										handler.sendMessage(msg);
									} 
									else if (strBetTC[0].equals("0000")&&strBetTC[1].equals("000001")) {
										Message msg = new Message();
										msg.what = 17;
										handler.sendMessage(msg);
									} else if (strBetTC[0].equals("1007")) {
										Message msg = new Message();
										msg.what = 2;
										handler.sendMessage(msg);
									} 
									else if (strBetTC[0].equals("040006")||strBetTC[0].equals("201015")) {
										Message msg = new Message();
										msg.what = 1;
										handler.sendMessage(msg);
									}  else if (strBetTC[1].equals("002002")) {
										Message msg = new Message();
										msg.what = 18;
										handler.sendMessage(msg);
									}
							 	   else if (strBetTC[0].equals("00")&&strBetTC[1].equals("00")) {
										Message msg = new Message();
										msg.what = 8;
										handler.sendMessage(msg);
									}else {
										Message msg = new Message();
										msg.what = 9;
										handler.sendMessage(msg);
									}

								}
							}

						});
						t.start();
						// showXingYunXuanHaoListView(ID_CLLN_SHOW_TRADE_SUCCESS
						// ,null);
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
	public void showAgreeAndPayBall(){
		//zlm 8.11 �����޸�
		LinearLayout[] agreePayBallLayoutGroup = {agreePayBallLayout01 , agreePayBallLayout02 ,agreePayBallLayout03 ,agreePayBallLayout04 ,agreePayBallLayout05};
		for(int i = 1 ; i<6 ; i++){
			if(iProgressJizhu == i){
				for(int j=0 ; j<i ;j++){
					showAgreeAndPayBallLayout(type06, agreePayBallLayoutGroup[j],
							receiveRandomNum[j]);
				}
			}
		}
	}
	/**
	 * Ͷע����ӿ�
	 * 
	 * @param aGameType
	 *            ��Ʊ����
	 * @param zhuShu
	 * @param beiShu
	 * @return
	 */
	// �½ӿ� �³� 20100711
	public String bet(String aGameType, int qiShu, int zhuShu, int beiShu) {
		// ������ 7.6 �����޸ģ���Ӵ���
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
				RuyiExpressFeel.this, "addInfo");
		String sessionid = pre.getUserLoginInfo("sessionid");
		String phonenum = pre.getUserLoginInfo("phonenum");

		String error_code = "00";
		if (!phonenum.equals("") || phonenum != null) {
			if (aGameType.equalsIgnoreCase("ssq")) {
				int type = 0;
				int[][] iZhuShu;
				String strBets;
				// GT gt = new GT();
//				�����Ƿ�ı�������ж� �³� 8.11
				iHttp.whetherChange=false;
				BettingInterface betting = new BettingInterface();
				iZhuShu = changeShuZu(zhuShu, aGameType);
				strBets = GT.betCodeToString(type, zhuShu, "00", beiShu,
						iZhuShu);
				// �½ӿ� �³�20100711
				// error_code=betting.Betting(phonenum, strBets, ""+zhuShu
				// ,sessionid);
				// ���Ӧ�ó��Ա��� �³� 20100713
				error_code = betting.BettingNew(strBets, qiShu + "", zhuShu
						* 200 * iProgressBeishu * qiShu + "", sessionid);
			} else if (aGameType.equalsIgnoreCase("fc3d")) {
				int type = 1;
				int[][] iZhuShu;
				String strBets;
				// GT gt = new GT();
//				�����Ƿ�ı�������ж� �³� 8.11
				iHttp.whetherChange=false;
				BettingInterface betting = new BettingInterface();
				iZhuShu = changeShuZu(zhuShu, aGameType);
				strBets = GT.betCodeToString(type, zhuShu, "00", beiShu,
						iZhuShu);
				// error_code=betting.BettingNew(phonenum, strBets, ""+zhuShu
				// ,sessionid);
				// �½ӿ� �³� 20100711
				// ���Ӧ�ó��Ա��� �³� 20100713
				error_code = betting.BettingNew(strBets, qiShu + "", zhuShu
						* 200 * iProgressBeishu * qiShu + "", sessionid);
			} else if (aGameType.equalsIgnoreCase("qlc")) {
				int type = 2;
				int[][] iZhuShu;
				String strBets;
				// GT gt = new GT();
				iHttp.whetherChange=false;
				BettingInterface betting = new BettingInterface();
				iZhuShu = changeShuZu(zhuShu, aGameType);
				strBets = GT.betCodeToString(type, zhuShu, "00", beiShu,
						iZhuShu);
				// error_code = betting.Betting(phonenum, strBets, ""+zhuShu
				// ,sessionid);
				// �½ӿ� �³� 20100711
				// ���Ӧ�ó��Ա��� �³� 20100713
				error_code = betting.BettingNew(strBets, qiShu + "", zhuShu
						* 200 * iProgressBeishu * qiShu + "", sessionid);
			}
		}
		return error_code;
	}

	/**
	 * ���Ͷע����ӿ�
	 * @param aGameType
	 * @param qiShu
	 * @param zhuShu
	 * @param beiShu
	 * @return
	 */
	public String[] betTC(String aGameType, int qiShu, int zhuShu, int beiShu){
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
				RuyiExpressFeel.this, "addInfo");
		String sessionid = pre.getUserLoginInfo("sessionid");
		String phonenum = pre.getUserLoginInfo("phonenum");

		String[] error_code = new String[2];
		
		if (aGameType.equalsIgnoreCase("pl3")) {
			int type = 3;
			int[][] iZhuShu;
			String strBets;
			// GT gt = new GT();
			iHttp.whetherChange=false;
			BettingInterface betting = new BettingInterface();
			iZhuShu = changeShuZu(zhuShu, aGameType);
			strBets = GT.betCodeToStringTC(type ,iZhuShu);
			// error_code = betting.Betting(phonenum, strBets, ""+zhuShu
			// ,sessionid);
			// �½ӿ� �³� 20100711
			// ���Ӧ�ó��Ա��� �³� 20100713
			System.out.println("---strBets---"+strBets+"-----beiShu---"+beiShu+"-----zhuShu"+zhuShu);
			error_code = betting.BettingTC(phonenum, "T01002", strBets, beiShu+"", zhuShu*2*beiShu+"", "2", qiShu+"",sessionid);
		} else if (aGameType.equalsIgnoreCase("cjdlt")) {
			int type = 4;
			int[][] iZhuShu;
			String strBets;
			// GT gt = new GT();
			iHttp.whetherChange=false;
			BettingInterface betting = new BettingInterface();
			iZhuShu = changeShuZu(zhuShu, aGameType);
			strBets = GT.betCodeToStringTC(type ,iZhuShu);
			// error_code = betting.Betting(phonenum, strBets, ""+zhuShu
			// ,sessionid);
			// �½ӿ� �³� 20100711
			// ���Ӧ�ó��Ա��� �³� 20100713
			System.out.println("---strBets---"+strBets+"-----beiShu---"+beiShu+"-----zhuShu"+zhuShu);
			error_code = betting.BettingTC(phonenum, "T01001", strBets, beiShu+"", zhuShu*2*beiShu+"", "2",qiShu+"", sessionid);
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
		if (aGameType.equalsIgnoreCase("ssq") || aGameType.equalsIgnoreCase("qlc") || aGameType.equalsIgnoreCase("cjdlt")) {
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
		} else if (aGameType.equalsIgnoreCase("fc3d") || aGameType.equalsIgnoreCase("pl3")) {
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
	 * ��ʾ�û����������ĶԻ���
	 */
	private void showAttentionImportNameDialog(String aGameType) {
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

	/**
	 * ��ʾ�û���������
	 */
	private void showAttentionImportShengriDialog(String aGameType) {
		type08 = aGameType;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("���������Ϣ�����������������գ�");

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						showXingYunXuanHaoListView(
								ID_CLLN_SHENGRI_DIALOG_LISTVIEW, type08);
					}

				});

		builder.show();
	}

	/**
	 * ��ʾ֧���ɹ��Ի���
	 */
	private void showTradeSuccess() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("��Ͷע�ɹ���");

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						showListView(ID_CLLN_MAINLISTVIEW);
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
	 * ��������
	 * 
	 * @param t
	 *            ����
	 * @return ����õ�����
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
	 * �����ѡ�ĺ������ַ���
	 * 
	 * @param aGameType
	 * @param aRandomNum
	 *            �����
	 * @return ������ַ���
	 */
	public String changeGroupToStr(String aGameType, int[] aRandomNum) {
		String changeStr = null;
		if (aGameType.equalsIgnoreCase("ssq")) {
			changeStr = "" + aRandomNum[0] + "," + "" + aRandomNum[1] + ","
					+ "" + aRandomNum[2] + "," + "" + aRandomNum[3] + "," + ""
					+ aRandomNum[4] + "," + "" + aRandomNum[5] + "," + ""
					+ aRandomNum[6];
		} else if (aGameType.equalsIgnoreCase("fc3d")) {
			changeStr = "" + aRandomNum[0] + "," + "" + aRandomNum[1] + ","
					+ "" + aRandomNum[2];
		} else if (aGameType.equalsIgnoreCase("qlc")) {
			changeStr = "" + aRandomNum[0] + "," + "" + aRandomNum[1] + ","
					+ "" + aRandomNum[2] + "," + "" + aRandomNum[3] + "," + ""
					+ aRandomNum[4] + "," + "" + aRandomNum[5] + "," + ""
					+ aRandomNum[6];
		}
		return changeStr;
	}

	/**
	 * ��ȷ��֧��ҳ�������С�򲼾�
	 * 
	 * @param aGameType
	 * @param aLinearLayout
	 *            С��Ĳ���
	 * @param aRandomNum
	 *            �����
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
		} else if (aGameType.equalsIgnoreCase("fc3d") || aGameType.equalsIgnoreCase("pl3")) {
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
		} else if (aGameType.equalsIgnoreCase("cjdlt")) {       //zlm ��������͸
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
			for (int i = 5 ; i<7 ; i++){
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, "" + aRandomNum[i],
						aBlueColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				aLinearLayout.addView(showBallView, lp);
			}
		}
	}

	// ˢ���ײͲ�ѯҳ�� �³� 20100713
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			// iCurrentBtFlag=-1�����ѯ״̬ 20100713 �³�
			iCurrentBtFlag = -1;
			setFlag();
			// showRuyiPackageListView();
			break;
		default:
			Toast.makeText(RuyiExpressFeel.this, "δ��¼�ɹ�", Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}

	/**
	 * �������ӶԻ���
	 * 
	 * @param id
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_VALUE: {
			progressDialog = new ProgressDialog(this);
			// progressdialog.setTitle("Indeterminate");
			progressDialog.setMessage("����������...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			return progressDialog;
		}
		}
		return null;
	}

}
