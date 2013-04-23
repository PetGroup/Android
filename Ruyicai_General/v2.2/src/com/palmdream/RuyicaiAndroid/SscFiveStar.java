/**
 * 
 */
package com.palmdream.RuyicaiAndroid;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid.BallBetPublicClass.BallHolder;
import com.palmdream.netintface.BettingInterface;
import com.palmdream.netintface.iHttp;
import com.palmdream.netintface.jrtLot;

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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Administrator
 * 
 */
public class SscFiveStar extends Activity implements OnClickListener, // С�򱻵��
		// onClick
		SeekBar.OnSeekBarChangeListener, // SeekBar�ı� onProgressChanged
		RadioGroup.OnCheckedChangeListener, // ��ѡ��ѡ��仯 onCheckedChangeListener
		MyDialogListener {

	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
	// ������
	private static final int DIALOG1_KEY = 0;
	private ProgressDialog progressdialog;
	private LinearLayout buyView;
	private int iScreenWidth;
	// ����
	private RadioGroup.LayoutParams topButtonLayoutParams;
	private RadioGroup topButtonGroup;
	private int iButtonNum = 4;
	int topButtonIdOn[] = { R.drawable.dantuo_b, R.drawable.dantuo_b,
			R.drawable.dantuo_b, R.drawable.dantuo_b };
	int topButtonIdOff[] = { R.drawable.dantuo, R.drawable.dantuo,
			R.drawable.dantuo, R.drawable.dantuo };

	SeekBar mSeekBarBeishu;
	SeekBar mSeekBarQishu;
	TextView mTextBeishu;
	TextView mTextQishu;
	TextView mTextSumMoney;
	ImageButton ssc_btn_touzhu;
	Button ssc_btn_newSelect;
	CheckBox mCheckBox;

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

	private static final int BALL_WIDTH = 29; // С��ͼ�Ŀ��

	private int iCurrentButton; // ����button��ǰ��λ��
	private int dialogType = 0;
	private int iWanBallNumber;// С���������ѡ��
	private int iQianBallNumber;// С���������ѡ��
	private int iBaiBallNumber;// С���������ѡ��
	private int iShiBallNumber;// С���������ѡ��
	private int iGeBallNumber;// С���������ѡ��
	private int mTimesMoney = 2;
	TextView issue;
	int iretrytimes = 2;
	String batchCode;
	String endTime;
	Timer timer;
	int countMinute;
	int countSecond;
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
				wanBallTable.clearAllHighlights();
				qianBallTable.clearAllHighlights();
				baiBallTable.clearAllHighlights();
				shiBallTable.clearAllHighlights();
				geBallTable.clearAllHighlights();

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
				// Ͷע�ɹ������С��
				wanBallTable.clearAllHighlights();
				qianBallTable.clearAllHighlights();
				baiBallTable.clearAllHighlights();
				shiBallTable.clearAllHighlights();
				geBallTable.clearAllHighlights();
				break;
			case 7:
				progressdialog.dismiss();

				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(SscFiveStar.this,
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
				Log.e("time[1]==", time[1]);
				times(Integer.parseInt(times[0]), Integer.parseInt(times[1]),
						Integer.parseInt(times[2]));
				break;

			case 13:// ��ʾ��ǰ����

				progressdialog.dismiss();
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
				issue.setText("��" + batchCode + "��" + "     ʣ��ʱ�䣺" + minute
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
		super.onCreate(savedInstanceState);
		// ----- ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// ----- ���ؿ�� layout
		setContentView(R.layout.layout_main_buy);
		ImageButton returnBtn = (ImageButton) findViewById(R.id.goucaitouzhu_title_return);
		returnBtn.setImageResource(R.drawable.ssc_back);
		returnBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
		ImageButton flush = (ImageButton) findViewById(R.id.layout_main_buy_imgbtn_flush);
		flush.setVisibility(ImageButton.VISIBLE);
		flush.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				getLotNo();
			}

		});
		TextView title = (TextView) findViewById(R.id.goucaitouzhu_title);
		title.setText(getResources().getString(R.string.ssc_wuxing));

		issue = (TextView) findViewById(R.id.layout_main_buy_text_title);
		issue.setVisibility(TextView.VISIBLE);
		getLotNo();
		// ��ȡ������id
		buyView = (LinearLayout) findViewById(R.id.layout_buy);
	
		iCurrentButton = PublicConst.SSC_FIVE_STAR;
		createView(iCurrentButton);
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
		Time t = new Time(); // or Time t=new Time("GMT+8"); ����Time Zone����

		t.setToNow(); // ȡ��ϵͳʱ�䡣

		int hour = t.hour + 8;
		int minute = t.minute;
		int second = t.second;

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
		showDialog(DIALOG1_KEY);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				String re = "";

				while (iretrytimes < 3 && iretrytimes > 0) {
					re = jrtLot.getLotNo("T01007");
					if (!re.equalsIgnoreCase("")) {
						try {
							JSONObject obj = new JSONObject(re);
							error_code = obj.getString("error_code");
							batchCode = obj.getString("batchCode");
							endTime = obj.getString("end_time");
							iretrytimes = 3;
						} catch (JSONException e) {
							iretrytimes--;
						}
					} else {
						iretrytimes--;
					}
				}
				iretrytimes = 2;

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

	}

	/**
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
			tabButton.setState(topButtonIdOn[i], topButtonIdOff[i]);
			tabButton.setId(i);
			tabButton.setGravity(Gravity.CENTER);
			topButtonGroup.addView(tabButton, i, topButtonLayoutParams);

		}
		topButtonGroup.check(0);// Ĭ������
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
		case 0: // ����
			iCurrentButton = PublicConst.SSC_FIVE_STAR;
			createView(iCurrentButton);
			break;
		case 1: // ����
			iCurrentButton = PublicConst.SSC_THREE_STAR;
			createView(iCurrentButton);
			break;
		case 2: // ����
			iCurrentButton = PublicConst.SSC_TWO_STAR;
			createView(iCurrentButton);
			break;
		case 3: // һ��
			iCurrentButton = PublicConst.SSC_ONE_STAR;
			createView(iCurrentButton);
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
		int BallViewWidth = SscFiveStar.BALL_WIDTH;
		iScreenWidth = PublicMethod.getDisplayWidth(this);
		switch (Type) {
		case PublicConst.SSC_FIVE_STAR:
			dialogType = PublicConst.SSC_FIVE_STAR;
			iV = (LinearLayout) inflate.inflate(R.layout.layout_ssc_five_star,
					null);

			wanBallTable = makeBallTable(iV, R.id.ssc_table_wan, iScreenWidth,
					BallViewNum, BallViewWidth, BallResId, WAN_BALL_START, 0);

			qianBallTable = makeBallTable(iV, R.id.ssc_table_qian,
					iScreenWidth, BallViewNum, BallViewWidth, BallResId,
					QIAN_BALL_START, 0);
			baiBallTable = makeBallTable(iV, R.id.ssc_table_bai, iScreenWidth,
					BallViewNum, BallViewWidth, BallResId, BAI_BALL_START, 0);
			shiBallTable = makeBallTable(iV, R.id.ssc_table_shi, iScreenWidth,
					BallViewNum, BallViewWidth, BallResId, SHI_BALL_START, 0);
			geBallTable = makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
					BallViewNum, BallViewWidth, BallResId, GE_BALL_START, 0);
			break;
		case PublicConst.SSC_THREE_STAR:
			dialogType = PublicConst.SSC_THREE_STAR;
			iV = (LinearLayout) inflate.inflate(R.layout.layout_ssc_three_star,
					null);
			baiBallTable = makeBallTable(iV, R.id.ssc_table_bai, iScreenWidth,
					BallViewNum, BallViewWidth, BallResId, BAI_BALL_START, 0);
			shiBallTable = makeBallTable(iV, R.id.ssc_table_shi, iScreenWidth,
					BallViewNum, BallViewWidth, BallResId, SHI_BALL_START, 0);
			geBallTable = makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
					BallViewNum, BallViewWidth, BallResId, GE_BALL_START, 0);
			break;
		case PublicConst.SSC_TWO_STAR:
			dialogType = PublicConst.SSC_TWO_STAR;
			iV = (LinearLayout) inflate.inflate(R.layout.layout_ssc_two_star,
					null);
			shiBallTable = makeBallTable(iV, R.id.ssc_table_shi, iScreenWidth,
					BallViewNum, BallViewWidth, BallResId, SHI_BALL_START, 0);
			geBallTable = makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
					BallViewNum, BallViewWidth, BallResId, GE_BALL_START, 0);
			break;
		case PublicConst.SSC_ONE_STAR:
			dialogType = PublicConst.SSC_ONE_STAR;
			iV = (LinearLayout) inflate.inflate(R.layout.layout_ssc_one_star,
					null);
			geBallTable = makeBallTable(iV, R.id.ssc_table_ge, iScreenWidth,
					BallViewNum, BallViewWidth, BallResId, GE_BALL_START, 0);
			break;
		default:
			break;
		}

		{
			TextView alert = (TextView) iV.findViewById(R.id.ssc_text_alert);
			alert.setText(getResources().getString(R.string.ssc_title_alert));
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

			ssc_btn_newSelect = (Button) iV
					.findViewById(R.id.ssc_btn_newSelect);
			ssc_btn_newSelect.setOnClickListener(new Button.OnClickListener() {

				@Override
				public void onClick(View v) {
					ChooseNumberDialogSsc iChooseNumberDialog = new ChooseNumberDialogSsc(
							SscFiveStar.this, dialogType, SscFiveStar.this);
					iChooseNumberDialog.show();
					changeTextSumMoney();
				}

			});
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

		mCheckBox = (CheckBox) this.findViewById(R.id.ssc_jixuan_cb);
		mCheckBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							ChooseNumberDialogSsc iChooseNumberDialog = new ChooseNumberDialogSsc(
									SscFiveStar.this, dialogType,
									SscFiveStar.this);
							iChooseNumberDialog.show();
							ssc_btn_newSelect.setVisibility(View.VISIBLE);
							changeTextSumMoney();

						} else {
							ssc_btn_newSelect.setVisibility(View.INVISIBLE);
						}
					}
				});
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
		case 0: // ����
			setCurrentTab(0);
			break;
		case 1: // ����
			setCurrentTab(1);
			break;
		case 2: // ����
			setCurrentTab(2);
			break;
		case 3: // һ��
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
		case PublicConst.SSC_FIVE_STAR:
			iWanBallNumber = aNums[0];
			iQianBallNumber = aNums[1];
			iBaiBallNumber = aNums[2];
			iShiBallNumber = aNums[3];
			iGeBallNumber = aNums[4];
			wanBallTable.sscRandomChooseConfigChange(iWanBallNumber);
			qianBallTable.sscRandomChooseConfigChange(iQianBallNumber);
			baiBallTable.sscRandomChooseConfigChange(iBaiBallNumber);
			shiBallTable.sscRandomChooseConfigChange(iShiBallNumber);
			geBallTable.sscRandomChooseConfigChange(iGeBallNumber);
			break;
		case PublicConst.SSC_THREE_STAR:
			iBaiBallNumber = aNums[0];
			iShiBallNumber = aNums[1];
			iGeBallNumber = aNums[2];
			baiBallTable.sscRandomChooseConfigChange(iBaiBallNumber);
			shiBallTable.sscRandomChooseConfigChange(iShiBallNumber);
			geBallTable.sscRandomChooseConfigChange(iGeBallNumber);
			break;
		case PublicConst.SSC_TWO_STAR:
			iShiBallNumber = aNums[0];
			iGeBallNumber = aNums[1];
			shiBallTable.sscRandomChooseConfigChange(iShiBallNumber);
			geBallTable.sscRandomChooseConfigChange(iGeBallNumber);
			break;
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
		int wan = wanBallTable.getHighlightBallNums();
		int qian = qianBallTable.getHighlightBallNums();
		int bai = baiBallTable.getHighlightBallNums();
		int shi = shiBallTable.getHighlightBallNums();
		int ge = geBallTable.getHighlightBallNums();
		int beishu = mSeekBarBeishu.getProgress();
		int iReturnValue = 0;
		switch (iCurrentButton) {
		case PublicConst.SSC_FIVE_STAR:
			iReturnValue = wan * qian * bai * shi * ge * beishu;
			break;
		case PublicConst.SSC_THREE_STAR:
			iReturnValue = bai * shi * ge * beishu;
			break;
		case PublicConst.SSC_TWO_STAR:
			iReturnValue = shi * ge * beishu;
			break;
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
		case PublicConst.SSC_FIVE_STAR:
			// ��ʽView�У���2��ball table
			buyTicket(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.SSC_THREE_STAR:
			buyTicket(aWhichGroupBall, aBallViewId);
			break;
		case PublicConst.SSC_TWO_STAR:
			buyTicket(aWhichGroupBall, aBallViewId);
			break;
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
		}
	}

	/**
	 * ����������ʾ��
	 */
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
		int wan = wanBallTable.getHighlightBallNums();
		int qian = qianBallTable.getHighlightBallNums();
		int bai = baiBallTable.getHighlightBallNums();
		int shi = shiBallTable.getHighlightBallNums();
		int ge = geBallTable.getHighlightBallNums();
		int beishu = mSeekBarBeishu.getProgress();
		int iZhuShu = getZhuShu();
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
				SscFiveStar.this, "addInfo");
		String sessionIdStr = pre.getUserLoginInfo("sessionid");
		if (sessionIdStr.equals("")) {
			Intent intentSession = new Intent(SscFiveStar.this, UserLogin.class);
			startActivityForResult(intentSession, 0);
		} else {

			if (wan == 0 | qian == 0 | bai == 0 | shi == 0 | ge == 0) {
				alert1("������ѡ��һע��");
			} else if (iZhuShu * 2 > 20000) {
				DialogExcessive();
			} else if (jrtLot.ifPerfectIfo(this, sessionIdStr)) {

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
		AlertDialog.Builder builder = new AlertDialog.Builder(SscFiveStar.this);
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
								iHttp.whetherChange = false;
								showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
							
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

	private String getTouzhuAlert() {
		int iZhuShu = getZhuShu();
		int[] wan = wanBallTable.getHighlightBallNOs();
		int[] qian = qianBallTable.getHighlightBallNOs();
		int[] bai = baiBallTable.getHighlightBallNOs();
		int[] shi = shiBallTable.getHighlightBallNOs();
		int[] ge = geBallTable.getHighlightBallNOs();

		return "ע�룺" + "\n" + "��" + batchCode + "��\n" + "��λ��"
				+ getStrZhuMa(wan) + "\n" + "ǧλ��" + getStrZhuMa(qian) + "\n"
				+ "��λ��" + getStrZhuMa(bai) + "\n" + "ʮλ��" + getStrZhuMa(shi)
				+ "\n" + "��λ��" + getStrZhuMa(ge) + "\n"
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
		int[] wan = wanBallTable.getHighlightBallNOs();
		int[] qian = qianBallTable.getHighlightBallNOs();
		int[] bai = baiBallTable.getHighlightBallNOs();
		int[] shi = shiBallTable.getHighlightBallNOs();
		int[] ge = geBallTable.getHighlightBallNOs();

		t_str = "5T|";
		t_str += getStr(wan) + "," + getStr(qian) + "," + getStr(bai) + ","
				+ getStr(shi) + "," + getStr(ge);

		Log.e("t_str==", t_str);
		return t_str;

	}

	public String getStr(int balls[]) {
		String str = "";
		for (int i = 0; i < balls.length; i++) {
			str += (balls[i] - 1);
		}
		return str;

	}

	public String getStrZhuMa(int balls[]) {
		String str = "";
		for (int i = 0; i < balls.length; i++) {
			str += (balls[i] - 1);
			if (i != (balls.length - 1))
				str += ",";
		}
		return str;

	}

	private int getBeishu() {
		return mSeekBarBeishu.getProgress();

	}

	private int getQishu() {
		return mSeekBarQishu.getProgress();

	}

	// Ͷע�½ӿ� 20100711�³�
	protected String[] payNew(String betCode, String lotMulti, String amount,
			String qiShu) {
	
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
			error_code = betting.BettingTC(phonenum, "T01007", betCode,
					lotMulti, amount, "2", qiShu, sessionid, batchCode);
		} else if (mTimesMoney == 3) {
			error_code = betting.BettingTC(phonenum, "T01007", betCode,
					lotMulti, amount, "3", qiShu, sessionid, batchCode);
		}

		return error_code;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (resultCode) {
		case RESULT_OK:
			
			beginTouZhu();
			
			break;
		default:
			Toast.makeText(SscFiveStar.this, "δ��¼�ɹ���", Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}
}
