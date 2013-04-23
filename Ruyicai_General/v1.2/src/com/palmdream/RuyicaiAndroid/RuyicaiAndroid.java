package com.palmdream.RuyicaiAndroid;

import android.content.Intent;
import android.os.Bundle;

public class RuyicaiAndroid extends ScrollableTabActivity {
	int stringId[] = { R.string.kaijianggonggao, R.string.goucaitouzhu,
			R.string.zhanghuchongzhi, R.string.zhuanjiafenxi, R.string.gengduo };
	// int drawableId[]={R.drawable.star_big_on,R.drawable.icon};
	int drawableId[] = { R.drawable.kaijiang, R.drawable.goucai,
			R.drawable.zhanghu, R.drawable.zhuanjia, R.drawable.gengduo };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ������ 7.4 �����޸ģ���ӵ�½���ж�
		ShellRWSharesPreferences pre = new ShellRWSharesPreferences(
				RuyicaiAndroid.this, "addInfo");
		pre.setUserLoginInfo("sessionid", "");
		for (int i = 0; i < 5; i++) {
			Intent intent = null;

			switch (i) {
			case 0:
				intent = new Intent(this, NoticePrizesOfLottery.class);
				// intent = new Intent(this, BuyLotteryMainList.class);
				// intent = new Intent(this, UserLogin.class);
				break;
			case 1:
				intent = new Intent(this, BuyLotteryMainList.class);
				// intent = new Intent(this, NoticePrizesOfLottery.class);
				// intent = new Intent(this, UserRegister.class);
				break;
			case 2:
				intent = new Intent(this, AccountRecharge.class);
				break;
			case 3:
				intent = new Intent(this, ExpertAnalyze.class);
//				intent = new Intent(this, RuyiExpressFeel.class);
				// intent = new Intent(this, RuYiChuanQingMainPage.class);
				// intent = new Intent(this, UserRegister.class);
				break;
			case 4:
				intent = new Intent(this, RuyiHelper.class);
			default:
				break;
			}
			/*
			 * This adds a title and an image to the tab bar button Image should
			 * be a PNG file with transparent background. Shades are opaque
			 * areas in on and off state are specific as parameters
			 */
			// this.addTab("title"+i, R.drawable.star_big_on,
			// RadioStateDrawable.SHADE_GRAY,
			// RadioStateDrawable.SHADE_GREEN,intent);
			String sss = getResources().getString(stringId[i]);
			// PublicMethod.myOutput("-----"+i+":"+sss);
			// this.addTab(sss, drawableId[0], RadioStateDrawable.SHADE_GRAY,
			// RadioStateDrawable.SHADE_GREEN,intent);
			this.addTab(sss, drawableId[i], RadioStateDrawable.SHADE_GRAY,
					RadioStateDrawable.NOW_COLOR, intent);
		}
		/*
		 * { Intent intent = null; intent = new Intent(this, UserLogin.class);
		 * this.addMyIntent(intent); }
		 */
		/*
		 * commit is required to redraw the bar after add tabs are added if you
		 * know of a better way, drop me your suggestion please.
		 */
		commit();
	}

}