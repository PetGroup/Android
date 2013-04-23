package com.palmdream.RuyicaiAndroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class QueryInfo extends Activity {
	private int TYPE;// ��ѯ����
	public static final int JOIN = 1;// �������
	public static final int BUY = 2;// �������
	public static final int DETAILS = 3;// �����������
	public static final int BUY_DETAILS = 4;// �����������
	private ProgressDialog progressdialog;
	private JSONObject obj;
	private String re;
	private int iretrytimes = 2;
	private String mobile_code;// �ֻ���
	private String sessionid;
	private String batch = "";// �ں�
	private List<Map<String, Object>> list;/* �б�������������Դ */
	public final static String TITLE1 = "TITLE1"; /* ���� */
	public final static String TITLE2 = "TITLE2"; /* ���� */
	public final static String TITLE3 = "TITLE3"; /* ���� */
	public final static String TITLE4 = "TITLE4"; /* ���� */
	private boolean start = false;
	private static ArrayList<String[]> infos = new ArrayList<String[]>();// ��Ҫ��������
	private static ArrayList<String[]> detInfos = new ArrayList<String[]>();// �μӺ������ϸ��Ϣ
	private int index = 0;
	/* ���� */
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				progressdialog.dismiss();
				Log.e("infos", "" + infos.size());
				initList();
				break;
			case 1:
				progressdialog.dismiss();
				Toast.makeText(QueryInfo.this, "�����쳣��", Toast.LENGTH_SHORT)
						.show();
				break;
			case 2:
				progressdialog.dismiss();
				Toast.makeText(QueryInfo.this, "��ѯ���Ϊ�գ�", Toast.LENGTH_SHORT)
						.show();
				break;
			case 3:
				progressdialog.dismiss();
				String str = "";

				str = turnString(detInfos.get(0), true);
				if (TYPE == BUY) {
					if (detInfos.get(0)[3].equals("������")) {
						alert(str, detInfos.get(0)[15], true);
					} else {
						alert(str, "", false);
					}
				} else {
					alert(str, "", false);
				}

				break;
			case 4:
				progressdialog.dismiss();
				Toast.makeText(QueryInfo.this, "������>=50%������������Χ��",
						Toast.LENGTH_SHORT).show();
				break;
			case 5:
				progressdialog.dismiss();
				Toast.makeText(QueryInfo.this, "�������ɹ���", Toast.LENGTH_SHORT)
						.show();
				break;
			case 6:
				progressdialog.dismiss();
				Toast.makeText(QueryInfo.this, "����Ϊ����״̬��", Toast.LENGTH_SHORT)
						.show();
				break;
			case 7:
				progressdialog.dismiss();
				Toast.makeText(QueryInfo.this, "���ⶳʧ�ܣ�", Toast.LENGTH_SHORT)
						.show();
				break;
			case 8:
				progressdialog.dismiss();
				String str1 = "";
				for (int i = 0; i < detInfos.size(); i++) {
					str1 += turnString(detInfos.get(i), false);
				}
				alert(str1, "", true);
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				QueryInfo.this, "addInfo");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		mobile_code = shellRW.getUserLoginInfo("phonenum");
		setContentView(R.layout.query_info_list_layout);
		TextView title = (TextView) findViewById(R.id.ruyipackage_title);
		ImageButton back = (ImageButton) findViewById(R.id.ruyizhushou_btn_return);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// checkJoin();
				finish();
			}
		});
		initList();
		// ��ȡ����
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			TYPE = bundle.getInt("type");// ��������
		}
		if (TYPE == JOIN) {
			title.setText("����>>�����¼");
			checkJoin();
		} else {
			title.setText("����>>�����¼");
			checkBuy();
		}
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		infos = new ArrayList<String[]>();
		detInfos = new ArrayList<String[]>();
	}

	/**
	 * ��ʼ���б�
	 */
	private void initList() {
		// ��ʼ��list
		// ����Դ
		list = getListForJoinAdapter();

		ListView listview = (ListView) findViewById(R.id.query_info_list);
		// listview.setDividerHeight(0);

		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.query_info_list_item, new String[] { TITLE1, TITLE2,
						TITLE3, TITLE4 }, new int[] { R.id.query_info_text1,
						R.id.query_info_text2, R.id.query_info_text3,
						R.id.query_info_text4 });

		listview.setAdapter(adapter);
		PublicMethod.setmydividerHeight(listview);

		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				index = position;
				Log.e("TYPE====", "" + TYPE);
				if (TYPE == JOIN | TYPE == DETAILS) {
					joinDetails(infos.get(position)[4]);
				} else {
					buyDetails(infos.get(position)[4]);
				}
			}

		};
		listview.setOnItemClickListener(clickListener);
	}

	public List<Map<String, Object>> getListForJoinAdapter() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
		if (infos.size() != 0) {
			for (int i = 0; i < infos.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(TITLE1, infos.get(i)[0]);
				map.put(TITLE2, infos.get(i)[1]);
				map.put(TITLE3, infos.get(i)[2]);
				map.put(TITLE4, infos.get(i)[3]);
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 
	 * ����������Ϣ�Ի���
	 * 
	 * @param string
	 *            ��ʾ����Ϣ
	 * @return
	 */
	private void alert(String string, final String case_caseId, boolean isOk) {

		start = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.query_info_dialog, null);
		TextView text = (TextView) view
				.findViewById(R.id.query_info_dialog_text);
		text.setText(string);
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("��������");
		dialog.setView(view);
		if (isOk) {
			dialog.setPositiveButton("��Ҫ����",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (start) {
								start = false;
								remove(case_caseId);
							}
						}
					});
		}
		if ((TYPE == JOIN | TYPE == DETAILS) && isOk) {
			dialog.setPositiveButton("����",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (start) {
								start = false;
								buyDetails(infos.get(index)[4]);
							}
						}
					});
		}
		dialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});

		dialog.show();

	}

	/**
	 * ���ַ�������ת����Ҫ���ַ���
	 * 
	 * @param str
	 * @return
	 */
	public String turnString(String str[], boolean begin) {
		if (begin) {
			return "����������" + str[4] + "\n" + "���������ˣ�" + str[5] + "\n"
					+ "��������ɣ�" + str[6] + "\n" + "�������ȣ�" + str[2] + "\n"
					+ "�Ѳ��룺" + str[7] + "\n" + "�������ݣ�" + "\n" + str[8] + "\n"
					+ "�����ںţ�" + str[0] + "  " + str[9] + "\n" + "������"
					+ str[10] + str[1] + "\n" + "���׷�����" + str[11] + "\n"
					+ "ÿ�ݽ�" + str[12] + "\n" + "���Ϲ�������" + str[13] + "\n"
					+ "ʣ�������" + str[14] + "\n";
		} else {
			return "������ţ�" + str[0] + "\n" + "�û���ţ�" + str[1] + "\n" + "�Ϲ�������"
					+ str[2] + "\n" + "�Ϲ���" + str[3] + "\n" + "�Ϲ�ʱ�䣺"
					+ str[4] + "\n" + "\n";
		}

	}

	/**
	 * ��������ѯ����
	 */
	private void checkJoin() {

		showDialog(0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				while (iretrytimes < 3 && iretrytimes > 0) {

					re = jrtLot.checkJoin(mobile_code, sessionid);

					try {
						obj = new JSONObject(re);
						error_code = obj.getString("error_code");
						iretrytimes = 3;
					} catch (JSONException e) {
						try {
							error_code = "0000";
							JSONArray objArray = new JSONArray(re);
							infos = parseInfo(objArray);
							iretrytimes = 3;

						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
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
							re = jrtLot.checkJoin(mobile_code, sessionid);
							try {
								obj = new JSONObject(re);
								error_code = obj.getString("error_code");
								iretrytimes = 3;
							} catch (JSONException e) {
								try {
									error_code = "0000";
									JSONArray objArray = new JSONArray(re);
									infos = parseInfo(objArray);
									iretrytimes = 3;

								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									iretrytimes--;
								}
							}
						}
					}

				}
				iretrytimes = 2;
				Log.e("error_code==", error_code);
				if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);

				} else if (error_code.equals("0000")) {
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);
				} else if (error_code.equals("400001")) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				}

			}

		}).start();
	}

	/**
	 * ��������ѯ����
	 */
	private void checkBuy() {
		showDialog(0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				while (iretrytimes < 3 && iretrytimes > 0) {

					re = jrtLot.checkBuy(mobile_code, batch, "", sessionid);

					try {
						obj = new JSONObject(re);
						error_code = obj.getString("error_code");
						iretrytimes = 3;
					} catch (JSONException e) {
						try {
							error_code = "0000";
							JSONArray objArray = new JSONArray(re);
							infos = parseInfo(objArray);
							iretrytimes = 3;

						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
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
							re = jrtLot.checkBuy(mobile_code, batch, "",
									sessionid);
							try {
								obj = new JSONObject(re);
								error_code = obj.getString("error_code");
								iretrytimes = 3;
							} catch (JSONException e) {
								try {
									error_code = "0000";
									JSONArray objArray = new JSONArray(re);
									infos = parseInfo(objArray);
									iretrytimes = 3;

								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									iretrytimes--;
								}
							}
						}
					}

				}
				iretrytimes = 2;
				if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);

				} else if (error_code.equals("0000")) {
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);
				} else if (error_code.equals("400001")) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				}

			}

		}).start();
	}

	/**
	 * ���������ϸ��Ϣ����
	 */
	private void joinDetails(final String case_caseId) {
		showDialog(0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				while (iretrytimes < 3 && iretrytimes > 0) {

					re = jrtLot
							.joinDetails(mobile_code, case_caseId, sessionid);

					try {
						obj = new JSONObject(re);
						error_code = obj.getString("error_code");
						iretrytimes = 3;
					} catch (JSONException e) {
						try {
							error_code = "0000";
							JSONArray objArray = new JSONArray(re);
							TYPE = DETAILS;
							detInfos = parseInfo(objArray);
							iretrytimes = 3;

						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
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
							re = jrtLot.joinDetails(mobile_code, case_caseId,
									sessionid);

							try {
								obj = new JSONObject(re);
								error_code = obj.getString("error_code");
								iretrytimes = 3;
							} catch (JSONException e) {
								try {
									error_code = "0000";
									JSONArray objArray = new JSONArray(re);
									TYPE = DETAILS;
									detInfos = parseInfo(objArray);
									iretrytimes = 3;

								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									iretrytimes--;
								}
							}
						}
					}

				}
				iretrytimes = 2;
				if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);

				} else if (error_code.equals("400001")) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				} else if (error_code.equals("0000")) {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);
				}

			}

		}).start();
	}

	/**
	 * ���������ϸ��Ϣ����
	 */
	private void buyDetails(final String case_caseId) {
		showDialog(0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				while (iretrytimes < 3 && iretrytimes > 0) {

					re = jrtLot.joinInfo(mobile_code, case_caseId, sessionid);

					try {
						obj = new JSONObject(re);
						error_code = obj.getString("error_code");
						iretrytimes = 3;
					} catch (JSONException e) {
						try {
							error_code = "0000";
							obj = new JSONObject(re);
							Log.e("====", obj.toString());

							detInfos = parseInfo(obj);
							iretrytimes = 3;

						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
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
							re = jrtLot.joinInfo(mobile_code, case_caseId,
									sessionid);

							try {
								obj = new JSONObject(re);
								error_code = obj.getString("error_code");
								iretrytimes = 3;
							} catch (JSONException e) {
								try {
									error_code = "0000";
									obj = new JSONObject(re);
									Log.e("====", obj.toString());

									detInfos = parseInfo(obj);
									iretrytimes = 3;

								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
									iretrytimes--;
								}
							}
						}
					}

				}
				iretrytimes = 2;
				if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);

				} else if (error_code.equals("400001")) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				} else if (error_code.equals("0000")) {
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				}

			}

		}).start();
	}

	/**
	 * ������������
	 */
	private void remove(final String case_caseId) {
		showDialog(0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				while (iretrytimes < 3 && iretrytimes > 0) {

					re = jrtLot.remove(mobile_code, case_caseId, sessionid);

					try {
						obj = new JSONObject(re);
						error_code = obj.getString("error_code");
						iretrytimes = 3;
					} catch (JSONException e) {

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
							re = jrtLot.remove(mobile_code, case_caseId,
									sessionid);

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
				if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);

				} else if (error_code.equals("400001")) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				} else if (error_code.equals("000")) {
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);
				} else if (error_code.equals("400006")) {
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);
				} else if (error_code.equals("000000")) {
					Message msg = new Message();
					msg.what = 5;
					handler.sendMessage(msg);
				} else if (error_code.equals("400004")) {
					Message msg = new Message();
					msg.what = 6;
					handler.sendMessage(msg);
				} else if (error_code.equals("400007")) {
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);
				}

			}

		}).start();
	}

	/**
	 * ��������
	 * 
	 * @param json
	 * @return
	 */
	private ArrayList<String[]> parseInfo(JSONArray json) {
		ArrayList list = new ArrayList<String[]>();
		if (TYPE == JOIN | TYPE == BUY) {
			for (int i = 0; i < json.length(); i++) {
				String str[] = new String[5];
				try {
					JSONObject obj = json.getJSONObject(i);
					str[0] = obj.getString("lotNo");
					str[1] = obj.getString("caseAllAmt");
					str[2] = obj.getString("caseBuyAfterAmt");
					str[3] = obj.getString("flag");
					str[4] = obj.getString("id");
					str[0] = parseLotNo(str[0]);
					str[1] = parseAmt(str[1]);
					str[2] = parseProgress(str[2]);
					str[3] = parseFlag(str[3]);
					list.add(str);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (TYPE == DETAILS) {
			for (int i = 0; i < json.length(); i++) {
				String str[] = new String[5];
				try {
					JSONObject obj = json.getJSONObject(i);
					str[0] = obj.getString("caseId");
					str[1] = obj.getString("userNo");
					str[2] = obj.getString("buyNumByUser");
					str[3] = obj.getString("buyAmtByUser");
					str[4] = obj.getString("buyTime");
					str[2] += "��";
					str[3] = parseAmt(str[3]);
					list.add(str);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return list;

	}

	/**
	 * �������
	 * 
	 * @param str
	 * @return
	 */
	private String parseAmt(String str) {
		return Integer.toString(Integer.parseInt(str) / 100) + "Ԫ";
	}

	/**
	 * ��������
	 * 
	 * @param str
	 * @return
	 */
	private String parseLotNo(String str) {
		if (str.equals("B001")) {
			str = "˫ɫ��";
		} else if (str.equals("D3")) {
			str = "����3D";
		} else if (str.equals("QL730")) {
			str = "���ֲ�";
		}
		return str;
	}

	/**
	 * ��������
	 * 
	 * @param str
	 * @return
	 */
	private String parseProgress(String str) {
		float caseBuyAfterAmt = Float.parseFloat(str);
		if (caseBuyAfterAmt * 100 < 100 && caseBuyAfterAmt * 100 + 1 < 100) {// 0.001
			caseBuyAfterAmt = caseBuyAfterAmt * 1000 % 10 > 0 ? caseBuyAfterAmt * 100 + 1
					: caseBuyAfterAmt * 100;
		} else {
			caseBuyAfterAmt = caseBuyAfterAmt * 100;
		}
		return Integer.toString((int) caseBuyAfterAmt) + "%";
	}

	/**
	 * ������ʶ
	 * 
	 * @param str
	 * @return
	 */
	private String parseFlag(String str) {
		String flag = "";
		switch (Integer.parseInt(str)) {
		case 0:
			flag = "������";
			break;
		case 1:
			flag = "�ѳ�Ʊ";
			break;
		case 2:
			flag = "ʧ��";
			break;
		case 3:
			flag = "���ֳɹ�";
			break;
		case 4:
			flag = "�ѳ���";
			break;
		case 5:
			flag = "����ʧ��";
			break;
		case 6:
			flag = "��Ա";
			break;
		case 7:
			flag = "�ɹ�";
			break;
		}
		return flag;
	}

	/**
	 * ��������
	 * 
	 * @param json
	 * @return
	 */
	private ArrayList<String[]> parseInfo(JSONObject obj) {
		ArrayList list = new ArrayList<String[]>();
		String str[] = new String[16];
		try {
			str[0] = obj.getString("lotNo");// ����
			str[1] = obj.getString("caseAllAmt");// �ܽ��
			str[2] = obj.getString("caseBuyAfterAmt");// ����
			str[3] = obj.getString("flag");// ״̬
			try {
				str[4] = obj.getString("describe");// ��������
			} catch (Exception e) {
				str[4] = "";
			}
			str[5] = obj.getString("userNo");// ������
			str[6] = obj.getString("pushMoney");// ���������
			// str[3] = obj.getString("flag");// ����
			str[7] = obj.getString("buyedUserNum");// ��������
			str[8] = obj.getString("caseContent");// ��������
			// str[3] = obj.getString("lotNo");// ����
			str[9] = obj.getString("batchCode");// �ں�
			str[10] = obj.getString("caseNum");// ����
			// str[3] = obj.getString("caseAllAmt");// �ܽ��
			str[11] = obj.getString("caseBaoDiAmt");// ���׷���
			str[12] = obj.getString("caseOneAmt");// ÿ�ݽ��
			str[13] = obj.getString("caseBuyAmtByUser");// ���Ϲ�����
			str[14] = obj.getString("BcaseNnum");// ʣ�����
			str[15] = obj.getString("id");// ���ֱ��
			str[1] = parseAmt(str[1]);
			str[2] = parseProgress(str[2]);
			str[3] = parseFlag(str[3]);
			str[6] = parseProgress(str[6]);
			str[8] = JoinIn.getByContent(str[8], str[0]);
			str[0] = parseLotNo(str[0]);
			str[9] += "��";
			str[10] += "��";
			str[11] += "��";
			str[12] = parseAmt(str[12]);
			str[13] += "��";
			str[14] += "��";

			list.add(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

	/**
	 * �������ӿ�
	 */
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

}
