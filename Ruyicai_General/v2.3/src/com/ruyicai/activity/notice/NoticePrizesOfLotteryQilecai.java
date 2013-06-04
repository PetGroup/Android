package com.ruyicai.activity.notice;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.common.RuyicaiActivityManager;

public class NoticePrizesOfLotteryQilecai extends Activity {

	private static final String[] qilecai_LotteryIssue = { "��2010026��",
			"��2010025��", "��2010024��", "��201023��" };
	private static final String[] qilecai_NoticeDateAndTime = { "2010-06-01",
			"2010-06-01", "2010-06-01", "2010-06-01" };
	private static final String[] qilecai_WinningNum = {
			"�н����룺01,02,03,04,05,06,07", "�н����룺01,02,03,04,05,06,07",
			"�н����룺01,02,03,04,05,06,07", "�н����룺01,02,03,04,05,06,07" };
	private static final String[] qilecai_FinalPrizesDate = {
			"�ά���ڣ�2010-08-01", "�ά���ڣ�2010-08-01", "�ά���ڣ�2010-08-01",
			"�ά���ڣ�2010-08-01" };
	private static final String[] qilecai_TotalSum = { "�ܽ�100��Ԫ",
			"�ܽ�100��Ԫ", "�ܽ�100��Ԫ", "�ܽ�100��Ԫ" };

	TextView noticePrizesTitle;
	TextView attention;

	public void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.notice_prizes_single_specific_main);

		noticePrizesTitle = (TextView) findViewById(R.id.notice_prizes_single_specific_title_id);
		noticePrizesTitle.setText(R.string.qilecai_kaijianggonggao);

		attention = (TextView) findViewById(R.id.notice_prizes_single_specific_attention);
		attention.setText(R.string.qilecai_attention);

		ListView listview = (ListView) findViewById(R.id.notice_prizes_single_specific_listview);

		EfficientAdapter adapter = new EfficientAdapter(this);
		listview.setAdapter(adapter);
	}

	public static class EfficientAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public EfficientAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return qilecai_LotteryIssue.length;
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
				convertView = mInflater.inflate(
						R.layout.notice_prizes_single_specific_layout, null);
				holder = new ViewHolder();
				holder.lotteryIssueView = (TextView) convertView
						.findViewById(R.id.notice_prizes_single_specific_issue_id);
				holder.noticeDateAndTimeView = (TextView) convertView
						.findViewById(R.id.notice_prizes_single_specific_noticedDate_id);

				holder.finalPrizesDateView = (TextView) convertView
						.findViewById(R.id.notice_prizes_single_specific_finalPrizesDate_id);
				holder.totalSumView = (TextView) convertView
						.findViewById(R.id.notice_prizes_single_specific_totalSum_id);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == 1) {
				holder.lotteryIssueView.setText(qilecai_LotteryIssue[position]);
				holder.noticeDateAndTimeView
						.setText(qilecai_NoticeDateAndTime[position]);
				holder.winningNumView.setText(qilecai_WinningNum[position]);
				holder.finalPrizesDateView
						.setText(qilecai_FinalPrizesDate[position]);
				holder.totalSumView.setText(qilecai_TotalSum[position]);
			} else if (position == 2) {
				holder.lotteryIssueView.setText(qilecai_LotteryIssue[position]);
				holder.noticeDateAndTimeView
						.setText(qilecai_NoticeDateAndTime[position]);
				holder.winningNumView.setText(qilecai_WinningNum[position]);
				holder.finalPrizesDateView
						.setText(qilecai_FinalPrizesDate[position]);
				holder.totalSumView.setText(qilecai_TotalSum[position]);
			} else if (position == 3) {
				holder.lotteryIssueView.setText(qilecai_LotteryIssue[position]);
				holder.noticeDateAndTimeView
						.setText(qilecai_NoticeDateAndTime[position]);
				holder.winningNumView.setText(qilecai_WinningNum[position]);
				holder.finalPrizesDateView
						.setText(qilecai_FinalPrizesDate[position]);
				holder.totalSumView.setText(qilecai_TotalSum[position]);
			} else {
				holder.lotteryIssueView.setText(qilecai_LotteryIssue[position]);
				holder.noticeDateAndTimeView
						.setText(qilecai_NoticeDateAndTime[position]);
				holder.winningNumView.setText(qilecai_WinningNum[position]);
				holder.finalPrizesDateView
						.setText(qilecai_FinalPrizesDate[position]);
				holder.totalSumView.setText(qilecai_TotalSum[position]);
			}

			return convertView;
		}

		static class ViewHolder {
			TextView lotteryIssueView;
			TextView noticeDateAndTimeView;
			TextView winningNumView;
			TextView finalPrizesDateView;
			TextView totalSumView;
		}
	}

}
