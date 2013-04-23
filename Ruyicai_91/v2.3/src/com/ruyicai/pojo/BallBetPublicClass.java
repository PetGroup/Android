package com.ruyicai.pojo;

public class BallBetPublicClass {
	public class BallHolder {
		public BallGroup mBallGroup = new BallGroup();
		public BallGroup DanShi = new BallGroup();
		public BallGroup FuShi;
		public BallGroup DanTuo;
		public int topButtonGroup;
		public int flag = 0;
	}

	/**
	 * @author WangYanling ��¼ÿ��ҳ�漰�Ƿ��л�������
	 */
	public class BallHolderFc3d {
		// ����3Dֱѡ
		public  BallGroupFc3d ZhixuanBallGroup = new BallGroupFc3d();
		// ����3D��3
		public BallGroupFc3d Zu3BallGroup = new BallGroupFc3d();
		// ������6
		public  BallGroupFc3d Zu6BallGroup = new BallGroupFc3d();
		// ���ʵ���
		public BallGroupFc3d DantuoBallGroup = new BallGroupFc3d();
		// ��ֵ
		public BallGroupFc3d HezhiZhixuanBallGroup = new BallGroupFc3d();
		public BallGroupFc3d HezhiZu3BallGroup = new BallGroupFc3d();
		public BallGroupFc3d HezhiZu6BallGroup = new BallGroupFc3d();

		// ������ǩRadioGroup
		public int topButtonGroup;
		// �Ƿ��л������� 1Ϊ�ǣ�0Ϊ��
		public int flag = 0;

	}

	public class BallGroup {
		// ��¼��ѡ�еĺ�ɫ�����ballTabale�е�index
		public int[] iRedBallStatus = new int[35];
		public int[] iBlueBallStatus = new int[16];
		public int[] iFuShiRedBallStatus = new int[35];
		public int[] iFuShiBlueBallStatus = new int[16];
		public int[] iDTDanMaRedBallStatus = new int[35];
		public int[] iTuoRedBallStatus = new int[35];
		public int[] iTuoBlueBallStatus = new int[35];
		public int[] iDTBlueBallStatus = new int[16];
		public int iBeiShu;
		public int iQiShu;
		public boolean bCheckBox;
	}

	/**
	 * @author WangYanling
	 * @category ��¼��ǰҳ��ؼ��ĸ���״̬�Լ���ѡ�е�С����ballTable�е�index
	 */
	public class BallGroupFc3d {
		// ����3Dֱѡ
		public int[] iZhixuanBaiweiBallStatus = new int[10];
		public int[] iZhixuanShiweiBallStatus = new int[10];
		public int[] iZhixuanGeweiBallStatus = new int[10];
		// ����3D��3
		public int[] iZu3A1BallStatus = new int[10];
		public int[] iZu3A2BallStatus = new int[10];
		public int[] iZu3BBallStatus = new int[10];
		public int[] iZu3FushiBallStatus = new int[10];
		public boolean bRadioBtnDanshi;
		public boolean bRadioBtnFushi;
		// ������6
		public int[] iZu6BallStatus = new int[10];
		// ���ʵ���
		public int[] iDantuoDanmaBallStatus = new int[10];
		public int[] iDantuoTuomaBallStatus = new int[10];
		// ���ʺ�ֱֵѡ
		public int[] iHezhiZhixuanBallStatus = new int[28];
		public int[] iHezhiZu3BallStatus = new int[26];
		public int[] iHezhiZu6BallStatus = new int[22];
		// ����
		public int iBeiShu;
		// ����
		public int iQiShu;
		// ��ѡ��ѡ��
		public boolean bCheckBox;
	}
}
