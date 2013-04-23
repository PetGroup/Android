/**
 * 
 */
package com.ruyicai.activity.game.ssc;

import java.text.SimpleDateFormat;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.RuyicaiActivityManager;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.game.ssq.SsqActivity;
import com.ruyicai.activity.home.RuyicaiAndroid;
import com.ruyicai.dialog.Accoutdialog;
import com.ruyicai.handler.MyDialogListener;
import com.ruyicai.net.JrtLot;
import com.ruyicai.net.transaction.BettingInterface;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.SensorActivity;
import com.ruyicai.util.ShellRWSharesPreferences;
import com.ruyicai.view.RadioStateDrawable;
import com.ruyicai.view.TabBarButton;

/**
 * @author Administrator
 * 
 */
public class SscZhiXuan extends Activity implements OnClickListener, // С�򱻵��
		// onClick
		SeekBar.OnSeekBarChangeListener, // SeekBar�ı� onProgressChanged
		RadioGroup.OnCheckedChangeListener, // ��ѡ��ѡ��仯 onCheckedChangeListener
		MyDialogListener {

	// ������
	private static final int DIALOG1_KEY = 0;
	private ProgressDialog progressdialog;
	private ProgressDialog getlotdialog;
	private LinearLayout buyView;
	private int iScreenWidth;
	// ����
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };
	private RadioGroup.LayoutParams topButtonLayoutParams;
	private RadioGroup topButtonGroup;
	private int iButtonNum = 2;
	int topButtonIdOn[] = {R.drawable.ssc_onstargoucaib, R.drawable.ssc_onestarjixuanb
			 };
	int topButtonIdOff[] = {R.drawable.ssc_onstargoucai, R.drawable.ssc_onestarjixuan };

	SeekBar mSeekBarBeishu;
	SeekBar mSeekBarQishu;
	TextView mTextBeishu;
	TextView mTextQishu;
	TextView mTextSumMoney;
	ImageButton ssc_btn_touzhu;
	
	ImageButton chongxuan;

	BallTable wanBallTable = null;
	BallTable qianBallTable = null;
	BallTable baiBallTable = null;
	BallTable shiBallTable = null;
	BallTable geBallTable = null;
	private int BallResId[] = { R.drawable.grey, R.drawable.red };
	public static final int WAN_BALL_START = 0x80000001; // С����ʼID
	public static final int QIAN_BALL_START = 0x80000011; // С����ʼID
	public static final int BAI_BALL_START = 0x80000021; // С����ʼID
	public static final int SHI_BALL_START = 0x80000031; // С����ʼID
	public static final int GE_BALL_START = 0x80000041; // С����ʼID

	int iProgressBeishu = 1;
	int iProgressQishu = 1;

	private int iCurrentButton; // ����button��ǰ��λ��
	private int dialogType = 0;
	private int iGeBallNumber;// С���������ѡ��
	private int mTimesMoney = 2;
    String batchCode;
	String endTime;
	String systime;
	Timer timer;
	int countMinute;
	int countSecond;
	
	private TextView term;
	private TextView title;
	private TextView one_star;
	private Spinner jixuanZhu;
	private LinearLayout zhuView;
	private Vector<Balls> balls = new Vector();
	ImageButton ssq_b_touzhu_danshi;
	ImageButton ssq_b_touzhu_fushi;
	private boolean isjixuan=false;
	

	private  EditText  geeditText;
	
	private  SscSensor sensor=new SscSensor(this);
	
	private  boolean   isOnclik=true;
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
				    Accoutdialog.getInstance().createAccoutdialog(SscZhiXuan.this, getResources().getString(R.string.goucai_Account_dialog_msg).toString());
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
				if(isFinishing() == false){
				    PublicMethod.showDialog(SscZhiXuan.this);
				}
//				wanBallTable.clearAllHighlights();
//				qianBallTable.clearAllHighlights();
//				baiBallTable.clearAllHighlights();
//				shiBallTable.clearAllHighlights();
				if(isjixuan){
					zhuView.removeAllViews();  
				}else{
				geBallTable.clearAllHighlights();
				geeditText.setText("");
				changeTextSumMoney();
				}
				Intent intent = new Intent(UserLogin.SUCCESS);
				sendBroadcast(intent);
				

				break;
		
			case 6:
				// //��Ҫ���AlertDialog��ʾ�û���¼�ɹ�
				Log.v("t", "4");
				progressdialog.dismiss();
				if(isFinishing() == false){
				    PublicMethod.showDialog(SscZhiXuan.this);
				}
				// Ͷע�ɹ������С��
