package com.ruyicai.activity.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.more.CompanyInfo;
import com.ruyicai.activity.more.MoreActivity;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.net.newtransaction.QueryDNAInterface;
import com.ruyicai.net.newtransaction.RechargeInterface;
import com.ruyicai.net.newtransaction.pojo.RechargePojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;
import com.ruyicai.util.wap.NetworkUtils;

/**
 * ��ҳ�� ��ֵ����
 * @author Administrator
 *
 */
public class AccountActivity extends Activity  implements HandlerMsg {
	private final String YINTYPE = "0900";
	private String url="";
	private ProgressDialog progressdialog;
	private String re;
	private String sessionId="";
	private String phonenum="";
	private String userno="";
	private String RECHARGTYPE = "0";
	HandlerMsg msg;
	private String TITLE = "title";
	private String ISHANDINGFREE = "isHandingFree";
	private String  PICTURE = "";
	private String error_code;
	private String strExpand;
	private MyHandler handler = new MyHandler(this);
	private String message="";
	private String xml = "";
	private String isonkey="";
	private RelativeLayout top;
	private Button returnButton;
	private String textString;
	private String[] bankNames = {"�й���������","�й�ũҵ����","�й���������","��������","�й�������������","��������","��ҵ����","��������","�й��������",
			                      "�Ϻ��ֶ���չ����","���ڷ�չ����"};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        factory = LayoutInflater.from(this);
		List list = null;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.account_list_main);
		setTopText();
		ListView listView = (ListView) findViewById(R.id.account_rechange_listview);
		list = setContentForMainList();
		AccountAdapter adapter = new AccountAdapter(this,list); 
		String onkey = getIntent().getStringExtra("isonKey");
		if(onkey!=null&&!onkey.equals("")){
			isonkey = onkey;
		}
		top =(RelativeLayout)findViewById(R.id.main_buy_title);
		if(isonkey.equals("fasle")){
			top.setVisibility(View.VISIBLE);
			returnButton=(Button)findViewById(R.id.layout_main_img_return);
			returnButton.setVisibility(View.INVISIBLE);
			initReturn();
		}
		listView.setAdapter(adapter);
		AccountMainItemListener listener=new AccountMainItemListener(this);
        listView.setOnItemClickListener(listener);
	}
	
	protected  void initReturn(){
		returnButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
					finish();
			}
		});
	}
	/**
	 * ����ͷ��text��Ϣ
	 * @author Administrator
	 *
	 */
	public void setTopText(){
		TextView textTop =  (TextView)findViewById(R.id.account_list_main_text);
		String text1 = getString(R.string.computer_rechargeinfo1);
		String text2 = getString(R.string.computer_address);
		String text3 = getString(R.string.computer_rechargeinfo2);
		Spanned spanned = Html.fromHtml("<a href=\"http://www.ruyicai.com\">"+text2+"</a>");
		textTop.append(text1);
		textTop.append(spanned);
		textTop.append(text3);
		textTop.setMovementMethod(LinkMovementMethod.getInstance());  
	}
	
	class AccountAdapter extends BaseAdapter{
		private LayoutInflater mInflater; // �������б���
		private List<Map<String, Object>> mList;
		public AccountAdapter(Context context, List<Map<String, Object>> list) {
			mInflater = LayoutInflater.from(context);
			mList = list;
		}
		public int getCount() {
			return mList.size();
		}
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			String title = (String) mList.get(position).get(TITLE);
			Integer  iconid = (Integer)mList.get(position).get(PICTURE);
			String alertStr = (String)mList.get(position).get(ISHANDINGFREE);
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.account_listviw_item,null);
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.account_recharge_listview_text);
				holder.isfreeHanding = (TextView)convertView.findViewById(R.id.ishandingfree);
				holder.lefticon = (ImageView)convertView.findViewById(R.id.account_recharge_type);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			SpannableStringBuilder builder1 = new SpannableStringBuilder(); 
			String str1 = title;
			builder1.append(str1);
			if(position==0||position==1||position==2){
				String alertStr1 = "(��������)";
				builder1.append(alertStr1);
			    builder1.setSpan(new ForegroundColorSpan(Color.RED), builder1.length()-alertStr1.length(), builder1.length(), Spanned.SPAN_COMPOSING); 
			}
			holder.title.setText(builder1);
			holder.lefticon.setBackgroundResource(iconid);
			SpannableStringBuilder builder = new SpannableStringBuilder(); 
			String str = alertStr;
			builder.append(str);
			if(position==0||position==3){
//				String alertStr1 = "���������ѣ�";
//				builder.append(alertStr1);
//			    builder.setSpan(new ForegroundColorSpan(Color.RED), builder.length()-alertStr1.length(), builder.length(), Spanned.SPAN_COMPOSING); 
			}
			holder.isfreeHanding.setHint(builder);
			return convertView;
		}
		class ViewHolder{
			TextView title;
			ImageView lefticon;
			TextView isfreeHanding;
		}
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initBankCardNoInfo();
		Constants.MEMUTYPE = 0;
	}
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	LayoutInflater factory = null;
	private Dialog RechargeType=null;
	EditText bank_card_phone_bankid;
	EditText bank_card_phone_phone_num;
	EditText bank_card_phone_name;
	RWSharedPreferences shellRW;
	
	//���п��绰��ֵ������(DNA)
    protected Dialog createBankCardPhoneDialog(){
      RECHARGTYPE = "01";	
	  final View bank_card_phone_online_view = factory.inflate(R.layout.account_bank_card_phone_online_dialog, null);
	  bank_card_phone_bankid = (EditText) bank_card_phone_online_view.findViewById(R.id.bank_card_phone_bankid);
      bank_card_phone_bankid.setText(bankCardNo);
      bank_card_phone_phone_num = (EditText)bank_card_phone_online_view.findViewById(R.id.bank_card_phone_phone_num);// �ֻ���
	  bank_card_phone_phone_num.setText(bindPhone);
	  bank_card_phone_bankid.setEnabled(false);
	  final Button ok = (Button)bank_card_phone_online_view.findViewById(R.id.ok);
		final Button canel =(Button)bank_card_phone_online_view.findViewById(R.id.canel);
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RWSharedPreferences pre = new RWSharedPreferences(AccountActivity.this, "addInfo");
				String sessionIdStr = pre.getStringValue("sessionid");
				if (sessionIdStr == null || sessionIdStr.equals("")) {
				    Intent intentSession = new Intent(AccountActivity.this, UserLogin.class);
					startActivity(intentSession);
				} else {
					// ���п�������ֵ��������
					beiginBankCardPhoneOnline(bank_card_phone_online_view);
				}
			}
		});
		canel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RechargeType.dismiss();
			}
		});
		        RechargeType = new Dialog(this,R.style.dialog);
		        RechargeType.setContentView(bank_card_phone_online_view);  
		        return RechargeType;
	}
    
    //���п��绰��ֵ������(DNA)��δ�� ��
    protected Dialog createBankCardPhoneDialogNo(){
    	RECHARGTYPE = "01";
		RWSharedPreferences pre = new RWSharedPreferences(AccountActivity.this, "addInfo");
    	final View bank_card_phone_view = factory.inflate(R.layout.account_bank_card_phone_dialog, null);
		String phonenum = pre.getStringValue("mobileid");
	    bank_card_phone_phone_num = (EditText) bank_card_phone_view.findViewById(R.id.bank_card_phone_phone_num);// �ֻ���
		bank_card_phone_name = (EditText) bank_card_phone_view.findViewById(R.id.bank_card_phone_phone_name);// ����
		bank_card_phone_name.setText(name);
		bank_card_phone_phone_num.setText(phonenum);
 		EditText bank_card_phone_bankid = (EditText) bank_card_phone_view.findViewById(R.id.bank_card_phone_bankid);// ���п���
 		EditText bank_card_phone_idcard = (EditText) bank_card_phone_view.findViewById(R.id.bank_card_phone_phone_idcard);// ���֤��
 		EditText bank_card_phone_home = (EditText) bank_card_phone_view.findViewById(R.id.bank_card_phone_phone_home);// �������ڵ�
 		EditText bank_card_phone_province = (EditText) bank_card_phone_view.findViewById(R.id.bank_card_phone_phone_province);// ����ʡ
 		final Spinner spinner = (Spinner) bank_card_phone_view.findViewById(R.id.Spinner01);
 		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bankNames);
 		adapter.setDropDownViewResource(R.layout.myspinner_dropdown);   
 		spinner.setAdapter(adapter); 
 		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				int position = spinner.getSelectedItemPosition();
				bankName = bankNames[position];
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
 		spinner.setSelection(1);
 		bank_card_phone_bankid.setText(bankCardNo);
 		bank_card_phone_idcard.setText(certid);
 		bank_card_phone_home.setText(certAddress);
 		bank_card_phone_province.setText(bankAddress);
	    final Button ok = (Button)bank_card_phone_view.findViewById(R.id.ok);
	    final Button canel =(Button)bank_card_phone_view.findViewById(R.id.canel);
			ok.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					RWSharedPreferences pre = new RWSharedPreferences(AccountActivity.this, "addInfo");
					String sessionIdStr = pre.getStringValue("sessionid");
					if (sessionIdStr!=null&&sessionIdStr.equals("")) {
						Intent intentSession = new Intent(AccountActivity.this,UserLogin.class);
						startActivity(intentSession);
					}else{
						beiginBankCardPhoneNo(bank_card_phone_view,bankName);
				    }
				}
			});
			canel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					RechargeType.dismiss();
				}
			});
	        RechargeType = new Dialog(this,R.style.dialog);
	        RechargeType.setContentView(bank_card_phone_view);
	        return RechargeType;
    }
    /**
     * ���dna��ֵ��Ϣ
     */
    public void initBankCardNoInfo(){
    	bankCardNo = "";
        name="";
        certid="";
        bankAddress="";
     	certAddress="";
    }
	private String phoneCardType = "0206";
	private String phoneCardValue = "100";
	private String gameCardType = "0204";
	// �ƶ���ֵ��
	private final String YIDONG = "0203";
	// ��ͨ��ֵ��
    private final String LIANTONG = "0206";
	// ���ų�ֵ��
	private final String DIANXIN = "0221";
	//�绰����ֵ������
    protected Dialog createPhoneRechargeCardDialog(){
    	RECHARGTYPE = "02";
    	final View phone_card_recharg_view = factory.inflate(R.layout.account_phone_cards_recharge_dialog, null);
		final Spinner phone_card_spinner = (Spinner) phone_card_recharg_view.findViewById(R.id.phone_card_spinner);
		phone_card_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {

						// ��������򡣡���
						int position = phone_card_spinner.getSelectedItemPosition();
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
		
		final Spinner phone_card_value_spinner = (Spinner) phone_card_recharg_view.findViewById(R.id.phone_card_value_spinner);
		phone_card_value_spinner.setSelection(4);// Ĭ��100Ԫ
		phone_card_value_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
                        int position = phone_card_value_spinner
								.getSelectedItemPosition();
						String[] values = { "10", "20", "30", "50", "100","200", "300", "500" };
						for (int i = 0; i < values.length; i++) {
							phoneCardValue = values[position];
						}
					}
					public void onNothingSelected(AdapterView<?> arg0) {
						// û���κεĴ����¼�ʱ
						}
                 });
		final Button ok = (Button)phone_card_recharg_view.findViewById(R.id.ok);
		final Button canel =(Button)phone_card_recharg_view.findViewById(R.id.canel);
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RWSharedPreferences pre = new RWSharedPreferences(AccountActivity.this, "addInfo");
				String sessionIdStr = pre.getStringValue("sessionid");
				if (sessionIdStr!=null&&sessionIdStr.equals("")) {
					Intent intentSession = new Intent(AccountActivity.this,UserLogin.class);
					startActivity(intentSession);}
				else{
			    beginPhoneCardRecharge(phone_card_recharg_view);
			    }
			
			}
		});
		canel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RechargeType.dismiss();
			}
		});
		        RechargeType = new Dialog(this,R.style.dialog);
		        RechargeType.setContentView(phone_card_recharg_view);
    	        return RechargeType;
    }
    boolean isWebView = false;//�������֧����
    //֧������ֵ������
    protected Dialog createAlipayDialog(){
    	RECHARGTYPE = "05";
    	final View zfb_recharge_view = factory.inflate(R.layout.account_alipay_recharge_dialog, null);
    	final Button ok = (Button)zfb_recharge_view.findViewById(R.id.ok);
		final Button canel =(Button)zfb_recharge_view.findViewById(R.id.canel);
		ok.setText("wap֧��");
		canel.setText("�����֧��");
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				isWebView = true;
				beginAlipayRecharge(zfb_recharge_view);
			}
		});
	   canel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				RechargeType.dismiss();
				isWebView = false;
				beginAlipayRecharge(zfb_recharge_view);
			}
		});
        RechargeType = new Dialog(this,R.style.dialog);
        RechargeType.setContentView(zfb_recharge_view);
        return RechargeType;
    }
    //����֧����ֵ������
    protected Dialog createYinpayDialog(){
    	RECHARGTYPE = "06";
    	final View view = factory.inflate(R.layout.account_alipay_recharge_dialog, null);
    	TextView title = (TextView) view.findViewById(R.id.zfb_text_title);
    	title.setText(getResources().getString(R.string.yin_bank_cards_recharge));
    	TextView alertText = (TextView) view.findViewById(R.id.zfb_text_alert);
    	alertText.setText("֧�ֵ�����>>");
    	alertText.setTextColor(Color.BLUE);
    	alertText.setTextSize(16);
    	alertText.setOnClickListener(textclick);
//    	alertText.setVisibility(TextView.GONE);
    	final Button ok = (Button)view.findViewById(R.id.ok);
		final Button canel =(Button)view.findViewById(R.id.canel);
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				beginYinpayRecharge(view);
			}
		});
	   canel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RechargeType.dismiss();
			}
		});
        RechargeType = new Dialog(this,R.style.dialog);
        RechargeType.setContentView(view);
        return RechargeType;
    }
    
 //֧������dialog  view   
    public View getView(){
 		LayoutInflater factoryProtocol = LayoutInflater.from(this);
 		final View view = factoryProtocol.inflate(R.layout.user_login_protocol_dialog, null);
 		WebView webView = (WebView) view.findViewById(R.id.ruyipackage_webview);
 	    String iFileName = "accoutyin.html";
 		String url = "file:///android_asset/" + iFileName;
 		webView.loadUrl(url);
 	   return view;
    }
 //֧�����е���¼�   
    OnClickListener textclick =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new  AlertDialog.Builder(AccountActivity.this).setTitle("֧�ֵ�����")
			.setView(getView()).setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int whichButton) {

						}
					}).setNegativeButton(R.string.xitongshezhi_check_off,new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					}).create().show();
		}
	};
	private final String ZHAOSHANG = "0101";
	// ��������
	private final String JIANSHE = "0102";
	// ��������
	private final String GONGSHANG = "0103";
	private String bankType = "CMBCHINA-WAP";
	//���п��绰��ֵ������
    protected Dialog createPhoneBankRechargeDialog(){
    	RECHARGTYPE = "03";
    	final View phone_bank_recharg_view = factory.inflate(R.layout.account_phone_bank_recharg_dialog, null);
		final Spinner phone_bank_spinner = (Spinner) phone_bank_recharg_view.findViewById(R.id.phone_bank_spinner);
		phone_bank_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// ��������򡣡���
						int position = phone_bank_spinner.getSelectedItemPosition();
						if (position == 0) {
							bankType = ZHAOSHANG;
						} else if (position == 1) {
							bankType = JIANSHE;
						} else {
							bankType = GONGSHANG;
						}
					}
					public void onNothingSelected(AdapterView<?> arg0) {
						// û���κεĴ����¼�ʱ
					}
				});
		final Button ok = (Button)phone_bank_recharg_view.findViewById(R.id.ok);
		final Button canel =(Button)phone_bank_recharg_view.findViewById(R.id.canel);
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RWSharedPreferences pre = new RWSharedPreferences(AccountActivity.this, "addInfo");
				String sessionIdStr = pre.getStringValue("sessionid");
				if (sessionIdStr!=null&&sessionIdStr.equals("")) {
					Intent intentSession = new Intent(AccountActivity.this,UserLogin.class);
					startActivity(intentSession);}
				else{
					beginPhoneBankRecharge(phone_bank_recharg_view);
				}
			}
		});
		canel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RechargeType.dismiss();
			}
		});
        RechargeType = new Dialog(this,R.style.dialog);
        RechargeType.setContentView(phone_bank_recharg_view);    
        return RechargeType;
    }
	private final String ZHENGTU = "0204";
	// ����һ��ͨ
	private final String JUNWANG = "0201";
	// ʢ��
	private final String SHENGDA = "0202";
    
    //��Ϸ�㿨��ֵ������
    protected Dialog createGameCardDialog(){
    	RECHARGTYPE = "04";
    	final View game_card_view = factory.inflate(R.layout.account_game_card_dialog, null);
		final Spinner game_card_spinner = (Spinner) game_card_view.findViewById(R.id.game_card_spinner);
		game_card_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {

						// ��������򡣡���
						int position = game_card_spinner.getSelectedItemPosition();
						if (position == 0) {
							gameCardType = ZHENGTU;
						} else if (position == 1) {
							gameCardType = SHENGDA;
						} else
							gameCardType = JUNWANG;
					}
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
		final Button ok = (Button)game_card_view.findViewById(R.id.ok);
		final Button canel =(Button)game_card_view.findViewById(R.id.canel);
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RWSharedPreferences pre = new RWSharedPreferences(AccountActivity.this, "addInfo");
				String sessionIdStr = pre.getStringValue("sessionid");
				if (sessionIdStr!=null&&sessionIdStr.equals("")) {
					Intent intentSession = new Intent(AccountActivity.this,UserLogin.class);
					startActivity(intentSession);}
				else{
				beginGameCardRecharge(game_card_view);
				}
			}
		});
		canel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RechargeType.dismiss();
			}
		});
       RechargeType = new Dialog(this,R.style.dialog);
       RechargeType.setContentView(game_card_view);    
      
    	return RechargeType;
    }
