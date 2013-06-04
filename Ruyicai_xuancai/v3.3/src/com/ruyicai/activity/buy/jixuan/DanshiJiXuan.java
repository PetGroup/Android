/**
 * 
 */
package com.ruyicai.activity.buy.jixuan;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

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
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.buy.zixuan.AddView;
import com.ruyicai.activity.buy.zixuan.AddView.CodeInfo;
import com.ruyicai.activity.buy.zixuan.ZixuanActivity;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.gift.GiftActivity;
import com.ruyicai.activity.join.JoinStartActivity;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.interfaces.BuyImplement;
import com.ruyicai.jixuan.Balls;
import com.ruyicai.net.newtransaction.BetAndGiftInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.pojo.AreaNum;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.SensorActivity;
import com.ruyicai.util.RWSharedPreferences;

/**
 * ��ʽ��ѡ����
 * @author Administrator
 *
 */
public class DanshiJiXuan extends Activity implements SeekBar.OnSeekBarChangeListener,HandlerMsg{
	private TextView mTextSumMoney;
	public SeekBar mSeekBarBeishu, mSeekBarQishu;
	private TextView mTextBeishu, mTextQishu;
	public int iProgressBeishu = 1, iProgressQishu = 1;
	private Spinner jixuanZhu;
	private LinearLayout zhumaView;
	private SsqSensor sensor = new SsqSensor(this);
	private boolean isOnclik = true;
	public Vector<Balls> balls = new Vector();
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	private int blueBallResId[] = { R.drawable.grey, R.drawable.blue };// ѡ��С���л�ͼƬ
	private Balls ballOne;
	private BuyImplement buyImplement;//ͶעҪʵ�ֵķ���
	private Toast toast;
	private boolean toLogin = false;
	ProgressDialog progressdialog;
	private static final int DIALOG1_KEY = 0;// ��������ֵ2010/7/4
	public BetAndGiftPojo betAndGift=new BetAndGiftPojo();//Ͷע��Ϣ��
	MyHandler handler = new MyHandler(this);//�Զ���handler
	public String phonenum,sessionId,userno;
	public boolean isGift = false;//�Ƿ�����
	public boolean isJoin = false;//�Ƿ����
	public boolean isTouzhu = false;//�Ƿ�Ͷע
	private int oneValue = 2;//��ע���
	String codeStr;
	String lotno;
	public int iZhuShu;
	RadioButton  check;
	RadioButton  joinCheck;
	RadioButton  touzhuCheck;
	TextView alertText;
	TextView issueText;
	AlertDialog touZhuDialog;
	TextView textAlert;
	TextView textZhuma;
    TextView textTitle;
    Button codeInfo;
	boolean isTen = true;
	public AddView addView;
	private final int All_ZHU = 99;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.buy_danshi_jixuan_activity_new);
	}
	/**
	 * ������ѡ����
	 */
	public void createView(Balls balles,BuyImplement buyImplement,boolean isTen){
		this.isTen = isTen;
		sensor.startAction();
		this.buyImplement = buyImplement;
		this.ballOne = balles;
		zhumaView = (LinearLayout) findViewById(R.id.buy_danshi_jixuan_linear_zhuma);
		zhumaView.removeAllViews();
		toast = Toast.makeText(this, "����ҡ���ֻ�������ѡ�ţ�", Toast.LENGTH_SHORT);
		toast.show();
		balls = new Vector<Balls>();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// ��ʼ��spinner
			jixuanZhu = (Spinner) findViewById(R.id.buy_danshi_jixuan_spinner);
			jixuanZhu.setSelection(4);
			jixuanZhu.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					int position = jixuanZhu.getSelectedItemPosition();
					if (isOnclik) {
						again();
					} else {
						isOnclik = true;
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
		int index = jixuanZhu.getSelectedItemPosition() + 1;
		for (int i = 0; i < index; i++) {
			Balls ball = ballOne.createBalls();
			balls.add(ball);
		}
		createTable(zhumaView);
		sensor.onVibrator();// ��
		Button zixuanTouzhu = (Button) findViewById(R.id.buy_danshi_jixuan_img_touzhu);
		zixuanTouzhu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				beginTouZhu();
			}
		});
		final TextView textNum = (TextView)findViewById(R.id.buy_zixuan_add_text_num);
		Button add_dialog = (Button) findViewById(R.id.buy_zixuan_img_add_delet);
		addView = new AddView(textNum,this,false);
		add_dialog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(addView.getSize()>0){
					showAddViewDialog();
				}else{
					 Toast.makeText(DanshiJiXuan.this, DanshiJiXuan.this.getString(R.string.buy_add_dialog_alert), Toast.LENGTH_SHORT).show();	
				}
			}
		});
		Button add = (Button) findViewById(R.id.buy_zixuan_img_add);
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
               addToCodes();
			}
		
		});

