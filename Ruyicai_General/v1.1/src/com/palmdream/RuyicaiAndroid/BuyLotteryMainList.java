package com.palmdream.RuyicaiAndroid;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class BuyLotteryMainList extends Activity implements MyDialogListener {

	public final static String[] buyLottery_lotteryName = { "˫ɫ��", "����3D",
			"���ֲ�" ,"������" ,"��������͸" };
	// public final static String[] buyLottery_lotteryIssue =
	// {"��2010026��","��2010026��","��2010026��"};

	TextView text_lotteryName; // ��Ʊ�����TextView
	// ������ 7.5 �����޸ģ�����˳����Ĵ��롪���������
	private int iQuitFlag = 0; // �����˳�

	// private boolean iCallOnKeyDownFlag=false;

	// ������ 7.5 �����޸ģ�����˳����
	/**
	 * �˳����
	 * 
	 * @param keyCode
	 *            ���ذ����ĺ���
	 * @param event
	 *            �¼�
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		PublicMethod.myOutput("--->>NoticePrizesOfLottery key:"
				+ String.valueOf(keyCode));
		switch (keyCode) {
		case 4: {
			break;
		}
			// ������ 7.8 �����޸ģ�����µ��ж�
		case 0x12345678: {
			/*
			 * if(iCallOnKeyDownFlag==false){ iCallOnKeyDownFlag=true;}
			 */
			switch (iQuitFlag) {
			case 0:
				WhetherQuitDialog iQuitDialog = new WhetherQuitDialog(this,
						this);
				iQuitDialog.show();
				break;

			}
			break;
		}
		}
		return false;
		// return super.onKeyDown(keyCode, event);
	}

	// ������ 7.5 �����޸ģ�����˳����
	/**
	 * ȡ��
	 */
	public void onCancelClick() {
		// TODO Auto-generated method stub
		// iCallOnKeyDownFlag=false;
	}

	// ������ 7.5 �����޸ģ�����˳����
	/**
	 * ȷ��
	 */
	public void onOkClick(int[] nums) {
		// TODO Auto-generated method stub
		// �˳�
		this.finish();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView buyLotteryMainListTitle;

		setContentView(R.layout.notice_prizes_main_other);
		String lotno_dlt = getLotno("information9");
		String lotno_pl3 = getLotno("information10");
		
		System.out.println("--------lotno_dlt----------" + lotno_dlt);
		System.out.println("--------lotno_pl3----------" + lotno_pl3);

		// ��ʾ���⣺����ƱͶע��
		// buyLotteryMainListTitle = (TextView)
		// findViewById(R.id.notice_prizes_title);
		// buyLotteryMainListTitle.setText(R.string.caipiaotouzhu);

		ListView listview = (ListView) findViewById(R.id.notice_prizes_listview);

		BuyLotteryEfficientAdapter adapter = new BuyLotteryEfficientAdapter(
				this);
		listview.setAdapter(adapter);

		// ���õ������
		OnItemClickListener clickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				text_lotteryName = (TextView) view
						.findViewById(R.id.buyLotteryMainList_lotteryName_id);
				String str = text_lotteryName.getText().toString();
				// ���˫ɫ�򣬾ͻ���ת��˫ɫ��Ĺ����б���
				if (getString(R.string.shuangseqiu).equals(str)) {
					// Intent intent1 = new Intent( BuyLotteryMainList.this
					// ,NoticePrizesOfLotteryShuangseqiu.class);
					Intent intent1 = new Intent(BuyLotteryMainList.this,
							ssqtest.class);
					startActivity(intent1);
				}
				// �������3D���ͻ���ת������3D�Ĺ����б���
				if (getString(R.string.fucai3d).equals(str)) {
					Intent intent1 = new Intent(BuyLotteryMainList.this,
							FC3DTest.class);
					startActivity(intent1);
				}
				// ������ֲʣ��ͻ���ת�����ֲʵĹ����б���
				if (getString(R.string.qilecai).equals(str)) {
					Intent intent1 = new Intent(BuyLotteryMainList.this,
							QLC.class);
					startActivity(intent1);
				}
				if (getString(R.string.daletou).equals(str)) {
					Intent intent1 = new Intent(BuyLotteryMainList.this,
							DLT.class);
					startActivity(intent1);
				}
				if (getString(R.string.pailie3).equals(str)) {
					
					Intent intent1 = new Intent(BuyLotteryMainList.this,PL3.class);
					startActivity(intent1);
				}

			}

		};

		listview.setOnItemClickListener(clickListener);

	}

	/**
	 * ���ò�ƱͶע��������
	 */
	public class BuyLotteryEfficientAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // �����ƱͶע�Ĳ���
		private Bitmap mShuangSeQiu;
		private Bitmap mFuCai;
		private Bitmap mQiLeCai;
		private Bitmap mDaLeTou;
		private Bitmap mPaiLieSan;
		String lotno_ssq = getLotno("information4");
		String lotno_ddd = getLotno("information5");
		String lotno_qlc = getLotno("information6");
		String lotno_dlt = getLotno("information9");
		String lotno_pl3 = getLotno("information10");
		public String[] buyLottery_lotteryIssue = { "��  " + lotno_ssq + "  ��",
				"��  " + lotno_ddd + "  ��", "��  " + lotno_qlc + "  ��" , "��  " + lotno_pl3 + "  ��" ,"��  " + lotno_dlt + "  ��"};

		public BuyLotteryEfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);

			mShuangSeQiu = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.shuangseqiu);
			mFuCai = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.fucai);
			mQiLeCai = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.qilecai);
			mDaLeTou = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.daletou);
			mPaiLieSan = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.pailiesan);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return buyLottery_lotteryName.length;
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

		// ���ò�Ʊ�����о������Ϣ
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;

			if (convertView == null) {
				// �벼���е���Ϣ��������
				convertView = mInflater.inflate(
						R.layout.buy_lottery_mainlist_layout, null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);
				holder.lotteryNameView = (TextView) convertView
						.findViewById(R.id.buyLotteryMainList_lotteryName_id);
				holder.lotteryIssueView = (TextView) convertView
						.findViewById(R.id.buyLotteryMainList_lotteryIssue_id);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (position == 0) {
				holder.icon.setImageBitmap(mShuangSeQiu);
				holder.lotteryNameView
						.setText(buyLottery_lotteryName[position]);
				holder.lotteryIssueView
						.setText(buyLottery_lotteryIssue[position]);
			}
			else if (position == 1) {
				holder.icon.setImageBitmap(mFuCai);
				holder.lotteryNameView
						.setText(buyLottery_lotteryName[position]);
				holder.lotteryIssueView
						.setText(buyLottery_lotteryIssue[position]);
			} else if (position == 2) {
				holder.icon.setImageBitmap(mQiLeCai);
				holder.lotteryNameView
						.setText(buyLottery_lotteryName[position]);
				holder.lotteryIssueView
						.setText(buyLottery_lotteryIssue[position]);
			} else if (position == 3) {
				holder.icon.setImageBitmap(mPaiLieSan);
				holder.lotteryNameView
						.setText(buyLottery_lotteryName[position]);
				holder.lotteryIssueView
						.setText(buyLottery_lotteryIssue[position]);
			} else if (position == 4) {     //8.9 zlm ��ӳ�������͸
				holder.icon.setImageBitmap(mDaLeTou);
				holder.lotteryNameView
						.setText(buyLottery_lotteryName[position]);
				holder.lotteryIssueView
						.setText(buyLottery_lotteryIssue[position]);
			}

			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView lotteryNameView;
			TextView lotteryIssueView;
		}
	}

	public String getLotno(String string) {
		String error_code;
		String batchcode = "";
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
				if (error_code.equals("0000")) {
					batchcode = obj.getString("batchCode");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return batchcode;
	}

}