package com.ruyicai.dialog;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import com.ruyicai.util.PublicConst;
import com.ruyicai.util.RuyicaiActivityManager;

public class ExitDialogFactory {
	
	public static void createExitDialog( final Activity activity){
		Dialog dialog = new AlertDialog.Builder(activity).setTitle("���Ҫ�뿪��")
		.setMessage("��ȷ��Ҫ�뿪")// ��������
		.setCancelable(false).setPositiveButton(activity.getString(R.string.ok),// ����ȷ����ť
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,	int which) {
						RuyicaiActivityManager.getInstance().exit();
						PublicConst.islogin=false;
						
					}

				}).setNegativeButton(activity.getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						dialog.cancel();
					}
				}).create();// ����
		// ��ʾ�Ի���
		dialog.show();
	}
}
