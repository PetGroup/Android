package com.palmdream.RuyicaiAndroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.palmdream.netintface.ChargeInterface;
import com.palmdream.netintface.iHttp;
import com.palmdream.netintface.jrtLot;

public class AccountRecharge extends Activity implements MyDialogListener {
	/** Called when the activity is first created. */
	private Intent toWebIntent;
	private static final int BANK_CARD_PHONE_DIALOG = 1;
	private static final int PHONE_CARD_RECHARGE_DIALOG = 2;
	private static final int ZFB_RECHARGE_DIALOG = 3;
	private static final int PHONE_BANK_RECHARGE_DIALOG = 4;
	private static final int GAME_CARD_DIALOG = 5;
	private static final int COMPUTER_NET_RECHARGE_DIALOG = 6;
	// ������ֵע��Ի���
	private static final int BANK_PHONE_CARD_REGISTER_DIALOG = 8;
	private static final int PROGRESS_VALUE = 7;
	ProgressDialog progressDialog;

	List<Map<String, Object>> list;/* �б�������������Դ */
	public final static String ICON = "ICON";/* ͼ�� */
	public final static String TITLE = "TITLE"; /* ���� */
	public final static String BACK = "TITLE"; /* ���� */
	public static TextView text;
	// AccountRechargeDialog accountRechargeDialog;
	private String error_code;
	private String game_card_number_string;
	private String game_card_password_string;
	// private String game_card_recharge_value_string; ɾ�� �³� 20100913
	private String game_card_total_value_string;

	private String bankType = "CMBCHINA-WAP";
	private String cardType;
	private String bank_card_id;
	String phonenum;
	String sessionid;
	int iretrytimes = 2;
	String re;
	JSONObject obj;
	// private String phoneCardType;
	// private String gameCardType;

	private String phoneCardType = "0206";
	private String phoneCardValue = "100";
	private String gameCardType = "0204";
	// �ƶ���ֵ��
	private final String YIDONG = "0203";
	// ��ͨ��ֵ��
	private final String LIANTONG = "0206";
	// ���ų�ֵ��
	private final String DIANXIN = "0221";
	// ��;��
	private final String ZHENGTU = "0204";
	// ����һ��ͨ
	private final String JUNWANG = "0201";
	// ʢ��
	private final String SHENGDA = "0202";

	// ebay
	private final String EBAY = "y00003";
	// ֧����
	// private final String Aplipay = ""

	// ��������
	private final String ZHAOSHANG = "01";
	// ��������
	private final String JIANSHE = "0102";
	// ��������
	private final String GONGSHANG = "0103";

	// ���뷽ʽ
	private String strAccesstype;
	// ebay֧����ʽ
	private String strCardType = EBAY;
	// ���б�ʶ
	private String strBankId = "";
	// ��չ����
	private String strExpand = "";
	String bankCardNo = "";

	String url;
	public static boolean flag = false;

	// String sessionIdStr; //������ 7.4 �����޸ģ���ӵ�½�Ĵ���
	// ��������֧�����ص���Ϣ
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.getData().getString("get");
			switch (msg.what) {
			case 0:
				alert("��Ϣ����Ϊ��");
				break;
			// case 1:
			// //��Ҫ���AlertDialog��ʾע��ɹ�
			// break;
			case 2:
				// ȡ�������Ի��� 2010/7/9 �³�
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "���Ż��������", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ�û���ע��
				// break;
				// case 3:
				// //��Ҫ���AlertDialog��ʾϵͳ���㣬���Ժ�
				// break;
				// case 4:
				// //��Ҫ���AlertDialog��ʾ�úű���ͣ����ϵ�ͷ�
				// break;
				// case 5:
				// //��Ҫ���AlertDialogע��ʧ��
				break;
			case 6:
				// ȡ�������Ի��� 2010/7/9 �³�
				progressDialog.dismiss();
				// //��Ҫ���AlertDialog��ʾ�û���¼�ɹ�
				Toast.makeText(getBaseContext(), "��ֵ�������������Ժ��ѯ��",
						Toast.LENGTH_LONG).show();
				break;
			case 7:
				// ȡ�������Ի��� 2010/7/9 �³�
				progressDialog.dismiss();
				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(AccountRecharge.this,
						UserLogin.class);
				startActivityForResult(intentSession, 0);
				// Toast.makeText(getBaseContext(), "Ϊ�������˻���ȫ�������˳��������µ�¼",
				// Toast.LENGTH_LONG).show();
				break;
			case 8:
				// ȡ�������Ի��� 2010/7/9 �³�
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ��¼ʧ��
				break;
			case 9:
				// ȡ�������Ի��� 2010/7/9 �³�
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "��ֵʧ��", Toast.LENGTH_LONG)
						.show();
				break;
			// ����Ĵ�����Ϣ 2010/7/9�³�
			case 10:
				// ȡ�������Ի��� 2010/7/9 �³�
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�������ύ", Toast.LENGTH_LONG)
						.show();
				break;
			case 11:
				// ȡ�������Ի��� 2010/7/9 �³�
				progressDialog.dismiss();
				// ��ʾ����ע��Ի���
				showDialog(BANK_PHONE_CARD_REGISTER_DIALOG);
				Toast.makeText(getBaseContext(), "���ύ��ϸ��Ϣ", Toast.LENGTH_LONG)
						.show();
				break;
			case 12:
				// ȡ�������Ի��� 2010/7/9 �³�
				progressDialog.dismiss();
				// ����webҳ��
				// ���������webҳ��
				PublicMethod.openUrlByString(AccountRecharge.this, url);
				// Intent toWebIntent = new Intent(AccountRecharge.this,
				// WebViewRequst.class);
				// startActivity(toWebIntent);
				break;
			case 13:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�ݲ�֧�ֵĿ���", Toast.LENGTH_LONG)
						.show();
				break;
			case 14:
				progressDialog.dismiss();
				// 30���Ӻ��ٴε�¼ �³� 20100719