//    //��һ��DNA��ֵ�󶨵�����
    protected Dialog createBankPhoneCardRegisterDialog(){
    	RECHARGTYPE = "01";//
    	final View phone_bank_card_view = factory.inflate(R.layout.account_bank_card_phone_register_dialog, null);
    	RechargeType = new AlertDialog.Builder(this).setTitle(R.string.bank_cards_recharge).setView(phone_bank_card_view).setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								beginRegisterBankPhoneCard(phone_bank_card_view);
								/* User clicked OK so do some stuff */
							}
						}).setNegativeButton(R.string.cancel,null).create();
    	return RechargeType;
    }
    //֧������ֵ
     private void beginAlipayRecharge(View Vi) {//isWebView = false�������������

		final EditText zfb_recharge_value = (EditText) Vi.findViewById(R.id.zfb_recharge_value);		
		final String zfb_recharge_value_string = zfb_recharge_value.getText().toString();
		RWSharedPreferences pre = new RWSharedPreferences(AccountActivity.this, "addInfo");
		String sessionIdStr = pre.getStringValue("sessionid");
		if (sessionIdStr.equals("")||sessionIdStr == null) {
			Intent intentSession = new Intent(AccountActivity.this,UserLogin.class);
			startActivity(intentSession);
		} else {
			if (zfb_recharge_value_string.equals("")|| zfb_recharge_value_string.length() == 0) {
				Toast.makeText(this, "����Ϊ�գ�", Toast.LENGTH_LONG).show();
			} else {
				// ֧������ֵ�����ȡ
				// ��Ϊ�߳� 2010/7/9�³�
				RechargePojo rechargepojo =new RechargePojo();;
				rechargepojo.setAmount(zfb_recharge_value_string);
				rechargepojo.setRechargetype(RECHARGTYPE);
				rechargepojo.setCardtype("0300");
				if(isWebView){
					rechargepojo.setBankAccount("4");//4֧����wap֧��
				}else{
					rechargepojo.setBankAccount("3");//3֧���������֧��
				}
				recharge(rechargepojo);
			}
		}
	}
     //������ֵ
     private void beginYinpayRecharge(View Vi) {

		final EditText zfb_recharge_value = (EditText) Vi.findViewById(R.id.zfb_recharge_value);
		
		final String zfb_recharge_value_string = zfb_recharge_value.getText().toString();
		RWSharedPreferences pre = new RWSharedPreferences(AccountActivity.this, "addInfo");
		String sessionIdStr = pre.getStringValue("sessionid");
		if (sessionIdStr.equals("")) {
			Intent intentSession = new Intent(AccountActivity.this,UserLogin.class);
			startActivity(intentSession);
		} else {
			if (zfb_recharge_value_string.equals("")|| zfb_recharge_value_string.length() == 0) {
				Toast.makeText(this, "����Ϊ�գ�", Toast.LENGTH_LONG).show();
			} else {
				// ֧������ֵ�����ȡ
				// ��Ϊ�߳� 2010/7/9�³�
				RechargePojo rechargepojo =new RechargePojo();;
				rechargepojo.setAmount(zfb_recharge_value_string);
				rechargepojo.setRechargetype(RECHARGTYPE);
				rechargepojo.setCardtype(YINTYPE);
				recharge(rechargepojo);
			}
		}
	}
     
     //�绰���г�ֵ
     private void beginPhoneBankRecharge(View Vi) {
 		final EditText phone_bank_enter_value = (EditText) Vi.findViewById(R.id.phone_bank_enter_value);
 		Editable phone_bank_value = phone_bank_enter_value.getText();
 		final String phone_bank_value_string = String.valueOf(phone_bank_value);
 		// �ֻ�������������
 		if ((!(bankType.equals("")) && bankType != null)&& (!(phone_bank_value_string.equals("")) && phone_bank_value_string != null)) {
 		if (Integer.parseInt(phone_bank_value_string) >= 1) {
 			    RechargePojo rechargepojo =new RechargePojo();;
 			    rechargepojo.setCardtype(bankType);
 			    rechargepojo.setAmount(phone_bank_value_string);
 			    rechargepojo.setRechargetype(RECHARGTYPE);
 	            recharge(rechargepojo);
 		} else{
 				Toast.makeText(this, "��ֵ�������Ϊ1Ԫ��", Toast.LENGTH_LONG).show();
 		}
 		} else{
 			
 			Toast.makeText(this, "����Ϊ�գ�", Toast.LENGTH_LONG).show();
 		}
 	}
      // ���п�������ֵ(DNA)
 	  private void beiginBankCardPhoneOnline(View vi) {
 		
 		EditText bank_card_phone_recharge_value = (EditText) vi.findViewById(R.id.bank_card_phone_recharge_value);
 		final String rechargevalue = bank_card_phone_recharge_value.getText().toString();
 		EditText bank_card_phone_phone_num = (EditText) vi.findViewById(R.id.bank_card_phone_phone_num);
 		final String cardphonenum = bank_card_phone_phone_num.getText().toString();
 		final String cardid = bank_card_phone_bankid.getText().toString();
 		// fqc edit 7/13/2010 �ֻ������߳�ֵ���ı���ʽ ��ֵ���20Ԫ���ϵ�����������20������������ �ֻ����룺����11λ
 		// ��
 		if ((!(cardphonenum.equals("")) && cardphonenum != null)&& (!(rechargevalue.equals("")) && rechargevalue != null)&& (!(cardid.equals("")) && cardid != null)) {
 			if (cardphonenum.length() == 11) {
 				if (Integer.parseInt(rechargevalue) >= 20) {

 			String	bank_card_id = cardid;
 			RechargePojo rechargepojo =new RechargePojo();;
 			rechargepojo.setAmount(rechargevalue);
 			rechargepojo.setCardno(bank_card_id);
 			rechargepojo.setCardtype("0101");
 			rechargepojo.setRechargetype(RECHARGTYPE);
 			rechargepojo.setIswhite("true");
 			rechargepojo.setPhonenum(cardphonenum);
 			rechargepojo.setBankName(bankName);
 		    recharge(rechargepojo);
            } else {
 					Toast.makeText(this, "���ٳ�ֵ���Ϊ20Ԫ��", Toast.LENGTH_LONG).show();
 			}
 			} else {
 				Toast.makeText(getBaseContext(), "�ֻ��ų��ȱ���Ϊ11λ��",Toast.LENGTH_LONG).show();
 			}
 		} else{
 			Toast.makeText(this, "����Ϊ�գ�", Toast.LENGTH_LONG).show();
 		}
 	}


 	// ���п�������ֵ(δ��)DNA
 	private void beiginBankCardPhoneNo(View vi,String bankName) {
 		EditText bank_card_phone_recharge_value = (EditText) vi.findViewById(R.id.bank_card_phone_recharge_value);// ���
 		final String value = bank_card_phone_recharge_value.getText().toString();
 		EditText bank_card_phone_bankid = (EditText) vi.findViewById(R.id.bank_card_phone_bankid);// ���п���
 		final String bankid = bank_card_phone_bankid.getText().toString();
 		final String name = bank_card_phone_name.getText().toString();
 		EditText bank_card_phone_idcard = (EditText) vi.findViewById(R.id.bank_card_phone_phone_idcard);// ���֤��
 		final String idcard = bank_card_phone_idcard.getText().toString();
 		EditText bank_card_phone_home = (EditText) vi.findViewById(R.id.bank_card_phone_phone_home);// �������ڵ�
 		final String home = bank_card_phone_home.getText().toString();
 		EditText bank_card_phone_province = (EditText) vi.findViewById(R.id.bank_card_phone_phone_province);// ����ʡ
 		final String province = bank_card_phone_province.getText().toString();
 		final String num = bank_card_phone_phone_num.getText().toString();

 		// ���п�������ֵ��������
 		// ��Ҫ���Ĳ��� 100 ��ֵ��� 106232601047067 ���п��� acceptphonenum �ӵ绰�ֻ���
 		// ui��ȱ�����п���
 		// fqc edit 7/13/2010 �ֻ������߳�ֵ���ı���ʽ ��ֵ���20Ԫ���ϵ�����������20������������ �ֻ����룺����11λ
 		if ((!(num.equals("")) && num != null)
 				&& (!(value.equals("")) && value != null)
 				&& (!(bankid.equals("")) && bankid != null)
 				&& (!(name.equals("")) && name != null)
 				&& (!(idcard.equals("")) && idcard != null)
 				&& (!(home.equals("")) && home != null)
 				&& (!(province.equals("")) && province != null)) {
 			if (num.length() == 11) {
 				if (Integer.parseInt(value) >= 20) {
 					String acceptphonenum = num;
 					strExpand = name + "," + idcard + "," + province
 							+ "," + home + " ," + acceptphonenum + ",false";

 		         	String 		bank_card_id = bankid;
 		         	RechargePojo rechargepojo =new RechargePojo();;
 		         	rechargepojo.setAmount(value);
 		         	rechargepojo.setCardno(bank_card_id);
 		 			rechargepojo.setCardtype("0101");
 		 			rechargepojo.setRechargetype(RECHARGTYPE);
 		 			rechargepojo.setName(name);
 		 	        rechargepojo.setCertid(idcard);
 		 	        rechargepojo.setAddressname(home);
 		 	        rechargepojo.setPhonenum(acceptphonenum);
 		 	        rechargepojo.setBankaddress(province);
 		 	        rechargepojo.setIswhite("false");
 		 	        rechargepojo.setBankName(bankName);
 		 		    recharge(rechargepojo);
 					bank_card_phone_recharge_value.setText("");
 					bank_card_phone_phone_num.setText("");
 					bank_card_phone_bankid.setText("");
 				} else {
 					Toast.makeText(this, "���ٳ�ֵ���Ϊ20Ԫ��", Toast.LENGTH_LONG).show();
 				}
 			} else {
 				Toast.makeText(getBaseContext(), "�ֻ��ų��ȱ���Ϊ11λ��",Toast.LENGTH_LONG).show();
 			}
 		} else{
 			Toast.makeText(this, "����Ϊ�գ�", Toast.LENGTH_LONG).show();
 		}

 	}
 	/**
 	 * ��ʼע�����е绰��
 	 * @param vi
 	 */
 	private void beginRegisterBankPhoneCard(View vi) {

 		RWSharedPreferences shellRW = new RWSharedPreferences(AccountActivity.this, "addInfo");
 		final String phonenum = shellRW.getStringValue("mobileid");
 		final String sessionid = shellRW.getStringValue("sessionid");
 		// �õ���ʵ���������֤�š������������ڵء������������ڵء����п��š����ֻ��� 20100711
 		EditText bank_card_phone_username = (EditText) vi.findViewById(R.id.bank_card_phone_user_name);
 		final String bank_card_phone_username_string = bank_card_phone_username.getText().toString();

 		EditText bank_card_phone_user_idnum = (EditText) vi.findViewById(R.id.bank_card_phone_user_idnum);
 		final String bank_card_phone_user_idnum_string = bank_card_phone_user_idnum.getText().toString();

 		EditText bank_card_phone_open_bank = (EditText) vi.findViewById(R.id.bank_card_phone_open_bank);
 		final String bank_card_phone_open_bank_string = bank_card_phone_open_bank.getText().toString();

 		EditText bank_card_phone_open_bankuser_address = (EditText) vi.findViewById(R.id.bank_card_phone_open_bankuser_address);
 		final String bank_card_phone_open_bankuser_address_string = bank_card_phone_open_bankuser_address
 				.getText().toString();

 		EditText bank_card_phone_bankid = (EditText) vi.findViewById(R.id.bank_card_phone_bankid);
 		final String bank_card_phone_bankid_string = bank_card_phone_bankid.getText().toString();

 		EditText bank_card_phone_recharge_value = (EditText) vi.findViewById(R.id.bank_card_phone_recharge_value);
 		final String bank_card_phone_recharge_value_string = bank_card_phone_recharge_value
 				.getText().toString();

 		EditText bank_card_phone_phone_num = (EditText) vi.findViewById(R.id.bank_card_phone_phone_num);
 		final String bank_card_phone_phone_num_string = bank_card_phone_phone_num.getText().toString();

 	  strExpand = bank_card_phone_username_string + ","
 				+ bank_card_phone_user_idnum_string + ","
 				+ bank_card_phone_open_bank_string + ","
 				+ bank_card_phone_open_bankuser_address_string + ","
 				+ bank_card_phone_phone_num_string + ",false";

 	    String 	bank_card_id = bank_card_phone_bankid_string;
 	        RechargePojo rechargepojo =new RechargePojo();;
 	        rechargepojo.setName(bank_card_phone_username_string);
 	        rechargepojo.setCertid(bank_card_phone_user_idnum_string);
 	        rechargepojo.setCardtype("0101");
 	        rechargepojo.setBankaddress(bank_card_phone_open_bankuser_address_string);
 	        rechargepojo.setPhonenum(bank_card_phone_phone_num_string);
 	        rechargepojo.setAmount(bank_card_phone_recharge_value_string);
			rechargepojo.setCertid(bank_card_id);
			rechargepojo.setRechargetype(RECHARGTYPE);
		    recharge(rechargepojo);
 	}

 	/**
 	 *  �ֻ�����ֵ
 	 * @param view
 	 */
 	private void beginPhoneCardRecharge(View view) {

 		final EditText phone_card_rechargecard_info = (EditText) view.findViewById(R.id.phone_card_rechargecard_info);
 		final String phone_card_rechargecard_info_string = phone_card_rechargecard_info.getText().toString();
 		final EditText phone_card_rechargecard_password = (EditText) view.findViewById(R.id.phone_card_rechargecard_password);
 		final String phone_card_rechargecard_password_string = phone_card_rechargecard_password.getText().toString();
 		// �ֻ���ֵ����ֵ
 		// �������壺0203 ��ֵ������ 10 ��ֵǮ�� 5000��ֵ�ܶ� 10623260104706723 ��ֵ����
 		// 261324590869999653 ��ֵ���� y00003���б�ʶĬ��
 		// uiȱ�ٳ�ֵ��Ǯ��
 		if ((!(phone_card_rechargecard_info_string.equals("")) && phone_card_rechargecard_info_string != null)
 				&& (!(phone_card_rechargecard_password_string.equals("")) && phone_card_rechargecard_password_string != null)) {
 			if (isCardString(phone_card_rechargecard_info_string)) {
 				// ��Ϊ�߳� �³� 200/7/9
 			RechargePojo rechargepojo =new RechargePojo();;
 			  rechargepojo.setRechargetype(RECHARGTYPE);
 			  rechargepojo.setCardtype(phoneCardType);
 			  rechargepojo.setAmount(phoneCardValue);
 			  rechargepojo.setCardno(phone_card_rechargecard_info_string);
 			  rechargepojo.setCardpwd(phone_card_rechargecard_password_string);
 			  recharge(rechargepojo);
 			} else {
 				Toast.makeText(this, "��ֵ�����к�ӦΪ���ֻ���ĸ��", Toast.LENGTH_LONG).show();
 			}
 		} else
 			Toast.makeText(this, "����Ϊ�գ�", Toast.LENGTH_LONG).show();
 	}

 	private void beginGameCardRecharge(View Vi) {

 	final  EditText game_card_number = (EditText) Vi.findViewById(R.id.game_card_number);
 	String	game_card_number_string = game_card_number.getText().toString();
 	final  EditText game_card_password = (EditText) Vi.findViewById(R.id.game_card_password);
 	String	game_card_password_string = game_card_password.getText().toString();
 	final  EditText game_card_total_value = (EditText) Vi.findViewById(R.id.game_card_total_value);
 	String game_card_total_value_string = game_card_total_value.getText().toString();
 		
 		if (game_card_number_string.equals("")|| game_card_password_string.equals("")|| game_card_total_value_string.equals("")) {
 			Message msg = new Message();
 			
 		} else if (isCardString(game_card_number_string)) {
 			RechargePojo rechargepojo =new RechargePojo();;
 			rechargepojo.setRechargetype(RECHARGTYPE);
 			rechargepojo.setCardtype(gameCardType);
 			rechargepojo.setAmount(game_card_total_value_string);
 			rechargepojo.setCardno(game_card_number_string);
 			rechargepojo.setCardpwd(game_card_password_string);
 			recharge(rechargepojo);
 		} else {
 			Toast.makeText(getBaseContext(), "���Ÿ�ʽ���벻��ȷ��", Toast.LENGTH_LONG).show();
 		}
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
	/**
 	 * DNA��ֵ�˻��󶨲�ѯ
 	 */
 	public void  checkDNA() {
 	 
 		RECHARGTYPE = "01";//ΪchenckDNA
 		RWSharedPreferences pre = new RWSharedPreferences(AccountActivity.this, "addInfo");
 		final String sessionId = pre.getStringValue("sessionid");
 		final String mobile = pre.getStringValue("mobileid");
 		phonenum = mobile;
 		final String userno = pre.getStringValue("userno");
 		if(sessionId==null||sessionId.equals("")){
 				Intent intentSession = new Intent(AccountActivity.this,UserLogin.class);
 				startActivityForResult(intentSession,0);
 		}else{
 		showDialog(0);
 		new Thread(new Runnable() {
 			@Override
 			public void run() {
 			String error_code = "00";
 			String message = "";
 				try {
	 				 re = QueryDNAInterface.getInstance().queryDNA(mobile, sessionId, userno);
	 				 JSONObject	obj = new JSONObject(re);
	 				 error_code = obj.getString("error_code");
	 				 message = obj.getString("message");
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 			    if(error_code.equals("0047")){
 			    handler.post(new Runnable() {
					public void run() {
						createBankCardPhoneDialogNo().show();
					}
				});
 			    }else if(error_code.equals("0000")){
 				    handler.post(new Runnable() {
 						public void run() {
 							dialogDNA(re);
 						}
 					});
 			    }else{
 			    	handler.handleMsg(error_code, message);
 			    }
 			   progressdialog.dismiss();
 			}
 		}).start();
 		}
 	}

 	/**
 	 * ����dna�Ի���
 	 * @�汾��
 	 */
 	private String name="";
 	private String certid="";
 	private String bankAddress="";
 	private String certAddress="";
 	private String bankCardNo = "";
 	private String bindPhone = "";
 	private String bankName = "";
 	public void dialogDNA(String str) {
 		String bindState = "";
 		try {
 			JSONObject obj = new JSONObject(str);
 			bindState = obj.getString("bindstate");
 			bankCardNo = obj.getString("bankcardno");
 			name = obj.getString("name");
 			certid =obj.getString("certid");
 			bankAddress = obj.getString("bankaddress");
 			certAddress = obj.getString("addressname");	
 			bindPhone = obj.getString("phonenum");
 			bankName = obj.getString("bankname");
 		} catch (JSONException e) {
 			e.printStackTrace();
 		}
 		if (bindState.equals("1")) {// �Ѿ���
 			createBankCardPhoneDialog().show();
 		}else if(bindState.equals("0")){
 			createBankCardPhoneDialogNo().show();
 		}
 	}
    //��ֵ
    private void recharge(final RechargePojo rechargepojo) {
    	RWSharedPreferences pre = new RWSharedPreferences(AccountActivity.this, "addInfo");
		sessionId = pre.getStringValue("sessionid");
		phonenum = pre.getStringValue("mobileid");
		userno = pre.getStringValue("userno");
		ConnectivityManager ConnMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = ConnMgr.getActiveNetworkInfo();
		if(RECHARGTYPE.equals("05")){
		if(info.getExtraInfo()!=null&&info.getExtraInfo().equalsIgnoreCase("3gwap")){
        	 Toast.makeText(this, "���ѣ���⵽���Ľ����Ϊ3gwap�������޷���ȷ��ֵ,���л���3gnet��", Toast.LENGTH_LONG).show();
		   }
		}

        showDialog(0); 
	    new Thread(new Runnable() {
			
			@Override
			public void run() {
				String error_code = "00";
			    message = "";
				// TODO Auto-generated method stub
				try{
						 rechargepojo.setSessionid(sessionId);
						 rechargepojo.setUserno(userno);
						 String re = RechargeInterface.getInstance().recharge(rechargepojo);
						
						 JSONObject	obj = new JSONObject(re);
						 error_code = obj.getString("error_code");
						 message = obj.getString("message");
						 if(error_code.equals("0000")){
							 if(RECHARGTYPE.equals("05")){
								 url = obj.getString("return_url"); 
							 }else if(RECHARGTYPE.equals("03")){
								 url = obj.getString("reqestUrl");  
							 }else if(RECHARGTYPE.equals("06")){
								 xml = obj.getString("value");  
							 }
						 }
					 }catch(JSONException e){
						e.printStackTrace();
					 }
					 if(error_code.equals("001400")){
						 handler.post(new Runnable() {
							public void run() {
								createBankPhoneCardRegisterDialog().show();	
							}
						 });
					 }else{
						 handler.handleMsg(error_code, message);
					 }
			     progressdialog.dismiss();
			}
		}).start();
		
	}


    
    //��ӳ�ֵ��ʽ����
	private List<Map<String, Object>> setContentForMainList() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(2);
        
		Map<String, Object> map;
		// ֧������ֵ
		map = new HashMap<String, Object>();
		map.put(TITLE, getString(R.string.zhfb_cards_recharge));
		map.put(PICTURE, R.drawable.recharge_alipay);
		map.put(ISHANDINGFREE,getString(R.string.account_zfb_alert));
		list.add(map);
		//����֧��
		map = new HashMap<String, Object>();
		map.put(TITLE, getString(R.string.yin_bank_cards_recharge));
		map.put(PICTURE,R.drawable.recharge_bank);
		map.put(ISHANDINGFREE,getString(R.string.account_yinlian_alert));
		list.add(map);
		// ���п��绰��ֵ
		map = new HashMap<String, Object>();
		map.put(TITLE, getString(R.string.bank_cards_recharge));
		map.put(PICTURE,R.drawable.recharge_phone);
		map.put(ISHANDINGFREE,getString(R.string.account_card_alert));
		list.add(map);
		// �ֻ���ֵ����ֵ
		map = new HashMap<String, Object>();
		map.put(TITLE, getString(R.string.phone_cards_recharge));
		map.put(PICTURE, R.drawable.recharge_phonebank);
		map.put(ISHANDINGFREE,getString(R.string.account_phone_alert));
		list.add(map);
        
		// ����ת��
		map = new HashMap<String, Object>();
		map.put(TITLE, getString(R.string.atm_recharge));
		map.put(PICTURE, R.drawable.recharge_atm);
		map.put(ISHANDINGFREE,getString(R.string.account_zhuanzhang_alert));
		list.add(map);
		

	    return list;
	}
	  
	
    /**
     * ��д�Żؽ�
     */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		   case 4:
			if(isonkey.equals("fasle")){
				this.finish();
			}else{
	        ExitDialogFactory.createExitDialog(this);
			}
           break;
		}
		return false;
	}
	/**
 	 * intent�ص�����
 	 * �û���¼����ֱ�ӵ�����ֵ��
 	 */
 	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 		switch (resultCode) {
 		case RESULT_OK:
// 			Log.e(textString, textString);
 		      if ("�ֻ���ֵ����ֵ".equals(textString)) {
 				  createPhoneRechargeCardDialog().show();
 				} else if ("֧������ֵ(��������)".equals(textString)) {
 				  createAlipayDialog().show(); 
 				} else if ("���п��绰��ֵ(��������)".equals(textString)) {
 				    checkDNA();
 				} else if ("������ֵ(��������)".equals(textString)) {
 		        	createYinpayDialog().show(); 
 		        } else if (getString(R.string.atm_recharge).equals(textString)) {
 		        	Intent intent3 = new Intent(AccountActivity.this, Accoutmovecash.class);
 					startActivity(intent3);	      
 				} 
 			break;
 		}
 	}
 	    
 	//���б�Item����¼�������
    public class AccountMainItemListener implements OnItemClickListener{
	    private Context context;
	    public AccountMainItemListener(Context context){
	    	    this.context = context;
	    }
		@Override
	    public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		    TextView AccountType = (TextView) view.findViewById(R.id.account_recharge_listview_text);
			textString = AccountType.getText().toString();
			 if ("�ֻ���ֵ����ֵ".equals(textString)) {
			  createPhoneRechargeCardDialog().show();
			} else if ("֧������ֵ(��������)".equals(textString)) {
			  createAlipayDialog().show(); 
			} else if ("���п��绰��ֵ(��������)".equals(textString)) {
			  checkDNA();
			} else if ("������ֵ(��������)".equals(textString)) {
	        	createYinpayDialog().show(); 
	        } else if (context.getString(R.string.atm_recharge).equals(textString)) {
	        	Intent intent3 = new Intent(AccountActivity.this, Accoutmovecash.class);
				startActivity(intent3);	        } 
		}
	}
	// �������(����֧��ҳ��)
	public final static String CMD_PAY_PLUGIN = "cmd_pay_plugin";
	// �������(�����û�����ҳ��)
	public final static String CMD_USERS_PLUGIN = "cmd_user_plugin";
	private String MAIN_ACTION = "com.palmdream.RuyicaiAndroid.back.view";
	//�̻�����
	private String MERCHANT_PACKAGE = "com.palmdream.RuyicaiAndroid";
    /**
     * ������ֵ��ת�����
     */
    public void turnYinView(String info){
		// �����ύ3Ҫ�ر���
		// *********************************************************************************//

		byte[] to_upomp = info.getBytes();
		// �˴���"com.gq.lthj.testcase"Ϊ�̻��İ���
		ComponentName com = new ComponentName(MERCHANT_PACKAGE,
				"com.unionpay.upomp.lthj.plugin.ui.MainActivity");
		// ����Intentָ��
		Intent intent = new Intent();
		intent.setComponent(com); // �������(����֧��ҳ��)
		intent.putExtra("action_cmd", CMD_PAY_PLUGIN);
		// �������(�����û�����ҳ��)
		// intent. putExtra("action_cmd", CMD_USERS_PLUGIN);
		// ����װ�õ�xml���Ĵ���bundle
		Bundle mbundle = new Bundle();
		// to_upompΪ�̻��ύ��XML
		mbundle.putByteArray("xml", to_upomp);
		// ע���˴���action�ǣ��̻���action
		mbundle.putString("merchantPackageName", MAIN_ACTION);
		// ��bundle����intent��
		intent.putExtras(mbundle);
		// ʹ��intent��ת���ֻ�POS
		startActivity(intent);
    }
   	    /**
    	 * ����������ʾ��
    	 */
    	protected Dialog onCreateDialog(int id) {
    		switch (id) {
    		case 0: {
    			progressdialog = new ProgressDialog(this);
    			// progressdialog.setTitle("Indeterminate");
    			progressdialog.setMessage("����������...");
    			progressdialog.setIndeterminate(true);
    		}
    			progressdialog.setCancelable(true);
    			return progressdialog;
    		}
    		return null;
    	} 
		@Override
		public void errorCode_0000() {
			// TODO Auto-generated method stub
		if(RECHARGTYPE.equals("05")){//RECHARGTYPE="03"Ϊ�ֻ����г�ֵȷ���Ѿ��ɵ�������ע��
			if(isWebView){
				Intent intent = new Intent(AccountActivity.this,AccountDialog.class);
				intent.putExtra("accounturl", url);
				startActivity(intent);
			}else{
				PublicMethod.openUrlByString(AccountActivity.this, url);
			}
		
		}else if(RECHARGTYPE.equals("06")){
			turnYinView(xml);
		}
		 RechargeType.dismiss();
		 if(!RECHARGTYPE.equals("06")){
		 Toast.makeText(AccountActivity.this,message ,Toast.LENGTH_SHORT).show();
		 }
		}
		@Override
		public void errorCode_000000() {
		}
		@Override
		public Context getContext() {
			// TODO Auto-generated method stub
			return this;
		}
}