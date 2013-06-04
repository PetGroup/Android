package com.ruyicai.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
import com.ruyicai.util.PublicConst;
import com.ruyicai.util.RuyicaiActivityManager;
/**
 * �˳���
 * @author Administrator
 *
 */
public class ExitDialogFactory {
	Activity activity;
	AlertDialog dialog;
	View view;
	public ExitDialogFactory(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		dialog = new AlertDialog.Builder(activity).create();
		setOkButton();
		setCancelButton();
	}
	/**
	 * �Զ�����ʾ��Ĭ�ϵ�view
	 */
	public View createDefaultView() {
		LayoutInflater factory = LayoutInflater.from(activity);
		view = factory.inflate(R.layout.exitdialog,null);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.canel);
		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
				onOkButton();
				
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.cancel();
				onCancelButton();
			}
		});
		return view;
	}

	/**
	 * ����ȷ����ť�¼�
	 */
	public void setOkButton() {
		dialog.setButton(activity.getString(R.string.ok),// ����ȷ����ť
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						onOkButton();
					}
				});
	}

	/**
	 * ����ȡ����ť�¼�
	 */
	public void setCancelButton() {
		dialog.setButton2(activity.getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						onCancelButton();
					}
				});
	}
	public void onOkButton() {
		// TODO Auto-generated method stub
		RuyicaiActivityManager.getInstance().exit();
		PublicConst.islogin=false;
	}

	public void onCancelButton() {
		// TODO Auto-generated method stub
		
	}
	private void show(){
		dialog.show();
		dialog.getWindow().setContentView(createDefaultView());
	}
	public static void createExitDialog(Activity activity) {
		// TODO Auto-generated method stub
		ExitDialogFactory dialog2 = new ExitDialogFactory(activity);
		dialog2.show();
	}

}
