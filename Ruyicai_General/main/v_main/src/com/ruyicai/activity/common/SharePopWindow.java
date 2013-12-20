package com.ruyicai.activity.common;

import com.palmdream.RuyicaiAndroid.R;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

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

	public void createSharePopWindow(Context context, OnChickItem onClickItem, View view) {
		this.mOnClickItem = onClickItem;
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contentView = inflate.inflate(R.layout.share_popwindow, null);
		mPopupWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.FILL_PARENT, 
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		Button shareToSinaWeiBo = (Button) contentView
				.findViewById(R.id.tosinaweibo);
		Button shareToTencentWeiBo = (Button) contentView
				.findViewById(R.id.totengxunweibo);
		Button shareToWeChat = (Button) contentView.findViewById(R.id.toweixin);
		Button shareToCircleOfFriends = (Button) contentView
				.findViewById(R.id.topengyouquan);
		Button cancel = (Button) contentView.findViewById(R.id.tocancel);
		ShareBtnClickListener btnListener = new ShareBtnClickListener();
		shareToSinaWeiBo.setOnClickListener(btnListener);
		shareToTencentWeiBo.setOnClickListener(btnListener);
		shareToWeChat.setOnClickListener(btnListener);
		shareToCircleOfFriends.setOnClickListener(btnListener);
		cancel.setOnClickListener(btnListener);
		mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}

	class ShareBtnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			mOnClickItem.onChickItem(v.getId());
			dissmiss();
		}
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
