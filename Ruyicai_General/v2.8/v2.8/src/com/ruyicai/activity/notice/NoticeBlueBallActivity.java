package com.ruyicai.activity.notice;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.palmdream.RuyicaiAndroid.R;

/**
 * ��������ͼ
 * @author Administrator
 *
 */
public class NoticeBlueBallActivity extends NoticeBallActivity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addBallView(NoticeActivityGroup.LOTNO,false);
	}
}
