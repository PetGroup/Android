/**
 * 
 */
package com.ruyicai.pojo;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;

/**
 * ѡ����
 * 
 * @author Administrator
 */
public class AreaNum {
	public TableLayout tableLayout;
	public BallTable table;
	public TextView textTitle;
	public LinearLayout layout;

	public int areaNum = 33;// ѡ��С�����
	public int chosenBallSum = 6;// ѡ�����ѡ��С����
	public int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	public int aIdStart = 0;// ѡ��С����ʼid
	public int aBallViewText = 1;// С�����ʼֵ
	public int textColor = 0;// ע�����ɫ
	public String textTtitle;// ѡ������
    public int isNum = 8;//ÿһ�е�С����

	/**
	 * ѡ����Ϣ��
	 * 
	 * @param areaNum
	 *            ѡ��С����
	 * @param areaNum
	 *            ÿһ��С����
	 * @param chosenBallSum
	 *            ��ѡС����
	 * @param ballResId
	 *            ѡ��С�������id�����磬{ R.drawable.grey, R.drawable.red }
	 * @param startId
	 *            С����ʼid һ��Ϊ��0��
	 * @param startText
	 *            С����ʵ���� һ��Ϊ��1��
	 * @param textColor
	 *            С����ɫ
	 * @param textView
	 *            ѡ����ʾ����"������:"
	 */
	public AreaNum(int areaNum,int isNum, int chosenBallSum, int ballResId[],int startId, int startText, int textColor, String textView) {
		this.areaNum = areaNum;
		this.isNum = isNum;
		this.chosenBallSum = chosenBallSum;
		this.ballResId = ballResId;
		this.aIdStart = startId;
		this.aBallViewText = startText;
		this.textColor = textColor;
		this.textTtitle = textView;
	}

	public void initView(int tableLayoutId, int textViewId, View view) {
		tableLayout = (TableLayout) view.findViewById(tableLayoutId);
		textTitle = (TextView) view.findViewById(textViewId);
	}

	public void initView(int tableLayoutId, int textViewId, int linearViewId,View view) {
		layout = (LinearLayout) view.findViewById(linearViewId);
		layout.setVisibility(LinearLayout.VISIBLE);
		tableLayout = (TableLayout) view.findViewById(tableLayoutId);
		textTitle = (TextView) view.findViewById(textViewId);

	}

	public AreaNum(int tableLayoutId, int textViewId, View view) {
		tableLayout = (TableLayout) view.findViewById(tableLayoutId);
		textTitle = (TextView) view.findViewById(textViewId);
	}

	public AreaNum(int tableLayoutId, int textViewId, int linearViewId,
			View view) {
		layout = (LinearLayout) view.findViewById(linearViewId);
		layout.setVisibility(LinearLayout.VISIBLE);
		tableLayout = (TableLayout) view.findViewById(tableLayoutId);
		textTitle = (TextView) view.findViewById(textViewId);

	}

	/**
	 * ��ʼ������
	 */
	public void init() {
		textTitle.setText(textTtitle);
	}
	public void removeView(){
		tableLayout.removeAllViews();
		table.clearAllBall();
	}
}
