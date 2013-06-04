package com.ruyicai.activity.game.zc;

/**
 * @author strong miao
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.RuyicaiActivityManager;
import com.ruyicai.activity.common.UserLogin;
import com.ruyicai.activity.game.ssq.SsqActivity;
import com.ruyicai.activity.game.zc.pojo.TeamInfo;
import com.ruyicai.net.transaction.BettingInterface;
import com.ruyicai.net.transaction.FootballInterface;
import com.ruyicai.pojo.BallTable;
import com.ruyicai.util.Constants;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.util.ShellRWSharesPreferences;
import com.ruyicai.view.OneBallView;

public class ShengFC extends Activity implements SeekBar.OnSeekBarChangeListener {
	int lieNum;

	public final static String TEAM1 = "TEAM1";
	public final static String TEAM2 = "TEAM2";
	public final static String SCORES1 = "SCORES1";
	public final static String SCORES2 = "SCORES2";
	String inflater = Context.LAYOUT_INFLATER_SERVICE;

	LayoutInflater layoutInflater;
	ListViewDemo listViewDemo;
	ScrollView mHScrollView;
	LinearLayout buyView;

	ListView mlist;
	TextView mTextSumMoney;
	List<Map<String, Object>> list;

	SeekBar mSeekBarBeishu;
	TextView mTextBeishu;
	int iProgressBeishu = 1;
	ImageButton sfc_btn_touzhu;
	Vector<BallTable> ballTables = new Vector<BallTable>();
	int index;
	// ������
	private static final int DIALOG1_KEY = 0;
	private ProgressDialog progressdialog;
	String batchCode;
	String qihaoxinxi[] = new String[4];// ����ںţ���ֹʱ�䣬����
	private JSONObject obj;
	String re;
	Vector<TeamInfo> teamInfos = new Vector<TeamInfo>();
	/**
	 * ��Ϣ������
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.getData().getString("get");
			switch (msg.what) {
			case 0:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG).show();
				break;
			case 1:
				progressdialog.dismiss();
				initList();
				break;
			case 2:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG).show();
				break;
			case 3:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����������JSON��ʽ��",Toast.LENGTH_LONG).show();
				break;
			case 4:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "ϵͳ����ά�����Ժ�����", Toast.LENGTH_LONG).show();
				break;
			case 5:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ںŴ���", Toast.LENGTH_LONG).show();
				break;

			case 6:
				progressdialog.dismiss();
				FootballContantDialog.alertIssueNOFQueue(ShengFC.this);
				break;
			case 7:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "ת���쳣��", Toast.LENGTH_LONG).show();
				break;
			case 8:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG).show();
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ�����㣡",Toast.LENGTH_LONG).show();

				break;
			case 10:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "��ȡ��Ϣʧ�ܣ�", Toast.LENGTH_LONG).show();
				break;
			case 11:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "��ƱͶע�У�", Toast.LENGTH_LONG).show();
				break;
			case 12:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷע�ɹ�����Ʊ�ɹ���",Toast.LENGTH_LONG).show();

				for (int i = 0; i < ballTables.size(); i++) {
					ballTables.get(i).clearAllHighlights();
				}
				Intent intent = new Intent(UserLogin.SUCCESS);
				sendBroadcast(intent);
				break;
			case 14:
				progressdialog.dismiss();
				if(isFinishing() == false){
				    PublicMethod.showDialog(ShengFC.this);
				}
				for (int i = 0; i < ballTables.size(); i++) {
					ballTables.get(i).clearAllHighlights();
				}
				changeTextSumMoney();
				Intent intent1 = new Intent(UserLogin.SUCCESS);
				sendBroadcast(intent1);
				break;
			case 15:
				progressdialog.dismiss();
				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(ShengFC.this, UserLogin.class);
				startActivity(intentSession);
				break;
			case 16:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)	.show();
				break;
			case 17:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ�ܣ�", Toast.LENGTH_LONG).show();
				break;
			case 18:
				// ��ͼƬ�ı������û�ԭ����ͼ����ʾ�ɵ��
				sfc_btn_touzhu.setImageResource(R.drawable.imageselecter);
				break;
			case 19:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "û���ںſ���Ͷע��",Toast.LENGTH_LONG).show();
				break;
			case 20:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "���볡�δ���", Toast.LENGTH_LONG).show();
				break;
			case 21:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "���ض���Ϊ�գ�", Toast.LENGTH_LONG).show();
				break;
			case 22:
				progressdialog.dismiss();
				alertZC(re);
				break;
			case 23:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷע�ںŲ����ڣ����ѹ��ڣ�",Toast.LENGTH_LONG).show();
				break;
			}
		}
	};

static int iScreenWidth;

	public void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		iScreenWidth = PublicMethod.getDisplayWidth(this);
		/** ----- ����ȫ�� */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.layout_zc_shengfc);
		/** �������ذ�ť */
		ImageButton returnBtn = (ImageButton) findViewById(R.id.goucaitouzhu_title_return);
		returnBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		JSONObject rx9LotnoInfo = PublicMethod.getCurrentLotnoBatchCode(Constants.LOTNO_SFC);
		if (rx9LotnoInfo != null) {
			// �ɹ���ȡ�����ں���Ϣ
			try {
				batchCode = rx9LotnoInfo.getString("batchCode");
				qihaoxinxi[0] = batchCode;
				qihaoxinxi[1] = rx9LotnoInfo.getString("endTime");
				qihaoxinxi[2] = Constants.LOTNO_SFC;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		TextView title = (TextView) findViewById(R.id.layout_main_text_title);
		title.setText("ʤ����");
		TextView time = (TextView) findViewById(R.id.layout_main_text_time);

		String lottery_time = "��" + qihaoxinxi[0] + "��  ��ֹʱ�䣺" + qihaoxinxi[1];
		time.setText(lottery_time);
		batchCode = qihaoxinxi[0];
		createVeiw();
		// ��ȡ����
		getData();
	}

	/**
	 * ��ʼ���б�
	 */
	public void initList() {

		mlist = (ListView) findViewById(R.id.shengfucai_xuan);
		list = getListForMainListAdapter();
		listViewDemo = new ListViewDemo(this, list);
		mlist.setAdapter(listViewDemo);
		Drawable drawable = getResources().getDrawable(R.drawable.list_selector_red);
		mlist.setSelector(drawable);
	}

	public void createVeiw() {
		mTextSumMoney = (TextView) findViewById(R.id.sfc_text_sum_money);
		mTextSumMoney.setText(getResources().getString(R.string.please_choose_number));
		mSeekBarBeishu = (SeekBar) findViewById(R.id.sfc_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		iProgressBeishu = 1;
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mTextBeishu = (TextView) findViewById(R.id.sfc_text_beishu);
		mTextBeishu.setText("" + iProgressBeishu);
		setSeekWhenAddOrSub(R.id.sfc_seekbar_subtract_beishu, null, -1,mSeekBarBeishu, true);
		setSeekWhenAddOrSub(R.id.sfc_seekbar_add_beishu, null, 1,mSeekBarBeishu, true);

		sfc_btn_touzhu = (ImageButton) findViewById(R.id.sfc_btn_touzhu);
		sfc_btn_touzhu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				beginTouZhu(); // 1��ʾ��ǰΪ��ʽ
			}
		});
	}

	/**
	 * ����������ʾ��
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
	 * Ͷע��ʾ���е���Ϣ
	 */
	private String getTouzhuAlert() {
		int iZhuShu = getZhuShu();
		return "��" + batchCode + "��\n" + "��ֹ���ڣ�" + qihaoxinxi[1] + "\n"
				+ "ѡ�Ž����\n" + getZhuMa() + "\n" + "ע����" + iZhuShu
				/ mSeekBarBeishu.getProgress() + "ע" + "\n"
				+ // ע���������ϱ��� �³� 20100713
				"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "��"
				+ (iZhuShu * 2) + "Ԫ" + "\n" + "ȷ��֧����";

	}
	/**
	 * ����BallTable
	 * @param LinearLayoutaParentView ��һ��Layout
	 * @param int aLayoutId ��ǰBallTable��LayoutId
	 * @param int aFieldWidth BallTable����Ŀ�ȣ�����Ļ��ȣ�
	 * @param int aBallNum С�����
	 * @param int aBallViewWidth С����ͼ�Ŀ�ȣ�ͼƬ��ȣ�
	 * @param int[] aResId С��ͼƬId
	 * @param int aIdStart С��Id��ʼ��ֵ
	 * @return BallTable
	 */
	public static BallTable makeBallTable(LinearLayout aParentView, int aLayoutId,
			 int[] aResId,int aIdStart) {
        
		BallTable iBallTable = new BallTable(aParentView, aLayoutId, aIdStart);
         int aFieldWidth = iScreenWidth/3;
		int iBallViewWidth = aFieldWidth/3-2;
		int iFieldWidth = aFieldWidth;
		/** �������Ŀ�� */
		int scrollBarWidth = 6;
		/** ÿһ�е�С������ */
		int viewNumPerLine = 3;
		/** �е����� */
		int lineNum = 1;
	
		/** �հ׵Ĵ�С */
		int margin = (iFieldWidth - scrollBarWidth - (iBallViewWidth + 2)* viewNumPerLine) / 2;
		int iBallViewNo = 0;

		for (int row = 0; row < lineNum; row++) {
			TableRow tableRow = new TableRow(aParentView.getContext());
			for (int col = 0; col < viewNumPerLine; col++) {
				/** ������ʾ������ */
				String iStrTemp = "" + (iBallViewNo + 1);
				if (iStrTemp.equals("1")) {
					iStrTemp = "3";
				} else if (iStrTemp.equals("2")) {
					iStrTemp = "1";
				} else {
					iStrTemp = "0";
				}
				/** ʵ����һ��BallView���� */
				OneBallView tempBallView = new OneBallView(aParentView.getContext());
				/** Ϊ���tempView����һ��Id */
				tempBallView.setId(aIdStart + iBallViewNo);
				/** �����С���ʼ������ */
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,	aResId);

				/*** ��С��tempView��ӵ�Table�� */
				iBallTable.addBallView(tempBallView);

				TableRow.LayoutParams lp = new TableRow.LayoutParams();
				if (col == 0) {
					/** ����TableRow�ĸ�����Ŀհ����� */
					lp.setMargins(margin + 1, 1, 1, 1);
				} else if (col == viewNumPerLine - 1) {
					lp.setMargins(1, 1, margin + scrollBarWidth + 1, 1);
				} else
					lp.setMargins(1, 1, 1, 1);
				tableRow.addView(tempBallView, lp);
				/** iBallViewNo������ѭ������С������� */
				iBallViewNo++;
			}
			/** �½���TableRow��ӵ�TableLayout */
			iBallTable.tableLayout.addView(tableRow, new TableLayout.LayoutParams(PublicConst.FP, PublicConst.WC));
		}
	
		return iBallTable;
	}
	/**
	 * ���������idΪai ÿ��С���idΪai*10+С��.Resid �������ܱ�֤С��id��Ψһ��
	 */
	/** С����ʼid */
	public static final int SHENGFC_START_ID = 0x83000001;
	public int iAllBallWidth;

	public class ListViewDemo extends BaseAdapter {

		private Context context;
		private List<Map<String, Object>> mList;
		private LayoutInflater mInflater; // �������б���
		
		public ListViewDemo(Context context, List<Map<String, Object>> list) {
			this.context = context;
			mInflater = LayoutInflater.from(context);
			mList = list;
		}

		@Override
		public int getCount() {
			return mList.size();
			
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			Log.v( "Itemid",  position+"");
			return position;
			
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			int[] aResId = { R.drawable.grey, R.drawable.red };
			
			int START_ID;
			START_ID = SHENGFC_START_ID + position * 3;

			String team1 = (String) mList.get(position).get("TEAM1");
			String team2 = (String) mList.get(position).get("TEAM2");
			String scores1 = (String) mList.get(position).get("SCORES1");
			String scores2 = (String) mList.get(position).get("SCORES2");
			final int index = position;
			
			ViewHolder holder = null;
			

			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.layout_zc_shengfc_listitem, parent,false);
            holder.lie = ((TextView) convertView.findViewById(R.id.lienum));
			holder.teamname = (TextView) convertView.findViewById(R.id.teamname);
			holder.teamrank = (TextView) convertView.findViewById(R.id.teamrank);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.shengfucai_layout);
			holder.info = (ImageView) convertView.findViewById(R.id.fenxi);	
			int aFieldWidth = iScreenWidth/3;
			final BallTable shengfcRow = makeBallTable(holder.layout, R.id.shengfucai_ball, aResId,START_ID);
			if( ballTables.size() < mList.size() ){
			    ballTables.add(shengfcRow);
			}
		
			Vector<OneBallView> BallViews = shengfcRow.getBallViews();
			for (int i = 0; i < BallViews.size(); i++) {
				final OneBallView ball = BallViews.get(i);
				ball.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ball.startAnim();
						ball.changeBallColor();
						changeTextSumMoney();
					}
				});
			}
            
			if (position < 9) {
				holder.lie.setText((String.valueOf(position + 1)) + "  ");
			} else {
				holder.lie.setText((String.valueOf(position + 1)));
			}

			if (team1.length() == 2) {
				team1 = "��" + team1;
			}
			if (team2.length() == 2) {
				team2 += "��";
			}

			holder.teamname.setText(team1 + "VS" + team2);
			try {
				if (scores1 != null){
					holder.teamrank.setText("  " + scores1 + "   " + scores2);
				}

			} catch (Exception e) {
				
			}

			holder.info.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					getInfo(index);
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView lie;
			TextView teamname;
			TextView teamrank;
			ImageView info;
			LinearLayout layout;
		}

	}

	/**
	 * ���б�����Ӧ������
	 */
	private List<Map<String, Object>> getListForMainListAdapter() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		for (int i = 0; i < teamInfos.size(); i++) {
			map = new HashMap<String, Object>();
			map.put(TEAM1, teamInfos.get(i).hTeam);
			map.put(TEAM2, teamInfos.get(i).vTeam);
			map.put(SCORES1, teamInfos.get(i).hRankNum);
			map.put(SCORES2, teamInfos.get(i).vRankNum);
			list.add(map);
		}

		return list;
	}

	/**
	 * ʤ���ʵ�ע�����㷽��
	 */
	private int getZhuShu() {
		int iReturnValue = 0;
		int beishu = mSeekBarBeishu.getProgress();
		for (int i = 0; i < ballTables.size(); i++) {
			if (i != 0) {
				iReturnValue *= ballTables.get(i).getHighlightBallNums();
			} else {
				iReturnValue = ballTables.get(i).getHighlightBallNums();
			}
		}
		return iReturnValue * beishu;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
		if (progress < 1)
			seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();

		switch (seekBar.getId()) {
		case R.id.sfc_seek_beishu:
			iProgressBeishu = iProgress;
			mTextBeishu.setText("" + iProgressBeishu);
			changeTextSumMoney();
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

	/**
	 * ��ȡע��
	 */
	public String getZhuMa() {
		String t_str = "";
		for (int i = 0; i < ballTables.size(); i++) {
			Log.v("lieshu",""+ballTables.size());
			int balls[] = ballTables.get(i).getHighlightBallNOs();
			for (int j = 0; j < balls.length; j++) {
				
					t_str +=balls[j];
			
				if(i==7){
					Log.v("zhuma",""+balls[j]);
				}

			}
			if (i < ballTables.size() - 1) {
				t_str += ",";
			}
		}

		return t_str;

	}

	/**
	 * ��ʾ��Ϣ
	 */
	public void changeTextSumMoney() {

		int iZhuShu = getZhuShu();
		if (iZhuShu != 0) {
			String iTempString = "��" + iZhuShu + "ע����" + (iZhuShu * 2) + "Ԫ";
			mTextSumMoney.setText(iTempString);
		} else {
			mTextSumMoney.setText(getResources().getString(R.string.please_choose_number));
		}
	}

	public void beginTouZhu() {
		int iZhuShu = getZhuShu();
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(ShengFC.this, "addInfo");
		String sessionIdStr = pre.getUserLoginInfo("sessionid");
		if (sessionIdStr == null || sessionIdStr.equals("")) {
			Intent intentSession = new Intent(ShengFC.this, UserLogin.class);
			startActivityForResult(intentSession, 0);
			sfc_btn_touzhu.setClickable(true);
		} else {
			if (isTouZhu()) {
				Toast.makeText(ShengFC.this, "������ѡ��һע��", Toast.LENGTH_SHORT)
						.show();
			} else if (iZhuShu * 2 > 20000) {
				DialogExcessive();
			} else {
				String sTouzhuAlert = "";
				sTouzhuAlert = getTouzhuAlert();
				alert(sTouzhuAlert);
			}
		}
	}

	public boolean isTouZhu() {
		for (int i = 0; i < ballTables.size(); i++) {
			if (ballTables.get(i).getHighlightBallNums() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ����Ͷע����2��Ԫʱ�ĶԻ���
	 */
	private void DialogExcessive() {
		AlertDialog.Builder builder = new AlertDialog.Builder(ShengFC.this);
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

	/**
	 * �Ӽ���ť�¼���������
	 */
	private void setSeekWhenAddOrSub(int idFind, View iV, final int isAdd,
			final SeekBar mSeekBar, final boolean isBeiShu) {
		ImageButton subtractBeishuBtn = (ImageButton) findViewById(idFind);
		subtractBeishuBtn.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isBeiShu) {
					if (isAdd == 1) {
						iProgressBeishu++;
						if (iProgressBeishu > 99){
							iProgressBeishu = 99;
						}
						mSeekBar.setProgress(iProgressBeishu);
					} else {
						iProgressBeishu--;
						if (iProgressBeishu < 1){
							iProgressBeishu = 1;
						}
						mSeekBar.setProgress(iProgressBeishu);
					}
				}
			}
		});
	}

	// ��ʾ��1 ��������ѡ�����
	// fqc delete ɾ��ȡ����ť 7/14/2010
	private void alertZC(String re) {
		// ��������
		JSONObject re1;
		JSONObject obj;
		String hTeam8 = "";
		String vTeam8 = "";
		String avgOdds = "";
		String title = "";
		try {
			re1 = new JSONObject(re);
			obj = re1.getJSONObject("value");
			hTeam8 = obj.getString("HTeam8");
			vTeam8 = obj.getString("VTeam8");
			avgOdds = obj.getString("avgOdds");
			title += obj.getString("num");
			title += obj.getString("HTeam");
			title += "VS";
			title += obj.getString("VTeam");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LayoutInflater factory = LayoutInflater.from(this);
		View view = factory.inflate(R.layout.fenxi, null);
		TextView row1_1 = (TextView) view.findViewById(R.id.sheng_zhishu);
		TextView row1_2 = (TextView) view.findViewById(R.id.zhudui_jinshi_sheng);
		TextView row1_3 = (TextView) view.findViewById(R.id.kedui_jinshi_sheng);
		TextView row2_1 = (TextView) view.findViewById(R.id.ping_zhishu);
		TextView row2_2 = (TextView) view.findViewById(R.id.zhudui_jinshi_ping);
		TextView row2_3 = (TextView) view.findViewById(R.id.kedui_jinshi_ping);
		TextView row3_1 = (TextView) view.findViewById(R.id.fu_zhishu);
		TextView row3_2 = (TextView) view.findViewById(R.id.zhudui_jinshi_fu);
		TextView row3_3 = (TextView) view.findViewById(R.id.kedui_jinshi_fu);
		TextView row4_1 = (TextView) view.findViewById(R.id.zhudui_jinshi_jinqiu);
		TextView row4_2 = (TextView) view.findViewById(R.id.kedui_jinshi_jinqiu);
		if(!hTeam8.equals("")){
			String hteam[] = hTeam8.split("\\|");
			row1_2.setText(hteam[0]);
			row2_2.setText(hteam[1]);
			row3_2.setText(hteam[2]);
			row4_1.setText(hteam[3] + "|" + hteam[4]);
		}
		if(!vTeam8.equals("")){
			String vteam[] = vTeam8.split("\\|");
			row1_3.setText(vteam[0]);
			row2_3.setText(vteam[1]);
			row3_3.setText(vteam[2]);
			row4_2.setText(vteam[3] + "|" + vteam[4]);
		}
		if(!avgOdds.equals("")) {
			String avg[] = avgOdds.split("\\|");
			row1_1.setText(avg[0].substring(1));
			row2_1.setText(avg[1].substring(1));
			row3_1.setText(avg[2].substring(1));
		}
		Builder dialog = new AlertDialog.Builder(this).setTitle(title).setView(
				view).setPositiveButton("ȡ��",
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
		dialog.show();
	}

	/**
	 * Ͷעȷ����ʾ��
	 */
	private void alert(String string) {
		Builder dialog = new AlertDialog.Builder(this).setTitle("��ѡ�����")
			.setMessage(string).setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {
						sfc_btn_touzhu.setClickable(false);
						sfc_btn_touzhu.setImageResource(R.drawable.touzhuup_n);
						showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
						Thread t = new Thread(new Runnable() {
							int iZhuShu = getZhuShu();
							String[] strCode = null;
							int iBeiShu = mSeekBarBeishu.getProgress();
							String zhuma = getZhuMa();

							@Override
							public void run() {
								strCode = payNew(zhuma, "" + iBeiShu,iZhuShu * 2 + "", "1" + "");
								sfc_btn_touzhu.setClickable(true);
								Message msg1 = new Message();
								msg1.what = 18;
								handler.sendMessage(msg1);

								if (strCode[0].equals("0000") && strCode[1].equals("000000")) {
									Message msg = new Message();
									msg.what = 14;
									handler.sendMessage(msg);
								} else if (strCode[0].equals("070002")) {
									Message msg = new Message();
									msg.what = 15;
									handler.sendMessage(msg);
								} else if (strCode[0].equals("0000")&& strCode[1].equals("000001")) {
									Message msg = new Message();
									msg.what = 12;
									handler.sendMessage(msg);
								} else if (strCode[0].equals("1007")) {
									Message msg = new Message();
									msg.what = 10;
									handler.sendMessage(msg);
								} else if (strCode[1].equals("002002")) {
									Message msg = new Message();
									msg.what = 11;
									handler.sendMessage(msg);
								} else if (strCode[0].equals("040006")|| strCode[0].equals("201015")) {
									Message msg = new Message();
									msg.what = 9;
									handler.sendMessage(msg);
								} else if (strCode[0].equals("00")&& strCode[1].equals("00")) {
									Message msg = new Message();
									msg.what = 13;
									handler.sendMessage(msg);
								} else if (strCode[0].equals("00")&& strCode[1].equals("1002")) {
									Message msg = new Message();
									msg.what = 23;
									handler.sendMessage(msg);
								} else if (strCode[0].equals("040003")) {
									Message msg = new Message();
									msg.what = 19;
									handler.sendMessage(msg);
								} else if (strCode[0].equals("20100706")) {
									Message msg = new Message();
									msg.what = 10;
									handler.sendMessage(msg);
								}else {
									Message msg = new Message();
									msg.what = 17;
									handler.sendMessage(msg);
								}
							}

						});
						t.start();
					}
				}).setNegativeButton("ȡ��",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {

					}
				});
		dialog.show();
	}

	// Ͷע�½ӿ� 20100711�³�
	protected String[] payNew(String betCode, String lotMulti, String amount,String qiShu) {

		String[] error_code = null;
		BettingInterface betting = BettingInterface.getInstance();
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,"addInfo");
		String sessionid = shellRW.getUserLoginInfo("sessionid");
		String phonenum = shellRW.getUserLoginInfo("phonenum");
		error_code = betting.BettingTC(phonenum, qihaoxinxi[2], betCode,lotMulti, amount, "2", qiShu, sessionid, batchCode);

		return error_code;
	}

	/**
	 * ��ȡ�������
	 * 
	 * @�汾��
	 */
	public void getData() {
		showDialog(DIALOG1_KEY);
		new Thread(new Runnable() {
			@Override
			public void run() {
				String error_code = "00";
				re = FootballInterface.getInstance().getZCData(qihaoxinxi[2], qihaoxinxi[0]);
				try {
					obj = new JSONObject(re);
					error_code = obj.getString("error_code");
					if (error_code.equals("000000")) {
						JSONArray value = obj.getJSONArray("value");
						for (int i = 0; i < value.length(); i++) {
							JSONObject re = value.getJSONObject(i);
							TeamInfo team = new TeamInfo();
							team.hTeam = re.getString("HTeam");
							team.vTeam = re.getString("VTeam");
							String rank = re.getString("leagueRank");
							team.num = re.getString("num");
							if (!rank.equals("")) {
								String str[] = rank.split("\\|");
								team.hRankNum = str[0];
								team.vRankNum = str[1];
							}
							teamInfos.add(team);
						}

					}
				} catch (Exception e) {

				}

				if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);

				} else if (error_code.equals("000000")) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				} else if (error_code.equals("100000")) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				} else if (error_code.equals("200001")) {
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				} else if (error_code.equals("200002")) {
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);
				} else if (error_code.equals("200003")) {
					Message msg = new Message();
					msg.what = 5;
					handler.sendMessage(msg);
				} else if (error_code.equals("200005")) {
					Message msg = new Message();
					msg.what = 6;
					handler.sendMessage(msg);
				} else if (error_code.equals("200008")) {
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	/** ��ȡ���������� */
	public void getInfo(final int index) {
		showDialog(DIALOG1_KEY);
		new Thread(new Runnable() {
			@Override
			public void run() {
				String error_code = "00";
				re = FootballInterface.getInstance().getZCInfo(qihaoxinxi[2], qihaoxinxi[0],teamInfos.get(index).num);
				try {
					obj = new JSONObject(re);
					error_code = obj.getString("error_code");
				} catch (Exception e) {

				}
				if (error_code.equals("00")) {
					Message msg = new Message();
					msg.what = 0;
					handler.sendMessage(msg);

				} else if (error_code.equals("000000")) {
					Message msg = new Message();
					msg.what = 22;
					handler.sendMessage(msg);
				} else if (error_code.equals("100000")) {
					Message msg = new Message();
					msg.what = 2;
					handler.sendMessage(msg);
				} else if (error_code.equals("200001")) {
					Message msg = new Message();
					msg.what = 3;
					handler.sendMessage(msg);
				} else if (error_code.equals("200002")) {
					Message msg = new Message();
					msg.what = 4;
					handler.sendMessage(msg);
				} else if (error_code.equals("200003")) {
					Message msg = new Message();
					msg.what = 5;
					handler.sendMessage(msg);
				} else if (error_code.equals("200005")) {
					Message msg = new Message();
					msg.what = 6;
					handler.sendMessage(msg);
				} else if (error_code.equals("200008")) {
					Message msg = new Message();
					msg.what = 7;
					handler.sendMessage(msg);
				} else if (error_code.equals("200004")) {
					Message msg = new Message();
					msg.what = 20;
					handler.sendMessage(msg);
				} else if (error_code.equals("200006")) {
					Message msg = new Message();
					msg.what = 21;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(ballTables!=null&&ballTables.size()>0){
			for (Iterator iterator = ballTables.iterator(); iterator.hasNext();) {
				BallTable balltable = (BallTable) iterator.next();
				PublicMethod.recycleBallTable(balltable);
			}
		}
	}
}