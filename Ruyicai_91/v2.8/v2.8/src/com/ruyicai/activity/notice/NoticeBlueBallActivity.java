package com.ruyicai.activity.notice;

import android.os.Bundle;

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
