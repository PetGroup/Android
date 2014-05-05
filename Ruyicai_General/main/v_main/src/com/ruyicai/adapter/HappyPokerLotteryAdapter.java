package com.ruyicai.adapter;

import java.util.List;

import com.google.inject.Inject;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.controller.service.LotteryService;
import com.ruyicai.model.HistoryLotteryBean;
import com.ruyicai.util.PublicMethod;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HappyPokerLotteryAdapter extends BaseAdapter{
	
	private Context context;
	private List<HistoryLotteryBean> lotteryData;

	public HappyPokerLotteryAdapter(Context context){
		this.context=context;
	}
	
	public void setLotteryList(List<HistoryLotteryBean> lotteryData){
		this.lotteryData=lotteryData;
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_happy_poker_history_lottery_list_item,
					null);
			holder.batchCodeText=(TextView) convertView.findViewById(R.id.batchCodeText);
			holder.historyLotteryLayout=(LinearLayout) convertView.findViewById(R.id.historyLotteryLayout);
			holder.historyLotteryPattern=(TextView) convertView.findViewById(R.id.historyLotteryPattern);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.historyLotteryLayout.removeAllViews();
		if (lotteryData != null) {
			String batchCode = lotteryData.get(position).getBatchCode();
			String LotteryNumber=lotteryData.get(position).getWinCode();
			holder.batchCodeText.setText(batchCode + "期");
			String iShowNumber;
			int[] lotteryNums = new int[3];
			for (int i = 0; i < 3; i++) {
				if(i==0){
					iShowNumber = LotteryNumber.substring(0 , 3);
				}else{
					iShowNumber = LotteryNumber.substring(i * 4 , i * 4+3);
				}
				lotteryNums[i] = Integer.valueOf(iShowNumber);
				ImageView tempView=new ImageView(context);
				tempView.setBackgroundResource(PublicMethod.setHappyPokerLotteryBg(iShowNumber));
				holder.historyLotteryLayout.addView(tempView);
			}
			if(isTongHuaShun(LotteryNumber,lotteryNums)){
				holder.historyLotteryPattern.setText("同花顺");
			}else if(isTonghua(lotteryNums)){
				holder.historyLotteryPattern.setText("同花");
			}else if(isShunZi(LotteryNumber)){
				holder.historyLotteryPattern.setText("顺子");
			}else{
				holder.historyLotteryPattern.setText(LotterySameNumber(LotteryNumber));
			}
		}
		if(position%2==0){
			convertView.setBackgroundColor(Color.rgb(151, 80, 0));
		}else{
			convertView.setBackgroundColor(Color.rgb(179, 105, 20));
		}
		return convertView;
	}

	class ViewHolder{
		TextView batchCodeText;
		LinearLayout historyLotteryLayout;
		TextView historyLotteryPattern;
	}
	
	/**
	 * 判断开奖号码相同个数
	 */
	private String LotterySameNumber(String LotteryNumber){
		String iShowNumber = "";
		int[] temp = new int[3];
		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				iShowNumber = LotteryNumber.substring(0, 3);
			} else {
				iShowNumber = LotteryNumber.substring(i * 4, i * 4 + 3);
			}
			temp[i] = Integer.valueOf(iShowNumber.subSequence(1, 3).toString());
		}
		int count=0;
		for(int i=0;i<temp.length;i++){
			for(int j=i+1;j<temp.length;j++){
				if(temp[i]==temp[j]){
					count++;
					
				}
			}
		}
		if(count==0){
			iShowNumber="";
		}else if(count==1){
			iShowNumber="对子";
		}else{
			iShowNumber="豹子";
		}
		return iShowNumber;
	}
	
	/**
	 * 判断是否是顺子
	 */
	private boolean isShunZi(String LotteryNumber){
		String iShowNumber = "";
		int[] temp = new int[3];
		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				iShowNumber = LotteryNumber.substring(0, 3);
			} else {
				iShowNumber = LotteryNumber.substring(i * 4, i * 4 + 3);
			}
			temp[i] = Integer.valueOf(iShowNumber.subSequence(1, 3).toString());
		}
		if((temp[0]==(temp[1]-1))&&(temp[1]==(temp[2]-1))){
			return true;
		}else if(temp[0]==1&&temp[1]==12&&temp[2]==13){
			return true;
		}
		return false;
	}
	
	/**
	 *判断是否是同花顺
	 */
	private boolean isTongHuaShun(String LotteryNumber,int[] LotteryNumbers){
		if(isTonghua(LotteryNumbers)&&isShunZi(LotteryNumber)){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 判断是否是同花
	 */
	private boolean isTonghua(int[] LotteryNumber) {
		if (isJudgeTongHua(LotteryNumber, 101, 113)) {//黑
			return true;
		}
		if (isJudgeTongHua(LotteryNumber, 201, 213)) {//红
			return true;
		}
		if (isJudgeTongHua(LotteryNumber, 301, 313)) {//梅
			return true;
		}
		if (isJudgeTongHua(LotteryNumber, 401, 413)) {//方
			return true;
		}
		return false;
	}

	private boolean isJudgeTongHua(int[] LotteryNumber, int start, int end) {
		for (int i = 0; i < 3; i++) {
			if (LotteryNumber[i] < start || LotteryNumber[i] > end) {
				return false;
			}
		}
		return true;
	}
}
