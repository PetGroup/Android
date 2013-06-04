package com.palmdream.RuyicaiAndroid;

/**
 * 
 */

/**
 * @author miao
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.palmdream.RuyicaiAndroid.BallTable;
import com.palmdream.RuyicaiAndroid.OneBallView;
import com.palmdream.RuyicaiAndroid.R;

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

public class LiuCB extends Activity implements OnClickListener,
		SeekBar.OnSeekBarChangeListener {
	/** Called when the activity is first created. */
	int lieNum;

	public final static String TEAM1 = "TEAM1";
	public final static String TEAM2 = "TEAM2";
	public final static String SCORES1 = "SCORES1";
	public final static String SCORES2 = "SCORES2";
	String inflater = Context.LAYOUT_INFLATER_SERVICE;
	/** С����ʼid */
	final int LIUCB_START_ID = 0x85000001;
	int iAllBallWidth;
	LayoutInflater layoutInflater;
	ListViewDemo listViewDemo;
	ScrollView mHScrollView;
	LinearLayout buyView;
	String str[] = new String[4];// ����ںţ���ֹʱ�䣬����
	ListView mlist;
	TextView mTextSumMoney;
	List<Map<String, Object>> list;
	private int iretrytimes = 2;
	SeekBar mSeekBarBeishu;
	TextView mTextBeishu;
	int iProgressBeishu;
	Vector<BallTable> ballTables = new Vector<BallTable>();
	ImageButton liucb_btn_touzhu;
	private JSONObject obj;
	// ������
	private static final int DIALOG1_KEY = 0;
	private ProgressDialog progressdialog;
	private int mTimesMoney = 2;
	String batchCode;
	String re;
	Vector<TeamInfo> teamInfos = new Vector<TeamInfo>();

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		/** ----- ����ȫ�� */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.liucb);

		/** �������ذ�ť */
		ImageButton returnBtn = (ImageButton) findViewById(R.id.goucaitouzhu_title_return);
		returnBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}

		});

		TextView title = (TextView) findViewById(R.id.goucaitouzhu_title);
		title.setText(getResources().getString(R.string.liuchangban));
		str = getLotno("information14");
		batchCode = str[0];
		createView();
		getData();
	}

	/**
	 * ��õ�ǰ����
	 * 
	 * @param string
	 * @return
	 */
	public String[] getLotno(String string) {
		String error_code;
		String str[] = new String[4];
		// ShellRWSharesPreferences
		ShellRWSharesPreferences shellRW = new ShellRWSharesPreferences(this,
				"addInfo");
		String notice = shellRW.getUserLoginInfo(string);
		PublicMethod.myOutput("------------------lotnossq" + notice);
		// �ж�ȡֵ�Ƿ�Ϊ�� cc 2010/7/9
		if (!notice.equals("") || notice != null) {
			try {
				JSONObject obj = new JSONObject(notice);
				error_code = obj.getString("error_code");
				if (error_code.equals("000000")) {
					JSONArray value = obj.getJSONArray("value");
					JSONObject re = value.getJSONObject(0);
					str[0] = re.getString("BATCHCODE");
					str[1] = re.getString("ENDTIME");
					str[2] = re.getString("LOTNO");

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	// ��ʼ���б�

	public void initList() {
		mlist = (ListView) findViewById(R.id.liuchangban_xuan);
		list = getListForMainListAdapter();
		listViewDemo = new ListViewDemo(this, list);
		mlist.setAdapter(listViewDemo);
		Drawable drawable = getResources().getDrawable(
				R.drawable.list_selector_red);
		mlist.setSelector(drawable);
	}

	public class ListViewDemo extends BaseAdapter {

		private Context context;
		private List<Map<String, Object>> mList;
		private LayoutInflater mInflater; // �������б���

		public ListViewDemo(Context context, List<Map<String, Object>> list) {
			this.context = context;

			mInflater = LayoutInflater.from(context);
			mList = list;
		}

		/*
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		/*
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		/*
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			int[] aResId = { R.drawable.grey, R.drawable.red };
			int ballNum = 3;
			int liuCBBallFieldWidth;
			int liuCBBallWidth = 30;
			liuCBBallFieldWidth = (liuCBBallWidth + 2) * 4;
			int START_ID;
			final int index = position;
			START_ID = LIUCB_START_ID + position * 3;
			String team1 = (String) mList.get(position).get("TEAM1");
			String team2 = (String) mList.get(position).get("TEAM2");
			String scores1 = (String) mList.get(position).get("SCORES1");
			String scores2 = (String) mList.get(position).get("SCORES2");

			ViewHolder holder = null;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.liuchangban, null);

				holder = new ViewHolder();
				holder.lie = ((TextView) convertView
						.findViewById(R.id.liuchangban_lienum));
				holder.teamnamerank1 = (TextView) convertView
						.findViewById(R.id.liuchangban_teamrank1);
				holder.teamnamerank2 = (TextView) convertView
						.findViewById(R.id.liuchangban_teamrank2);
				holder.layout = (LinearLayout) convertView
						.findViewById(R.id.liuchangban_ball_layout);
				holder.info = (ImageView) convertView
						.findViewById(R.id.liuchangban_fenxi);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			BallTable liucbRow1 = makeBallTable((LinearLayout) convertView,
					R.id.liuchangban_ball_1, liuCBBallFieldWidth, ballNum,
					liuCBBallWidth, aResId, START_ID);
			ballTables.add(liucbRow1);
			Vector<OneBallView> BallViews1 = liucbRow1.getBallViews();
			for (int i = 0; i < BallViews1.size(); i++) {
				final OneBallView ball = BallViews1.get(i);
				ball.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// shengfcRow.changeBallState(3, 1);
						ball.startAnim();
						ball.showNextId();
						changeTextSumMoney();
						// ����ע����ֵ
						int iZhuShu = getZhuShu();
					}
				});
			}
			BallTable liucbRow2 = makeBallTable((LinearLayout) convertView,
					R.id.liuchangban_ball_2, liuCBBallFieldWidth, ballNum,
					liuCBBallWidth, aResId, START_ID);
			ballTables.add(liucbRow2);
			Vector<OneBallView> BallViews2 = liucbRow2.getBallViews();
			for (int i = 0; i < BallViews2.size(); i++) {
				final OneBallView ball2 = BallViews2.get(i);
				ball2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// shengfcRow.changeBallState(3, 1);
						ball2.startAnim();
						ball2.showNextId();
						changeTextSumMoney();
						// ����ע����ֵ
						int iZhuShu = getZhuShu();
					}
				});
			}

			holder.lie.setText((String.valueOf(position + 1)));

			if (scores1 == null || scores2.equals(null)) {
				holder.teamnamerank1.setText(team1);
				holder.teamnamerank2.setText(team2);

			} else {
				holder.teamnamerank1.setText(team1 + "[" + scores1 + "]");
				holder.teamnamerank2.setText(team2 + "[" + scores2 + "]");
			}
			holder.info.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					getInfo(index);
				}
			});
			return convertView;

		}

		class ViewHolder {
			TextView lie;
			TextView teamnamerank1;
			TextView teamnamerank2;
			LinearLayout layout;
			ImageView info;
		}

	}

	/**
	 * ����BallTable
	 * 
	 * @param LinearLayout
	 *            aParentView ��һ��Layout
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
		/** �������Ŀ�� */
		int scrollBarWidth = 6;
		/** ÿһ�е�С������ */
		int viewNumPerLine = (iFieldWidth - scrollBarWidth)
				/ (iBallViewWidth + 2);
		/** �е����� */
		int lineNum = iBallNum / viewNumPerLine;
		/** ���һ�е�view����Ŀ */
		int lastLineViewNum = iBallNum % viewNumPerLine;
		/** �հ׵Ĵ�С */
		int margin = (iFieldWidth - scrollBarWidth - (aBallViewWidth + 2)
				* viewNumPerLine) / 2;
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
				OneBallView tempBallView = new OneBallView(aParentView
						.getContext());
				/** Ϊ���tempView����һ��Id */
				tempBallView.setId(aIdStart + iBallViewNo);
				/** �����С���ʼ������ */
				tempBallView.initBall(iBallViewWidth, iBallViewWidth, iStrTemp,
						aResId);

				/** Ϊ��ʼ����С�����ü��� */
				tempBallView.setOnClickListener(this);
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

	/**
	 * ���б�����Ӧ������
	 */
	private List<Map<String, Object>> getListForMainListAdapter() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		// String iLayoutType ;

		// zlm 7.16 �����޸ģ���ӿ�������

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
	 * ��ȡ�������
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
	public void getData() {
		showDialog(DIALOG1_KEY);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				while (iretrytimes < 3 && iretrytimes > 0) {
					re = jrtLot.getZCData(str[2], str[0]);
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
						iretrytimes = 3;
					} catch (Exception e) {
						iretrytimes--;

					}

					if (iretrytimes == 0 && iHttp.whetherChange == false) {
						iHttp.whetherChange = true;
						if (iHttp.conMethord == iHttp.CMWAP) {
							iHttp.conMethord = iHttp.CMNET;
						} else {
							iHttp.conMethord = iHttp.CMWAP;
						}
						iretrytimes = 2;
						while (iretrytimes < 3 && iretrytimes > 0) {
							re = jrtLot.getZCData(str[2], str[0]);
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
										String rank = re
												.getString("leagueRank");
										team.num = re.getString("num");
										if (!rank.equals("")) {

											String str[] = rank.split("\\|");
											team.hRankNum = str[0];
											team.vRankNum = str[1];
										}
										teamInfos.add(team);
									}

								}
								iretrytimes = 3;
							} catch (Exception e) {

								iretrytimes--;
								error_code = "000";

							}
						}
					}
				}
				iretrytimes = 2;
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

	/**
	 * ��Ϣ������
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String result = msg.getData().getString("get");
			switch (msg.what) {
			case 0:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)
						.show();
				break;
			case 1:
				progressdialog.dismiss();
				initList();
				createView();
				break;
			case 2:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)
						.show();
				break;
			case 3:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����������JSON��ʽ��",
						Toast.LENGTH_LONG).show();
				break;

			case 4:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "������ִ���", Toast.LENGTH_LONG)
						.show();
				break;
			case 5:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ںŴ���", Toast.LENGTH_LONG)
						.show();
				break;

			case 6:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ںŶ�Ӧ�Ķ����¼Ϊ�գ�",
						Toast.LENGTH_LONG).show();
				break;
			case 7:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "ת���쳣��", Toast.LENGTH_LONG)
						.show();
				break;
			case 8:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)
						.show();
				break;
			case 9:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ�����㣡", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾע��ɹ�
				break;
			case 10:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����ѽ�����", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ�û���ע��
				break;
			case 11:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "��ƱͶע�У�", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾϵͳ���㣬���Ժ�
				break;
			case 12:
				progressdialog.dismiss();
				Toast
						.makeText(getBaseContext(), "Ͷע�ɹ�����Ʊ�ɹ���",
								Toast.LENGTH_LONG).show();

				for (int i = 0; i < ballTables.size(); i++) {
					ballTables.get(i).clearAllHighlights();
				}

				break;
			case 14:
				// //��Ҫ���AlertDialog��ʾ�û���¼�ɹ�
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷע������", Toast.LENGTH_LONG)
						.show();
				// Ͷע�ɹ������С��

				for (int i = 0; i < ballTables.size(); i++) {
					ballTables.get(i).clearAllHighlights();
				}
				break;
			case 15:
				progressdialog.dismiss();

				// 30���Ӻ��ٴε�¼ �³� 20100719
				Intent intentSession = new Intent(LiuCB.this, UserLogin.class);
				startActivity(intentSession);
				break;
			case 16:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "�����쳣��", Toast.LENGTH_LONG)
						.show();
				// //��Ҫ���AlertDialog��ʾ��¼ʧ��
				break;
			case 17:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷעʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 18:
				// ��ͼƬ�ı������û�ԭ����ͼ����ʾ�ɵ��
				liucb_btn_touzhu.setImageResource(R.drawable.imageselecter);
				break;
			case 19:

				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "û���ںſ���Ͷע��", Toast.LENGTH_LONG)
						.show();
				break;
			case 20:

				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "���볡�δ���", Toast.LENGTH_LONG)
						.show();
				break;
			case 21:

				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "���ض���Ϊ�գ�", Toast.LENGTH_LONG)
						.show();
				break;
			case 22:

				progressdialog.dismiss();
				alertZC(re);
				break;
			case 23:
				progressdialog.dismiss();
				Toast.makeText(getBaseContext(), "Ͷע�ںŲ����ڣ����ѹ��ڣ�",
						Toast.LENGTH_LONG).show();
				break;

			}
		}
	};

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LayoutInflater factory = LayoutInflater.from(this);
		View view = factory.inflate(R.layout.fenxi, null);
		if (!hTeam8.equals("") && !vTeam8.equals("") && !avgOdds.equals("")) {

			String avg[] = avgOdds.split("\\|");
			String hteam[] = hTeam8.split("\\|");
			String vteam[] = vTeam8.split("\\|");
			TextView row1_1 = (TextView) view.findViewById(R.id.sheng_zhishu);
			TextView row1_2 = (TextView) view
					.findViewById(R.id.zhudui_jinshi_sheng);
			TextView row1_3 = (TextView) view
					.findViewById(R.id.kedui_jinshi_sheng);
			TextView row2_1 = (TextView) view.findViewById(R.id.ping_zhishu);
			TextView row2_2 = (TextView) view
					.findViewById(R.id.zhudui_jinshi_ping);
			TextView row2_3 = (TextView) view
					.findViewById(R.id.kedui_jinshi_ping);
			TextView row3_1 = (TextView) view.findViewById(R.id.fu_zhishu);
			TextView row3_2 = (TextView) view
					.findViewById(R.id.zhudui_jinshi_fu);
			TextView row3_3 = (TextView) view
					.findViewById(R.id.kedui_jinshi_fu);
			TextView row4_1 = (TextView) view
					.findViewById(R.id.zhudui_jinshi_jinqiu);
			TextView row4_2 = (TextView) view
					.findViewById(R.id.kedui_jinshi_jinqiu);
			row1_1.setText(avg[0].substring(1));
			row1_2.setText(hteam[0]);
			row1_3.setText(vteam[0]);
			row2_1.setText(avg[1].substring(1));
			row2_2.setText(hteam[1]);
			row2_3.setText(vteam[1]);
			row3_1.setText(avg[2].substring(1));
			row3_2.setText(hteam[2]);
			row3_3.setText(vteam[2]);
			row4_1.setText(hteam[3] + "|" + hteam[4]);
			row4_2.setText(vteam[3] + "|" + vteam[4]);
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
	 * ��������ʱ��Ϊʤ���������ļ��㷽�� ˫ɫ�������ļ��㷽��
	 * 
	 * @return int ����ע��
	 */
	private int getQiShu() {
		int iReturnValue = 0;

		return iReturnValue;
	}

	public void createView() {

		mTextSumMoney = (TextView) findViewById(R.id.liucb_text_sum_money);
		mTextSumMoney.setText(getResources().getString(
				R.string.please_choose_number));
		mSeekBarBeishu = (SeekBar) findViewById(R.id.liucb_seek_beishu);
		mSeekBarBeishu.setOnSeekBarChangeListener(this);
		iProgressBeishu = 1;
		mSeekBarBeishu.setProgress(iProgressBeishu);
		mTextBeishu = (TextView) findViewById(R.id.liucb_text_beishu);
		mTextBeishu.setText("" + iProgressBeishu);
		setSeekWhenAddOrSub(R.id.liucb_seekbar_subtract_beishu, null, -1,
				mSeekBarBeishu, true);
		setSeekWhenAddOrSub(R.id.liucb_seekbar_add_beishu, null, 1,
				mSeekBarBeishu, true);

		liucb_btn_touzhu = (ImageButton) findViewById(R.id.liucb_btn_touzhu);
		liucb_btn_touzhu.setOnClickListener(new OnClickListener() {
			@Override
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

	/*
	 * Ͷע��ʾ���е���Ϣ
	 */
	private String getTouzhuAlert() {
		int iZhuShu = getZhuShu();
		return "��" + batchCode + "��\n" + "��ֹ���ڣ�" + str[1] + "\n" + "ѡ�Ž����\n"
				+ getZhuMa() + "\n" + "ע����" + iZhuShu
				/ mSeekBarBeishu.getProgress() + "ע" + "\n"
				+ // ע���������ϱ��� �³� 20100713
				"������" + mSeekBarBeishu.getProgress() + "��" + "\n" + "��"
				+ (iZhuShu * 2) + "Ԫ" + "\n" + "ȷ��֧����";

	}

	/**
	 *Ϊÿһ�����ü����¼�
	 * */
	public void onListItemClickListener(ListView l, View view,
			final int position, long id) {
		LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(
				R.layout.shengfucai, null);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	/**
	 * �������ע�����㷽��
	 * 
	 * 
	 * 
	 * */
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
		for (int i = 0; i < ballTables.size(); i++) {
			int balls[] = ballTables.get(i).getHighlightBallNOs();
			for (int j = 0; j < balls.length; j++) {
				if (balls[j] == 1) {
					t_str += 3;
				} else if (balls[j] == 2) {
					t_str += 1;
				} else if (balls[j] == 3) {
					t_str += 0;
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

	//
	public void beginTouZhu() {
		int iZhuShu = getZhuShu();
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(LiuCB.this,
				"addInfo");
		String sessionIdStr = pre.getUserLoginInfo("sessionid");
		if (sessionIdStr.equals("")) {
			Intent intentSession = new Intent(LiuCB.this, UserLogin.class);
			startActivityForResult(intentSession, 0);
			liucb_btn_touzhu.setClickable(true);
		} else {

			if (isTouZhu()) {
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

	//
	public boolean isTouZhu() {
		for (int i = 0; i < ballTables.size(); i++) {
			if (ballTables.get(i).getHighlightBallNums() == 0) {
				return true;
			}
		}
		return false;
	}

	// /**
	// * ����Ͷע����2��Ԫʱ�ĶԻ���
	// */
	private void DialogExcessive() {
		AlertDialog.Builder builder = new AlertDialog.Builder(LiuCB.this);
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
		ImageButton subtractBeishuBtn = (ImageButton) findViewById(idFind);
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
				}
			}
		});
	}

	// ��ʾ��1 ��������ѡ�����
	// // fqc delete ɾ��ȡ����ť 7/14/2010
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
								liucb_btn_touzhu.setClickable(false);
								liucb_btn_touzhu
										.setImageResource(R.drawable.touzhuup_n);
								iHttp.whetherChange = false;
								showDialog(DIALOG1_KEY); // ��ʾ������ʾ�� 2010/7/4
								// TODO Auto-generated method stub
								Thread t = new Thread(new Runnable() {
									int iZhuShu = getZhuShu();
									String[] strCode = null;
									int iBeiShu = mSeekBarBeishu.getProgress();
									String zhuma = getZhuMa();

									@Override
									public void run() {

										if (mTimesMoney == 2) {
											strCode = payNew(zhuma, ""
													+ iBeiShu,
													iZhuShu * 2 + "", "1" + "");
										} else if (mTimesMoney == 3) {
											strCode = payNew(zhuma, ""
													+ iBeiShu,
													iZhuShu * 3 + "", "1" + "");
										}
										liucb_btn_touzhu.setClickable(true);

										Message msg1 = new Message();
										msg1.what = 18;
										handler.sendMessage(msg1);

										Log.v("+++++strCode[]++++LiuCB++",
												strCode[0] + "<>" + strCode[1]);
										if (strCode[0].equals("0000")
												&& strCode[1].equals("000000")) {
											Message msg = new Message();
											msg.what = 14;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("070002")) {
											Message msg = new Message();
											msg.what = 15;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("0000")
												&& strCode[1].equals("000001")) {
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
										} else if (strCode[0].equals("040006")
												|| strCode[0].equals("201015")) {
											Message msg = new Message();
											msg.what = 9;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("00")
												&& strCode[1].equals("00")) {
											Message msg = new Message();
											msg.what = 13;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("040003")) {
											Message msg = new Message();
											msg.what = 19;
											handler.sendMessage(msg);
										} else if (strCode[0].equals("00")
												&& strCode[1].equals("1002")) {
											Message msg = new Message();
											msg.what = 23;
											handler.sendMessage(msg);
										} else {
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
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}

						});
		dialog.show();

	}

	//
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
			error_code = betting.BettingTC(phonenum, str[2], betCode, lotMulti,
					amount, "2", qiShu, sessionid, batchCode);
		} else if (mTimesMoney == 3) {
			error_code = betting.BettingTC(phonenum, str[2], betCode, lotMulti,
					amount, "3", qiShu, sessionid, batchCode);
		}

		return error_code;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		if (progress < 1)
			seekBar.setProgress(1);
		int iProgress = seekBar.getProgress();

		switch (seekBar.getId()) {
		case R.id.liucb_seek_beishu:
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
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	/** ��ȡ���������� */
	public void getInfo(final int index) {
		showDialog(DIALOG1_KEY);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String error_code = "00";
				while (iretrytimes < 3 && iretrytimes > 0) {

					re = jrtLot.getZCInfo(str[2], str[0],
							teamInfos.get(index).num);

					try {
						obj = new JSONObject(re);
						error_code = obj.getString("error_code");
						iretrytimes = 3;
					} catch (Exception e) {
						iretrytimes--;

					}

					if (iretrytimes == 0 && iHttp.whetherChange == false) {
						iHttp.whetherChange = true;
						if (iHttp.conMethord == iHttp.CMWAP) {
							iHttp.conMethord = iHttp.CMNET;
						} else {
							iHttp.conMethord = iHttp.CMWAP;
						}
						iretrytimes = 2;
						while (iretrytimes < 3 && iretrytimes > 0) {
							re = jrtLot.getZCInfo(str[2], str[0], teamInfos
									.get(index).num);
							try {
								obj = new JSONObject(re);
								error_code = obj.getString("error_code");
								iretrytimes = 3;
							} catch (Exception e) {

								iretrytimes--;
								error_code = "000";

							}
						}
					}
				}
				iretrytimes = 2;
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

	public class TeamInfo {
		String hTeam;
		String vTeam;
		String hRankNum;
		String vRankNum;
		String num;
	}

}
