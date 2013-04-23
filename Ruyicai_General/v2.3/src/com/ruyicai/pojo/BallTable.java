package com.ruyicai.pojo;

/**
 * @Title ��װBallTable
 * @Description: 
 * @Copyright: Copyright (c) 2009
 * @Company: PalmDream
 * @author FanYaJun
 * @version 1.0 20100618
 */

import java.util.Iterator;
import java.util.Vector;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.ruyicai.activity.game.pl3.PL3.BallHolderPL3;
import com.ruyicai.pojo.BallBetPublicClass.BallHolder;
import com.ruyicai.pojo.BallBetPublicClass.BallHolderFc3d;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.PublicMethod;
import com.ruyicai.view.OneBallView;

public class BallTable {
	LinearLayout a;
	public TableLayout tableLayout;
	public Vector<OneBallView> ballViewVector = null;
	public int startId;

	/*
	 * ���캯��
	 * @param LinearLayout aV ��һ��Layout
	 * @param int aLayoutId ��ǰ��LayoutId
	 * @param int aStartId С��Id��ʼ��ֵ
	 * @return void
	 */
	public BallTable(LinearLayout aV, int aLayoutId, int aStartId) {
		a = aV;
		ballViewVector = new Vector<OneBallView>();

		tableLayout = (TableLayout) a.findViewById(aLayoutId);
		tableLayout.setStretchAllColumns(true);

		startId = aStartId;
	}

	public BallTable(LinearLayout aV, int aStartId) {
		a = aV;
		ballViewVector = new Vector<OneBallView>();

		tableLayout = new TableLayout(a.getContext());
		tableLayout.setStretchAllColumns(true);
		a.addView(tableLayout);
		startId = aStartId;
	}

	/*
	 * ���С��View
	 * @param OneBallView aBall Ҫ��ӵ�С��ʵ��
	 * @return void
	 */
	public void addBallView(OneBallView aBall) {
		ballViewVector.add(aBall);
	}

	/*
	 * ��ȡ����С����� ���� Ϊʵ�ʺ��룬��1��ʼ����
	 * @return int[] С�����
	 */
	public int[] getHighlightBallNOs() {
		int i = 0;
		int iSum = ballViewVector.size();
		int iHighlightSum = 0;

		for (i = 0; i < iSum; i++) {
			iHighlightSum += getOneBallStatue(i);
			// ((OneBallView)ballViewVector.elementAt(i)).getShowId();
		}

		int[] iBallNOs = new int[iHighlightSum];
		int iCurrent = 0;
		for (i = 0; i < iSum; i++) {
			if (getOneBallStatue(i) == 1) {
				iBallNOs[iCurrent] = i + 1;
				iBallNOs[iCurrent] = Integer
						.parseInt(((OneBallView) ballViewVector.elementAt(i))
								.getNum());
				iCurrent++;
			}
		}
		return iBallNOs;
	}
	/*
	 * ��ȡ����С����� ���� Ϊʵ�ʺ��룬��1��ʼ����
	 * @return int[] С������С˫��ר��
	 */
	public int[] getHighlightBallNOsbigsmall() {
		int i = 0;
		int iSum = ballViewVector.size();
		int iHighlightSum = 0;

		for (i = 0; i < iSum; i++) {
			iHighlightSum += getOneBallStatue(i);
			// ((OneBallView)ballViewVector.elementAt(i)).getShowId();
		}

		int[] iBallNOs = new int[iHighlightSum];
		int iCurrent = 0;
		for (i = 0; i < iSum; i++) {
			if (getOneBallStatue(i) == 1) {
				iBallNOs[iCurrent] = i + 1;
				iCurrent++;
			}
		}
		return iBallNOs;
	}

	/*
	 * ����С��View
	 */
	public Vector getBallViews() {
		return ballViewVector;
	}

	/*
	 * ��ȡȫ��С��״̬
	 * @return int[] ȫ��С��״̬������
	 */
	public int[] getBallStatus() {
		int i = 0;
		int iSum = ballViewVector.size();
		int[] iBallStatus = new int[iSum];
		// iBallStatus = new int [ballViewVector.size()];

		for (i = 0; i < iSum; i++) {
			iBallStatus[i] = getOneBallStatue(i);
			// ((OneBallView)ballViewVector.elementAt(i)).getShowId();
		}
		return iBallStatus;
	}

	/*
	 * ȥ��С�����
	 * @param int aBallId С��Id
	 */
	public void clearOnBallHighlight(int aBallId) {
		if (getOneBallStatue(aBallId) > 0) {
			((OneBallView) ballViewVector.elementAt(aBallId)).changeBallColor();
		}
	}

