package com.ruyicai.activity.game.ssc;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
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
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.common.RuyicaiActivityManager;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.game.ssq.SsqActivity;
import com.ruyicai.activity.home.RuyicaiAndroid;
import com.ruyicai.activity.home.ScrollableTabActivity;
import com.ruyicai.dialog.Accoutdialog;
import com.ruyicai.handler.MyDialogListener;
import com.ruyicai.net.JrtLot;
import com.ruyicai.net.transaction.BettingInterface;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.SensorActivity;
import com.ruyicai.util.ShellRWSharesPreferences;
import com.ruyicai.view.OneBallView;
import com.ruyicai.view.RadioStateDrawable;
import com.ruyicai.view.TabBarButton;

/**
 * @author Administrator
 * 
 */
public class SscTwoStar extends Activity implements OnClickListener, // С�򱻵��
		// onClick
		SeekBar.OnSeekBarChangeListener, // SeekBar�ı� onProgressChanged
		RadioGroup.OnCheckedChangeListener, // ��ѡ��ѡ��仯 onCheckedChangeListener
		MyDialogListener {

	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
	// ������
	private static final int DIALOG1_KEY = 0;
	private ProgressDialog progressdialog;
	private ProgressDialog getlotdialog;
	private LinearLayout buyView;
	private int iScreenWidth;
	private boolean iszhixuan;
	// ����
	private RadioGroup.LayoutParams topButtonLayoutParams;
	private RadioGroup topButtonGroup;
	private int iButtonNum = 4;
	int topButtonIdOn[] = { R.drawable.goucai_pl3_b, R.drawable.zhixuan_pl3_b,
			R.drawable.zuxuan_pl3_b, R.drawable.hezhi_pl3_b };
	int topButtonIdOff[] = { R.drawable.goucai_pl3, R.drawable.zhixuan_pl3,
			R.drawable.zuxuan_pl3, R.drawable.hezhi_pl3 };

	SeekBar mSeekBarBeishu;
	SeekBar mSeekBarQishu;
	TextView mTextBeishu;
	TextView mTextQishu;
	TextView mTextSumMoney;
	ImageButton ssc_btn_touzhu;
	Button ssc_btn_newSelect;
	ImageButton chongxuan;

	BallTable shiBallTable = null;
	BallTable geBallTable = null;
	BallTable oneBallTable = null;
	private int BallResId[]={R.drawable.grey, R.drawable.red};
	public static final int SHI_BALL_START = 0x80000031; // С����ʼID
	public static final int GE_BALL_START = 0x80000041; // С����ʼID
	public static final int ONE_BALL_START = 0x80000051; // С����ʼID

	int iProgressBeishu = 1;
	int iProgressQishu = 1;

	private static final int BALL_WIDTH = 55; // С��ͼ�Ŀ��

	private int iCurrentButton; // ����button��ǰ��λ��
	private int dialogType = 0;

	private int iShiBallNumber;// С���������ѡ��
	private int iGeBallNumber;// С���������ѡ��
	private int iOneBallNumber;// С���������ѡ��
	private int mTimesMoney = 2;
	TextView issue;
	String batchCode;
	String endTime;
	String systime;
	Timer timer;
	int countMinute;
	int countSecond;
	private TextView title;
	private TextView term;
	private EditText shiedittext;
	private EditText geeditetext;
	private Spinner jixuanZhu;
	private LinearLayout zhuView;
	private Vector<Balls> balls = new Vector<Balls>();
	ImageButton ssq_b_touzhu_danshi;
	ImageButton ssq_b_touzhu_fushi;
	
	private boolean isOnclik=true;
	
//	private boolean zhixuan=false;
    
	private SscSensor sensor = new SscSensor(this);
	/**
	 * ��Ϣ������
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.getData().getString("get");
			switch (msg.what) {
			case 0:
				break;
			case 1:
				progressdialog.dismiss();
				if(isFinishing() == false){
				    Accoutdialog.getInstance().createAccoutdialog(SscTwoStar.this, getResources().getString(R.string.goucai_Account_dialog_msg).toString());
				}
				// //��Ҫ���AlertDialog��ʾע��ɹ�
				break;
			case 2:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ѽ�����", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ�û���ע��
				break;
			case 3:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "��ƱͶע�У�", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾϵͳ���㣬���Ժ�
				break;
			case 4:
				progressdialog.dismiss();
				switch(iCurrentButton){
				case PublicConst.SSC_TWO_STAR:
					if(iszhixuan){
						zhuView.removeAllViews();
					}else{
					shiedittext.setText("");
					geeditetext.setText("");
					shiBallTable.clearAllHighlights();
					geBallTable.clearAllHighlights();
					changeTextSumMoney();
					}
					break;
				case PublicConst.SSC_TWO_STAR_ZU_XUAN:
					geeditetext.setText("");
					oneBallTable.clearAllHighlights();
					changeTextSumMoney();
				    break;
				case PublicConst.SSC_TWO_STAR_HE_ZHI:
					geeditetext.setText("");
					oneBallTable.clearAllHighlights();
					changeTextSumMoney();
					break;
				default:
				
						break;
				}
				
				Intent intent = new Intent(UserLogin.SUCCESS);
				sendBroadcast(intent);
				if(isFinishing() == false){
				    PublicMethod.showDialog(SscTwoStar.this);
				}
				break;
				
			case 6:
				// //��Ҫ���AlertDialog��ʾ�û���¼�ɹ�
				progressdialog.dismiss();
				
				switch(iCurrentButton){
				case PublicConst.SSC_TWO_STAR:
					if(iszhixuan){
						zhuView.removeAllViews();
					}else{
					shiedittext.setText("");
					geeditetext.setText("");
					shiBallTable.clearAllHighlights();
					geBallTable.clearAllHighlights();
					changeTextSumMoney();
					}
					break;
				case PublicConst.SSC_TWO_STAR_ZU_XUAN:
					geeditetext.setText("");
					oneBallTable.clearAllHighlights();
					changeTextSumMoney();
				    break;
				case PublicConst.SSC_TWO_STAR_HE_ZHI:
					geeditetext.setText("");
					oneBallTable.clearAllHighlights();
					changeTextSumMoney();
					break;
				default:
				
						break;
				}
				
				Intent intent2 = new Intent(UserLogin.SUCCESS);
				sendBroadcast(intent2);
				if(isFinishing() == false){
				    PublicMethod.showDialog(SscTwoStar.this);
				}
				break;
			case 7:
				progressdialog.dismiss();

				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(SscTwoStar.this,
						UserLogin.class);
				startActivity(intentSession);
				break;
			case 8:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ��¼ʧ��
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 10:
				// ��ͼƬ�ı������û�ԭ����ͼ����ʾ�ɵ��
				ssc_btn_touzhu.setImageResource(R.drawable.imageselecter);

				break;
			case 11:

				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "û���ںſ���Ͷע��", Toast.LENGTH_LONG)
						.show();
				break;
			case 12:// ��ʾ��ǰ����

				String time[] = endTime.split(" ");
				String times[] = time[1].split(":");
				times(Integer.parseInt(times[0]), Integer.parseInt(times[1]),
						Integer.parseInt(times[2]));
				break;

			case 13:// ��ʾ��ǰ����

				getlotdialog.dismiss();
				String minute = "";
				String second = "";
				if (countSecond > 0) {
					countSecond--;
				} else if (countSecond == 0 && countMinute > 0) {
					countMinute--;
					countSecond = 59;
				} else {
					timer.cancel();
				}
				if (countMinute < 10) {
					minute = "0";
				}
				if (countSecond < 10) {
					second = "0";
				}
				term.setText("��" + batchCode + "��" + "     ʣ��ʱ�䣺" + minute
						+ countMinute + ":" + second + countSecond);
				break;
			case 14:// �����ѽ�ֹ

				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����Ѿ���ֹ��", Toast.LENGTH_LONG)
						.show();
				break;

			}
		}
	};

	/**
	 * ��ں���
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		// ----- ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// ----- ���ؿ�� layout
		setContentView(R.layout.layout_main_buy);
		
//		ImageButton flush = (ImageButton) findViewById(R.id.layout_main_buy_imgbtn_flush);
//		flush.setVisibility(ImageButton.VISIBLE);
//		flush.setOnClickListener(new Button.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				getLotNo();
//			}
//
//		});
//		TextView title = (TextView) findViewById(R.id.goucaitouzhu_title);
//		title.setText(getResources().getString(R.string.ssc_erxing));
//
//		issue = (TextView) findViewById(R.id.layout_main_buy_text_title);
//		issue.setVisibility(TextView.VISIBLE);
		title = (TextView) findViewById(R.id.layout_main_text_title);
		term = (TextView) findViewById(R.id.layout_main_text_time);
		Spinner ssc_zhonglei=(Spinner)findViewById(R.id.ssc_zhonglei_spinner);
		ssc_zhonglei.setVisibility(View.VISIBLE);
		ssc_zhonglei.setSelection(1, true);
		ssc_zhonglei.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch(position){
				case 0:
					Intent intent = new Intent(SscTwoStar.this,SscZhiXuan.class);
					startActivity(intent);
					SscTwoStar.this.finish();
					break;
				case 2:
					Intent intent2 = new Intent(SscTwoStar.this,SscThreeStar.class);
					startActivity(intent2);
					SscTwoStar.this.finish();
					break;
				case 3:
					Intent intent3 = new Intent(SscTwoStar.this,SscFiveStar.class);
					startActivity(intent3);
					SscTwoStar.this.finish();
					break;
				case 4:
					Intent intent4 = new Intent(SscTwoStar.this,SscBigSmall.class);
					startActivity(intent4);
					SscTwoStar.this.finish();
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		ImageButton flush = (ImageButton) findViewById(R.id.layout_ssc__flush);
		flush.setVisibility(ImageButton.VISIBLE);
		flush.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				getLotNo();
			}

		});

		getLotNo();
		// ��ȡ������id
		buyView = (LinearLayout) findViewById(R.id.layout_buy);
		initTopButtons();// ��ʼ��������ǩ
		//commit();// ����
		iCurrentButton=PublicConst.SSC_TWO_STAR;
		createView(iCurrentButton);

	}
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	sensor.stopAction();
    }
	/**
	 * ����ʱ
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	public void times(int endHour, int endMinute, int endSecond) {
	      Date date = new Date(new Long(systime));
	  	

	  	//	t.setToNow(); // ȡ��ϵͳʱ�䡣
//	  		  int hour = date.hour + 8;// ;
	  		  int minute =date.getMinutes();
	  		  int second = date.getSeconds();
	

		// ����ʣ��ʱ��

		if (endSecond < second) {
			countSecond = endSecond - second + 60;
			endMinute--;
		} else {
			countSecond = endSecond - second;
		}
		if (endMinute < minute) {
			countMinute = endMinute - minute + 60;
			endHour--;
		} else {
			countMinute = endMinute - minute;
		}
		// if (endHour != hour && endHour != (hour + 12)) {
		// countMinute = 10;
		// countSecond = 0;
		// }

		TimerTask task = new TimerTask() {
			public void run() {
				Message message = new Message();
				message.what = 13;
				handler.sendMessage(message);
			}
		};
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer(true);
		timer.schedule(task, 1000, 1000);
	}

	/**
	 * ��õ�ǰ�ں�
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	public String getLotNo() {
		getlotdialog = new ProgressDialog(this);
		// progressdialog.setTitle("Indeterminate");
		getlotdialog.setMessage("����������...");
		getlotdialog.setIndeterminate(true);
		getlotdialog.setCancelable(true);
		getlotdialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				String re = "";

				
					re = JrtLot.getLotNo("T01007");
					if (!re.equalsIgnoreCase("")) {
						try {
							JSONObject obj = new JSONObject(re);
							error_code = obj.getString("error_code");
							batchCode = obj.getString("batchCode");
							endTime = obj.getString("end_time");
							systime = obj.getString("sys_current_time");
							
						} catch (JSONException e) {
						
						}
					} else {
					
					}
			
			

				if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 8;
					handler.sendMessage(msg);

				} else if (error_code.equals("0000")) {
					Message msg = new Message();
					msg.what = 12;
					handler.sendMessage(msg);
				}
			}

		}).start();
		return null;
	}

	/**
	 * ��ʼ��������ǩ
	 * 
	 */
	private void initTopButtons() {
		topButtonGroup = (RadioGroup) findViewById(R.id.topMenu);
		topButtonGroup.setOnCheckedChangeListener(this);
		topButtonLayoutParams = new RadioGroup.LayoutParams(/* 320/5 */64,
				RadioGroup.LayoutParams.WRAP_CONTENT);
		commit();
	}

	/**
	 * ��Ӷ�����ť������ʾ��ʽ�淨View
	 * 
	 * @return void
	 */
	private void commit() {
		topButtonGroup.removeAllViews();
		if (iszhixuan) {
			topButtonIdOn[1] = R.drawable.zhixuan_pl3_b;
			topButtonIdOff[1] = R.drawable.zhixuan_pl3;
		} else {
			topButtonIdOn[1] = R.drawable.jixuan_pl3_b;
			topButtonIdOff[1] = R.drawable.jixuan_pl3;
		}

		int optimum_visible_items_in_portrait_mode = iButtonNum;

		int screen_width = getWindowManager().getDefaultDisplay().getWidth();
		int width;
		width = screen_width / iButtonNum;
	//	width = 130;
		RadioStateDrawable.other_width = width;
		RadioStateDrawable.other_screen_width = screen_width;

		topButtonLayoutParams = new RadioGroup.LayoutParams(width,
				RadioGroup.LayoutParams.WRAP_CONTENT);

		for (int i = 0; i < iButtonNum; i++) {
			TabBarButton tabButton = new TabBarButton(this);
			tabButton.setState(topButtonIdOn[i], topButtonIdOff[i],4);
			tabButton.setId(i);
			tabButton.setGravity(Gravity.CENTER);
			topButtonGroup.addView(tabButton, i, topButtonLayoutParams);

		}
		//topButtonGroup.check(1);// Ĭ����ѡ
	}

	
	public void changeImg(int iButtonNum, int[] topButtonIdon,
			int[] topButtonIdonoff, RadioGroup top) {
		top.removeViews(1,1);
		if (iszhixuan) {
			topButtonIdOn[1] = R.drawable.zhixuan_pl3_b;    
			topButtonIdOff[1] = R.drawable.zhixuan_pl3;
		} else {
			topButtonIdOn[1] = R.drawable.jixuan_pl3_b;
			topButtonIdOff[1] = R.drawable.jixuan_pl3;
		}
		
		int screen_width = getWindowManager().getDefaultDisplay().getWidth();
		int width = screen_width / iButtonNum;

		topButtonLayoutParams = new RadioGroup.LayoutParams(width,
				RadioGroup.LayoutParams.WRAP_CONTENT);

		TabBarButton tabButton = new TabBarButton(this);

		tabButton.setState(topButtonIdon[1], topButtonIdonoff[1],4);
		tabButton.setId(1);
		topButtonLayoutParams = new RadioGroup.LayoutParams(width,
				RadioGroup.LayoutParams.WRAP_CONTENT);
		top.addView(tabButton, 1, topButtonLayoutParams);

	}
	/**
	 * ���ö�����ť��λ��
	 * 
	 * @param int index ������ť������
	 * 
	 * @return void
	 */
	public void setCurrentTab(int index) {
	
		switch (index) {
		case 0:
		 sensor.stopAction();
         Intent intent =new Intent(this,RuyicaiAndroid.class);
         startActivity(intent);
         finish();
		    break;
		case 1:
			iCurrentButton = PublicConst.SSC_TWO_STAR;
			iszhixuan=!iszhixuan;
	        if(iszhixuan){
			sensor.startAction();
			create_TwoStar_JiXuan();
			
			}else{
		    sensor.stopAction();
	        createView(iCurrentButton);
			}
			topButtonGroup.setSelected(false);
			commit();
			break;
		case 2:
			sensor.stopAction();
			if(!iszhixuan){
				iszhixuan=true;
			}
			iCurrentButton = PublicConst.SSC_TWO_STAR_ZU_XUAN;
			createView(iCurrentButton);
			topButtonGroup.setSelected(false);
			changeImg(iButtonNum, topButtonIdOn, topButtonIdOff, topButtonGroup);
			
			
			break;
		case 3:
			sensor.stopAction();
			iCurrentButton = PublicConst.SSC_TWO_STAR_HE_ZHI;
			if(!iszhixuan){
				iszhixuan=true;
			}
            createView(iCurrentButton);
			topButtonGroup.setSelected(false);
			changeImg(iButtonNum, topButtonIdOn, topButtonIdOff, topButtonGroup);
			
			break;
		
		
		}
	}

	/**
	 * ����
	 */
	
	
	public void createView(int Type) {
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = null;
		int BallViewNum = 10;
		int BallViewWidth = SscTwoStar.BALL_WIDTH;
		iScreenWidth = PublicMethod.getDisplayWidth(this);
		switch (Type) {
		case PublicConst.SSC_TWO_STAR_ZU_XUAN:
			title.setText("ʱʱ�ʶ�����ѡ");
			dialogType = PublicConst.SSC_TWO_STAR_ZU_XUAN;
			iV = (LinearLayout) inflate.inflate(
					R.layout.layout_ssc_twostar_main, null);
			oneBallTable = PublicMethod.makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
					BallViewNum,BallResId, ONE_BALL_START, 0,this,this);
			
			geeditetext=(EditText)iV.findViewById(R.id.ssc_zhixuantwostars_edit);
			
			chongxuan = (ImageButton) iV.findViewById(R.id.ssc_jisuan_cb);
			chongxuan.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					oneBallTable.clearAllHighlights();
					geeditetext.setText("");
					changeTextSumMoney();
					
				}
			});
			break;
		case PublicConst.SSC_TWO_STAR:
			title.setText("ʱʱ�ʶ���ֱѡ");
			dialogType = PublicConst.SSC_TWO_STAR;
			iV = (LinearLayout) inflate.inflate(R.layout.layout_ssc_two_star,
					null);
			final TableLayout shiarea=(TableLayout)iV.findViewById(R.id.ssc_table_shi);
			final TableLayout gearea=(TableLayout)iV.findViewById(R.id.ssc_table_ge);
			gearea.setVisibility(View.GONE);
            final TextView twostarshi=(TextView)iV.findViewById(R.id.ssc_twostarshi);
            final TextView twostarge=(TextView)iV.findViewById(R.id.ssc_twostarge);
			shiBallTable = PublicMethod.makeBallTable(iV, R.id.ssc_table_shi, iScreenWidth,
					BallViewNum,  BallResId, SHI_BALL_START, 0,this,this);
			geBallTable = PublicMethod.makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
					BallViewNum,  BallResId, GE_BALL_START, 0,this,this);
			shiedittext=(EditText)iV.findViewById(R.id.ssc_zhixuantwostars_edit);
			geeditetext=(EditText)iV.findViewById(R.id.ssc_zhixuantwostarg_edit);
			shiedittext.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
				  // TODO Auto-generated method stub
					if(hasFocus){
						shiedittext.setBackgroundResource(R.drawable.hongkuang);
						shiarea.setVisibility(LinearLayout.VISIBLE);
						gearea.setVisibility(LinearLayout.GONE);
						shiedittext.setTextColor(Color.RED);
						twostarshi.setTextColor(Color.RED);
					}
					else{
						shiedittext.setBackgroundResource(R.drawable.huikuang);
						shiedittext.setTextColor(Color.BLACK);
						twostarshi.setTextColor(Color.BLACK);
					}
					
				}
			});
			geeditetext.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if(hasFocus){
						geeditetext.setBackgroundResource(R.drawable.hongkuang);
						gearea.setVisibility(LinearLayout.VISIBLE);
						shiarea.setVisibility(LinearLayout.GONE);
						geeditetext.setTextColor(Color.RED);
						twostarge.setTextColor(Color.RED);
					}
					else{
						geeditetext.setBackgroundResource(R.drawable.huikuang);
						geeditetext.setTextColor(Color.BLACK);
						twostarge.setTextColor(Color.BLACK);
					}
					
				}
			});
			chongxuan = (ImageButton) iV.findViewById(R.id.ssc_jisuan_cb);
			chongxuan.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shiBallTable.clearAllHighlights();
	                geBallTable.clearAllHighlights();
	                changeTextSumMoney();
					shiedittext.setText("");
					geeditetext.setText("");
					
				}
			});
			break;
		case PublicConst.SSC_TWO_STAR_BAO_DIAN:
			title.setText("ʱʱ�ʶ��Ǻ�ֵ");
			dialogType = PublicConst.SSC_TWO_STAR_BAO_DIAN;
			BallViewNum = 19;
			iV = (LinearLayout) inflate.inflate(
					R.layout.layout_ssc_twostar_main, null);
