package com.palmdream.RuyicaiAndroid;

/**
 * @Title ����͸�淨 Activity��
 * @Description: 
 * @Copyright: Copyright (c) 2009
 * @Company: PalmDream
 * @author FanYaJun
 * @version 1.0 20100618
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.palmdream.RuyicaiAndroid.BallBetPublicClass.BallHolder;
import com.palmdream.netintface.BettingInterface;
import com.palmdream.netintface.iHttp;
import com.palmdream.netintface.jrtLot;

public class DLT extends Activity // �¼� �ص�����
		implements OnClickListener, // С�򱻵�� onClick
		SeekBar.OnSeekBarChangeListener, // SeekBar�ı� onProgressChanged
		RadioGroup.OnCheckedChangeListener, // ��ѡ��ѡ��仯 onCheckedChangeListener
		MyDialogListener { // �Ի���������ֵ onOKClick

	// С��ͼ�Ŀ��
	public static final int BALL_WIDTH = 32;
	// ������ǩ����
	private int iButtonNum = 2;

	// ����button��ǰ��λ��
	private int iCurrentButton; // 0x55660001 --- DLT danshi
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

	int topButtonStringId[] = { R.string.dantuo, R.string.twoin12 };
	int topButtonIdOn[] = { R.drawable.dantuo_b, R.drawable.xuan2_b };
	int topButtonIdOff[] = { R.drawable.dantuo, R.drawable.xuan2 };

	// С����ʼID
	public static final int RED_BALL_START = 0x80000001;
	public static final int RED_TUO_BALL_START = 0x82000001;
	public static final int BLUE_BALL_START = 0x81000001;
	public static final int BLUE_TUO_BALL_START = 0x83000001;

	private static final int DIALOG1_KEY = 0;// ��������ֵ2010/7/4
	ProgressDialog progressdialog;

	ScrollView mHScrollView;

	SeekBar mSeekBarBeishu;
	SeekBar mSeekBarQishu;

	TextView mTextBeishu;
	TextView mTextQishu;

	TextView mTextSumMoney;
	private int mTimesMoney = 2;

	ImageButton dlt_b_touzhu_danshi;
	ImageButton dlt_b_touzhu_fushi;
	ImageButton dlt_b_touzhu_dantuo;
	ImageButton dlt_b_touzhu_twoin12;
	Button dlt_btn_newSelect;
	CheckBox mCheckBox;
	CheckBox mZhuijia;
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

	BallTable blueTuoBallTable = null;
	private int blueTuoBallResId[] = { R.drawable.grey, R.drawable.blue };

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
	private int iDantuoBlueTuoNumber;
	// 12ѡ2С���������ѡ��
	private int iTwoin12BlueBallNumber;

	// ע��
	public static int zhuma[] = null;
	// �����㱶���ĵ�ǰע�� ���ڹ�������
	private long iSendZhushu = 0;
	// ʵ�ֺ��ݵ��л�
	private BallHolder mBallHolder = null;
	private BallHolder tempBallHolder = null;
	private BallBetPublicClass ballBetPublicClass = new BallBetPublicClass();
	public int publicTopButton;
	public int type = 0;
	public static final int ZHIXUAN = 0;
	public static final int DANTUO = 1;
	/** Called when the activity is first created. */
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
				Toast.makeText(getBaseContext(), "Ͷעʧ�����㣡", Toast.LENGTH_LONG)
						.show();
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
				Toast
						.makeText(getBaseContext(), "Ͷע�ɹ�����Ʊ�ɹ���",
								Toast.LENGTH_LONG).show();
				if (iCurrentButton == PublicConst.BUY_DLT_DANSHI
						|| iCurrentButton == PublicConst.BUY_DLT_FUSHI) {
					redBallTable.clearAllHighlights();
					blueBallTable.clearAllHighlights();
				} else if (iCurrentButton == PublicConst.BUY_DLT_DANTUO) {
					redBallTable.clearAllHighlights();
					blueBallTable.clearAllHighlights();
					redTuoBallTable.clearAllHighlights();
					blueTuoBallTable.clearAllHighlights();
				} else if (iCurrentButton == PublicConst.BUY_DLT_TWOIN12) {
					blueBallTable.clearAllHighlights();
				}
				break;
			// //��Ҫ���AlertDialog��ʾ�úű���ͣ����ϵ�ͷ�
			// case 5:
			// //��Ҫ���AlertDialogע��ʧ��
			// break;
			case 6:
				// //��Ҫ���AlertDialog��ʾ�û���¼�ɹ�
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷע������", Toast.LENGTH_LONG)
						.show();
				// Ͷע�ɹ������С�� �³� 20100728
				if (iCurrentButton == PublicConst.BUY_DLT_DANSHI
						|| iCurrentButton == PublicConst.BUY_DLT_FUSHI) {
					redBallTable.clearAllHighlights();
					blueBallTable.clearAllHighlights();
				} else if (iCurrentButton == PublicConst.BUY_DLT_DANTUO) {
					redBallTable.clearAllHighlights();
					blueBallTable.clearAllHighlights();
					redTuoBallTable.clearAllHighlights();
					blueTuoBallTable.clearAllHighlights();
				} else if (iCurrentButton == PublicConst.BUY_DLT_TWOIN12) {
					blueBallTable.clearAllHighlights();
				}
				break;
			case 7:
				progressdialog.dismiss();

				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(DLT.this, UserLogin.class);
				startActivity(intentSession);
				// Toast.makeText(getBaseContext(), "���¼",
				// Toast.LENGTH_LONG).show();
				// alert1("���¼");
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
				// // tv.setText(result);
				break;
			case 10:
				// ��ͼƬ�ı������û�ԭ����ͼ����ʾ�ɵ��
				if (iCurrentButton == PublicConst.BUY_DLT_DANSHI) {
					dlt_b_touzhu_danshi
							.setImageResource(R.drawable.imageselecter);
				} else if (iCurrentButton == PublicConst.BUY_DLT_FUSHI) {
					dlt_b_touzhu_fushi
							.setImageResource(R.drawable.imageselecter);
				} else if (iCurrentButton == PublicConst.BUY_DLT_DANTUO) {
					dlt_b_touzhu_dantuo
							.setImageResource(R.drawable.imageselecter);
				}
				break;
			}
			//				
		}
	};

	/*
	 * ��ں��� @param savedInstanceState @return void
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

				if (type == ZHIXUAN) {
					finish();
				} else if (type == DANTUO) {
					type = ZHIXUAN;
					// topBar.setVisibility(View.VISIBLE);
					topButtonGroup.setVisibility(View.VISIBLE);
					// ----- ��ʼ��������ť
					initButtons();
				}
			}

		});
		TextView title = (TextView) findViewById(R.id.goucaitouzhu_title);
		title.setText(getResources().getString(R.string.daletou));

		mHScrollView = (ScrollView) findViewById(R.id.scroll_global);
		// ��ȡ������id
		buyView = (LinearLayout) findViewById(R.id.layout_buy);
		// ----- ��ʼ��������ť
		initButtons();

		// ----- Ĭ�� ���ص�ʽlayout
		// iCurrentButton = PublicConst.BUY_DLT_DANSHI;
		// createBuyView(iCurrentButton);
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
		if (publicTopButton == PublicConst.BUY_DLT_FUSHI) {
			create_DLT_FUSHI();
			changeTextSumMoney();
		} else if (publicTopButton == PublicConst.BUY_DLT_DANTUO) {
			create_DLT_DANTUO();
			changeTextSumMoney();
		}
		mBallHolder.flag = 0;
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		// TODO Auto-generated method stub
		// for(int i=0;i<35;i++){
		// PublicMethod.myOutput("******mBallHolder[i]" +" "+i+" " +
		// mBallHolder.DanShi.iRedBallStatus[i]);
		// }
		mBallHolder.topButtonGroup = iCurrentButton;
		return mBallHolder;
		// return super.onRetainNonConfigurationInstance();
	}

	/*
	 * ��������View�����ն�����ť��״̬���� @param int aWhichBuy ��ӦView��Id @return void
	 */
	private void createBuyView(int aWhichBuy) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
		mTimesMoney = 2;
		publicTopButton = aWhichBuy;
		switch (aWhichBuy) {

		case PublicConst.BUY_DLT_FUSHI: // ��ʽ
			create_DLT_FUSHI();
			break;
		case PublicConst.BUY_DLT_DANTUO: // ����
			create_DLT_DANTUO();
			break;
		case PublicConst.BUY_DLT_TWOIN12: // ����
			create_DLT_TWOIN12();
			break;
		default:
			break;
		}

	}

	/*
	 * �������Ϲ���View @return void
	 */
	private void create_DLT_TWOIN12() {
		// topBar.setVisibility(View.GONE);
		topButtonGroup.setVisibility(View.GONE);
		type = DANTUO;
		buyView.removeAllViews();
		// View iV = View.inflate(this, R.layout.layout_dlt_twoin12, null);
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_dlt_twoin12, null);
		{
			int redBallViewWidth = DLT.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			int blueBallViewNum = 12;
			blueBallTable = makeBallTable(iV, R.id.table_blue, iScreenWidth,
					blueBallViewNum, redBallViewWidth, blueBallResId,
					BLUE_BALL_START);

			mTextSumMoney = (TextView) iV
					.findViewById(R.id.dlt_twoin12_text_sum_money);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));
			mSeekBarBeishu = (SeekBar) iV
					.findViewById(R.id.dlt_twoin12_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) iV
					.findViewById(R.id.dlt_twoin12_seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) iV
					.findViewById(R.id.dlt_twoin12_text_beishu);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) iV
					.findViewById(R.id.dlt_twoin12_text_qishu);
			mTextQishu.setText("" + iProgressQishu);
			/*
			 * ����Ӽ�ͼ�꣬��seekbar�������ã� @param int idFind, ͼ���id View iV, ��ǰ��view
			 * final int isAdd, 1��ʾ�� -1��ʾ�� final SeekBar mSeekBar @return void
			 */
			setSeekWhenAddOrSub(R.id.dlt_twoin12_seekbar_subtract_beishu, iV,
					-1, mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.dlt_twoin12_seekbar_add_beishu, iV, 1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.dlt_twoin12_seekbar_subtract_qihao, iV,
					-1, mSeekBarQishu, false);
			setSeekWhenAddOrSub(R.id.dlt_twoin12_seekbar_add_qihao, iV, 1,
					mSeekBarQishu, false);

			dlt_btn_newSelect = (Button) iV
					.findViewById(R.id.dlt_twoin12_btn_newSelect);
			dlt_btn_newSelect.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					mBallHolder = ballBetPublicClass.new BallHolder();
					ChooseNumberDialogDLT iChooseNumberDialog = new ChooseNumberDialogDLT(
							DLT.this, 2, DLT.this);
					iChooseNumberDialog.show();
				}

			});
			dlt_b_touzhu_twoin12 = (ImageButton) iV
					.findViewById(R.id.dlt_twoin12_b_touzhu);
			dlt_b_touzhu_twoin12.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					beginTouZhu(); // 1��ʾ��ǰΪ��ʽ
				}
			});
		}
		// ����Ļ�л���ʱ���ͼ�Ժ󣬰��Ѿ�������С����ʾ����
		if (mBallHolder.flag == 1) {
			redBallTable
					.changeBallStateConfigChange(mBallHolder.DanShi.iRedBallStatus);
			blueBallTable
					.changeBallStateConfigChange(mBallHolder.DanShi.iBlueBallStatus);
			mSeekBarBeishu.setProgress(mBallHolder.DanShi.iBeiShu);
			mSeekBarQishu.setProgress(mBallHolder.DanShi.iQiShu);
			// iCB_Danshi.setChecked(mBallHolder.DanShi.bCheckBox);

		}
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));
		mTimesMoney = 2;
		// mZhuijia = (CheckBox)
		// this.findViewById(R.id.dlt_twoin12_zhuijia_touzhu_cb);
		// mZhuijia.setOnCheckedChangeListener(new
		// CompoundButton.OnCheckedChangeListener(){
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// if(isChecked){
		// mTimesMoney = 3;
		// }else mTimesMoney = 2;
		// changeTextSumMoney();
		// }});
		mCheckBox = (CheckBox) this.findViewById(R.id.dlt_twoin12_jixuan_cb);
		if (mBallHolder.flag == 1) {
			mCheckBox.setChecked(mBallHolder.DanShi.bCheckBox);
			if (mBallHolder.DanShi.bCheckBox)
				dlt_btn_newSelect.setVisibility(View.VISIBLE);
			else
				dlt_btn_newSelect.setVisibility(View.INVISIBLE);
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
								// redBallTable.randomChoose(4);
								// blueBallTable.randomChoose(1);
								// mBallHolder = redBallTable
								// .randomChooseConfigChange(4,
								// mBallHolder, 0);
								ChooseNumberDialogDLT iChooseNumberDialog = new ChooseNumberDialogDLT(
										DLT.this, 2, DLT.this);
								iChooseNumberDialog.show();
								dlt_btn_newSelect.setVisibility(View.VISIBLE);
								// redBallTable.randomChoose("red");
								// blueBallTable.randomChoose("blue");
								changeTextSumMoney();
							}
						} else {
							dlt_btn_newSelect.setVisibility(View.INVISIBLE);
						}
					}
				});

	}

	/*
	 * �������Ϲ���View @return void
	 */
	private void create_DLT_DANTUO() {
		// topBar.setVisibility(View.GONE);
		topButtonGroup.setVisibility(View.GONE);
		type = DANTUO;
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_dlt_dantuo, null);
		{
			int redBallViewNum = 35;
			int redBallViewWidth = DLT.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			redBallTable = makeBallTable(iV, R.id.table_front_danma,
					iScreenWidth, redBallViewNum, redBallViewWidth,
					redBallResId, RED_BALL_START);
			redTuoBallTable = makeBallTable(iV, R.id.table_front_tuoma,
					iScreenWidth, redBallViewNum, redBallViewWidth,
					redBallResId, RED_TUO_BALL_START);

			int blueBallViewNum = 12;
			blueBallTable = makeBallTable(iV, R.id.table_rear_danma,
					iScreenWidth, blueBallViewNum, redBallViewWidth,
					blueBallResId, BLUE_BALL_START);

			blueTuoBallTable = makeBallTable(iV, R.id.table_rear_tuoma,
					iScreenWidth, blueBallViewNum, redBallViewWidth,
					blueTuoBallResId, BLUE_TUO_BALL_START);
			mTextSumMoney = (TextView) iV
					.findViewById(R.id.dlt_dantuo_text_sum_money);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));

			mSeekBarBeishu = (SeekBar) iV
					.findViewById(R.id.dlt_dantuo_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) iV
					.findViewById(R.id.dlt_dantuo_seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) iV
					.findViewById(R.id.dlt_dantuo_text_beishu_change);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) iV
					.findViewById(R.id.dlt_dantuo_text_qishu_change);
			mTextQishu.setText("" + iProgressQishu);
			// true - ���� false-����
			setSeekWhenAddOrSub(R.id.dlt_dantuo_seekbar_subtract_beishu, iV,
					-1, mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.dlt_dantuo_seekbar_add_beishu, iV, 1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.dlt_dantuo_seekbar_subtract_qihao, iV, -1,
					mSeekBarQishu, false);
			setSeekWhenAddOrSub(R.id.dlt_dantuo_seekbar_add_qihao, iV, 1,
					mSeekBarQishu, false);

			dlt_btn_newSelect = (Button) iV
					.findViewById(R.id.dlt_dantuo_btn_newSelect);
			dlt_btn_newSelect.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					mBallHolder = ballBetPublicClass.new BallHolder();
					ChooseNumberDialogDLT iChooseNumberDialog = new ChooseNumberDialogDLT(
							DLT.this, 1, DLT.this);
					iChooseNumberDialog.show();
				}

			});

			dlt_b_touzhu_dantuo = (ImageButton) iV
					.findViewById(R.id.dlt_dantuo_b_touzhu);
			dlt_b_touzhu_dantuo.setOnClickListener(new OnClickListener() {
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
			blueBallTable
					.changeBallStateConfigChange(mBallHolder.DanShi.iBlueBallStatus);
			blueTuoBallTable
					.changeBallStateConfigChange(mBallHolder.DanShi.iTuoBlueBallStatus);
			mSeekBarBeishu.setProgress(mBallHolder.DanShi.iBeiShu);
			mSeekBarQishu.setProgress(mBallHolder.DanShi.iQiShu);
		}
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));
		mZhuijia = (CheckBox) this
				.findViewById(R.id.dlt_dantuo_zhuijia_touzhu_cb);
		mZhuijia
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							mTimesMoney = 3;
						} else
							mTimesMoney = 2;
						changeTextSumMoney();
					}
				});
		mCheckBox = (CheckBox) this.findViewById(R.id.dlt_dantuo_cb_jixuan);
		if (mBallHolder.flag == 1) {
			mCheckBox.setChecked(mBallHolder.DanShi.bCheckBox);
			if (mBallHolder.DanShi.bCheckBox)
				dlt_btn_newSelect.setVisibility(View.VISIBLE);
			else
				dlt_btn_newSelect.setVisibility(View.INVISIBLE);
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
								ChooseNumberDialogDLT iChooseNumberDialog = new ChooseNumberDialogDLT(
										DLT.this, 1, DLT.this);
								iChooseNumberDialog.show();
								dlt_btn_newSelect.setVisibility(View.VISIBLE);
							} else {
								dlt_btn_newSelect.setVisibility(View.INVISIBLE);
							}
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
		 * boolean isChecked) { // TODO Auto-generated method stub }
		 * 
		 * });
		 */
	}

	/*
	 * ������ʽ����View @return void
	 */
	private void create_DLT_FUSHI() {
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_dlt_fushi, null);
		{
			int redBallViewNum = 35;
			int redBallViewWidth = DLT.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			redBallTable = makeBallTable(iV, R.id.table_red_fushi,
					iScreenWidth, redBallViewNum, redBallViewWidth,
					redBallResId, RED_BALL_START);

			int blueBallViewNum = 12;
			blueBallTable = makeBallTable(iV, R.id.table_blue_fushi,
					iScreenWidth, blueBallViewNum, redBallViewWidth,
					blueBallResId, BLUE_BALL_START);

			mTextSumMoney = (TextView) iV
					.findViewById(R.id.dlt_fushi_text_sum_money);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));
			mSeekBarBeishu = (SeekBar) iV
					.findViewById(R.id.dlt_fushi_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) iV
					.findViewById(R.id.dlt_fushi_seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) iV
					.findViewById(R.id.dlt_fushi_text_beishu);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) iV.findViewById(R.id.dlt_fushi_text_qishu);
			mTextQishu.setText("" + iProgressQishu);

			/*
			 * ����Ӽ�ͼ�꣬��seekbar�������ã� @param int idFind, ͼ���id View iV, ��ǰ��view
			 * final int isAdd, 1��ʾ�� -1��ʾ�� final SeekBar mSeekBar @return void
			 */
			setSeekWhenAddOrSub(R.id.dlt_fushi_seekbar_subtract_beishu, iV, -1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.dlt_fushi_seekbar_add_beishu, iV, 1,
					mSeekBarBeishu, true);
			setSeekWhenAddOrSub(R.id.dlt_fushi_seekbar_subtract_qihao, iV, -1,
					mSeekBarQishu, false);
			setSeekWhenAddOrSub(R.id.dlt_fushi_seekbar_add_qishu, iV, 1,
					mSeekBarQishu, false);

			dlt_btn_newSelect = (Button) iV
					.findViewById(R.id.dlt_fushi_btn_newSelect);
			dlt_btn_newSelect.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					mBallHolder = ballBetPublicClass.new BallHolder();
					ChooseNumberDialogDLT iChooseNumberDialog = new ChooseNumberDialogDLT(
							DLT.this, 0, DLT.this);
					iChooseNumberDialog.show();
				}

			});
			dlt_b_touzhu_fushi = (ImageButton) iV
					.findViewById(R.id.dlt_fushi_b_touzhu);
			dlt_b_touzhu_fushi.setOnClickListener(new OnClickListener() {
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
			blueBallTable
					.changeBallStateConfigChange(mBallHolder.DanShi.iBlueBallStatus);
			mSeekBarBeishu.setProgress(mBallHolder.DanShi.iBeiShu);
			mSeekBarQishu.setProgress(mBallHolder.DanShi.iQiShu);

		}
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));
		mZhuijia = (CheckBox) this
				.findViewById(R.id.dlt_fushi_zhuijia_touzhu_cb);
		mZhuijia
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							mTimesMoney = 3;
						} else
							mTimesMoney = 2;
						changeTextSumMoney();
					}
				});
		mCheckBox = (CheckBox) this.findViewById(R.id.dlt_fushi_cb_jixuan);
		if (mBallHolder.flag == 1) {
			mCheckBox.setChecked(mBallHolder.DanShi.bCheckBox);
			if (mBallHolder.DanShi.bCheckBox)
				dlt_btn_newSelect.setVisibility(View.VISIBLE);
			else
				dlt_btn_newSelect.setVisibility(View.INVISIBLE);
		}
		mCheckBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							if (mBallHolder.flag != 1) {// �ǵ���¼�������ѡ��checkbox���������������󴥷����¼���
								ChooseNumberDialogDLT iChooseNumberDialog = new ChooseNumberDialogDLT(
										DLT.this, 0, DLT.this);
								iChooseNumberDialog.show();
								dlt_btn_newSelect.setVisibility(View.VISIBLE);

								changeTextSumMoney();
							}
						} else {
							dlt_btn_newSelect.setVisibility(View.INVISIBLE);
						}
					}
				});

	}

	/*
	 * ��ʼ��������button @return void
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
	 * ��Ӷ�����ť������ʾ��ʽ�淨View @return void
	 */
	private void commit() {
		topButtonGroup.removeAllViews();

		int optimum_visible_items_in_portrait_mode = iButtonNum;

		int screen_width = getWindowManager().getDefaultDisplay().getWidth();
		int width;
		width = screen_width / iButtonNum;
		RadioStateDrawable.other_width = width;
		RadioStateDrawable.other_screen_width = screen_width;
		// topButtonLayoutParams = new RadioGroup.LayoutParams(width,
		// RadioGroup.LayoutParams.WRAP_CONTENT);
		topButtonLayoutParams = new RadioGroup.LayoutParams(96,
				RadioGroup.LayoutParams.WRAP_CONTENT);

		for (int i = 0; i < iButtonNum; i++) {
			TabBarButton tabButton = new TabBarButton(this);
	
			tabButton.setState(topButtonIdOn[i], topButtonIdOff[i]);
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
	 * ���ö�����ť��λ�� @param int index ������ť������ @return void
	 */
	public void setCurrentTab(int index) {

		switch (index) {

		case 0: // ��ʽ
			iCurrentButton = PublicConst.BUY_DLT_FUSHI;
			createBuyView(iCurrentButton);
			break;
		case 1: // ����
			iCurrentButton = PublicConst.BUY_DLT_DANTUO;
			createBuyView(iCurrentButton);
			break;
		case 2: // ����
			iCurrentButton = PublicConst.BUY_DLT_TWOIN12;
			createBuyView(iCurrentButton);
			break;
		}
	}

	/*
	 * ������ť�仯�Ļص����� @param RadioGroup group ����������ù��� @param int checkedId
	 * ����button��id @return void
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		mBallHolder = ballBetPublicClass.new BallHolder();

		switch (checkedId) {

		case 0: // ����
			iCurrentButton = PublicConst.BUY_DLT_DANTUO;
			createBuyView(iCurrentButton);
			break;
		case 1: // ����
			iCurrentButton = PublicConst.BUY_DLT_TWOIN12;
			createBuyView(iCurrentButton);
			break;
		}

		mHScrollView.fullScroll(ScrollView.FOCUS_UP);
	}

	/*
	 * ��ȡ��ǰ����button��id @return int id
	 */
	public int getCurrentTab() {
		return topButtonGroup.getCheckedRadioButtonId();
	}

	/*
	 * SeekBar�ı�ʱ�Ļص����� @param SeekBar seekBar �����仯��SeekBarʵ�� @param int progress
	 * �仯���λ��ֵ @param boolean fromUser ����������ù��� @return void
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

		case R.id.dlt_fushi_seek_beishu:
		case R.id.dlt_dantuo_seek_beishu:
		case R.id.dlt_twoin12_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			changeTextSumMoney();
			break;

		case R.id.dlt_fushi_seek_qishu:
		case R.id.dlt_dantuo_seek_qishu:
		case R.id.dlt_twoin12_seek_qishu:
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
	 * ��ӦС�򱻵���Ļص����� @param View v �������view @return void
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
				changeBuyViewByRule(iCurrentButton, 0, iBallViewId);// �������
			}
		} else if (iBallId >= BLUE_BALL_START && iBallId < RED_TUO_BALL_START) {
			int iBallViewId = v.getId() - BLUE_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				PublicMethod.myOutput("----- blue:" + iBallViewId);
				changeBuyViewByRule(iCurrentButton, 1, iBallViewId);// ��������
			}
		} else if (iBallId >= RED_TUO_BALL_START
				&& iBallId < BLUE_TUO_BALL_START) {
			int iBallViewId = v.getId() - RED_TUO_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {
				changeBuyViewByRule(iCurrentButton, 2, iBallViewId);// ��������
			}
		} else {
			int iBallViewId = v.getId() - BLUE_TUO_BALL_START;
			if (iBallViewId < 0) {
				return;
			} else {

				changeBuyViewByRule(iCurrentButton, 3, iBallViewId);// ��������
			}

		}
		// text_sum_money
		changeTextSumMoney();
	}

	/*
	 * ����͸ע���ļ��㷽�� @return int ����ע��
	 */
	private int getZhuShu() {
		int iReturnValue = 0;
		switch (iCurrentButton) {
		case PublicConst.BUY_DLT_DANSHI:
			iReturnValue = mSeekBarBeishu.getProgress();
			iSendZhushu = 1;
			break;
		case PublicConst.BUY_DLT_FUSHI:
			int iRedBalls = redBallTable.getHighlightBallNums();
			int iBlueBalls = blueBallTable.getHighlightBallNums();
			iReturnValue = (int) getDLTFSZhuShu(iRedBalls, iBlueBalls);
			PublicMethod.myOutput("-----***" + iReturnValue + " " + iRedBalls
					+ " " + iBlueBalls);
			break;
		case PublicConst.BUY_DLT_DANTUO:
			int iRedHighlights = redBallTable.getHighlightBallNums();
			int iRedTuoHighlights = redTuoBallTable.getHighlightBallNums();
			int iBlueHighlights = blueBallTable.getHighlightBallNums();
			int iBlueTuoHighlights = blueTuoBallTable.getHighlightBallNums();
			iReturnValue = (int) getDLTDTZhuShu(iRedHighlights,
					iRedTuoHighlights, iBlueHighlights, iBlueTuoHighlights);
			break;
		case PublicConst.BUY_DLT_TWOIN12:

			int iBlueBalls12 = blueBallTable.getHighlightBallNums();
			iReturnValue = (int) getDLT12ZhuShu(iBlueBalls12);
			PublicMethod.myOutput("-----***" + iReturnValue + " "
					+ iBlueBalls12);
			break;
		default:
			break;
		}
		return iReturnValue;
	}


	private long getDLT12ZhuShu(int aBlueBalls) {
		long dltZhuShu = 0L;
		iSendZhushu = 0L;
		if (aBlueBalls >= 2) {
			dltZhuShu += (PublicMethod.zuhe(2, aBlueBalls) * iProgressBeishu);
			iSendZhushu = PublicMethod.zuhe(2, aBlueBalls);
		}
		return dltZhuShu;
	}

	/*
	 * �����淨ע�����㷽�� @param int aRedBalls ������� @param int aRedTuoBalls �����������
	 * 
	 * @param int aBlueBalls ������� @return long ע��
	 */
	private long getDLTDTZhuShu(int aRedBalls, int aRedTuoBalls,
			int aBlueBalls, int aBlueTuoBalls) {// �õ�����͸���ϵ�ע��
		long dltZhuShu = 0L;
		iSendZhushu = 0L;
		dltZhuShu += (PublicMethod.zuhe(5 - aRedBalls, aRedTuoBalls)
				* PublicMethod.zuhe(2 - aBlueBalls, aBlueTuoBalls) * iProgressBeishu);
		iSendZhushu = (PublicMethod.zuhe(5 - aRedBalls, aRedTuoBalls) * PublicMethod
				.zuhe(2 - aBlueBalls, aBlueTuoBalls));
		return dltZhuShu;
	}

	/*
	 * ��ʽ�淨ע�����㷽�� @param int aRedBalls ������� @param int aBlueBalls ������� @return
	 * long ע��
	 */
	private long getDLTFSZhuShu(int aRedBalls, int aBlueBalls) {
		long dltZhuShu = 0L;
		iSendZhushu = 0L;
		if (aRedBalls > 0 && aBlueBalls > 0) {
			dltZhuShu += (PublicMethod.zuhe(5, aRedBalls)
					* PublicMethod.zuhe(2, aBlueBalls) * iProgressBeishu);
			iSendZhushu = PublicMethod.zuhe(5, aRedBalls)
					* PublicMethod.zuhe(2, aBlueBalls);
		}
		return dltZhuShu;
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
	 * �ı���ʾ��Ϣ @return void
	 */
	public void changeTextSumMoney() {
		switch (iCurrentButton) {
		case PublicConst.BUY_DLT_DANSHI:
			// ��ʽView
			if (redBallTable.getHighlightBallNums() < 5) {
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_red_number_dlt));
			} else {
				if (blueBallTable.getHighlightBallNums() < 2) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_blue_number_dlt));
				} else {
					int iZhuShu = getZhuShu();
					String iTempString = "��" + iZhuShu + "ע����"
							+ (iZhuShu * mTimesMoney) + "Ԫ";
					mTextSumMoney.setText(iTempString);
				}
			}

			break;
		case PublicConst.BUY_DLT_FUSHI: {
			int iRedHighlights = redBallTable.getHighlightBallNums();
			int iBlueHighlights = blueBallTable.getHighlightBallNums();

			// ������ ����
			if (iRedHighlights < 5) {
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_red_number_dlt));
			}
			// �������ﵽ���Ҫ��
			else if (iRedHighlights == 5) {
				if (iBlueHighlights < 2) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_blue_number_dlt));
				} else if (iBlueHighlights == 2) {
					int iZhuShu = getZhuShu();
					String iTempString = "��ǰΪ��ʽ����" + iZhuShu + "ע����"
							+ (iZhuShu * mTimesMoney) + "Ԫ";
					mTextSumMoney.setText(iTempString);
				} else {
					int iZhuShu = getZhuShu();
					String iTempString = "��ǰΪ������ʽ����" + iZhuShu + "ע����"
							+ (iZhuShu * mTimesMoney) + "Ԫ";
					mTextSumMoney.setText(iTempString);
				}
			}
			// ����ʽ
			else {
				if (iBlueHighlights < 2) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_blue_number_dlt));
				} else if (iBlueHighlights == 2) {
					int iZhuShu = getZhuShu();
					String iTempString = "��ǰΪǰ����ʽ����" + iZhuShu + "ע����"
							+ (iZhuShu * mTimesMoney) + "Ԫ";
					mTextSumMoney.setText(iTempString);
				} else {
					int iZhuShu = getZhuShu();
					String iTempString = "��ǰΪȫ��ʽ����" + iZhuShu + "ע����"
							+ (iZhuShu * mTimesMoney) + "Ԫ";
					mTextSumMoney.setText(iTempString);
				}
			}
			break;
		}
		case PublicConst.BUY_DLT_DANTUO: {
			int iRedHighlights = redBallTable.getHighlightBallNums();
			int iBlueHighlights = blueBallTable.getHighlightBallNums();
			int iRedTuoHighlights = redTuoBallTable.getHighlightBallNums();
			int iBlueTuoHighlights = blueTuoBallTable.getHighlightBallNums();

			if (iRedHighlights + iRedTuoHighlights < 6) {
				mTextSumMoney.setText(getResources().getString(
						R.string.choose_number_dialog_tip10));
			} else if (iBlueHighlights + iBlueTuoHighlights < 2) {
				mTextSumMoney.setText(getResources().getString(
						R.string.choose_number_dialog_tip11));
			} else {
				int iZhuShu = getZhuShu();
				String iTempString = "��ǰΪ���ϣ���" + iZhuShu + "ע����"
						+ (iZhuShu * mTimesMoney) + "Ԫ";
				mTextSumMoney.setText(iTempString);
			}

			break;
		}
		case PublicConst.BUY_DLT_TWOIN12:
			// ��ʽView
			if (blueBallTable.getHighlightBallNums() < 2) {
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_number));
			} else {
				int iZhuShu = getZhuShu();
				String iTempString = "��" + iZhuShu + "ע����"
						+ (iZhuShu * mTimesMoney) + "Ԫ";
				mTextSumMoney.setText(iTempString);
			}
			break;
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
		case PublicConst.BUY_DLT_DANSHI:
			// ��ʽView�У���2��ball table
			buy_DANSHI(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.BUY_DLT_FUSHI:
			buy_FUSHI(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.BUY_DLT_DANTUO:
			buy_DANTUO(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.BUY_DLT_TWOIN12:
			buy_TWOIN12(aWhichGroupBall, aBallViewId);
			break;
		default:
			break;
		}
	}

	/*
	 * 12ѡ2�淨�ı�View @param int aWhichGroupBall �������С��λ�� @param int aBallViewId
	 * С��id @return void
	 */
	private void buy_TWOIN12(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 1) {
			int iChosenBallSum = 12;
			int isHighLight = blueBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 1;
				PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
						+ aBallViewId);
			} else
				mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 0;
			// mBallHolder.flag=1;
		}
	}

	/*
	 * �����淨�ı�View @param int aWhichGroupBall �������С��λ�� @param int aBallViewId
	 * С��id @return void
	 */
	private void buy_DANTUO(int aWhichGroupBall, int aBallViewId) {

		if (aWhichGroupBall == 0) { // ���� ���� ���4��С��
			int iChosenBallSum = 4;
			if (redTuoBallTable.getHighlightBallNums() > 18)
				iChosenBallSum = 22 - redTuoBallTable.getHighlightBallNums();
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
			// mBallHolder.flag=1;
		} else if (aWhichGroupBall == 1) { // ����������
			int iChosenBallSum = 1;
			int isHighLight = blueBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {

				mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 1;
				mBallHolder.DanShi.iTuoBlueBallStatus[aBallViewId] = 0;
				blueTuoBallTable.clearOnBallHighlight(aBallViewId);
				PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
						+ aBallViewId);
			} else
				mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 0;
			// mBallHolder.flag=1;

		} else if (aWhichGroupBall == 2) { // ���� ���� ���34��С��
			int iChosenBallSum = 22 - redBallTable.getHighlightBallNums();
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
			// mBallHolder.flag=1;
		} else if (aWhichGroupBall == 3) { // ���� ���� ���5��С��
			int iChosenBallSum = 12;
			int isHighLight = blueTuoBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.DanShi.iTuoBlueBallStatus[aBallViewId] = 1;
				mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 0;
				blueBallTable.clearOnBallHighlight(aBallViewId);
				PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
						+ aBallViewId);
			} else
				mBallHolder.DanShi.iTuoBlueBallStatus[aBallViewId] = 0;
			// mBallHolder.flag=1;
		}
	}

	/*
	 * ��ʽ�淨�ı�View @param int aWhichGroupBall �������С��λ�� @param int aBallViewId
	 * С��id @return void
	 */
	private void buy_FUSHI(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 0) { // ������
			int iChosenBallSum = 22;
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
			// mBallHolder.flag=1;
		} else if (aWhichGroupBall == 1) {
			int iChosenBallSum = 12;
			int isHighLight = blueBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 1;
				PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
						+ aBallViewId);
			} else
				mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 0;
			// mBallHolder.flag=1;
		}
	}

	/*
	 * ��ʽ�淨�ı�View @param int aWhichGroupBall �������С��λ�� @param int aBallViewId
	 * С��id @return void
	 */
	private void buy_DANSHI(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 0) { // ������
			int iChosenBallSum = 5;
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
			// mBallHolder.flag=1;
		} else if (aWhichGroupBall == 1) {
			int iChosenBallSum = 2;
			int isHighLight = blueBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 1;
				PublicMethod.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
						+ aBallViewId);
			} else
				mBallHolder.DanShi.iBlueBallStatus[aBallViewId] = 0;
			// mBallHolder.flag=1;
		}
	}

	/*
	 * ����BallTable @param LinearLayout aParentView ��һ��Layout @param int
	 * aLayoutId ��ǰBallTable��LayoutId @param int aFieldWidth
	 * BallTable����Ŀ�ȣ�����Ļ��ȣ� @param int aBallNum С����� @param int aBallViewWidth
	 * С����ͼ�Ŀ�ȣ�ͼƬ��ȣ� @param int[] aResId С��ͼƬId @param int aIdStart С��Id��ʼ��ֵ
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
		// PublicMethod.myOutput("-----w:"+iBallTable.tableLayout.getWidth()+"
		// h:"+iBallTable.tableLayout.getHeight());
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
		menuInflater.inflate(R.menu.menu_dlt, menu);
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
		PublicMethod.myOutput("************featureId " + featureId);
		switch (item.getItemId()) {
		case R.id.dlt_menu_confirm:
			beginTouZhu();
			break;
		case R.id.dlt_menu_reselect_num:
			beginReselect();
			break;
		case R.id.dlt_menu_game_introduce:
			showGameIntroduction();

			break;
		case R.id.dlt_menu_cancel:
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
		// TODO Auto-generated method stub
	}

	/*
	 * �Ի���ص����� @param int[] aNums �Ի��򷵻ص���ֵ @return BallTable
	 */
	public void onOkClick(int[] aNums) {
		// TODO Auto-generated method stub
		mBallHolder = ballBetPublicClass.new BallHolder();
		// mBallHolder = new BallHolder();
		// ��ʽ ����2������
		if (aNums.length == 2) {
			iFushiRedBallNumber = aNums[0];
			iFushiBlueBallNumber = aNums[1];
			mBallHolder = redBallTable.randomChooseConfigChange(
					iFushiRedBallNumber, mBallHolder, 0);

			mBallHolder = blueBallTable.randomChooseConfigChange(
					iFushiBlueBallNumber, mBallHolder, 1);
			changeTextSumMoney();
		} else if (aNums.length == 4) {
			iDantuoRedDanNumber = aNums[0];
			iDantuoRedTuoNumber = aNums[1];
			iDantuoBlueNumber = aNums[2];
			iDantuoBlueTuoNumber = aNums[3];

			int[] iTotalRandoms = PublicMethod.getRandomsWithoutCollision(
					aNums[0] + aNums[1], 0, 34);
			int[] iTotalBlueRandoms = PublicMethod.getRandomsWithoutCollision(
					aNums[2] + aNums[3], 0, 11);

			// redBallTable.randomChoose(iDantuoRedDanNumber);
			// redTuoBallTable.randomChoose(iDantuoRedTuoNumber);
			redBallTable.clearAllHighlights();
			redTuoBallTable.clearAllHighlights();
			int i;
			for (i = 0; i < iDantuoRedDanNumber; i++) {
				// redBallTable.changeBallState(4, iTotalRandoms[i]);
				int isHighLight = redBallTable.changeBallState(4,
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
				int isHighLight = redTuoBallTable.changeBallState(22,
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
			blueBallTable.clearAllHighlights();
			blueTuoBallTable.clearAllHighlights();
			int j;
			for (j = 0; j < iDantuoBlueNumber; j++) {
				int isHighLight = blueBallTable.changeBallState(1,
						iTotalBlueRandoms[j]);
				PublicMethod.myOutput("****isHighLight " + isHighLight
						+ "PublicConst.BALL_TO_HIGHLIGHT "
						+ PublicConst.BALL_TO_HIGHLIGHT);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.DanShi.iBlueBallStatus[iTotalBlueRandoms[j]] = 1;
					PublicMethod
							.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
									+ iTotalBlueRandoms[j]);
				} else
					mBallHolder.DanShi.iBlueBallStatus[iTotalBlueRandoms[j]] = 0;
			}
			for (j = iDantuoBlueNumber; j < iDantuoBlueNumber
					+ iDantuoBlueTuoNumber; j++) {
				int isHighLight = blueTuoBallTable.changeBallState(12,
						iTotalBlueRandoms[j]);
				PublicMethod.myOutput("****isHighLight " + isHighLight
						+ "PublicConst.BALL_TO_HIGHLIGHT "
						+ PublicConst.BALL_TO_HIGHLIGHT);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.DanShi.iTuoBlueBallStatus[iTotalBlueRandoms[j]] = 1;
					PublicMethod
							.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
									+ iTotalBlueRandoms[j]);
				} else
					mBallHolder.DanShi.iTuoBlueBallStatus[iTotalBlueRandoms[j]] = 0;
			}
	
			changeTextSumMoney();
		} else if (aNums.length == 1) {
			iTwoin12BlueBallNumber = aNums[0];
			PublicMethod.myOutput("*********** iTwoin12BlueBallNumber "
					+ iTwoin12BlueBallNumber);
			mBallHolder = blueBallTable.randomChooseConfigChange(
					iTwoin12BlueBallNumber, mBallHolder, 1);
			changeTextSumMoney();
		}

	}

	// ��ʼͶע ��Ӧ���Ͷע��ť �� menu�����Ͷעѡ��
	private void beginTouZhu() {
		// ������ 7.4 �����޸ģ���ӵ�½���ж�
		// fqc eidt 2010/7/13 �޸Ĵ���͸�淨
		int iZhuShu = getZhuShu();
		PublicMethod.myOutput("*************iCurrentButton  " + iCurrentButton);
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(DLT.this,
				"addInfo");
		String sessionIdStr = pre.getUserLoginInfo("sessionid");

		if (iCurrentButton == PublicConst.BUY_DLT_FUSHI) {
		
			if (sessionIdStr.equals("")) {
				Intent intentSession = new Intent(DLT.this, UserLogin.class);
				startActivity(intentSession);
			} else if ((redBallTable.getHighlightBallNums() < 5 || redBallTable
					.getHighlightBallNums() > 22)
					&& (blueBallTable.getHighlightBallNums() < 2 || blueBallTable
							.getHighlightBallNums() > 12)) {
				alert1("��ѡ��5~22��ǰ�������2~12����������");
			} else if (redBallTable.getHighlightBallNums() < 5) {
				alert1("��ѡ������5��ǰ������");
			} else if (blueBallTable.getHighlightBallNums() < 2) {
				alert1("��ѡ������2����������");
			} else if (iZhuShu > 10000) {
				DialogExcessive();
			} else if (jrtLot.ifPerfectIfo(this, sessionIdStr)) {

				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				alert(sTouzhuAlert);
			}
		}
		if (iCurrentButton == PublicConst.BUY_DLT_DANTUO) {
			// //������ 7.4 �����޸ģ���ӵ�½���ж�

			int redNumber = redBallTable.getHighlightBallNums();
			int redTuoNumber = redTuoBallTable.getHighlightBallNums();
			int blueNumber = blueBallTable.getHighlightBallNums();
			int blueTuoNumber = blueTuoBallTable.getHighlightBallNums();
			if (sessionIdStr.equals("")) {
				Intent intentSession = new Intent(DLT.this, UserLogin.class);
				startActivity(intentSession);
			}

			else if (redNumber + redTuoNumber < 6
					|| redNumber + redTuoNumber > 20 || redTuoNumber < 2
					|| redNumber > 4 || blueTuoNumber < 2 || blueNumber > 1) {
				alert1("��ѡ��:\n 0~4��ǰ�����룻\n" + " 2~16��ǰ�����룻\n" + " 0~1���������룻\n"
						+ " 2~12���������룻\n" + " ǰ������6~20����\n" + " ��������2~12����\n");
			} else if (iZhuShu > 10000) {
				DialogExcessive();
			} else if (jrtLot.ifPerfectIfo(this, sessionIdStr)) {

				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				// Ӧ�õ��õ��ϵķ��� �³� 20100713
				alert_dantuo(sTouzhuAlert);
			}

		}
		if (iCurrentButton == PublicConst.BUY_DLT_TWOIN12) { // 1Ϊ��ʽͶע
			if (sessionIdStr.equals("")) {
				Intent intentSession = new Intent(DLT.this, UserLogin.class);
				startActivity(intentSession);
			} else if (blueBallTable.getHighlightBallNums() < 2) {
				alert1("��ѡ��2~12������");
				// }
			} else if (iZhuShu > 10000) {
				DialogExcessive();
			} else if (jrtLot.ifPerfectIfo(this, sessionIdStr)) {

				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				alertTwoIn12(sTouzhuAlert);
			}
		}
	}

	// ���menu�����»�ѡ �������»�ѡ�ķ���
	private void beginReselect() {

		if (iCurrentButton == PublicConst.BUY_DLT_DANSHI) {
			redBallTable.clearAllHighlights();
			blueBallTable.clearAllHighlights();
		} else if (iCurrentButton == PublicConst.BUY_DLT_FUSHI) {
			redBallTable.clearAllHighlights();
			blueBallTable.clearAllHighlights();
		} else if (iCurrentButton == PublicConst.BUY_DLT_DANTUO) {
			redBallTable.clearAllHighlights();
			blueBallTable.clearAllHighlights();
			redTuoBallTable.clearAllHighlights();
			blueTuoBallTable.clearAllHighlights();
		} else if (iCurrentButton == PublicConst.BUY_DLT_TWOIN12) {
			blueBallTable.clearAllHighlights();
		}
	}

	// ���menu������淨���ܣ�ͨ���Ի�����ʾ
	private void showGameIntroduction() {
		WebView webView = new WebView(this);
		String url = "file:///android_asset/ruyihelper_gameIntroduction_dlt.html";
		// String url
		// ="file://android_asset/"+"ruyihelper_gameIntroduction_dlt.html";
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

	// ��ʾ�� ����ȷ���Ƿ�Ͷע
	private void alertTwoIn12(String string) {

		Builder dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����")
				.setMessage(string).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								iHttp.whetherChange = false;
								showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
								// TODO Auto-generated method stub
								Thread t = new Thread(new Runnable() {
									int iZhuShu = getZhuShu();
									int iQiShu = getQishu();
									String[] strCode = null;
									int iBeiShu = mSeekBarBeishu.getProgress();

									@Override
									public void run() {
										// TODO Auto-generated method stub
										PublicMethod
												.myOutput("----------------zhushu"
														+ iZhuShu);
										int iBlueHighlights = blueBallTable
												.getHighlightBallNums();
										PublicMethod
												.myOutput("---------------iBlueHighlights "
														+ iBlueHighlights);
										if (iBlueHighlights >= 2) {
											String zhuma = zhuma_twoin12();
											PublicMethod
													.myOutput("----------------zhuma"
															+ zhuma);
											strCode = payNew(zhuma, ""
													+ iBeiShu,
													iZhuShu * 2 + "", iQiShu
															+ "");
										}

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
										} else if (strCode[0].equals("040006")
												|| strCode[0].equals("201015")) {
											Message msg = new Message();
											msg.what = 1;
											handler.sendMessage(msg);
										}
										else if (strCode[1].equals("002002")) {
											Message msg = new Message();
											msg.what = 3;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("00")
												&& strCode[1].equals("00")) {
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

	// ��ʾ�� ����ȷ���Ƿ�Ͷע
	private void alert(String string) {
		Builder dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����")
				.setMessage(string).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								if (iCurrentButton == PublicConst.BUY_DLT_DANSHI) {
									dlt_b_touzhu_danshi.setClickable(false);
									dlt_b_touzhu_danshi
											.setImageResource(R.drawable.touzhuup_n);
								} else if (iCurrentButton == PublicConst.BUY_DLT_FUSHI) {
									dlt_b_touzhu_fushi.setClickable(false);
									dlt_b_touzhu_fushi
											.setImageResource(R.drawable.touzhuup_n);
								}

								iHttp.whetherChange = false;
								showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
								// TODO Auto-generated method stub
								Thread t = new Thread(new Runnable() {
									int iZhuShu = getZhuShu();
									int iQiShu = getQishu();
									String[] strCode = null;
									int iBeiShu = mSeekBarBeishu.getProgress();

									@Override
									public void run() {
										// TODO Auto-generated method stub
										int iRedHighlights = redBallTable
												.getHighlightBallNums();
										int iBlueHighlights = blueBallTable
												.getHighlightBallNums();
										// int
										// iTuoRedHighlights=redTuoBallTable.getHighlightBallNums();
										// �ж��Ǵ���͸��ʽ���Ǹ�ʽ
										if (iRedHighlights == 5
												&& iBlueHighlights == 2) {
											String zhuma = zhuma_danshi();
											PublicMethod
													.myOutput("----------------zhuma"
															+ zhuma);
											if (mTimesMoney == 2) {
												strCode = payNew(zhuma, ""
														+ iBeiShu, iZhuShu * 2
														+ "", iQiShu + "");
											} else if (mTimesMoney == 3) {
												strCode = payNew(zhuma, ""
														+ iBeiShu, iZhuShu * 3
														+ "", iQiShu + "");
											}
										} else if ((iRedHighlights > 5 && iBlueHighlights == 2)
												|| (iRedHighlights == 5 && iBlueHighlights > 2)
												|| (iRedHighlights > 5 && iBlueHighlights > 2)) {//
											String zhuma_fushi = zhuma_fushi();
											PublicMethod
													.myOutput("-------------------fushizhuma"
															+ zhuma_fushi);
		
											// �½ӿ� �³�20100711
											if (mTimesMoney == 2) {
												strCode = payNew(zhuma_fushi,
														"" + iBeiShu, iZhuShu
																* 2 + "",
														iQiShu + "");
											} else if (mTimesMoney == 3) {
												strCode = payNew(zhuma_fushi,
														"" + iBeiShu, iZhuShu
																* 3 + "",
														iQiShu + "");
											}
										}
			

										if (iCurrentButton == PublicConst.BUY_DLT_DANSHI) {
											dlt_b_touzhu_danshi
													.setClickable(true);
										} else if (iCurrentButton == PublicConst.BUY_DLT_FUSHI) {
											dlt_b_touzhu_fushi
													.setClickable(true);
										}
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
										}
										else if (strCode[0].equals("00")
												&& strCode[1].equals("00")) {
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

	private void alert_dantuo(String string) {

		Builder dialog = new AlertDialog.Builder(this).setMessage(string)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dlt_b_touzhu_dantuo.setClickable(false);
						dlt_b_touzhu_dantuo
								.setImageResource(R.drawable.touzhuup_n);

						iHttp.whetherChange = false;
						showDialog(DIALOG1_KEY); // ��ʾ������ʾ��

						// TODO Auto-generated method stub
						Thread t = new Thread(new Runnable() {
							int iZhuShu = getZhuShu();
							int iQiShu = getQishu();
							int iBeiShu = mSeekBarBeishu.getProgress();
							String[] strCode = null;

							//

							@Override
							public void run() {
								// TODO Auto-generated method stub
								// String str=
								// pay("1512-F47104-00-01-0001151718202232~06^",iZhuShu+"");
								String zhuma_dantuo = zhuma_dantuo();
								// 20100711 cc �Ͻӿ�
								// str=pay(zhuma_dantuo,iZhuShu+"");
								// 20100711 cc �½ӿ�
								if (mTimesMoney == 2) {
									strCode = payNew(zhuma_dantuo,
											iBeiShu + "", iZhuShu * 2 + "",
											iQiShu + "");
								} else if (mTimesMoney == 3) {
									strCode = payNew(zhuma_dantuo,
											iBeiShu + "", iZhuShu * 3 + "",
											iQiShu + "");
								}
								// }

								dlt_b_touzhu_dantuo.setClickable(true);
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
								} else if (strCode[0].equals("040006")) {
									Message msg = new Message();
									msg.what = 1;
									handler.sendMessage(msg);
								}
								else if (strCode[0].equals("00")
										&& strCode[1].equals("00")) {
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

	// Ͷע����ӿڴ���
	protected String pay(String bets, String bets_zhu_num) {
		// TODO Auto-generated method stub
		BettingInterface betting = new BettingInterface();

		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,
				"addInfo");
		String sessionid = shellRW.getUserLoginInfo("sessionid");
		String phonenum = shellRW.getUserLoginInfo("phonenum");
		PublicMethod.myOutput("-------------touzhusessionid" + sessionid);
		PublicMethod.myOutput("-------------phonenum" + phonenum);

		String error_code = betting.Betting(phonenum, bets, bets_zhu_num,
				sessionid);
		// FileIO file=new FileIO();
		// file.infoToWrite="----------------dlttouzhu"+error_code;
		// file.write();
		return error_code;
	}

	// Ͷע�½ӿ� 20100711�³�
	protected String[] payNew(String betCode, String lotMulti, String amount,
			String qiShu) {
		// TODO Auto-generated method stub
		String[] error_code = null;
		BettingInterface betting = new BettingInterface();

		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,
				"addInfo");
		String sessionid = shellRW.getUserLoginInfo("sessionid");
		String phonenum = shellRW.getUserLoginInfo("phonenum");
		PublicMethod.myOutput("-------------touzhusessionid" + sessionid);
		PublicMethod.myOutput("-------------phonenum" + phonenum
				+ "----lotMulti---" + lotMulti + "----amount----" + amount
				+ "---qiShu---" + qiShu);
		if (mTimesMoney == 2) {
			error_code = betting.BettingTC(phonenum, "T01001", betCode,
					lotMulti, amount, "2", qiShu, sessionid);
		} else if (mTimesMoney == 3) {
			error_code = betting.BettingTC(phonenum, "T01001", betCode,
					lotMulti, amount, "3", qiShu, sessionid);
		}

		return error_code;
	}

	// ��ʽ��Ͷעʱ�����Ͷע����� ��ʽ5+2��ʽ
	public String zhuma_danshi() {

		// int beishu = mSeekBarBeishu.getProgress();
		int[] zhuma = redBallTable.getHighlightBallNOs();
		int[] zhumablue = blueBallTable.getHighlightBallNOs();
		// PublicMethod.myOutput("-----------zhuma[0]"+zhuma[0]);

		String t_str = "";

		for (int i = 0; i < zhuma.length; i++) {
			if (zhuma[i] >= 10) {
				t_str += zhuma[i];
			} else if (zhuma[i] < 10) {
				t_str += "0" + zhuma[i];
			}
			if (i != zhuma.length - 1)
				t_str += " ";
			else
				t_str += "-";
		}
		for (int i = 0; i < 2; i++) {
			if (zhumablue[i] >= 10) {
				t_str += zhumablue[i];
			} else if (zhumablue[i] < 10) {
				t_str += "0" + zhumablue[i];
			}
			if (i != 1)
				t_str += " ";
		}

		return t_str;

	}

	// ��ʽͶעʱͶע�����
	public String zhuma_fushi() {
		// int beishu = mSeekBarBeishu.getProgress();



		int zhuma[] = redBallTable.getHighlightBallNOs();
		int zhumablue[] = blueBallTable.getHighlightBallNOs();
		PublicMethod.myOutput("---------------fushi" + zhuma[0]);
		String t_str = "";

		for (int i = 0; i < zhuma.length; i++) {
			if (zhuma[i] >= 10) {
				t_str += zhuma[i];
			} else if (zhuma[i] < 10) {
				t_str += "0" + zhuma[i];
			}
			if (i != zhuma.length - 1)
				t_str += " ";
			else
				t_str += "-";
		}
		for (int j = 0; j < zhumablue.length; j++) {
			if (zhumablue[j] >= 10) {
				t_str += zhumablue[j];
			} else if (zhumablue[j] < 10) {
				t_str += "0" + zhumablue[j];
			}
			if (j != zhumablue.length - 1)
				t_str += " ";
		}

		return t_str;
	}

	// ����ͶעʱͶע�����
	public String zhuma_dantuo() {
		// int zhushu=getZhuShu();
		// int beishu = mSeekBarBeishu.getProgress();
		// int iBlueHighlights = blueBallTable.getHighlightBallNums();
		int[] zhuma = redBallTable.getHighlightBallNOs();
		int[] tuozhuma = redTuoBallTable.getHighlightBallNOs();
		int[] zhumablue = blueBallTable.getHighlightBallNOs();
		int[] tuozhumablue = blueTuoBallTable.getHighlightBallNOs();
		// PublicMethod.myOutput("---------------fushi"+zhuma[0]);
		String t_str = "";

		for (int i = 0; i < zhuma.length; i++) {
			if (zhuma[i] >= 10) {
				t_str += zhuma[i];
			} else if (zhuma[i] < 10) {
				t_str += "0" + zhuma[i];
			}
			if (i != zhuma.length - 1)
				t_str += " ";

		}
		t_str += "$";
		for (int j = 0; j < tuozhuma.length; j++) {
			if (tuozhuma[j] >= 10) {
				t_str += tuozhuma[j];
			} else if (tuozhuma[j] < 10) {
				t_str += "0" + tuozhuma[j];
			}
			if (j != tuozhuma.length - 1)
				t_str += " ";
			else
				t_str += "-";
		}
		for (int k = 0; k < zhumablue.length; k++) {
			if (zhumablue[k] >= 10) {
				t_str += +zhumablue[k];
			} else if (zhumablue[k] < 10) {
				t_str += "0" + zhumablue[k];
			}
			if (k != zhumablue.length - 1)
				t_str += " ";

			// t_str+="^";�Ƶ����� �³� 20100714
		}
		t_str += "$";
		for (int j = 0; j < tuozhumablue.length; j++) {
			if (tuozhumablue[j] >= 10) {
				t_str += tuozhumablue[j];
			} else if (tuozhumablue[j] < 10) {
				t_str += "0" + tuozhumablue[j];
			}
			if (j != tuozhumablue.length - 1)
				t_str += " ";
		}

		PublicMethod.myOutput("-------------------dantuo" + t_str);
		return t_str;
	}

	public String zhuma_twoin12() {

		// int beishu = mSeekBarBeishu.getProgress();
		int[] zhumablue = blueBallTable.getHighlightBallNOs();
		// PublicMethod.myOutput("-----------zhuma[0]"+zhuma[0]);

		String t_str = "";


		for (int i = 0; i < zhumablue.length; i++) {
			if (zhumablue[i] >= 10) {
				t_str += zhumablue[i];
			} else if (zhumablue[i] < 10) {
				t_str += "0" + zhumablue[i];
			}
			if (i != zhumablue.length - 1)
				t_str += " ";
		}

		return t_str;

	}

	// �������л�֮��radioButton����ʾ
	public void showHighLight() {
		if (iCurrentButton == PublicConst.BUY_DLT_DANSHI) {
			topButtonGroup.check(1);
			topButtonGroup.check(0);
			topButtonGroup.invalidate();
		} else if (iCurrentButton == PublicConst.BUY_DLT_FUSHI)
			topButtonGroup.check(1);
		else
			topButtonGroup.check(2);
		PublicMethod.myOutput("**********topButtonGroup.check(0);   "
				+ iCurrentButton + " PublicConst.BUY_DLT_DANSHI  "
				+ PublicConst.BUY_DLT_DANSHI);
	}

	// ����������ʾ�� 2010/7/4 �³�
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG1_KEY: {
			progressdialog = new ProgressDialog(this);
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
		int qiShu = getQishu();
		String red_zhuma_string = " ";
		int[] redZhuMa = redBallTable.getHighlightBallNOs();
		for (int i = 0; i < redBallTable.getHighlightBallNOs().length; i++) {
			red_zhuma_string = red_zhuma_string + String.valueOf(redZhuMa[i]);
			if (i != redBallTable.getHighlightBallNOs().length - 1)
				red_zhuma_string = red_zhuma_string + ".";
		}
		String blue_zhuma_string = " ";
		int[] blueZhuMa = blueBallTable.getHighlightBallNOs();
		for (int i = 0; i < blueBallTable.getHighlightBallNOs().length; i++) {
			blue_zhuma_string = blue_zhuma_string
					+ String.valueOf(blueZhuMa[i]);
			if (i != blueBallTable.getHighlightBallNOs().length - 1)
				blue_zhuma_string = blue_zhuma_string + ".";
		}

		if (iCurrentButton == PublicConst.BUY_DLT_DANSHI
				|| iCurrentButton == PublicConst.BUY_DLT_FUSHI) {

			return "ע�룺" + red_zhuma_string + " | " + blue_zhuma_string
					+ "\n"
					+ "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * mTimesMoney) + "Ԫ" + "\n" + "�����"
					+ ((qiShu - 1) * iZhuShu * mTimesMoney) + "Ԫ" + "\n"
					+ "ȷ��֧����";

		}
		if (iCurrentButton == PublicConst.BUY_DLT_TWOIN12) {

			return "ע�룺" + blue_zhuma_string + "\n"
					+ "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * mTimesMoney) + "Ԫ" + "\n" + "�����"
					+ ((qiShu - 1) * iZhuShu * mTimesMoney) + "Ԫ" + "\n"
					+ "ȷ��֧����";

		} else {
			String red_tuo_zhuma_string = " ";
			int[] redTuoZhuMa = redTuoBallTable.getHighlightBallNOs();
			for (int i = 0; i < redTuoBallTable.getHighlightBallNOs().length; i++) {
				red_tuo_zhuma_string = red_tuo_zhuma_string
						+ String.valueOf(redTuoZhuMa[i]);
				if (i != redTuoBallTable.getHighlightBallNOs().length - 1)
					red_tuo_zhuma_string = red_tuo_zhuma_string + ".";
			}
			String blue_tuo_zhuma_string = " ";
			int[] blueTuoZhuMa = blueTuoBallTable.getHighlightBallNOs();
			for (int i = 0; i < blueTuoBallTable.getHighlightBallNOs().length; i++) {
				blue_tuo_zhuma_string = blue_tuo_zhuma_string
						+ String.valueOf(blueTuoZhuMa[i]);
				if (i != blueTuoBallTable.getHighlightBallNOs().length - 1)
					blue_tuo_zhuma_string = blue_tuo_zhuma_string + ".";
			}
			return "ע�룺" + "\n" + "   ǰ�����룺" + red_zhuma_string + "\n"
					+ "   ǰ�����룺" + red_tuo_zhuma_string + "\n" + "   �������룺"
					+ blue_zhuma_string + "\n" + "   �������룺"
					+ blue_tuo_zhuma_string + "\n"
					+ "ע����"
					+ iZhuShu / mSeekBarBeishu.getProgress()
					+ "ע"
					+ "\n"
					+ // ע���������ϱ��� �³� 20100713
					"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "׷�ţ�"
					+ mSeekBarQishu.getProgress() + "��" + "\n" + "��"
					+ (iZhuShu * mTimesMoney) + "Ԫ" + "\n" + "�����"
					+ ((qiShu - 1) * iZhuShu * mTimesMoney) + "Ԫ" + "\n"
					+ "ȷ��֧����";
		}
	}

	/**
	 * ����Ͷע����10��Ԫʱ�ĶԻ���
	 */
	private void DialogExcessive() {
		AlertDialog.Builder builder = new AlertDialog.Builder(DLT.this);
		builder.setTitle("Ͷעʧ��");
		builder
				.setMessage("���Ų�Ʊ����Ͷע�����Ͷע������20000Ԫ������Ͷע��׷��Ͷע�����Ͷע������30000Ԫ��");
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