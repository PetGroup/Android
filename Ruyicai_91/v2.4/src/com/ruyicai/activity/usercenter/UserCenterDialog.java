package com.ruyicai.activity.usercenter;

import android.app.ProgressDialog;
import android.content.Context;

public class UserCenterDialog {
	
//	protected static ProgressDialog progressDialog;
	/**
	 *  �������ӿ�
	 */
	protected static ProgressDialog onCreateDialog(Context context) {
	//	Log.v("11111111111","22222222222222");
		ProgressDialog progressDialog;
	
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("����������...");
		progressDialog.setIndeterminate(true);
		return progressDialog;
	}


}
