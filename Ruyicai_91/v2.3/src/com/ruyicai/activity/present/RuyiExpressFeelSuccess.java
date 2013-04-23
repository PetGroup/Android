package com.ruyicai.activity.present;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts.People;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.RuyicaiActivityManager;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.game.ssq.SsqActivity;
import com.ruyicai.activity.home.ScrollableTabActivity;
import com.ruyicai.dialog.Accoutdialog;
import com.ruyicai.net.transaction.GiftLotteryInterface;
import com.ruyicai.util.GT;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.ShellRWSharesPreferences;

/**
 * 
 * @author wangyl ���⴫���ѡ����ѡ�����ɹ�����ת��ҳ��
 * 
 */
public class RuyiExpressFeelSuccess extends Activity {

	EditText messageET;
	EditText phoneET;
	ListView phoneListView;

	String phoneString = "1";
	String nameString = "";

	Button phoneAddBtn;
	Button phoneBtn;
	Button okBtn;
	Button cancelBtn;
	Button phoneDelBtn;
	TextView phoneDelTV;
	TextView nameDelTV;
	boolean confirm = true;// ȷ�ϵ绰���벻Ϊ�գ�ӦĬ��Ϊfalse

	public final static String NAME = "NAME";/* ͼ�� */
	public final static String PHONE = "PHONE";/* ͼ�� */
	public final static String DELETEBTN = "DELETEBTN"; /* ���� */
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(10);/* �б�������������Դ */
	Map<String, Object> map;
	// ������ 7.8 �޸ĵ绰��
	int listSize;
	RuyiExpressFeelSuccessEfficientAdapter adapter;// ������
	public final static int LAYOUT_INDEX = 0;// ������holder��layout��ID
	LinearLayout buttonGroup; // ������holder����ӵ�button��layout
	int iPosition;// ȫ��holder��position
	String holderphone;// ������holder��ĵ绰����

	public static final int PICK_CONTACT_SUBACTIVITY = 2;
	private static final int DIALOG1_KEY = 0;
	ProgressDialog progressDialog;
	int iRetrytimes = 2;

	String successStr;// �ж��Ǵ��Ǹ�activity��������
	// int[][] randomNums = new int[5][7];//��ѡ�����
	int[][] randomNums;
	// ˫ɫ��
	int[] redBallNums = new int[33];// ˫ɫ�����
	int[] blueBallNums = new int[16];// ˫ɫ������
	// ����3D
	int[] baiBallNums = new int[10];
	int[] shiBallNums = new int[10];
	int[] geBallNums = new int[10];
	// ������
	int[] baiBallNumsPl3 = new int[10];
	int[] shiBallNumsPl3 = new int[10];
	int[] geBallNumsPl3 = new int[10];
	// ����͸
	int[] redBallNumsDlt = new int[35];
	int[] blueBallNumsDlt = new int[12];
	// ���ֲ�
	int[] qlcBallNums = new int[33];
	int iBeishu;
	int iZhushu;