				Intent intent = new Intent(AccountRecharge.this,
						UserLogin.class);
				startActivityForResult(intent, 0);
				// Toast.makeText(RuyiHelper.this, "���¼",
				// Toast.LENGTH_SHORT).show();
				break;
			case 15:
				progressDialog.dismiss();
				Toast
						.makeText(AccountRecharge.this, "ϵͳ����",
								Toast.LENGTH_SHORT).show();
				break;
			case 16:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣", Toast.LENGTH_LONG)
						.show();
				break;
			case 17:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "��ѯʧ��", Toast.LENGTH_LONG)
						.show();
				break;
			case 18:
				progressDialog.dismiss();
				showUserCenterBalanceInquiry();
				break;
			}
			//				
		}
	};

	// ������ 7.5 �����޸ģ�����˳����Ĵ��롪���������
	private int iQuitFlag = 0; // �����˳�

	// private boolean iCallOnKeyDownFlag=false;

	// ������ 7.5 �����޸ģ�����˳����
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
				if (!flag) {
					WhetherQuitDialog iQuitDialog = new WhetherQuitDialog(this,
							this);
					iQuitDialog.show();
				} else {
					flag = false;
					finish();
				}

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

	protected Dialog onCreateDialog(int i) {
		/*
		 * //������ 7.4 �����޸ģ���ӵ�½�Ĵ��� ShellRWSharesPreferences pre = new
		 * ShellRWSharesPreferences(AccountRecharge.this , "addInfo");
		 * sessionIdStr = pre.getUserLoginInfo("sessionid");
		 */
		LayoutInflater factory = LayoutInflater.from(this);
		switch (i) {
		case BANK_CARD_PHONE_DIALOG:

			final View bank_card_phone_online_view = factory.inflate(
					R.layout.bank_card_phone_online_dialog, null);
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.bank_cards_phone_recharge1).setTitle(
					R.string.bank_cards_recharge).setView(
					bank_card_phone_online_view).setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							// ������ 7.4 �����޸ģ���ӵ�½���ж�
							ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
									AccountRecharge.this, "addInfo");
							String sessionIdStr = pre
									.getUserLoginInfo("sessionid");
							if (sessionIdStr.equals("")) {
								Intent intentSession = new Intent(
										AccountRecharge.this, UserLogin.class);
								startActivity(intentSession);
							} else {
								// ���п�������ֵ��������
								beiginBankCardPhoneOnline(bank_card_phone_online_view);
							}

							/* User clicked OK so do some stuff */
						}
					}).setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							/* User clicked cancel so do some stuff */
						}
					}).create();
		case PHONE_CARD_RECHARGE_DIALOG:

			final View phone_card_recharg_view = factory.inflate(
					R.layout.phone_cards_recharge_dialog, null);
			final Spinner phone_card_spinner = (Spinner) phone_card_recharg_view
					.findViewById(R.id.phone_card_spinner);
			phone_card_spinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {

							// ��������򡣡���
							int position = phone_card_spinner
									.getSelectedItemPosition();
							PublicMethod.myOutput("*********position "
									+ position);
							if (position == 0) {
								phoneCardType = YIDONG;
							} else if (position == 1) {
								phoneCardType = LIANTONG;
							} else if (position == 2) {
								phoneCardType = DIANXIN;
							}

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// û���κεĴ����¼�ʱ

						}

					});
			final Spinner phone_card_value_spinner = (Spinner) phone_card_recharg_view
					.findViewById(R.id.phone_card_value_spinner);
			phone_card_value_spinner.setSelection(4);// Ĭ��100Ԫ
			phone_card_value_spinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {

							// ��������򡣡���
							int position = phone_card_value_spinner
									.getSelectedItemPosition();
							PublicMethod.myOutput("*********position "
									+ position);
							String[] values = { "10", "20", "30", "50", "100",
									"200", "300", "500" };
							for (int i = 0; i < values.length; i++) {
								phoneCardValue = values[position];
							}
							System.out.println("phoneCardValue=========="
									+ phoneCardValue);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// û���κεĴ����¼�ʱ

						}

					});

			return new AlertDialog.Builder(this).setIcon(
					R.drawable.phone_cards_recharge2).setTitle(
					R.string.phone_cards_recharge).setView(
					phone_card_recharg_view).setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// ������ 7.4 �����޸ģ���ӵ�½���ж�
							ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
									AccountRecharge.this, "addInfo");
							String sessionIdStr = pre
									.getUserLoginInfo("sessionid");
							if (sessionIdStr.equals("")) {
								Intent intentSession = new Intent(
										AccountRecharge.this, UserLogin.class);
								startActivity(intentSession);
							} else {
								beginPhoneCardRecharge(phone_card_recharg_view);
							}
						}
					}).setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							/* User clicked cancel so do some stuff */
						}
					}).create();
		case ZFB_RECHARGE_DIALOG:

			final View zfb_recharge_view = factory.inflate(
					R.layout.zfb_recharge_dialog, null);
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.zhfb_cards_recharge3).setTitle(
					R.string.zhfb_cards_recharge).setView(zfb_recharge_view)
					.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int whichButton) {
									// ������ 7.4 �����޸ģ���ӵ�½���ж�
									beginZFBRecharge(zfb_recharge_view);
									// ShellRWSharesPreferences pre = new
									// ShellRWSharesPreferences(AccountRecharge.this
									// , "addInfo");
									// String sessionIdStr =
									// pre.getUserLoginInfo("sessionid");
									// if(sessionIdStr.equals("")){
									// Intent intentSession = new
									// Intent(AccountRecharge.this ,
									// UserLogin.class);
									// startActivity(intentSession);
									// } else {
									// // ֧������ֵ�����ȡ
									// // ��Ϊ�߳� 2010/7/9�³�
									// showDialog(PROGRESS_VALUE);
									// Thread t=new Thread(new Runnable(){
									//
									// @Override
									// public void run() {
									// ShellRWSharesPreferences shellRW = new
									// ShellRWSharesPreferences(AccountRecharge.this,
									// "addInfo");
									// String phonenum =
									// shellRW.getUserLoginInfo("phonenum");
									// String sessionid =
									// shellRW.getUserLoginInfo("sessionid");
									//           	      
									//           	      
									// ChargeInterface zfbChargeInterface = new
									// ChargeInterface();
									//                  
									// // ��Ҫ���Ĳ����� 10 ��ֵ���
									//                  
									//                  
									// String
									// error_code=zfbChargeInterface.phonebankcharge(strAccesstype,
									// phonenum, "0300", "10",
									// "zfb001",strExpand, sessionid);
									//                  
									// if(error_code.equals("000000")){
									// url=zfbChargeInterface.url;
									// shellRW.setUserLoginInfo("url", url);
									// Message msg=new Message();
									// msg.what=12;
									// handler.sendMessage(msg);
									// // �Ƶ�����handle��2010/7/9�³�
									// // Intent toWebIntent = new
									// Intent(AccountRecharge.this,
									// WebViewRequst.class);
									// // startActivity(toWebIntent);
									// }
									// else if(error_code.equals("070002")){
									// Message msg=new Message();
									// msg.what=7;
									// handler.sendMessage(msg);
									// // Toast.makeText(AccountRecharge.this,
									// "���¼", Toast.LENGTH_SHORT).show();
									// }
									// else if(error_code.equals("00")){
									// Message msg=new Message();
									// msg.what=8;
									// handler.sendMessage(msg);
									// // Toast.makeText(AccountRecharge.this,
									// "�����쳣", Toast.LENGTH_SHORT).show();
									// }
									// else{
									// Message msg=new Message();
									// msg.what=9;
									// handler.sendMessage(msg);
									// // Toast.makeText(AccountRecharge.this,
									// "����ʧ��", Toast.LENGTH_SHORT).show();
									// }
									// }
									// 							
									// });
									// t.start();
									// // ShellRWSharesPreferences shellRW = new
									// ShellRWSharesPreferences(AccountRecharge.this,
									// "addInfo");
									// // String phonenum =
									// shellRW.getUserLoginInfo("phonenum");
									// // String sessionid =
									// shellRW.getUserLoginInfo("sessionid");
									// // ChargeInterface zfbChargeInterface =
									// new ChargeInterface();
									// //
									// //// ��Ҫ���Ĳ����� 10 ��ֵ���
									// // String
									// error_code=zfbChargeInterface.phonebankcharge(strAccesstype,
									// phonenum, "0300", "10",
									// "zfb001",strExpand, sessionid);
									// //
									// // if(error_code.equals("000000")){
									// // String url=zfbChargeInterface.url;
									// // shellRW.setUserLoginInfo("url", url);
									// // Intent toWebIntent = new
									// Intent(AccountRecharge.this,
									// WebViewRequst.class);
									// // startActivity(toWebIntent);
									// // }
									// // else if(error_code.equals("070002")){
									// // Toast.makeText(AccountRecharge.this,
									// "���¼", Toast.LENGTH_SHORT).show();
									// // }
									// // else if(error_code.equals("00")){
									// // Toast.makeText(AccountRecharge.this,
									// "�����쳣", Toast.LENGTH_SHORT).show();
									// // }
									// // else{
									// // Toast.makeText(AccountRecharge.this,
									// "����ʧ��", Toast.LENGTH_SHORT).show();
									// // }
									// /* User clicked OK so do some stuff */
									// }
								}
							}).setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									/* User clicked cancel so do some stuff */
								}
							}).create();

		case PHONE_BANK_RECHARGE_DIALOG:

			final View phone_bank_recharg_view = factory.inflate(
					R.layout.phone_bank_recharg_dialog, null);

			final Spinner phone_bank_spinner = (Spinner) phone_bank_recharg_view
					.findViewById(R.id.phone_bank_spinner);
			phone_bank_spinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {

							// ��������򡣡���
							int position = phone_bank_spinner
									.getSelectedItemPosition();
							PublicMethod.myOutput("********position "
									+ position);
							if (position == 0) {
								bankType = ZHAOSHANG;
							} else if (position == 1) {
								bankType = JIANSHE;
							} else {
								bankType = GONGSHANG;
							}

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// û���κεĴ����¼�ʱ

						}

					});
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.phone_bank_cards_recharge4).setTitle(
					R.string.phone_bank_cards_recharge).setView(
					phone_bank_recharg_view).setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// ������ 7.4 �����޸ģ���ӵ�½���ж�
							ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
									AccountRecharge.this, "addInfo");
							String sessionIdStr = pre
									.getUserLoginInfo("sessionid");
							if (sessionIdStr.equals("")) {
								Intent intentSession = new Intent(
										AccountRecharge.this, UserLogin.class);
								startActivity(intentSession);
							} else {
								beginPhoneBankRecharge(phone_bank_recharg_view);
							}
						}
					}).setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							/* User clicked cancel so do some stuff */
						}
					}).create();

		case GAME_CARD_DIALOG:

			final View game_card_view = factory.inflate(
					R.layout.game_card_dialog, null);
			final Spinner game_card_spinner = (Spinner) game_card_view
					.findViewById(R.id.game_card_spinner);
			game_card_spinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {

							// ��������򡣡���
							int position = game_card_spinner
									.getSelectedItemPosition();
							PublicMethod.myOutput("*********position "
									+ position);
							if (position == 0) {
								gameCardType = ZHENGTU;
							} else if (position == 1) {
								gameCardType = SHENGDA;
							} else
								gameCardType = JUNWANG;

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// û���κεĴ����¼�ʱ

						}
					});
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.game_cards_recharge5).setTitle(
					R.string.game_cards_recharge).setView(game_card_view)
					.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// ������ 7.14 �����޸ģ���ӵ�½���ж�
									ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
											AccountRecharge.this, "addInfo");
									String sessionIdStr = pre
											.getUserLoginInfo("sessionid");
									if (sessionIdStr.equals("")) {
										Intent intentSession = new Intent(
												AccountRecharge.this,
												UserLogin.class);
										startActivity(intentSession);
									} else {
										beginGameCardRecharge(game_card_view);
									}
									/* User clicked OK so do some stuff */
								}
							}).setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									/* User clicked cancel so do some stuff */
								}
							}).create();
			// case PHONE_WAP_RECHARGE_DIALOG:
			//    		
			// final View phone_wap_recharge_view =
			// factory.inflate(R.layout.phone_wap_recharge_dialog, null);
			// return new AlertDialog.Builder(this)
			// .setIcon(R.drawable.icon48x48_1)
			// .setTitle(R.string.phone_WAP_recharge)
			// .setView(phone_wap_recharge_view)
			// .setPositiveButton(R.string.ok, new
			// DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog, int whichButton) {
			// 
			// /* User clicked OK so do some stuff */
			// }
			// })
			// .setNegativeButton(R.string.cancel, new
			// DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog, int whichButton) {
			//
			// /* User clicked cancel so do some stuff */
			// }
			// })
			// .create();
		case COMPUTER_NET_RECHARGE_DIALOG:
			// fqc delete ɾ��ȡ����ť 7/14/2010
			final View computer_net_recharge_view = factory.inflate(
					R.layout.computer_net_recharge_dialog, null);
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.computer_online_recharge).setTitle(
					R.string.computer_net_recharge).setView(
					computer_net_recharge_view).setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							/* User clicked OK so do some stuff */
						}
					}).create();

			// ���� ��һ��ʹ��������ֵʱ��ע��Ի��� �³�2010/7/11
		case BANK_PHONE_CARD_REGISTER_DIALOG:

			final View phone_bank_card_view = factory.inflate(
					R.layout.bank_card_phone_register_dialog, null);
			return new AlertDialog.Builder(this).setIcon(
					R.drawable.bank_cards_phone_recharge1).setTitle(
					R.string.bank_cards_recharge).setView(phone_bank_card_view)
					.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									beginRegisterBankPhoneCard(phone_bank_card_view);
									/* User clicked OK so do some stuff */
								}
							}).setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

									/* User clicked cancel so do some stuff */
								}
							}).create();
		case PROGRESS_VALUE:
			progressDialog = new ProgressDialog(this);
			// progressdialog.setTitle("Indeterminate");
			progressDialog.setMessage("����������...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			return progressDialog;
		}
		// startActivity(toWebIntent);
		return null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_rechange_list_main);
		ListView listView = (ListView) findViewById(R.id.account_rechange_listview);

		list = getListForUserCenterAdapter();
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.account_recharge_listviw_item, new String[] { ICON,
						TITLE, BACK }, new int[] {
						R.id.account_recharge_listview_icon,
						R.id.account_recharge_listview_text });
		listView.setAdapter(adapter);

		OnItemClickListener onItemClickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				text = (TextView) view
						.findViewById(R.id.account_recharge_listview_text);
				String textString = text.getText().toString();
				// ���ӵ�¼���ж� �³� 20100715 ��¼֮��ֱ���������ҳ��
				ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
						AccountRecharge.this, "addInfo");
				String sessionIdStr = pre.getUserLoginInfo("sessionid");
				if (getString(R.string.computer_net_recharge)
						.equals(textString)) {
					showDialog(COMPUTER_NET_RECHARGE_DIALOG);
				} else {
					if (sessionIdStr.equals("")) {
						Intent intentSession = new Intent(AccountRecharge.this,
								UserLogin.class);
						startActivityForResult(intentSession, 0);
					} else {

						if (getString(R.string.bank_cards_recharge).equals(
								textString)) {
							showDialog(BANK_CARD_PHONE_DIALOG);
						} else if (getString(R.string.phone_cards_recharge)
								.equals(textString)) {
							showDialog(PHONE_CARD_RECHARGE_DIALOG);
						} else if (getString(R.string.zhfb_cards_recharge)
								.equals(textString)) {
							showDialog(ZFB_RECHARGE_DIALOG);
						} else if (getString(R.string.phone_bank_cards_recharge)
								.equals(textString)) {
							showDialog(PHONE_BANK_RECHARGE_DIALOG);
						} else if (getString(R.string.game_cards_recharge)
								.equals(textString)) {
							showDialog(GAME_CARD_DIALOG);
							// }else
							// if(getString(R.string.computer_net_recharge).equals(textString)){
							// showDialog(COMPUTER_NET_RECHARGE_DIALOG);
						} else if (getString(R.string.ruyihelper_balanceInquiry)
								.equals(textString)) {
							ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
									AccountRecharge.this, "addInfo");
							phonenum = shellRW.getUserLoginInfo("phonenum");
							sessionid = shellRW.getUserLoginInfo("sessionid");
							if (sessionid.equals("")) {
								Intent intentSession = new Intent(
										AccountRecharge.this, UserLogin.class);
								// startActivity(intentSession);
								startActivityForResult(intentSession, 0);
							} else {
								iHttp.whetherChange = false;
								String str = text.getText().toString();
								ShellRWSharesPreferences shellRW1 = new ShellRWSharesPreferences(
										AccountRecharge.this, "addInfo");
								phonenum = shellRW1
										.getUserLoginInfo("phonenum");
								sessionid = shellRW1
										.getUserLoginInfo("sessionid");
								// String
								// userId=shellRW.getUserLoginInfo("userId");
								// ����ѯ

								showDialog(PROGRESS_VALUE);
								Thread t = new Thread(new Runnable() {
									public void run() {
										String error_code = "00";
										while (iretrytimes < 3
												&& iretrytimes > 0) {

											re = jrtLot.findUserBalance(
													phonenum, sessionid);
											try {
												obj = new JSONObject(re);
												error_code = obj
														.getString("error_code");
												iretrytimes = 3;
											} catch (JSONException e) {
												iretrytimes--;
											}
											// �����Ƿ�ı�������ж� �³� 8.11

											if (iretrytimes == 0
													&& iHttp.whetherChange == false) {
												iHttp.whetherChange = true;
												if (iHttp.conMethord == iHttp.CMWAP) {
													iHttp.conMethord = iHttp.CMNET;
												} else {
													iHttp.conMethord = iHttp.CMWAP;
												}
												iretrytimes = 2;
												while (iretrytimes < 3
														&& iretrytimes > 0) {
													re = jrtLot
															.findUserBalance(
																	phonenum,
																	sessionid);
													try {
														obj = new JSONObject(re);
														error_code = obj
																.getString("error_code");
														iretrytimes = 3;
													} catch (JSONException e) {
														iretrytimes--;
													}
												}
											}
										}
										iretrytimes = 2;
										if (error_code.equals("0000")) {
											Message msg = new Message();
											msg.what = 18;
											handler.sendMessage(msg);

										} else if (error_code.equals("070002")) {
											Message msg = new Message();
											msg.what = 14;
											handler.sendMessage(msg);

										} else if (error_code.equals("4444")) {
											Message msg = new Message();
											msg.what = 15;
											handler.sendMessage(msg);

										} else if (error_code.equals("00")) {
											Message msg = new Message();
											msg.what = 16;
											handler.sendMessage(msg);
										} else {
											Message msg = new Message();
											msg.what = 17;
											handler.sendMessage(msg);
										}
									}
								});
								t.start();
							}
						}
					}

				}

			}

		};
		listView.setOnItemClickListener(onItemClickListener);

	}

	private List<Map<String, Object>> getListForUserCenterAdapter() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
		// ���п��绰��ֵ
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.bank_cards_phone_recharge1);
		map.put(TITLE, getString(R.string.bank_cards_recharge));
		// map.put(BACK, R.drawable.list_item);
		list.add(map);

		// �ֻ����ѳ�ֵ��
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.phone_cards_recharge2);
		map.put(TITLE, getString(R.string.phone_cards_recharge));
		// map.put(BACK, R.drawable.list_item);
		list.add(map);

		// ֧������ֵ
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.zhfb_cards_recharge3);
		map.put(TITLE, getString(R.string.zhfb_cards_recharge));
		// map.put(BACK, R.drawable.list_item);
		list.add(map);

		// �ֻ����г�ֵ
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.phone_bank_cards_recharge4);
		map.put(TITLE, getString(R.string.phone_bank_cards_recharge));
		// map.put(BACK, R.drawable.list_item);
		list.add(map);

		// ��Ϸ�㿨��ֵ
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.game_cards_recharge5);
		map.put(TITLE, getString(R.string.game_cards_recharge));
		// map.put(BACK, R.drawable.list_item);
		list.add(map);
		// �������ϳ�ֵ
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.computer_online_recharge);
		map.put(TITLE, getString(R.string.computer_net_recharge));
		// map.put(BACK, R.drawable.list_item);
		list.add(map);
		// ����ѯ
		map = new HashMap<String, Object>();
		map.put(ICON, R.drawable.yuechaxun);
		map.put(TITLE, getString(R.string.ruyihelper_balanceInquiry));
		// map.put(BACK, R.drawable.list_item);
		list.add(map);

		return list;
	}

	private void beginZFBRecharge(View Vi) {

		final EditText zfb_recharge_value = (EditText) Vi
				.findViewById(R.id.zfb_recharge_value);
		final String zfb_recharge_value_string = zfb_recharge_value.getText()
				.toString();
		final EditText zfb_account_number = (EditText) Vi
				.findViewById(R.id.zfb_account_number);
		String zfb_account_number_string = zfb_account_number.getText()
				.toString();
		PublicMethod.myOutput("***********zfb_recharge_value_string"
				+ zfb_recharge_value_string);
		PublicMethod.myOutput("***********zfb_account_number_string"
				+ zfb_account_number_string);

		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
				AccountRecharge.this, "addInfo");
		String sessionIdStr = pre.getUserLoginInfo("sessionid");
		if (sessionIdStr.equals("")) {
			Intent intentSession = new Intent(AccountRecharge.this,
					UserLogin.class);
			startActivity(intentSession);
		} else {
			// zlm 7.21 ����ж�
			if (zfb_recharge_value_string.equals("")
					|| zfb_recharge_value_string.length() == 0
					|| zfb_account_number_string.equals("")
					|| zfb_account_number_string.length() == 0) {
				Toast.makeText(this, "����Ϊ��!", Toast.LENGTH_LONG).show();
			} else {
				// ֧������ֵ�����ȡ
				// ��Ϊ�߳� 2010/7/9�³�
				showDialog(PROGRESS_VALUE);
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
								AccountRecharge.this, "addInfo");
						String phonenum = shellRW.getUserLoginInfo("phonenum");
						String sessionid = shellRW
								.getUserLoginInfo("sessionid");

						strAccesstype = "C";
						iHttp.whetherChange = false;
						ChargeInterface zfbChargeInterface = new ChargeInterface();

						// ��Ҫ���Ĳ����� 10 ��ֵ���

						String error_code = zfbChargeInterface.phonebankcharge(
								strAccesstype, phonenum, "0300",
								zfb_recharge_value_string, "zfb001", strExpand,
								sessionid);

						if (error_code.equals("000000")) {
							url = zfbChargeInterface.url;
							shellRW.setUserLoginInfo("url", url);
							zfb_recharge_value.setText("");
							zfb_account_number.setText("");
							Message msg = new Message();
							msg.what = 12;
							handler.sendMessage(msg);
							// �Ƶ�����handle��2010/7/9�³�
							// Intent toWebIntent = new
							// Intent(AccountRecharge.this,
							// WebViewRequst.class);
							// startActivity(toWebIntent);
						} else if (error_code.equals("070002")) {
							zfb_recharge_value.setText("");
							zfb_account_number.setText("");
							Message msg = new Message();
							msg.what = 7;
							handler.sendMessage(msg);
							// Toast.makeText(AccountRecharge.this, "���¼",
							// Toast.LENGTH_SHORT).show();
						} else if (error_code.equals("00")) {
							zfb_recharge_value.setText("");
							zfb_account_number.setText("");
							Message msg = new Message();
							msg.what = 8;
							handler.sendMessage(msg);
							// Toast.makeText(AccountRecharge.this, "�����쳣",
							// Toast.LENGTH_SHORT).show();
						} else {
							zfb_recharge_value.setText("");
							zfb_account_number.setText("");
							Message msg = new Message();
							msg.what = 9;
							handler.sendMessage(msg);
							// Toast.makeText(AccountRecharge.this, "����ʧ��",
							// Toast.LENGTH_SHORT).show();
						}
					}

				});
				t.start();
				// ShellRWSharesPreferences shellRW = new
				// ShellRWSharesPreferences(AccountRecharge.this, "addInfo");
				// String phonenum = shellRW.getUserLoginInfo("phonenum");
				// String sessionid = shellRW.getUserLoginInfo("sessionid");
				// ChargeInterface zfbChargeInterface = new ChargeInterface();
				//          
				// // ��Ҫ���Ĳ����� 10 ��ֵ���
				// String
				// error_code=zfbChargeInterface.phonebankcharge(strAccesstype,
				// phonenum, "0300", "10", "zfb001",strExpand, sessionid);
				//          
				// if(error_code.equals("000000")){
				// String url=zfbChargeInterface.url;
				// shellRW.setUserLoginInfo("url", url);
				// Intent toWebIntent = new Intent(AccountRecharge.this,
				// WebViewRequst.class);
				// startActivity(toWebIntent);
				// }
				// else if(error_code.equals("070002")){
				// Toast.makeText(AccountRecharge.this, "���¼",
				// Toast.LENGTH_SHORT).show();
				// }
				// else if(error_code.equals("00")){
				// Toast.makeText(AccountRecharge.this, "�����쳣",
				// Toast.LENGTH_SHORT).show();
				// }
				// else{
				// Toast.makeText(AccountRecharge.this, "����ʧ��",
				// Toast.LENGTH_SHORT).show();
				// }
				/* User clicked OK so do some stuff */
			}
		}

	}

	private void beginPhoneBankRecharge(View Vi) {

		final EditText phone_bank_enter_value = (EditText) Vi
				.findViewById(R.id.phone_bank_enter_value);
		Editable phone_bank_value = phone_bank_enter_value.getText();
		final String phone_bank_value_string = String.valueOf(phone_bank_value);
		PublicMethod.myOutput("*********bankType phone_bank_value_string  "
				+ bankType + phone_bank_value_string);
		// �ֻ�������������
		// fqc edit 7/13/2010 �޸��ı�������Ҫ�� ���������ֵ���(����)�����֣�������������С��������λ����
		if ((!(bankType.equals("")) && bankType != null)
				&& (!(phone_bank_value_string.equals("")) && phone_bank_value_string != null)) {
			if (Integer.parseInt(phone_bank_value_string) >= 1) {
				// ��Ϊ�߳� 200/7/9 cc
				showDialog(PROGRESS_VALUE);
				iHttp.whetherChange = false;
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						ChargeInterface phonebankChargeInterface = new ChargeInterface();
						ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
								AccountRecharge.this, "addInfo");
						String phonenum = shellRW.getUserLoginInfo("phonenum");
						String sessionid = shellRW
								.getUserLoginInfo("sessionid");
						strAccesstype = "B";
						// �������� 0102 �ֻ����б�ʶ 10 ��ֵ��� y00003 ���б�ʶ��Ĭ�ϣ�

						String error_code = phonebankChargeInterface
								.phonebankcharge(strAccesstype, phonenum,
										bankType, phone_bank_value_string,
										"y00003", strExpand, sessionid);
						// bankChargeInterface.bankcharge("13651262537",
						// bankType, phone_bank_value_string,
						// "E04F5D10727CDE6CEC462B3317670128");

						if (error_code.equals("000000")) {
							url = phonebankChargeInterface.url;
							phone_bank_enter_value.setText("");
							Message msg = new Message();
							msg.what = 12;
							handler.sendMessage(msg);

							// String url=phonebankChargeInterface.url;
							// url=phonebankChargeInterface.url;
							shellRW.setUserLoginInfo("url", url);
							// Intent in = new Intent(AccountRecharge.this,
							// WebViewRequst.class);
							// startActivity(toWebIntent);
						} else if (error_code.equals("070002")) {
							phone_bank_enter_value.setText("");
							Message msg = new Message();
							msg.what = 7;
							handler.sendMessage(msg);

							// Toast.makeText(this, "���¼",
							// Toast.LENGTH_SHORT).show();
						} else if (error_code.equals("00")) {
							phone_bank_enter_value.setText("");
							Message msg = new Message();
							msg.what = 8;
							handler.sendMessage(msg);
							phone_bank_enter_value.setText("");
							// Toast.makeText(this, "�����쳣",
							// Toast.LENGTH_SHORT).show();
						} else {
							phone_bank_enter_value.setText("");
							Message msg = new Message();
							msg.what = 9;
							handler.sendMessage(msg);

							// Toast.makeText(this, "����ʧ��",
							// Toast.LENGTH_SHORT).show();
						}
					}

				});
				t.start();
				// ChargeInterface phonebankChargeInterface = new
				// ChargeInterface();
				// ShellRWSharesPreferences shellRW = new
				// ShellRWSharesPreferences(this, "addInfo");
				// String phonenum = shellRW.getUserLoginInfo("phonenum");
				// String sessionid = shellRW.getUserLoginInfo("sessionid");
				//	       
				// // �������� 0102 �ֻ����б�ʶ 10 ��ֵ��� y00003 ���б�ʶ��Ĭ�ϣ�
				//	       
				// String error_code =
				// phonebankChargeInterface.phonebankcharge(strAccesstype,
				// phonenum, "0102", phone_bank_value_string, "y00003",
				// strExpand, sessionid);
				// // bankChargeInterface.bankcharge("13651262537", bankType,
				// phone_bank_value_string, "E04F5D10727CDE6CEC462B3317670128");
				//	       
				// if(error_code.equals("000000")){
				// String url=phonebankChargeInterface.url;
				// shellRW.setUserLoginInfo("url", url);
				// Intent in = new Intent(AccountRecharge.this,
				// WebViewRequst.class);
				// startActivity(toWebIntent);
				// }
				// else if(error_code.equals("070002")){
				// Toast.makeText(this, "���¼", Toast.LENGTH_SHORT).show();
				// }
				// else if(error_code.equals("00")){
				// Toast.makeText(this, "�����쳣", Toast.LENGTH_SHORT).show();
				// }
				// else{
				// Toast.makeText(this, "����ʧ��", Toast.LENGTH_SHORT).show();
				// }
			} else
				Toast.makeText(this, "��ֵ�������Ϊ1Ԫ!", Toast.LENGTH_LONG).show();
		} else
			Toast.makeText(this, "����Ϊ��!", Toast.LENGTH_LONG).show();
	}

	// ���п�������ֵ
	private void beiginBankCardPhoneOnline(View vi) {
		EditText bank_card_phone_recharge_value = (EditText) vi
				.findViewById(R.id.bank_card_phone_recharge_value);
		final String bank_card_phone_recharge_value_string = bank_card_phone_recharge_value
				.getText().toString();
		EditText bank_card_phone_phone_num = (EditText) vi
				.findViewById(R.id.bank_card_phone_phone_num);
		final String bank_card_phone_phone_num_string = bank_card_phone_phone_num
				.getText().toString();
		EditText bank_card_phone_bankid = (EditText) vi
				.findViewById(R.id.bank_card_phone_bankid);
		final String bank_card_phone_bankid_string = bank_card_phone_bankid
				.getText().toString();
		// if(Integer.parseInt(bank_card_phone_recharge_value_string)<20){
		// Toast.makeText(getBaseContext(), "��ֵ������Ϊ20Ԫ��20Ԫ����",
		// Toast.LENGTH_LONG).show();
		// }
		// if(bank_card_phone_phone_num_string.length() != 11){
		// Toast.makeText(getBaseContext(), "�ֻ��ų��ȱ���Ϊ11λ!",
		// Toast.LENGTH_LONG).show();
		// }
		// fqc edit 7/13/2010
		// ��ֵ���20Ԫ���ϵ�����������20������������
		// �ֻ����룺����11λ ��
		PublicMethod
				.myOutput("************bank_card_phone_recharge_value_string  bank_card_phone_phone_num_string"
						+ bank_card_phone_recharge_value_string
						+ bank_card_phone_phone_num_string);

		// ���п�������ֵ��������

		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				AccountRecharge.this, "addInfo");
		final String phonenum = shellRW.getUserLoginInfo("phonenum");
		final String sessionid = shellRW.getUserLoginInfo("sessionid");

		// ��Ҫ���Ĳ��� 100 ��ֵ��� 106232601047067 ���п��� acceptphonenum �ӵ绰�ֻ���
		// ui��ȱ�����п���
		// fqc edit 7/13/2010 �ֻ������߳�ֵ���ı���ʽ ��ֵ���20Ԫ���ϵ�����������20������������ �ֻ����룺����11λ
		// ��
		if ((!(bank_card_phone_phone_num_string.equals("")) && bank_card_phone_phone_num_string != null)
				&& (!(bank_card_phone_recharge_value_string.equals("")) && bank_card_phone_recharge_value_string != null)
				&& (!(bank_card_phone_bankid_string.equals("")) && bank_card_phone_bankid_string != null)) {
			if (bank_card_phone_phone_num_string.length() == 11) {
				if (Integer.parseInt(bank_card_phone_recharge_value_string) >= 20) {
					// showDialog(PROGRESS_VALUE);
					// // ��Ϊ�߳� �³� 2010/7/9
					// Thread t=new Thread(new Runnable(){
					// @Override
					// public void run() {
					// // TODO Auto-generated method stub
					String acceptphonenum = bank_card_phone_phone_num_string;
					strExpand = " , , , ," + acceptphonenum + ",true,";

					bank_card_id = bank_card_phone_bankid_string;
					bank_phone_card_net(phonenum,
							bank_card_phone_recharge_value_string,
							bank_card_id, sessionid);
					bank_card_phone_recharge_value.setText("");
					bank_card_phone_phone_num.setText("");
					bank_card_phone_bankid.setText("");
					// ChargeInterface phonecardChargeInterface = new
					// ChargeInterface();
					//
					// String
					// error_code=phonecardChargeInterface.phonecardcharge("C",phonenum,"0101","10",bank_card_phone_recharge_value_string,"106232601047067","","dna001",strExpand,sessionid);
					// if(error_code.equals("00A3")){
					// Message msg=new Message();
					// msg.what=10;
					// handler.sendMessage(msg);
					// // Toast.makeText(AccountRecharge.this, "�������ύ��",
					// Toast.LENGTH_SHORT).show();
					// }else if(error_code.equals("T437")){
					// Message msg=new Message();
					// msg.what=11;
					// handler.sendMessage(msg);
					// // Toast.makeText(AccountRecharge.this, "���ύ��ϸ��Ϣ",
					// Toast.LENGTH_SHORT).show();
					// }else if(error_code.equals("070002")){
					// Message msg=new Message();
					// msg.what=7;
					// handler.sendMessage(msg);
					// // Toast.makeText(AccountRecharge.this, "���¼",
					// Toast.LENGTH_SHORT).show();
					// }else if(error_code.equals("00")){
					// Message msg=new Message();
					// msg.what=8;
					// handler.sendMessage(msg);
					// // Toast.makeText(AccountRecharge.this, "�����쳣",
					// Toast.LENGTH_SHORT).show();
					// }else{
					// Message msg=new Message();
					// msg.what=9;
					// handler.sendMessage(msg);
					// // Toast.makeText(AccountRecharge.this, "����ʧ��",
					// Toast.LENGTH_SHORT).show();
					// }
					// }
					//	    			  
					// });
					// t.start();
					// String acceptphonenum=bank_card_phone_phone_num_string;
					// strExpand= " , , , ,"+acceptphonenum+",true,";
					// ChargeInterface phonecardChargeInterface = new
					// ChargeInterface();
					//
					// String
					// error_code=phonecardChargeInterface.phonecardcharge("C",phonenum,"0101","10",bank_card_phone_recharge_value_string,"106232601047067","","dna001",strExpand,sessionid);
					// if(error_code.equals("00A3")){
					// Toast.makeText(AccountRecharge.this, "�������ύ��",
					// Toast.LENGTH_SHORT).show();
					// }else if(error_code.equals("T437")){
					// Toast.makeText(AccountRecharge.this, "���ύ��ϸ��Ϣ",
					// Toast.LENGTH_SHORT).show();
					// }else if(error_code.equals("070002")){
					// Toast.makeText(AccountRecharge.this, "���¼",
					// Toast.LENGTH_SHORT).show();
					// }else if(error_code.equals("00")){
					// Toast.makeText(AccountRecharge.this, "�����쳣",
					// Toast.LENGTH_SHORT).show();
					// }else{
					// Toast.makeText(AccountRecharge.this, "����ʧ��",
					// Toast.LENGTH_SHORT).show();
					// }
				} else {
					Toast.makeText(this, "���ٳ�ֵ���Ϊ20Ԫ", Toast.LENGTH_LONG)
							.show();
				}
			} else {
				Toast.makeText(getBaseContext(), "�ֻ��ų��ȱ���Ϊ11λ!",
						Toast.LENGTH_LONG).show();
			}
		} else
			Toast.makeText(this, "����Ϊ��", Toast.LENGTH_LONG).show();
		/* User clicked OK so do some stuff */

	}

	// 20100711 �³� ��������ע���

	private void beginRegisterBankPhoneCard(View vi) {

		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				AccountRecharge.this, "addInfo");
		final String phonenum = shellRW.getUserLoginInfo("phonenum");
		final String sessionid = shellRW.getUserLoginInfo("sessionid");
		// �õ���ʵ���������֤�š������������ڵء������������ڵء����п��š����ֻ��� 20100711
		EditText bank_card_phone_username = (EditText) vi
				.findViewById(R.id.bank_card_phone_user_name);
		final String bank_card_phone_username_string = bank_card_phone_username
				.getText().toString();

		EditText bank_card_phone_user_idnum = (EditText) vi
				.findViewById(R.id.bank_card_phone_user_idnum);
		final String bank_card_phone_user_idnum_string = bank_card_phone_user_idnum
				.getText().toString();

		EditText bank_card_phone_open_bank = (EditText) vi
				.findViewById(R.id.bank_card_phone_open_bank);
		final String bank_card_phone_open_bank_string = bank_card_phone_open_bank
				.getText().toString();

		EditText bank_card_phone_open_bankuser_address = (EditText) vi
				.findViewById(R.id.bank_card_phone_open_bankuser_address);
		final String bank_card_phone_open_bankuser_address_string = bank_card_phone_open_bankuser_address
				.getText().toString();

		EditText bank_card_phone_bankid = (EditText) vi
				.findViewById(R.id.bank_card_phone_bankid);
		final String bank_card_phone_bankid_string = bank_card_phone_bankid
				.getText().toString();

		EditText bank_card_phone_recharge_value = (EditText) vi
				.findViewById(R.id.bank_card_phone_recharge_value);
		final String bank_card_phone_recharge_value_string = bank_card_phone_recharge_value
				.getText().toString();

		EditText bank_card_phone_phone_num = (EditText) vi
				.findViewById(R.id.bank_card_phone_phone_num);
		final String bank_card_phone_phone_num_string = bank_card_phone_phone_num
				.getText().toString();

		strExpand = bank_card_phone_username_string + ","
				+ bank_card_phone_user_idnum_string + ","
				+ bank_card_phone_open_bank_string + ","
				+ bank_card_phone_open_bankuser_address_string + ","
				+ bank_card_phone_phone_num_string + ",false,";

		bank_card_id = bank_card_phone_bankid_string;

		bank_phone_card_net(phonenum, bank_card_phone_recharge_value_string,
				bank_card_id, sessionid);
		// bank_card_phone_username.setText("");

	}

	// �ֻ�����ֵ
	private void beginPhoneCardRecharge(View Vi) {

		final EditText phone_card_rechargecard_info = (EditText) Vi
				.findViewById(R.id.phone_card_rechargecard_info);
		final String phone_card_rechargecard_info_string = phone_card_rechargecard_info
				.getText().toString();
		final EditText phone_card_rechargecard_password = (EditText) Vi
				.findViewById(R.id.phone_card_rechargecard_password);
		final String phone_card_rechargecard_password_string = phone_card_rechargecard_password
				.getText().toString();
		// final EditText phone_card_rechargecard_value = (EditText) Vi
		// .findViewById(R.id.phone_card_rechargecard_value);
		// final String phone_card_rechargecard_value_string =
		// phone_card_rechargecard_value
		// .getText().toString();
		PublicMethod
				.myOutput("**********phone_card_rechargecard_info_string  phone_card_rechargecard_password_string  phone_card_rechargecard_value_string"
						+ phone_card_rechargecard_info_string
						+ phone_card_rechargecard_password_string);
		PublicMethod.myOutput("**********phoneCardType " + phoneCardType);
		PublicMethod.myOutput("**********phoneCardValue " + phoneCardValue);
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,
				"addInfo");
		final String phonenum = shellRW.getUserLoginInfo("phonenum");
		final String sessionid = shellRW.getUserLoginInfo("sessionid");
		final ChargeInterface phonecardChargeInterface = new ChargeInterface();

		// �ֻ���ֵ����ֵ
		// �������壺0203 ��ֵ������ 10 ��ֵǮ�� 5000��ֵ�ܶ� 10623260104706723 ��ֵ����
		// 261324590869999653 ��ֵ���� y00003���б�ʶĬ��
		// uiȱ�ٳ�ֵ��Ǯ��
		if ((!(phone_card_rechargecard_info_string.equals("")) && phone_card_rechargecard_info_string != null)
				&& (!(phone_card_rechargecard_password_string.equals("")) && phone_card_rechargecard_password_string != null)) {
			if (isCardString(phone_card_rechargecard_info_string)) {
				// ��Ϊ�߳� �³� 200/7/9
				showDialog(PROGRESS_VALUE);
				iHttp.whetherChange = false;
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// String
						// error_code=phonecardChargeInterface.phonecardcharge("C",phonenum,phoneCardType,phone_card_rechargecard_value_string,phone_card_rechargecard_value_string,phone_card_rechargecard_info_string,phone_card_rechargecard_password_string,"y00003","",sessionid);

						String error_code = "";
						if (phoneCardType == DIANXIN) {
							error_code = phonecardChargeInterface
									.phonecardcharge(
											"C",
											phonenum,
											phoneCardType,
											phoneCardValue,
											phoneCardValue,
											phone_card_rechargecard_info_string,
											phone_card_rechargecard_password_string,
											"gyj001", "", sessionid);
						} else {
							error_code = phonecardChargeInterface
									.phonecardcharge(
											"C",
											phonenum,
											phoneCardType,
											phoneCardValue,
											phoneCardValue,
											phone_card_rechargecard_info_string,
											phone_card_rechargecard_password_string,
											"y00003", "", sessionid);
						}

						if (error_code.equals("000000")) {
							Message msg = new Message();
							msg.what = 6;
							handler.sendMessage(msg);
							// �³� �༭����Ϊ�� 20100716
							phone_card_rechargecard_info.setText("");
							phone_card_rechargecard_password.setText("");
							// phone_card_rechargecard_value.setText("");

							// Toast.makeText(this, "��ֵ������",
							// Toast.LENGTH_SHORT).show();
						} else if (error_code.equals("070002")) {
							Message msg = new Message();
							msg.what = 7;
							handler.sendMessage(msg);
							// Toast.makeText(this, "���¼",
							// Toast.LENGTH_SHORT).show();
						} else if (error_code.equals("00")) {
							// Toast.makeText(this, "�����쳣",
							// Toast.LENGTH_SHORT).show();
							Message msg = new Message();
							msg.what = 8;
							handler.sendMessage(msg);
							// �³� �༭����Ϊ�� 20100716
							phone_card_rechargecard_info.setText("");
							phone_card_rechargecard_password.setText("");
							// phone_card_rechargecard_value.setText("");
						} else {
							Message msg = new Message();
							msg.what = 9;
							handler.sendMessage(msg);

							// �³� �༭����Ϊ�� 20100716
							phone_card_rechargecard_info.setText("");
							phone_card_rechargecard_password.setText("");
							// phone_card_rechargecard_value.setText("");
							// Toast.makeText(this, "����ʧ��",
							// Toast.LENGTH_SHORT).show();
						}

					}

				});
				t.start();
				// String
				// error_code=phonecardChargeInterface.phonecardcharge("C",phonenum,"0203","10",phone_card_rechargecard_value_string,phone_card_rechargecard_info_string,phone_card_rechargecard_password_string,"y00003","",sessionid);
				//          
				// if(error_code.equals("000000")){
				// Toast.makeText(this, "��ֵ������", Toast.LENGTH_SHORT).show();
				// }else if(error_code.equals("070002")){
				// Toast.makeText(this, "���¼", Toast.LENGTH_SHORT).show();
				// }else if(error_code.equals("00")){
				// Toast.makeText(this, "�����쳣", Toast.LENGTH_SHORT).show();
				// }else{
				// Toast.makeText(this, "����ʧ��", Toast.LENGTH_SHORT).show();
				// }
			} else {
				Toast.makeText(this, "��ֵ�����к�ӦΪ���ֻ���ĸ", Toast.LENGTH_LONG).show();
			}
		} else
			Toast.makeText(this, "����Ϊ��", Toast.LENGTH_LONG).show();
	}

	private void beginGameCardRecharge(View Vi) {

		final EditText game_card_number = (EditText) Vi
				.findViewById(R.id.game_card_number);
		game_card_number_string = game_card_number.getText().toString();

		final EditText game_card_password = (EditText) Vi
				.findViewById(R.id.game_card_password);
		game_card_password_string = game_card_password.getText().toString();

		// EditText game_card_recharge_value = (EditText)
		// Vi.findViewById(R.id.game_card_recharge_value);
		// game_card_recharge_value_string =
		// game_card_recharge_value.getText().toString();

		final EditText game_card_total_value = (EditText) Vi
				.findViewById(R.id.game_card_total_value);
		game_card_total_value_string = game_card_total_value.getText()
				.toString();
		PublicMethod
				.myOutput("*********gameCardType game_card_number_string  game_card_password_string"
						+ " ---gameCardType-------- "
						+ gameCardType
						+ " ----game_card_number_string----- "
						+ game_card_number_string
						+ "----game_card_password_string--------  "
						+ game_card_password_string);
		PublicMethod.myOutput("*********game_card_total_value_string" + "   "
				+ game_card_total_value_string);
		// if(Integer.parseInt(game_card_total_value_string)>=1){
		if (game_card_number_string.equals("")
				|| game_card_password_string.equals("")
				|| game_card_total_value_string.equals("")) {
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
		} else if (isCardString(game_card_number_string)) {

			// ���������ʾ ��Ϊ�߳� �³� 2010/7/9
			showDialog(PROGRESS_VALUE);
			iHttp.whetherChange = false;
			Thread t = new Thread(new Runnable() {
				public void run() {

					// EpayChargeInterface epayChargeInterface = new
					// EpayChargeInterface();

					// ��Ϸ�㿨�ĳ��½ӿ�
					ChargeInterface gameCardRecharge = new ChargeInterface();

					ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
							AccountRecharge.this, "addInfo");

					String sessionid = shellRW.getUserLoginInfo("sessionid");
					String phonenum = shellRW.getUserLoginInfo("phonenum");
					// error_code=epayChargeInterface.yeepaycharge("13121795856",
					// game_card_number_string, game_card_password_string,
					// game_card_recharge_value_string,game_card_total_value_string,"ZHENGTU","BE9EDF6A0C22F99288076C31EB4A1ED4");
					// game_card_recharge_value_string�Ѿ�ȥ������ҲӦɾ���ĳ�game_card_total_value_string
					// �³� 20100913
					// error_code=epayChargeInterface.yeepaycharge(phonenum,
					// game_card_number_string, game_card_password_string,
					// game_card_total_value_string,game_card_total_value_string,gameCardType,sessionid);
					error_code = gameCardRecharge.phonecardcharge("C",
							phonenum, gameCardType,
							game_card_total_value_string,
							game_card_total_value_string,
							game_card_number_string, game_card_password_string,
							"y00003", "", sessionid);
					// error_code=gameCardRecharge.phonecardcharge("C",
					// phonenum, gameCardType, "1",
					// game_card_total_value_string, game_card_number_string,
					// game_card_password_string, "y00003", "", sessionid);
					PublicMethod.myOutput("------------------charge"
							+ error_code);

					PublicMethod.myOutput("---" + error_code);
					if (error_code.equals("000000")) {
						Message msg = new Message();
						msg.what = 6;
						handler.sendMessage(msg);
						// ��Ϣ��Ϊ�� �³� 20100716
						game_card_total_value.setText("");
						game_card_password.setText("");
						game_card_number.setText("");

						game_card_number_string = "";
						game_card_password_string = "";
						// game_card_recharge_value_string=""; ɾ��
						game_card_total_value_string = "";

					} else if (error_code.equals("070002")) {
						Message msg = new Message();
						msg.what = 7;
						handler.sendMessage(msg);
					} else if (error_code.equals("0012")) {
						Message msg = new Message();
						msg.what = 2;
						handler.sendMessage(msg);
						// ��Ϣ��Ϊ�� �³� 20100716
						game_card_total_value.setText("");
						game_card_password.setText("");
						game_card_number.setText("");
					} else if (error_code.equals("4444")) {
						Message msg = new Message();
						msg.what = 3;
						// msg.getData().putString("get","ϵͳ���㣬���Ժ�");
						handler.sendMessage(msg);
						// ��Ϣ��Ϊ�� �³� 20100716
						game_card_total_value.setText("");
						game_card_password.setText("");
						game_card_number.setText("");
					} else if (error_code.equals("00")) {
						Message msg = new Message();
						msg.what = 8;
						handler.sendMessage(msg);
						// ��Ϣ��Ϊ�� �³� 20100716
						game_card_total_value.setText("");
						game_card_password.setText("");
						game_card_number.setText("");

					} else {
						Message msg = new Message();
						msg.what = 9;
						handler.sendMessage(msg);
						// ��Ϣ��Ϊ�� �³� 20100716
						game_card_total_value.setText("");
						game_card_password.setText("");
						game_card_number.setText("");
					}

				}

			});
			t.start();
		} else {
			Toast.makeText(getBaseContext(), "���Ÿ�ʽ���벻��ȷ", Toast.LENGTH_LONG)
					.show();
		}
	}

	// ��Ҫ�������� �ʵ�������� 2010/7/11
	/**
	 * ���п�������ֵ����
	 * 
	 * @param phonenum
	 *            ���յ绰�ֻ���
	 * @param bank_card_phone_totalAmount
	 *            ��ֵ���
	 * @param bank_card_Num
	 *            ���п���
	 * @param sessionid
	 *            ��¼��sessionid
	 */
	private void bank_phone_card_net(final String phonenum,
			final String bank_card_phone_totalAmount,
			final String bank_card_Num, final String sessionid) {
		showDialog(PROGRESS_VALUE);
		iHttp.whetherChange = false;
		// ��Ϊ�߳� �³� 2010/7/9
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// String acceptphonenum=bank_card_phone_phone_num_string;
				// strExpand= " , , , ,"+acceptphonenum+",true,";
				ChargeInterface phonecardChargeInterface = new ChargeInterface();
				// Ǯ���Է�Ϊ��λ������̨ �ʼӡ�00�� �³� 20100714
				String error_code = phonecardChargeInterface.phonecardcharge(
						"C", phonenum, "0101", bank_card_phone_totalAmount,
						bank_card_phone_totalAmount, bank_card_Num, "",
						"dna001", strExpand, sessionid);
				PublicMethod.myOutput("---phonenum----" + phonenum
						+ "---bank_card_phone------"
						+ bank_card_phone_totalAmount
						+ "----bank_card_Num-------" + bank_card_Num
						+ "----strExpand-----" + strExpand);
				if (error_code.equals("00A3")) {
					bankCardNo = bank_card_Num;
					Message msg = new Message();
					msg.what = 10;
					handler.sendMessage(msg);
					// Toast.makeText(AccountRecharge.this, "�������ύ��",
					// Toast.LENGTH_SHORT).show();
				} else if (error_code.equals("T437")
						|| error_code.equals("T438")) {
					Message msg = new Message();
					msg.what = 11;
					handler.sendMessage(msg);
					// Toast.makeText(AccountRecharge.this, "���ύ��ϸ��Ϣ",
					// Toast.LENGTH_SHORT).show();
				} else if (error_code.equals("070002")) {
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);
					// Toast.makeText(AccountRecharge.this, "���¼",
					// Toast.LENGTH_SHORT).show();
				} else if (error_code.equals("T404")) {
					Message msg = new Message();
					msg.what = 13;
					handler.sendMessage(msg);
				} else if (error_code.equals("00")) {

					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);
					// Toast.makeText(AccountRecharge.this, "�����쳣",
					// Toast.LENGTH_SHORT).show();
				} else {
					Message msg = new Message();
					msg.what = 9;
					handler.sendMessage(msg);
					// Toast.makeText(AccountRecharge.this, "����ʧ��",
					// Toast.LENGTH_SHORT).show();
				}
			}

		});
		t.start();
	}

	// ��ʾ��
	private void alert(String string) {
		Builder dialog = new AlertDialog.Builder(this).setTitle(string)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
		dialog.show();

	}

	private boolean isCardString(String cardNumber) {
		int length = cardNumber.length();
		boolean isRight = true;
		for (int i = 0; i < length - 1; i++) {
			if (cardNumber.charAt(i) < '0'
					|| (cardNumber.charAt(i) > '9' && cardNumber.charAt(i) < 'A')
					|| (cardNumber.charAt(i) > 'Z' && cardNumber.charAt(i) < 'a')
					|| (cardNumber.charAt(i) > 'z')) {
				isRight = false;
			}
		}
		return isRight;

	}

	@Override
	// ��Ӧ�Ի��򵯳� 20100715 �³�
	/**
	 * intent�ص�����
	 * 
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			String textString = text.getText().toString();

			if (getString(R.string.bank_cards_recharge).equals(textString)) {
				showDialog(BANK_CARD_PHONE_DIALOG);
			} else if (getString(R.string.phone_cards_recharge).equals(
					textString)) {
				showDialog(PHONE_CARD_RECHARGE_DIALOG);
			} else if (getString(R.string.zhfb_cards_recharge).equals(
					textString)) {
				showDialog(ZFB_RECHARGE_DIALOG);
			} else if (getString(R.string.phone_bank_cards_recharge).equals(
					textString)) {
				showDialog(PHONE_BANK_RECHARGE_DIALOG);
			} else if (getString(R.string.game_cards_recharge).equals(
					textString)) {
				showDialog(GAME_CARD_DIALOG);
			}
			// else
			// if(getString(R.string.computer_net_recharge).equals(textString)){
			// showDialog(COMPUTER_NET_RECHARGE_DIALOG);
			// }

			break;
		default:
			Toast.makeText(AccountRecharge.this, "δ��¼�ɹ�", Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}

	// �û�����-����ѯ
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
				+ getString(R.string.usercenter_currentBalance)
				+ deposit_amount + getString(R.string.unit) + "\n"
				+ getString(R.string.usercenter_withdrawBalance) + Valid_amount
				+ getString(R.string.unit) + "\n"
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

}