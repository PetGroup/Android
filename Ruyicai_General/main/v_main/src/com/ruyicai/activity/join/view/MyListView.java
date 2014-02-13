package com.ruyicai.activity.join.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 自定义ListView嵌套在其他ScrollView中重新计算ListView的高度
 * 
 * @author
 * 
 */
public class MyListView extends ListView {

	boolean dspatchTouchEvent;
	public MyListView(Context context,AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >>2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

	public void setDispatchTouchEvent(boolean dspatchTouchEvent){
		this.dspatchTouchEvent=dspatchTouchEvent;
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(dspatchTouchEvent){
			return super.dispatchTouchEvent(ev);
		}else {
			return false;
		}
	}
}