	Vector phoneVector;
	String iNumberInET;
	String iTextInET;
	String batchCode="";
	Boolean isGenerate;
	String playName;// ����
	int sendNo = 0;
	Boolean iFushi;
	// String phonenum;
	// ����
	String caipiao_type;
	// ��ʾ����д�ֻ��ź�Ի����ע��
	String zhuma_dialog = "";
	int[][] iTempRand;
	String iTempRandString;
	/**
	 * ������Ϣ
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// String result=msg.getData().getString("get");
			switch (msg.what) {
			case -2:
				progressDialog.dismiss(); // wyl 7.21 ���Ͷ�����ʾ
				Toast.makeText(RuyiExpressFeelSuccess.this, "���Ͳ�Ʊ�ɹ������Ͷ���ʧ�ܣ�",
						Toast.LENGTH_LONG).show();
				break;
			case -1:// ȫ���㶨
				progressDialog.dismiss();
				// wangyl 7.12 �޸����ͳɹ�����ȷ��ʱ���ص�ҳ��
				if ("ssqJixuan".equalsIgnoreCase(successStr)
						|| "fc3dJixuan".equalsIgnoreCase(successStr)
						|| "qlcJixuan".equalsIgnoreCase(successStr)
						|| "pl3Jixuan".equalsIgnoreCase(successStr)
						|| "dltJixuan".equalsIgnoreCase(successStr)) {
					finish();
				}
				if ("ssqZixuan".equalsIgnoreCase(successStr)
						|| "fc3dZixuan".equalsIgnoreCase(successStr)
						|| "qlcZixuan".equalsIgnoreCase(successStr)
						|| "pl3Zixuan".equalsIgnoreCase(successStr)
						|| "dltZixuan".equalsIgnoreCase(successStr)) {

					RuyiExpressFeelSuccess.this.setResult(RESULT_OK);
					RuyiExpressFeelSuccess.this.finish();
				}
				Toast.makeText(RuyiExpressFeelSuccess.this, "���Ͳ�Ʊ�ɹ���",
						Toast.LENGTH_LONG).show();
				break;
			case 0: {// �������߳�
				ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
						RuyiExpressFeelSuccess.this, "addInfo");
				final String phonenum = pre.getUserLoginInfo("phonenum");
				int iTempNumbersSum = phoneVector.size();
				if (!iNumberInET.equalsIgnoreCase("")) {
					iTempNumbersSum++;
				}

				if (iTempNumbersSum > 0) {
					final String[] iTempNumbers = new String[iTempNumbersSum];
					int i1;
					for (i1 = 0; i1 < phoneVector.size(); i1++) {
						iTempNumbers[i1] = (String) phoneVector.elementAt(i1);
					}
					if (!iNumberInET.equalsIgnoreCase("")) {
						iTempNumbers[i1] = iNumberInET;
					}
					new Thread() {
						public void run() {
							boolean sendOk = true;
							for (int i = 0; i < iTempNumbers.length; i++) {
								String content = null;
								if (isGenerate == true) {
									content = "���ĺ���" + phonenum + "����"
											+ playName + batchCode + "�ڲ�Ʊ"
											+ iZhushu + "ע:"
											+ ((String) iNumbers.elementAt(i))
											+ "�������£�"
											+ messageET.getText().toString()
											+ ",�ֻ���¼wap.51500.cn��ѯ";
									

								} else {
									content = "���ĺ���" + phonenum + "����"
											+ playName + batchCode + "�ڲ�Ʊ"
											+ iZhushu + "ע:"
											+ ((String) iNumbers.elementAt(0))
											+ "�������£�"
											+ messageET.getText().toString()
											+ ",�ֻ���¼wap.51500.cn��ѯ";
								}
								// =iTextInET;
								String code = iTempNumbers[i];
								sendOk = PublicMethod.sendSMS(code, content);// (String)iNumbers.elementAt(i));//
								if (sendOk == false) {
									break;
								}
								// iPVAF.sendSMS("13466697879","success");
							}	
							if (sendOk) {
								Message mg = Message.obtain();
								mg.what = -1;
								handler.sendMessage(mg);
							} else {
								Message mg = Message.obtain();
								mg.what = -2;
								handler.sendMessage(mg);
							}
						}
					}.start();
				} else {
					Message mg = Message.obtain();
					mg.what = -1;
					handler.sendMessage(mg);
				}
				break;
			}
			case 1:
				progressDialog.dismiss();
				Accoutdialog.getInstance().createAccoutdialog(RuyiExpressFeelSuccess.this, getResources().getString(R.string.goucai_Account_dialog_msg).toString());
				break;
			case 2:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�ۿ�ʧ�ܣ�", Toast.LENGTH_LONG).show();
				break;
			case 3:
				progressDialog.dismiss();
				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(RuyiExpressFeelSuccess.this, UserLogin.class);
				startActivity(intentSession);
				// Toast.makeText(getBaseContext(), "���¼",
				// Toast.LENGTH_LONG).show();
				break;
			case 4:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)	.show();
				break;
			case 5:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "����ʧ�ܣ�", Toast.LENGTH_LONG).show();
				break;
			case 6:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ѽᣡ", Toast.LENGTH_LONG).show();
				break;
			case 7:
				progressDialog.dismiss();
				if ("pl3Jixuan".equalsIgnoreCase(successStr)
						|| "dltJixuan".equalsIgnoreCase(successStr)) {
					finish();
				}
				if ("pl3Zixuan".equalsIgnoreCase(successStr)
						|| "dltZixuan".equalsIgnoreCase(successStr)) {

					RuyiExpressFeelSuccess.this.setResult(RESULT_OK);
					RuyiExpressFeelSuccess.this.finish();
				}

				Toast.makeText(getBaseContext(), "��������������", Toast.LENGTH_LONG).show();
				break;
			case 8:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "���������У�", Toast.LENGTH_LONG).show();
				break;
			}
			//				
		}
	};

	Vector iNumbers = null;

	private String getRandomString(int[][] aRandoms) {
		String iReturn = "";
		for (int i = 0; i < aRandoms.length; i++) {
			int[] iLines = aRandoms[i];
			int i1 = 0;
			for (; i1 < iLines.length - 1; i1++) {
				iReturn += "" + iLines[i1] + ",";
			}
			iReturn += "" + iLines[i1] + "|";
		}
		return iReturn;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		// �ı�ʵ����λ��
		phoneVector = new Vector();

		// ----- ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Bundle bundle = this.getIntent().getExtras();
		successStr = bundle.getString("success");

		if ("ssqJixuan".equals(successStr)) {
			caipiao_type = "˫ɫ��";
			int ssqzhushu = Integer.parseInt(bundle.getString("ssqzhushu"));
			iZhushu = ssqzhushu;
		
		}
		if ("fc3dJixuan".equals(successStr)) {
			caipiao_type = "����3D";
			int fc3dzhushu = Integer.parseInt(bundle.getString("fc3dzhushu"));
			iZhushu = fc3dzhushu;
		
		}

		if ("qlcJixuan".equals(successStr)) {
			caipiao_type = "���ֲ�";
			int qlczhushu = Integer.parseInt(bundle.getString("qlczhushu"));
			iZhushu = qlczhushu;
			
		}
		if ("pl3Jixuan".equals(successStr)) {
			caipiao_type = "������";
			int pl3zhushu = Integer.parseInt(bundle.getString("pl3zhushu"));
			iZhushu = pl3zhushu;
		

		}
		if ("dltJixuan".equals(successStr)) {
			caipiao_type = "����͸";
			int dltzhushu = Integer.parseInt(bundle.getString("dltzhushu"));
			iZhushu = dltzhushu;
	

		}
		if ("ssqZixuan".equals(successStr)) {
			caipiao_type = "˫ɫ��";
			int[] tempRedBall = sort(bundle.getIntArray("redBall"));
			redBallNums = tempRedBall;
			int[] tempBlueBall = sort(bundle.getIntArray("blueBall"));
			blueBallNums = tempBlueBall;
			for (int i = 0; i < tempRedBall.length; i++) {
				zhuma_dialog += redBallNums[i];
				if (i != tempRedBall.length - 1)
					zhuma_dialog += ",";
				else
					zhuma_dialog += "|";
			}
			for (int i = 0; i < tempBlueBall.length; i++) {
				zhuma_dialog += blueBallNums[i];
				if (i != tempBlueBall.length - 1)
					zhuma_dialog += ",";
			}
			iBeishu = bundle.getInt("ssqzixuanbeishu");
			iZhushu = bundle.getInt("ssqzixuanzhushu");

		}

		if ("fc3dZixuan".equals(successStr)) {
			caipiao_type = "����3D";
			int[] tempBaiBall = sort(bundle.getIntArray("baiBall"));
			baiBallNums = tempBaiBall;
			int[] tempShiBall = sort(bundle.getIntArray("shiBall"));
			shiBallNums = tempShiBall;
			int[] tempGeBall = sort(bundle.getIntArray("geBall"));
			geBallNums = tempGeBall;
			for (int i = 0; i < tempBaiBall.length; i++) {
				zhuma_dialog += baiBallNums[i];
				if (i != tempBaiBall.length - 1)
					zhuma_dialog += ",";
				else
					zhuma_dialog += "|";
			}
			for (int i = 0; i < tempShiBall.length; i++) {
				zhuma_dialog += shiBallNums[i];
				if (i != tempShiBall.length - 1)
					zhuma_dialog += ",";
				else
					zhuma_dialog += "|";
			}
			for (int i = 0; i < tempGeBall.length; i++) {
				zhuma_dialog += geBallNums[i];
				if (i != tempGeBall.length - 1)
					zhuma_dialog += ",";
			}
			iBeishu = bundle.getInt("fc3dzixuanbeishu");
			iZhushu = bundle.getInt("fc3dzixuanzhushu");

		}

		if ("qlcZixuan".equals(successStr)) {
			caipiao_type = "���ֲ�";
			int[] tempQlcBall = sort(bundle.getIntArray("qlcBall"));
			qlcBallNums = tempQlcBall;
			for (int i = 0; i < tempQlcBall.length; i++) {
				zhuma_dialog += qlcBallNums[i];
				if (i != tempQlcBall.length - 1)
					zhuma_dialog += ",";
			}
			iBeishu = bundle.getInt("qlczixuanbeishu");
			iZhushu = bundle.getInt("qlczixuanzhushu");

		}
		if ("pl3Zixuan".equals(successStr)) {
			caipiao_type = "������";
			int[] tempBaiBall = sort(bundle.getIntArray("baiBall"));
			baiBallNumsPl3 = tempBaiBall;
			int[] tempShiBall = sort(bundle.getIntArray("shiBall"));
			shiBallNumsPl3 = tempShiBall;
			int[] tempGeBall = sort(bundle.getIntArray("geBall"));
			geBallNumsPl3 = tempGeBall;
			for (int i = 0; i < tempBaiBall.length; i++) {
				zhuma_dialog += baiBallNumsPl3[i];
				if (i != tempBaiBall.length - 1)
					zhuma_dialog += ",";
				else
					zhuma_dialog += "|";
			}
			for (int i = 0; i < tempShiBall.length; i++) {
				zhuma_dialog += shiBallNumsPl3[i];
				if (i != tempShiBall.length - 1)
					zhuma_dialog += ",";
				else
					zhuma_dialog += "|";
			}
			for (int i = 0; i < tempGeBall.length; i++) {
				zhuma_dialog += geBallNumsPl3[i];
				if (i != tempGeBall.length - 1)
					zhuma_dialog += ",";
			}
			iBeishu = bundle.getInt("pl3zixuanbeishu");
			iZhushu = bundle.getInt("pl3zixuanzhushu");

		}
		if ("dltZixuan".equals(successStr)) {
			caipiao_type = "����͸";
			int[] tempRedBall = sort(bundle.getIntArray("redBall"));
			redBallNumsDlt = tempRedBall;
			int[] tempBlueBall = sort(bundle.getIntArray("blueBall"));
			blueBallNumsDlt = tempBlueBall;
			for (int i = 0; i < tempRedBall.length; i++) {
				zhuma_dialog += redBallNumsDlt[i];
				if (i != tempRedBall.length - 1)
					zhuma_dialog += ",";
				else
					zhuma_dialog += "|";
			}
			for (int i = 0; i < tempBlueBall.length; i++) {
				zhuma_dialog += blueBallNumsDlt[i];
				if (i != tempBlueBall.length - 1)
					zhuma_dialog += ",";
			}
			iBeishu = bundle.getInt("dltzixuanbeishu");
			iZhushu = bundle.getInt("dltzixuanzhushu");

		}
		setContentView(R.layout.ruyichuanqing_success_layout);

		messageET = (EditText) findViewById(R.id.ruyichuanqing_success_message_text);
		phoneET = (EditText) findViewById(R.id.ruyichuanqing_success_phonenumber_text);

		phoneListView = (ListView) findViewById(R.id.ruyichuanqing_success_phonenumber_listview);

		phoneAddBtn = (Button) findViewById(R.id.ruyichuanqing_phone_add);
		phoneAddBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// wangyl 7.14 �ж��ֻ����볤�� �ֻ����룺������11λ���֣�
				if (phoneET.length() < 11) {
					AlertDialog.Builder builder = new AlertDialog.Builder(RuyiExpressFeelSuccess.this);
					builder.setTitle("��������ȷ�ֻ�����");
					builder.setMessage("������11λ�ֻ�����");
					// ȷ��
					builder.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}

						});
					builder.show();
				} else {
					if (phoneString == "1" || phoneString.equalsIgnoreCase("1")) {// phoneString==1�ӱ༭�����
						// wangyl 7.13 ����绰��ɾ������ʱ����ɾ�����һ��������
						list = addOrDeleteResultForAdapter(1, "", phoneET
								.getText().toString(), 0);
					} else {// ����ӵ绰�����
						// wangyl 7.13 ����绰��ɾ������ʱ����ɾ�����һ��������
						list = addOrDeleteResultForAdapter(1, nameString,
								phoneString, 0);
					}

				}
				phoneString = "1";
				setListViewAdapter(list);
			}

		});

		phoneBtn = (Button) findViewById(R.id.ruyichuanqing_phone_btn);
		phoneBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// wangyl 7.15������phone�ݲ�֧�ִ˹���

				Uri uri = Uri.parse("content://contacts/people");
				Intent intent = new Intent(Intent.ACTION_PICK, uri);
				startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY);

				// Toast.makeText(getBaseContext(), "�ݲ�֧�ִ˹���",
				// Toast.LENGTH_LONG)
				// .show();
			}

		});

		okBtn = (Button) findViewById(R.id.ruyichuanqing_success_ok_btn);
		okBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 7.28 wangyl �����ֻ���������
				if (phoneET.length() < 11 && phoneVector.isEmpty() == true) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RuyiExpressFeelSuccess.this);
					builder.setTitle("��������ȷ�ֻ�����");
					builder.setMessage("������11λ�ֻ�����");
					// ȷ��
					builder.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}

							});
					builder.show();
				} else {
					if (phoneET.length() < 11 && phoneET.length() > 0) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								RuyiExpressFeelSuccess.this);
						builder.setTitle("��������ȷ�ֻ�����");
						builder.setMessage("������11λ�ֻ�����");
						// ȷ��
						builder.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}

								});
						builder.show();
					} else {
						dialogAlert();
					}
				}
			}
			// }
		});

		cancelBtn = (Button) findViewById(R.id.ruyichuanqing_success_cancel_btn);
		cancelBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});

	}

	/**
	 * ��ø���������Ӧע�������ע��
	 * 
	 * @param lotteryType
	 *            ����
	 * @param iZhushu
	 *            ע��
	 * @return
	 */
	private int[][] getRandromNums(String lotteryType, int iZhushu) {
		int randomNumbers[][] = new int[1][7];
		// int [][] randomNumbers = new int[iZhushu][7];
		if ("ssq".equals(lotteryType)) {
			randomNumbers = new int[iZhushu][7];
			for (int i = 0; i < iZhushu; i++) {
				int[] redrandoms = sort(PublicMethod
						.getRandomsWithoutCollision(6, 1, 33));
				int[] bluerandoms = PublicMethod.getRandomsWithoutCollision(1,
						1, 16);
				for (int j = 0; j < 6; j++) {
					randomNumbers[i][j] = redrandoms[j];
				}
				randomNumbers[i][6] = bluerandoms[0];
			}
		}

		if ("fc3d".equals(lotteryType)) {
			randomNumbers = new int[iZhushu][3];
			for (int i = 0; i < iZhushu; i++) {
				int[] randoms = PublicMethod
						.getRandomsWithoutCollision(3, 0, 9);
				randomNumbers[i] = sort(randoms);
			}
		}

		if ("qlc".equals(lotteryType)) {
			randomNumbers = new int[iZhushu][7];
			for (int i = 0; i < iZhushu; i++) {
				int[] randoms = PublicMethod.getRandomsWithoutCollision(7, 1,
						30);
				randomNumbers[i] = sort(randoms);
			}
		}
		if ("pl3".equals(lotteryType)) {
			randomNumbers = new int[iZhushu][3];
			for (int i = 0; i < iZhushu; i++) {
				int[] randoms = PublicMethod
						.getRandomsWithoutCollision(3, 0, 9);
				randomNumbers[i] = sort(randoms);
			}
		}
		if ("dlt".equals(lotteryType)) {
			randomNumbers = new int[iZhushu][7];
			for (int i = 0; i < iZhushu; i++) {
				int[] redrandoms = sort(PublicMethod
						.getRandomsWithoutCollision(5, 1, 35));
				int[] bluerandoms = PublicMethod.getRandomsWithoutCollision(2,
						1, 12);
				for (int j = 0; j < 5; j++) {
					randomNumbers[i][j] = redrandoms[j];
				}
				randomNumbers[i][5] = bluerandoms[0];
				randomNumbers[i][6] = bluerandoms[1];
			}
		}

		return randomNumbers;
	}

