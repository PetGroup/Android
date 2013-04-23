package com.ruyicai.activity.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.account.AccountActivity;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.join.JoinCheckActivity;
import com.ruyicai.activity.more.FeedBack;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.dialog.LogOutDialog;
import com.ruyicai.dialog.MyDialogListener;
import com.ruyicai.net.newtransaction.AccountDetailQueryInterface;
import com.ruyicai.net.newtransaction.BalanceQueryInterface;
import com.ruyicai.net.newtransaction.BetQueryInterface;
import com.ruyicai.net.newtransaction.FeedBackListInterface;
import com.ruyicai.net.newtransaction.GiftQueryInterface;
import com.ruyicai.net.newtransaction.QueryintegrationInterface;
import com.ruyicai.net.newtransaction.SetupnicknameInterface;
import com.ruyicai.net.newtransaction.TrackQueryInterface;
import com.ruyicai.net.newtransaction.UserScoreDetailQueryInterface;
import com.ruyicai.net.newtransaction.WinQueryInterface;
import com.ruyicai.net.newtransaction.pojo.AccountDetailQueryPojo;
import com.ruyicai.net.newtransaction.pojo.BetAndWinAndTrackAndGiftQueryPojo;
import com.ruyicai.net.newtransaction.pojo.UserScroeDetailQueryPojo;
import com.ruyicai.net.newtransaction.usercenter.AutoGetScoresRules;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;

/**
 * �û�����
 * 
 * @author Administrator
 */
public class NewUserCenter extends Activity implements MyDialogListener {
	LogOutDialog logOutDialog;// ע����
	ProgressDialog dialog, progressDialog;
	private Dialog nicknameDialog;
	private List<Map<String, Object>> list;/* �б�������������Դ */
	private static final String IICON = "IICON";
	private final static String TITLE = "TITLE"; /* ���� */
	protected String phonenum, sessionid, userno, certid, mobileid, name;
	protected LinearLayout usecenerLinear;
	private final int DIALOG_BINDED = 1, DIALOG_BINDPHONE = 2;
	protected Button returnButton;
	protected TextView titleTextView;
	private boolean isgetscroe = true;// �Ƿ��ȡ����
	// private ShellRWSharesPreferences shellRW;
	String jsonString;
	private String textStr;
	// �û����֡�
	private String username = "", nickname = "", balance = "", score = "",
			mobileiduser = "", crididuser = "";
	private ImageView scoreshow, cridbindim, phonebindim;
	private TextView nicknamecontent, mobilecontent, balacecontent,
			pointcontent, cridbindtx, phonebindtx;
	private ListView usermoneylist, usercaipiaolist, usersetlist;
	Activity context;
	RWSharedPreferences shellRW;

