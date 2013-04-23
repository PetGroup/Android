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

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.activity.common.RuyicaiActivityManager;

public class NoticePrizesOfLotteryFucai extends Activity {

	private static final String[] fucai_LotteryIssue = { "��2010026��",
			"��2010025��", "��2010024��", "��2010023��" };
	private static final String[] fucai_NoticeDateAndTime = { "2010-06-01",
			"2010-06-01", "2010-06-01", "2010-06-01" };
	private static final String[] fucai_WinningNum = {
			"�н����룺01,02,03,04,05,06,07", "�н����룺01,02,03,04,05,06,07",
			"�н����룺01,02,03,04,05,06,07", "�н����룺01,02,03,04,05,06,07" };
	private static final String[] fucai_FinalPrizesDate = { "�ά���ڣ�2010-08-01",
			"�ά���ڣ�2010-08-01", "�ά���ڣ�2010-08-01", "�ά���ڣ�2010-08-01" };
	private static final String[] fucai_TotalSum = { "�ܽ�100��Ԫ", "�ܽ�100��Ԫ",
			"�ܽ�100��Ԫ", "�ܽ�100��Ԫ" };

	TextView noticePrizesTitle;
	TextView attention;

	public void onCreate(Bundle savedInstanceState) {
//		RuyicaiActivityManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.notice_prizes_single_specific_main);

		noticePrizesTitle = (TextView) findViewById(R.id.notice_prizes_single_specific_title_id);
		noticePrizesTitle.setText(R.string.fucai3d_kaijianggonggao);

		attention = (TextView) findViewById(R.id.notice_prizes_single_specific_attention);
		attention.setText(R.string.fucai3d_attention);

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
			return fucai_LotteryIssue.length;
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

			/*
			 * for( int i = position; i<5 ;i++){
			 * holder.lotteryIssueView.setText(fucai_LotteryIssue[i]);
			 * holder.noticeDateAndTimeView.setText(fucai_NoticeDateAndTime[i]);
			 * holder.winningNumView.setText(fucai_WinningNum[i]);
			 * holder.finalPrizesDateView.setText(fucai_FinalPrizesDate[i]);
			 * holder.totalSumView.setText(fucai_TotalSum[i]); }
			 */
			if (position == 1) {
				holder.lotteryIssueView.setText(fucai_LotteryIssue[position]);
				holder.noticeDateAndTimeView
						.setText(fucai_NoticeDateAndTime[position]);
				holder.winningNumView.setText(fucai_WinningNum[position]);
				holder.finalPrizesDateView
						.setText(fucai_FinalPrizesDate[position]);
				holder.totalSumView.setText(fucai_TotalSum[position]);
			} else if (position == 2) {
				holder.lotteryIssueView.setText(fucai_LotteryIssue[position]);
				holder.noticeDateAndTimeView
						.setText(fucai_NoticeDateAndTime[position]);
				holder.winningNumView.setText(fucai_WinningNum[position]);
				holder.finalPrizesDateView
						.setText(fucai_FinalPrizesDate[position]);
				holder.totalSumView.setText(fucai_TotalSum[position]);
			} else if (position == 3) {
				holder.lotteryIssueView.setText(fucai_LotteryIssue[position]);
				holder.noticeDateAndTimeView
						.setText(fucai_NoticeDateAndTime[position]);
				holder.winningNumView.setText(fucai_WinningNum[position]);
				holder.finalPrizesDateView
						.setText(fucai_FinalPrizesDate[position]);
				holder.totalSumView.setText(fucai_TotalSum[position]);
			} else {
				holder.lotteryIssueView.setText(fucai_LotteryIssue[position]);
				holder.noticeDateAndTimeView
						.setText(fucai_NoticeDateAndTime[position]);
				holder.winningNumView.setText(fucai_WinningNum[position]);
				holder.finalPrizesDateView
						.setText(fucai_FinalPrizesDate[position]);
				holder.totalSumView.setText(fucai_TotalSum[position]);
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
