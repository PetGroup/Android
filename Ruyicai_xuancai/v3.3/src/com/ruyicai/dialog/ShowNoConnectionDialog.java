/**
 * Copyright 2010 PalmDream
 * All right reserved.
 * Development History:
 * Date             Author          Version            Modify
 * 2010-5-18        fqc              1.5                none
 */

package com.ruyicai.dialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import com.palmdream.RuyicaiAndroidxuancai.R;
import com.ruyicai.activity.home.HomeActivity;
import com.ruyicai.activity.home.TransitActivity;


public class ShowNoConnectionDialog extends Activity {

	Context ctx;
	HomeActivity iParent;

	/* ���캯�� */
	public ShowNoConnectionDialog(Context context, HomeActivity aParent) {
		ctx = context;
		iParent = aParent;
	}

	public void showNoConnectionDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

		builder.setCancelable(true);
		builder.setMessage(R.string.no_connection);
		builder.setTitle(R.string.no_connection_title);
		builder.setPositiveButton(R.string.settings,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						ctx.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
						iParent.finish();
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						iParent.finish();
						return;
					}
				});
		//�������粻ͨ״̬��ת����ҳ���������� BY��˼�� 2012-7-2
		builder.setNeutralButton(R.string.enter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent in = new Intent(ctx, TransitActivity.class);
				ctx.startActivity(in);
				Toast.makeText(ctx,"���粻����", 0).show();
				iParent.finish();
			}
		});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				iParent.finish();
				return;
			}
		});
		

		builder.show();
	}

}
