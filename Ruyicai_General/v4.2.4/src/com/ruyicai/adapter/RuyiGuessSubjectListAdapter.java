package com.ruyicai.adapter;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.common.CommonViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RuyiGuessSubjectListAdapter extends BaseExpandableListAdapter{
	private Context mContext = null;
	private LayoutInflater mInflater = null;
	private boolean mIsLogin = false;
	public RuyiGuessSubjectListAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getGroupCount() {
		return 2;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (groupPosition == 0 && !mIsLogin) {
			return 1;
		}
		return 10;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		CommonViewHolder.GroupViewHolder holder = null;
		if (convertView == null) {
			holder = new CommonViewHolder.GroupViewHolder();
			convertView = mInflater.inflate(
					R.layout.buy_jc_item_layout, null);
			holder.titleTV = (TextView)convertView.findViewById(R.id.buy_jc_textview);
			convertView.setTag(holder);
		} else {
			holder = (CommonViewHolder.GroupViewHolder) convertView.getTag();
		}
		if (isExpanded) {
			holder.titleTV.setBackgroundResource(R.drawable.buy_jc_item_btn_open);
		} else {
			holder.titleTV.setBackgroundResource(R.drawable.buy_jc_item_btn_close);
		}
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
//		if (groupPosition == 0 && !mIsLogin) {
//			
//		} else {
//			
//		}
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
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
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
	
	
}
