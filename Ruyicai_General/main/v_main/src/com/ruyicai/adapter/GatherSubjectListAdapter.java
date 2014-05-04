package com.ruyicai.adapter;

import com.palmdream.RuyicaiAndroid.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author wangw
 *
 */
public class GatherSubjectListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	private int[] mIconArray = { R.drawable.textview_red_style,
			R.drawable.textview_orange_style,
			R.drawable.textview_yellow_style };
	
	public GatherSubjectListAdapter(LayoutInflater inflater){
		mInflater = inflater;
	}
	
	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder =null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.buy_ruyiguess_subject_list_item, null);
			holder = new ViewHolder();
			holder.integral = (TextView) convertView.findViewById(R.id.guess_subject_item_integral);
			holder.subjectName = (TextView) convertView.findViewById(R.id.guess_subject_name);
			holder.sponsorName = (TextView) convertView.findViewById(R.id.guess_subject_sponsor);
			holder.lasttime = (TextView) convertView.findViewById(R.id.guess_subject_lasttime);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.integral.setBackgroundResource(mIconArray[position % 3]);
		return convertView;
	}
	
	class ViewHolder{
		TextView integral;
		TextView subjectName;
		TextView sponsorName;
		TextView lasttime;
	}

}
