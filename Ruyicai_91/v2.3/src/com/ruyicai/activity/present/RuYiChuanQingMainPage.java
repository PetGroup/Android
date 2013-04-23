package com.ruyicai.activity.present;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.RuyicaiActivityManager;
import com.ruyicai.activity.notice.NoticePrizesOfLotteryFucai;
import com.ruyicai.activity.notice.NoticePrizesOfLotteryQilecai;
import com.ruyicai.activity.notice.NoticePrizesOfLotteryShuangseqiu;

public class RuYiChuanQingMainPage extends Activity {
	public final static String[] ruyichuanqing_listName = { "���⴫��", "�����ײ�",
			"����ѡ��" };

	TextView text_ruyichuanqing_listName; // ���⴫�������б���ÿһ������Ƶ�TextView

	public void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);


		setContentView(R.layout.notice_prizes_main_other);

		// �������⴫��ı��⣺���⴫��(˫ɫ��)
		// buyLotteryMainListTitle = (TextView)
		// findViewById(R.id.notice_prizes_title);
		// buyLotteryMainListTitle.setText(R.string.ruyichuanqing_shuangseqiu);

		ListView listview = (ListView) findViewById(R.id.notice_prizes_listview);

		RuYiChuanQingEfficientAdapter adapter = new RuYiChuanQingEfficientAdapter(
				this);
		listview.setAdapter(adapter);

		// ���õ������
		OnItemClickListener clickListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				text_ruyichuanqing_listName = (TextView) view
						.findViewById(R.id.ruyichuanqing_mainPage_listName_id);
				String str = text_ruyichuanqing_listName.getText().toString();
				// ������⴫�飬����ת�����⴫����б���
				if (getString(R.string.ruyichuanqing).equals(str)) {
					Intent intent1 = new Intent(RuYiChuanQingMainPage.this,
							NoticePrizesOfLotteryShuangseqiu.class);
					startActivity(intent1);
				}
				// ��������ײͣ�����ת�������ײ͵��б���
				if (getString(R.string.ruyitaocan).equals(str)) {
					Intent intent1 = new Intent(RuYiChuanQingMainPage.this,
							NoticePrizesOfLotteryFucai.class);
					startActivity(intent1);
				}
				// �������ѡ�ţ�����ת������ѡ�ŵ��б���
				if (getString(R.string.xingyunxuanhao).equals(str)) {
					Intent intent1 = new Intent(RuYiChuanQingMainPage.this,
							NoticePrizesOfLotteryQilecai.class);
					startActivity(intent1);
				}

			}

		};

		listview.setOnItemClickListener(clickListener);

	}

	// �������⴫���������
	public static class RuYiChuanQingEfficientAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private Bitmap mRuYiChuanQing;
		private Bitmap mRuYiTaoCan;
		private Bitmap mXingYunXuanHao;

		public RuYiChuanQingEfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);

//			mRuYiChuanQing = BitmapFactory.decodeResource(context
//					.getResources(), R.drawable.ruyichuanqing);
//			mRuYiTaoCan = BitmapFactory.decodeResource(context.getResources(),
//					R.drawable.ruyitaocan);
//			mXingYunXuanHao = BitmapFactory.decodeResource(context
//					.getResources(), R.drawable.xingyunxuanhao);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ruyichuanqing_listName.length;
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

		// �������⴫�鲼�ֵ���ϸ��Ϣ
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;

			// �벼�ֵ���Ϣ�����
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.ruyichuanqing_mainpage_layout, null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView
						.findViewById(R.id.ruyichuanqing_icon_id);
				holder.ruyichuanqing_listNameView = (TextView) convertView
						.findViewById(R.id.ruyichuanqing_mainPage_listName_id);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == 1) {
				//holder.icon.setImageBitmap(mRuYiChuanQing);
				holder.ruyichuanqing_listNameView
						.setText(ruyichuanqing_listName[position]);
			} else if (position == 2) {
				//holder.icon.setImageBitmap(mRuYiTaoCan);
				holder.ruyichuanqing_listNameView
						.setText(ruyichuanqing_listName[position]);
			} else {
				//holder.icon.setImageBitmap(mXingYunXuanHao);
				holder.ruyichuanqing_listNameView
						.setText(ruyichuanqing_listName[position]);
			}

			return convertView;
		}

		static class ViewHolder {
			ImageView icon;
			TextView ruyichuanqing_listNameView;
		}
	}

}