	/**
	 * ��������
	 * 
	 * @param t
	 * @return
	 */
	public static int[] sort(int t[]) {
		int t_s[] = t;
		int temp;
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

	// wangyl 7.13 ����绰��ɾ������ʱ����ɾ�����һ��������
	/**
	 * ��ӻ�ɾ������Դ
	 * 
	 * @param int iOperactionFlag 1Ϊ��ӣ�2Ϊɾ��
	 * @param int position ɾ����λ��
	 * @return List<Map<String, Object>> ��������Դ
	 */
	private List<Map<String, Object>> addOrDeleteResultForAdapter(
			int iOperactionFlag, String name, String phone, int position) {

		if (iOperactionFlag == 1) {
			// �����Ϊ����ӽ�ȥ
			if (!phone.equals("")) {
				phoneVector.addElement(phone);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(NAME, name);
			map.put(PHONE, phone);
			map.put(DELETEBTN, "-");

			list.add(map);
		} else if (iOperactionFlag == 2) {
			// wangyl 7.13 ����ע�Ϳ�ɾ��
			/*
			 * Map<String,Object> map = new HashMap<String, Object>();
			 * map.put(NAME,name); map.put(PHONE,phone); map.put(DELETEBTN,
			 * "-"); int i;
			 */

			// for(i=0;i<phoneVector.size();i++)
			// PublicMethod.myOutput("---------"+i+" "+(String)phoneVector.elementAt(i));
			// PublicMethod.myOutput("---------***"+phone);
			// phoneVector.removeElement(phone);

			// wangyl 7.13 ����ע�Ϳ�ɾ��
			// for(i=0;i<phoneVector.size();i++)
			// PublicMethod.myOutput("---------"+i+" "+(String)phoneVector.elementAt(i));
			/*
			 * if(list.size()>0){ for(i=0;i<list.size();i++){
			 * if(list.get(i).get(PHONE)==map.get(PHONE)){ list.remove(i); } } }
			 */

			// wangyl 7.13 ����绰��ɾ������ʱ����ɾ�����һ��������
			phone = list.get(position - 100).get("PHONE").toString();
			phoneVector.removeElement(phone);

			list.remove(position - 100);
		}
		listSize = list.size();
		return list;
	}

	/**
	 * �����������ͼ�����
	 */
	// ������ 7.8 �޸ĵ绰��
	private void setListViewAdapter(List<Map<String, Object>> resultList) {

		phoneET.setText("");

		adapter = new RuyiExpressFeelSuccessEfficientAdapter(this, resultList);
		phoneListView.setAdapter(adapter);

	}

	/**
	 * ������
	 */
	// ������ 7.8 �޸ĵ绰��
	public class RuyiExpressFeelSuccessEfficientAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // �������б���
		Context context;
		int listSize;
		List<Map<String, Object>> resultList;

		public RuyiExpressFeelSuccessEfficientAdapter(Context context,
				List<Map<String, Object>> resultList) {
			mInflater = LayoutInflater.from(context);
			this.context = context;
			this.listSize = resultList.size();
			this.resultList = resultList;
		}

		@Override
		public int getCount() {
			return listSize;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		/**
		 * �������б����е���ϸ����
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			iPosition = position;
			// �벼���е���Ϣ��������
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.ruyichuanqing_listview_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.ruyichuanqing_listview_item_name);
				holder.phone = (TextView) convertView
						.findViewById(R.id.ruyichuanqing_listview_item_phone);
				if(list.get(position).get(NAME) != null){
				holder.name.setText(list.get(position).get(NAME).toString());
				}else{
				holder.name.setText("");
				}
				holder.name.setTextSize(16);
				holder.phone.setText(list.get(position).get(PHONE).toString());
				holder.phone.setTextSize(16);
				holderphone = holder.phone.getText().toString();
				// ���ð�ť
				holder.iButtonGroupLayout = (LinearLayout) convertView
						.findViewById(R.id.ruyichuanqing_button_group);
				holder.iButtonGroupLayout.setId(position + LAYOUT_INDEX);

				buttonGroup = new LinearLayout(context);
				buttonGroup.setOrientation(LinearLayout.VERTICAL);

				Button btn = new Button(context);
				btn.setText("-");
				btn.setId(position + 100);
				buttonGroup.addView(btn);

				btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// ɾ������
						// wangyl 7.13 ����绰��ɾ������ʱ����ɾ�����һ��������
						list = addOrDeleteResultForAdapter(2, "", holderphone,
								arg0.getId());
						setListViewAdapter(list);
					}
				});

				holder.iButtonGroupLayout.addView(buttonGroup);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			return convertView;
		}

		class ViewHolder {
			TextView name;
			TextView phone;
			LinearLayout iButtonGroupLayout;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case PICK_CONTACT_SUBACTIVITY:
			if (data == null)
				break;
			final Uri uriRet = data.getData();
			if (uriRet != null) {
				try {
					Cursor c = managedQuery(uriRet, null, null, null, null);
					c.moveToFirst();
					String strName = c.getString(c
							.getColumnIndexOrThrow(People.NAME));
					String strPhone = c.getString(c
							.getColumnIndexOrThrow(People.NUMBER));
					if (strPhone == null) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								RuyiExpressFeelSuccess.this);
						builder.setTitle("���ֻ�����");
						builder.setMessage("����ϵ�����ֻ�����");
						// ȷ��
						builder.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}

								});
						builder.show();
					} else {
						// phoneET.setText(strName);
						// wangyl 7.14 Ϊ�������ֻ����볤�� �ֻ����룺������11λ���֣�
						phoneET.setText(strPhone);
						phoneString = strPhone;
						nameString = strName;
					}

				} catch (Exception e) {

				}
			}
			break;
		case RESULT_OK:
			startTouZhu();
		break;
		}

	}

	// ��ѡע��
	String typeCode;// �淨��ʶ

	/**
	 * ���Ͳ�Ʊ����ѡע���ʽ
	 * 
	 * @param playname
	 *            ����
	 * @param beishu
	 *            ����
	 * @param zhushu
	 *            ע��
	 */
	public String zhumaZiXuan(String playname, int beishu, int zhushu) {
		PublicMethod.myOutput("?????????" + beishu + "????????" + zhushu);
		String str = "";
		String iBallNum = "";

		if (playname.equals("F47104")) {
			str += "1512-F47104-";
			if (redBallNums.length == 6 && blueBallNums.length == 1) {
				str += "00-01-00";
			} else if (redBallNums.length > 6 && blueBallNums.length == 1) {
				str += "10-01-10";
			} else if (redBallNums.length == 6 && blueBallNums.length > 1) {
				str += "20-01-20";
			} else if (redBallNums.length > 6 && blueBallNums.length > 1) {
				str += "30-01-30";
			}
			if (beishu < 10) {
				str += "0" + beishu;
			} else if (beishu >= 10) {
				str += beishu;
			}
			if (redBallNums.length > 6 || blueBallNums.length > 1) {
				str += "*";
			}
			iBallNum = "����";
			for (int i = 0; i < redBallNums.length; i++) {
				if (redBallNums[i] < 10) {
					str += "0" + redBallNums[i];
				} else if (redBallNums[i] >= 10) {
					str += redBallNums[i];
				}

				iBallNum += redBallNums[i] + ",";
				// iNumbers.add(redBallNums[i]);

			}
			str += "~";
			iBallNum += "����:";
			for (int i = 0; i < blueBallNums.length; i++) {
				if (blueBallNums[i] < 10) {
					str += "0" + blueBallNums[i];
				} else if (blueBallNums[i] >= 10) {
					str += blueBallNums[i];
				}
				iBallNum += blueBallNums[i] + ",";
			}
			iNumbers.add(iBallNum);
			str += "^";
		} else if (playname.equals("F47103")) {
			str += "1512-F47103-";
			String[] zhuma = null;
			if (baiBallNums.length == 1 && shiBallNums.length == 1
					&& geBallNums.length == 1) {
				typeCode = "00";

				String[] str_ = { "0" + (baiBallNums[0] ),
						"0" + (shiBallNums[0] ), "0" + (geBallNums[0] ) };
				iBallNum = "��λ��";
				iBallNum += (baiBallNums[0] );
				// =��Ϊ+= �³� 20100730
				iBallNum += "ʮλ��";
				iBallNum += (shiBallNums[0] );
				// =��Ϊ+= �³� 20100730
				iBallNum += "��λ��";
				iBallNum += (geBallNums[0] );
				iNumbers.add(iBallNum);
				// iNumbers.add((baiBallNums[0]-1));
				// iNumbers.add((shiBallNums[0]-1));
				// iNumbers.add((geBallNums[0]-1));

				zhuma = str_;
			} else {
				// 3Dֱѡ��ʽע���淨 2010/7/4 �³�
				if (baiBallNums.length != 0 && shiBallNums.length != 0
						&& geBallNums.length != 0) {
					typeCode = "20";
					String[] str_fu = new String[baiBallNums.length
							+ shiBallNums.length + geBallNums.length + 5];
					if (baiBallNums.length < 10) {
						str_fu[0] = "0" + baiBallNums.length;
					} else {
						str_fu[0] = baiBallNums.length + "";
					}
					iBallNum = "��λ��";
					for (int i = 0; i < baiBallNums.length; i++) {
						str_fu[i + 1] = "0" + (baiBallNums[i] );
						iBallNum += (baiBallNums[i]) + ",";
						// iNumbers.add((baiBallNums[i]-1));
					}
					str_fu[baiBallNums.length + 1] = "^";

					if (shiBallNums.length < 10) {
						str_fu[baiBallNums.length + 2] = "0"
								+ shiBallNums.length;
					} else {
						str_fu[baiBallNums.length + 2] = shiBallNums.length
								+ "";
					}
					// =��Ϊ+= �³� 20100730
					iBallNum += "ʮλ��";
					for (int i = 0; i < shiBallNums.length; i++) {
						str_fu[baiBallNums.length + 3 + i] = "0"
								+ (shiBallNums[i] );
						iBallNum += (shiBallNums[i] ) + ",";
						// iNumbers.add((shiBallNums[i]-1));
					}
					str_fu[baiBallNums.length + shiBallNums.length + 3] = "^";
					if (geBallNums.length < 10) {
						str_fu[baiBallNums.length + shiBallNums.length + 4] = "0"
								+ geBallNums.length;
					} else {
						str_fu[baiBallNums.length + shiBallNums.length + 4] = geBallNums.length
								+ "";
					}
					// =��Ϊ+= �³� 20100730
					iBallNum += "��λ��";
					for (int i = 0; i < geBallNums.length; i++) {
						str_fu[baiBallNums.length + shiBallNums.length + 5 + i] = "0"
								+ (geBallNums[i] );
						iBallNum += (geBallNums[i] ) + ",";
						// iNumbers.add((geBallNums[i]-1));
					}
					zhuma = str_fu;
				}
				iNumbers.add(iBallNum);

				// }
			}
			String beishu_ = "";
			if (beishu < 10) {
				beishu_ += "0" + beishu;
				PublicMethod.myOutput("?????????beishu" + beishu_);
			} else if (beishu >= 10) {
				beishu_ += "" + beishu;
			}
			String zhushu_ = "";
			if (zhushu < 10) {
				zhushu_ += "0" + zhushu;
				PublicMethod.myOutput("?????????zhushu_" + zhushu_);
			} else if (zhushu >= 10) {
				zhushu_ += "" + zhushu;
			}

			if (baiBallNums.length == 1 && shiBallNums.length == 1
					&& geBallNums.length == 1) {
				str += typeCode + "-" + beishu_ + "-" + typeCode + beishu_;
			} else {
				str += typeCode + "-" + zhushu_ + "-" + typeCode + beishu_;
			}
			for (int i = 0; i < zhuma.length; i++) {
				str += zhuma[i];
			}
			str += "^";
		} else if (playname.equals("F47102")) {
			str += "1512-F47102-";
			if (qlcBallNums.length == 7) {
				typeCode = "00";

			} else if (qlcBallNums.length > 7) {
				typeCode = "10";
			}
			String zhushu_ = "";
			if (zhushu < 10) {
				zhushu_ += "0" + zhushu;
			} else if (zhushu >= 10) {
				zhushu_ += "" + zhushu;
			}

			String beishu_ = "";
			if (beishu < 10) {
				beishu_ += "0" + beishu;
			} else if (beishu >= 10) {
				beishu_ += "" + beishu;
			}
			if (qlcBallNums.length == 7) {
				str += typeCode + "-" + zhushu_ + "-" + typeCode + beishu_;
			} else if (qlcBallNums.length > 7) {
				str += typeCode + "-" + zhushu_ + "-" + typeCode + beishu_
						+ "*";
			}
			for (int i = 0; i < qlcBallNums.length; i++) {
				if (qlcBallNums[i] < 10) {
					str += "0" + qlcBallNums[i];
				} else if (qlcBallNums[i] >= 10) {
					str += qlcBallNums[i];
				}
				// ���ֲ�ע�벻��Ҫ��-1��ȥ�����³� 20100730
				iBallNum += qlcBallNums[i] + ",";
				// iNumbers.add(qlcBallNums[i]);
			}
			iNumbers.add(iBallNum);
			str += "^";
		} else if (playname.equals("T01002")) {
			if (baiBallNumsPl3.length > 1 || shiBallNumsPl3.length > 1
					|| geBallNumsPl3.length > 1) {
				iFushi = true;
			} else {
				iFushi = false;
			}
			iBallNum = "��λ��";
			str += "1|";
			for (int i = 0; i < baiBallNumsPl3.length; i++) {
				str += (baiBallNumsPl3[i] ) + "";
				iBallNum += (baiBallNumsPl3[i] ) + ",";
			}
			iBallNum += "ʮλ��";
			str += ",";
			for (int i = 0; i < shiBallNumsPl3.length; i++) {
				str += (shiBallNumsPl3[i] ) + "";
				iBallNum += (shiBallNumsPl3[i] ) + ",";
			}
			str += ",";
			iBallNum += "��λ��";
			for (int i = 0; i < geBallNumsPl3.length; i++) {
				str += (geBallNumsPl3[i] ) + "";
				iBallNum += (geBallNumsPl3[i]) + ",";
			}
			iNumbers.add(iBallNum);

		} else if (playname.equals("T01001")) {
			if (redBallNumsDlt.length == 5 && blueBallNumsDlt.length == 2) {
				iFushi = false;
			} else {
				iFushi = true;
			}

			iBallNum = "ǰ����";
			for (int i = 0; i < redBallNumsDlt.length - 1; i++) {
				if (redBallNumsDlt[i] < 10) {
					str += "0" + redBallNumsDlt[i] + " ";
				} else if (redBallNumsDlt[i] >= 10) {
					str += redBallNumsDlt[i] + " ";
				}

				iBallNum += redBallNumsDlt[i] + ",";
				// iNumbers.add(redBallNums[i]);

			}
			if (redBallNumsDlt[redBallNumsDlt.length - 1] < 10) {
				str += "0" + redBallNumsDlt[redBallNumsDlt.length - 1];
			} else if (redBallNumsDlt[redBallNumsDlt.length - 1] >= 10) {
				str += redBallNumsDlt[redBallNumsDlt.length - 1];
			}

			iBallNum += redBallNumsDlt[redBallNumsDlt.length - 1] + ",";

			str += "-";
			iBallNum += "����:";
			for (int i = 0; i < blueBallNumsDlt.length - 1; i++) {
				if (blueBallNumsDlt[i] < 10) {
					str += "0" + blueBallNumsDlt[i] + " ";
				} else if (blueBallNumsDlt[i] >= 10) {
					str += blueBallNumsDlt[i] + " ";
				}
				iBallNum += blueBallNumsDlt[i] + ",";
			}
			if (blueBallNumsDlt[blueBallNumsDlt.length - 1] < 10) {
				str += "0" + blueBallNumsDlt[blueBallNumsDlt.length - 1];
			} else if (blueBallNumsDlt[blueBallNumsDlt.length - 1] >= 10) {
				str += blueBallNumsDlt[blueBallNumsDlt.length - 1];
			}
			iBallNum += blueBallNumsDlt[blueBallNumsDlt.length - 1] + ",";
			iNumbers.add(iBallNum);
		}
		// }
		return str;
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG1_KEY: {
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

	public void startTouZhu() {
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
				RuyiExpressFeelSuccess.this, "addInfo");
		String sessionid = pre.getUserLoginInfo("sessionid");

		// fyj edit ����ĵ�¼�߼�
		if (sessionid.equals("")) {
			Intent intentSession = new Intent(RuyiExpressFeelSuccess.this,
					UserLogin.class);
			startActivityForResult(intentSession,0);
			return;
		} else {// �����֤
			if (confirm) {
				// 7.7 ���⴫�� �����̶�Ϊ1 ��ԭ���ĸ�Ϊ�߳� �³�7/8
				// ------------�����ص����б�
				showDialog(DIALOG1_KEY); // DIALOG1_KEY ����
				Thread t = new Thread(new Runnable() {
					ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
							RuyiExpressFeelSuccess.this, "addInfo");
					String sessionid = pre.getUserLoginInfo("sessionid");
					String phonenum = pre.getUserLoginInfo("phonenum");
					String betcode = "";
					String error_code = "999999,";

					JSONObject obj;

					@Override
					public void run() {
						String aToPhoneNum = "";
						// ��ȡ�����ֻ���
						String toPhoneNum = "";
						betcode = "";
						iNumberInET = "";
						iTextInET = messageET.getText().toString();

						// �ж�����༭�������ֻ�������뵽������ �³� 2010/7/8
						if ("ssqJixuan".equals(successStr)
								|| "fc3dJixuan".equals(successStr)
								|| "qlcJixuan".equals(successStr)
								|| "ssqZixuan".equals(successStr)
								|| "fc3dZixuan".equals(successStr)
								|| "qlcZixuan".equals(successStr)) {
							if (!phoneET.getText().toString().equals("")) {// phoneVector.isEmpty()==true&&
								toPhoneNum += phoneET.getText().toString()
										+ ",";
								iNumberInET = phoneET.getText().toString();
							}

							// �����̬���鲻Ϊ�����ȡ�ֻ����� �³� 2010/7/8
							if (phoneVector.isEmpty() == false) {
								PublicMethod.myOutput(">>>>>>>>>>>>>>>>>"
										+ phoneVector.size());
								for (int i = 0; i < phoneVector.size(); i++) {
									toPhoneNum += phoneVector.elementAt(i)
											.toString()
											+ ",";
								}
							}

							if (iNumbers != null) {
								iNumbers.removeAllElements();
							} else {
								iNumbers = new Vector();
							}
						} else if ("pl3Jixuan".equals(successStr)
								|| "dltJixuan".equals(successStr)
								|| "pl3Zixuan".equals(successStr)
								|| "dltZixuan".equals(successStr)) {

							if (!phoneET.getText().toString().equals("")
									&& phoneVector.isEmpty() == false) {
								toPhoneNum += phoneET.getText().toString()
										+ ",";
								iNumberInET = phoneET.getText().toString();

							}
							if (!phoneET.getText().toString().equals("")
									&& phoneVector.isEmpty() == true) {
								toPhoneNum += phoneET.getText().toString();
								iNumberInET = phoneET.getText().toString();
							}

							// �����̬���鲻Ϊ�����ȡ�ֻ����� �³� 2010/7/8
							if (phoneVector.isEmpty() == false) {
								PublicMethod.myOutput(">>>>>>>>>>>>>>>>>"
										+ phoneVector.size());
								for (int i = 0; i < phoneVector.size() - 1; i++) {
									toPhoneNum += phoneVector.elementAt(i)
											.toString()
											+ ",";
								}
								toPhoneNum += phoneVector.elementAt(
										phoneVector.size() - 1).toString();
							}

							if (iNumbers != null) {
								iNumbers.removeAllElements();
							} else {
								iNumbers = new Vector();
							}
						}
						if ("ssqJixuan".equals(successStr)) {
							// ���Ͷ���ʱ��������ע�� 200/7/9�³�
							if (!phoneET.getText().toString().equals("")) {
								PublicMethod.myOutput("***********"
										+ phoneVector.size());

								for (int i = 0; i < phoneVector.size() + 1; i++) {
//									iTempRand = getRandromNums("ssq", iZhushu);
									iTempRandString = getRandomString(iTempRand);
									iNumbers.add(iTempRandString);
									betcode += GT.betCodeToString(0, iZhushu,
											"00", 1, iTempRand);

								}
								sendNo = phoneVector.size() + 1;
							} else {
								for (int i = 0; i < phoneVector.size(); i++) {
//									iTempRand = getRandromNums("ssq", iZhushu);
									iTempRandString = getRandomString(iTempRand);
									iNumbers.add(iTempRandString);
									betcode += GT.betCodeToString(0, iZhushu,
											"00", 1, iTempRand);
								}
								PublicMethod.myOutput("-----------betcodessq==="
												+ betcode);
								sendNo = phoneVector.size();
							}

							playName = "˫ɫ��";
							isGenerate = true;

						} else if ("fc3dJixuan".equals(successStr)) {
							// ���Ͷ���ʱ��������ע�� 200/7/9�³�
							if (!phoneET.getText().toString().equals("")) {
								for (int i = 0; i < phoneVector.size() + 1; i++) {
//									iTempRand = getRandromNums("fc3d", iZhushu);
									iTempRandString = getRandomString(iTempRand);
									iNumbers.add(iTempRandString);
									// Ͷעע�������ע��һ�� �³�
									// 20100728
									betcode += GT.betCodeToString(1, iZhushu,
											"00", 1,iTempRand);
								}

								PublicMethod
										.myOutput("-----------betcodessq==="
												+ betcode);

								sendNo = phoneVector.size() + 1;
							} else {
								for (int i = 0; i < phoneVector.size(); i++) {
//									iTempRand = getRandromNums("fc3d", iZhushu);
									iTempRandString = getRandomString(iTempRand);
									iNumbers.add(iTempRandString);
									betcode += GT.betCodeToString(1, iZhushu,
											"00", 1, iTempRand);
								}
								sendNo = phoneVector.size();

								System.out
										.println("-----------betcodfc3djixuan==="
												+ betcode);
							}
							playName = "����3D";

							PublicMethod.myOutput("-----------betcodessq==="
									+ betcode);
							isGenerate = true;
						} else if ("qlcJixuan".equals(successStr)) {
							// ���Ͷ���ʱ��������ע�� 200/7/9�³�
							if (!phoneET.getText().toString().equals("")) {
								for (int i = 0; i < phoneVector.size() + 1; i++) {
//									iTempRand = getRandromNums("qlc", iZhushu);
									iTempRandString = getRandomString(iTempRand);
									iNumbers.add(iTempRandString);
									// Ͷעע�������ע��һ�� �³�
									// 20100728
									betcode += GT.betCodeToString(2, iZhushu,
											"00", 1, iTempRand);
								}

								PublicMethod
										.myOutput("-----------betcodessq==="
												+ betcode);
								sendNo = phoneVector.size() + 1;
							} else {
								for (int i = 0; i < phoneVector.size(); i++) {
//									iTempRand = getRandromNums("qlc", iZhushu);
									iTempRandString = getRandomString(iTempRand);
									iNumbers.add(iTempRandString);
									// Ͷעע�������ע��һ�� �³�
									// 20100728
									betcode += GT.betCodeToString(2, iZhushu,
											"00", 1, iTempRand);
								}
								sendNo = phoneVector.size();

								PublicMethod
										.myOutput("-----------betcodessq==="
												+ betcode);
							}
							playName = "���ֲ�";
							// betcode=GT.betCodeToString(2,
							// iZhushu, "00", 1, randomNums);
							isGenerate = true;
						} else if ("pl3Jixuan".equals(successStr)) {
							// ���Ͷ���ʱ��������ע�� 200/7/9�³� ����͸��ѡһע
							iBeishu = 1;
//							iTempRand = getRandromNums("pl3", iZhushu);
							iTempRandString = getRandomString(iTempRand);
							iNumbers.add(iTempRandString);
							betcode += GT.betCodeToStringTC(3, iTempRand);

							playName = "������";
							// betcode=GT.betCodeToString(1,
							// iZhushu, "00", 1,
							// randomNums);
							PublicMethod.myOutput("-----------betcodepl3==="
									+ betcode);
							isGenerate = false;
							iFushi = false;
						} else if ("dltJixuan".equals(successStr)) {
							// ���Ͷ���ʱ��������ע�� 200/7/9�³�
							iBeishu = 1;

//							iTempRand = getRandromNums("dlt", iZhushu);
							iTempRandString = getRandomString(iTempRand);
							System.out
									.println("---------iTempRandString----------"
											+ iTempRandString);
							iNumbers.add(iTempRandString);
							betcode += GT.betCodeToStringTC(4, iTempRand);

							playName = "��������͸";
							isGenerate = false;
							iFushi = false;
						} else if ("ssqZixuan".equals(successStr)) {
							// ���Ͷ���ʱ��������ע�� 200/7/9�³�
							if (!phoneET.getText().toString().equals("")) {
								for (int i = 0; i < phoneVector.size() + 1; i++) {
									// iNumbers.add(zhumaZiXuan("F47104",iBeishu,0));
									betcode += zhumaZiXuan("F47104", iBeishu, 0)
											+ ",";
								}
								sendNo = phoneVector.size() + 1;
								// betcode+=zhumaZiXuan("F47104",iBeishu,0);
								PublicMethod
										.myOutput("-----------betcodessq==="
												+ betcode);
							} else {
								for (int i = 0; i < phoneVector.size(); i++) {
									// iNumbers.add(zhumaZiXuan("F47104",iBeishu,0));
									betcode += zhumaZiXuan("F47104", iBeishu, 0)
											+ ",";
								}
								sendNo = phoneVector.size();
								// betcode+=zhumaZiXuan("F47104",iBeishu,0);
								PublicMethod
										.myOutput("-----------betcodessq==="
												+ betcode);
							}
							// betcode=zhumaZiXuan("F47104",iBeishu,0);
							playName = "˫ɫ��";
							PublicMethod
									.myOutput("-----------betcodessqzixuan==="
											+ betcode);
							isGenerate = false;
						} else if ("fc3dZixuan".equals(successStr)) {
							// ���Ͷ���ʱ��������ע�� 200/7/9�³�
							if (!phoneET.getText().toString().equals("")) {
								for (int i = 0; i < phoneVector.size() + 1; i++) {
									// iNumbers.add(zhumaZiXuan("F47103",iBeishu,iZhushu));
									betcode += zhumaZiXuan("F47103", iBeishu,
											iZhushu)
											+ ",";
								}
								sendNo = phoneVector.size() + 1;
							} else {
								for (int i = 0; i < phoneVector.size(); i++) {
									// iNumbers.add(zhumaZiXuan("F47103",iBeishu,iZhushu));
									betcode += zhumaZiXuan("F47103", iBeishu,
											iZhushu)
											+ ",";
								}
								sendNo = phoneVector.size();
							}
							playName = "����3D";
							isGenerate = false;
						} else if ("qlcZixuan".equals(successStr)) {
							// ���Ͷ���ʱ��������ע�� 200/7/9�³�
							if (!phoneET.getText().toString().equals("")) {
								for (int i = 0; i < phoneVector.size() + 1; i++) {
									betcode += zhumaZiXuan("F47102", iBeishu,
											iZhushu)
											+ ",";
								}
								sendNo = phoneVector.size() + 1;
							} else {
								for (int i = 0; i < phoneVector.size(); i++) {
									betcode += zhumaZiXuan("F47102", iBeishu,
											iZhushu)
											+ ",";
								}
								sendNo = phoneVector.size();
							}
							playName = "���ֲ�";
							isGenerate = false;
						}
						// cc 8.25
						else if ("pl3Zixuan".equals(successStr)) {
							// ���Ͷ���ʱ��������ע�� 200/7/9�³�
							betcode += zhumaZiXuan("T01002", iBeishu, iZhushu);
							playName = "������";
							isGenerate = false;
						} else if ("dltZixuan".equals(successStr)) {
							// ���Ͷ���ʱ��������ע�� 200/7/9�³�
							betcode += zhumaZiXuan("T01001", iBeishu, 0);
							playName = "��������͸";
							PublicMethod
									.myOutput("-----------betcodedltzixuan==="
											+ betcode);
							isGenerate = false;
						}

						if ("ssqJixuan".equals(successStr)
								|| "fc3dJixuan".equals(successStr)
								|| "qlcJixuan".equals(successStr)
								|| "ssqZixuan".equals(successStr)
								|| "fc3dZixuan".equals(successStr)
								|| "qlcZixuan".equals(successStr)) {
							try {
	
								aToPhoneNum = toPhoneNum;
								String re = GiftLotteryInterface.gift(phonenum,
										toPhoneNum, betcode, 2 * sendNo
												* iZhushu + "00", sessionid);
								obj = new JSONObject(re);
								String codeResponse1 = obj
										.getString("error_code");
								int index = 0;
								for (int i = 0; i < codeResponse1.length(); i++) {
									if (codeResponse1.charAt(i) == ',') {
										index = i;
										i = codeResponse1.length();
									} else {
										index = codeResponse1.length();
									}

								}
								String codeResponse = codeResponse1.substring(
										0, index);
								// ��ȡ�������ǰ6���ַ�
								if (codeResponse.length() >= 6) {
									error_code = codeResponse.substring(0, 6);
								} else {
									// ���ӷ������ж� �³� 20100716
									error_code = codeResponse.substring(0, 4);
								}
								iRetrytimes = 3;
							} catch (JSONException e) {

							}
					
							if (error_code.equalsIgnoreCase("000000")) {// equals("000000")
								// -----------�����ص����б�
								try {
									batchCode = obj.getString("sell_term_code");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								Message msg = new Message();
								msg.what = 0;// �ɹ�
								handler.sendMessage(msg);
							} else if (error_code.equals("040006")) {
								Message msg = new Message();
								msg.what = 1;// �û�����
								handler.sendMessage(msg);

							} else if (error_code.equals("040016")) {
								Message msg = new Message();
								msg.what = 2;// �ۿ�ʧ��
								handler.sendMessage(msg);

							} else if (error_code.equals("070002")) {
								Message msg = new Message();
								msg.what = 3;// �û�δ��¼
								handler.sendMessage(msg);

							} else if (error_code.equals("001007")
									|| error_code.equals("1007")) {
								Message msg = new Message();
								msg.what = 6;// �ڽ�
								handler.sendMessage(msg);

							} else if (error_code.equals("999999")) {
								Message msg = new Message();
								msg.what = 4;// �����쳣
								handler.sendMessage(msg);

							} else {
								Message msg = new Message();
								msg.what = 5;// ����ʧ��
								handler.sendMessage(msg);
							}
						} else if ("pl3Jixuan".equals(successStr)
								|| "dltJixuan".equals(successStr)
								|| "pl3Zixuan".equals(successStr)
								|| "dltZixuan".equals(successStr)) {
							String lotNo = "";
							String error_code_DYJ = "999999";
							String re="";
							if ("pl3Jixuan".equals(successStr)
									|| "pl3Zixuan".equals(successStr)) {
								lotNo = "T01002";
							} else {
								lotNo = "T01001";
							}

							try {
								aToPhoneNum = toPhoneNum;
								if (iFushi == false) {
									re = GiftLotteryInterface.giftTC(phonenum,
											toPhoneNum, lotNo, betcode, iBeishu
													+ "", 2 * iZhushu + "00",
											"2", iZhushu + "", sessionid,
											messageET.getText().toString());// *sendNo//
								} else {
									re = GiftLotteryInterface.giftTC(phonenum,
											toPhoneNum, lotNo, betcode, iBeishu
													+ "", 2 * iZhushu + "00",
											"2", "", sessionid, messageET
													.getText().toString());// *sendNo//
								}
								obj = new JSONObject(re);
								String codeResponse1 = obj
										.getString("error_code");
								error_code = codeResponse1;
								error_code_DYJ = obj
										.getString("error_code_DYJ");

							} catch (JSONException e) {
								if (error_code.equals("999999") && obj != null) {
									try {
										error_code_DYJ = obj
												.getString("error_code_DYJ");
									} catch (JSONException e1) {
										e1.printStackTrace();
									}
								}
							}
							if (error_code.equalsIgnoreCase("0000")
									&& error_code_DYJ.equals("000001")
									|| error_code_DYJ.equals("000000")) {// equals("000000")
								// -----------�����ص����б�
								try {
									batchCode = obj.getString("batchCode");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Message msg = new Message();
								msg.what = 0;// �ɹ�
								handler.sendMessage(msg);
							} else if (error_code.equals("040006")
									|| error_code.equals("201015")) {
								Message msg = new Message();
								msg.what = 1;// �û�����
								handler.sendMessage(msg);

							} else if (error_code.equals("070002")) {
								Message msg = new Message();
								msg.what = 3;// �û�δ��¼
								handler.sendMessage(msg);

							} else if (error_code.equals("001007")
									|| error_code.equals("1007")
									|| error_code_DYJ.equals("1006")) {
								Message msg = new Message();
								msg.what = 6;// �ڽ�
								handler.sendMessage(msg);

							} else if (error_code_DYJ.equals("002002")) {
								Message msg = new Message();
								msg.what = 8;// Ͷע��
								handler.sendMessage(msg);

							} else if (error_code.equals("999999")) {
								Message msg = new Message();
								msg.what = 4;// �����쳣
								handler.sendMessage(msg);

							} else {
								Message msg = new Message();
								msg.what = 5;// ����ʧ��
								handler.sendMessage(msg);
							}
						}
					}
				});
				t.start();
			}
		}

	}
    String type = "";
	/**
	 * ��ʾ��
	 * 
	 */
	private void dialogAlert() {
		// ���б������˵��ֻ�����
		String toPhoneNum = "";
		if (!phoneET.getText().toString().equals("")
				&& phoneVector.isEmpty() == false) {
			toPhoneNum += phoneET.getText().toString() + ",";
		}
		if (!phoneET.getText().toString().equals("")
				&& phoneVector.isEmpty() == true) {
			toPhoneNum += phoneET.getText().toString();
		}
		if (phoneVector.isEmpty() == false) {
			for (int i = 0; i < phoneVector.size(); i++) {
				toPhoneNum += phoneVector.elementAt(i).toString();
				if (i != phoneVector.size() - 1) {
					toPhoneNum += ",";
				}
			}
		}
		// ��ѡʱ��ע��
		if (zhuma_dialog.equals("")) {
			zhuma_dialog = jiXuanZhuMa();
		}
		Builder dialog = new AlertDialog.Builder(RuyiExpressFeelSuccess.this)
				.setTitle("������ʾ��")
				.setMessage(
						"�������ԣ�\n" + messageET.getText() + "\n" + "���֣�"
								+ caipiao_type + "\n" + "ע����" + iZhushu + "ע\n"
								+ "��" + iZhushu * 2 + "Ԫ" + "\n" + "ע�룺\n"
								+ zhuma_dialog + "\n" + "�����ֻ���\n" + toPhoneNum
								+ "\n" + " " + "\n" + "ȷ��������ȷ��" + "," + "�����뷵��")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method
						// stub
						startTouZhu();

					}

				}).setNegativeButton("����",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method
								// stub
							}

						});
		dialog.show();
	}

