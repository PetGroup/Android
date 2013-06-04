package com.ruyicai.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.palmdream.RuyicaiAndroid.R;
/**
 * �Ի������
 * @author Administrator
 *
 */
public abstract class BaseDialog {
	Activity activity;
	AlertDialog dialog;
	String title;
	String message;
	Button ok ;
	Button cancel;
	View view;

	public BaseDialog(Activity activity, String title, String message) {// ϵͳ��ʾ��
		this.activity = activity;
		dialog = new AlertDialog.Builder(activity).create();
		this.title = title;
		this.message = message;
		setTitle(title);
		setMessage(message);
		setOkButton();
		setCancelButton();
	}

	public void createMyDialog() {// �Զ�����ʾ��
		dialog.getWindow().setContentView(createDefaultView());
	}

	/**
	 * ���ñ���
	 */
	public void setTitle(String title) {
		dialog.setTitle(title);
	}

	/**
	 * ��������
	 * 
	 */
	public void setMessage(String message) {
		dialog.setMessage(message);
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

	public abstract void onOkButton();

	public abstract void onCancelButton();

	/**
	 * ��ʾ�Ի���
	 * 
	 */
	public void showDialog() {
		dialog.show();
	}
    /**
     * ����ȷ����ť����
     */
	public void setOkButtonStr(String ok) {
       this.ok.setText(ok);
	}
    /**
     * ����ȡ����ť����
     */
	public void setCancelButtonStr(String cancel) {
        this.cancel.setText(cancel);
	}

	/**
	 * �Զ�����ʾ��Ĭ�ϵ�view
	 */
	public View createDefaultView() {
		LayoutInflater factory = LayoutInflater.from(activity);
		view = factory.inflate(R.layout.base_dialog_default_view,null);
		TextView title = (TextView) view.findViewById(R.id.zfb_text_title);
		TextView alertText = (TextView) view.findViewById(R.id.zfb_text_alert);
		title.setText(this.title);
		alertText.setText(this.message);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.canel);
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
	 * ���öԻ����͸�
	 * 
	 */
	public void setDialogWH(int width,int height){
	  if(width!=0){
		  view.setMinimumWidth(width);
	  }
	  if(height!=0){
		  view.setMinimumHeight(height); 
	  }
//	  view.setLayoutParams(new ViewGroup.LayoutParams(width, height));
	}
}
