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
 * @author wangyl ���⴫�鸣��3����ѡ
 * 
 */
public class RuyiExpressFeelFc3dZixuan extends Activity implements
		OnClickListener, SeekBar.OnSeekBarChangeListener {

	// С��ͼ�Ŀ��
	public static final int BALL_WIDTH = 32;
	private int iScreenWidth;

	BallTable Fc3dZixuanBaiweiBallTable = null;// ��λ
	BallTable Fc3dZixuanShiweiBallTable = null;// ʮλ
	BallTable Fc3dZixuanGeweiBallTable = null;// ��λ

	// ����3Dֱѡ
	public static final int RED_FC3D_ZIXUAN_BAIWEI_START = 0x70000010;
	public static final int RED_FC3D_ZIXUAN_SHIWEI_START = 0x70000020;
	public static final int RED_FC3D_ZIXUAN_GEWEI_START = 0x70000030;
	// wangyl 7.12 ���ͳɹ��󷵻ش���
	public static final int FC3D_INTENT = 1;

	private int Fc3dBallResId[] = { R.drawable.grey, R.drawable.red };

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

	int[] baiBallNumbers = new int[10];
	int[] shiBallNumbers = new int[10];
	int[] geBallNumbers = new int[10];
	int iZhushu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// ----- ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.ruyichuanqing_fc3d_zixuan);

		// ������7.6����
		ImageView rtnview = (ImageView) findViewById(R.id.ruyipackage_btn_return_fc3d);
		rtnview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
		scrollView = (ScrollView) findViewById(R.id.ruyichuanqing_fc3d_scrollview);

		LinearLayout iV = (LinearLayout) findViewById(R.id.ruyichuanqing_fc3d_layout_id);

		{
			int redBallViewNum = 10;
			int redBallViewWidth = FC3DTest.BALL_WIDTH;
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			Fc3dZixuanBaiweiBallTable = makeBallTable(iV,
					R.id.table_fc3d_zixuan_baiwei, iScreenWidth,
					redBallViewNum, redBallViewWidth, Fc3dBallResId,
					RED_FC3D_ZIXUAN_BAIWEI_START, 0);
			Fc3dZixuanShiweiBallTable = makeBallTable(iV,
					R.id.table_fc3d_zixuan_shiwei, iScreenWidth,
					redBallViewNum, redBallViewWidth, Fc3dBallResId,
					RED_FC3D_ZIXUAN_SHIWEI_START, 0);
			Fc3dZixuanGeweiBallTable = makeBallTable(iV,
					R.id.table_fc3d_zixuan_gewei, iScreenWidth, redBallViewNum,
					redBallViewWidth, Fc3dBallResId,
					RED_FC3D_ZIXUAN_GEWEI_START, 0);

			mTextSumMoney = (TextView) iV
					.findViewById(R.id.text_sum_money_fc3d_zixuan);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));

			mSeekBarBeishu = (SeekBar) iV
					.findViewById(R.id.fc3d_zixuan_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mTextBeishu = (TextView) iV
					.findViewById(R.id.fc3d_zixuan_text_beishu);
			mTextBeishu.setText("" + iProgressBeishu);

			ImageButton addBtn = (ImageButton) iV
					.findViewById(R.id.fc3d_seekbar_add_zixuan);
			ImageButton subtractBtn = (ImageButton) iV
					.findViewById(R.id.fc3d_seekbar_subtract_zixuan);
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

		sureBtn = (Button) findViewById(R.id.fc3d_sure_zixuan);
		sureBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				int baiNums = Fc3dZixuanBaiweiBallTable.getHighlightBallNums();
				int shiNums = Fc3dZixuanShiweiBallTable.getHighlightBallNums();
				int geNums = Fc3dZixuanGeweiBallTable.getHighlightBallNums();
				if (baiNums < 1 || shiNums < 1 || geNums < 1) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RuyiExpressFeelFc3dZixuan.this);
					builder.setTitle(getResources().getString(
							R.string.please_choose_number));
					builder.setMessage("��λ��ʮλ����λ������ѡ��1��С��");
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
					iZhushu = (int) getZhuShu() * iProgressBeishu;
					Intent intent = new Intent(RuyiExpressFeelFc3dZixuan.this,
							RuyiExpressFeelSuccess.class);
					Bundle fc3dZixuanBundle = new Bundle();
					fc3dZixuanBundle.putString("success", "fc3dZixuan");
					fc3dZixuanBundle.putIntArray("baiBall", baiBallNumbers);
					fc3dZixuanBundle.putIntArray("shiBall", shiBallNumbers);
					fc3dZixuanBundle.putIntArray("geBall", geBallNumbers);
					fc3dZixuanBundle.putInt("fc3dzixuanzhushu", iZhushu);
					fc3dZixuanBundle
							.putInt("fc3dzixuanbeishu", iProgressBeishu);
					intent.putExtras(fc3dZixuanBundle);
					// wangyl 7.12 ���ͳɹ��󷵻ش���
					startActivityForResult(intent, FC3D_INTENT);
				}
			}

		});

		newselected = (Button) findViewById(R.id.fc3d_newselectd_zixuan);
		newselected.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Fc3dZixuanBaiweiBallTable.clearAllHighlights();
				Fc3dZixuanShiweiBallTable.clearAllHighlights();
				Fc3dZixuanGeweiBallTable.clearAllHighlights();
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_number));
				mSeekBarBeishu.setProgress(1);
			}

		});

	}

	// wangyl 7.12 ���ͳɹ��󷵻ش���
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == FC3D_INTENT) {
			if (resultCode == RESULT_OK) {
				RuyiExpressFeelFc3dZixuan.this.finish();
			}
		}
	}

	@Override
	public void onClick(View v) {
		// ����3Dֱѡ��λ
		if (v.getId() < RED_FC3D_ZIXUAN_SHIWEI_START
				&& v.getId() >= RED_FC3D_ZIXUAN_BAIWEI_START) {
			int iBallViewId = v.getId() - RED_FC3D_ZIXUAN_BAIWEI_START;
			if (iBallViewId < 0) {
				return;
			} else {
				changeBuyViewByRule(1, iBallViewId);
			}
			if (Fc3dZixuanBaiweiBallTable.getHighlightBallNums() == 0
					|| Fc3dZixuanShiweiBallTable.getHighlightBallNums() == 0
					|| Fc3dZixuanGeweiBallTable.getHighlightBallNums() == 0) {
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_number));
			} else {
				changeTextSumMoney();
			}

		}
		// ����3Dʮλ
		if (v.getId() < RED_FC3D_ZIXUAN_GEWEI_START
				&& v.getId() >= RED_FC3D_ZIXUAN_SHIWEI_START) {
			int iBallViewId = v.getId() - RED_FC3D_ZIXUAN_SHIWEI_START;
			if (iBallViewId < 0) {
				return;
			} else {
				changeBuyViewByRule(2, iBallViewId);
			}
			if (Fc3dZixuanBaiweiBallTable.getHighlightBallNums() == 0
					|| Fc3dZixuanShiweiBallTable.getHighlightBallNums() == 0
					|| Fc3dZixuanGeweiBallTable.getHighlightBallNums() == 0) {
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_number));
			} else {
				changeTextSumMoney();
			}
		}
		// ����3D��λ
		if (v.getId() >= RED_FC3D_ZIXUAN_GEWEI_START) {
			int iBallViewId = v.getId() - RED_FC3D_ZIXUAN_GEWEI_START;
			if (iBallViewId < 0) {
				return;
			} else {
				changeBuyViewByRule(3, iBallViewId);
			}
			if (Fc3dZixuanBaiweiBallTable.getHighlightBallNums() == 0
					|| Fc3dZixuanShiweiBallTable.getHighlightBallNums() == 0
					|| Fc3dZixuanGeweiBallTable.getHighlightBallNums() == 0) {
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_number));
			} else {
				changeTextSumMoney();
			}
		}
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
		if (Fc3dZixuanBaiweiBallTable.getHighlightBallNums() == 1
				&& Fc3dZixuanShiweiBallTable.getHighlightBallNums() == 1
				&& Fc3dZixuanGeweiBallTable.getHighlightBallNums() == 1) {
			int iZhuShu = iProgressBeishu;
			mTextSumMoney.setText("��ǰ�淨Ϊֱѡ��ʽ����" + iZhuShu + "ע����"
					+ (iZhuShu * 2) + "Ԫ");
		} else if (Fc3dZixuanBaiweiBallTable.getHighlightBallNums() > 1
				|| Fc3dZixuanShiweiBallTable.getHighlightBallNums() > 1
				|| Fc3dZixuanGeweiBallTable.getHighlightBallNums() > 1) {
			int iZhuShu = getZhuShu() * iProgressBeishu;
			mTextSumMoney.setText("��ǰ�淨Ϊֱѡ��ʽ����" + iZhuShu + "ע����"
					+ (iZhuShu * 2) + "Ԫ");
		}
	}

	/**
	 * ��ø����淨��ע��
	 * 
	 * @return ����ע��
	 */
	private int getZhuShu() {
		int iReturnValue = 0;
		iReturnValue = Fc3dZixuanBaiweiBallTable.getHighlightBallNums()
				* Fc3dZixuanShiweiBallTable.getHighlightBallNums()
				* Fc3dZixuanGeweiBallTable.getHighlightBallNums();
		return iReturnValue;
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
		if (aWhichGroupBall == 1) { // ��λ
			int iChosenBallSum = 10;
			Fc3dZixuanBaiweiBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			baiBallNumbers = Fc3dZixuanBaiweiBallTable.getHighlightBallNOs();
		}
		if (aWhichGroupBall == 2) { // ʮλ
			int iChosenBallSum = 10;
			Fc3dZixuanShiweiBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			shiBallNumbers = Fc3dZixuanShiweiBallTable.getHighlightBallNOs();
		}
		if (aWhichGroupBall == 3) { // ��λ
			int iChosenBallSum = 10;
			Fc3dZixuanGeweiBallTable.changeBallState(iChosenBallSum,
					aBallViewId);
			geBallNumbers = Fc3dZixuanGeweiBallTable.getHighlightBallNOs();
		}
	}
}
