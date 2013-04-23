package com.palmdream.RuyicaiAndroid;

/**
 * @Title ��װBallTable
 * @Description: 
 * @Copyright: Copyright (c) 2009
 * @Company: PalmDream
 * @author FanYaJun
 * @version 1.0 20100618
 */

import java.util.Vector;

import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.palmdream.RuyicaiAndroid.BallBetPublicClass.BallHolder;
import com.palmdream.RuyicaiAndroid.FC3DTest.BallHolderFc3d;
import com.palmdream.RuyicaiAndroid.PL3.BallHolderPL3;

public class BallTable {
	LinearLayout a;
	public TableLayout tableLayout;
	public Vector<OneBallView> ballViewVector = null;
	public int startId;

	/*
	 * ���캯��
	 * 
	 * @param LinearLayout aV ��һ��Layout
	 * 
	 * @param int aLayoutId ��ǰ��LayoutId
	 * 
	 * @param int aStartId С��Id��ʼ��ֵ
	 * 
	 * @return void
	 */
	public BallTable(LinearLayout aV, int aLayoutId, int aStartId) {
		a = aV;
		ballViewVector = new Vector<OneBallView>();

		tableLayout = (TableLayout) a.findViewById(aLayoutId);
		tableLayout.setStretchAllColumns(true);

		startId = aStartId;
	}

	/*
	 * ���С��View
	 * 
	 * @param OneBallView aBall Ҫ��ӵ�С��ʵ��
	 * 
	 * @return void
	 */
	public void addBallView(OneBallView aBall) {
		ballViewVector.add(aBall);
	}

	/*
	 * ��ȡ����С����� ���� Ϊʵ�ʺ��룬��1��ʼ����
	 * 
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
				iCurrent++;
			}
		}
		return iBallNOs;
	}

	/*
	 * ��ȡȫ��С��״̬
	 * 
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
	 * 
	 * @param int aBallId С��Id
	 */
	public void clearOnBallHighlight(int aBallId) {
		if (getOneBallStatue(aBallId) > 0) {
			((OneBallView) ballViewVector.elementAt(aBallId)).showNextId();
		}
	}

	/*
	 * ��ȡĳ��С���״̬
	 * 
	 * @param int aBallId С��Id
	 * 
	 * @return int ĳ��С��״̬
	 */
	public int getOneBallStatue(int aBallId) {
		return ((OneBallView) ballViewVector.elementAt(aBallId)).getShowId();
	}

	/*
	 * ��ȡ����С�����
	 * 
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
	 * 
	 * @param int aMaxHighlight ��ǰ������С��ĸ���
	 * 
	 * @param int aBallId Ҫ�ı��С���Id
	 * 
	 * @return int 0:δ�ı� 1:
	 */
	// public void changeBallState(int aMaxHighlight,int aBallId){
	public int changeBallState(int aMaxHighlight, int aBallId) {
		int iChosenBallSum = getHighlightBallNums();
		int iCurrentBallStatue = getOneBallStatue(aBallId);

		if (iCurrentBallStatue > 0) {
			ballViewVector.elementAt(aBallId).showNextId();
			return PublicConst.BALL_HIGHLIGHT_TO_NOT;
		} else {
			if (iChosenBallSum >= aMaxHighlight) {
				// ��ʾ ȥ��ʽ ����Ͷע ���öԻ���
				return 0;
			} else {
				ballViewVector.elementAt(aBallId).showNextId();
				return PublicConst.BALL_TO_HIGHLIGHT;
			}
		}
	}

	public void changeBallStateConfigChange(int iBallId[]) {
		for (int i = 0; i < iBallId.length; i++) {
			if (iBallId[i] == 1) {
				ballViewVector.elementAt(i).showNextId();
			}
		}
	}

