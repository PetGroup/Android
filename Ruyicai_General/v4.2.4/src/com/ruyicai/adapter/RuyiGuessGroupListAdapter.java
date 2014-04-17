package com.ruyicai.adapter;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.common.CommonViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class RuyiGuessGroupListAdapter extends BaseExpandableListAdapter{

	private Context mContext = null;
	private LayoutInflater mInflater = null;
	private boolean mIsLogin = false;
	public RuyiGuessGroupListAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getGroupCount() {
		return 2;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 0;
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
		if (groupPosition == 0 && !mIsLogin) {
			
		} else {
			
		}
		return null;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}
