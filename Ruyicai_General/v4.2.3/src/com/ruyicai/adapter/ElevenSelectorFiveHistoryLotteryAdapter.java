package com.ruyicai.adapter;

import java.util.List;
import java.util.Vector;


import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.cq11x5.Cq11Xuan5;
import com.ruyicai.activity.buy.dlc.Dlc;
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

public class ElevenSelectorFiveHistoryLotteryAdapter extends BaseAdapter{

	private Context context;
	private List<PrizeInfoBean> prizeInfosList;
	
	public ElevenSelectorFiveHistoryLotteryAdapter(Context context){
		this.context=context;
	}
	
	public void setLotteryPrizeList(List<PrizeInfoBean> prizeInfosList){
		this.prizeInfosList=prizeInfosList;
	}
	
	@Override
	public int getCount() {
		if(prizeInfosList!=null){
			return prizeInfosList.size();
		}else{
			return 10;
		}
	}

	@Override
	public Object getItem(int position) {
		if(prizeInfosList!=null){
			return prizeInfosList.get(position);
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
			convertView=LayoutInflater.from(context).inflate(R.layout.activity_eleven_selector_five_lottery_list_item, null);
			
			holder.historyLotteryListTop=(LinearLayout) convertView.findViewById(R.id.historyLotteryListTop);
			holder.historyLotteryBatchcode=(TextView) convertView.findViewById(R.id.historyLotteryBatchcode);
			holder.historyNumberOne=(Button) convertView.findViewById(R.id.historyNumberOne);
			holder.historyNumberTwo=(Button) convertView.findViewById(R.id.historyNumberTwo);
			holder.historyNumberThree=(Button) convertView.findViewById(R.id.historyNumberThree);
			holder.historyNumberFour=(Button) convertView.findViewById(R.id.historyNumberFour);
			holder.historyNumberFive=(Button) convertView.findViewById(R.id.historyNumberFive);
			holder.historyNumberSix=(Button) convertView.findViewById(R.id.historyNumberSix);
			holder.historyNumberSeven = (Button) convertView
					.findViewById(R.id.historyNumberSeven);
			holder.historyNumberEight = (Button) convertView
					.findViewById(R.id.historyNumberEight);
			holder.historyNumberNine = (Button) convertView
					.findViewById(R.id.historyNumberNine);
			holder.historyNumberTen = (Button) convertView
					.findViewById(R.id.historyNumberTen);
			holder.historyNumberEleven = (Button) convertView
					.findViewById(R.id.historyNumberEleven);
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
		buttonList.add(holder.historyNumberSeven);
		buttonList.add(holder.historyNumberEight);
		buttonList.add(holder.historyNumberNine);
		buttonList.add(holder.historyNumberTen);
		buttonList.add(holder.historyNumberEleven);
		for(int i=0;i<buttonList.size();i++){
			buttonList.get(i).setVisibility(View.GONE);
		}
		
		if(position==0){
			holder.historyLotteryListTop.setVisibility(View.VISIBLE);
		}else{
			holder.historyLotteryListTop.setVisibility(View.GONE);
		}
		
		if (prizeInfosList != null) {
			String batchCode = prizeInfosList.get(position).getBatchCode();
			holder.historyLotteryBatchcode.setText(batchCode
					.substring(batchCode.length() - 2) + "期");
			int[] lotteryNumber = PublicMethod.getLotteryNumber(prizeInfosList.get(position)
					.getWinCode());
			for (int i = 0; i < lotteryNumber.length; i++) {
				buttonList.get(lotteryNumber[i] - 1)
						.setVisibility(View.VISIBLE);
				buttonList.get(lotteryNumber[i] - 1).setText(
						lotteryNumber[i] + "");
			}
		}

		return convertView;
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
	}
	
}
