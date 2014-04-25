package com.ruyicai.adapter;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.buy.guess.RuyiGuessActivity;
import com.ruyicai.activity.common.CommonViewHolder;
import com.ruyicai.activity.common.UserLogin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RuyiGuessGroupListAdapter extends BaseExpandableListAdapter{
	private Context mContext = null;
	private LayoutInflater mInflater = null;
	private boolean mIsLogin = false;
	private String[] mTitles = {"我的扎堆", "推荐扎堆"};
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
		holder.titleTV.setText(mTitles[groupPosition]);
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
			holder.loginLayout = (LinearLayout) convertView
					.findViewById(R.id.buy_ruyiguess_login_layout);
			holder.noSubject = (TextView) convertView
			.findViewById(R.id.buy_guess_no_subject);
			
			holder.loginBtn = (Button) convertView
					.findViewById(R.id.buy_ruyiguess_login_btn);
			holder.landBtn = (Button) convertView
					.findViewById(R.id.buy_ruyiguess_land_btn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (groupPosition == 0 && !mIsLogin) {
			holder.loginLayout.setVisibility(View.VISIBLE);
			holder.noSubject.setVisibility(View.GONE);
			holder.landBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mContext.startActivity(new Intent(mContext, UserLogin.class));
				}
			});
			holder.loginBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, UserLogin.class);
					intent.putExtra("regist", "regist");
					mContext.startActivity(intent);
					
				}
			});
		} else {
			holder.loginLayout.setVisibility(View.GONE);
			if ("".equals("")) {
				holder.noSubject.setVisibility(View.VISIBLE);
			} else {
				holder.noSubject.setVisibility(View.GONE);
			}
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
		TextView noSubject;
		View divider;  //分割线
		LinearLayout itemLayout;
		LinearLayout loginLayout;
		Button loginBtn;
		Button landBtn;
	}
	
	
}