//		ImageButton again = (ImageButton) findViewById(R.id.buy_danshi_jixuan_img_again);
//		again.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				zhumaView.removeAllViews();
//				balls = new Vector();
//				for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
//					Balls ball = ballOne.createBalls();
//					balls.add(ball);
//				}
//				createTable(zhumaView);
//			}
//		});
	}
	/**
	 * ��ѡȡ��Ϣ��ӵ���������
	 */
	private void addToCodes() {
		if(addView.getSize()+balls.size()-1>=All_ZHU){
			Toast.makeText(DanshiJiXuan.this,DanshiJiXuan.this.getString(R.string.buy_add_view_zhu_alert) , Toast.LENGTH_SHORT).show();
		}else{
			getCodeInfo(addView);
			addView.updateTextNum();
			again();	
		}
		
	}
	
	public void getCodeInfo(AddView addView){
		for(int i=0;i<balls.size();i++){
			String codeStr = getAddZhuma(i);
			CodeInfo codeInfo = addView.initCodeInfo(betAndGift.getAmt(), 1);
			codeInfo.addAreaCode(codeStr,Color.BLACK);
			codeInfo.setTouZhuCode(balls.get(i).getZhuma(balls,1));
			addView.addCodeInfo(codeInfo);
		}   
		addView.setIsJXcode(ballOne.getZhuma(balls, iProgressBeishu));
	}
	/**
	 * ����ѡ�񷽷�
	 */
	private void again() {
		zhumaView.removeAllViews();
		balls = new Vector();
		for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
			Balls ball = ballOne.createBalls();
			balls.add(ball);
		}
		createTable(zhumaView);
	}
	private void showAddViewDialog() {
		addView.createDialog(DanshiJiXuan.this.getString(R.string.buy_add_dialog_title));
		addView.showDialog();
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
	private void setSeekWhenAddOrSub(int idFind, final int isAdd,final SeekBar mSeekBar, final boolean isBeiShu,View view) {
		ImageButton subtractBeishuBtn = (ImageButton) view.findViewById(idFind);
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
				} else {
					if (isAdd == 1) {
						mSeekBar.setProgress(++iProgressQishu);
					} else {
						mSeekBar.setProgress(--iProgressQishu);
					}
					iProgressQishu = mSeekBar.getProgress();
				}
			}
		});
	}
	/**
	 * Ͷע����
	 */
	private void beginTouZhu() {
			if (balls.size() == 0) {
				alertInfo("������ѡ��1ע��Ʊ");
			} else {
				if(addView.getSize()==0){
					addToCodes();
					alert_jixuan();
				}else{
					showAddViewDialog();
				}
				
			}
	}
    public void setOneValue(int value){
    	oneValue = value;
    }
	/**
	 * ֱѡ��ѡͶע��ʾ���е���Ϣ
	 */
	private String getTouzhuAlertJixuan() {
		int oneAmt = betAndGift.getAmt();
        int iZhushu = getZhushu();
		int beishu = mSeekBarBeishu.getProgress();
		return "ע����"+ iZhushu + "ע    " + "��"
				+ oneAmt * (mSeekBarQishu.getProgress() * iZhushu*beishu) + "Ԫ" ;
	}
	private int getZhushu(){
		return addView.getSize();
	}
	/**
	 * Ͷע��ʾ��ʾע��
	 * @return
	 */
	private String getZhuma(){
		String zhumaString = "";
		for (int i = 0; i < balls.size(); i++) {
			for(int j=0;j<balls.get(i).getVZhuma().size();j++){
				if(isTen){
					zhumaString += balls.get(i).getTenShowZhuma(j);
				}else{
					zhumaString += balls.get(i).getShowZhuma(j);
				}
				
				if(j!=balls.get(i).getVZhuma().size()-1){
					zhumaString += "+";
				}
			}
			if (i != balls.size() - 1) {
				zhumaString += "\n";
			}
		}
		return "ע�룺" + "\n" + zhumaString ;	
	}
	/**
	 * ��������ʾע��
	 * @return
	 */
	private String getAddZhuma(int index){
		String zhumaString = "";
			for(int j=0;j<balls.get(index).getVZhuma().size();j++){
				if(isTen){
					zhumaString += balls.get(index).getTenShowZhuma(j);
				}else{
					zhumaString += balls.get(index).getShowZhuma(j);
				}
				
				if(j!=balls.get(index).getVZhuma().size()-1){
					zhumaString += "|";
				}
			}
		return zhumaString ;	
	}
	/**
	 * seekBar����ļ�����
	 */
	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
		// TODO Auto-generated method stub
		if (progress < 1)
			seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();
		switch (seekBar.getId()) {
		case R.id.buy_zixuan_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			break;
		case R.id.buy_zixuan_seek_qishu:
			iProgressQishu = iProgress;
			mTextQishu.setText("" + iProgressQishu);
			stateCheck();
			break;
		default:
			break;
		}
		alertText.setText(getTouzhuAlertJixuan());

	}
	/**
	 * ����ֱѡ��ѡ
	 */
	public void createTable(LinearLayout layout) {
		for (int i = 0; i < balls.size(); i++) {
			final int index = i;
			int iScreenWidth = PublicMethod.getDisplayWidth(this);
			LinearLayout lines = new LinearLayout(layout.getContext());
			for(int j=0;j<balls.get(i).getVZhuma().size();j++){
				 String color = (String) balls.get(i).getVColor().get(j);
				 TableLayout table;
				if(color.equals("red")){
					table = PublicMethod.makeBallTableJiXuan(null,iScreenWidth,redBallResId, balls.get(i).getBalls(j), this);
				}else{
				    table = PublicMethod.makeBallTableJiXuan(null,iScreenWidth,blueBallResId, balls.get(i).getBalls(j), this);
				}
					
				lines.addView(table);
			}	
			ImageButton delet = new ImageButton(lines.getContext());
			delet.setBackgroundResource(R.drawable.shanchu);
			delet.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (balls.size() > 1 && index < balls.size()) {
						zhumaView.removeAllViews();
						balls.remove(index);
						isOnclik = false;
						jixuanZhu.setSelection(balls.size() - 1);
						createTable(zhumaView);
					} else {
						Toast.makeText(DanshiJiXuan.this, getResources().getText(R.string.zhixuan_jixuan_toast),Toast.LENGTH_SHORT).show();
					}
				}
			});	
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			param.setMargins(10, 5, 0, 0);
			lines.addView(delet, param);
			lines.setGravity(Gravity.CENTER_HORIZONTAL);
            if(i%2==0){
				lines.setBackgroundResource(R.drawable.jixuan_list_bg);
			}
            lines.setPadding(0, 3, 0, 0);
			layout.addView(lines);
			
		}

	}
	/**
	 * ��ʾ��1 ��������ѡ�����
	 * @param string     ��ʾ����Ϣ
	 * @return
	 */
	public void alertInfo(String string) {   
		Builder dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����")
				.setMessage(string).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}

						});
		dialog.show();

	}

	/**
	 * ��ѡͶע���ú���
	 * @param string         ��ʾ����Ϣ
	 * @return
	 */
	public void alert_jixuan() {
		sensor.stopAction();
		buyImplement.touzhuNet();//Ͷע���ͺͲ���
		setZhuShu(balls.size());
		toLogin = true;
		isGift = false;
		isJoin = false;
		isTouzhu = true; 
		if(touZhuDialog == null){
			initTouZhuDialog();
		}else{
		    initAlerDialog();
		}
		touZhuDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				sensor.startAction();
			}
		});

	}
	   /**
	    * ��ǰע��
	    */
		public void setZhuShu(int zhushu){
			iZhuShu = zhushu;
		}
	/**
	 * ��ʼ������������������
	 * @param view
	 */
    public void initImageView(View view){
		mSeekBarBeishu = (SeekBar) view.findViewById(R.id.buy_zixuan_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mSeekBarQishu = (SeekBar) view.findViewById(R.id.buy_zixuan_seek_qishu);
		mSeekBarQishu.setOnSeekBarChangeListener(this);
		mSeekBarQishu.setProgress(iProgressQishu);

		mTextBeishu = (TextView) view.findViewById(R.id.buy_zixuan_text_beishu);
		mTextBeishu.setText("" + iProgressBeishu);
		mTextQishu = (TextView) view.findViewById(R.id.buy_zixuan_text_qishu);
		mTextQishu.setText("" + iProgressQishu);
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_subtract_beishu, -1,mSeekBarBeishu, true,view);
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_add_beishu, 1, mSeekBarBeishu,true,view);
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_subtract_qihao, -1,mSeekBarQishu, false,view);
		setSeekWhenAddOrSub(R.id.buy_zixuan_img_add_qishu, 1, mSeekBarQishu,false,view);
    }
	/**
	 * ��һ������Ͷע��ʾ��
	 */
	public void initTouZhuDialog(){
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View v= inflater.inflate(R.layout.alert_dialog_touzhu_new, null);
		touZhuDialog = new AlertDialog.Builder(this).setTitle("��ѡ�����").create();
		touZhuDialog.show();
		initImageView(v);
		if(betAndGift.isZhui()){
			initZhuiJia(v);
		}
		issueText =(TextView) v.findViewById(R.id.alert_dialog_touzhu_textview_qihao);
		alertText =(TextView) v.findViewById(R.id.alert_dialog_touzhu_text_one);
		textZhuma =(TextView) v.findViewById(R.id.alert_dialog_touzhu_text_zhuma);
		addView.getCodeList().get(addView.getSize()-1).setTextCodeColor(textZhuma);
		textTitle = (TextView) v.findViewById(R.id.alert_dialog_touzhu_text_zhuma_title);
		textTitle.setText("ע�룺"+"����"+addView.getSize()+"��Ͷע");
		issueText.setText(PublicMethod.toIssue(betAndGift.getLotno())+"��");
		alertText.setText(getTouzhuAlertJixuan());
		Button cancel = (Button) v.findViewById(R.id.alert_dialog_touzhu_button_cancel);
		Button ok = (Button) v.findViewById(R.id.alert_dialog_touzhu_button_ok);
		codeInfo = (Button) v.findViewById(R.id.alert_dialog_touzhu_btn_look_code);
		isCodeText(codeInfo);
		codeInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addView.createCodeInfoDialog();
				addView.showDialog();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				touZhuDialog.cancel();
				toLogin = false;
				clearProgress();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RWSharedPreferences pre = new RWSharedPreferences(DanshiJiXuan.this, "addInfo");
				sessionId = pre.getStringValue("sessionid");
				phonenum = pre.getStringValue("phonenum");
				userno = pre.getStringValue("userno");
				if (sessionId.equals("")) {
					toLogin = true;
					Intent intentSession = new Intent(DanshiJiXuan.this, UserLogin.class);
					startActivityForResult(intentSession, 0);
				} else {
					
				}
				touZhu();
			}

		});
		CheckBox checkPrize = (CheckBox) v.findViewById(R.id.alert_dialog_touzhu_check_prize);
		checkPrize.setChecked(true);
		checkPrize.setButtonDrawable(R.drawable.check_on_off);
		checkPrize.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					betAndGift.setPrizeend("1");
				}else{
					betAndGift.setPrizeend("0");
				}
			}
		});
		check = (RadioButton) v.findViewById(R.id.alert_dialog_touzhu_check);
		joinCheck = (RadioButton) v.findViewById(R.id.alert_dialog_join_check);
		touzhuCheck = (RadioButton) v.findViewById(R.id.alert_dialog_touzhu1_check);
		touzhuCheck.setChecked(true);
		textAlert = (TextView) v.findViewById(R.id.alert_dialog_touzhu_text_alert);
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
		joinCheck.setPadding(50, 0, 0, 0);
	    joinCheck.setButtonDrawable(R.drawable.check_select);
		// ʵ�ּ�ס���� �� ��ѡ���״̬
	    joinCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
                    isJoin = isChecked;
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
		stateCheck();
		touZhuDialog.setCancelable(false);
		touZhuDialog.getWindow().setContentView(v);
	}
	/**
	 * �û�Ͷע
	 */
	private void touZhu() {
		toLogin = false;
		initBet();
		touZhuDialog.cancel();
		// TODO Auto-generated method stub
		if(isGift){
			String code=addView.getsharezhuma();
			toActivity(code);
		}else if(isJoin){
			toJoinActivity();
		}else if(isTouzhu){
				touZhuNet();
		}
		clearProgress();
	}
	/**
	 * ��ʾ׷��Ͷע
	 * @param view
	 */
	private void initZhuiJia(View view) {
		LinearLayout toggleLinear = (LinearLayout)view.findViewById(R.id.buy_zixuan_linear_toggle);
		toggleLinear.setVisibility(LinearLayout.VISIBLE);
		ToggleButton zhuijiatouzhu = (ToggleButton)view.findViewById(R.id.dlt_zhuijia);
		zhuijiatouzhu.setOnCheckedChangeListener(new OnCheckedChangeListener() {			

			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				if(isChecked){
					betAndGift.setAmt(3);
					betAndGift.setIssuper("0");
				}else{
					betAndGift.setIssuper("");
					betAndGift.setAmt(2);
				}
				addView.setCodeAmt(betAndGift.getAmt());
				alertText.setText(getTouzhuAlertJixuan());
			}
		});
	}
	/**
	 * ��ձ����������Ľ�����
	 */
	public void clearProgress(){
		iProgressBeishu = 1;
		iProgressQishu = 1;
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mSeekBarQishu.setProgress(iProgressQishu);
	}
	/**
	 * �ٴ�������ʾ��
	 */
	public void initAlerDialog(){
		touzhuCheck.setChecked(true);
		clearProgress();
		stateCheck();
		issueText.setText(PublicMethod.toIssue(betAndGift.getLotno())+"��");
		textTitle.setText("ע�룺"+"����"+addView.getSize()+"��Ͷע");
		addView.getCodeList().get(addView.getSize()-1).setTextCodeColor(textZhuma);
		isCodeText(codeInfo);
		alertText.setText(getTouzhuAlertJixuan());
		touZhuDialog.show();
	}
	private void isCodeText(Button codeInfo) {
		if(addView.getSize()>1){
			codeInfo.setVisibility(Button.VISIBLE);
		}else{
			codeInfo.setVisibility(Button.GONE);
		}
	}
    /**
     * �ж������Ƿ����1
     */
    public void stateCheck(){
		if(iProgressQishu>1){
			isGift = false;
			isJoin = false;
			isTouzhu = true;
			check.setVisibility(CheckBox.GONE);
			joinCheck.setVisibility(CheckBox.GONE);
			touzhuCheck.setVisibility(CheckBox.GONE);
			textAlert.setVisibility(TextView.VISIBLE);
		}else{
			check.setVisibility(CheckBox.VISIBLE);
			joinCheck.setVisibility(CheckBox.VISIBLE);
			touzhuCheck.setVisibility(CheckBox.VISIBLE);
			textAlert.setVisibility(TextView.GONE);
		}
    }
	public void toJoinActivity(){
		  clearAddView();
		  ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		   try {
			   ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
	           objStream.writeObject(betAndGift);
		  } catch (IOException e) {
			  return;  // should not happen, so donot do error handling
		  }
		  
		  Intent intent = new Intent(DanshiJiXuan.this,JoinStartActivity.class);
		  intent.putExtra("info", byteStream.toByteArray());
		  startActivity(intent); 


	}
	/**
	 * ���Ͳ�Ʊ��ת
	 * @param zhuma
	 */
	public void toActivity(String zhuma){
		  clearAddView();
		  ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		   try {
			   ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
	           objStream.writeObject(betAndGift);
		  } catch (IOException e) {
			  return;  // should not happen, so donot do error handling
		  }
		  
		  Intent intent = new Intent(DanshiJiXuan.this, GiftActivity.class);
		  intent.putExtra("info", byteStream.toByteArray());
		  intent.putExtra("zhuma", zhuma);
		  startActivity(intent); 


	}
	/**
	 * Ͷע����
	 */
	public void touZhuNet(){
	
		showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
					str = BetAndGiftInterface.getInstance().betOrGift(betAndGift);
					try {
						JSONObject obj = new JSONObject(str);		
						String msg = obj.getString("message");
						String error = obj.getString("error_code");
						handler.handleMsg(error,msg);
					} catch (JSONException e) {
						e.printStackTrace();
	
					}
					progressdialog.dismiss();
			}

		});
		t.start();
	}
	/**
	 * ��ʼ��Ͷע��Ϣ
	 */
	public void initBet(){
		betAndGift.setIsSellWays("1");//��עͶ
		betAndGift.setSessionid(sessionId);
		betAndGift.setPhonenum(phonenum);
		betAndGift.setUserno(userno);
		betAndGift.setBettype("bet");// ͶעΪbet,����Ϊgift 
		betAndGift.setLotmulti(""+iProgressBeishu);//lotmulti    ����   Ͷע�ı���
		betAndGift.setBatchnum(""+iProgressQishu);//batchnum    ׷������ Ĭ��Ϊ1����׷�ţ�
		betAndGift.setBatchcode(PublicMethod.toIssue(betAndGift.getLotno()));
		betAndGift.setAmount(""+addView.getSize()*iProgressBeishu*betAndGift.getAmt()*100);// amount      ��� ��λΪ�֣��ܽ�
//		betAndGift.setBet_code(ballOne.getZhuma(balls, iProgressBeishu));
		betAndGift.setBet_code(addView.getTouzhuCode(iProgressBeishu,betAndGift.getAmt()*100));
		lotno = PublicMethod.toLotno(betAndGift.getLotno());
		betAndGift.setBatchcode(PublicMethod.toIssue(betAndGift.getLotno()));
		
	}
	/**
	 * ����������ʾ��
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG1_KEY: {
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
	/**
	 * ʵ���𶯵���
	 * @author Administrator
	 *
	 */
	class SsqSensor extends SensorActivity {

		public SsqSensor(Context context) {
			getContext(context);
		}

		@Override
		public void action() {
			zhumaView.removeAllViews();
			balls = new Vector<Balls>();
			for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
				Balls ball = ballOne.createBalls();
				balls.add(ball);
			}
			createTable(zhumaView);
		}
	}
	/**
	 * ���³�ʼ������
	 */
	public void againView(){
	    sensor.startAction();
	    sensor.onVibrator();// ��
	    toast.show();
	    jixuanZhu.setSelection(4);
		zhumaView.removeAllViews();
		balls = new Vector();
		for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
			Balls ball = ballOne.createBalls();
			balls.add(ball);
		}
		createTable(zhumaView);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
//			touZhu();
			break;
		}
	}
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}	
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!toLogin){
		    againView();			
		}else{
			toLogin = false;
		}
	}
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(!toLogin){
			sensor.stopAction();
		}
	}
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	/* (non-Javadoc)
	 * @see com.ruyicai.handler.HandlerMsg#errorCode_0000()
	 */
	@Override
	public void errorCode_0000() {
		// TODO Auto-generated method stub
		String code=addView.getsharezhuma();
		clearAddView();
	    PublicMethod.showDialog(DanshiJiXuan.this,lotno+code);
	}
	private void clearAddView() {
		addView.clearInfo();
		addView.updateTextNum();
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
	/**
	 * �˳���ʾ
	 * 
	 * @param string
	 *            ��ʾ����Ϣ
	 * @return
	 */
	public void alertExit(String string) {   
		Builder dialog = new AlertDialog.Builder(this).setTitle("��ܰ��ʾ")
				.setMessage(string).setNeutralButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
                                    finish();
							}
						}).setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
		dialog.show();

	}
	/**
	 * ��д�Żؽ�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case 4:
			if(addView.getSize()!=0){
				alertExit(getString(R.string.buy_alert_exit));
			}else{
				finish();
			}
			break;
		}
		return false;
	}
}
