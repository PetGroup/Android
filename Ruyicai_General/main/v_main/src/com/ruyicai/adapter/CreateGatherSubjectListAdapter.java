package com.ruyicai.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.guess.bean.ItemInfoBean;
import com.ruyicai.model.RuyiGuessSubjectBean;

/**
 * 创建扎堆-竞猜题目列表数据适配器
 * @author wangw
 *
 */
public class CreateGatherSubjectListAdapter extends BaseAdapter {

	/**
	 * 存放剩余秒数
	 */
	Map<Integer, Long> remainTimeMap = Collections.synchronizedMap(new HashMap<Integer, Long>());
	
	/**
	 * 存放是否计时完成的状态
	 */
	Map<Integer, Boolean> flagMap = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
	
	/**
	 * 存放计时线程
	 */
	Map<Integer, Thread> threadMap = Collections.synchronizedMap(new HashMap<Integer, Thread>());
	
	/**
	 * 存放少于一分钟的秒数
	 */
	Map<Integer, Long> lessMinuteMap = Collections.synchronizedMap(new HashMap<Integer, Long>());
	
	/**
	 * 存放少于一分钟的秒数的状态
	 */
	Map<Integer, Boolean> lessMinuteFlagMap = Collections.synchronizedMap(new HashMap<Integer, Boolean>());

	
	
	private LayoutInflater mInflater;
	private int mSelectedid;
	private Activity mContext;
	private List<ItemInfoBean> mData;

	public CreateGatherSubjectListAdapter(Activity context){
		mContext = context;
		this.mInflater = mContext.getLayoutInflater();
		mData = new ArrayList<ItemInfoBean>();
	}
	
	/**
	 * 获取当前选中的Itemid
	 * @return
	 */
	public String getSelectedid() {
		ItemInfoBean bean = mData.get(mSelectedid);
		if(bean != null)
			return bean.getId();
		return "";
	}

	/**
	 * 设置用户选择的题目
	 * @param position
	 */
	public void setSelectItem(int position){
		if(mSelectedid != position){
			mSelectedid = position;
			notifyDataSetChanged();
		}
	}
	
	/**
	 * 添加数据
	 * @param data
	 */
	public void addData(List<ItemInfoBean> data){
		mData.addAll(data);
		notifyDataSetChanged();
	}
	
