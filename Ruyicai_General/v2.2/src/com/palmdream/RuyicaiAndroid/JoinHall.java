package com.palmdream.RuyicaiAndroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid.BallBetPublicClass.BallHolder;
import com.palmdream.RuyicaiAndroid.BuyLotteryMainList.ImageAdapter;
import com.palmdream.RuyicaiAndroid.BuyLotteryMainList.SuccessReceiver;
import com.palmdream.netintface.iHttp;
import com.palmdream.netintface.jrtLot;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * �������
 */
public class JoinHall extends Activity implements
		RadioGroup.OnCheckedChangeListener, MyDialogListener {
	public final static String TITLE = "TITLE"; /* ���� */
	public final static String CONTENT = "CONTENT"; /* ���� */
	private List<Map<String, Object>> list;/* �б�������������Դ */
	private ArrayList<String[]> titles = new ArrayList();// ��Ҫ��������
	private RadioGroup joinGroup;
	private String topButtonStringId[] = { "˫ɫ��", "����3D", "���ֲ�" };
	private RadioGroup.LayoutParams topButtonLayoutParams;
	private int screen_width;
	private int width;
	private String[] type = { "˫ɫ��", "����3D", "���ֲ�" };// ��Ҫ��������
	int topButtonIdOn[] = { R.drawable.shuangseqiu01, R.drawable.fucai_3,
			R.drawable.qilecai01 };
	int topButtonIdOff[] = { R.drawable.shuangseqiu02, R.drawable.fucai_3_b,
			R.drawable.qilecai02 };
	private String typeLabel = "F47104";// ���ֱ�ʶĬ��˫ɫ��
	ProgressDialog progressdialog;
	private boolean start;
	public static boolean once = true;
	private int index;
	private static int indexGroup;
	private PopupWindow popList;
	ImageButton login;
	private IntentFilter loginSuccessFilter;
	private SuccessReceiver loginSuccessReceiver;
	String sessionid;// ��¼id
	int typeQuery;
	// showDialog(0);//��ʾ������
	/* ���� */
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				progressdialog.dismiss();
				break;
			case 1:
				progressdialog.dismiss();
				initList();
				break;
			case 2:
				progressdialog.dismiss();
				initList();
				Toast.makeText(JoinHall.this, "û�к��򷽰���", Toast.LENGTH_SHORT)
						.show();
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
		setContentView(R.layout.join_hall);

	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		popList.dismiss();
		once = true;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		lotno_ssq = getLotno("information4");
		lotno_ddd = getLotno("information5");
		lotno_qlc = getLotno("information6");
		// ��ȡ����
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			index = bundle.getInt("index");
		}
		init();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.unregisterReceiver(loginSuccessReceiver);
	}

	// ��ʼ�����
	public void init() {
		// ��ʼ���������
		ImageView back = (ImageView) findViewById(R.id.join_hall_back);
		TextView title = (TextView) findViewById(R.id.join_hall_title_top);
		ImageView startJoin = (ImageView) findViewById(R.id.join_hall_start);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		startJoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkTypeDialog();
			}
		});
		title.setText("�������");
		// ��ʼ��radioGroup
		joinGroup = (RadioGroup) findViewById(R.id.join_hall_radio_group);
		joinGroup.setOnCheckedChangeListener(this);
		screen_width = getWindowManager().getDefaultDisplay().getWidth();
		// screen_width = 300;
		width = screen_width / 3 + 15;
		RadioStateDrawable.other_width = width;
		RadioStateDrawable.other_screen_width = screen_width;
		topButtonLayoutParams = new RadioGroup.LayoutParams(width,
				RadioGroup.LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < topButtonStringId.length; i++) {
			TabBarButton tabButton = new TabBarButton(this);
			// tabButton.setState(topButtonStringId[i]);
			tabButton.setState(topButtonIdOff[i], topButtonIdOn[i]);
			tabButton.setId(i);
			tabButton.setGravity(Gravity.CENTER);
			tabButton.setTextSize(10);
			joinGroup.addView(tabButton, i, topButtonLayoutParams);
		}
		// if (once) {
		// once = false;
		joinGroup.check(indexGroup);
		// }
		// ��ʼ��spinner
		final Spinner joinType = (Spinner) findViewById(R.id.join_hall_spinner);
		switch (index) {
		case 0:
			joinType.setSelection(2);
			break;
		case 1:
			joinType.setSelection(3);
			break;
		case 2:
			joinType.setSelection(4);
			break;
		case 3:
			joinType.setSelection(0);
			break;
		}

		joinType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int position = joinType.getSelectedItemPosition();
				if (position == 0) {// �������
					flag = 3;
				} else if (position == 1) {// ���º���
					flag = 4;
				} else if (position == 2) {// ������Ա
					flag = 0;
				} else if (position == 3) {// ��������
					flag = 1;
				} else if (position == 4) {// �������
					flag = 2;
				}
				checkJoin(flag);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		// ��ʼ��list
		initList();
		// ������Ļ
		ViewFlipper mFlipper = ((ViewFlipper) this
				.findViewById(R.id.join_hall_flipper));
		TextView mtext = (TextView) findViewById(R.id.join_hall_title);
		mtext.setText(getInfo());
		mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_up_in));
		mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_up_out));
		mFlipper.startFlipping();

		ImageButton join;
		ImageButton buy;
		join = (ImageButton) findViewById(R.id.query_join);
		buy = (ImageButton) findViewById(R.id.query_buy);
		join.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				typeQuery = QueryInfo.JOIN;
				turnQuery(QueryInfo.JOIN);
			}
		});
		buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				typeQuery = QueryInfo.BUY;
				turnQuery(QueryInfo.BUY);
			}
		});
		// �����˵�
		LayoutInflater mLayoutInflater = (LayoutInflater) JoinHall.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View iv = mLayoutInflater.inflate(R.layout.menu_list, null);
		ImageButton logn = (ImageButton) iv.findViewById(R.id.logon);
		logn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popList.dismiss();
				Intent intent1 = new Intent(JoinHall.this, UserLogin.class);
				Bundle mBundle = new Bundle();
				mBundle.putBoolean("switch", true);// ѹ������
				intent1.putExtras(mBundle);
				startActivity(intent1);
			}
		});
		ImageButton exit = (ImageButton) iv.findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popList.dismiss();
				WhetherQuitDialog iQuitDialog = new WhetherQuitDialog(
						JoinHall.this, JoinHall.this);
				iQuitDialog.show();
			}
		});
		popList = new PopupWindow(iv, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// ��ӹ㲥������
		loginSuccessFilter = new IntentFilter(
				ScrollableTabActivity.ACTION_LOGIN_SUCCESS);
		loginSuccessReceiver = new SuccessReceiver();
		registerReceiver(loginSuccessReceiver, loginSuccessFilter);
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

	public void turnQuery(int type) {
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
				JoinHall.this, "addInfo");
		sessionid = shellRW.getUserLoginInfo("sessionid");
		if (!sessionid.equals("")) {
			Intent intent = new Intent(JoinHall.this, QueryInfo.class);
			Bundle mBundle = new Bundle();
			mBundle.putInt("type", type);// ѹ������
			intent.putExtras(mBundle);
			startActivity(intent);
		} else {
			Intent intent = new Intent(JoinHall.this, UserLogin.class);
			startActivityForResult(intent, 0);
		}
	}

	// ��ʼ��list
	public void initList() {

		// ����Դ
		list = getListForJoinAdapter();

		ListView listview = (ListView) findViewById(R.id.join_hall_list);
		listview.setDividerHeight(0);

		// ������
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.join_hall_list_item, new String[] { TITLE, CONTENT },
				new int[] { R.id.buyLotterMianList_text,
						R.id.buyLotterMianList_text_two });

		listview.setAdapter(adapter);

		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				for (int i = 0; i < titles.size(); i++) {
					if (position == i) {
						Intent intent = new Intent(JoinHall.this, JoinIn.class);
						Bundle mBundle = new Bundle();
						mBundle.putString("id", titles.get(i)[0]);// ѹ������
						mBundle.putString("user", titles.get(i)[2]);// ������
						mBundle.putString("allNum", titles.get(i)[3]);// �ܷ���
						mBundle.putString("type", typeLabel);// ����
						intent.putExtras(mBundle);
						startActivity(intent);
					}
				}

			}

		};
		listview.setOnItemClickListener(clickListener);
	}

	// ��ʼ���Ի������list
	public void initDialogList(ListView listview) {

		// ����Դ
		listview.setDividerHeight(0);

		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, type);
		listview.setAdapter(adapter);

		listview.setItemsCanFocus(false);
		listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

	}

	public List<Map<String, Object>> getListForJoinAdapter() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);

		for (int i = 0; i < titles.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, titles.get(i)[1]);
			map.put(CONTENT, titles.get(i)[4]);
			list.add(map);
		}

		return list;
	}

	// ��ȡ������Ϣ
	public String getInfo() {
		String news = "��ӭʹ�������";
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,
				"addInfo");
		String re = shellRW.getUserLoginInfo("information0");
		if (re.equalsIgnoreCase("") || re == null) {
			// ��ȡʧ�� ���ı�news
		} else {
			try {
				JSONObject obj = new JSONObject(re);
				news = obj.getString("news");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return news;
	}

	int type1 = 3;

	// ѡ����ֶԻ���
	public void checkTypeDialog() {
		start = true;

		LayoutInflater inflater = LayoutInflater.from(this);
		View failView = inflater.inflate(R.layout.join_hall_check_dialog, null);
		ListView listView = (ListView) failView
				.findViewById(R.id.join_hall_check_dialog_list);
		// initDialogList(listView);
		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, type);
		listView.setAdapter(adapter);

		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		OnItemClickListener clickListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					type1 = 0;
				} else if (position == 1) {
					type1 = 1;
				} else if (position == 2) {
					type1 = 2;
				}

			}

		};
		listView.setOnItemClickListener(clickListener);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.join_hall_check_dialog_title);
		builder.setView(failView);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// WinningVector.removeAllElements();
						Intent intent;
						if (start) {
							start = false;
							switch (type1) {
							case 0:
								intent = new Intent(JoinHall.this,
										ssqtestJoin.class);
								startActivity(intent);
								break;
							case 1:
								intent = new Intent(JoinHall.this,
										FC3DTestJoin.class);
								startActivity(intent);
								break;
							case 2:
								intent = new Intent(JoinHall.this,
										QLCJoin.class);
								startActivity(intent);
								break;
							}
						}
					}

				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// WinningVector.removeAllElements();
					}

				});
		builder.show();
	}

	String title_0 = "";// ������Ա
	String title_1 = "";// ��������
	String title_2 = "";// ���
	String title_3 = "";// �������
	String title_4 = "";// ���º���
	private int iretrytimes = 2;
	private JSONObject obj;
	String name;// ������
	String allNum;// �ܷ���
	String re;
	private int flag = 0;
	private String lotno;
	String lotno_ssq;
	String lotno_ddd;
	String lotno_qlc;

	// �����ѯ
	private void checkJoin(int type) {

		title_0 = "";// ������Ա
		title_1 = "";// ��������
		title_2 = "";// ���
		title_3 = "";// �������
		title_4 = "";// ���º���
		switch (type) {
		case 0:
			title_0 = "true";
			break;
		case 1:
			title_1 = "true";
			break;
		case 2:
			title_2 = "6";
			break;
		case 3:
			title_3 = "true";
		case 4:
			title_4 = "";
			break;
		}

		showDialog(0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				if (once) {
					once = false;
					while (iretrytimes < 3 && iretrytimes > 0) {
						titles = new ArrayList<String[]>();
						re = jrtLot.queryJoin(typeLabel, title_2, "", title_0,
								title_1, title_3, title_4, lotno, "100");

						try {
							obj = new JSONObject(re);
							error_code = obj.getString("error_code");
							iretrytimes = 3;
						} catch (JSONException e) {

							try {
								JSONArray objArray = new JSONArray(re);
								for (int i = 0; i < objArray.length(); i++) {
									obj = objArray.getJSONObject(i);
									String[] strs = new String[5];
									strs[0] = (String) obj.get("id");

									try {
										Integer mm = (Integer) obj
												.get("caseAllAmt") / 100;

										float caseBuyAfterAmt = Float
												.parseFloat(obj
														.getString("caseBuyAfterAmt"));
										if (caseBuyAfterAmt * 100 < 100
												&& caseBuyAfterAmt * 100 + 1 < 100) {// 0.001
											caseBuyAfterAmt = caseBuyAfterAmt * 1000 % 10 > 0 ? caseBuyAfterAmt * 100 + 1
													: caseBuyAfterAmt * 100;
										} else {
											caseBuyAfterAmt = caseBuyAfterAmt * 100;
										}
										name = (String) obj.get("userNo");
										allNum = Integer.toString((Integer) obj
												.get("caseAllNum"));
										String str = name;
										String str1 = "��"
												+ Integer.toString(mm)
												+ "+"
												+ Integer
														.toString((int) caseBuyAfterAmt)
												+ "%";
										strs[1] = str;
										strs[2] = name;
										strs[3] = allNum;
										strs[4] = str1;
										titles.add(strs);
									} catch (Exception ex) {
										Integer mm = (Integer) obj
												.get("caseAllAmt") / 100;

										Integer amt = (Integer) obj
												.get("caseBuyAfterAmt") * 100;
										name = (String) obj.get("userNo");
										allNum = Integer.toString((Integer) obj
												.get("caseAllNum"));
										String str = name;
										String str1 = "��"
												+ Integer.toString(mm) + "+"
												+ Integer.toString(amt) + "%";
										strs[1] = str;
										strs[2] = name;
										strs[3] = allNum;
										strs[4] = str1;
										titles.add(strs);
									}
								}
								iretrytimes = 3;
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
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
								re = jrtLot.queryJoin(typeLabel, title_2, "",
										title_0, title_1, title_3, title_4,
										lotno, "100");
								try {
									obj = new JSONObject(re);
									error_code = obj.getString("error_code");
									iretrytimes = 3;
								} catch (JSONException e) {

									try {
										JSONArray objArray = new JSONArray(re);
										for (int i = 0; i < objArray.length(); i++) {
											String[] strs = new String[5];
											strs[0] = (String) obj.get("id");

											try {
												Integer mm = (Integer) obj
														.get("caseAllAmt") / 100;

												float caseBuyAfterAmt = Float
														.parseFloat(obj
																.getString("caseBuyAfterAmt"));
												if (caseBuyAfterAmt * 100 < 100
														&& caseBuyAfterAmt * 100 + 1 < 100) {// 0.001
													caseBuyAfterAmt = caseBuyAfterAmt * 1000 % 10 > 0 ? caseBuyAfterAmt * 100 + 1
															: caseBuyAfterAmt * 100;
												} else {
													caseBuyAfterAmt = caseBuyAfterAmt * 100;
												}
												name = (String) obj
														.get("userNo");
												allNum = Integer
														.toString((Integer) obj
																.get("caseAllNum"));
												String str = name;
												String str1 = "��"
														+ Integer.toString(mm)
														+ "+"
														+ Integer
																.toString((int) caseBuyAfterAmt)
														+ "%";
												strs[1] = str;
												strs[2] = name;
												strs[3] = allNum;
												strs[4] = str1;
												titles.add(strs);
												Log.e("titles[i][1].add(str)",
														"" + titles.get(i));
											} catch (Exception ex) {
												Integer mm = (Integer) obj
														.get("caseAllAmt") / 100;

												Integer amt = (Integer) obj
														.get("caseBuyAfterAmt") * 100;
												String str = (String) obj
														.get("userNo");
												String str1 = "��"
														+ Integer.toString(mm)
														+ "+"
														+ Integer.toString(amt)
														+ "%";
												strs[1] = str;
												strs[2] = name;
												strs[3] = allNum;
												strs[4] = str1;
												titles.add(strs);
												Log.e("titles[i][1].add(str)",
														"" + titles.get(i));
											}
										}
										iretrytimes = 3;
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										iretrytimes--;
										error_code = "000";
									}
								}
							}
						}
					}
					iretrytimes = 2;
					once = true;
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
					}
				}

			}

		}).start();
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

	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case 0:
			typeLabel = "F47104";// F47104��F02904����˫ɫ��
			lotno = lotno_ssq;
			indexGroup = 0;
			checkJoin(flag);
			break;
		case 1:
			typeLabel = "F47103";// F47103��F02903����3D
			lotno = lotno_ddd;
			indexGroup = 1;
			checkJoin(flag);
			break;
		case 2:
			typeLabel = "F47102";// F47102��F02902�������ֲ�
			lotno = lotno_qlc;
			indexGroup = 2;
			checkJoin(flag);
			break;
		}
	}

	@Override
	public void onCancelClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onOkClick(int[] aNums) {
		// TODO Auto-generated method stub
		// finish();
		// System.exit(0);
		// android.os.Process.killProcess(android.os.Process.myPid());
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		am.restartPackage(getPackageName());
	}

	public class SuccessReceiver extends BroadcastReceiver {
		Context context;

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			this.context = context;
			showSuccessReceiver();
		}

		public void showSuccessReceiver() {

		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			// PublicMethod.myOutput("-------iType----" + iType);
			// if (iType == 0) {
			turnQuery(typeQuery);
			// }
			break;
		default:
			Toast.makeText(JoinHall.this, "δ��¼�ɹ���", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
