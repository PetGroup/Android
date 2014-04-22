package com.ruyicai.activity.buy.guess.view;

import com.ruyicai.util.PublicMethod;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

/**
 * 自定义ExpandableListView 重写onTouchEvent()事件 为实现上方广告位向上滑动
 * @author win
 *
 */

public class CustomExpandableListView extends ExpandableListView {
	private float mLastY = -1;
	private int mTopMarginPx = 0;
	private FrameLayout.LayoutParams mParams = null;
	private int mDensity = 0;
	private ViewFlipper mViewFlipper = null;

	public CustomExpandableListView(Context context) {
		super(context);
		init(context);
	}
	
	public CustomExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public CustomExpandableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			/**广告位向上滚动***/
			if ((mParams.topMargin >= -mTopMarginPx && mParams.topMargin <= 0)
					|| (getFirstVisiblePosition() == 0 && deltaY > 0)) {
				int topMargin = (int) (mDensity * deltaY) + mParams.topMargin;
				if (topMargin < -mTopMarginPx) {
					topMargin = -mTopMarginPx;
				} else if (topMargin > 0) {
					topMargin = 0;
				}
				mParams.setMargins(0, topMargin, 0, 0);
				mViewFlipper.setLayoutParams(mParams);
			}
			/**广告位向上滚动***/
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	private void init(Context context) {
		mTopMarginPx = PublicMethod.getPxInt(150, getContext());
		mParams = new FrameLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				mTopMarginPx);
		mDensity = (int)PublicMethod.getDensity(context);
	}
	
	public void setmViewFlipper(ViewFlipper mViewFlipper) {
		this.mViewFlipper = mViewFlipper;
	}

}
