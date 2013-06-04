package com.ruyicai.activity.present;

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

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.common.RuyicaiActivityManager;
import com.ruyicai.activity.game.fc3d.Fc3d;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.view.OneBallView;

/**
 * 
 * @author wangyl ���⴫��˫ɫ����ѡ
 * 
 */
public class RuyiExpressFeelSsqZixuan extends Activity implements
		OnClickListener, SeekBar.OnSeekBarChangeListener {

	// С��ͼ�Ŀ��
	public static final int BALL_WIDTH = 32;
	private int iScreenWidth;

	BallTable SSQRedZhixuanBallTable = null;// ������
	BallTable SSQBlueZhixuanBallTable = null;// ������

	// ����3Dֱѡ
	public static final int SSQ_RED_ZIXUAN_START = 0x70000010;
	public static final int SSQ_BLUE_ZIXUAN_START = 0x70000050;
	// wangyl 7.12 ���ͳɹ��󷵻ش���
	public static final int SSQ_INTENT = 1;

	private int SSQRedBallResId[] = { R.drawable.grey, R.drawable.red };
	private int SSQBlueBallResId[] = { R.drawable.grey, R.drawable.blue };

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
	int[] redBallNumbers = new int[33];
	int[] blueBallNumbers = new int[16];
	int iZhushu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);

		// ----- ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.ruyichuanqing_ssq_zixuan);
		// ������7.6����
		ImageView rtnview = (ImageView) findViewById(R.id.ruyipackage_btn_return_ssq);
		rtnview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});

		scrollView = (ScrollView) findViewById(R.id.ruyichuanqing_ssq_scrollview);

		LinearLayout iV = (LinearLayout) findViewById(R.id.ruyichuanqing_ssq_layout_id);

		{
			iScreenWidth = PublicMethod.getDisplayWidth(this);

			int redBallViewNum = 33;
			SSQRedZhixuanBallTable = makeBallTable(iV,
					R.id.table_ssq_zixuan_red, iScreenWidth, redBallViewNum, SSQRedBallResId, SSQ_RED_ZIXUAN_START, 1);

			int blueBallViewNum = 16;
			SSQBlueZhixuanBallTable = makeBallTable(iV,
					R.id.table_ssq_zixuan_blue, iScreenWidth, blueBallViewNum, SSQBlueBallResId, SSQ_BLUE_ZIXUAN_START,
					1);

			mTextSumMoney = (TextView) iV
					.findViewById(R.id.text_sum_money_ssq_zixuan);
			mTextSumMoney.setText(getResources().getString(
					R.string.please_choose_number));

			mSeekBarBeishu = (SeekBar) iV
					.findViewById(R.id.ssq_zixuan_seek_beishu);
			mSeekBarBeishu.setOnSeekBarChangeListener(this);
			mSeekBarBeishu.setProgress(iProgressBeishu);

			mTextBeishu = (TextView) iV
					.findViewById(R.id.ssq_zixuan_text_beishu);
			mTextBeishu.setText("" + iProgressBeishu);

			ImageButton addBtn = (ImageButton) iV
					.findViewById(R.id.ssq_seekbar_add_zixuan);
			ImageButton subtractBtn = (ImageButton) iV
					.findViewById(R.id.ssq_seekbar_subtract_zixuan);
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

		sureBtn = (Button) findViewById(R.id.ssq_sure_zixuan);
		sureBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				int redNums = SSQRedZhixuanBallTable.getHighlightBallNums();
				int blueNums = SSQBlueZhixuanBallTable.getHighlightBallNums();
				iZhushu = (int) getZhuShu(redBallNumbers.length,
						blueBallNumbers.length);
				if (redNums < 6 || blueNums < 1) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RuyiExpressFeelSsqZixuan.this);
					builder.setTitle(getResources().getString(
							R.string.please_choose_number));
					builder.setMessage("������ѡ��6�������1������");
					// ȷ��
					builder.setPositiveButton(R.string.ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}

							});
					builder.show();
				} else if (iZhushu * 2 > 100000) {
					DialogExcessive();
				} else {
					// iZhushu = (int) getZhuShu(redBallNumbers.length,
					// blueBallNumbers.length);
					Intent intent = new Intent(RuyiExpressFeelSsqZixuan.this,
							RuyiExpressFeelSuccess.class);
					Bundle ssqZixuanBundle = new Bundle();
					ssqZixuanBundle.putString("success", "ssqZixuan");
					ssqZixuanBundle.putIntArray("redBall", redBallNumbers);
					ssqZixuanBundle.putIntArray("blueBall", blueBallNumbers);
					ssqZixuanBundle.putInt("ssqzixuanzhushu", iZhushu);
					ssqZixuanBundle.putInt("ssqzixuanbeishu", iProgressBeishu);
					intent.putExtras(ssqZixuanBundle);
					// wangyl 7.12 ���ͳɹ��󷵻ش���
					startActivityForResult(intent, SSQ_INTENT);
				}
			}

		});

		newselected = (Button) findViewById(R.id.ssq_newselectd_zixuan);
		newselected.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				SSQRedZhixuanBallTable.clearAllHighlights();
				SSQBlueZhixuanBallTable.clearAllHighlights();
				mTextSumMoney.setText(getResources().getString(
						R.string.please_choose_number));
				mSeekBarBeishu.setProgress(1);
			}

		});

	}

	// wangyl 7.12 ���ͳɹ��󷵻ش���
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SSQ_INTENT) {
			if (resultCode == RESULT_OK) {
				RuyiExpressFeelSsqZixuan.this.finish();
			}
		}
	}

	@Override
	public void onClick(View v) {
		int iBallId = v.getId();
		if (iBallId >= SSQ_RED_ZIXUAN_START && iBallId < SSQ_BLUE_ZIXUAN_START) {
			int iBallViewId = v.getId() - SSQ_RED_ZIXUAN_START;
			if (iBallViewId < 0) {
				return;
			} else {
				changeBuyViewByRule(1, iBallViewId);// �������
			}
		} else if (iBallId >= SSQ_BLUE_ZIXUAN_START) {
			int iBallViewId = v.getId() - SSQ_BLUE_ZIXUAN_START;
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
			int aFieldWidth, int aBallNum,int[] aResId,
			int aIdStart, int aBallViewText) {
		BallTable iBallTable = new BallTable(aParentView, aLayoutId, aIdStart);

		int iBallNum = aBallNum;
		
		int iFieldWidth = aFieldWidth;
		int scrollBarWidth = 6;
		//����û��С��ĸ���Ϊ7
		int viewNumPerLine = 7;
		int iBallViewWidth = (iFieldWidth - scrollBarWidth)/viewNumPerLine-2;

		
		int lineNum = iBallNum / viewNumPerLine;
		int lastLineViewNum = iBallNum % viewNumPerLine;
		int margin = (iFieldWidth - scrollBarWidth - (iBallViewWidth + 2)
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
		int iRedHighlights = SSQRedZhixuanBallTable.getHighlightBallNums();
		int iBlueHighlights = SSQBlueZhixuanBallTable.getHighlightBallNums();

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
				long iZhuShu = getZhuShu(iRedHighlights, iBlueHighlights);
				String iTempString = "��ǰΪ��ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ";
				mTextSumMoney.setText(iTempString);
			} else {
				long iZhuShu = getZhuShu(iRedHighlights, iBlueHighlights);
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
				long iZhuShu = getZhuShu(iRedHighlights, iBlueHighlights);
				String iTempString = "��ǰΪ�츴ʽ����" + iZhuShu + "ע����"
						+ (iZhuShu * 2) + "Ԫ";
				mTextSumMoney.setText(iTempString);
			} else {
				long iZhuShu = getZhuShu(iRedHighlights, iBlueHighlights);
				String iTempString = "��ǰΪȫ��ʽ����" + iZhuShu + "ע����"
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
		long ssqZhuShu = 0L;
		if (aRedBalls > 0 && aBlueBalls > 0) {
			ssqZhuShu += (PublicMethod.zuhe(6, aRedBalls)
					* PublicMethod.zuhe(1, aBlueBalls) * iProgressBeishu);
		}
		return ssqZhuShu;
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
		if (aWhichGroupBall == 1) { // ������
			int iChosenBallSum = 20;
			SSQRedZhixuanBallTable.changeBallState(iChosenBallSum, aBallViewId);
			redBallNumbers = SSQRedZhixuanBallTable.getHighlightBallNOs();
		}
		if (aWhichGroupBall == 2) { // ������
			int iChosenBallSum = 16;
			SSQBlueZhixuanBallTable
					.changeBallState(iChosenBallSum, aBallViewId);
			blueBallNumbers = SSQBlueZhixuanBallTable.getHighlightBallNOs();
		}
	}

	/**
	 * ����Ͷע����10��Ԫʱ�ĶԻ���
	 */
	private void DialogExcessive() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				RuyiExpressFeelSsqZixuan.this);
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