//			TextView alert = (TextView) iV.findViewById(R.id.ssc_text_ge);
//			alert.setVisibility(TextView.GONE);
			oneBallTable = PublicMethod.makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
					BallViewNum, BallResId, ONE_BALL_START, 0,this,this);
			geeditetext=(EditText)iV.findViewById(R.id.ssc_zhixuantwostars_edit);
			chongxuan = (ImageButton) iV.findViewById(R.id.ssc_jisuan_cb);
			chongxuan.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					oneBallTable.clearAllHighlights();
					changeTextSumMoney();
					geeditetext.setText("");
					
				}
			});
			break;
		case PublicConst.SSC_TWO_STAR_HE_ZHI:
			title.setText("ʱʱ�ʶ��Ǻ�ֵ");
			dialogType = PublicConst.SSC_TWO_STAR_HE_ZHI;
			BallViewNum = 19;
			iV = (LinearLayout) inflate.inflate(
					R.layout.layout_ssc_twostar_main, null);
//			TextView alert1 = (TextView) iV.findViewById(R.id.ssc_text_ge);
//			alert1.setVisibility(TextView.GONE);
			oneBallTable = PublicMethod.makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
					BallViewNum, BallResId, ONE_BALL_START, 0,this,this);
			geeditetext=(EditText)iV.findViewById(R.id.ssc_zhixuantwostars_edit);
			chongxuan = (ImageButton) iV.findViewById(R.id.ssc_jisuan_cb);
			chongxuan.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					oneBallTable.clearAllHighlights();
					changeTextSumMoney();
					geeditetext.setText("");
					
				}
			});
			break;
		default:
			break;
		}

		{

			mTextSumMoney = (TextView) iV.findViewById(R.id.ssc_text_sum_money);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));
			mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.ssc_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			iProgressBeishu = 1;
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) iV.findViewById(R.id.ssc_seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			iProgressQishu = 1;
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) iV.findViewById(R.id.ssc_text_beishu);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) iV.findViewById(R.id.ssc_text_qishu);
			mTextQishu.setText("" + iProgressQishu);
			/*
			 * ����Ӽ�ͼ�꣬��seekbar�������ã�
			 * 
			 * @param int idFind, ͼ���id View iV, ��ǰ��view final int isAdd, 1��ʾ��
			 * -1��ʾ�� final SeekBar mSeekBar
			 * 
			 * @return void
			 */
			setSeekWhenAddOrSub(R.id.ssc_seekbar_subtract_beishu, iV, -1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.ssc_seekbar_add_beishu, iV, 1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.ssc_seekbar_subtract_qihao, iV, -1,
					mSeekBarQishu, false);
			setSeekWhenAddOrSub(R.id.ssc_seekbar_add_qihao, iV, 1,
					mSeekBarQishu, false);

