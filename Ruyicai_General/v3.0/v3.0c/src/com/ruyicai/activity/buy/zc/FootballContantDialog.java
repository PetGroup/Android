package com.ruyicai.activity.buy.zc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

public class FootballContantDialog extends Activity{ 
	private static String alertIssueNOFQueue = "��Ʊδ��ʽ���ۣ��������δ����,�����ڴ�!";
	
	public static void alertIssueNOFQueue(Context mContext) {
		Builder dialog = new AlertDialog.Builder(mContext).setTitle("��ʾ")
			.setMessage(alertIssueNOFQueue).setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
					
					}
				});
		dialog.show();
	}

}
