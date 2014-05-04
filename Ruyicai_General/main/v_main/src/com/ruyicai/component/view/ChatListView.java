package com.ruyicai.component.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.palmdream.RuyicaiAndroid.R;

public class ChatListView extends HandyListView {
	private final static int RELEASE_TO_REFRESH = 0;
	private final static int PULL_TO_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;
	private final static int RATIO = 2;

	private View mHeader;
	private ImageView mIvLoading;

	private RotateAnimation mLoadingAnimation;

	private boolean mIsRecored;

	private int mHeaderHeight;

	private int mStartY;

	private int mState;

	private OnRefreshListener mOnRefreshListener;
	private boolean mIsRefreshable;

	public ChatListView(Context context) {
		super(context);
		init();
	}

	public ChatListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ChatListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mHeader = mInflater.inflate(R.layout.chat_refreshing_header,null);
		mIvLoading = (ImageView) mHeader.findViewById(R.id.refreshing_header_iv_loading);

		measureView(mHeader);
		addHeaderView(mHeader);
		mHeaderHeight = mHeader.getMeasuredHeight();
		mHeader.setPadding(0, -1 * mHeaderHeight, 0, 0);
		mHeader.invalidate();

		mLoadingAnimation = new RotateAnimation(0, 360,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mLoadingAnimation.setDuration(1000L);
		mLoadingAnimation.setInterpolator(new LinearInterpolator());
		mLoadingAnimation.setRepeatCount(-1);
		mLoadingAnimation.setRepeatMode(Animation.RESTART);

		mState = DONE;
		mIsRefreshable = false;
	}

	@Override
	public void onDown(MotionEvent ev) {
		if (mIsRefreshable) {
			if (mFirstVisibleItem == 0 && !mIsRecored) {
				mIsRecored = true;
				mStartY = mDownPoint.y;
			}
		}
	}

	@Override
	public void onMove(MotionEvent ev) {
		if (mIsRefreshable) {
			if (!mIsRecored && mFirstVisibleItem == 0) {
				mIsRecored = true;
				mStartY = mMovePoint.y;
			}
			if (mState != REFRESHING && mIsRecored && mState != LOADING) {
				if (mState == RELEASE_TO_REFRESH) {
					setSelection(0);
					if (((mMovePoint.y - mStartY) / RATIO < mHeaderHeight)&& (mMovePoint.y - mStartY) > 0) {
						mState = PULL_TO_REFRESH;
						changeHeaderViewByState();
					} else if (mMovePoint.y - mStartY <= 0) {
						mState = DONE;
						changeHeaderViewByState();
					}
				}
				if (mState == PULL_TO_REFRESH) {
					setSelection(0);
					if ((mMovePoint.y - mStartY) / RATIO >= mHeaderHeight) {
						mState = RELEASE_TO_REFRESH;
						changeHeaderViewByState();
					} else if (mMovePoint.y - mStartY <= 0) {
						mState = DONE;
						changeHeaderViewByState();
					}
				}
				if (mState == DONE) {
					if (mMovePoint.y - mStartY > 0) {
						mState = PULL_TO_REFRESH;
						changeHeaderViewByState();
					}
				}
				if (mState == PULL_TO_REFRESH) {
					mHeader.setPadding(0, -1 * mHeaderHeight+ (mMovePoint.y - mStartY) / RATIO, 0, 0);
				}
				if (mState == RELEASE_TO_REFRESH) {
					mHeader.setPadding(0, (mMovePoint.y - mStartY) / RATIO- mHeaderHeight, 0, 0);
				}

			}

		}
	}

	@Override
	public void onUp(MotionEvent ev) {
		if (mState != REFRESHING && mState != LOADING) {
			if (mState == PULL_TO_REFRESH) {
				mState = DONE;
				changeHeaderViewByState();
			}
			if (mState == RELEASE_TO_REFRESH) {
				mState = REFRESHING;
				changeHeaderViewByState();
				onRefresh();

			}
		}
		mIsRecored = false;
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	private void changeHeaderViewByState() {
		switch (mState) {
		case RELEASE_TO_REFRESH:
			mIvLoading.setVisibility(View.GONE);
			mIvLoading.clearAnimation();
			break;
		case PULL_TO_REFRESH:
			mIvLoading.setVisibility(View.GONE);
			mIvLoading.clearAnimation();
			break;

		case REFRESHING:
			mHeader.setPadding(0, 0, 0, 0);
			mIvLoading.setVisibility(View.VISIBLE);
			mIvLoading.clearAnimation();
			mIvLoading.startAnimation(mLoadingAnimation);
			break;
		case DONE:
			mHeader.setPadding(0, -1 * mHeaderHeight, 0, 0);
			mIvLoading.setVisibility(View.GONE);
			mIvLoading.clearAnimation();
			break;
		}
	}
	public void onRefreshComplete() {
		mState = DONE;
		changeHeaderViewByState();
	}

	private void onRefresh() {
		if (mOnRefreshListener != null) {
			mOnRefreshListener.onRefresh();
		}
	}
	public void onManualRefresh() {
		if (mIsRefreshable) {
			mState = REFRESHING;
			changeHeaderViewByState();
			onRefresh();
		}
	}
	public void stopRefre(){
		mState = REFRESHING;
	}
	public void setOnRefreshListener(OnRefreshListener l) {
		mOnRefreshListener = l;
		mIsRefreshable = true;
	}
	public interface OnRefreshListener {
		public void onRefresh();
	}
}
