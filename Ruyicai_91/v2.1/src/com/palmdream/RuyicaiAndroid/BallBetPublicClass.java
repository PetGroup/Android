package com.palmdream.RuyicaiAndroid;

public class BallBetPublicClass {
	public class BallHolder {
		BallGroup mBallGroup = new BallGroup();
		BallGroup DanShi = new BallGroup();
		BallGroup FuShi;
		BallGroup DanTuo;
		int topButtonGroup;
		int flag = 0;
	}

	/**
	 * @author WangYanling ��¼ÿ��ҳ�漰�Ƿ��л�������
	 */
	public class BallHolderFc3d {
		// ����3Dֱѡ
		BallGroupFc3d ZhixuanBallGroup = new BallGroupFc3d();
		// ����3D��3
		BallGroupFc3d Zu3BallGroup = new BallGroupFc3d();
		// ������6
		BallGroupFc3d Zu6BallGroup = new BallGroupFc3d();
		// ���ʵ���
		BallGroupFc3d DantuoBallGroup = new BallGroupFc3d();
		// ��ֵ
		BallGroupFc3d HezhiZhixuanBallGroup = new BallGroupFc3d();
		BallGroupFc3d HezhiZu3BallGroup = new BallGroupFc3d();
		BallGroupFc3d HezhiZu6BallGroup = new BallGroupFc3d();

		// ������ǩRadioGroup
		int topButtonGroup;
		// �Ƿ��л������� 1Ϊ�ǣ�0Ϊ��
		int flag = 0;

	}

	public class BallGroup {
		// ��¼��ѡ�еĺ�ɫ�����ballTabale�е�index
		int[] iRedBallStatus = new int[35];
		int[] iBlueBallStatus = new int[16];
		int[] iFuShiRedBallStatus = new int[35];
		int[] iFuShiBlueBallStatus = new int[16];
		int[] iDTDanMaRedBallStatus = new int[35];
		int[] iTuoRedBallStatus = new int[35];
		int[] iTuoBlueBallStatus = new int[35];
		int[] iDTBlueBallStatus = new int[16];
		int iBeiShu;
		int iQiShu;
		boolean bCheckBox;
	}

	/**
	 * @author WangYanling
	 * @category ��¼��ǰҳ��ؼ��ĸ���״̬�Լ���ѡ�е�С����ballTable�е�index
	 */
	public class BallGroupFc3d {
		// ����3Dֱѡ
		int[] iZhixuanBaiweiBallStatus = new int[10];
		int[] iZhixuanShiweiBallStatus = new int[10];
		int[] iZhixuanGeweiBallStatus = new int[10];
		// ����3D��3
		int[] iZu3A1BallStatus = new int[10];
		int[] iZu3A2BallStatus = new int[10];
		int[] iZu3BBallStatus = new int[10];
		int[] iZu3FushiBallStatus = new int[10];
		boolean bRadioBtnDanshi;
		boolean bRadioBtnFushi;
		// ������6
		int[] iZu6BallStatus = new int[10];
		// ���ʵ���
		int[] iDantuoDanmaBallStatus = new int[10];
		int[] iDantuoTuomaBallStatus = new int[10];
		// ���ʺ�ֱֵѡ
		int[] iHezhiZhixuanBallStatus = new int[28];
		int[] iHezhiZu3BallStatus = new int[26];
		int[] iHezhiZu6BallStatus = new int[22];
		// ����
		int iBeiShu;
		// ����
		int iQiShu;
		// ��ѡ��ѡ��
		boolean bCheckBox;
	}
}
