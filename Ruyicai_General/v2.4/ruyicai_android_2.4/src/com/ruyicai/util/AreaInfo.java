/**
 * 
 */
package com.ruyicai.util;

import com.palmdream.RuyicaiAndroid.R;


/**
 * ѡ����Ϣ��
 * 
 * @author Administrator
 * 
 */
public class AreaInfo {
	public int areaNum = 33;// ѡ��С�����
	public int chosenBallSum = 6;// ѡ�����ѡ��С����
	public int ballResId[] = { R.drawable.grey, R.drawable.red };// ѡ��С���л�ͼƬ
	public int aIdStart = 0;// ѡ��С����ʼid
	public int aBallViewText = 1;// С�����ʼֵ
	public int textColor = 0;// ע�����ɫ
	public String textTtitle ;//ѡ������
	/**
	 * ѡ����Ϣ��
	 * @param areaNum       ѡ��С����
	 * @param chosenBallSum ��ѡС����
	 * @param ballResId     ѡ��С�������id�����磬{ R.drawable.grey, R.drawable.red }
	 * @param startId       С����ʼid  һ��Ϊ��0��
	 * @param startText     С����ʵ���� һ��Ϊ��1��
	 * @param textColor  С����ɫ
	 * @param textView  ѡ����ʾ����"������:"
	 */
	public AreaInfo(int areaNum,int chosenBallSum,int ballResId[],int startId,int startText,int textColor ,String textView) {
     this.areaNum = areaNum;
     this.chosenBallSum = chosenBallSum;
     this.ballResId = ballResId;
     this.aIdStart = startId;
     this.aBallViewText = startText;
     this.textColor = textColor;
     this.textTtitle = textView;
	}
}
