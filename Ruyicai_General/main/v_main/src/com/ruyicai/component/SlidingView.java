package com.ruyicai.component;

import java.util.ArrayList;
import java.util.List;
import com.palmdream.RuyicaiAndroid.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * 滑动切换界面
 *
 */
public class SlidingView {

	private ViewPager viewPager;
	private List<View> listViews; // Tab页面列表
	private ImageView imageView;// 动画图片view
	private TextView topView;// 页卡头标
	private List<TextView> listTopViews; // Tab页面列表
	private int initialOffset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private LinearLayout layout;//存放tab表头的linearlayout
	private String[] topViewName;//tab表头集合
	private Context context;
	private SlidingViewSetCurrentItemListener slidingViewSetCurrentListener;//单击tab表头自定义监听
	private SlidingViewPageChangeListener slidingViewPageChangeListener;//viewpage改变自定义监听
	private int textSize;//tab表头字体大小
	private int textSelectColor;//tab表头选中字体颜色
	
	public void addSlidingViewSetCurrentItemListener(SlidingViewSetCurrentItemListener currentItem) {
		slidingViewSetCurrentListener = currentItem;
	}

	public interface SlidingViewSetCurrentItemListener {
		public void SlidingViewSetCurrentItem(int index);
	}
	
	public void addSlidingViewPageChangeListener(SlidingViewPageChangeListener page) {
		slidingViewPageChangeListener = page;
	}
	
	public interface SlidingViewPageChangeListener{
		public void SlidingViewPageChange(int arg0);
	}
	
	/**
	 * @param context 
	 * @param topViewName tab表头名称
	 * @param listViews viewpage需要加载的页面集合
	 * @param layout tab表头需要存放的layout
	 * @param imageView 动画图片所在的ImageView
	 * @param bmpW 动画图片宽度
	 * @param viewPager 滑动的viewPage
	 * @param textSize tab表头字体大小
	 * @param textSelectColor tab表头选中字体颜色
	 */
	public SlidingView(Context context,String[] topViewName, List<View> listViews,
			LinearLayout layout, ImageView imageView, int bmpW, ViewPager viewPager,
			int textSize, int textSelectColor){
		this.context=context;
		this.topViewName=topViewName;
		this.listViews=listViews;
		this.layout=layout;
		this.imageView=imageView;
		this.bmpW=bmpW;
		this.viewPager=viewPager;
		this.textSize=textSize;
		this.textSelectColor=textSelectColor;
		
		InitImageView();
		InitTextView();
		InitViewPager();
	}

	/**
	 * 初始化头标
	 */
	private void InitTextView() {
		listTopViews=new ArrayList<TextView>();
		for(int i=0;i<topViewName.length;i++){
			topView = new TextView(context);
			topView.setGravity(Gravity.CENTER);
			topView.setText(topViewName[i]);
			topView.setTextColor(context.getResources().getColor(R.color.black));
			topView.setTextSize(textSize);
			topView.setId(i);
			topView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f));
			layout.addView(topView);
			listTopViews.add(topView);
			topView.setOnClickListener(new MyOnClickListener(i));
		}
		listTopViews.get(0).setTextColor(textSelectColor);
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		viewPager.setAdapter(new MyPagerAdapter(listViews));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 初始化动画
	 */
	private void InitImageView() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		initialOffset = (screenW / topViewName.length - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(initialOffset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}

	/**
	 * ViewPager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
			slidingViewSetCurrentListener.SlidingViewSetCurrentItem(index);
			//字体换色
			for(int i=0;i<listTopViews.size();i++){
				if(i==index){
					listTopViews.get(i).setTextColor(textSelectColor);
				}else{ 
					listTopViews.get(i).setTextColor(context.getResources().getColor(R.color.black));
				}
			}
		}
	};

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int offset = initialOffset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			//字体换色
			for(int i=0;i<listTopViews.size();i++){
				if(i==arg0){
					listTopViews.get(i).setTextColor(textSelectColor);
				}else{ 
					listTopViews.get(i).setTextColor(context.getResources().getColor(R.color.black));
				}
			}
			if (arg0 < currIndex) {
				animation = new TranslateAnimation(offset*currIndex, offset*arg0, 0, 0);
			}else{
				if(currIndex == 0){
					animation = new TranslateAnimation(initialOffset, offset*arg0, 0, 0);
				}else{
					animation = new TranslateAnimation(offset*currIndex, offset*arg0, 0, 0);
				}
			}
			slidingViewPageChangeListener.SlidingViewPageChange(arg0);
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
}
