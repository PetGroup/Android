package com.ruyicai.activity.usercenter;

import android.app.ProgressDialog;
import android.content.Context;

public class UserCenterDialog {
	
	/**
	 *  �������ӿ�
	 */
	public static ProgressDialog onCreateDialog(Context context) {
			ProgressDialog progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("����������...");
			progressDialog.setIndeterminate(true);
			return progressDialog;
	}
}
