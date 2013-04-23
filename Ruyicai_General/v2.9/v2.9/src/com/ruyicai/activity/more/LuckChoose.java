/**
 * 
 */
package com.ruyicai.activity.more;

import java.util.Calendar;

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
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.dialog.ExitDialogFactory;
import com.ruyicai.handler.HandlerMsg;
import com.ruyicai.handler.MyHandler;
import com.ruyicai.interfaces.ReturnPage;
import com.ruyicai.net.newtransaction.BetAndGiftInterface;
import com.ruyicai.net.newtransaction.pojo.BetAndGiftPojo;
import com.ruyicai.pojo.OneBallView;
import com.ruyicai.util.Constants;
import com.ruyicai.util.GT;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.ShellRWSharesPreferences;

/**
 * 
 * ����ѡ��
 * @author Administrator
 *
 */
public class LuckChoose extends Activity implements HandlerMsg{
	// ����ѡ��
	private static final String[] chooseLuckLotteryNum_zhonglei = { "˫ɫ��","����3D", "���ֲ�", "������", "��������͸" };
	private static final String[] chooseLuckLotteryNum_title = { "˫ɫ��", "����3D","���ֲ�", "������", "����͸" };
	private static final Integer[] mIcon = { R.drawable.join_ssq,R.drawable.join_fc3d, R.drawable.join_qlc, R.drawable.join_pl3,R.drawable.join_dlt }; // zlm 8.9 �������������������͸ͼ��
	private static final String[] textContent = { "����", "��Ф", "����", "����" };
	private static final Integer[] imageId = { R.drawable.xingzuo,R.drawable.shengxiao, R.drawable.xingming, R.drawable.shengri };
	private static final Integer[] mShengxiaoIcon = {// ��ʾ��ͼƬ����
                R.drawable.shengxiao_1_mouse, R.drawable.shengxiao_2_bull,
				R.drawable.shengxiao, R.drawable.shengxiao_4_rabbit,
				R.drawable.shengxiao_5_dragon, R.drawable.shengxiao_6_snake,
				R.drawable.shengxiao_7_horse, R.drawable.shengxiao_8_sheep,
				R.drawable.shengxiao_9_monkey, R.drawable.shengxiao_10_chicken,
				R.drawable.shengxiao_11_dog, R.drawable.shengxiao_12_pig, };
    private static final Integer[] mXingzuoIcon = { R.drawable.xingzuo_shuiping,
				R.drawable.xingzuo_shuangyu, R.drawable.xingzuo_baiyang,
				R.drawable.xingzuo_jinniu, R.drawable.xingzuo_shuangzi,
				R.drawable.xingzuo_juxie, R.drawable.xingzuo_shizi,
				R.drawable.xingzuo_chunv, R.drawable.xingzuo_tianping,
				R.drawable.xingzuo_tianxie, R.drawable.xingzuo_sheshou,
				R.drawable.xingzuo_mojie };
    private static final String[] xingzuoName = { "ˮƿ��", "˫����", "������", "��ţ��","˫����", "��з��", "ʨ����", "��Ů��", "�����", "��Ы��", "������", "ħЫ��" };
    private static final String[] shengxiaoName = { "��", "ţ", "��", "��", "��","��", "��", "��", "��", "��", "��", "��" };
	private String type01, type02, type03, type04, type05, type06, type07, type08; // ��Ʊ����
	private String[] gridText = new String[12];
	private Integer[] gridImage = new Integer[12];
	private PopupWindow popupwindow;
	private int iScreenWidth;
	private EditText editTextXingming;
	public final static int ID_CLLN_XINGMING_DIALOG_LISTVIEW = 106;
	public final static int ID_CLLN_SHOWBALLMONRY = 117;
	public final static int ID_CLLN_SHOW_ZHIFU_DIALOG = 118;
	public final static int ID_CLLN_SHOW_TRADE_SUCCESS = 119;
	private int[][] receiveRandomNum = new int[5][];
	private View view;
	TextView agreeAndpayTitleView; // ����ѡ����ȷ��ҳ��ı��� ������ �����޸ģ�7.3��ӵĴ���
	LinearLayout agreePayBallLayout01; // ������ �����޸ģ�7.3��ӵĴ���
	LinearLayout agreePayBallLayout02;
	LinearLayout agreePayBallLayout03;
	LinearLayout agreePayBallLayout04;
	LinearLayout agreePayBallLayout05;
	int iProgressBeishu = 1;
	int iProgressQishu = 1;
	int iProgressJizhu = 1;
	int countLinearLayout = 0;
	public static  int BALL_WIDTH;//С���
	private static int[] aRedColorResId = { R.drawable.red };
	private static int[] aBlueColorResId = { R.drawable.blue };
	private static int temp;
	boolean isDrawing;
	TextView mTextMoney;
	TextView mTextJizhu; // ��ע
	TextView mTextBeishu;// ����
	TextView mTextQishu;// ����
	SeekBar mSeekBarJizhu; // ע��
	SeekBar mSeekBarBeishu;// ����
	SeekBar mSeekBarQishu;// ����
	LinearLayout[] layoutAll;
	AlertDialog.Builder builderXingming; // �����ĶԻ���
//	ReturnPage returnPage;
	MyHandler handler;//�Զ���handler
	private ProgressDialog progressdialog;
	public BetAndGiftPojo betAndGift=new BetAndGiftPojo();//Ͷע��Ϣ��
	boolean isWindow = false;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		iScreenWidth = PublicMethod.getDisplayWidth(this);
		BALL_WIDTH = iScreenWidth/9;
		setContentView(showView());
		handler = new MyHandler(this);
	}

	/**
	 * ����ѡ�ŵ����б�
	 */
	public View showView() {
		LayoutInflater inflate = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		view= (LinearLayout) inflate.inflate(R.layout.ruyihelper_listview, null);
		ListView listview = (ListView) view.findViewById(R.id.ruyihelper_listview_ruyihelper_id);
		TextView text = (TextView) view.findViewById(R.id.ruyipackage_title);
		text.setText(getResources().getString(R.string.xingyunxuanhao));
		Button tvreturn = (Button) view.findViewById(R.id.ruyizhushou_btn_return);
		tvreturn.setBackgroundResource(R.drawable.returnselecter);
		tvreturn.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(popupwindow!=null){
					popupwindow.dismiss();
				}
				finish();
			}
		});
		ChooseLuckLotteryNum_EfficientAdapter adapter = new ChooseLuckLotteryNum_EfficientAdapter(this);
		listview.setAdapter(adapter);
		listview.setDividerHeight(0);
		return view;
	}
	/**
	 * ����ѡ�ŵ�������
	 */
	public class ChooseLuckLotteryNum_EfficientAdapter extends BaseAdapter {

		protected LayoutInflater mInflater; // �������б���
		Context context;
		int id;

		public ChooseLuckLotteryNum_EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			this.context = context;
		}

		@Override
		public int getCount() {
			return chooseLuckLotteryNum_title.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// �������б����е���ϸ����
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			// �벼���е���Ϣ��������
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.choose_luck_lottery_num_listview_layout_two,
						null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView.findViewById(R.id.choose_luck_lottery_num_icon_id);
				holder.icon.setImageResource(mIcon[position]);
				holder.chooseLuckLotteryNum_zhonglei_View = (TextView) convertView
						.findViewById(R.id.choose_luck_lottery_num_zhonglei);
				holder.chooseLuckLotteryNum_zhonglei_View
						.setText(chooseLuckLotteryNum_title[position]);

				// ���ð�ť
				holder.iButtonGroupLayout = (LinearLayout) convertView
						.findViewById(R.id.choose_luck_num_listview_layout_button_group);
				holder.iButtonGroupLayout.setId(position);

				int i;
				for (i = 0; i < 4; i++) {
					LinearLayout iImageButton = new LinearLayout(context);
					iImageButton.setOrientation(LinearLayout.VERTICAL);
					iImageButton.setPadding(5, 0, 0, 0);
					ImageView iImage = new ImageView(context);
					iImage.setImageResource(imageId[i]);
					iImageButton.addView(iImage);

					TextView iText = new TextView(context);
					iText.setText(textContent[i]);
					iText.setGravity(Gravity.CENTER);
					iText.setTextColor(Color.BLACK);
					iImageButton.addView(iText);

					iImageButton.setId(i + 4 * position + 100);
					if (i == 0) {
						iImageButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								showGridView(arg0, mXingzuoIcon, xingzuoName);
							}
						});
					} else if (i == 1) {
						iImageButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								showGridView(arg0, mShengxiaoIcon,shengxiaoName);
							}
						});
					} else if (i == 2) {
						iImageButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								showXingMingGialog(arg0);
							}
						});
					} else if (i == 3) {
						iImageButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								// id = arg0.getId() - LAYOUT_INDEX;
								showShengRiDialog(arg0);
							}
						});
					}

					holder.iButtonGroupLayout.addView(iImageButton);
				}
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView chooseLuckLotteryNum_zhonglei_View;
			LinearLayout iButtonGroupLayout;
		}

	}
	/**
	 * ������ʾ---��ʾ��������Ф
	 * @param v   ��ͼ
	 * @param gridIcon    ����/��Ф��ͼƬ
	 * @param gridIconName      ����/��Ф������
	 */
	protected void showGridView(View v, Integer[] gridIcon,String[] gridIconName) {	
		isWindow = true;
		gameClassify(v); // ��Ʊ�������
		gridImage = gridIcon;
		gridText = gridIconName;
		LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView= (LinearLayout) inflate.inflate(R.layout.choose_luck_lottery_num_main_grid, null);
		popupwindow = new PopupWindow(popupView, LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT, false);
		GridView gridview = (GridView) popupView.findViewById(R.id.chooose_luck_lottery_num_gridview_id);
		// ������7.3�����޸ģ��������ء�Button����ImageButton
		Button button = (Button) popupView.findViewById(R.id.chooose_luck_lottery_num_return);
		button.setBackgroundResource(R.drawable.returnselecter);
		button.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				popupwindow.dismiss();
			}

		});

		ChooseLuckLotteryNum_GridAdapter gridAdapter = new ChooseLuckLotteryNum_GridAdapter(this);
		gridview.setAdapter(gridAdapter);
		popupwindow.showAtLocation(view.findViewById(R.id.ruyihelper_listview_ruyihelper_id),Gravity.CENTER, 0, 0);
	}
	/**
	 * GridView������
	 */
	public class ChooseLuckLotteryNum_GridAdapter extends BaseAdapter {

		protected LayoutInflater mInflater; // �������б���
		Context context;

		public ChooseLuckLotteryNum_GridAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mXingzuoIcon.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.choose_luck_lottery_num_main_grid_specific,null);
				holder = new ViewHolder();
				// ���ð�ť
				holder.iButtonGroupLayout = (LinearLayout) convertView.findViewById(R.id.choose_luck_num_gridview_layout_button_group);

				LinearLayout iImageButton = new LinearLayout(context);
				iImageButton.setOrientation(LinearLayout.VERTICAL);

				ImageView iImage = new ImageView(context);
				iImage.setImageResource(gridImage[position]);
				iImageButton.addView(iImage);

				TextView iText = new TextView(context);
				iText.setText(gridText[position]);
				iText.setGravity(Gravity.CENTER);
				iText.setTextColor(Color.BLACK);
				iImageButton.addView(iText);

				iImageButton.setId(position);
				iImageButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// popupwindow.dismiss();
						showXingYunXuanHaoListView(ID_CLLN_SHOWBALLMONRY,type02);
					}
				});

				holder.iButtonGroupLayout.addView(iImageButton);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			return convertView;
		}

		class ViewHolder {
			LinearLayout iButtonGroupLayout;
		}
	}
	/**
	 * ����ѡ��
	 * @param v       ��ͼ
	 */
	protected void showXingMingGialog(View v) {
		gameClassify(v); // ��Ʊ�������

		LayoutInflater inflater = LayoutInflater.from(this);
		View textView = inflater.inflate(R.layout.choose_luck_lottery_num_xingming_dialog_layout, null);
		editTextXingming = (EditText) textView.findViewById(R.id.clln_xingming_edit_dialog_id);
		builderXingming = new AlertDialog.Builder(this);
		builderXingming.setCancelable(true);
		builderXingming.setTitle(R.string.qingshuruxingming);
		builderXingming.setView(textView);

		builderXingming.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						if(editTextXingming.length() == 0) {
							showAttentionImportNameDialog(type02);
						}else{
							showXingYunXuanHaoListView(ID_CLLN_SHOWBALLMONRY,type02);
						}
					}

				});
		builderXingming.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ����
					}

				});
		builderXingming.show();
	}
	/**
	 * ��ʾ�û����������ĶԻ���
	 */
	protected void showAttentionImportNameDialog(String aGameType) {
		type07 = aGameType;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("���������Ϣ�������ݲ���Ϊ�գ�����������������");

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						showXingYunXuanHaoListView(ID_CLLN_XINGMING_DIALOG_LISTVIEW, type07);
					}

				});

		builder.show();
	}
	/**
	 * ����ѡ��
	 * @param v    ��ͼ
	 */
	protected void showShengRiDialog(View v) {
		gameClassify(v); // ��Ʊ�������

		LayoutInflater inflater = LayoutInflater.from(this);
		View textView = inflater.inflate(
				R.layout.choose_luck_num_shengri_date_picker, null);
		DatePicker dp = (DatePicker) textView
				.findViewById(R.id.choose_luck_num_date_picker_id);
		Calendar calendar = Calendar.getInstance();
		int calYear = calendar.get(Calendar.YEAR);
		int calMonth = calendar.get(Calendar.MONTH);
		int calDay = calendar.get(Calendar.DAY_OF_MONTH);
		dp.init(calYear, calMonth, calDay, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(R.string.qingshurushengri);
		builder.setView(textView);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						showXingYunXuanHaoListView(ID_CLLN_SHOWBALLMONRY,type02);
					}
				});
		builder.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.show();
	}
	/**
	 *  ����ѡ���б�ѡ��
	 * @param listviewid
	 * @param aGameType
	 */
	protected void showXingYunXuanHaoListView(int listviewid, String aGameType) {

		switch (listviewid) {
		case ID_CLLN_SHOWBALLMONRY:
			dialogLuck(aGameType);
			break;
		case ID_CLLN_SHOW_ZHIFU_DIALOG:
			showAgreeAndPayDialog(aGameType);
			break;
		case ID_CLLN_SHOW_TRADE_SUCCESS:
			showTradeSuccess();
			break;
		}
	}

	/**
	 * ��ʾ֧���ɹ��Ի���
	 */
	protected void showTradeSuccess() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("��Ͷע�ɹ���");

		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						
					}

				});
		builder.setNegativeButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ����
					}

				});
		builder.show();
	}
	/**
	 * ����ѡ�ŶԻ���
	 */
	private void dialogLuck(String aGameType) {
		type06 = aGameType;
		String title = null;
		if (aGameType.equalsIgnoreCase("ssq")) {
			title = getResources().getString(R.string.xyxh_shuangseqiu);
			// �����޸ģ��޸ı���
		} else if (aGameType.equalsIgnoreCase("fc3d")) {
			title = getResources().getString(R.string.xyxh_fucai3d);
		} else if (aGameType.equalsIgnoreCase("qlc")) {
			title = getResources().getString(R.string.xyxh_qilecai);
		} else if (aGameType.equalsIgnoreCase("pl3")) {
			title = getResources().getString(R.string.xyxh_pailiesan);
		} else if (aGameType.equalsIgnoreCase("cjdlt")) {
			title = getResources().getString(R.string.xyxh_chaojidaletou);
		}
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(R.layout.choose_luck_num_trade_showball, null);
		TextView titleText = (TextView)view.findViewById(R.id.choose_luck_num_gametype_title);
		titleText.setText(title);
	    Button zhifu = (Button)view.findViewById(R.id.choose_luck_zhifu_dialog_ok);
		Button cancle = (Button)view.findViewById(R.id.choose_luck_zhifu_dialog_cancle);
		// ��ʼ��view
		chooseLuckNumShowBallMoney(aGameType, view);
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		zhifu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				buyLuck();
			}
		});
		dialog.show();
		dialog.getWindow().setContentView(view);
	}
	/**
	 * ����ѡ��֮ѡ�Ų���
	 */

	private void chooseLuckNumShowBallMoney(String aGameType, View view) {
		type05 = aGameType;

		// zlm 7.15 �����޸ģ����С���Ƿ�ɻ����ж�
		isDrawing = false;

		// zlm 7.13 �����޸ģ���ӳ�ʼ��
		iProgressJizhu = 1;
		iProgressBeishu = 1;
		iProgressQishu = 1;

		int iStartZhuShu = iProgressJizhu;
		int iStartBeiShu = iProgressBeishu;
		int iStartQiShu = iProgressQishu;

		mTextMoney = (TextView) view.findViewById(R.id.choose_luck_num_show_money_total);
		mTextJizhu = (TextView) view.findViewById(R.id.choose_luck_num_text_zhushu);
		mTextBeishu = (TextView) view.findViewById(R.id.choose_luck_num_text_beishu);
		mTextQishu = (TextView) view.findViewById(R.id.choose_luck_num_text_qishu);

		String iTempString = "�ܽ�"+ (iStartZhuShu * iStartBeiShu * iStartQiShu * 2) + "Ԫ\n\n"
				+ "ȷ��֧����";
		mTextMoney.setText(iTempString);
		mTextJizhu.setText("" + iProgressJizhu);
		mTextBeishu.setText("" + iProgressBeishu);
		mTextQishu.setText("" + iProgressQishu);

		mSeekBarBeishu = (SeekBar) view.findViewById(R.id.choose_luck_num_seek_beishu);
		OnSeekBarChangeListener onSeekBarChange = new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (progress < 1)
					seekBar.setProgress(1);
				int iProgress = seekBar.getProgress();

				switch (seekBar.getId()) {
				case R.id.choose_luck_num_seek_beishu:
					iProgressBeishu = iProgress;
					mTextBeishu.setText("" + iProgressBeishu);
					showTextSumMoney();
					break;
				case R.id.choose_luck_num_seek_qishu:
					iProgressQishu = iProgress;
					mTextQishu.setText("" + iProgressQishu);
					showTextSumMoney();
					break;
				}
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
		};
		mSeekBarBeishu.setOnSeekBarChangeListener( onSeekBarChange);
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mSeekBarQishu = (SeekBar) view.findViewById(R.id.choose_luck_num_seek_qishu);
		mSeekBarQishu.setOnSeekBarChangeListener(onSeekBarChange);
		mSeekBarQishu.setProgress(iProgressQishu);
		// showSsqLayout01(type05);
		// zlm 7.16 �����޸ģ��任��ʾ����
		drawSuccess(iProgressJizhu, view);

		// ������7.3������ӣ����ѡ��ע���ġ��Ӻš�

		// ������7.3������ӣ����ѡ�����ġ����š�
		ImageButton subtractBeishuBtn = (ImageButton) view.findViewById(R.id.choose_luck_num_seekbar_subtract_beishu);
		subtractBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSeekBarBeishu.setProgress(--iProgressBeishu);
			}
		});
		// ������7.3������ӣ����ѡ�����ġ��Ӻš�
		ImageButton addBeishuBtn = (ImageButton) view.findViewById(R.id.choose_luck_num_seekbar_add_beishu);
		addBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSeekBarBeishu.setProgress(++iProgressBeishu);
			}
		});
		// ������7.3������ӣ����ѡ�������ġ����š�
		ImageButton subtractQihaoBtn = (ImageButton) view.findViewById(R.id.choose_luck_num_seekbar_subtract_qishu);
		subtractQihaoBtn.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSeekBarQishu.setProgress(--iProgressQishu);
			}
		});
		// ������7.3������ӣ����ѡ�������ġ��Ӻš�
		ImageButton addQihaoBtn = (ImageButton) view.findViewById(R.id.choose_luck_num_seekbar_add_qishu);
		addQihaoBtn.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSeekBarQishu.setProgress(++iProgressQishu);
			}
		});
		
	}
	/**
	 * ע����Ǯ��
	 */
	public void showTextSumMoney() {
		int iZhuShu = iProgressJizhu;
		int iBeiShu = iProgressBeishu;
		int iQiShu = iProgressQishu;
		String iTempString = "�ܽ�" + (iZhuShu * iBeiShu * 2) + "Ԫ\n\n"+ "ȷ��֧����";
		mTextMoney.setText(iTempString);
	}
	public void drawSuccess(int aChangeTo, View view) {
		isDrawing = true;

		layoutAll = new LinearLayout[5];
		layoutAll[0] = (LinearLayout) view.findViewById(R.id.choose_luck_num_ball_linearlayout_01);
		layoutAll[1] = (LinearLayout) view.findViewById(R.id.choose_luck_num_ball_linearlayout_02);
		layoutAll[2] = (LinearLayout) view.findViewById(R.id.choose_luck_num_ball_linearlayout_03);
		layoutAll[3] = (LinearLayout) view.findViewById(R.id.choose_luck_num_ball_linearlayout_04);
		layoutAll[4] = (LinearLayout) view.findViewById(R.id.choose_luck_num_ball_linearlayout_05);
		int i;
		countLinearLayout = 0;
		for (i = 0; i < 5; i++) {
			if (layoutAll[i] != null && layoutAll[i].getChildCount() > 0) {
				countLinearLayout = countLinearLayout + 1; // zlm 7.14
				// �����޸ģ��޸���ʽ
			}
		}

		if (countLinearLayout < aChangeTo) {
			for (i = countLinearLayout; i < aChangeTo; i++) {
				// showAllBallLayout(type05 , layoutAll[i]);
				receiveRandomNum[i] = showAllBallLayout(type05, layoutAll[i]);
				layoutAll[i].invalidate();
			}
		} else {// if(countLinearLayout > iProgressJizhu)
			for (i = aChangeTo; i < countLinearLayout; i++) {
				layoutAll[i].removeAllViewsInLayout();
				layoutAll[i].invalidate();
			}
		}
	}
	/**
	 * ����ѡ��������С��Ĳ��� zlm 7.13 �����޸ģ���Ӵ���
	 * 
	 * @param aGameType
	 * @param layout
	 */
	public int[] showAllBallLayout(String aGameType, LinearLayout layout) {
		// zlm 7.16 �����޸ģ����С���Ƿ�ɻ����ж�
		isDrawing = false;
		int[] numRandomGroup = new int[7];
		if (aGameType.equalsIgnoreCase("ssq")) {
			OneBallView showBallView;
			int[] group01 = new int[7];
			int[] group02 = new int[6];
			int[] group03 = new int[6];
			int[] group = new int[7];

			group01 = getBallNum(aGameType, 7);

			for (int i = 0; i < 6; i++) {
				group02[i] = group01[i];
			}

			group03 = sort(group02);

			for (int i = 0; i < 6; i++) {
				group[i] = group03[i];
			}

			group[6] = group01[6];
			numRandomGroup = group;
			for (int i = 0; i < 6; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""+ numRandomGroup[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
			}
			showBallView = new OneBallView(this);
			showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""+ numRandomGroup[6], aBlueColorResId);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layout.addView(showBallView, lp);
			// ballLayoutGroup.addView(layout01, lp);
		} else if (aGameType.equalsIgnoreCase("fc3d")) {
			OneBallView showBallView;
			int[] group = new int[3];
			int[] group01 = new int[7];
			group01 = getBallNum(aGameType, 3);
			for (int i = 0; i < 3; i++) {
				group[i] = group01[i];
			}
			// int[] randomNumGroup ;
			numRandomGroup = group;
			for (int i = 0; i < 3; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ numRandomGroup[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
				// PublicMethod.myOutput("----------------showBall");
			}
		} else if (aGameType.equalsIgnoreCase("qlc")) {
			OneBallView showBallView;
			numRandomGroup = sort(getBallNum(aGameType, 7));
			for (int i = 0; i < 7; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ numRandomGroup[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
			}
		} else if (aGameType.equalsIgnoreCase("pl3")) { // zlm ������
			OneBallView showBallView;
			int[] group = new int[3];
			int[] group01 = new int[7];
			group01 = getBallNum(aGameType, 3);
			for (int i = 0; i < 3; i++) {
				group[i] = group01[i];
			}
			numRandomGroup = group;
			for (int i = 0; i < 3; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ numRandomGroup[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
			}
		} else if (aGameType.equalsIgnoreCase("cjdlt")) { // zlm ��������͸
			OneBallView showBallView;
			int[] group01 = new int[7];
			int[] group02 = new int[5];
			int[] group03 = new int[5];
			int[] group04 = new int[2];
			int[] group05 = new int[2];
			int[] group = new int[7];

			group01 = getBallNum(aGameType, 7);

			for (int i = 0; i < 5; i++) {
				group02[i] = group01[i];
			}

			group03 = sort(group02);

			for (int i = 0; i < 5; i++) {
				group[i] = group03[i];
			}

			group04[0] = group01[5];
			group04[1] = group01[6];

			group05 = sort(group04);

			group[5] = group05[0];
			group[6] = group05[1];
			numRandomGroup = group;

			for (int i = 0; i < 5; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ numRandomGroup[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
			}
			for (int i = 5; i < 7; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ numRandomGroup[i], aBlueColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layout.addView(showBallView, lp);
			}
		}
		return numRandomGroup;
	}
	/**
	 * ��ȡС��������/
	 * 
	 * @param aGameType
	 * @param aRandomNums
	 * @return
	 */
	public int[] getBallNum(String aGameType, int aRandomNums) {
		int[] ballNumStr = new int[7];
		// int[] ballNumStr = {1,2,3,4,5,6,7};
		if (aGameType.equalsIgnoreCase("ssq")) {
			int[] iShowRedBallNum = PublicMethod.getRandomsWithoutCollision(
					aRandomNums - 1, 1, 33);
			int[] iShowBlueBallNum = PublicMethod.getRandomsWithoutCollision(1,
					1, 16);

			for (int i = 0; i < 6; i++) {
				ballNumStr[i] = iShowRedBallNum[i];
			}
			ballNumStr[6] = iShowBlueBallNum[0];
		} else if (aGameType.equalsIgnoreCase("fc3d")
				|| aGameType.equalsIgnoreCase("pl3")) { // zlm ������
			int[] iShowBallNum01 = PublicMethod.getRandomsWithoutCollision(
					aRandomNums - 2, 0, 9);
			int[] iShowBallNum02 = PublicMethod.getRandomsWithoutCollision(
					aRandomNums - 2, 0, 9);
			int[] iShowBallNum03 = PublicMethod.getRandomsWithoutCollision(
					aRandomNums - 2, 0, 9);
			ballNumStr[0] = iShowBallNum01[0];
			ballNumStr[1] = iShowBallNum02[0];
			ballNumStr[2] = iShowBallNum03[0];
		} else if (aGameType.equalsIgnoreCase("qlc")) {
			int[] iShowRedBallNum = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 1, 30);
			ballNumStr = iShowRedBallNum;
		} else if (aGameType.equalsIgnoreCase("cjdlt")) { // zlm ��������͸
			int[] iShowRedBallNum = PublicMethod.getRandomsWithoutCollision(
					aRandomNums - 2, 1, 35);
			int[] iShowBlueBallNum = PublicMethod.getRandomsWithoutCollision(2,
					1, 12);

			for (int i = 0; i < 5; i++) {
				ballNumStr[i] = iShowRedBallNum[i];
			}
			ballNumStr[5] = iShowBlueBallNum[0];
			ballNumStr[6] = iShowBlueBallNum[1];
		}

		return ballNumStr;
	}

	/**
	 * ��������
	 * 
	 * @param t
	 * @return
	 */
	public static int[] sort(int t[]) {
		int t_s[] = t;
		// int t_a = 0;
		for (int i = 0; i < t_s.length; i++) {
			for (int j = i + 1; j < t_s.length; j++) {
				if (t_s[i] > t_s[j]) {
					temp = t_s[i];
					t_s[i] = t_s[j];
					t_s[j] = temp;
				}
			}
		}
		return t_s;
	}
	/**
	 * ���ַ��� zlm 8.11 �����޸�
	 * @param v
	 */
	public void gameClassify(View v) {
		if (Math.floor(v.getId() / 4) == 25) {
			type02 = "ssq";
		} else if (Math.floor(v.getId() / 4) == 26) {
			type02 = "fc3d";
		} else if (Math.floor(v.getId() / 4) == 27) {
			type02 = "qlc";
		} else if (Math.floor(v.getId() / 4) == 28) { // ������
			type02 = "pl3";
		} else if (Math.floor(v.getId() / 4) == 29) { // ��������͸
			type02 = "cjdlt";
		}
	}
	/**
	 * ����ѡ��֧������
	 */
	protected void buyLuck() {
		// ������ 7.4 �����޸ģ���ӵ�½���ж�
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this, "addInfo");
		String sessionidStr = shellRW.getUserLoginInfo("sessionid");
		if (sessionidStr == null || sessionidStr.equals("")) {
			Intent intentSession = new Intent(this, UserLogin.class);
			startActivityForResult(intentSession, 0);
		} else {
			ShellRWSharesPreferences pre = new ShellRWSharesPreferences(this, "addInfo");
			bet(type06,1);
			
		}
	}
	/**
	 * ����ѡ��Ͷע����
	 * 
	 */
	public void luckNet(final BetAndGiftPojo betAndGift){
		showDialog(0); // ��ʾ������ʾ�� 2010/7/4
		// �����Ƿ�ı�������ж� �³� 8.11
		Thread t = new Thread(new Runnable() {
			String str = "00";
			@Override
			public void run() {
				
				str = BetAndGiftInterface.getInstance().betOrGift(betAndGift);
				try {
					JSONObject obj = new JSONObject(str);		
					String msg = obj.getString("message");
					String error = obj.getString("error_code");
					handler.handleMsg(error,msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				progressdialog.dismiss();
			}

		});
		t.start();
	}
	/**
	 * Ͷע����ӿ�
	 * 
	 * @param aGameType
	 * @param zhuShu
	 * @param beiShu
	 * @return
	 */
	// �½ӿ� �³� 20100711
	public void bet(String aGameType,int zhuShu) {
		// ������ 7.6 �����޸ģ���Ӵ���
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(LuckChoose.this, "addInfo");
		String sessionid = pre.getUserLoginInfo("sessionid");
		String phonenum = pre.getUserLoginInfo("phonenum");
		String userno = pre.getUserLoginInfo("userno");
		String strBets = "";
		String lotno = "";
		if (!phonenum.equals("") || phonenum != null) {
			if (aGameType.equalsIgnoreCase("ssq")) {
				lotno = Constants.LOTNO_SSQ;
				int type = 0;
				int[][] iZhuShu;
				iZhuShu = changeShuZu(zhuShu, aGameType);
				strBets = GT.betCodeToString(type, zhuShu, "00", iProgressBeishu,iZhuShu);
			} else if (aGameType.equalsIgnoreCase("fc3d")) {
				lotno = Constants.LOTNO_FC3D;
				int type = 1;
				int[][] iZhuShu;
				// GT gt = new GT();
				// �����Ƿ�ı�������ж� �³� 8.11
				iZhuShu = changeShuZu(zhuShu, aGameType);
				strBets = GT.betCodeToString(type, zhuShu, "00", iProgressBeishu,iZhuShu);

			} else if (aGameType.equalsIgnoreCase("qlc")) {
				lotno = Constants.LOTNO_QLC;
				int type = 2;
				int[][] iZhuShu;
				// GT gt = new GT();
				iZhuShu = changeShuZu(zhuShu, aGameType);
				strBets = GT.betCodeToString(type, zhuShu, "00", iProgressBeishu,iZhuShu);
			} else if (aGameType.equalsIgnoreCase("pl3")) {
				lotno = Constants.LOTNO_PL3;
				int type = 3;
				int[][] iZhuShu;
				iZhuShu = changeShuZu(zhuShu, aGameType);
				strBets = GT.betCodeToStringTC(type, iZhuShu);
			} else if (aGameType.equalsIgnoreCase("cjdlt")) {
				lotno = Constants.LOTNO_DLT;
				int type = 4;
				int[][] iZhuShu;
				iZhuShu = changeShuZu(zhuShu, aGameType);
				strBets = GT.betCodeToStringTC(type, iZhuShu);
			}
			betAndGift.setSessionid( sessionid );
			betAndGift.setPhonenum(phonenum);
			betAndGift.setUserno(userno);
			betAndGift.setBet_code(strBets);
			betAndGift.setBettype("bet");// ͶעΪbet,����Ϊgift 
			betAndGift.setLotmulti(""+iProgressBeishu);//lotmulti    ����   Ͷע�ı���
			betAndGift.setBatchnum(""+iProgressQishu);//batchnum    ׷������ Ĭ��Ϊ1����׷�ţ�
			// amount      ��� ��λΪ�֣��ܽ�
	        //lotno       ���ֱ��  Ͷע���֣��磺˫ɫ��ΪF47104
			betAndGift.setSellway("0");//1�����ѡ 0������ѡ
			betAndGift.setLotno(lotno);
			betAndGift.setAmount(""+zhuShu*iProgressBeishu*200);
			luckNet(betAndGift);
		}
	}

	/**
	 * ����ѡ�ţ�����ȷ��֧��Dailog
	 */
	// ������ 7.3 �����޸ģ��ڸöԻ��������С�����ʾ
	protected void showAgreeAndPayDialog(String aGameType) {
		type06 = aGameType;

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		String[] gameType = { "ssq", "fc3d", "qlc", "pl3", "cjdlt" };
		int[] titleID = { R.string.shuangseqiu, R.string.fucai3d,R.string.qilecai, R.string.pailiesan, R.string.chaojidaletou };
		LayoutInflater inflater = LayoutInflater.from(this);
		View textView = inflater.inflate(R.layout.choose_luck_lottery_num_agree_and_pay_dialog, null);

		agreeAndpayTitleView = (TextView) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_title);
		TextView tx01 = (TextView) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text01);
		tx01.setText("��" + hour + ":" + minute + "��������ѡ���ǣ�" + "\n");

		TextView tx02 = (TextView) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text02);
		agreePayBallLayout01 = (LinearLayout) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_linearlayout01);
		agreePayBallLayout02 = (LinearLayout) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_linearlayout02);
		agreePayBallLayout03 = (LinearLayout) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_linearlayout03);
		agreePayBallLayout04 = (LinearLayout) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_linearlayout04);
		agreePayBallLayout05 = (LinearLayout) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_linearlayout05);
		// zlm 7.13 �����޸ģ��任���� zlm 8.11 �����޸�
		for (int i = 0; i < 5; i++) {
			if (type06.equalsIgnoreCase(gameType[i])) {
				agreeAndpayTitleView.setText(titleID[i]);
				showAgreeAndPayBall();
			}
		}
		TextView tx03 = (TextView) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text03);
		tx03.setText("ע����  " + "" + iProgressJizhu + "   ע" + "\n");
		TextView tx04 = (TextView) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text04);
		tx04.setText("������  " + "" + iProgressBeishu + "   ��" + "\n");
		TextView tx05 = (TextView) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text05);
		tx05.setText("׷�ţ�  " + "" + iProgressQishu + "   ��" + "\n");

		TextView tx06 = (TextView) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text06);
		tx06.setText("�ܽ�  " + "" + iProgressJizhu * iProgressBeishu * 2+ "   Ԫ" + "\n");// *iProgressQishu ȡ�� zlm 20100713

		TextView tx07 = (TextView) textView.findViewById(R.id.choose_luck_lottery_num_agree_and_pay_dialog_text07);
		tx07.setText("ȷ��֧����" + "\n");

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setView(textView);

		builder.setPositiveButton(R.string.str_return,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// wangyl 20101220 ������֤��֤
					}

				});
		builder.setNegativeButton(R.string.haveatry,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// ����
						ShellRWSharesPreferences pre = new ShellRWSharesPreferences(LuckChoose.this, "addInfo");
						String sessionIdStr = pre.getUserLoginInfo("sessionid");
						showDialog(0);// ��ʾ������ʾ�� �³�2010/7/10
					}

				});
		builder.show();
	}
	/**
	 * ȷ��֧��ҳ����ʾС��
	 */
	public void showAgreeAndPayBall() {
		// zlm 8.11 �����޸�
		LinearLayout[] agreePayBallLayoutGroup = { agreePayBallLayout01,
				agreePayBallLayout02, agreePayBallLayout03,
				agreePayBallLayout04, agreePayBallLayout05 };
		for (int i = 1; i < 6; i++) {
			if (iProgressJizhu == i) {
				for (int j = 0; j < i; j++) {
					showAgreeAndPayBallLayout(type06,agreePayBallLayoutGroup[j], receiveRandomNum[j]);
				}
			}
		}
	}
	/**
	 * ��ȷ��֧��ҳ�������С�򲼾�
	 * 
	 * @param aGameType
	 * @param aLinearLayout
	 * @param aRandomNum
	 */
	// ������ 7.3 ������ӣ��������֧���Ի�����С�����ʾ
	public void showAgreeAndPayBallLayout(String aGameType,
			LinearLayout aLinearLayout, int[] aRandomNum) {

		if (aGameType.equalsIgnoreCase("ssq")) {
			OneBallView showBallView;
			for (int i = 0; i < 6; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ aRandomNum[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				aLinearLayout.addView(showBallView, lp);
				// PublicMethod.myOutput("----------------showBall");
			}
			showBallView = new OneBallView(this);
			showBallView.initBall(BALL_WIDTH, BALL_WIDTH, "" + aRandomNum[6],
					aBlueColorResId);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			aLinearLayout.addView(showBallView, lp);
		} else if (aGameType.equalsIgnoreCase("fc3d")
				|| aGameType.equalsIgnoreCase("pl3")) {
			OneBallView showBallView;
			for (int i = 0; i < 3; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ aRandomNum[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				aLinearLayout.addView(showBallView, lp);
				// PublicMethod.myOutput("----------------showBall");
			}
		} else if (aGameType.equalsIgnoreCase("qlc")) {
			OneBallView showBallView;
			for (int i = 0; i < 7; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ aRandomNum[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				aLinearLayout.addView(showBallView, lp);
			}
		} else if (aGameType.equalsIgnoreCase("cjdlt")) { // zlm ��������͸
			OneBallView showBallView;
			for (int i = 0; i < 5; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ aRandomNum[i], aRedColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				aLinearLayout.addView(showBallView, lp);
			}
			for (int i = 5; i < 7; i++) {
				showBallView = new OneBallView(this);
				showBallView.initBall(BALL_WIDTH, BALL_WIDTH, ""
						+ aRandomNum[i], aBlueColorResId);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				aLinearLayout.addView(showBallView, lp);
			}
		}
	}

	/**
	 * ������õ�������任�ɶ�ά����
	 * 
	 * @param jiZhu
	 * @param aGameType
	 * @return
	 */
	public int[][] changeShuZu(int jiZhu, String aGameType) {
		int[][] zhuShu = null;
		if (aGameType.equalsIgnoreCase("ssq")
				|| aGameType.equalsIgnoreCase("qlc")
				|| aGameType.equalsIgnoreCase("cjdlt")) {
			switch (jiZhu) {
			case 1:
				zhuShu = new int[1][7];
				zhuShu[0] = receiveRandomNum[0];
				break;
			case 2:
				zhuShu = new int[2][7];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				break;
			case 3:
				zhuShu = new int[3][7];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				break;
			case 4:
				zhuShu = new int[4][7];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				zhuShu[3] = receiveRandomNum[3];
				break;
			case 5:
				zhuShu = new int[5][7];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				zhuShu[3] = receiveRandomNum[3];
				zhuShu[4] = receiveRandomNum[4];
				break;
			}
		} else if (aGameType.equalsIgnoreCase("fc3d")
				|| aGameType.equalsIgnoreCase("pl3")) {
			switch (jiZhu) {
			case 1:
				zhuShu = new int[1][3];
				zhuShu[0] = receiveRandomNum[0];
				break;
			case 2:
				zhuShu = new int[2][3];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				break;
			case 3:
				zhuShu = new int[3][3];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				break;
			case 4:
				zhuShu = new int[4][3];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				zhuShu[3] = receiveRandomNum[3];
				break;
			case 5:
				zhuShu = new int[5][3];
				zhuShu[0] = receiveRandomNum[0];
				zhuShu[1] = receiveRandomNum[1];
				zhuShu[2] = receiveRandomNum[2];
				zhuShu[3] = receiveRandomNum[3];
				zhuShu[4] = receiveRandomNum[4];
				break;
			}
		}
		return zhuShu;
	}
	
	/**
	 *  �������ӿ�
	 */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0: {
			progressdialog = new ProgressDialog(this);
			progressdialog.setMessage("����������...");
			progressdialog.setIndeterminate(true);
			return progressdialog;
		}
		}
		return null;
	}
	/**
	 * Ͷע����ӿ�
	 * 
	 * @param aGameType
	 * @param zhuShu
	 * @param beiShu
	 * @return
	 */
	// �½ӿ� �³� 20100711

	/**
	 * ���Ͷע����ӿ�
	 * 
	 * @param aGameType
	 * @param qiShu
	 * @param zhuShu
	 * @param beiShu
	 * @return
	 */

	public void errorCode_0000() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Ͷע�ɹ���", Toast.LENGTH_SHORT).show();
	}

	public void errorCode_000000() {
		// TODO Auto-generated method stub
		
	}

	public Context getContext() {
		// TODO Auto-generated method stub
		return this;
	}
    /**
     * ��д�Żؽ�
     */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		   case 4:
			   if(isWindow&&popupwindow!=null){
				   isWindow = false;
				   popupwindow.dismiss();
			   }else{
				   finish();
			   }
           break;
		}
		return false;
	}
}
