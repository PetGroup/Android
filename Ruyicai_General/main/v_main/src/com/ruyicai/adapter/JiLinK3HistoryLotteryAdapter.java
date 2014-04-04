package com.ruyicai.adapter;

import java.util.List;
import java.util.Vector;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.cq11x5.Cq11Xuan5;
import com.ruyicai.activity.buy.dlc.Dlc;
import com.ruyicai.activity.buy.jlk3.JiLinK3;
import com.ruyicai.model.HistoryLotteryBean;
import com.ruyicai.model.PrizeInfoBean;
import com.ruyicai.util.PublicMethod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JiLinK3HistoryLotteryAdapter extends BaseAdapter{
	
	private Context context;
	private List<HistoryLotteryBean> lotteryData;
	private int[] lotteryBallPic={R.drawable.notice_ball_black,R.drawable.notice_ball_blue,R.drawable.notice_ball_red};
	
	public JiLinK3HistoryLotteryAdapter(Context context){
		this.context=context;
	}
	
	public void setLotteryList(List<HistoryLotteryBean> lotteryData){
		this.lotteryData=lotteryData;
	}
	public List<HistoryLotteryBean> getLotteryData(){
		return lotteryData;
	}

	@Override
	public int getCount() {
		if(lotteryData!=null){
			return lotteryData.size();
		}else{
			return 10;
		}
	}

	@Override
	public Object getItem(int position) {
		if(lotteryData!=null){
			return lotteryData.get(position);
		}else{
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.activity_jilink3_history_lottery_list_item, null);
			
			holder.historyLotteryListTop=(LinearLayout) convertView.findViewById(R.id.historyLotteryListTop);
			holder.historyLotteryBatchcode=(TextView) convertView.findViewById(R.id.historyLotteryBatchcode);
			holder.historyLotterySum=(TextView) convertView.findViewById(R.id.historyLotterySum);
			holder.historyLotteryPattern=(TextView) convertView.findViewById(R.id.historyLotteryPattern);
			holder.historyNumberOne=(Button) convertView.findViewById(R.id.historyNumberOne);
			holder.historyNumberTwo=(Button) convertView.findViewById(R.id.historyNumberTwo);
			holder.historyNumberThree=(Button) convertView.findViewById(R.id.historyNumberThree);
			holder.historyNumberFour=(Button) convertView.findViewById(R.id.historyNumberFour);
			holder.historyNumberFive=(Button) convertView.findViewById(R.id.historyNumberFive);
			holder.historyNumberSix=(Button) convertView.findViewById(R.id.historyNumberSix);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		Vector<Button> buttonList=new Vector<Button>();
		buttonList.add(holder.historyNumberOne);
		buttonList.add(holder.historyNumberTwo);
		buttonList.add(holder.historyNumberThree);
		buttonList.add(holder.historyNumberFour);
		buttonList.add(holder.historyNumberFive);
		buttonList.add(holder.historyNumberSix);
		for(int i=0;i<buttonList.size();i++){
			buttonList.get(i).setVisibility(View.GONE);
		}
		
		if(position==0){
			holder.historyLotteryListTop.setVisibility(View.VISIBLE);
		}else{
			holder.historyLotteryListTop.setVisibility(View.GONE);
		}
		if (lotteryData != null) {
			String batchCode = lotteryData.get(position).getBatchCode();
			holder.historyLotteryBatchcode.setText(batchCode
					.substring(batchCode.length() - 2) + "期");
			int[] lotteryNumber = PublicMethod.getLotteryNumber(lotteryData.get(position)
					.getWinCode());
			int lotterySum = 0;
			for (int i = 0; i < lotteryNumber.length; i++) {
				buttonList.get(lotteryNumber[i] - 1)
						.setVisibility(View.VISIBLE);
				buttonList.get(lotteryNumber[i] - 1).setText(
						lotteryNumber[i] + "");
				lotterySum = lotterySum + lotteryNumber[i];
				buttonList.get(lotteryNumber[i] - 1).setBackgroundResource(
						lotteryBallPic[i]);
			}
			holder.historyLotterySum.setText(lotterySum + "");
			holder.historyLotteryPattern
					.setText(getSameNumberCount(lotteryNumber));
		}
		
		return convertView;
	}
	
	/**
	 *
	 *获取历史开奖号码，并判断形态
	 */
	private String getSameNumberCount(int[] lotteryNumber){
		String lotteryPattern="";
		int count=0;
		for(int i=0;i<lotteryNumber.length;i++){
			for(int j=i+1;j<lotteryNumber.length;j++){
				if(lotteryNumber[i]==lotteryNumber[j]){
					count++;
				}
			}
		}
		if(count==0){
			lotteryPattern="三不同";
		}else if(count==1){
			lotteryPattern="二同号";
		}else{
			lotteryPattern="三同号";
		}
		return lotteryPattern;
	}

	class ViewHolder{
		LinearLayout historyLotteryListTop;
		TextView historyLotteryBatchcode;
		Button historyNumberOne;
		Button historyNumberTwo;
		Button historyNumberThree;
		Button historyNumberFour;
		Button historyNumberFive;
		Button historyNumberSix;
		Button historyNumberSeven;
		Button historyNumberEight;
		Button historyNumberNine;
		Button historyNumberTen;
		Button historyNumberEleven;
		TextView historyLotterySum;
		TextView historyLotteryPattern;
	}

}
