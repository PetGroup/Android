package com.ruyicai.activity.usercenter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class UserCenterDialog {
	
//	protected static ProgressDialog progressDialog;
	/**
	 *  �������ӿ�
	 */
	protected static ProgressDialog onCreateDialog(Context context) {
		ProgressDialog progressDialog;
	
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("����������...");
		progressDialog.setIndeterminate(true);
		return progressDialog;
	}


}
