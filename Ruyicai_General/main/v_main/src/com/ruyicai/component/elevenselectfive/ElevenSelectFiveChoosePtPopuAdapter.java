package com.ruyicai.component.elevenselectfive;

import java.util.List;

import com.palmdream.RuyicaiAndroid.R;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ElevenSelectFiveChoosePtPopuAdapter extends BaseAdapter{

	private Context context;
	private OnChickItem onChickItem;
	private List<String> listResource;
	private int index;
	private int[] backGroundid;
	private List<String> playMethodDescribeList;
	private int colorId;
	
	public ElevenSelectFiveChoosePtPopuAdapter(Context context, OnChickItem onChickItem,
			List<String> listResource) {
		this.context = context;
		this.onChickItem = onChickItem;
		this.listResource = listResource;
	}
	
	public void setItemClickBackground(int[] backGroundid){
		this.backGroundid=backGroundid;
	}
	
	public void setplayMethodDescribeList(List<String> listResource){
		this.playMethodDescribeList=listResource;
	}
	
	public void setTextColor(int colorId){
		this.colorId=colorId;
	}
	
	@Override
	public int getCount() {
		return listResource.size();
	}

	@Override
	public Object getItem(int position) {
		return listResource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.popu_item,
				null);
		RelativeLayout chickBtn = (RelativeLayout) view.findViewById(R.id.itemBtn);
		TextView playMethodName = (TextView) view.findViewById(R.id.playMethodName);
		TextView playMethodDescribe = (TextView) view.findViewById(R.id.playMethodDescribe);
		playMethodName.setText(listResource.get(position).toString());
		playMethodDescribe.setTextColor(colorId);
		playMethodName.setTextColor(colorId);
		if(playMethodDescribeList!=null&&playMethodDescribeList.size()>0){
			playMethodDescribe.setText(playMethodDescribeList.get(position).toString());
		}else{
			playMethodDescribe.setVisibility(View.GONE);
		}
		chickBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onChickItem.onChickItem(v, position, listResource.get(position));
			}
		});
		if(index==-1){
			chickBtn.setBackgroundResource(backGroundid[0]);
		}else {
			if (position == index) {
				chickBtn.setBackgroundResource(backGroundid[1]);
			} else {
				chickBtn.setBackgroundResource(backGroundid[0]);
			}
		}
		return view;
	}

	public interface OnChickItem {
		public void onChickItem(View view, int position, String text);
	}

	public void setItemSelect(int index) {
		this.index = index;
	}

}
