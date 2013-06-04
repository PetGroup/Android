/**
 * 
 */
package com.ruyicai.activity.join;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.text.NumberFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.net.newtransaction.JoinStartInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicMethod;

/**
 * @author Administrator
 *
 */
public class JoinStartActivity extends Activity implements HandlerMsg,OnCheckedChangeListener{
	
	private BetAndGiftPojo betAndGift;// Ͷע��Ϣ��
	private TextView titleText;
	private TextView atmText;
	private TextView zhuText;
	private TextView beiText;
	private TextView renText;
	private TextView baoText;
	private EditText buyEdit;
	private EditText minEdit;
	private EditText safeEdit;
	private EditText descriptionEdit;
	private Spinner deductSpinner;
	private String baoTitle[] = { "��", "��" };
	private String openTitle[] = { "����", "�������˹���","�Ը�������������","�������˽�ֹ�󹫿�","�Ը����߽�ֹ�󹫿�" };
	private RadioGroup baoRadioGroup;
	private RadioGroup openRadioGroup;
	private int allAtm;
	private int beishu;
	private int zhushu;
	private String commisionRation="1";
	private String visible = "0";
	ProgressDialog progressdialog;
	String message;
	JSONObject obj;
	MyHandler handler = new MyHandler(this);// �Զ���handler
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.join_start);
		getInfo();
		init();
		initRadioGroup();

	}
	public void init(){
		titleText = (TextView) findViewById(R.id.layout_join_text_title);
	    atmText = (TextView) findViewById(R.id.layout_join_text_all_atm);
		zhuText = (TextView) findViewById(R.id.layout_join_text_zhushu);
		beiText = (TextView) findViewById(R.id.layout_join_text_beishu);
		
	    renText = (TextView) findViewById(R.id.layout_join_text_rengou);
		baoText = (TextView) findViewById(R.id.layout_join_text_baodi);
		buyEdit = (EditText) findViewById(R.id.layout_join_edit_rengou);
		minEdit = (EditText) findViewById(R.id.layout_join_edit_gendan);
		safeEdit = (EditText) findViewById(R.id.layout_join_edit_baodi);
		descriptionEdit = (EditText) findViewById(R.id.layout_join_edit_description);
		
		buyEdit.setText("1");
		safeEdit.setText("0");
		minEdit.setText("1");
		deductSpinner = (Spinner) findViewById(R.id.layout_join_start_spinner);
		Button imgRetrun = (Button) findViewById(R.id.layout_join_img_return);
		imgRetrun.setBackgroundResource(R.drawable.returnselecter);
		// ImageView�ķ����¼�
		imgRetrun.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		Button ok =(Button) findViewById(R.id.join_img_ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isJoin();
			}
		});
		deductSpinner.setSelection(9);
		// ��ʼ��spinner
		deductSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				commisionRation = (String) deductSpinner.getSelectedItem();
		
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		//��ֵ
		beishu = Integer.parseInt(betAndGift.getLotmulti());
		allAtm = Integer.parseInt(betAndGift.getAmount()) / 100;
		
		zhushu = allAtm / beishu / betAndGift.getAmt();
		titleText.setText("�������-"+PublicMethod.toLotno(betAndGift.getLotno()));
		atmText.setText("��"+allAtm);
		zhuText.setText(zhushu+"ע");
		beiText.setText(beishu+"��");
		onEditTextClik();
	}
	public void onEditTextClik(){
		buyEdit.addTextChangedListener(new TextWatcher() {
			
			public void afterTextChanged(Editable s) {
				String amount = buyEdit.getText().toString();
				renText.setText("ռ�ܶ�" +  progress(isNull(amount),""+allAtm) + "%");// �ܽ��
				setEditText();
			}
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				
			}
			public void onTextChanged(CharSequence s, int start, int before,int count) {
				
			}

		});
		minEdit.addTextChangedListener(new TextWatcher() {
			
			public void afterTextChanged(Editable s) {
				setEditText();
			}
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				
			}
			public void onTextChanged(CharSequence s, int start, int before,int count) {
			}

		});
		safeEdit.addTextChangedListener(new TextWatcher() {
			
			public void afterTextChanged(Editable s) {
				String amount = safeEdit.getText().toString();
				baoText.setText("ռ�ܶ�" +  progress(isNull(amount),""+allAtm) + "%");// �ܽ��
				setEditText();
			}
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				
			}
			public void onTextChanged(CharSequence s, int start, int before,int count) {
			}

		});
	}
	public void setEditText(){
	  	int buyInt = Integer.parseInt(isNull(buyEdit.getText().toString()));
		int safeInt = Integer.parseInt(isNull(safeEdit.getText().toString()));
		int minInt = Integer.parseInt(isNull(minEdit.getText().toString()));
        if(buyInt>allAtm){
        	buyInt = allAtm;
        	buyEdit.setText(""+buyInt);
        }
        if(safeInt>allAtm-buyInt){
        	safeInt = allAtm-buyInt;
        	safeEdit.setText(""+ safeInt);
        }
        if(minInt>allAtm-buyInt){
        	minInt = allAtm-buyInt;
        		minEdit.setText(""+minInt);
        }
	}
	/**
	 * ȫ��׷���
	 */
	public void setAllSafeEdit(boolean isSafe){
		if(isSafe){
			int buyInt = Integer.parseInt(isNull(buyEdit.getText().toString()));
			safeEdit.setText(""+ (allAtm - buyInt));
			safeEdit.setEnabled(false);
		}else{
			safeEdit.setText("0");
			safeEdit.setEnabled(true);
		}
	
	}
	/**
	 * ����ٷֱ�
	 * @param amt
	 * @param allAmt
	 * @return
	 */
	public String  progress(String amt,String allAmt){
		if(allAmt.equals("0")){
		   return "0";
		}else{
		   float amount =  Integer.parseInt(amt);
		   float allAmount = Integer.parseInt(allAmt);
		   float progress = (amount/allAmount)*100;
		   NumberFormat   formatter   =   NumberFormat.getNumberInstance(); 
		   formatter.setMaximumFractionDigits(1); 
		   formatter.setMinimumFractionDigits(1); 
		   return  formatter.format(progress);
		}
	}
	/**
	 * �������
	 * @param allAmt
	 * @param amt
	 * @return
	 */
	public String leavMount(String allAmt,String amt){
		String amtStr="";
		amtStr = Integer.toString(Integer.parseInt(isNull(allAmt))-Integer.parseInt(isNull(amt)));
		return amtStr;
	}
	/**
	 * �ж��ַ����Ƿ��ǿ�ֵ
	 * 
	 */
	public String isNull(String str){
		String string;
		if(str==null||str.equals("")){
			return "0";
		}else{
			return str;
		}
		
	}
	/**
	 * ��ʼ����ѡ��ť��
	 */
	public void initRadioGroup() {
		baoRadioGroup = (RadioGroup) findViewById(R.id.buy_join_radiogroup_baodi);
		for (int i = 0; i < baoTitle.length; i++) {
			RadioButton radio = new RadioButton(this);
			radio.setText(baoTitle[i]);
			radio.setTextColor(Color.BLACK);
			radio.setTextSize(13);
			radio.setId(i);
			radio.setButtonDrawable(R.drawable.radio_select);
			radio.setPadding(Constants.PADDING, 0, 10, 0);
			baoRadioGroup.addView(radio);

		}
		baoRadioGroup.setOnCheckedChangeListener(this);
		baoRadioGroup.check(1);
		openRadioGroup = (RadioGroup) findViewById(R.id.buy_join_radiogroup_open);
		for (int i = 0; i < openTitle.length; i++) {
			RadioButton radio = new RadioButton(this);
			radio.setText(openTitle[i]);
			radio.setTextColor(Color.BLACK);
			radio.setTextSize(13);
			radio.setId(i);
			radio.setButtonDrawable(R.drawable.radio_select);
			radio.setPadding(Constants.PADDING, 0, 10, 0);
			openRadioGroup.addView(radio);

		}
		openRadioGroup.setOnCheckedChangeListener(this);
		openRadioGroup.check(0);
	}
	
	/**
	 * ����һ��ҳ���ȡ��Ϣ
	 */
	public void getInfo() {
		Intent intent = getIntent();
		byte[] bytes = intent.getByteArrayExtra("info");
		if (bytes != null) {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			try {
				ObjectInputStream objStream = new ObjectInputStream(byteStream);
				betAndGift = (BetAndGiftPojo) objStream.readObject();
			} catch (Exception e) {
			}
		}

	}
	/**
	 * �Ժ��������и�ֵ
	 */
	public void setPojo(){
		betAndGift.setBettype("startcase");
//		betAndGift.setTotalAmt(betAndGift.getAmount());
//		betAndGift.setAmount(""+Integer.parseInt(betAndGift.getTotalAmt())/Integer.parseInt(betAndGift.getLotmulti()));
		if(betAndGift.getIssuper().equals("0")){
			betAndGift.setOneAmount("300");
		}else{
			betAndGift.setOneAmount("200");
		}
		
		betAndGift.setSafeAmt(isNull(PublicMethod.toFen(isNull(safeEdit.getText().toString()))));
		betAndGift.setBuyAmt(isNull(PublicMethod.toFen(isNull(buyEdit.getText().toString()))));
		betAndGift.setMinAmt(PublicMethod.toFen(isNull(minEdit.getText().toString())));
		betAndGift.setCommisionRation(commisionRation);
		betAndGift.setVisibility(visible);
		betAndGift.setBatchcode(PublicMethod.toIssue(betAndGift.getLotno()));
		betAndGift.setDescription(descriptionEdit.getText().toString());
		
	}
	/**
	 * �Ƿ������
	 */
	public void isJoin(){
	  	int buyInt = Integer.parseInt(isNull(buyEdit.getText().toString()));
		int safeInt = Integer.parseInt(isNull(safeEdit.getText().toString()));
		int minInt = Integer.parseInt(isNull(minEdit.getText().toString()));
		if(buyInt==0&&safeInt==0){
			Toast.makeText(this, "�Ϲ����ͱ��׽��ܶ�Ϊ0��", Toast.LENGTH_SHORT).show();
		}else if(allAtm-buyInt>0&&minInt==0){
			Toast.makeText(this, "��͸�������Ϊ1Ԫ��", Toast.LENGTH_SHORT).show();
		}else{
			joinNet();
		}
	    if(minInt>allAtm-buyInt){
        	minInt = allAtm-buyInt;
        		minEdit.setText(""+minInt);
        }
	}
	/**
	 * �����������
	 * 
	 */
	public void joinNet() {
		setPojo();
		showDialog(0); // ��ʾ������ʾ�� 2010/7/4
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";

			@Override
			public void run() {
				str = JoinStartInterface.getInstance().joinStart(betAndGift);
				try {
					obj = new JSONObject(str);
					message = obj.getString("message");
					String error = obj.getString("error_code");
					handler.handleMsg(error, message);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				progressdialog.dismiss();
			}

		});
		t.start();
	}
	/**
	 * ��дRadioGroup��������onCheckedChanged
	 * 
	 * @param RadioGroup
	 *            RadioGroup
	 * @param int checkedId ��ǰ��ѡ���RadioId
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (group.getId()) {
		case R.id.buy_join_radiogroup_baodi:
			switch (checkedId) {
			case 0:// ��
				setAllSafeEdit(true);
				break;
			case 1:// ��
				setAllSafeEdit(false);
				break;
			}
		case R.id.buy_join_radiogroup_open:
			switch (checkedId) {
			case 0:// ����
				visible = "1";
				break;
			case 1:// ��ȫ����
				visible = "0";
				break;
			case 2://��������
				visible = "3";
				break;
			case 3://��ֹ�󹫿�
				visible = "2";
				break;
			case 4://�����߹���
				visible = "4";
				break;
			}
		}
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
			progressdialog.setCancelable(true);
			return progressdialog;
		}
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.ruyicai.handler.HandlerMsg#errorCode_0000()
	 */
	@Override
	public void errorCode_0000() {
		// TODO Auto-generated method stub
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		finish();
	}

	/* (non-Javadoc)
	 * @see com.ruyicai.handler.HandlerMsg#errorCode_000000()
	 */
	@Override
	public void errorCode_000000() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.ruyicai.handler.HandlerMsg#getContext()
	 */
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}

}
