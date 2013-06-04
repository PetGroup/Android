package com.ruyicai.custom.jc.button;

import com.palmdream.RuyicaiAndroid91.R;
import com.ruyicai.util.PublicMethod;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
/**
 * ����button��ť���Զ���
 * @author Administrator
 *
 */
public class MyButton extends ImageView{
	private static final String NAMESPACE = "http://www.ruyicai.com/res/custom";
	private static final String NAME = "http://schemas.android.com/apk/res/android";
	private static final String ATTR_TITLE = "layout_gravity";
	private static final int DEFAULTVALUE_DEGREES = 0;
	private boolean isOnClick = false;//button�Ƿ񱻵��
	private int[] bgId = {R.drawable.jc_btn,R.drawable.jc_btn_b};//Ĭ�ϱ���
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Context context;
	private String textContent = "";
	private int size;
	private float x,y;
	private String codeStr;
	public MyButton(Context context){
    	super(context);
    	this.context = context;
    	initBtn();
    }
	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		initBtn();
//		x = attrs.getAttributeResourceValue(NAME, ATTR_TITLE,DEFAULTVALUE_DEGREES);
	}
    public boolean isOnClick() {
		return isOnClick;
	}
	public void setOnClick(boolean isOnClick) {
		this.isOnClick = isOnClick;
	}
	public void initBg(int[] bgId){
		this.bgId = bgId;
	}
	/**
	 * ��ʼ��button
	 */
	public void initBtn(){
		isOnClick = false;
		setBackgroundResource(bgId[0]);
		size = PublicMethod.getPxInt(15, context);
		x = PublicMethod.getPxInt(5, context);
	    y = PublicMethod.getPxInt(23, context);
	}
	/**
	 * ��ť�����֮����������
	 */
	public void onAction(){
		isOnClick = !isOnClick;
		switchBg();
	}
	/**
	 * �л�����ͼƬ
	 */
	public void switchBg(){
		if(isOnClick){
			setBackgroundResource(bgId[1]);
		}else{
			setBackgroundResource(bgId[0]);
		}
	}
	public void setTextSize(int size){
		this.size = size;
	}
	public void setTextX(int x){
		this.x = x;
	}
	public void setTextY(int y){
		this.y = y;
	}
	public void setCodeStr(String text){
		codeStr = text;
	}
	public String getCodeStr() {
		return codeStr;
	}
	public void setBtnText(String text){
		textContent = text;
		postInvalidate();
	}
	public String getBtnText(){
		return textContent;
	}
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setTypeface(null);
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(size);
		canvas.drawText(textContent, x, y, mPaint);
		
		
	}	

}
