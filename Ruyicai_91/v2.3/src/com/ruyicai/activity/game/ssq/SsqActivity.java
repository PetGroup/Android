package com.ruyicai.activity.game.ssq;

/**
 * @Title ˫ɫ���淨 Activity��
 * @Description: 
 * @Copyright: Copyright (c) 2009
 * @Company: PalmDream
 * @author FanYaJun
 * @version 1.0 20100618
 */

import java.util.Iterator;
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
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.home.RuyicaiAndroid;
import com.ruyicai.dialog.Accoutdialog;
import com.ruyicai.dialog.ChooseNumberDialogSSQ;
import com.ruyicai.handler.MyDialogListener;
import com.ruyicai.net.transaction.BettingInterface;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.SensorActivity;
import com.ruyicai.util.ShellRWSharesPreferences;
import com.ruyicai.view.OneBallView;
import com.ruyicai.view.RadioStateDrawable;
import com.ruyicai.view.TabBarButton;

public class SsqActivity extends Activity implements OnClickListener,// С�򱻵��
		// onClick
		SeekBar.OnSeekBarChangeListener, // SeekBar�ı� onProgressChanged
		RadioGroup.OnCheckedChangeListener, // ��ѡ��ѡ��仯 onCheckedChangeListener
		MyDialogListener { // �Ի���������ֵ onOKClick

	// С��ͼ�Ŀ��
	public static final int BALL_WIDTH = 55;
	// ������ǩ����
	private int iButtonNum = 3;

	// ����button��ǰ��λ��
	private int iCurrentButton; // 0x55660001 --- SSQ danshi
	private int tempCurrentButton;
	private int iCurrentButton_menu;
	/*
	 * Button danshiButton; Button fushiButton; Button dantuoButton;
	 */
	public static final int WANFA_START_ID = 0x55550001;
	private HorizontalScrollView topBar;
	private RadioGroup.LayoutParams topButtonLayoutParams;
	private RadioGroup topButtonGroup;

	private int defaultOffShade;
	private int defaultOnShade;

	int topButtonStringId[] = { R.string.dantuo };
	int topButtonIdOn[] = { R.drawable.goucai_b, R.drawable.jixuan_b,
			R.drawable.dantuo_b };
	int topButtonIdOff[] = { R.drawable.goucai, R.drawable.jixuan,
			R.drawable.dantuo };

	// С����ʼID
	public static final int RED_BALL_START = 0x80000001;
	public static final int RED_TUO_BALL_START = 0x82000001;
	public static final int BLUE_BALL_START = 0x81000001;
	private static final int DIALOG1_KEY = 0;// ��������ֵ2010/7/4
	ProgressDialog progressdialog;

	SeekBar mSeekBarBeishu;
	SeekBar mSeekBarQishu;

	TextView mTextBeishu;
	TextView mTextQishu;

	TextView mTextSumMoney;

	ImageButton ssq_b_touzhu_danshi;
	ImageButton ssq_b_touzhu_fushi;
	ImageButton ssq_b_touzhu_dantuo;
	Button ssq_btn_newSelect;
	ImageButton imgJixuan;
	int iProgressBeishu = 1;
	int iProgressQishu = 1;

	private int iScreenWidth;


	private int redBallResId[] = { R.drawable.grey, R.drawable.red };

	BallTable redBallTable = null;// ����ball table
	BallTable redTuoBallTable = null;

	BallTable blueBallTable = null;
	private int blueBallResId[] = { R.drawable.grey, R.drawable.blue };

	LinearLayout buyView;

	// ��ʽ С���������ѡ��
	private int iFushiRedBallNumber;
	private int iFushiBlueBallNumber;

	// ���� С���������ѡ��
	private int iDantuoRedDanNumber;
	private int iDantuoRedTuoNumber;
	private int iDantuoBlueNumber;

	// ע��
	public static int zhuma[] = null;
	// �����㱶���ĵ�ǰע�� ���ڹ�������
	private long iSendZhushu = 0;
	public int publicTopButton;
	public int type;
	public static final int ZHIXUAN = 0;
	public static final int DANTUO = 1;
	private EditText redEdit;
	private EditText redDanEdit;
	private EditText redTuoEdit;
	private EditText blueEdit;
	private boolean isJiXuanDanTuo = true;// �Ƿ��ǵ��ϻ�ѡ
	private boolean isJiXuanZhiXuan = false;// �Ƿ���ֱѡ��ѡ
	private Vector<Balls> balls = new Vector();
	private LinearLayout zhuView;
	private Spinner jixuanZhu;
	private String endTime;
	private String issue[] = new String[2];
	private TextView term;
	private TextView title;
	private TextView time;
	private SsqSensor sensor = new SsqSensor(this);
	private boolean isOnclik = true;
	int width;
	/** Called when the activity is first created. */

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
				    Accoutdialog.getInstance().createAccoutdialog(SsqActivity.this, getResources().getString(R.string.goucai_Account_dialog_msg).toString());
				}
				// //��Ҫ���AlertDialog��ʾע��ɹ�
				break;
			case 2:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ѽ�����", Toast.LENGTH_LONG).show();
				// //��Ҫ���AlertDialog��ʾ�û���ע��
				break;
			case 3:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "ϵͳ���㣬���Ժ�",Toast.LENGTH_LONG).show();
				// //��Ҫ���AlertDialog��ʾϵͳ���㣬���Ժ�
				break;
			case 4:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�޿����߼�����", Toast.LENGTH_LONG).show();
				// //��Ҫ���AlertDialog��ʾ�úű���ͣ����ϵ�ͷ�
				break;

			// //��Ҫ���AlertDialogע��ʧ��

			case 6:
				// //��Ҫ���AlertDialog��ʾ�û���¼�ɹ�

				progressdialog.dismiss();
				Intent intent = new Intent(UserLogin.SUCCESS);
				sendBroadcast(intent);
				// Ͷע�ɹ������С�� �³� 20100728
				if (iCurrentButton == PublicConst.BUY_SSQ_ZHIXUAN) {
					if (isJiXuanZhiXuan) {
						zhuView.removeAllViews();
					} else {
						redEdit.setText("");
						blueEdit.setText("");
						redBallTable.clearAllHighlights();
						blueBallTable.clearAllHighlights();
						changeTextSumMoney();
					}
				} else if (iCurrentButton == PublicConst.BUY_SSQ_DANTUO ) {
					redDanEdit.setText("");
					redTuoEdit.setText("");
					blueEdit.setText("");
					redBallTable.removeView();
					blueBallTable.removeView();
					redTuoBallTable.removeView();
					changeTextSumMoney();
				}
				if(isFinishing() == false){
				    PublicMethod.showDialog(SsqActivity.this);
				}
				break;
			case 7:
				progressdialog.dismiss();
				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(SsqActivity.this,UserLogin.class);
				startActivity(intentSession);
				break;
			case 8:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG).show();
				// //��Ҫ���AlertDialog��ʾ��¼ʧ��
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ�ܣ�", Toast.LENGTH_LONG).show();
				// // tv.setText(result);
				break;
			case 10:
				// ��ͼƬ�ı������û�ԭ����ͼ����ʾ�ɵ��
				if (iCurrentButton == PublicConst.BUY_SSQ_DANSHI) {
					ssq_b_touzhu_danshi
							.setImageResource(R.drawable.imageselecter);
				} else if (iCurrentButton == PublicConst.BUY_SSQ_ZHIXUAN) {
					ssq_b_touzhu_fushi
							.setImageResource(R.drawable.imageselecter);
				} else if (iCurrentButton == PublicConst.BUY_SSQ_DANTUO) {
					ssq_b_touzhu_dantuo
							.setImageResource(R.drawable.imageselecter);
				}
				break;
			}
			//	
		}
	};


	/*
	 * @return void
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		// ----- ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// ----- ���ؿ�� layout
		setContentView(R.layout.layout_main_buy);
		title = (TextView) findViewById(R.id.layout_main_text_title);
		term = (TextView) findViewById(R.id.layout_main_text_issue);
		time = (TextView) findViewById(R.id.layout_main_text_time);
		// ��ȡ�ںźͽ�ֹʱ��
		JSONObject ssqLotnoInfo = PublicMethod.getCurrentLotnoBatchCode(Constants.LOTNO_SSQ);
		if (ssqLotnoInfo != null) {
			// �ɹ���ȡ�����ں���Ϣ
			try {
				issue[0] = ssqLotnoInfo.getString("batchCode");
				issue[1] = ssqLotnoInfo.getString("endTime");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			term.setText("��" + this.issue[0] + "��");
			time.setText("��ֹʱ�䣺" + this.issue[1]);
		} else {
			// û�л�ȡ���ں���Ϣ,����������ȡ�ں�
			issue[0] = "";
			issue[1] = "";
		    PublicMethod.getIssue(Constants.LOTNO_SSQ,term,time,new Handler());
		}
		// ��ȡ������id
		buyView = (LinearLayout) findViewById(R.id.layout_buy);
		// ----- ��ʼ��������ť
		initButtons();
		iCurrentButton = PublicConst.BUY_SSQ_ZHIXUAN;
		createBuyView(iCurrentButton);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sensor.stopAction();
	}

	@Override
	public Object getLastNonConfigurationInstance() {
		return super.getLastNonConfigurationInstance();
	}

	/**
	 * ��������View�����ն�����ť��״̬����
	 * @param int aWhichBuy ��ӦView��Id
	 * @return void
	 */
	private void createBuyView(int aWhichBuy) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
		publicTopButton = aWhichBuy;
		switch (aWhichBuy) {

		case PublicConst.BUY_SSQ_ZHIXUAN: // ֱѡ
			if (isJiXuanZhiXuan) {
				sensor.startAction();
				create_SSQ_ZHIXUAN_JIXUAN();
			} else {
				sensor.stopAction();
				create_SSQ_ZHIXUAN();
			}
			topButtonGroup.setSelected(false);
			commit();
			break;
		case PublicConst.BUY_SSQ_DANTUO: // ����

			if (isJiXuanDanTuo) {

				create_SSQ_DANTUO_JIXUAN();

			} else {

				create_SSQ_DANTUO();

			}
			topButtonGroup.setSelected(false);
			commit();
			break;
		default:
			break;
		}

	}

	/*
	 * �������Ϲ���View
	 * 
	 * @return void
	 */
	private void create_SSQ_DANTUO() {
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(R.layout.layout_ssq_dantuo, null);
			title.setText("˫ɫ����");

			final LinearLayout redDanArea = (LinearLayout) iV
					.findViewById(R.id.ssq_dantuo_linear_red_danma);
			final LinearLayout redTuoArea = (LinearLayout) iV
					.findViewById(R.id.ssq_dantuo_linear_red_tuoma);
			final LinearLayout blueArea = (LinearLayout) iV
					.findViewById(R.id.ssq_dantuo_linear_blue);
			blueArea.setVisibility(LinearLayout.GONE);
			redTuoArea.setVisibility(LinearLayout.GONE);
			final TextView redDanText = (TextView) iV.findViewById(R.id.ssq_dantuo_text_red_danma);
			final TextView redTuoText = (TextView) iV
					.findViewById(R.id.ssq_dantuo_text_red_tuoma);
			final TextView blueText = (TextView) iV
					.findViewById(R.id.ssq_dantuo_text_blue);
			redDanEdit = (EditText) iV
					.findViewById(R.id.ssq_dantuo_edit_red_danma);
			redTuoEdit = (EditText) iV
					.findViewById(R.id.ssq_dantuo_edit_red_tuoma);
			blueEdit = (EditText) iV.findViewById(R.id.ssq_dantuo_edit_blue);

			redDanEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub

					if (hasFocus) {
						redDanArea.setVisibility(LinearLayout.VISIBLE);
						redTuoArea.setVisibility(LinearLayout.GONE);
						blueArea.setVisibility(LinearLayout.GONE);
						redDanEdit.setBackgroundResource(R.drawable.hongkuang);
						redDanText.setTextColor(Color.RED);
					} else {
						redDanEdit.setBackgroundResource(R.drawable.huikuang);
						redDanText.setTextColor(Color.BLACK);
					}

				}
			});
			redTuoEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {

					if (hasFocus) {
						redDanArea.setVisibility(LinearLayout.GONE);
						redTuoArea.setVisibility(LinearLayout.VISIBLE);
						blueArea.setVisibility(LinearLayout.GONE);
						redTuoEdit.setBackgroundResource(R.drawable.hongkuang);
						redTuoText.setTextColor(Color.RED);
					} else {
						redTuoEdit.setBackgroundResource(R.drawable.huikuang);
						redTuoText.setTextColor(Color.BLACK);
					}
				}
			});
			blueEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {

					if (hasFocus) {
						redDanArea.setVisibility(LinearLayout.GONE);
						redTuoArea.setVisibility(LinearLayout.GONE);
						blueArea.setVisibility(LinearLayout.VISIBLE);
						blueEdit.setBackgroundResource(R.drawable.lankuang);
						blueText.setTextColor(Color.BLUE);
					} else {
						blueEdit.setBackgroundResource(R.drawable.huikuang);
						blueText.setTextColor(Color.BLACK);
					}
				}
			});

			int redBallViewNum = 33;
			iScreenWidth = PublicMethod.getDisplayWidth(this);
			
			PublicMethod.recycleBallTable(redBallTable);//��������֮ǰ�������չ���
			redBallTable = PublicMethod.makeBallTable(iV, R.id.table_red_danma,iScreenWidth, redBallViewNum, redBallResId, RED_BALL_START,1, this, this);
			
			PublicMethod.recycleBallTable(redTuoBallTable);
			redTuoBallTable = PublicMethod.makeBallTable(iV,
					R.id.table_red_tuoma, iScreenWidth, redBallViewNum,
					redBallResId, RED_TUO_BALL_START, 1, this, this);

			int blueBallViewNum = 16;
			
			PublicMethod.recycleBallTable(blueBallTable);
			blueBallTable = PublicMethod.makeBallTable(iV,
					R.id.table_blue_dantuo, iScreenWidth, blueBallViewNum,
					blueBallResId, BLUE_BALL_START, 1, this, this);
			mTextSumMoney = (TextView) iV
					.findViewById(R.id.ssq_dantuo_text_sum_money);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));

			mSeekBarBeishu = (SeekBar) iV
					.findViewById(R.id.ssq_dantuo_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) iV
					.findViewById(R.id.ssq_dantuo_seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) iV
					.findViewById(R.id.ssq_dantuo_text_beishu_change);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) iV
					.findViewById(R.id.ssq_dantuo_text_qishu_change);
			mTextQishu.setText("" + iProgressQishu);
			// true - ���� false-����
			setSeekWhenAddOrSub(R.id.ssq_dantuo_seekbar_subtract_beishu, iV,
					-1, mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.ssq_dantuo_seekbar_add_beishu, iV, 1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.ssq_dantuo_seekbar_subtract_qihao, iV, -1,
					mSeekBarQishu, false);
			setSeekWhenAddOrSub(R.id.ssq_dantuo_seekbar_add_qihao, iV, 1,
					mSeekBarQishu, false);

			ssq_b_touzhu_dantuo = (ImageButton) iV
					.findViewById(R.id.ssq_dantuo_b_touzhu);
			ssq_b_touzhu_dantuo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					beginTouZhu();
				}
			});
		ImageButton newSelect = (ImageButton) iV
				.findViewById(R.id.ssq_dantuo_new_select);
		newSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Ͷע�ɹ������С�� �³� 20100728
				redTuoEdit.setText("");
				redDanEdit.setText("");
				blueEdit.setText("");

				redBallTable.clearAllHighlights();
				blueBallTable.clearAllHighlights();
				redTuoBallTable.clearAllHighlights();
				changeTextSumMoney();

			}
		});
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView.getLayoutParams().width, buyView.getLayoutParams().height));

	}

	/*
	 * ����ֱѡ����View
	 * 
	 * @return void
	 */
	private void create_SSQ_ZHIXUAN() {
		
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(R.layout.layout_ssq_zhixuan, null);
		title.setText("˫ɫ��ֱѡ");
		int redBallViewNum = 33;
		iScreenWidth = PublicMethod.getDisplayWidth(this);

		mTextSumMoney = (TextView) iV.findViewById(R.id.ssq_zhixuan_text_sum_money);
		mTextSumMoney.setText(getResources().getString(R.string.please_choose_number));
		PublicMethod.recycleBallTable(redBallTable);
		redBallTable = PublicMethod.makeBallTable(iV,R.id.ssq_zhixuan_table_red, iScreenWidth, redBallViewNum,redBallResId, RED_BALL_START, 1, this, this);
		int blueBallViewNum = 16;
		
		PublicMethod.recycleBallTable(blueBallTable);
		blueBallTable = PublicMethod.makeBallTable(iV,R.id.ssq_zhixuan_table_blue, iScreenWidth, blueBallViewNum,blueBallResId, BLUE_BALL_START, 1, this, this);
		
		final LinearLayout redArea = (LinearLayout) iV
				.findViewById(R.id.ssq_zhixuan_linear_red);
		final LinearLayout blueArea = (LinearLayout) iV
				.findViewById(R.id.ssq_zhixuan_linear_blue);
		blueArea.setVisibility(LinearLayout.GONE);
		final TextView redText = (TextView) iV
				.findViewById(R.id.ssq_zhixuan_text_red);
		final TextView blueText = (TextView) iV
				.findViewById(R.id.ssq_zhixuan_text_blue);

		View.OnFocusChangeListener redChooseListener = new OnFocusChangeListener() {
			// ���� ����ѡ���
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					redEdit.setBackgroundResource(R.drawable.hongkuang);
					redArea.setVisibility(LinearLayout.VISIBLE);
					blueArea.setVisibility(LinearLayout.GONE);
					redText.setTextColor(Color.RED);
				} else {
					redEdit.setBackgroundResource(R.drawable.huikuang);
					redText.setTextColor(Color.BLACK);
				}
			}
		};
			
		View.OnFocusChangeListener blueChooseListener = new OnFocusChangeListener() {
			// ���������ѡ���
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus) {
					blueEdit.setBackgroundResource(R.drawable.lankuang);
					redArea.setVisibility(LinearLayout.GONE);
					blueArea.setVisibility(LinearLayout.VISIBLE);
					blueText.setTextColor(Color.BLUE);
				} else {
					blueEdit.setBackgroundResource(R.drawable.huikuang);
					blueText.setTextColor(Color.BLACK);
				}
			}
		};

		redEdit = (EditText) iV.findViewById(R.id.ssq_zhixuan_edit_red);
		blueEdit = (EditText) iV.findViewById(R.id.ssq_zhixuan_edit_blue);
		redEdit.setOnFocusChangeListener(redChooseListener);
		blueEdit.setOnFocusChangeListener(blueChooseListener);

		mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.ssq_fushi_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mSeekBarQishu = (SeekBar) iV.findViewById(R.id.ssq_fushi_seek_qishu);
		mSeekBarQishu.setOnSeekBarChangeListener(this);
		mSeekBarQishu.setProgress(iProgressQishu);

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
		ImageButton newSelect = (ImageButton) iV
				.findViewById(R.id.ssq_zhixuan_new_select);
		newSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Ͷע�ɹ������С�� �³� 20100728
				redEdit.setText("");
				blueEdit.setText("");
				redBallTable.clearAllHighlights();
				blueBallTable.clearAllHighlights();
				changeTextSumMoney();

			}
		});
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));
	}

	/**
	 * ���� ���ϻ�ѡ
	 */
	private void create_SSQ_DANTUO_JIXUAN() {
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_ssq_dantuo_jixuan, null);

		title.setText("˫ɫ���ϻ�ѡ");
		
		PublicMethod.recycleBallTable(redBallTable);
		redBallTable = new BallTable(iV, R.id.table_red_danma, RED_BALL_START);
		
		PublicMethod.recycleBallTable(redTuoBallTable);
		redTuoBallTable = new BallTable(iV, R.id.table_red_tuoma,RED_TUO_BALL_START);
		
		PublicMethod.recycleBallTable(blueBallTable);
		blueBallTable = new BallTable(iV, R.id.table_blue_dantuo,BLUE_BALL_START);

		mTextSumMoney = (TextView) iV.findViewById(R.id.ssq_dantuo_text_sum_money);
		mTextSumMoney.setText(getResources().getString(R.string.please_choose_number));

		mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.ssq_dantuo_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		mSeekBarBeishu.setProgress(iProgressBeishu);

		mSeekBarQishu = (SeekBar) iV.findViewById(R.id.ssq_dantuo_seek_qishu);
		mSeekBarQishu.setOnSeekBarChangeListener(this);
		mSeekBarQishu.setProgress(iProgressQishu);

		mTextBeishu = (TextView) iV
				.findViewById(R.id.ssq_dantuo_text_beishu_change);
		mTextBeishu.setText("" + iProgressBeishu);
		mTextQishu = (TextView) iV
				.findViewById(R.id.ssq_dantuo_text_qishu_change);
		mTextQishu.setText("" + iProgressQishu);
		// true - ���� false-����
		setSeekWhenAddOrSub(R.id.ssq_dantuo_seekbar_subtract_beishu, iV, -1,
				mSeekBarBeishu, true);
		setSeekWhenAddOrSub(R.id.ssq_dantuo_seekbar_add_beishu, iV, 1,
				mSeekBarBeishu, true);
		setSeekWhenAddOrSub(R.id.ssq_dantuo_seekbar_subtract_qihao, iV, -1,
				mSeekBarQishu, false);
		setSeekWhenAddOrSub(R.id.ssq_dantuo_seekbar_add_qihao, iV, 1,
				mSeekBarQishu, false);

		ssq_b_touzhu_dantuo = (ImageButton) iV
				.findViewById(R.id.ssq_dantuo_b_touzhu);
		ssq_b_touzhu_dantuo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				beginTouZhu();
			}
		});
		imgJixuan = (ImageButton) iV.findViewById(R.id.ssq_dantuo_img_jixuan);

		imgJixuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ChooseNumberDialogSSQ iChooseNumberDialog = new ChooseNumberDialogSSQ(
						SsqActivity.this, 1, SsqActivity.this);
				iChooseNumberDialog.show();
			}
		});
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));

		ChooseNumberDialogSSQ iChooseNumberDialog = new ChooseNumberDialogSSQ(
				SsqActivity.this, 1, SsqActivity.this);
		iChooseNumberDialog.show();

	}

	/**
	 * ���� ֱѡ��ѡ
	 */
	private void create_SSQ_ZHIXUAN_JIXUAN() {
		buyView.removeAllViews();
		Toast.makeText(this, "����ҡ���ֻ�������ѡ�ţ�", Toast.LENGTH_SHORT).show();
		balls = new Vector<Balls>();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(R.layout.layout_zhixuan_jixuan, null);

			title.setText("˫ɫ���ѡ");
			// ��ʼ��spinner
			jixuanZhu = (Spinner) iV.findViewById(R.id.layout_zhixuan_jixuan_spinner);
			jixuanZhu.setSelection(4);
			jixuanZhu.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					int position = jixuanZhu.getSelectedItemPosition();
					if (isOnclik) {
						zhuView.removeAllViews();
						balls = new Vector();
						for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
							Balls ball = new Balls();
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
			zhuView = (LinearLayout) iV.findViewById(R.id.layout_zhixuan_linear_zhuma);

			int index = jixuanZhu.getSelectedItemPosition() + 1;
			for (int i = 0; i < index; i++) {
				Balls ball = new Balls();
				balls.add(ball);
			}

			createTable(zhuView);
			sensor.onVibrator();// ��
		mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.ssq_fushi_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		mSeekBarBeishu.setProgress(iProgressBeishu);

		mSeekBarQishu = (SeekBar) iV.findViewById(R.id.ssq_fushi_seek_qishu);
		mSeekBarQishu.setOnSeekBarChangeListener(this);
		mSeekBarQishu.setProgress(iProgressQishu);

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
				balls = new Vector();
				for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
					Balls ball = new Balls();
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
	 * ��ʼ��������button
	 * 
	 * @return void
	 */
	private void initButtons() {

		initTopButtons();
		commit();

	}

	private void initTopButtons() {
		// topBar = (HorizontalScrollView) findViewById(R.id.topBar);
		topButtonGroup = (RadioGroup) findViewById(R.id.topMenu);

		defaultOffShade = RadioStateDrawable.SHADE_GRAY;
		defaultOnShade = RadioStateDrawable.SHADE_YELLOW;
		topButtonGroup.setOnCheckedChangeListener(this);
		topButtonLayoutParams = new RadioGroup.LayoutParams(
		/* 320/5 */RadioGroup.LayoutParams.FILL_PARENT,
				RadioGroup.LayoutParams.WRAP_CONTENT);
	}

	/**
	 * ��Ӷ�����ť������ʾ��ʽ�淨View
	 * 
	 * @return void
	 */
	private void commit() {
		
//		topButtonGroup.removeAllViews();
		
		if (isJiXuanDanTuo) {
			topButtonIdOn[2] = R.drawable.dantuo_b;
			topButtonIdOff[2] = R.drawable.dantuo;
		} else {
			topButtonIdOn[2] = R.drawable.jixuan_b;
			topButtonIdOff[2] = R.drawable.jixuan;
		}
		if (isJiXuanZhiXuan) {
			topButtonIdOn[1] = R.drawable.zhixuan_b;
			topButtonIdOff[1] = R.drawable.zhixuan;
		} else {
			topButtonIdOn[1] = R.drawable.jixuan_b;
			topButtonIdOff[1] = R.drawable.jixuan;
		}
		int screen_width = getWindowManager().getDefaultDisplay().getWidth();
		int width = screen_width / iButtonNum;
		RadioStateDrawable.other_width = width;
		RadioStateDrawable.other_screen_width = screen_width;
		topButtonLayoutParams = new RadioGroup.LayoutParams(width,
				RadioGroup.LayoutParams.WRAP_CONTENT);

		for (int i = 0; i < iButtonNum; i++) {
			TabBarButton tabButton = new TabBarButton(this);
			// topButtonIdOff[i], this, 160, 72);
			tabButton.setState(topButtonIdOn[i], topButtonIdOff[i], 3);
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
		case 0:// ����
			break;
		case 1:// ֱѡ
			iCurrentButton = PublicConst.BUY_SSQ_ZHIXUAN;
			createBuyView(iCurrentButton);
			break;
		case 2: // ����
			iCurrentButton = PublicConst.BUY_SSQ_DANTUO;
			createBuyView(iCurrentButton);
			break;
		case 3:// ����

			break;
		}
	}

	/**
	 * ������ť�仯�Ļص�����
	 * 
	 * @param RadioGroup
	 *            group ����������ù���
	 * 
	 * @param int checkedId ����button��id
	 * 
	 * @return void
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

		switch (checkedId) {
		case 0:// ����
			sensor.stopAction();
			Intent intent1 = new Intent(SsqActivity.this, RuyicaiAndroid.class);
			startActivity(intent1);
			finish();
			break;
		case 1:// ֱѡ
			iCurrentButton = PublicConst.BUY_SSQ_ZHIXUAN;
			isJiXuanZhiXuan = !isJiXuanZhiXuan;
			if (!isJiXuanZhiXuan && !isJiXuanDanTuo) {
				isJiXuanDanTuo = true;
			}
			createBuyView(iCurrentButton);
			break;
		case 2: // ����
			sensor.stopAction();
			iCurrentButton = PublicConst.BUY_SSQ_DANTUO;
			isJiXuanDanTuo = !isJiXuanDanTuo;
			if (!isJiXuanZhiXuan && !isJiXuanDanTuo) {
				isJiXuanZhiXuan = true;
			}
			createBuyView(iCurrentButton);
			break;
		case 3:// ����
			// sensor.stopAction();
			// Intent intent = new Intent(SsqActivity.this, JoinHall.class);
			// startActivity(intent);
			// topButtonGroup.check(1);
			break;
		}
	}

	/**
	 * ��ȡ��ǰ����button��id
	 * 
	 * @return int id
	 */
	public int getCurrentTab() {
		return topButtonGroup.getCheckedRadioButtonId();
	}

	/**
	 * SeekBar�ı�ʱ�Ļص�����
	 * 
	 * @param SeekBar
	 *            seekBar �����仯��SeekBarʵ��
	 * 
	 * @param int progress �仯���λ��ֵ
	 * 
	 * @param boolean fromUser ����������ù���
	 * 
	 * @return void
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (progress < 1)
			seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();

		switch (seekBar.getId()) {
		// ��ʽ
		case R.id.ssq_fushi_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			if (!isJiXuanZhiXuan)
				changeTextSumMoney();
			break;
		case R.id.ssq_dantuo_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			changeTextSumMoney();
			break;
		case R.id.ssq_fushi_seek_qishu:
		case R.id.ssq_dantuo_seek_qishu:
			iProgressQishu = iProgress;
			mTextQishu.setText("" + iProgressQishu);
			break;
		default:
			break;
		}
	}

	// Seekbar�ص����� ��ʱ����

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	// Seekbar�ص����� ��ʱ����

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	/**
	 * ��ӦС�򱻵���Ļص�����
	 * 
	 * @param View
	 *            v �������view
	 * @return void
	 */
	@Override
	public void onClick(View v) {
		int iBallId = v.getId(); //

		if (iBallId >= RED_BALL_START && iBallId < BLUE_BALL_START) {
			int iBallViewId = v.getId() - RED_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				changeBuyViewByRule(iCurrentButton, 0, iBallViewId);// �������
			}
		} else if (iBallId >= BLUE_BALL_START && iBallId < RED_TUO_BALL_START) {
			int iBallViewId = v.getId() - BLUE_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				changeBuyViewByRule(iCurrentButton, 1, iBallViewId);// ��������
			}
		} else {
			int iBallViewId = v.getId() - RED_TUO_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				changeBuyViewByRule(iCurrentButton, 2, iBallViewId);// ��������
			}
		}

		changeTextSumMoney();
	}

	/**
	 * ˫ɫ��ע���ļ��㷽��
	 * 
	 * @return int ����ע��
	 */
	private int getZhuShu() {
		int iReturnValue = 0;
		switch (iCurrentButton) {
		case PublicConst.BUY_SSQ_DANSHI:
			iReturnValue = mSeekBarBeishu.getProgress();
			iSendZhushu = 1;
			break;
		case PublicConst.BUY_SSQ_ZHIXUAN:
			int iRedBalls = redBallTable.getHighlightBallNums();
			int iBlueBalls = blueBallTable.getHighlightBallNums();
			iReturnValue = (int) getSSQFSZhuShu(iRedBalls, iBlueBalls);
			break;
		case PublicConst.BUY_SSQ_DANTUO:
			int iRedHighlights = redBallTable.getHighlightBallNums();
			int iRedTuoHighlights = redTuoBallTable.getHighlightBallNums();
			int iBlueHighlights = blueBallTable.getHighlightBallNums();
			iReturnValue = (int) getSSQDTZhuShu(iRedHighlights,
					iRedTuoHighlights, iBlueHighlights);
			break;
		default:
			break;
		}
		return iReturnValue;
	}

	/**
	 * ˫ɫ�������ļ��㷽��
	 * 
	 * @return int ����ע��
	 */
	private int getQiShu() {
		int iReturnValue = 0;
		switch (iCurrentButton) {
		case PublicConst.BUY_SSQ_DANSHI:
			iReturnValue = mSeekBarQishu.getProgress();
			iSendZhushu = 1;
			break;
		case PublicConst.BUY_SSQ_ZHIXUAN:
			iReturnValue = mSeekBarQishu.getProgress();
			break;
		case PublicConst.BUY_SSQ_DANTUO:
			iReturnValue = mSeekBarQishu.getProgress();
			break;
		default:
			break;
		}
		return iReturnValue;
	}

	/**
	 * �����淨ע�����㷽��
	 * 
	 * @param int aRedBalls �������
	 * @param int aRedTuoBalls �����������
	 * @param int aBlueBalls �������
	 * @return long ע��
	 */
	private long getSSQDTZhuShu(int aRedBalls, int aRedTuoBalls, int aBlueBalls) {// �õ�˫ɫ���ϵ�ע��
		long ssqZhuShu = 0L;
		iSendZhushu = 0L;
		if (aRedBalls > 0 && aRedTuoBalls > 0 && aBlueBalls > 0) {
			ssqZhuShu += (PublicMethod.zuhe(6 - aRedBalls, aRedTuoBalls)
					* PublicMethod.zuhe(1, aBlueBalls) * iProgressBeishu);
			iSendZhushu = PublicMethod.zuhe(6 - aRedBalls, aRedTuoBalls)
					* PublicMethod.zuhe(1, aBlueBalls);
		}
		return ssqZhuShu;
	}

	/**
	 * ��ʽ�淨ע�����㷽��
	 * 
	 * @param int aRedBalls �������
	 * 
	 * @param int aBlueBalls �������
	 * 
	 * @return long ע��
	 */
	private long getSSQFSZhuShu(int aRedBalls, int aBlueBalls) {
		long ssqZhuShu = 0L;
		iSendZhushu = 0L;
		if (aRedBalls > 0 && aBlueBalls > 0) {
			ssqZhuShu += (PublicMethod.zuhe(6, aRedBalls)
					* PublicMethod.zuhe(1, aBlueBalls) * iProgressBeishu);
			iSendZhushu = PublicMethod.zuhe(6, aRedBalls)
					* PublicMethod.zuhe(1, aBlueBalls);
		}
		return ssqZhuShu;
	}

	private int getBeishu() {
		return mSeekBarBeishu.getProgress();

	}

	private int getQishu() {
		return mSeekBarQishu.getProgress();

	}

	// private boolean getCheckBox() {
	// return mCheckBox.isChecked();
	// }

	// fqc edit ���һ������ isBeiShu ���жϵ�ǰ�Ǳ����������� ��
	private void setSeekWhenAddOrSub(int idFind, View iV, final int isAdd,
			final SeekBar mSeekBar, final boolean isBeiShu) {
		ImageButton subtractBeishuBtn = (ImageButton) iV.findViewById(idFind);
		subtractBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isBeiShu) {
					if (isAdd == 1) {
						mSeekBar.setProgress(++iProgressBeishu);
					} else {
						mSeekBar.setProgress(--iProgressBeishu);
					}
				} else {
					if (isAdd == 1) {
						mSeekBar.setProgress(++iProgressQishu);
					} else {
						mSeekBar.setProgress(--iProgressQishu);
					}

				}
			}
		});
	}

	/**
	 * �ı���ʾ��Ϣ
	 * 
	 * @return void
	 */
	public void changeTextSumMoney() {
		switch (iCurrentButton) {
		case PublicConst.BUY_SSQ_DANSHI:
			// ��ʽView
			if (redBallTable.getHighlightBallNums() < 6) {
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_red_number));
			} else {
				if (blueBallTable.getHighlightBallNums() < 1) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_blue_number));
				} else {
					int iZhuShu = getZhuShu();
					String iTempString = "��" + iZhuShu + "ע����" + (iZhuShu * 2)
							+ "Ԫ";
					mTextSumMoney.setText(iTempString);
				}
			}

			break;
		case PublicConst.BUY_SSQ_ZHIXUAN: {
			int iRedHighlights = redBallTable.getHighlightBallNums();
			int iBlueHighlights = blueBallTable.getHighlightBallNums();

			// ������ ����
			if (iRedHighlights < 6) {
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_red_number));
			}
			// �������ﵽ���Ҫ��
			else if (iRedHighlights == 6) {
				if (iBlueHighlights < 1) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_blue_number));
				} else if (iBlueHighlights == 1) {
					int iZhuShu = getZhuShu();
					String iTempString = "��ǰΪ��ʽ����" + iZhuShu + "ע����"
							+ (iZhuShu * 2) + "Ԫ";
					mTextSumMoney.setText(iTempString);
				} else {
					int iZhuShu = getZhuShu();
					String iTempString = "��ǰΪ����ʽ����" + iZhuShu + "ע����"
							+ (iZhuShu * 2) + "Ԫ";
					mTextSumMoney.setText(iTempString);
				}
			}
			// ����ʽ
			else {
				if (iBlueHighlights < 1) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_blue_number));
				} else if (iBlueHighlights == 1) {
					int iZhuShu = getZhuShu();
					String iTempString = "��ǰΪ�츴ʽ����" + iZhuShu + "ע����"
							+ (iZhuShu * 2) + "Ԫ";
					mTextSumMoney.setText(iTempString);
				} else {
					int iZhuShu = getZhuShu();
					String iTempString = "��ǰΪȫ��ʽ����" + iZhuShu + "ע����"
							+ (iZhuShu * 2) + "Ԫ";
					mTextSumMoney.setText(iTempString);
				}
			}
			break;
		}
		case PublicConst.BUY_SSQ_DANTUO: {
			int iRedHighlights = redBallTable.getHighlightBallNums();
			int iBlueHighlights = blueBallTable.getHighlightBallNums();
			int iRedTuoHighlights = redTuoBallTable.getHighlightBallNums();

			if (iRedHighlights + iRedTuoHighlights < 7) {
				mTextSumMoney.setText(getResources().getString(
						R.string.choose_number_dialog_tip5));
			} else if (iBlueHighlights < 1) {
				mTextSumMoney.setText(getResources().getString(
						R.string.choose_number_dialog_tip6));
			} else {
				int iZhuShu = getZhuShu();
				String iTempString = "��ǰΪ���ϣ���" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ";
				mTextSumMoney.setText(iTempString);
			}

			break;
		}
		default:
			break;
		}
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
		case PublicConst.BUY_SSQ_DANSHI:
			// ��ʽView�У���2��ball table
			buy_DANSHI(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.BUY_SSQ_ZHIXUAN:
			buy_ZHIXUAN(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.BUY_SSQ_DANTUO:
			buy_DANTUO(aWhichGroupBall, aBallViewId);
			break;
		default:
			break;
		}
	}

	/**
	 * �����淨�ı�View
	 * 
	 * @param int aWhichGroupBall �������С��λ��
	 * 
	 * @param int aBallViewId С��id
	 * 
	 * @return void
	 */
	private void buy_DANTUO(int aWhichGroupBall, int aBallViewId) {

		if (aWhichGroupBall == 0) { // ���� ���� ���5��С��
			int iChosenBallSum = 5;
			// ÿ�ε�����סС���״̬
			int isHighLight = redBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT&& redTuoBallTable.getOneBallStatue(aBallViewId) !=0) {
				redTuoBallTable.clearOnBallHighlight(aBallViewId);
				Toast.makeText(this,getResources().getString(R.string.ssq_toast_danma_title), Toast.LENGTH_SHORT).show();
			}
			// ��¼�������fulei

			String red_zhuma_string = "  ";
			int[] redZhuMa = redBallTable.getHighlightBallNOs();
			for (int i = 0; i < redBallTable.getHighlightBallNOs().length; i++) {
				red_zhuma_string += PublicMethod.getZhuMa(redZhuMa[i]);
				if (i != redBallTable.getHighlightBallNOs().length - 1)
					red_zhuma_string = red_zhuma_string + ",";
			}

			if (red_zhuma_string.equals("  ")) {
				redDanEdit.setText("");
				redDanEdit.setHint(getResources().getString(
						R.string.ssq_dantuo_edit_Prompt_red_danma));
			} else {
				redDanEdit.setText(red_zhuma_string);
			}
			// fulei�Ѽ�¼���������
			String red_tuo_zhuma_string = "  ";
			int[] redTuoZhuMa = redTuoBallTable.getHighlightBallNOs();
			for (int i = 0; i < redTuoBallTable.getHighlightBallNOs().length; i++) {
				red_tuo_zhuma_string += PublicMethod.getZhuMa(redTuoZhuMa[i]);
				if (i != redTuoBallTable.getHighlightBallNOs().length - 1)
					red_tuo_zhuma_string = red_tuo_zhuma_string + ",";
			}
			if (red_tuo_zhuma_string.equals("  ")) {
				redTuoEdit.setText("");
				redTuoEdit.setHint(getResources().getString(
						R.string.ssq_dantuo_edit_Prompt_red_tuoma));
			} else {
				redTuoEdit.setText(red_tuo_zhuma_string);
			}

		} else if (aWhichGroupBall == 1) { // ������
			int iChosenBallSum = 16;
			int isHighLight = blueBallTable.changeBallState(iChosenBallSum,
					aBallViewId);

			// ��¼�������fulei
			String blue_zhuma_string = "  ";
			int[] blueZhuMa = blueBallTable.getHighlightBallNOs();
			for (int i = 0; i < blueBallTable.getHighlightBallNOs().length; i++) {
				blue_zhuma_string += PublicMethod.getZhuMa(blueZhuMa[i]);
				if (i != blueBallTable.getHighlightBallNOs().length - 1)
					blue_zhuma_string = blue_zhuma_string + ",";
			}

			if (blue_zhuma_string.equals("  ")) {
				blueEdit.setText("");
				blueEdit.setHint(getResources().getString(
						R.string.ssq_dantuo_edit_Prompt_blue));
			} else {
				blueEdit.setText(blue_zhuma_string);
			}

		} else if (aWhichGroupBall == 2) { // ���� ���� ���20��С��
			int iChosenBallSum = 20;
			int isHighLight = redTuoBallTable.changeBallState(iChosenBallSum,
					aBallViewId);

			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT&& redBallTable.getOneBallStatue(aBallViewId) != 0) {

				redBallTable.clearOnBallHighlight(aBallViewId);
				Toast.makeText(this,getResources().getString(R.string.ssq_toast_tuoma_title), Toast.LENGTH_SHORT).show();
			}
			// ��¼�������fulei

			String red_zhuma_string = "  ";
			int[] redZhuMa = redBallTable.getHighlightBallNOs();
			for (int i = 0; i < redBallTable.getHighlightBallNOs().length; i++) {
				red_zhuma_string += PublicMethod.getZhuMa(redZhuMa[i]);
				if (i != redBallTable.getHighlightBallNOs().length - 1)
					red_zhuma_string = red_zhuma_string + ",";
			}
			//
			if (red_zhuma_string.equals("  ")) {
				redDanEdit.setText("");
				redDanEdit.setHint(getResources().getString(
						R.string.ssq_dantuo_edit_Prompt_red_danma));
			} else {
				redDanEdit.setText(red_zhuma_string);
			}
			// fulei�Ѽ�¼���������
			String red_tuo_zhuma_string = "  ";
			int[] redTuoZhuMa = redTuoBallTable.getHighlightBallNOs();
			for (int i = 0; i < redTuoBallTable.getHighlightBallNOs().length; i++) {
				red_tuo_zhuma_string += PublicMethod.getZhuMa(redTuoZhuMa[i]);
				if (i != redTuoBallTable.getHighlightBallNOs().length - 1)
					red_tuo_zhuma_string = red_tuo_zhuma_string + ",";
			}
			if (red_tuo_zhuma_string.equals("  ")) {
				redTuoEdit.setText("");
				redTuoEdit.setHint(getResources().getString(
						R.string.ssq_dantuo_edit_Prompt_red_tuoma));
			} else {
				redTuoEdit.setText(red_tuo_zhuma_string);
			}
		}
	}

	/**
	 * ��ʽ�淨�ı�View
	 * 
	 * @param int aWhichGroupBall �������С��λ��
	 * @param int aBallViewId С��id
	 * @return void
	 */
	private void buy_ZHIXUAN(int aWhichGroupBall, int aBallViewId) {

		if (aWhichGroupBall == 0) { // ������
			int iChosenBallSum = 20;
			// ÿ�ε�����סС���״̬
			int isHighLight = redBallTable.changeBallState(iChosenBallSum,
					aBallViewId);

			// ��¼�������fuleiz
			String red_zhuma_string = "  ";
			int[] redZhuMa = redBallTable.getHighlightBallNOs();
			for (int i = 0; i < redBallTable.getHighlightBallNOs().length; i++) {
				red_zhuma_string += PublicMethod.getZhuMa(redZhuMa[i]);
				if (i != redBallTable.getHighlightBallNOs().length - 1)
					red_zhuma_string = red_zhuma_string + ",";
			}

			if (red_zhuma_string.equals("  ")) {
				redEdit.setText("");
				redEdit.setHint(getResources().getString(
						R.string.ssq_zhixuan_edit_Prompt_red));
			} else {
				redEdit.setText(red_zhuma_string);
			}

		} else if (aWhichGroupBall == 1) {
			int iChosenBallSum = 16;
			int isHighLight = blueBallTable.changeBallState(iChosenBallSum,
					aBallViewId);

			// ��¼�������fulei
			String blue_zhuma_string = "  ";
			int[] blueZhuMa = blueBallTable.getHighlightBallNOs();
			for (int i = 0; i < blueBallTable.getHighlightBallNOs().length; i++) {
				blue_zhuma_string += PublicMethod.getZhuMa(blueZhuMa[i]);
				if (i != blueBallTable.getHighlightBallNOs().length - 1) {
					blue_zhuma_string = blue_zhuma_string + ",";
				}
			}
			if (blue_zhuma_string.equals("  ")) {
				blueEdit.setText("");
				blueEdit.setHint(getResources().getString(
						R.string.ssq_zhixuan_edit_Prompt_blue));
			} else {
				blueEdit.setText(blue_zhuma_string);
			}
		}
	}

	/**
	 * ��ʽ�淨�ı�View
	 * 
	 * @param int aWhichGroupBall �������С��λ��
	 * @param int aBallViewId С��id
	 * @return void
	 */
	private void buy_DANSHI(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 0) { // ������
			int iChosenBallSum = 6;
			// ÿ�ε�����סС���״̬
			int isHighLight = redBallTable.changeBallState(iChosenBallSum,
					aBallViewId);

		} else if (aWhichGroupBall == 1) {
			int iChosenBallSum = 1;
			int isHighLight = blueBallTable.changeBallState(iChosenBallSum,
					aBallViewId);

		}
	}

	public static int /* String */getDisplayMetrics(Context cx) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		float density = dm.density;
		float xdpi = dm.xdpi;
		float ydpi = dm.ydpi;

		return screenWidth;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_ssq, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		PublicMethod.myOutput("************featureId " + featureId);
		switch (item.getItemId()) {
		case R.id.ssq_menu_confirm:
			beginTouZhu();
			break;
		case R.id.ssq_menu_reselect_num:
			beginReselect();
			break;
		case R.id.ssq_menu_game_introduce:
			showGameIntroduction();

			break;
		case R.id.ssq_menu_cancel:
			this.finish();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public int getChangingConfigurations() {
		// TODO Auto-generated method stub
		return super.getChangingConfigurations();
	}

	public void onCancelClick() {

	}

	/**
	 * �Ի���ص�����
	 * 
	 * @param int[] aNums �Ի��򷵻ص���ֵ
	 * @return BallTable
	 */
	public void onOkClick(int[] aNums) {

		if (aNums.length == 2) {
			iFushiRedBallNumber = aNums[0];
			iFushiBlueBallNumber = aNums[1];
			redBallTable.randomChooseConfigChange(iFushiRedBallNumber, 0);

			blueBallTable.randomChooseConfigChange(iFushiBlueBallNumber, 1);
			changeTextSumMoney();
		} else if (aNums.length == 3) {
			iDantuoRedDanNumber = aNums[0];
			iDantuoRedTuoNumber = aNums[1];
			iDantuoBlueNumber = aNums[2];

			int[] iTotalRandoms = PublicMethod.getRandomsWithoutCollision(
					aNums[0] + aNums[1], 0, 32);
			int[] iTotalRandomsBlue = PublicMethod.getRandomsWithoutCollision(
					aNums[2], 0, 15);

			if (isJiXuanDanTuo) {
				redBallTable.removeView();
				redTuoBallTable.removeView();
				blueBallTable.removeView();
				iScreenWidth = PublicMethod.getDisplayWidth(this);
				int[] iTotalRandomsRed = new int[iDantuoRedDanNumber];
				int[] iTotalRandomsRedTuo = new int[iDantuoRedTuoNumber];
				for (int i = 0; i < iDantuoRedDanNumber; i++) {
					iTotalRandomsRed[i] = iTotalRandoms[i];
				}
				for (int i = iDantuoRedDanNumber; i < (iDantuoRedDanNumber + iDantuoRedTuoNumber); i++) {
					iTotalRandomsRedTuo[i - iDantuoRedDanNumber] = iTotalRandoms[i];
				}
				iTotalRandomsRed = PublicMethod
						.orderby(iTotalRandomsRed, "abc");
				iTotalRandomsRedTuo = PublicMethod.orderby(iTotalRandomsRedTuo,"abc");
				iTotalRandomsBlue = PublicMethod.orderby(iTotalRandomsBlue,"abc");
				PublicMethod.makeBallTableJiXuan(redBallTable, iScreenWidth,redBallResId, iTotalRandomsRed, this);
				PublicMethod.makeBallTableJiXuan(redTuoBallTable, iScreenWidth,redBallResId, iTotalRandomsRedTuo, this);
				PublicMethod.makeBallTableJiXuan(blueBallTable, iScreenWidth,redBallResId, iTotalRandomsBlue, this);

				changeTextSumMoney();
			} else {
				redBallTable.clearAllHighlights();
				redTuoBallTable.clearAllHighlights();
				int i;

				for (i = iDantuoRedDanNumber; i < iDantuoRedDanNumber
						+ iDantuoRedTuoNumber; i++) {

					int isHighLight = redTuoBallTable.changeBallState(20,
							iTotalRandoms[i]);

				}
				blueBallTable.randomChooseConfigChange(iDantuoBlueNumber, 1);
				changeTextSumMoney();
			}

		}

	}

	// ��ʼͶע ��Ӧ���Ͷע��ť �� menu�����Ͷעѡ��
	private void beginTouZhu() {
		// ������ 7.4 �����޸ģ���ӵ�½���ж�
		// fqc eidt 2010/7/13 �޸�˫ɫ���淨
        //
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
				SsqActivity.this, "addInfo");
		String sessionIdStr = pre.getUserLoginInfo("sessionid");
		if (sessionIdStr.equals("")) {
			Intent intentSession = new Intent(SsqActivity.this, UserLogin.class);
			startActivityForResult(intentSession, 0);
		} else {
			if (iCurrentButton == PublicConst.BUY_SSQ_DANSHI) { // 1Ϊ��ʽͶע
				int iZhuShu = getZhuShu();
				if (redBallTable.getHighlightBallNums() != 6
						&& blueBallTable.getHighlightBallNums() != 1) {
					alert1("��ѡ��6�������1������");
				} else if (redBallTable.getHighlightBallNums() != 6) {
					alert1("��ѡ��6������");
				} else if (blueBallTable.getHighlightBallNums() != 1) {
					alert1("��ѡ��1������");
				} else if (iZhuShu * 2 > 100000) {
					dialogExcessive();
				} else {
					String sTouzhuAlert = "";
					sTouzhuAlert = getTouzhuAlert();
					alert(sTouzhuAlert);
				}
			}
			if (iCurrentButton == PublicConst.BUY_SSQ_ZHIXUAN) {
				if (isJiXuanZhiXuan) {
					if (balls.size() == 0) {
						alert1("������ѡ��1ע��Ʊ");
					} else {
						String sTouzhuAlert = "";
						sTouzhuAlert = getTouzhuAlertJixuan();
						alert_jixuan(sTouzhuAlert);
					}
				} else {
					int iZhuShu = getZhuShu();
					if (redBallTable.getHighlightBallNums() < 6
							&& blueBallTable.getHighlightBallNums() < 1) {
						alert1("������ѡ��6�������1������	");
					} else if (redBallTable.getHighlightBallNums() < 6) {
						alert1("��ѡ������6������");
					} else if (blueBallTable.getHighlightBallNums() < 1) {
						alert1("��ѡ��1������");
					}

					else if (iZhuShu * 2 > 100000) {
						dialogExcessive();
					} else {
						String sTouzhuAlert = "";
						sTouzhuAlert = getTouzhuAlert();
						alert(sTouzhuAlert);
					}
				}
			}
			if (iCurrentButton == PublicConst.BUY_SSQ_DANTUO) {
				int iZhuShu = getZhuShu();
				// //������ 7.4 �����޸ģ���ӵ�½���ж�

				int redNumber = redBallTable.getHighlightBallNums();
				int redTuoNumber = redTuoBallTable.getHighlightBallNums();
				int blueNumber = blueBallTable.getHighlightBallNums();
				if (redNumber + redTuoNumber > 25
						|| redNumber + redTuoNumber < 7 || redNumber < 1
						|| redNumber > 5 || blueNumber < 1 || blueNumber > 16
						|| redTuoNumber < 2 || redTuoNumber > 20) {
					alert1("��ѡ��:\n1~5����ɫ���룻\n" + " 2~20����ɫ���룻\n"
							+ " 1~16����ɫ��\n" + " �������������֮����7~25֮��");
				} else if (iZhuShu * 2 > 100000) {
					dialogExcessive();
				} else {

					String sTouzhuAlert = "";
					sTouzhuAlert = getTouzhuAlert();
					// Ӧ�õ��õ��ϵķ��� �³� 20100713
					alert_dantuo(sTouzhuAlert);
				}
			}
		}
	}

	/**
	 * ���menu�����»�ѡ �������»�ѡ�ķ���
	 */
	private void beginReselect() {

		if (iCurrentButton == PublicConst.BUY_SSQ_DANSHI) {
			redBallTable.clearAllHighlights();
			blueBallTable.clearAllHighlights();
		} else if (iCurrentButton == PublicConst.BUY_SSQ_ZHIXUAN) {
			redBallTable.clearAllHighlights();
			blueBallTable.clearAllHighlights();
		} else if (iCurrentButton == PublicConst.BUY_SSQ_DANTUO) {
			redBallTable.clearAllHighlights();
			blueBallTable.clearAllHighlights();
			redTuoBallTable.clearAllHighlights();
		}
	}

	/**
	 * ���menu������淨���ܣ�ͨ���Ի�����ʾ
	 */
	private void showGameIntroduction() {
		WebView webView = new WebView(this);
		String url = "file:///android_asset/ruyihelper_gameIntroduction_ssq.html";

		webView.loadUrl(url);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("�淨����");
		builder.setView(webView);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});
		builder.show();
	}

	// fqc delete ɾ��ȡ����ť 7/14/2010
	/**
	 * ��ʾ��1 ��������ѡ�����
	 * 
	 * @param string
	 *            ��ʾ����Ϣ
	 * @return
	 */
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
	 * ����ʽͶע���ú���
	 * 
	 * @param string
	 *            ��ʾ����Ϣ
	 * @return
	 */
	private void alert(String string) {

		Builder dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����")
				.setMessage(string).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								if (iCurrentButton == PublicConst.BUY_SSQ_DANSHI) {
									ssq_b_touzhu_danshi.setClickable(false);
									ssq_b_touzhu_danshi
											.setImageResource(R.drawable.touzhuup_n);
								} else if (iCurrentButton == PublicConst.BUY_SSQ_ZHIXUAN) {
									ssq_b_touzhu_fushi.setClickable(false);
									ssq_b_touzhu_fushi
											.setImageResource(R.drawable.touzhuup_n);
								}

								showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
								// �����Ƿ�ı�������ж� �³� 8.11
								Thread t = new Thread(new Runnable() {
									int iZhuShu = getZhuShu();
									int iQiShu = getQiShu();
									String str = "00";

									@Override
									public void run() {
										int iRedHighlights = redBallTable
												.getHighlightBallNums();
										int iBlueHighlights = blueBallTable
												.getHighlightBallNums();
										// int

										// �ж���˫ɫ��ʽ���Ǹ�ʽ
										if (iRedHighlights == 6
												&& iBlueHighlights == 1) {
											String zhuma = zhuma_danshi();
											str = payNew(zhuma, "" + iQiShu,
													iZhuShu * 200 * iQiShu + "");
										} else if ((iRedHighlights > 6 && iBlueHighlights == 1)
												|| (iRedHighlights == 6 && iBlueHighlights > 1)
												|| (iRedHighlights > 6 && iBlueHighlights > 1)) {//
											String zhuma_fushi = zhuma_fushi();
											str = payNew(zhuma_fushi, ""
													+ iQiShu, iZhuShu * 200
													* iQiShu + "");
										}

										if (iCurrentButton == PublicConst.BUY_SSQ_DANSHI) {
											ssq_b_touzhu_danshi
													.setClickable(true);
										} else if (iCurrentButton == PublicConst.BUY_SSQ_ZHIXUAN) {
											ssq_b_touzhu_fushi
													.setClickable(true);
										}
										Message msg1 = new Message();
										msg1.what = 10;
										handler.sendMessage(msg1);
										if (str.equals("000000")) {
											Message msg = new Message();
											msg.what = 6;
											handler.sendMessage(msg);
										} else if (str.equals("070002")) {
											Message msg = new Message();
											msg.what = 7;
											handler.sendMessage(msg);
										} else if (str.equals("040006")) {
											Message msg = new Message();
											msg.what = 1;
											handler.sendMessage(msg);
										} else if (str.equals("1007")) {
											Message msg = new Message();
											msg.what = 2;
											handler.sendMessage(msg);
										} else if (str.equals("040007")) {
											Message msg = new Message();
											msg.what = 4;
											handler.sendMessage(msg);
										} else if (str.equals("4444")) {
											Message msg = new Message();
											msg.what = 3;
											handler.sendMessage(msg);
										} else if (str.equals("00")) {
											Message msg = new Message();
											msg.what = 8;
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

						});
		dialog.show();

	}

	/**
	 * ����Ͷע���ú���
	 * 
	 * @param string
	 *            ��ʾ����Ϣ
	 * @return
	 */
	private void alert_dantuo(String string) {

		Builder dialog = new AlertDialog.Builder(this).setMessage(string).setTitle("��ѡ�����")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ssq_b_touzhu_dantuo
								.setImageResource(R.drawable.touzhuup_n);
						ssq_b_touzhu_dantuo.setClickable(false);

						showDialog(DIALOG1_KEY); // ��ʾ������ʾ��
						// �����Ƿ�ı�������ж� �³� 8.11
						Thread t = new Thread(new Runnable() {
							int iZhuShu = getZhuShu();
							int iQiShu = getQiShu();
							String str = "00";

							@Override
							public void run() {

								String zhuma_dantuo = zhuma_dantuo();
								str = payNew(zhuma_dantuo, iQiShu + "", iZhuShu
										* 200 * iQiShu + "");
								ssq_b_touzhu_dantuo.setClickable(true);
								Message msg1 = new Message();
								msg1.what = 10;
								handler.sendMessage(msg1);
								if (str.equals("000000")) {
									Message msg = new Message();
									msg.what = 6;
									handler.sendMessage(msg);
								} else if (str.equals("070002")) {
									Message msg = new Message();
									msg.what = 7;
									handler.sendMessage(msg);
								} else if (str.equals("040006")) {
									Message msg = new Message();
									msg.what = 1;
									handler.sendMessage(msg);
								} else if (str.equals("1007")) {
									Message msg = new Message();
									msg.what = 2;
									handler.sendMessage(msg);
								} else if (str.equals("040007")) {
									Message msg = new Message();
									msg.what = 4;
									handler.sendMessage(msg);
								} else if (str.equals("4444")) {
									Message msg = new Message();
									msg.what = 3;
									handler.sendMessage(msg);
								} else if (str.equals("00")) {
									Message msg = new Message();
									msg.what = 8;
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

						});
		dialog.show();

	}

	// Ͷע�½ӿ� 20100711�³�
	/**
	 * Ͷע�½ӿ�
	 * 
	 * @param bets
	 *            ע��
	 * @param count
	 *            ����
	 * @param amount
	 *            Ͷע�ܽ��
	 * @return
	 */
	protected String payNew(String bets, String count, String amount) {
		BettingInterface betting = BettingInterface.getInstance();

		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,
				"addInfo");
		String sessionid = shellRW.getUserLoginInfo("sessionid");
		String error_code = betting.bettingNew(bets, count, amount, sessionid);

		return error_code;
	}

	/**
	 * ��ʽͶעʱͶע�����
	 * 
	 * @return
	 */
	public String zhuma_danshi() {

		int zhumablue[] = null;
		int zhuma[] = null;
		int beishu = mSeekBarBeishu.getProgress();
		String t_str = "1512-F47104-";
		t_str += "00-";

		if (isJiXuanZhiXuan) {
			int zhushu = balls.size();
			if (zhushu < 10) {
				t_str += "0"+zhushu;
			}
			if (zhushu >= 10) {
				t_str += zhushu;
			}
	        t_str+="-";
			for (int j = 0; j < balls.size(); j++) {
				   t_str+="00";
				if (beishu < 10) {
					t_str += "0" +beishu;
				} else {
					t_str += "" + beishu;
				}
				zhuma = balls.get(j).getRed();
				zhumablue = balls.get(j).getBlue();
				for (int i = 0; i < zhuma.length; i++) {
					int zhuRed = zhuma[i] + 1;
					if (zhuRed >= 10) {
						t_str += zhuRed;
					} else if (zhuma[i] < 10) {
						t_str += "0" + zhuRed;
					}
				}
				int zhuBlue = zhumablue[0] + 1;
				if (zhuBlue >= 10) {
					t_str += "~" + zhuBlue;
				} else if (zhumablue[0] < 10) {
					t_str += "~0" + zhuBlue;
				}
				t_str += "^";
			}
		} else {

			t_str += "01-00";
			if (beishu < 10) {
				t_str += "0" +beishu;
			} else {
				t_str += "" + beishu;
			}
			zhuma = redBallTable.getHighlightBallNOs();
			zhumablue = blueBallTable.getHighlightBallNOs();
			for (int i = 0; i < zhuma.length; i++) {
				if (zhuma[i] >= 10) {
					t_str += zhuma[i];
				} else if (zhuma[i] < 10) {
					t_str += "0" + zhuma[i];
				}

			}
			if(zhumablue.length>0){
				if (zhumablue[0] >= 10) {
					t_str += "~" + zhumablue[0];
				} else if (zhumablue[0] < 10) {
					t_str += "~0" + zhumablue[0];
				}
			}
			t_str += "^";
		}

		return t_str;

	}

	/**
	 * ��ʽͶעʱͶע�����
	 * 
	 * @return
	 */
	public String zhuma_fushi() {
		int beishu = mSeekBarBeishu.getProgress();
		int iRedHighlights = redBallTable.getHighlightBallNums();
		int iBlueHighlights = blueBallTable.getHighlightBallNums();
		zhuma = redBallTable.getHighlightBallNOs();
		int zhumablue[] = blueBallTable.getHighlightBallNOs();
		String t_str = "1512-F47104-";
		if (iRedHighlights > 6 && iBlueHighlights == 1) {
			t_str += "10-01-10";
		} else if (iBlueHighlights > 1 && iRedHighlights == 6) {
			t_str += "20-01-20";
		} else if (iRedHighlights > 6 && iBlueHighlights > 1) {
			t_str += "30-01-30";
		}
		if (beishu < 10) {
			t_str += "0" + beishu;
		} else {
			t_str += "" + beishu;
		}
		t_str += "*";
		for (int i = 0; i < zhuma.length; i++) {
			if (zhuma[i] >= 10) {
				t_str += zhuma[i];
			} else if (zhuma[i] < 10) {
				t_str += "0" + zhuma[i];
			}

		}

		if (zhumablue[0] >= 10) {
			t_str += "~" + zhumablue[0];
		} else if (zhumablue[0] < 10) {
			t_str += "~0" + zhumablue[0];
		}
		for (int j = 1; j < zhumablue.length; j++) {
			if (zhumablue[j] >= 10) {
				t_str += +zhumablue[j];
			} else if (zhumablue[j] < 10) {
				t_str += "0" + zhumablue[j];
			}

		}
		t_str += "^";

		return t_str;
	}

	/**
	 * payNew ����ͶעʱͶע�����
	 * 
	 * @return
	 */
	public String zhuma_dantuo() {

		int beishu = mSeekBarBeishu.getProgress();

		int iBlueHighlights = blueBallTable.getHighlightBallNums();
		zhuma = redBallTable.getHighlightBallNOs();
		int[] tuozhuma = redTuoBallTable.getHighlightBallNOs();

		int zhumablue[] = blueBallTable.getHighlightBallNOs();
		String t_str = "1512-F47104-";
		if (iBlueHighlights == 1) {
			t_str += "40-01-40";

		} else if (iBlueHighlights > 1) {
			t_str += "50-01-50";
		}
		if (beishu < 10) {
			t_str += "0" + beishu;
		} else {
			t_str += "" + beishu;
		}
		for (int i = 0; i < zhuma.length; i++) {
			if (zhuma[i] >= 10) {
				t_str += zhuma[i];
			} else if (zhuma[i] < 10) {
				t_str += "0" + zhuma[i];
			}
		}
		t_str += "*";
		for (int j = 0; j < tuozhuma.length; j++) {
			if (tuozhuma[j] >= 10) {
				t_str += tuozhuma[j];
			} else if (tuozhuma[j] < 10) {
				t_str += "0" + tuozhuma[j];
			}
		}
		t_str += "~";
		for (int k = 0; k < zhumablue.length; k++) {
			if (zhumablue[k] >= 10) {
				t_str += +zhumablue[k];
			} else if (zhumablue[k] < 10) {
				t_str += "0" + zhumablue[k];
			}
			// t_str+="^";�Ƶ����� �³� 20100714
		}
		t_str += "^";
		return t_str;
	}

	// ����������ʾ�� 2010/7/4 �³�
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
	 * Ͷע��ʾ���е���Ϣ
	 */
	private String getTouzhuAlert() {
		int iZhuShu = getZhuShu();
		String red_zhuma_string = " ";
		int[] redZhuMa = redBallTable.getHighlightBallNOs();
		for (int i = 0; i < redBallTable.getHighlightBallNOs().length; i++) {
			red_zhuma_string = red_zhuma_string + String.valueOf(redZhuMa[i]);
			if (i != redBallTable.getHighlightBallNOs().length - 1) {
				red_zhuma_string = red_zhuma_string + ",";
			}
		}
		String blue_zhuma_string = " ";
		int[] blueZhuMa = blueBallTable.getHighlightBallNOs();
		for (int i = 0; i < blueBallTable.getHighlightBallNOs().length; i++) {
			blue_zhuma_string = blue_zhuma_string
					+ String.valueOf(blueZhuMa[i]);
			if (i != blueBallTable.getHighlightBallNOs().length - 1) {
				blue_zhuma_string = blue_zhuma_string + ",";
			}
		}
		if (iCurrentButton == PublicConst.BUY_SSQ_ZHIXUAN) {

			return "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"
					+ "\n" + "ע�룺" + red_zhuma_string + " | "
					+ blue_zhuma_string + "\n" + "ȷ��֧����";

		} else {
			String red_tuo_zhuma_string = " ";
			int[] redTuoZhuMa = redTuoBallTable.getHighlightBallNOs();
			for (int i = 0; i < redTuoBallTable.getHighlightBallNOs().length; i++) {
				red_tuo_zhuma_string = red_tuo_zhuma_string
						+ String.valueOf(redTuoZhuMa[i]);
				if (i != redTuoBallTable.getHighlightBallNOs().length - 1)
					red_tuo_zhuma_string = red_tuo_zhuma_string + ",";
			}
			return "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"
					+ "\n" + "ע�룺" + "\n" + "�����룺" + red_zhuma_string + "\n"
					+ "�������룺" + red_tuo_zhuma_string + "\n" + "����"
					+ blue_zhuma_string + "\n" + "ȷ��֧����";
		}
	}

	/**
	 * ����Ͷע����10��Ԫʱ�ĶԻ���
	 */
	private void dialogExcessive() {
		AlertDialog.Builder builder = new AlertDialog.Builder(SsqActivity.this);
		builder.setTitle(getResources().getString(R.string.toast_touzhu_title).toString());
		builder.setMessage("����Ͷע���ܴ���100000Ԫ");
		// ȷ��
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}

				});
		builder.show();
	}

	class Balls {
		int[] redNum = new int[6];
		int[] blueNum = new int[1];

		public Balls() {
			redNum = PublicMethod.getRandomsWithoutCollision(6, 0, 32);
			redNum = PublicMethod.orderby(redNum, "abc");
			blueNum = PublicMethod.getRandomsWithoutCollision(1, 0, 15);

		}

		public int[] getRed() {
			return redNum;

		}

		public int[] getBlue() {
			return blueNum;

		}

		public String getRedZhu() {
			String str = "";
			for (int i = 0; i < redNum.length; i++) {
				if (i != redNum.length - 1)
					str += PublicMethod.getZhuMa(redNum[i] + 1) + ",";
				else
					str += PublicMethod.getZhuMa(redNum[i] + 1);
			}
			return str;

		}

		public String getBlueZhu() {
			String str = "";
			for (int i = 0; i < blueNum.length; i++) {
				if (i != blueNum.length - 1)
					str += PublicMethod.getZhuMa(blueNum[i] + 1) + ",";
				else
					str += PublicMethod.getZhuMa(blueNum[i] + 1);
			}
			return str;

		}

	}

	/**
	 * ����ֱѡ��ѡ
	 */
	public void createTable(LinearLayout layout) {
		for (int i = 0; i < balls.size(); i++) {
			final int index = i;
			iScreenWidth = PublicMethod.getDisplayWidth(this);
			LinearLayout lines = new LinearLayout(layout.getContext());
			
			BallTable ballTableRed = new BallTable(lines, RED_BALL_START);
			
			
			PublicMethod.makeBallTableJiXuan(ballTableRed, iScreenWidth,
					redBallResId, balls.get(i).getRed(), this);
			BallTable ballTableBlue = new BallTable(lines, BLUE_BALL_START);
			PublicMethod.makeBallTableJiXuan(ballTableBlue, iScreenWidth,
					blueBallResId, balls.get(i).getBlue(), this);
			ImageButton delet = new ImageButton(lines.getContext());
			delet.setBackgroundResource(R.drawable.shanchu);
			delet.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (balls.size() > 1) {
						zhuView.removeAllViews();
						balls.remove(index);
						isOnclik = false;
						jixuanZhu.setSelection(balls.size() - 1);
						createTable(zhuView);
					} else {
						Toast.makeText(SsqActivity.this, getResources().getText(R.string.zhixuan_jixuan_toast),
								Toast.LENGTH_SHORT).show();

					}

				}
			});
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			param.setMargins(15, 10, 0, 0);
			lines.addView(delet, param);
			lines.setGravity(Gravity.CENTER_HORIZONTAL);
			layout.addView(lines);
		}

	}

	/**
	 * ֱѡ��ѡͶע��ʾ���е���Ϣ
	 */
	private String getTouzhuAlertJixuan() {
		String zhumaString = "";
		for (int i = 0; i < balls.size(); i++) {
			zhumaString += balls.get(i).getRedZhu() + "+"
					+ balls.get(i).getBlueZhu();
			if (i != balls.size() - 1) {
				zhumaString += "\n";
			}
		}
		int beishu = mSeekBarBeishu.getProgress();
		int iZhuShu = balls.size() * beishu;
		return "ע����"
				+ balls.size()
				+ "ע"
				+ "\n"
				+ // ע���������ϱ��� �³� 20100713
				"������" + beishu + "��" + "\n" + "׷�ţ�"
				+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
				+ (balls.size() * 2 * beishu) + "Ԫ" + "\n" + "�����"
				+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"
				+ "\n" + "ע�룺" + "\n" + zhumaString + "\n" + "ȷ��֧����";
	}

	/**
	 * ��ѡͶע���ú���
	 * 
	 * @param string
	 *            ��ʾ����Ϣ
	 * @return
	 */
	private void alert_jixuan(String string) {
		sensor.stopAction();
		Dialog dialog = new AlertDialog.Builder(this).setMessage(string).setTitle("��ѡ�����")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						ssq_b_touzhu_fushi.setImageResource(R.drawable.touzhuup_n);
						ssq_b_touzhu_fushi.setClickable(false);

						showDialog(DIALOG1_KEY); // ��ʾ������ʾ��
						Thread t = new Thread(new Runnable() {
							int beishu = mSeekBarBeishu.getProgress();
							int iZhuShu = balls.size() * beishu;
							int iQiShu = getQiShu();
							String str = "00";

							//

							@Override
							public void run() {

								String zhuma = zhuma_danshi();
								str = payNew(zhuma, iQiShu + "", iZhuShu * 200* iQiShu + "");
								ssq_b_touzhu_fushi.setClickable(true);
								Message msg1 = new Message();
								msg1.what = 10;
								handler.sendMessage(msg1);

								if (str.equals("000000")) {
									Message msg = new Message();
									msg.what = 6;
									handler.sendMessage(msg);
								} else if (str.equals("070002")) {
									Message msg = new Message();
									msg.what = 7;
									handler.sendMessage(msg);
								} else if (str.equals("040006")) {
									Message msg = new Message();
									msg.what = 1;
									handler.sendMessage(msg);
								} else if (str.equals("1007")) {
									Message msg = new Message();
									msg.what = 2;
									handler.sendMessage(msg);
								} else if (str.equals("040007")) {
									Message msg = new Message();
									msg.what = 4;
									handler.sendMessage(msg);
								} else if (str.equals("4444")) {
									Message msg = new Message();
									msg.what = 3;
									handler.sendMessage(msg);
								} else if (str.equals("00")) {
									Message msg = new Message();
									msg.what = 8;
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
		dialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				sensor.startAction();
			}
		});

	}

	class SsqSensor extends SensorActivity {

		public SsqSensor(Context context) {
			getContext(context);
		}

		@Override
		public void action() {
			zhuView.removeAllViews();
			balls = new Vector<Balls>();
			for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
				Balls ball = new Balls();
				balls.add(ball);
			}
			createTable(zhuView);
		}
	}

	/**
	 * intent�ص�����
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			beginTouZhu();
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//�ͷź���ͼƬ
		if(redBallTable != null && redBallTable.getBallViews()!= null){
			for (Iterator iterator = redBallTable.getBallViews().iterator(); iterator.hasNext();) {
				OneBallView ball = (OneBallView) iterator.next();
				ball.recycleBitmaps();
			}
		}
		redBallTable = null;
		
		//�ͷ�����ͼƬ
		if(blueBallTable != null && blueBallTable.getBallViews()!= null){
			for (Iterator iterator = blueBallTable.getBallViews().iterator(); iterator.hasNext();) {
				OneBallView ball = (OneBallView) iterator.next();
				ball.recycleBitmaps();
			}
		}
		blueBallTable = null;
		
		if(redTuoBallTable != null && redTuoBallTable.getBallViews()!= null){
			for (Iterator iterator = redTuoBallTable.getBallViews().iterator(); iterator.hasNext();) {
				OneBallView ball = (OneBallView) iterator.next();
				ball.recycleBitmaps();
			}
		}
		redTuoBallTable = null;
		
	}
	
	

}