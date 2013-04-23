package com.ruyicai.dialog;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.activity.home.HomeActivity;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.RuyicaiActivityManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.view.View;
/**
 * ������ݷ�ʽ
 * @author Administrator
 *
 */
public class ShortcutDialog extends BaseDialog {
    Activity activity;
	public ShortcutDialog(Activity activity, String title, String message) {
		super(activity, title, message);
		this.activity = activity;
	}

	@Override
	public void onOkButton() {
		// TODO Auto-generated method stub
		addShortcut();
	}

	@Override
	public void onCancelButton() {
		// TODO Auto-generated method stub

	}
	/** 
	 * Ϊ���򴴽������ݷ�ʽ 
	 */  
	private void addShortcut(){  
	    Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");  
	          
	    //��ݷ�ʽ������  
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, activity.getString(R.string.app_name));  
	    shortcut.putExtra("duplicate", false); //�������ظ�����  
	          
	    //��ݷ�ʽ�Ķ���
	    Intent myIntent = new Intent(activity,HomeActivity.class);
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);  
	  
	    //��ݷ�ʽ��ͼ��  
	    ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(activity, R.drawable.icon);  
	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);  
	    activity.sendBroadcast(shortcut);  
	}

}