	// �û����Ĺ�����
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.usercenter_mainlayout);
		context = this;
		inituserpoint();
		dialog = UserCenterDialog.onCreateDialog(this);

		// usecenerLinear = (LinearLayout)findViewById(R.id.usercenterContent);
		returnButton = (Button) findViewById(R.id.layout_usercenter_img_return);
		titleTextView = (TextView) findViewById(R.id.usercenter_mainlayou_text_title);
		returnButton.setBackgroundResource(R.drawable.returnselecter);
		scoreshow = (ImageView) findViewById(R.id.userillustrations);
	    shellRW = new RWSharedPreferences(this, "addInfo");
		initReturn();
		initsroreshow();
		initPojo();
		// usecenerLinear.addView(showView(NewUserCenter.this));
		initFuctionLayout();

	}

	protected void initReturn() {
		returnButton.setText("ע��");
		returnButton.setVisibility(View.VISIBLE);
		returnButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				logOutDialog = LogOutDialog.createDialog(context);
				logOutDialog.setOnClik(NewUserCenter.this);
			}
		});
	}

	private String getnickname(String nickname) {
		if (nickname.length() <= 6) {
			return nickname;
		} else {
			String name = nickname.substring(0, 5) + "**";
			return name;
		}

	}

	protected void initsroreshow() {
		scoreshow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new ScroesRules(NewUserCenter.this);
			}
		});
	}

	// �û����Ĺ�����
	protected void initFuctionLayout() {
		List<Map<String, Object>> list1 = getListForUsermonyeAdapter();
		List<Map<String, Object>> list2 = getListForAccountAdapter();
		List<Map<String, Object>> list3 = getListForusersetAdapter();
		UerCenterAdapter adapter1 = new UerCenterAdapter(this, list1);
		UerCenterAdapter adapter2 = new UerCenterAdapter(this, list2);
		UerCenterAdapter adapter3 = new UerCenterAdapter(this, list3);
		usermoneylist = (ListView) findViewById(R.id.usermoneylist);
		usercaipiaolist = (ListView) findViewById(R.id.usercaipiaolist);
		usersetlist = (ListView) findViewById(R.id.usersetlist);
		usermoneylist.setAdapter(adapter1);
		usercaipiaolist.setAdapter(adapter2);
		usersetlist.setAdapter(adapter3);
		usermoneylist.setOnItemClickListener(clickListener);
		usercaipiaolist.setOnItemClickListener(clickListener);
		usersetlist.setOnItemClickListener(clickListener);

	}

	// ����listview �߶�
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1))
				+ 2;
		((MarginLayoutParams) params).setMargins(0, 10, 0, 10);
		listView.setLayoutParams(params);
	}

	private void getFeedbackListNet() {
		dialog.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				Constants.feedBackData = FeedBackListInterface.getInstance()
						.getFeedbackList("0", "10", userno);
				try {
					Message msg = new Message();
					JSONObject feedjson = new JSONObject(Constants.feedBackData);
					String errorCode = feedjson.getString("error_code");
					if (errorCode.equals("0000")) {
						Constants.feedBackJSONArray = feedjson
								.getJSONArray("result");
						if (Constants.feedBackJSONArray.length() == 0) {
							msg.what = 13;
						} else {
							msg.what = 11;
							msg.obj = Constants.feedBackJSONArray;
						}
					} else {
						msg.what = 13;
						msg.obj = feedjson.getString("message");
					}
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	// �û�������ʾ�ؼ�
	protected void inituserpoint() {
		nicknamecontent = (TextView) findViewById(R.id.nickcontent);
		mobilecontent = (TextView) findViewById(R.id.mobilecontent);
		balacecontent = (TextView) findViewById(R.id.lesscontent);
		pointcontent = (TextView) findViewById(R.id.pointscontent);
		cridbindtx = (TextView) findViewById(R.id.critidbindtx);
		cridbindim = (ImageView) findViewById(R.id.critidbindimmmmm);
		phonebindtx = (TextView) findViewById(R.id.phonebindtx);
		phonebindim = (ImageView) findViewById(R.id.phonebindim);
		clickTextView(phonebindtx,
				this.getString(R.string.usercenter_bindphonenum));
		clickTextView(cridbindtx, this.getString(R.string.usercenter_bindID));
	}

	/**
	 * �����¼����Ϣ��ע�����Ϣ
	 */
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				Toast.makeText(NewUserCenter.this, (String) msg.obj,
						Toast.LENGTH_LONG).show();
				break;
			case 2:
				dialog.dismiss();
				Intent intent = new Intent(NewUserCenter.this,
						WinPrizeActivity.class);
				intent.putExtra("winjson", (String) msg.obj);
				startActivity(intent);
				break;
			case 3:
				dialog.dismiss();
				Intent intentbalance = new Intent(NewUserCenter.this,
						BalanceQueryActivity.class);
				intentbalance.putExtra("balancejson", (String) msg.obj);
				startActivity(intentbalance);
				break;
			case 4:
				dialog.dismiss();
				Intent intentbet = new Intent(NewUserCenter.this,
						BetQueryActivity.class);
				intentbet.putExtra("betjson", (String) msg.obj);
				startActivity(intentbet);
				break;
			case 5:
				dialog.dismiss();
				Intent intentgift = new Intent(NewUserCenter.this,
						GiftQueryActivity.class);
				intentgift.putExtra("giftjson", (String) msg.obj);
				startActivity(intentgift);
				break;
			case 6:
				dialog.dismiss();
				Intent intentaccount = new Intent(NewUserCenter.this,
						AccountDetailsActivity.class);
				intentaccount.putExtra("allaccountjson", (String) msg.obj);
				startActivity(intentaccount);
				break;
			case 7:
				dialog.dismiss();
				Intent intentTrack = new Intent(NewUserCenter.this,
						TrackQueryActivity.class);
				intentTrack.putExtra("trackjson", (String) msg.obj);
				startActivity(intentTrack);
				break;
			case 8:
				// ����
				if (nickname.equals("")) {
					nicknamecontent.setEnabled(true);
					nicknamecontent.setTextColor(Color.BLUE);
					nicknamecontent.setText("(�������)");
					nicknamecontent.setOnClickListener(nicknameclick);
				} else {
					nicknamecontent.setText(getnickname(nickname));
					nicknamecontent.setEnabled(false);
					nicknamecontent.setTextColor(Color.BLACK);
				}
				mobilecontent.setTextColor(Color.BLACK);
				mobilecontent.setText(getnickname(username));
				if (mobileiduser.equals("")) {
					phonebindtx.setText("δ���ֻ�");
					phonebindtx.setTextColor(Color.rgb(99, 99, 99));
					phonebindim.setImageResource(R.drawable.phonebindno);
					phonebindtx.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(NewUserCenter.this,
									BindPhonenumActivity.class);
							startActivity(intent);
						}
					});
				} else {
					phonebindtx.setText("�Ѱ��ֻ�");
					phonebindtx.setTextColor(Color.rgb(33, 66, 33));
					phonebindim.setImageResource(R.drawable.phonebind);
				}

				if (crididuser.equals("")) {
					cridbindtx.setText("δ�����֤");
					cridbindtx.setTextColor(Color.rgb(99, 99, 99));
					cridbindim.setImageResource(R.drawable.crididbindno);
				} else {
					cridbindtx.setText("�Ѱ����֤");
					cridbindtx.setTextColor(Color.rgb(33, 66, 33));
					cridbindim.setImageResource(R.drawable.crididbind);
				}
				balacecontent.setText(balance);
				pointcontent.setText(score);
				break;
			case 9:
				break;
			case 10:
				dialog.dismiss();
				Intent intentscroe = new Intent(NewUserCenter.this,
						UserScoreActivity.class);
				intentscroe.putExtra("scroe", (String) msg.obj);
				intentscroe.putExtra("myscroe", score);
				startActivity(intentscroe);
				break;
			case 11:
				dialog.dismiss();
				Intent feedListIntent = new Intent(NewUserCenter.this,
						FeedbackListActivity.class);
				feedListIntent.putExtra("feedBackArray", "" + msg.obj);
				startActivity(feedListIntent);
				break;
			case 12:
				dialog.dismiss();
				Toast.makeText(NewUserCenter.this, (String) msg.obj,
						Toast.LENGTH_LONG).show();
				break;
			case 13:
				dialog.dismiss();
				Toast.makeText(NewUserCenter.this, (String) msg.obj,
						Toast.LENGTH_LONG).show();
				Intent toFeedIntent = new Intent(NewUserCenter.this,
						FeedBack.class);
				startActivity(toFeedIntent);
				break;
			}
		}
	};
	// ����ǳ�δ���õ���¼�
	OnClickListener nicknameclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) NewUserCenter.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.usercenter_bindphone, null);
			nicknameDialog = new Dialog(NewUserCenter.this, R.style.dialog);
			nicknameDialog.setContentView(view);
			TextView title = (TextView) view
					.findViewById(R.id.usercenter_bindphonetitle);
			TextView nicknamelable = (TextView) view
					.findViewById(R.id.usercenter_bindphonelabel);
			nicknamelable.setText("�ǳƣ�");
			final EditText nickname = (EditText) view
					.findViewById(R.id.usercenter_edittext_bindphoneContext);
			Button cancle = (Button) view
					.findViewById(R.id.usercenter_bindphone_back);
			Button submit = (Button) view
					.findViewById(R.id.usercenter_bindphone_ok);
			submit.setText("�ύ");
			submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final String nickname2 = nickname.getText().toString();

					showDialog(0);
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							jsonString = SetupnicknameInterface.getInstance()
									.setupnickname(userno, nickname2);
							try {
								JSONObject nicknameup = new JSONObject(
										jsonString);
								String errorcode = nicknameup
										.getString("error_code");
								final String message = nicknameup
										.getString("message");
								if (errorcode.equals("0000")) {
									handler.post(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											Toast.makeText(NewUserCenter.this,
													message, Toast.LENGTH_LONG)
													.show();
											nicknamecontent.setEnabled(false);
											dialog.dismiss();
											nicknameDialog.dismiss();
											getusermessage();
										}
									});
								} else {
									handler.post(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method stub
											Toast.makeText(NewUserCenter.this,
													message, Toast.LENGTH_LONG)
													.show();
											dialog.dismiss();
											nicknameDialog.dismiss();

										}
									});
								}
							} catch (Exception e) {

							}
						}
					}).start();

				}
			});
			cancle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					nicknameDialog.dismiss();
				}
			});
			title.setText("�����ǳ�");
			nicknameDialog.show();
		}
	};

	public void setnc() {
		phonebindtx.setTextColor(999999);
		phonebindim.setImageResource(R.drawable.phonebindno);
		cridbindtx.setText("");
		cridbindtx.setTextColor(Color.rgb(33, 66, 33));
		cridbindtx.setBackgroundResource(R.drawable.crididbindno);
	}

	// �û�����������ҳ�������
	protected void clickTextView(TextView layout, final String str) {
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textStr = str;
				isLogin();
			}
		});
	}

	/**
	 * �ж��Ƿ��½
	 */
	public void isLogin() {
		RWSharedPreferences shellRW = new RWSharedPreferences(this, "addInfo");
		phonenum = shellRW.getStringValue("phonenum");
		sessionid = shellRW.getStringValue("sessionid");
		certid = shellRW.getStringValue("certid");
		name = shellRW.getStringValue("name");
		mobileid = shellRW.getStringValue("mobileid");
		if (sessionid == null || sessionid.equals("")) {
			Intent intentSession = new Intent(this, UserLogin.class);
			startActivityForResult(intentSession, 0);
		} else {
			userCenterDetail();
		}
	}

	// ��ȡ�û���Ϣ
	private void getusermessage() {
		nicknamecontent.setText("��ѯ��...");
		mobilecontent.setText("��ѯ��...");
		balacecontent.setText("��ѯ��...");
		pointcontent.setText("��ѯ��...");
		// showDialog(0);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				jsonString = QueryintegrationInterface.getInstance().queryintegration(phonenum, sessionid, userno);
				Message msg = new Message();
				try {
					JSONObject json = new JSONObject(jsonString);
					nickname = json.getString("nickName");
					crididuser = json.getString("certId");
					balance = json.getString("bet_balance");
					score = json.getString("score");
					username = json.getString("userName");
					mobileiduser = json.getString("mobileId");
					msg.what = 8;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					msg.what = 9;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	/**
	 * ��ÿ��������������� ��ѯ��Ϣ�б��Ӧѡ��
	 * 
	 * @param str
	 */
	private void initPojo() {
		phonenum = shellRW.getStringValue(ShellRWConstants.PHONENUM);
		sessionid = shellRW.getStringValue(ShellRWConstants.SESSIONID);
		userno = shellRW.getStringValue(ShellRWConstants.USERNO);
	}

	private void userCenterDetail() {
		String str = textStr;
		// ��ֵ
		if (str.equals("�˻���ֵ")) {
			Intent intent = new Intent(NewUserCenter.this,
					AccountActivity.class);
			intent.putExtra("isonKey", "fasle");
			startActivity(intent);
		}
		// �����ѯ
		if (str.equals("�ҵĺ���")) {
			Intent intent = new Intent(NewUserCenter.this,
					JoinCheckActivity.class);
			startActivity(intent);
		}
		// ����
		if (str.equals("�ҵ�����")) {
			getFeedbackListNet();
		}
		// ����ѯ
		if (this.getString(R.string.ruyihelper_balanceInquiry).equals(str)) {
			isgetscroe = false;
			showDialog(0);
			new Thread(new Runnable() {
				public void run() {
					jsonString = BalanceQueryInterface.balanceQuery(userno,
							sessionid, phonenum);
					Message msg = new Message();
					try {
						JSONObject aa = new JSONObject(jsonString);
						String errcode = aa.getString("error_code");
						String message = aa.getString("message");
						if (errcode.equals("0047")) {
							msg.what = 1;
							msg.obj = message;
							handler.sendMessage(msg);
						} else if (errcode.equals("0000")) {
							msg.what = 3;
							msg.obj = jsonString;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						msg.what = 4;
						msg.obj = jsonString;
						handler.sendMessage(msg);
					}
				}
			}).start();
		}
		// �н���ѯ
		if (this.getString(R.string.usercenter_winningCheck).equals(str)) {
			isgetscroe = false;
			showDialog(0);
			new Thread(new Runnable() {
				public void run() {
					BetAndWinAndTrackAndGiftQueryPojo winQueryPojo = new BetAndWinAndTrackAndGiftQueryPojo();
					winQueryPojo.setUserno(userno);
					winQueryPojo.setSessionid(sessionid);
					winQueryPojo.setPhonenum(phonenum);
					winQueryPojo.setPageindex("0");
					winQueryPojo.setMaxresult("10");
					winQueryPojo.setType("win");
					Message msg = new Message();
					jsonString = WinQueryInterface.getInstance().winQuery(
							winQueryPojo);
					try {
						JSONObject aa = new JSONObject(jsonString);
						String errcode = aa.getString("error_code");
						String message = aa.getString("message");
						if (errcode.equals("0047")) {

							msg.what = 1;
							msg.obj = message;
							handler.sendMessage(msg);
						} else if (errcode.equals("0000")) {
							msg.what = 2;
							msg.obj = jsonString;
							handler.sendMessage(msg);
						} else {
							msg.what = 1;
							msg.obj = message;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						msg.what = 2;
						msg.obj = jsonString;
						handler.sendMessage(msg);
					}
				}
			}).start();
		}
		// Ͷע��ѯ
		if (("Ͷע��¼").equals(str)) {
			showDialog(0);
			new Thread(new Runnable() {
				public void run() {
					BetAndWinAndTrackAndGiftQueryPojo betQueryPojo = new BetAndWinAndTrackAndGiftQueryPojo();
					betQueryPojo.setUserno(userno);
					betQueryPojo.setSessionid(sessionid);
					betQueryPojo.setPhonenum(phonenum);
					betQueryPojo.setPageindex("0");
					betQueryPojo.setMaxresult("10");
					betQueryPojo.setType("bet");

					Message msg = new Message();
					jsonString = BetQueryInterface.getInstance().betQuery(
							betQueryPojo);
					try {
						JSONObject aa = new JSONObject(jsonString);
						String errcode = aa.getString("error_code");
						String message = aa.getString("message");
						if (errcode.equals("0047")) {
							msg.what = 1;
							msg.obj = message;
							handler.sendMessage(msg);
						} else if (errcode.equals("0000")) {
							msg.what = 4;
							msg.obj = jsonString;
							handler.sendMessage(msg);
						} else {
							msg.what = 1;
							msg.obj = message;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						msg.what = 2;
						msg.obj = jsonString;
						handler.sendMessage(msg);
					}
				}
			}).start();
		}
		// ���Ͳ�ѯ
		if (("���ʲ�ѯ").equals(str)) {
			isgetscroe = false;
			showDialog(0);
			new Thread(new Runnable() {
				public void run() {
					BetAndWinAndTrackAndGiftQueryPojo giftQueryPojo = new BetAndWinAndTrackAndGiftQueryPojo();
					giftQueryPojo.setUserno(userno);
					giftQueryPojo.setSessionid(sessionid);
					giftQueryPojo.setPhonenum(phonenum);
					giftQueryPojo.setPageindex("1");
					giftQueryPojo.setMaxresult("10");
					giftQueryPojo.setType("gift");

					Message msg = new Message();
					jsonString = GiftQueryInterface.getInstance().giftQuery(
							giftQueryPojo);
					try {
						JSONObject aa = new JSONObject(jsonString);
						String errcode = aa.getString("error_code");
						String message = aa.getString("message");
						if (errcode.equals("0047")) {
							msg.what = 5;
							msg.obj = message;
							handler.sendMessage(msg);
						} else if (errcode.equals("0000")) {
							msg.what = 5;
							msg.obj = jsonString;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
					}
				}
			}).start();
		}
		// ׷�Ų�ѯ
		if (this.getString(R.string.usercenter_trackNumberInquiry).equals(str)) {
			showDialog(0);
			new Thread(new Runnable() {
				public void run() {
					BetAndWinAndTrackAndGiftQueryPojo winQueryPojo = new BetAndWinAndTrackAndGiftQueryPojo();
					winQueryPojo.setUserno(userno);
					winQueryPojo.setSessionid(sessionid);
					winQueryPojo.setPhonenum(phonenum);
					winQueryPojo.setPageindex("0");
					winQueryPojo.setMaxresult("10");
					winQueryPojo.setType("track");

					Message msg = new Message();
					jsonString = TrackQueryInterface.getInstance().trackQuery(
							winQueryPojo);
					try {
						JSONObject aa = new JSONObject(jsonString);
						String errcode = aa.getString("error_code");
						String message = aa.getString("message");
						if (errcode.equals("0047")) {

							msg.what = 1;
							msg.obj = message;
							handler.sendMessage(msg);
						} else if (errcode.equals("0000")) {
							msg.what = 7;
							msg.obj = jsonString;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						msg.what = 7;
						msg.obj = jsonString;
						handler.sendMessage(msg);
					}
				}
			}).start();
		}
		// �˻���ϸ
		if (this.getString(R.string.usercenter_accountDetails).equals(str)) {
			isgetscroe = false;
			showDialog(0);
			new Thread(new Runnable() {
				public void run() {
					AccountDetailQueryPojo accountDetailPojo = new AccountDetailQueryPojo();
					accountDetailPojo.setUserno(userno);
					accountDetailPojo.setSessionid(sessionid);
					accountDetailPojo.setPhonenum(phonenum);
					accountDetailPojo.setPageindex("0");
					accountDetailPojo.setTransactiontype("0");
					accountDetailPojo.setMaxresult("10");
					accountDetailPojo.setType("new");

					Message msg = new Message();
					jsonString = AccountDetailQueryInterface.getInstance()
							.accountDetailQuery(accountDetailPojo);
					try {
						JSONObject aa = new JSONObject(jsonString);
						String errcode = aa.getString("error_code");
						String message = aa.getString("message");
						if (errcode.equals("0047")) {

							msg.what = 1;
							msg.obj = message;
							handler.sendMessage(msg);
						} else if (errcode.equals("0000")) {
							msg.what = 6;
							msg.obj = jsonString;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {

					}
				}
			}).start();
		}
		// �˻�����
		if (this.getString(R.string.ruyihelper_accountWithdraw).equals(str)) {
			Intent intent = new Intent(NewUserCenter.this,
					AccountWithdrawActivity.class);
			startActivity(intent);
		}
		// �����޸�
		if (this.getString(R.string.usercenter_passwordChange).equals(str)) {
			isgetscroe = false;
			Intent intent = new Intent(NewUserCenter.this,
					ChangePasswordActivity.class);
			startActivity(intent);
		}
		// ���ֻ���
		if (this.getString(R.string.usercenter_bindphonenum).equals(str)) {
			Log.e(mobileid, mobileid + "Log.e(certid,certid);");
			if (mobileid == null || mobileid.equals("")) {
				Intent intent = new Intent(NewUserCenter.this,
						BindPhonenumActivity.class);
				startActivity(intent);
			} else {
				Intent intentbalance = new Intent(NewUserCenter.this,
						HadBindedPhoneOrUnBindPhone.class);
				intentbalance.putExtra("mobileid", mobileid);
				startActivity(intentbalance);
			}

		}
		// �����֤
		if (this.getString(R.string.usercenter_bindID).equals(str)) {
			if (certid == null || certid.equals("") || certid.equals("null")) {
				Intent intent = new Intent(NewUserCenter.this,
						BindIDActivity.class);
				startActivity(intent);
			} else {
				Intent intentbalance = new Intent(NewUserCenter.this,
						HadBindedID.class);
				intentbalance.putExtra("certid", certid);
				intentbalance.putExtra("name", name);
				startActivity(intentbalance);
			}

		}
		// ������ϸ
		if (str.equals("�ҵĻ���")) {
			showDialog(0);
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					UserScroeDetailQueryPojo scroeDetailPojo = new UserScroeDetailQueryPojo();
					scroeDetailPojo.setUserno(userno);
					scroeDetailPojo.setSessionid(sessionid);
					scroeDetailPojo.setPhonenum(phonenum);
					scroeDetailPojo.setPageindex("0");
					scroeDetailPojo.setMaxresult("10");
					scroeDetailPojo.setType("scoreDetail");
					Message msg = new Message();
					jsonString = UserScoreDetailQueryInterface.getInstance()
							.scroeDetailQuery(scroeDetailPojo);
					try {
						JSONObject aa = new JSONObject(jsonString);
						String errcode = aa.getString("error_code");
						String message = aa.getString("message");
						if (errcode.equals("0047")) {
							msg.what = 1;
							msg.obj = message;
							handler.sendMessage(msg);
						} else if (errcode.equals("0000")) {
							msg.what = 10;
							msg.obj = jsonString;
							handler.sendMessage(msg);
						} else if (errcode.equals("9999")) {

							msg.what = 1;
							msg.obj = message;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {

					}
				}
			}).start();
		}
		this.finishActivity(0);
	}

	/**
	 * �û����ĵ�������
	 */
	public class UerCenterAdapter extends BaseAdapter {
		private LayoutInflater mInflater; // �������б���
		private List<Map<String, Object>> mList;

		public UerCenterAdapter(Context context, List<Map<String, Object>> list) {
			mInflater = LayoutInflater.from(context);
			mList = list;
		}

		public int getCount() {
			return mList.size();
		}

		public Object getItem(int position) {
			return mList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			String title = (String) mList.get(position).get(TITLE);
			Integer iconid = (Integer) mList.get(position).get(IICON);
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.usercenter_listitem,
						null);
				holder = new ViewHolder();
				holder.title = (TextView) convertView
						.findViewById(R.id.usercenter_item_text);
				holder.title.setText(title);
				holder.lefticon = (ImageView) convertView
						.findViewById(R.id.usercenter_item_lefticon);
				holder.lefticon.setBackgroundResource(iconid);
				holder.righticon = (ImageView) convertView
						.findViewById(R.id.usercenter_item_righticon);
				holder.righticon.setVisibility(ImageView.VISIBLE);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}

		class ViewHolder {
			TextView title;
			ImageView lefticon;
			ImageView righticon;
		}
	}

	/**
	 * �û�����б�����ļ�������ʵ�ֶԵ��������text��ȡ
	 */
	OnItemClickListener clickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			TextView text = (TextView) view
					.findViewById(R.id.usercenter_item_text);
			textStr = text.getText().toString();
			isLogin();
		}
	};

	/**
	 * ����û��ʽ�����
	 * 
	 * @return
	 */
	protected List<Map<String, Object>> getListForUsermonyeAdapter() {
		String[] usercenterQueryInfoTitles = { "�˻���ֵ", "�˻�����", "�˻���ϸ", "�ʽ�����",
				"�ҵĻ���" };
		int[] usercenterQueryInfoIcons = { R.drawable.putinmoney,
				R.drawable.tixianw, R.drawable.zhanghumingxi,
				R.drawable.zijinxiangqing, R.drawable.usercroe };
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
		for (int i = 0; i < usercenterQueryInfoTitles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, usercenterQueryInfoTitles[i]);
			map.put(IICON, usercenterQueryInfoIcons[i]);
			list.add(map);
		}
		return list;
	}

	/**
	 * ����ҵĲ�Ʊ����
	 * 
	 * @return
	 */
	protected List<Map<String, Object>> getListForAccountAdapter() {
		String[] titles = { "�н���ѯ", "Ͷע��¼", "׷�Ų�ѯ", "���ʲ�ѯ", "�ҵĺ���" };
		int[] accountDetailInfoIcons = { R.drawable.zhoangjiangchaxun,
				R.drawable.touzhujilu, R.drawable.zhuihaochaxun,
				R.drawable.zengcaichaxun, R.drawable.wodehemai };
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, titles[i]);
			map.put(IICON, accountDetailInfoIcons[i]);
			list.add(map);
		}
		return list;
	}

	/**
	 * ����û���������
	 * 
	 * @return
	 */
	protected List<Map<String, Object>> getListForusersetAdapter() {
		String[] titles = { "�����޸�", "���֤��", "�ֻ��Ű�", "�ҵ�����" };
		int[] accountDetailInfoIcons = { R.drawable.mimaxiugai,
				R.drawable.sfzbd, R.drawable.mobilebindlable,
				R.drawable.myliuyan };
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(TITLE, titles[i]);
			map.put(IICON, accountDetailInfoIcons[i]);
			list.add(map);
		}
		return list;
	}

	/**
	 * NH H ��д���ؽ�JH
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		ExitDialogFactory.createExitDialog(this);
		return false;
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initPojo();
		if (sessionid != null && !sessionid.equals("")) {
			getusermessage();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		PublicMethod.myOutLog("onRestart", "onRestart");
	}

	@Override
	protected void onPause() {
		super.onPause();
		PublicMethod.myOutLog("onPause", "onPause");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * �������ӿ�
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			dialog = new ProgressDialog(this);
			dialog.setMessage("����������...");
			dialog.setIndeterminate(true);
			return dialog;
		}
		}
		return null;
	}

	/**
	 * ����һ��activity���ص�ǰactivityִ�еķ���
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (resultCode) {
		case RESULT_OK:
			isLogin();
			break;
		}
	}

	@Override
	public void onOkClick() {
		// TODO Auto-generated method stub
		Constants.hasLogin = false;
		NewUserCenter.this.finish();
		logOutDialog.clearLastLoginInfo();
	}

	@Override
	public void onCancelClick() {
		// TODO Auto-generated method stub

	}
}