	/**
	 * ��ȡĳ��С���״̬
	 * @param int aBallId С��Id
	 * @return int ĳ��С��״̬
	 */
	public int getOneBallStatue(int aBallId) {
		return ((OneBallView) ballViewVector.elementAt(aBallId)).getShowId();
	}

	/*
	 * ��ȡ����С�����
	 * @return int ����С�����
	 */
	public int getHighlightBallNums() {
		int[] ballStates = getBallStatus();
		int iChosenBallSum = 0;
		for (int i = 0; i < ballStates.length; i++) {
			iChosenBallSum += ballStates[i];
		}
		
		return iChosenBallSum;
	}

	/*
	 * �ı�ĳ��С���״̬���������Ƿ��޸�С��״̬
	 * @param int aMaxHighlight ��ǰ������С��ĸ���
	 * @param int aBallId Ҫ�ı��С���Id
	 * @return int 0:δ�ı� 1:
	 */
	// public void changeBallState(int aMaxHighlight,int aBallId){
	public int changeBallState(int aMaxHighlight, int aBallId) {
		int iChosenBallSum = getHighlightBallNums();
		int iCurrentBallStatue = getOneBallStatue(aBallId);

		if (iCurrentBallStatue > 0) {
			ballViewVector.elementAt(aBallId).changeBallColor();
			// fulei
			ballViewVector.elementAt(aBallId).startAnim();
			return PublicConst.BALL_HIGHLIGHT_TO_NOT;
		} else {
			if (iChosenBallSum >= aMaxHighlight) {
				// ��ʾ ȥ��ʽ ����Ͷע ���öԻ���
				return 0;
			} else {
				ballViewVector.elementAt(aBallId).changeBallColor();
				// fulei
				ballViewVector.elementAt(aBallId).startAnim();
				return PublicConst.BALL_TO_HIGHLIGHT;
			}
		}
	}

	public void changeBallStateConfigChange(int iBallId[]) {
		for (int i = 0; i < iBallId.length; i++) {
			if (iBallId[i] == 1) {
				ballViewVector.elementAt(i).changeBallColor();
			}
		}
	}
  
	/*
	 * С��ȫ��������
	 * @return void
	 */
	public void clearAllHighlights() {
		
		for (int i = 0; i < ballViewVector.size(); i++) {
			changeBallState(0, i);
		}
	}