//				wanBallTable.clearAllHighlights();
//				qianBallTable.clearAllHighlights();
//				baiBallTable.clearAllHighlights();
//				shiBallTable.clearAllHighlights();
				if(isjixuan){
					zhuView.removeAllViews();  
				}else{
				geBallTable.clearAllHighlights();
				geeditText.setText("");
				changeTextSumMoney();
				}
				Intent intent2 = new Intent(UserLogin.SUCCESS);
				sendBroadcast(intent2);
				

				break;
			case 7:
				progressdialog.dismiss();

				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(SscZhiXuan.this,
						UserLogin.class);
				startActivity(intentSession);
				break;
			case 8:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)
						.show();
			
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 10:
			
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

		
		setContentView(R.layout.layout_main_buy);
		title = (TextView) findViewById(R.id.layout_main_text_title);
		term = (TextView) findViewById(R.id.layout_main_text_time);
		Spinner ssc_zhonglei=(Spinner)findViewById(R.id.ssc_zhonglei_spinner);
		ssc_zhonglei.setVisibility(View.VISIBLE);
		ssc_zhonglei.setSelection(0, true);
		ssc_zhonglei.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch(position){
				case 1:
					Intent intent = new Intent(SscZhiXuan.this,SscTwoStar.class);
					startActivity(intent);
					SscZhiXuan.this.finish();
					break;
				case 2:
					Intent intent2 = new Intent(SscZhiXuan.this,SscThreeStar.class);
					startActivity(intent2);
					SscZhiXuan.this.finish();
					break;
				case 3:
					Intent intent3 = new Intent(SscZhiXuan.this,SscFiveStar.class);
					startActivity(intent3);
					SscZhiXuan.this.finish();
					break;
				case 4:
					Intent intent4 = new Intent(SscZhiXuan.this,SscBigSmall.class);
					startActivity(intent4);
					SscZhiXuan.this.finish();
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
//		TextView title = (TextView) findViewById(R.id.goucaitouzhu_title);
//		title.setText(getResources().getString(R.string.ssc_zhixuan));

	
	
		getLotNo();

		// ��ȡ������id
		buyView = (LinearLayout) findViewById(R.id.layout_buy);
		initTopButtons();// ��ʼ��������ǩ
		//commit();// ����
		iCurrentButton = PublicConst.SSC_ONE_STAR;
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
	 */
	public void times(int endHour, int endMinute, int endSecond) {
	//	Time t = new Time(); 
	//        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd HHmmss");
	        
	      Date date = new Date(new Long(systime));
	

	//	t.setToNow(); // ȡ��ϵͳʱ�䡣
//		  int hour = date.hour + 8;// ;
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
		if (isjixuan) {
			topButtonIdOn[1] = R.drawable.ssc_onstarzhixuanb;
			topButtonIdOff[1] = R.drawable.ssc_onstarzhixuan;
		} else {
			topButtonIdOn[1] = R.drawable.ssc_onestarjixuanb;
			topButtonIdOff[1] = R.drawable.ssc_onestarjixuan;
		}

	//	int optimum_visible_items_in_portrait_mode = iButtonNum;

		int screen_width = getWindowManager().getDefaultDisplay().getWidth();
		int width;
		width = screen_width / iButtonNum;
		RadioStateDrawable.other_width = width;
		RadioStateDrawable.other_screen_width = screen_width;

		topButtonLayoutParams = new RadioGroup.LayoutParams(width,
				RadioGroup.LayoutParams.WRAP_CONTENT);

		for (int i = 0; i < iButtonNum; i++) {
			TabBarButton tabButton = new TabBarButton(this);
			tabButton.setState(topButtonIdOn[i], topButtonIdOff[i],2);
			tabButton.setId(i);
			tabButton.setGravity(Gravity.CENTER);
			topButtonGroup.addView(tabButton, i, topButtonLayoutParams);
 		   
		}

	
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
		case 0: // ���ع��ʴ���
			sensor.stopAction();
			Intent intent=new Intent(this,RuyicaiAndroid.class);
			startActivity(intent);
		    finish();

		case 1: // һ��ֱѡ

		
		break;

		
		}
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
	 * ����
	 */
	public void createView(int Type) {
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = null;
		int BallViewNum = 10;
	//	int BallViewWidth = SscZhiXuan.BALL_WIDTH;
		iScreenWidth = PublicMethod.getDisplayWidth(this);
		switch (Type) {
//		case PublicConst.SSC_FIVE_STAR:
//			dialogType = PublicConst.SSC_FIVE_STAR;
//			iV = (LinearLayout) inflate.inflate(R.layout.layout_ssc_five_star,
//					null);
//			title.setText("ʱʱ��һ��ֱѡ");
//			TextView alert = (TextView) iV.findViewById(R.id.ssc_text_alert);
//			alert.setVisibility(TextView.GONE);
//			wanBallTable = makeBallTable(iV, R.id.ssc_table_wan, iScreenWidth,
//					BallViewNum, BallViewWidth, BallResId, WAN_BALL_START, 0);
//
//			qianBallTable = makeBallTable(iV, R.id.ssc_table_qian,
//					iScreenWidth, BallViewNum, BallViewWidth, BallResId,
//					QIAN_BALL_START, 0);
//			baiBallTable = makeBallTable(iV, R.id.ssc_table_bai, iScreenWidth,
//					BallViewNum, BallViewWidth, BallResId, BAI_BALL_START, 0);
//			shiBallTable = makeBallTable(iV, R.id.ssc_table_shi, iScreenWidth,
//					BallViewNum, BallViewWidth, BallResId, SHI_BALL_START, 0);
//			geBallTable = makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
//					BallViewNum, BallViewWidth, BallResId, GE_BALL_START, 0);
//			break;
//		case PublicConst.SSC_THREE_STAR:
//			dialogType = PublicConst.SSC_THREE_STAR;
//			iV = (LinearLayout) inflate.inflate(R.layout.layout_ssc_three_star,
//					null);
//
//			baiBallTable = makeBallTable(iV, R.id.ssc_table_bai, iScreenWidth,
//					BallViewNum, BallViewWidth, BallResId, BAI_BALL_START, 0);
//			shiBallTable = makeBallTable(iV, R.id.ssc_table_shi, iScreenWidth,
//					BallViewNum, BallViewWidth, BallResId, SHI_BALL_START, 0);
//			geBallTable = makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
//					BallViewNum, BallViewWidth, BallResId, GE_BALL_START, 0);
//			break;
//		case PublicConst.SSC_TWO_STAR:
//			dialogType = PublicConst.SSC_TWO_STAR;
//			iV = (LinearLayout) inflate.inflate(R.layout.layout_ssc_two_star,
//					null);
//			shiBallTable = makeBallTable(iV, R.id.ssc_table_shi, iScreenWidth,
//					BallViewNum, BallViewWidth, BallResId, SHI_BALL_START, 0);
//			geBallTable = makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
//					BallViewNum, BallViewWidth, BallResId, GE_BALL_START, 0);
//			break;
		case PublicConst.SSC_ONE_STAR:
			title.setText("ʱʱ��һ��ֱѡ");
			dialogType = PublicConst.SSC_ONE_STAR;
			iV = (LinearLayout) inflate.inflate(R.layout.layout_ssc_one_star,
					null);
			one_star=(TextView)iV.findViewById(R.id.ssc_onestar);
			geBallTable = PublicMethod.makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
					BallViewNum, BallResId, GE_BALL_START, 0,this,this);
			geeditText=(EditText)iV.findViewById(R.id.ssc_zhixuanonestar_edit);
			chongxuan = (ImageButton) iV.findViewById(R.id.ssc_jisuan_cb);
			chongxuan.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					geeditText.setText("");
	                geBallTable.clearAllHighlights();
	                changeTextSumMoney();
					
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
//							SscZhiXuan.this, dialogType, SscZhiXuan.this);
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
		// TODO Auto-generated method stub
		int iBallId = v.getId();
		// red ball
		if (iBallId >= WAN_BALL_START && iBallId < QIAN_BALL_START) {
			int iBallViewId = v.getId() - WAN_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				changeBuyViewByRule(iCurrentButton, 0, iBallViewId);// �������

			}
		} else if (iBallId >= QIAN_BALL_START && iBallId < BAI_BALL_START) {
			int iBallViewId = v.getId() - QIAN_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				PublicMethod.myOutput("----- blue:" + iBallViewId);
				changeBuyViewByRule(iCurrentButton, 1, iBallViewId);// ��������
			}
		} else if (iBallId >= BAI_BALL_START && iBallId < SHI_BALL_START) {
			int iBallViewId = v.getId() - BAI_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				PublicMethod.myOutput("----- blue:" + iBallViewId);
				changeBuyViewByRule(iCurrentButton, 2, iBallViewId);// ��������
			}
		} else if (iBallId >= SHI_BALL_START && iBallId < GE_BALL_START) {
			int iBallViewId = v.getId() - SHI_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				PublicMethod.myOutput("----- blue:" + iBallViewId);
				changeBuyViewByRule(iCurrentButton, 3, iBallViewId);// ��������
			}
		} else {
			int iBallViewId = v.getId() - GE_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				PublicMethod.myOutput("----- blue:" + iBallViewId);
				changeBuyViewByRule(iCurrentButton, 4, iBallViewId);// ��������
			}

		}

		changeTextSumMoney();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
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
		case 0: // ���ʴ���
			setCurrentTab(0);
			break;
		case 1: // ת��һ�ǻ�ѡ
			iCurrentButton = PublicConst.SSC_ONE_STAR;
			isjixuan=!isjixuan;
		
			if(isjixuan){
			sensor.startAction();
			create_OneStar_JiXuan();
			
			}else{
			sensor.stopAction();
	        createView(iCurrentButton);
			}
			topButtonGroup.setSelected(false);
			commit();
			
			break;
	
		
		}

	}

	@Override
	public void onCancelClick() {
		

	}

	@Override
	public void onOkClick(int[] aNums) {
		
		switch (iCurrentButton) {
		case PublicConst.SSC_ONE_STAR:
			iGeBallNumber = aNums[0];
			geBallTable.sscRandomChooseConfigChange(iGeBallNumber);
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

//		int wan = wanBallTable.getHighlightBallNums();
//		int qian = qianBallTable.getHighlightBallNums();
//
//		int bai = baiBallTable.getHighlightBallNums();
//		int shi = shiBallTable.getHighlightBallNums();
		int ge = geBallTable.getHighlightBallNums();
		int beishu = mSeekBarBeishu.getProgress();
		int iReturnValue = 0;
		switch (iCurrentButton) {
//		case PublicConst.SSC_FIVE_STAR:
//			iReturnValue = wan * qian * bai * shi * ge * beishu;
//			break;
//		case PublicConst.SSC_THREE_STAR:
//			iReturnValue = bai * shi * ge * beishu;
//			break;
//		case PublicConst.SSC_TWO_STAR:
//			iReturnValue = shi * ge * beishu;
//			break;
		case PublicConst.SSC_ONE_STAR:
			iReturnValue = ge * beishu;
			break;
		}
		return iReturnValue;
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
		switch (aWhichTopButton) {
//		case PublicConst.SSC_FIVE_STAR:
//		
//			buyTicket(aWhichGroupBall, aBallViewId);
//			break;
//		case PublicConst.SSC_THREE_STAR:
//			buyTicket(aWhichGroupBall, aBallViewId);
//			break;
//		case PublicConst.SSC_TWO_STAR:
//			buyTicket(aWhichGroupBall, aBallViewId);
//			break;
		case PublicConst.SSC_ONE_STAR:
			buyTicket(aWhichGroupBall, aBallViewId);
			break;
		default:
			break;
		}
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
		if (aWhichGroupBall == 0) { // ������
			int iChosenBallSum = 10;
			// ÿ�ε�����סС���״̬
  
			int isHighLight = wanBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
        

		} else if (aWhichGroupBall == 1) {
			int iChosenBallSum = 10;
			int isHighLight = qianBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			
		} else if (aWhichGroupBall == 2) {
			int iChosenBallSum = 10;
			int isHighLight = baiBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			
		} else if (aWhichGroupBall == 3) {
			int iChosenBallSum = 10;
			int isHighLight = shiBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			
		} else if (aWhichGroupBall == 4) {
			int iChosenBallSum = 10;
			int isHighLight = geBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			PublicMethod.showedittextnumber(geeditText, geBallTable, getString(R.string.sscgeweitext));
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
			}
		}
		return progressdialog;
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
	 * ����BallTable
	 * 
	 * @param LinearLayout
	 *            aParentView ��һ��Layout
	 * @param int aLayoutId ��ǰBallTable��LayoutId
	 * @param int aFieldWidth BallTable����Ŀ�ȣ�����Ļ��ȣ�
	 * @param int aBallNum С�����
	 * @param int aBallViewWidth С����ͼ�Ŀ�ȣ�ͼƬ��ȣ�
	 * @param int[] aResId С��ͼƬId
	 * @param int aIdStart С��Id��ʼ��ֵ
	 * @param int aBallViewText 0:С���0��ʼ��ʾ,1:С���1��ʼ��ʾ ,3С���3��ʼ��ʾ(����3D��ֵ��6��3��ʼ)
	 * @return BallTable
	 */
	
	

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
//		int wan = wanBallTable.getHighlightBallNums();
//		int qian = qianBallTable.getHighlightBallNums();
//		int bai = baiBallTable.getHighlightBallNums();
//		int shi = shiBallTable.getHighlightBallNums();
		int ge = geBallTable.getHighlightBallNums();
		int beishu = mSeekBarBeishu.getProgress();
		int iZhuShu = getZhuShu();
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
				SscZhiXuan.this, "addInfo");
		String sessionIdStr = pre.getUserLoginInfo("sessionid");
		if (sessionIdStr==null||sessionIdStr.equals("")) {
			Intent intentSession = new Intent(SscZhiXuan.this, UserLogin.class);
			startActivityForResult(intentSession, 0);
		} else {
			boolean isTouzhu = false;
			switch (iCurrentButton) {
//			case PublicConst.SSC_FIVE_STAR:
//				if (wan == 0 | qian == 0 | bai == 0 | shi == 0 | ge == 0) {
//					alert1("������ѡ��һע��");
//				} else if (iZhuShu * 2 > 20000) {
//					DialogExcessive();
//				} else if (wan + qian + bai + shi + ge > 45) {
//					alert1("����ֱѡ��С��ĸ������Ϊ45����");
//				} else {
//					isTouzhu = true;
//				}
//				break;
//			case PublicConst.SSC_THREE_STAR:
//				if (bai == 0 | shi == 0 | ge == 0) {
//					alert1("������ѡ��һע��");
//				} else if (iZhuShu * 2 > 20000) {
//					DialogExcessive();
//				} else if (bai + shi + ge > 24) {
//					alert1("����ֱѡ��С��ĸ������Ϊ24����");
//				} else {
//					isTouzhu = true;
//				}
//				break;
//			case PublicConst.SSC_TWO_STAR:
//				if (shi == 0 | ge == 0) {
//					alert1("������ѡ��һע��");
//				} else if (iZhuShu * 2 > 20000) {
//					DialogExcessive();
//				} else if (shi + ge > 12) {
//					alert1("����ֱѡ��С��ĸ������Ϊ12����");
//				} else {
//					isTouzhu = true;
//				}
//				break;
			case PublicConst.SSC_ONE_STAR:
		 if(isjixuan){
			 if(balls.size()==0){
			  alert1("������ѡ��1ע��Ʊ");
			}else{
				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlertJixuan();
				alert_jixuan(sTouzhuAlert);
			}
			 
		    }else{
				if (ge == 0) {
					alert1("������ѡ��һע��");
				} else if (iZhuShu * 2 > 20000) {
					DialogExcessive();
				} else if (ge > 5) {
					alert1("һ��ֱѡ��С��ĸ������Ϊ5����");
				} else {
					isTouzhu = true;
				}
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
		AlertDialog.Builder builder = new AlertDialog.Builder(SscZhiXuan.this);
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
//		int[] wan = wanBallTable.getHighlightBallNOs();
//		int[] qian = qianBallTable.getHighlightBallNOs();
//		int[] bai = baiBallTable.getHighlightBallNOs();
//		int[] shi = shiBallTable.getHighlightBallNOs();
		int[] ge = geBallTable.getHighlightBallNOs();
		switch (iCurrentButton) {
//		case PublicConst.SSC_FIVE_STAR:
//			return "ע�룺" + "\n" + "��" + batchCode + "��\n" + "��λ��"
//					+ getStrZhuMa(wan) + "\n" + "ǧλ��" + getStrZhuMa(qian)
//					+ "\n" + "��λ��" + getStrZhuMa(bai) + "\n" + "ʮλ��"
//					+ getStrZhuMa(shi) + "\n" + "��λ��" + getStrZhuMa(ge)
//					+ "\n"
//					+ "ע����"
//					+ iZhuShu / mSeekBarBeishu.getProgress()
//					+ "ע"
//					+ "\n"
//					+ // ע���������ϱ��� �³� 20100713
//					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
//					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
//					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
//					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"
//					+ "\n" + "ȷ��֧����";
//
//		case PublicConst.SSC_THREE_STAR:
//			return "ע�룺" + "\n" + "��" + batchCode + "��\n" + "��λ��"
//					+ getStrZhuMa(bai) + "\n" + "ʮλ��" + getStrZhuMa(shi) + "\n"
//					+ "��λ��" + getStrZhuMa(ge) + "\n"
//					+ "ע����"
//					+ iZhuShu / mSeekBarBeishu.getProgress()
//					+ "ע"
//					+ "\n"
//					+ // ע���������ϱ��� �³� 20100713
//					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
//					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
//					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
//					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"
//					+ "\n" + "ȷ��֧����";
//
//		case PublicConst.SSC_TWO_STAR:
//			return "ע�룺" + "\n" + "��" + batchCode + "��\n" + "ʮλ��"
//					+ getStrZhuMa(shi) + "\n" + "��λ��" + getStrZhuMa(ge)
//					+ "\n"
//					+ "ע����"
//					+ iZhuShu / mSeekBarBeishu.getProgress()
//					+ "ע"
//					+ "\n"
//					+ // ע���������ϱ��� �³� 20100713
//					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
//					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
//					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
//					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"
//					+ "\n" + "ȷ��֧����";

		case PublicConst.SSC_ONE_STAR:
			return  "��" + batchCode + "��\n" 
					+ "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"+ "\n" 
					+"ע�룺" + "\n" + "��λ��" + getStrZhuMa(ge)
					+ "\n" + "ȷ��֧����";

		      default:
			break;
		}
		return null;
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
		return  "��" + batchCode + "��\n" 
		        +"ע����"
				+ balls.size()
				+ "ע"
				+ "\n"
				+ // ע���������ϱ��� �³� 20100713
				"������" + beishu + "��" + "\n" + "׷�ţ�"
				+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
				+ (balls.size() * 2 * beishu) + "Ԫ" + "\n" + "�����"
				+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"
				+ "\n" + "ע�룺"+"\n" + "��λ��" +  "\n" + zhumaString + "ȷ��֧����";
	}
	



    /*
     * *  ����һ�ǻ�ѡView
     *    
     */
	public void create_OneStar_JiXuan(){
		PublicMethod.recycleBallTable(wanBallTable);
		PublicMethod.recycleBallTable(qianBallTable);
		PublicMethod.recycleBallTable(baiBallTable);
		PublicMethod.recycleBallTable(shiBallTable);
		PublicMethod.recycleBallTable(geBallTable);
		buyView.removeAllViews();
     	balls = new Vector<Balls>();
     	title.setText("ʱʱ��һ�ǻ�ѡ");
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
						Balls ball = new Balls(1);
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
	  Balls ball = new Balls(1);
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
			Balls ball = new Balls(1);
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
		PublicMethod.recycleBallTable(wanBallTable);
		PublicMethod.recycleBallTable(qianBallTable);
		PublicMethod.recycleBallTable(baiBallTable);
		PublicMethod.recycleBallTable(shiBallTable);
		PublicMethod.recycleBallTable(geBallTable);

		for (int i = 0; i < balls.size(); i++) {
			final int index = i;
			//int redBallViewWidth = SsqActivity.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);
			LinearLayout lines = new LinearLayout(layout.getContext());
			BallTable ballTablege = new BallTable(lines, GE_BALL_START);
			PublicMethod.makeBallTableJiXuanSSC(ballTablege, iScreenWidth,
					redBallResId, balls.get(i).getball(), this);
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
								SscZhiXuan.this,
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

								 // ��ʾ������ʾ�� 2010/7/4
								// TODO Auto-generated method stub
                                showDialog(0);
							    new Thread(new Runnable() {
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
										} 
                                        Log.v("t", "1");
										ssc_btn_touzhu.setClickable(true);
                                        Log.v("t", "2");
										Message msg1 = new Message();
										msg1.what = 10;
										handler.sendMessage(msg1);
									   
                                        if (strCode[0].equals("0000")
												&& strCode[1].equals("000000")) {
                                        	Log.v("t", "3");
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

								}).start();
							}
						}).setNegativeButton("ȡ��",
						null);
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
										} else if (strCode[0].equals("20100706")) {
											Message msg = new Message();
											msg.what = 2;
											handler.sendMessage(msg);
										}else {
											Message msg = new Message();
											msg.what = 9;
											handler.sendMessage(msg);
										}
										Log.v("strCode", strCode[0]);
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


	/**
	 * ��ȡע��
	 */
	public String getZhuMa() {
		String t_str = "";
//		int[] wan = wanBallTable.getHighlightBallNOs();
//		int[] qian = qianBallTable.getHighlightBallNOs();
//		int[] bai = baiBallTable.getHighlightBallNOs();
//		int[] shi = shiBallTable.getHighlightBallNOs();
		int[] ge = geBallTable.getHighlightBallNOs();
		switch (iCurrentButton) {
//		case PublicConst.SSC_FIVE_STAR:
//			t_str = "5D|";
//			t_str += getStr(wan) + "," + getStr(qian) + "," + getStr(bai) + ","
//					+ getStr(shi) + "," + getStr(ge);
//			break;
//		case PublicConst.SSC_THREE_STAR:
//			t_str = "3D|";
//			t_str += "-," + "-," + getStr(bai) + "," + getStr(shi) + ","
//					+ getStr(ge);
//			break;
//		case PublicConst.SSC_TWO_STAR:
//			t_str = "2D|";
//			t_str += "-," + "-," + "-," + getStr(shi) + "," + getStr(ge);
//			break;
		case PublicConst.SSC_ONE_STAR:
		if(isjixuan){
			
			t_str = "1D|";
			t_str +=  getstrjixuan();
		}else{
			t_str = "1D|";
			t_str += "-," + "-," + "-," + "-," + getStr(ge);
		}
		
			break;
			}
		
		
		return t_str;
		
		
	}
	public String getstrjixuan(){
		String str="-," + "-," + "-," + "-";
		for (int i = 0; i < balls.size(); i++) {
		   
			int ge[]= balls.get(i).getball();
		//	int j;
			for(int j=0;j<ge.length;j++){
				
					str += ","+ge[j];
				
				
			}
	        if(i!=balls.size()-1){
			str+=";1D|"+"-," + "-," + "-," + "-";	
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

		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,
				"addInfo");
		String sessionid = shellRW.getUserLoginInfo("sessionid");
		String phonenum = shellRW.getUserLoginInfo("phonenum");
		if (mTimesMoney == 2) {
			error_code = betting.BettingTC(phonenum, "T01007", betCode,
					lotMulti, amount, "2", qiShu, sessionid, batchCode);
		} else if (mTimesMoney == 3) {
			error_code = betting.BettingTC(phonenum, "T01007", betCode,
					lotMulti, amount, "3", qiShu, sessionid, batchCode);
		}
		for(int i=0; i<error_code.length;i++){
		Log.v("error_code",error_code[i]);
		}
		return error_code;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (resultCode) {
		case RESULT_OK:
			
			beginTouZhu();
			
			break;
		default:
			Toast.makeText(SscZhiXuan.this, "δ��¼�ɹ���", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	/**
	 * �ֻ�����
	 * 
	 * @author Administrator
	 * 
	 */
	
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
				Balls ball = new Balls(1);
				ball.createBalls();
				balls.add(ball);
			}
			createTable(zhuView);
		}
	}
  
	@Override
	protected void onDestroy() {
		super.onDestroy();
		PublicMethod.recycleBallTable(wanBallTable);
		PublicMethod.recycleBallTable(qianBallTable);
		PublicMethod.recycleBallTable(baiBallTable);
		PublicMethod.recycleBallTable(shiBallTable);
		PublicMethod.recycleBallTable(geBallTable);
		
	}
	
}
