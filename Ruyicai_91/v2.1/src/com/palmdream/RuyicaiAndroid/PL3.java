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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.netintface.BettingInterface;
import com.palmdream.netintface.iHttp;
import com.palmdream.netintface.jrtLot;

/**
 * 
 * @author ������ �������淨����
 * 
 */
public class PL3 extends Activity implements OnClickListener,
		SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener,
		MyDialogListener {

	// С��ͼ�Ŀ��
	public static final int BALL_WIDTH = 29;
	// ������ǩ����
	private int iButtonNum = 2;

	// ����button
	private int iCurrentButton;

	private HorizontalScrollView topBar;
	private RadioGroup.LayoutParams topButtonLayoutParams;
	private RadioGroup topButtonGroup;

	private int defaultOffShade;
	private int defaultOnShade;

	int topButtonStringId[] = { R.string.zu_xuan, R.string.hezhi };
	int zuTopButtonStringId[] = { R.string.zuxuan3, R.string.zuxuan6 };
	int heTopButtonStringId[] = { R.string.hezhi_zhixuan, R.string.hezhi_zu3,
			R.string.hezhi_zu6 };
	int topButtonIdOn[] = { R.drawable.zuxuan_b, R.drawable.hezhi_b };
	int zuTopButtonIdOn[] = { R.drawable.zu3_b, R.drawable.zu6_b };
	int heTopButtonIdOn[] = { R.drawable.hezhizhixuan_b, R.drawable.hezhizu3_b,
			R.drawable.hezhizu6_b };
	int topButtonIdOff[] = { R.drawable.zuxuan, R.drawable.hezhi };
	int zuTopButtonIdOff[] = { R.drawable.zu3, R.drawable.zu6 };
	int heTopButtonIdOff[] = { R.drawable.hezhizhixuan, R.drawable.hezhizu3,
			R.drawable.hezhizu6 };

	// ��������3
	public static final int RED_PL3_ZU3_DANSHI_BAIWEI_START = 0x71000000;
	public static final int RED_PL3_ZU3_DANSHI_SHIWEI_START = 0x71000010;
	public static final int RED_PL3_ZU3_DANSHI_GEWEI_START = 0x71000020;
	public static final int RED_PL3_ZU3_FUSHI_START = 0x71000030;
	// ��������6
	public static final int RED_PL3_ZU6_START = 0x71000040;
	// ������ֱѡ
	public static final int RED_PL3_ZHIXUAN_BAIWEI_START = 0x71000070;
	public static final int RED_PL3_ZHIXUAN_SHIWEI_START = 0x71000080;
	public static final int RED_PL3_ZHIXUAN_GEWEI_START = 0x71000090;
	// ��������ֵ
	public static final int RED_PL3_HEZHI_ZHIXUAN_START = 0x71000100;
	public static final int RED_PL3_HEZHI_ZU3_START = 0x71000200;
	public static final int RED_PL3_HEZHI_ZU6_START = 0x71000303;

	// ������ʾ��
	private static final int DIALOG1_KEY = 0;
	ProgressDialog progressdialog;

	ScrollView mHScrollView;

	SeekBar mSeekBarBeishu;// ����
	SeekBar mSeekBarQishu;// ����

	TextView mTextBeishu;// ����
	TextView mTextQishu;// ����

	int iWhich = 0;

	// SeekBarĬ��Ϊ1
	int iProgressBeishu = 1;
	int iProgressQishu = 1;
	// ��ѡȫ�ָ�ѡ��
	CheckBox mCheckBox;

	TextView mTextSumMoney;// �ܽ��
	private int iScreenWidth;

	// ��������3��ʽǰ2λ��ͬ
	BallTable PL3A1Zu3DanshiBallTable = null;
	BallTable PL3A2Zu3DanshiBallTable = null;
	// ��������3��ʽ����λ
	BallTable PL3BZu3DanshiBallTable = null;
	// ��������3��ʽ
	BallTable PL3Zu3FushiBallTable = null;
	// ��������6����ʽ
	BallTable PL3Zu6BallTable = null;
	// ������ֱѡ
	BallTable PL3ZhixuanBaiweiBallTable = null;// ֱѡ��λ
	BallTable PL3ZhixuanShiweiBallTable = null;// ֱѡʮλ
	BallTable PL3ZhixuanGeweiBallTable = null;// ֱѡ��λ
	// ��������ֵ
	BallTable PL3HezhiZhixuanBallTable = null;// ��ֱֵѡ
	BallTable PL3HezhiZu3BallTable = null;// ��ֵ��3
	BallTable PL3HezhiZu6BallTable = null;// ��ֵ��6

	// ��������ֵ��������tab��ť
	TextView PL3_hezhi_zhixuan;
	TextView PL3_hezhi_zu3;
	TextView PL3_hezhi_zu6;

	// ��3����ʽ
	boolean bZu3Danshi = true;
	boolean bZu3Fushi = false;

	private int PL3BallResId[] = { R.drawable.grey, R.drawable.red };

	// ��������3�淨��ť
	RadioGroup radiogroup;
	RadioButton danshirbtn;
	RadioButton fushirbtn;
	int redBallViewNum;
	int redBallViewWidth;
	LinearLayout linearLayout;

	Button zhixuanNewSelectbtn;
	Button zu3NewSelectbtn;
	Button zu6NewSelectbtn;
	Button hezhiZhixuanNewSelectbtn;
	Button hezhiZu3NewSelectbtn;
	Button hezhiZu6NewSelectbtn;

	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;

	LinearLayout buyView;
	// ʵ�ֺ��ݵ��л�
	private BallHolderPL3 mBallHolder = null;
	private BallHolderPL3 tempBallHolder = null;
	private int tempCurrentButton;
	public int publicTopButton;
	public int tempCurrentWhich;
	public int type;
	public static final int ZHIXUAN = 0;
	public static final int ZUXUAN = 1;
	public static final int HEZHI = 2;
	private ImageButton touzhu;

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
				Toast
						.makeText(getBaseContext(), "Ͷעʧ�����㣡",
								Toast.LENGTH_LONG).show();
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
				Toast.makeText(getBaseContext(), "Ͷע�ɹ�����Ʊ�ɹ���",
						Toast.LENGTH_LONG).show();
				// //��Ҫ���AlertDialog��ʾ�úű���ͣ����ϵ�ͷ�
				// �������С�� �³� 20100728
				if (iCurrentButton == PublicConst.BUY_PL3_ZHIXUAN) {
					PL3ZhixuanBaiweiBallTable.clearAllHighlights();
					PL3ZhixuanShiweiBallTable.clearAllHighlights();
					PL3ZhixuanGeweiBallTable.clearAllHighlights();
				} else if (iCurrentButton == PublicConst.BUY_PL3_ZU3) {
					if (bZu3Danshi) {
						PL3A1Zu3DanshiBallTable.clearAllHighlights();
						PL3A2Zu3DanshiBallTable.clearAllHighlights();
						PL3BZu3DanshiBallTable.clearAllHighlights();
					}
					if (bZu3Fushi) {
						PL3Zu3FushiBallTable.clearAllHighlights();
					}
				} else if (iCurrentButton == PublicConst.BUY_PL3_ZU6) {
					PL3Zu6BallTable.clearAllHighlights();
				} else if (iCurrentButton == PublicConst.BUY_PL3_HEZHI) {
					if (iWhich == 10) {
						PL3HezhiZhixuanBallTable.clearAllHighlights();
					}
					if (iWhich == 11) {
						PL3HezhiZu3BallTable.clearAllHighlights();
					}
					if (iWhich == 12) {
						PL3HezhiZu6BallTable.clearAllHighlights();
					}
				}
				break;
			// case 5:
			// //��Ҫ���AlertDialogע��ʧ��
			// break;
			case 6:
				// //��Ҫ���AlertDialog��ʾ�û���¼�ɹ�
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷע������", Toast.LENGTH_LONG)
						.show();
				// �������С�� �³� 20100728
				if (iCurrentButton == PublicConst.BUY_PL3_ZHIXUAN) {
					PL3ZhixuanBaiweiBallTable.clearAllHighlights();
					PL3ZhixuanShiweiBallTable.clearAllHighlights();
					PL3ZhixuanGeweiBallTable.clearAllHighlights();
				} else if (iCurrentButton == PublicConst.BUY_PL3_ZU3) {
					if (bZu3Danshi) {
						PL3A1Zu3DanshiBallTable.clearAllHighlights();
						PL3A2Zu3DanshiBallTable.clearAllHighlights();
						PL3BZu3DanshiBallTable.clearAllHighlights();
					}
					if (bZu3Fushi) {
						PL3Zu3FushiBallTable.clearAllHighlights();
					}
				} else if (iCurrentButton == PublicConst.BUY_PL3_ZU6) {
					PL3Zu6BallTable.clearAllHighlights();
				} else if (iCurrentButton == PublicConst.BUY_PL3_HEZHI) {
					if (iWhich == 10) {
						PL3HezhiZhixuanBallTable.clearAllHighlights();
					}
					if (iWhich == 11) {
						PL3HezhiZu3BallTable.clearAllHighlights();
					}
					if (iWhich == 12) {
						PL3HezhiZu6BallTable.clearAllHighlights();
					}
				}
				break;
			case 7:
				progressdialog.dismiss();
				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(PL3.this, UserLogin.class);
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
				touzhu.setImageResource(R.drawable.imageselecter);
				break;
			case 11:
				progressdialog.dismiss();// δ��¼
				Intent intent1 = new Intent(UserLogin.UNSUCCESS);
				sendBroadcast(intent1);
				ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(
						PL3.this, "addInfo");
				shellRW.setUserLoginInfo("sessionid", "");
				Intent intent2 = new Intent(PL3.this, UserLogin.class);
				startActivity(intent2);
				break;
			}
			//				
		}
	};

	/**
	 * Called when the activity is first created.
	 * 
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ----- ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// ----- ���ؿ�� layout
		setContentView(R.layout.layout_main_buy);

		// 7.3�����޸ģ������ء�Button����ImageButton
		ImageButton returnBtn = (ImageButton) findViewById(R.id.goucaitouzhu_title_return);
		returnBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (type) {
				case ZHIXUAN:
					finish();
					break;
				case ZUXUAN:
					type = ZHIXUAN;
					// ----- ��ʼ��������ť
					commit(iButtonNum, topButtonIdOn, topButtonIdOff);
					break;
				case HEZHI:
					type = ZHIXUAN;
					// ----- ��ʼ��������ť
					commit(iButtonNum, topButtonIdOn, topButtonIdOff);
					break;
				}
			}

		});
		TextView title = (TextView) findViewById(R.id.goucaitouzhu_title);
		title.setText(getResources().getString(R.string.pailie3));

		mHScrollView = (ScrollView) findViewById(R.id.scroll_global);
		// ��ȡ������id
		buyView = (LinearLayout) findViewById(R.id.layout_buy);
		// ----- ��ʼ��������ť
		initButtons();

		// ----- Ĭ�� ���ص�ʽlayout
		// iCurrentButton = PublicConst.BUY_SSQ_DANSHI;
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
	public Object onRetainNonConfigurationInstance() {
		mBallHolder.topButtonGroup = iCurrentButton;
		return mBallHolder;
	}

	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);
		mBallHolder.flag = 1;
		switch (iCurrentButton) {
		case PublicConst.BUY_PL3_ZHIXUAN:
			mBallHolder.ZhixuanBallGroup.iBeiShu = getBeishu();
			mBallHolder.ZhixuanBallGroup.iQiShu = getQishu();
			mBallHolder.ZhixuanBallGroup.bCheckBox = getCheckBox();
			break;
		case PublicConst.BUY_PL3_ZU3:
			mBallHolder.Zu3BallGroup.iBeiShu = getBeishu();
			mBallHolder.Zu3BallGroup.iQiShu = getQishu();
			mBallHolder.Zu3BallGroup.bCheckBox = getCheckBox();
			mBallHolder.Zu3BallGroup.bRadioBtnDanshi = getZu3DanshiRadioButton();
			mBallHolder.Zu3BallGroup.bRadioBtnFushi = getZu3FushiRadioButton();
			break;
		case PublicConst.BUY_PL3_ZU6:
			mBallHolder.Zu6BallGroup.iBeiShu = getBeishu();
			mBallHolder.Zu6BallGroup.iQiShu = getQishu();
			mBallHolder.Zu6BallGroup.bCheckBox = getCheckBox();
			break;
		case PublicConst.BUY_PL3_HEZHI:
			if (iWhich == 10) {
				mBallHolder.HezhiZhixuanBallGroup.iBeiShu = getBeishu();
				mBallHolder.HezhiZhixuanBallGroup.iQiShu = getQishu();
				mBallHolder.HezhiZhixuanBallGroup.bCheckBox = getCheckBox();
			}
			if (iWhich == 11) {
				mBallHolder.HezhiZu3BallGroup.iBeiShu = getBeishu();
				mBallHolder.HezhiZu3BallGroup.iQiShu = getQishu();
				mBallHolder.HezhiZu3BallGroup.bCheckBox = getCheckBox();
			}
			if (iWhich == 12) {
				mBallHolder.HezhiZu6BallGroup.iBeiShu = getBeishu();
				mBallHolder.HezhiZu6BallGroup.iQiShu = getQishu();
				mBallHolder.HezhiZu6BallGroup.bCheckBox = getCheckBox();
			}
			break;
		}

		tempBallHolder = mBallHolder;
		tempCurrentButton = iCurrentButton;
		tempCurrentWhich = iWhich;
		if (mBallHolder == null) {
			mBallHolder = new BallHolderPL3();
		}
		initButtons();
		// setCurrentTab(0);
		iCurrentButton = tempCurrentButton;

		createBuyView(iCurrentButton);
		// �л�֮����ʾ������radioButton
		showHighLight();

		mBallHolder = tempBallHolder;
		iWhich = tempCurrentWhich;
		if (publicTopButton == PublicConst.BUY_PL3_ZHIXUAN) {
			create_PL3_ZHIXUAN();
			changeTextSumMoney(0);
		} else if (publicTopButton == PublicConst.BUY_PL3_ZU3) {
			create_PL3_ZU3();
			changeTextSumMoney(0);
		} else if (publicTopButton == PublicConst.BUY_PL3_ZU6) {
			create_PL3_ZU6();
			changeTextSumMoney(0);
		} else if (publicTopButton == PublicConst.BUY_PL3_HEZHI) {
			create_PL3_HEZHI();
		}
		mBallHolder.flag = 0;

	}

	/**
	 * ��������View
	 * 
	 * @param int aWhichBuy ���������淨�ı�ǩId
	 */
	private void createBuyView(int aWhichBuy) {
		iProgressBeishu = 1;
		iProgressQishu = 1;
		publicTopButton = aWhichBuy;
		switch (aWhichBuy) {
		case PublicConst.BUY_PL3_ZHIXUAN:
			create_PL3_ZHIXUAN();
			break;
		case PublicConst.BUY_PL3_ZU3:
			create_PL3_ZU3();
			break;
		case PublicConst.BUY_PL3_ZU6:
			create_PL3_ZU6();
			break;
		case PublicConst.BUY_PL3_HEZHI:
			create_PL3_HEZHI();
			break;
		default:
			break;
		}
	}

	/**
	 * ��������ֵ
	 */
	private void create_PL3_HEZHI() {
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout iV = (RelativeLayout) inflate.inflate(
				R.layout.layout_pl3_hezhi_tab, null);
		{
			PL3_hezhi_zhixuan = (TextView) iV.findViewById(R.id.hezhi_zhixuan);
			PL3_hezhi_zu3 = (TextView) iV.findViewById(R.id.hezhi_zu3);
			PL3_hezhi_zu6 = (TextView) iV.findViewById(R.id.hezhi_zu6);
			PL3_hezhi_zhixuan
					.setOnClickListener(new TextView.OnClickListener() {

						public void onClick(View v) {
							if (mBallHolder.flag != 1) {
								mBallHolder = new BallHolderPL3();
								create_PL3_HEZHI_ZHIXUAN();
								PL3_hezhi_zhixuan
										.setBackgroundDrawable(getResources()
												.getDrawable(
														R.drawable.frame_rectangle_user));
								PL3_hezhi_zu3
										.setBackgroundDrawable(getResources()
												.getDrawable(
														R.drawable.frame_rectangle_user_d));
								PL3_hezhi_zu6
										.setBackgroundDrawable(getResources()
												.getDrawable(
														R.drawable.frame_rectangle_user_d));
							}

						}

					});

			PL3_hezhi_zu3.setOnClickListener(new TextView.OnClickListener() {

				public void onClick(View v) {
					if (mBallHolder.flag != 1) {
						mBallHolder = new BallHolderPL3();
						create_PL3_HEZHI_ZU3();
						PL3_hezhi_zhixuan
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.frame_rectangle_user_d));
						PL3_hezhi_zu3.setBackgroundDrawable(getResources()
								.getDrawable(R.drawable.frame_rectangle_user));
						PL3_hezhi_zu6
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.frame_rectangle_user_d));
					}
				}

			});

			PL3_hezhi_zu6.setOnClickListener(new TextView.OnClickListener() {

				public void onClick(View v) {
					if (mBallHolder.flag != 1) {
						mBallHolder = new BallHolderPL3();
						create_PL3_HEZHI_ZU6();
						PL3_hezhi_zhixuan
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.frame_rectangle_user_d));
						PL3_hezhi_zu3
								.setBackgroundDrawable(getResources()
										.getDrawable(
												R.drawable.frame_rectangle_user_d));
						PL3_hezhi_zu6.setBackgroundDrawable(getResources()
								.getDrawable(R.drawable.frame_rectangle_user));
					}
				}

			});

		}
		// ��View���������
		buyView.addView(iV, new RelativeLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));
		if (mBallHolder != null) {
			if (mBallHolder.flag == 1) {
				if (iWhich == 10) {
					PL3_hezhi_zhixuan.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.frame_rectangle_user));
					PL3_hezhi_zu3.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.frame_rectangle_user_d));
					PL3_hezhi_zu6.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.frame_rectangle_user_d));
					create_PL3_HEZHI_ZHIXUAN();
				}
				if (iWhich == 11) {
					create_PL3_HEZHI_ZU3();
					PL3_hezhi_zhixuan.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.frame_rectangle_user_d));
					PL3_hezhi_zu3.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.frame_rectangle_user));
					PL3_hezhi_zu6.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.frame_rectangle_user_d));
				}
				if (iWhich == 12) {
					create_PL3_HEZHI_ZU6();
					PL3_hezhi_zhixuan.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.frame_rectangle_user_d));
					PL3_hezhi_zu3.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.frame_rectangle_user_d));
					PL3_hezhi_zu6.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.frame_rectangle_user));
				}
			} else {
				PL3_hezhi_zhixuan.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.frame_rectangle_user));
				PL3_hezhi_zu3.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.frame_rectangle_user_d));
				PL3_hezhi_zu6.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.frame_rectangle_user_d));
				create_PL3_HEZHI_ZHIXUAN();
			}
		}
	}

	/**
	 * ��������ֵ��6
	 */
	private void create_PL3_HEZHI_ZU6() {
		iWhich = 12;
		iProgressBeishu = 1;
		iProgressQishu = 1;
		if (buyView.findViewById(R.id.pl3_hezhi_zu3_linearlayout) != null) {
			buyView.removeView(buyView
					.findViewById(R.id.pl3_hezhi_zu3_linearlayout));
		}
		if (buyView.findViewById(R.id.pl3_hezhi_zhixuan_linearlayout) != null) {
			buyView.removeView(buyView
					.findViewById(R.id.pl3_hezhi_zhixuan_linearlayout));
		}
		if (buyView.findViewById(R.id.pl3_hezhi_zu6_linearlayout) == null) {

			LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout iV = (LinearLayout) inflate.inflate(
					R.layout.layout_pl3_hezhi_zu6, null);
			{

				int redBallViewNum = 22;
				int redBallViewWidth = PL3.BALL_WIDTH;
				iScreenWidth = PublicMethod.getDisplayWidth(this);

				PL3HezhiZu6BallTable = makeBallTable(iV, R.id.table_hezhi_zu6,
						iScreenWidth, redBallViewNum, redBallViewWidth,
						PL3BallResId, RED_PL3_HEZHI_ZU6_START, 3);

				hezhiZu6NewSelectbtn = (Button) iV
						.findViewById(R.id.pl3_hezhi_zu6_newselected_btn);
				hezhiZu6NewSelectbtn
						.setOnClickListener(new Button.OnClickListener() {

							@Override
							public void onClick(View v) {
								ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
										PL3.this, 6, PL3.this);
								iChooseNumberDialog.show();
							}

						});

				ImageButton subtractBeishuBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_subtract_hezhi_zu6_beishu);
				subtractBeishuBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarBeishu.setProgress(--iProgressBeishu);
							}

						});

				ImageButton addBeishuBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_add_hezhi_zu6_beishu);
				addBeishuBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarBeishu.setProgress(++iProgressBeishu);
							}

						});

				ImageButton subtractQihaoBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_subtract_hezhi_zu6_qihao);
				subtractQihaoBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarQishu.setProgress(--iProgressQishu);
							}

						});

				ImageButton addQihaoBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_add_hezhi_zu6_qihao);
				addQihaoBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarQishu.setProgress(++iProgressQishu);
							}

						});

				mTextSumMoney = (TextView) iV
						.findViewById(R.id.text_sum_money_hezhi_zu6);
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_number));

				mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.seek_beishu);
				mSeekBarBeishu.setOnSeekBarChangeListener(this);
				mSeekBarBeishu.setProgress(iProgressBeishu);

				mSeekBarQishu = (SeekBar) iV.findViewById(R.id.seek_qishu);
				mSeekBarQishu.setOnSeekBarChangeListener(this);
				mSeekBarQishu.setProgress(iProgressQishu);

				mTextBeishu = (TextView) iV.findViewById(R.id.text_beishu);
				mTextBeishu.setText("" + iProgressBeishu);
				mTextQishu = (TextView) iV.findViewById(R.id.text_qishu);
				mTextQishu.setText("" + iProgressQishu);

				// ImageButton b_touzhu_ddd_zu6hezhi = (ImageButton) iV
				// .findViewById(R.id.b_touzhu_hezhi_zu6);
				// b_touzhu_ddd_zu6hezhi.setOnClickListener(new
				// OnClickListener() {
				// @Override
				// public void onClick(View v) {
				// beginTouZhu();
				// }
				//
				// });
				touzhu = (ImageButton) iV.findViewById(R.id.b_touzhu_hezhi_zu6);
				touzhu.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						beginTouZhu();
					}

				});
			}
			// ��View���������
			buyView
					.addView(iV, new LinearLayout.LayoutParams(buyView
							.getLayoutParams().width,
							buyView.getLayoutParams().height));

			mCheckBox = (CheckBox) this.findViewById(R.id.cb_jixuan_hezhi_zu6);
			mCheckBox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {

							if (isChecked) {
								if (mBallHolder.flag != 1) {
									ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
											PL3.this, 6, PL3.this);
									iChooseNumberDialog.show();
									hezhiZu6NewSelectbtn
											.setVisibility(View.VISIBLE);
								}
							} else {
								hezhiZu6NewSelectbtn
										.setVisibility(View.INVISIBLE);
							}
						}
					});
			// ����Ļ�л���ʱ���ͼ�Ժ󣬰��Ѿ�������С����ʾ����
			if (mBallHolder.flag == 1) {
				PL3HezhiZu6BallTable
						.changeBallStateConfigChange(mBallHolder.HezhiZu6BallGroup.iHezhiZu6BallStatus);
				mSeekBarBeishu
						.setProgress(mBallHolder.HezhiZu6BallGroup.iBeiShu);
				mSeekBarQishu.setProgress(mBallHolder.HezhiZu6BallGroup.iQiShu);
				mCheckBox.setChecked(mBallHolder.HezhiZu6BallGroup.bCheckBox);
				if (mBallHolder.HezhiZu6BallGroup.bCheckBox) {
					hezhiZu6NewSelectbtn.setVisibility(View.VISIBLE);
				} else {
					hezhiZu6NewSelectbtn.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	/**
	 * ��������ֵ��3
	 */
	private void create_PL3_HEZHI_ZU3() {
		iWhich = 11;
		iProgressBeishu = 1;
		iProgressQishu = 1;
		if (buyView.findViewById(R.id.pl3_hezhi_zhixuan_linearlayout) != null) {
			buyView.removeView(buyView
					.findViewById(R.id.pl3_hezhi_zhixuan_linearlayout));
		}
		if (buyView.findViewById(R.id.pl3_hezhi_zu6_linearlayout) != null) {
			buyView.removeView(buyView
					.findViewById(R.id.pl3_hezhi_zu6_linearlayout));
		}
		if (buyView.findViewById(R.id.pl3_hezhi_zu3_linearlayout) == null) {

			LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout iV = (LinearLayout) inflate.inflate(
					R.layout.layout_pl3_hezhi_zu3, null);
			{

				int redBallViewNum = 26;
				int redBallViewWidth = PL3.BALL_WIDTH;
				iScreenWidth = PublicMethod.getDisplayWidth(this);

				PL3HezhiZu3BallTable = makeBallTable(iV, R.id.table_hezhi_zu3,
						iScreenWidth, redBallViewNum, redBallViewWidth,
						PL3BallResId, RED_PL3_HEZHI_ZU3_START, 1);

				hezhiZu3NewSelectbtn = (Button) iV
						.findViewById(R.id.pl3_hezhi_zu3_newselected_btn);
				hezhiZu3NewSelectbtn
						.setOnClickListener(new Button.OnClickListener() {

							@Override
							public void onClick(View v) {
								ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
										PL3.this, 5, PL3.this);
								iChooseNumberDialog.show();
							}

						});

				ImageButton subtractBeishuBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_subtract_hezhi_zu3_beishu);
				subtractBeishuBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarBeishu.setProgress(--iProgressBeishu);
							}

						});

				ImageButton addBeishuBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_add_hezhi_zu3_beishu);
				addBeishuBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarBeishu.setProgress(++iProgressBeishu);
							}

						});

				ImageButton subtractQihaoBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_subtract_hezhi_zu3_qihao);
				subtractQihaoBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarQishu.setProgress(--iProgressQishu);
							}

						});

				ImageButton addQihaoBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_add_hezhi_zu3_qihao);
				addQihaoBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarQishu.setProgress(++iProgressQishu);
							}

						});

				mTextSumMoney = (TextView) iV
						.findViewById(R.id.text_sum_money_hezhi_zu3);
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_number));

				mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.seek_beishu);
				mSeekBarBeishu.setOnSeekBarChangeListener(this);
				mSeekBarBeishu.setProgress(iProgressBeishu);

				mSeekBarQishu = (SeekBar) iV.findViewById(R.id.seek_qishu);
				mSeekBarQishu.setOnSeekBarChangeListener(this);
				mSeekBarQishu.setProgress(iProgressQishu);

				mTextBeishu = (TextView) iV.findViewById(R.id.text_beishu);
				mTextBeishu.setText("" + iProgressBeishu);
				mTextQishu = (TextView) iV.findViewById(R.id.text_qishu);
				mTextQishu.setText("" + iProgressQishu);

				// ImageButton b_touzhu_ddd_hezhizu3 = (ImageButton) iV
				// .findViewById(R.id.b_touzhu_hezhi_zu3);
				// b_touzhu_ddd_hezhizu3.setOnClickListener(new
				// OnClickListener() {
				// @Override
				// public void onClick(View v) {
				// beginTouZhu();
				// }
				//
				// });
				touzhu = (ImageButton) iV.findViewById(R.id.b_touzhu_hezhi_zu3);
				touzhu.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						beginTouZhu();
					}

				});
			}
			// ��View���������
			buyView
					.addView(iV, new LinearLayout.LayoutParams(buyView
							.getLayoutParams().width,
							buyView.getLayoutParams().height));

			mCheckBox = (CheckBox) this.findViewById(R.id.cb_jixuan_hezhi_zu3);
			mCheckBox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								if (mBallHolder.flag != 1) {
									ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
											PL3.this, 5, PL3.this);
									iChooseNumberDialog.show();
									hezhiZu3NewSelectbtn
											.setVisibility(View.VISIBLE);
								}
							} else {
								hezhiZu3NewSelectbtn
										.setVisibility(View.INVISIBLE);
							}
						}
					});
			// ����Ļ�л���ʱ���ͼ�Ժ󣬰��Ѿ�������С����ʾ����
			if (mBallHolder.flag == 1) {
				PL3HezhiZu3BallTable
						.changeBallStateConfigChange(mBallHolder.HezhiZu3BallGroup.iHezhiZu3BallStatus);
				mSeekBarBeishu
						.setProgress(mBallHolder.HezhiZu3BallGroup.iBeiShu);
				mSeekBarQishu.setProgress(mBallHolder.HezhiZu3BallGroup.iQiShu);
				mCheckBox.setChecked(mBallHolder.HezhiZu3BallGroup.bCheckBox);
				if (mBallHolder.HezhiZu3BallGroup.bCheckBox) {
					hezhiZu3NewSelectbtn.setVisibility(View.VISIBLE);
				} else {
					hezhiZu3NewSelectbtn.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	/**
	 * ��������ֱֵѡ
	 */
	private void create_PL3_HEZHI_ZHIXUAN() {
		iWhich = 10;
		iProgressBeishu = 1;
		iProgressQishu = 1;
		if (buyView.findViewById(R.id.pl3_hezhi_zu3_linearlayout) != null) {
			buyView.removeView(buyView
					.findViewById(R.id.pl3_hezhi_zu3_linearlayout));
		}
		if (buyView.findViewById(R.id.pl3_hezhi_zu6_linearlayout) != null) {
			buyView.removeView(buyView
					.findViewById(R.id.pl3_hezhi_zu6_linearlayout));
		}
		if (buyView.findViewById(R.id.pl3_hezhi_zhixuan_linearlayout) == null) {

			LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout iV = (LinearLayout) inflate.inflate(
					R.layout.layout_pl3_hezhi_zhixuan, null);
			{

				int redBallViewNum = 28;
				int redBallViewWidth = PL3.BALL_WIDTH;
				iScreenWidth = PublicMethod.getDisplayWidth(this);

				PL3HezhiZhixuanBallTable = makeBallTable(iV,
						R.id.table_hezhi_zhixuan, iScreenWidth, redBallViewNum,
						redBallViewWidth, PL3BallResId,
						RED_PL3_HEZHI_ZHIXUAN_START, 0);

				hezhiZhixuanNewSelectbtn = (Button) iV
						.findViewById(R.id.pl3_hezhi_zhixuan_newselected_btn);
				hezhiZhixuanNewSelectbtn
						.setOnClickListener(new Button.OnClickListener() {

							@Override
							public void onClick(View v) {
								ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
										PL3.this, 4, PL3.this);
								iChooseNumberDialog.show();
							}

						});

				ImageButton subtractBeishuBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_subtract_hezhi_zhixuan_beishu);
				subtractBeishuBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarBeishu.setProgress(--iProgressBeishu);
							}

						});

				ImageButton addBeishuBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_add_hezhi_zhixuan_beishu);
				addBeishuBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarBeishu.setProgress(++iProgressBeishu);
							}

						});

				ImageButton subtractQihaoBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_subtract_hezhi_zhixuan_qihao);
				subtractQihaoBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarQishu.setProgress(--iProgressQishu);
							}

						});

				ImageButton addQihaoBtn = (ImageButton) iV
						.findViewById(R.id.pl3_seekbar_add_hezhi_zhixuan_qihao);
				addQihaoBtn
						.setOnClickListener(new ImageButton.OnClickListener() {

							@Override
							public void onClick(View v) {
								mSeekBarQishu.setProgress(++iProgressQishu);
							}

						});

				mTextSumMoney = (TextView) iV
						.findViewById(R.id.text_sum_money_hezhi_zhixuan);
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_number));

				mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.seek_beishu);
				mSeekBarBeishu.setOnSeekBarChangeListener(this);
				mSeekBarBeishu.setProgress(iProgressBeishu);

				mSeekBarQishu = (SeekBar) iV.findViewById(R.id.seek_qishu);
				mSeekBarQishu.setOnSeekBarChangeListener(this);
				mSeekBarQishu.setProgress(iProgressQishu);

				mTextBeishu = (TextView) iV.findViewById(R.id.text_beishu);
				mTextBeishu.setText("" + iProgressBeishu);
				mTextQishu = (TextView) iV.findViewById(R.id.text_qishu);
				mTextQishu.setText("" + iProgressQishu);
				// ImageButton b_touzhu_ddd_zhixuanhezhi = (ImageButton) iV
				// .findViewById(R.id.b_touzhu_hezhi_zhixuan);
				// b_touzhu_ddd_zhixuanhezhi
				// .setOnClickListener(new OnClickListener() {
				// @Override
				// public void onClick(View v) {
				// beginTouZhu();
				// }
				//
				// });
				touzhu = (ImageButton) iV
						.findViewById(R.id.b_touzhu_hezhi_zhixuan);
				touzhu.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						beginTouZhu();
					}

				});

			}
			// ��View���������
			buyView
					.addView(iV, new LinearLayout.LayoutParams(buyView
							.getLayoutParams().width,
							buyView.getLayoutParams().height));

			mCheckBox = (CheckBox) this
					.findViewById(R.id.cb_jixuan_hezhi_zhixuan);
			mCheckBox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								if (mBallHolder.flag != 1) {
									ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
											PL3.this, 4, PL3.this);
									iChooseNumberDialog.show();
									hezhiZhixuanNewSelectbtn
											.setVisibility(View.VISIBLE);
								}
							} else {
								hezhiZhixuanNewSelectbtn
										.setVisibility(View.INVISIBLE);
							}
						}
					});
			// ����Ļ�л���ʱ���ͼ�Ժ󣬰��Ѿ�������С����ʾ����
			if (mBallHolder.flag == 1) {
				PL3HezhiZhixuanBallTable
						.changeBallStateConfigChange(mBallHolder.HezhiZhixuanBallGroup.iHezhiZhixuanBallStatus);
				mSeekBarBeishu
						.setProgress(mBallHolder.HezhiZhixuanBallGroup.iBeiShu);
				mSeekBarQishu
						.setProgress(mBallHolder.HezhiZhixuanBallGroup.iQiShu);
				mCheckBox
						.setChecked(mBallHolder.HezhiZhixuanBallGroup.bCheckBox);
				if (mBallHolder.HezhiZhixuanBallGroup.bCheckBox) {
					hezhiZhixuanNewSelectbtn.setVisibility(View.VISIBLE);
				} else {
					hezhiZhixuanNewSelectbtn.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	/**
	 * ������ֱѡ
	 */
	private void create_PL3_ZHIXUAN() {
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_pl3_zhixuan, null);
		{
			int redBallViewNum = 10;
			int redBallViewWidth = PL3.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			PL3ZhixuanBaiweiBallTable = makeBallTable(iV,
					R.id.table_zhixuan_baiwei, iScreenWidth, redBallViewNum,
					redBallViewWidth, PL3BallResId,
					RED_PL3_ZHIXUAN_BAIWEI_START, 0);
			PL3ZhixuanShiweiBallTable = makeBallTable(iV,
					R.id.table_zhixuan_shiwei, iScreenWidth, redBallViewNum,
					redBallViewWidth, PL3BallResId,
					RED_PL3_ZHIXUAN_SHIWEI_START, 0);
			PL3ZhixuanGeweiBallTable = makeBallTable(iV,
					R.id.table_zhixuan_gewei, iScreenWidth, redBallViewNum,
					redBallViewWidth, PL3BallResId,
					RED_PL3_ZHIXUAN_GEWEI_START, 0);

			mTextSumMoney = (TextView) iV
					.findViewById(R.id.text_sum_money_zhixuan);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));

			ImageButton subtractBeishuBtn = (ImageButton) iV
					.findViewById(R.id.pl3_seekbar_subtract_zhixuan_beishu);
			subtractBeishuBtn
					.setOnClickListener(new ImageButton.OnClickListener() {

						@Override
						public void onClick(View v) {
							mSeekBarBeishu.setProgress(--iProgressBeishu);
						}

					});

			ImageButton addBeishuBtn = (ImageButton) iV
					.findViewById(R.id.pl3_seekbar_add_zhixuan_beishu);
			addBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

				@Override
				public void onClick(View v) {
					mSeekBarBeishu.setProgress(++iProgressBeishu);
				}

			});

			ImageButton subtractQihaoBtn = (ImageButton) iV
					.findViewById(R.id.pl3_seekbar_subtract_zhixuan_qihao);
			subtractQihaoBtn
					.setOnClickListener(new ImageButton.OnClickListener() {

						@Override
						public void onClick(View v) {
							mSeekBarQishu.setProgress(--iProgressQishu);
						}

					});

			ImageButton addQihaoBtn = (ImageButton) iV
					.findViewById(R.id.pl3_seekbar_add_zhixuan_qihao);
			addQihaoBtn.setOnClickListener(new ImageButton.OnClickListener() {

				@Override
				public void onClick(View v) {
					mSeekBarQishu.setProgress(++iProgressQishu);
				}

			});

			mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) iV.findViewById(R.id.seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) iV.findViewById(R.id.text_beishu);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) iV.findViewById(R.id.text_qishu);
			mTextQishu.setText("" + iProgressQishu);

			zhixuanNewSelectbtn = (Button) iV
					.findViewById(R.id.pl3_zhixuan_newselected_btn);
			zhixuanNewSelectbtn
					.setOnClickListener(new Button.OnClickListener() {

						@Override
						public void onClick(View v) {
							ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
									PL3.this, 1, PL3.this);
							iChooseNumberDialog.show();
						}

					});

			// ImageButton b_touzhu_ddd_zhixuan = (ImageButton) iV
			// .findViewById(R.id.b_touzhu_zhixuan);
			// b_touzhu_ddd_zhixuan
			// .setOnClickListener(new ImageButton.OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// // ������ 7.8 ��menu
			// beginTouZhu();
			// }
			//
			// });
			touzhu = (ImageButton) iV.findViewById(R.id.b_touzhu_zhixuan);
			touzhu.setOnClickListener(new ImageButton.OnClickListener() {
				@Override
				public void onClick(View v) {
					// ������ 7.8 ��menu
					beginTouZhu();
				}

			});
		}
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));

		// ��ѡ
		mCheckBox = (CheckBox) this.findViewById(R.id.cb_jixuan_zhixuan);
		mCheckBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {
							if (mBallHolder.flag != 1) {
								ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
										PL3.this, 1, PL3.this);
								iChooseNumberDialog.show();
								zhixuanNewSelectbtn.setVisibility(View.VISIBLE);
							}
						} else {
							zhixuanNewSelectbtn.setVisibility(View.INVISIBLE);
						}
					}
				});

		// ����Ļ�л���ʱ���ͼ�Ժ󣬰��Ѿ�������С����ʾ����
		if (mBallHolder.flag == 1) {
			PL3ZhixuanBaiweiBallTable
					.changeBallStateConfigChange(mBallHolder.ZhixuanBallGroup.iZhixuanBaiweiBallStatus);
			PL3ZhixuanShiweiBallTable
					.changeBallStateConfigChange(mBallHolder.ZhixuanBallGroup.iZhixuanShiweiBallStatus);
			PL3ZhixuanGeweiBallTable
					.changeBallStateConfigChange(mBallHolder.ZhixuanBallGroup.iZhixuanGeweiBallStatus);
			mSeekBarBeishu.setProgress(mBallHolder.ZhixuanBallGroup.iBeiShu);
			mSeekBarQishu.setProgress(mBallHolder.ZhixuanBallGroup.iQiShu);
			mCheckBox.setChecked(mBallHolder.ZhixuanBallGroup.bCheckBox);
			if (mBallHolder.ZhixuanBallGroup.bCheckBox) {
				zhixuanNewSelectbtn.setVisibility(View.VISIBLE);
			} else {
				zhixuanNewSelectbtn.setVisibility(View.INVISIBLE);
			}
		}
	}

	/**
	 * ��������6
	 */
	private void create_PL3_ZU6() {
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout iV = (LinearLayout) inflate.inflate(
				R.layout.layout_pl3_zu6, null);
		{
			int redBallViewNum = 10;
			int redBallViewWidth = PL3.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			PL3Zu6BallTable = makeBallTable(iV, R.id.table_zu6, iScreenWidth,
					redBallViewNum, redBallViewWidth, PL3BallResId,
					RED_PL3_ZU6_START, 0);

			zu6NewSelectbtn = (Button) iV
					.findViewById(R.id.pl3_zu6_newselected_btn);
			zu6NewSelectbtn.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
							PL3.this, 3, PL3.this);
					iChooseNumberDialog.show();
				}

			});

			ImageButton subtractBeishuBtn = (ImageButton) iV
					.findViewById(R.id.pl3_seekbar_subtract_zu6_beishu);
			subtractBeishuBtn
					.setOnClickListener(new ImageButton.OnClickListener() {

						@Override
						public void onClick(View v) {
							mSeekBarBeishu.setProgress(--iProgressBeishu);
						}

					});

			ImageButton addBeishuBtn = (ImageButton) iV
					.findViewById(R.id.pl3_seekbar_add_zu6_beishu);
			addBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

				@Override
				public void onClick(View v) {
					mSeekBarBeishu.setProgress(++iProgressBeishu);
				}

			});

			ImageButton subtractQihaoBtn = (ImageButton) iV
					.findViewById(R.id.pl3_seekbar_subtract_zu6_qihao);
			subtractQihaoBtn
					.setOnClickListener(new ImageButton.OnClickListener() {

						@Override
						public void onClick(View v) {
							mSeekBarQishu.setProgress(--iProgressQishu);
						}

					});

			ImageButton addQihaoBtn = (ImageButton) iV
					.findViewById(R.id.pl3_seekbar_add_zu6_qihao);
			addQihaoBtn.setOnClickListener(new ImageButton.OnClickListener() {

				@Override
				public void onClick(View v) {
					mSeekBarQishu.setProgress(++iProgressQishu);
				}

			});

			mTextSumMoney = (TextView) iV.findViewById(R.id.text_sum_money_zu6);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));

			mSeekBarBeishu = (SeekBar) iV.findViewById(R.id.seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) iV.findViewById(R.id.seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) iV.findViewById(R.id.text_beishu);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) iV.findViewById(R.id.text_qishu);
			mTextQishu.setText("" + iProgressQishu);
			// ImageButton b_ddd_zu6_touzhu = (ImageButton) iV
			// .findViewById(R.id.b_touzhu_zu6);
			// b_ddd_zu6_touzhu.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// beginTouZhu();
			// }
			//
			// });
			touzhu = (ImageButton) iV.findViewById(R.id.b_touzhu_zu6);
			touzhu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					beginTouZhu();
				}

			});
		}
		// ��View���������
		buyView.addView(iV, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));

		mCheckBox = (CheckBox) this.findViewById(R.id.cb_jixuan_zu6);
		mCheckBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							if (mBallHolder.flag != 1) {
								ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
										PL3.this, 3, PL3.this);
								iChooseNumberDialog.show();
								zu6NewSelectbtn.setVisibility(View.VISIBLE);
							}
						} else {
							zu6NewSelectbtn.setVisibility(View.INVISIBLE);
						}
					}
				});

		// ����Ļ�л���ʱ���ͼ�Ժ󣬰��Ѿ�������С����ʾ����
		if (mBallHolder.flag == 1) {
			PL3Zu6BallTable
					.changeBallStateConfigChange(mBallHolder.Zu6BallGroup.iZu6BallStatus);
			mSeekBarBeishu.setProgress(mBallHolder.Zu6BallGroup.iBeiShu);
			mSeekBarQishu.setProgress(mBallHolder.Zu6BallGroup.iQiShu);
			mCheckBox.setChecked(mBallHolder.Zu6BallGroup.bCheckBox);
			if (mBallHolder.Zu6BallGroup.bCheckBox) {
				zu6NewSelectbtn.setVisibility(View.VISIBLE);
			} else {
				zu6NewSelectbtn.setVisibility(View.INVISIBLE);
			}
		}
	}

	/**
	 * ��������3
	 */
	private void create_PL3_ZU3() {
		// ������ 7.6 ���Ͷע��ť����ʾ��������ȷ
		bZu3Danshi = true;
		bZu3Fushi = false;
		buyView.removeAllViews();
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		linearLayout = (LinearLayout) inflate.inflate(R.layout.layout_pl3_zu3,
				null);
		{
			redBallViewNum = 10;
			int redBallViewWidth = PL3.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			PL3A1Zu3DanshiBallTable = makeBallTable(linearLayout,
					R.id.table_zu3_danshi_baiwei, iScreenWidth, redBallViewNum,
					redBallViewWidth, PL3BallResId,
					RED_PL3_ZU3_DANSHI_BAIWEI_START, 0);
			PL3A2Zu3DanshiBallTable = makeBallTable(linearLayout,
					R.id.table_zu3_danshi_shiwei, iScreenWidth, redBallViewNum,
					redBallViewWidth, PL3BallResId,
					RED_PL3_ZU3_DANSHI_SHIWEI_START, 0);
			PL3BZu3DanshiBallTable = makeBallTable(linearLayout,
					R.id.table_zu3_danshi_gewei, iScreenWidth, redBallViewNum,
					redBallViewWidth, PL3BallResId,
					RED_PL3_ZU3_DANSHI_GEWEI_START, 0);
			PL3Zu3FushiBallTable = makeBallTable(linearLayout,
					R.id.table_zu3_fushi, iScreenWidth, redBallViewNum,
					redBallViewWidth, PL3BallResId, RED_PL3_ZU3_FUSHI_START, 0);
			// Ĭ���ǵ�ʽ�����Ը�ʽ������
			for (int i = 0; i < redBallViewNum; i++) {
				PL3Zu3FushiBallTable.ballViewVector.elementAt(i).setGrey();
				PL3Zu3FushiBallTable.ballViewVector.elementAt(i).setEnabled(
						false);
			}
			radiogroup = (RadioGroup) linearLayout
					.findViewById(R.id.radiogroupid);
			danshirbtn = (RadioButton) linearLayout
					.findViewById(R.id.radio_zu3_danshi);
			fushirbtn = (RadioButton) linearLayout
					.findViewById(R.id.radio_zu3_fushi);

			radiogroup
					.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							if (checkedId == danshirbtn.getId()) {
								if (mBallHolder.flag != 1) {
									mBallHolder = new BallHolderPL3();
									bZu3Danshi = true;
									bZu3Fushi = false;
									for (int i = 0; i < redBallViewNum; i++) {
										mTextSumMoney
												.setText(getResources()
														.getString(
																R.string.please_choose_number));
										PL3A1Zu3DanshiBallTable.ballViewVector
												.elementAt(i).setEnabled(true);
										PL3A2Zu3DanshiBallTable.ballViewVector
												.elementAt(i).setEnabled(true);
										PL3BZu3DanshiBallTable.ballViewVector
												.elementAt(i).setEnabled(true);
										PL3Zu3FushiBallTable.ballViewVector
												.elementAt(i).setEnabled(false);
										PL3Zu3FushiBallTable.ballViewVector
												.elementAt(i).setGrey();
									}
								}
							}
							if (checkedId == fushirbtn.getId()) {
								if (mBallHolder.flag != 1) {
									mBallHolder = new BallHolderPL3();
									bZu3Danshi = false;
									bZu3Fushi = true;
									for (int i = 0; i < redBallViewNum; i++) {
										mTextSumMoney
												.setText(getResources()
														.getString(
																R.string.please_choose_number));
										PL3A1Zu3DanshiBallTable.ballViewVector
												.elementAt(i).setEnabled(false);
										PL3A2Zu3DanshiBallTable.ballViewVector
												.elementAt(i).setEnabled(false);
										PL3BZu3DanshiBallTable.ballViewVector
												.elementAt(i).setEnabled(false);
										PL3A1Zu3DanshiBallTable.ballViewVector
												.elementAt(i).setGrey();
										PL3A2Zu3DanshiBallTable.ballViewVector
												.elementAt(i).setGrey();
										PL3BZu3DanshiBallTable.ballViewVector
												.elementAt(i).setGrey();
										PL3Zu3FushiBallTable.ballViewVector
												.elementAt(i).setEnabled(true);
									}
								}
							}
						}

					});

			ImageButton subtractBeishuBtn = (ImageButton) linearLayout
					.findViewById(R.id.pl3_seekbar_subtract_zu3_beishu);
			subtractBeishuBtn
					.setOnClickListener(new ImageButton.OnClickListener() {

						@Override
						public void onClick(View v) {
							mSeekBarBeishu.setProgress(--iProgressBeishu);
						}

					});

			ImageButton addBeishuBtn = (ImageButton) linearLayout
					.findViewById(R.id.pl3_seekbar_add_zu3_beishu);
			addBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {

				@Override
				public void onClick(View v) {
					mSeekBarBeishu.setProgress(++iProgressBeishu);
				}

			});

			ImageButton subtractQihaoBtn = (ImageButton) linearLayout
					.findViewById(R.id.pl3_seekbar_subtract_zu3_qihao);
			subtractQihaoBtn
					.setOnClickListener(new ImageButton.OnClickListener() {

						@Override
						public void onClick(View v) {
							mSeekBarQishu.setProgress(--iProgressQishu);
						}

					});

			ImageButton addQihaoBtn = (ImageButton) linearLayout
					.findViewById(R.id.pl3_seekbar_add_zu3_qihao);
			addQihaoBtn.setOnClickListener(new ImageButton.OnClickListener() {

				@Override
				public void onClick(View v) {
					mSeekBarQishu.setProgress(++iProgressQishu);
				}

			});

			mTextSumMoney = (TextView) linearLayout
					.findViewById(R.id.text_sum_money_zu3);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));

			mSeekBarBeishu = (SeekBar) linearLayout
					.findViewById(R.id.seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mSeekBarQishu = (SeekBar) linearLayout
					.findViewById(R.id.seek_qishu);
			mSeekBarQishu.setOnSeekBarChangeListener(this);
			mSeekBarQishu.setProgress(iProgressQishu);

			mTextBeishu = (TextView) linearLayout
					.findViewById(R.id.text_beishu);
			mTextBeishu.setText("" + iProgressBeishu);
			mTextQishu = (TextView) linearLayout.findViewById(R.id.text_qishu);
			mTextQishu.setText("" + iProgressQishu);

			zu3NewSelectbtn = (Button) linearLayout
					.findViewById(R.id.pl3_zu3_newselected_btn);
			zu3NewSelectbtn.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {

					if (bZu3Danshi) {
						if (mBallHolder.flag != 1) {
							mBallHolder = new BallHolderPL3();
							PL3A1Zu3DanshiBallTable.clearAllHighlights();
							PL3A2Zu3DanshiBallTable.clearAllHighlights();
							PL3BZu3DanshiBallTable.clearAllHighlights();

							// ǰ��λ��ͬ������λ��ͬ ��ȡ������ŵ�randomChooseConfigChangePL3
							int[] randomNums = PublicMethod
									.getRandomsWithoutCollision(2, 0, 9);// ���2λ��0~9�������
							mBallHolder = PL3A1Zu3DanshiBallTable
									.randomChooseConfigChangePL3(1,
											mBallHolder, 0, randomNums);
							mBallHolder = PL3A2Zu3DanshiBallTable
									.randomChooseConfigChangePL3(1,
											mBallHolder, 1, randomNums);
							mBallHolder = PL3BZu3DanshiBallTable
									.randomChooseConfigChangePL3(1,
											mBallHolder, 2, randomNums);
							changeTextSumMoney(0);
						}
					}

					if (bZu3Fushi) {
						if (mBallHolder.flag != 1) {
							ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
									PL3.this, 2, PL3.this);
							iChooseNumberDialog.show();
						}
					}

				}

			});

			// ImageButton b_ddd_zu3 = (ImageButton) linearLayout
			// .findViewById(R.id.b_touzhu_zu3);
			// b_ddd_zu3.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// beginTouZhu();
			// }
			//
			// });
			touzhu = (ImageButton) linearLayout.findViewById(R.id.b_touzhu_zu3);
			touzhu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					beginTouZhu();
				}

			});
		}
		// ��View���������
		buyView.addView(linearLayout, new LinearLayout.LayoutParams(buyView
				.getLayoutParams().width, buyView.getLayoutParams().height));

		mCheckBox = (CheckBox) this.findViewById(R.id.cb_jixuan_zu3);
		mCheckBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							if (bZu3Danshi) {
								if (mBallHolder.flag != 1) {
									mBallHolder = new BallHolderPL3();
									PL3A1Zu3DanshiBallTable
											.clearAllHighlights();
									PL3A2Zu3DanshiBallTable
											.clearAllHighlights();
									PL3BZu3DanshiBallTable.clearAllHighlights();

									// ǰ��λ��ͬ������λ��ͬ
									// ��ȡ������ŵ�randomChooseConfigChangePL3
									int[] randomNums = PublicMethod
											.getRandomsWithoutCollision(2, 0, 9);// ���2λ��0~9�������
									mBallHolder = PL3A1Zu3DanshiBallTable
											.randomChooseConfigChangePL3(1,
													mBallHolder, 0, randomNums);
									mBallHolder = PL3A2Zu3DanshiBallTable
											.randomChooseConfigChangePL3(1,
													mBallHolder, 1, randomNums);
									mBallHolder = PL3BZu3DanshiBallTable
											.randomChooseConfigChangePL3(1,
													mBallHolder, 2, randomNums);
									changeTextSumMoney(0);
								}
							}
							if (bZu3Fushi) {
								if (mBallHolder.flag != 1) {
									ChooseNumberDialogPL3 iChooseNumberDialog = new ChooseNumberDialogPL3(
											PL3.this, 2, PL3.this);
									iChooseNumberDialog.show();
								}
							}
							zu3NewSelectbtn.setVisibility(View.VISIBLE);
						} else {
							zu3NewSelectbtn.setVisibility(View.INVISIBLE);
						}
					}
				});

		// ����Ļ�л���ʱ���ͼ�Ժ󣬰��Ѿ�������С����ʾ����
		if (mBallHolder.flag == 1) {
			danshirbtn.setChecked(mBallHolder.Zu3BallGroup.bRadioBtnDanshi);
			fushirbtn.setChecked(mBallHolder.Zu3BallGroup.bRadioBtnFushi);
			if (mBallHolder.Zu3BallGroup.bRadioBtnDanshi) {
				PL3A1Zu3DanshiBallTable
						.changeBallStateConfigChange(mBallHolder.Zu3BallGroup.iZu3A1BallStatus);
				PL3A2Zu3DanshiBallTable
						.changeBallStateConfigChange(mBallHolder.Zu3BallGroup.iZu3A2BallStatus);
				PL3BZu3DanshiBallTable
						.changeBallStateConfigChange(mBallHolder.Zu3BallGroup.iZu3BBallStatus);
				PL3Zu3FushiBallTable.clearAllHighlights();
			}
			if (mBallHolder.Zu3BallGroup.bRadioBtnFushi) {
				PL3Zu3FushiBallTable
						.changeBallStateConfigChange(mBallHolder.Zu3BallGroup.iZu3FushiBallStatus);
				PL3A1Zu3DanshiBallTable.clearAllHighlights();
				PL3A2Zu3DanshiBallTable.clearAllHighlights();
				PL3BZu3DanshiBallTable.clearAllHighlights();
			}

			mSeekBarBeishu.setProgress(mBallHolder.Zu3BallGroup.iBeiShu);
			mSeekBarQishu.setProgress(mBallHolder.Zu3BallGroup.iQiShu);
			mCheckBox.setChecked(mBallHolder.Zu3BallGroup.bCheckBox);
			if (mBallHolder.Zu3BallGroup.bCheckBox) {
				zu3NewSelectbtn.setVisibility(View.VISIBLE);
			} else {
				zu3NewSelectbtn.setVisibility(View.INVISIBLE);
			}

		}
	}

	/**
	 * ��ʼ��buttons
	 * 
	 * @param void
	 */
	private void initButtons() {
		if (mBallHolder != null)
			if (mBallHolder.flag == 1)
				initTopButtons();
		if (mBallHolder == null)
			initTopButtons();
		commit(iButtonNum, topButtonIdOn, topButtonIdOff);
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

	/**
	 * RadioGroup�ύ����
	 * 
	 * @param void
	 */
	public void commit(int iButtonNum, int[] topButtonIdOn, int[] topButtonOff) {
		topButtonGroup.removeAllViews();
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
			// tabButton.setState(getResources().getString(topButtonStringId[i]));
			tabButton.setState(topButtonIdOn[i], topButtonOff[i]);
			tabButton.setId(i);
			tabButton.setGravity(Gravity.CENTER);
			topButtonGroup.addView(tabButton, i, topButtonLayoutParams);
		}

		if (getLastNonConfigurationInstance() != null) {
			mBallHolder = (BallHolderPL3) getLastNonConfigurationInstance();
			int buttonGroup = mBallHolder.topButtonGroup;
			setCurrentTab(buttonGroup);
		} else {
			mBallHolder = new BallHolderPL3();
			// Ĭ�ϵ�ǰ��ǩ
			setCurrentTab(0);
		}
	}

	/**
	 * ���õ�ǰ��ǩ
	 * 
	 * @param index
	 *            ��ǰ��ǩ��Id
	 */
	public void setCurrentTab(int index) {
		// topButtonGroup.check(index);
		// switch (index) {
		// case 0:
		// iCurrentButton = PublicConst.BUY_PL3_ZHIXUAN;
		// createBuyView(iCurrentButton);
		// break;
		// case 1:
		// iCurrentButton = PublicConst.BUY_PL3_ZU3;
		// createBuyView(iCurrentButton);
		// break;
		// case 2:
		// iCurrentButton = PublicConst.BUY_PL3_ZU6;
		// createBuyView(iCurrentButton);
		// break;
		// case 3:
		// iCurrentButton = PublicConst.BUY_PL3_HEZHI;
		// createBuyView(iCurrentButton);
		// break;
		// }
		if (type == ZHIXUAN) {
			iCurrentButton = PublicConst.BUY_PL3_ZHIXUAN;
			createBuyView(iCurrentButton);
		} else if (type == ZUXUAN) {
			topButtonGroup.check(1);// ��3
			iCurrentButton = PublicConst.BUY_PL3_ZU6;
			createBuyView(iCurrentButton);
		} else if (type == HEZHI) {// ��ֵ-ֱѡ
			topButtonGroup.check(index);
			// create_FC3D_HEZHI_ZHIXUAN();
			iCurrentButton = PublicConst.BUY_PL3_HEZHI;
			createBuyView(iCurrentButton);
		}
	}

	/**
	 * ��дRadioGroup��������onCheckedChanged
	 * 
	 * @param RadioGroup
	 *            RadioGroup
	 * @param int checkedId ��ǰ��ѡ���RadioId
	 */
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		mBallHolder = new BallHolderPL3();
		if (type == ZHIXUAN) {
			switch (checkedId) {
			// ��ѡ
			case 0:
				type = ZUXUAN;
				// ----- ��ʼ��������ť
				initTopButtons();
				commit(2, zuTopButtonIdOn, zuTopButtonIdOff);

				break;
			// ��ֵ
			case 1:
				type = HEZHI;
				// ----- ��ʼ��������ť
				commit(3, heTopButtonIdOn, heTopButtonIdOff);
				break;
			}
		} else if (type == ZUXUAN) {
			switch (checkedId) {
			// ����
			case 0:
				iCurrentButton = PublicConst.BUY_PL3_ZU3;
				createBuyView(iCurrentButton);
				break;
			// ����
			case 1:
				iCurrentButton = PublicConst.BUY_PL3_ZU6;
				createBuyView(iCurrentButton);
				break;
			}
		} else if (type == HEZHI) {
			switch (checkedId) {
			// ��ֵ-ֱѡ
			case 0:
				create_PL3_HEZHI_ZHIXUAN();
				break;
			// ��ֵ-��3
			case 1:
				create_PL3_HEZHI_ZU3();
				break;
			// ��ֵ-��6
			case 2:
				create_PL3_HEZHI_ZU6();
				break;
			}
		}
		// switch (checkedId) {
		// // ֱѡ
		// case 0:
		// iCurrentButton = PublicConst.BUY_PL3_ZHIXUAN;
		// createBuyView(iCurrentButton);
		// break;
		// // ��3
		// case 1:
		// iCurrentButton = PublicConst.BUY_PL3_ZU3;
		// createBuyView(iCurrentButton);
		// break;
		// // ��6
		// case 2:
		// iCurrentButton = PublicConst.BUY_PL3_ZU6;
		// createBuyView(iCurrentButton);
		// break;
		// // ��ֵ
		// case 3:
		// iCurrentButton = PublicConst.BUY_PL3_HEZHI;
		// createBuyView(iCurrentButton);
		// break;
		// }
		mHScrollView.fullScroll(ScrollView.FOCUS_UP);
	}

	public int getCurrentTab() {
		return topButtonGroup.getCheckedRadioButtonId();
	}

	/**
	 * ��дonProgressChanged ͨ���ı�SeekBar��progress��������ʵʱ�ı�ע��
	 */
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (progress < 1)
			seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();

		switch (seekBar.getId()) {
		case R.id.seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			if (iWhich == 0) {
				changeTextSumMoney(0);
			} else if (iWhich == 10) {
				changeTextSumMoney(10);
			} else if (iWhich == 11) {
				changeTextSumMoney(11);
			} else if (iWhich == 12) {
				changeTextSumMoney(12);
			}

			break;
		case R.id.seek_qishu:
			iProgressQishu = iProgress;
			mTextQishu.setText("" + iProgressQishu);
			break;
		default:
			break;
		}
	}

	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	/**
	 * ��ӦС�򱻵���Ļص�����
	 * 
	 * @param View
	 *            v �������view
	 * @return void
	 */
	public void onClick(View v) {
		if (iCurrentButton == PublicConst.BUY_PL3_HEZHI) {
			// ��������ֱֵѡ
			if (v.getId() < RED_PL3_HEZHI_ZU3_START
					&& v.getId() >= RED_PL3_HEZHI_ZHIXUAN_START) {
				int iBallViewId = v.getId() - RED_PL3_HEZHI_ZHIXUAN_START;
				if (iBallViewId < 0) {
					return;
				} else {
					changeBuyViewByRule(iCurrentButton, 10, iBallViewId);
				}
				if (PL3HezhiZhixuanBallTable.getHighlightBallNums() == 0) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_number));
				} else {
					changeTextSumMoney(10);
				}

			}
			// ��������ֵ��3
			if (v.getId() < RED_PL3_HEZHI_ZU6_START
					&& v.getId() >= RED_PL3_HEZHI_ZU3_START) {
				int iBallViewId = v.getId() - RED_PL3_HEZHI_ZU3_START;
				if (iBallViewId < 0) {
					return;
				} else {
					changeBuyViewByRule(iCurrentButton, 11, iBallViewId);
				}
				if (PL3HezhiZu3BallTable.getHighlightBallNums() == 0) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_number));
				} else {
					changeTextSumMoney(11);
				}
			}
			// ��������ֵ��6
			if (v.getId() >= RED_PL3_HEZHI_ZU6_START) {
				int iBallViewId = v.getId() - RED_PL3_HEZHI_ZU6_START;
				if (iBallViewId < 0) {
					return;
				} else {
					changeBuyViewByRule(iCurrentButton, 12, iBallViewId);
				}
				if (PL3HezhiZu6BallTable.getHighlightBallNums() == 0) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_number));
				} else {
					changeTextSumMoney(12);
				}
			}

		} else {
			// ��������3��ʽ��һ��
			if (v.getId() < RED_PL3_ZU3_DANSHI_SHIWEI_START
					&& v.getId() >= RED_PL3_ZU3_DANSHI_BAIWEI_START) {
				int iBallViewId = v.getId() - RED_PL3_ZU3_DANSHI_BAIWEI_START;
				if (iBallViewId < 0) {
					return;
				} else {
					changeBuyViewByRule(iCurrentButton, 0, iBallViewId);
				}
				if (PL3A1Zu3DanshiBallTable.getHighlightBallNums() == 0
						|| PL3A2Zu3DanshiBallTable.getHighlightBallNums() == 0
						|| PL3BZu3DanshiBallTable.getHighlightBallNums() == 0) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_number));
				} else {
					changeTextSumMoney(0);
				}
			}
			// ��������3��ʽ�ڶ���
			if (v.getId() < RED_PL3_ZU3_DANSHI_GEWEI_START
					&& v.getId() >= RED_PL3_ZU3_DANSHI_SHIWEI_START) {
				int iBallViewId = v.getId() - RED_PL3_ZU3_DANSHI_SHIWEI_START;
				if (iBallViewId < 0) {
					return;
				} else {
					changeBuyViewByRule(iCurrentButton, 1, iBallViewId);
				}
				if (PL3A1Zu3DanshiBallTable.getHighlightBallNums() == 0
						|| PL3A2Zu3DanshiBallTable.getHighlightBallNums() == 0
						|| PL3BZu3DanshiBallTable.getHighlightBallNums() == 0) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_number));
				} else {
					changeTextSumMoney(0);
				}
			}
			// ��������3��ʽ������
			if (v.getId() < RED_PL3_ZU3_FUSHI_START
					&& v.getId() >= RED_PL3_ZU3_DANSHI_GEWEI_START) {
				int iBallViewId = v.getId() - RED_PL3_ZU3_DANSHI_GEWEI_START;
				if (iBallViewId < 0) {
					return;
				} else {
					changeBuyViewByRule(iCurrentButton, 2, iBallViewId);
				}
				if (PL3A1Zu3DanshiBallTable.getHighlightBallNums() == 0
						|| PL3A2Zu3DanshiBallTable.getHighlightBallNums() == 0
						|| PL3BZu3DanshiBallTable.getHighlightBallNums() == 0) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_number));
				} else {
					changeTextSumMoney(0);
				}
			}
			// ��������3��ʽ
			if (v.getId() < RED_PL3_ZU6_START
					&& v.getId() >= RED_PL3_ZU3_FUSHI_START) {
				int iBallViewId = v.getId() - RED_PL3_ZU3_FUSHI_START;
				if (iBallViewId < 0) {
					return;
				} else {
					changeBuyViewByRule(iCurrentButton, 3, iBallViewId);
				}
				if (PL3Zu3FushiBallTable.getHighlightBallNums() >= 0
						&& PL3Zu3FushiBallTable.getHighlightBallNums() < 2) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_number));
				} else {
					changeTextSumMoney(0);
				}
			}
			// ��������6
			if (v.getId() < RED_PL3_ZHIXUAN_BAIWEI_START
					&& v.getId() >= RED_PL3_ZU6_START) {
				int iBallViewId = v.getId() - RED_PL3_ZU6_START;
				if (iBallViewId < 0) {
					return;
				} else {
					changeBuyViewByRule(iCurrentButton, 4, iBallViewId);
				}
				if (PL3Zu6BallTable.getHighlightBallNums() >= 0
						&& PL3Zu6BallTable.getHighlightBallNums() < 3) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_number));
				} else {
					changeTextSumMoney(0);
				}
			}

			// ������ֱѡ��λ
			if (v.getId() < RED_PL3_ZHIXUAN_SHIWEI_START
					&& v.getId() >= RED_PL3_ZHIXUAN_BAIWEI_START) {
				int iBallViewId = v.getId() - RED_PL3_ZHIXUAN_BAIWEI_START;
				if (iBallViewId < 0) {
					return;
				} else {
					changeBuyViewByRule(iCurrentButton, 7, iBallViewId);
				}
				if (PL3ZhixuanBaiweiBallTable.getHighlightBallNums() == 0
						|| PL3ZhixuanShiweiBallTable.getHighlightBallNums() == 0
						|| PL3ZhixuanGeweiBallTable.getHighlightBallNums() == 0) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_number));
				} else {
					changeTextSumMoney(0);
				}

			}
			// ������ʮλ
			if (v.getId() < RED_PL3_ZHIXUAN_GEWEI_START
					&& v.getId() >= RED_PL3_ZHIXUAN_SHIWEI_START) {
				int iBallViewId = v.getId() - RED_PL3_ZHIXUAN_SHIWEI_START;
				if (iBallViewId < 0) {
					return;
				} else {
					changeBuyViewByRule(iCurrentButton, 8, iBallViewId);
				}
				if (PL3ZhixuanBaiweiBallTable.getHighlightBallNums() == 0
						|| PL3ZhixuanShiweiBallTable.getHighlightBallNums() == 0
						|| PL3ZhixuanGeweiBallTable.getHighlightBallNums() == 0) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_number));
				} else {
					changeTextSumMoney(0);
				}
			}
			// ��������λ
			if (v.getId() < RED_PL3_HEZHI_ZHIXUAN_START
					&& v.getId() >= RED_PL3_ZHIXUAN_GEWEI_START) {
				int iBallViewId = v.getId() - RED_PL3_ZHIXUAN_GEWEI_START;
				if (iBallViewId < 0) {
					return;
				} else {
					changeBuyViewByRule(iCurrentButton, 9, iBallViewId);
				}
				if (PL3ZhixuanBaiweiBallTable.getHighlightBallNums() == 0
						|| PL3ZhixuanShiweiBallTable.getHighlightBallNums() == 0
						|| PL3ZhixuanGeweiBallTable.getHighlightBallNums() == 0) {
					mTextSumMoney.setText(getResources().getString(
							R.string.please_choose_number));
				} else {
					changeTextSumMoney(0);
				}
			}
		}
	}

	/**
	 * ��ø����淨��ע��
	 * 
	 * @return ����ע��
	 */
	private int getZhuShu() {
		int iReturnValue = 0;
		switch (iCurrentButton) {
		// ������ֱѡ��ʽ
		case PublicConst.BUY_PL3_ZHIXUAN:
			iReturnValue = PL3ZhixuanBaiweiBallTable.getHighlightBallNums()
					* PL3ZhixuanShiweiBallTable.getHighlightBallNums()
					* PL3ZhixuanGeweiBallTable.getHighlightBallNums();
			break;

		// ��������3��ʽ
		case PublicConst.BUY_PL3_ZU3:
			// �����3��ʽע����ȡ20100912 �³�
			if (bZu3Danshi) {
				// ��ʽע����Ϊ1
				iReturnValue = 1;
			}
			// ��3 ��ʽע���Ļ�ȡ 20100912 �³�
			if (bZu3Fushi) {
				int iZu3Balls = PL3Zu3FushiBallTable.getHighlightBallNums();
				iReturnValue = (int) getPL3Zu3FushiZhushu(iZu3Balls);
			}
			break;
		// ��������6��ʽ
		case PublicConst.BUY_PL3_ZU6:
			int iZu6Balls = PL3Zu6BallTable.getHighlightBallNums();
			iReturnValue = (int) getPL3Zu6FushiZhushu(iZu6Balls);
			break;
		case PublicConst.BUY_PL3_HEZHI:
			// ��������ֱֵѡ
			if (iWhich == 10) {
				iReturnValue = getPL3ZhixuanHezhiZhushu();
			} else if (iWhich == 11) {
				iReturnValue = getPL3Zu3HezhiZhushu();
			} else if (iWhich == 12) {
				iReturnValue = getPL3Zu6HezhiZhushu();
			}
			break;
		default:
			break;
		}
		return iReturnValue;
	}

	// wangyl 8.11 �ݲ�֧������
	/**
	 * ��ø����淨������
	 * 
	 * @return ��������
	 */
	private int getQiShu() {
		int iReturnValue = 0;
		iReturnValue = mSeekBarQishu.getProgress();
		/*
		 * switch (iCurrentButton) { // ������ֱѡ��ʽ case
		 * PublicConst.BUY_PL3_ZHIXUAN: iReturnValue =
		 * mSeekBarQishu.getProgress(); break;
		 * 
		 * // ��������3��ʽ case PublicConst.BUY_PL3_ZU3: iReturnValue =
		 * mSeekBarQishu.getProgress(); break; // ��������6��ʽ case
		 * PublicConst.BUY_PL3_ZU6: iReturnValue = mSeekBarQishu.getProgress();
		 * break; case PublicConst.BUY_PL3_HEZHI: // ��������ֱֵѡ if (iWhich == 10) {
		 * iReturnValue = mSeekBarQishu.getProgress(); } else if (iWhich == 11)
		 * { iReturnValue = mSeekBarQishu.getProgress(); } else if (iWhich ==
		 * 12) { iReturnValue = mSeekBarQishu.getProgress(); } break; default:
		 * break; }
		 */
		return iReturnValue;
	}

	/**
	 * �����������6��ʽע��
	 * 
	 * @param iZu6balls
	 *            ѡ��С�����
	 * @return ����ע��
	 */
	private long getPL3Zu6FushiZhushu(int iZu6balls) {
		long tempzhushu = 0l;
		if (iZu6balls > 0) {
			tempzhushu += PublicMethod.zuhe(3, iZu6balls);
		}
		return tempzhushu;

	}

	/**
	 * �����������3��ʽע��
	 * 
	 * @param iZu3balls
	 *            ѡ��С�����
	 * @return ����ע��
	 */
	private long getPL3Zu3FushiZhushu(int iZu3balls) {
		long tempzhushu = 0l;
		if (iZu3balls > 0) {
			tempzhushu += PublicMethod.zuhe(2, iZu3balls) * 2;
		}
		return tempzhushu;

	}

	/**
	 * ���������ֱѡ��ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getPL3ZhixuanHezhiZhushu() {
		int iZhuShu = 0;
		int[] BallNos = PL3HezhiZhixuanBallTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���1�����0������ʵ��ѡ��ļ�ȥ1
		int[] BallNoZhushus = { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 63, 69,
				73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15, 10, 6, 3, 1 };// 0~27

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i] - 1) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С������1��ʼ���ʼ�ȥ1
					iZhuShu += BallNoZhushus[j];
				}
			}
		}
		return iZhuShu;
	}

	/**
	 * �����������3��ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getPL3Zu3HezhiZhushu() {
		int iZhuShu = 0;
		int[] BallNos = PL3HezhiZu3BallTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���3�����3��
		int[] BallNoZhushus = { 1, 2, 1, 3, 3, 3, 4, 5, 4, 5, 5, 4, 5, 5, 4, 5,
				5, 4, 5, 4, 3, 3, 3, 1, 2, 1 };// 1~26

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i] - 1) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С��ʵ��Id��1��ʼ���ʼ�ȥ1
					iZhuShu += BallNoZhushus[j];
				}
			}
		}
		return iZhuShu;
	}

	/**
	 * �����������6��ֵע��
	 * 
	 * @return ����ע��
	 */
	private int getPL3Zu6HezhiZhushu() {
		int iZhuShu = 0;
		int[] BallNos = PL3HezhiZu6BallTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���3�����1��
		int[] BallNoZhushus = { 1, 1, 2, 3, 4, 5, 7, 8, 9, 10, 10, 10, 10, 9,
				8, 7, 5, 4, 3, 2, 1, 1 };// 3~24

		for (int i = 0; i < BallNos.length; i++) {
			for (int j = 0; j < BallNoZhushus.length; j++) {
				if (j == BallNos[i] - 1) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С��ʵ��Id��1��ʼ���ʼ�ȥ1
					iZhuShu += BallNoZhushus[j];
				}
			}
		}
		return iZhuShu;
	}

	/**
	 * ��ʾ�����淨��ע������
	 * 
	 * @param aWhichGroupBall
	 *            ��ѡ�е�BallTable����Ҫ�����������ֵ�淨��0Ϊ�����淨��10Ϊ��������ֱֵѡ��11Ϊ��������3��ֵ��3��12Ϊ��������ֵ��6
	 *            ��
	 */
	public void changeTextSumMoney(int aWhichGroupBall) {
		switch (iCurrentButton) {
		// ������ֱѡ
		case PublicConst.BUY_PL3_ZHIXUAN:
			if (PL3ZhixuanBaiweiBallTable.getHighlightBallNums() == 1
					&& PL3ZhixuanShiweiBallTable.getHighlightBallNums() == 1
					&& PL3ZhixuanGeweiBallTable.getHighlightBallNums() == 1) {
				int iZhuShu = iProgressBeishu;
				mTextSumMoney.setText("��ǰ�淨Ϊֱѡ��ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ");
			} else if (PL3ZhixuanBaiweiBallTable.getHighlightBallNums() > 1
					|| PL3ZhixuanShiweiBallTable.getHighlightBallNums() > 1
					|| PL3ZhixuanGeweiBallTable.getHighlightBallNums() > 1) {
				int iZhuShu = getZhuShu() * iProgressBeishu;
				mTextSumMoney.setText("��ǰ�淨Ϊֱѡ��ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ");
			}
			break;
		// ��������3
		case PublicConst.BUY_PL3_ZU3:
			if (PL3A1Zu3DanshiBallTable.getHighlightBallNums() == 1
					&& PL3A2Zu3DanshiBallTable.getHighlightBallNums() == 1
					&& PL3BZu3DanshiBallTable.getHighlightBallNums() == 1) {
				int iZhuShu = iProgressBeishu;
				mTextSumMoney.setText("��ǰ�淨Ϊ��3��ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ");
			}
			if (PL3Zu3FushiBallTable.getHighlightBallNums() > 1) {
				int iZhuShu = getZhuShu() * iProgressBeishu;
				mTextSumMoney.setText("��ǰ�淨Ϊ��3��ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ");
			}
			break;
		// ��������6
		case PublicConst.BUY_PL3_ZU6:
			if (PL3Zu6BallTable.getHighlightBallNums() == 3) {
				int iZhuShu = iProgressBeishu;
				mTextSumMoney.setText("��ǰ�淨Ϊ��6��ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ");
			}
			if (PL3Zu6BallTable.getHighlightBallNums() > 3) {
				int iZhuShu = getZhuShu() * iProgressBeishu;
				mTextSumMoney.setText("��ǰ�淨Ϊ��6��ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ");

			}
			break;
		// ��������ֵ
		case PublicConst.BUY_PL3_HEZHI:
			// ��������ֱֵѡ
			if (aWhichGroupBall == 10) {
				int[] BallNos = PL3HezhiZhixuanBallTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���1�����0������ʵ��ѡ��ļ�ȥ1
				int[] BallNoZhushus = { 1, 3, 6, 10, 15, 21, 28, 36, 45, 55,
						63, 69, 73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15,
						10, 6, 3, 1 };// 0~27
				int iZhuShu = 0;
				for (int i = 0; i < BallNos.length; i++) {
					for (int j = 0; j < BallNoZhushus.length; j++) {
						if (j == BallNos[i] - 1) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С������1��ʼ���ʼ�ȥ1
							iZhuShu += BallNoZhushus[j];
							String temp = "��ǰ�淨Ϊ��ֱֵѡ����" + iZhuShu
									* iProgressBeishu + "ע����"
									+ (iZhuShu * iProgressBeishu * 2) + "Ԫ";
							mTextSumMoney.setText(temp);
						}
					}
				}
			}
			// ��������ֵ��3
			if (aWhichGroupBall == 11) {
				int[] BallNos = PL3HezhiZu3BallTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���3�����3��
				int[] BallNoZhushus = { 1, 2, 1, 3, 3, 3, 4, 5, 4, 5, 5, 4, 5,
						5, 4, 5, 5, 4, 5, 4, 3, 3, 3, 1, 2, 1 };// 1~26
				int iZhuShu = 0;
				for (int i = 0; i < BallNos.length; i++) {
					for (int j = 0; j < BallNoZhushus.length; j++) {
						if (j == BallNos[i] - 1) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С��ʵ��Id��1��ʼ���ʼ�ȥ1
							iZhuShu += BallNoZhushus[j];
							String temp = "��ǰ�淨Ϊ��ֵ��3����" + iZhuShu
									* iProgressBeishu + "ע����"
									+ (iZhuShu * iProgressBeishu * 2) + "Ԫ";
							mTextSumMoney.setText(temp);
						}
					}
				}
			}
			// ��������ֵ��6
			if (aWhichGroupBall == 12) {
				int[] BallNos = PL3HezhiZu6BallTable.getHighlightBallNOs();// ��ѡ��С��ĺ��루���3�����1��
				int[] BallNoZhushus = { 1, 1, 2, 3, 4, 5, 7, 8, 9, 10, 10, 10,
						10, 9, 8, 7, 5, 4, 3, 2, 1, 1 };// 3~24
				int iZhuShu = 0;
				for (int i = 0; i < BallNos.length; i++) {
					for (int j = 0; j < BallNoZhushus.length; j++) {
						if (j == BallNos[i] - 1) {// ��Ϊ�����Ǵ�0��ʼ�ģ�С��ʵ��Id��1��ʼ���ʼ�ȥ1
							iZhuShu += BallNoZhushus[j];
							String temp = "��ǰ�淨Ϊ��ֵ��6����" + iZhuShu
									* iProgressBeishu + "ע����"
									+ (iZhuShu * iProgressBeishu * 2) + "Ԫ";
							mTextSumMoney.setText(temp);
						}
					}
				}
			}
			break;
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
		case PublicConst.BUY_PL3_ZU3:
			buy_PL3_ZU3(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.BUY_PL3_ZU6:
			buy_PL3_ZU6(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.BUY_PL3_ZHIXUAN:
			buy_PL3_ZHIXUAN(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.BUY_PL3_HEZHI:
			// ��ǰiCurrentButtonΪBUY_PL3_HEZHI
			if (aWhichGroupBall == 10) {
				buy_PL3_HEZHI_ZHIXUAN(aWhichGroupBall, aBallViewId);
			}
			if (aWhichGroupBall == 11) {
				buy_PL3_HEZHI_ZU3(aWhichGroupBall, aBallViewId);
			}
			if (aWhichGroupBall == 12) {
				buy_PL3_HEZHI_ZU6(aWhichGroupBall, aBallViewId);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * ��ֵ��6
	 * 
	 * @param aWhichGroupBall
	 *            �ڼ���С��
	 * @param aBallViewId
	 *            ��clickС���id����3��ʼ������С������ʾ������Ϊid
	 */
	private void buy_PL3_HEZHI_ZU6(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 12) { // ��ֵ��6
			int iChosenBallSum = 9;
			int isHighLight = PL3HezhiZu6BallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.HezhiZu6BallGroup.iHezhiZu6BallStatus[aBallViewId] = 1;
			} else {
				mBallHolder.HezhiZu6BallGroup.iHezhiZu6BallStatus[aBallViewId] = 0;
			}
		}
	}

	/**
	 * ��ֵ��3
	 * 
	 * @param aWhichGroupBall
	 *            �ڼ���С��
	 * @param aBallViewId
	 *            ��clickС���id����0��ʼ������С������ʾ������Ϊid
	 */
	private void buy_PL3_HEZHI_ZU3(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 11) { // ��ֵ��3
			int iChosenBallSum = 9;
			int isHighLight = PL3HezhiZu3BallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.HezhiZu3BallGroup.iHezhiZu3BallStatus[aBallViewId] = 1;
			} else {
				mBallHolder.HezhiZu3BallGroup.iHezhiZu3BallStatus[aBallViewId] = 0;
			}
		}
	}

	/**
	 * ��ֱֵѡ
	 * 
	 * @param aWhichGroupBall
	 *            �ڼ���С��
	 * @param aBallViewId
	 *            ��clickС���id����0��ʼ������С������ʾ������Ϊid
	 */
	private void buy_PL3_HEZHI_ZHIXUAN(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 10) { // ��ֱֵѡ
			int iChosenBallSum = 9;
			int isHighLight = PL3HezhiZhixuanBallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.HezhiZhixuanBallGroup.iHezhiZhixuanBallStatus[aBallViewId] = 1;
			} else {
				mBallHolder.HezhiZhixuanBallGroup.iHezhiZhixuanBallStatus[aBallViewId] = 0;
			}
		}
	}

	/**
	 * ������ֱѡ
	 * 
	 * @param aWhichGroupBall
	 *            �ڼ���С��
	 * @param aBallViewId
	 *            ��clickС���id����0��ʼ������С������ʾ������Ϊid
	 */
	private void buy_PL3_ZHIXUAN(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 7) { // ��λ
			int iChosenBallSum = 10;
			// ÿ�ε�����סС���״̬
			int isHighLight = PL3ZhixuanBaiweiBallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.ZhixuanBallGroup.iZhixuanBaiweiBallStatus[aBallViewId] = 1;
			} else {
				mBallHolder.ZhixuanBallGroup.iZhixuanBaiweiBallStatus[aBallViewId] = 0;
			}
		}
		if (aWhichGroupBall == 8) { // ʮλ
			int iChosenBallSum = 10;
			// ÿ�ε�����סС���״̬
			int isHighLight = PL3ZhixuanShiweiBallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.ZhixuanBallGroup.iZhixuanShiweiBallStatus[aBallViewId] = 1;
			} else {
				mBallHolder.ZhixuanBallGroup.iZhixuanShiweiBallStatus[aBallViewId] = 0;
			}
		}
		if (aWhichGroupBall == 9) { // ��λ
			int iChosenBallSum = 10;
			// ÿ�ε�����סС���״̬
			int isHighLight = PL3ZhixuanGeweiBallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.ZhixuanBallGroup.iZhixuanGeweiBallStatus[aBallViewId] = 1;
			} else {
				mBallHolder.ZhixuanBallGroup.iZhixuanGeweiBallStatus[aBallViewId] = 0;
			}
		}
	}

	/**
	 * ��������6
	 * 
	 * @param aWhichGroupBall
	 *            �ڼ���С��
	 * @param aBallViewId
	 *            ��clickС���id����0��ʼ������С������ʾ������Ϊid
	 */
	private void buy_PL3_ZU6(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 4) { // ��6
			int iChosenBallSum = 9;
			int isHighLight = PL3Zu6BallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.Zu6BallGroup.iZu6BallStatus[aBallViewId] = 1;
			} else {
				mBallHolder.Zu6BallGroup.iZu6BallStatus[aBallViewId] = 0;
			}
		}
	}

	/**
	 * ��������3
	 * 
	 * @param aWhichGroupBall
	 *            �ڼ���С��
	 * @param aBallViewId
	 *            ��clickС���id����0��ʼ������С������ʾ������Ϊid
	 */
	private void buy_PL3_ZU3(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 0) { // 
			int iChosenBallSum = 1;
			// �������������ظ�
			// ÿ�ε�����סС���״̬
			int isHighLightA1 = PL3A1Zu3DanshiBallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			int isHighLightA2 = PL3A2Zu3DanshiBallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			if (isHighLightA1 == PublicConst.BALL_TO_HIGHLIGHT
					&& isHighLightA2 == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.Zu3BallGroup.iZu3A1BallStatus[aBallViewId] = 1;
				mBallHolder.Zu3BallGroup.iZu3A2BallStatus[aBallViewId] = 1;
				PL3BZu3DanshiBallTable.clearOnBallHighlight(aBallViewId);
				mBallHolder.Zu3BallGroup.iZu3BBallStatus[aBallViewId] = 0;

			} else {
				mBallHolder.Zu3BallGroup.iZu3A1BallStatus[aBallViewId] = 0;
				mBallHolder.Zu3BallGroup.iZu3A2BallStatus[aBallViewId] = 0;
			}
		} else if (aWhichGroupBall == 1) {
			int iChosenBallSum = 1;
			// �������������ظ�
			// ÿ�ε�����סС���״̬
			int isHighLightA1 = PL3A1Zu3DanshiBallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			int isHighLightA2 = PL3A2Zu3DanshiBallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			if (isHighLightA1 == PublicConst.BALL_TO_HIGHLIGHT
					&& isHighLightA2 == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.Zu3BallGroup.iZu3A1BallStatus[aBallViewId] = 1;
				mBallHolder.Zu3BallGroup.iZu3A2BallStatus[aBallViewId] = 1;
				PL3BZu3DanshiBallTable.clearOnBallHighlight(aBallViewId);
				mBallHolder.Zu3BallGroup.iZu3BBallStatus[aBallViewId] = 0;

			} else {
				mBallHolder.Zu3BallGroup.iZu3A1BallStatus[aBallViewId] = 0;
				mBallHolder.Zu3BallGroup.iZu3A2BallStatus[aBallViewId] = 0;
			}
		} else if (aWhichGroupBall == 2) {
			int iChosenBallSum = 1;
			// ��3,ǰ������ͬ�������鲻����֮��ͬ

			int isHighLight = PL3BZu3DanshiBallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.Zu3BallGroup.iZu3BBallStatus[aBallViewId] = 1;
				PL3A1Zu3DanshiBallTable.clearOnBallHighlight(aBallViewId);
				PL3A2Zu3DanshiBallTable.clearOnBallHighlight(aBallViewId);
				mBallHolder.Zu3BallGroup.iZu3A1BallStatus[aBallViewId] = 0;
				mBallHolder.Zu3BallGroup.iZu3A2BallStatus[aBallViewId] = 0;

			} else {
				mBallHolder.Zu3BallGroup.iZu3BBallStatus[aBallViewId] = 0;
			}
		} else if (aWhichGroupBall == 3) {
			int iChosenBallSum = 10;
			int isHighLight = PL3Zu3FushiBallTable.changeBallState(
					iChosenBallSum, aBallViewId);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.Zu3BallGroup.iZu3FushiBallStatus[aBallViewId] = 1;
			} else {
				mBallHolder.Zu3BallGroup.iZu3FushiBallStatus[aBallViewId] = 0;
			}
		}
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
	 * @param int aBallViewText 0:С���0��ʼ��ʾ,1:С���1��ʼ��ʾ ,3С���3��ʼ��ʾ(��������ֵ��6��3��ʼ)
	 * @return BallTable
	 */
	private BallTable makeBallTable(LinearLayout aParentView, int aLayoutId,
			int aFieldWidth, int aBallNum, int aBallViewWidth, int[] aResId,
			int aIdStart, int aBallViewText) {
		BallTable iBallTable = new BallTable(aParentView, aLayoutId, aIdStart);

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
				String iStrTemp = "";
				if (aBallViewText == 0) {
					iStrTemp = "" + (iBallViewNo);// С���0��ʼ
				} else if (aBallViewText == 1) {
					iStrTemp = "" + (iBallViewNo + 1);// С���1��ʼ
				} else if (aBallViewText == 3) {
					iStrTemp = "" + (iBallViewNo + 3);// С���3��ʼ
				}
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
			iBallTable.tableLayout.addView(tableRow,
					new TableLayout.LayoutParams(FP, WC));
		}
		if (lastLineViewNum > 0) {
			TableRow tableRow = new TableRow(this);
			for (int col = 0; col < lastLineViewNum; col++) {

				String iStrTemp = "";
				if (aBallViewText == 0) {
					iStrTemp = "" + (iBallViewNo);// С���0��ʼ
				} else if (aBallViewText == 1) {
					iStrTemp = "" + (iBallViewNo + 1);// С���1��ʼ
				} else if (aBallViewText == 3) {
					iStrTemp = "" + (iBallViewNo + 3);// С���3��ʼ
				}
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
			iBallTable.tableLayout.addView(tableRow,
					new TableLayout.LayoutParams(FP, WC));
		}
		return iBallTable;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_pl3, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		PublicMethod.myOutput("************featureId " + featureId);
		switch (item.getItemId()) {
		// ȷ��Ͷע
		case R.id.pl3_confirm:
			beginTouZhu();
			break;
		// ���»�ѡ
		case R.id.pl3_reselect_num:
			beginReselect();
			break;
		// �淨����
		case R.id.pl3_game_introduce:
			dialogGameIntroduction();
			break;
		// ȡ��
		case R.id.pl3_cancel:
			this.finish();
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	public void onCancelClick() {

	}

	/**
	 * menu�������ѡ��
	 */
	private void beginReselect() {

		// ������ֱѡ
		if (iCurrentButton == PublicConst.BUY_PL3_ZHIXUAN) {
			PL3ZhixuanBaiweiBallTable.clearAllHighlights();
			PL3ZhixuanShiweiBallTable.clearAllHighlights();
			PL3ZhixuanGeweiBallTable.clearAllHighlights();
		}
		// ��������3
		if (iCurrentButton == PublicConst.BUY_PL3_ZU3) {
			PL3A1Zu3DanshiBallTable.clearAllHighlights();
			PL3A2Zu3DanshiBallTable.clearAllHighlights();
			PL3BZu3DanshiBallTable.clearAllHighlights();
			PL3Zu3FushiBallTable.clearAllHighlights();
		}
		// ��������6
		if (iCurrentButton == PublicConst.BUY_PL3_ZU6) {
			PL3Zu6BallTable.clearAllHighlights();
		}
		// ��������ֵ
		if (iCurrentButton == PublicConst.BUY_PL3_HEZHI) {
			// ��������ֱֵѡ
			if (iWhich == 10) {
				PL3HezhiZhixuanBallTable.clearAllHighlights();
			}
			// ��������ֵ��3
			if (iWhich == 11) {
				PL3HezhiZu3BallTable.clearAllHighlights();
			}
			// ��������ֵ��6
			if (iWhich == 12) {
				PL3HezhiZu6BallTable.clearAllHighlights();
			}
		}

	}

	/**
	 * �����淨��Ͷע����
	 */
	private void beginTouZhu() {
		// ������ 7.4 �����޸ģ���ӵ�½���ж�
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(PL3.this,
				"addInfo");
		String sessionIdStr = pre.getUserLoginInfo("sessionid");
		// ��ȡ���� �³� 20100711
		int iQiShu = getQiShu();
		// Ͷעʱ�ж��Ƿ��¼
		if (sessionIdStr.equals("")) {
			Intent intentSession = new Intent(PL3.this, UserLogin.class);
			startActivity(intentSession);
		} else {
			// ������ֱѡ
			if (iCurrentButton == PublicConst.BUY_PL3_ZHIXUAN) {

				int baiweiNums = PL3ZhixuanBaiweiBallTable
						.getHighlightBallNums();
				int shiweiNums = PL3ZhixuanShiweiBallTable
						.getHighlightBallNums();
				int geweiNums = PL3ZhixuanGeweiBallTable.getHighlightBallNums();
				int[] baiweis = PL3ZhixuanBaiweiBallTable.getHighlightBallNOs();
				int[] shiweis = PL3ZhixuanShiweiBallTable.getHighlightBallNOs();
				int[] geweis = PL3ZhixuanGeweiBallTable.getHighlightBallNOs();
				String baiweistr = "";
				String shiweistr = "";
				String geweistr = "";
				for (int i = 0; i < baiweiNums; i++) {
					baiweistr += (baiweis[i] - 1) + ".";
					if (i == baiweiNums - 1) {
						baiweistr = baiweistr.substring(0,
								baiweistr.length() - 1);
					}
				}
				for (int i = 0; i < shiweiNums; i++) {
					shiweistr += (shiweis[i] - 1) + ".";
					if (i == shiweiNums - 1) {
						shiweistr = shiweistr.substring(0,
								shiweistr.length() - 1);
					}
				}
				for (int i = 0; i < geweiNums; i++) {
					geweistr += (geweis[i] - 1) + ".";
					if (i == geweiNums - 1) {
						geweistr = geweistr.substring(0, geweistr.length() - 1);
					}
				}
				if (baiweiNums < 1 || shiweiNums < 1 || geweiNums < 1) {
					alertDialog("��ѡ�����", "���ڰ�λ��ʮλ����λ������ѡ��һ��С�����Ͷע");
				} else if ((baiweiNums + shiweiNums + geweiNums) > 24) {
					alertDialog("Ͷעʧ��", "С�����������24��");
				} else {
					int iZhuShu = getZhuShu() * iProgressBeishu;
					if (iZhuShu / iProgressBeishu > 600) {
						alertDialog("Ͷעʧ��", "��ѡ�񲻴���600עͶע");
					} else if (iZhuShu * 2 > 100000) {
						alertDialog("Ͷעʧ��", "����Ͷע���ܴ���100000Ԫ");
					} else if (jrtLot.ifPerfectIfo(this, sessionIdStr)) {

						alert("ע�룺" + "\n" + "   ��λ��" + baiweistr + "\n"
								+ "   ʮλ��" + shiweistr + "\n" + "   ��λ��"
								+ geweistr + "\n" + "ע����"
								+ iZhuShu / iProgressBeishu + "ע" + "\n"
								+ "������" + iProgressBeishu + "��" + "\n" + "׷�ţ�"
								+ iProgressQishu + "��" + "\n" + "��" + iZhuShu
								* 2 + "Ԫ" + "\n" + "�����"
								+ (2 * (iProgressQishu - 1) * iZhuShu) + "Ԫ"
								+ "\n" + "ȷ��֧����");
					}
				}

			}
			// ��������3
			if (iCurrentButton == PublicConst.BUY_PL3_ZU3) {

				int iZhuShu = 0;
				if (bZu3Danshi) {
					int baiweiNums = PL3A1Zu3DanshiBallTable
							.getHighlightBallNums();
					int shiweiNums = PL3A2Zu3DanshiBallTable
							.getHighlightBallNums();
					int geweiNums = PL3BZu3DanshiBallTable
							.getHighlightBallNums();

					if (baiweiNums < 1 || shiweiNums < 1 || geweiNums < 1) {
						alertDialog("��ѡ�����", "���ٰ�λ��ʮλ�� ��λ�о�ѡ��һ��С�����Ͷע");
					} else if (baiweiNums == 1 && shiweiNums == 1
							&& geweiNums == 1) {
						iZhuShu = mSeekBarBeishu.getProgress();
						String baiweistr = PL3A1Zu3DanshiBallTable
								.getHighlightBallNOs()[0]
								- 1 + "";// ǰ2λ��ͬ
						String geweistr = PL3BZu3DanshiBallTable
								.getHighlightBallNOs()[0]
								- 1 + "";// ǰ2λ��ͬ
						if (iZhuShu * 2 > 100000) {
							alertDialog("Ͷעʧ��", "����Ͷע���ܴ���100000Ԫ");
						} else if (jrtLot.ifPerfectIfo(this, sessionIdStr)) {

							alert("ע�룺" + baiweistr + "." + baiweistr + "."
									+ geweistr + "\n" + "ע����" + 1 + "ע" + "\n"
									+ "������" + iProgressBeishu + "��" + "\n"
									+ "׷�ţ�" + iProgressQishu + "��" + "\n"
									+ "��" + iZhuShu * 2 + "Ԫ" + "\n"
									+ "�����"
									+ (2 * (iProgressQishu - 1) * iZhuShu)
									+ "Ԫ" + "\n" + "ȷ��֧����");
						}
					}
				}
				if (bZu3Fushi) {
					if (PL3Zu3FushiBallTable.getHighlightBallNums() < 2) {
						alertDialog("��ѡ�����", "������ѡ��2��С�����Ͷע");
					} else {
						int[] fushiNums = PL3Zu3FushiBallTable
								.getHighlightBallNOs();
						String fushiStr = "";
						for (int i = 0; i < fushiNums.length; i++) {
							fushiStr += (fushiNums[i] - 1) + ".";
							if (i == fushiNums.length - 1) {
								fushiStr = fushiStr.substring(0, fushiStr
										.length() - 1);
							}
						}
						iZhuShu = getZhuShu() * iProgressBeishu;
						if (iZhuShu * 2 > 100000) {
							alertDialog("Ͷעʧ��", "����Ͷע���ܴ���100000Ԫ");
						} else if (jrtLot.ifPerfectIfo(this, sessionIdStr)) {

							alert("ע�룺" + fushiStr + "\n" + "ע����"
									+ iZhuShu / iProgressBeishu + "ע" + "\n"
									+ "������" + iProgressBeishu + "��" + "\n"
									+ "׷�ţ�" + iProgressQishu + "��" + "\n"
									+ "��" + iZhuShu * 2 + "Ԫ" + "\n"
									+ "�����"
									+ (2 * (iProgressQishu - 1) * iZhuShu)
									+ "Ԫ" + "\n" + "ȷ��֧����");
						}
					}
				}

			}
			// ��������6
			if (iCurrentButton == PublicConst.BUY_PL3_ZU6) {

				if (PL3Zu6BallTable.getHighlightBallNums() < 3) {
					alertDialog("��ѡ�����", "������ѡ��3��С�����Ͷע");
				} else {
					int[] fushiNums = PL3Zu6BallTable.getHighlightBallNOs();
					String fushiStr = "";
					for (int i = 0; i < fushiNums.length; i++) {
						fushiStr += (fushiNums[i] - 1) + ".";
						if (i == fushiNums.length - 1) {
							fushiStr = fushiStr.substring(0,
									fushiStr.length() - 1);
						}
					}
					int iZhuShu = getZhuShu() * iProgressBeishu;

					if (iZhuShu * 2 > 100000) {
						alertDialog("Ͷעʧ��", "����Ͷע���ܴ���100000Ԫ");
					} else if (jrtLot.ifPerfectIfo(this, sessionIdStr)) {

						alert("ע�룺" + fushiStr + "\n" + "ע����"
								+ iZhuShu / iProgressBeishu + "ע" + "\n"
								+ "������" + iProgressBeishu + "��" + "\n" + "׷�ţ�"
								+ iProgressQishu + "��" + "\n" + "��"
								+ iZhuShu * 2 + "Ԫ" + "\n" + "�����"
								+ (2 * (iProgressQishu - 1) * iZhuShu) + "Ԫ"
								+ "\n" + "ȷ��֧����");
					}
				}

			}
			// ��������ֵ
			if (iCurrentButton == PublicConst.BUY_PL3_HEZHI) {
				// ��������ֱֵѡ
				if (iWhich == 10) {

					if (PL3HezhiZhixuanBallTable.getHighlightBallNums() < 1) {
						alertDialog("��ѡ�����", "��ѡ��С��������Ͷע");
					} else if (PL3HezhiZhixuanBallTable.getHighlightBallNums() <= 9) {
						int iZhuShu = getZhuShu() * iProgressBeishu;
						String fushiStr = "";
						int[] zhuma_zhixuanhezhi = PL3HezhiZhixuanBallTable
								.getHighlightBallNOs();
						for (int i = 0; i < zhuma_zhixuanhezhi.length; i++) {
							fushiStr += (zhuma_zhixuanhezhi[i] - 1) + ",";
							if (i == zhuma_zhixuanhezhi.length - 1) {
								fushiStr = fushiStr.substring(0, fushiStr
										.length() - 1);
							}
						}
						if (iZhuShu * 2 > 100000) {
							alertDialog("Ͷעʧ��", "����Ͷע���ܴ���100000Ԫ");
						} else if (jrtLot.ifPerfectIfo(this, sessionIdStr)) {

							alert("ע�룺" + fushiStr + "\n" + "ע����"
									+ iZhuShu / iProgressBeishu + "ע" + "\n"
									+ "������" + iProgressBeishu + "��" + "\n"
									+ "׷�ţ�" + iProgressQishu + "��" + "\n"
									+ "��" + iZhuShu * 2 + "Ԫ" + "\n"
									+ "�����"
									+ (2 * (iProgressQishu - 1) * iZhuShu)
									+ "Ԫ" + "\n" + "ȷ��֧����");
						}
					}

				}
				// ��������ֵ��3
				if (iWhich == 11) {

					if (PL3HezhiZu3BallTable.getHighlightBallNums() < 1) {
						alertDialog("��ѡ�����", "��ѡ��С��������Ͷע");
					} else if (PL3HezhiZu3BallTable.getHighlightBallNums() <= 9) {
						// ��ʾ�û�ѡ�е���Ϣ
						int iZhuShu = getZhuShu() * iProgressBeishu;
						String fushiStr = "";
						int[] zhuma_zu3hezhi = PL3HezhiZu3BallTable
								.getHighlightBallNOs();
						for (int i = 0; i < zhuma_zu3hezhi.length; i++) {
							fushiStr += (zhuma_zu3hezhi[i]) + ",";
							if (i == zhuma_zu3hezhi.length - 1) {
								fushiStr = fushiStr.substring(0, fushiStr
										.length() - 1);
							}
						}
						if (iZhuShu * 2 > 100000) {
							alertDialog("Ͷעʧ��", "����Ͷע���ܴ���100000Ԫ");
						} else if (jrtLot.ifPerfectIfo(this, sessionIdStr)) {

							alert("ע�룺" + fushiStr + "\n" + "ע����"
									+ iZhuShu / iProgressBeishu + "ע" + "\n"
									+ "������" + iProgressBeishu + "��" + "\n"
									+ "׷�ţ�" + iProgressQishu + "��" + "\n"
									+ "��" + iZhuShu * 2 + "Ԫ" + "\n"
									+ "�����"
									+ (2 * (iProgressQishu - 1) * iZhuShu)
									+ "Ԫ" + "\n" + "ȷ��֧����");
						}
					}

				}
				// ��������ֵ��6
				if (iWhich == 12) {

					if (PL3HezhiZu6BallTable.getHighlightBallNums() < 1) {
						alertDialog("��ѡ�����", "��ѡ��С��������Ͷע");
					} else if (PL3HezhiZu6BallTable.getHighlightBallNums() <= 9) {
						int iZhuShu = getZhuShu() * iProgressBeishu;
						String fushiStr = "";
						int[] zhuma_zu6hezhi = PL3HezhiZu6BallTable
								.getHighlightBallNOs();
						for (int i = 0; i < zhuma_zu6hezhi.length; i++) {
							fushiStr += (zhuma_zu6hezhi[i] + 2) + ",";
							if (i == zhuma_zu6hezhi.length - 1) {
								fushiStr = fushiStr.substring(0, fushiStr
										.length() - 1);
							}
						}
						if (iZhuShu * 2 > 100000) {
							alertDialog("Ͷעʧ��", "����Ͷע���ܴ���100000Ԫ");
						} else if (jrtLot.ifPerfectIfo(this, sessionIdStr)) {

							alert("ע�룺" + fushiStr + "\n" + "ע����"
									+ iZhuShu / iProgressBeishu + "ע" + "\n"
									+ "������" + iProgressBeishu + "��" + "\n"
									+ "׷�ţ�" + iProgressQishu + "��" + "\n"
									+ "��" + iZhuShu * 2 + "Ԫ" + "\n"
									+ "�����"
									+ (2 * (iProgressQishu - 1) * iZhuShu)
									+ "Ԫ" + "\n" + "ȷ��֧����");
						}
					}

				}
			}
		}
	}

	@Override
	public void onOkClick(int[] aReturn) {
		mBallHolder = new BallHolderPL3();
		switch (iCurrentButton) {
		case PublicConst.BUY_PL3_ZHIXUAN:
			mBallHolder = PL3ZhixuanBaiweiBallTable
					.randomChooseConfigChangePL3(aReturn[0], mBallHolder, 7,
							null);
			mBallHolder = PL3ZhixuanShiweiBallTable
					.randomChooseConfigChangePL3(aReturn[1], mBallHolder, 8,
							null);
			mBallHolder = PL3ZhixuanGeweiBallTable.randomChooseConfigChangePL3(
					aReturn[2], mBallHolder, 9, null);
			changeTextSumMoney(0);
			break;
		case PublicConst.BUY_PL3_ZU3:
			mBallHolder = PL3Zu3FushiBallTable.randomChooseConfigChangePL3(
					aReturn[0], mBallHolder, 3, null);
			changeTextSumMoney(0);
			break;
		case PublicConst.BUY_PL3_ZU6:
			mBallHolder = PL3Zu6BallTable.randomChooseConfigChangePL3(
					aReturn[0], mBallHolder, 4, null);
			changeTextSumMoney(0);
			break;
		case PublicConst.BUY_PL3_HEZHI:
			if (iWhich == 10) {
				mBallHolder = PL3HezhiZhixuanBallTable
						.randomChooseConfigChangePL3(aReturn[0], mBallHolder,
								10, null);
				changeTextSumMoney(10);
				break;
			}
			if (iWhich == 11) {
				mBallHolder = PL3HezhiZu3BallTable.randomChooseConfigChangePL3(
						aReturn[0], mBallHolder, 11, null);
				changeTextSumMoney(11);
				break;
			}
			if (iWhich == 12) {
				mBallHolder = PL3HezhiZu6BallTable.randomChooseConfigChangePL3(
						aReturn[0], mBallHolder, 12, null);
				changeTextSumMoney(12);
				break;
			}

		default:
			break;
		}
	}

	// ʹ���½ӿ� �³� 2010/7/11
	/**
	 * Ͷע�½ӿ�
	 * 
	 * @param betsע��
	 * @param count����
	 * @param amountͶע�ܽ��
	 */
	protected String[] payNew(String betCode, String lotMulti, String amount,
			String qiShu) {
		// TODO Auto-generated method stub
		BettingInterface betting = new BettingInterface();
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,
				"addInfo");
		String sessionid = shellRW.getUserLoginInfo("sessionid");
		String phonenum = shellRW.getUserLoginInfo("phonenum");

		PublicMethod.myOutput("-------------touzhusessionid" + sessionid);
		PublicMethod.myOutput("-------------phonenum" + phonenum);

		String[] error_code = betting.BettingTC(phonenum, "T01002", betCode,
				lotMulti, amount, "", qiShu, sessionid);
		return error_code;
	}

	// ÿ���淨�ı��
	String zhixuan = "1|";// ֱѡ
	String zuxuan = "6|";// ��ѡ��ʽ������������������
	String zu3fushi = "F3|";// ������ʽ
	String zu6fushi = "F6|";// ������ʽ
	String zxHHH = "S1|";// ֱѡ��ֵ
	String z3HHH = "S3|";// ��3��ֵ
	String z6HHH = "S6|";// ��6��ֵ

	/**
	 * ��ȡÿһ�������ע��
	 * 
	 */
	private String zhuma_PL3() {
		String strZM = "";
		// ������ֱѡ�淨
		if (iCurrentButton == PublicConst.BUY_PL3_ZHIXUAN) {

			int[] zhuma_baiwei = PL3ZhixuanBaiweiBallTable
					.getHighlightBallNOs();// ��õĺ�����Ҫ��ȥ1
			int[] zhuma_shiwei = PL3ZhixuanShiweiBallTable
					.getHighlightBallNOs();
			int[] zhuma_gewei = PL3ZhixuanGeweiBallTable.getHighlightBallNOs();

			if (zhuma_baiwei.length > 0 && zhuma_shiwei.length > 0
					&& zhuma_gewei.length > 0) {
				strZM = zhixuan;
				for (int i = 0; i < zhuma_baiwei.length; i++) {
					strZM += (zhuma_baiwei[i] - 1) + "";
				}
				strZM += ",";
				for (int i = 0; i < zhuma_shiwei.length; i++) {
					strZM += (zhuma_shiwei[i] - 1) + "";
				}
				strZM += ",";
				for (int i = 0; i < zhuma_gewei.length; i++) {
					strZM += (zhuma_gewei[i] - 1) + "";
				}
				PublicMethod.myOutput("------zhuma------" + strZM);
			}
		}
		// �����������淨
		else if (iCurrentButton == PublicConst.BUY_PL3_ZU3) {
			if (bZu3Danshi) {// ��ʽ
				int[] zhuma_zu3danshi_A1 = PL3A1Zu3DanshiBallTable
						.getHighlightBallNOs();
				int[] zhuma_zu3danshi_A2 = PL3A2Zu3DanshiBallTable
						.getHighlightBallNOs();
				int[] zhuma_zu3danshi_A3 = PL3BZu3DanshiBallTable
						.getHighlightBallNOs();
				if (zhuma_zu3danshi_A1.length > 0
						&& zhuma_zu3danshi_A2.length > 0
						&& zhuma_zu3danshi_A3.length > 0) {
					strZM = zuxuan + (zhuma_zu3danshi_A3[0] - 1) + ","
							+ (zhuma_zu3danshi_A1[0] - 1) + ","
							+ (zhuma_zu3danshi_A1[0] - 1);
				}
				PublicMethod.myOutput("------zhuma------" + strZM);
			}
			if (bZu3Fushi) {// ��ʽҲ����������
				strZM = zu3fushi;
				int[] zhuma_zu3fushi = PL3Zu3FushiBallTable
						.getHighlightBallNOs();
				if (zhuma_zu3fushi.length > 0) {
					for (int i = 1; i < zhuma_zu3fushi.length + 1; i++) {
						strZM += zhuma_zu3fushi[i - 1] - 1 + "";
					}
				}
				PublicMethod.myOutput("------zhuma------" + strZM);
			}
		}
		// �����������淨
		else if (iCurrentButton == PublicConst.BUY_PL3_ZU6) {
			int[] zhuma_zu6danfushi = PL3Zu6BallTable.getHighlightBallNOs();
			if (zhuma_zu6danfushi.length == 3) {// ��ʽ
				strZM = zuxuan + (zhuma_zu6danfushi[0] - 1) + ","
						+ (zhuma_zu6danfushi[1] - 1) + ","
						+ (zhuma_zu6danfushi[2] - 1);
				PublicMethod.myOutput("------zhuma------" + strZM);
			}
			if (zhuma_zu6danfushi.length > 3) {// ��ʽҲ����������
				strZM = zu6fushi;
				for (int i = 1; i < zhuma_zu6danfushi.length + 1; i++) {
					strZM += zhuma_zu6danfushi[i - 1] - 1 + "";
				}
				PublicMethod.myOutput("------zhuma------" + strZM);
			}

		}
		// ��������ֵ�淨
		else if (iCurrentButton == PublicConst.BUY_PL3_HEZHI) {
			// �ж��Ǻ�ֱֵѡ ���Ǻ�ֵ��3 ���Ǻ�ֵ��6
			if (iWhich == 10) {
				strZM = zxHHH;
				int[] zhuma_zhixuanhezhi = PL3HezhiZhixuanBallTable
						.getHighlightBallNOs();
				for (int i = 0; i < zhuma_zhixuanhezhi.length; i++) {
					strZM += (zhuma_zhixuanhezhi[i] - 1) + ",";
					if (i == zhuma_zhixuanhezhi.length - 1) {
						strZM = strZM.substring(0, strZM.length() - 1);
					}
				}
				PublicMethod.myOutput("------zhuma------" + strZM);

			} else if (iWhich == 11) {
				strZM = z3HHH;
				int[] zhuma_zu3hezhi = PL3HezhiZu3BallTable
						.getHighlightBallNOs();
				for (int i = 0; i < zhuma_zu3hezhi.length; i++) {
					strZM += (zhuma_zu3hezhi[i]) + ",";
					if (i == zhuma_zu3hezhi.length - 1) {
						strZM = strZM.substring(0, strZM.length() - 1);
					}
				}
				PublicMethod.myOutput("------zhuma------" + strZM);

			} else if (iWhich == 12) {
				strZM = z6HHH;
				int[] zhuma_zu6hezhi = PL3HezhiZu6BallTable
						.getHighlightBallNOs();
				for (int i = 0; i < zhuma_zu6hezhi.length; i++) {
					strZM += (zhuma_zu6hezhi[i] + 2) + ",";
					if (i == zhuma_zu6hezhi.length - 1) {
						strZM = strZM.substring(0, strZM.length() - 1);
					}
				}
				PublicMethod.myOutput("------zhuma------" + strZM);
			}

		}
		return strZM;
	}

	/**
	 * Ͷע����������ʱ�����ĶԻ���
	 * 
	 * @param message
	 */
	private void alertDialog(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(PL3.this);
		builder.setTitle(title);
		builder.setMessage(message);
		// ȷ��
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}

				});
		builder.show();
	}

	/**
	 * MENU����淨����
	 */
	private void dialogGameIntroduction() {

		WebView webView = new WebView(this);
		String url = "file:///android_asset/ruyihelper_gameIntroduction_pl3.html";
		webView.loadUrl(url);

		AlertDialog.Builder builder = new AlertDialog.Builder(PL3.this);
		builder.setTitle("�淨����");
		builder.setView(webView);
		// ȷ��
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}

				});
		builder.show();
	}

	/**
	 * ���к������л�ʱ��õı���
	 */
	private int getBeishu() {
		return mSeekBarBeishu.getProgress();

	}

	/**
	 * ���к������л�ʱ��õ�����
	 */
	private int getQishu() {
		return mSeekBarQishu.getProgress();

	}

	/**
	 * ���к������л�ʱ��ѡ��ѡ���״̬
	 */
	private boolean getCheckBox() {
		return mCheckBox.isChecked();
	}

	/**
	 * ��3���к������л�ʱ��ʽRadioButton��״̬
	 */
	private boolean getZu3DanshiRadioButton() {
		return danshirbtn.isChecked();
	}

	/**
	 * ��3���к������л�ʱ��ʽRadioButton��״̬
	 */
	private boolean getZu3FushiRadioButton() {
		return fushirbtn.isChecked();
	}

	/**
	 * ���к������л�ʱ���õ�ǰ������ǩ״̬
	 * 
	 */
	public void showHighLight() {
		// ������ֱѡ
		if (iCurrentButton == PublicConst.BUY_PL3_ZHIXUAN) {
			topButtonGroup.check(1);// û�иı䲻�����¼�����ΪĬ����0
			topButtonGroup.check(0);
			topButtonGroup.invalidate();
		}
		// ��������3
		if (iCurrentButton == PublicConst.BUY_PL3_ZU3) {
			topButtonGroup.check(1);
			topButtonGroup.invalidate();
		}
		// ��������6
		if (iCurrentButton == PublicConst.BUY_PL3_ZU6) {
			topButtonGroup.check(2);
			topButtonGroup.invalidate();
		}
		// ��������ֵ
		if (iCurrentButton == PublicConst.BUY_PL3_HEZHI) {
			topButtonGroup.check(4);
			topButtonGroup.invalidate();
		}
	}

	/**
	 * @author WangYanling ��¼ÿ��ҳ�漰�Ƿ��л������� ��������ֵ��������Ҫ����д����Ϊ֧�ֶ��ѡ��
	 *         ������δ��������3D��ͬ��3D֧��һ��ѡ�š�
	 */
	public class BallHolderPL3 {
		// ������ֱѡ
		BallGroup ZhixuanBallGroup = new BallGroup();
		// ��������3
		BallGroup Zu3BallGroup = new BallGroup();
		// ��������6
		BallGroup Zu6BallGroup = new BallGroup();
		// ��ֵ
		BallGroup HezhiZhixuanBallGroup = new BallGroup();
		BallGroup HezhiZu3BallGroup = new BallGroup();
		BallGroup HezhiZu6BallGroup = new BallGroup();

		// ������ǩRadioGroup
		int topButtonGroup;
		// �Ƿ��л������� 1Ϊ�ǣ�0Ϊ��
		int flag = 0;

	}

	/**
	 * @author WangYanling
	 * @category ��¼��ǰҳ��ؼ��ĸ���״̬�Լ���ѡ�е�С����ballTable�е�index
	 */
	public class BallGroup {
		// ������ֱѡ
		int[] iZhixuanBaiweiBallStatus = new int[10];
		int[] iZhixuanShiweiBallStatus = new int[10];
		int[] iZhixuanGeweiBallStatus = new int[10];
		// ��������3
		int[] iZu3A1BallStatus = new int[10];
		int[] iZu3A2BallStatus = new int[10];
		int[] iZu3BBallStatus = new int[10];
		int[] iZu3FushiBallStatus = new int[10];
		boolean bRadioBtnDanshi;
		boolean bRadioBtnFushi;
		// ��������6
		int[] iZu6BallStatus = new int[10];
		// ��������ֱֵѡ
		int[] iHezhiZhixuanBallStatus = new int[28];
		int[] iHezhiZu3BallStatus = new int[26];
		int[] iHezhiZu6BallStatus = new int[22];
		// ����
		int iBeiShu;
		// ����
		int iQiShu;
		// ��ѡ��ѡ��
		boolean bCheckBox;
	}

	// ��ʾ�� ����ȷ���Ƿ�Ͷע
	/**
	 * Ͷעʱ���õĺ���
	 * 
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
	 */
	private void alert(String string) {

		Builder dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����")
				.setMessage(string).setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								touzhu.setImageResource(R.drawable.touzhuup_n);
								touzhu.setClickable(false);

								iHttp.whetherChange = false;
								showDialog(DIALOG1_KEY);
								// TODO Auto-generated method stub
								Thread t = new Thread(new Runnable() {
									int iZhuShu = getZhuShu();
									int iQiShu = getQiShu();
									int iBeiShu = getBeishu();
									String[] strCode = null;

									@Override
									public void run() {
										String zhuma = zhuma_PL3();
										// TODO Auto-generated method stub
										PublicMethod.myOutput("@@@@@@@@@"
												+ zhuma);
										// str=pay(zhuma,iZhuShu+"");
										// Ͷע�½ӿ� �³� 20100711 Ǯ������������ 2010/7/13
										// �³�
										strCode = payNew(zhuma, iBeiShu + "",
												iZhuShu * iProgressBeishu * 2
														+ "", iQiShu + "");

										touzhu.setClickable(true);

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
											msg.what = 11;
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
										} else if (strCode[1].equals("002002")) {
											Message msg = new Message();
											msg.what = 3;
											handler.sendMessage(msg);
										}
										// else if (str.equals("002002")) {
										// Message msg = new Message();
										// msg.what = 3;
										// handler.sendMessage(msg);
										// }
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
			// progressdialog.setCancelable(true);
			return progressdialog;
		}
		}
		return null;
	}

}