	public String jiXuanZhuMa() {
		String zhuma = "";
		if ("ssqJixuan".equals(successStr)) {
			iTempRand = getRandromNums("ssq", iZhushu);
			iTempRandString = getRandomString(iTempRand);
			zhuma = iTempRandString;
			String strs[] = zhuma.split("\\|");
			zhuma = "";
			for (int i = 0; i < strs.length; i++) {
				int leng = strs[i].length();
				String blue = strs[i].substring(leng - 2, leng);
				String red = strs[i].substring(0, leng - 2);
				String start = blue.substring(0, 1);
				if (start.equalsIgnoreCase(",")) {
					blue = blue.substring(1, 2);
				}
				String end = red.substring(red.length() - 1, red.length());
				if (end.equalsIgnoreCase(",")) {
					red = red.substring(0, red.length() - 1);
				}
				zhuma += red + "+" + blue;
				if (i != strs.length)
					zhuma += "\n";

			}

		} else if ("fc3dJixuan".equals(successStr)) {
			iTempRand = getRandromNums("fc3d", iZhushu);
			iTempRandString = getRandomString(iTempRand);
			zhuma = iTempRandString;

		} else if ("qlcJixuan".equals(successStr)) {
			iTempRand = getRandromNums("qlc", iZhushu);
			iTempRandString = getRandomString(iTempRand);
			zhuma = iTempRandString;

		} else if ("pl3Jixuan".equals(successStr)) {
			iTempRand = getRandromNums("pl3", iZhushu);
			iTempRandString = getRandomString(iTempRand);
			zhuma = iTempRandString;

		} else if ("dltJixuan".equals(successStr)) {
			iTempRand = getRandromNums("dlt", iZhushu);
			iTempRandString = getRandomString(iTempRand);
			zhuma = iTempRandString;

		}
		return zhuma;

	}

}
