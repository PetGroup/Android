/**
 * @Title С�� View��
 * @Description: 
 * @Copyright: Copyright (c) 2009
 * @Company: PalmDream
 * @author FanYaJun
 * @version 1.0 20100618
 */
package com.palmdream.RuyicaiAndroid;

import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class OneBallView extends View {
	// ��Ա����
	private int iWidth = 0;
	private int iHeight = 0;

	private String iShowString;
	private Paint p = null;

	private int iResId[];
	private int iShowId = 0;

	private int iShowStringX = 0;
	private int iShowStringY = 0;

	public OneBallView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public OneBallView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OneBallView(Context context) {
		super(context);
	}

	/*
	 * ��ʼ��С����
	 * 
	 * @param int aWidth ���
	 * 
	 * @param int aHeight �߶�
	 * 
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
	 * 
	 * @param int aWidth ���
	 * 
	 * @param int aHeight �߶�
	 * 
	 * @param String sShowString ��ʾString
	 * 
	 * @param int aResId[] ͼƬIDs
	 * 
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
	 * 
	 * @param String sShowString ��ʾString
	 * 
	 * @return int ûʲô�ô�
	 */
	public int initBallShowString(String aShowString) {
		// ����Ĳ���
		if (aShowString == null || aShowString.equalsIgnoreCase(""))
			return -1;

		iShowString = aShowString;
		setPaint();
		return 0;
	}

	/*
	 * ��ʼ��С��
	 * 
	 * @param int aResId[] ͼƬIDs
	 * 
	 * @return int ûʲô�ô�
	 */
	public int initBallBackground(int aResId[]) {
		// wrong []
		if (aResId.length <= 0)
			return -1;

		iResId = aResId;

		return 0;
	}

	/*
	 * ���û���
	 * 
	 * @return void
	 */
	private void setPaint() {
		if (p == null) {
			p = new Paint();
			// String familyName = "����";
			// Typeface font =
			// Typeface.create(familyName,Typeface.NORMAL);//Typeface.BOLD);
			// font.
			p.setColor(Color.WHITE);
			// p.setTypeface(font);
			p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
			p.setTextSize(18);
			// p.setUnderlineText(true);
		}
		float[] stringLength = new float[iShowString.length()];
		p.getTextWidths(iShowString, stringLength);
		float fTemp = 0;
		for (int i1 = 0; i1 < stringLength.length; i1++) {
			fTemp += stringLength[i1];
		}
		iShowStringX = (int) ((iWidth - fTemp) / 2);
		iShowStringY = 23;
	}

	/*
	 * ��ʾ��һ��ͼƬ
	 * 
	 * @return void
	 */
	public void showNextId() {
		iShowId = (iShowId + 1) % iResId.length;
		invalidate();
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
		BitmapDrawable bmpDraw = new BitmapDrawable(is);
		return bmpDraw.getBitmap();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(iWidth, iHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		/*
		 * Resources r = this.getContext().getResources(); InputStream is =
		 * r.openRawResource(iResId[iShowId]); BitmapDrawable bmpDraw = new
		 * BitmapDrawable(is); Bitmap bmp = bmpDraw.getBitmap();
		 * canvas.drawBitmap(bmp, 0, 0, p);
		 */
		canvas.drawColor(Color.TRANSPARENT);
		canvas.drawBitmap(getBitmapFromRes(iResId[iShowId]), 0, 0, null);

		canvas.drawText(iShowString, iShowStringX, iShowStringY, p);
	}

	public int getShowId() {
		return iShowId;
	}
}
