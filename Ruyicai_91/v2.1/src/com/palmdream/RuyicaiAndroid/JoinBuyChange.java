package com.palmdream.RuyicaiAndroid;

import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.netintface.iHttp;
import com.palmdream.netintface.jrtLot;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * �������
 * 
 * @author Administrator
 * 
 */
public class JoinBuyChange extends Activity {
	public static final String SUCCESS = "joinsuccess";
	ImageView back;
	TextView titleTop;
	EditText editName;
	EditText editNum;
	EditText editMoney;
	TextView textMoney;
	Button buttonMoney;
	EditText editTell;
	Spinner spinnerMoney;
	Spinner spinnerDeduct;
	EditText editBuy;
	EditText editLeast;
	Spinner spinnerType;
	Button buttonSubmit;
	ProgressDialog progressdialog;
	private boolean start;
	private String sessionid;//
	private int iretrytimes = 2;
	private JSONObject obj;

	String re;
	// showDialog(0);//��ʾ������
	/* ���� */
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				progressdialog.dismiss();
				Toast.makeText(JoinBuyChange.this, "�������ɹ���",
						Toast.LENGTH_SHORT).show();
				finish();
				Intent intent = new Intent(SUCCESS);
				sendBroadcast(intent);
				break;
			case 1:
				progressdialog.dismiss();
				Toast.makeText(JoinBuyChange.this, "�����ļ���ʽ����ȷ��",
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				progressdialog.dismiss();

				Toast.makeText(JoinBuyChange.this, "�ͻ������뷽���ܽ�����̨�õ��ܽ�һ�£�",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				progressdialog.dismiss();
				try {
					String deposit_amount = PublicMethod.changeMoney(obj
							.getString("deposit_amount"));
					update(deposit_amount);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				progressdialog.dismiss();
				update("");
				Toast.makeText(JoinBuyChange.this, "����ѯʧ�ܣ�",
						Toast.LENGTH_SHORT).show();
				break;
			case 5:
				progressdialog.dismiss();
				Toast.makeText(JoinBuyChange.this, "�����ֹ��������",
						Toast.LENGTH_SHORT).show();
				break;
			case 6:
				progressdialog.dismiss();
				Toast.makeText(JoinBuyChange.this, "����ʧ�ܣ�",
						Toast.LENGTH_SHORT).show();
				break;
			case 7:
				progressdialog.dismiss();
				Toast.makeText(JoinBuyChange.this, "��·�쳣��", Toast.LENGTH_SHORT)
						.show();
				break;
			case 8:
				progressdialog.dismiss();
				Toast.makeText(JoinBuyChange.this, "�������ʧ�ܣ��Ϲ������򱣵׽���Ϊ�գ�",
						Toast.LENGTH_SHORT).show();
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ������", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾע��ɹ�
				break;
			case 10:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ѽ���", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ�û���ע��
				break;
			case 11:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "ϵͳ���㣬���Ժ�", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾϵͳ���㣬���Ժ�
				break;
			case 12:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�޿����߼���", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ�úű���ͣ����ϵ�ͷ�
				break;
			case 13:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "���ڲ���������", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ�úű���ͣ����ϵ�ͷ�
				break;
			case 14:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�������ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ�úű���ͣ����ϵ�ͷ�
				break;
			case 15:
				progressdialog.dismiss();

				progressdialog.dismiss();// δ��¼
				Intent intent1 = new Intent(UserLogin.UNSUCCESS);
				sendBroadcast(intent1);
				ShellRWSharesPreferences shellRW1 = new ShellRWSharesPreferences(
						JoinBuyChange.this, "addInfo");
				shellRW1.setUserLoginInfo("sessionid", "");
				Intent intent2 = new Intent(JoinBuyChange.this, UserLogin.class);
				startActivity(intent2);
				break;
			}
		}

	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ----- ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.join_buy_change);

