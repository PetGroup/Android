package com.palmdream.RuyicaiAndroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 
 * @author wangyl ���⴫�����͸��ѡ
 * 
 */
public class RuyiExpressFeelDltZixuan extends Activity implements
		OnClickListener, SeekBar.OnSeekBarChangeListener {

	// С��ͼ�Ŀ��
	public static final int BALL_WIDTH = 32;
	private int iScreenWidth;

	BallTable DLTRedZhixuanBallTable = null;// ������
	BallTable DLTBlueZhixuanBallTable = null;// ������

	public static final int DLT_RED_ZIXUAN_START = 0x90000010;
	public static final int DLT_BLUE_ZIXUAN_START = 0x90000050;
	// wangyl 7.12 ���ͳɹ��󷵻ش���
	public static final int DLT_INTENT = 1;

	private int DLTRedBallResId[] = { R.drawable.grey, R.drawable.red };
	private int DLTBlueBallResId[] = { R.drawable.grey, R.drawable.blue };

	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;

	TextView mTextSumMoney;// �ܽ��
	// SeekBarĬ��Ϊ1
	int iProgressBeishu = 1;
	SeekBar mSeekBarBeishu;// ����
	TextView mTextBeishu;// ����
	ScrollView scrollView;
	Button sureBtn;
	Button newselected;
	int[] redBallNumbers = new int[35];
	int[] blueBallNumbers = new int[12];
	int iZhushu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// ----- ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.ruyichuanqing_dlt_zixuan);
		// ������7.6����
		ImageView rtnview = (ImageView) findViewById(R.id.ruyipackage_btn_return_dlt);
		rtnview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});

		scrollView = (ScrollView) findViewById(R.id.ruyichuanqing_dlt_scrollview);

		LinearLayout iV = (LinearLayout) findViewById(R.id.ruyichuanqing_dlt_layout_id);

		{
			int redBallViewWidth = FC3DTest.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			int redBallViewNum = 35;
			DLTRedZhixuanBallTable = makeBallTable(iV,
					R.id.table_dlt_zixuan_red, iScreenWidth, redBallViewNum,
					redBallViewWidth, DLTRedBallResId, DLT_RED_ZIXUAN_START, 1);

			int blueBallViewNum = 12;
			DLTBlueZhixuanBallTable = makeBallTable(iV,
					R.id.table_dlt_zixuan_blue, iScreenWidth, blueBallViewNum,
					redBallViewWidth, DLTBlueBallResId, DLT_BLUE_ZIXUAN_START,
					1);

			mTextSumMoney = (TextView) iV
					.findViewById(R.id.text_sum_money_dlt_zixuan);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));

			mSeekBarBeishu = (SeekBar) iV
					.findViewById(R.id.dlt_zixuan_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mTextBeishu = (TextView) iV
					.findViewById(R.id.dlt_zixuan_text_beishu);
			mTextBeishu.setText("" + iProgressBeishu);

			ImageButton addBtn = (ImageButton) iV
					.findViewById(R.id.dlt_seekbar_add_zixuan);
			ImageButton subtractBtn = (ImageButton) iV
					.findViewById(R.id.dlt_seekbar_subtract_zixuan);
			addBtn.setOnClickListener(new ImageButton.OnClickListener() {

				@Override
				public void onClick(View v) {
					mSeekBarBeishu.setProgress(++iProgressBeishu);
				}

			});
			subtractBtn.setOnClickListener(new ImageButton.OnClickListener() {

				@Override
				public void onClick(View v) {
					mSeekBarBeishu.setProgress(--iProgressBeishu);
				}

			});

		}

		sureBtn = (Button) findViewById(R.id.dlt_sure_zixuan);
		sureBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				int redNums = DLTRedZhixuanBallTable.getHighlightBallNums();
				int blueNums = DLTBlueZhixuanBallTable.getHighlightBallNums();
				if (redNums < 5 || blueNums < 2) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RuyiExpressFeelDltZixuan.this);
					builder.setTitle(getResources().getString(
							R.string.please_choose_number));
					builder.setMessage("ǰ������ѡ��5��С���������ѡ��2��С��");
					// ȷ��
					builder.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}

							});
					builder.show();
				} else {
					iZhushu = (int) getZhuShu(redBallNumbers.length,
							blueBallNumbers.length);
					Intent intent = new Intent(RuyiExpressFeelDltZixuan.this,
							RuyiExpressFeelSuccess.class);
					Bundle dltZixuanBundle = new Bundle();
					dltZixuanBundle.putString("success", "dltZixuan");
					dltZixuanBundle.putIntArray("redBall", redBallNumbers);//ǰ��
					dltZixuanBundle.putIntArray("blueBall", blueBallNumbers);//����
					dltZixuanBundle.putInt("dltzixuanzhushu", iZhushu);
					dltZixuanBundle.putInt("dltzixuanbeishu", iProgressBeishu);
					intent.putExtras(dltZixuanBundle);
					// wangyl 7.12 ���ͳɹ��󷵻ش���
					startActivityForResult(intent, DLT_INTENT);
				}
			}

		});

		newselected = (Button) findViewById(R.id.dlt_newselectd_zixuan);
		newselected.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				DLTRedZhixuanBallTable.clearAllHighlights();
				DLTBlueZhixuanBallTable.clearAllHighlights();
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_number));
				mSeekBarBeishu.setProgress(1);
			}

		});

	}

	// wangyl 7.12 ���ͳɹ��󷵻ش���
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == DLT_INTENT) {
			if (resultCode == RESULT_OK) {
				RuyiExpressFeelDltZixuan.this.finish();
			}
		}
	}

	@Override
	public void onClick(View v) {
		int iBallId = v.getId();
		if (iBallId >= DLT_RED_ZIXUAN_START && iBallId < DLT_BLUE_ZIXUAN_START) {
			int iBallViewId = v.getId() - DLT_RED_ZIXUAN_START;
			if (iBallViewId < 0) {
				return;
			} else {
				changeBuyViewByRule(1, iBallViewId);// �������
			}
		} else if (iBallId >= DLT_BLUE_ZIXUAN_START) {
			int iBallViewId = v.getId() - DLT_BLUE_ZIXUAN_START;
			if (iBallViewId < 0) {
				return;
			} else {
				changeBuyViewByRule(2, iBallViewId);// ��������
			}
		}
		changeTextSumMoney();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

		if (progress < 1) {
			seekBar.setProgress(1);
		}
		int iProgress = seekBar.getProgress();
		iProgressBeishu = iProgress;
		mTextBeishu.setText("" + iProgressBeishu);
		changeTextSumMoney();

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

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
	 * ��ʾ�����淨��ע������
	 * 
	 * @param aWhichGroupBall
	 *            ��ѡ�е�BallTable����Ҫ��Ը���3D��ֵ�淨��0Ϊ�����淨��10Ϊ����3D��ֱֵѡ��11Ϊ����3D��3��ֵ��3��12Ϊ����3D��ֵ��6
	 *            ��
	 */
	public void changeTextSumMoney() {
		int iRedHighlights = DLTRedZhixuanBallTable.getHighlightBallNums();
		int iBlueHighlights = DLTBlueZhixuanBallTable.getHighlightBallNums();

		// ������ ����
		if (iRedHighlights < 5) {
			mTextSumMoney.setText("��ѡ��ǰ��С��");
		}
		// �������ﵽ���Ҫ��
		else if (iRedHighlights == 5) {
			if (iBlueHighlights < 2) {
				mTextSumMoney.setText("��ѡ�����С��");
			} else if (iBlueHighlights == 2) {
				long iZhuShu = getZhuShu(iRedHighlights, iBlueHighlights);
				String iTempString = "��ǰΪ��ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ";
				mTextSumMoney.setText(iTempString);
			} else {
				long iZhuShu = getZhuShu(iRedHighlights, iBlueHighlights);
				String iTempString = "��ǰΪ������ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ";
				mTextSumMoney.setText(iTempString);
			}
		}
		// ����ʽ
		else {
			if (iBlueHighlights < 2) {
				mTextSumMoney.setText("��ѡ�����С��");
			} else if (iBlueHighlights == 2) {
				long iZhuShu = getZhuShu(iRedHighlights, iBlueHighlights);
				String iTempString = "��ǰΪǰ����ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ";
				mTextSumMoney.setText(iTempString);
			} else {
				long iZhuShu = getZhuShu(iRedHighlights, iBlueHighlights);
				String iTempString = "��ǰΪ˫����ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ";
				mTextSumMoney.setText(iTempString);
			}
		}
	}

	/**
	 * ��ø����淨��ע��
	 * 
	 * @return ����ע��
	 */
	private long getZhuShu(int aRedBalls, int aBlueBalls) {
		long dltZhuShu = 0L;
		if (aRedBalls > 0 && aBlueBalls > 0) {
			dltZhuShu += (PublicMethod.zuhe(5, aRedBalls)
					* PublicMethod.zuhe(2, aBlueBalls) * iProgressBeishu);
		}
		return dltZhuShu;
	}

	/**
	 * �����淨�ı䵱ǰView ����Ӧ
	 * 
	 * @param aWhichGroupBall
	 *            �ڼ���С���絥ʽ������С�򣬴�0��ʼ����
	 * @param aBallViewId
	 *            ��clickС���id����0��ʼ������С������ʾ������Ϊid+1
	 */
	private void changeBuyViewByRule(int aWhichGroupBall, int aBallViewId) {
		if (aWhichGroupBall == 1) { // ǰ��
			int iChosenBallSum = 22;
			DLTRedZhixuanBallTable.changeBallState(iChosenBallSum, aBallViewId);
			redBallNumbers = DLTRedZhixuanBallTable.getHighlightBallNOs();
		}
		if (aWhichGroupBall == 2) { // ����
			int iChosenBallSum = 12;
			DLTBlueZhixuanBallTable
					.changeBallState(iChosenBallSum, aBallViewId);
			blueBallNumbers = DLTBlueZhixuanBallTable.getHighlightBallNOs();
		}
	}
}
