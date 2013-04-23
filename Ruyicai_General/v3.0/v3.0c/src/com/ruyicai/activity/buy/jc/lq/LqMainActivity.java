package com.ruyicai.activity.buy.jc.lq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.jc.lq.view.LqMainView;
import com.ruyicai.activity.buy.jc.lq.view.sf.DxView;
import com.ruyicai.activity.buy.jc.lq.view.sf.RfView;
import com.ruyicai.activity.buy.jc.lq.view.sf.SfView;
import com.ruyicai.activity.buy.jc.lq.view.sf.SfcView;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.gift.GiftActivity;
import com.ruyicai.activity.usercenter.UserCenterDialog;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.net.newtransaction.BetAndGiftInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.util.RWSharedPreferences;



public class LqMainActivity extends Activity implements SeekBar.OnSeekBarChangeListener,HandlerMsg{
	private static int TYPE = 0;
	private final static int SF = 1;//ʤ��
	private final static int RF_SF = 2;//�÷�ʤ��
	private final static int SFC = 3;//ʤ�ֲ�
	private final static int DXF = 4;//��С��
	private TextView textTitle;
	private TextView mTextBeishu;
	private SeekBar mSeekBarBeishu;
	private String phonenum,sessionId,userno;
	private int iProgressBeishu = 1;
	private MyHandler handler = new MyHandler(this);//�Զ���handler
	private BetAndGiftPojo betAndGift=new BetAndGiftPojo();//Ͷע��Ϣ��
	private String msg;
	private boolean isGift = false;//�Ƿ�����
	private boolean isTouzhu = true;//�Ƿ�Ͷע
	private Dialog	dialogType = null;//�淨�л���ʾ��
	private View viewType;
	private LqMainView lqMainView;
	private LinearLayout layoutView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_lq_main);
		initView();
		createView(SF);
	}
    /**
     * �Ƿ����ؿͶ���ǰ����
     * 
     */
    public void setTitle(boolean isVisable){
    TextView title = (TextView)findViewById(R.id.buy_jc_main_text_title);
    	if(isVisable){
    		title.setVisibility(TextView.VISIBLE);
    	}else{
    		title.setVisibility(TextView.GONE);
    	}
    }
    /**
     * ��ʼ���齨
     */
	public void initView(){
        layoutView = (LinearLayout)findViewById(R.id.buy_lq_mian_layout);
        textTitle = (TextView) findViewById(R.id.layout_main_text_title);
		mSeekBarBeishu = (SeekBar) findViewById(R.id.buy_zixuan_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mTextBeishu = (TextView) findViewById(R.id.buy_zixuan_text_beishu);
		mTextBeishu.setText("" + iProgressBeishu);
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_subtract_beishu, -1,mSeekBarBeishu, true);
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_add_beishu, 1, mSeekBarBeishu,true);
		Button btnType = (Button) findViewById(R.id.buy_lq_main_btn_type);
		btnType.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createDialog();
			}
		});
		ImageButton zixuanTouzhu = (ImageButton) findViewById(R.id.buy_zixuan_img_touzhu);
		zixuanTouzhu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				beginTouZhu();
			}
		});
	}
	
	/**
	 * �淨�л�������
	 */
	public void createDialog(){
		if(dialogType == null){
			LayoutInflater factory = LayoutInflater.from(this);
			viewType = factory.inflate(R.layout.buy_lq_main_type_dialog,null);
			initRadioGroup(viewType);
			dialogType = new AlertDialog.Builder(this).create();
		}
	    dialogType.show();
	    dialogType.getWindow().setContentView(viewType);
	}
	private void initRadioGroup(View view) {
		RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int id) {
				// TODO Auto-generated method stub
				switch(id){
				case R.id.radio0:
					createView(SF);
					break;
				case R.id.radio1:
					createView(RF_SF);
					break;
				case R.id.radio2:
					createView(SFC);
					break;
				case R.id.radio3:
					createView(DXF);
					break;
				}
				  dialogType.cancel();
			}
		});
	}
	/**
	 * �����淨��ť��������
	 */
	private void createView(int type){
		switch(type){
		case SF:
			textTitle.setText(getString(R.string.jclq_sf_guoguan_title));
			lqMainView = new SfView(this,betAndGift,new Handler(),layoutView);
			TYPE = SF;
			break;
		case RF_SF:
			textTitle.setText(getString(R.string.jclq_rf_guoguan_title));
			lqMainView = new RfView(this,betAndGift,new Handler(),layoutView);
			TYPE = RF_SF;
			break;
		case SFC:
			textTitle.setText(getString(R.string.jclq_sfc_guoguan_title));
			lqMainView = new SfcView(this,betAndGift,new Handler(),layoutView);
			TYPE = SFC;
			break;
		case DXF:
			textTitle.setText(getString(R.string.jclq_dxf_guoguan_title));
			lqMainView = new DxView(this,betAndGift,new Handler(),layoutView);
			TYPE = DXF;
			break;
		}
		initSeekBar();
		
	}
	private void initSeekBar() {
		iProgressBeishu = 1;
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mTextBeishu.setText("" + iProgressBeishu);
	}
	/**
	 * Ͷע����
	 */
	public void beginTouZhu() {
		RWSharedPreferences pre = new RWSharedPreferences(LqMainActivity.this, "addInfo");
		sessionId = pre.getStringValue("sessionid");
		phonenum = pre.getStringValue("phonenum");
		userno = pre.getStringValue("userno");
		if (userno == null || userno.equals("")) {
			Intent intentSession = new Intent(LqMainActivity.this, UserLogin.class);
			startActivityForResult(intentSession, 0);
		} else {
			if(lqMainView.isTouZhuNet()){
				alert(lqMainView.getAlertStr(), lqMainView.getAlertCode());
			};
		}
	}
	/**
	 * fqc edit ���һ������ isBeiShu ���жϵ�ǰ�Ǳ����������� ��
	 * 
	 * @param idFind
	 * @param iV
	 * @param isAdd
	 * @param mSeekBar
	 * @param isBeiShu
	 */
	protected void setSeekWhenAddOrSub(int idFind, final int isAdd,final SeekBar mSeekBar, final boolean isBeiShu) {
		ImageButton subtractBeishuBtn = (ImageButton) findViewById(idFind);
		subtractBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isBeiShu) {
					if (isAdd == 1) {
						mSeekBar.setProgress(++iProgressBeishu);
					} else {
						mSeekBar.setProgress(--iProgressBeishu);
					}
					iProgressBeishu = mSeekBar.getProgress();
				}
			}
		});
	}

	
	
	/**
	 * Ͷע���ú���
	 * @param string  ��ʾ����Ϣ
	 * @return
	 */  
	public void alert(String string,final String zhuma) {
		isTouzhu =true;   
		isGift = false;   
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View v= inflater.inflate(R.layout.alert_dialog_touzhu, null);
		final AlertDialog dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����").create();
		dialog.show();
		LinearLayout layout = (LinearLayout)v.findViewById(R.id.alert_dialog_touzhu_linear_qihao_beishu);
		layout.setVisibility(LinearLayout.GONE);
		TextView text =(TextView) v.findViewById(R.id.alert_dialog_touzhu_text_one);
		text.setText(string);
		TextView textZhuma =(TextView) v.findViewById(R.id.alert_dialog_touzhu_text_zhuma);
		textZhuma.setText(zhuma);
		Button cancel = (Button) v.findViewById(R.id.alert_dialog_touzhu_button_cancel);
		Button ok = (Button) v.findViewById(R.id.alert_dialog_touzhu_button_ok);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
				initBet();
				if(isGift){
					toActivity(zhuma);
				}else if(isTouzhu){
					touZhuNet();
				}
			}
		});
		RadioButton check = (RadioButton) v.findViewById(R.id.alert_dialog_touzhu_check);
		RadioButton touzhuCheck = (RadioButton) v.findViewById(R.id.alert_dialog_touzhu1_check);
		RadioButton joinCheck = (RadioButton) v.findViewById(R.id.alert_dialog_join_check);
		joinCheck.setVisibility(RadioButton.GONE);
		touzhuCheck.setChecked(true);
		check.setPadding(50, 0, 0, 0);
	    check.setButtonDrawable(R.drawable.check_select);
		// ʵ�ּ�ס���� �� ��ѡ���״̬
	    check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
                    isGift = isChecked;
			}
		});
		touzhuCheck.setPadding(50, 0, 0, 0);
	    touzhuCheck.setButtonDrawable(R.drawable.check_select);
		// ʵ�ּ�ס���� �� ��ѡ���״̬
	    touzhuCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				    isTouzhu = isChecked;
			}
		});
		dialog.getWindow().setContentView(v);
	}

	public void toActivity(String zhuma){
		  ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		   try {
		   ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
		            objStream.writeObject(betAndGift);
		  } catch (IOException e) {
		   return;  // should not happen, so donot do error handling
		  }
		  Intent intent = new Intent(LqMainActivity.this, GiftActivity.class);
		  intent.putExtra("info", byteStream.toByteArray());
		  intent.putExtra("zhuma", zhuma);
		  startActivity(intent); 


	}

	/**
	 * ��ʼ��Ͷע��Ϣ
	 */
	public void initBet(){
		betAndGift.setSessionid(sessionId);
		betAndGift.setPhonenum(phonenum);
		betAndGift.setUserno(userno);
		betAndGift.setBettype("bet");// ͶעΪbet,����Ϊgift 
		betAndGift.setLotmulti(""+iProgressBeishu);//lotmulti    ����   Ͷע�ı���
	}
	/**
	 * Ͷע����
	 */
	public void touZhuNet(){
		final ProgressDialog progressDialog =  UserCenterDialog.onCreateDialog(this);// ��ʾ������ʾ�� 2010/7/4
		progressDialog.show();
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
				str = BetAndGiftInterface.getInstance().betOrGift(betAndGift);
				progressDialog.cancel();
				try {
					JSONObject obj = new JSONObject(str);		
					msg = obj.getString("message");
					String error = obj.getString("error_code");
					handler.handleMsg(error,msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				progressDialog.dismiss();
			}

		});
		t.start();
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			beginTouZhu();
			break;
		}
	}
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		if (progress < 1)
			seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();

		switch (seekBar.getId()) {
		case R.id.buy_zixuan_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			lqMainView.setBeiShu(iProgressBeishu);
			lqMainView.showEditText();
			break;
		default:
			break;
		}

		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	protected void onStop(){
		super.onStop();
	}
	public void onDestroy(){
	super.onDestroy();  
	lqMainView.clearInfo();
	}
	public void errorCode_0000() {
		// TODO Auto-generated method stub
		createView(TYPE);
	    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}


	public void errorCode_000000() {
		// TODO Auto-generated method stub
		
	}

	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}

