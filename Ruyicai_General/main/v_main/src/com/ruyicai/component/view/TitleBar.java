package com.ruyicai.component.view;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.util.PublicMethod;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleBar extends RelativeLayout {
//	private Button mBackBtn = null;
	private ImageView mBackBtn = null;
	private TextView mTitle = null;
	private Context mContext = null;

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		mContext = context;
	}
	
	public TitleBar(Context context) {
		this(context, null);
	}
	
	private void initView(Context context) {
		mBackBtn = new ImageView(context);
		mTitle = new TextView(context);
		RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		btnParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		btnParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		btnParams.setMargins(PublicMethod.getPxInt(5, context), 0, 0, 0);
		addView(mBackBtn, btnParams);
		addView(mTitle, titleParams);
		setBackgroundResource(R.drawable.buy_bottom_bg);
		setTitleTextColor(context.getResources().getColor(R.color.white));
		setTitleTextSize(20);
		setBackButtonResource(R.drawable.ruyicai_titlebar_back);
		mBackBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mContext instanceof Activity) {
					Activity activity = (Activity)mContext;
					activity.finish();
				}
			}
		});
		
	}
	
	public void setBackButtonResource(int resId) {
		mBackBtn.setImageResource(resId);
//		mBackBtn.setBackgroundResource(resId);
	}
	
	public void setBackButtonDrawable(Drawable drawable) {
		mBackBtn.setImageDrawable(drawable);
//		mBackBtn.setBackgroundDrawable(drawable);
	}
	
	public void setBackButtonBitmap(Bitmap bitmap) {
		mBackBtn.setImageBitmap(bitmap);
	}
	
	public void setBackButtonBgColor(int color) {
		mBackBtn.setBackgroundColor(color);
	}
	
//	public void setBackButtonTextSize(float size) {
//		mBackBtn.setTextSize(size);
//	}
	
	public void setTitleText(String title) {
		mTitle.setText(title);
	}
	
	public void setTitleText(int resId) {
		mTitle.setText(resId);
	}
	
	public void setTitleTextSize(float size) {
		mTitle.setTextSize(size);
	}
	
	public void setTitleTextColor(int color) {
		mTitle.setTextColor(color);
	}

}
