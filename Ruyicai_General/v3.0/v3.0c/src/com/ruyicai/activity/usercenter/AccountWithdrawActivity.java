/**
 * 
 */
package com.ruyicai.activity.usercenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.account.AccountActivity;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.join.JoinCheckActivity;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.net.newtransaction.CancelWithdrawInterface;
import com.ruyicai.net.newtransaction.ChangePasswordInterface;
import com.ruyicai.net.newtransaction.ChangeWithdrawInterface;
import com.ruyicai.net.newtransaction.QueryDNAInterface;
import com.ruyicai.net.newtransaction.QueryJoinCheckInterface;
import com.ruyicai.net.newtransaction.QueryLatelyWithdrawInterface;
import com.ruyicai.net.newtransaction.pojo.ChangeWithdrawPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.RWSharedPreferences;

/**
 * �˻�����
 * @author Administrator
 *
 */
public class AccountWithdrawActivity extends Activity implements HandlerMsg{
	private String re;
	private JSONObject obj;	
	private String phonenum,sessionid,userno;
	private boolean start = true;	
	private int state = 0;
	TextView nameText,bankText,addText,numText,moneyText;//�����

	Vector WinningVector;
	String bankName = "00";
	String moneyNum = "00";
	String bankaccount;
	String name;
	String areaName;
	Button b_canceltranking;
	JSONObject json;
	MyHandler handler;//�Զ���handler
	Handler handlerTwo = new Handler();
	private ProgressDialog progressdialog;
	AccountInfo info = new AccountInfo();
	//
    private static final String[] allBankName={"�й���������","��������","�й���������","�й�ũҵ����","��ͨ����","�Ϻ��ֶ���չ����"
    	                                        ,"�㶫��չ����","�й��������","��ҵ����","���ڷ�չ����","�й���������"};   
    String allName[] = null;
    List allcountries=null;   
    ArrayAdapter<String> adapter; 
    boolean isQuery=true;//�Ƿ��ǲ�ѯ����
    String message= "";
    RWSharedPreferences shellRW;
    String drawBStr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		handler = new MyHandler(this);
		shellRW = new RWSharedPreferences(AccountWithdrawActivity.this, "addInfo");
		getInfo();
		stateNet();
	}
	/**
	 * ��ȡ��¼�����Ϣ
	 */
	public void getInfo(){
		 drawBStr = shellRW.getStringValue(Constants.DRAWBALANCE);//�˻����������
		 phonenum = shellRW.getStringValue("phonenum");
		 sessionid = shellRW.getStringValue("sessionid");
		 userno = shellRW.getStringValue("userno");
	}
	/**
	 * ����������Ϣ��
	 */
	public AccountInfo createAccountInfo(JSONObject json){
		AccountInfo info = new AccountInfo();
		try {
			info.setState(json.getString("stat"));
			info.setNameStr(json.getString("name"));
			info.setMoney(json.getString("amount"));
			info.setNum(json.getString("bankcardno"));
			info.setAllbankname(json.getString("allbankname"));
			info.setAdd(json.getString("areaname"));
			info.setBank(json.getString("bankname"));
			info.setCashdetailId(json.getString("cashdetailid"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    return info;
	}
	/**
	 * ��ѯ����״̬
	 */
	public void stateNet(){
		showDialog(0); // ��ʾ������ʾ�� 2010/7/4
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
					str = QueryLatelyWithdrawInterface.queryLatelyWithdraw(userno, sessionid, phonenum);
					try {
						String msg="";
						String error="";
						json=new JSONObject(str);
						msg = json.getString("message");
						error = json.getString("error_code");
						if(error.equals("0")||error.equals("0047")){
							handlerTwo.post(new Runnable() {
								public void run() {
									checkDNA();
								}
							});
							handler.handleMsg(error,msg);
						}else{
							handler.handleMsg(error,msg);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					progressdialog.dismiss();
			}

		});
		t.start();
	}
	/**
	 * �˻���������
	 * 
	 */
	public void accountWithdrawNet(final ChangeWithdrawPojo changeWithdPojo){
		showDialog(0); // ��ʾ������ʾ�� 2010/7/4
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
					str = ChangeWithdrawInterface.changeWithdraw(changeWithdPojo);
					try {
						String msg="";
						String error="";
						if(str.equals("00")){
							error = str;
						}else{
							json=new JSONObject(str);
							message = json.getString("message");
							error = json.getString("error_code");
						}
						handler.handleMsg(error,message);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					progressdialog.dismiss();
			}

		});
		t.start();
	} 
	/**
	 * ȡ����������
	 */
	public void cancelNet(){
		showDialog(0); // ��ʾ������ʾ�� 2010/7/4
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
					str = CancelWithdrawInterface.cancelWithdraw(userno, sessionid, phonenum,info.getCashdetailId());
					try {
						String msg="";
						String error="";
						if(str.equals("00")){
							error = str;
						}else{
							json=new JSONObject(str);
							message = json.getString("message");
							error = json.getString("error_code");
						}
						handler.handleMsg(error,message);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					progressdialog.dismiss();
			}

		});
		t.start();
	}
	/**
 	 * DNA��ֵ�˻��󶨲�ѯ
 	 */
 	public void  checkDNA() {
 		RWSharedPreferences pre = new RWSharedPreferences(AccountWithdrawActivity.this, "addInfo");
 		final String sessionId = pre.getStringValue("sessionid");
 		final String mobile = pre.getStringValue("mobileid");
 		final String userno = pre.getStringValue("userno");
 		if(sessionId==null||sessionId.equals("")){
 				Intent intentSession = new Intent(AccountWithdrawActivity.this,UserLogin.class);
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
						setContentView(getMoneyDialog());
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
 	public void dialogDNA(String str) {
 		String bindState = "";
 		try {
 			JSONObject obj = new JSONObject(str);
 			bindState = obj.getString("bindstate");
 		if (bindState.equals("1")) {// �Ѿ���
 			info.setBindState(obj.getString("bindstate"));
 			info.setNum(obj.getString("bankcardno"));
 			info.setNameStr(obj.getString("name"));
 			info.setAdd(obj.getString("bankaddress"));
 			info.setBankName(obj.getString("bankname"));
 		}
 		} catch (JSONException e) {
 			e.printStackTrace();
 		}
 		setContentView(getMoneyDialog());
 	}
	/**
	 * ���ִ���
	 */
	protected View getMoneyDialog() {
		start = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		View getView = inflater.inflate(R.layout.get_money_submit, null);
		LinearLayout layout1 = (LinearLayout) getView.findViewById(R.id.LinearLayout_one);
		LinearLayout layout2 = (LinearLayout) getView.findViewById(R.id.LinearLayout_two);
		LinearLayout layout3 = (LinearLayout) getView.findViewById(R.id.LinearLayout_third);
		final EditText name = (EditText) getView.findViewById(R.id.get_money_name_edit);
		final Spinner money_brank = (Spinner) getView.findViewById(R.id.get_money_bank_spinner);
		//
	    allcountries=new ArrayList<String>();
	    if(info.getAllbankname()!=null&&info.getAllbankname().length>0){
	    	allName = info.getAllbankname();
	    }else{
	    	allName = allBankName;
	    }
        for(int i=0;i<allName.length;i++){   
            allcountries.add(allName[i]);   
        }   
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,allcountries);   
        adapter.setDropDownViewResource(R.layout.myspinner_dropdown);   
        money_brank.setAdapter(adapter);
        //
		money_brank.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int position = money_brank.getSelectedItemPosition();
				bankName = allName [position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		final TextView drawbalanceText = (TextView) getView.findViewById(R.id.withdraw_drawbalance); 
		if(drawBStr == null||drawBStr == ""||drawBStr == "null"){
			drawBStr = "������ȡʧ��";
		}
		drawbalanceText.setText("�����˻����������Ϊ��"+drawBStr);
		final String drawMoney = drawBStr.substring(0, drawBStr.length()-1);
		final EditText money_bank_start = (EditText) getView.findViewById(R.id.get_money_bank_start_edit);
		final EditText money_bank_num = (EditText) getView.findViewById(R.id.get_money_bank_num_edit);
		final EditText money = (EditText) getView.findViewById(R.id.get_money_money_edit);
        Button submit = (Button) getView.findViewById(R.id.get_money_img_submit);
        submit.setBackgroundResource(R.drawable.join_info_btn_selecter);
        Button cancel = (Button) getView.findViewById(R.id.get_money_img_back);
        cancel.setBackgroundResource(R.drawable.join_info_btn_selecter);
        submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				ChangeWithdrawPojo changeWithdPojo = new ChangeWithdrawPojo();
				String nameStr = name.getText().toString();
				String moneyStr = money.getText().toString();
				String bankNameStr = bankName;
				String bankAreaStr = money_bank_start.getText().toString();
				String bankNoStr  = money_bank_num.getText().toString();
				changeWithdPojo.setUserno(userno);
				changeWithdPojo.setPhonenum(phonenum);
				changeWithdPojo.setSessionid(sessionid);
				if(info.getBankName().equals("")||info.getBankName().equals("null")){
					changeWithdPojo.setBankname(bankNameStr);
				}else{
					changeWithdPojo.setBankname(info.getBankName());
				}
				if( nameStr.equals("")){
					Toast.makeText(AccountWithdrawActivity.this, "�ֿ���������Ϊ�գ�", Toast.LENGTH_SHORT).show();
				}else if( bankAreaStr.equals("")){
					Toast.makeText(AccountWithdrawActivity.this, "������ַ����Ϊ�գ�", Toast.LENGTH_SHORT).show();
				}else if(bankNoStr.equals("")){
					Toast.makeText(AccountWithdrawActivity.this, "���п��Ų���Ϊ�գ�", Toast.LENGTH_SHORT).show();
				}else if(moneyStr.equals("")){
					Toast.makeText(AccountWithdrawActivity.this, "���ֽ���Ϊ�գ�", Toast.LENGTH_SHORT).show();
				}else if(Integer.parseInt(moneyStr)<1){
					Toast.makeText(AccountWithdrawActivity.this, "��������һԪ��", Toast.LENGTH_SHORT).show();
				}else if(Float.parseFloat(moneyStr)>Float.parseFloat(drawMoney)){
					Toast.makeText(AccountWithdrawActivity.this, "�����ֵĽ��������Ŀ�������", Toast.LENGTH_SHORT).show();
				}else{
					changeWithdPojo.setName(nameStr);
					changeWithdPojo.setAmount(PublicMethod.toFen(moneyStr));
					changeWithdPojo.setAraeaname(bankAreaStr);
					changeWithdPojo.setBankcardno(bankNoStr);
					accountWithdrawNet(changeWithdPojo);
					
				}
			}
		});
        cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
        name.append(info.getNameStr());
        money_bank_start.append(info.getAdd());
        money_bank_num.append(info.getNum());
        if(info.getBindState().equals("1")){//dna�Ƿ��
        	layout1.setVisibility(LinearLayout.GONE);
        	layout3.setVisibility(LinearLayout.GONE);
        	if(info.getBankName().equals("")||info.getBankName().equals("null")){
        		layout2.setVisibility(LinearLayout.VISIBLE);
        	}else{
        		layout2.setVisibility(LinearLayout.GONE);
        	}
        	money_bank_num.setEnabled(false);
        }
		return getView;
	}


	/**
	 * ���ִ���˴���
	 */
	protected View waitDialog() {
		start = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		View waitView = inflater.inflate(R.layout.get_money_stating, null);
        nameText = (TextView) waitView.findViewById(R.id.get_money_name);
        bankText = (TextView) waitView.findViewById(R.id.get_money_bank);
        addText = (TextView) waitView.findViewById(R.id.get_money_bank_address);
        numText = (TextView) waitView.findViewById(R.id.get_money_bank_num);
        moneyText = (TextView) waitView.findViewById(R.id.get_money_money);
        Button submit = (Button) waitView.findViewById(R.id.get_money_img_submit);
        submit.setBackgroundResource(R.drawable.join_info_btn_selecter);
        Button cancel = (Button) waitView.findViewById(R.id.get_money_img_cancel);
        cancel.setBackgroundResource(R.drawable.join_info_btn_selecter);
        Button exit = (Button) waitView.findViewById(R.id.get_money_img_exit);
        exit.setBackgroundResource(R.drawable.join_info_btn_selecter);
        submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				setContentView(getMoneyDialog());
				checkDNA();
			}
		});
        cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				cancelNet();
			}
		});
        exit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			 finish();	
			}
		});
        nameText.append(info.getNameStr());
        bankText.append(info.getBank());
        addText.append(info.getAdd());
        numText.append(info.getNum());
        moneyText.append(info.getMoney());
		return waitView;
	}
	/**
	 * ��������д���
	 */
	protected View waitingDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View waitingView = inflater.inflate(R.layout.get_money_state, null);
		TextView content = (TextView) waitingView.findViewById(R.id.get_money_state_text);
		content.setText(R.string.waiting_text);
        Button submit = (Button) waitingView.findViewById(R.id.get_money_img_submit);
        submit.setVisibility(ImageButton.GONE);
        Button cancel = (Button) waitingView.findViewById(R.id.get_money_img_cancel);
        cancel.setBackgroundResource(R.drawable.join_info_btn_selecter);
        cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				 finish();		
			}
		});
		return waitingView;
	}
	/**
	 * ����ʧ�ܴ���
	 */
	protected View failDialog() {
		start = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		View failView = inflater.inflate(R.layout.get_money_state, null);
		TextView content = (TextView) failView.findViewById(R.id.get_money_state_text);
		content.setText(R.string.fail_text);
        Button submit = (Button) failView.findViewById(R.id.get_money_img_submit);
        submit.setText(R.string.usercenter_withdrawAnew);
        submit.setBackgroundResource(R.drawable.join_info_btn_selecter);
        Button cancel = (Button) failView.findViewById(R.id.get_money_img_cancel);
        cancel.setBackgroundResource(R.drawable.join_info_btn_selecter);
        submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
//				setContentView(getMoneyDialog());
				checkDNA();
			}
		});
        cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		return failView;
	}
	/**
	 * ��ͨ����˴���
	 */
	protected View successDialog() {
		start = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		View successView = inflater.inflate(R.layout.get_money_state, null);
		TextView content = (TextView) successView.findViewById(R.id.get_money_state_text);
		content.setText(R.string.success_text);
        Button submit = (Button) successView.findViewById(R.id.get_money_img_submit);
        submit.setText(R.string.usercenter_withdrawAnew);
        submit.setBackgroundResource(R.drawable.join_info_btn_selecter);
        Button cancel = (Button) successView.findViewById(R.id.get_money_img_cancel);
        cancel.setBackgroundResource(R.drawable.join_info_btn_selecter);
        submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				setContentView(getMoneyDialog());	
				checkDNA();
			}
		});
        cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();		
			}
		});
		return successView;
	}
	/**
	 *  �������ӿ�
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			progressdialog = new ProgressDialog(this);
			progressdialog.setTitle(R.string.usercenter_netDialogTitle);
			progressdialog.setMessage(getString(R.string.usercenter_netDialogRemind));
			progressdialog.setIndeterminate(true);
			return progressdialog;
		}
		}
		return null;
	}

	public void errorCode_0000() {
		if(isQuery){
			isQuery = false;
			info = createAccountInfo(json);
			state = Integer.parseInt(info.getState());
			switch (state) {
			case 0:// ���ִ���
//				setContentView(getMoneyDialog());
				checkDNA();
				break;
			case 31:// �޸����ִ���
//				changeDialog();
				break;
			case 1:// �����
				setContentView(waitDialog());
				break;
			case 102:// �����
				setContentView(waitingDialog());
				break;
			case 103:// �����
				setContentView(waitingDialog());
				break;
			case 104:// ����
				setContentView(failDialog());
				break;
			case 106:// ����
				setContentView(failDialog());
				break;
			case 105:// ��ͨ�����
				setContentView(successDialog());
				break;
			}	
		}else{
			Toast.makeText(AccountWithdrawActivity.this,message, Toast.LENGTH_SHORT).show();
			finish();
		}

	}

	public void errorCode_000000() {
	}

	public Context getContext() {
		return this;
	}
	/**
	 * ������Ϣ��
	 * @author Administrator
	 *
	 */
	class AccountInfo{
		String state="0",nameStr = "",bank = "",add ="",num = "",money ="0",cashdetailId;
		private String bindState ="";//1�ǰ󶨣�0��δ��dna
		private String bankName = "";
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public String getBindState() {
			return bindState;
		}
		public void setBindState(String bindState) {
			this.bindState = bindState;
		}
		public String getCashdetailId() {
			return cashdetailId;
		}
		public void setCashdetailId(String cashdetailId) {
			this.cashdetailId = cashdetailId;
		}
		String allbankname[] = null;
		public AccountInfo(){
			
		}
		public void setState(String state){
			this.state = state;
		}
		public String getState(){
			return state;
		}
		public void setNameStr(String nameStr) {
			this.nameStr = nameStr;
		}

		public void setBank(String bank) {
			this.bank = bank;
		}

		public void setAdd(String add) {
			this.add = add;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public void setMoney(String money) {
			this.money = money;
		}

		public String getNameStr() {
			return nameStr;
		}

		public String getBank() {
			return bank;
		}

		public String getAdd() {
			return add;
		}

		public String getNum() {
			return num;
		}

		public String[] getAllbankname() {
		    return allbankname;
		}
		public void setAllbankname(String allbankname) {
			 
			this.allbankname = allbankname.split(",");
		}
		public String getMoney() {
			return PublicMethod.formatMoney(money);
		}
	}
}
