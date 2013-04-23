/**
 * 
 */
package com.ruyicai.activity.notice;

import com.ruyicai.activity.buy.BuyActivityGroup;
import com.ruyicai.activity.buy.ssq.SsqJiXuan;
import com.ruyicai.activity.buy.ssq.SsqZiDanTuo;
import com.ruyicai.activity.buy.ssq.SsqZiZhiXuan;
import com.ruyicai.util.Constants;

import android.app.Activity;
import android.os.Bundle;

/**
 * �������б�
 * @author Administrator
 *
 */
public class NoticeActivityGroup extends BuyActivityGroup{

	public static int LOTNO=2;
	public final static int ID_SUB_SHUANGSEQIU_LISTVIEW = 2;
	public final static int ID_SUB_FUCAI3D_LISTVIEW = 3;
	public final static int ID_SUB_QILECAI_LISTVIEW = 4;
	public final static int ID_SUB_PAILIESAN_LISTVIEW = 5; // zlm 8.9 ������ӣ�������
	public final static int ID_SUB_DLC_LISTVIEW = 6; // zlm 8.9
	public final static int ID_SUB_SHISHICAI_LISTVIEW = 7; // zlm 8.9
	public final static int ID_SUB_SFC_LISTVIEW = 8; // zlm 8.9
	public final static int ID_SUB_RXJ_LISTVIEW = 9; // zlm 8.9
	public final static int ID_SUB_LCB_LISTVIEW = 10; // zlm 8.9
	public final static int ID_SUB_JQC_LISTVIEW = 11; // zlm 8.9
	public final static int ID_SUB_DLT_LISTVIEW = 12; // zlm 8.9
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(LOTNO);
        isIssue(false);
	}
	/**
	 * �������������б������б�֮�����ת
	 * @param listViewID
	 *            �б�ID
	 */
	private void initView(int listViewID) {
		switch (listViewID) {
		case NoticeActivityGroup.ID_SUB_SHUANGSEQIU_LISTVIEW:
			String[] topTitles1 ={"˫ɫ�򿪽�����","˫ɫ�򿪽�����","˫ɫ�򿪽�����"};
			String[] titles1 ={"��������","��������","��������"};
			Class[] allId1 ={NoticeRedBallActivity.class,NoticeBlueBallActivity.class,NoticeInfoActivity.class};
			init(titles1, topTitles1, allId1);
			break;
		case NoticeActivityGroup.ID_SUB_FUCAI3D_LISTVIEW:
			String[] topTitles2 ={"����3D��������","����3D��������"};
			String[] titles2 ={"��������","��������"};
			Class[] allId2 ={NoticeRedBallActivity.class,NoticeInfoActivity.class};
			init(titles2, topTitles2, allId2);
			break;
		case NoticeActivityGroup.ID_SUB_QILECAI_LISTVIEW:
			String[] topTitles3 ={"���ֲʿ�������","���ֲʿ�������","���ֲʿ�������"};
			String[] titles3 ={"��������","��������","��������"};
			Class[] allId3 ={NoticeRedBallActivity.class,NoticeBlueBallActivity.class,NoticeInfoActivity.class};
			init(titles3, topTitles3, allId3);
			break;
		case NoticeActivityGroup.ID_SUB_PAILIESAN_LISTVIEW:
			String[] topTitles4 ={"��������������","��������������"};
			String[] titles4 ={"��������","��������"};
			Class[] allId4 ={NoticeRedBallActivity.class,NoticeInfoActivity.class};
			init(titles4, topTitles4, allId4);
			break;
		case NoticeActivityGroup.ID_SUB_DLT_LISTVIEW:
			String[] topTitles5 ={"����͸��������","����͸��������","����͸��������"};
			String[] titles5 ={"��������","��������","��������"};
			Class[] allId5 ={NoticeRedBallActivity.class,NoticeBlueBallActivity.class,NoticeInfoActivity.class};
			init(titles5, topTitles5, allId5);
			break;
		case NoticeActivityGroup.ID_SUB_SHISHICAI_LISTVIEW:
			String[] topTitles6 ={"ʱʱ�ʿ�������"};
			String[] titles6 ={"��������"};
			Class[] allId6 ={NoticeInfoActivity.class};
			init(titles6, topTitles6, allId6);
			break;
		case NoticeActivityGroup.ID_SUB_DLC_LISTVIEW:
			String[] topTitles7 ={"11ѡ5��������","11ѡ5��������"};
			String[] titles7 ={"�����ֲ�","��������"};
			Class[] allId7 ={NoticeRedBallActivity.class,NoticeInfoActivity.class};
			init(titles7, topTitles7, allId7);
			break;
		case NoticeActivityGroup.ID_SUB_SFC_LISTVIEW:
			String[] topTitles8 ={"ʤ���ʿ�������"};
			String[] titles8 ={"��������"};
			Class[] allId8 ={NoticeInfoActivity.class};
			init(titles8, topTitles8, allId8);
			break;
		case NoticeActivityGroup.ID_SUB_RXJ_LISTVIEW:
			String[] topTitles9 ={"��ѡ�ſ�������"};
			String[] titles9 ={"��������"};
			Class[] allId9 ={NoticeInfoActivity.class};
			init(titles9, topTitles9, allId9);
			break;
		case NoticeActivityGroup.ID_SUB_LCB_LISTVIEW:
			String[] topTitles10 ={"�����뿪������"};
			String[] titles10 ={"��������"};
			Class[] allId10 ={NoticeInfoActivity.class};
			init(titles10, topTitles10, allId10);
			break;
		case NoticeActivityGroup.ID_SUB_JQC_LISTVIEW:
			String[] topTitles11 ={"����ʿ�������"};
			String[] titles11 ={"��������"};
			Class[] allId11 ={NoticeInfoActivity.class};
			init(titles11, topTitles11, allId11);
			break;
		}
	}
}
