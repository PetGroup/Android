/**
 * Copyright 2010 PalmDream
 * All right reserved.
 * Development History:
 * Date             Author          Version            Modify
 * 2010-5-18        fqc              1.5                none
 */

package com.ruyicai.activity.common;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.constant.Constants;
import com.ruyicai.constant.ShellRWConstants;
import com.ruyicai.net.newtransaction.LoginInterface;
import com.ruyicai.net.newtransaction.RegisterInterface;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.RWSharedPreferences;

public class UserLogin extends Activity implements TextWatcher {
	public static final String SUCCESS = "loginsuccess";
	public static final String UNSUCCESS = "unloginsuccess";
    
	private static final int DIALOG_FORGET_PASSWORD = 1;
	private static final int DIALOG_PROTOCOL = 2;
	// cc 20100711 ������ʾ��
	private static final int PROGRESS_VALUE = 0;
	ProgressDialog progressDialog;
	private RWSharedPreferences shellRW;
	private CheckBox remPwd_checkBox,auto_login_checkBox;//�Զ���¼�ͼ�ס�����checkbox
	private EditText phoneNum_edit;
	private EditText password_edit;
	boolean b = false;//�û���¼�ɹ� b = true���û���¼ʧ�� b = false�����û���¼�ɹ���ʱ�򷢳���¼�ɹ��Ĺ㲥 mainGroup��������㲥��ʼ��ͷ��Ϣ������initTop������
	boolean isConfigChange = false;
	public int configFlag;// 0��ʾ��¼��1��ʾע��
	int age;
	String phonenum;
	String password;
	boolean on = false;
	boolean turn = true;
	String realName;
	Animation shake =  null;
	private String message,mobileid,name;
	private String randomNumber;//�Զ���¼�󷵻ص������
	Boolean isProtocol = true;
	String isBindPhone = "1";
	private String isAutoLogin = "0";
	private boolean autologin = false;
	boolean ischeckId = false;
	/**
	 * �����¼����Ϣ��ע�����Ϣ
	 */
	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
				regToLogin(phonenum,password,"0");
				break;
			case 2:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
				break;
			case 3:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), message ,Toast.LENGTH_LONG).show();
				break;
			case 4:
				Toast.makeText(getBaseContext(), message,Toast.LENGTH_LONG).show();
				break;
			case 5:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG) .show();
				break;
			case 6:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
				break;
			case 7:
				progressDialog.dismiss();
				password_edit.startAnimation(shake);
				Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG) .show();
				break;
			case 8:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG).show();
				break;
			case 9:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
				break;
			case 10:
				progressDialog.dismiss();
				Toast.makeText(getBaseContext(), message ,Toast.LENGTH_LONG).show();
				if (b == true) {
					Intent intent = new Intent(SUCCESS);
					sendBroadcast(intent);
					PublicConst.islogin=true;
					UserLogin.this.setResult(RESULT_OK);
					UserLogin.this.finish();
				}
				break;
			}
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_FORGET_PASSWORD:
			LayoutInflater factory = LayoutInflater.from(this);
			final View forgetPwd = factory.inflate(R.layout.alert_dialog_forget_password, null);
			return new AlertDialog.Builder(UserLogin.this).setIcon(
					R.drawable.star_big_on).setTitle(R.string.forget_password)
					.setView(forgetPwd).setPositiveButton(R.string.giveCall,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int whichButton) {
									Intent myIntentDial = new Intent("android.intent.action.CALL", Uri.parse("tel:4006651000"));
									startActivity(myIntentDial);
								}
							}).setNegativeButton(R.string.return_button,new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).create();
		case DIALOG_PROTOCOL:
			return new AlertDialog.Builder(UserLogin.this).setTitle(R.string.login_string_protocol)
					.setView(getView()).setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int whichButton) {
		
								}
							}).setNegativeButton(R.string.xitongshezhi_check_off,new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).create();
			// ����������ʾ�� 2010/7/11 �³�
		case PROGRESS_VALUE: {
			 
			progressDialog = new ProgressDialog(this);
			progressDialog.setTitle("��ʾ");
			progressDialog.setMessage("����������...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			return progressDialog;
		}
			// }

		}
		return null;
	}
   public View getView(){
		LayoutInflater factoryProtocol = LayoutInflater.from(this);
		final View view = factoryProtocol.inflate(R.layout.user_login_protocol_dialog, null);
		WebView webView = (WebView) view.findViewById(R.id.ruyipackage_webview);
	    String iFileName = "login_protocol.html";
		String url = "file:///android_asset/" + iFileName;
		webView.loadUrl(url);
	   return view;
   }
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		isConfigChange = true;
		if (configFlag == 0) {
			turnToLogin();
		} else if (configFlag == 1) {
			turnToReg();
		}
		isConfigChange = false;
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * Initialization of the Activity after it is first created. Must at least
	 * call {@link android.app.Activity#setContentView(int)} to describe what is
	 * to be displayed in the screen.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			on = bundle.getBoolean("switch");// ��������
		}
		if (on) {
			turnToReg();
		} else {
			turnToLogin();
		}

	}

	/**
	 * ��¼�Ի���
	 */
	private void turnToLogin() {
		turn = false;
		configFlag = 0;
		setContentView(R.layout.alert_dialog_login_entry);
		Timer timer = new Timer();
        timer.schedule(new TimerTask() {
           @Override
                public void run() {
                        InputMethodManager imm = (InputMethodManager)UserLogin.this.getSystemService(INPUT_METHOD_SERVICE); 
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
                        
                     }

              }, 500);  //��һ����
		
        TextView title=(TextView) findViewById(R.id.alert_login_title);
		Button login = (Button) findViewById(R.id.login_button);
		shellRW = new RWSharedPreferences(this, "addInfo");
		
		autologin = shellRW.getBooleanValue(ShellRWConstants.AUTO_LOGIN);
		// ��ס�绰���� ���ֻ��� ���Ƿ��ס�����״̬
		remPwd_checkBox = (CheckBox) findViewById(R.id.remember_password_checkBox);
		auto_login_checkBox = (CheckBox)findViewById(R.id.auto_login_checkBox);
		auto_login_checkBox.setChecked(autologin);
		phoneNum_edit = (EditText) findViewById(R.id.phoneNum_edit);
		phoneNum_edit.setFocusable(true);
		phoneNum_edit.requestFocus();
		password_edit = (EditText) findViewById(R.id.password_edit);
		// fyj edit �߼����� 20100709 �ж� checkbox��¼����Ϣ�Ƿ����
		// fqc eidt ��ס�������ڵ���˼�ס����ĸ�ѡ�� �� �����¼ ʱ���뵽preference password��ֵ��
		// fqc edit ����Ļ�л���ʱ��������뵽preference�е�passwordConfig����ʱ ��ס����
		String iTempCheck = shellRW.getStringValue("remPwd_checkBox");
		String iTempPass = shellRW.getStringValue("password");
		title.setText("�û���¼");
		login.setText(getResources().getString(R.string.login));
		if (isConfigChange == true) {
			if (shellRW.getStringValue("passwordConfig") != null){
				password_edit.setText(shellRW.getStringValue("passwordConfig"));
			}else{
				password_edit.setText("");
			}
			if (shellRW.getStringValue("remPwd_checkBox") != null&&shellRW.getStringValue("remPwd_checkBox") == "true") {
				remPwd_checkBox.setChecked(true);
			} else{
				remPwd_checkBox.setText("");
			}
		} else if (iTempCheck != null && iTempPass != null) {
			if (iTempCheck.equalsIgnoreCase("true")) {
				password_edit.setText(iTempPass);
				remPwd_checkBox.setChecked(true);
			} else {
				password_edit.setText("");
			}
		} else {
			password_edit.setText("");
		}
		if (shellRW.getStringValue("phoneNumber") != null) {
			phoneNum_edit.setText(shellRW.getStringValue("phoneNumber"));
		}

		phoneNum_edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				String phoneNumber = phoneNum_edit.getText().toString();
				shellRW.putStringValue("phoneNumber", phoneNumber);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}
		});
		password_edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				String password = password_edit.getText().toString();
				shellRW.putStringValue("passwordConfig", password);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			} 

		});   
		initLoginCheckBox();//��ʼ�� ��ס���� �� �Զ���¼��
		TextView forget_password = (TextView) findViewById(R.id.remember_password_view);
		forget_password.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserLogin.this, ForgetPasswordActivity.class);
				startActivity(intent);
			}
		});
		/* ������ֻ����ı����ʱ�������������� */
		final EditText phone_name_Text = (EditText) findViewById(R.id.phoneNum_edit);
		login.setBackgroundResource(R.drawable.loginselecter);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager)UserLogin.this.getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(phoneNum_edit.getWindowToken(), 0);
				beginLogin();
			}
		});

		// ���ע�ᰴťʱ����ת��ע��ҳ��
		Button register = (Button) findViewById(R.id.register_button);
		register.setBackgroundResource(R.drawable.loginselecter);
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				turnToReg();
			}
		});
		
		Button login_return = (Button) findViewById(R.id.usercenter_btn_return);
		login_return.setBackgroundResource(R.drawable.returnselecter);
		login_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager)UserLogin.this.getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(phoneNum_edit.getWindowToken(), 0);
				
				UserLogin.this.setResult(0); // δ��¼
				UserLogin.this.finish();
			}

		});
	}
	/**
	 * ��ʼ���û���½��CheckBox
	 */
	private void initLoginCheckBox(){
		remPwd_checkBox.setButtonDrawable(R.drawable.check_select);
		auto_login_checkBox.setButtonDrawable(R.drawable.check_select);
		
		auto_login_checkBox.setChecked(shellRW.getBooleanValue(ShellRWConstants.AUTO_LOGIN));
		auto_login_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				auto_login(isChecked);
			}
		});
		// ʵ�ּ�ס���� �� ��ѡ���״̬
		remPwd_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						rem_password_num(isChecked);
					}
				});
