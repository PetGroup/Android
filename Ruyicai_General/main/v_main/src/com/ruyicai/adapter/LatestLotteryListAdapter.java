package com.ruyicai.adapter;

import java.util.List;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.constant.Constants;
import com.ruyicai.model.PrizeInfoBean;
import com.ruyicai.util.PublicMethod;

public class LatestLotteryListAdapter extends BaseAdapter {
		private Context context;
		private List<PrizeInfoBean> latestLotteryList;
		private LayoutInflater inflater;
        private String lotno = "";
    	private final static int CQ_QY = 20;//前一直选
    	private final static int CQ_QE = 21;//前二直选
    	private final static int CQ_QS = 22;//前三直选
    	private int type;
		public LatestLotteryListAdapter(Context context,String lotno,int type,
				List<PrizeInfoBean> latestLotteryList) {
			super();
			this.context = context;
			this.lotno = lotno;
			this.type = type;
			this.latestLotteryList = latestLotteryList;
	
		}

		@Override
		public int getCount() {
			return latestLotteryList.size();
		}

		@Override
		public Object getItem(int position) {
			return latestLotteryList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				this.inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.latestlottery_listitem,
						null);
				holder.issue = (TextView) convertView
						.findViewById(R.id.latestlottery_textview_issue);
				holder.winningNumber = (TextView) convertView
						.findViewById(R.id.latestlottery_textview_winningnumbers);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			String batchCode = latestLotteryList.get(position).getBatchCode();
			StringBuffer batchCodeString = new StringBuffer();
			if (lotno == Constants.LOTNO_eleven) {
				batchCodeString.append("第").append(batchCode.substring(0, 8))
						.append("期");
			} else {
				batchCodeString.append(batchCode.substring(0, 8))
						.append("-").append(batchCode.substring(8)).append("期");
			}

			holder.issue.setText(batchCodeString);

			String winCodeString = null;
			if (lotno == Constants.LOTNO_NMK3) {
				winCodeString = PublicMethod.formatNMK3Num(latestLotteryList
						.get(position).getWinCode(), 2);
				holder.winningNumber.setText(winCodeString);
			} else if (lotno == Constants.LOTNO_SSC) {
				winCodeString = PublicMethod.formatSSCNum(latestLotteryList
						.get(position).getWinCode(), 1);
				holder.winningNumber.setText(winCodeString);
			} else if (lotno == Constants.LOTNO_CQ_ELVEN_FIVE||lotno == Constants.LOTNO_11_5
					||lotno == Constants.LOTNO_eleven||lotno == Constants.LOTNO_GD_11_5) {
				winCodeString = PublicMethod.formatNum(latestLotteryList.get(position).getWinCode(), 2);
				holder.issue.setTextColor(this.context.getResources().getColor(R.color.cq_11_5_text_color));
				SpannableStringBuilder builder =null;  
				if(this.type==CQ_QY){
					builder=setTextColors(0,3,3,winCodeString.length(),winCodeString);
					holder.winningNumber.setText(builder); 
				}else if(this.type==CQ_QE){
					builder=setTextColors(0,6,6,winCodeString.length(),winCodeString);
					holder.winningNumber.setText(builder); 
				}else if(this.type==CQ_QS){
					builder=setTextColors(0,9,9,winCodeString.length(),winCodeString);
					holder.winningNumber.setText(builder); 
				}else {
					holder.winningNumber.setTextColor(this.context.getResources().getColor(R.color.cq_11_5_text_color));
					holder.winningNumber.setText(winCodeString); 
				}
			}else {
				winCodeString = PublicMethod.formatNum(
						latestLotteryList.get(position).getWinCode(), 2);
				holder.winningNumber.setText(winCodeString);
			}
			//来自2013-10-17徐培松  －－－>>>latestlottery_listitem布局
			if (lotno == Constants.LOTNO_NMK3) {
				holder.issue.setTextColor(this.context.getResources().getColor(R.color.white));
				holder.winningNumber.setTextColor(this.context.getResources().getColor(R.color.white));
				if (position % 2 == 0) {
					convertView
							.setBackgroundResource(R.color.nmk3_latest_lottery_list_one);//0x157800
				} else {
					convertView
							.setBackgroundResource(R.color.nmk3_latest_lottery_list_two);//0x126800
				}
			} else {
				if (position % 2 == 0) {
					convertView
							.setBackgroundResource(R.color.latest_lottery_list_one);
				} else {
					convertView
							.setBackgroundResource(R.color.latest_lottery_list_two);
				}
			}

			return convertView;
		}
		static class ViewHolder {
			public TextView issue;
			public TextView winningNumber;
		}
		private SpannableStringBuilder setTextColors(int startOne,int endOne,int startTwo,int endTwo,String winCodeString){
			SpannableStringBuilder builder = new SpannableStringBuilder(winCodeString);  
			ForegroundColorSpan redSpan = new ForegroundColorSpan(this.context.getResources().getColor(R.color.red));  
			ForegroundColorSpan whiteSpan = new ForegroundColorSpan(this.context.getResources().getColor(R.color.cq_11_5_text_color));
			builder.setSpan(redSpan, startOne, endOne, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
			builder.setSpan(whiteSpan, startTwo,endTwo, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			return builder;
		}
	}

