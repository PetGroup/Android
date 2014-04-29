package com.ruyicai.adapter;

import java.util.ArrayList;
import java.util.List;

import com.palmdream.RuyicaiAndroid.R;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 创建扎堆-竞猜题目列表数据适配器
 * @author wangw
 *
 */
public class CreateGatherSubjectListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Integer> mSelecteds;
	
	public CreateGatherSubjectListAdapter(LayoutInflater inflater){
		this.mInflater = inflater;
		mSelecteds = new ArrayList<Integer>();
	}
	
	/**
	 * 设置用户选择的题目
	 * @param position
	 */
	public void setSelectItem(int position){
		//TODO 临时测试，使用mSelecteds存储选择的Position
		if(mSelecteds.contains(position))
			mSelecteds.remove((Object)(position));
		else
			mSelecteds.add(position);
		
		notifyDataSetChanged();
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
			convertView = mInflater.inflate(R.layout.buy_guess_subject_item, null);
			holder = new ViewHolder();
			holder.bg = convertView.findViewById(R.id.guess_subject_item_layout);
			holder.title = (TextView) convertView.findViewById(R.id.subject_title);
			holder.lastTime = (TextView) convertView.findViewById(R.id.subject_time);
			holder.play = (TextView) convertView.findViewById(R.id.subject_play);
			holder.selecimg = (ImageView) convertView.findViewById(R.id.subject_select_img);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(mSelecteds.contains(position)){
			holder.selecimg.setVisibility(View.VISIBLE);
			holder.bg.setBackgroundColor(Color.parseColor("#DBFFDB"));
		}else{
			holder.selecimg.setVisibility(View.GONE);
			holder.bg.setBackgroundColor(Color.WHITE);
		}
		
			
		return convertView;
	}
	
	class ViewHolder{
		View bg;
		TextView title;
		TextView lastTime;
		TextView play;
		ImageView selecimg;
	}
	

}