	/*
	 * �������С��
	 * 
	 * @param int aRandomNums �������С�������
	 * 
	 * @return void
	 */
	public void randomChoose(int aRandomNums) {
		int i;
		// turn off all ball
		// for(i=0;i<ballViewVector.size();i++){
		// changeBallState(0,i);
		// }
		clearAllHighlights();
		int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
				aRandomNums, 0, ballViewVector.size() - 1);
		for (i = 0; i < iHighlightBallId.length; i++) {
			// PublicMethod.myOutput("-----"+i+"   "+iHighlightBallId[i]);
			changeBallState(aRandomNums, iHighlightBallId[i]);
		}

	}

	public void randomChooseConfigChange(int aRandomNums, int whichGroupBall) {
		clearAllHighlights();
		if (whichGroupBall == 0) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				// PublicMethod.myOutput("-----"+i+"   "+iHighlightBallId[i]);
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				PublicMethod.myOutput("****isHighLight " + isHighLight
						+ "PublicConst.BALL_TO_HIGHLIGHT "
						+ PublicConst.BALL_TO_HIGHLIGHT);
			
			}
		}
		if (whichGroupBall == 1) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				// PublicMethod.myOutput("-----"+i+"   "+iHighlightBallId[i]);
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				PublicMethod.myOutput("****isHighLight " + isHighLight
						+ "PublicConst.BALL_TO_HIGHLIGHT "
						+ PublicConst.BALL_TO_HIGHLIGHT);
	
			}
		}

	}

	public void sscRandomChooseConfigChange(int aRandomNums) {
		clearAllHighlights();

		int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
				aRandomNums, 0, ballViewVector.size() - 1);
		for (int i = 0; i < iHighlightBallId.length; i++) {
			// PublicMethod.myOutput("-----"+i+"   "+iHighlightBallId[i]);
			int isHighLight = changeBallState(aRandomNums, iHighlightBallId[i]);

		}

	}

	/**
	 * ���ڸ���3D�������л�
	 * 
	 * @param aRandomNums
	 *            �������С��ĸ���
	 * @param mBallHolder
	 *            ��¼��ǰҳ������ؼ�״̬����
	 * @param whichGroupBall
	 *            ���ĸ�BallTable
	 * @param randomNumbers
	 *            �����������Ϊ������ʽ�淨�������淨����ֵ�淨׼����
	 * @return
	 */
	public void randomChooseConfigChangeFc3d(int aRandomNums, int whichGroupBall, int[] randomNumbers) {
		clearAllHighlights();
		// ֱѡ��λ
		if (whichGroupBall == 7) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);

			}
		}
		// ֱѡʮλ
		if (whichGroupBall == 8) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
			
			}
		}
		// ֱѡ��λ
		if (whichGroupBall == 9) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);

			}
		}
		// ��3��ʽ
		if (whichGroupBall == 0) {
			int isHighLight = changeBallState(1, randomNumbers[0]);


		}
		if (whichGroupBall == 1) {
			int isHighLight = changeBallState(1, randomNumbers[0]);


		}
		if (whichGroupBall == 2) {
			int isHighLight = changeBallState(1, randomNumbers[1]);


		}
		// ��3��ʽ
		if (whichGroupBall == 3) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);

			}
		}
		// ��6
		if (whichGroupBall == 4) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);

			}
		}
		// ���ϵ���
		if (whichGroupBall == 5) {
			for (int i = 0; i < aRandomNums; i++) {
				int isHighLight = changeBallState(aRandomNums, randomNumbers[i]);

			}
		}
		// ��������
		if (whichGroupBall == 6) {
			for (int i = randomNumbers.length - aRandomNums; i < randomNumbers.length; i++) {
				int isHighLight = changeBallState(aRandomNums, randomNumbers[i]);

			}
		}
		// ��ֱֵѡ
		if (whichGroupBall == 10) {
			int isHighLight = changeBallState(aRandomNums, randomNumbers[0]);

		}
		// ��ֵ��3
		if (whichGroupBall == 11) {
			int isHighLight = changeBallState(aRandomNums, randomNumbers[0]);

		}
		// ��ֵ��6
		if (whichGroupBall == 12) {
			int isHighLight = changeBallState(aRandomNums, randomNumbers[0]);

		}

	}

	// wangyl 8.9 ������
	/**
	 * �����������������л�
	 * 
	 * @param aRandomNums
	 *            �������С��ĸ���
	 * @param mBallHolder
	 *            ��¼��ǰҳ������ؼ�״̬����
	 * @param whichGroupBall
	 *            ���ĸ�BallTable
	 * @param randomNumbers
	 *            �����������Ϊ������ʽ�淨�������淨����ֵ�淨׼����
	 * @return
	 */
	public void randomChooseConfigChangePL3(int aRandomNums, int whichGroupBall, int[] randomNumbers) {
		clearAllHighlights();
		// ֱѡ��λ
		if (whichGroupBall == 7) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
		
			}
		}
		// ֱѡʮλ
		if (whichGroupBall == 8) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);

			}
		}
		// ֱѡ��λ
		if (whichGroupBall == 9) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);

			}
		}
		// ��3��ʽ
		if (whichGroupBall == 0) {
			int isHighLight = changeBallState(1, randomNumbers[0]);


		}
		if (whichGroupBall == 1) {
			int isHighLight = changeBallState(1, randomNumbers[0]);


		}
		if (whichGroupBall == 2) {
			int isHighLight = changeBallState(1, randomNumbers[1]);


		}
		// ��3��ʽ
		if (whichGroupBall == 3) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
	
			}
		}
		// ��6
		if (whichGroupBall == 4) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
	
			}
		}
		// ��ֱֵѡ
		if (whichGroupBall == 10) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, 27);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);

			}
		}
		// ��ֵ��3
		if (whichGroupBall == 11) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, 25);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);

			}
		}
		// ��ֵ��6
		if (whichGroupBall == 12) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, 21);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);

			}
		}
	}

	// ���ø���С��������ֵ
	public void setMaxHighLight(int aNum) {

	}

	/**
	 * ��ȡĳ��С��ĺ���
	 * 
	 * @param int aBallId С��Id
	 * 
	 * @return int ĳ��С��״̬
	 */
	public String getOneBallNum(int aBallId) {
		return ((OneBallView) ballViewVector.elementAt(aBallId)).getNum();
	}

	/**
	 * 
	 * @���ߣ�
	 * @���ڣ�
	 * @������
	 * @����ֵ��
	 * @�޸��ˣ�
	 * @�޸����ݣ�
	 * @�޸����ڣ�
	 * @�汾��
	 */
	public Context getContext() {

		return a.getContext();
	}

	public int getStartId() {

		return startId;

	}

	public void clearAllBall() {
		ballViewVector = new Vector<OneBallView>();
	}

	/**
	 * �Ƴ�ȫ��С��
	 */
	public void removeView() {
		tableLayout.removeAllViews();
		if(ballViewVector!=null){
			ballViewVector=null;
			ballViewVector = new Vector<OneBallView>();
		}
	}
}