		init();// ��ʼ�����
		onClik();// ע�����
		getDate();// ��ȡ����
		updateConnection();// ������ȡ����
	}

	private void init() {
		back = (ImageView) findViewById(R.id.join_buy_change_image_back);
		titleTop = (TextView) findViewById(R.id.join_buy_change_text_title);
		editName = (EditText) findViewById(R.id.join_buy_change_edit_name);
		editNum = (EditText) findViewById(R.id.join_buy_change_edit_num);
		editMoney = (EditText) findViewById(R.id.join_buy_change_edit_money);
		textMoney = (TextView) findViewById(R.id.join_buy_change_text_money);
		buttonMoney = (Button) findViewById(R.id.join_buy_change_button_money);
		editTell = (EditText) findViewById(R.id.join_buy_change_edit_tell);
		spinnerMoney = (Spinner) findViewById(R.id.join_buy_change_spinner_money);
		spinnerDeduct = (Spinner) findViewById(R.id.join_buy_change_spinner_deduct);
		editBuy = (EditText) findViewById(R.id.join_buy_change_edit_buy);
		editLeast = (EditText) findViewById(R.id.join_buy_change_edit_buy_least);
		spinnerType = (Spinner) findViewById(R.id.join_buy_change_spinner_type);
		buttonSubmit = (Button) findViewById(R.id.join_buy_change_button_submit);
	}

	private void onClik() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		spinnerMoney.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int position = spinnerMoney.getSelectedItemPosition();
				if (position == 0) {
					oneAmt = "1";
				} else if (position == 1) {
					oneAmt = "2";
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		spinnerDeduct.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int position = spinnerDeduct.getSelectedItemPosition();
				PublicMethod.myOutput("*********position " + position);
				if (position == 0) {
					pushmoney = "0";
				} else if (position == 1) {
					pushmoney = "1";
				} else if (position == 2) {
					pushmoney = "2";
				} else if (position == 3) {
					pushmoney = "3";
				} else if (position == 4) {
					pushmoney = "4";
				} else if (position == 5) {
					pushmoney = "5";
				} else if (position == 6) {
					pushmoney = "6";
				} else if (position == 7) {
					pushmoney = "7";
				} else if (position == 8) {
					pushmoney = "8";
				} else if (position == 9) {
					pushmoney = "9";
				} else if (position == 10) {
					pushmoney = "10";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		spinnerType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int position = spinnerType.getSelectedItemPosition();
				PublicMethod.myOutput("*********position " + position);
				if (position == 0) {
					open_flag = "0";
				} else if (position == 1) {
					open_flag = "2";// �Ϲ��󹫿�
				} else if (position == 2) {
					open_flag = "1";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		buttonMoney.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = intent = new Intent(JoinBuyChange.this,
						RuyicaiAndroid.class);
				Bundle mBundle = new Bundle();
				mBundle.putInt("index", 2);// ��ֵ����
				mBundle.putBoolean("flag", true);
				intent.putExtras(mBundle);
				startActivity(intent);
			}
		});
		buttonSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buyamt_by_user = editBuy.getText().toString();
				baodiamt = editLeast.getText().toString();
				name = editName.getText().toString();
				allAmt = editMoney.getText().toString();
				int all = 0;
				int buy = 0;
				int bao = 0;
				if (!allAmt.equals("")) {
					all = Integer.parseInt(allAmt) / Integer.parseInt(oneAmt);
				}
				if (!buyamt_by_user.equals("")) {
					buy = Integer.parseInt(buyamt_by_user);
				}
				if (!baodiamt.equals("")) {
					bao = Integer.parseInt(baodiamt);
				}
				allNum = Integer.toString(all);// all����������
				if (!editTell.getText().toString().equals("")) {
					descible = editTell.getText().toString();
				}
				if (all < (buy + bao)) {

					Toast.makeText(JoinBuyChange.this,
							"�������ʧ�ܣ��Ϲ������ͱ��׷���֮�Ͳ��ܴ����ܷ�����", Toast.LENGTH_SHORT)
							.show();
				} else if (buyamt_by_user.equals("") | baodiamt.equals("")) {

					Toast.makeText(JoinBuyChange.this, "�Ϲ������򱣵׷�������Ϊ�գ�",
							Toast.LENGTH_SHORT).show();
				} else if (Integer.parseInt(buyamt_by_user) < 1) {
					Toast.makeText(JoinBuyChange.this, "�����Ϲ�һ�ݣ�����Ϊ�㣡",
							Toast.LENGTH_SHORT).show();
				} else {
					submit();// �ύ
				}

			}
		});
		editLeast.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				editLeast.setText("");
				return false;
			}
		});
		editBuy.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				editBuy.setText("");
				return false;
			}
		});
	}

	private void getDate() {
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				JoinBuyChange.this, "addInfo");
		phonenum = shellRW.getUserLoginInfo("phonenum");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		// ��ȡ����
		Bundle bundle = getIntent().getExtras();
		zhushu = bundle.getString("zhushu");// ��������
		allAmt = bundle.getString("allAmt");// ��������
		lotnoFlg = bundle.getString("lotno");// ���ֱ�ʶ���ֲʣ�QL730˫ɫ��B001ʱʱ�ʣ�DT5
		drawway = bundle.getString("drawway");
		muchcontent = bundle.getString("zhuma");
		num = bundle.getString("beishu");

	}

	private void updateConnection() {
		showDialog(0);
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
					// �����Ƿ�ı�������ж� �³� 8.11

					if (iretrytimes == 0 && iHttp.whetherChange == false) {
						iHttp.whetherChange = true;
						if (iHttp.conMethord == iHttp.CMWAP) {
							iHttp.conMethord = iHttp.CMNET;
						} else {
							iHttp.conMethord = iHttp.CMWAP;
						}
						iretrytimes = 2;
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
					}
				}
				iretrytimes = 2;
				if (error_code.equals("0000")) {
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);

				} else if (error_code.equals("070002")) {
					Message msg = new Message();
					msg.what = 15;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);
				}
			}
		});
		t.start();
	}

	private void update(String money) {
		// ���ֱ�ʶ���ֲʣ�QLC˫ɫ��B001ʱʱ��3D
		if (lotnoFlg.equals("QLC")) {
			titleTop.setText("���ֲʺ���");
		} else if (lotnoFlg.equals("B001")) {
			titleTop.setText("˫ɫ�����");
		} else if (lotnoFlg.equals("3D")) {
			titleTop.setText("����3D����");
		}
		editName.setText(phonenum);
		editNum.setText(zhushu);
		editMoney.setText(allAmt);
		editBuy.setText("1");
		editLeast.setText("0");
		editTell.setText("�������ʣ�����������");
		textMoney.setText(money + "Ԫ(��Ͷע���)");

	}

	String lotnoFlg;
	String oneAmt = "1";// ���ݽ��
	String muchcontent;// ��������/ע��
	String lotno;// ���ֱ��
	String batch;// ����
	String buyamt_by_user;// �Ϲ�����
	String open_flag = "0";// open_flag ������ʶ
	String pushmoney = "0";// pushmoney ���������
	String baodiamt;// ���׷���
	String name;// ������
	String num = "1";// ����
	String drawway; // �����淨
	String title;// ��������
	String zhushu;// ע��
	String allNum;// �����ܷ���
	String allAmt;// ��Ǯ��
	String bet_code;// ע�뼯��
	String phonenum;// �ֻ���
	String descible = "rychm";// ��������

	private void submit() {
		if (lotnoFlg.equals("B001")) {
			batch = getLotno("information4");
			lotno = "F47104";
		} else if (lotnoFlg.equals("QLC")) {
			batch = getLotno("information6");
			lotno = "F47102";
		} else if (lotnoFlg.equals("3D")) {
			batch = getLotno("information5");
			lotno = "F47103";
		}

		showDialog(0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				String pus = "";
				if (!pushmoney.equals("0")) {
					Double money = Double.parseDouble(pushmoney);
					pus = Double.toString(money / 100);
				} else {
					pus = pushmoney;
				}
				while (iretrytimes < 3 && iretrytimes > 0) {

					re = jrtLot.startJoin(phonenum, allAmt, num, oneAmt, name,
							muchcontent, batch, lotno, drawway, baodiamt,
							buyamt_by_user, allNum, pus, open_flag, sessionid,
							descible);

					try {
						obj = new JSONObject(re);
						error_code = obj.getString("error_code");
						Log.e("====", obj.toString());
						iretrytimes = 3;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						iretrytimes--;
					}

					if (iretrytimes == 0 && iHttp.whetherChange == false) {
						iHttp.whetherChange = true;
						if (iHttp.conMethord == iHttp.CMWAP) {
							iHttp.conMethord = iHttp.CMNET;
						} else {
							iHttp.conMethord = iHttp.CMWAP;
						}
						iretrytimes = 2;
						while (iretrytimes < 3 && iretrytimes > 0) {
							double money1 = Double.parseDouble(pushmoney);
							String pus1 = "";
							if (!pushmoney.equals("0")) {
								Double money = Double.parseDouble(pushmoney);
								pus1 = Double.toString(money / 100);
							} else {
								pus1 = pushmoney;
							}
							re = jrtLot.startJoin(phonenum, allAmt, num,
									oneAmt, name, muchcontent, batch, lotno,
									drawway, baodiamt, buyamt_by_user, allNum,
									pus1, open_flag, sessionid, descible);
							try {
								obj = new JSONObject(re);
								error_code = obj.getString("error_code");
								iretrytimes = 3;
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								iretrytimes--;

							}
						}
					}
				}
				iretrytimes = 2;
				if (error_code.equals("000000")) {
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);

				} else if (error_code.equals("030000")) {// �����ļ��ϴ�ʧ��
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				} else if (error_code.equals("020000")) {// �������۸ģ��ͻ������뷽���ܽ�����̨�õ��ܽ�һ��
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				} else if (error_code.equals("400009")) {// �����ֹ��������
					Message msg = new Message();
					msg.what = 5;
					handler.sendMessage(msg);
				} else if (error_code.equals("400005")) {// ����ʧ��
					Message msg = new Message();
					msg.what = 6;
					handler.sendMessage(msg);
				} else if (error_code.equals("00")) {// ��·�쳣
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);
				} else if (error_code.equals("0018")) {// �����쳣
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);
				} else if (error_code.equals("040006")) {
					Message msg = new Message();
					msg.what = 9;
					handler.sendMessage(msg);
				} else if (error_code.equals("1007")) {
					Message msg = new Message();
					msg.what = 10;
					handler.sendMessage(msg);
				} else if (error_code.equals("040007")) {
					Message msg = new Message();
					msg.what = 12;
					handler.sendMessage(msg);
				} else if (error_code.equals("4444")) {
					Message msg = new Message();
					msg.what = 11;
					handler.sendMessage(msg);
				} else if (error_code.equals("400003")) {
					Message msg = new Message();
					msg.what = 13;
					handler.sendMessage(msg);
				} else if (error_code.equals("070002")) {
					Message msg = new Message();
					msg.what = 15;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 14;
					handler.sendMessage(msg);
				}

			}

		}).start();
	}

	// �������ӿ�
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
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

	// ��õ�ǰ����
	public String getLotno(String string) {
		String error_code;
		String batchcode = "";
		// ShellRWSharesPreferences
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,
				"addInfo");
		String notice = shellRW.getUserLoginInfo(string);
		PublicMethod.myOutput("------------------lotnossq" + notice);
		// �ж�ȡֵ�Ƿ�Ϊ�� cc 2010/7/9
		if (!notice.equals("") || notice != null) {
			try {
				JSONObject obj = new JSONObject(notice);
				error_code = obj.getString("error_code");
				if (error_code.equals("0000")) {
					batchcode = obj.getString("batchCode");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return batchcode;
	}
}
