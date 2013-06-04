package com.palmdream.RuyicaiAndroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid.BallBetPublicClass.BallHolder;
import com.palmdream.netintface.BettingInterface;
import com.palmdream.netintface.iHttp;

public class QLC extends Activity // �¼� �ص�����
		implements OnClickListener, // С�򱻵�� onClick
		SeekBar.OnSeekBarChangeListener, // SeekBar�ı� onProgressChanged
		RadioGroup.OnCheckedChangeListener, // ��ѡ��ѡ��仯 onCheckedChangeListener
		MyDialogListener { // �Ի���������ֵ onOKClick

	// С��ͼ�Ŀ��
	public static final int BALL_WIDTH = 32;
	// ������ǩ����
	private int iButtonNum = 3;

	// ����button��ǰ��λ��
	private int iCurrentButton; // 0x55660001 --- QLC danshi
	private int tempCurrentButton;
	/*
	 * Button danshiButton; Button fushiButton; Button dantuoButton;
	 */
	public static final int WANFA_START_ID = 0x55550001;

	private HorizontalScrollView topBar;
	private RadioGroup.LayoutParams topButtonLayoutParams;
	private RadioGroup topButtonGroup;

	private int defaultOffShade;
	private int defaultOnShade;

	int topButtonStringId[] = { R.string.danshi, R.string.fushi,
			R.string.dantuo };

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
	Button qlc_btn_newSelect;
	CheckBox mCheckBox;

	int iProgressBeishu = 1;
	int iProgressQishu = 1;

	private int iScreenWidth;
	// Vector<OneBallView> redBallViewVector;
	BallTable redBallTable = null;
	private int redBallResId[] = { R.drawable.grey, R.drawable.red };

	BallTable redTuoBallTable = null;
	private int redTuoBallResId[] = { R.drawable.grey, R.drawable.red };

	// Vector<OneBallView> blueBallViewVector;
	BallTable blueBallTable = null;
	private int blueBallResId[] = { R.drawable.grey, R.drawable.blue };
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;

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
	private BallHolder mBallHolder = null;
	private BallHolder tempBallHolder = null;
	private BallBetPublicClass ballBetPublicClass = new BallBetPublicClass();

	private SensorManager mSensorManager;
	int lastAccelerometer = SensorManager.SENSOR_ACCELEROMETER;
	public int publicTopButton;
	/** Called when the activity is first created. */
	// ����Ͷע���ص���Ϣ
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.getData().getString("get");
			switch (msg.what) {
			case 0:
				// Toast.makeText(getBaseContext(), "���¼",
				// Toast.LENGTH_LONG).show();
				// alert1("���¼");
				break;
			case 1:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ������", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾע��ɹ�
				break;
			case 2:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ѽ���", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ�û���ע��
				break;
			case 3:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "ϵͳ���㣬���Ժ�", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾϵͳ���㣬���Ժ�
				break;
			case 4:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�޿����߼���", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ�úű���ͣ����ϵ�ͷ�
				break;
			// case 5:
			// //��Ҫ���AlertDialogע��ʧ��
			// break;
			case 6:
				// //��Ҫ���AlertDialog��ʾ�û���¼�ɹ�
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷע�ɹ�", Toast.LENGTH_LONG)
						.show();
//				Ͷע�ɹ��� �������
				if(iCurrentButton==PublicConst.BUY_QLC_DANSHI||iCurrentButton==PublicConst.BUY_QLC_FUSHI){
					redBallTable.clearAllHighlights();
				}else if(iCurrentButton==PublicConst.BUY_QLC_DANTUO){
					redBallTable.clearAllHighlights();
					redTuoBallTable.clearAllHighlights();
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
				Toast.makeText(getBaseContext(), "�����쳣", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ��¼ʧ��
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ��", Toast.LENGTH_LONG)
						.show();
				// // tv.setText(result);
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
		super.onCreate(savedInstanceState);

		// ----- ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// ----- ���ؿ�� layout
		setContentView(R.layout.layout_main_buy);
		ImageButton returnBtn = (ImageButton) findViewById(R.id.goucaitouzhu_title_return);
		returnBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
		TextView title = (TextView) findViewById(R.id.goucaitouzhu_title);
		title.setText(getResources().getString(R.string.qilecai));
		mHScrollView = (ScrollView) findViewById(R.id.scroll_global);
		// ��ȡ������id
		buyView = (LinearLayout) findViewById(R.id.layout_buy);
		// ----- ��ʼ��������ť
		initButtons();

		// ----- Ĭ�� ���ص�ʽlayout
		// iCurrentButton = PublicConst.BUY_QLC_DANSHI;
		// createBuyView(iCurrentButton);
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

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		PublicMethod.myOutput("********onConfigurationChanged");

		mBallHolder.flag = 1;
		mBallHolder.DanShi.iBeiShu = getBeishu();
		mBallHolder.DanShi.iQiShu = getQishu();
		mBallHolder.DanShi.bCheckBox = getCheckBox();

		PublicMethod
				.myOutput("********mBallHolder.DanShi.bCheckBox            "
						+ mBallHolder.DanShi.bCheckBox);
		tempBallHolder = mBallHolder;
		tempCurrentButton = iCurrentButton;
		if (mBallHolder == null) {
			mBallHolder = ballBetPublicClass.new BallHolder();
			PublicMethod.myOutput("********onConfigurationChanged null");
		}
		initButtons();
		// setCurrentTab(0);
		iCurrentButton = tempCurrentButton;
		createBuyView(iCurrentButton);
		// �л�֮����ʾ������radioButton
		showHighLight();

		PublicMethod.myOutput("********iCurrentButton" + iCurrentButton);

		mBallHolder = tempBallHolder;
		if (publicTopButton == PublicConst.BUY_QLC_DANSHI) {
			create_QLC_DANSHI();
			changeTextSumMoney();
		} else if (publicTopButton == PublicConst.BUY_QLC_FUSHI) {
			create_QLC_FUSHI();
			changeTextSumMoney();
		} else if (publicTopButton == PublicConst.BUY_QLC_DANTUO) {
			create_QLC_DANTUO();
			changeTextSumMoney();
		}
		mBallHolder.flag = 0;
		super.onConfigurationChanged(newConfig);
	}

	// @Override
	// public Object onRetainNonConfigurationInstance() {
	// TODO Auto-generated method stub
	// for(int i=0;i<33;i++){
	// PublicMethod.myOutput("******mBallHolder[i]" +"  "+i+"  " +
	// mBallHolder.DanShi.iRedBallStatus[i]);
	// }
	// mBallHolder.topButtonGroup = iCurrentButton;
	// return mBallHolder;
	// return super.onRetainNonConfigurationInstance();
	// }

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
		case PublicConst.BUY_QLC_DANSHI: // ��ʽ
			create_QLC_DANSHI();
			break;
		case PublicConst.BUY_QLC_FUSHI: // ��ʽ
			create_QLC_FUSHI();
			break;
		case PublicConst.BUY_QLC_DANTUO: // ����
			create_QLC_DANTUO();
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
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_qlc_dantuo, null);
		{
			int redBallViewNum = 30;
			int redBallViewWidth = QLC.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			redBallTable = makeBallTable(iV, R.id.table_red_danma_qlc,
					iScreenWidth, redBallViewNum, redBallViewWidth,
					redBallResId, RED_BALL_START);
			redTuoBallTable = makeBallTable(iV, R.id.table_red_tuoma_qlc,
					iScreenWidth, redBallViewNum, redBallViewWidth,
					redBallResId, RED_TUO_BALL_START);

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

			qlc_btn_newSelect = (Button) iV
					.findViewById(R.id.qlc_dantuo_btn_newSelect);
			qlc_btn_newSelect.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					mBallHolder = ballBetPublicClass.new BallHolder();
					ChooseNumberDialogQLC iChooseNumberDialog = new ChooseNumberDialogQLC(
							QLC.this, 1, QLC.this);
					iChooseNumberDialog.show();
				}

			});
			qlc_b_touzhu_dantuo = (ImageButton) iV
					.findViewById(R.id.qlc_dantuo_b_touzhu);
			qlc_b_touzhu_dantuo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					beginTouZhu();
				}

			});
		}
		if (mBallHolder.flag == 1) {
			redBallTable
					.changeBallStateConfigChange(mBallHolder.DanShi.iRedBallStatus);
			redTuoBallTable
					.changeBallStateConfigChange(mBallHolder.DanShi.iTuoRedBallStatus);
			// blueBallTable.changeBallStateConfigChange(mBallHolder.DanShi.iBlueBallStatus);
			mSeekBarBeishu.setProgress(mBallHolder.DanShi.iBeiShu);
			mSeekBarQishu.setProgress(mBallHolder.DanShi.iQiShu);
		}
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));

		mCheckBox = (CheckBox) this.findViewById(R.id.qlc_dantuo_cb_jixuan);
		if (mBallHolder.flag == 1) {
			mCheckBox.setChecked(mBallHolder.DanShi.bCheckBox);
			if (mBallHolder.DanShi.bCheckBox)
				qlc_btn_newSelect.setVisibility(View.VISIBLE);
			else
				qlc_btn_newSelect.setVisibility(View.INVISIBLE);
		}
		mCheckBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							if (mBallHolder.flag != 1) {
								// redBallTable.randomChoose("red");
								// blueBallTable.randomChoose("blue");
								ChooseNumberDialogQLC iChooseNumberDialog = new ChooseNumberDialogQLC(
										QLC.this, 1, QLC.this);
								iChooseNumberDialog.show();
								qlc_btn_newSelect.setVisibility(View.VISIBLE);
								// }
							}
						}
						// fqc 20100714
						else {
							qlc_btn_newSelect.setVisibility(View.INVISIBLE);
						}
					}
				});
		/*
		 * //���toggleButton���¼����� ToggleButton toggleButton = (ToggleButton)
		 * this.findViewById(R.id.cb_jixuan_dantuo);
		 * toggleButton.setOnCheckedChangeListener(new
		 * OnCheckedChangeListener(){
		 * 
		 * @Override public void onCheckedChanged(CompoundButton buttonView,
		 * boolean isChecked) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * });
		 */
	}

	/*
	 * ������ʽ����View
	 * 
	 * @return void
	 */
	private void create_QLC_FUSHI() {
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_qlc_fushi, null);
		{
			int redBallViewNum = 30;
			int redBallViewWidth = QLC.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			redBallTable = makeBallTable(iV, R.id.table_red_fushi_qlc,
					iScreenWidth, redBallViewNum, redBallViewWidth,
					redBallResId, RED_BALL_START);

			mTextSumMoney = (TextView) iV
					.findViewById(R.id.text_sum_money_fushi_qlc);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));

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

			qlc_btn_newSelect = (Button) iV
					.findViewById(R.id.qlc_fushi_btn_newSelect);
			qlc_btn_newSelect.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					mBallHolder = ballBetPublicClass.new BallHolder();
					ChooseNumberDialogQLC iChooseNumberDialog = new ChooseNumberDialogQLC(
							QLC.this, 0, QLC.this);
					iChooseNumberDialog.show();
				}

			});
			qlc_b_touzhu_fushi = (ImageButton) iV
					.findViewById(R.id.qlc_fushi_b_touzhu);
			qlc_b_touzhu_fushi.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					beginTouZhu();
				}

			});
		}
		// ����Ļ�л���ʱ���ͼ�Ժ󣬰��Ѿ�������С����ʾ����
		if (mBallHolder.flag == 1) {
			redBallTable
					.changeBallStateConfigChange(mBallHolder.DanShi.iRedBallStatus);
			// blueBallTable.changeBallStateConfigChange(mBallHolder.DanShi.iBlueBallStatus);
			mSeekBarBeishu.setProgress(mBallHolder.DanShi.iBeiShu);
			mSeekBarQishu.setProgress(mBallHolder.DanShi.iQiShu);

		}
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));
		mCheckBox = (CheckBox) this.findViewById(R.id.qlc_fushi_cb_jixuan);
		if (mBallHolder.flag == 1) {
			mCheckBox.setChecked(mBallHolder.DanShi.bCheckBox);
			if (mBallHolder.DanShi.bCheckBox)
				qlc_btn_newSelect.setVisibility(View.VISIBLE);
			else
				qlc_btn_newSelect.setVisibility(View.INVISIBLE);
		}
		mCheckBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							if (mBallHolder.flag != 1) {// �ǵ���¼�������ѡ��checkbox���������������󴥷����¼���
								ChooseNumberDialogQLC iChooseNumberDialog = new ChooseNumberDialogQLC(
										QLC.this, 0, QLC.this);
								iChooseNumberDialog.show();
								// redBallTable.randomChoose("red");
								// blueBallTable.randomChoose("blue");
								qlc_btn_newSelect.setVisibility(View.VISIBLE);
								changeTextSumMoney();
							}
						} else {
							qlc_btn_newSelect.setVisibility(View.INVISIBLE);
						}
					}
				});
		/*
		 * //���toggleButton���¼����� ToggleButton toggleButton = (ToggleButton)
		 * this.findViewById(R.id.cb_jixuan_fushi);
		 * toggleButton.setOnCheckedChangeListener(new
		 * OnCheckedChangeListener(){
		 * 
		 * @Override public void onCheckedChanged(CompoundButton buttonView,
		 * boolean isChecked) { // TODO Auto-generated method stub
		 * if(isChecked){ redBallTable.randomChoose("red");
		 * blueBallTable.randomChoose("blue"); changeTextSumMoney(); } } });
		 */
	}

	/*
	 * ������ʽ����View
	 * 
	 * @return void
	 */
	private void create_QLC_DANSHI() {
		buyView.removeAllViews();
		// View iV = View.inflate(this, R.layout.layout_qlc_danshi, null);
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_qlc_danshi, null);
		{
			int redBallViewNum = 30;
			int redBallViewWidth = QLC.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			redBallTable = makeBallTable(iV, R.id.table_red_qlc, iScreenWidth,
					redBallViewNum, redBallViewWidth, redBallResId,
					RED_BALL_START);
			// redBallTable.tableLayout.

			mTextSumMoney = (TextView) iV
					.findViewById(R.id.qlc_danshi_text_sum_money);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));

			mSeekBarBeishu = (SeekBar) iV
					.findViewById(R.id.qlc_danshi_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) iV
					.findViewById(R.id.qlc_danshi_seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) iV
					.findViewById(R.id.qlc_danshi_text_beishu);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) iV.findViewById(R.id.qlc_danshi_text_qishu);
			mTextQishu.setText("" + iProgressQishu);
			/*
			 * ����Ӽ�ͼ�꣬��seekbar�������ã�
			 * 
			 * @param int idFind, ͼ���id View iV, ��ǰ��view final int isAdd, 1��ʾ��
			 * -1��ʾ�� final SeekBar mSeekBar
			 * 
			 * @return void
			 */
			setSeekWhenAddOrSub(R.id.qlc_danshi_seekbar_subtract_beishu, iV,
					-1, mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.qlc_danshi_seekbar_add_beishu, iV, 1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.qlc_danshi_seekbar_subtract_qihao, iV, -1,
					mSeekBarQishu, false);
			setSeekWhenAddOrSub(R.id.qlc_danshi_seekbar_add_qihao, iV, 1,
					mSeekBarQishu, false);

			qlc_btn_newSelect = (Button) iV
					.findViewById(R.id.qlc_danshi_btn_newSelect);
			qlc_btn_newSelect.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					mBallHolder = ballBetPublicClass.new BallHolder();
					mBallHolder = redBallTable.randomChooseConfigChange(7,
							mBallHolder, 0);
					// mBallHolder =
					// blueBallTable.randomChooseConfigChange(1,mBallHolder,1);
					// redBallTable.randomChoose(7);
				}

			});
			// Ͷעbutton��Ӧ�¼�
			qlc_b_touzhu_danshi = (ImageButton) iV
					.findViewById(R.id.qlc_danshi_b_touzhu);
			qlc_b_touzhu_danshi.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					beginTouZhu();
				}

			});
		}
		// ����Ļ�л���ʱ���ͼ�Ժ󣬰��Ѿ�������С����ʾ����
		if (mBallHolder.flag == 1) {
			redBallTable
					.changeBallStateConfigChange(mBallHolder.DanShi.iRedBallStatus);
			// blueBallTable.changeBallStateConfigChange(mBallHolder.DanShi.iBlueBallStatus);
			mSeekBarBeishu.setProgress(mBallHolder.DanShi.iBeiShu);
			mSeekBarQishu.setProgress(mBallHolder.DanShi.iQiShu);
			// iCB_Danshi.setChecked(mBallHolder.DanShi.bCheckBox);
			//			
		}
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));

		mCheckBox = (CheckBox) this.findViewById(R.id.qlc_danshi_jixuan_cb);
		if (mBallHolder.flag == 1) {
			mCheckBox.setChecked(mBallHolder.DanShi.bCheckBox);
			if (mBallHolder.DanShi.bCheckBox)
				qlc_btn_newSelect.setVisibility(View.VISIBLE);
			else
				qlc_btn_newSelect.setVisibility(View.INVISIBLE);
		}
		mCheckBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							if (mBallHolder.flag != 1) {
								mBallHolder = ballBetPublicClass.new BallHolder();
								// redBallTable.randomChoose(7);
								// blueBallTable.randomChoose(1);
								mBallHolder = redBallTable
										.randomChooseConfigChange(7,
												mBallHolder, 0);
								//
								// mBallHolder =
								// blueBallTable.randomChooseConfigChange(1,mBallHolder,1);

								qlc_btn_newSelect.setVisibility(View.VISIBLE);
								// redBallTable.randomChoose("red");
								// blueBallTable.randomChoose("blue");
								changeTextSumMoney();
							}
						} else {
							qlc_btn_newSelect.setVisibility(View.INVISIBLE);
						}
					}
				});

		/*
		 * //���toggleButton���¼����� ToggleButton toggleButton = (ToggleButton)
		 * this.findViewById(R.id.cb_jixuan_danshi);
		 * toggleButton.setOnCheckedChangeListener(new
		 * OnCheckedChangeListener(){
		 * 
		 * @Override public void onCheckedChanged(CompoundButton buttonView,
		 * boolean isChecked) { // TODO Auto-generated method stub
		 * if(isChecked){ redBallTable.randomChoose("red");
		 * blueBallTable.randomChoose("blue"); } } });
		 */
	}

	/*
	 * ��ʼ��������button
	 * 
	 * @return void
	 */
	private void initButtons() {
		if (mBallHolder != null)
			if (mBallHolder.flag == 1)
				initTopButtons();
		if (mBallHolder == null)
			initTopButtons();
		commit();
	}

	private void initTopButtons() {
		topBar = (HorizontalScrollView) findViewById(R.id.topBar);
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
			// int[] iconStates = { RadioStateDrawable.SHADE_GRAY,
			// RadioStateDrawable.SHADE_GREEN};
			// tabButton.setState(
			// getResources().getString(stringId[i]),iconStates[0]);
			// tabButton.setState(getResources().getString(stringId[i]),drawableId[i%2],
			// iconStates[0], iconStates[1]);
			tabButton.setState(getResources().getString(topButtonStringId[i]));
			tabButton.setId(i);
			tabButton.setGravity(Gravity.CENTER);
			topButtonGroup.addView(tabButton, i, topButtonLayoutParams);
		}
		if (getLastNonConfigurationInstance() != null) {
			mBallHolder = (BallHolder) getLastNonConfigurationInstance();
			int buttonGroup = mBallHolder.topButtonGroup;
			PublicMethod.myOutput("*********buttonGroup " + buttonGroup);
			setCurrentTab(buttonGroup);
		} else {
			mBallHolder = ballBetPublicClass.new BallHolder();
			setCurrentTab(0);
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
		topButtonGroup.check(index);
		// startGroupActivity(titleList.get(index).toString(),
		// (Intent)intentList.get(index));
		switch (index) {
		case 0: // ��ʽ
			iCurrentButton = PublicConst.BUY_QLC_DANSHI;
			createBuyView(iCurrentButton);
			break;
		case 1: // ��ʽ
			iCurrentButton = PublicConst.BUY_QLC_FUSHI;
			createBuyView(iCurrentButton);
			break;
		case 2: // ����
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
		mBallHolder = ballBetPublicClass.new BallHolder();

		switch (checkedId) {
		case 0: // ��ʽ
			iCurrentButton = PublicConst.BUY_QLC_DANSHI;
			createBuyView(iCurrentButton);
			break;
		case 1: // ��ʽ
			iCurrentButton = PublicConst.BUY_QLC_FUSHI;
			createBuyView(iCurrentButton);
			break;
		case 2: // ����
			iCurrentButton = PublicConst.BUY_QLC_DANTUO;
			createBuyView(iCurrentButton);
			break;
		}
		// PublicMethod.myOutput("-----top"+mHScrollView.getTop());
		// mHScrollView.scrollBy(0, mHScrollView.getTop());
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
		case R.id.qlc_danshi_seek_beishu:
		case R.id.qlc_fushi_seek_beishu:
		case R.id.qlc_dantuo_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			changeTextSumMoney();
			break;
		case R.id.qlc_danshi_seek_qishu:
		case R.id.qlc_fushi_seek_qishu:
		case R.id.qlc_dantuo_seek_qishu:
			iProgressQishu = iProgress;
			mTextQishu.setText("" + iProgressQishu);
			break;
		/*
		 * case R.id.seek_beishu_fushi: iProgressBeishu=iProgress;
		 * mTextBeishu.setText(""+iProgressBeishu); changeTextSumMoney(); break;
		 * 
		 * case R.id.seek_qishu_fushi: iProgressQishu=iProgress;
		 * mTextQishu.setText(""+iProgressQishu); break; case
		 * R.id.seek_beishu_dantuo: iProgressBeishu=iProgress;
		 * mTextBeishu.setText(""+iProgressBeishu); changeTextSumMoney(); break;
		 * case R.id.seek_qishu_dantuo: iProgressQishu=iProgress;
		 * mTextQishu.setText(""+iProgressQishu); break;
		 */
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
		case PublicConst.BUY_QLC_FUSHI:
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
		case PublicConst.BUY_QLC_FUSHI:
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

	private boolean getCheckBox() {
		return mCheckBox.isChecked();
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
		case PublicConst.BUY_QLC_FUSHI: {
			int iRedHighlights = redBallTable.getHighlightBallNums();
			// int iBlueHighlights = blueBallTable.getHighlightBallNums();

			// ������ ����//fqc edit ���������������ʱ����ʾ��Ӧ����ʾ
			if (iRedHighlights < 8) {
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_red_number));
			}
			// �������ﵽ���Ҫ��
			if (iRedHighlights >= 8) {
				// if(iBlueHighlights<1){
				// mTextSumMoney.setText(getResources().getString(R.string.please_choose_blue_number));
				// }
				// else if(iBlueHighlights==1){
				// int iZhuShu=getZhuShu();
				// String iTempString="��ǰΪ��ʽ����"+iZhuShu+"ע����"+(iZhuShu*2)+"Ԫ";
				// mTextSumMoney.setText(iTempString);
				// }
				// else{
				int iZhuShu = getZhuShu();
				String iTempString = "��ǰΪ��ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ";
				mTextSumMoney.setText(iTempString);
				// }
			}
			// ����ʽ
			// else{
			// if(iBlueHighlights<1){
			// mTextSumMoney.setText(getResources().getString(R.string.please_choose_blue_number));
			// }
			// else if(iBlueHighlights==1){
			// int iZhuShu=getZhuShu();
			// String iTempString="��ǰΪ�츴ʽ����"+iZhuShu+"ע����"+(iZhuShu*2)+"Ԫ";
			// mTextSumMoney.setText(iTempString);
			// }
			// else{
			// int iZhuShu=getZhuShu();
			// String iTempString="��ǰΪȫ��ʽ����"+iZhuShu+"ע����"+(iZhuShu*2)+"Ԫ";
			// mTextSumMoney.setText(iTempString);
			// }
			// }
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
			// else if(iBlueHighlights<1){
			// mTextSumMoney.setText(getResources().getString(R.string.choose_number_dialog_tip6));
			// }
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
		case PublicConst.BUY_QLC_FUSHI:
			buy_FUSHI(aWhichGroupBall, aBallViewId);
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
			if(redTuoBallTable.getHighlightBallNums()>20)
				iChosenBallSum = 26-redTuoBallTable.getHighlightBallNums();
			// ÿ�ε�����סС���״̬
			int isHighLight = redBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			PublicMethod.myOutput("****isHighLight " + isHighLight
					+ "PublicConst.BALL_TO_HIGHLIGHT "
					+ PublicConst.BALL_TO_HIGHLIGHT);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.DanShi.iRedBallStatus[aBallViewId] = 1;
				mBallHolder.DanShi.iTuoRedBallStatus[aBallViewId] = 0;
				redTuoBallTable.clearOnBallHighlight(aBallViewId);
				PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
						+ aBallViewId);
			} else
				mBallHolder.DanShi.iRedBallStatus[aBallViewId] = 0;
		} else if (aWhichGroupBall == 1) { // ������
			// int iChosenBallSum = 16;
			// int isHighLight = blueBallTable.changeBallState(iChosenBallSum,
			// aBallViewId);
			// if(isHighLight == PublicConst.BALL_TO_HIGHLIGHT){
			// mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 1;
			// PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT" +
			// aBallViewId);
			// }
			// else mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 0;
		} else if (aWhichGroupBall == 2) { // ���� ���� ���29��С��
//			int iChosenBallSum = 29;
			int iChosenBallSum = 25;
			if(redBallTable.getHighlightBallNums()>1)
				iChosenBallSum = 26-redBallTable.getHighlightBallNums();
			int isHighLight = redTuoBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.DanShi.iTuoRedBallStatus[aBallViewId] = 1;
				mBallHolder.DanShi.iRedBallStatus[aBallViewId] = 0;
				redBallTable.clearOnBallHighlight(aBallViewId);
				PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
						+ aBallViewId);
			} else
				mBallHolder.DanShi.iTuoRedBallStatus[aBallViewId] = 0;
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
	private void buy_FUSHI(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 0) { // ������
			// ���ѡȡ16������
			int iChosenBallSum = 16;
			// ÿ�ε�����סС���״̬
			int isHighLight = redBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			PublicMethod.myOutput("****isHighLight " + isHighLight
					+ "PublicConst.BALL_TO_HIGHLIGHT "
					+ PublicConst.BALL_TO_HIGHLIGHT);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.DanShi.iRedBallStatus[aBallViewId] = 1;
				PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
						+ aBallViewId);
			} else
				mBallHolder.DanShi.iRedBallStatus[aBallViewId] = 0;
		} else if (aWhichGroupBall == 1) {
			// int iChosenBallSum = 16;
			// int isHighLight = blueBallTable.changeBallState(iChosenBallSum,
			// aBallViewId);
			// if(isHighLight == PublicConst.BALL_TO_HIGHLIGHT){
			// mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 1;
			// PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT" +
			// aBallViewId);
			// }
			// else mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 0;
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
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.DanShi.iRedBallStatus[aBallViewId] = 1;
				PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
						+ aBallViewId);
			} else
				mBallHolder.DanShi.iRedBallStatus[aBallViewId] = 0;
		} else if (aWhichGroupBall == 1) {
			// int iChosenBallSum = 1;
			// int isHighLight = blueBallTable.changeBallState(iChosenBallSum,
			// aBallViewId);
			// if(isHighLight == PublicConst.BALL_TO_HIGHLIGHT){
			// mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 1;
			// PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT" +
			// aBallViewId);
			// }
			// else mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 0;
		}
	}

	/*
	 * ����BallTable
	 * 
	 * @param LinearLayout aParentView ��һ��Layout
	 * 
	 * @param int aLayoutId ��ǰBallTable��LayoutId
	 * 
	 * @param int aFieldWidth BallTable����Ŀ�ȣ�����Ļ��ȣ�
	 * 
	 * @param int aBallNum С�����
	 * 
	 * @param int aBallViewWidth С����ͼ�Ŀ�ȣ�ͼƬ��ȣ�
	 * 
	 * @param int[] aResId С��ͼƬId
	 * 
	 * @param int aIdStart С��Id��ʼ��ֵ
	 * 
	 * @return BallTable
	 */
	private BallTable makeBallTable(LinearLayout aParentView, int aLayoutId,
			int aFieldWidth, int aBallNum, int aBallViewWidth, int[] aResId,
			int aIdStart) {
		BallTable iBallTable = new BallTable(aParentView, aLayoutId, aIdStart);
		// BallTable iBallTable=new BallTable(aLayoutId,aIdStart);

		int iBallNum = aBallNum;
		int iBallViewWidth = aBallViewWidth;
		int iFieldWidth = aFieldWidth;
		int scrollBarWidth = 6;

		int viewNumPerLine = (iFieldWidth - scrollBarWidth)
				/ (iBallViewWidth + 2);
		int lineNum = iBallNum / viewNumPerLine;
		int lastLineViewNum = iBallNum % viewNumPerLine;
		int margin = (iFieldWidth - scrollBarWidth - (aBallViewWidth + 2)
				* viewNumPerLine) / 2;
		// PublicMethod.myOutput("--------margin:" + margin + " iFieldWidth" +
		// iFieldWidth +" iBallViewWidth:" + iBallViewWidth +" viewNumPerLine:"
		// + viewNumPerLine);
		int iBallViewNo = 0;
		for (int row = 0; row < lineNum; row++) {
			TableRow tableRow = new TableRow(aParentView.getContext());
			for (int col = 0; col < viewNumPerLine; col++) {
				String iStrTemp = "" + (iBallViewNo + 1);
				OneBallView tempBallView = new OneBallView(aParentView
						.getContext());
				tempBallView.setId(aIdStart + iBallViewNo);
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,
						aResId);
				tempBallView.setOnClickListener(this);

				iBallTable.addBallView(tempBallView);

				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					lp.setMargins(margin + 1, 1, 1, 1);
				} else if (col == viewNumPerLine - 1) {
					lp.setMargins(1, 1, margin + scrollBarWidth + 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				iBallViewNo++;
			}
			// �½���TableRow��ӵ�TableLayout
			iBallTable.tableLayout
					.addView(tableRow, new TableLayout.LayoutParams(
							PublicConst.FP, PublicConst.WC));
		}
		if (lastLineViewNum > 0) {
			TableRow tableRow = new TableRow(this);
			for (int col = 0; col < lastLineViewNum; col++) {
				// PublicMethod.myOutput("-----------"+iBallViewNo);
				String iStrTemp = "" + (iBallViewNo + 1);
				OneBallView tempBallView = new OneBallView(aParentView
						.getContext());
				tempBallView.setId(aIdStart + iBallViewNo);
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,
						aResId);
				tempBallView.setOnClickListener(this);
				iBallTable.addBallView(tempBallView);
				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					lp.setMargins(margin + 1, 1, 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				iBallViewNo++;
			}
			// �½���TableRow��ӵ�TableLayout
			iBallTable.tableLayout
					.addView(tableRow, new TableLayout.LayoutParams(
							PublicConst.FP, PublicConst.WC));
		}
		// PublicMethod.myOutput("-----w:"+iBallTable.tableLayout.getWidth()+"   h:"+iBallTable.tableLayout.getHeight());
		return iBallTable;
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
		mBallHolder = ballBetPublicClass.new BallHolder();
		// ��ʽ ����2������
		if (aNums.length == 2) {
			iFushiRedBallNumber = aNums[0];
			iFushiBlueBallNumber = aNums[1];
			// redBallTable.randomChoose(iFushiRedBallNumber);
			// blueBallTable.randomChoose(iFushiBlueBallNumber);
			mBallHolder = redBallTable.randomChooseConfigChange(
					iFushiRedBallNumber, mBallHolder, 0);
			changeTextSumMoney();
		} else if (aNums.length == 3) {
			iDantuoRedDanNumber = aNums[0];
			iDantuoRedTuoNumber = aNums[1];
			iDantuoBlueNumber = aNums[2];
			// ��ѡ��ΧΪ29 wyl 20100714
			int[] iTotalRandoms = PublicMethod.getRandomsWithoutCollision(
					aNums[0] + aNums[1], 0, 29);

			// redBallTable.randomChoose(iDantuoRedDanNumber);
			// redTuoBallTable.randomChoose(iDantuoRedTuoNumber);
			redBallTable.clearAllHighlights();
			redTuoBallTable.clearAllHighlights();
			int i;
			for (i = 0; i < iDantuoRedDanNumber; i++) {
				// redBallTable.changeBallState(6, iTotalRandoms[i]);
				int isHighLight = redBallTable.changeBallState(6,
						iTotalRandoms[i]);
				PublicMethod.myOutput("****isHighLight " + isHighLight
						+ "PublicConst.BALL_TO_HIGHLIGHT "
						+ PublicConst.BALL_TO_HIGHLIGHT);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.DanShi.iRedBallStatus[iTotalRandoms[i]] = 1;
					PublicMethod
							.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
									+ iTotalRandoms[i]);
				} else
					mBallHolder.DanShi.iRedBallStatus[iTotalRandoms[i]] = 0;
			}
			for (i = iDantuoRedDanNumber; i < iDantuoRedDanNumber
					+ iDantuoRedTuoNumber; i++) {
				// redTuoBallTable.changeBallState(20, iTotalRandoms[i]);
				int isHighLight = redTuoBallTable.changeBallState(29,
						iTotalRandoms[i]);
				PublicMethod.myOutput("****isHighLight " + isHighLight
						+ "PublicConst.BALL_TO_HIGHLIGHT "
						+ PublicConst.BALL_TO_HIGHLIGHT);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.DanShi.iTuoRedBallStatus[iTotalRandoms[i]] = 1;
					PublicMethod
							.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
									+ iTotalRandoms[i]);
				} else
					mBallHolder.DanShi.iTuoRedBallStatus[iTotalRandoms[i]] = 0;
			}
			// blueBallTable.randomChoose(iDantuoBlueNumber);
			changeTextSumMoney();
		}
		/*
		 * switch(iCurrentButton){ case PublicConst.BUY_QLC_DANSHI: break; case
		 * PublicConst.BUY_QLC_FUSHI: iFushiRedBallNumber = aRedNum;
		 * iFushiBlueBallNumber = aBlueNum; redBallTable.randomChoose(aRedNum);
		 * blueBallTable.randomChoose(aBlueNum); changeTextSumMoney(); break;
		 * case PublicConst.BUY_QLC_DANTUO: break; default: break; }
		 */
	}

	// ��ʼͶע ��Ӧ���Ͷע��ť �� menu�����Ͷעѡ��
	private void beginTouZhu() {
		// ������ 7.4 �����޸ģ���ӵ�½���ж�
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(QLC.this,
				"addInfo");
		String sessionIdStr = pre.getUserLoginInfo("sessionid");
		if (iCurrentButton == PublicConst.BUY_QLC_DANSHI) {
			int iZhuShu = getZhuShu();
			// ��ȡ���� �³� 20100711
			int iQiShu = getQiShu();
			if (sessionIdStr.equals("")) {
				Intent intentSession = new Intent(QLC.this, UserLogin.class);
				startActivity(intentSession);
			} else if (redBallTable.getHighlightBallNums() < 7) {
				alert1("��ѡ��7������");
			} else if (iZhuShu * 2 > 100000) {
				DialogExcessive();
			} else {
				// String iTempString="��"+iZhuShu+"ע����"+(iZhuShu*2)+"Ԫ";
				// alert("��ѡ�����"+iZhuShu+"ע���ֲʵ�ʽ��Ʊ��"+"����"+(iZhuShu*2)+"Ԫ");
				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				alert(sTouzhuAlert);
			}
		} else if (iCurrentButton == PublicConst.BUY_QLC_FUSHI) {
			int iZhuShu = getZhuShu();
			int iQiShu = getQiShu();
			if (sessionIdStr.equals("")) {
				Intent intentSession = new Intent(QLC.this, UserLogin.class);
				startActivity(intentSession);
			} else if (redBallTable.getHighlightBallNums() < 8
					|| redBallTable.getHighlightBallNums() > 16) {
				alert1("������ѡ��8~16������");
			} else if (iZhuShu * 2 > 100000) {
				DialogExcessive();
			} else {
				// alert("��ѡ�����"+iZhuShu+"ע���ֲʸ�ʽ��Ʊ��"+"����"+(iZhuShu*2)+"Ԫ");
				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				alert(sTouzhuAlert);
			}
		} else if (iCurrentButton == PublicConst.BUY_QLC_DANTUO) {
			int iZhuShu = getZhuShu();
			int iQiShu = getQiShu();
			int redballno = redBallTable.getHighlightBallNums();
			int redtuoballno = redTuoBallTable.getHighlightBallNums();
			int redNumber = redBallTable.getHighlightBallNums();
			int redTuoNumber = redTuoBallTable.getHighlightBallNums();
			// int blueNumber = blueBallTable.getHighlightBallNums();
			if (sessionIdStr.equals("")) {
				Intent intentSession = new Intent(QLC.this, UserLogin.class);
				startActivity(intentSession);
			} else if ((redNumber < 1 || redNumber > 6)
					&& (redTuoNumber < 1 || redTuoNumber > 29)) {
				alert1("��ѡ��1~6�������룬1~29���������룡");
			} else if (redNumber + redTuoNumber < 8) {
				alert1("������ͺ�������֮������Ϊ8����");
			} else if (iZhuShu <= 0) {
				alert1("���������֮������Ϊ8����");
			} else if (iZhuShu * 2 > 100000) {
				DialogExcessive();
			} else {
				// ��ʾ����������� �³� 2010/7/11
				// String iTempString="��"+iZhuShu+"ע����"+(iZhuShu*2)+"Ԫ";
				// alert_dantuo("��ѡ�����"+iZhuShu+"ע���ֲʵ��ϲ�Ʊ��"+"����"+(iZhuShu*2)+"Ԫ");
				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				// Ӧ�õ��õ��ϵķ��� �³� 20100713
				alert_dantuo(sTouzhuAlert);
			}
		}
	}

	// ���menu�����»�ѡ �������»�ѡ�ķ���
	private void beginReselect() {

		if (iCurrentButton == PublicConst.BUY_QLC_DANSHI) {
			redBallTable.clearAllHighlights();
		} else if (iCurrentButton == PublicConst.BUY_QLC_FUSHI) {
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

								showDialog(DIALOG1_KEY);
								// TODO Auto-generated method stub
//								�����Ƿ�ı�������ж� �³� 8.11
								iHttp.whetherChange=false;
								Thread t = new Thread(new Runnable() {
									int iZhuShu = getZhuShu();
									int iQiShu = getQiShu();
									String str;

									@Override
									public void run() {
										// TODO Auto-generated method stub
										int iRedHighlights = redBallTable
												.getHighlightBallNums();
										// int iBlueHighlights =
										// blueBallTable.getHighlightBallNums();
										// int
										// iTuoRedHighlights=redTuoBallTable.getHighlightBallNums();
										// �ж������ֲʵ�ʽ���Ǹ�ʽ
										if (iRedHighlights == 7) {
											String zhuma = zhuma_danshi();
											// PublicMethod.myOutput("----------------zhuma"+zhuma);
											// str=pay(zhuma, iZhuShu+"");
											str = pay(zhuma, iQiShu + "",
													iZhuShu * iQiShu * 200 + "");
											// FileIO file=new FileIO();
											// file.infoToWrite="----------------qilecaidanshitouzhu"+str;
											// file.write();
										} else if (iRedHighlights > 7) {
											String zhuma_fushi = zhuma_fushi();
											// str=pay(zhuma_fushi,iZhuShu+"");
											str = pay(zhuma_fushi, iQiShu + "",
													iZhuShu * iQiShu * 200 + "");
										}
										// else if(iTuoRedHighlights!=0)
										// else{
										// String zhuma_dantuo=zhuma_dantuo();
										// str=pay(zhuma_dantuo,iZhuShu+"");
										// }
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

		Builder dialog = new AlertDialog.Builder(this).setMessage(string)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						showDialog(DIALOG1_KEY);
						// TODO Auto-generated method stub
//						�����Ƿ�ı�������ж� �³� 8.11
						iHttp.whetherChange=false;
						Thread t = new Thread(new Runnable() {
							int iZhuShu = getZhuShu();
							int iQiShu = getQiShu();
							String str;

							@Override
							public void run() {
								// TODO Auto-generated method stub
								String zhuma = zhuma_dantuo();
								// PublicMethod.myOutput("----------------zhuma"+zhuma);
								// str=pay(zhuma, iZhuShu+"");
								// �½ӿ� 2010/7/11 �³�
								str = pay(zhuma, iQiShu + "", iZhuShu * iQiShu
										* 200 + "");
								// FileIO file=new FileIO();
								// file.infoToWrite="----------------qilecaidanshitouzhu"+str;
								// file.write();
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
		BettingInterface betting = new BettingInterface();

		// String error_code=betting.Betting("15931199023", bets, bets_zhu_num,
		// "9E6764DA32B1BAD4ABBC41616992F82F");
		// String error_code=betting.Betting("13121795856", bets, bets_zhu_num,
		// "C8FAC57FDB8C68BE453B7D838F2E1D23");
		// String error_code=betting.Betting("13121795856", bets, bets_zhu_num,
		// "57637B1A23BFCB80A1096A5320F44425");
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,
				"addInfo");
		String sessionid = shellRW.getUserLoginInfo("sessionid");
		String phonenum = shellRW.getUserLoginInfo("phonenum");
		PublicMethod.myOutput("-------------touzhusessionid" + sessionid);
		PublicMethod.myOutput("-------------phonenum" + phonenum);

		// String error_code=betting.Betting(phonenum, bets,
		// bets_zhu_num,sessionid);
		String error_code = betting.BettingNew(bets, count, amount, sessionid);
		// FileIO file=new FileIO();
		// file.infoToWrite="----------------qilecaitouzhu"+error_code;
		// file.write();
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
		zhuma = redBallTable.getHighlightBallNOs();
		String t_str = "1512-F47102-";
		t_str += "00-";
		if (beishu < 10) {
			t_str += beishu;
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
		for (int i = 0; i < zhuma.length; i++) {
			if (zhuma[i] >= 10) {
				t_str += zhuma[i];
			} else if (zhuma[i] < 10) {
				t_str += "0" + zhuma[i];
			}
		}
		t_str += "^";
		PublicMethod.myOutput("-------------qilecai" + t_str);
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
		} else if (iCurrentButton == PublicConst.BUY_QLC_FUSHI)
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
				red_zhuma_string = red_zhuma_string + ".";
		}

		if (iCurrentButton == PublicConst.BUY_QLC_DANSHI
				|| iCurrentButton == PublicConst.BUY_QLC_FUSHI) {

			return "ע�룺" + red_zhuma_string + "\n" + "ע����" + iZhuShu
					/ mSeekBarBeishu.getProgress() + "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "ע" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" 
					+"�����"
					+(2* (mSeekBarQishu.getProgress()-1)*iZhuShu)+"Ԫ"+"\n"+ "ȷ��֧����";

		} else {
			String red_tuo_zhuma_string = " ";
			int[] redTuoZhuMa = redTuoBallTable.getHighlightBallNOs();
			for (int i = 0; i < redTuoBallTable.getHighlightBallNOs().length; i++) {
				red_tuo_zhuma_string = red_tuo_zhuma_string
						+ String.valueOf(redTuoZhuMa[i]);
				if (i != redTuoBallTable.getHighlightBallNOs().length - 1)
					red_tuo_zhuma_string = red_tuo_zhuma_string + ".";
			}
			return "ע�룺\n" + "   �����룺" + red_zhuma_string + "\n" + "   �������룺"
					+ red_tuo_zhuma_string + "\n" + "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress() + "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "ע" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * 2) + "Ԫ" + "\n" 
					+"�����"
					+(2* (mSeekBarQishu.getProgress()-1)*iZhuShu)+"Ԫ"+"\n"+ "ȷ��֧����";
		}
	}

	/**
	 * ����Ͷע����10��Ԫʱ�ĶԻ���
	 */
	private void DialogExcessive() {
		AlertDialog.Builder builder = new AlertDialog.Builder(QLC.this);
		builder.setTitle("Ͷעʧ��");
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
}