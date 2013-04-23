package com.palmdream.RuyicaiAndroid;

import java.net.URLEncoder;
import java.sql.Date;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.netintface.iHttp;
import com.palmdream.netintface.jrtLot;

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
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ����������
 */
public class JoinIn extends Activity {
	ImageView back;
	TextView titleTop;
	TextView textTime;
	TextView textNum;
	TextView textStart;

	TextView textDepict;
	TextView textProgress;
	TextView textJoin;

	TextView textContent;
	TextView textInfo;
	TextView textGet;
	TextView textLeast;
	TextView textResidual;
	TextView textMoney;
	EditText editNum;
	Button buttonBuy;
	Button buttonDepict;
	Button buttonJoin;
	Button buttonContent;
	ProgressDialog progressdialog;
	String id;
	private boolean start;
	private int iretrytimes = 2;
	private JSONObject obj;
	String re;
	String phonenum = "";// �ֻ���
	String sessionid;// ��¼id
	String userName;// ������
	String allNum;// �ܷ���
	String type = "F47104";// ����
	String allAmt = "";// �ܽ��
	int one;
	boolean join = false;
	// showDialog(0);//��ʾ������
	/* ���� */
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				progressdialog.dismiss();
				Toast.makeText(JoinIn.this, "��������Ϲ��ɹ���", Toast.LENGTH_SHORT)
						.show();
				JoinHall.once = true;
				finish();
				break;
			case 1:// ��������Ա
				progressdialog.dismiss();
				Toast.makeText(JoinIn.this, "��������Ա��", Toast.LENGTH_SHORT)
						.show();
				break;
			case 2:// ������������ʣ�����
				progressdialog.dismiss();
				Toast.makeText(JoinIn.this, " ������������ʣ�������", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:// ��һ��ע�����Ͳʽ�����������
				progressdialog.dismiss();
				Toast
						.makeText(JoinIn.this, "δ��ֵ�û�������������",
								Toast.LENGTH_SHORT).show();
				break;
			case 4:// 400005�����Ϲ�ʧ��
				progressdialog.dismiss();
				Toast.makeText(JoinIn.this, "��������Ϲ�ʧ�ܣ�", Toast.LENGTH_SHORT)
						.show();
				break;
			case 5:// ���ݼ��سɹ�
				progressdialog.dismiss();
				update();
				break;
			case 6:// ���ݼ���ʧ��
				progressdialog.dismiss();
				Toast.makeText(JoinIn.this, "���ݼ���ʧ�ܣ�", Toast.LENGTH_SHORT)
						.show();
				break;
			case 7:// �����쳣
				progressdialog.dismiss();
				Toast.makeText(JoinIn.this, "�����쳣��", Toast.LENGTH_SHORT).show();
				break;
	
			case 8:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ������", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾע��ɹ�
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ѽ���", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ�û���ע��
				break;
			case 10:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "ϵͳ���㣬���Ժ�", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾϵͳ���㣬���Ժ�
				break;
			case 11:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�޿����߼���", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ�úű���ͣ����ϵ�ͷ�
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
		setContentView(R.layout.join_in);
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				JoinIn.this, "addInfo");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		if (sessionid.equals("")) {
			phonenum = "";
		} else {
			phonenum = shellRW.getUserLoginInfo("phonenum");
		}
		// ��ȡ����
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("id");// ��������
		userName = bundle.getString("user");// ��������
		allNum = bundle.getString("allNum");// ��������
		type = bundle.getString("type");// ��������
		init(); // ��ʼ�����
		connectionCheck();// ��������
		onClik();
	}

	public void init() {
		back = (ImageView) findViewById(R.id.join_buy_change_image_back);
		titleTop = (TextView) findViewById(R.id.join_buy_change_text_title);
		textTime = (TextView) findViewById(R.id.join_in_text_time);
		textNum = (TextView) findViewById(R.id.join_in_text_num);
		textStart = (TextView) findViewById(R.id.join_in_text_start);
		textDepict = (TextView) findViewById(R.id.join_in_text_depict);
		textProgress = (TextView) findViewById(R.id.join_in_text_progress);
		textJoin = (TextView) findViewById(R.id.join_in_text_join);
		textContent = (TextView) findViewById(R.id.join_in_text_content);
		textInfo = (TextView) findViewById(R.id.join_in_text_info);
		textGet = (TextView) findViewById(R.id.join_in_text_get);
		textLeast = (TextView) findViewById(R.id.join_in_text_least);
		textResidual = (TextView) findViewById(R.id.join_in_text_residual);
		textMoney = (TextView) findViewById(R.id.join_in_text_money);
		buttonBuy = (Button) findViewById(R.id.join_in_button_buy);
		editNum = (EditText) findViewById(R.id.join_in_edit_num);
		buttonDepict = (Button) findViewById(R.id.join_in_button_depict);
		buttonJoin = (Button) findViewById(R.id.join_in_button_join);
		buttonContent = (Button) findViewById(R.id.join_in_button_content);

	}

	public void connectionCheck() {

		showDialog(0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				while (iretrytimes < 3 && iretrytimes > 0) {

					re = jrtLot.joinInfo(phonenum, id, sessionid);

					try {
						obj = new JSONObject(re);
						Log.e("====", obj.toString());
						iretrytimes = 3;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						try {
							obj = new JSONObject(re);
							error_code = obj.getString("error_code");
							iretrytimes = 3;
						} catch (JSONException e1) {
							iretrytimes--;
						}
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
							re = jrtLot.joinInfo(phonenum, id, sessionid);
							Log.e("re===", re);
							try {
								obj = new JSONObject(re);
								iretrytimes = 3;
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								try {
									obj = new JSONObject(re);
									error_code = obj.getString("error_code");
									iretrytimes = 3;
								} catch (JSONException e1) {
									iretrytimes--;
								}

							}
						}
					}
				}
				iretrytimes = 2;
				if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 5;
					handler.sendMessage(msg);

				} else {
					Message msg = new Message();
					msg.what = 6;
					handler.sendMessage(msg);
				}

			}

		}).start();
	}

	public void connectionBuy() {
		showDialog(0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				int num = Integer.parseInt(editNum.getText().toString());
				while (iretrytimes < 3 && iretrytimes > 0) {

					re = jrtLot.buyJoin(phonenum, id, editNum.getText()
							.toString(), Integer.toString(one * 100 * num),
							sessionid);

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
							re = jrtLot.buyJoin(phonenum, id, editNum.getText()
									.toString(), Integer.toString(one * 100
									* num), sessionid);
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

				} else if (error_code.equals("400002")) {// ��������Ա
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				} else if (error_code.equals("400009")) {// ������������ʣ�����
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				} else if (error_code.equals("400010")) {// ��һ��ע�����Ͳʽ�����������
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				} else if (error_code.equals("400005")) {// 400005�����Ϲ�ʧ��

					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);
				} else if (error_code.equals("040006")) {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);
				} else if (error_code.equals("1007")) {
					Message msg = new Message();
					msg.what = 9;
					handler.sendMessage(msg);
				} else if (error_code.equals("040007")) {
					Message msg = new Message();
					msg.what = 11;
					handler.sendMessage(msg);
				} else if (error_code.equals("4444")) {
					Message msg = new Message();
					msg.what = 10;
					handler.sendMessage(msg);
				} else if (error_code.equals("00")) {// �����쳣

					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);
				}

			}

		}).start();
	}

	public void update() {
		try {
			// ���ֱ�ʶ���ֲʣ�QLC˫ɫ��B001ʱʱ��3D
			if (type.equals("F47102")) {
				titleTop.setText("���ֲ�>>�������");
			} else if (type.equals("F47104")) {
				titleTop.setText("˫ɫ��>>�������");
			} else if (type.equals("F47103")) {
				titleTop.setText("����3D>>�������");
			}
			textTime.setText("��ֹʱ�䣺" + (String) obj.get("caseEndTime"));
			textNum.setText((String) obj.get("id"));
			textStart.setText(userName);// ������
			try {
				textDepict.setText((String) obj.get("describe"));
			} catch (Exception e) {
			}
			try {
				double caseBuyAfterAmt = Double.parseDouble(obj
						.getString("caseBuyAfterAmt"));
				if (caseBuyAfterAmt * 100 < 100
						&& caseBuyAfterAmt * 100 + 1 < 100) {// 0.001
					caseBuyAfterAmt = caseBuyAfterAmt * 1000 % 10 > 0 ? caseBuyAfterAmt * 100 + 1
							: caseBuyAfterAmt * 100;
				} else {
					caseBuyAfterAmt = caseBuyAfterAmt * 100;
				}
				textProgress.setText(Integer.toString((int) caseBuyAfterAmt)
						+ "%");
			} catch (Exception e) {
				Integer amt = (Integer) obj.get("caseBuyAfterAmt") * 100;
				textProgress.setText(Integer.toString(amt) + "%");
			}
			Integer userNum = (Integer) obj.get("buyedUserNum");
			textJoin.setText(userNum + "��");// �Ѿ�����
			String openFlag = Integer.toString((Integer) obj.get("openFlag"));
			String flag = Integer.toString((Integer) obj.get("flag"));
			Log.e("openflag", openFlag);
			Log.e("flag===", flag);
			String content = (String) obj.get("caseContent");
			if (openFlag.equals("2")) {
				textContent.setText("���Բ����û�������");
			} else if (openFlag.equals("3") || openFlag.equals("0")) {
				textContent.setText(getByContent(content, type));
			} else if (openFlag.equals("1") && flag.equals("7")) {
				textContent.setText(getByContent(content, type));
			} else if(openFlag.equals("1")){
				textContent.setText("���������󹫿���");
			}else if (openFlag.equals("4")) {
				textContent.setText(getByContent(content, type));
			}
			Integer all = (Integer) obj.get("caseAllAmt") / 100;
			one = (Integer) obj.get("caseOneAmt") / 100;
			Integer num = (Integer) obj.get("caseNum");
			String info = Integer.toString(num) + "��,�ܽ��"
					+ Integer.toString(all) + "Ԫ,��" + allNum + "��,ÿ�ݣ�"
					+ Integer.toString(one) + "Ԫ��";
			textInfo.setText(info);
			String get = Double.toString(Double.valueOf((String) obj
					.get("pushMoney")) * 100);

			textGet.setText(get + "%");
			Integer bao = (Integer) obj.get("caseBaoDiAmt");
			textLeast.setText(Integer.toString(bao) + "��");
			Integer buy = (Integer) obj.get("caseBuyAmtByUser");
			Integer lest = (Integer) obj.get("BcaseNnum");
			String str = Integer.toString(lest);
			textResidual.setText(str + "��");// ʣ�����
			if (lest == 0) {
				editNum.setEnabled(false);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onClik() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		buttonBuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
						JoinIn.this, "addInfo");
				phonenum = shellRW.getUserLoginInfo("phonenum");
				sessionid = shellRW.getUserLoginInfo("sessionid");
				String num = editNum.getText().toString();
				if (sessionid.equals("")) {
					Intent intentSession = new Intent(JoinIn.this,
							UserLogin.class);
					// startActivity(intentSession);
					startActivityForResult(intentSession, 0);
				} else if (num.equals("")) {
					Toast.makeText(JoinIn.this, "�Ϲ���������Ϊ��", Toast.LENGTH_SHORT)
							.show();
				} else {
					iHttp.whetherChange = false;
					// connectionBuy();
					alert("�������Ϲ�����Ϊ" + num + "��,�ܽ��Ϊ" + allAmt + "Ԫ");
				}

			}
		});
		buttonDepict.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		buttonJoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		buttonContent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		TextWatcher watcher = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0) {
					editNum.setClickable(true);
					editNum.setEnabled(true);
					allAmt = "";
					int num = Integer.parseInt(editNum.getText().toString());
					allAmt = Integer.toString(num * one);
					textMoney.setText("��," + allAmt + "Ԫ");// �ܽ��

				} else {
					textMoney.setText("��," + "0Ԫ");// �ܽ��
				}

			}

		};
		editNum.addTextChangedListener(watcher);
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

	/**
	 * ����ʽͶע���ú���
	 * 
	 * @param string
	 *            ��ʾ����Ϣ
	 * @return
	 */
	private void alert(String string) {

		start = true;
		Builder dialog = new AlertDialog.Builder(this).setTitle("ȷ����Ϣ")
				.setMessage(string).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (start) {
									start = false;
									connectionBuy();
								}
							}
						}).setNegativeButton("����",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}

						});

		dialog.show();

	}

	// �������� ��������
	@SuppressWarnings( { "rawtypes", "unused" })
	public static String getByContent(String content, String lotno) {
		String result = "";
		if (content.equals("rychm")) {
			return content;
		}
		try {
			if (lotno.equals("F47104") || lotno.equals("B001")) {
				String cont = content;
				if (content.endsWith("|")) {
					String[] conts = cont.split("\\|");
					// if (conts.length > 1) {
					// for (int i = 0; i < conts.length; i++) {
					// conts[i] = conts[i].substring(4);
					// if (conts[i].endsWith("^")) {
					// conts[i] = conts[i].substring(0, conts[i]
					// .length() - 1);
					// }
					// String[] codes = conts[i].split("-");
					// String[] zhumas = codes[0].split("~");
					// String redNumbers = getSortString(zhumas[0]);
					// String blueNumbers = getSortString(zhumas[1]);
					// Vector redArray = getStringArrayFromString(redNumbers);
					// Vector blueArray = getStringArrayFromString(blueNumbers);
					// String newzhuma = "ע�룺"
					// + joinStringArrayWithComma(redArray) + "+"
					// + joinStringArrayWithComma(blueArray);
					// result += newzhuma + "  " + codes[1] + "ע   "
					// + Integer.parseInt(codes[2]) / 100 + "Ԫ\n";
					// }
					// } else {
					for (int i = 0; i < conts.length; i++) {
						if (conts[i].substring(0, 2).equals("00")) {
							Log.e("conts[i]", conts[i]);
							cont = conts[i].substring(0, conts[i].length());
							cont = cont.substring(4);
							String[] codess = cont.split("-");// ע�� ע�� ���
							String codes = codess[0];// ע��
							if (codes.endsWith("^")) {
								codes = codes.substring(0, codes.length() - 1);
							}
							String[] zhuma = codes.split("~");
							String redma = getSortString(zhuma[0]);
							String bluema = getSortString(zhuma[1]);
							Vector redarray = getStringArrayFromString(redma);
							Vector bluearray = getStringArrayFromString(bluema);
							String newzhuma = "����"
									+ joinStringArrayWithComma(redarray)
									+ "����"
									+ joinStringArrayWithComma(bluearray);
							result += newzhuma + "  " + codess[1] + "ע    "
									+ Integer.parseInt(codess[2]) / 100 + "Ԫ"
									+ "\n";

						}
						if (conts[i].substring(0, 2).equals("50")
								|| conts[i].substring(0, 2).equals("40")) {
							cont = conts[i].substring(0, conts[i].length());
							cont = cont.substring(4);
							String[] codess = cont.split("-");// ע�� ע�� ���
							String codes = codess[0];// ע��
							if (codes.endsWith("^")) {
								codes = codes.substring(0, codes.length() - 1);
							}
							String[] zhuma = codes.split("~");
							String[] dantuo = zhuma[0].split("\\*");
							String danma = getSortString(dantuo[0]);
							String tuo = getSortString(dantuo[1]);
							String blue = getSortString(zhuma[1]);
							Vector danarray = getStringArrayFromString(danma);
							Vector tuoarray = getStringArrayFromString(tuo);
							Vector bluearray = getStringArrayFromString(blue);
							String newzhuma = "���룺"
									+ joinStringArrayWithComma(danarray)
									+ "���룺"
									+ joinStringArrayWithComma(tuoarray)
									+ "����"
									+ joinStringArrayWithComma(bluearray);
							result += newzhuma + "  " + codess[1] + "ע    "
									+ Integer.parseInt(codess[2]) / 100 + "Ԫ"
									+ "\n";

						} else if (conts[i].substring(0, 2).equals("20")
								|| conts[i].substring(0, 2).equals("10")) {
							cont = conts[i].substring(0, conts[i].length());
							String[] codess = cont.split("-");
							String codes = codess[0];
							if (codess.length > 0
									&& codess[0].indexOf("*") >= 0) {
								codess[0] = codess[0].substring(codess[0]
										.indexOf("*") + 1);
							}
							if (codess[0].endsWith("^")) {
								codess[0] = codess[0].substring(0, codess[0]
										.length() - 1);
							}
							String[] zhuma = codess[0].split("~");
							String redNumbers = getSortString(zhuma[0]);
							String blueNumbers = getSortString(zhuma[1]);
							Vector redArray = getStringArrayFromString(redNumbers);
							Vector blueArray = getStringArrayFromString(blueNumbers);
							String newzhuma = "ע�룺"
									+ joinStringArrayWithComma(redArray) + "+"
									+ joinStringArrayWithComma(blueArray);
							result += newzhuma + "  " + codess[1] + "ע    "
									+ Integer.parseInt(codess[2]) / 100 + "Ԫ"
									+ "\n";

						} else if (conts[i].substring(0, 2).equals("30")) {
							cont = conts[i].substring(0, conts[i].length());
							String[] codess = cont.split("-");
							String codes = codess[0];
							if (codess.length > 0
									&& codess[0].indexOf("*") >= 0) {
								codess[0] = codess[0].substring(codess[0]
										.indexOf("*") + 1);
							}
							if (codess[0].endsWith("^")) {
								codess[0] = codess[0].substring(0, codess[0]
										.length() - 1);
							}
							String[] zhuma = codess[0].split("~");
							String redNumbers = getSortString(zhuma[0]);
							String blueNumbers = getSortString(zhuma[1]);
							Vector redArray = getStringArrayFromString(redNumbers);
							Vector blueArray = getStringArrayFromString(blueNumbers);
							String newzhuma = "ע�룺"
									+ joinStringArrayWithComma(redArray) + "+"
									+ joinStringArrayWithComma(blueArray);
							result += newzhuma + "  " + codess[1] + "ע    "
									+ Integer.parseInt(codess[2]) / 100 + "Ԫ"
									+ "\n";
						}
					}
				}

			} else if (lotno.equals("F47103") || lotno.equals("D3")) {// 0103^0102^0101
				String cont = content;
				String[] conts = cont.split("\\|");
				for (int i = 0; i < conts.length; i++) {
					if (conts[i].substring(0, 2).equals("20")) {// 20010103^0102^0101^-1-200|
						// ��ʽ
						String zhuma = conts[i].substring(4);
						zhuma = zhuma.substring(0, zhuma.length());
						String[] zhumas = zhuma.split("-");
						if (zhumas[0].endsWith("^"))
							zhumas[0] = zhumas[0].substring(0, zhumas[0]
									.length() - 1);
						String[] newZhuma = zhumas[0].split("\\^");
						String bai = newZhuma[0].substring(2);
						String shi = newZhuma[1].substring(2);
						String ge = newZhuma[2].substring(2);
						Vector baiA = getStringArrayFromString(bai);
						Vector shiA = getStringArrayFromString(shi);
						Vector geA = getStringArrayFromString(ge);
						result += "3Dֱѡ��λ" + joinStringArrayWithComma(baiA)
								+ "  ʮλ " + joinStringArrayWithComma(shiA)
								+ "  ��λ " + joinStringArrayWithComma(geA)
								+ "  " + zhumas[1] + "ע    "
								+ Integer.parseInt(zhumas[2]) / 100 + "Ԫ"
								+ "\n";
					}
					if (conts[i].substring(0, 2).equals("31")) {// 3101050203040506^-20-4000|
						// ����
						String zhuma = conts[i].substring(6);
						zhuma = zhuma.substring(0, zhuma.length());
						String[] zhumas = zhuma.split("-");
						if (zhumas[0].endsWith("^"))
							zhumas[0] = zhumas[0].substring(0, zhumas[0]
									.length() - 1);
						Vector redArray = getStringArrayFromString(zhumas[0]);
						result += "����" + joinStringArrayWithComma(redArray)
								+ "  " + zhumas[1] + "ע    "
								+ Integer.parseInt(zhumas[2]) / 100 + "Ԫ"
								+ "\n";

					}
					if (conts[i].substring(0, 2).equals("32")) {// 32010402030405^-4-800|
						// ����
						String zhuma = conts[i].substring(6);
						zhuma = zhuma.substring(0, zhuma.length());
						String[] zhumas = zhuma.split("-");
						if (zhumas[0].endsWith("^"))
							zhumas[0] = zhumas[0].substring(0, zhumas[0]
									.length() - 1);
						Vector redArray = getStringArrayFromString(zhumas[0]);
						Log.e("zhumas[2]===", zhumas[2]);
						result += "����" + joinStringArrayWithComma(redArray)
								+ "  " + zhumas[1] + "ע    "
								+ Integer.parseInt(zhumas[2]) / 100 + "Ԫ"
								+ "\n";
					}
					if (conts[i].substring(0, 2).equals("34")) {// 34010401020305^-24-4800|
						// ֱѡ����
						String zhuma = conts[i].substring(6);
						zhuma = zhuma.substring(0, zhuma.length());
						String[] zhumas = zhuma.split("-");
						if (zhumas[0].endsWith("^"))
							zhumas[0] = zhumas[0].substring(0, zhumas[0]
									.length() - 1);
						Vector redArray = getStringArrayFromString(zhumas[0]);
						result += "ֱѡ����" + joinStringArrayWithComma(redArray)
								+ "  " + zhumas[1] + "ע    "
								+ Integer.parseInt(zhumas[2]) / 100 + "Ԫ"
								+ "\n";

					}
					if (conts[i].substring(0, 2).equals("02")) {// 0201010203^-1-200|
						// ����
						String zhuma = conts[i].substring(4);
						zhuma = zhuma.substring(0, zhuma.length());
						String[] zhumas = zhuma.split("-");
						if (zhumas[0].endsWith("^"))
							zhumas[0] = zhumas[0].substring(0, zhumas[0]
									.length() - 1);
						Vector redArray = getStringArrayFromString(zhumas[0]);
						result += "����" + joinStringArrayWithComma(redArray)
								+ "  " + zhumas[1] + "ע    "
								+ Integer.parseInt(zhumas[2]) / 100 + "Ԫ"
								+ "\n";
					}
				}
			} else if (lotno.equals("F47102") || lotno.equals("QL730")) {
				String cont = content;
				String[] conts = cont.split("\\|");
				for (int i = 0; i < conts.length; i++) {
					if (content.substring(0, 2).equals("00")) {// 000101020304050607~-a-b|
						// String[] conts = content.split("\\|");
						// System.out.println(conts.length);
						// if (conts.length >= 1) {
						// for (int i = 0; i < conts.length; i++) {
						conts[i] = conts[i].substring(4);
						if (conts[i].endsWith("\\|")) {
							conts[i] = conts[i].substring(0, conts[i].length());
						}
						String[] codes = conts[i].split("-");
						String zhumas = codes[0];
						if (zhumas.endsWith("^")) {
							zhumas = zhumas.substring(0, zhumas.length() - 1);
						}
						String numbers = getSortString(zhumas);
						Vector numArray = getStringArrayFromString(numbers);
						String newzhuma = "ע�룺"
								+ joinStringArrayWithComma(numArray);
						result += newzhuma + "  " + codes[1] + "ע   "
								+ Integer.parseInt(codes[2]) / 100 + "Ԫ\n";
						// }
						// }
					} else if (conts[i].substring(0, 2).equals("10")) { // 1001*01020304050607080910^-120-24000|

						if (conts[i].endsWith("|")) {
							conts[i] = conts[i].substring(0, conts[i].length());
						}
						conts[i] = conts[i].substring(5);
						String[] codes = conts[i].split("-");
						String zhumas = codes[0];
						if (zhumas.endsWith("^")) {
							zhumas = zhumas.substring(0, zhumas.length() - 1);
						}
						String numbers = getSortString(zhumas);
						Vector numArray = getStringArrayFromString(numbers);
						result += "ע��:" + joinStringArrayWithComma(numArray)
								+ "  " + codes[1] + "ע   "
								+ Integer.parseInt(codes[2]) / 100 + "Ԫ" + "\n";
					} else if (conts[i].substring(0, 2).equals("20")) {// //010203*04050607080910
						// -a-b
						// String cont = content;
						if (conts[i].endsWith("|")) {
							conts[i] = conts[i].substring(0, conts[i].length());
						}
						conts[i] = conts[i].substring(4);
						String[] codes = conts[i].split("-");
						String zhumas = codes[0];
						if (zhumas.endsWith("^")) {
							zhumas = zhumas.substring(0, zhumas.length() - 1);
						}
						String[] dantuos = zhumas.split("\\*");
						String danma = getSortString(dantuos[0]);
						String tuoma = getSortString(dantuos[1]);
						Vector danmaArray = getStringArrayFromString(danma);
						Vector tuomaArray = getStringArrayFromString(tuoma);
						result += "����:" + joinStringArrayWithComma(danmaArray)
								+ "  ����:"
								+ joinStringArrayWithComma(tuomaArray) + "  "
								+ codes[1] + "ע   "
								+ Integer.parseInt(codes[2]) / 100 + "Ԫ" + "\n";
					}
				}
			}
		} catch (Exception ex) {

			ex.printStackTrace();
			return "";
		}
		return result;

	}

	public static String getSortString(String code) {
		StringBuffer sb = new StringBuffer();
		Vector vector = getStringArrayFromString(code);
		Collections.sort(vector);
		for (int i = 0; i < vector.size(); i++) {
			String str = (String) vector.get(i);
			if (str.length() < 2) {
				str = "0" + str;
			}
			sb.append(str);
		}
		return sb.toString();
	}

	public static Vector getStringArrayFromString(String strArray) {
		try {
			Vector v = new Vector();

			int l = strArray.length();
			int h = l / 2;

			int n = 0;
			for (int i = 0; i < h; i++) {
				String ss = strArray.substring(n, n + 2);
				n = n + 2;
				v.add(ss);
			}
			return v;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String joinStringArrayWithComma(Vector v) {
		String resultStr = "";
		if (v == null || v.size() == 0) {
			return "";
		} else {
			for (int i = 0; i < v.size(); i++) {
				resultStr += v.get(i) + ",";
			}

			if (resultStr.charAt(resultStr.length() - 1) == ',') {
				resultStr = resultStr.substring(0, resultStr.length() - 1);
			}
			return resultStr;
		}

	}

	public static boolean getDeadlineHM(String type, int state) {
		// Tlotctrl tlotctrl = tlotctrlService.getTlotCtrlByType(type, state);
		// ȡ�õ�ǰ�ں�
		String term = jrtLot.getTerm(type);
		term = term.substring(4);
		// Date endtime = tlotctrl.getENDTIME();
		Date endtime = null;
		long endtime1 = endtime.getTime() - 90 * 60 * 1000;
		long now = System.currentTimeMillis();
		if (endtime1 - now > 0) {
			return false;
		} else {
			return true;
		}
	}

}
