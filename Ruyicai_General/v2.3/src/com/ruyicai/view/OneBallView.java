/**
 * @Title С�� View��
 * @Description: 
 * @Copyright: Copyright (c) 2009
 * @Company: PalmDream
 * @author FanYaJun
 * @version 1.0 20100618
 */
package com.ruyicai.view;

import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.util.Constants;

public class OneBallView extends ImageView {
	// ��Ա����
	private int iWidth = 0;
	private int iHeight = 0;

	private String iShowString;
	private Paint p = null;

	private int iResId[];
	private Bitmap bitmaps[];
	private int iShowId = 0;
	
	
	private int initColor = 0;
	

	private int iShowStringX = 0;
	private int iShowStringY = 0;
	private int color = Color.WHITE;
	private Context context;

	public OneBallView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;

	}

	public OneBallView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}

	public OneBallView(Context context) {
		super(context);
		this.context = context;

	}
	
	public OneBallView(Context context,int initColor) {
		super(context);
		this.context = context;
        this.initColor = initColor;
	}

	/*
	 * ��ʼ��С����
	 * @param int aWidth ���
	 * @param int aHeight �߶�
	 * @return int ûʲô�ô�
	 */
	public int initBallWidthHeight(int aWidth, int aHeight) {
		// ����Ĳ���
		if (aWidth <= 0 || aHeight <= 0)
			return -1;

		iWidth = aWidth;
		iHeight = aHeight;
		return 0;
	}

	/*
	 * ��ʼ��С��
	 * @param int aWidth ���
	 * @param int aHeight �߶�
	 * @param String sShowString ��ʾString
	 * @param int aResId[] ͼƬIDs
	 * @return int ûʲô�ô�
	 */
	public int initBall(int aWidth, int aHeight, String sShowString,
			int aResId[]) {
		initBallWidthHeight(aWidth, aHeight);
		initBallShowString(sShowString);
		initBallBackground(aResId);
		return 0;
	}

	/*
	 * ��ʼ��С��
	 * @param int aWidth ���
	 * @param int aHeight �߶�
	 * @param String sShowString ��ʾString
	 * @param int aResId[] ͼƬIDs
	 * @return int ûʲô�ô�
	 */
	public int initBall(int aWidth, int aHeight, String sShowString,
			int aResId[], int color) {
		this.color = color;
		initBallWidthHeight(aWidth, aHeight);
		initBallShowString(sShowString);
		initBallBackground(aResId);
		return 0;
	}

	/*
	 * ��ʼ��С��
	 * @param String sShowString ��ʾString
	 * @return int ûʲô�ô�
	 */
	public int initBallShowString(String aShowString) {
		// ����Ĳ���
		if (aShowString == null || aShowString.equalsIgnoreCase(""))
			return -1;

		iShowString = aShowString;
		setPaint(); //���û���
		return 0;
	}
	Bitmap resizeBitmap = null;
	/*
	 * ��ʼ��С��
	 * 
	 * @param int aResId[] ͼƬIDs
	 * 
	 * @return int ûʲô�ô�
	 */
	public int initBallBackground(int aResId[]) {
		if (aResId.length <= 0){
			return -1;
		}

		iResId = aResId;
		bitmaps = new Bitmap[aResId.length];
		for (int i = 0; i < aResId.length; i++) {
			// ����ͼƬ
			Bitmap bitmap = getBitmapFromRes(iResId[i]);
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();

			float sw = ((float) iWidth) / width;
			float sh = ((float) iHeight) / height;

			Matrix matrix = new Matrix();
			matrix.postScale(sw, sh);
			
		    resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,height, matrix, true);
			bitmaps[i] = resizeBitmap;
			bitmap.recycle();
			bitmap = null;
		}
		return 0;
	}
	
	
	/**
	 * ����ͼƬ��Դ
	 */
	public void recycleBitmaps(){
		if(bitmaps==null){
			return;
		}
		
		for (int i = 0; i < bitmaps.length; i++) {
			if(bitmaps[i]!=null && bitmaps[i].isRecycled()==false){
				bitmaps[i].recycle();
				bitmaps[i] = null;
			}
		}
	}
	
	/*
	 * ���û���
	 * 
	 * @return void
	 */
	private void setPaint() {
		if (p == null) {
			p = new Paint();
			
			p.setColor(color);
			// p.setTypeface(font);
			p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
			
			float textSize = 25*(Float.valueOf(Constants.SCREEN_WIDTH)/Float.valueOf(480));
			
			p.setTextSize(textSize);

			// p.setUnderlineText(true);
		}
		float[] stringLength = new float[iShowString.length()];
		p.getTextWidths(iShowString, stringLength);
		float fTemp = 0;
		for (int i1 = 0; i1 < stringLength.length; i1++) {
			fTemp += stringLength[i1];
		}
		iShowStringX = (int) ((iWidth - fTemp) / 2);
		iShowStringY = (iHeight + 17) / 2;
	}

	/*
	 * ��ʾ��һ��ͼƬ
	 * 
	 * @return void
	 */
	public void changeBallColor() {
		iShowId = (iShowId + 1) % iResId.length;
		this.invalidate();
	}

	/**
	 * ��С�����óɻ�ɫ��������Ҫ��Ը���3D��������ʽ�淨��
	 */
	public void setGrey() {
		iShowId = 0;
		invalidate();
	}

	protected Bitmap getBitmapFromRes(int aResId) {
		Resources r = this.getContext().getResources();
		InputStream is = r.openRawResource(aResId);
		return new BitmapDrawable(is).getBitmap();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(iWidth, iHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		/*
		 * Resources r = this.getContext().getResources(); InputStream is =
		 * r.openRawResource(iResId[iShowId]); BitmapDrawable bmpDraw = new
		 * BitmapDrawable(is); Bitmap bmp = bmpDraw.getBitmap();
		 * canvas.drawBitmap(bmp, 0, 0, p);
		 */
		canvas.drawColor(Color.TRANSPARENT);

		// //����һ���µ�ͼƬ
		// BitmapDrawable bmp = new BitmapDrawable(bitmaps[iShowId]);
		// setImageDrawable(bmp);
		canvas.drawBitmap(bitmaps[iShowId], 0, 0, null);
		if(iShowId == 0){
			//����,�����
			p.setColor(Color.BLACK);
		}else{
			p.setColor(Color.WHITE);
		}
		if(initColor == 1){
			p.setColor(Color.WHITE);
		}
		
		canvas.drawText(iShowString, iShowStringX, iShowStringY, p);
	}

	public int getShowId() {
		return iShowId;
	}

	public void startAnim() {
		Animation anim = AnimationUtils.loadAnimation(context,R.anim.push_up_in);
		this.startAnimation(anim);

	}

	/**
	 * ��ȡС��ĺ���
	 */
	public String getNum() {
		if(iShowString == "3+"){
			iShowString = "3";
		}
		return iShowString;
	}
}
