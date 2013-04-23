/**
 * Copyright 2010 PalmDream
 * All right reserved.
 * Development History:
 * Date             Author          Version            Modify
 * 2010-5-18        fqc              1.5                none
 */

package com.palmdream.RuyicaiAndroid;

import com.palmdream.RuyicaiAndroid.R;
import com.palmdream.RuyicaiAndroid.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ShowConnectionDialog extends Activity {

	Context ctx;
	private ShellRWSharesPreferences shellRW;
	boolean hintPreference = false;
	HomePage iHomePage;

	// public boolean isHint(Context ctx){
	// if(shellRW.equals(null)){
	// return false;
	// }
	// else if(shellRW.getPreferencesValue("noHint-") == "true")
	// return false;
	// else return true;
	// }

	boolean isHint() {
		return hintPreference;
	}

	/* ���캯�� */

	ShowConnectionDialog(Context context, HomePage aHomePage,
			ShellRWSharesPreferences shellRW) {
		ctx = context;
		iHomePage = aHomePage;
		this.shellRW = shellRW;
	}

	public void showConnectionDialog(Context ctx1) {

		final Context ctx = ctx1;
		LinearLayout layout = new LinearLayout(ctx);
		layout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		TextView hint = new TextView(ctx);
		hint.setText(R.string.connect_hint);
		hint.setTextAppearance(ctx, android.R.style.TextAppearance_Medium);
		CheckBox checkBox = new CheckBox(ctx);
		checkBox.setText(R.string.no_hint);

		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				hintPreference = isChecked;
			}
		});
		layout.addView(hint, lp);
		layout.addView(checkBox, lp);

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		shellRW = new ShellRWSharesPreferences(ctx, "addInfo");
		builder.setCancelable(true);
		builder.setTitle(R.string.tishi); // ������ 7.7 �����޸ģ�����������ʾ�ı���
		// builder.setMessage(R.string.connect_hint);
		builder.setView(layout);
		// builder.setMultiChoiceItems(R.array.no_hint, new boolean[]{false},
		// new DialogInterface.OnMultiChoiceClickListener(){
		//        
		// @Override
		// public void onClick(DialogInterface dialog, int which, boolean
		// isChecked) {
		// //����preferense �ӿ�
		// hintPreference = isChecked;
		// // if(isChecked) hintPreference = true;
		// // else hintPreference = false;
		// }
		// });

		// builder.setView(forgetPwd);
		// ������ 7.7 �����޸ģ�������������Ϊ��ȷ����
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						HintCheck(hintPreference);
						Message mg = Message.obtain();
						mg.what = 3;
						// mHandler.sendMessage(mHandler.obtainMessage());
						iHomePage.mHandler.sendMessage(mg);
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						hintPreference = false;
						HintCheck(hintPreference);
						iHomePage.finish();
						return;
					}
				});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				iHomePage.finish();
				return;
			}
		});
		builder.show();

	}

	private void HintCheck(boolean isChecked) {
		if (isChecked) {

			shellRW.setUserLoginInfo("noHint", "true");
			// rwSharedPreferences.putValue("noHint", "true");
			// PublicMethod.myOutput("----------setUserLoginInfo" +
			// shellRW.getUserLoginInfo("noHint"));
		} else {
			shellRW.setUserLoginInfo("noHint", "false");
		}
	}

	private void toLogin() {

	}
}
