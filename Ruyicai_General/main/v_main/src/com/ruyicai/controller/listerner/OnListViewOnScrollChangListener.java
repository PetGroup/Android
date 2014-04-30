package com.ruyicai.controller.listerner;

public interface OnListViewOnScrollChangListener {
	public void onIdle();// 滑动后静止

	public void onFling();// 手指离开屏幕后，惯性滑动

	public void onTouchScroll();// 手指在屏幕上滑动
}