	/*
	 * С��ȫ��������
	 * 
	 * @return void
	 */
	public void clearAllHighlights() {
		int i;
		// turn off all ball
		for (i = 0; i < ballViewVector.size(); i++) {
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
		/*
		 * if(aBallColor.equalsIgnoreCase("red")){ // �������Ϊ��ʼС���id����26������Ϊ0��25
		 * int[] iHighlightBallId=PublicMethod.getRandomsWithoutCollision(6, 0,
		 * ballViewVector.size()-1); for(i=0;i<iHighlightBallId.length;i++){
		 * //PublicMethod.myOutput("-----"+i+"   "+iHighlightBallId[i]);
		 * changeBallState(6, iHighlightBallId[i]); } } else{ // blue int[]
		 * iHighlightBallId=PublicMethod.getRandomsWithoutCollision(1, 0,
		 * ballViewVector.size()-1); for(i=0;i<iHighlightBallId.length;i++){
		 * //PublicMethod.myOutput("-----"+i+"   "+iHighlightBallId[i]);
		 * changeBallState(1, iHighlightBallId[i]); } }
		 */
	}

	public BallHolder randomChooseConfigChange(int aRandomNums,
			BallHolder mBallHolder, int whichGroupBall) {
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
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.DanShi.iRedBallStatus[iHighlightBallId[i]] = 1;
					PublicMethod
							.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
									+ iHighlightBallId[i]);
				} else
					mBallHolder.DanShi.iRedBallStatus[iHighlightBallId[i]] = 0;
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
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.DanShi.iBlueBallStatus[iHighlightBallId[i]] = 1;
					PublicMethod
							.myOutput("****danshi hongqiu BALL_TO_HIGHLIGHT"
									+ iHighlightBallId[i]);
				} else
					mBallHolder.DanShi.iBlueBallStatus[iHighlightBallId[i]] = 0;
			}
		}

		return mBallHolder;
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
	public BallHolderFc3d randomChooseConfigChangeFc3d(int aRandomNums,
			BallHolderFc3d mBallHolder, int whichGroupBall, int[] randomNumbers) {
		clearAllHighlights();
		// ֱѡ��λ
		if (whichGroupBall == 7) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.ZhixuanBallGroup.iZhixuanBaiweiBallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.ZhixuanBallGroup.iZhixuanBaiweiBallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ֱѡʮλ
		if (whichGroupBall == 8) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.ZhixuanBallGroup.iZhixuanShiweiBallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.ZhixuanBallGroup.iZhixuanShiweiBallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ֱѡ��λ
		if (whichGroupBall == 9) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.ZhixuanBallGroup.iZhixuanGeweiBallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.ZhixuanBallGroup.iZhixuanGeweiBallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ��3��ʽ
		if (whichGroupBall == 0) {
			int isHighLight = changeBallState(1, randomNumbers[0]);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.Zu3BallGroup.iZu3A1BallStatus[randomNumbers[0]] = 1;
			} else
				mBallHolder.Zu3BallGroup.iZu3A1BallStatus[randomNumbers[0]] = 0;

		}
		if (whichGroupBall == 1) {
			int isHighLight = changeBallState(1, randomNumbers[0]);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.Zu3BallGroup.iZu3A2BallStatus[randomNumbers[0]] = 1;
			} else
				mBallHolder.Zu3BallGroup.iZu3A2BallStatus[randomNumbers[0]] = 0;

		}
		if (whichGroupBall == 2) {
			int isHighLight = changeBallState(1, randomNumbers[1]);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.Zu3BallGroup.iZu3BBallStatus[randomNumbers[1]] = 1;
			} else
				mBallHolder.Zu3BallGroup.iZu3BBallStatus[randomNumbers[1]] = 0;

		}
		// ��3��ʽ
		if (whichGroupBall == 3) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.Zu3BallGroup.iZu3FushiBallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.Zu3BallGroup.iZu3FushiBallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ��6
		if (whichGroupBall == 4) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.Zu6BallGroup.iZu6BallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.Zu6BallGroup.iZu6BallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ���ϵ���
		if (whichGroupBall == 5) {
			for (int i = 0; i < aRandomNums; i++) {
				int isHighLight = changeBallState(aRandomNums, randomNumbers[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.DantuoBallGroup.iDantuoDanmaBallStatus[randomNumbers[i]] = 1;
				} else
					mBallHolder.DantuoBallGroup.iDantuoDanmaBallStatus[randomNumbers[i]] = 0;
			}
		}
		// ��������
		if (whichGroupBall == 6) {
			for (int i = randomNumbers.length - aRandomNums; i < randomNumbers.length; i++) {
				int isHighLight = changeBallState(aRandomNums, randomNumbers[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.DantuoBallGroup.iDantuoTuomaBallStatus[randomNumbers[i]] = 1;
				} else
					mBallHolder.DantuoBallGroup.iDantuoTuomaBallStatus[randomNumbers[i]] = 0;
			}
		}
		// ��ֱֵѡ
		if (whichGroupBall == 10) {
			int isHighLight = changeBallState(aRandomNums, randomNumbers[0]);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.HezhiZhixuanBallGroup.iHezhiZhixuanBallStatus[randomNumbers[0]] = 1;
			} else
				mBallHolder.HezhiZhixuanBallGroup.iHezhiZhixuanBallStatus[randomNumbers[0]] = 0;
		}
		// ��ֵ��3
		if (whichGroupBall == 11) {
			int isHighLight = changeBallState(aRandomNums, randomNumbers[0]);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.HezhiZu3BallGroup.iHezhiZu3BallStatus[randomNumbers[0]] = 1;
			} else
				mBallHolder.HezhiZu3BallGroup.iHezhiZu3BallStatus[randomNumbers[0]] = 0;
		}
		// ��ֵ��6
		if (whichGroupBall == 12) {
			int isHighLight = changeBallState(aRandomNums, randomNumbers[0]);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.HezhiZu6BallGroup.iHezhiZu6BallStatus[randomNumbers[0]] = 1;
			} else
				mBallHolder.HezhiZu6BallGroup.iHezhiZu6BallStatus[randomNumbers[0]] = 0;
		}
		return mBallHolder;
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
	public BallHolderPL3 randomChooseConfigChangePL3(int aRandomNums,
			BallHolderPL3 mBallHolder, int whichGroupBall, int[] randomNumbers) {
		clearAllHighlights();
		// ֱѡ��λ
		if (whichGroupBall == 7) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.ZhixuanBallGroup.iZhixuanBaiweiBallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.ZhixuanBallGroup.iZhixuanBaiweiBallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ֱѡʮλ
		if (whichGroupBall == 8) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.ZhixuanBallGroup.iZhixuanShiweiBallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.ZhixuanBallGroup.iZhixuanShiweiBallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ֱѡ��λ
		if (whichGroupBall == 9) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.ZhixuanBallGroup.iZhixuanGeweiBallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.ZhixuanBallGroup.iZhixuanGeweiBallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ��3��ʽ
		if (whichGroupBall == 0) {
			int isHighLight = changeBallState(1, randomNumbers[0]);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.Zu3BallGroup.iZu3A1BallStatus[randomNumbers[0]] = 1;
			} else
				mBallHolder.Zu3BallGroup.iZu3A1BallStatus[randomNumbers[0]] = 0;

		}
		if (whichGroupBall == 1) {
			int isHighLight = changeBallState(1, randomNumbers[0]);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.Zu3BallGroup.iZu3A2BallStatus[randomNumbers[0]] = 1;
			} else
				mBallHolder.Zu3BallGroup.iZu3A2BallStatus[randomNumbers[0]] = 0;

		}
		if (whichGroupBall == 2) {
			int isHighLight = changeBallState(1, randomNumbers[1]);
			if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
				mBallHolder.Zu3BallGroup.iZu3BBallStatus[randomNumbers[1]] = 1;
			} else
				mBallHolder.Zu3BallGroup.iZu3BBallStatus[randomNumbers[1]] = 0;

		}
		// ��3��ʽ
		if (whichGroupBall == 3) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.Zu3BallGroup.iZu3FushiBallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.Zu3BallGroup.iZu3FushiBallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ��6
		if (whichGroupBall == 4) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, ballViewVector.size() - 1);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.Zu6BallGroup.iZu6BallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.Zu6BallGroup.iZu6BallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ��ֱֵѡ
		if (whichGroupBall == 10) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, 27);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.HezhiZhixuanBallGroup.iHezhiZhixuanBallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.HezhiZhixuanBallGroup.iHezhiZhixuanBallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ��ֵ��3
		if (whichGroupBall == 11) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, 25);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.HezhiZu3BallGroup.iHezhiZu3BallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.HezhiZu3BallGroup.iHezhiZu3BallStatus[iHighlightBallId[i]] = 0;
			}
		}
		// ��ֵ��6
		if (whichGroupBall == 12) {
			int[] iHighlightBallId = PublicMethod.getRandomsWithoutCollision(
					aRandomNums, 0, 21);
			for (int i = 0; i < iHighlightBallId.length; i++) {
				int isHighLight = changeBallState(aRandomNums,
						iHighlightBallId[i]);
				if (isHighLight == PublicConst.BALL_TO_HIGHLIGHT) {
					mBallHolder.HezhiZu6BallGroup.iHezhiZu6BallStatus[iHighlightBallId[i]] = 1;
				} else
					mBallHolder.HezhiZu6BallGroup.iHezhiZu6BallStatus[iHighlightBallId[i]] = 0;
			}
		}
		return mBallHolder;
	}

	/*
	 * // ��ʽ ��ѡ public void randomChoose(String aBallColor){ int i; // turn off
	 * all ball for(i=0;i<ballViewVector.size();i++){ changeBallState(0,i); }
	 * 
	 * if(aBallColor.equalsIgnoreCase("red")){ // �������Ϊ��ʼС���id����26������Ϊ0��25
	 * int[] iHighlightBallId=PublicMethod.getRandomsWithoutCollision(6, 0,
	 * ballViewVector.size()-1); for(i=0;i<iHighlightBallId.length;i++){
	 * //PublicMethod.myOutput("-----"+i+"   "+iHighlightBallId[i]);
	 * changeBallState(6, iHighlightBallId[i]); } } else{ // blue int[]
	 * iHighlightBallId=PublicMethod.getRandomsWithoutCollision(1, 0,
	 * ballViewVector.size()-1); for(i=0;i<iHighlightBallId.length;i++){
	 * //PublicMethod.myOutput("-----"+i+"   "+iHighlightBallId[i]);
	 * changeBallState(1, iHighlightBallId[i]); } } }
	 */

	// ���ø���С��������ֵ
	public void setMaxHighLight(int aNum) {

	}
}