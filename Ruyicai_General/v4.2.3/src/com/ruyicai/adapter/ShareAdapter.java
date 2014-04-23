package com.ruyicai.adapter;

import com.palmdream.RuyicaiAndroid.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareAdapter extends BaseAdapter{
	private int[] mShareText = {R.string.share_weixin_friend, R.string.share_weixin_friend_circle,
			R.string.share_sina, R.string.share_tencent};
	private int[] mShareIcon = {R.drawable.weixin_share_icon, R.drawable.weixin_friend_circle_share_icon,
			R.drawable.sina_share_icon, R.drawable.tencent_share_icon};
	private LayoutInflater mInflater = null;
	
	public ShareAdapter(Context context) {
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return mShareText.length;
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
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.buy_gridview_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.imageView);
			holder.title = (TextView) convertView
					.findViewById(R.id.imageText);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.icon.setImageResource(mShareIcon[position]);
		holder.title.setText(mShareText[position]);
		return convertView;
	}
	
	class ViewHolder {
		ImageView icon;
		TextView title;
	}

}
