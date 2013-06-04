package com.ruyicai.activity.game.qlc;

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
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.RuyicaiActivityManager;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.game.fc3d.Fc3d;
import com.ruyicai.activity.game.ssq.SsqActivity;
import com.ruyicai.activity.home.RuyicaiAndroid;
import com.ruyicai.activity.home.ScrollableTabActivity;
import com.ruyicai.dialog.Accoutdialog;
import com.ruyicai.dialog.ChooseNumberDialogQLC;
import com.ruyicai.handler.MyDialogListener;
import com.ruyicai.net.transaction.BettingInterface;
import com.ruyicai.net.transaction.SoftwareUpdateInterface;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.SensorActivity;
import com.ruyicai.util.ShellRWSharesPreferences;
import com.ruyicai.view.OneBallView;
import com.ruyicai.view.RadioStateDrawable;
import com.ruyicai.view.TabBarButton;

public class QLC extends Activity // �¼� �ص�����
		implements OnClickListener, // С�򱻵�� onClick
		SeekBar.OnSeekBarChangeListener, // SeekBar�ı� onProgressChanged
		RadioGroup.OnCheckedChangeListener, // ��ѡ��ѡ��仯 onCheckedChangeListener
		MyDialogListener { // �Ի���������ֵ onOKClick

	// С��ͼ�Ŀ��
	public static final int BALL_WIDTH = 55;
	// ������ǩ����
	private int iButtonNum = 3;

	// ����button��ǰ��λ��
	private int iCurrentButton; // 0x55660001 --- QLC danshi
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

	private static final int DIALOG1_KEY = 0;
	ProgressDialog progressdialog;

	ScrollView mHScrollView;

	SeekBar mSeekBarBeishu;
	SeekBar mSeekBarQishu;

	TextView mTextBeishu;
	TextView mTextQishu;

	TextView mTextSumMoney;
	ImageButton qlc_b_touzhu_danshi;
	ImageButton qlc_b_touzhu_fushi;
	ImageButton qlc_b_touzhu_dantuo;

	int iProgressBeishu = 1;
	int iProgressQishu = 1;

	private int iScreenWidth;
	// Vector<OneBallView> redBallViewVector;
	BallTable redBallTable = null;
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };

	BallTable redTuoBallTable = null;

	// Vector<OneBallView> blueBallViewVector;
	BallTable blueBallTable = null;

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
	// ʵ�ֺ��ݵ��л�

	private SensorManager mSensorManager;
	int lastAccelerometer = SensorManager.SENSOR_ACCELEROMETER;
	public int publicTopButton;
	public int type;
	public static final int ZHIXUAN = 0;
	public static final int DANTUO = 1;
	/** Called when the activity is first created. */
	// fulei
	private String issue[] = new String[2];
	private TextView term;
	private TextView title;
	private TextView time;
	private EditText danEdit;
	private EditText tuoEdit;
	private EditText redEdit;
	private boolean isJiXuanDanTuo = true;// �Ƿ��ǵ��ϻ�ѡ
	private boolean isJiXuanZhiXuan = false;// �Ƿ���ֱѡ��ѡ
	private SsqSensor sensor = new SsqSensor(this);
	private Spinner jixuanZhu;
	private Vector<Balls> balls = new Vector();
	private LinearLayout zhuView;
	private ImageButton imgJixuan;
	private boolean isOnclik = true;// �������Ƿ���Ӧ
	// ����Ͷע���ص���Ϣ
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.getData().getString("get");
			switch (msg.what) {
			case 0:

				break;
			case 1:
				progressdialog.dismiss();
				if(isFinishing() == false){
				    Accoutdialog.getInstance().createAccoutdialog(QLC.this, getResources().getString(R.string.goucai_Account_dialog_msg).toString());
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
			// case 5:
			// //��Ҫ���AlertDialogע��ʧ��
			// break;
			case 6:
				// //��Ҫ���AlertDialog��ʾ�û���¼�ɹ�
				progressdialog.dismiss();
				// Ͷע�ɹ��� �������
				if (iCurrentButton == PublicConst.BUY_QLC_DANSHI
						|| iCurrentButton == PublicConst.BUY_QLC_ZHIXUAN) {
					if (isJiXuanZhiXuan) {
						zhuView.removeAllViews();
					} else {
						redEdit.setText("");
						redBallTable.clearAllHighlights();
						changeTextSumMoney();
					}

				} else if (iCurrentButton == PublicConst.BUY_QLC_DANTUO) {
					danEdit.setText("");
					tuoEdit.setText("");
					redBallTable.removeView();
					redTuoBallTable.removeView();
					changeTextSumMoney();
				}	
				Intent intent2 = new Intent(UserLogin.SUCCESS);
				sendBroadcast(intent2);
				if(isFinishing() == false){
				    PublicMethod.showDialog(QLC.this);
				}
				break;
			case 7:
				progressdialog.dismiss();
				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(QLC.this, UserLogin.class);
				startActivity(intentSession);
				// Toast.makeText(getBaseContext(), "���¼",
				// Toast.LENGTH_LONG).show();
				// alert1("���¼");
				break;
			case 8:
				progressdialog.dismiss();
				// progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ��¼ʧ��
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				// // tv.setText(result);
				break;
			case 10:
				if (iCurrentButton == PublicConst.BUY_QLC_DANSHI) {
					qlc_b_touzhu_danshi.setImageResource(R.drawable.imageselecter);
				} else if (iCurrentButton == PublicConst.BUY_QLC_ZHIXUAN) {
					qlc_b_touzhu_fushi.setImageResource(R.drawable.imageselecter);
				} else if (iCurrentButton == PublicConst.BUY_QLC_DANTUO) {
					qlc_b_touzhu_dantuo.setImageResource(R.drawable.imageselecter);
				}
				break;
			}
			//			
		}
	};

	/*
	 * ��ں���
	 * 
	 * @param savedInstanceState
	 * 
	 * @return void
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		// ----- ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ----- ���ؿ�� layout
		setContentView(R.layout.layout_main_buy);
		title = (TextView) findViewById(R.id.layout_main_text_title);
		term = (TextView) findViewById(R.id.layout_main_text_issue);
		time = (TextView) findViewById(R.id.layout_main_text_time);
		// ��ȡ�ںźͽ�ֹʱ��
		JSONObject qlcLotnoInfo = PublicMethod.getCurrentLotnoBatchCode(Constants.LOTNO_QLC);
		if (qlcLotnoInfo != null) {
			// �ɹ���ȡ�����ں���Ϣ
			try {
				issue[0] = qlcLotnoInfo.getString("batchCode");
				issue[1] = qlcLotnoInfo.getString("endTime");
				term.setText("��" + this.issue[0] + "��");
				time.setText("��ֹʱ�䣺" + this.issue[1]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			issue[0] = "";
			issue[1] = "";
		    PublicMethod.getIssue(Constants.LOTNO_QLC,term,time,new Handler());
		}
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mHScrollView = (ScrollView) findViewById(R.id.scroll_global);
		// ��ȡ������id
		buyView = (LinearLayout) findViewById(R.id.layout_buy);
		// ----- ��ʼ��������ť
		initButtons();
		iCurrentButton = PublicConst.BUY_QLC_ZHIXUAN;
		createBuyView(iCurrentButton);

	}

	@Override
	public int getChangingConfigurations() {
		// TODO Auto-generated method stub
		return super.getChangingConfigurations();
	}

	@Override
	public Object getLastNonConfigurationInstance() {
		// TODO Auto-generated method stub
		return super.getLastNonConfigurationInstance();
	}

	/*
	 * ��������View�����ն�����ť��״̬����
	 * 
	 * @param int aWhichBuy ��ӦView��Id
	 * 
	 * @return void
	 */
	private void createBuyView(int aWhichBuy) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
		publicTopButton = aWhichBuy;
		switch (aWhichBuy) {

		case PublicConst.BUY_QLC_ZHIXUAN: // ֱѡ

			if (isJiXuanZhiXuan) {
				sensor.startAction();
				create_QLC_ZHIXUAN_JIXUAN();
			} else {
				sensor.stopAction();

				create_QLC_ZHIXUAN();
			}
			topButtonGroup.setSelected(false);
			commit();
			break;
		case PublicConst.BUY_QLC_DANTUO: // ����

			if (isJiXuanDanTuo) {

				create_QLC_DANTUO_JIXUAN();

			} else {

				create_QLC_DANTUO();

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
	private void create_QLC_DANTUO() {
		// topBar.setVisibility(View.GONE);
		type = DANTUO;
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_qlc_dantuo, null);
			title.setText("���ֲʵ���");
			int redBallViewNum = 30;
			int redBallViewWidth = QLC.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);
			
			PublicMethod.recycleBallTable(redBallTable);
			redBallTable = PublicMethod.makeBallTable(iV,
					R.id.table_red_danma_qlc, iScreenWidth, redBallViewNum, redBallResId, RED_BALL_START, 1, this,
					this);
			
			PublicMethod.recycleBallTable(redTuoBallTable);
			redTuoBallTable = PublicMethod.makeBallTable(iV,
					R.id.table_red_tuoma_qlc, iScreenWidth, redBallViewNum,redBallResId, RED_TUO_BALL_START, 1,
					this, this);

			mTextSumMoney = (TextView) iV
					.findViewById(R.id.qlc_dantuo_text_sum_money);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));

			mSeekBarBeishu = (SeekBar) iV
					.findViewById(R.id.qlc_dantuo_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) iV
					.findViewById(R.id.qlc_dantuo_seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) iV
					.findViewById(R.id.qlc_dantuo_text_beishu_change);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) iV
					.findViewById(R.id.qlc_dantuo_text_qishu_change);
			mTextQishu.setText("" + iProgressQishu);
			setSeekWhenAddOrSub(R.id.qlc_dantuo_seekbar_subtract_beishu, iV,
					-1, mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.qlc_dantuo_seekbar_add_beishu, iV, 1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.qlc_dantuo_seekbar_subtract_qihao, iV, -1,
					mSeekBarQishu, false);
			setSeekWhenAddOrSub(R.id.qlc_dantuo_seekbar_add_qihao, iV, 1,
					mSeekBarQishu, false);

			qlc_b_touzhu_dantuo = (ImageButton) iV
					.findViewById(R.id.qlc_dantuo_b_touzhu);
			qlc_b_touzhu_dantuo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					beginTouZhu();
				}

			});
			ImageButton newSelect = (ImageButton) iV
					.findViewById(R.id.qlc_dantuo_new_select);
			newSelect.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					tuoEdit.setText("");
					danEdit.setText("");
					redBallTable.clearAllHighlights();
					redTuoBallTable.clearAllHighlights();
					changeTextSumMoney();
				}
			});
			final LinearLayout danArea = (LinearLayout) iV
					.findViewById(R.id.balls_layout_qlc_danma);
			final LinearLayout tuoArea = (LinearLayout) iV
					.findViewById(R.id.balls_layout_qlc_tuoma);
			danArea.setVisibility(LinearLayout.GONE);
			final TextView danText = (TextView) iV
					.findViewById(R.id.qlc_danma_text);
			final TextView tuoText = (TextView) iV
					.findViewById(R.id.qlc_tuoma_text);
			danEdit = (EditText) iV.findViewById(R.id.qlc_danma_edit);
			tuoEdit = (EditText) iV.findViewById(R.id.qlc_tuoma_edit);

			danEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					if (hasFocus) {
						danEdit.setBackgroundResource(R.drawable.hongkuang);
						tuoArea.setVisibility(LinearLayout.GONE);
						danArea.setVisibility(LinearLayout.VISIBLE);
						danText.setTextColor(Color.RED);
					} else {
						danEdit.setBackgroundResource(R.drawable.huikuang);
						danText.setTextColor(Color.BLACK);
					}

				}
			});
			tuoEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub

					if (hasFocus) {
						tuoEdit.setBackgroundResource(R.drawable.hongkuang);
						danArea.setVisibility(LinearLayout.GONE);
						tuoArea.setVisibility(LinearLayout.VISIBLE);
						tuoText.setTextColor(Color.RED);
					} else {
						tuoEdit.setBackgroundResource(R.drawable.huikuang);
						tuoText.setTextColor(Color.BLACK);
					}
				}
			});

		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));

	}

	/*
	 * ������ʽ����View
	 * 
	 * @return void
	 */
	private void create_QLC_ZHIXUAN() {
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(R.layout.layout_qlc_fushi, null);
			title.setText("���ֲ�ֱѡ");

			int redBallViewNum = 30;
			int redBallViewWidth = BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);
			
			PublicMethod.recycleBallTable(redBallTable);
			PublicMethod.recycleBallTable(blueBallTable);
			redBallTable = PublicMethod.makeBallTable(iV,R.id.table_red_fushi_qlc, iScreenWidth, redBallViewNum, redBallResId, RED_BALL_START, 1, this,this);

			mTextSumMoney = (TextView) iV.findViewById(R.id.text_sum_money_fushi_qlc);
			mTextSumMoney.setText(getResources().getString(R.string.please_choose_number));

			mSeekBarBeishu = (SeekBar) iV
					.findViewById(R.id.qlc_fushi_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) iV
					.findViewById(R.id.qlc_fushi_seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) iV
					.findViewById(R.id.qlc_fushi_text_beishu);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) iV.findViewById(R.id.qlc_fushi_text_qishu);
			mTextQishu.setText("" + iProgressQishu);
			/*
			 * ����Ӽ�ͼ�꣬��seekbar�������ã�
			 * 
			 * @param int idFind, ͼ���id View iV, ��ǰ��view final int isAdd, 1��ʾ��
			 * -1��ʾ�� final SeekBar mSeekBar
			 * 
			 * @return void
			 */
			setSeekWhenAddOrSub(R.id.qlc_fushi_seekbar_subtract_beishu, iV, -1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.qlc_fushi_seekbar_add_beishu, iV, 1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.qlc_fushi_seekbar_subtract_qihao, iV, -1,
					mSeekBarQishu, false);
			setSeekWhenAddOrSub(R.id.qlc_fushi_seekbar_add_qishu, iV, 1,
					mSeekBarQishu, false);

			qlc_b_touzhu_fushi = (ImageButton) iV
					.findViewById(R.id.qlc_fushi_b_touzhu);
			qlc_b_touzhu_fushi.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					beginTouZhu();
				}

			});
			ImageButton newSelect = (ImageButton) iV
					.findViewById(R.id.qlc_zhixuan_new_select);
			newSelect.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					redEdit.setText("");
					redBallTable.clearAllHighlights();
					changeTextSumMoney();

				}
			});
			final TextView redText = (TextView) iV
					.findViewById(R.id.qlc_text_zhixuan_title);
			redEdit = (EditText) iV.findViewById(R.id.qlc_edit_zhixuan);

			redEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					if (hasFocus) {
						redEdit.setBackgroundResource(R.drawable.hongkuang);
						redText.setTextColor(Color.RED);
					} else {
						redEdit.setBackgroundResource(R.drawable.huikuang);
						redText.setTextColor(Color.BLACK);
					}

				}
			});

		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));

	}

	/**
	 * ֱѡ��ѡ
	 * 
	 */
	private void create_QLC_ZHIXUAN_JIXUAN() {
		buyView.removeAllViews();
		PublicMethod.recycleBallTable(redBallTable);
		PublicMethod.recycleBallTable(blueBallTable);
		Toast.makeText(this, "����ҡ���ֻ�������ѡ�ţ�", Toast.LENGTH_SHORT).show();
		balls = new Vector<Balls>();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_zhixuan_jixuan, null);

			title.setText("���ֲʻ�ѡ");
			// ��ʼ��spinner
			jixuanZhu = (Spinner) iV
					.findViewById(R.id.layout_zhixuan_jixuan_spinner);
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
			zhuView = (LinearLayout) iV
					.findViewById(R.id.layout_zhixuan_linear_zhuma);

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

		qlc_b_touzhu_fushi = (ImageButton) iV
				.findViewById(R.id.ssq_fushi_b_touzhu);
		qlc_b_touzhu_fushi.setOnClickListener(new OnClickListener() {
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
	 * ���ϻ�ѡ
	 * 
	 */
	private void create_QLC_DANTUO_JIXUAN() {
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_qlc_dantuo_jixuan, null);
			title.setText("���ֲʵ��ϻ�ѡ");
			
			PublicMethod.recycleBallTable(redBallTable);
			redBallTable = new BallTable(iV, R.id.table_red_danma_qlc,RED_BALL_START);
			PublicMethod.recycleBallTable(redTuoBallTable);
			redTuoBallTable = new BallTable(iV, R.id.table_red_tuoma_qlc,RED_TUO_BALL_START);

			mTextSumMoney = (TextView) iV.findViewById(R.id.qlc_dantuo_text_sum_money);
			mTextSumMoney.setText(getResources().getString(R.string.please_choose_number));

			mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.qlc_dantuo_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) iV.findViewById(R.id.qlc_dantuo_seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) iV.findViewById(R.id.qlc_dantuo_text_beishu_change);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) iV
					.findViewById(R.id.qlc_dantuo_text_qishu_change);
			mTextQishu.setText("" + iProgressQishu);
			setSeekWhenAddOrSub(R.id.qlc_dantuo_seekbar_subtract_beishu, iV,
					-1, mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.qlc_dantuo_seekbar_add_beishu, iV, 1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.qlc_dantuo_seekbar_subtract_qihao, iV, -1,
					mSeekBarQishu, false);
			setSeekWhenAddOrSub(R.id.qlc_dantuo_seekbar_add_qihao, iV, 1,
					mSeekBarQishu, false);

			qlc_b_touzhu_dantuo = (ImageButton) iV
					.findViewById(R.id.qlc_dantuo_b_touzhu);
			qlc_b_touzhu_dantuo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					beginTouZhu();
				}

			});
			imgJixuan = (ImageButton) iV
					.findViewById(R.id.qlc_dantuo_img_jixuan);

			imgJixuan.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ChooseNumberDialogQLC iChooseNumberDialog = new ChooseNumberDialogQLC(
							QLC.this, 1, QLC.this);
					iChooseNumberDialog.show();
				}
			});

		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));
		// ������ѡ��
		ChooseNumberDialogQLC iChooseNumberDialog = new ChooseNumberDialogQLC(
				QLC.this, 1, QLC.this);
		iChooseNumberDialog.show();

	}

	/*
	 * ��ʼ��������button
	 * 
	 * @return void
	 */
	private void initButtons() {

		initTopButtons();
		commit();
	}

	private void initTopButtons() {
//		topBar = (HorizontalScrollView) findViewById(R.id.topBar);
		topButtonGroup = (RadioGroup) findViewById(R.id.topMenu);

		defaultOffShade = RadioStateDrawable.SHADE_GRAY;
		defaultOnShade = RadioStateDrawable.SHADE_YELLOW;
		topButtonGroup.setOnCheckedChangeListener(this);
		topButtonLayoutParams = new RadioGroup.LayoutParams(/* 320/5 */64,
				RadioGroup.LayoutParams.WRAP_CONTENT);
	}

	/*
	 * ��Ӷ�����ť������ʾ��ʽ�淨View
	 * 
	 * @return void
	 */
	private void commit() {
		topButtonGroup.removeAllViews();
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
		int optimum_visible_items_in_portrait_mode = iButtonNum;

		int screen_width = getWindowManager().getDefaultDisplay().getWidth();
		int width;

		width = screen_width / iButtonNum;
		RadioStateDrawable.other_width = width;
		RadioStateDrawable.other_screen_width = screen_width;
		topButtonLayoutParams = new RadioGroup.LayoutParams(width,
				RadioGroup.LayoutParams.WRAP_CONTENT);

		for (int i = 0; i < iButtonNum; i++) {
			TabBarButton tabButton = new TabBarButton(this);

			tabButton.setState(topButtonIdOn[i], topButtonIdOff[i],3);
			tabButton.setId(i);
			tabButton.setGravity(Gravity.CENTER);
			topButtonGroup.addView(tabButton, i, topButtonLayoutParams);
		}

	}

	/*
	 * ���ö�����ť��λ��
	 * 
	 * @param int index ������ť������
	 * 
	 * @return void
	 */
	public void setCurrentTab(int index) {
		// topButtonGroup.check(index);
		// startGroupActivity(titleList.get(index).toString(),
		// (Intent)intentList.get(index));
		switch (index) {
		// case 0: // ��ʽ
		// iCurrentButton = PublicConst.BUY_QLC_DANSHI;
		// createBuyView(iCurrentButton);
		// break;
		case 0: // ��ʽ
			iCurrentButton = PublicConst.BUY_QLC_ZHIXUAN;
			createBuyView(iCurrentButton);
			break;
		case 1: // ����
			iCurrentButton = PublicConst.BUY_QLC_DANTUO;
			createBuyView(iCurrentButton);
			break;
		}
	}

	/*
	 * ������ť�仯�Ļص�����
	 * 
	 * @param RadioGroup group ����������ù���
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
			Intent intent1 = new Intent(QLC.this, RuyicaiAndroid.class);
			startActivity(intent1);
			finish();
			break;
		case 1:// ֱѡ
			iCurrentButton = PublicConst.BUY_QLC_ZHIXUAN;
			isJiXuanZhiXuan = !isJiXuanZhiXuan;
			if (!isJiXuanZhiXuan && !isJiXuanDanTuo) {
				isJiXuanDanTuo = true;
			}
			createBuyView(iCurrentButton);
			break;
		case 2: // ����
			sensor.stopAction();
			iCurrentButton = PublicConst.BUY_QLC_DANTUO;
			isJiXuanDanTuo = !isJiXuanDanTuo;
			if (!isJiXuanZhiXuan && !isJiXuanDanTuo) {
				isJiXuanZhiXuan = true;
			}
			createBuyView(iCurrentButton);
			break;
		case 3:// ����
//			sensor.stopAction();
//			Intent intent = new Intent(QLC.this, JoinHall.class);
//			startActivity(intent);
//			topButtonGroup.check(1);
			break;
		}

		mHScrollView.fullScroll(ScrollView.FOCUS_UP);
	}

	/*
	 * ��ȡ��ǰ����button��id
	 * 
	 * @return int id
	 */
	public int getCurrentTab() {
		return topButtonGroup.getCheckedRadioButtonId();
	}

	/*
	 * SeekBar�ı�ʱ�Ļص�����
	 * 
	 * @param SeekBar seekBar �����仯��SeekBarʵ��
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
		// TODO Auto-generated method stub
		if (progress < 1)
			seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();

		switch (seekBar.getId()) {
		// ��ʽ
		case R.id.qlc_fushi_seek_beishu:
		case R.id.qlc_dantuo_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			changeTextSumMoney();
			break;
		case R.id.qlc_fushi_seek_qishu:
		case R.id.qlc_dantuo_seek_qishu:
			iProgressQishu = iProgress;
			mTextQishu.setText("" + iProgressQishu);
			break;
		// ��ʽ
		case R.id.ssq_fushi_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			if (!isJiXuanZhiXuan)
				changeTextSumMoney();
			break;
		case R.id.ssq_fushi_seek_qishu:
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
		// TODO Auto-generated method stub

	}

	// Seekbar�ص����� ��ʱ����
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	/*
	 * ��ӦС�򱻵���Ļص�����
	 * 
	 * @param View v �������view
	 * 
	 * @return void
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int iBallId = v.getId();
		// red ball
		if (iBallId >= RED_BALL_START && iBallId < BLUE_BALL_START) {
			int iBallViewId = v.getId() - RED_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				PublicMethod.myOutput("----- red:" + iBallViewId
						+ " buttonnum:" + iCurrentButton);
				// redBallTable.ballViewVector.elementAt(iBallViewId).showNextId();
				changeBuyViewByRule(iCurrentButton, 0, iBallViewId);// �������
			}
		} else if (iBallId >= BLUE_BALL_START && iBallId < RED_TUO_BALL_START) {
			int iBallViewId = v.getId() - BLUE_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				PublicMethod.myOutput("----- blue:" + iBallViewId);
				// blueBallTable.ballViewVector.elementAt(iBallViewId).showNextId();
				changeBuyViewByRule(iCurrentButton, 1, iBallViewId);// ��������
			}
		} else {
			int iBallViewId = v.getId() - RED_TUO_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				// PublicMethod.myOutput("----- blue:"+iBallViewId);
				// blueBallTable.ballViewVector.elementAt(iBallViewId).showNextId();
				changeBuyViewByRule(iCurrentButton, 2, iBallViewId);// ��������
			}
		}
		// text_sum_money
		changeTextSumMoney();
	}

	/*
	 * ˫ɫ��ע���ļ��㷽��
	 * 
	 * @return int ����ע��
	 */
	private int getZhuShu() {
		int iReturnValue = 0;
		switch (iCurrentButton) {
		case PublicConst.BUY_QLC_DANSHI:
			iReturnValue = mSeekBarBeishu.getProgress();
			iSendZhushu = 1;
			break;
		case PublicConst.BUY_QLC_ZHIXUAN:
			int iRedBalls = redBallTable.getHighlightBallNums();
			// int iBlueBalls = blueBallTable.getHighlightBallNums();
			iReturnValue = (int) getQLCFSZhuShu(iRedBalls);
			// PublicMethod.myOutput("-----***"+iReturnValue+" "+iRedBalls+" "+iBlueBalls);
			break;
		case PublicConst.BUY_QLC_DANTUO:
			int iRedHighlights = redBallTable.getHighlightBallNums();
			int iRedTuoHighlights = redTuoBallTable.getHighlightBallNums();
			// int iBlueHighlights = blueBallTable.getHighlightBallNums();
			iReturnValue = (int) getQLCDTZhuShu(iRedHighlights,
					iRedTuoHighlights);
			break;
		default:
			break;
		}
		return iReturnValue;
	}

	// ��ȡ���� 2010/7/11 �³�
	private int getQiShu() {
		int iReturnValue = 0;
		switch (iCurrentButton) {
		case PublicConst.BUY_QLC_DANSHI:
			iReturnValue = mSeekBarQishu.getProgress();
			break;
		case PublicConst.BUY_QLC_ZHIXUAN:
			iReturnValue = mSeekBarQishu.getProgress();
			break;
		case PublicConst.BUY_QLC_DANTUO:
			iReturnValue = mSeekBarQishu.getProgress();
			break;
		default:
			break;
		}
		return iReturnValue;
	}

	/*
	 * �����淨ע�����㷽��
	 * 
	 * @param int aRedBalls �������
	 * 
	 * @param int aRedTuoBalls �����������
	 * 
	 * @param int aBlueBalls �������
	 * 
	 * @return long ע��
	 */
	private long getQLCDTZhuShu(int aRedBalls, int aRedTuoBalls) {// �õ�˫ɫ���ϵ�ע��
		long qlcZhuShu = 0L;
		iSendZhushu = 0L;
		if (aRedBalls > 0 && aRedTuoBalls > 0) {
			qlcZhuShu += (PublicMethod.zuhe(7 - aRedBalls, aRedTuoBalls) * iProgressBeishu);
			iSendZhushu = PublicMethod.zuhe(7 - aRedBalls, aRedTuoBalls);
		}
		return qlcZhuShu;
	}

	/*
	 * ��ʽ�淨ע�����㷽��
	 * 
	 * @param int aRedBalls �������
	 * 
	 * @param int aBlueBalls �������
	 * 
	 * @return long ע��
	 */
	private long getQLCFSZhuShu(int aRedBalls) {
		long qlcZhuShu = 0L;
		iSendZhushu = 0L;
		if (aRedBalls > 0) {
			qlcZhuShu += (PublicMethod.zuhe(7, aRedBalls) * iProgressBeishu);
			iSendZhushu = PublicMethod.zuhe(7, aRedBalls);
		}
		return qlcZhuShu;

	}

	private long getSendZhushu() {
		return iSendZhushu;
	}

	private int getBeishu() {
		return mSeekBarBeishu.getProgress();

	}

	private int getQishu() {
		return mSeekBarQishu.getProgress();

	}

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

	/*
	 * �ı���ʾ��Ϣ
	 * 
	 * @return void
	 */
	public void changeTextSumMoney() {
		switch (iCurrentButton) {
		case PublicConst.BUY_QLC_DANSHI:
			// ��ʽView
			if (redBallTable.getHighlightBallNums() < 7) {
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_red_number));
			} else {

				int iZhuShu = getZhuShu();
				String iTempString = "��" + iZhuShu + "ע����" + (iZhuShu * 2)
						+ "Ԫ";
				mTextSumMoney.setText(iTempString);

			}
			break;
		case PublicConst.BUY_QLC_ZHIXUAN: {
			int iRedHighlights = redBallTable.getHighlightBallNums();
			// int iBlueHighlights = blueBallTable.getHighlightBallNums();

			// ������ ����//fqc edit ���������������ʱ����ʾ��Ӧ����ʾ
			if (iRedHighlights < 8) {
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_number));
			}
			// �������ﵽ���Ҫ��
			if (iRedHighlights == 7) {
				int iZhuShu = getZhuShu();
				String iTempString = "��" + iZhuShu + "ע����" + (iZhuShu * 2)
						+ "Ԫ";
				mTextSumMoney.setText(iTempString);
			} else if (iRedHighlights >= 8) {

				int iZhuShu = getZhuShu();
				String iTempString = "��ǰΪ��ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ";
				mTextSumMoney.setText(iTempString);

			}

			break;
		}
		case PublicConst.BUY_QLC_DANTUO: {
			int iRedDanHighlights = redBallTable.getHighlightBallNums();
			// int iBlueHighlights = blueBallTable.getHighlightBallNums();
			int iRedTuoHighlights = redTuoBallTable.getHighlightBallNums();

			if (iRedDanHighlights + iRedTuoHighlights < 8) {
				mTextSumMoney.setText(getResources().getString(
						R.string.choose_number_dialog_tip5));
			}

			else {
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

	// �����淨�ı䵱ǰView ����Ӧ
	// param1 - aWhichTopButton ��ǰ������ǩλ��
	// param2 - aWhichGroupBall �ڼ���С���絥ʽ������С�򣬴�0��ʼ����
	// param3 - aBallViewId ��clickС���id����0��ʼ������С������ʾ������Ϊid+1
	private void changeBuyViewByRule(int aWhichTopButton, int aWhichGroupBall,
			int aBallViewId) {
		switch (aWhichTopButton) {
		case PublicConst.BUY_QLC_DANSHI:
			// ��ʽView�У���2��ball table
			buy_DANSHI(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.BUY_QLC_ZHIXUAN:
			buy_ZHIXUAN(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.BUY_QLC_DANTUO:
			buy_DANTUO(aWhichGroupBall, aBallViewId);
			break;
		default:
			break;
		}
	}

	/*
	 * �����淨�ı�View
	 * 
	 * @param int aWhichGroupBall �������С��λ��
	 * 
	 * @param int aBallViewId С��id
	 * 
	 * @return void
	 */
	private void buy_DANTUO(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 0) { // ���� ���� ���6��С��
			int iChosenBallSum = 6;
			if (redTuoBallTable.getHighlightBallNums() > 20)
				iChosenBallSum = 26 - redTuoBallTable.getHighlightBallNums();
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
				danEdit.setText("");
				danEdit.setHint(getResources().getString(
						R.string.qlc_dantuo_edit_Prompt_danma));
			} else {
				danEdit.setText(red_zhuma_string);
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
				tuoEdit.setText("");
				tuoEdit.setHint(getResources().getString(
						R.string.qlc_dantuo_edit_Prompt_tuoma));
			} else {
				tuoEdit.setText(red_tuo_zhuma_string);
			}

		} else if (aWhichGroupBall == 1) { // ������

		} else if (aWhichGroupBall == 2) { // ���� ���� ���29��С��

			int iChosenBallSum = 25;
			if (redBallTable.getHighlightBallNums() > 1)
				iChosenBallSum = 26 - redBallTable.getHighlightBallNums();
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
				danEdit.setText("");
				danEdit.setHint(getResources().getString(
						R.string.qlc_dantuo_edit_Prompt_danma));
			} else {
				danEdit.setText(red_zhuma_string);
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
				tuoEdit.setText("");
				tuoEdit.setHint(getResources().getString(
						R.string.qlc_dantuo_edit_Prompt_tuoma));
			} else {
				tuoEdit.setText(red_tuo_zhuma_string);
			}
		}
	}

	/*
	 * ��ʽ�淨�ı�View
	 * 
	 * @param int aWhichGroupBall �������С��λ��
	 * 
	 * @param int aBallViewId С��id
	 * 
	 * @return void
	 */
	private void buy_ZHIXUAN(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 0) { // ������
			// ���ѡȡ16������
			int iChosenBallSum = 16;
			// ÿ�ε�����סС���״̬
			int isHighLight = redBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			PublicMethod.myOutput("****isHighLight " + isHighLight
					+ "PublicConst.BALL_TO_HIGHLIGHT "
					+ PublicConst.BALL_TO_HIGHLIGHT);

			// ��¼�������fulei
			String red_zhuma_string = "  ";
			int[] redZhuMa = redBallTable.getHighlightBallNOs();
			for (int i = 0; i < redBallTable.getHighlightBallNOs().length; i++) {
				red_zhuma_string += PublicMethod.getZhuMa(redZhuMa[i]);
				if (i != redBallTable.getHighlightBallNOs().length - 1)
					red_zhuma_string = red_zhuma_string + ",";
			}

			if (red_zhuma_string.equals("  ")) {
				redEdit.setText("");
			} else {
				redEdit.setText(red_zhuma_string);
			}

		} else if (aWhichGroupBall == 1) {

		}
	}

	/*
	 * ��ʽ�淨�ı�View
	 * 
	 * @param int aWhichGroupBall �������С��λ��
	 * 
	 * @param int aBallViewId С��id
	 * 
	 * @return void
	 */
	private void buy_DANSHI(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 0) { // ������
			int iChosenBallSum = 7;
			int isHighLight = redBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			PublicMethod.myOutput("****isHighLight " + isHighLight
					+ "PublicConst.BALL_TO_HIGHLIGHT "
					+ PublicConst.BALL_TO_HIGHLIGHT);

		} else if (aWhichGroupBall == 1) {

		}
	}

	public static int /* String */getDisplayMetrics(Context cx) {
		String str = "";
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
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_qlc, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.qlc_menu_confirm:
			beginTouZhu();
			break;
		case R.id.qlc_menu_reselect_num:
			beginReselect();
			break;
		case R.id.qlc_menu_game_introduce:
			showGameIntroduction();

			break;
		case R.id.qlc_menu_cancel:
			this.finish();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public void onCancelClick() {
		// TODO Auto-generated method stub
		// PublicVarAndFun.OutPutString("--->>>Dialog Cancel");
	}

	/*
	 * �Ի���ص�����
	 * 
	 * @param int[] aNums �Ի��򷵻ص���ֵ
	 * 
	 * @return BallTable
	 */
	public void onOkClick(int[] aNums) {
		// TODO Auto-generated method stub

		// ��ʽ ����2������
		if (aNums.length == 2) {
			iFushiRedBallNumber = aNums[0];
			iFushiBlueBallNumber = aNums[1];
			// redBallTable.randomChoose(iFushiRedBallNumber);
			// blueBallTable.randomChoose(iFushiBlueBallNumber);
			redBallTable.randomChooseConfigChange(iFushiRedBallNumber, 0);
			changeTextSumMoney();
		} else if (aNums.length == 3) {
			iDantuoRedDanNumber = aNums[0];
			iDantuoRedTuoNumber = aNums[1];
			// iDantuoBlueNumber = aNums[2];
			// ��ѡ��ΧΪ29 wyl 20100714
			int[] iTotalRandoms = PublicMethod.getRandomsWithoutCollision(
					aNums[0] + aNums[1], 0, 29);
			if (isJiXuanDanTuo) {
				redBallTable.removeView();
				redTuoBallTable.removeView();
				iScreenWidth = PublicMethod.getDisplayWidth(this);

				int[] iTotalRandomsRedDan = new int[iDantuoRedDanNumber];
				int[] iTotalRandomsRedTuo = new int[iDantuoRedTuoNumber];
				for (int i = 0; i < iDantuoRedDanNumber; i++) {
					iTotalRandomsRedDan[i] = iTotalRandoms[i];
				}
				for (int i = iDantuoRedDanNumber; i < iDantuoRedDanNumber+ iDantuoRedTuoNumber; i++) {
					iTotalRandomsRedTuo[i - iDantuoRedDanNumber] = iTotalRandoms[i];
				}
				iTotalRandomsRedDan = PublicMethod.orderby(iTotalRandomsRedDan,"abc");
				iTotalRandomsRedTuo = PublicMethod.orderby(iTotalRandomsRedTuo,"abc");
				PublicMethod.makeBallTableJiXuan(redBallTable, iScreenWidth, redBallResId, iTotalRandomsRedDan,this);
				PublicMethod.makeBallTableJiXuan(redTuoBallTable, iScreenWidth, redBallResId, iTotalRandomsRedTuo,this);
				changeTextSumMoney();
			} else {
				redBallTable.clearAllHighlights();
				redTuoBallTable.clearAllHighlights();
				int i;
				for (i = 0; i < iDantuoRedDanNumber; i++) {
					int isHighLight = redBallTable.changeBallState(6,iTotalRandoms[i]);
				}
				for (i = iDantuoRedDanNumber; i < iDantuoRedDanNumber
						+ iDantuoRedTuoNumber; i++) {
					int isHighLight = redTuoBallTable.changeBallState(29,iTotalRandoms[i]);
				}
				changeTextSumMoney();
			}

		}

	}

	// ��ʼͶע ��Ӧ���Ͷע��ť �� menu�����Ͷעѡ��
	private void beginTouZhu() {
		// ������ 7.4 �����޸ģ���ӵ�½���ж�
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(QLC.this,
				"addInfo");
		String sessionIdStr = pre.getUserLoginInfo("sessionid");
		if (sessionIdStr == null || sessionIdStr.equals("")) {
			Intent intentSession = new Intent(QLC.this, UserLogin.class);
			startActivityForResult(intentSession,0);
		} else{
		 if (iCurrentButton == PublicConst.BUY_QLC_DANSHI) {
			int iZhuShu = getZhuShu();
			// ��ȡ���� �³� 20100711
			int iQiShu = getQiShu();
		 if (redBallTable.getHighlightBallNums() < 7) {
				alert1("��ѡ��7����");
			} else if (iZhuShu * 2 > 100000) {
				DialogExcessive();
			} else {
				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				alert(sTouzhuAlert);
			}
		} else if (iCurrentButton == PublicConst.BUY_QLC_ZHIXUAN) {
			if (isJiXuanZhiXuan) {
				if (sessionIdStr == null || sessionIdStr.equals("")) {
					Intent intentSession = new Intent(QLC.this, UserLogin.class);
					startActivity(intentSession);
					// �³� 20100728 �ж����������ƣ�����ѡ��6������1������
				} else if (balls.size() == 0) {
					alert1("������ѡ��1ע��Ʊ");
				} else {

					String sTouzhuAlert = "";
					sTouzhuAlert = getTouzhuAlertJixuan();
					alert_jixuan(sTouzhuAlert);
				}

			} else {
				int iZhuShu = getZhuShu();
				int iQiShu = getQiShu();
				 if (redBallTable.getHighlightBallNums() < 7
						|| redBallTable.getHighlightBallNums() > 16) {
					alert1("������ѡ��7~16����");
				} else if (iZhuShu * 2 > 100000) {
					DialogExcessive();
				} else {
					// alert("��ѡ�����"+iZhuShu+"ע���ֲʸ�ʽ��Ʊ��"+"����"+(iZhuShu*2)+"Ԫ");

					String sTouzhuAlert = "";
					sTouzhuAlert = getTouzhuAlert();
					alert(sTouzhuAlert);
				}
			}
		} else if (iCurrentButton == PublicConst.BUY_QLC_DANTUO) {
			int iZhuShu = getZhuShu();
			int iQiShu = getQiShu();
			int redballno = redBallTable.getHighlightBallNums();
			int redtuoballno = redTuoBallTable.getHighlightBallNums();
			int redNumber = redBallTable.getHighlightBallNums();
			int redTuoNumber = redTuoBallTable.getHighlightBallNums();
			// int blueNumber = blueBallTable.getHighlightBallNums();
			 if ((redNumber < 1 || redNumber > 6)&& (redTuoNumber < 1 || redTuoNumber > 29)) {
				alert1("��ѡ��1~6�����룬1~29�����룡");
			} else if (redNumber + redTuoNumber < 8) {
				alert1("���������֮������Ϊ8����");
			} else if (iZhuShu <= 0) {
				alert1("���������֮������Ϊ8����");
			} else if (iZhuShu * 2 > 100000) {
				DialogExcessive();
			} else {
				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				alert_dantuo(sTouzhuAlert);
			}
		  }
		}
	}

	// ���menu�����»�ѡ �������»�ѡ�ķ���
	private void beginReselect() {

		if (iCurrentButton == PublicConst.BUY_QLC_DANSHI) {
			redBallTable.clearAllHighlights();
		} else if (iCurrentButton == PublicConst.BUY_QLC_ZHIXUAN) {
			redBallTable.clearAllHighlights();
		} else if (iCurrentButton == PublicConst.BUY_QLC_DANTUO) {
			redBallTable.clearAllHighlights();
			redTuoBallTable.clearAllHighlights();
		}
	}

	// ���menu������淨���ܣ�ͨ���Ի�����ʾ
	private void showGameIntroduction() {
		WebView webView = new WebView(this);
		String url = "file:///android_asset/ruyihelper_gameIntroduction_qlc.html";
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
	 * ���ֲʵ���ʽͶע���ú���
	 * 
	 * @param String
	 *            ��ʾ����Ϣ
	 * @return
	 */
	private void alert(String string) {
		String zhuma = zhuma_danshi();

		Builder dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����")
				.setMessage(string).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (iCurrentButton == PublicConst.BUY_QLC_DANSHI) {
									qlc_b_touzhu_danshi.setClickable(false);
									qlc_b_touzhu_danshi
											.setImageResource(R.drawable.touzhuup_n);
								} else if (iCurrentButton == PublicConst.BUY_QLC_ZHIXUAN) {
									qlc_b_touzhu_fushi.setClickable(false);
									qlc_b_touzhu_fushi
											.setImageResource(R.drawable.touzhuup_n);
								}
								showDialog(DIALOG1_KEY);
								// TODO Auto-generated method stub
								// �����Ƿ�ı�������ж� �³� 8.11
								Thread t = new Thread(new Runnable() {
									int iZhuShu = getZhuShu();
									int iQiShu = getQiShu();
									String str = "00";

									@Override
									public void run() {
										// TODO Auto-generated method stub
										int iRedHighlights = redBallTable
												.getHighlightBallNums();

										// �ж������ֲʵ�ʽ���Ǹ�ʽ
										if (iRedHighlights == 7) {
											String zhuma = zhuma_danshi();

											str = pay(zhuma, iQiShu + "",iZhuShu * iQiShu * 200 + "");

										} else if (iRedHighlights > 7) {
											String zhuma_fushi = zhuma_fushi();
											str = pay(zhuma_fushi, iQiShu + "",iZhuShu * iQiShu * 200 + "");
										}

										if (iCurrentButton == PublicConst.BUY_QLC_DANSHI) {
											qlc_b_touzhu_danshi.setClickable(true);
										} else if (iCurrentButton == PublicConst.BUY_QLC_ZHIXUAN) {
											qlc_b_touzhu_fushi.setClickable(true);
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
								// TODO Auto-generated method stub
							}

						});
		dialog.show();

	}

	/**
	 * ���ֲʵ���Ͷע���ú���
	 * 
	 * @param String
	 *            ��ʾ����Ϣ
	 * @return
	 */
	private void alert_dantuo(String string) {
		// String zhuma=zhuma_danshi();

		Builder dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����").setMessage(string)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						qlc_b_touzhu_dantuo
								.setImageResource(R.drawable.touzhuup_n);
						qlc_b_touzhu_dantuo.setClickable(false);
						showDialog(DIALOG1_KEY);

						showDialog(DIALOG1_KEY);
						// TODO Auto-generated method stub
						// �����Ƿ�ı�������ж� �³� 8.11
						Thread t = new Thread(new Runnable() {
							int iZhuShu = getZhuShu();
							int iQiShu = getQiShu();
							String str = "00";

							@Override
							public void run() {
								String zhuma = zhuma_dantuo();
								// �½ӿ� 2010/7/11 �³�
								str = pay(zhuma, iQiShu + "", iZhuShu * iQiShu
										* 200 + "");

								qlc_b_touzhu_dantuo.setClickable(true);
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
								// TODO Auto-generated method stub
							}

						});
		dialog.show();

	}

	// count ���� amount ��Ǯ�� �½ӿ� �³�20100711
	/**
	 * Ͷע�½ӿ�
	 * 
	 * @param bets
	 *            ע��
	 * @param count
	 *            ����
	 * @param amount
	 *            Ͷע�ܽ��
	 * @param
	 * @param
	 */
	protected String pay(String bets, String count, String amount) {
		// TODO Auto-generated method stub
		BettingInterface betting = BettingInterface.getInstance();

		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,
				"addInfo");
		String sessionid = shellRW.getUserLoginInfo("sessionid");

		String error_code = betting.bettingNew(bets, count, amount, sessionid);

		return error_code;
	}

	/**
	 * ���ֲʵ�ʽʽע���ʽ
	 * 
	 * @param
	 * @return
	 */
	private String zhuma_danshi() {
		// int zhushu=getZhuShu();
		int beishu = getBeishu();
		int[] zhuma=null;
		String t_str = "1512-F47102-";
		if (isJiXuanZhiXuan) {
			int zhushu = balls.size() ;
			t_str += "00-";
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
					t_str += "0" + beishu;
				}
				if (beishu >= 10) {
					t_str += beishu;
				}
				zhuma = balls.get(j).getRed();
				for (int i = 0; i < zhuma.length; i++) {
					int zhu = zhuma[i] + 1;
					if (zhu >= 10) {
						t_str += zhu;
					} else if (zhu < 10) {
						t_str += "0" + zhu;
					}
				}
				t_str += "^";
			}
		} else {
			t_str += "00-";

			if (beishu < 10) {
				t_str += "0"+beishu;
			}
			if (beishu >= 10) {
				t_str += beishu;
			}
			t_str += "-00";
			if (beishu < 10) {
				t_str += "0" + beishu;
			}
			if (beishu >= 10) {
				t_str += beishu;
			}
			zhuma = redBallTable.getHighlightBallNOs();
			for (int i = 0; i < zhuma.length; i++) {
				if (zhuma[i] >= 10) {
					t_str += zhuma[i];
				} else if (zhuma[i] < 10) {
					t_str += "0" + zhuma[i];
				}
			}
			t_str += "^";
		}

		return t_str;
	}

	/**
	 * ���ֲʸ�ʽע���ʽ
	 * 
	 * @param
	 * @return
	 */
	private String zhuma_fushi() {
		int beishu = getBeishu();
		long sendzhushu = getSendZhushu();
		zhuma = redBallTable.getHighlightBallNOs();
		String t_str = "1512-F47102-";
		t_str += "10-";
		if (sendzhushu < 10) {
			t_str += "0" + sendzhushu;
		}
		if (sendzhushu >= 10) {
			t_str += sendzhushu;
		}
		t_str += "-10";
		if (beishu < 10) {
			t_str += "0" + beishu;
		}
		if (beishu >= 10) {
			t_str += beishu;
		}
		t_str += "*";
		for (int i = 0; i < zhuma.length; i++) {
			if (zhuma[i] >= 10) {
				t_str += zhuma[i];
			} else if (zhuma[i] < 10) {
				t_str += "0" + zhuma[i];
			}
		}
		t_str += "^";
		PublicMethod.myOutput("-----------------qilecaifushi" + t_str);
		return t_str;
	}

	/**
	 * ���ֲʵ���ע���ʽ
	 * 
	 * @param
	 * @return
	 */
	private String zhuma_dantuo() {
		int beishu = getBeishu();
		long sendzhushu = getSendZhushu();
		zhuma = redBallTable.getHighlightBallNOs();
		int[] zhumablue = redTuoBallTable.getHighlightBallNOs();
		String t_str = "1512-F47102-";
		t_str += "20-";
		if (sendzhushu < 10) {
			t_str += "0" + sendzhushu;
		}
		if (sendzhushu >= 10) {
			t_str += sendzhushu;
		}
		t_str += "-20";
		if (beishu < 10) {
			t_str += "0" + beishu;
		}
		if (beishu >= 10) {
			t_str += beishu;
		}

		for (int i = 0; i < zhuma.length; i++) {
			if (zhuma[i] >= 10) {
				t_str += zhuma[i];
			} else if (zhuma[i] < 10) {
				t_str += "0" + zhuma[i];
			}
		}
		t_str += "*";
		for (int i = 0; i < zhumablue.length; i++) {
			if (zhumablue[i] >= 10) {
				t_str += zhumablue[i];
			} else if (zhumablue[i] < 10) {
				t_str += "0" + zhumablue[i];
			}
		}
		t_str += "^";
		PublicMethod.myOutput("-----------------qilecaifushi" + t_str);
		return t_str;
	}

	public void showHighLight() {
		if (iCurrentButton == PublicConst.BUY_QLC_DANSHI) {
			topButtonGroup.check(1);
			topButtonGroup.check(0);
			topButtonGroup.invalidate();
		} else if (iCurrentButton == PublicConst.BUY_QLC_ZHIXUAN)
			topButtonGroup.check(1);
		else
			topButtonGroup.check(2);
		PublicMethod.myOutput("**********topButtonGroup.check(0);   "
				+ iCurrentButton + " PublicConst.BUY_QLC_DANSHI  "
				+ PublicConst.BUY_QLC_DANSHI);
	}

	/**
	 * ����������ʾ��
	 * 
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
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

	private String getTouzhuAlert() {
		int iZhuShu = getZhuShu();
		String red_zhuma_string = " ";
		int[] redZhuMa = redBallTable.getHighlightBallNOs();
		for (int i = 0; i < redBallTable.getHighlightBallNOs().length; i++) {
			red_zhuma_string = red_zhuma_string + String.valueOf(redZhuMa[i]);
			if (i != redBallTable.getHighlightBallNOs().length - 1)
				red_zhuma_string = red_zhuma_string + ",";
		}

		if (iCurrentButton == PublicConst.BUY_QLC_DANSHI
				|| iCurrentButton == PublicConst.BUY_QLC_ZHIXUAN) {

			return "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" + "�����"
					+ (2 * (mSeekBarQishu.getProgress() - 1) * iZhuShu) + "Ԫ"
					+ "\n" + "ע�룺" + "\n" + red_zhuma_string + "\n" + "ȷ��֧����";

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
					+ "\n" + "ע�룺\n" + "���룺" + red_zhuma_string + "\n" + "���룺"
					+ red_tuo_zhuma_string + "\n" + "ȷ��֧����";
		}
	}

	/**
	 * ����Ͷע����10��Ԫʱ�ĶԻ���
	 */
	private void DialogExcessive() {
		AlertDialog.Builder builder = new AlertDialog.Builder(QLC.this);
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
		int[] redNum = new int[7];

		public Balls() {
			redNum = PublicMethod.getRandomsWithoutCollision(7, 0, 29);
			redNum = PublicMethod.orderby(redNum, "abc");

		}

		public int[] getRed() {
			return redNum;

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

	}

	/**
	 * �ֻ���
	 * 
	 * @author Administrator
	 * 
	 */
	class SsqSensor extends SensorActivity {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.palmdream.netintface.SensorActivity#action()
		 */
		public SsqSensor(Context context) {
			getContext(context);
		}

		@Override
		public void action() {
			zhuView.removeAllViews();
			balls = new Vector();
			for (int i = 0; i < jixuanZhu.getSelectedItemPosition() + 1; i++) {
				Balls ball = new Balls();
				balls.add(ball);
			}
			createTable(zhuView);
		}
	}

	/**
	 * ����ֱѡ��ѡ����
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

		for (int i = 0; i < balls.size(); i++) {
			final int index = i;
			iScreenWidth = PublicMethod.getDisplayWidth(this);
			LinearLayout lines = new LinearLayout(layout.getContext());
			BallTable ballTableRed = new BallTable(lines, RED_BALL_START);
			PublicMethod.makeBallTableJiXuan(ballTableRed, iScreenWidth, redBallResId, balls.get(i)
									.getRed(), this);
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
								QLC.this,
								getResources().getText(
										R.string.zhixuan_jixuan_toast),
								Toast.LENGTH_SHORT).show();
					}

				}
			});
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
		
			param.setMargins(15, 10, 0, 0);
			lines.addView(delet,param);
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
			zhumaString += balls.get(i).getRedZhu();
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
	 * ����Ͷע���ú���
	 * 
	 * @param string
	 *            ��ʾ����Ϣ
	 * @return
	 */
	private void alert_jixuan(String string) {
		sensor.stopAction();
		Dialog dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����").setMessage(string)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						qlc_b_touzhu_fushi
								.setImageResource(R.drawable.touzhuup_n);
						qlc_b_touzhu_fushi.setClickable(false);

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
								str = pay(zhuma, iQiShu + "", iZhuShu * 200
										* iQiShu + "");
								// }

								qlc_b_touzhu_fushi.setClickable(true);
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sensor.stopAction();
	}
	/**
	 * intent�ص�����
	 * 
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
		        beginTouZhu();
			break;
		}
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		recycleBallTable(redBallTable);
		recycleBallTable(redTuoBallTable);
		recycleBallTable(blueBallTable);
		
	}
	
	
	private void recycleBallTable(BallTable balltable){
		if(balltable != null && balltable.getBallViews()!= null){
			for (Iterator iterator = balltable.getBallViews().iterator(); iterator.hasNext();) {
				OneBallView ball = (OneBallView) iterator.next();
				ball.recycleBitmaps();
			}
		}
	}

}