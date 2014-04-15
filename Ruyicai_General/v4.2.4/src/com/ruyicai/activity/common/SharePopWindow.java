package com.ruyicai.activity.common;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.adapter.ShareAdapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 分享PopWindow
 * @author win
 *
 */
public class SharePopWindow {
	private PopupWindow mPopupWindow = null;
	private OnChickItem mOnClickItem = null;
	private static SharePopWindow sInstance;

	public static SharePopWindow getInstance() {
		if (sInstance == null) {
			sInstance = new SharePopWindow();
		}
		return sInstance;
	}

	public void createSharePopWindow(Context context, OnChickItem onClickItem, 
			View view, String shareTitle) {
		this.mOnClickItem = onClickItem;
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout contentView = (LinearLayout) inflate.inflate(R.layout.popwindow_layout, null);
		TextView title = (TextView)contentView.findViewById(R.id.share_description_text);
		title.setText(shareTitle);
		GridView gridView = (GridView) contentView.findViewById(R.id.gridview);
		gridView.setNumColumns(4);
		ShareAdapter adapter = new ShareAdapter(context);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mOnClickItem.onChickItem(position);
				dissmiss();
			}
		});
		mPopupWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.FILL_PARENT, 
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}

	private void dissmiss() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}

	public interface OnChickItem {
		public void onChickItem(int viewId);
	}

}
