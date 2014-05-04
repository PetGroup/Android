package com.ruyicai.adapter;

import com.palmdream.RuyicaiAndroid.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 积分详情-我的积分-积分用法列表Adapter
 * @author wangw
 *
 */
public class ScoreUsageListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	public ScoreUsageListAdapter(LayoutInflater inflater){
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
		ViewHolder holder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.buy_guess_score_usage_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.myscore_usage_title);
			holder.time = (TextView) convertView.findViewById(R.id.myscore_usage_time);
			holder.score = (TextView) convertView.findViewById(R.id.myscore_usage_score);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}
	
	class ViewHolder{
		private TextView title;
		private TextView time;
		private TextView score;
	}

}