//		CheckBox password_checkBox = (CheckBox) findViewById(R.id.user_login_check_password);
//		password_checkBox.setButtonDrawable(R.drawable.check_select);
//		// ʵ�ּ�ס���� �� ��ѡ���״̬
//		password_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//						if(isChecked){
//							password_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//						}else{
//							password_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
//						}
//					}
//				});
	}
	/**
	 * ��¼��������
	 */

	private void beginLogin() {
		// showDialog(DIALOG_PROGRESS);
		EditText password_edit = (EditText) findViewById(R.id.password_edit);
		Editable password = password_edit.getText();
		final String password_string = String.valueOf(password);
		if (remPwd_checkBox.isChecked())
			shellRW.putStringValue("password", password_string);
		// �ж��ֻ��ų����Ƿ���ȷ
		EditText phone_name_Text = (EditText) findViewById(R.id.phoneNum_edit);
		Editable editText = phone_name_Text.getText();
		// fqc edit 7/3/2010 �ж����볤�� ������6~16֮��
		EditText password_text = (EditText) findViewById(R.id.password_edit);
		int passwordLength = password_text.getText().toString().length();
	    if(editText.toString().equals("")){
	    	Toast.makeText(this, "�û�������Ϊ�գ�", Toast.LENGTH_LONG).show();
	    }else if (passwordLength < 6 || passwordLength > 16) {
			password_edit.startAnimation(shake);
			Toast.makeText(this, "�������Ϊ6~16λ��", Toast.LENGTH_LONG).show();
		} else {//
//			if(auto_login_checkBox.isChecked()){
//				isAutoLogin = "1";
//			}else{
//				isAutoLogin = "0";
//			}
			regToLogin(String.valueOf(phoneNum_edit.getText()), password_string,isAutoLogin);
		}
	}

	/**
	 * ע��ɹ���ֱ�ӵ�¼
	 * 
	 */
	private void regToLogin(final String phonenum,final String  password,final String isAutoLogin) {
		showDialog(PROGRESS_VALUE);
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				String error_code="0";
				try {
				String str = LoginInterface.login(phonenum, password,isAutoLogin);
				JSONObject json = new JSONObject(str);
				error_code = json.getString("error_code");
				message = json.getString("message");
				if (error_code.equals("0000")) {
					shellRW = new RWSharedPreferences(UserLogin.this,"addInfo");
					mobileid = json.getString("mobileid");
					name = json.getString("name");
					if(isAutoLogin.equals("1")){
						randomNumber = json.getString("randomNumber");
						shellRW.putStringValue(ShellRWConstants.RANDOMNUMBER, randomNumber);
					}else{
						shellRW.putBooleanValue(ShellRWConstants.AUTO_LOGIN, false);
						shellRW.putStringValue(ShellRWConstants.RANDOMNUMBER, "");
					}
					String sessionid = json.getString("sessionid");
					String userno = json.getString("userno");
					String cerdid = json.getString("certid");
					String username = json.getString("userName");
					
					shellRW.putStringValue("sessionid", sessionid);
					shellRW.putStringValue("name", name);
					shellRW.putStringValue("userno", userno);
					shellRW.putStringValue("phonenum", phonenum);
					shellRW.putStringValue("username", username);//�û���
					shellRW.putStringValue("mobileid", mobileid);//��ShellRWSharesPreferences��д����ֻ���
					shellRW.putStringValue("certid", cerdid);//��ShellRWSharesPreferences��д�����֤��
					b = true;
					Message msg = new Message();
					msg.what = 10;
					handler.sendMessage(msg);
				} else if (error_code.equals("070002")) {
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);
				} else if (error_code.equals("4444")) {
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				} else if (error_code.equals("0")) {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);

				} else {
					Message msg = new Message();
					msg.what = 9;
					handler.sendMessage(msg);
				}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					progressDialog.dismiss();
				}
			}

		});
		t.start();

	}
	/**
	 * ע���
	 */
	private void turnToReg() {
		
		configFlag = 1;
		setContentView(R.layout.user_register);
		
		Button register_button = (Button) findViewById(R.id.register_button_);
		final EditText phoneNumEdit = (EditText) findViewById(R.id.register_username_edit);
		final EditText passwordEdit = (EditText) findViewById(R.id.register_password_edit);
		final EditText confirmPasswordEdit = (EditText) findViewById(R.id.register_confirm_password_edit);
		final EditText cardIdEdit = (EditText) findViewById(R.id.register_id_num_edit);
		final EditText nameEdit = (EditText) findViewById(R.id.register_name_edit);
		final LinearLayout layoutId = (LinearLayout) findViewById(R.id.user_register_linear_card_id);
		//���ֻ��Ÿ�ѡ��
		CheckBox check = (CheckBox) findViewById(R.id.user_register_check);
	    check.setButtonDrawable(R.drawable.check_select);
	    check.setChecked(true);
	    check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		           if(isChecked){
	                	  isBindPhone = "1";//1�ǰ󶨣�0�ǲ���
	                  }else{
	                	  isBindPhone = "0";
	                  }
			}
		});
	    //����Э�鸴ѡ��
		CheckBox checkProtocol = (CheckBox) findViewById(R.id.user_register_check_protocol);
		checkProtocol.setButtonDrawable(R.drawable.check_select);
		checkProtocol.setChecked(true);
		checkProtocol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				isProtocol = isChecked; 
			}
		});
		 //�����֤
		CheckBox checkId = (CheckBox) findViewById(R.id.user_register_check_card_id);
		checkId.setButtonDrawable(R.drawable.check_select);
		checkId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				   ischeckId = isChecked;
                   if(isChecked){
                	   layoutId.setVisibility(LinearLayout.VISIBLE);
                   }else{
                	   cardIdEdit.setText("");
                	   nameEdit.setText("");
                	   layoutId.setVisibility(LinearLayout.GONE);
                   }
			}
		});
		//����Э��
		TextView textProtocol = (TextView) findViewById(R.id.user_register_text_protocol);
		textProtocol.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DIALOG_PROTOCOL);
			}
		});
		phoneNumEdit.setFocusable(true);
		InputMethodManager imm = (InputMethodManager)UserLogin.this.getSystemService(INPUT_METHOD_SERVICE);
		imm.showSoftInput(phoneNumEdit, 0);
		phoneNumEdit.requestFocus();
		register_button.setBackgroundResource(R.drawable.loginselecter);
        register_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �����ж�ע��ʱ��������������Ƿ�һ�� ��Ȼ���ٽ���ע��
				String phoneNum = phoneNumEdit.getText().toString();
				String password = passwordEdit.getText().toString();
				String confirmPassword = confirmPasswordEdit.getText().toString();
				int passwordLength = password.length();
				String cardId = cardIdEdit.getText().toString();
				realName = nameEdit.getText().toString();
				if (phoneNum.length() != 11) {
					Toast.makeText(getBaseContext(), "�ֻ��ű���Ϊ11λ��",Toast.LENGTH_LONG).show();
				}else if (passwordLength < 6 || passwordLength > 16) {
					Toast.makeText(getBaseContext(), "�������Ϊ6~16λ��",Toast.LENGTH_LONG).show();
				} else if (!password.equals(confirmPassword)) {
					Toast.makeText(getBaseContext(), "�����������벻ͬ��",Toast.LENGTH_LONG).show();
				}else if(!isProtocol){
					Toast.makeText(getBaseContext(), "�빴ѡ����Э����ע�ᣡ",Toast.LENGTH_LONG).show();
				}else if(ischeckId){
					if(cardId.equals("")||cardId == null){
						Toast.makeText(getBaseContext(), "���֤����Ϊ�գ�",Toast.LENGTH_LONG).show();
					}else if(!isCardId(cardId)){
						Toast.makeText(getBaseContext(), "����������֤����ȷ�����������룡",Toast.LENGTH_LONG).show();
					}else if(realName.equals("")||realName == null){
						Toast.makeText(getBaseContext(), "��ʵ��������Ϊ�գ�",Toast.LENGTH_LONG).show();
					}else{
						beginRegister(phoneNum,password,cardId,realName,isBindPhone);
					}
				}else{
					beginRegister(phoneNum,password,cardId,realName,isBindPhone);
				}
			}
		});
		
		Button login_return = (Button) findViewById(R.id.usercenter_btn_return);
		login_return.setBackgroundResource(R.drawable.returnselecter);
		login_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (turn) {
					finish();
				} else {
					turnToLogin();
				}

			}

		});

	}
	/**
	 * �ж��Ƿ������֤
	 * @return
	 */
	public boolean isCardId(String cardId){
		boolean isRight = true;
		int cardIdLength = cardId.length();
		if (cardIdLength != 18&& cardIdLength != 15){
			isRight = false;
		}else{
			String cardIdSubstring = cardId.substring(0, cardIdLength - 2);
			for (int i = 0; i < cardIdLength - 2; i++) {
				if (cardId.charAt(i) < '0' || cardId.charAt(i) > '9')
					isRight = false;
			}
			if (cardId.charAt(cardIdLength - 1) != 'x'&& cardId.charAt(cardIdLength - 1) != 'X'
				&& (cardId.charAt(cardIdLength - 1) > '9' || cardId.charAt(cardIdLength - 1) < '0')) {
					isRight = false;
				}
			
		}
		
		return isRight;
	}
	/**
	 * �������֤�ж��Ƿ���ʮ����
	 */
    public boolean isYear(String cardId){
    	boolean isRight = true;
    	int cardIdLength = cardId.length();
		// �ж��Ƿ���18
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		if (cardIdLength == 15) {
			age = Integer.parseInt("19"+ cardId.substring(6, 8));
		} else if (cardIdLength == 18) {
			age = Integer.parseInt(cardId.substring(6, 10));
		}
		if (year - age < 18) {
			isRight = false;
		}
		return isRight;
    }
	/**
	 *  ��ѡ�м�ס�û�����İ�ťʱ
	 * @param isChecked
	 */
	private void rem_password_num(boolean isChecked) {
		EditText password_edit = (EditText) findViewById(R.id.password_edit);
		String password_string = password_edit.getText().toString();
		if (isChecked) {
			shellRW.putStringValue("remPwd_checkBox", "true");
			shellRW.putStringValue("password", password_string);
		} else{
			shellRW.putStringValue("remPwd_checkBox", "false");
			shellRW.putStringValue("password", "");
		}
	}
	private void auto_login(boolean isChecked){
		EditText password_edit = (EditText) findViewById(R.id.password_edit);
		String password_string = password_edit.getText().toString();
		if (isChecked) {
			shellRW.putStringValue("remPwd_checkBox", "true");
			shellRW.putBooleanValue(ShellRWConstants.AUTO_LOGIN, true);
			isAutoLogin = "1";
			shellRW.putStringValue("password", password_string);
		} else{
			isAutoLogin = "0";
			shellRW.putBooleanValue(ShellRWConstants.AUTO_LOGIN, false);
		}
	}

	// ���ע�ᣬ���ע��
	// ������ע��ʱ���õĴ���
	// TODO Auto-generated method stub
	/**
	 * ע����������
	 */
	private void beginRegister(final String phoneNum,final String partPassword,final String cardId,final String name,final String isBindPhone) {
		showDialog(PROGRESS_VALUE);
		Thread regthread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// try{
				
				String re = RegisterInterface.getInstance().userregister(phoneNum, partPassword, cardId, name,isBindPhone);
				JSONObject obj;
				String error_code = "0";
				try {
					obj = new JSONObject(re);
					error_code = obj.getString("error_code");
					message = obj.getString("message");
				} catch (JSONException e) {
					e.printStackTrace();
				}
	
				if (error_code.equals("0000")) {
					shellRW.putStringValue("name", realName);
					phonenum = phoneNum;
					password = partPassword;
					shellRW.putStringValue("phonenum", phonenum);
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				} else if (error_code.equals("0013")) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				} else if (error_code.equals("4444")) {
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				} else if (error_code.equals("000012")) {
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);
				} else if (error_code.equals("0")) {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.what = 5;
					handler.sendMessage(msg);
				}
			}

		});
		regthread.start();

	}

	/**
	 * ʵ�ֵ绰����ļ��书��
	 */
	@Override
	protected void onResume() {
		super.onResume();

		SharedPreferences prefs = getPreferences(0);
		String restoredText = prefs.getString("phoneNumText", null);
		if (restoredText != null && !on) {
			phoneNum_edit.setText(restoredText, TextView.BufferType.EDITABLE);

			int selectionStart = prefs.getInt("selection-start", -1);
			int selectionEnd = prefs.getInt("selection-end", -1);
			if (selectionStart != -1 && selectionEnd != -1) {
				phoneNum_edit.setSelection(selectionStart, selectionEnd);
			}
		}
	}

	/**
	 * Any time we are paused we need to save away the current state, so it will
	 * be restored correctly when we are resumed.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (!on) {
			SharedPreferences.Editor editor = getPreferences(0).edit();
			editor.putString("phoneNumText", phoneNum_edit.getText().toString());
			editor.putInt("selection-start", phoneNum_edit.getSelectionStart());
			editor.putInt("selection-end", phoneNum_edit.getSelectionEnd());
			editor.commit();
		}
	}


	@Override
	public void afterTextChanged(Editable s) {
		String phoneNumber = phoneNum_edit.getText().toString();
		shellRW.putStringValue("phoneNumber", phoneNumber);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	// ��ס�ֻ���
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}
    public static void main(String[] args) {
		String str = "abcd";
		System.out.println(str.indexOf("bc"));
		
	}
}
