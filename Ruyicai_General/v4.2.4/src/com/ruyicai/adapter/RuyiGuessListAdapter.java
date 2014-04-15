package com.ruyicai.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.guess.bean.ItemInfoBean;
import com.ruyicai.util.PublicMethod;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class RuyiGuessListAdapter extends BaseAdapter{
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

	private List<ItemInfoBean> mQuestionsList = null;
	private LayoutInflater mInflater = null; 
	private Context mContext = null;
	private boolean mIsMySelected = false;
	private boolean mIsLogin = false;
	
	private int[] mIconArray = { R.drawable.textview_red_style,
			R.drawable.textview_orange_style,
			R.drawable.textview_yellow_style };
	
	public RuyiGuessListAdapter(Context context, List<ItemInfoBean> list,
			LayoutInflater inflater, boolean isMySelected, boolean isLogin) {
		mContext = context;
		mQuestionsList = list;
		mIsMySelected = isMySelected;
		mIsLogin = isLogin;
		mInflater = inflater;
	}
	
	public void setLoginState(boolean isLogin) {
		mIsLogin = isLogin; 
	}
	
	@Override
	public int getCount() {
		if (mQuestionsList != null) {
			return mQuestionsList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return mQuestionsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemInfoBean info = mQuestionsList.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.buy_ruyiguess_listview_item, null);
			holder = new ViewHolder();
			holder.integral = (TextView) convertView
					.findViewById(R.id.ruyi_guess_item_integral);
			holder.title = (TextView) convertView
					.findViewById(R.id.ruyi_guess_item_title);
			holder.detail = (TextView) convertView
					.findViewById(R.id.ruyi_guess_item_detail);
			holder.time = (TextView) convertView
					.findViewById(R.id.ruyi_guess_item_time);
			holder.participate = (TextView) convertView
					.findViewById(R.id.ruyi_guess_item_participate);
			holder.endState = (TextView) convertView
					.findViewById(R.id.ruyi_myguess_item_participate);
			holder.divider = (View) convertView
					.findViewById(R.id.ruyi_guess_divider);
			holder.itemLayout = (LinearLayout) convertView
					.findViewById(R.id.ruyi_guess_item_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FrameLayout.LayoutParams params = (LayoutParams) holder.itemLayout.getLayoutParams();
		int mUnitPadValue = PublicMethod.getPxInt(1, mContext);
		if (position == mQuestionsList.size()-1) {
			holder.divider.setVisibility(View.VISIBLE);
			params.height = PublicMethod.getPxInt(93, mContext);
			holder.itemLayout.setLayoutParams(params);
			holder.itemLayout.setPadding(10*mUnitPadValue, 8*mUnitPadValue, 8*mUnitPadValue, 7*mUnitPadValue);
		} else {
			holder.divider.setVisibility(View.GONE);
			params.height = PublicMethod.getPxInt(90, mContext);
			holder.itemLayout.setLayoutParams(params);
			holder.itemLayout.setPadding(10*mUnitPadValue, 8*mUnitPadValue, 8*mUnitPadValue, 4*mUnitPadValue);
		}
		
		holder.title.setText(info.getTitle());
		holder.detail.setText(info.getDetail());
		boolean isLottery = false;
		if ("2".equals(info.getState())) {
			isLottery = true;
		} else {
			isLottery = false;
		}
		Long remainTime = 0L;
		try {
			remainTime = Long.valueOf(info.getTime_remaining());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTimeStyle(position, remainTime, holder.time, isLottery, holder.endState, info);
		holder.integral.setBackgroundResource(mIconArray[position%3]);
		if (mIsMySelected) {
			holder.integral.setText(PublicMethod.formatString(mContext, 
					R.string.buy_ruyi_guess_mythrow_score, info.getPayScore()));
			
			if (remainTime > 0) {
				holder.endState.setVisibility(View.GONE);
			} else {
				holder.endState.setVisibility(View.VISIBLE);
				if (isLottery) {
					if ("1".equals(info.getIsWin())) {
						holder.endState.setText(R.string.buy_ruyi_guess_iswin);
					} else {
						holder.endState.setText(R.string.buy_ruyi_guess_nowin);
					}
				} else {
					holder.endState.setText(R.string.buy_ruyi_guess_wait_open);
				}
			}
			
			holder.participate.setVisibility(View.GONE);
		} else {
			holder.endState.setVisibility(View.GONE);
			holder.participate.setVisibility(View.VISIBLE);
			holder.integral.setText(PublicMethod.formatString(mContext, 
					R.string.buy_ruyi_guess_item_integral_two, info.getPrizePoolScore()));
			if ("0".equals(info.getIsEnd())) { //竞猜是否结束 0:未结束;1:已结束
				if (mIsLogin) {
					if ("0".equals(info.getIsParticipate())) { //是否参与竞猜 0:未参与;1:已参与
						holder.participate.setText(R.string.buy_ruyi_guess_btn_participate_jc);//根据状态判断显示
						holder.participate.setBackgroundResource(R.drawable.buy_ruyiguess_item_state_green);
					} else {
						holder.participate.setText(R.string.buy_ruyi_guess_btn_participate);//根据状态判断显示
						holder.participate.setBackgroundResource(R.drawable.buy_ruyiguess_item_state_gray);
					}
				} else {
					holder.participate.setText(R.string.buy_ruyi_guess_btn_participate_jc);//根据状态判断显示
					holder.participate.setBackgroundResource(R.drawable.buy_ruyiguess_item_state_green);
				}
			} else {
				holder.participate.setBackgroundResource(R.drawable.buy_ruyiguess_item_state_gray);
				if (mIsLogin) {
					if ("0".equals(info.getIsParticipate())) { //是否参与竞猜 0:未参与;1:已参与
						holder.participate.setText(R.string.buy_ruyi_guess_btn_participate_jc);//根据状态判断显示
					} else {
						holder.participate.setText(R.string.buy_ruyi_guess_btn_participate);//根据状态判断显示
					}
				} else {
					holder.participate.setText(R.string.buy_ruyi_guess_btn_participate_jc);//根据状态判断显示
				}
			}
		}
		
		return convertView;
	}
	
	class ViewHolder {
		TextView integral; //积分
		TextView title; //竞猜题目
		TextView detail; //竞猜题目详情
		TextView time; //竞猜剩余时间
		TextView participate; //参与状态
		TextView endState; //待公布、已公布状态
		View divider;  //分割线
		LinearLayout itemLayout;
	}
	
	public void clearData() {
		remainTimeMap.clear();
		flagMap.clear();
		lessMinuteFlagMap.clear();
		lessMinuteMap.clear();
		if (threadMap.size() > 0) {
			for (int key : threadMap.keySet()) {
				if (threadMap.get(key) != null){
					threadMap.get(key).interrupt();
				}
			}
			threadMap.clear();
		}
	}
	
	private void setTimeStyle(final int position, Long time, 
			final TextView timeTv, boolean isLottery, TextView state, ItemInfoBean info) {
		if (time > 0) {
			timeTv.setVisibility(View.VISIBLE);
			if (!remainTimeMap.containsKey(position)) {
				remainTimeMap.put(position, time);
				timeTv.setText(formatLongToString(position, time));
				flagMap.put(position, true);
				if (!threadMap.containsKey(position)) {
					threadMap.put(position, new TimeThread(timeTv, state, position, info));
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
			timeTv.setTextColor(mContext.getResources().getColor(R.color.ruyi_guess_progress_red_color));
		} else {
			if (mIsMySelected) {
				timeTv.setVisibility(View.GONE);
			} else {
				timeTv.setVisibility(View.VISIBLE);
				if (isLottery) {
					timeTv.setText(R.string.buy_ruyi_guess_open);
					timeTv.setTextColor(mContext.getResources().getColor(R.color.ruyi_guess_progress_red_color));
				} else {
					timeTv.setText(R.string.buy_ruyi_guess_wait_open);
					timeTv.setTextColor(mContext.getResources().getColor(R.color.ruyi_guess_progress_green_color));
				}
			}
		}
	}
	
	private class TimeThread extends Thread {
		
		TextView timeTv;
		TextView mState;
		int position;
		ItemInfoBean mInfo;
		public TimeThread(TextView timeTv, TextView state, int position, ItemInfoBean info) {
			this.timeTv = timeTv;
			this.position = position;
			this.mState = state;
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
									if (mIsMySelected) {
										if ("".equals(timeStr)) {
											mState.setVisibility(View.VISIBLE);
											mState.setText(R.string.buy_ruyi_guess_wait_open);
											timeTv.setVisibility(View.GONE);
										} else {
											timeTv.setText(timeStr);
											timeTv.setVisibility(View.VISIBLE);
											mState.setVisibility(View.GONE);
										}
									} else {
										timeTv.setText(timeStr);
										timeTv.setVisibility(View.VISIBLE);
										mState.setVisibility(View.GONE);
									}
									
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
