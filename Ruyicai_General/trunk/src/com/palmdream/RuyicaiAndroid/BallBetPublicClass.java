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

	public class BallGroup {
		// 记录被选中的红色球的在ballTabale中的index
		int[] iRedBallStatus = new int[33];
		int[] iBlueBallStatus = new int[16];
		int[] iFuShiRedBallStatus = new int[33];
		int[] iFuShiBlueBallStatus = new int[16];
		int[] iDTDanMaRedBallStatus = new int[33];
		int[] iTuoRedBallStatus = new int[33];
		int[] iDTBlueBallStatus = new int[16];
		int iBeiShu;
		int iQiShu;
		boolean bCheckBox;
	}
}