	/**
	 * 刷新数据
	 * @param data
	 */
	public void refreshData(List<ItemInfoBean> data){
		mData.clear();
		addData(data);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
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
			holder.divider = convertView.findViewById(R.id.ruyi_guess_divider);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		ItemInfoBean bean = mData.get(position);
		holder.title.setText(bean.getTitle());
		holder.lastTime.setText(bean.getTime_remaining());

		//判断是否选中状态
		if(mSelectedid == position){
			holder.selecimg.setVisibility(View.VISIBLE);
			holder.bg.setBackgroundColor(Color.parseColor("#DBFFDB"));
		}else{
			holder.selecimg.setVisibility(View.GONE);
			holder.bg.setBackgroundColor(Color.WHITE);
		}
		
		//判断是否到底部了，如果到底部则显示下面的分割线
		if(position == mData.size()-1){
			holder.divider.setVisibility(View.VISIBLE);
		}else{
			holder.divider.setVisibility(View.GONE);
		}
		
		Long lastTime = 0L;
		try {
			lastTime = Long.parseLong(bean.getTime_remaining());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setTimeStyle(position,lastTime,holder.lastTime,bean);
		
		
		return convertView;
	}

	class ViewHolder{
		View bg;
		TextView title;
		TextView lastTime;
		TextView play;
		ImageView selecimg;
		View divider;
	}
	
	
	private void setTimeStyle(final int position, Long time, 
			final TextView timeTv, ItemInfoBean info) {
		if (time > 0) {
			if (!remainTimeMap.containsKey(position)) {
				remainTimeMap.put(position, time);
				timeTv.setText(formatLongToString(position, time));
				flagMap.put(position, true);
				if (!threadMap.containsKey(position)) {
					threadMap.put(position, new TimeThread(timeTv, position, info));
					Thread thread = threadMap.get(position);
					if (!(thread.isAlive())) {
						thread.start();
					}
				}
			} else {
				if (remainTimeMap.containsKey(position)) {
					timeTv.setText(formatLongToString(position, remainTimeMap.get(position)));
				} else {
					timeTv.setText("");
				}
			}
		} else {
				timeTv.setText("已截止");
		}
	}
	
	private class TimeThread extends Thread {
		
		TextView timeTv;
//		TextView mState;
		int position;
		ItemInfoBean mInfo;
		public TimeThread(TextView timeTv,int position, ItemInfoBean info) {
			this.timeTv = timeTv;
			this.position = position;
//			this.mState = state;
			this.mInfo = info;
		}

		@Override
		public void run() {
			boolean flag = false;
			if (flagMap != null && flagMap.containsKey(position)) {
				flag = flagMap.get(position);
			}
			while (flag) {
				long currentTime = 0L;
				synchronized (remainTimeMap) {
					if (remainTimeMap == null || 
							(!(remainTimeMap.containsKey(position))
							&& !(flagMap.containsKey(position)))) {
						return;
					}
					currentTime = remainTimeMap.get(position);
				}
				if (currentTime > 0) {
					int sleep = 60 * 1000;
					if (lessMinuteFlagMap.containsKey(position) && lessMinuteFlagMap.get(position)) {
						lessMinuteFlagMap.put(position, false);
						long tempTime = lessMinuteMap.get(position);
						sleep = (int)tempTime* 1000;
					}
					if (isInterrupted()) {
						return;
					}
					try {
						Thread.sleep(sleep);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					long newTime = currentTime - sleep/1000;
					if (!remainTimeMap.containsKey(position)) {
						return;
					}
					remainTimeMap.put(position, newTime);
					if (mContext instanceof Activity) {
						Activity activity = (Activity)mContext;
						activity.runOnUiThread(new Runnable() {
							public void run() {
								if ((flagMap.containsKey(position))) {
									mInfo.setTime_remaining(String.valueOf(remainTimeMap.get(position)));
									String timeStr = formatLongToString(position, remainTimeMap.get(position));
//									if (mIsMySelected) {
//										if ("".equals(timeStr)) {
//											mState.setVisibility(View.VISIBLE);
//											mState.setText(R.string.buy_ruyi_guess_wait_open);
//											timeTv.setVisibility(View.GONE);
//										} else {
//											timeTv.setText(timeStr);
//											timeTv.setVisibility(View.VISIBLE);
//											mState.setVisibility(View.GONE);
//										}
//									} else {
										timeTv.setText(timeStr);
//										timeTv.setVisibility(View.VISIBLE);
//										mState.setVisibility(View.GONE);
//									}
									
									notifyDataSetChanged();
								}
							}
						});
					}
					
					if (!(newTime > 0)) {
						flagMap.put(position, false);
					}
					
					if ((flagMap.get(position) == null)
							|| !(flagMap.containsKey(position))) {
						flag = false;
					}
				}
			}
		}
	}
	
	public String formatLongToString(int position, long time) {
		StringBuffer buffer = new StringBuffer();
		int day = 0;
		int hour = 0;
		long minute = 0;
		if (time > 60) {
			minute = time / 60;
			time = time % 60;
			if (time > 0) {
				minute = minute + 1;
				lessMinuteMap.put(position, time);
				if (!lessMinuteFlagMap.containsKey(position)) {
					lessMinuteFlagMap.put(position, true);
				}
			}
		} else if (time > 0 && time <= 60){
			minute = 1;
		}
		
		if (minute > 0) {
			buffer.append("剩");
		} else {
			return "";
		}
		
		if (minute >= 60) {
			hour = (int)(minute / 60);
			minute = minute % 60;
		}
		
		if (hour >= 24) {
			day = hour / 24;
			hour = hour % 24;
		}
		buffer.append(day).append("天");
		buffer.append(hour).append("时");
		buffer.append(minute).append("分");
		return buffer.toString();
	}
	

}
