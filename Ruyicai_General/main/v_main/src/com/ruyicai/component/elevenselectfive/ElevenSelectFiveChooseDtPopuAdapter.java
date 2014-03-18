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

public class ElevenSelectFiveChooseDtPopuAdapter  extends BaseAdapter{

	private Context context;
	private OnDtChickItem onChickItem;
	private List<String> listResource;
	private int index;
	
	public ElevenSelectFiveChooseDtPopuAdapter(Context context, OnDtChickItem onChickItem,
			List<String> listResource) {
		this.context = context;
		this.onChickItem = onChickItem;
		this.listResource = listResource;
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
		Button chickBtn = (Button) view.findViewById(R.id.itemBtn);
		chickBtn.setText(listResource.get(position).toString());
		chickBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onChickItem.onChickItem(v, position, listResource.get(position));
			}
		});	
		if(index==-1){
			chickBtn.setBackgroundResource(R.drawable.shaixuanbutton_normal);
		}else {
			if (position == index) {
				chickBtn.setBackgroundResource(R.drawable.shaixuanbutton_click);
			} else {
				chickBtn.setBackgroundResource(R.drawable.shaixuanbutton_normal);
			}
		}
		return view;
	}
	
	public interface OnDtChickItem {
		public void onChickItem(View view, int position, String text);
	}

	public void setItemSelect(int index) {
		this.index = index;
	}

}