//			ssc_btn_newSelect = (Button) iV
//					.findViewById(R.id.ssc_btn_newSelect);
//			ssc_btn_newSelect.setOnClickListener(new Button.OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					ChooseNumberDialogSsc iChooseNumberDialog = new ChooseNumberDialogSsc(
//							SscTwoStar.this, dialogType, SscTwoStar.this);
//					iChooseNumberDialog.show();
//					changeTextSumMoney();
//				}
//
//			});
			ssc_btn_touzhu = (ImageButton) iV.findViewById(R.id.ssc_btn_touzhu);
			ssc_btn_touzhu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					beginTouZhu(); // 1��ʾ��ǰΪ��ʽ
				}
			});
		}
		// ��View���������
		
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));
		
//		chongxuan = (ImageButton) iV.findViewById(R.id.ssc_jisuan_cb);
//		chongxuan.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				shiBallTable.clearAllHighlights();
//                geBallTable.clearAllHighlights();
//				
//				
//			}
//		});
//		mCheckBox = (CheckBox) this.findViewById(R.id.ssc_jixuan_cb);
//		mCheckBox
//				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//					@Override
//					public void onCheckedChanged(CompoundButton buttonView,
//							boolean isChecked) {
//						// TODO Auto-generated method stub
//						if (isChecked) {
//							ChooseNumberDialogSsc iChooseNumberDialog = new ChooseNumberDialogSsc(
//									SscTwoStar.this, dialogType,
//									SscTwoStar.this);
//							iChooseNumberDialog.show();
//							ssc_btn_newSelect.setVisibility(View.VISIBLE);
//							changeTextSumMoney();
//
//						} else {
//							ssc_btn_newSelect.setVisibility(View.INVISIBLE);
//						}
//					}
//				});
	}
    /*
     * *  �������ǻ�ѡView
     *    
     */
	public void create_TwoStar_JiXuan(){
		PublicMethod.recycleBallTable(shiBallTable);
		PublicMethod.recycleBallTable(geBallTable);
		PublicMethod.recycleBallTable(oneBallTable);
		title.setText("ʱʱ�ʶ��ǻ�ѡ");
		buyView.removeAllViews();
     	balls = new Vector<Balls>();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_zhixuan_jixuan, null);
		jixuanZhu = (Spinner)iV.findViewById(R.id.layout_zhixuan_jixuan_spinner);
        jixuanZhu.setSelection(4);
        jixuanZhu.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int position = jixuanZhu.getSelectedItemPosition();
				if (isOnclik) {
					zhuView.removeAllViews();
				    balls = new Vector<Balls>();
					for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
						Balls ball = new Balls(2);
						ball.createBalls();
						balls.add(ball);
					}
					
					createTable(zhuView);
				} else {
					isOnclik = true;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
        
       zhuView = (LinearLayout) iV
  		.findViewById(R.id.layout_zhixuan_linear_zhuma);

      int index = jixuanZhu.getSelectedItemPosition() + 1;
      for (int i = 0; i < index; i++) {
	  Balls ball = new Balls(2);
	  ball.createBalls();
	  balls.add(ball);
}
      createTable(zhuView);
      
      mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.ssq_fushi_seek_beishu);
      mSeekBarBeishu.setOnSeekBarChangeListener(this);
	  iProgressBeishu = 1;

	  mSeekBarQishu = (SeekBar) iV.findViewById(R.id.ssq_fushi_seek_qishu);
	  mSeekBarQishu.setOnSeekBarChangeListener(this);
	  iProgressQishu = 1;

	  mTextBeishu = (TextView) iV.findViewById(R.id.ssq_fushi_text_beishu);
	  mTextBeishu.setText("" + iProgressBeishu);
	  mTextQishu = (TextView) iV.findViewById(R.id.ssq_fushi_text_qishu);
	  mTextQishu.setText("" + iProgressQishu);

		/*
		 * ����Ӽ�ͼ�꣬��seekbar�������ã�
		 * 
		 * @param int idFind, ͼ���id View iV, ��ǰ��view final int isAdd, 1��ʾ�� -1��ʾ��
		 * final SeekBar mSeekBar
		 * 
		 * @return void
		 */
		setSeekWhenAddOrSub(R.id.ssq_fushi_seekbar_subtract_beishu, iV, -1,
				mSeekBarBeishu, true);
		setSeekWhenAddOrSub(R.id.ssq_fushi_seekbar_add_beishu, iV, 1,
				mSeekBarBeishu, true);
		setSeekWhenAddOrSub(R.id.ssq_fushi_seekbar_subtract_qihao, iV, -1,
				mSeekBarQishu, false);
		setSeekWhenAddOrSub(R.id.ssq_fushi_seekbar_add_qishu, iV, 1,
				mSeekBarQishu, false);

		ssq_b_touzhu_fushi = (ImageButton) iV
				.findViewById(R.id.ssq_fushi_b_touzhu);
		ssq_b_touzhu_fushi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				beginTouZhu();
			}
		});
        
		ImageButton jixuan = (ImageButton) iV
		.findViewById(R.id.jixuan_new_select);
        jixuan.setOnClickListener(new OnClickListener() {

	     @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		zhuView.removeAllViews();
		balls = new Vector<Balls>();
		for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
			Balls ball = new Balls(2);
			ball.createBalls();
			balls.add(ball);
		}
		createTable(zhuView);
	}
});
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));

		
		
	}
	/**
	 * ����ֱѡ��ѡ
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	public void createTable(LinearLayout layout) {
		PublicMethod.recycleBallTable(shiBallTable);
		PublicMethod.recycleBallTable(geBallTable);
		PublicMethod.recycleBallTable(oneBallTable);
		
		for (int i = 0; i < balls.size(); i++) {
			final int index = i;
		//	int redBallViewWidth = SsqActivity.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);
			LinearLayout lines = new LinearLayout(layout.getContext());
			BallTable ballTablege = new BallTable(lines, GE_BALL_START);
			PublicMethod.makeBallTableJiXuanSSC(ballTablege, iScreenWidth,
					BallResId, balls.get(i).getball(),this);
			
			ImageButton delet = new ImageButton(lines.getContext());

			delet.setBackgroundResource(R.drawable.shanchu);
			delet.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (balls.size() > 1) {
						zhuView.removeAllViews();
						balls.remove(index);
						isOnclik = false;
						jixuanZhu.setSelection(balls.size() - 1);
						createTable(zhuView);
					} else {
						Toast.makeText(
								SscTwoStar.this,
								getResources().getText(
										R.string.zhixuan_jixuan_toast),
								Toast.LENGTH_SHORT).show();

					}

				}
			});
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			param.setMargins(param.rightMargin, 13, 0, 0);
			lines.setGravity(Gravity.CENTER_HORIZONTAL);
			lines.addView(delet, param);
			layout.addView(lines);
		}

	}

	/**
	 * ��ӦС�򱻵���Ļص�����
	 * 
	 * @param View
	 *            v �������view
	 * 
	 * @return void
	 */
	@Override
	public void onClick(View v) {
		
		int iBallId = v.getId();
		
		if (iBallId >= SHI_BALL_START && iBallId < GE_BALL_START) {
			int iBallViewId = v.getId() - SHI_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
			
				changeBuyViewByRule(iCurrentButton, 0, iBallViewId);
			}
		} else if (iBallId >= GE_BALL_START && iBallId < ONE_BALL_START) {
			int iBallViewId = v.getId() - GE_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				PublicMethod.myOutput("----- blue:" + iBallViewId);
			
				changeBuyViewByRule(iCurrentButton, 1, iBallViewId);
			}
		} else {
			int iBallViewId = v.getId() - ONE_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				
				changeBuyViewByRule(iCurrentButton, 3, iBallViewId);
			}
		}
		changeTextSumMoney();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		
		if (progress < 1)
			seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();

		switch (seekBar.getId()) {
		case R.id.ssc_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			changeTextSumMoney();
			break;
		case R.id.ssc_seek_qishu:
			iProgressQishu = iProgress;
			mTextQishu.setText("" + iProgressQishu);
			break;
		case R.id.ssq_fushi_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			break;
		case R.id.ssq_fushi_seek_qishu:
			iProgressQishu = iProgress;
			mTextQishu.setText("" + iProgressQishu);
			break;
		default:
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
	

		switch (checkedId) {
		case 0: // ��ѡ
			setCurrentTab(0);
			break;
		
		case 1: // ����
			setCurrentTab(1);
			break;
		case 2: // ��ֵ
			setCurrentTab(2);
			break;
		case 3:
			setCurrentTab(3);
		    break;
		
			
		}

	}

	@Override
	public void onCancelClick() {
		

	}

	@Override
	public void onOkClick(int[] aNums) {
		
		switch (iCurrentButton) {
		case PublicConst.SSC_TWO_STAR_ZU_XUAN:
			iOneBallNumber = aNums[0];
			oneBallTable.sscRandomChooseConfigChange(iOneBallNumber);
			break;
		case PublicConst.SSC_TWO_STAR:
			iShiBallNumber = aNums[0];
			iGeBallNumber = aNums[1];
			shiBallTable.sscRandomChooseConfigChange(iShiBallNumber);
			geBallTable.sscRandomChooseConfigChange(iGeBallNumber);
			break;
		case PublicConst.SSC_TWO_STAR_BAO_DIAN:
			iOneBallNumber = aNums[0];
			oneBallTable.sscRandomChooseConfigChange(iOneBallNumber);
			break;
		case PublicConst.SSC_TWO_STAR_HE_ZHI:
			iOneBallNumber = aNums[0];
			oneBallTable.sscRandomChooseConfigChange(iOneBallNumber);
			break;
		}
		changeTextSumMoney();

	}

	/**
	 * ��ʾ��Ϣ
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	public void changeTextSumMoney() {

		int iZhuShu = getZhuShu();
		if (iZhuShu != 0) {
			String iTempString = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
			mTextSumMoney.setText(iTempString);
		} else {
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));
		}
	}

	/**
	 *ʱʱ��ע���ļ���
	 * 
	 * @return int ����ע�� ��������
	 */
	private int getZhuShu() {

		int beishu = mSeekBarBeishu.getProgress();
		int iReturnValue = 0;
		switch (iCurrentButton) {
		case PublicConst.SSC_TWO_STAR_ZU_XUAN:
			int one = oneBallTable.getHighlightBallNums();
			iReturnValue = (int) (PublicMethod.zuhe(2, one) * beishu);
			break;
		case PublicConst.SSC_TWO_STAR:
			int shi = shiBallTable.getHighlightBallNums();
			int ge = geBallTable.getHighlightBallNums();
			iReturnValue = shi * ge * beishu;
			break;
		case PublicConst.SSC_TWO_STAR_BAO_DIAN:
			iReturnValue = getSscBaoDianZhushu() * beishu;
			break;
		case PublicConst.SSC_TWO_STAR_HE_ZHI:
			iReturnValue = getSscHeZhiZhushu() * beishu;
			break;
		}
		return iReturnValue;
	}

	/**
	 * ���ʱʱ�ʶ��ǰ���ע��
	 * 
	 * @return ����ע��
	 */
	private int getSscBaoDianZhushu() {
		int iZhuShu = 0;
		int[] BallNos = oneBallTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���1�����0������ʵ��ѡ��ļ�ȥ1
		int[] BallNoZhushus = { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 5, 4, 4, 3, 3, 2,
				2, 1, 1 };// 0~27

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i] - 1) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С������1��ʼ���ʼ�ȥ1
					// ɾ������ cc 20100713
					iZhuShu += BallNoZhushus[j];// *iProgressBeishu;
				}
			}
		}
		return iZhuShu;
	}

	/**
	 * ���ʱʱ�ʶ��Ǻ�ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getSscHeZhiZhushu() {
		int iZhuShu = 0;
		int[] BallNos = oneBallTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���1�����0������ʵ��ѡ��ļ�ȥ1
//		int[] BallNoZhushus = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5,
//				4, 3, 2, 1 };// 0~27
		int[] BallNoZhushus={1,1,2,2,3,3,4,4,5,5,5,4,4,3,3,2,2,1,1};
		

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i]) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С������1��ʼ���ʼ�ȥ1
					// ɾ������ cc 20100713
					iZhuShu += BallNoZhushus[j];// *iProgressBeishu;
				}
			}
		}
		return iZhuShu;
	}

	/**
	 * �����淨�ı䵱ǰView ����Ӧ
	 * 
	 * @param aWhichTopButton
	 *            ��ǰ������ǩλ��
	 * @param aWhichGroupBall
	 *            �ڼ���С���絥ʽ������С�򣬴�0��ʼ����
	 * @param aBallViewId
	 *            ��clickС���id����0��ʼ������С������ʾ������Ϊid+1
	 */
	private void changeBuyViewByRule(int aWhichTopButton, int aWhichGroupBall,
			int aBallViewId) {

		buyTicket(aWhichGroupBall, aBallViewId);

	}

	/**
	 * ��ʽ�淨�ı�View
	 * 
	 * @param int aWhichGroupBall �������С��λ��
	 * 
	 * @param int aBallViewId С��id
	 * 
	 * @return void
	 */
	private void buyTicket(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 0) {
			int iChosenBallSum = 10;
			int isHighLight = shiBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			PublicMethod.showedittextnumber(shiedittext, shiBallTable,getString(R.string.sscshiweitext));
		} else if (aWhichGroupBall == 1) {
			int iChosenBallSum = 10;
			int isHighLight = geBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			PublicMethod.showedittextnumber(geeditetext, geBallTable, getString(R.string.sscgeweitext));
		} else if (aWhichGroupBall == 3) {
			int iChosenBallSum = 0;
			if (iCurrentButton == PublicConst.SSC_TWO_STAR_ZU_XUAN) {
				iChosenBallSum = 7;
			} else {
				iChosenBallSum = 19;
			}
			int isHighLight = oneBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			PublicMethod.showedittextnumber(geeditetext, oneBallTable, getString(R.string.sscgeweitext));
		}
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
	 * �Ӽ���ť�¼���������
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	private void setSeekWhenAddOrSub(int idFind, View iV, final int isAdd,
			final SeekBar mSeekBar, final boolean isBeiShu) {
		ImageButton subtractBeishuBtn = (ImageButton) iV.findViewById(idFind);
		subtractBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isBeiShu) {
					if (isAdd == 1) {
						iProgressBeishu++;
						if (iProgressBeishu > 99)
							iProgressBeishu = 99;
						mSeekBar.setProgress(iProgressBeishu);
					} else {
						iProgressBeishu--;
						if (iProgressBeishu < 1)
							iProgressBeishu = 1;
						mSeekBar.setProgress(iProgressBeishu);
					}
				} else {
					if (isAdd == 1) {
						iProgressQishu++;
						if (iProgressQishu > 99) {
							iProgressQishu = 99;
						}
						mSeekBar.setProgress(iProgressQishu);
					} else {
						iProgressQishu--;
						if (iProgressQishu < 1) {
							iProgressQishu = 1;
						}
						mSeekBar.setProgress(iProgressQishu);
					}

				}
			}
		});
	}

	
	/**
	 * ʱʱ��Ͷע����
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	private void beginTouZhu() {
		
		int beishu = mSeekBarBeishu.getProgress();
		int iZhuShu = getZhuShu();
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
				SscTwoStar.this, "addInfo");
		String sessionIdStr = pre.getUserLoginInfo("sessionid");
		if (sessionIdStr==null||sessionIdStr.equals("")) {
			Intent intentSession = new Intent(SscTwoStar.this, UserLogin.class);
			startActivityForResult(intentSession, 0);
		} else {
			boolean isTouzhu = false;
			switch (iCurrentButton) {
			case PublicConst.SSC_TWO_STAR_ZU_XUAN:
				int one = oneBallTable.getHighlightBallNums();
				if (one < 2) {
					alert1("������ѡ��һע��");
				} else if (iZhuShu * 2 > 20000) {
					DialogExcessive();
				} else if (one > 7) {
					alert1("������ѡ��С��ĸ������Ϊ7����");
				} else {
					isTouzhu = true;
				}
				break;
			case PublicConst.SSC_TWO_STAR:
				 if(iszhixuan){
					 if(balls.size()==0){
					  alert1("������ѡ��1ע��Ʊ");
					}else{
						String sTouzhuAlert = "";
						sTouzhuAlert = getTouzhuAlertJixuan();
						alert_jixuan(sTouzhuAlert);
					}
					 
				    }else{
				int shi = shiBallTable.getHighlightBallNums();
				int ge = geBallTable.getHighlightBallNums();
				if (shi == 0 | ge == 0) {
					alert1("������ѡ��һע��");
				} else if (iZhuShu * 2 > 20000) {
					DialogExcessive();
				} else if (shi + ge > 12) {
					alert1("�����λ��С��ĸ������Ϊ12����");
				} else {
					isTouzhu = true;
				}
				    }
				break;
//			case PublicConst.SSC_TWO_STAR_BAO_DIAN:
//				int one1 = oneBallTable.getHighlightBallNums();
//				if (one1 == 0) {
//					alert1("������ѡ��һע��");
//				} else if (iZhuShu * 2 > 20000) {
//					DialogExcessive();
//				} else if (one1 > 8) {
//					alert1("������㣬С��ĸ������Ϊ8����");
//				} else {
//					isTouzhu = true;
//				}
//				break;
			case PublicConst.SSC_TWO_STAR_HE_ZHI:
				int one2 = oneBallTable.getHighlightBallNums();
				if (one2 == 0) {
					alert1("������ѡ��һע��");
				} else if (iZhuShu * 2 > 20000) {
					DialogExcessive();
				} else if (one2 > 8) {
					alert1("�����ֵ��С��ĸ������Ϊ8����");
				} else {
					isTouzhu = true;
				}
				break;
			default:
				break;
			}
			if (isTouzhu) {

				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				alert(sTouzhuAlert);
			}
		}
	}

	/**
	 * ����Ͷע����2��Ԫʱ�ĶԻ���
	 */
	private void DialogExcessive() {
		AlertDialog.Builder builder = new AlertDialog.Builder(SscTwoStar.this);
		builder.setTitle("Ͷעʧ��");
		builder.setMessage("����Ͷע���ܴ���2��Ԫ");
		// ȷ��
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}

				});
		builder.show();
	}

	private int getBeishu() {
		return mSeekBarBeishu.getProgress();

	}

	private int getQishu() {
		return mSeekBarQishu.getProgress();

	}

	private String getTouzhuAlert() {
		int iZhuShu = getZhuShu();

	
		
		switch (iCurrentButton) {
		
		case PublicConst.SSC_TWO_STAR_ZU_XUAN:
			int[] one = oneBallTable.getHighlightBallNOs();
			return "��" + batchCode + "��\n" 
					+ "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"+ "\n" 
					+"ע�룺" + "\n" 
					+ getStrZhuMa(one)
					+ "\n" + "ȷ��֧����";

		case PublicConst.SSC_TWO_STAR:
			int[] shi = shiBallTable.getHighlightBallNOs();
			int[] ge = geBallTable.getHighlightBallNOs();
			return "��" + batchCode + "��\n" 
			+ "ע����"
			+ iZhuShu / mSeekBarBeishu.getProgress()
			+ "ע"
			+ "\n"
			+ // ע���������ϱ��� �³� 20100713
			"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
			+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
			+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
			+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"+ "\n" 
			+"ע�룺" + "\n" +"ʮλ:"+getStrZhuMa(shi) + "\n" + "��λ:" + getStrZhuMa(ge)
			+ "\n" + "ȷ��֧����";

		case PublicConst.SSC_TWO_STAR_BAO_DIAN:
			int[] one1 = oneBallTable.getHighlightBallNOs();
			return "ע�룺" + "\n" + "��" + batchCode + "��\n" + "���㣺"
					+ getStrZhuMa(one1) + "\n"
					+ "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"
					+ "\n" + "ȷ��֧����";

		case PublicConst.SSC_TWO_STAR_HE_ZHI:
			int[] one2 = oneBallTable.getHighlightBallNOs();
			return  "��" + batchCode + "��\n" + "��ֵ��"
					+ "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"+ "\n" 
					+"ע�룺" + "\n" + getStrZhuMa(one2) 
					+ "\n" + "ȷ��֧����";

		default:
			break;
		}
		return null;
	}

	/**
	 * Ͷעȷ����ʾ��
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	private void alert(String string) {
		Builder dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����")
				.setMessage(string).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ssc_btn_touzhu.setClickable(false);
								ssc_btn_touzhu
										.setImageResource(R.drawable.touzhuup_n);
								showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
								// TODO Auto-generated method stub
								Thread t = new Thread(new Runnable() {
									int iZhuShu = getZhuShu();
									int iQiShu = getQishu();
									String[] strCode = null;
									int iBeiShu = mSeekBarBeishu.getProgress();
									String zhuma = getZhuMa();

									@Override
									public void run() {

										if (mTimesMoney == 2) {
											strCode = payNew(zhuma, ""
													+ iBeiShu,
													iZhuShu * 2 + "", iQiShu
															+ "");
										} else if (mTimesMoney == 3) {
											strCode = payNew(zhuma, ""
													+ iBeiShu,
													iZhuShu * 3 + "", iQiShu
															+ "");
										}

										ssc_btn_touzhu.setClickable(true);

										Message msg1 = new Message();
										msg1.what = 10;
										handler.sendMessage(msg1);

										if (strCode[0].equals("0000")
												&& strCode[1].equals("000000")) {
											Message msg = new Message();
											msg.what = 6;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("070002")) {
											Message msg = new Message();
											msg.what = 7;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("0000")
												&& strCode[1].equals("000001")) {
											Message msg = new Message();
											msg.what = 4;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("1007")) {
											Message msg = new Message();
											msg.what = 2;
											handler.sendMessage(msg);
										} else if (strCode[1].equals("002002")) {
											Message msg = new Message();
											msg.what = 3;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("040006")
												|| strCode[0].equals("201015")) {
											Message msg = new Message();
											msg.what = 1;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("00")
												&& strCode[1].equals("00")) {
											Message msg = new Message();
											msg.what = 8;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("040003")) {
											Message msg = new Message();
											msg.what = 11;
											handler.sendMessage(msg);
										} else if (strCode[1].equals("1002")) {
											Message msg = new Message();
											msg.what = 14;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("20100706")) {
											Message msg = new Message();
											msg.what = 2;
											handler.sendMessage(msg);
										}else {
											Message msg = new Message();
											msg.what = 9;
											handler.sendMessage(msg);
										}
									}

								});
								t.start();
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
	
	/*��ѡͶע��ʾ��*/
	private void alert_jixuan(String string) {
		sensor.stopAction();
		Dialog dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����")
				.setMessage(string).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ssc_btn_touzhu.setClickable(false);
								ssc_btn_touzhu
										.setImageResource(R.drawable.touzhuup_n);

								showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
								// TODO Auto-generated method stub
								Thread t = new Thread(new Runnable() {
								    int iQiShu = getQishu();
									String[] strCode = null;
									int iBeiShu = mSeekBarBeishu.getProgress();
									int iZhuShu =balls.size()*iBeiShu;
									
									String zhuma = getZhuMa();
									

									@Override
									public void run() {

										if (mTimesMoney == 2) {
											strCode = payNew(zhuma, ""
													+ iBeiShu,
													iZhuShu * 2 + "", iQiShu
															+ "");
										} else if (mTimesMoney == 3) {
											strCode = payNew(zhuma, ""
													+ iBeiShu,
													iZhuShu * 3 + "", iQiShu
															+ "");
										}

										ssc_btn_touzhu.setClickable(true);

										Message msg1 = new Message();
										msg1.what = 10;
										handler.sendMessage(msg1);

										if (strCode[0].equals("0000")
												&& strCode[1].equals("000000")) {
											Message msg = new Message();
											msg.what = 6;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("070002")) {
											Message msg = new Message();
											msg.what = 7;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("0000")
												&& strCode[1].equals("000001")) {
											Message msg = new Message();
											msg.what = 4;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("1007")) {
											Message msg = new Message();
											msg.what = 2;
											handler.sendMessage(msg);
										} else if (strCode[1].equals("002002")) {
											Message msg = new Message();
											msg.what = 3;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("040006")
												|| strCode[0].equals("201015")) {
											Message msg = new Message();
											msg.what = 1;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("00")
												&& strCode[1].equals("00")) {
											Message msg = new Message();
											msg.what = 8;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("040003")) {
											Message msg = new Message();
											msg.what = 11;
											handler.sendMessage(msg);
										} else if (strCode[1].equals("1002")) {
											Message msg = new Message();
											msg.what = 14;
											handler.sendMessage(msg);
										}else if (strCode[0].equals("20100706")) {
											Message msg = new Message();
											msg.what = 2;
											handler.sendMessage(msg);
										} else {
											Message msg = new Message();
											msg.what = 9;
											handler.sendMessage(msg);
										}
									}

								});
								t.start();
							}
						}).setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
							}

						}).create();
		  dialog.show();
	      dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
					sensor.startAction();
				}
			}) ;

	}

	
	/*��ѡͶע������
	 * */
		private String getTouzhuAlertJixuan() {
			String zhumaString = "";
			for (int i = 0; i < balls.size(); i++) {
				zhumaString +=balls.get(i).getzhuma()+ "\n";
			}

			int beishu = mSeekBarBeishu.getProgress();
			int iZhuShu = balls.size() * beishu;
			return "��" + batchCode + "��\n" 
			        + "ע����"
					+ balls.size()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + beishu + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (balls.size() * 2 * beishu) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"
					+ "\n" + "ע�룺" + "\n" +"ʮλ-��λ"+"\n"+ zhumaString + "ȷ��֧����";
		}
	/**
	 * ��ȡע��
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	public String getZhuMa() {
		String t_str = "";

		
		int[] shi = shiBallTable.getHighlightBallNOs();
		int[] ge = geBallTable.getHighlightBallNOs();
		switch (iCurrentButton) {
		case PublicConst.SSC_TWO_STAR_ZU_XUAN:
			int[] one = oneBallTable.getHighlightBallNOs();
			t_str = "F2|";
			t_str += getStr(one);
			break;
		case PublicConst.SSC_TWO_STAR:
			if(iszhixuan){
				
				t_str = "2D|";
				t_str +=  getstrjixuan();
			}else{
				t_str = "2D|";
				t_str += "-," + "-," + "-," + getStr(shi)+","+getStr(ge);
			}
			
				break;
	
		case PublicConst.SSC_TWO_STAR_BAO_DIAN:
			int[] one1 = oneBallTable.getHighlightBallNOs();
			t_str = "S2|";
			t_str += getStrZhuMa(one1);
			break;
		case PublicConst.SSC_TWO_STAR_HE_ZHI:
			int[] one2 = oneBallTable.getHighlightBallNOs();
			//��ֵΪ��ѡ��ֵ�����㣩��ΪS2�����Ǻ�ֵΪH2
			t_str = "S2|";
			t_str += getStrZhuMa(one2);
			break;
		}
		return t_str;

	}
    
	public String getstrjixuan(){
		String str="-," + "-," + "-";
		for (int i = 0; i < balls.size(); i++) {
		   
			int ge[]= balls.get(i).getball();
			for(int j=0;j<ge.length;j++){
				str += ","+ge[j];
				
			}
	        if(i!=balls.size()-1){
			str+=";2D|"+"-," + "-," + "-";	
			}
				
		}
	
		return str;
		
	}
	public String getStr(int balls[]) {
		String str = "";
		for (int i = 0; i < balls.length; i++) {
			str += (balls[i]);
		}
		return str;

	}
	
	

	public String getStrZhuMa(int balls[]) {
		String str = "";
		for (int i = 0; i < balls.length; i++) {
			str += (balls[i]);
			if (i != (balls.length - 1))
				str += ",";
		}
		return str;

	}
	
	// Ͷע�½ӿ� 20100711�³�
	protected String[] payNew(String betCode, String lotMulti, String amount,
			String qiShu) {
	
		String[] error_code = null;
		BettingInterface betting = BettingInterface.getInstance();

		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,"addInfo");
		String sessionid = shellRW.getUserLoginInfo("sessionid");
		String phonenum = shellRW.getUserLoginInfo("phonenum");
		if (mTimesMoney == 2) {
			error_code = betting.BettingTC(phonenum, "T01007", betCode,
					lotMulti, amount, "2", qiShu, sessionid, batchCode);
		} else if (mTimesMoney == 3) {
			error_code = betting.BettingTC(phonenum, "T01007", betCode,
					lotMulti, amount, "3", qiShu, sessionid, batchCode);
		}

		return error_code;
	}

	// ��ʾ��1 ��������ѡ�����
	// fqc delete ɾ��ȡ����ť 7/14/2010
	private void alert1(String string) {
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (resultCode) {
		case RESULT_OK:
			
			beginTouZhu();
			// }
			break;
		default:
			Toast.makeText(SscTwoStar.this, "δ��¼�ɹ���", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	class SscSensor extends SensorActivity {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.palmdream.netintface.SensorActivity#action()
		 */
		public SscSensor(Context context) {
			getContext(context);
		}

		@Override
		public void action() {
			zhuView.removeAllViews();
			balls = new Vector<Balls>();
			for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
				Balls ball = new Balls(2);
				ball.createBalls();
				balls.add(ball);
			}
		
			createTable(zhuView);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		PublicMethod.recycleBallTable(shiBallTable);
		PublicMethod.recycleBallTable(geBallTable);
		PublicMethod.recycleBallTable(oneBallTable);
		
	}
}
